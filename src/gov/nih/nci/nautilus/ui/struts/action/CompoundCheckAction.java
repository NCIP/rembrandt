// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.ui.struts.action;


import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;

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
	private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
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
		SessionQueryBag queryBag = cacheManager.getSessionQueryBag(sessionId);
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
