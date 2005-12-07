/*
 *  @author: SahniH
 *  Created on Oct 11, 2004
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
package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.view.GroupType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.resultset.ViewByGroupResultsetHandler;

public class GeneExprSingleViewHandler extends GeneExprViewHandler{
		public static GeneExprSingleViewResultsContainer handleGeneExprSingleView(GeneExprSingleViewResultsContainer geneViewContainer, GeneExpr.GeneExprSingle exprObj,GroupType groupType){
		GeneResultset geneResultset = null;
		ReporterResultset reporterResultset = null;
		SampleFoldChangeValuesResultset biospecimenResultset = null;
		DiseaseTypeResultset diseaseResultset = null;
		AgeGroupResultset ageGroupResultset = null;
		SurvivalRangeResultset survivalRangeResultset = null;
      	if (exprObj != null){
      		geneResultset = handleGeneResulset(geneViewContainer, exprObj);
      		biospecimenResultset = handleSampleFoldChangeValuesResultset(exprObj);
      		reporterResultset = handleReporterResultset(geneResultset,exprObj);
      		if(groupType.getGroupType().equals(GroupType.DISEASE_TYPE_GROUP)){
      			diseaseResultset = ViewByGroupResultsetHandler.handleDiseaseTypeResultset(reporterResultset,exprObj);
      			diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(diseaseResultset);
          		geneViewContainer.addBiospecimensToGroups(exprObj.getDiseaseType(),exprObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.AGE_GROUP)){
      			ageGroupResultset = ViewByGroupResultsetHandler.handleAgeGroupResultset(reporterResultset,exprObj);
      			ageGroupResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(ageGroupResultset);
          		geneViewContainer.addBiospecimensToGroups(exprObj.getAgeGroup(),exprObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.SURVIVAL_RANGE_GROUP)){
      			survivalRangeResultset = ViewByGroupResultsetHandler.handleSurvivalRangeResultset(reporterResultset,exprObj);
      			survivalRangeResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(survivalRangeResultset);
          		geneViewContainer.addBiospecimensToGroups(exprObj.getSurvivalLengthRange(),exprObj.getSampleId().toString());
      		}
      		
      		geneResultset.addReporterResultset(reporterResultset);
      		//add the reporter to geneResultset
      		geneViewContainer.addGeneResultset(geneResultset);
      	}
      	return geneViewContainer;
    }

    private static SampleFoldChangeValuesResultset handleSampleFoldChangeValuesResultset(GeneExpr.GeneExprSingle exprObj){
		//find out the biospecimenID associated with the GeneExpr.GeneExprSingle
		//populate the BiospecimenResuluset
		SampleFoldChangeValuesResultset sampleFoldChangeValuesResultset = new SampleFoldChangeValuesResultset();
		sampleFoldChangeValuesResultset.setSampleIDDE(new SampleIDDE(exprObj.getSampleId()));
		sampleFoldChangeValuesResultset.setBiospecimen(new BioSpecimenIdentifierDE(exprObj.getBiospecimenId()));
		sampleFoldChangeValuesResultset.setFoldChangeRatioValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
		sampleFoldChangeValuesResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getSampleIntensity()));
		sampleFoldChangeValuesResultset.setFoldChangeNonTumorIntensity(new DatumDE(DatumDE.FOLD_CHANGE_NORMAL_INTENSITY,exprObj.getNormalIntensity()));
		sampleFoldChangeValuesResultset.setAgeGroup(new DatumDE(DatumDE.AGE_GROUP,exprObj.getAgeGroup()));
		sampleFoldChangeValuesResultset.setSurvivalLengthRange(new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,exprObj.getSurvivalLengthRange()));
		sampleFoldChangeValuesResultset.setGenderCode(new GenderDE(exprObj.getGenderCode()));
		
  		return sampleFoldChangeValuesResultset;
    }



}
