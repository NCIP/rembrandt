/*
 *  @author: SahniH
 *  Created on Nov 9, 2004
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

}
