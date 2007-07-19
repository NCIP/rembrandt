/**
 * 
 */
package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

/**
 * @author sahnih
 *
 */
public class GeneExprDiseaseGroupHandler {
	private Map<String,ClinicalQueryDTO> clinicalQueryDTOMap = new HashMap<String,ClinicalQueryDTO>();
	private Collection<InstitutionDE> insitutions;
	private List<ClassComparisonQueryDTO> classComparisonQueryDTOList = new ArrayList<ClassComparisonQueryDTO>();
	private void createDiseaseGroups(){
	       List<String> allGliomaSamplesList = new ArrayList<String>();
	        /**
	         * this section loops through all REMBRANDTs disease groups found
	         * in the getDiseaseType below. Based on credentials, queries are
	         * run to return sample according to each disease and made into 
	         * default user lists.
	         */
			try {
				DiseaseTypeLookup[] myDiseaseTypes =LookupManager.getDiseaseType();
				if(myDiseaseTypes != null){
					for (DiseaseTypeLookup diseaseTypeLookup : myDiseaseTypes){
						//1. Get the sample Ids from the each disease type
						//Collection<InstitutionDE> insitutions = InsitutionAccessHelper.getInsititutionCollection(session);
						List<String> specimanNames = LookupManager.getSpecimanNames(diseaseTypeLookup.getDiseaseDesc(),insitutions);
						//2. validate samples so that GE data exsists for these samples
				        Collection<String> validSpecimanNames = DataValidator.validateSpecimanNames(specimanNames);
				        if(validSpecimanNames != null){				        	
				               /**
				                * add valid samples to allSamplesList to be created last.
				                * Do not add unknown , unclassified and non_tumor samples. 
				                */
				               if(!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNKNOWN)==0)
				                       && !(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNCLASSIFIED)==0)
				                       && !(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.NON_TUMOR)==0)){
				                	   allGliomaSamplesList.addAll(validSpecimanNames);
					               }
				               /**
				                * Combine all unknown , unclassified samples. 
				                */
				               if((diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNKNOWN)==0)
				                       && (diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNCLASSIFIED)==0)){
			            	   		ClinicalDataQuery group = null;
			            	   		SampleCriteria sampleCriteria = null;
			            	   		if(!clinicalQueryDTOMap.containsKey(RembrandtConstants.UNCLASSIFIED)){
			            	   			group = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
			            	   			group.setQueryName(RembrandtConstants.UNCLASSIFIED);
			            	   			sampleCriteria = new SampleCriteria();					
										sampleCriteria.setSampleIDs(validSpecimanNames);
			            	   		}
			            	   		else{
			            	   			group = (ClinicalDataQuery) clinicalQueryDTOMap.get(RembrandtConstants.UNCLASSIFIED);
			            	   			sampleCriteria = group.getSampleIDCrit();
			            	   			sampleCriteria.setSampleIDs(validSpecimanNames);
			            	   		}
									group.setSampleIDCrit(sampleCriteria);
									group.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));	
									clinicalQueryDTOMap.put(group.getQueryName(),group);
					            } else{
									ClinicalDataQuery group = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
									group.setQueryName(diseaseTypeLookup.getDiseaseDesc());
									SampleCriteria sampleCriteria = new SampleCriteria();					
									sampleCriteria.setSampleIDs(validSpecimanNames);
									group.setSampleIDCrit(sampleCriteria);
									group.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));	
									clinicalQueryDTOMap.put(group.getQueryName(),group);
				               }
				               
				        }

					}
				}
			} catch (OperationNotSupportedException e1) {
				//logger.error(e1);
			} catch (Exception e1) {
				//logger.error(e1);
			}
	        
	         //now add the all samples userlist
	        if(!allGliomaSamplesList.isEmpty()){
        
				ClinicalDataQuery group = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
				group.setQueryName(RembrandtConstants.ALL_GLIOMA);
				SampleCriteria sampleCriteria = new SampleCriteria();					
				sampleCriteria.setSampleIDs(allGliomaSamplesList);
				group.setSampleIDCrit(sampleCriteria);
				group.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));	
				clinicalQueryDTOMap.put(group.getQueryName(),group);

	        }
	}
	private void createClassComparisonQueryDTOs(){
		for(String key : clinicalQueryDTOMap.keySet()){
			ClassComparisonQueryDTO myClassComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
			myClassComparisonQueryDTO.setQueryName(key);
			myClassComparisonQueryDTO.setStatisticTypeDE(new StatisticTypeDE(StatisticalMethodType.TTest));
			//myClassComparisonQueryDTO.setStatisticalSignificanceDE(new StatisticalSignificanceDE(0.5,Operator.GT,StatisticalSignificanceType.adjustedpValue));
			myClassComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.FWER ));
			myClassComparisonQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
			//myClassComparisonQueryDTO.setExprFoldChangeDE(new ExprFoldChangeDE.UpRegulation(new Float(2)));
			myClassComparisonQueryDTO.setInstitutionDEs(insitutions);
			List<ClinicalQueryDTO> groupCollection= new ArrayList<ClinicalQueryDTO>();
			groupCollection.add(clinicalQueryDTOMap.get(key)); //set group
			groupCollection.add(clinicalQueryDTOMap.get(RembrandtConstants.NON_TUMOR));//set baseline
			myClassComparisonQueryDTO.setComparisonGroups(groupCollection);
			classComparisonQueryDTOList.add(myClassComparisonQueryDTO);
		}
	}
}
