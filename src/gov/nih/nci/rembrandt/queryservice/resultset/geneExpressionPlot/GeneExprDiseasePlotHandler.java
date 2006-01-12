package gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprGroup;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr.UnifiedGeneExprGroup;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public class GeneExprDiseasePlotHandler {
	/* This  handler assembls a GeneExprPlot resultset from  disease grouped resultset
	 * 
	 */
	public static GeneExprDiseasePlotContainer handleGeneExprDiseaseView(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer, GeneExpr.GeneExprGroup exprObj) throws Exception{
		DiseaseGeneExprPlotResultset diseaseResultset = null;
		ReporterFoldChangeValuesResultset reporterResultset = null;
      	if (geneExprDiseasePlotContainer != null && exprObj != null){
      		geneExprDiseasePlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		//geneExprDiseasePlotContainer = handleDiseaseGeneExprPlotResultset(geneExprDiseasePlotContainer, exprObj);
      		diseaseResultset = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(exprObj.getDiseaseType());
      		reporterResultset = handleReporterFoldChangeValuesResultset(diseaseResultset,exprObj);
   			diseaseResultset.addReporterFoldChangeValuesResultset(reporterResultset);
      		geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);
      	}
      	return geneExprDiseasePlotContainer;

	}
	/* This  handler assembls a GeneExprPlot resultset from unified disease grouped resultset
	 * 
	 */
	public static GeneExprDiseasePlotContainer handleUnifiedGeneExprDiseaseView(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer, UnifiedGeneExprGroup exprObj) throws Exception{
		DiseaseGeneExprPlotResultset diseaseResultset = null;
		ReporterFoldChangeValuesResultset reporterResultset = null;
      	if (geneExprDiseasePlotContainer != null && exprObj != null){
      		geneExprDiseasePlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		//geneExprDiseasePlotContainer = handleDiseaseGeneExprPlotResultset(geneExprDiseasePlotContainer, exprObj);
      		diseaseResultset = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(exprObj.getDiseaseType());
      		reporterResultset = handleReporterFoldChangeValuesResultset(diseaseResultset,exprObj);
   			diseaseResultset.addReporterFoldChangeValuesResultset(reporterResultset);
      		geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);
      	}
      	return geneExprDiseasePlotContainer;

	}

	/**
	 * @param diseaseResultset
	 * @param exprObj
	 * @return
	 */
	private static ReporterFoldChangeValuesResultset handleReporterFoldChangeValuesResultset(DiseaseGeneExprPlotResultset diseaseResultset, GeneExprGroup exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterFoldChangeValuesResultset reporterResultset = null;
		if(diseaseResultset != null && exprObj != null){
			//TODO:only Affy Probesets for now
	    	if(exprObj.getProbesetName() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = diseaseResultset.getReporterFoldChangeValuesResultset(exprObj.getProbesetName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
	      			}
	      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,exprObj.getRatioPval()));
	      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getSampleIntensity()));
	      		reporterResultset.setStandardDeviationRatio(new DatumDE(DatumDE.STD_DEVIATION_RATIO,exprObj.getStandardDeviationRatio()));
	    		}
	  		
		}
        return reporterResultset;
	}
	/**
	 * @param diseaseResultset
	 * @param exprObj
	 * @return
	 */
	private static ReporterFoldChangeValuesResultset handleReporterFoldChangeValuesResultset(DiseaseGeneExprPlotResultset diseaseResultset, UnifiedGeneExprGroup exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterFoldChangeValuesResultset reporterResultset = null;
		if(diseaseResultset != null && exprObj != null){
			//TODO:only Affy Probesets for now
	    	if(exprObj.getUnifiedGeneID() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,exprObj.getUnifiedGeneID());
	       		reporterResultset = diseaseResultset.getReporterFoldChangeValuesResultset(exprObj.getUnifiedGeneID());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
	      			}
	      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,exprObj.getExpressionRatio()));
	      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getSampleIntensity()));
	      		reporterResultset.setStandardDeviationRatio(new DatumDE(DatumDE.STD_DEVIATION_RATIO,exprObj.getStandardDeviation()));
	    		}
	  		
		}
        return reporterResultset;
	}
	/**
	 * @param diseaseResultset
	 * @param exprObj
	 * @return
	 */
	public static GeneExprDiseasePlotContainer handleNoramlAsDisease(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer, GeneExprGroup exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterFoldChangeValuesResultset reporterResultset = null;
		DiseaseGeneExprPlotResultset non_tumor = null;
		if(geneExprDiseasePlotContainer != null && exprObj != null){
			//TODO:only Affy Probesets for now
	    	if(exprObj.getProbesetName() != null){
	    		non_tumor = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(RembrandtConstants.NON_TUMOR);
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = non_tumor.getReporterFoldChangeValuesResultset(exprObj.getProbesetName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
	      			}
	      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,new Double("0.00")));//TODO: Should be changed to repecial value
	      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getNormalIntensity()));
	    		}
   			geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(non_tumor);     		
   			non_tumor.addReporterFoldChangeValuesResultset(reporterResultset);
	  		
		}
        return geneExprDiseasePlotContainer;
	}
	/**
	 * @param geneExprDiseasePlotContainer
	 * @param exprObj
	 * @return
	 * @throws Exception
	 */
	public static GeneExprDiseasePlotContainer handleDiseaseGeneExprPlotResultset(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer) throws Exception {
		//find out the disease type associated with the exprObj
  		//populate the DiseaseTypeResultset
		DiseaseGeneExprPlotResultset diseaseResultset = null;
		DiseaseTypeLookup[] diseaseTypes = LookupManager.getDiseaseType();
		for(int i = 0; i< diseaseTypes.length ; i++){
			DiseaseNameDE disease = new DiseaseNameDE(diseaseTypes[i].getDiseaseType().toString());
		    diseaseResultset= new DiseaseGeneExprPlotResultset(disease);
		    geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(diseaseResultset);
		}
  		return geneExprDiseasePlotContainer;
	}
	public static GeneExprDiseasePlotContainer handleNoramlAsDisease(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer, UnifiedGeneExprGroup exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterFoldChangeValuesResultset reporterResultset = null;
		DiseaseGeneExprPlotResultset non_tumor = null;
		if(geneExprDiseasePlotContainer != null && exprObj != null){
			//TODO:only Affy Probesets for now
	    	if(exprObj.getUnifiedGeneID() != null){
	    		non_tumor = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(RembrandtConstants.NON_TUMOR);
	  			DatumDE reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,exprObj.getUnifiedGeneID());
	       		reporterResultset = non_tumor.getReporterFoldChangeValuesResultset(exprObj.getUnifiedGeneID().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
	      			}
	      		reporterResultset.setRatioPval(new DatumDE(DatumDE.FOLD_CHANGE_RATIO_PVAL,new Double("0.00")));//TODO: Should be changed to repecial value
	      		reporterResultset.setFoldChangeIntensity(new DatumDE(DatumDE.FOLD_CHANGE_SAMPLE_INTENSITY,exprObj.getNormalIntensity()));
	    		}
   			geneExprDiseasePlotContainer.addDiseaseGeneExprPlotResultset(non_tumor);     		
   			non_tumor.addReporterFoldChangeValuesResultset(reporterResultset);
	  		
		}
        return geneExprDiseasePlotContainer;
	}

}
