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

import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.de.SampleIDDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.OperatorType;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CytobandResultset;
import gov.nih.nci.nautilus.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.view.CopyNumberSampleView;
import gov.nih.nci.nautilus.view.GeneExprSampleView;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
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
	private CompoundQuery handleQuery() throws Exception{
		CompoundQuery  compoundQuery = null;
		if (this.resultant != null && this.resultant.getResultsContainer() != null){
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
					leftQuery = geneExpressionQuery;
				}
				else if( leftQuery instanceof ComparativeGenomicQuery){
					ComparativeGenomicQuery comparativeGenomicQuery = (ComparativeGenomicQuery) leftQuery;
					comparativeGenomicQuery.setCopyNumberCrit(null);
					comparativeGenomicQuery.setSampleIDCrit(sampleCrit);
					leftQuery = comparativeGenomicQuery;
				}
			}
			
			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightQuery = getShowAllValuesQuery((CompoundQuery) rightQuery, sampleCrit);
					}
				else if( rightQuery instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) rightQuery;
					geneExpressionQuery.setFoldChgCrit(null);
					geneExpressionQuery.setSampleIDCrit(sampleCrit);
					rightQuery = geneExpressionQuery;
				}
				else if( rightQuery instanceof ComparativeGenomicQuery){
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
	public Resultant getShowAllValuesResultant() throws Exception{
			ResultsContainer resultsContainer = this.resultant.getResultsContainer();
			CompoundQuery showQuery = handleQuery();
			Resultant showAllResultant = ResultsetManager.executeCompoundQuery(showQuery);
			

			int recordCount = 0;
			int totalSamples = 0;
			
			if( resultsContainer instanceof DimensionalViewContainer)	{
				DimensionalViewContainer originalViewContainer = (DimensionalViewContainer) resultsContainer;
				DimensionalViewContainer showallDimensionalViewContainer = (DimensionalViewContainer) showAllResultant.getResultsContainer();				
				if(originalViewContainer != null && showallDimensionalViewContainer != null)	{					
					if(( resultant.getAssociatedView() instanceof GeneExprSampleView )&& originalViewContainer.getGeneExprSingleViewContainer() != null){
						GeneExprSingleViewResultsContainer showAllGeneViewContainer = showallDimensionalViewContainer.getGeneExprSingleViewContainer();
						GeneExprSingleViewResultsContainer originalGeneViewContainer = originalViewContainer.getGeneExprSingleViewContainer();
						showAllGeneViewContainer = handleGeneExprContainer( originalGeneViewContainer,showAllGeneViewContainer);
						showallDimensionalViewContainer.setGeneExprSingleViewContainer(showAllGeneViewContainer);
					}
					else if((resultant.getAssociatedView()instanceof CopyNumberSampleView)&& originalViewContainer.getCopyNumberSingleViewContainer() != null){
						CopyNumberSingleViewResultsContainer showAllCopyNumberContainer = showallDimensionalViewContainer.getCopyNumberSingleViewContainer();
						CopyNumberSingleViewResultsContainer originalCopyNumberContainer = originalViewContainer.getCopyNumberSingleViewContainer();
						showAllCopyNumberContainer = handleCopyNumberContainer( originalCopyNumberContainer,showAllCopyNumberContainer);
						showallDimensionalViewContainer.setCopyNumberSingleViewContainer(showAllCopyNumberContainer);
					}
				}
			}
			return showAllResultant;
	}
	private GeneExprSingleViewResultsContainer handleGeneExprContainer(GeneExprSingleViewResultsContainer geneViewContainer,GeneExprSingleViewResultsContainer showAllGeneViewContainer){


    	Collection genes = showAllGeneViewContainer.getGeneResultsets();				   
    	Collection labels = showAllGeneViewContainer.getGroupsLabels();
		Collection sampleIds = null;
		
    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
    		Collection reporters = geneResultset.getReporterResultsets();

    		
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
        		String reporterName = reporterResultset.getReporter().getValue().toString();
        		GeneSymbol gene = geneResultset.getGeneSymbol();
        		String geneSymbol = null;
        		if( gene != null){
        			geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
        		}
        		Collection groupTypes = reporterResultset.getGroupByResultsets();
        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        			String label = (String) labelIterator.next();
        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
             			sampleIds = geneViewContainer.getBiospecimenLabels(label);
	        			if(groupResultset != null)
        				{
	                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	                       		String sampleId = (String) sampleIdIterator.next();
	                       		if (groupResultset.getBioSpecimenResultset(sampleId) instanceof SampleFoldChangeValuesResultset){
		                       		SampleFoldChangeValuesResultset showAllBiospecimenResultset  = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
		                       		SampleFoldChangeValuesResultset sampleFoldChangeValuesResultset = (SampleFoldChangeValuesResultset) geneViewContainer.getBioSpecimentResultset(geneSymbol,reporterName, label, sampleId);
		                       		//this gets only the common samples, we actually want the ones that are in the ALLrs and not the regular RS
		                       		//if(showAllBiospecimenResultset != null && sampleFoldChangeValuesResultset != null){
		                       			Double ratio = null;
		                       				try {
												ratio = (Double)sampleFoldChangeValuesResultset.getFoldChangeRatioValue().getValue();
											} catch (Exception e) {
												ratio = null;
											}
		                       			Double showAllRatio = null;
		                       				try {
												showAllRatio = (Double)showAllBiospecimenResultset.getFoldChangeRatioValue().getValue();
											} catch (Exception e1) {
												showAllRatio = null;
											}
		                       			if(ratio != null && ratio.equals(showAllRatio)){
		                       				showAllBiospecimenResultset.setHighlighted(false);  
		                       				groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);        			
		                       			}
                                        else{
                                            showAllBiospecimenResultset.setHighlighted(true);  
                                            groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);                    

                                        }
			                       //	}
	                     		}
                       		
	                       	}
                       }
	        			reporterResultset.addGroupByResultset(groupResultset);		                       	
         		}		        		
   				geneResultset.addReporterResultset(reporterResultset);
    		}
				showAllGeneViewContainer.addGeneResultset(geneResultset);
    	}
    	return showAllGeneViewContainer;
	}
	private CopyNumberSingleViewResultsContainer handleCopyNumberContainer(CopyNumberSingleViewResultsContainer copyNumberViewContainer,CopyNumberSingleViewResultsContainer showAllCopyNumberViewContainer){


    	Collection cytobands = showAllCopyNumberViewContainer.getCytobandResultsets();				   
    	Collection labels = showAllCopyNumberViewContainer.getGroupsLabels();
		Collection sampleIds = null;
		
    	for (Iterator geneIterator = cytobands.iterator(); geneIterator.hasNext();) {
    		CytobandResultset cytobandResultset = (CytobandResultset)geneIterator.next();
    		Collection reporters = cytobandResultset.getReporterResultsets();

    		
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
        		String reporterName = reporterResultset.getReporter().getValue().toString();
        		CytobandDE cytoband = cytobandResultset.getCytoband();
        		String cytobandName = " ";
        		if( cytoband != null){
        			cytobandName = cytobandResultset.getCytoband().getValueObject().toString();
        		}
        		Collection groupTypes = reporterResultset.getGroupByResultsets();
        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        			String label = (String) labelIterator.next();
        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
             			sampleIds = copyNumberViewContainer.getBiospecimenLabels(label);
	        			if(groupResultset != null)
        				{
	                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	                       		String sampleId = (String) sampleIdIterator.next();
	                       		if (groupResultset.getBioSpecimenResultset(sampleId) instanceof SampleCopyNumberValuesResultset){
	                       			SampleCopyNumberValuesResultset showAllBiospecimenResultset  = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
	                       			SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) copyNumberViewContainer.getBioSpecimentResultset(cytobandName,reporterName, label, sampleId);
		                       		//if(showAllBiospecimenResultset != null && sampleResultset2 != null){
	                       			Double ratio = null;
                       				try {
                       					ratio = (Double)showAllBiospecimenResultset.getCopyNumber().getValue();
									} catch (Exception e) {
										ratio = null;
									}
									
									Double showAllRatio = null;
                       				try {
                       					showAllRatio = (Double)sampleResultset2.getCopyNumber().getValue();
									} catch (Exception e1) {
										showAllRatio = null;
									}
									/*
										Double ratio = (Double)showAllBiospecimenResultset.getCopyNumber().getValue();
		                       			Double showAllRatio = (Double)showAllBiospecimenResultset.getCopyNumber().getValue();
		                       		*/
		                       			if(ratio != null && ratio.equals(showAllRatio)){
		                       				showAllBiospecimenResultset.setHighlighted(false);  
		                       				groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);        			
		                       			}
		                       			else{
                                            showAllBiospecimenResultset.setHighlighted(true);  
                                            groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);                    
                                        }
			                      // 	}
	                     		}			                       		
	                       	}
                       }
	        			reporterResultset.addGroupByResultset(groupResultset);		                       	
         		}		        		
   				cytobandResultset.addReporterResultset(reporterResultset);
    		}
				showAllCopyNumberViewContainer.addCytobandResultset(cytobandResultset);
    	}
    	return showAllCopyNumberViewContainer;
	}
}
