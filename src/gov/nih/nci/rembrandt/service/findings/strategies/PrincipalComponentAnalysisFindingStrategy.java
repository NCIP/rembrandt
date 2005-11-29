package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.PrincipalComponentAnalysisRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.rembrandt.analysis.server.AnalysisServerClientManager;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.validation.Validator;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class PrincipalComponentAnalysisFindingStrategy implements FindingStrategy {
	private static Logger logger = Logger.getLogger(PrincipalComponentAnalysisFindingStrategy.class);	
	@SuppressWarnings("unused")
	private PrincipalComponentAnalysisQueryDTO myQueryDTO;
	private ReporterGroup reporterGroup; 
	private SampleGroup sampleGroup;
	private String sessionId;
	private String taskId;
	private ClinicalDataQuery clinicalDataQuery;
	private GeneExpressionQuery geneExprQuery;
	private Collection<CloneIdentifierDE> reportersNotFound;
	private Collection<GeneIdentifierDE> genesNotFound;
	private Collection<SampleIDDE> samplesNotFound;
	private PrincipalComponentAnalysisRequest pcaRequest;
	private PrincipalComponentAnalysisFinding pcaFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	public PrincipalComponentAnalysisFindingStrategy(String sessionId, String taskId, PrincipalComponentAnalysisQueryDTO queryDTO) throws ValidationException {
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = queryDTO;
			this.sessionId = sessionId;
			this.taskId = taskId;
			pcaRequest = new PrincipalComponentAnalysisRequest(this.sessionId,this.taskId);
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
		pcaFinding = new PrincipalComponentAnalysisFinding(sessionId, taskId, currentStatus, null);
		cacheManager.addToSessionCache(sessionId, taskId, pcaFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		boolean flag = true;

		if(myQueryDTO.getComparisonGroup() != null){
			clinicalDataQuery = (ClinicalDataQuery) myQueryDTO.getComparisonGroup();
		}
	    
		return flag;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		//Get Sample Ids from DB
		if(clinicalDataQuery != null){
			CompoundQuery compoundQuery;
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
							Collection<SampleIDDE> validSampleIDDEs = Validator.validateSampleIds(sampleIDDEs);
							//3. Extracts sampleIds as Strings
							Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
							if(sampleIDs != null){
								//3.1 add them to SampleGroup
								sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),validSampleIDDEs.size());
								sampleGroup.addAll(sampleIDs);
//								//3.2 Find out any samples that were not processed  
								Set<SampleIDDE> set = new HashSet<SampleIDDE>();
								set.addAll(sampleIDDEs); //samples from the original query
								//3.3 Remove all samples that are validated								set.removeAll(validSampleIDDEs);
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
		//Get Reporters from DB
		if(myQueryDTO.getGeneIdentifierDEs() != null ||
				myQueryDTO.getReporterIdentifierDEs() != null){
			if(	myQueryDTO.getReporterIdentifierDEs() != null){
					Collection<CloneIdentifierDE> validCloneDEs;
					try {
						validCloneDEs = Validator.validateReporters(myQueryDTO.getReporterIdentifierDEs());

					//Create a set of submitted Reporters 
					Set<CloneIdentifierDE> set = new HashSet<CloneIdentifierDE>();
					set.addAll(myQueryDTO.getReporterIdentifierDEs());
					// Find out if any reports were not validated
					set.removeAll(validCloneDEs);
					reportersNotFound = set;
					
					Collection<String> reporters = StrategyHelper.extractReporters(validCloneDEs);
					if(reporters != null){
						this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
						reporterGroup.addAll(reporters);
						
					}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage());
			  			throw new FindingsQueryException(e.getMessage());
					}
				
				}
			if(	myQueryDTO.getGeneIdentifierDEs() != null){
				Collection<GeneIdentifierDE> validGeneDEs;
				try {
					validGeneDEs = Validator.validateGenes(myQueryDTO.getGeneIdentifierDEs());

				//Create a set of submitted Reporters 
				Set<GeneIdentifierDE> set = new HashSet<GeneIdentifierDE>();
				set.addAll(myQueryDTO.getGeneIdentifierDEs());
				// Find out if any reports were not validated
				set.removeAll(validGeneDEs);
				genesNotFound = set;
				
				Collection<String> reporters = StrategyHelper.extractGenes(validGeneDEs);
				if(reporters != null){
					this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
					reporterGroup.addAll(reporters);
					
				}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
		  			throw new FindingsQueryException(e.getMessage());
				}
			
			}
			}
		





			return true;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		pcaRequest = new PrincipalComponentAnalysisRequest(sessionId, taskId);
		pcaRequest.setVarianceFilterValue(myQueryDTO.getGeneVectorPercentileDE().getDecimalValue());
		pcaRequest.setPlatform(myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType());
		if(reporterGroup != null){
			pcaRequest.setReporterGroup(reporterGroup);
		}
		if(sampleGroup != null){
			pcaRequest.setSampleGroup(sampleGroup);
		}
		else{
			throw new FindingsAnalysisException("PC Analysis requires a set of samples");
		}
        try {
			analysisServerClientManager.sendRequest(pcaRequest);
		} catch (JMSException e) {
			logger.error(e.getMessage());
  			throw new FindingsAnalysisException(e.getMessage());
		} catch(Exception e){
			logger.error(e.getMessage());
			throw new FindingsAnalysisException("Error in setting PrincipalComponentAnalysisRequest object");
		}
        return true;
	}

    


	public Finding getFinding() {
		return pcaFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof PrincipalComponentAnalysisQueryDTO){
			_valid = true;
			PrincipalComponentAnalysisQueryDTO pcaQueryDTO = (PrincipalComponentAnalysisQueryDTO)queryDTO;
				
			try {
						ValidationUtility.checkForNull(pcaQueryDTO.getInstitutionDEs());
						ValidationUtility.checkForNull(pcaQueryDTO.getArrayPlatformDE()) ;
						ValidationUtility.checkForNull(pcaQueryDTO.getComparisonGroup());
						ValidationUtility.checkForNull(pcaQueryDTO.getQueryName());
					
					if( pcaQueryDTO.getGeneVectorPercentileDE() == null && 
							pcaQueryDTO.getGeneIdentifierDEs()== null &&
							pcaQueryDTO.getReporterIdentifierDEs() == null){
						throw new ValidationException("PCA query should be contrained by atleast one of the following: GeneVector, Genes or Reporters");
					}
				} catch (ValidationException ex) {

					logger.error(ex.getMessage());
					throw ex;
				}
		}
		return _valid;	
	}
}
