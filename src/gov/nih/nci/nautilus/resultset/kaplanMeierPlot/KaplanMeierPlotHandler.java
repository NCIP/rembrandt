/*
 * @author: SahniH
 * Created on Nov 11, 2004
 * @version $ Revision: 1.0 $
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
package gov.nih.nci.nautilus.resultset.kaplanMeierPlot;

import gov.nih.nci.nautilus.de.BioSpecimenIdentifierDE;
import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.lookup.PatientDataLookup;
import gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;

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
 		if(geneExprObjects != null){
 			for (int i = 0; i < geneExprObjects.length; i++) {
 				if(geneExprObjects[i] != null) {
 					ResultSet obj = geneExprObjects[i];
 					if (obj instanceof GeneExpr.GeneExprSingle)  {
 						//Propulate the KaplanMeierPlotContainer
 						GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
 						kaplanMeierPlotContainer = handleKaplanMeierPlotContainer(kaplanMeierPlotContainer,exprObj);
 					}
 				}
          	}//for
 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(geneExprObjects[0].getGeneSymbol()));
 			Collection samples = kaplanMeierPlotContainer.getBioSpecimenResultsets();
 			Map paitentDataLookup = LookupManager.getPatientDataMap();
	    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
	    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
	    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getBiospecimen().getValue().toString());
	    		if(patient != null){
		    		sample.setSurvivalLength(new DatumDE(DatumDE.SURVIVAL_LENGTH,patient.getSurvivalLength()));
		    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
		    		kaplanMeierPlotContainer.addBioSpecimenResultset(sample);  //update sample resultset
	    		}
	    	}

 		}
        return kaplanMeierPlotContainer;

	}
	public static KaplanMeierPlotContainer handleKMCopyNumberPlotContainer(CopyNumber[] copyNumberObjects) throws Exception{
 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
/* 		if(copyNumberObjects != null){
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
 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(copyNumberObjects[0].()));//TODO NEED GeneSymbol in CopyNumber
 			Collection samples = kaplanMeierPlotContainer.getBioSpecimenResultsets();
 			Map paitentDataLookup = LookupManager.getPatientDataMap();
	    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
	    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
	    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getBiospecimen().getValue().toString());
	    		if(patient != null){
		    		sample.setSurvivalLength(new DatumDE(DatumDE.SURVIVAL_LENGTH,patient.getSurvivalLength()));
		    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
		    		kaplanMeierPlotContainer.addBioSpecimenResultset(sample);  //update sample resultset
	    		}
	    	}

 		}
 		*/
        return kaplanMeierPlotContainer;

	}
	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return KaplanMeierPlotContainer
	 */
	private static KaplanMeierPlotContainer handleKaplanMeierPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer,GeneExpr.GeneExprSingle exprObj){
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterFoldChangeValuesResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && exprObj != null){
      		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, exprObj);
      		reporterResultset = handleReporterFoldChangeValuesResultset(sampleResultset,exprObj);
      		sampleResultset.addReporterFoldChangeValuesResultset(reporterResultset);
      		kaplanMeierPlotContainer.addBioSpecimenResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;
	}
	/**
	 * @param sampleResultset
	 * @param exprObj
	 * @return
	 */
	private static ReporterFoldChangeValuesResultset handleReporterFoldChangeValuesResultset(SampleKaplanMeierPlotResultset sampleResultset, GeneExprSingle exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterFoldChangeValuesResultset reporterResultset = null;
		if(sampleResultset != null && exprObj != null){
	    	if(exprObj.getProbesetName() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = sampleResultset.getReporterFoldChangeValuesResultset(exprObj.getProbesetName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterFoldChangeValuesResultset(reporter);
	      			}
	      		reporterResultset.setFoldChangeRatioValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
	    		}	  		
		}
        return reporterResultset;
	}

	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return
	 */
	private static SampleKaplanMeierPlotResultset handleSampleKaplanMeierPlotResultset(KaplanMeierPlotContainer kaplanMeierPlotContainer, GeneExprSingle exprObj) {
//		find out the sample associated with the exprObj
  		//populate the SampleKaplanMeierPlotResultset
		SampleKaplanMeierPlotResultset sampleResultset = null;
  		if(kaplanMeierPlotContainer != null && exprObj != null &&  exprObj.getBiospecimenId() != null){
  			BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(exprObj.getBiospecimenId().toString());
  			sampleResultset = (SampleKaplanMeierPlotResultset) kaplanMeierPlotContainer.getBioSpecimenResultset(exprObj.getBiospecimenId().toString());
  		    if (sampleResultset == null){
  		    	sampleResultset= new SampleKaplanMeierPlotResultset(biospecimenID);
  		    }
      	}
  		return sampleResultset;
	}
}
