// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.ComparativeGenomicForm;

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
 * ComparitivegenomicAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 09-12-2004
 * 
 * XDoclet definition:
 * @struts:action path="/comparitivegenomic" name="comparitivegenomicForm" input="/jsp/comparitivegenomic.jsp" validate="true"
 * @struts:action-forward name="nautilus.menu" path="nautilus.menu"
 * @struts:action-forward name="nautilus.comparitiveGenomic" path="nautilus.comparitiveGenomic"
 */
public class ComparativeGenomicAction extends Action {

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
		  request.getSession().setAttribute("currentPage", "0");
	      request.getSession().removeAttribute("currentPage2");	 
		     
			ComparativeGenomicForm comparativeGenomicForm =
				(ComparativeGenomicForm) form;				
			
			String thisView = comparativeGenomicForm.getResultView();
			
			// Create Query Objects
			ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
			cghQuery.setQueryName(comparativeGenomicForm.getQueryName());
			
			// Change this code later to get view type directly from Form !!
			if (thisView.equalsIgnoreCase("sample")) {
				cghQuery.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
				}
			else if (thisView.equalsIgnoreCase("gene")){
				cghQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW)); 
				}

				
				
		
	   // set Disease criteria
		DiseaseOrGradeCriteria diseaseOrGradeCriteria = comparativeGenomicForm.getDiseaseOrGradeCriteria();
		if(!diseaseOrGradeCriteria.isEmpty()){
		   cghQuery.setDiseaseOrGradeCrit(diseaseOrGradeCriteria);		
		}		

		// Set gene criteria
		GeneIDCriteria geneIDCrit = comparativeGenomicForm.getGeneIDCriteria();
		if(!geneIDCrit.isEmpty()){
		   cghQuery.setGeneIDCrit(geneIDCrit);
		}
		
		// set copy number criteria
		CopyNumberCriteria CopyNumberCrit = comparativeGenomicForm.getCopyNumberCriteria();
		if(!CopyNumberCrit.isEmpty()){
		   cghQuery.setCopyNumberCrit(CopyNumberCrit);
		   }
		
		// set region criteria
		RegionCriteria regionCrit = comparativeGenomicForm.getRegionCriteria();
		if(!regionCrit.isEmpty()){
		  cghQuery.setRegionCrit(regionCrit);
		  }
	    
		// set clone/probe criteria
		CloneOrProbeIDCriteria cloneOrProbeIDCriteria = comparativeGenomicForm.getCloneOrProbeIDCriteria();
		if(!cloneOrProbeIDCriteria.isEmpty()){
		  cghQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
		  }
		
		// set snp criteria
		SNPCriteria  snpCrit = comparativeGenomicForm.getSNPCriteria();
		if(!snpCrit.isEmpty()){
		   cghQuery.setSNPCrit(snpCrit);
		   }
		
		// set allele criteria
		AlleleFrequencyCriteria  alleleFrequencyCriteria = comparativeGenomicForm.getAlleleFrequencyCriteria();
		if(!alleleFrequencyCriteria.isEmpty()){
		  cghQuery.setAlleleFrequencyCrit(alleleFrequencyCriteria);
		  }
		
		
		AssayPlatformCriteria assayPlatformCriteria = comparativeGenomicForm.getAssayPlatformCriteria();
		if(!assayPlatformCriteria.isEmpty()){		
		    cghQuery.setAssayPlatformCrit(assayPlatformCriteria);		
		  }
		
		try{
		
			//Set query in Session.
			if (! cghQuery.isEmpty()) {			  
			 
				// Get Hashmap from session if available
				
				QueryCollection queryCollection = (QueryCollection)request.getSession().getAttribute(Constants.QUERY_KEY);
		        if( queryCollection == null){				  
				   queryCollection = new QueryCollection();
				  }
				queryCollection.putQuery(cghQuery);
				request.getSession().setAttribute(Constants.QUERY_KEY, queryCollection);
				
				
				/*HashMap queryMap = (HashMap) request.getSession().getAttribute(Constants.QUERY_KEY);
				  if (queryMap == null) {
				  System.out.println("Query Map in Session is empty");
					queryMap = new HashMap();
				}
				queryMap.put(cghQuery.getQueryName(), cghQuery);
				request.getSession().setAttribute(Constants.QUERY_KEY, queryMap);
				*/
			} else {
			
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.form.query.cgh.error"));
				this.saveErrors(request, errors);
				return mapping.findForward("backToCGHExp");
				
			   }
			 
			  }		
		
		catch(Exception e){
		  e.printStackTrace();
		 }	
		 
        if (comparativeGenomicForm.getMethod().equals("run report")) {
            CompoundQuery compoundQuery = new CompoundQuery(cghQuery);
            compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            QueryCollection collection = new QueryCollection();
            collection.setCompoundQuery(compoundQuery);
            request.setAttribute(Constants.QUERY_KEY, collection);
            return mapping.findForward("previewReport");
        } else {
            return mapping.findForward("advanceSearchMenu");
        }			
		
		
	} //throw new UnsupportedOperationException("Generated method 'execute(...)' not implemented.");
		

}
