// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.RefineQueryForm;

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
import gov.nih.nci.nautilus.parser.*;

import java.util.*;



/** 
 */
public class RefineQueryAction extends Action {


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
		
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		String leftParen = "";
		String queryName1 = "";
		String queryName2 = "";
		String queryName3 = "";
		Query query = null;
		String rightParen = "";
		String operatorType = "";
		Vector vectorOfTokens = new Vector();
		ActionErrors errors = new ActionErrors();

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);

		if (queryCollect != null){
			//Create a vector of search token values from FormBean
		
// Query 1
			queryName1 = refineQueryForm.getQueryName1();
			queryName2 = refineQueryForm.getQueryName2();
			queryName3 = refineQueryForm.getQueryName3();
			
			if ((queryName1.trim().length() >= 1) && (queryName2.trim().length() < 1) & (queryName3.trim().length() < 1)){
				CompoundQuery compoundQuery = new CompoundQuery(queryCollect.getQuery(queryName1));
				refineQueryForm.setQueryText(queryName1);
				//Stuff compoundquery in queryCollection 
				queryCollect.setCompoundQuery(compoundQuery);
				return mapping.findForward("displayQuery");
			}

			if (queryName1 != null) {
				leftParen = refineQueryForm.getLeftParen1();
				query = queryCollect.getQuery(queryName1);
				rightParen = refineQueryForm.getRightParen1();
				operatorType = refineQueryForm.getOperatorType1();
				
				if (leftParen != null && leftParen.length() > 0) vectorOfTokens.add(leftParen);
				if (query != null) vectorOfTokens.add(query);
				if (rightParen != null && rightParen.length() > 0) vectorOfTokens.add(rightParen);
				if (operatorType != null && operatorType.length() > 0) vectorOfTokens.add(operatorType);
			
			}

//	Query 2
			if (queryName2 != null) {
				 leftParen = refineQueryForm.getLeftParen2();
				 query = queryCollect.getQuery(queryName2);
				 rightParen = refineQueryForm.getRightParen2();
				 operatorType = refineQueryForm.getOperatorType2();
				
				if (leftParen != null && leftParen.length() > 0) vectorOfTokens.add(leftParen);
				if (query != null) vectorOfTokens.add(query);
				if (rightParen != null && rightParen.length() > 0) vectorOfTokens.add(rightParen);
				if (operatorType != null && operatorType.length() > 0) vectorOfTokens.add(operatorType);
			
			}
//	Query 3
			if (queryName3 != null) {
				 leftParen = refineQueryForm.getLeftParen3();
				 query = queryCollect.getQuery(queryName3);
				 rightParen = refineQueryForm.getRightParen3();
				
				if (leftParen != null && leftParen.length() > 0) vectorOfTokens.add(leftParen);
				if (query != null) vectorOfTokens.add(query);
				if (rightParen != null && rightParen.length() > 0) vectorOfTokens.add(rightParen);
			
			}

			try {
				Parser queryParser = new Parser(vectorOfTokens);
				queryParser.expression();
				Queriable compoundQuery = queryParser.getCompoundQuery();
				//Display validated query on the screen 
				refineQueryForm.setQueryText(compoundQuery.toString());
				//Stuff compoundquery in queryCollection 
				queryCollect.setCompoundQuery((CompoundQuery) compoundQuery);
			
 
	
			}catch (Exception e){
				refineQueryForm.setQueryText("Error!! "+e.getMessage());
				System.out.println("Error Parsing Query and/or creating Compound Query " + e.getMessage());
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.parse.error",e.getMessage()));

				this.saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

		}else{
			// Query Collection is null.  Go back and display error
			refineQueryForm.setQueryText("Error!! Query Collection is null");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.no.error"));
			this.saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
			
		}
		return mapping.findForward("displayQuery");
		
     }
}
