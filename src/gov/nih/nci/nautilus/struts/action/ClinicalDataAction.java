// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.ClinicalDataForm;

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
import gov.nih.nci.nautilus.constants.NautilusConstants;



/** 
 * ClinicalDataAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:action path="/ClinicalData" name="ClinicalDataActionForm" input="/jsp/clinicalDataAction.jsp" validate="true"
 * @struts:action-forward name="/jsp/advanceSearchMenu.jsp" path="/jsp/advanceSearchMenu.jsp"
 * @struts:action-forward name="/jsp/queryPreview.jsp" path="/jsp/queryPreview.jsp"
 */
public class ClinicalDataAction extends Action {

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
		  
		ClinicalDataForm clinicalDataForm = (ClinicalDataForm) form;
	
		
		String thisView = clinicalDataForm.getResultView();
		// Create Query Objects
		ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		clinicalDataQuery.setQueryName(clinicalDataForm.getQueryName());
		
		// Change this code later to get view type directly from Form !!
		if (thisView.equalsIgnoreCase("sample")) {
			clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
			//clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
			}
		else if (thisView.equalsIgnoreCase("gene")){
			clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
			//clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_VIEW_TYPE)); 
			}
 
 
		// Set disease criteria	
	    DiseaseOrGradeCriteria diseaseOrGradeCrit = clinicalDataForm.getDiseaseOrGradeCriteria();		
		if(!diseaseOrGradeCrit.isEmpty()){
		   clinicalDataQuery.setDiseaseOrGradeCrit(diseaseOrGradeCrit);
		}
				
 
		// Set Occurrence criteria
		OccurrenceCriteria occurrenceCrit = clinicalDataForm.getOccurrenceCriteria();
		if(!occurrenceCrit.isEmpty()){
		   clinicalDataQuery.setOccurrenceCrit(occurrenceCrit);
		  }
		
		// Set RadiationTherapy criteria
		RadiationTherapyCriteria radiationTherapyCrit = clinicalDataForm.getRadiationTherapyCriteria();
		if(!radiationTherapyCrit.isEmpty()){
		   clinicalDataQuery.setRadiationTherapyCrit(radiationTherapyCrit);
		   }
		
		//Set ChemoAgent Criteria
		ChemoAgentCriteria chemoAgentCrit = clinicalDataForm.getChemoAgentCriteria();
		if(!chemoAgentCrit.isEmpty()){
		   clinicalDataQuery.setChemoAgentCrit(chemoAgentCrit);
		   }
		
		// Set SurgeryType Criteria
		SurgeryTypeCriteria surgeryTypeCrit = clinicalDataForm.getSurgeryTypeCriteria();
		if(!surgeryTypeCrit.isEmpty()){
		   clinicalDataQuery.setSurgeryTypeCrit(surgeryTypeCrit);
		   }
		
		// Set Survival Criteria
		SurvivalCriteria survivalCrit = clinicalDataForm.getSurvivalCriteria();
		if(!survivalCrit.isEmpty()){
		   clinicalDataQuery.setSurvivalCrit(survivalCrit);
		   }
		
		// Set Age Criteria
		AgeCriteria ageCrit = clinicalDataForm.getAgeCriteria();
		if(!ageCrit.isEmpty()){
		   clinicalDataQuery.setAgeCrit(ageCrit);
		   }
		
		// Set gender Criteria		
		GenderCriteria genderCrit = clinicalDataForm.getGenderCriteria();
		if(!genderCrit.isEmpty()){
		   clinicalDataQuery.setGenderCrit(genderCrit);		
		   }
		
		
		try{
		
			//Set query in Session.
			if (! clinicalDataQuery.isEmpty()) {
			  		 
				// Get QueryCollection from session if available
				QueryCollection queryCollection = (QueryCollection)request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
				if(queryCollection == null){
				    System.out.println("Query Map in Session is empty");
					queryCollection = new QueryCollection();
				  }
				queryCollection.putQuery(clinicalDataQuery);
				request.getSession().setAttribute(NautilusConstants.QUERY_KEY, queryCollection);  
				
				
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
		 
        if (clinicalDataForm.getMethod().equals("run report")) {
            CompoundQuery compoundQuery = new CompoundQuery(clinicalDataQuery);
            compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
            QueryCollection collection = new QueryCollection();
            collection.setCompoundQuery(compoundQuery);
            request.setAttribute(NautilusConstants.QUERY_KEY, collection);
            return mapping.findForward("previewReport");
        } else {
            return mapping.findForward("advanceSearchMenu");
        }
   
	
	}

}
