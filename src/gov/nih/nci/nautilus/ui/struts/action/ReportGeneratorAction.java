/*
 * Created on Nov 19, 2004
 */ 
package gov.nih.nci.nautilus.ui.struts.action;

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
    
    public ActionForward runGeneViewReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
    	ReportGeneratorForm rgForm = (ReportGeneratorForm)form;
    	Cache sessionCache = CacheManagerWrapper.getSessionCache(request.getSession().getId());
    	Element cacheElement = sessionCache.get(rgForm.getQueryName());
    	ReportBean reportBean = (ReportBean)cacheElement.getValue();
    	request.setAttribute(NautilusConstants.REPORT_XML, reportBean.getReportXML());
    	return mapping.findForward("runGeneViewReport");
    }
    
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