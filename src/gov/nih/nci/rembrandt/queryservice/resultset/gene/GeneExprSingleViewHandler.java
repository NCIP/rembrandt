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
