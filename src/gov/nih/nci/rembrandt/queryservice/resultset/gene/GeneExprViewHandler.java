/*
 *  @author: SahniH
 *  Created on Oct 29, 2004
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
