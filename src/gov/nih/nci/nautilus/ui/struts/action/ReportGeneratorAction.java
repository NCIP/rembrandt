/*
 * Created on Nov 19, 2004
 */ 
package gov.nih.nci.nautilus.ui.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import gov.nih.nci.nautilus.cache.CacheManagerWrapper;
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
import org.dom4j.Document;

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
    	//Get the sessionCache
    	Cache sessionCache = CacheManagerWrapper.getSessionCache(request.getSession().getId());
    	
    	//I think I should add convenience methods to the CachManagerWrapper
    	//to avoid classes from having to know the implementation of the
    	//cache.  For instnce, if a user is looking for a specific reportXML
    	//in the cache, why not allow them to say "hey, give me this reportXML"
    	//and have reportXML returned.  Why should the user have to worry about
    	//cache elements and what not.  --Dave
    	Element cacheElement = sessionCache.get(rgForm.getQueryName());
    	request.setAttribute(NautilusConstants.FILTER_PARAM_MAP, rgForm.getFilterParams());
    	if(cacheElement!=null) {
	    	ReportBean reportBean = (ReportBean)cacheElement.getValue();
	    	//Apply any filters
	    	if("".equals(rgForm.getXsltFileName())||rgForm.getXsltFileName()==null) {
	    		request.setAttribute(NautilusConstants.XSLT_FILE_NAME,NautilusConstants.DEFAULT_XSLT_FILENAME);
	    	}else {
	    		request.setAttribute(NautilusConstants.XSLT_FILE_NAME,rgForm.getXsltFileName());
	    	}
	    	request.setAttribute(NautilusConstants.REPORT_XML, reportBean.getReportXML());
    	}else {
    		throw new IllegalStateException("Can not find the desired report in cache");
    	}
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
        // We obviously have passed validation...
        //So now go back to the submitting page and run
        //java script to spawn the report window.
        request.removeAttribute("previewForm");
        request.setAttribute("preview", new String("yes"));
        request.setAttribute("queryName","temp_results");
        logger.debug("back: " + goBack);
        return mapping.findForward(goBack);
	}
	
}