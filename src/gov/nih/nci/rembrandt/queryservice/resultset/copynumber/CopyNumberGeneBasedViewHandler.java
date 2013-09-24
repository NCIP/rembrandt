/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset.copynumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.view.GroupType;
import gov.nih.nci.caintegrator.util.MathUtil;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExprSingleInterface;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr.UnifiedGeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.resultset.ViewByGroupResultsetHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.AgeGroupResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.DiseaseTypeResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprViewHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SurvivalRangeResultset;
import gov.nih.nci.rembrandt.util.RembrandtConstants;



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

public class CopyNumberGeneBasedViewHandler extends GeneExprViewHandler{
		public static CopyNumberGeneViewResultsContainer handleCopyNumberGeneBasedView(CopyNumberGeneViewResultsContainer geneViewContainer, CopyNumber copyNumberObj,GroupType groupType){
		GeneResultset geneResultset = null;
		//ReporterResultset reporterResultset = null;
		SampleCopyNumberValuesResultset biospecimenResultset = null;
		DiseaseTypeResultset diseaseResultset = null;
      	if (copyNumberObj != null){
      		geneResultset = handleGeneResulset(geneViewContainer, copyNumberObj.getGeneSymbol());
      		biospecimenResultset = handleCopyNumberChangeValuesResultset(copyNumberObj);
      		//if(copyNumberObj instanceof CopyNumber){
      		//	reporterResultset = handleReporterResultset(geneResultset,copyNumberObj);
      		//} 
      		if(groupType.getGroupType().equals(GroupType.DISEASE_TYPE_GROUP)){
      			diseaseResultset = ViewByGroupResultsetHandler.handleDiseaseTypeResultset(geneResultset,copyNumberObj);

      			if(diseaseResultset != null){
      				diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
      			}
      			geneResultset.addGroupByResultset(diseaseResultset);
          		geneViewContainer.addBiospecimensToGroups(copyNumberObj.getDiseaseType(),biospecimenResultset.getBiospecimen());
      		}
      		
      		//geneResultset.addReporterResultset(reporterResultset);
      		//add the reporter to geneResultset
      		geneViewContainer.addGeneResultset(geneResultset);
      	}
      	return geneViewContainer;
    }









