/*
 *  @author: SahniH
 *  Created on Jan 7, 2005
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
package gov.nih.nci.nautilus.resultset;

import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.de.SampleIDDE;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.OperatorType;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.sample.SampleResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.view.Viewable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author SahniH
 * Date: Jan 7, 2005
 * 
 */
public class ShowAllValuesHandler {
	private static Logger logger = Logger.getLogger(ShowAllValuesHandler.class);
	private Resultant resultant = null;
	public ShowAllValuesHandler(Resultant resultant){
		this.resultant = resultant;
	}
	public CompoundQuery handleQuery() throws Exception{
		CompoundQuery  compoundQuery = null;
		if (this.resultant != null){
			compoundQuery = (CompoundQuery) resultant.getAssociatedQuery();
			Viewable view = compoundQuery.getAssociatedView();
			ResultsContainer resultsContainer = this.resultant.getResultsContainer();
			SampleCriteria sampleCrit = null;
	        Collection sampleIDs = new ArrayList();
			//Get the samples from the resultset object
			if(resultsContainer!= null){
				Collection samples = null;
				if(resultsContainer instanceof DimensionalViewContainer){				
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
					SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
					samples = sampleViewContainer.getBioSpecimenResultsets();
				}else if (resultsContainer instanceof SampleViewResultsContainer){
	
					SampleViewResultsContainer sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
					samples = sampleViewContainer.getBioSpecimenResultsets();
				}
				if(samples != null && samples.size() > 0){
		   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
	
		   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
		   	   			sampleIDs.add(new SampleIDDE(sampleResultset.getBiospecimen().getValue().toString()));
		   			}
		   			sampleCrit = new SampleCriteria();
		   			sampleCrit.setSampleIDs(sampleIDs);
				}
				compoundQuery = getShowAllValuesQuery(compoundQuery,sampleCrit);
				compoundQuery.setAssociatedView(view);
				
			}
		}
		return compoundQuery;
		}
	private CompoundQuery getShowAllValuesQuery(CompoundQuery compoundQuery, SampleCriteria sampleCrit) throws Exception{
		CompoundQuery showAllValuesQuery = null;
		Queriable leftQuery = compoundQuery.getLeftQuery();
		Queriable rightQuery = compoundQuery.getRightQuery();
		OperatorType operator = compoundQuery.getOperatorType();
		
		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftQuery = getShowAllValuesQuery((CompoundQuery) leftQuery, sampleCrit);
					}
				else if( leftQuery instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) leftQuery;
					geneExpressionQuery.setFoldChgCrit(null);
					geneExpressionQuery.setSampleIDCrit(sampleCrit);
					rightQuery = geneExpressionQuery;
				}
				else if( leftQuery instanceof ComparativeGenomicQuery){
					ComparativeGenomicQuery comparativeGenomicQuery = (ComparativeGenomicQuery) leftQuery;
					comparativeGenomicQuery.setCopyNumberCrit(null);
					comparativeGenomicQuery.setSampleIDCrit(sampleCrit);
					leftQuery = comparativeGenomicQuery;
				}
			}
			
			if (rightQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					rightQuery = getShowAllValuesQuery((CompoundQuery) rightQuery, sampleCrit);
					}
				else if( leftQuery instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) rightQuery;
					geneExpressionQuery.setFoldChgCrit(null);
					geneExpressionQuery.setSampleIDCrit(sampleCrit);
					rightQuery = geneExpressionQuery;
				}
				else if( leftQuery instanceof ComparativeGenomicQuery){
					ComparativeGenomicQuery comparativeGenomicQuery = (ComparativeGenomicQuery) rightQuery;
					comparativeGenomicQuery.setCopyNumberCrit(null);
					comparativeGenomicQuery.setSampleIDCrit(sampleCrit);
					rightQuery = comparativeGenomicQuery;
				}
			}
			if (operator != null) {
				showAllValuesQuery = new CompoundQuery(operator,leftQuery,rightQuery);							
			}else{ //then its the right query				
				showAllValuesQuery = new CompoundQuery(rightQuery);
			}
			
		}catch (Exception ex) {
			logger.error(ex);
		}
		return new CompoundQuery(OperatorType.AND,compoundQuery,showAllValuesQuery);
	}
	public DatumDE getFoldChangeValue(){
		if (this.resultant != null){
			CompoundQuery compoundQuery = (CompoundQuery) resultant.getAssociatedQuery();
			Query[] queries = compoundQuery.getAssociatiedQueries();
			for(int i = 0; i < queries.length; i++){
				Queriable query = queries[i];
				if(query instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) query;
					FoldChangeCriteria foldChgCrit= geneExpressionQuery.getFoldChgCrit();
					if (foldChgCrit != null){
						foldChgCrit.getFoldChangeObjects();
					}
				}
			}
		}
		return null;
	}
	public DatumDE getCopyNumberValue(){
		return null;
	}
}
