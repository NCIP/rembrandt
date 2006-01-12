package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import java.util.ArrayList;
import java.util.Collection;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
 */
public abstract class GeneExprViewHandler {
    protected static GeneResultset handleGeneResulset (GeneExprResultsContainer geneViewResultsContainer, GeneExpr exprObj){
  		//get the gene accessesion number for this record
  		//check if the gene exsists in the GeneExprSingleViewResultsContainer, otherwise add a new one.
		GeneResultset geneResultset = geneViewResultsContainer.getGeneResultset(exprObj.getGeneSymbol());
  		if(geneResultset == null){ // no record found
  			geneResultset = new GeneResultset();
  		}
  		if(exprObj.getGeneSymbol()!= null){
  		geneResultset.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(exprObj.getGeneSymbol()));
 		}
  		else{
  			geneResultset.setAnonymousGene();
  		}
  		return geneResultset;

    }
	protected static ReporterResultset handleReporterResultset(GeneResultset geneResultset,GeneExpr exprObj){
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(geneResultset != null && exprObj != null){
	    	if(exprObj.getProbesetName() != null && exprObj.getCloneName() == null){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = geneResultset.getRepoterResultset(exprObj.getProbesetName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}  	
	    		}
	  		else if(exprObj.getProbesetName() == null && exprObj.getCloneName() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.CLONE_ID,exprObj.getCloneName());
	       		reporterResultset = geneResultset.getRepoterResultset(exprObj.getCloneName().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}
	  			}
	            reporterResultset.setValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
		  		if(exprObj.getAnnotation() != null){
		  			GeneExpr.Annotaion annotation = exprObj.getAnnotation();
		  			reporterResultset.setAssiciatedGenBankAccessionNos(exprObj.getAnnotation().getAccessions());
		  			reporterResultset.setAssiciatedLocusLinkIDs(exprObj.getAnnotation().getLocusLinks());
		  		if(exprObj.getAnnotation().getGeneAnnotation() != null){
		  			if(exprObj.getAnnotation().getGeneAnnotation().getGeneSymbol()!= null){
			  			Collection geneSymbols = new ArrayList();
			  			geneSymbols.add(exprObj.getAnnotation().getGeneAnnotation().getGeneSymbol());
						reporterResultset.setAssiciatedGeneSymbols(geneSymbols);			  			
		  			}

		  			reporterResultset.setAssociatedPathways(exprObj.getAnnotation().getGeneAnnotation().getPathwayNames());
		  		    reporterResultset.setAssociatedGOIds(exprObj.getAnnotation().getGeneAnnotation().getGoIDs());
		  		}

	 		}
		}
	    return reporterResultset;
    }
}
