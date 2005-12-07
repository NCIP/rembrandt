package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.UploadGeneSetForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class UploadGeneSetAction extends Action{
    private static Logger logger = Logger.getLogger(RefineQueryAction.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private SessionCriteriaBag sessionCriteriaBag;
    
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
        
        ActionForward thisForward;
        UploadGeneSetForm uploadGeneSetForm = (UploadGeneSetForm) form;
        logger.debug("This is a a gene set upload");
        
        //check if file has been uploaded yet
        String sessionId = request.getSession().getId();
        String nameKey = uploadGeneSetForm.getGeneSetName();
        sessionCriteriaBag = presentationTierCache.getSessionCriteriaBag(sessionId);
        if(sessionCriteriaBag.getUserList(ListType.GeneIdentifierSet,nameKey)!=null){
            thisForward = mapping.findForward("success");
        }
        else thisForward = mapping.findForward("backToUpload");
        
	
	return thisForward;
 }
      
}
