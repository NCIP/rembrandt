// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.RefineQueryForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.parser.*;
import org.apache.struts.util.LabelValueBean;
import gov.nih.nci.nautilus.view.*;
import gov.nih.nci.nautilus.util.*;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;




import java.util.*;



/** 
 */
public class RefineQueryAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward validate(
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

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);

		if (queryCollect != null){
			//Create a vector of search token values from FormBean
		
// Query 1
			queryName1 = refineQueryForm.getQueryName1();
			queryName2 = refineQueryForm.getQueryName2();
			queryName3 = refineQueryForm.getQueryName3();
			
			// User input a single query 
			if ((queryName1.trim().length() >= 1) && (queryName2.trim().length() < 1) & (queryName3.trim().length() < 1)){
				CompoundQuery compoundQuery = new CompoundQuery(queryCollect.getQuery(queryName1));
				refineQueryForm.setQueryText(queryName1);
				//Stuff compoundquery in queryCollection 
				queryCollect.setCompoundQuery(compoundQuery);
				
				// Get collection of view types
				Collection viewCollection = setRefineQueryView(compoundQuery, request);
				// Set collection of view types
				refineQueryForm.setCompoundViewColl((ArrayList) viewCollection);
				// Set Resultset name for Single Query
				refineQueryForm.setResultsetName(queryName1);		
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
				// Get collection of view types
				Collection viewCollection = setRefineQueryView((CompoundQuery) compoundQuery, request);
				// Set collection of view types in Form
				refineQueryForm.setCompoundViewColl((ArrayList) viewCollection);

				//Stuff compoundquery in queryCollection 
				queryCollect.setCompoundQuery((CompoundQuery) compoundQuery);
			
 
	
			}catch (Exception e){
				refineQueryForm.setQueryText("Error!! "+e.getMessage());
				logger.error("Error Parsing Query and/or creating Compound Query ");
				logger.error(e);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.parse.error",e.getMessage()));

				this.saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

		}else{
			// Query Collection is null.  Go back and display error
			refineQueryForm.setQueryText("Error!! Query Collection is null");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.empty.error"));
			this.saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
			
		}
		return mapping.findForward("displayQuery");
		
     }
     

	private Collection setRefineQueryView(CompoundQuery cQuery, HttpServletRequest request) {

		// Get the Query Collection from the session
		ArrayList queryViewColl = new ArrayList();

		Properties props = new Properties();
		props = ApplicationContext.getLabelProperties();

		
		if (cQuery != null && props != null) {
			
			ViewType [] availableViewTypes = cQuery.getValidViews();
			//Set the View Types array in request to be used on return trip
			request.getSession().setAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY, availableViewTypes);

			
			for (int viewIndex = 0; viewIndex < availableViewTypes.length; viewIndex++) {
				ViewType thisViewType = (ViewType) availableViewTypes[viewIndex];
				String viewText = (String) props.get(thisViewType.getClass().getName());

				queryViewColl.add( new LabelValueBean( viewText, Integer.toString(viewIndex)) );
			}
		
		}else {
		
			queryViewColl.add( new LabelValueBean( " ", " " ));
			logger.debug("Compound Query passed is null");
		}
		return queryViewColl;
	}

  
	public ActionForward displayresult(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;


		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		// Get the viewType array from session 
		ViewType [] availableViewTypes = (ViewType []) request.getSession().getAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY);
// 		Set ViewType array in session to null, we dont need it anymore
//		request.getSession().setAttribute(Constants.VALID_QUERY_TYPES_KEY, null);
		
		if (queryCollect != null) {
			if (queryCollect.hasCompoundQuery()) {
				CompoundQuery cQuery = (CompoundQuery) queryCollect.getCompoundQuery();
				logger.debug(refineQueryForm.getCompoundView());
				ViewType selectView = availableViewTypes[Integer.parseInt(refineQueryForm.getCompoundView())];
				logger.debug(selectView);
				// Set View in compoundQuery
				cQuery.setAssociatedView(ViewFactory.newView(selectView));

				// Execute the query and place the query in session
//				ResultSet[] queryResultSetObjects = QueryManager.executeQuery(queryCollect.getCompoundQuery());
//				print(queryResultSetObjects);
//				request.getSession().setAttribute(Constants.RESULTSET_KEY,queryResultSetObjects);
				
			}else {
			    logger.debug("QueryCollection has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
				ActionForward thisForward = mapping.findForward("failure");
			}
		}else{	
		    logger.debug("QueryCollection object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
			ActionForward thisForward = mapping.findForward("failure");
		}
//Send to the appropriate view as per selection!!
		ActionForward thisForward = mapping.findForward("success");
		
		return thisForward;

	 }
  
	private void print(ResultSet[] geneExprObjects) {
		if(geneExprObjects != null){
		    logger.debug("Number of Records:"+ geneExprObjects.length);
			for (int i =0; i < geneExprObjects.length; i++) {
				GeneExpr.GeneExprSingle expObj = (GeneExpr.GeneExprSingle) geneExprObjects[i];
				if(expObj != null){
				    logger.debug( "uID: " + expObj.getDesId() + "|geneSymbol: " + expObj.getGeneSymbol() +"|clone: " + expObj.getCloneName()+"|probeSet: "+expObj.getProbesetName()+"|biospecimenID: " + expObj.getBiospecimenId() );
				}
			}
		}
	}		
     
}
