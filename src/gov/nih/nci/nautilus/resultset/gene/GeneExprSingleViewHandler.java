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
package gov.nih.nci.nautilus.resultset.gene;

import gov.nih.nci.nautilus.de.*;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.view.GroupType;

public class GeneExprSingleViewHandler extends GeneExprViewHandler{
		public GeneExprSingleViewResultsContainer handleGeneExprSingleView(GeneExprSingleViewResultsContainer geneViewContainer, GeneExpr.GeneExprSingle exprObj,GroupType groupType){
		GeneResultset geneResultset = null;
		ReporterResultset reporterResultset = null;
		SampleFoldChangeValuesResultset biospecimenResultset = null;
		DiseaseTypeResultset diseaseResultset = null;
		AgeGroupResultset ageGroupResultset = null;
		SurvivalRangeResultset survivalRangeResultset = null;
      	if (exprObj != null){
      		geneResultset = handleGeneResulset(geneViewContainer, exprObj);
      		biospecimenResultset = handleBioSpecimenResultset(exprObj);
      		reporterResultset = handleReporterResultset(geneResultset,exprObj);
      		if(groupType.getGroupType().equals(GroupType.DISEASE_TYPE_GROUP)){
      			diseaseResultset = handleDiseaseTypeResultset(reporterResultset,exprObj);
      			diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(diseaseResultset);
          		geneViewContainer.addBiospecimensToGroups(exprObj.getDiseaseType(),exprObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.AGE_GROUP)){
      			ageGroupResultset = handleAgeGroupResultset(reporterResultset,exprObj);
      			ageGroupResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(ageGroupResultset);
          		geneViewContainer.addBiospecimensToGroups(exprObj.getAgeGroup(),exprObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.SURVIVAL_RANGE_GROUP)){
      			survivalRangeResultset = handleSurvivalRangeResultset(reporterResultset,exprObj);
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

    private SampleFoldChangeValuesResultset handleBioSpecimenResultset(GeneExpr.GeneExprSingle exprObj){
		//find out the biospecimenID associated with the GeneExpr.GeneExprSingle
		//populate the BiospecimenResuluset
		SampleFoldChangeValuesResultset sampleFoldChangeValuesResultset = new SampleFoldChangeValuesResultset();
		BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(exprObj.getSampleId().toString());
		sampleFoldChangeValuesResultset.setBiospecimen(biospecimenID);
		sampleFoldChangeValuesResultset.setFoldChangeRatioValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
		sampleFoldChangeValuesResultset.setFoldChangeSampleIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getSampleIntensity()));
		sampleFoldChangeValuesResultset.setFoldChangeNormalIntensity(new DatumDE(DatumDE.FOLD_CHANGE_NORMAL_INTENSITY,exprObj.getNormalIntensity()));
		sampleFoldChangeValuesResultset.setAgeGroup(new DatumDE(DatumDE.AGE_GROUP,exprObj.getAgeGroup()));
		sampleFoldChangeValuesResultset.setSurvivalLengthRange(new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,exprObj.getSurvivalLengthRange()));
		sampleFoldChangeValuesResultset.setGenderCode(new GenderDE(exprObj.getGenderCode()));
  		return sampleFoldChangeValuesResultset;
    }

    private AgeGroupResultset handleAgeGroupResultset(ReporterResultset reporterResultset, GeneExpr.GeneExprSingle exprObj){
  		//find out the age group associated with the exprObj
  		//populate the AgeGroupResultset
		AgeGroupResultset ageGroupResultset = null;
  		if(reporterResultset != null && exprObj != null &&  exprObj.getAgeGroup() != null){
  			DatumDE ageGroup = new DatumDE(DatumDE.AGE_GROUP,exprObj.getAgeGroup().toString());
  			ageGroupResultset = (AgeGroupResultset) reporterResultset.getGroupByResultset(exprObj.getAgeGroup().toString());
  		    if (ageGroupResultset == null){
  		    	ageGroupResultset= new AgeGroupResultset(ageGroup);
	      		}
      	}
  		return ageGroupResultset;
    }
    private SurvivalRangeResultset handleSurvivalRangeResultset(ReporterResultset reporterResultset, GeneExpr.GeneExprSingle exprObj){
  		//find out the age group associated with the exprObj
  		//populate the SurvivalRangeResultset
		SurvivalRangeResultset survivalRangeResultset = null;
  		if(reporterResultset != null && exprObj != null &&  exprObj.getSurvivalLengthRange() != null){
  			DatumDE survivalRange = new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,exprObj.getSurvivalLengthRange().toString());
  			survivalRangeResultset = (SurvivalRangeResultset) reporterResultset.getGroupByResultset(exprObj.getSurvivalLengthRange().toString());
  		    if (survivalRangeResultset == null){
  		    	survivalRangeResultset= new SurvivalRangeResultset(survivalRange);
	      		}
      	}
  		return survivalRangeResultset;
    }
    private DiseaseTypeResultset handleDiseaseTypeResultset(ReporterResultset reporterResultset, GeneExpr.GeneExprSingle exprObj){
  		//find out the disease type associated with the exprObj
  		//populate the DiseaseTypeResultset
		DiseaseTypeResultset diseaseResultset = null;
  		if(reporterResultset != null && exprObj != null &&  exprObj.getDiseaseType() != null){
  			DiseaseNameDE disease = new DiseaseNameDE(exprObj.getDiseaseType().toString());
  			diseaseResultset = (DiseaseTypeResultset) reporterResultset.getGroupByResultset(exprObj.getDiseaseType().toString());
  		    if (diseaseResultset == null){
  		    	diseaseResultset= new DiseaseTypeResultset(disease);
	      		}
      	}
  		return diseaseResultset;
    }

}
