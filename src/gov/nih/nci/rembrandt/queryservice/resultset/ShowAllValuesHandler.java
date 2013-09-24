/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset;

import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CytobandResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.log4j.Logger;

/**
 * @author SahniH
 * Date: Jan 7, 2005
 * 
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
	@SuppressWarnings("unchecked")
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
				if(resultsContainer != null &&resultsContainer instanceof DimensionalViewContainer){				
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer)resultsContainer;
						CopyNumberSingleViewResultsContainer copyNumberSingleViewContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
						GeneExprSingleViewResultsContainer geneExprSingleViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
						
						if(copyNumberSingleViewContainer!= null){
							Set<BioSpecimenIdentifierDE> biospecimenIDs =  copyNumberSingleViewContainer.getAllBiospecimenLabels();
				       		for (BioSpecimenIdentifierDE bioSpecimen: biospecimenIDs) {
				       			if(bioSpecimen.getSpecimenName()!= null){
				       				sampleIDs.add(new SampleIDDE(bioSpecimen.getSpecimenName()));
				   				}
				       		}
						}
						if(geneExprSingleViewContainer!= null){
							Set<BioSpecimenIdentifierDE> biospecimenIDs =  geneExprSingleViewContainer.getAllBiospecimenLabels();
				       		for (BioSpecimenIdentifierDE bioSpecimen: biospecimenIDs) {
				       			if(bioSpecimen.getSpecimenName()!= null){
				   					sampleIDs.add(new SampleIDDE(bioSpecimen.getSpecimenName()));
				   				}
				       		}
						}
				}
			   		sampleCrit = new SampleCriteria();
		   			sampleCrit.setSampleIDs(sampleIDs);

                AddConstrainsToQueriesHelper constrainedSamplesHandler= new AddConstrainsToQueriesHelper();
                constrainedSamplesHandler.constrainQueryWithSamples(newCompoundQuery,sampleCrit);
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
	                     		BioSpecimenIdentifierDE sampleId = (BioSpecimenIdentifierDE) sampleIdIterator.next();
	                     		String specimenName = sampleId.getSpecimenName();
	                       		if (groupResultset.getBioSpecimenResultset(specimenName) instanceof SampleFoldChangeValuesResultset){
		                       		SampleFoldChangeValuesResultset showAllBiospecimenResultset  = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(specimenName);
		                       		SampleFoldChangeValuesResultset sampleFoldChangeValuesResultset = (SampleFoldChangeValuesResultset) geneViewContainer.getBioSpecimentResultset(geneSymbol,reporterName, label, specimenName);
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
	                     		BioSpecimenIdentifierDE sampleId = (BioSpecimenIdentifierDE) sampleIdIterator.next();
	                     		String specimenName = sampleId.getSpecimenName();
		                       		if (groupResultset.getBioSpecimenResultset(specimenName) instanceof SampleCopyNumberValuesResultset){
		                       			SampleCopyNumberValuesResultset showAllBiospecimenResultset  = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(specimenName);
		                       			SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) copyNumberViewContainer.getBioSpecimentResultset(cytobandName,reporterName, label, specimenName);
	
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
