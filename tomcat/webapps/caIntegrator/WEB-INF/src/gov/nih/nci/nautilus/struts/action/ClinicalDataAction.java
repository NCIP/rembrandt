// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.ClinicalDataForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.view.*;



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
		ClinicalDataForm clinicalDataForm = (ClinicalDataForm) form;
		
		String thisView = clinicalDataForm.getResultView();
		// Create Query Objects
		ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		clinicalDataQuery.setQueryName(clinicalDataForm.getQueryName());
		
		// Change this code later to get view type directly from Form !!
		if (thisView.equalsIgnoreCase("sample")) {
			clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
			}
		else if (thisView.equalsIgnoreCase("gene")){
			clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_VIEW_TYPE)); 
			}
 
 
		// Set disease criteria	
	    DiseaseOrGradeCriteria diseaseOrGradeCrit = clinicalDataForm.getDiseaseOrGradeCriteria();
		clinicalDataQuery.setDiseaseOrGradeCrit(diseaseOrGradeCrit);
				
 
		// Set Occurrence criteria
		OccurrenceCriteria occurrenceCrit = clinicalDataForm.getOccurrenceCriteria();
		clinicalDataQuery.setOccurrenceCrit(occurrenceCrit);
		
		// Set RadiationTherapy criteria
		RadiationTherapyCriteria radiationTherapyCrit = clinicalDataForm.getRadiationTherapyCriteria();
		clinicalDataQuery.setRadiationTherapyCrit(radiationTherapyCrit);
		
		//Set ChemoAgent Criteria
		ChemoAgentCriteria chemoAgentCrit = clinicalDataForm.getChemoAgentCriteria();
		clinicalDataQuery.setChemoAgentCrit(chemoAgentCrit);
		
		// Set SurgeryType Criteria
		SurgeryTypeCriteria surgeryTypeCrit = clinicalDataForm.getSurgeryTypeCriteria();
		clinicalDataQuery.setSurgeryTypeCrit(surgeryTypeCrit);
		
		// Set Survival Criteria
		SurvivalCriteria survivalCrit = clinicalDataForm.getSurvivalCriteria();
		clinicalDataQuery.setSurvivalCrit(survivalCrit);
		
		// Set Age Criteria
		AgeCriteria ageCrit = clinicalDataForm.getAgeCriteria();
		clinicalDataQuery.setAgeCrit(ageCrit);
		
		// Set gender Criteria		
		GenderCriteria genderCrit = clinicalDataForm.getGenderCriteria();
		clinicalDataQuery.setGenderCrit(genderCrit);
		
		try {

			QueryManager.executeQuery(clinicalDataQuery);

		} catch(Throwable t ) {
			t.printStackTrace();
			System.out.println("Error Clinical Data Query" + t.getMessage());
		}



		return mapping.findForward("advanceSearchMenu");
	}

}