	private static GeneResultset handleGeneResulset(
				CopyNumberGeneBasedResultsContainer1 geneViewContainer,
				String geneSymbol) {
 		//get the gene accessesion number for this record
  		//check if the gene exsists in the GeneExprSingleViewResultsContainer, otherwise add a new one.
		GeneResultset geneResultset = geneViewContainer.getGeneResultset(geneSymbol);
  		if(geneResultset == null){ // no record found
  			geneResultset = new GeneResultset();
  		}
  		if(geneSymbol!= null){
  		geneResultset.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(geneSymbol));
 		}
  		else{
  			geneResultset.setAnonymousGene();
  		}
  		return geneResultset;

    }


	protected static ReporterResultset handleReporterResultset(GeneResultset geneViewContainer,CopyNumber copyNumberObj){
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(geneViewContainer != null && copyNumberObj != null){
	  			DatumDE reporter = new DatumDE(DatumDE.COPY_NUMBER,geneViewContainer.getGeneSymbol());
	       		reporterResultset = geneViewContainer.getRepoterResultset(geneViewContainer.getGeneSymbol().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);                    
	      			}  	
	      	Long start =  new Long(0);
	      	Long end = new Long(0);
	      	if(reporterResultset.getStartPhysicalLocation()!= null ){
	      		start = new Long (reporterResultset.getStartPhysicalLocation().toString());
	      	}
	      	if(reporterResultset.getEndPhysicalLocation()!= null ){
	      		end = new Long (reporterResultset.getEndPhysicalLocation().toString());
	      	}
	      	if(copyNumberObj.getChromosomeStart() != null && copyNumberObj.getChromosomeStart()<= start){
            reporterResultset.setStartPhysicalLocation(new BasePairPositionDE.StartPosition(copyNumberObj.getChromosomeStart()));
	      	}
	      	if(copyNumberObj.getChromosomeEnd()!= null && copyNumberObj.getChromosomeEnd()>= end){
            reporterResultset.setEndPhysicalLocation(new BasePairPositionDE.EndPosition(copyNumberObj.getChromosomeEnd()));
	      	}
	      	if(copyNumberObj.getChromosome()!= null ){
	    	  	reporterResultset.setChromosomeNumber(new ChromosomeNumberDE(copyNumberObj.getChromosome()));		      	
			}
	  		if(copyNumberObj.getAnnotations() != null){
	  			CopyNumber.SNPAnnotation annotation = copyNumberObj.getAnnotations();
                if(annotation.getAccessionNumbers()!= null){
                    reporterResultset.setAssiciatedGenBankAccessionNos(annotation.getAccessionNumbers());
                }
                if(annotation.getLocusLinkIDs()!=null){
                    reporterResultset.setAssiciatedLocusLinkIDs(copyNumberObj.getAnnotations().getLocusLinkIDs());
                }
                if(annotation.getGeneSymbols()!=null){
                    reporterResultset.setAssiciatedGeneSymbols(copyNumberObj.getAnnotations().getGeneSymbols());
                }
	  			
	  		}
	    	
		}
        return reporterResultset;
	}

        private static SampleCopyNumberValuesResultset handleCopyNumberChangeValuesResultset(CopyNumber copyNumberObj){
    		//find out the biospecimenID associated with the GeneExpr.GeneExprSingle
    		//populate the BiospecimenResuluset
        	SampleCopyNumberValuesResultset sampleCopyNumberValuesResultset = new SampleCopyNumberValuesResultset();
    		BioSpecimenIdentifierDE bioSpecimenIdentifierDE = new BioSpecimenIdentifierDE(copyNumberObj.getBiospecimenId());
    		bioSpecimenIdentifierDE.setSampleId(copyNumberObj.getSampleId());
    		bioSpecimenIdentifierDE.setSpecimenName(copyNumberObj.getSpecimenName());
    		sampleCopyNumberValuesResultset.setBiospecimen(bioSpecimenIdentifierDE);

    		sampleCopyNumberValuesResultset.setSampleIDDE(new SampleIDDE(copyNumberObj.getSampleId()));
    		sampleCopyNumberValuesResultset.setChr(copyNumberObj.getChromosome());
    		sampleCopyNumberValuesResultset.setLocStart(copyNumberObj.getChromosomeStart().toString());
    		sampleCopyNumberValuesResultset.setLocEnd(copyNumberObj.getChromosomeEnd().toString());
    		sampleCopyNumberValuesResultset.setCopyNumber(new DatumDE(DatumDE.COPY_NUMBER,copyNumberObj.getCalculatedCopyNumber()));
    		sampleCopyNumberValuesResultset.setSegmentMean(new DatumDE(DatumDE.COPY_NUMBER,copyNumberObj.getSegmentMean()));

    		//sampleCopyNumberValuesResultset.setChannelRatioValue(new DatumDE(DatumDE.COPY_NUMBER_CHANNEL_RATIO,copyNumberObj.getChannelRatio()));
    		//sampleCopyNumberValuesResultset.setCopyNumberPvalue(new DatumDE(DatumDE.COPY_NUMBER_RATIO_PVAL,copyNumberObj.getCopynoPval()));
    		//sampleCopyNumberValuesResultset.setLOH(new DatumDE(DatumDE.COPY_NUMBER_LOH,copyNumberObj.getLoh()));		
    		//sampleCopyNumberValuesResultset.setAgeGroup(new DatumDE(DatumDE.AGE_GROUP,copyNumberObj.getAgeGroup()));
    		//sampleCopyNumberValuesResultset.setSurvivalLengthRange(new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,copyNumberObj.getSurvivalLengthRange()));
    		//sampleCopyNumberValuesResultset.setGenderCode(new GenderDE(copyNumberObj.getGenderCode()));
    		
      	
      		return sampleCopyNumberValuesResultset;
        }

}
