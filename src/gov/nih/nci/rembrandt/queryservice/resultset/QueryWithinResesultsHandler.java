package gov.nih.nci.rembrandt.queryservice.resultset;

import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author SahniH
 * Date: Jan 14, 2005
 * 
 */
public class QueryWithinResesultsHandler {
	private Resultant resultant = null;
	public QueryWithinResesultsHandler(Resultant resultant){
		this.resultant = resultant;
	}
	@SuppressWarnings("unchecked")
	public CompoundQuery getResultsetQuery() throws Exception{
			CompoundQuery  queryWithinResesults = null;
			if (this.resultant != null){
				CompoundQuery compoundQuery = (CompoundQuery) resultant.getAssociatedQuery();
				Viewable view = compoundQuery.getAssociatedView();
				ResultsContainer resultsContainer = this.resultant.getResultsContainer();
				SampleCriteria sampleCrit = null;
		        Collection sampleIDs = new ArrayList();
				//Get the samples from the resultset object
				if(resultsContainer!= null){
					Collection<SampleResultset> samples = null;
					if(resultsContainer instanceof DimensionalViewContainer){				
						DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
						SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
						samples = sampleViewContainer.getSampleResultsets();
					}else if (resultsContainer instanceof SampleViewResultsContainer){
		
						SampleViewResultsContainer sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
						samples = sampleViewContainer.getSampleResultsets();
					}
					if(samples != null && samples.size() > 0){
			   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		
			   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
			   	   			sampleIDs.add(sampleResultset.getSampleIDDE());
			   			}
			   			sampleCrit = new SampleCriteria();
			   			sampleCrit.setSampleIDs(sampleIDs);
					}
					ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
					clinicalDataQuery.setQueryName("Resultset Sample Query");
					clinicalDataQuery.setSampleIDCrit(sampleCrit);
		            queryWithinResesults = new CompoundQuery( OperatorType.AND, compoundQuery,clinicalDataQuery );

					
				}
			}
			return queryWithinResesults;
			}

}
