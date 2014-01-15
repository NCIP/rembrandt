/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ChemoAgentCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.KarnofskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.LanskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.MRIClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.NeuroExamClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OccurrenceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OnStudyChemoAgentCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OnStudyRadiationTherapyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OnStudySurgeryOutcomeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OnStudySurgeryTitleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PriorSurgeryTitleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RaceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RadiationTherapyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurgeryOutcomeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.de.AgeAtDiagnosisDE;
import gov.nih.nci.caintegrator.dto.de.ChemoAgentDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.KarnofskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.LanskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.MRIClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.NeuroExamClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.OccurrenceDE;
import gov.nih.nci.caintegrator.dto.de.OnStudyChemoAgentDE;
import gov.nih.nci.caintegrator.dto.de.OnStudyRadiationTherapyDE;
import gov.nih.nci.caintegrator.dto.de.OnStudySurgeryOutcomeDE;
import gov.nih.nci.caintegrator.dto.de.OnStudySurgeryTitleDE;
import gov.nih.nci.caintegrator.dto.de.PriorSurgeryTitleDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;
import gov.nih.nci.caintegrator.dto.de.RadiationTherapyDE;
import gov.nih.nci.caintegrator.dto.de.SurgeryOutcomeDE;
import gov.nih.nci.caintegrator.dto.de.SurvivalDE;
import gov.nih.nci.rembrandt.dbbean.NeuroEvaluation;
import gov.nih.nci.rembrandt.dbbean.OnStudyChemotherapy;
import gov.nih.nci.rembrandt.dbbean.OnStudyRadiationtherapy;
import gov.nih.nci.rembrandt.dbbean.OnStudySurgery;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dbbean.PriorChemotherapy;
import gov.nih.nci.rembrandt.dbbean.PriorRadiationtherapy;
import gov.nih.nci.rembrandt.dbbean.PriorSurgery;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.util.RaceType;
import gov.nih.nci.rembrandt.util.MoreStringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.apache.struts.action.ActionError;
//import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class ClinicalDataForm extends BaseForm implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ClinicalDataForm.class);

    /** queryName property */
    private String queryName;

    private String resultView = "";
    
    /** clinical_evaluation  lansky property */
    private String lansky;

    /** clinical_evaluation lansky type property */
    private String lanskyType;
    
    /** clinical_evaluation  neuroExam property */
    private String neuroExam;

    /** clinical_evaluation neuroExam type property */
    private String neuroExamType;
    
    /** clinical_evaluation  mri property */
    private String mri;

    /** clinical_evaluation mri type property */
    private String mriType;
    
    /** clinical_evaluation  karnofsky property */
    private String karnofsky;

    /** clinical_evaluation karnofsky type property */
    private String karnofskyType;

    
    /** caucasion property */
    private String caucasion;
    
    /** africanAmerican property */
    private String africanAmerican;
    
    /** nativeHawaiian property */
    private String nativeHawaiian;
    
    /** asian property */
    private String asian;
    
    /** other property */
    private String other;  
    
    /** other property */
    private String unknown;  
    
 
    /** tumorGrade property */
    private String tumorGrade;

    /** occurence first presentation property */
    private String firstPresentation;

    /** occurence recurrence property */
    private String recurrence;

    /** occurence recurrence type property */
    private String recurrenceType;

    /** radiation group property */
    private String radiation;

    /** radiation type property */
    private String radiationType;

    /** chemo agent group property */
    private String chemo;

    /** chemo agent type property */
    private String chemoType;

    /** surgery group property */
    private String surgery;

    /** surgery outcome property */
    private String surgeryOutcome;
    
    private String surgeryTitle;
    
    
    /** radiation group property */
    private String onStudyRadiation;

    /** radiation type property */
    private String onStudyRadiationType;

    /** chemo agent group property */
    private String onStudyChemo;

    /** chemo agent type property */
    private String onStudyChemoType;

    /** surgery group property */
    private String onStudySurgery;

    /** onStudy surgery outcome property */
    private String onStudySurgeryOutcome;
    
    /** onStudy surgery title property */
    
    private String onStudySurgeryTitle;



    /** survival lower property */
    private String survivalLower;

    /** survival upper property */
    private String survivalUpper;

    /** age lower property */
    private String ageLower;

    /** age upper property */
    private String ageUpper;

    /** gender property */
    private String genderType;
    
    /** age upper and lower limit property */
    private AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit = null;
    private AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit = null;
    
    /** survival upper and lower limit property */
    private SurvivalDE.UpperSurvivalRange upperSurvivalRange = null;
    private SurvivalDE.LowerSurvivalRange lowerSurvivalRange = null;    
   
	
	private String recur;
	

    /**
     * private Collections used for Lookup values.
     */ 
   
    private ArrayList recurrenceTypeColl = new ArrayList();

    private ArrayList radiationTypeColl = new ArrayList();

    private ArrayList chemoAgentTypeColl = new ArrayList();

    private ArrayList surgeryOutcomeColl = new ArrayList();
    
    private ArrayList surgeryTitleColl = new ArrayList();
    
    private ArrayList onStudyRadiationTypeColl = new ArrayList();

    private ArrayList onStudyChemoAgentTypeColl = new ArrayList();

    private ArrayList onStudySurgeryOutcomeColl = new ArrayList();
    
    private ArrayList onStudySurgeryTitleColl = new ArrayList();

    private ArrayList survivalLowerColl = new ArrayList();

    private ArrayList survivalUpperColl = new ArrayList();

    private ArrayList ageLowerColl = new ArrayList();

    private ArrayList ageUpperColl = new ArrayList();

    private ArrayList genderTypeColl = new ArrayList();
    
    private ArrayList karnofskyTypeColl = new ArrayList();
    
    private ArrayList lanskyTypeColl = new ArrayList();
    
    private ArrayList neuroExamTypeColl = new ArrayList();
    
    private ArrayList mriTypeColl = new ArrayList();
    
    private List<String> resultViewColl = new ArrayList<String>();
   
    /**
     * private search criteria objects used to contain data element objects
     */
    private OccurrenceCriteria occurrenceCriteria = new OccurrenceCriteria();

    private RadiationTherapyCriteria radiationTherapyCriteria;

    private ChemoAgentCriteria chemoAgentCriteria;

    private SurgeryOutcomeCriteria surgeryOutcomeCriteria;
    
    private PriorSurgeryTitleCriteria priorSurgeryTitleCriteria;
    
    private OnStudyRadiationTherapyCriteria onStudyRadiationTherapyCriteria;

    private OnStudyChemoAgentCriteria onStudyChemoAgentCriteria;

    private OnStudySurgeryOutcomeCriteria onStudySurgeryOutcomeCriteria;
    
    private OnStudySurgeryTitleCriteria onStudySurgeryTitleCriteria;

    private SurvivalCriteria survivalCriteria = new SurvivalCriteria();

    private AgeCriteria ageCriteria = new AgeCriteria();

    private GenderCriteria genderCriteria;
    
    private  RaceCriteria raceCriteria = new RaceCriteria();
    
    private KarnofskyClinicalEvalCriteria karnofskyCriteria;
    
    private  LanskyClinicalEvalCriteria lanskyCriteria ;
    
    private  MRIClinicalEvalCriteria mriCriteria ;
    
    
    private  NeuroExamClinicalEvalCriteria neuroExamCriteria ;    
   

  
  

    

    // --------------------------------------------------------- Methods
    public ClinicalDataForm() {
        super();
        // Create Lookups for Clinical Data screens
        setClinicalDataLookup();
        
    }
   
  
   /**
    * private method to look up the prior therapy radiationTypes from the database
    *
    */
    private void setPriorRadiationTypes() {
		try {
						
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(PriorRadiationtherapy.class,PriorRadiationtherapy.RADIATION_TYPE);
			
			radiationTypeColl.add(new LabelValueBean("",""));
			radiationTypeColl.add(new LabelValueBean("ANY","Any"));  
			
			for(int i = 0; i<list.size();i++) {				
				radiationTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			radiationTypeColl = null;
			logger.error("Unable to get prior radiation types names  from the LookupManager");
			logger.error(e);
		}
	}

    
    /**
     * private method to look up the prior therapy chemo agent names from the database
     *
     */
    
    private void setPriorChemoNames() {
		try {
						
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(PriorChemotherapy.class,PriorChemotherapy.AGENT_NAME);
			
		    chemoAgentTypeColl.add(new LabelValueBean("",""));
		    chemoAgentTypeColl.add(new LabelValueBean("ANY","Any"));  
			
			for(int i = 0; i<list.size();i++) {				
				chemoAgentTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			chemoAgentTypeColl = null;
			logger.error("Unable to get prior agent names  from the LookupManager");
			logger.error(e);
		}
	}

    /**
     * private method to look up the prior therapy surgery titles from the database
     *
     */
    private void  setPriorSurgeryTitles() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(PriorSurgery.class,PriorSurgery.PROCEDURE_TITLE);
			
		    surgeryTitleColl.add(new LabelValueBean("", ""));
	        surgeryTitleColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				surgeryTitleColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			surgeryTitleColl = null;
			logger.error("Unable to get prior surgery titles  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    /**
     * private method to look up the prior therapy surgery outcomes from the database
     *
     */
    private void  setPriorSurgeryOutcomes() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(PriorSurgery.class, PriorSurgery.SURGERY_OUTCOME);
			
		    surgeryOutcomeColl.add(new LabelValueBean("", ""));
		    surgeryOutcomeColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				surgeryOutcomeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			surgeryOutcomeColl = null;
			logger.error("Unable to get prior surgery outcomes  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    /**
     * private method to look up the onStudy therapy radiationTypes from the database
     *
     */
    private void  setOnStudyRadiationTypes() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(OnStudyRadiationtherapy.class, OnStudyRadiationtherapy.RADIATION_TYPE);
			
		    onStudyRadiationTypeColl.add(new LabelValueBean("", ""));
	        onStudyRadiationTypeColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				onStudyRadiationTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			onStudyRadiationTypeColl = null;
			logger.error("Unable to get onstudy radiation types from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    /**
     * private method to look up the onStudy therapy chemo agent names from the database
     *
     */
    private void  setOnStudyChemoNames() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(OnStudyChemotherapy.class, OnStudyChemotherapy.AGENT_NAME);
			
		    onStudyChemoAgentTypeColl.add(new LabelValueBean("", ""));
		    onStudyChemoAgentTypeColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				onStudyChemoAgentTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			onStudyChemoAgentTypeColl = null;
			logger.error("Unable to get onstudy chemo names  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    /**
     * private method to look up the onStudy therapy surgery titles from the database
     *
     */
    private void  setOnStudySurgeryTitles() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(OnStudySurgery.class, OnStudySurgery.PROCEDURE_TITLE);
			
		    onStudySurgeryTitleColl.add(new LabelValueBean("", ""));
		    onStudySurgeryTitleColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				onStudySurgeryTitleColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			onStudySurgeryTitleColl = null;
			logger.error("Unable to get prior surgery titles  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    /**
     * private method to look up the onStudy therapy surgery outcomes from the database
     *
     */
    private void  setOnStudySurgeryOutcomes() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(OnStudySurgery.class, OnStudySurgery.SURGERY_OUTCOME);
			
		    onStudySurgeryOutcomeColl.add(new LabelValueBean("", ""));
		    onStudySurgeryOutcomeColl.add(new LabelValueBean("ANY", "any"));
			
			for(int i = 0; i<list.size();i++) {				
				onStudySurgeryOutcomeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			onStudySurgeryOutcomeColl = null;
			logger.error("Unable to get prior surgery outcomes  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    
    /**
     * private method to look up the KarnofskyScores from the database
     *
     */
    private void  setKarnofskyScores() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(NeuroEvaluation.class, NeuroEvaluation.KARNOFSKY_SCORE);
			
		    karnofskyTypeColl.add(new LabelValueBean("", "")); 
			
			for(int i = 0; i<list.size();i++) {				
				karnofskyTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			karnofskyTypeColl = null;
			logger.error("Unable to get karnofsky scores  from the LookupManager");
			logger.error(e);
		}
    	
    }
    
    
    
    /**
     * private method to look up the LanskyScores from the database
     *
     */
     private void  setLanskyScores() {
    	try {
			
			List list = new ArrayList();
		    list =	LookupManager.lookUpClinicalQueryTermValues(NeuroEvaluation.class, NeuroEvaluation.LANSKY_SCORE);
			
		    lanskyTypeColl.add(new LabelValueBean("", "")); 
			
			for(int i = 0; i<list.size();i++) {				
				lanskyTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
			
			}
		}catch(Exception e) {
			lanskyTypeColl = null;
			logger.error("Unable to get lansky scores  from the LookupManager");
			logger.error(e);
		}
    	
    }
     
     /**
      * private method to look up the NeuroExams from the database
      *
      */
     
     private void  setNeuroExams() {
     	try {
 			
 			List list = new ArrayList();
 		    list =	LookupManager.lookUpClinicalQueryTermValues(NeuroEvaluation.class, NeuroEvaluation.NEURO_EXAM);
 			
 		   neuroExamTypeColl.add(new LabelValueBean("", "")); 
 			
 			for(int i = 0; i<list.size();i++) {				
 				neuroExamTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
 			
 			}
 		}catch(Exception e) {
 			neuroExamTypeColl = null;
 			logger.error("Unable to get prior neuroExams   from the LookupManager");
 			logger.error(e);
 		}
     	
     }
     
     /**
      * private method to look up the MRI types from the database
      *
      */
     
      private void  setMRITypes() {
     	try {
 			
 			List list = new ArrayList();
 		    list =	LookupManager.lookUpClinicalQueryTermValues(NeuroEvaluation.class, NeuroEvaluation.MRI_CT_SCORE);
 			
 		   mriTypeColl.add(new LabelValueBean("", "")); 
 			
 			for(int i = 0; i<list.size();i++) {				
 				mriTypeColl.add(new LabelValueBean((String)list.get(i),(String)list.get(i)));  
 			
 			}
 		}catch(Exception e) {
 			mriTypeColl = null;
 			logger.error("Unable to get MRI   from the LookupManager");
 			logger.error(e);
 		}
     	
     }
     
      /**
       * private method to look up the gender types from the database
       *
       */
      private void setGenderTypes() {
    	  
    	  
    	  try {
   			
   			List list = new ArrayList();
   		    list =	LookupManager.lookUpClinicalQueryTermValues(PatientData.class, PatientData.GENDER);
   			
   		    genderTypeColl.add(new LabelValueBean("", "")); 
   			
   			for(int i = 0; i<list.size();i++) {		
   				String genderCode = (String)list.get(i);   				
   				String genderDesc = null;
   				if(genderCode.equalsIgnoreCase("M")) {
   					genderDesc = "Male";
   				 }
   				else if(genderCode.equalsIgnoreCase("F")) {
   					genderDesc = "Female";
  				 }
   				else if(genderCode.equalsIgnoreCase("O")) {
   					genderDesc = "Other";
  				 }
   				genderTypeColl.add(new LabelValueBean(genderDesc,genderCode));  
   			
   			}
   		}catch(Exception e) {
   			mriTypeColl = null;
   			logger.error("Unable to get gender  from the LookupManager");
   			logger.error(e);
   		}
      }
      
    /**
     * Used to load the look up values for the clinical query page
     *
     */
    public void setClinicalDataLookup() {

        recurrenceTypeColl = new ArrayList();
        radiationTypeColl = new ArrayList();
        chemoAgentTypeColl = new ArrayList();
        surgeryOutcomeColl = new ArrayList();
        surgeryTitleColl = new ArrayList();
        onStudyRadiationTypeColl = new ArrayList();
        onStudyChemoAgentTypeColl = new ArrayList();
        onStudySurgeryOutcomeColl = new ArrayList();
        onStudySurgeryTitleColl = new ArrayList();
        survivalLowerColl = new ArrayList();
        survivalUpperColl = new ArrayList();
        ageLowerColl = new ArrayList();
        ageUpperColl = new ArrayList();
        genderTypeColl = new ArrayList();

        recurrenceTypeColl.add(new LabelValueBean("", ""));
        recurrenceTypeColl.add(new LabelValueBean("Any", "any"));
        recurrenceTypeColl.add(new LabelValueBean("1", "1"));
        recurrenceTypeColl.add(new LabelValueBean("2", "2"));
        recurrenceTypeColl.add(new LabelValueBean("3", "3"));
        
        setPriorRadiationTypes();
        setPriorChemoNames();
        setPriorSurgeryTitles();
        setPriorSurgeryOutcomes();
        
        setOnStudyRadiationTypes();
        setOnStudyChemoNames();
        setOnStudySurgeryTitles();
        setOnStudySurgeryOutcomes();     
        setKarnofskyScores();   
        setLanskyScores(); 
        setNeuroExams(); 
        setMRITypes(); 
        setGenderTypes();


      

        survivalLowerColl.add(new LabelValueBean("", ""));
        survivalLowerColl.add(new LabelValueBean("0", "0"));
        survivalLowerColl.add(new LabelValueBean("10", "10"));
        survivalLowerColl.add(new LabelValueBean("20", "20"));
        survivalLowerColl.add(new LabelValueBean("30", "30"));
        survivalLowerColl.add(new LabelValueBean("40", "40"));
        survivalLowerColl.add(new LabelValueBean("50", "50"));
        survivalLowerColl.add(new LabelValueBean("60", "60"));
        survivalLowerColl.add(new LabelValueBean("70", "70"));
        survivalLowerColl.add(new LabelValueBean("80", "80"));
        survivalLowerColl.add(new LabelValueBean("90", "90"));

        survivalUpperColl.add(new LabelValueBean("", ""));
        survivalUpperColl.add(new LabelValueBean("0", "0"));
        survivalUpperColl.add(new LabelValueBean("10", "10"));
        survivalUpperColl.add(new LabelValueBean("20", "20"));
        survivalUpperColl.add(new LabelValueBean("30", "30"));
        survivalUpperColl.add(new LabelValueBean("40", "40"));
        survivalUpperColl.add(new LabelValueBean("50", "50"));
        survivalUpperColl.add(new LabelValueBean("60", "60"));
        survivalUpperColl.add(new LabelValueBean("70", "70"));
        survivalUpperColl.add(new LabelValueBean("80", "80"));
        survivalUpperColl.add(new LabelValueBean("90", "90"));
        //	survivalUpperColl.add( new LabelValueBean( "90+", "90+" ) );

        ageLowerColl.add(new LabelValueBean("", ""));
        ageLowerColl.add(new LabelValueBean("0", "0"));
        ageLowerColl.add(new LabelValueBean("10", "10"));
        ageLowerColl.add(new LabelValueBean("20", "20"));
        ageLowerColl.add(new LabelValueBean("30", "30"));
        ageLowerColl.add(new LabelValueBean("40", "40"));
        ageLowerColl.add(new LabelValueBean("50", "50"));
        ageLowerColl.add(new LabelValueBean("60", "60"));
        ageLowerColl.add(new LabelValueBean("70", "70"));
        ageLowerColl.add(new LabelValueBean("80", "80"));
        ageLowerColl.add(new LabelValueBean("90", "90"));

        ageUpperColl.add(new LabelValueBean("", ""));
        ageUpperColl.add(new LabelValueBean("0", "0"));
        ageUpperColl.add(new LabelValueBean("10", "10"));
        ageUpperColl.add(new LabelValueBean("20", "20"));
        ageUpperColl.add(new LabelValueBean("30", "30"));
        ageUpperColl.add(new LabelValueBean("40", "40"));
        ageUpperColl.add(new LabelValueBean("50", "50"));
        ageUpperColl.add(new LabelValueBean("60", "60"));
        ageUpperColl.add(new LabelValueBean("70", "70"));
        ageUpperColl.add(new LabelValueBean("80", "80"));
        ageUpperColl.add(new LabelValueBean("90", "90"));
        //	ageUpperColl.add( new LabelValueBean( "90+", "90+" ) );
        
        this.resultViewColl.add("Sample Centric");
        this.resultViewColl.add("Gene Centric");

    }

    /**
     * Method reset. Reset all properties to their default values.
     * 
     * @param ActionMapping
     *            mapping used to select this instance.
     * @param HttpServletRequest
     *            request The servlet request we are processing.
     */

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        queryName = "";
        resultView = "";    
        lansky= "";     
        lanskyType ="";   
        neuroExam = "";      
        neuroExamType = "";        
        mri = "";       
        mriType ="";        
        karnofsky ="";    
        karnofskyType = "";

        caucasion= "";       
        africanAmerican= "";      
        other= "";      
        asian= "";       
        nativeHawaiian= "";
        unknown= "";
        //tumorType = "";
        tumorGrade = "";
        firstPresentation = "";
        recurrence = "";
        recurrenceType = "";
        radiation = "";
        radiationType = "";
        chemo = "";
        chemoType = "";
        surgery = "";
        surgeryOutcome = "";
        surgeryTitle = "";
        onStudyRadiation = "";
        onStudyRadiationType = "";
        onStudyChemo = "";
        onStudyChemoType = "";
        onStudySurgery = "";
        onStudySurgeryOutcome = "";
        onStudySurgeryTitle = "";
        survivalLower = "";
        survivalUpper = "";
        ageLower = "";
        ageUpper = "";
        genderType = "";
        sampleGroup = "";
		sampleList = "";
		

        diseaseOrGradeCriteria = new DiseaseOrGradeCriteria();
        occurrenceCriteria = new OccurrenceCriteria();
        radiationTherapyCriteria = new RadiationTherapyCriteria();
        chemoAgentCriteria = new ChemoAgentCriteria();
        surgeryOutcomeCriteria = new SurgeryOutcomeCriteria();
        priorSurgeryTitleCriteria = new PriorSurgeryTitleCriteria();
        onStudyRadiationTherapyCriteria = new OnStudyRadiationTherapyCriteria();
        onStudyChemoAgentCriteria = new OnStudyChemoAgentCriteria();
        onStudySurgeryOutcomeCriteria = new OnStudySurgeryOutcomeCriteria();
        onStudySurgeryTitleCriteria = new OnStudySurgeryTitleCriteria();
        survivalCriteria = new SurvivalCriteria();
        ageCriteria = new AgeCriteria();
        genderCriteria = new GenderCriteria();
        sampleCriteria = new SampleCriteria();  
        raceCriteria = new RaceCriteria();
       

       
    }
 
    
    

   
	/**
	 * @return Returns the karnofsky.
	 */
	public String getKarnofsky() {
		return karnofsky;
	}





	/**
	 * @param karnofsky The karnofsky to set.
	 */
	public void setKarnofsky(String karnofsky) {
		this.karnofsky = karnofsky;
	}





	/**
	 * @return Returns the karnofskyType.
	 */
	public String getKarnofskyType() {
		return karnofskyType;
	}





	/**
	 * @param karnofskyType The karnofskyType to set.
	 */
	public void setKarnofskyType(String karnofskyType) {
		this.karnofskyType = karnofskyType;
	}
	
	public void setKarnofskyTypeDetails() {
		
//		   if (thisRequest != null) {
//	            // this is to check if thisKarnofsky option is selected
		String thisKarnofsky = this.karnofsky; // thisRequest.getParameter("karnofsky");
		karnofskyCriteria = new KarnofskyClinicalEvalCriteria();

		// this is to check the type of Karnofsky
		String thisKarnofskyType = this.karnofskyType; // thisRequest.getParameter("karnofskyType");

		if (thisKarnofsky != null && thisKarnofskyType != null
				&& !thisKarnofskyType.equals("")) {
			KarnofskyClinicalEvalDE karnofskyClinicalEvalDE = new KarnofskyClinicalEvalDE(this.karnofskyType);
			karnofskyCriteria.setKarnofskyClinicalEvalDE(karnofskyClinicalEvalDE);	              
		}
//	        }
	}





	/**
	 * @return Returns the lansky.
	 */
	public String getLansky() {
		return lansky;
	}





	/**
	 * @param lansky The lansky to set.
	 */
	public void setLansky(String lansky) {
		this.lansky = lansky;
	}





	/**
	 * @return Returns the lanskyType.
	 */
	public String getLanskyType() {
		return lanskyType;
	}





	/**
	 * @param lanskyType The lanskyType to set.
	 */
	public void setLanskyType(String lanskyType) {
		this.lanskyType = lanskyType;
	}
	
	public void setLanskyTypeDetails() {
		
//		if (thisRequest != null) {
//            // this is to check if thisKarnofsky option is selected
		String thisLansky = this.lansky; // thisRequest.getParameter("lansky");
		lanskyCriteria = new LanskyClinicalEvalCriteria();

		// this is to check the type of lanskyType
		String thisLanskyType = this.lanskyType; //thisRequest.getParameter("lanskyType");

		if (thisLansky != null && thisLanskyType != null
				&& !thisLanskyType.equals("")) {
			LanskyClinicalEvalDE lanskyClinicalEvalDE = new LanskyClinicalEvalDE(this.lanskyType);
			lanskyCriteria.setLanskyClinicalEvalDE(lanskyClinicalEvalDE);	              
		}
//        }
	}





	/**
	 * @return Returns the mri.
	 */
	public String getMri() {
		return mri;
	}





	/**
	 * @param mri The mri to set.
	 */
	public void setMri(String mri) {
		this.mri = mri;
	}





	/**
	 * @return Returns the mriType.
	 */
	public String getMriType() {
		return mriType;
	}





	/**
	 * @param mriType The mriType to set.
	 */
	public void setMriType(String mriType) {
		this.mriType = mriType;
	}
	
	public void setMriTypeDetails() {
		
//		if (thisRequest != null) {
//            // this is to check if thisKarnofsky option is selected
		String thisMRI= this.mri;  //thisRequest.getParameter("mri");
		mriCriteria = new MRIClinicalEvalCriteria();

		// this is to check the type of lanskyType
		String thisMRIType = this.mriType; //thisRequest.getParameter("mriType");

		if (thisMRI != null && thisMRIType != null
				&& !thisMRIType.equals("")) {
			MRIClinicalEvalDE mriClinicalEvalDE = new MRIClinicalEvalDE(this.mriType);
			mriCriteria.setMRIClinicalEvalDE(mriClinicalEvalDE);	              
		}
//        }
	}





	/**
	 * @return Returns the neuroExam.
	 */
	public String getNeuroExam() {
		return neuroExam;
	}





	/**
	 * @param neuroExam The neuroExam to set.
	 */
	public void setNeuroExam(String neuroExam) {
		this.neuroExam = neuroExam;
	}





	/**
	 * @return Returns the neuroExamType.
	 */
	public String getNeuroExamType() {
		return neuroExamType;
	}


	/**
	 * @param neuroExamType The neuroExamType to set.
	 */
	public void setNeuroExamType(String neuroExamType) {
		this.neuroExamType = neuroExamType;
	}
	
	public void setNeuroExamTypeDetails() {
		
//		if (thisRequest != null) {
//            // this is to check if thisKarnofsky option is selected
		String thisNeuroExam= this.neuroExam; //thisRequest.getParameter("neuroExam");
		neuroExamCriteria = new NeuroExamClinicalEvalCriteria();

		// this is to check the type of lanskyType
		String thisNeuroExamType =this.neuroExamType; // thisRequest.getParameter("neuroExamType");

		if (thisNeuroExam != null && thisNeuroExamType != null
				&& !thisNeuroExamType.equals("")) {
			NeuroExamClinicalEvalDE neuroExamClinicalEvalDE = new NeuroExamClinicalEvalDE(this.neuroExamType);
			neuroExamCriteria.setNeuroExamClinicalEvalDE(neuroExamClinicalEvalDE);	              
		}
//        }
	}





	/**
	 * @return Returns the africanAmerican.
	 */
	public String getAfricanAmerican() {
		return africanAmerican;
	}





	/**
	 * @param africanAmerican The africanAmerican to set.
	 */
	public void setAfricanAmerican(String africanAmerican) {
		this.africanAmerican = africanAmerican;
	
		if (africanAmerican != null) {
			if (africanAmerican.equalsIgnoreCase("Specify")) {	               
				this.africanAmerican = RaceType.BLACK_OR_AFRICAN_AMERICAN.toString(); //"BLACK";
				RaceDE raceDE = new RaceDE(this.africanAmerican );
				raceCriteria.setRace(raceDE);
				this.africanAmerican ="Specify";
			}


		}
	}


	    


	/**
	 * @return Returns the caucasion.
	 */
	public String getCaucasion() {
		return caucasion;
	}

	/**
	 * @param caucasion The caucasion to set.
	 */
	public void setCaucasion(String caucasion) {
		this.caucasion = caucasion;	
	
		if (caucasion != null) {
			if (caucasion.equalsIgnoreCase("Specify")) {
				//this.caucasion = "Caucasion";	     
				this.caucasion = RaceType.WHITE.toString(); //"WHITE";	 
				RaceDE raceDE = new RaceDE(this.caucasion );
				raceCriteria.setRace(raceDE);
				this.caucasion ="Specify";

			}
	           
	        }
	}

	/**
	 * @return Returns the asian.
	 */
	public String getAsian() {
		return asian;
	}





	/**
	 * @param asian The asian to set.
	 */
	public void setAsian(String asian) {
		this.asian = asian;
		 if (asian != null) {
	            if (asian.equalsIgnoreCase("Specify")) {
	                //this.caucasion = "Caucasion";	     
	            	this.asian = RaceType.ASIAN.toString(); //"ASIAN NOS";	 
	            	RaceDE raceDE = new RaceDE(this.asian );
	 	            raceCriteria.setRace(raceDE);
	 	           this.asian ="Specify";
	            }
	           
	          
	        }
	}





	/**
	 * @return Returns the nativeHawaiian.
	 */
	public String getNativeHawaiian() {
		return nativeHawaiian;
	}





	/**
	 * @param nativeHawaiian The nativeHawaiian to set.
	 */
	public void setNativeHawaiian(String nativeHawaiian) {
		this.nativeHawaiian = nativeHawaiian;
		
		 if (nativeHawaiian != null) {
	            if (nativeHawaiian.equalsIgnoreCase("Specify")) {
	                //this.caucasion = "Caucasion";	     
	            	this.nativeHawaiian = RaceType.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER.toString(); //"NATIVE HAWAIIAN";	
	            	RaceDE raceDE = new RaceDE(this.nativeHawaiian );
		            raceCriteria.setRace(raceDE);
		            this.nativeHawaiian="Specify";		          
	            }
	            
	        }
	}





	/**
	 * @return Returns the other.
	 */
	public String getOther() {
		return other;
	}





	/**
	 * @param other The other to set.
	 */
	public void setOther(String other) {
		this.other = other;
		
		 if (other != null) {
	            if (other.equalsIgnoreCase("Specify")) {	                   
	            	this.other = RaceType.OTHER.toString();	
	            	RaceDE raceDE = new RaceDE(this.other );
	 	            raceCriteria.setRace(raceDE);
	 	            this.other="Specify";
	            }
	          
	          
	        }
	}





	/**
	 * @return Returns the unknown.
	 */
	public String getUnknown() {
		return unknown;
	}





	/**
	 * @param unknown The unknown to set.
	 */
	public void setUnknown(String unknown) {
		this.unknown = unknown;
		 if (unknown != null) {
	            if (unknown.equalsIgnoreCase("Specify")) {	                   
	            	this.unknown = RaceType.UNKNOWN.toString();	
	            	RaceDE raceDE = new RaceDE(this.unknown );
		            raceCriteria.setRace(raceDE);
		            this.unknown = "Specify";
		          
	            }
	            
	        }
		 
	}






	  
  /**
     * Returns the tumorGrade.
     * 
     * @return String
     */
    public String getTumorGrade() {
        return tumorGrade;
    }

    /**
     * Set the tumorGrade.
     * 
     * @param tumorType
     *            The tumorGrade to set
     */
    public void setTumorGrade(String tumorGrade) {
    	this.tumorGrade = tumorGrade;    	
     
     }

  
  

    /**
     * Set the firstPresentation.
     * 
     * @param firstPresentation
     *            The firstPresentation to set
     */
    public void setFirstPresentation(String firstPresentation) {
        if (firstPresentation != null) {
            if (firstPresentation.equalsIgnoreCase("on")) {
                this.firstPresentation = "first presentation";
                
            }
            OccurrenceDE occurrenceDE = new OccurrenceDE(this.firstPresentation );
            occurrenceCriteria.setOccurrence(occurrenceDE);
          
        }
    }

    /**
     * Returns the firstPresentation.
     * 
     * @return String
     */
    public String getFirstPresentation() {
        return firstPresentation;
    }

    /**
     * Set the recurrence.
     * 
     * @param recurrence
     *            The recurrence to set
     */
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }
    
    public void setRecurrenceDetails() {
    //    if (thisRequest != null) {
    	String recurrenceStr = this.recur; // (String) thisRequest.getParameter("recur");
    	String recurrenceSpecify =this.recurrence;
    	//(String) thisRequest.getParameter("recurrence");
    	if (recurrenceStr != null && !recurrenceStr.equals("")&& recurrenceSpecify != null && !recurrenceSpecify.equals("")) {            	 
    		OccurrenceDE occurrenceDE = new OccurrenceDE(this.recurrence);
    		occurrenceCriteria.setOccurrence(occurrenceDE);

    	}
 //      }
    }

    /**
     * Returns the recurrence.
     * 
     * @return String
     */
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Set the recurrenceType.
     * 
     * @param recurrenceType
     *            The recurrenceType to set
     */
    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
        if(this.recurrenceType !=null && !this.recurrenceType.equals("")){
           OccurrenceDE occurrenceDE = new OccurrenceDE(this.recurrenceType);
           occurrenceCriteria.setOccurrence(occurrenceDE);
        }
       
    }

    /**
     * Returns the recurrenceType.
     * 
     * @return String
     */
    public String getRecurrenceType() {
        return recurrenceType;
    }

    /**
     * Set the radiation.
     * 
     * @param radiation
     *            The radiation to set
     */
    public void setRadiation(String radiation) {
        this.radiation = radiation;

    }

    /**
     * Returns the radiation.
     * 
     * @return String
     */
    public String getRadiation() {
        return radiation;
    }

    /**
     * Set the radiationType.
     * 
     * @param radiationType
     *            The radiationType to set
     */
    public void setRadiationType(String radiationType) {
        this.radiationType = radiationType;
    }
    
    public void setRadiationTypeDetails() {
//        if (thisRequest != null) {
//            // this is to check if radiation option is selected
    	String thisRadiation = this.radiation; // thisRequest.getParameter("radiation");
    	radiationTherapyCriteria = new RadiationTherapyCriteria();

    	// this is to check the type of radiation
    	String thisRadiationType = this.radiationType; // thisRequest.getParameter("radiationType");

    	if (thisRadiation != null && thisRadiationType != null
    			&& !thisRadiationType.equals("")) {

    		if(thisRadiationType.equalsIgnoreCase("ANY")) {

    			// This is to deal with adding a collection of all the prior therapy radiation types if any is selected            	

    			ArrayList allRadiationTypes = this.getRadiationTypeColl();
    			for (Iterator radiationIter = allRadiationTypes.iterator(); radiationIter.hasNext();) {
    				LabelValueBean thisLabelBean = (LabelValueBean) radiationIter.next();
    				String thisRadiationSiteType = thisLabelBean.getValue();    
    				if (!thisRadiationSiteType.equalsIgnoreCase("ANY")) {
    					RadiationTherapyDE radiationDE = new RadiationTherapyDE(thisRadiationSiteType);
    					radiationTherapyCriteria.setRadiationTherapyDE(radiationDE);
    				}

    			}
    		}
    		else {
    			// This is to deal with adding a single prior therapy radiation type    
    			RadiationTherapyDE radiationTherapyDE = new RadiationTherapyDE(this.radiationType);
    			radiationTherapyCriteria.setRadiationTherapyDE(radiationTherapyDE);
    		}

    	}
//        }
    }

    /**
     * Returns the radiationType.
     * 
     * @return String
     */
    public String getRadiationType() {
        return radiationType;
    }
    
    public void setRadiationTherapyCriteria(
			RadiationTherapyCriteria radiationTherapyCriteria) {
		this.radiationTherapyCriteria = radiationTherapyCriteria;
		
		if ( radiationTherapyCriteria != null ) {
			if ( radiationTherapyCriteria.getRadiations() != null && radiationTherapyCriteria.getRadiations().size() > 0) {
				this.radiation = "Specify";
				
				if ( radiationTherapyCriteria.getRadiations().size() == 1 )
					this.radiationType =  ( (RadiationTherapyDE)( (ArrayList)radiationTherapyCriteria.getRadiations() ).get(0) ).getValueObject();
				else
					this.radiationType = "Any";
			}
		}
	}


	public void setChemoAgentCriteria(ChemoAgentCriteria chemoAgentCriteria) {
		this.chemoAgentCriteria = chemoAgentCriteria;
		
		if( chemoAgentCriteria != null ) {
			if ( chemoAgentCriteria.getAgents() != null && chemoAgentCriteria.getAgents().size() > 0) {
				this.chemo = "Specify";
				
				if ( chemoAgentCriteria.getAgents().size() == 1 )
					this.chemoType =  ( (ChemoAgentDE)( (ArrayList)chemoAgentCriteria.getAgents() ).get(0) ).getValueObject();
				else
					this.chemoType = "Any";
			}
		}
		
	}
	
	public void setPriorSurgeryTitleCriteria(
			PriorSurgeryTitleCriteria priorSurgeryTitleCriteria) {
		this.priorSurgeryTitleCriteria = priorSurgeryTitleCriteria;
		
		if ( priorSurgeryTitleCriteria != null ) {
			if ( priorSurgeryTitleCriteria.getTitles() != null && priorSurgeryTitleCriteria.getTitles().size() > 0) {
				this.surgery = "Specify";
				
				if ( priorSurgeryTitleCriteria.getTitles().size() == 1 )
					this.surgeryTitle =  ( (PriorSurgeryTitleDE)( (ArrayList)priorSurgeryTitleCriteria.getTitles() ).get(0) ).getValueObject();
				else
					this.surgeryTitle = "Any";
			}
		}
		
	}


	/**
     * Set the chemo.
     * 
     * @param chemo
     *            The chemo to set
     */
    public void setChemo(String chemo) {
        this.chemo = chemo;

    }

    /**
     * Returns the chemo.
     * 
     * @return String
     */
    public String getChemo() {
        return chemo;
    }

    /**
     * Set the chemoType.
     * 
     * @param chemoType
     *            The chemoType to set
     */
    public void setChemoType(String chemoType) {
        this.chemoType = chemoType;
    }
    
    public void setChemoTypeDetails() {
//        if (thisRequest != null) {
//            // this is to check if the chemo option is selected
    	String thisChemo = this.chemo; // thisRequest.getParameter("chemo");
    	chemoAgentCriteria = new ChemoAgentCriteria();
    	// this is to check the chemo type
    	String thisChemoType = this.chemoType; // thisRequest.getParameter("chemoType");
    	if (thisChemo != null && thisChemoType != null
    			&& !thisChemoType.equals("")) { 
    		if(thisChemoType.equalsIgnoreCase("ANY")) {

    			// This is to deal with adding a collection of all the prior therapy chemo types if any is selected            	

    			ArrayList allAgents = this.getChemoAgentTypeColl();
    			for (Iterator agentIter = allAgents.iterator(); agentIter.hasNext();) {
    				LabelValueBean thisLabelBean = (LabelValueBean) agentIter.next();
    				String thisAgentType = thisLabelBean.getValue();    
    				if (!thisAgentType.equalsIgnoreCase("ANY")) {
    					ChemoAgentDE chemoAgentDE = new ChemoAgentDE(thisAgentType);
    					chemoAgentCriteria.setChemoAgentDE(chemoAgentDE);
    				}

    			}
    		}	 
    		else {
    			// This is to deal with adding a single prior therapy chemo type    
    			ChemoAgentDE chemoAgentDE = new ChemoAgentDE(thisChemoType);
    			chemoAgentCriteria.setChemoAgentDE(chemoAgentDE);
    		}

    	}
//              
//            }
        }
   
    

    /**
     * Returns the chemoType.
     * 
     * @return String
     */
    public String getChemoType() {
        return chemoType;
    }

    /**
     * Set the surgery.
     * 
     * @param surgery
     *            The surgery to set
     */
    public void setSurgery(String surgery) {
        this.surgery = surgery;

    }

    /**
     * Returns the surgery.
     * 
     * @return String
     */
    public String getSurgery() {
        return surgery;
    }

    /**
     * Set the surgeryOutcome.
     * 
     * @param surgeryOutcome
     *            The surgeryOutcome to set
     */
    public void setSurgeryOutcome(String surgeryOutcome) {
        this.surgeryOutcome = surgeryOutcome;
    }
    
    public void setSurgeryOutcomeDetails() {
//        if (thisRequest != null) {
    	String thisSurgery = this.surgery; // thisRequest.getParameter("surgery");
    	String thisSurgeryOutcome = this.surgeryOutcome; // thisRequest.getParameter("surgeryOutcome");
    	surgeryOutcomeCriteria = new SurgeryOutcomeCriteria();
    	if (thisSurgery != null && thisSurgeryOutcome != null
    			&& !thisSurgeryOutcome.equals("")) {

    		if(thisSurgeryOutcome.equalsIgnoreCase("ANY")) {
    			// This is to deal with adding a collection of all the prior therapy surgery outcomes if any is selected            	

    			ArrayList allSurgeryOutcomes = this.getSurgeryOutcomeColl();
    			for (Iterator outcomeIter = allSurgeryOutcomes.iterator(); outcomeIter.hasNext();) {
    				LabelValueBean thisLabelBean = (LabelValueBean) outcomeIter.next();
    				String thisOutcomeType = thisLabelBean.getValue();    
    				if (!thisOutcomeType.equalsIgnoreCase("ANY")) {
    					SurgeryOutcomeDE surgeryOutcomeDE = new SurgeryOutcomeDE(thisOutcomeType);
    					surgeryOutcomeCriteria.setSurgeryOutcomeDE(surgeryOutcomeDE);
    				}

    			}
    		}
    		else {
    			// This is to deal with adding a single prior therapy surgery outcome       	

    			SurgeryOutcomeDE surgeryOutcomeDE = new SurgeryOutcomeDE(this.surgeryOutcome);
    			surgeryOutcomeCriteria.setSurgeryOutcomeDE(surgeryOutcomeDE); 
    		}
    	}
//        }
    }

    /**
     * Returns the surgeryOutcome.
     * 
     * @return String
     */
    public String getSurgeryOutcome() {
        return surgeryOutcome;
    }

    
    public void setSurgeryOutcomeCriteria(
			SurgeryOutcomeCriteria surgeryOutcomeCriteria) {
		this.surgeryOutcomeCriteria = surgeryOutcomeCriteria;
		
		if ( surgeryOutcomeCriteria != null && surgeryOutcomeCriteria.getOutcomes() != null ) {
			if ( surgeryOutcomeCriteria.getOutcomes().size() == 1 )
				this.surgeryOutcome =  ( (SurgeryOutcomeDE)( (ArrayList)surgeryOutcomeCriteria.getOutcomes() ).get(0) ).getValueObject();
			else
				this.surgeryOutcome = "any";
		}
		
	}


	/**
	 * @return Returns the onStudyChemo.
	 */
	public String getOnStudyChemo() {
		return onStudyChemo;
	}





	/**
	 * @param onStudyChemo The onStudyChemo to set.
	 */
	public void setOnStudyChemo(String onStudyChemo) {
		this.onStudyChemo = onStudyChemo;
	}





	/**
	 * @return Returns the onStudyChemoType.
	 */
	public String getOnStudyChemoType() {
		return onStudyChemoType;
	}





	/**
	 * @param onStudyChemoType The onStudyChemoType to set.
	 */
	public void setOnStudyChemoType(String onStudyChemoType) {
		this.onStudyChemoType = onStudyChemoType;
	}
	
	
	public void setOnStudyChemoTypeDetails() {	
//		if (thisRequest != null) {
//            // this is to check if the chemo option is selected
		String thisOnStudyChemo = this.onStudyChemo; // thisRequest.getParameter("onStudyChemo");
		onStudyChemoAgentCriteria = new OnStudyChemoAgentCriteria();
		// this is to check the chemo type
		String thisOnStudyChemoType = this.onStudyChemoType; // thisRequest.getParameter("onStudyChemoType");
		if (thisOnStudyChemo != null && thisOnStudyChemoType != null
				&& !thisOnStudyChemoType.equals("")) { 
			// This is to deal with adding a collection of all the onStudy chemo types if any is selected            	
			if(thisOnStudyChemoType.equalsIgnoreCase("ANY")) {
				ArrayList allAgents = this.getOnStudyChemoAgentTypeColl();
				for (Iterator agentIter = allAgents.iterator(); agentIter.hasNext();) {
					LabelValueBean thisLabelBean = (LabelValueBean) agentIter.next();
					String thisAgentType = thisLabelBean.getValue();    
					if (!thisAgentType.equalsIgnoreCase("ANY")) {
						OnStudyChemoAgentDE chemoAgentDE = new OnStudyChemoAgentDE(thisAgentType);
						onStudyChemoAgentCriteria.setOnStudyChemoAgentDE(chemoAgentDE);
					}

				}
			}	 
			else {
				// This is to deal with adding a single onStudy chemo type
				OnStudyChemoAgentDE chemoAgentDE = new OnStudyChemoAgentDE(thisOnStudyChemoType);
				onStudyChemoAgentCriteria.setOnStudyChemoAgentDE(chemoAgentDE);
			}

		}
//              
//            }
        }
   
	





	/**
	 * @return Returns the onStudyRadiation.
	 */
	public String getOnStudyRadiation() {
		return onStudyRadiation;
	}





	/**
	 * @param onStudyRadiation The onStudyRadiation to set.
	 */
	public void setOnStudyRadiation(String onStudyRadiation) {
		this.onStudyRadiation = onStudyRadiation;
	}





	/**
	 * @return Returns the onStudyRadiationType.
	 */
	public String getOnStudyRadiationType() {
		return onStudyRadiationType;
	}





	/**
	 * @param onStudyRadiationType The onStudyRadiationType to set.
	 */
	public void setOnStudyRadiationType(String onStudyRadiationType) {
		this.onStudyRadiationType = onStudyRadiationType;
	}
	
	
	public void setOnStudyRadiationTypeDetails() {	
//		if (thisRequest != null) {
//            // this is to check if the chemo option is selected
		String thisOnStudyRadiation = this.onStudyRadiation; // thisRequest.getParameter("onStudyRadiation");
		onStudyRadiationTherapyCriteria = new OnStudyRadiationTherapyCriteria();
		// this is to check the chemo type
		String thisOnStudyRadiationType = this.onStudyRadiationType; // thisRequest.getParameter("onStudyRadiationType");
		if (thisOnStudyRadiation != null && thisOnStudyRadiationType != null
				&& !thisOnStudyRadiationType.equals("")) { 
			// This is to deal with adding a collection of all the radiation types if any is selected
			if(thisOnStudyRadiationType.equalsIgnoreCase("ANY")) {
				ArrayList allRadiationTypes = this.getOnStudyRadiationTypeColl();
				for (Iterator radiationTypeIter = allRadiationTypes.iterator(); radiationTypeIter.hasNext();) {
					LabelValueBean thisLabelBean = (LabelValueBean) radiationTypeIter.next();
					String thisRadiationType = thisLabelBean.getValue();    
					if (!thisRadiationType.equalsIgnoreCase("ANY")) {
						OnStudyRadiationTherapyDE onStudyRadiationTherapyDE = new OnStudyRadiationTherapyDE(thisRadiationType);
						onStudyRadiationTherapyCriteria.setOnStudyRadiationTherapyDE(onStudyRadiationTherapyDE);
					}

				}
			}	 
			else {// this is to deal with adding a single entry of radiation type
				OnStudyRadiationTherapyDE onStudyRadiationTherapyDE = new OnStudyRadiationTherapyDE(thisOnStudyRadiationType);
				onStudyRadiationTherapyCriteria.setOnStudyRadiationTherapyDE(onStudyRadiationTherapyDE);
			}

		}
//              
//            }
	}





	/**
	 * @return Returns the onStudySurgery.
	 */
	public String getOnStudySurgery() {
		return onStudySurgery;
	}





	/**
	 * @param onStudySurgery The onStudySurgery to set.
	 */
	public void setOnStudySurgery(String onStudySurgery) {
		this.onStudySurgery = onStudySurgery;
	}





	/**
	 * @return Returns the onStudySurgeryOutcome.
	 */
	public String getOnStudySurgeryOutcome() {
		return onStudySurgeryOutcome;
	}





	/**
	 * @param onStudySurgeryOutcome The onStudySurgeryOutcome to set.
	 */
	public void setOnStudySurgeryOutcome(String onStudySurgeryOutcome) {
		this.onStudySurgeryOutcome = onStudySurgeryOutcome;
	}
	
	public void setOnStudySurgeryOutcomeDetails() {	
//		if (thisRequest != null) {
//            // this is to check if the chemo option is selected
		String thisOnStudySurgery = this.onStudySurgery; // thisRequest.getParameter("onStudySurgery");
		onStudySurgeryOutcomeCriteria = new OnStudySurgeryOutcomeCriteria();
		// this is to check the chemo type
		String thisOnStudySurgeryOutcome = this.onStudySurgeryOutcome; // thisRequest.getParameter("onStudySurgeryOutcome");
		if (thisOnStudySurgery != null && thisOnStudySurgeryOutcome != null
				&& !thisOnStudySurgeryOutcome.equals("")) { 
			if(thisOnStudySurgeryOutcome.equalsIgnoreCase("ANY")) {

				// This is to deal with adding a collection of all the onstudy surgery outcomes if any is selected

				ArrayList allSurgeryOutcomes = this.getOnStudySurgeryOutcomeColl();
				for (Iterator surgeryOutcomeIter = allSurgeryOutcomes.iterator(); surgeryOutcomeIter.hasNext();) {
					LabelValueBean thisLabelBean = (LabelValueBean) surgeryOutcomeIter.next();
					String thisSurgeryOutcome = thisLabelBean.getValue();    
					if (!thisSurgeryOutcome.equalsIgnoreCase("ANY")) {
						OnStudySurgeryOutcomeDE onStudySurgeryOutcomeDE = new OnStudySurgeryOutcomeDE(thisSurgeryOutcome);
						onStudySurgeryOutcomeCriteria.setOnStudySurgeryOutcomeDE(onStudySurgeryOutcomeDE);
					}

				}
			}	 
			else {
				// this is to deal with adding a single entry of onstudy surgery outcome
				OnStudySurgeryOutcomeDE onStudySurgeryOutcomeDE = new OnStudySurgeryOutcomeDE(thisOnStudySurgeryOutcome);
				onStudySurgeryOutcomeCriteria.setOnStudySurgeryOutcomeDE(onStudySurgeryOutcomeDE);
			}

		}
//              
//            }
	}





	/**
	 * @return Returns the onStudySurgeryTitle.
	 */
	public String getOnStudySurgeryTitle() {
		return onStudySurgeryTitle;
	}





	/**
	 * @param onStudySurgeryTitle The onStudySurgeryTitle to set.
	 */
	public void setOnStudySurgeryTitle(String onStudySurgeryTitle) {
		this.onStudySurgeryTitle = onStudySurgeryTitle;
	}
		
	public void setOnStudySurgeryTitleDetails() {
//		if (thisRequest != null) {
//            // this is to check if the chemo option is selected
		String thisOnStudySurgery = this.onStudySurgery; // thisRequest.getParameter("onStudySurgery");
		onStudySurgeryTitleCriteria = new OnStudySurgeryTitleCriteria();
		// this is to check the chemo type
		String thisOnStudySurgeryTitle = this.onStudySurgeryTitle; // thisRequest.getParameter("onStudySurgeryTitle");
		if (thisOnStudySurgery != null && thisOnStudySurgeryTitle != null
				&& !thisOnStudySurgeryTitle.equals("")) { 
			// This is to deal with adding a collection of all the onstudy surgery titles if any is selected
			if(thisOnStudySurgeryTitle.equalsIgnoreCase("ANY")) {
				ArrayList allSurgeryTitles = this.getOnStudySurgeryTitleColl();
				for (Iterator surgeryTitleIter = allSurgeryTitles.iterator(); surgeryTitleIter.hasNext();) {
					LabelValueBean thisLabelBean = (LabelValueBean) surgeryTitleIter.next();
					String thisSurgeryTitle = thisLabelBean.getValue();    
					if (!thisSurgeryTitle.equalsIgnoreCase("ANY")) {
						OnStudySurgeryTitleDE onStudySurgeryTitleDE = new OnStudySurgeryTitleDE(thisSurgeryTitle);
						onStudySurgeryTitleCriteria.setOnStudySurgeryTitleDE(onStudySurgeryTitleDE);
					}

				}
			}	 
			else {
				// this is to deal with adding a single entry of onstudy surgery title
				OnStudySurgeryTitleDE onStudySurgeryTitleDE = new OnStudySurgeryTitleDE(thisOnStudySurgeryTitle);
				onStudySurgeryTitleCriteria.setOnStudySurgeryTitleDE(onStudySurgeryTitleDE);
			}

		}
//		}
	}





	/**
     * Set the survivalLower.
     * 
     * @param survivalLower
     *            The survivalLower to set
     */
    public void setSurvivalLower(String survivalLower) {
        this.survivalLower = survivalLower;      
        if(this.survivalLower != null && this.survivalLower.trim().length()>=1){
        	try{
		        lowerSurvivalRange = new SurvivalDE.LowerSurvivalRange(Integer.valueOf(this.survivalLower));
		        survivalCriteria.setLowerSurvivalRange(lowerSurvivalRange);
	        	}
        	catch(NumberFormatException e){}
	     } 
        
    }

    /**
     * Returns the survivalLower.
     * 
     * @return String
     */
    public String getSurvivalLower() {
        return survivalLower;
    }

    /**
     * Set the survivalUpper.
     * 
     * @param survivalUpper
     *            The survivalUpper to set
     */
    public void setSurvivalUpper(String survivalUpper) {
        this.survivalUpper = survivalUpper;       
       
        if(this.survivalUpper != null && this.survivalUpper.trim().length()>=1){
        	try{
	        upperSurvivalRange = new SurvivalDE.UpperSurvivalRange(Integer.valueOf(this.survivalUpper));
	        survivalCriteria.setUpperSurvivalRange(upperSurvivalRange);
           }
        	catch(NumberFormatException e){}
        }
      
    }

    /**
     * Returns the survivalUpper.
     * 
     * @return String
     */
    public String getSurvivalUpper() {
        return survivalUpper;
    }

    /**
     * Set the ageLower.
     * 
     * @param ageLower
     *            The ageLower to set
     */
   
    public void setAgeLower(String ageLower) {
        this.ageLower = ageLower;
        
       if(this.ageLower  != null && this.ageLower.trim().length()>=1){
        	try{        	
	          lowerAgeLimit = new AgeAtDiagnosisDE.LowerAgeLimit(Integer.valueOf(this.ageLower));	       
	          ageCriteria.setLowerAgeLimit(lowerAgeLimit);
        	}      
        
           catch(NumberFormatException e){}       
       
        }
  
    }
  
    /**
     * Returns the ageLower.
     * 
     * @return String
     */
    public String getAgeLower() {
        return ageLower;
    }

    /**
     * Set the ageUpper.
     * 
     * @param ageUpper
     *            The ageUpper to set
     */
    public void setAgeUpper(String ageUpper) {
        this.ageUpper = ageUpper;
        
      if(this.ageUpper != null && this.ageUpper.trim().length()>=1){
        	try{        	
	         upperAgeLimit = new AgeAtDiagnosisDE.UpperAgeLimit(Integer.valueOf(this.ageUpper));	        	  
	         ageCriteria.setUpperAgeLimit(upperAgeLimit);
        	} 
        	
        catch(NumberFormatException e){}
    }
    
    
    }

    /**
     * Returns the genderType.
     * 
     * @return String
     */
    public String getAgeUpper() {
        return ageUpper;
    }

    /**
     * Set the genderType.
     * 
     * @param genderType
     *            The genderType to set
     */
    public void setGenderType(String genderType) {
        this.genderType = genderType;

        if (this.genderType != null && !this.genderType.trim().equals("")) {        	
           	  genderCriteria = new GenderCriteria();
           	  GenderDE genderDE = new GenderDE(this.genderType );
           	  genderCriteria.setGenderDE(genderDE);
         }

    }

    /**
     * Returns the genderType.
     * 
     * @return String
     */
    public String getGenderType() {
        return genderType;
    }

    /**
     * Set the queryName.
     * 
     * @param queryName
     *            The queryName to set
     */
    public void setQueryName(String queryName) {
		if (queryName != null )
			queryName = MoreStringUtils.cleanJavascript(queryName);

        this.queryName = queryName;
    }

    /**
     * Returns the queryName.
     * 
     * @return String
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * Returns the resultView.
     * 
     * @return String
     */
    public String getResultView() {
        return resultView;
    }

    /**
     * Set the resultView.
     * 
     * @param resultView
     *            The resultView to set
     */
    public void setResultView(String resultView) {
        this.resultView = resultView;
    }    
    

    public OccurrenceCriteria getOccurrenceCriteria() {
        return this.occurrenceCriteria;
    }

    public SurgeryOutcomeCriteria getSurgeryOutcomeCriteria() {
        return this.surgeryOutcomeCriteria;
    }

    public RadiationTherapyCriteria getRadiationTherapyCriteria() {
        return this.radiationTherapyCriteria;
    }

    public ChemoAgentCriteria getChemoAgentCriteria() {
        return this.chemoAgentCriteria;
    }

    
    /**
	 * @return Returns the onStudyChemoAgentCriteria.
	 */
	public OnStudyChemoAgentCriteria getOnStudyChemoAgentCriteria() {
		return onStudyChemoAgentCriteria;
	}

	public void setOnStudyChemoAgentCriteria(OnStudyChemoAgentCriteria chemoAgentCriteria) {
		onStudyChemoAgentCriteria = chemoAgentCriteria;
		
		if ( onStudyChemoAgentCriteria != null ) {
			if ( onStudyChemoAgentCriteria.getAgents() != null && onStudyChemoAgentCriteria.getAgents().size() > 0) {
				this.onStudyChemo = "Specify";
				
				if ( onStudyChemoAgentCriteria.getAgents().size() == 1 )
					this.onStudyChemoType =  ( (OnStudyChemoAgentDE)( (ArrayList)onStudyChemoAgentCriteria.getAgents() ).get(0) ).getValueObject();
				else
					this.onStudyChemoType = "any";
			}
		}
		
	}




	/**
	 * @return Returns the onStudyRadiationTherapyCriteria.
	 */
	public OnStudyRadiationTherapyCriteria getOnStudyRadiationTherapyCriteria() {
		return onStudyRadiationTherapyCriteria;
	}

	public void setOnStudyRadiationTherapyCriteria( OnStudyRadiationTherapyCriteria onStudyRadiationTherapyCriteria) {
		this.onStudyRadiationTherapyCriteria = onStudyRadiationTherapyCriteria;
		
		if ( onStudyRadiationTherapyCriteria != null ) {
			if ( onStudyRadiationTherapyCriteria.getRadiations() != null && onStudyRadiationTherapyCriteria.getRadiations().size() > 0) {
				this.onStudyRadiation = "Specify";
				
				if ( onStudyRadiationTherapyCriteria.getRadiations().size() == 1 )
					this.onStudyRadiationType =  ( (OnStudyRadiationTherapyDE)( (ArrayList)onStudyRadiationTherapyCriteria.getRadiations() ).get(0) ).getValueObject();
				else
					this.onStudyRadiationType = "any";
			}
		}
		
	}


	/**
	 * @return Returns the onStudySurgeryOutcomeCriteria.
	 */
	public OnStudySurgeryOutcomeCriteria getOnStudySurgeryOutcomeCriteria() {
		return onStudySurgeryOutcomeCriteria;
	}

	public void setOnStudySurgeryOutcomeCriteria( OnStudySurgeryOutcomeCriteria onStudySurgeryOutcomeCriteria) {
		this.onStudySurgeryOutcomeCriteria = onStudySurgeryOutcomeCriteria;
		
		if ( onStudySurgeryOutcomeCriteria != null ) {
			if ( onStudySurgeryOutcomeCriteria.getOutcomes() != null && onStudySurgeryOutcomeCriteria.getOutcomes().size() > 0) {
				this.onStudySurgery = "Specify";
				
				if ( onStudySurgeryOutcomeCriteria.getOutcomes().size() == 1 )
					this.onStudySurgeryOutcome =  ( (OnStudySurgeryOutcomeDE)( (ArrayList)onStudySurgeryOutcomeCriteria.getOutcomes() ).get(0) ).getValueObject();
				else
					this.onStudySurgeryOutcome = "any";
			}
		}
		
	}


	/**
	 * @return Returns the onStudySurgeryTitleCriteria.
	 */
	public OnStudySurgeryTitleCriteria getOnStudySurgeryTitleCriteria() {
		return onStudySurgeryTitleCriteria;
	}

	public void setOnStudySurgeryTitleCriteria( OnStudySurgeryTitleCriteria onStudySurgeryTitleCriteria) {
		this.onStudySurgeryTitleCriteria = onStudySurgeryTitleCriteria;
		
		if ( onStudySurgeryTitleCriteria != null ) {
			if ( onStudySurgeryTitleCriteria.getTitles() != null && onStudySurgeryTitleCriteria.getTitles().size() > 0) {
				this.onStudySurgery = "Specify";
				
				if ( onStudySurgeryTitleCriteria.getTitles().size() == 1 )
					this.onStudySurgeryTitle =  ( (OnStudySurgeryTitleDE)( (ArrayList)onStudySurgeryTitleCriteria.getTitles() ).get(0) ).getValueObject();
				else
					this.onStudySurgeryTitle = "any";
			}
		}
	}




	public SurvivalCriteria getSurvivalCriteria() {
        return this.survivalCriteria;
    }

	public void setSurvivalCriteria( SurvivalCriteria survivalCriteria ) {
		this.survivalCriteria = survivalCriteria;
		
		if ( survivalCriteria != null && survivalCriteria.getLowerSurvivalRange() != null ) {
			if ( survivalCriteria.getLowerSurvivalRange().getValueObject() > -1 )
				this.survivalLower = survivalCriteria.getLowerSurvivalRange().getValueObject().toString();
			
			if ( survivalCriteria.getUpperSurvivalRange().getValueObject() > -1 )
				this.survivalUpper = survivalCriteria.getUpperSurvivalRange().getValueObject().toString();
		}
	}
	
	public AgeCriteria getAgeCriteria() {
        return this.ageCriteria;
    }

	public void setAgeCriteria( AgeCriteria ageCriteria) {
        this.ageCriteria = ageCriteria;
        
        if ( ageCriteria != null ) {
        
	        if ( ageCriteria.getLowerAgeLimit() != null && ageCriteria.getLowerAgeLimit().getValueObject() > -1 )
	        	this.ageLower = ageCriteria.getLowerAgeLimit().getValueObject().toString();
	        
	        if ( ageCriteria.getUpperAgeLimit() != null && ageCriteria.getUpperAgeLimit().getValueObject() > -1 )
	        	this.ageUpper = ageCriteria.getUpperAgeLimit().getValueObject().toString();
        }
        
    }

	public GenderCriteria getGenderCriteria() {
        return this.genderCriteria;
    }

	public void setGenderCriteria( GenderCriteria genderCriteria) {
        this.genderCriteria = genderCriteria;
        
        if ( genderCriteria != null && genderCriteria.getGenderDE() != null && !genderCriteria.getGenderDE().getValueObject().equals(""))
        	this.genderType = genderCriteria.getGenderDE().getValueObject();
    }

	public RaceCriteria getRaceCriteria() {
        return this.raceCriteria;
    }
    
	public void setRaceCriteria( RaceCriteria raceCriteria) {
        this.raceCriteria = raceCriteria;
        
        if ( raceCriteria != null && raceCriteria.getRaces() != null ) {
	        Iterator iterator = raceCriteria.getRaces().iterator();
	        RaceDE raceDE = null;
	        while ( iterator.hasNext() ) {
	        	raceDE = (RaceDE)iterator.next();
	        	
	        	if ( raceDE.getValueObject().equals( RaceType.WHITE.toString() ) ) {
	        		this.caucasion = "Specify";
	        	}
	        	else if ( raceDE.getValueObject().equals( RaceType.BLACK_OR_AFRICAN_AMERICAN.toString() ) ) {
	        		this.africanAmerican = "Specify";
	        	}
	        	else if ( raceDE.getValueObject().equals( RaceType.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER.toString() ) ) {
	        		this.nativeHawaiian = "Specify";
	        	}
	        	else if ( raceDE.getValueObject().equals( RaceType.ASIAN.toString() ) ) {
	        		this.asian = "Specify";
	        	}
	        	else if ( raceDE.getValueObject().equals( RaceType.OTHER.toString() ) ) {
	        		this.other = "Specify";
	        	}
	        	else if ( raceDE.getValueObject().equals( RaceType.UNKNOWN.toString() ) ) {
	        		this.unknown = "Specify";
	        	}
	
	        }
        }
        	
    }
    
    
    /**
	 * @return Returns the karnofskyCriteria.
	 */
	public KarnofskyClinicalEvalCriteria getKarnofskyCriteria() {
		return karnofskyCriteria;
	}



	/**
	 * @return Returns the lanskyCriteria.
	 */
	public LanskyClinicalEvalCriteria getLanskyCriteria() {
		return lanskyCriteria;
	}


	/**
	 * @return Returns the mriCriteria.
	 */
	public MRIClinicalEvalCriteria getMriCriteria() {
		return mriCriteria;
	}

	/**
	 * @return Returns the neuroExamCriteria.
	 */
	public NeuroExamClinicalEvalCriteria getNeuroExamCriteria() {
		return neuroExamCriteria;
	}



	public ArrayList getRecurrenceTypeColl() {
        return recurrenceTypeColl;
    }

    public ArrayList getRadiationTypeColl() {
        return radiationTypeColl;
    }

    public ArrayList getChemoAgentTypeColl() {
        return chemoAgentTypeColl;
    }

    public ArrayList getSurgeryOutcomeColl() {
        return surgeryOutcomeColl;
    }

    public ArrayList getSurvivalLowerColl() {
        return survivalLowerColl;
    }

    public ArrayList getSurvivalUpperColl() {
        return survivalUpperColl;
    }

    public ArrayList getAgeLowerColl() {
        return ageLowerColl;
    }

    public ArrayList getAgeUpperColl() {
        return ageUpperColl;
    }

    public ArrayList getGenderTypeColl() {
        return genderTypeColl;
    }


	/**
	 * @return Returns the karnofskyTypeColl.
	 */
	public ArrayList getKarnofskyTypeColl() {
		return karnofskyTypeColl;
	}
/**
	 * @return Returns the lanskyTypeColl.
	 */
	public ArrayList getLanskyTypeColl() {
		return lanskyTypeColl;
	}

/**
	 * @return Returns the mriTypeColl.
	 */
	public ArrayList getMriTypeColl() {
		return mriTypeColl;
	}




	/**
	 * @return Returns the neuroExamTypeColl.
	 */
	public ArrayList getNeuroExamTypeColl() {
		return neuroExamTypeColl;
	}
    
	
    /**
	 * @return Returns the surgeryTitle.
	 */
	public String getSurgeryTitle() {
		return surgeryTitle;
	}





		/**
	 * @param surgeryTitle The surgeryTitle to set.
	 */
	public void setSurgeryTitle(String surgeryTitle) {
		this.surgeryTitle = surgeryTitle;	
	}
	
	public void setSurgeryTitleDetails() {
//	        if (thisRequest != null) {
		String thisSurgery = this.surgery; // thisRequest.getParameter("surgery");
		String thisSurgeryTitle = this.surgeryTitle; // thisRequest.getParameter("surgeryTitle");
		priorSurgeryTitleCriteria = new PriorSurgeryTitleCriteria();
		if (thisSurgery != null && thisSurgeryTitle != null
				&& !thisSurgeryTitle.equals("")) {

			if(thisSurgeryTitle.equalsIgnoreCase("ANY")) {
				// This is to deal with adding a collection of all the prior therapy surgery titles if any is selected
				ArrayList allSurgeryTitles = this.getSurgeryTitleColl();
				for (Iterator titleIter = allSurgeryTitles.iterator(); titleIter.hasNext();) {
					LabelValueBean thisLabelBean = (LabelValueBean) titleIter.next();
					String thisTitleType = thisLabelBean.getValue();    
					if (!thisTitleType.equalsIgnoreCase("ANY")) {
						PriorSurgeryTitleDE priorSurgeryTitleDE = new PriorSurgeryTitleDE(thisTitleType);
						priorSurgeryTitleCriteria.setPriorSurgeryTitleDE(priorSurgeryTitleDE);
					}

				}
			}
			else {

				// This is to deal with adding a single entry of  prior therapy surgery title

				PriorSurgeryTitleDE priorSurgeryTitleDE = new PriorSurgeryTitleDE(this.surgeryTitle);
				priorSurgeryTitleCriteria.setPriorSurgeryTitleDE(priorSurgeryTitleDE); 
			}
		}
//	        }
	    }

	





	/**
	 * @return Returns the surgeryTitleColl.
	 */
	public ArrayList getSurgeryTitleColl() {
		return surgeryTitleColl;
	}





	/**
	 * @return Returns the onStudyChemoAgentTypeColl.
	 */
	public ArrayList getOnStudyChemoAgentTypeColl() {
		return onStudyChemoAgentTypeColl;
	}





	/**
	 * @return Returns the onStudyRadiationTypeColl.
	 */
	public ArrayList getOnStudyRadiationTypeColl() {
		return onStudyRadiationTypeColl;
	}





	/**
	 * @return Returns the onStudySurgeryOutcomeColl.
	 */
	public ArrayList getOnStudySurgeryOutcomeColl() {
		return onStudySurgeryOutcomeColl;
	}





	/**
	 * @return Returns the onStudySurgeryTitleColl.
	 */
	public ArrayList getOnStudySurgeryTitleColl() {
		return onStudySurgeryTitleColl;
	}




  /**
   * Method used to copy individual values when called
   * 
   */
	public ClinicalDataForm cloneMe() {
        ClinicalDataForm form = new ClinicalDataForm();
        form.setQueryName(queryName);
        form.setTumorGrade(tumorGrade);
        form.setTumorType(tumorType);
        form.setResultView(resultView);
        form.setFirstPresentation(firstPresentation);
        form.setRecurrence(recurrence);
        form.setRecurrenceType(recurrenceType);        
        form.setLansky(lansky);       
        form.setLanskyType(lanskyType);        
        form.setNeuroExam(neuroExam);        
        form.setNeuroExamType(neuroExamType);
        form.setMri(mri);        
        form.setMriType(mriType);       
        form.setKarnofsky(karnofsky);       
        form.setKarnofskyType(karnofskyType);
        form.setRadiation(radiation);
        form.setRadiationType(radiationType);
        form.setChemo(chemo);
        form.setChemoType(chemoType);
        form.setSurgery(surgery);
        form.setSurgeryOutcome(surgeryOutcome);
        form.setSurgeryTitle(surgeryTitle);
        form.setOnStudyRadiation(onStudyRadiation);
        form.setOnStudyRadiationType(onStudyRadiationType);
        form.setOnStudyChemo(onStudyChemo);
        form.setOnStudyChemoType(onStudyChemoType);
        form.setOnStudySurgery(onStudySurgery);
        form.setOnStudySurgeryOutcome(onStudySurgeryOutcome);
        form.setOnStudySurgeryTitle(onStudySurgeryTitle);
        form.setSurvivalLower(survivalLower);
        form.setSurvivalUpper(survivalUpper);
        form.setAgeLower(ageLower);
        form.setAgeUpper(ageUpper);
        form.setGenderType(genderType);
        form.setSampleList(sampleList);
        form.setSampleFile(sampleFile);
        form.setSampleGroup(sampleGroup);
        form.setAfricanAmerican(africanAmerican);
        form.setCaucasion(caucasion);
        form.setAsian(asian);
        form.setOther(other);
        form.setUnknown(unknown);
        form.setNativeHawaiian(nativeHawaiian);        
              
        return form;
    }
    /**
     * Method validate
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     * @return ActionErrors
     */
	
	///Shan temp comment out
	/*
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        //ActionErrors errors = new ActionErrors();
        List<String> errors = new ArrayList<String>();

        // Query Name cannot be blank
//      if the method of the button is "submit" or "run report", validate
        if(this.getMethod()!=null && (this.getMethod().equalsIgnoreCase("submit") || this.getMethod().equalsIgnoreCase("preview"))){
            logger.debug("Validating Form");
            if ((queryName == null || queryName.length() < 1))
                //errors.add("queryName", new ActionError(
                  //      "gov.nih.nci.nautilus.ui.struts.form.queryname.no.error"));
            	errors.add("gov.nih.nci.nautilus.ui.struts.form.queryname.no.error");
             if (!MoreStringUtils.isURLSafe(queryName))
                //errors.add("queryName", new ActionError(
                  //      "gov.nih.nci.nautilus.ui.struts.form.queryname.illegal.characters"));
            	 errors.add("gov.nih.nci.nautilus.ui.struts.form.queryname.illegal.characters");
        }
        // survival range validations
     

        return errors;
    }

*/



	/**
	 * @return Returns the priorSurgeryTitleCriteria.
	 */
	public PriorSurgeryTitleCriteria getPriorSurgeryTitleCriteria() {
		return priorSurgeryTitleCriteria;
	}


	public void setKarnofskyCriteria(KarnofskyClinicalEvalCriteria karnofskyCriteria) {
		this.karnofskyCriteria = karnofskyCriteria;
		
		if ( karnofskyCriteria != null && karnofskyCriteria.getKarnofskyClinicalEvalDE() != null) {
			this.karnofsky = "Specify";
			this.karnofskyType = karnofskyCriteria.getKarnofskyClinicalEvalDE().getValueObject();
		}
	}


	public void setLanskyCriteria(LanskyClinicalEvalCriteria lanskyCriteria) {
		this.lanskyCriteria = lanskyCriteria;
		
		if ( lanskyCriteria != null && lanskyCriteria.getLanskyClinicalEvalDE() != null ) {
			this.lansky = "Specify";
			this.lanskyType = lanskyCriteria.getLanskyClinicalEvalDE().getValueObject();
		}
		
	}


	public void setMriCriteria(MRIClinicalEvalCriteria mriCriteria) {
		this.mriCriteria = mriCriteria;
		
		if ( mriCriteria != null && mriCriteria.getMRIClinicalEvalDE() != null ) {
			this.mri = "Specify";
			this.mriType = mriCriteria.getMRIClinicalEvalDE().getValueObject();
		}
		
	}


	public void setNeuroExamCriteria(NeuroExamClinicalEvalCriteria neuroExamCriteria) {
		this.neuroExamCriteria = neuroExamCriteria;
		
		if ( neuroExamCriteria != null && neuroExamCriteria.getNeuroExamClinicalEvalDE() != null ) {
			this.neuroExam = "Specify";
			this.neuroExamType = neuroExamCriteria.getNeuroExamClinicalEvalDE().getValueObject();
		}
		
	}

	
	@Override
	public String getSubmitActionName() {
		// TODO Auto-generated method stub
		return "cdSubmit";
	}

	@Override
	public String getPreviewActionName() {
		// TODO Auto-generated method stub
		return "cdPreview";
	}

	public void setTumorType(String[] tumorType) {
		super.setTumorType(tumorType);
	}

	@Override
	public void setSampleList(String sampleList) {
		// TODO Auto-generated method stub
		super.setSampleList(sampleList);
	}

	@Override
	public void setSampleFile(String sampleFile) {
		// TODO Auto-generated method stub
		super.setSampleFile(sampleFile);
	}

	@Override
	public void setSampleGroup(String sampleGroup) {
		// TODO Auto-generated method stub
		super.setSampleGroup(sampleGroup);
	}
	
	

	@Override
	public void setTumorGrades(List<String> tumorGradeList) {
		// TODO Auto-generated method stub
		super.setTumorGrades(tumorGradeList);
	}
	

	@Override
	public ArrayList<LabelValueBean> getGeneTypeColl() {
		// TODO Auto-generated method stub
		return super.getGeneTypeColl();
	}

	@Override
	public void setGeneTypeColl(ArrayList<LabelValueBean> geneTypeColl) {
		// TODO Auto-generated method stub
		super.setGeneTypeColl(geneTypeColl);
	}


	public String getRecur() {
		return recur;
	}


	public void setRecur(String recur) {
		this.recur = recur;
	}


}
