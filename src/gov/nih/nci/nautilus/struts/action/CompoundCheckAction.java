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
import gov.nih.nci.nautilus.constants.Constants;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;



/**
 */
public class CompoundCheckAction extends Action {

	private void print(ResultSet[] geneExprObjects) {
		if(geneExprObjects != null){
			System.out.println("Number of Records:"+ geneExprObjects.length);
			for (int i =0; i < geneExprObjects.length; i++) {
				GeneExpr.GeneExprSingle expObj = (GeneExpr.GeneExprSingle) geneExprObjects[i];
				if(expObj != null){
				System.out.println( "uID: " + expObj.getDesId() + "|geneSymbol: " + expObj.getGeneSymbol() +"|clone: " + expObj.getCloneName()+"|probeSet: "+expObj.getProbesetName()+"|biospecimenID: " + expObj.getBiospecimenId() );
				}
			}
		}
		
	}

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

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);

		if (queryCollect != null) { 
			if (queryCollect.hasCompoundQuery()) {
				
					try {
						// Execute the query and place the query in session
						ResultSet[] queryResultSetObjects = QueryManager.executeQuery(queryCollect.getCompoundQuery());
						print(queryResultSetObjects);
						request.getSession().setAttribute(Constants.RESULTSET_KEY,queryResultSetObjects);
						

						ActionForward thisForward = mapping.findForward("success");
						return thisForward;
					}
					catch (Exception ex) {
						ex.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(ex.getMessage()));
						this.saveErrors(request, errors);
					}
				} 
			else {
				System.out.println("QueryCollection has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
			}
		}else{
			System.out.println("QueryCollection object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
		}
		ActionForward thisForward = mapping.findForward("failure");
//		ActionForward thisForward = new ActionForward(mapping.getInput());
		return thisForward;

     }
}
