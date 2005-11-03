package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




/**
 */
public class CompoundCheckAction extends Action {
    private Logger logger = Logger.getLogger(CompoundCheckAction.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
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

		ActionErrors errors = new ActionErrors();
		String sessionId = request.getSession().getId();
		SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
		if (queryBag == null) {
		    logger.debug("SessionQueryBag object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
			ActionForward thisForward = mapping.findForward("failure");
		}else{	
			if (!queryBag.hasCompoundQuery()) {
			    logger.debug("SessionQueryBag has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
				ActionForward thisForward = mapping.findForward("failure");
				}
		}
		//Send to the appropriate view as per selection!!
		ActionForward thisForward = mapping.findForward("success");
		return thisForward;

     }
}
