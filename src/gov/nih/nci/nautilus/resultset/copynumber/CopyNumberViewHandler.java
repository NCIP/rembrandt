/*
 * Created on Nov 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;

import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyNumberViewHandler {
    protected static CytobandResultset handleCytobandResulset (CopyNumberResultsContainer copyNumberResultsContainer, CopyNumber copyNumberObj){
  		//get the gene accessesion number for this record
  		//check if the gene exsists in the CopyNumberResultsContainer, otherwise add a new one.
		CytobandResultset cytobandResultset = copyNumberResultsContainer.getCytobandResultset(copyNumberObj.getCytoband());
  		if(cytobandResultset == null){ // no record found
  			cytobandResultset = new CytobandResultset();
  		}
  		if(copyNumberObj.getCytoband()!= null){
  		cytobandResultset.setCytoband(new CytobandDE(copyNumberObj.getCytoband()));
  		}
  		else{
  			cytobandResultset.setAnonymousCytoband(true);
  		}
  		return cytobandResultset;

    }
	protected static ReporterResultset handleReporterResultset(CytobandResultset cytobandResultset,CopyNumber copyNumberObj){
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(cytobandResultset != null && copyNumberObj != null){
	    	if(copyNumberObj.getSnpProbesetName() != null ){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,copyNumberObj.getSnpProbesetName());
	       		reporterResultset = cytobandResultset.getRepoterResultset(copyNumberObj.getSnpProbesetName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}  	
	    	}
	  		if(copyNumberObj.getAnnotations() != null){
	  			CopyNumber.SNPAnnotation annotation = copyNumberObj.getAnnotations();
	  			reporterResultset.setAssiciatedGenBankAccessionNos(copyNumberObj.getAnnotations().getAccessionNumbers());
	  			reporterResultset.setAssiciatedLocusLinkIDs(copyNumberObj.getAnnotations().getLocusLinkIDs());
	  			reporterResultset.setAssiciatedGeneSymbols(copyNumberObj.getAnnotations().getGeneSymbols());
	  			
	  		}
	    	
		}
        return reporterResultset;
    }
}
