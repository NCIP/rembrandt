package gov.nih.nci.nautilus.ui.struts.action;


import gov.nih.nci.nautilus.constants.NautilusConstants;
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
public class RefineCheckAction extends Action {
    private static Logger logger = Logger.getLogger(RefineCheckAction.class);

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
		request.getSession().setAttribute("currentPage", "0");
		request.getSession().removeAttribute("currentPage2");

		SessionQueryBag queryCollect = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);

		if (queryCollect != null) { 
			if (queryCollect.hasQuery()) {
					ActionForward thisForward = mapping.findForward("success");
					return thisForward;
				} 
			else {
			    logger.debug("SessionQueryBag has no queries.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.no.query.error"));
				this.saveErrors(request, errors);
			}
		}else{
		    logger.debug("SessionQueryBag object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
		}
		ActionForward thisForward = mapping.findForward("failure");

		return thisForward;

     }
}
