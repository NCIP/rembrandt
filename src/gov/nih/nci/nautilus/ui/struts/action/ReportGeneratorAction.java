/*
 * Created on Nov 19, 2004
 */ 
package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.struts.form.ClinicalDataForm;
import gov.nih.nci.nautilus.ui.struts.form.ComparativeGenomicForm;
import gov.nih.nci.nautilus.ui.struts.form.GeneExpressionForm;
import gov.nih.nci.nautilus.ui.struts.form.ReportGeneratorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReportGeneratorAction extends DispatchAction {

    private Logger logger = Logger.getLogger(ReportGeneratorAction.class);
	
    public ActionForward compundReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("generateReport");
	}
    /**
     * This action method should be called when it is desired to actually render
     * a report to a jsp.  It will grab the desired report XML to display from the cache
     * and store it in the request so that it can be rendered.  
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward runGeneViewReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
    	ReportGeneratorForm rgForm = (ReportGeneratorForm)form;
    	String sessionId = request.getSession().getId();
    	ReportBean reportBean = CacheManagerDelegate.getInstance().getReportBean(sessionId,rgForm.getQueryName());
    	if(reportBean!=null) {
	    	if("".equals(rgForm.getXsltFileName())||rgForm.getXsltFileName()==null) {
	    		//If no filters specified then use the default XSLT
	    		request.setAttribute(NautilusConstants.XSLT_FILE_NAME,NautilusConstants.DEFAULT_XSLT_FILENAME);
	    	}else {
	    		//Apply any filters defined in the form
	    		request.setAttribute(NautilusConstants.XSLT_FILE_NAME,rgForm.getXsltFileName());
	    	}
	    	//add the Filter Parameters from the form to the forwarding request
	    	request.setAttribute(NautilusConstants.FILTER_PARAM_MAP, rgForm.getFilterParams());
	    	//put the report xml in the request
	    	request.setAttribute(NautilusConstants.REPORT_XML, reportBean.getReportXML());
    	}else {
    		//Throw an exception because you should never call this action method
    		//unless you have already generated the report and stored it in the cache
    		logger.error( new IllegalStateException("Can not find the desired report in cache"));
    	}
    	//Go to the geneViewReport.jsp to render the report
    	return mapping.findForward("runGeneViewReport");
    }
    /**
     * This action is used to generate a preview report.  Because the current
     * preview is in fact a popup from the build query page this forward
     * actually returns back to the input action.  This action method is currently
     * called by all 3 /preview* action mappings and is necesary to allow for validation
     * before we actually execute a query and display the results.  The previous
     * actions will have already created and executed the preview query.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward previewReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        String goBack=null;	
        if(form instanceof GeneExpressionForm) {
            request.setAttribute("geneexpressionForm", request.getAttribute("previewForm"));
            goBack = "backToGeneExp";
        }else if(form instanceof ClinicalDataForm) {
            request.setAttribute("clinicaldataForm", request.getAttribute("previewForm"));
            goBack = "backToClinical";
        }else if(form instanceof ComparativeGenomicForm) {
            request.setAttribute("comparitivegenomicForm", request.getAttribute("previewForm"));
            goBack = "backToCGH";
        }
        //We obviously have passed validation...
        //So now go back to the submitting page and run
        //java script to spawn the report window.
        request.removeAttribute("previewForm");
        request.setAttribute("preview", new String("yes"));
        request.setAttribute("queryName",NautilusConstants.PREVIEW_RESULTS);
        logger.debug("back: " + goBack);
        return mapping.findForward(goBack);
	}
	public ActionForward submitSamples(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportGeneratorForm rgForm = (ReportGeneratorForm)form;
		return runGeneViewReport(mapping, form, request, response);
	}
	
}