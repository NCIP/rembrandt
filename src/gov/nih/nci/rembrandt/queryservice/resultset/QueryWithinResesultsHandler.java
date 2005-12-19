/*
 *  @author: SahniH
 *  Created on Jan 14, 2005
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
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
