/*
 *  @author: SahniH
 *  Created on Mar 19, 2005
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
package gov.nih.nci.nautilus.resultset.kaplanMeierPlot;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.nautilus.de.SNPIdentifierDE.SNPProbeSet;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.ui.graph.kaplanMeier.KMGraphGenerator;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import org.apache.log4j.Logger;

/**
 * @author SahniH Date: Mar 19, 2005
 *  
 */
public class KMPlotManager {
	private static Logger logger = Logger.getLogger(KMGraphGenerator.class);

	/***************************************************************************
	 * 
	 * @return @throws
	 *         Exception
	 */
	public ResultsContainer performKMGeneExpressionQuery(String geneSymbol)
			throws Exception {
		ResultsContainer resultsContainer = null;
		try {
			if (geneSymbol != null) {
				GeneIDCriteria geneCrit = new GeneIDCriteria();
				geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(
						geneSymbol));
				GeneExpressionQuery geneQuery = (GeneExpressionQuery) QueryManager
						.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
				geneQuery.setQueryName("KaplanMeierPlot");
				geneQuery.setAssociatedView(ViewFactory
						.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
				geneQuery.setGeneIDCrit(geneCrit);
				geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(
						new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
				Resultant resultant = ResultsetManager
						.executeKaplanMeierPlotQuery(geneQuery);
				resultsContainer = resultant.getResultsContainer();
			}

		} catch (Exception e) {
			logger.error("KMPlotManager has thrown an exception");
			logger.error(e);
		}
		return resultsContainer;
	}

	public ResultsContainer performKMCopyNumberQuery(ComparativeGenomicQuery copyNumberQuery)
			throws Exception {
		ResultsContainer resultsContainer = null;
		try {
			if (copyNumberQuery != null) {
				Resultant resultant = ResultsetManager
						.executeKaplanMeierPlotQuery(copyNumberQuery);
				resultsContainer = resultant.getResultsContainer();
			}

		} catch (Exception e) {
			logger.error("KMPlotManager has thrown an exception");
			logger.error(e);
		}
		return resultsContainer;
	}

    public ResultsContainer performKMCopyNumberQuery(GeneSymbol geneSymbolDE) throws Exception {
        if (geneSymbolDE != null) {
            GeneIDCriteria geneCrit = new GeneIDCriteria();
            geneCrit.setGeneIdentifier(geneSymbolDE);
            ComparativeGenomicQuery copyNumberQuery = (ComparativeGenomicQuery) QueryManager
                    .createQuery(QueryType.CGH_QUERY_TYPE);
            copyNumberQuery.setQueryName("CopyNumberKMPlot");
            copyNumberQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            copyNumberQuery.setGeneIDCrit(geneCrit);
            copyNumberQuery.setAssayPlatformCrit(new AssayPlatformCriteria(
                    new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY)));
            return performKMCopyNumberQuery(copyNumberQuery);
        }
        
        return null;
    }


    public ResultsContainer performKMCopyNumberQuery(SNPProbeSet snpDE) throws Exception {
        if (snpDE != null) {
            SNPCriteria snpCrit = new SNPCriteria();
            snpCrit.setSNPIdentifier(snpDE);
            ComparativeGenomicQuery copyNumberQuery = (ComparativeGenomicQuery) QueryManager
                    .createQuery(QueryType.CGH_QUERY_TYPE);
            copyNumberQuery.setQueryName("CopyNumberKMPlot");
            copyNumberQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            copyNumberQuery.setSNPCrit(snpCrit);
            copyNumberQuery.setAssayPlatformCrit(new AssayPlatformCriteria(
                    new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY)));
            return performKMCopyNumberQuery(copyNumberQuery);
        }
        return null;
    }
}

