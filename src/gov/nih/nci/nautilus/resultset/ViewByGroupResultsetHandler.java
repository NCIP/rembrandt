/*
 * Created on Nov 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;

import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.resultset.gene.AgeGroupResultset;
import gov.nih.nci.nautilus.resultset.gene.DiseaseTypeResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.SurvivalRangeResultset;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ViewByGroupResultsetHandler {
    public static AgeGroupResultset handleAgeGroupResultset(ReporterResultset reporterResultset, ClinicalResultSet resultObj){
  		//find out the age group associated with the exprObj
  		//populate the AgeGroupResultset
		AgeGroupResultset ageGroupResultset = null;
  		if(reporterResultset != null && resultObj != null &&  resultObj.getAgeGroup() != null){
  			DatumDE ageGroup = new DatumDE(DatumDE.AGE_GROUP,resultObj.getAgeGroup().toString());
  			ageGroupResultset = (AgeGroupResultset) reporterResultset.getGroupByResultset(resultObj.getAgeGroup().toString());
  		    if (ageGroupResultset == null){
  		    	ageGroupResultset= new AgeGroupResultset(ageGroup);
	      		}
      	}
  		return ageGroupResultset;
    }
    public static SurvivalRangeResultset handleSurvivalRangeResultset(ReporterResultset reporterResultset, ClinicalResultSet resultObj){
  		//find out the age group associated with the exprObj
  		//populate the SurvivalRangeResultset
		SurvivalRangeResultset survivalRangeResultset = null;
  		if(reporterResultset != null && resultObj != null &&  resultObj.getSurvivalLengthRange() != null){
  			DatumDE survivalRange = new DatumDE(DatumDE.SURVIVAL_LENGTH_RANGE,resultObj.getSurvivalLengthRange().toString());
  			survivalRangeResultset = (SurvivalRangeResultset) reporterResultset.getGroupByResultset(resultObj.getSurvivalLengthRange().toString());
  		    if (survivalRangeResultset == null){
  		    	survivalRangeResultset= new SurvivalRangeResultset(survivalRange);
	      		}
      	}
  		return survivalRangeResultset;
    }
    public static DiseaseTypeResultset handleDiseaseTypeResultset(ReporterResultset reporterResultset, ClinicalResultSet resultObj){
  		//find out the disease type associated with the resultObj
  		//populate the DiseaseTypeResultset
		DiseaseTypeResultset diseaseResultset = null;
  		if(reporterResultset != null && resultObj != null &&  resultObj.getDiseaseType() != null){
  			DiseaseNameDE disease = new DiseaseNameDE(resultObj.getDiseaseType().toString());
  			diseaseResultset = (DiseaseTypeResultset) reporterResultset.getGroupByResultset(resultObj.getDiseaseType().toString());
  		    if (diseaseResultset == null){
  		    	diseaseResultset= new DiseaseTypeResultset(disease);
	      		}
      	}
  		return diseaseResultset;
    }
}
