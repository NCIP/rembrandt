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
package gov.nih.nci.nautilus.resultset.gene;

import gov.nih.nci.nautilus.de.DatumDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
 */
public class GeneExprDiseaseGroupViewHandler extends GeneExprViewHandler {
	public GeneExprResultsContainer handleGeneExprDiseaseView(GeneExprResultsContainer geneViewContainer, GeneExpr.GeneExprGroup exprObj){
		GeneResultset geneResultset = null;
		ReporterResultset reporterResultset = null;
		DiseaseGroupResultset diseaseResultset = null;
      	if (exprObj != null){
      		geneResultset = handleGeneResulset(geneViewContainer, exprObj);
      		reporterResultset = handleReporterResultset(geneResultset,exprObj);
   			diseaseResultset = handleDiseaseGroupResultset(reporterResultset,exprObj);
   			reporterResultset.addGroupByResultset(diseaseResultset);
      		geneResultset.addReporterResultset(reporterResultset);
      		//add the reporter to geneResultset
      		geneViewContainer.addGeneResultset(geneResultset);
      	}
      	return geneViewContainer;
    }
	protected DiseaseGroupResultset handleDiseaseGroupResultset(ReporterResultset reporterResultset, GeneExpr.GeneExprGroup exprObj){
  		//find out the disease type associated with the exprObj
  		//populate the DiseaseTypeResultset
		DiseaseGroupResultset diseaseGroupResultset = null;
  		if(reporterResultset != null && exprObj != null &&  exprObj.getDiseaseType() != null){
  			DiseaseNameDE disease = new DiseaseNameDE(exprObj.getDiseaseType().toString());
  			diseaseGroupResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(exprObj.getDiseaseType().toString());
  		    if (diseaseGroupResultset == null){
  		    	diseaseGroupResultset= new DiseaseGroupResultset(disease);
	      		}
  		    diseaseGroupResultset.setFoldChangeRatioValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
  		    diseaseGroupResultset.setRatioPval(new DatumDE(DatumDE.RATIO_PVAL,exprObj.getRatioPval()));

      	}
  		return diseaseGroupResultset;
    }

}
