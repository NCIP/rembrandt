// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.constants.Constants;



/**
 */
public class SelectPresentDispatchAction extends DispatchAction {


	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward changegenelist(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);

// Code used to test Finalize query page in the absense of QueryCollection coming from menu!!
//		queryCollect = new QueryCollection();
//		GeneExpressionQuery q1 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
//		GeneExpressionQuery q2 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
//		GeneExpressionQuery q3 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
//		q1.setQueryName("Query 1");
//		q2.setQueryName("Query 2");
//		q3.setQueryName("Query 3");
//		queryCollect.putQuery(q1);
//		queryCollect.putQuery(q2);
//		queryCollect.putQuery(q3);
//		request.getSession().setAttribute(Constants.QUERY_KEY, queryCollect);
//		Code used to test Finalize query page ends!!

		if (queryCollect != null) {
			if (queryCollect.hasQuery()) {
					ActionForward thisForward = mapping.findForward("success");
					return thisForward;
				}
			else {
				System.out.println("QueryCollection has no queries.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.no.error"));
				this.saveErrors(request, errors);
			}
		}else{
			System.out.println("QueryCollection object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
		}
		ActionForward thisForward = mapping.findForward("failure");

		return thisForward;

     }
}
