// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.view.*;



/**
 */
public class CompoundCheckAction extends Action {

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

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		java.util.Enumeration enum = request.getAttributeNames();
		while (enum.hasMoreElements()) {
			String element = (String) enum.nextElement();
			System.out.println(element);
		}

		if (queryCollect == null) {
			System.out.println("QueryCollection object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
			ActionForward thisForward = mapping.findForward("failure");
		}else{	
			if (!queryCollect.hasCompoundQuery()) {
				System.out.println("QueryCollection has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
				ActionForward thisForward = mapping.findForward("failure");
				}
		}
//Send to the appropriate view as per selection!!
		ActionForward thisForward = mapping.findForward("success");
		
		return thisForward;

     }
}
