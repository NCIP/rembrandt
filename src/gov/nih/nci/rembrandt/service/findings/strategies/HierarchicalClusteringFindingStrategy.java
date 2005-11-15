package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.HierarchicalClusteringRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.caintegrator.dto.view.ViewType.GeneSingleSampleView;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.rembrandt.analysis.server.AnalysisServerClientManager;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.Collection;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class HierarchicalClusteringFindingStrategy implements FindingStrategy {
	private static Logger logger = Logger.getLogger(HierarchicalClusteringFindingStrategy.class);	
	@SuppressWarnings("unused")
	private HierarchicalClusteringQueryDTO myQueryDTO;
	private ReporterGroup reporterGroup; 
	private SampleGroup sampleGroup;
	private String sessionId;
	private String taskId;
	private ClinicalDataQuery clinicalDataQuery;
	private GeneExpressionQuery geneExprQuery;
	private HierarchicalClusteringRequest hcRequest;
	private HCAFinding hcFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	public HierarchicalClusteringFindingStrategy(String sessionId, String taskId, HierarchicalClusteringQueryDTO queryDTO) throws ValidationException {
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = queryDTO;
			this.sessionId = sessionId;
			this.taskId = taskId;
			hcRequest = new HierarchicalClusteringRequest(this.sessionId,this.taskId);
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
		hcFinding = new HCAFinding(sessionId, taskId, currentStatus, null);
		cacheManager.addToSessionCache(sessionId, taskId, hcFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * 
	 */
	public boolean createQuery() throws FindingsQueryException {
		boolean flag = true;

		
		if(myQueryDTO.getGeneIdentifierDEs() != null ||
				myQueryDTO.getReporterIdentifierDEs() != null){
			geneExprQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	    	geneExprQuery.setQueryName(myQueryDTO.getQueryName());
	    	geneExprQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
	    	
		    if(myQueryDTO.getGeneIdentifierDEs() != null){
		    	GeneIDCriteria geneCriteria = new GeneIDCriteria();
		    	geneCriteria.setGeneIdentifiers(myQueryDTO.getGeneIdentifierDEs());
		    	geneExprQuery.setGeneIDCrit(geneCriteria);
		    }
		    	
		    if(myQueryDTO.getReporterIdentifierDEs() != null){
		    	CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
		    	reporterCriteria.setIdentifiers(myQueryDTO.getReporterIdentifierDEs()); 
		    	geneExprQuery.setCloneOrProbeIDCrit(reporterCriteria);
		    }    	
		}
	    
		return flag;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		
		//Get Reporters from DB
		if(geneExprQuery != null){
			CompoundQuery compoundQuery;
			Resultant resultant;
			try {
				compoundQuery = new CompoundQuery(geneExprQuery);
				compoundQuery.setAssociatedView(ViewFactory
	                .newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
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
		 			if(view instanceof GeneSingleSampleView){
		 				try {
							Collection<String> reporters = StrategyHelper.extractReporters(resultsContainer);
							if(reporters != null){
								this.reporterGroup = new ReporterGroup(clinicalDataQuery.getQueryName(),reporters.size());
								reporterGroup.addAll(reporters);
								
							}
						} catch (OperationNotSupportedException e) {
							logger.error(e.getMessage());
				  			throw new FindingsQueryException(e.getMessage());
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
		hcRequest = new HierarchicalClusteringRequest(sessionId, taskId);
		hcRequest.setVarianceFilterValue(myQueryDTO.getGeneVectorPercentileDE().getDecimalValue());
		hcRequest.setArrayPlatform(myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType());
		if(reporterGroup != null){
			hcRequest.setReporterGroup(reporterGroup);
		}
		
        try {
			analysisServerClientManager.sendRequest(hcRequest);
		} catch (JMSException e) {
			logger.error(e.getMessage());
  			throw new FindingsAnalysisException(e.getMessage());
		} catch(Exception e){
			logger.error(e.getMessage());
			throw new FindingsAnalysisException("Error in setting HierarchicalClusteringRequest object");
		}
        return true;
	}

    


	public Finding getFinding() {
		return hcFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof HierarchicalClusteringQueryDTO){
			_valid = true;
            HierarchicalClusteringQueryDTO hcQueryDTO = (HierarchicalClusteringQueryDTO)queryDTO;
				
			try {
						//ValidationUtility.checkForNull(pcaQueryDTO.getInstitutionNameDE() != null));
						ValidationUtility.checkForNull(hcQueryDTO.getArrayPlatformDE()) ;
						ValidationUtility.checkForNull(hcQueryDTO.getQueryName());
					
					if( hcQueryDTO.getGeneVectorPercentileDE() == null && 
							hcQueryDTO.getGeneIdentifierDEs()== null &&
							hcQueryDTO.getReporterIdentifierDEs() == null){
						throw new ValidationException("HC query should be contrained by atleast one of the following: GeneVector, Genes or Reporters");
					}
				} catch (ValidationException ex) {

					logger.error(ex.getMessage());
					throw ex;
				}
		}
		return _valid;	
	}
}
