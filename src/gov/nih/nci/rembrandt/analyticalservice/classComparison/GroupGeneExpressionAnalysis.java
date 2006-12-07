package gov.nih.nci.rembrandt.analyticalservice.classComparison;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.CompoundClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQueryInterface;
import gov.nih.nci.rembrandt.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations.AnnotationHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.DiseaseGeneExprPlotResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;
import gov.nih.nci.rembrandt.util.MathUtil;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

	public class GroupGeneExpressionAnalysis {
	private static final long QUERY_TIME_LIMIT = 180000; //3 mins
	private static Logger logger = Logger.getLogger(GroupGeneExpressionAnalysis.class);
	private static BusinessTierCache businessTierCache  = ApplicationFactory.getBusinessTierCache();
	private Map<String,ClinicalQueryDTO> clinicalQueryDTOMap = new HashMap<String,ClinicalQueryDTO>();

	public GroupGeneExpressionAnalysis(){
		
	}
	private void createDiseaseGroups(Collection<InstitutionDE> insitutions){
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
					List<SampleIDDE> sampleIDDEs = LookupManager.getSampleIDDEs(diseaseTypeLookup.getDiseaseDesc(),insitutions);
					//2. validate samples so that GE data exsists for these samples
			        Collection<String> sampleIDs = StrategyHelper.extractSamples(sampleIDDEs);
			        //List<String> pdids = new ArrayList<String>(DataValidator.validateSampleIdsForGEData(sampleIDs));
			        if(sampleIDs != null  && sampleIDs.size() > 3){				        	
			               /**
			                * add valid samples to allSamplesList to be created last.
			                * Do not add unknown , unclassified and non_tumor samples. 
			                */
			               if(!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNKNOWN)==0)
			                       && !(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNCLASSIFIED)==0)
			                       && !(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.NON_TUMOR)==0)){
			                	   allGliomaSamplesList.addAll(sampleIDs);
				               }
			               		PatientUserListQueryDTO group = new PatientUserListQueryDTO();
								group.setQueryName(diseaseTypeLookup.getDiseaseDesc());
								group.setPatientDIDs(new ArrayList<String>(sampleIDs));
								clinicalQueryDTOMap.put(group.getQueryName(),group);
			        }

				}
			}
		} catch (OperationNotSupportedException e1) {
			logger.error(e1);
		} catch (Exception e1) {
			logger.error(e1);
		}
        
         //now add the all samples userlist
        if(!allGliomaSamplesList.isEmpty()){			
			PatientUserListQueryDTO group = new PatientUserListQueryDTO();
			group.setQueryName(RembrandtConstants.ALL);
			group.setPatientDIDs(allGliomaSamplesList);
			clinicalQueryDTOMap.put(group.getQueryName(),group);
        }

	}
	/**
	 * This method creates a list of ClassComparisonQueryDTO to be used for CompoundClassComparison
	 * @param arrayPlatformType
	 * @param institutions
	 * @return
	 */
	private List<ClassComparisonQueryDTO> createCompoundClassComparisionQuery(ArrayPlatformType arrayPlatformType,Collection<InstitutionDE> institutions) {
		List<ClassComparisonQueryDTO> classComparisonQueryDTOList = new ArrayList<ClassComparisonQueryDTO>(); 
		for( String queryName: clinicalQueryDTOMap.keySet()){
			if(queryName.compareToIgnoreCase(RembrandtConstants.NON_TUMOR)!= 0){
				ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
				classComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
				classComparisonQueryDTO.setQueryName(queryName);
				classComparisonQueryDTO.setStatisticTypeDE(new StatisticTypeDE(StatisticalMethodType.TTest));
				classComparisonQueryDTO.setStatisticalSignificanceDE(new StatisticalSignificanceDE(1.0,Operator.GT,StatisticalSignificanceType.pValue));
				classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.NONE ));
				classComparisonQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(arrayPlatformType.toString()));
				classComparisonQueryDTO.setExprFoldChangeDE(new ExprFoldChangeDE.UpRegulation(new Float(0)));
				classComparisonQueryDTO.setInstitutionDEs(institutions);
				
				List<ClinicalQueryDTO> groupCollection= new ArrayList<ClinicalQueryDTO>();
				groupCollection.add(clinicalQueryDTOMap.get(queryName));
				groupCollection.add(clinicalQueryDTOMap.get(RembrandtConstants.NON_TUMOR));//baseline
				classComparisonQueryDTO.setComparisonGroups(groupCollection);
				classComparisonQueryDTOList.add(classComparisonQueryDTO);	
			}
		}
		return classComparisonQueryDTOList;
	}
	public ResultsContainer executeGroupGeneExpression(GeneExpressionQueryInterface geneExpresionQuery, String sessionId) throws Throwable{
		Map<String, List<String>> geneReporterMap;
		GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = new GeneExprDiseasePlotContainer();
 		List<String> reporters = new ArrayList<String>();
		if(validateQuery(geneExpresionQuery)){
			createDiseaseGroups(geneExpresionQuery.getInstitutionCriteria().getInstitutions());
	 		geneExprDiseasePlotContainer = handleDiseaseGeneExprPlotResultset(geneExprDiseasePlotContainer);
			geneExprDiseasePlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(extractGeneSymbol(geneExpresionQuery)));
			geneReporterMap = getGeneReporterMapFromGenes(geneExpresionQuery);
			if(geneReporterMap != null){
		        for(Map.Entry<String,List<String>> mapEntry: geneReporterMap.entrySet()){
		        	reporters.addAll(mapEntry.getValue());
		        }
			}
			List<ClassComparisonQueryDTO> ccQueryList = createCompoundClassComparisionQuery(geneExpresionQuery.getArrayPlatformCriteria().getPlatform().getValueObjectAsArrayPlatformType(),geneExpresionQuery.getInstitutionCriteria().getInstitutions());
			Finding finding = executeCompoundClassComparisonQuery(ccQueryList, sessionId,geneExpresionQuery.getQueryName(),reporters);
			//check query type
			ArrayPlatformType arrayPlatformType = geneExpresionQuery.getArrayPlatformCriteria().getPlatform().getValueObjectAsArrayPlatformType();
			if(finding != null  && finding.getStatus()== FindingStatus.Completed){
				if(finding instanceof CompoundClassComparisonFinding){
			        for(Map.Entry<String,List<String>> mapEntry: geneReporterMap.entrySet()){
			        	geneExprDiseasePlotContainer = filterFindings(geneExprDiseasePlotContainer,(CompoundClassComparisonFinding)finding, mapEntry,arrayPlatformType);
			        }
				}
				
			}
			else{
				geneExprDiseasePlotContainer = null;
				throw new IllegalStateException("Error Running Group Class Comparison Analysis");
			}
		}
		
		return geneExprDiseasePlotContainer;
		
	}
	private boolean validateQuery(GeneExpressionQueryInterface geneExpresionQuery) {
		if(geneExpresionQuery != null &&
			geneExpresionQuery.getArrayPlatformCriteria() != null &&
			geneExpresionQuery.getArrayPlatformCriteria().getPlatform() != null   &&
			geneExpresionQuery.getInstitutionCriteria() != null  &&
			geneExpresionQuery.getInstitutionCriteria().getInstitutions() != null  &&
			geneExpresionQuery.getGeneIDCrit() != null &&
			geneExpresionQuery.getGeneIDCrit().getGeneIdentifiers().size()  == 1){
			return true;
		}
		return false;
	}
	private String extractGeneSymbol(GeneExpressionQueryInterface geneExpresionQuery){
		if(geneExpresionQuery.getGeneIDCrit().getGeneIdentifiers().size()== 1){
			Object[] objArray= geneExpresionQuery.getGeneIDCrit().getGeneIdentifiers().toArray();
			GeneIdentifierDE geneIdentifierDE  = (GeneIdentifierDE) objArray[0];
               return geneIdentifierDE.getValueObject().toUpperCase();
		}
		return null;
	}
	private GeneExprDiseasePlotContainer filterFindings(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer, CompoundClassComparisonFinding compoundClassComparisonFinding, Entry<String, List<String>> mapEntry, ArrayPlatformType arrayPlatformType){
		if(compoundClassComparisonFinding != null){
			List<ClassComparisonFinding>  ccList = compoundClassComparisonFinding.getClassComparisonFindingList();
			for(ClassComparisonFinding cc: ccList){
				ReporterFoldChangeValuesResultset reporterResultset = null;
				DiseaseGeneExprPlotResultset diseaseResultset = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(cc.getTaskId());
				List<ClassComparisonResultEntry> ccResultEntryList = cc.getResultEntries();
				for(ClassComparisonResultEntry ccResultEntry: ccResultEntryList){					
					if(mapEntry.getValue().contains(ccResultEntry.getReporterId())){
						DatumDE reporter = null;
						if(arrayPlatformType.equals(ArrayPlatformType.AFFY_OLIGO_PLATFORM)){
							reporter = new DatumDE(DatumDE.PROBESET_ID,ccResultEntry.getReporterId());
						}else if(arrayPlatformType.equals(ArrayPlatformType.UNIFIED_GENE)){
							reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,ccResultEntry.getReporterId());
						}
						reporterResultset = diseaseResultset.getReporterFoldChangeValuesResultset(ccResultEntry.getReporterId());
			      		if(reporterResultset == null){
			      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
			      		}
			      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,ccResultEntry.getPvalue()));
			      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,MathUtil.getAntiLog2(ccResultEntry.getMeanGrp1())));
			      		reporterResultset.setFoldChangeLog2Intensity(new DatumDE(DatumDE.FOLD_CHANGE_LOG2_INTENSITY,ccResultEntry.getMeanGrp1()));
			      		reporterResultset.setStandardDeviationRatio(new DatumDE(DatumDE.STD_DEVIATION_RATIO,ccResultEntry.getStdGrp1()));					
			      		diseaseResultset.addReporterFoldChangeValuesResultset(reporterResultset);
			      		geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);	
					}
				}
			}
			//Add NON_TUMOR using baseline 
			ReporterFoldChangeValuesResultset reporterResultset = null;
			DiseaseNameDE disease = new DiseaseNameDE(RembrandtConstants.NON_TUMOR);
			DiseaseGeneExprPlotResultset diseaseResultset= new DiseaseGeneExprPlotResultset(disease);
		    if (ccList != null
					&& ccList.size() > 0) {
				ClassComparisonFinding classComparisonFinding = ccList.get(0);
				List<ClassComparisonResultEntry> ccResultEntryList = classComparisonFinding.getResultEntries();
				for(ClassComparisonResultEntry ccResultEntry: ccResultEntryList){					
					if(mapEntry.getValue().contains(ccResultEntry.getReporterId())){
						DatumDE reporter = null;
						if(arrayPlatformType.equals(ArrayPlatformType.AFFY_OLIGO_PLATFORM)){
							reporter = new DatumDE(DatumDE.PROBESET_ID,ccResultEntry.getReporterId());
						}else if(arrayPlatformType.equals(ArrayPlatformType.UNIFIED_GENE)){
							reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,ccResultEntry.getReporterId());
						}
						reporterResultset = diseaseResultset.getReporterFoldChangeValuesResultset(ccResultEntry.getReporterId());
			      		if(reporterResultset == null){
			      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
			      		}
			      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,ccResultEntry.getPvalue()));
			      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,MathUtil.getAntiLog2(ccResultEntry.getMeanBaselineGrp())));
			      		reporterResultset.setFoldChangeLog2Intensity(new DatumDE(DatumDE.FOLD_CHANGE_LOG2_INTENSITY,ccResultEntry.getMeanBaselineGrp()));
			      		reporterResultset.setStandardDeviationRatio(new DatumDE(DatumDE.STD_DEVIATION_RATIO,ccResultEntry.getStdBaselineGrp()));					
			      		diseaseResultset.addReporterFoldChangeValuesResultset(reporterResultset);
			      		geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);	
					}
				}
		    }
			
			
		}
		return geneExprDiseasePlotContainer;
		
	}
	/**
	 * @param classComparisonQueryDTOList
	 * @param sessionId
	 * @param taskId
	 * @param reporters 
	 * @return
	 */
	private Finding executeCompoundClassComparisonQuery(List<ClassComparisonQueryDTO> classComparisonQueryDTOList,String sessionId,String taskId, List<String> reporters){
		RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
		Finding finding = null;
		try {
			finding = factory.createCompoundClassComparisonFinding(classComparisonQueryDTOList,sessionId,taskId,reporters);
			
		} catch (FrameworkException e) {
			logger.error(e);
		}
		while(finding.getStatus() == FindingStatus.Running){
			 finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());			 
				 try {				
					Thread.sleep(500);
				} catch (InterruptedException e) {
					logger.error(e);
				}
		}
		return finding;
	}
	private  Map<String, List<String>> getGeneReporterMapFromGenes (GeneExpressionQueryInterface geneExpresionQuery) throws Exception{
		Map<String, List<String>> geneReporterMap = null;
		Collection geneIdDEs = geneExpresionQuery.getGeneIDCrit().getGeneIdentifiers();
        ArrayList<String> geneIDs = new ArrayList<String>();
        for (Object obj: geneIdDEs) {
            GeneIdentifierDE geneIdentifierDE  = (GeneIdentifierDE) obj;
            String value = null;
            value = geneIdentifierDE.getValueObject().toUpperCase();
            geneIDs.add(value);
        }
		ArrayPlatformType arrayPlatformType = geneExpresionQuery.getArrayPlatformCriteria().getPlatform().getValueObjectAsArrayPlatformType();
		if(arrayPlatformType.equals(ArrayPlatformType.AFFY_OLIGO_PLATFORM)){
			geneReporterMap = AnnotationHandler.getAffyProbeSetsForGeneSymbols(geneIDs);
		}else if(arrayPlatformType.equals(ArrayPlatformType.UNIFIED_GENE)){
			geneReporterMap = AnnotationHandler.getUnifiedGeneReportersForGeneSymbols(geneIDs);
		}
        return geneReporterMap;
	}
	/**
	 * @param geneExprDiseasePlotContainer
	 * @param exprObj
	 * @return
	 * @throws Exception
	 */
	private GeneExprDiseasePlotContainer handleDiseaseGeneExprPlotResultset(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer) throws Exception {
		//find out the disease type associated with the exprObj
  		//populate the DiseaseTypeResultset
		DiseaseGeneExprPlotResultset diseaseResultset = null;
		Set<String> diseaseTypes = clinicalQueryDTOMap.keySet();
		for(String diseaseType: diseaseTypes){
			DiseaseNameDE disease = new DiseaseNameDE(diseaseType);
		    diseaseResultset= new DiseaseGeneExprPlotResultset(disease);
		    geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);
		}
  		return geneExprDiseasePlotContainer;
	}
}
