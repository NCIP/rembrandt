/*
 * Created on Nov 19, 2004
 */ 
package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.struts.form.ClinicalDataForm;
import gov.nih.nci.nautilus.ui.struts.form.ComparativeGenomicForm;
import gov.nih.nci.nautilus.ui.struts.form.GeneExpressionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReportGeneratorAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass());
	
    public ActionForward compundReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("generateReport");
	}

	public ActionForward previewReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        String goBack=null;	
        if(form instanceof GeneExpressionForm) {
            request.setAttribute("geneexpressionForm", request.getAttribute("previewForm"));
            goBack = "backToGeneExp";
        }else if(form instanceof ClinicalDataForm) {
            goBack = "backToCGH";
        }else if(form instanceof ComparativeGenomicForm) {
            goBack = "backToClinical";
        }
        // We obviously have passed validation...
        //So now go back to the submitting page and run
        //java script to spawn the report window.
        request.setAttribute("preview", new String("yes"));
        logger.debug("back: " + goBack);
        return mapping.findForward(goBack);
	}
}