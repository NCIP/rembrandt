package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
 */
public class GeneExprDiseaseGroupViewHandler extends GeneExprViewHandler {
	public static GeneExprResultsContainer handleGeneExprDiseaseView(GeneExprResultsContainer geneViewContainer, GeneExpr.GeneExprGroup exprObj){
		GeneResultset geneResultset = null;
		ReporterResultset reporterResultset = null;
		DiseaseGroupResultset diseaseResultset = null;
      	if (exprObj != null){
      		geneResultset = handleGeneResulset(geneViewContainer, exprObj);
      		reporterResultset = handleReporterResultset(geneResultset,exprObj);
   			diseaseResultset = handleDiseaseGroupResultset(reporterResultset,exprObj);
   			reporterResultset.addGroupByResultset(diseaseResultset);
      		geneResultset.addReporterResultset(reporterResultset);
     		geneViewContainer.addDiseaseTypes(exprObj.getDiseaseType());
     		 
      		//add the reporter to geneResultset
      		geneViewContainer.addGeneResultset(geneResultset);
      	}
      	return geneViewContainer;
    }
	protected static DiseaseGroupResultset handleDiseaseGroupResultset(ReporterResultset reporterResultset, GeneExpr.GeneExprGroup exprObj){
  		//find out the disease type associated with the exprObj
  		//populate the DiseaseTypeResultset
		DiseaseGroupResultset diseaseGroupResultset = null;
  		if(reporterResultset != null && exprObj != null &&  exprObj.getDiseaseType() != null){
  			DiseaseNameDE disease = new DiseaseNameDE(exprObj.getDiseaseType().toString());
  			diseaseGroupResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(exprObj.getDiseaseType().toString());
  		    if (diseaseGroupResultset == null){
  		    	diseaseGroupResultset= new DiseaseGroupResultset(disease);
	      		}
  		    diseaseGroupResultset.setDiseaseType(disease);
  		    diseaseGroupResultset.setFoldChangeRatioValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
  		    diseaseGroupResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,exprObj.getRatioPval()));
  		    diseaseGroupResultset.setFoldChangeNonTumorIntensity(new DatumDE(DatumDE.FOLD_CHANGE_NORMAL_INTENSITY,exprObj.getNormalIntensity()));
  		    diseaseGroupResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getSampleIntensity()));

      	}
  		return diseaseGroupResultset;
    }

}
