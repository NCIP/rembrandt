/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;

import gov.nih.nci.nautilus.de.BioSpecimenIdentifierDE;
import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.de.GenderDE;
import gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.nautilus.resultset.ViewByGroupResultsetHandler;
import gov.nih.nci.nautilus.resultset.gene.AgeGroupResultset;
import gov.nih.nci.nautilus.resultset.gene.DiseaseTypeResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.SurvivalRangeResultset;
import gov.nih.nci.nautilus.view.GroupType;



/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyNumberSingleViewHandler extends CopyNumberViewHandler{
	public static CopyNumberSingleViewResultsContainer handleCopyNumberSingleView(CopyNumberSingleViewResultsContainer copyNumberContainer, CopyNumber copyNumberObj,GroupType groupType){
		CytobandResultset cytobandResultset = null;
		ReporterResultset reporterResultset = null;
		SampleCopyNumberValuesResultset biospecimenResultset = null;
		DiseaseTypeResultset diseaseResultset = null;
		AgeGroupResultset ageGroupResultset = null;
		SurvivalRangeResultset survivalRangeResultset = null;
      	if (copyNumberObj != null){
      		cytobandResultset = handleCytobandResulset(copyNumberContainer, copyNumberObj);
      		biospecimenResultset = handleCopyNumberChangeValuesResultset(copyNumberObj);
      		reporterResultset = handleReporterResultset(cytobandResultset,copyNumberObj);
      		if(groupType.getGroupType().equals(GroupType.DISEASE_TYPE_GROUP)){
      			diseaseResultset = ViewByGroupResultsetHandler.handleDiseaseTypeResultset(reporterResultset,copyNumberObj);
      			diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(diseaseResultset);
      			copyNumberContainer.addBiospecimensToGroups(copyNumberObj.getDiseaseType(),copyNumberObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.AGE_GROUP)){
      			ageGroupResultset = ViewByGroupResultsetHandler.handleAgeGroupResultset(reporterResultset,copyNumberObj);
      			ageGroupResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(ageGroupResultset);
      			copyNumberContainer.addBiospecimensToGroups(copyNumberObj.getAgeGroup(),copyNumberObj.getSampleId().toString());
      		}
      		else if(groupType.getGroupType().equals(GroupType.SURVIVAL_RANGE_GROUP)){
      			survivalRangeResultset = ViewByGroupResultsetHandler.handleSurvivalRangeResultset(reporterResultset,copyNumberObj);
      			survivalRangeResultset.addBioSpecimenResultset(biospecimenResultset);
      			reporterResultset.addGroupByResultset(survivalRangeResultset);
      			copyNumberContainer.addBiospecimensToGroups(copyNumberObj.getSurvivalLengthRange(),copyNumberObj.getSampleId().toString());
      		}
      		
      		cytobandResultset.addReporterResultset(reporterResultset);
      		//add the reporter to geneResultset
      		copyNumberContainer.addCytobandResultset(cytobandResultset);
      	}
      	return copyNumberContainer;
    }

    private static SampleCopyNumberValuesResultset handleCopyNumberChangeValuesResultset(CopyNumber copyNumberObj){
		//find out the biospecimenID associated with the GeneExpr.GeneExprSingle
		//populate the BiospecimenResuluset
    	SampleCopyNumberValuesResultset sampleCopyNumberValuesResultset = new SampleCopyNumberValuesResultset();
		BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(copyNumberObj.getSampleId().toString());
		sampleCopyNumberValuesResultset.setBiospecimen(biospecimenID);
		sampleCopyNumberValuesResultset.setCopyNumber(new DatumDE(DatumDE.COPY_NUMBER,copyNumberObj.getCopyNumber()));
		sampleCopyNumberValuesResultset.setChannelRatioValue(new DatumDE(DatumDE.COPY_NUMBER_CHANNEL_RATIO,copyNumberObj.getChannelRatio()));
		sampleCopyNumberValuesResultset.setCopyNumberPvalue(new DatumDE(DatumDE.COPY_NUMBER_RATIO_PVAL,copyNumberObj.getCopynoPval()));
		sampleCopyNumberValuesResultset.setLOH(new DatumDE(DatumDE.COPY_NUMBER_LOH,copyNumberObj.getLoh()));		
		sampleCopyNumberValuesResultset.setAgeGroup(new DatumDE(DatumDE.AGE_GROUP,copyNumberObj.getAgeGroup()));
		sampleCopyNumberValuesResultset.setSurvivalLengthRange(new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,copyNumberObj.getSurvivalLengthRange()));
		sampleCopyNumberValuesResultset.setGenderCode(new GenderDE(copyNumberObj.getGenderCode()));
  		return sampleCopyNumberValuesResultset;
    }


}
