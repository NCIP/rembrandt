package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.rembrandt.analysis.server.AnalysisServerClientManager;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class ClassComparisonFindingStrategy implements FindingStrategy {
	private static Logger logger = Logger.getLogger(ClassComparisonFindingStrategy.class);	
	@SuppressWarnings("unused")
	private ClassComparisonQueryDTO myQueryDTO;
	@SuppressWarnings("unused")
	private Collection<ClinicalDataQuery> clinicalQueries;
	@SuppressWarnings({"unchecked"})
	private Collection<SampleGroup> sampleGroups = new ArrayList<SampleGroup>(); // = testmethod();
	private Collection<SampleIDDE> samplesNotFound;
	private String sessionId;
	private String taskId;
	private ClassComparisonRequest classComparisonRequest = null;
	private ClassComparisonFinding classComparisonFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	public ClassComparisonFindingStrategy(String sessionId, String taskId, ClassComparisonQueryDTO queryDTO) throws ValidationException {
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = queryDTO;
			this.sessionId = sessionId;
			this.taskId = taskId;
			classComparisonRequest = new ClassComparisonRequest(this.sessionId,this.taskId);
            try {
                analysisServerClientManager = AnalysisServerClientManager.getInstance();
            } catch (NamingException e) {               
                logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
                logger.error(e.getMessage());
                logger.error(e);
            } catch (JMSException e) {                
                logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
                logger.error(e.getMessage());
                logger.error(e);
            }
		}
		/*
		 * Set the Finding into the cache! YOU HAVE TO DO THIS!
		 */
		
		FindingStatus currentStatus = FindingStatus.Running;
		classComparisonFinding = new ClassComparisonFinding(sessionId, taskId, currentStatus, null);
		cacheManager.addToSessionCache(sessionId, taskId, classComparisonFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		//because each layer is valid I am assured I will be getting a fulling populated query object
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();

		if(myQueryDTO.getComparisonGroups() != null ){
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				if( myQueryDTO.getComparisonGroups().size() != 2){
					throw new FindingsQueryException("Incorrect Number of queries passed for the TTest and  Wilcoxin stat type");
				}
				break;
			default:
				throw new FindingsQueryException("No StatisticalMethodType selected");
			}
			/**
			 * We have to convert from ClinicalQueryDTO to ClinicalDataQuery (a rembrandt class)
			 * because at the time of this writing the DTO was only a marker interface.
			 * The ClinicalDataQuery was an exsisting Query that we could not change 
			 * in the interest of time.
			 */
			Collection<ClinicalQueryDTO> clinicalQueryDTOs = myQueryDTO.getComparisonGroups();
			clinicalQueries = new ArrayList<ClinicalDataQuery>();
			for(ClinicalQueryDTO clinicalQueryDTO: clinicalQueryDTOs) {
				clinicalQueries.add((ClinicalDataQuery)clinicalQueryDTO);
			}
			
			return true;	
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		if(clinicalQueries != null){
		CompoundQuery compoundQuery;
			    for (ClinicalDataQuery clinicalDataQuery: clinicalQueries){
			    Resultant resultant;
				try {
					compoundQuery = new CompoundQuery(clinicalDataQuery);
					compoundQuery.setAssociatedView(ViewFactory
		                .newView(ViewType.CLINICAL_VIEW));
					InstitutionCriteria institutionCriteria = new InstitutionCriteria();
					institutionCriteria.setInstitutions(myQueryDTO.getInstitutionDEs());
					compoundQuery.setInstitutionCriteria( institutionCriteria);
					resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
		  		}
		  		catch (Throwable t)	{
		  			logger.error("Error Executing the query/n"+ t.getMessage());
		  			throw new FindingsQueryException("Error executing clinical query/n"+t.getMessage());
		  		}

				if(resultant != null) {      
			 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
			 		Viewable view = resultant.getAssociatedView();
			 		if(resultsContainer != null)	{
			 			if(view instanceof ClinicalSampleView){
			 				try {
			 					//1. Get the sample Ids from the return Clinical query
								Collection<SampleIDDE> sampleIDDEs = StrategyHelper.extractSampleIDDEs(resultsContainer);
								//2. validate samples so that GE data exsists for these samples
								Collection<SampleIDDE> validSampleIDDEs = DataValidator.validateSampleIds(sampleIDDEs);
								//3. Extracts sampleIds as Strings
								Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
								if(sampleIDs != null){
									//3.1 add them to SampleGroup
									SampleGroup sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),validSampleIDDEs.size());
									sampleGroup.addAll(sampleIDs);
									sampleGroups.add(sampleGroup);
//									//3.2 Find out any samples that were not processed  
									Set<SampleIDDE> set = new HashSet<SampleIDDE>();
									set.addAll(sampleIDDEs); //samples from the original query
									//3.3 Remove all samples that are validated	
									set.removeAll(validSampleIDDEs);
									samplesNotFound = set;
									
								}
							} catch (OperationNotSupportedException e) {
								logger.error(e.getMessage());
					  			throw new FindingsQueryException(e.getMessage());
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage());
					  			throw new FindingsQueryException(e.getMessage());
							}

		 				}	
			 		}
				}
			 }
		}
	    return true;


	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		classComparisonRequest.setStatisticalMethod(statisticType);
		try{
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				{
					//set MultiGroupComparisonAdjustmentType
					classComparisonRequest.setMultiGroupComparisonAdjustmentType(
							myQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject());				
					//set foldchange
				
					ExprFoldChangeDE foldChange = myQueryDTO.getExprFoldChangeDE();
					classComparisonRequest.setFoldChangeThreshold(foldChange.getValueObject());
					//set platform
					//Covert ArrayPlatform String to Enum -Himanso 10/15/05
						classComparisonRequest.setArrayPlatform(myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()); 

					// set SampleGroups
                    Object[] obj = sampleGroups.toArray();
					//SampleGroup[] sampleGroupObjects =  (SampleGroup[]) sampleGroups.toArray();				
					if (obj.length >= 2) {
						classComparisonRequest.setGroup1((SampleGroup)obj[0]);
						classComparisonRequest.setGroup2((SampleGroup)obj[1]);
					}
					// set PvalueThreshold
					classComparisonRequest.setPvalueThreshold(myQueryDTO.getStatisticalSignificanceDE().getValueObject());
                    analysisServerClientManager.sendRequest(classComparisonRequest);
                    return true;
				}
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
  			throw new FindingsAnalysisException(e.getMessage());
		} catch(Exception e){
			logger.error(e.getMessage());
			throw new FindingsAnalysisException("Error in setting ClassComparisonRequest object");
		}

		return false;
	}


	public Finding getFinding() {
		return classComparisonFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof ClassComparisonQueryDTO){
			ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)queryDTO;
			try {
				ValidationUtility.checkForNull(classComparisonQueryDTO.getInstitutionDEs());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getArrayPlatformDE()) ;
				ValidationUtility.checkForNull(classComparisonQueryDTO.getComparisonGroups());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getExprFoldChangeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getQueryName());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticalSignificanceDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticTypeDE());
					switch (classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject()){
						case NONE:
							if(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() != StatisticalSignificanceType.pValue)
								throw(new ValidationException("When multiGroupComparisonAdjustmentTypeDE is NONE, Statistical Type should be pValue"));
							break;
						case FWER:
						case FDR:
							if(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() != StatisticalSignificanceType.adjustedpValue)
								throw(new ValidationException("When multiGroupComparisonAdjustmentTypeDE is FWER or FDR, Statistical Type should be adjusted pValue"));
							break;
						default:
								throw(new ValidationException("multiGroupComparisonAdjustmentTypeDE is does not match any options"));
					}
					_valid = true;
				} catch (ValidationException ex) {
					logger.error(ex.getMessage());
					throw ex;
				}
		}		
		return _valid;
	}
}
