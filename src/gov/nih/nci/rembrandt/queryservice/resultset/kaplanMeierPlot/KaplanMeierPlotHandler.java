package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr.UnifiedGeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KaplanMeierPlotHandler {
	/**
	 * @param geneExpr
	 * @return KaplanMeierPlotContainer
	 * @throws Exception
	 */
	public static KaplanMeierPlotContainer handleKMGeneExprPlotContainer(GeneExpr.GeneExprSingle[] geneExprObjects) throws Exception{
 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
 		if(geneExprObjects != null  ){
 			for (int i = 0; i < geneExprObjects.length; i++) {
 				if(geneExprObjects[i] != null) {
 					ResultSet obj = geneExprObjects[i];
 					if (obj instanceof GeneExpr.GeneExprSingle)  {
 						//Propulate the KaplanMeierPlotContainer
 						GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
 						kaplanMeierPlotContainer = handleKMGeneExpPlotContainer(kaplanMeierPlotContainer,exprObj);
 					}
 				}
          	}//for
 			if(geneExprObjects.length > 1){
	 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(geneExprObjects[0].getGeneSymbol()));
	 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
	 			Map paitentDataLookup = LookupManager.getPatientDataMap();
		    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
		    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
		    		if(patient != null  && patient.getSurvivalLength() != null && patient.getCensoringStatus()!=null){
	
			    		sample.setSurvivalLength(patient.getSurvivalLength());
			    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
			    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
		    		}
		    	}	
 			}

 		}
        return kaplanMeierPlotContainer;

	}
	public static KaplanMeierPlotContainer handleKMCopyNumberPlotContainer(CopyNumber[] copyNumberObjects) throws Exception{
 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
 		if(copyNumberObjects != null){
 			for (int i = 0; i < copyNumberObjects.length; i++) {
 				if(copyNumberObjects[i] != null) {
 					ResultSet obj = copyNumberObjects[i];
 					if (obj instanceof CopyNumber)  {
 						//Propulate the KaplanMeierPlotContainer
 						CopyNumber  copyNumberObj = (CopyNumber) obj;
 						kaplanMeierPlotContainer = handleKMCopyNumberPlotContainer(kaplanMeierPlotContainer,copyNumberObj);
 					}
 				}
          	}//for
 			if(copyNumberObjects.length > 0){
	 	 		kaplanMeierPlotContainer.setCytobandDE(new CytobandDE(copyNumberObjects[0].getCytoband()));//TODO NEED GeneSymbol in CopyNumber
	 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
	 			Map paitentDataLookup = LookupManager.getPatientDataMap();
		    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
		    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
		    		if(patient != null){
			    		sample.setSurvivalLength(patient.getSurvivalLength());
			    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
			    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
		    		}
		    	}
 			}

 		}
 		
        return kaplanMeierPlotContainer;

	}
	/**
	 * @param kaplanMeierPlotContainer
	 * @param copyNumberObj
	 * @return
	 */
	private static KaplanMeierPlotContainer handleKMCopyNumberPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer, CopyNumber copyNumberObj) {
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && copyNumberObj != null){
      		kaplanMeierPlotContainer.setCytobandDE(new CytobandDE (copyNumberObj.getCytoband()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, copyNumberObj);
      		reporterResultset = handleCopyNumberReporterResultset(sampleResultset,copyNumberObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;
	}
	private static ReporterResultset handleCopyNumberReporterResultset(SampleKaplanMeierPlotResultset sampleResultset, CopyNumber copyNumberObj) {
        // find out if it has a probeset or a clone associated with it
        //populate ReporterResultset with the approciate one
        ReporterResultset reporterResultset = null;
        if(sampleResultset != null && copyNumberObj != null){
            if(copyNumberObj.getSnpProbesetName() != null ){
                DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,copyNumberObj.getSnpProbesetName());
                reporterResultset = sampleResultset.getReporterResultset(copyNumberObj.getSnpProbesetName().toString());
                if(reporterResultset == null){
                    reporterResultset = new ReporterResultset(reporter);                    
                    }   
            }
            reporterResultset.setValue(new DatumDE(DatumDE.COPY_NUMBER,copyNumberObj.getCopyNumber()));
            reporterResultset.setStartPhysicalLocation(new BasePairPositionDE.StartPosition(copyNumberObj.getPhysicalPosition()));
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
    /**
	 * @param sampleResultset
	 * @param copyNumberObj
	 * @return
	 */

	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return KaplanMeierPlotContainer
	 */
	private static KaplanMeierPlotContainer handleKMGeneExpPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer,GeneExpr.GeneExprSingle exprObj){
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && exprObj != null){
      		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, exprObj);
      		reporterResultset = handleReporterFoldChangeValuesResultset(sampleResultset,exprObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;
	}
	/**
	 * @param sampleResultset
	 * @param exprObj
	 * @return
	 */
	private static ReporterResultset handleReporterFoldChangeValuesResultset(SampleKaplanMeierPlotResultset sampleResultset, GeneExprSingle exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(sampleResultset != null && exprObj != null){
	    	if(exprObj.getProbesetName() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = sampleResultset.getReporterResultset(reporter.getValue().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}
	      		reporterResultset.setValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
	    		}	  		
		}
        return reporterResultset;
	}

	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return
	 */
	private static SampleKaplanMeierPlotResultset handleSampleKaplanMeierPlotResultset(KaplanMeierPlotContainer kaplanMeierPlotContainer, ClinicalResultSet clinicalObj) {
//		find out the sample associated with the exprObj
  		//populate the SampleKaplanMeierPlotResultset
		SampleKaplanMeierPlotResultset sampleResultset = null;
  		if(kaplanMeierPlotContainer != null && clinicalObj != null &&  clinicalObj.getSampleId() != null){
  			SampleIDDE sampleID = new SampleIDDE(clinicalObj.getSampleId());
  			sampleResultset = (SampleKaplanMeierPlotResultset) kaplanMeierPlotContainer.getSampleResultset(clinicalObj.getSampleId().toString());
  		    if (sampleResultset == null){
  		    	sampleResultset= new SampleKaplanMeierPlotResultset(sampleID);
  		    }
      	}
  		return sampleResultset;
	}
	public static ResultsContainer handleKMUnifiedGeneExprPlotContainer(UnifiedGeneExprSingle[] unifiedGeneExprObjects) throws Exception {
	 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
	 		if(unifiedGeneExprObjects != null  ){
	 			for (int i = 0; i < unifiedGeneExprObjects.length; i++) {
	 				if(unifiedGeneExprObjects[i] != null) {
	 					ResultSet obj = unifiedGeneExprObjects[i];
	 					if (obj instanceof UnifiedGeneExpr.UnifiedGeneExprSingle)  {
	 						//Propulate the KaplanMeierPlotContainer
	 						UnifiedGeneExpr.UnifiedGeneExprSingle  exprObj = (UnifiedGeneExpr.UnifiedGeneExprSingle) obj;
	 						kaplanMeierPlotContainer = handleKMUnifiedGeneExpPlotContainer(kaplanMeierPlotContainer,exprObj);
	 					}
	 				}
	          	}//for
	 			if(unifiedGeneExprObjects.length > 1){
		 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(unifiedGeneExprObjects[0].getGeneSymbol()));
		 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
		 			Map paitentDataLookup = LookupManager.getPatientDataMap();
			    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
			    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
			    		if(patient != null  && patient.getSurvivalLength() != null && patient.getCensoringStatus()!=null){
		
				    		sample.setSurvivalLength(patient.getSurvivalLength());
				    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
				    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
			    		}
			    	}	
	 			}

	 		}
	        return kaplanMeierPlotContainer;

		}
	private static KaplanMeierPlotContainer handleKMUnifiedGeneExpPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer, UnifiedGeneExprSingle exprObj) {
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && exprObj != null){
      		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, exprObj);
      		reporterResultset = handleUnifiedReporterFoldChangeValuesResultset(sampleResultset,exprObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;

	}
	private static ReporterResultset handleUnifiedReporterFoldChangeValuesResultset(SampleKaplanMeierPlotResultset sampleResultset, UnifiedGeneExprSingle exprObj) {
 		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(sampleResultset != null && exprObj != null){
	    	if(exprObj.getUnifiedGeneID() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,exprObj.getUnifiedGeneID());
	       		reporterResultset = sampleResultset.getReporterResultset(reporter.getValue().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}
	      		reporterResultset.setValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
	    		}	  		
		}
        return reporterResultset;
	}

}
