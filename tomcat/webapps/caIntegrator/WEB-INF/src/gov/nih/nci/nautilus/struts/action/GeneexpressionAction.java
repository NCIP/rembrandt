// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.GeneExpressionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.view.*;
import gov.nih.nci.nautilus.constants.Constants;



/** 
 * GeneexpressionAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:action path="/geneexpression" name="geneexpressionForm" input="/jsp/geneexpression.jsp" validate="true"
 * @struts:action-forward name="/jsp/advanceSearchMenu.jsp" path="/jsp/advanceSearchMenu.jsp"
 * @struts:action-forward name="/jsp/queryPreview.jsp" path="/jsp/queryPreview.jsp"
 */
public class GeneexpressionAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

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
		GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
		
		String thisView = geneExpressionForm.getResultView();
		// Create Query Objects
		GeneExpressionQuery geneExpQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
		geneExpQuery.setQueryName(geneExpressionForm.getQueryName());
		// Change this code later to get view type directly from Form !!
		if (thisView.equalsIgnoreCase("sample")) 
			geneExpQuery.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
		else if (thisView.equalsIgnoreCase("gene"))
			geneExpQuery.setAssociatedView(ViewFactory.newView(ViewType.Gene_VIEW_TYPE));
		
		// Set gene criteria
		GeneIDCriteria geneIDCrit = geneExpressionForm.getGeneIDCriteria();
		geneExpQuery.setGeneIDCrit(geneIDCrit);
		
		FoldChangeCriteria foldChangeCrit = geneExpressionForm.getFoldChangeCriteria();
		geneExpQuery.setFoldChgCrit(foldChangeCrit);
		
		RegionCriteria regionCrit = geneExpressionForm.getRegionCriteria();
		geneExpQuery.setRegionCrit(regionCrit);
		
		//Set query in Session.
		if (! geneExpQuery.isEmpty()) {
			// Get Hashmap from session if available
			HashMap queryMap = (HashMap) request.getSession().getAttribute(Constants.QUERY_KEY);
			if (queryMap == null) {
				System.out.println("Query Map in Session is empty");
				queryMap = new HashMap();
			}
			queryMap.put(geneExpQuery.getQueryName(), geneExpQuery);
			request.getSession().setAttribute(Constants.QUERY_KEY, queryMap);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.form.query.geneexp.error"));
			this.saveErrors(request, errors);
			return mapping.findForward("backToGeneExp");
			
		}
		
		
		//Test display of query from Hashmap !!
		HashMap thisQueryMap = (HashMap) request.getSession().getAttribute(Constants.QUERY_KEY);
		Query thisQuery = (Query) thisQueryMap.get(geneExpQuery.getQueryName());
		System.out.println("I am in gene expression action ");

		if (thisQuery.getQueryType().equals(QueryType.GENE_EXPR_QUERY_TYPE)) {
			System.out.println(thisQuery.toString());
		}
		
		
		
		

		return mapping.findForward("advanceSearchMenu");
		}
}
