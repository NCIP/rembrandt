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

import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;
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
import gov.nih.nci.nautilus.view.Viewable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author SahniH
 * Date: Jan 7, 2005
 * 
 */
public class ShowAllValuesHandler {
	private static Logger logger = Logger.getLogger(ShowAllValuesHandler.class);
	private Resultant resultant = null;
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria = null;
	private SNPCriteria snpCriteria = null;
	private SampleCriteria sampleCrit = null;
    private CompoundQuery newCompoundQuery;
    private CompoundQuery originalQuery;
	public ShowAllValuesHandler(Resultant resultant){
		this.resultant = resultant;
		originalQuery = (CompoundQuery) resultant.getAssociatedQuery();
		if(originalQuery != null){
			newCompoundQuery =  (CompoundQuery) originalQuery.clone();
		}
	}
	private CompoundQuery handleQuery() throws Exception{
		//CompoundQuery  compoundQuery = null;
		if (this.resultant != null && this.resultant.getResultsContainer() != null){
			//compoundQuery = (CompoundQuery) resultant.getAssociatedQuery();
			Viewable view = newCompoundQuery.getAssociatedView();
			ResultsContainer resultsContainer = this.resultant.getResultsContainer();
			sampleCrit = null;
	        Collection sampleIDs = new ArrayList(); 
			//Get the samples from the resultset object
			if(resultsContainer!= null){
				Collection samples = null;
				if(resultsContainer instanceof DimensionalViewContainer){				
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
					///TO NEED TO RUN IT FOR NOW handleReporters(dimensionalViewContainer);
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
				ConstrainedQueryWithSamplesHandler.constrainQuery(newCompoundQuery,sampleCrit);
				newCompoundQuery = getShowAllValuesQuery(newCompoundQuery);
				newCompoundQuery.setAssociatedView(view);
				
			}
		}
		return newCompoundQuery;
		}
	/**
	 * @param dimensionalViewContainer
	 */
	private void handleReporters(DimensionalViewContainer dimensionalViewContainer) {
		Collection geneExpressionReporters = new ArrayList();
		Collection copyNumberReprters = new ArrayList();
		if(dimensionalViewContainer != null){
		CopyNumberSingleViewResultsContainer copyNumberSingleViewContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
		GeneExprSingleViewResultsContainer geneExprSingleViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
		if(copyNumberSingleViewContainer!= null){
			List reporterNames =  copyNumberSingleViewContainer.getReporterNames();
       		for (Iterator reporterNamesIterator = reporterNames.iterator(); reporterNamesIterator.hasNext();) {
       			String reporterName = (String) reporterNamesIterator.next();
       			copyNumberReprters.add(new SNPIdentifierDE.SNPProbeSet(reporterName));
       		}
		}
		if(geneExprSingleViewContainer!= null){
			List reporterNames =  geneExprSingleViewContainer.getAllReporterNames();
       		for (Iterator reporterNamesIterator = reporterNames.iterator(); reporterNamesIterator.hasNext();) {
       			String reporterName = (String) reporterNamesIterator.next();
       			if(reporterName.indexOf("IMAGE")>0){
       				geneExpressionReporters.add(new CloneIdentifierDE.IMAGEClone(reporterName));
       			}
       			else{
    				geneExpressionReporters.add(new CloneIdentifierDE.ProbesetID(reporterName));
       			}
       		}
		}
		if(copyNumberReprters.size()> 0){
			this.snpCriteria = new SNPCriteria();
			this.snpCriteria.setSNPIdentifiers(copyNumberReprters);
		}
		if(geneExpressionReporters.size()> 0){
			this.cloneOrProbeIDCriteria = new CloneOrProbeIDCriteria();
			this.cloneOrProbeIDCriteria.setIdentifiers(geneExpressionReporters);
		}
		}
		
	}
	private CompoundQuery getShowAllValuesQuery(CompoundQuery compoundQuery) throws Exception{
		CompoundQuery newQuery = null;
		Queriable leftQuery = compoundQuery.getLeftQuery();
		Queriable rightQuery = compoundQuery.getRightQuery();
		OperatorType operator = compoundQuery.getOperatorType();
		
		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftQuery = getShowAllValuesQuery((CompoundQuery) leftQuery);
					}
				else if( leftQuery instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) leftQuery;
                    leftQuery = populateGeneExpressionQuery(geneExpressionQuery);
				}
				else if( leftQuery instanceof ComparativeGenomicQuery){
					ComparativeGenomicQuery comparativeGenomicQuery = (ComparativeGenomicQuery) leftQuery;
                    leftQuery = populateCGHQuery(comparativeGenomicQuery);                  
				}
			}
			
			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightQuery = getShowAllValuesQuery((CompoundQuery) rightQuery);
					}
				else if( rightQuery instanceof GeneExpressionQuery){
					GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) rightQuery;
                    rightQuery = populateGeneExpressionQuery(geneExpressionQuery);
                    
				}
				else if( rightQuery instanceof ComparativeGenomicQuery){
					ComparativeGenomicQuery comparativeGenomicQuery = (ComparativeGenomicQuery) rightQuery;
                    rightQuery = populateCGHQuery(comparativeGenomicQuery);                   
                }
			}
			if (operator != null) {
				newQuery = new CompoundQuery(operator,leftQuery,rightQuery);							
			}else{ //then its the right query				
				newQuery = new CompoundQuery(rightQuery);
			}
			
		}catch (Exception ex) {
			logger.error(ex);
		}
		return newQuery;
	}
	/**
	 * @param geneExpressionQuery
	 * @return
	 */
	private GeneExpressionQuery populateGeneExpressionQuery(GeneExpressionQuery geneExpressionQuery) {
	 if(geneExpressionQuery.getFoldChgCrit() != null){
        geneExpressionQuery.setFoldChgCrit(null);
	 }
	 if(cloneOrProbeIDCriteria != null){
	 	geneExpressionQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
	 }
	 return geneExpressionQuery;
	}
	/**
	 * @param comparativeGenomicQuery
	 * @return
	 */
	private ComparativeGenomicQuery populateCGHQuery(ComparativeGenomicQuery comparativeGenomicQuery) {
		if(comparativeGenomicQuery.getCopyNumberCriteria() != null){
            comparativeGenomicQuery.setCopyNumberCrit(null);
        }
		if(snpCriteria != null){
		 	comparativeGenomicQuery.setSNPCrit(snpCriteria);
		 }
		 return comparativeGenomicQuery;
	}
	public Resultant getShowAllValuesResultant() throws Exception{
			ResultsContainer resultsContainer = this.resultant.getResultsContainer();
			CompoundQuery newShowAllQuery = handleQuery();
			Resultant showAllResultant = ResultsetManager.executeCompoundQuery(newShowAllQuery);
			

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
					showAllResultant.setResultsContainer(showallDimensionalViewContainer);
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

		                       			Double ratio = null;
		                       			if(sampleFoldChangeValuesResultset != null && sampleFoldChangeValuesResultset.getFoldChangeRatioValue() != null){
		                       				ratio = (Double)sampleFoldChangeValuesResultset.getFoldChangeRatioValue().getValue();
		                       			}
		                       			Double showAllRatio = null;
		                       			if(showAllBiospecimenResultset != null && showAllBiospecimenResultset.getFoldChangeRatioValue() != null){
		                       				showAllRatio = (Double)showAllBiospecimenResultset.getFoldChangeRatioValue().getValue();
		                       			}		  
		                       			if(ratio != null && ratio.equals(showAllRatio)){
		                       				showAllBiospecimenResultset.setHighlighted(false);  
		                       				groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);        			
		                       			}
                                        else{
                                            showAllBiospecimenResultset.setHighlighted(true);  
                                            groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);                    

                                        }

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

	                       			Double ratio = null;
	                       			if(sampleResultset2 != null && sampleResultset2.getCopyNumber() != null){
	                       				ratio = (Double)sampleResultset2.getCopyNumber().getValue();
	                       			}
	                       			Double showAllRatio = null;
	                       			if(showAllBiospecimenResultset != null && showAllBiospecimenResultset.getCopyNumber()!= null){
	                       				showAllRatio = (Double)showAllBiospecimenResultset.getCopyNumber().getValue();
	                       			}
		                       			if(ratio != null && ratio.equals(showAllRatio)){
		                       				showAllBiospecimenResultset.setHighlighted(false);  
		                       				groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);        			
		                       			}
		                       			else{
                                            showAllBiospecimenResultset.setHighlighted(true);  
                                            groupResultset.addBioSpecimenResultset(showAllBiospecimenResultset);                    
                                        }

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
