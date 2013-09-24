/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprDiseaseView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GEFactHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;

/**
 * @author BhattarR
 */


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

public class CommonFactHandler {
	/*
    public static void addDiseaseCriteria(Query query, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
    	addDiseaseSampleCriteria( query,  beanClass,  criteria);
    }
    public static void addSingleDiseaseCriteria(DiseaseNameDE disease, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
        //DiseaseOrGradeCriteria diseaseCrit = query.getDiseaseOrGradeCriteria();
        String columnName = QueryHandler.getColumnName(pb, DiseaseNameDE.class.getName(), beanClass.getName());
        if (disease != null) {
            criteria.addEqualTo(columnName, disease.getValueObject());
        }
    }

                        
    public static void addSampleIDCriteria(Query query, Class beanClass, Criteria criteria)
    throws Exception {
    	addDiseaseSampleCriteria( query,  beanClass,  criteria);
    }
    */
    public static void addDiseaseSampleCriteria(Query query, Class beanClass,
			Criteria criteria) throws Exception {
		Set<String> samples = null;
		PatientDataLookup[] patientDataLookupArray = LookupManager
				.getPatientData();
		DiseaseOrGradeCriteria diseaseCrit = query.getDiseaseOrGradeCriteria();
		if (diseaseCrit != null && diseaseCrit.getDiseases() != null) {
			samples = new HashSet<String>();
			List<String> diseasesTypes = new ArrayList<String>();
			for (Object diseaseType : diseaseCrit.getDiseases()) {
				diseasesTypes.add(((DiseaseNameDE) diseaseType)
						.getValueObject());
			}

			for (String diseaseName : diseasesTypes) {
				for (PatientDataLookup patientDataLookup : patientDataLookupArray) {
					if (patientDataLookup.getDiseaseType() != null
							&& patientDataLookup.getDiseaseType()
									.equalsIgnoreCase(diseaseName)) {
						if (query instanceof ClinicalDataQuery) {
							if (patientDataLookup.getSampleId() != null) {
								samples.add(patientDataLookup.getSampleId());
							}
						} else {
							if (patientDataLookup.getSpecimenName() != null) {
								samples
										.add(patientDataLookup
												.getSpecimenName());
							}
						}
					}

				}
			}
		}
		SampleCriteria sampleIDCrit = query.getSampleIDCrit();
		if (sampleIDCrit != null && sampleIDCrit.getSampleIDs() != null) {
			List<String> sampleIDs = new ArrayList<String>();
			for (Object sampleID : sampleIDCrit.getSampleIDs()) {
				sampleIDs.add(((SampleIDDE) sampleID).getValueObject());
			}
			Set<String> sampleCritList = new HashSet<String>();
			for (String sampleID : sampleIDs) {
				for (PatientDataLookup patientDataLookup : patientDataLookupArray) {
					if (query instanceof ClinicalDataQuery) {
						if (patientDataLookup.getSampleId() != null
								&& patientDataLookup.getSampleId()
										.equalsIgnoreCase(sampleID)) {
							sampleCritList.add(patientDataLookup.getSampleId());
						}
					} else {
						if (patientDataLookup.getSpecimenName() != null
								&& patientDataLookup.getSpecimenName()
										.equalsIgnoreCase(sampleID)) {
							sampleCritList.add(patientDataLookup
									.getSpecimenName());
						}
					}
				}
			}
			// Retains only the elements in this set that are contained
			// in the both the disease criteria and sample criteria.
			// In other words, removes from this set all of its elements that
			// are not contained
			// in the samples AND sampleIDs collection.
			// This operation effectively modifies this set so that its value is
			// the intersection of the two sets.
			if(samples == null){
				samples = new HashSet<String>();
				samples.addAll(sampleCritList);
			}
			else{
				samples.retainAll(sampleCritList);
			}

		}
		if (sampleIDCrit != null && sampleIDCrit.getSpecimenType() != null) {
			Set<String> specimenTypeList = new HashSet<String>();
			for (PatientDataLookup patientDataLookup : patientDataLookupArray) {
				switch (sampleIDCrit.getSpecimenType()) {
				case BLOOD:
					if (patientDataLookup.getSpecimenName() != null
							&& patientDataLookup.getSpecimenName().endsWith(
									"_B")) {
						specimenTypeList.add(patientDataLookup
								.getSpecimenName());
					}
					break;
				case TISSUE_BRAIN:
					if (patientDataLookup.getSpecimenName() != null
							&& !patientDataLookup.getSpecimenName().endsWith(
									"_B")) {// DOES NPT END IN "_B"
						specimenTypeList.add(patientDataLookup
								.getSpecimenName());
					}
					break;
				}
			}
			// Retains only the elements in this set that are contained
			// in the disease, sample & specimanType criteria.
			// In other words, removes from this set all of its elements that
			// are not contained
			// in the samples AND sampleIDs AND specimanType collection.
			// This operation effectively modifies this set so that its value is
			// the intersection of the three sets.
			if(samples == null){
				samples = new HashSet<String>();
				samples.addAll(specimenTypeList);
			}
			else{
				samples.retainAll(specimenTypeList);
			}
		} 
        if(samples != null){
	    	Criteria c = new Criteria();
	        String sampleIDAttr = "SPECIMEN_NAME";
	        if(query instanceof ClinicalDataQuery){
	        	sampleIDAttr = QueryHandler.getAttrNameForTheDE(SampleIDDE.class.getName(), beanClass.getName());
	        }      
	        if(samples.size()== 0){
	        	samples.add("DUMMY_ID"); //to make sure the query is constructed properly
	        }
	        c.addIn(sampleIDAttr, samples);
	        criteria.addAndCriteria(c);
        }
        
    }
    public static void addAccessCriteria(Query query, Class beanClass, Criteria criteria)
    throws Exception {
    	//This is a work around interceptor to provide proper access for GE Disease Queries
    	
        InstitutionCriteria institCrit = interceptorForGEDiseaseGroupQuery(query);
        if (institCrit  != null) {
            ArrayList<Long> instIDs = new ArrayList<Long>();
            ArrayList<String> instNames = new ArrayList<String>();
            Collection<InstitutionDE> institutes = institCrit.getInstitutions();
           
            for (InstitutionDE institution : institutes) {
               instIDs.add(institution.getValueObject());
               // Make sure Institution Name is set
               if(institution.getDisplayName() != null && institution.getDisplayName().length()> 0){
            	   instNames.add(institution.getInstituteName());
               }
            }
            //Add criteria for Intitution ID (access id)
            String instIDAttr = QueryHandler.getAttrNameForTheDE(InstitutionDE.class.getName(), beanClass.getName());
            Criteria c = new Criteria();
            c.addIn(instIDAttr, instIDs);
            criteria.addAndCriteria(c);
          //Add criteria for Insitution Name
            if(instNames.size()>0){
	            c = new Criteria();
	            c.addIn("INSTITUTION_NAME", instNames);
	            criteria.addAndCriteria(c);
            }
           
            
            //
        }
    }
	private static InstitutionCriteria  interceptorForGEDiseaseGroupQuery(Query query) {
		// sahnih
		//This methods allows proper selection of access Codes for GE Group Queries
//		IF AFTER LOGIN ID = 8 ONLY THEN ID = 8 ONLY
//		IF ID = 8 + ANY ONE INSTITUTION_ID THEN THEN ID = THAT ONE INSTITUTION_ID
//		IF ID = 8 + 1 + 5 THEN ID = 5
//		IF ID = 8 + ALL OTHER INSTITUTION_IDS THEN ID = 13
		InstitutionCriteria institCrit = query.getInstitutionCriteria ();  
		InstitutionCriteria newInstitCriteria = new InstitutionCriteria();
		Collection<InstitutionDE> updatesInstitutes = new ArrayList<InstitutionDE>();
		if( 	query instanceof GeneExpressionQuery  &&
				query.getAssociatedView() instanceof GeneExprDiseaseView){
			if (institCrit  != null) {
		            Collection<InstitutionDE> institutes = institCrit.getInstitutions();		            
		            if(institutes!= null ){
		            	//	change to public
		            	if(institutes.size()== 1 ){
		            		for (InstitutionDE institution : institutes) {
			 		               if(!(institution.getValueObject().equals(CaIntegratorConstants.PUBLIC_ACCESS))){
				            			InstitutionDE institutionDE = new InstitutionDE("PUBLIC",CaIntegratorConstants.PUBLIC_ACCESS);
					 		            updatesInstitutes.add(institutionDE);
			 		               		}
			            		}
			 		    }
		            	// remove public
		            	else if(institutes.size() == 2){
		            		for (InstitutionDE institution : institutes) {
		 		               if(!(institution.getValueObject().equals(CaIntegratorConstants.PUBLIC_ACCESS))){
		 		            	  updatesInstitutes.add(institution);
		 		               		}
		            		}
		            	}
		            	// remove public + Henry Ford
		            	else if(institutes.size() == 3){
		            		InstitutionDE institutionDE = new InstitutionDE("NABTT/HENRY FORD",new Long(5));
		 		            updatesInstitutes.add(institutionDE);
		            	}
		            	//change to super
		            	else if(institutes.size() > 3){
		            		InstitutionDE institutionDE = new InstitutionDE("ALL INSTITITIONS",new Long(13));
			 		        updatesInstitutes.add(institutionDE);
			 		    }
		            	
		            }
		        }
		}
		if(updatesInstitutes.size()>0){
			newInstitCriteria.setInstitutions(updatesInstitutes);	
			return newInstitCriteria;    		
    	}
			return institCrit;
	}

}
