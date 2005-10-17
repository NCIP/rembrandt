/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.rembrandt.dto.finding.ClassComparisonFindingsResultset;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQuery;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.view.ClinicalSampleView;
import gov.nih.nci.rembrandt.queryservice.view.ViewFactory;
import gov.nih.nci.rembrandt.queryservice.view.ViewType;
import gov.nih.nci.rembrandt.queryservice.view.Viewable;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class ClassComparisonFindingStrategy implements FindingStrategy {
	private static Logger logger = Logger.getLogger(ClassComparisonFindingStrategy.class);	
	@SuppressWarnings("unused")
	private ClassComparisonQuery query = null;
	@SuppressWarnings("unused")
	private Collection<ClinicalDataQuery> clinicalDataQueries;
	@SuppressWarnings({"unchecked"})
	private Collection<SampleGroup> sampleGroups = new ArrayList();
	private String sessionId = null;
	private String taskId = null;
	private ClassComparisonRequest classComparisonRequest = null;
	private ClassComparisonResult classComparisonResult = null;
	private ClassComparisonFindingsResultset classComparisonFindingsResultset = null;
	
	public ClassComparisonFindingStrategy(String sessionId, String taskId, ClassComparisonQuery query) throws ValidationException {
		//Check if the passed query is valid
		if(validate((Query) query)){
			this.query = query;
			this.sessionId = sessionId;
			this.taskId = taskId;
			classComparisonRequest = new ClassComparisonRequest(sessionId,taskId);
		}
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		//because each layer is valid I am assured I will be getting a fulling populated query object
		StatisticalMethodType statisticType = query.getClassComparisionAnalysisCriteria().getStatisticTypeDE().getValueObject();

		if(query.getClinicalDataQueryCollection() != null ){
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				if( query.getClinicalDataQueryCollection().size() != 2){
					throw new FindingsQueryException("Incorrect Number of queries passed for the TTest and  Wilcoxin stat type");
				}
				break;
			default:
				throw new FindingsQueryException("No StatisticalMethodType selected");
			}
			clinicalDataQueries = query.getClinicalDataQueryCollection();
			return true;	
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		if(clinicalDataQueries != null){
		Resultant resultant;	
		CompoundQuery compoundQuery;
			    for (ClinicalDataQuery clinicalDataQuery: clinicalDataQueries){

				try {
					compoundQuery = new CompoundQuery(clinicalDataQuery);
					compoundQuery.setAssociatedView(ViewFactory
		                .newView(ViewType.CLINICAL_VIEW));
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
								Collection<String> sampleIDs = StrategyHelper.extractSampleIds(resultsContainer);
								if(sampleIDs != null){
									SampleGroup group = new SampleGroup(clinicalDataQuery.getQueryName(),sampleIDs.size());
									group.addAll(sampleIDs);
									sampleGroups.add(group);
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
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		StatisticalMethodType statisticType = query.getClassComparisionAnalysisCriteria().getStatisticTypeDE().getValueObject();
		classComparisonRequest.setStatisticalMethod(statisticType);
		try{
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				{
					//set MultiGroupComparisonAdjustmentType
					classComparisonRequest.setMultiGroupComparisonAdjustmentType(
					query.getClassComparisionAnalysisCriteria().getMultiGroupComparisonAdjustmentTypeDE().getValueObject());				
					//set foldchange
					Collection objs = query.getFoldChangeCriteria().getFoldChangeObjects();
					Object[] foldObjs = objs.toArray();				
					// Either only UpRegulation or DownRegulation
					if (foldObjs.length == 1) {
						ExprFoldChangeDE foldChgObj = (ExprFoldChangeDE) foldObjs[0];
						Double foldChange = new Double(foldChgObj.getValueObject().floatValue());
						classComparisonRequest.setFoldChangeThreshold(foldChange);
					}
					//set platform
					//TODO:Covert ArrayPlatform String to Enum -Himanso 10/15/05
					if(query.getArrayPlatformCriteria().getPlatform().getValueObject().equals(ArrayPlatformType.AFFY_OLIGO_PLATFORM)){
						classComparisonRequest.setArrayPlatform(ArrayPlatformType.AFFY_OLIGO_PLATFORM); //TODO: Needs to change
					}
					// set SampleGroups
					SampleGroup[] sampleGroupObjects = (SampleGroup[]) sampleGroups.toArray();				
					if (sampleGroupObjects.length == 2) {
						classComparisonRequest.setGroup1(sampleGroupObjects[0]);
						classComparisonRequest.setGroup2(sampleGroupObjects[1]);
					}
					// set PvalueThreshold
					classComparisonRequest.setPvalueThreshold(query.getClassComparisionAnalysisCriteria().getStatisticalSignificanceDE().getValueObject());
					return true;
				}
			}
			
		}catch(Exception e){
			throw new FindingsAnalysisException("Error in setting ClassComparisonRequest object");
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#getResultSet()
	 */
	public Object getResultSet() {
		// TODO Auto-generated method stub
		return classComparisonFindingsResultset;
	}

	public boolean validate(Query query) throws ValidationException {
		if(query != null  && (query.validate())){
			return true;
		}
		else throw new ValidationException("ClassComparionQuery in ClassComparionFindingStatergy cannot be null");


	}


}
