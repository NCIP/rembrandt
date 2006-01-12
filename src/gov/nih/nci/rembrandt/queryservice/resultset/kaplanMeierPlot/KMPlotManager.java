package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AssayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE.SNPProbeSet;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.UnifiedGeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

import org.apache.log4j.Logger;

/**
 * @author SahniH Date: Mar 19, 2005
 *  
 */
public class KMPlotManager {
	private static Logger logger = Logger.getLogger(KMPlotManager.class);

	/***************************************************************************
	 * 
	 * @return @throws
	 *         Exception
	 */
	public ResultsContainer performKMGeneExpressionQuery(String geneSymbol,InstitutionCriteria instCrit)
			throws Exception {
		ResultsContainer resultsContainer = null;
		try {
			if (geneSymbol != null  && instCrit != null) {
				GeneIDCriteria geneCrit = new GeneIDCriteria();
				geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(
						geneSymbol));
				GeneExpressionQuery geneQuery = (GeneExpressionQuery) QueryManager
						.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
				geneQuery.setInstitutionCriteria(instCrit);
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
	/***************************************************************************
	 * 
	 * @return @throws
	 *         Exception
	 */
	public ResultsContainer performUnifiedKMGeneExpressionQuery(String geneSymbol,InstitutionCriteria instCrit)
	throws Exception {
		ResultsContainer resultsContainer = null;
		try {
			if (geneSymbol != null  && instCrit != null) {
				GeneIDCriteria geneCrit = new GeneIDCriteria();
				geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(
						geneSymbol));
				UnifiedGeneExpressionQuery geneQuery = (UnifiedGeneExpressionQuery) QueryManager
						.createQuery(QueryType.UNIFIED_GENE_EXPR_QUERY_TYPE);
				geneQuery.setQueryName("UnifiedKaplanMeierPlot");
				geneQuery.setAssociatedView(ViewFactory
						.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
				geneQuery.setGeneIDCrit(geneCrit);
				geneQuery.setInstitutionCriteria(instCrit);
				//geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(
				//		new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
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

	public ResultsContainer performKMCopyNumberQuery(ComparativeGenomicQuery copyNumberQuery )
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

    public ResultsContainer performKMCopyNumberQuery(GeneSymbol geneSymbolDE,InstitutionCriteria instCrit)
	throws Exception {
        if (geneSymbolDE != null  && instCrit != null) {
            GeneIDCriteria geneCrit = new GeneIDCriteria();
            geneCrit.setGeneIdentifier(geneSymbolDE);
            ComparativeGenomicQuery copyNumberQuery = (ComparativeGenomicQuery) QueryManager
                    .createQuery(QueryType.CGH_QUERY_TYPE);
            copyNumberQuery.setQueryName("CopyNumberKMPlot");
            copyNumberQuery.setInstitutionCriteria(instCrit);
            copyNumberQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            copyNumberQuery.setGeneIDCrit(geneCrit);
            copyNumberQuery.setAssayPlatformCrit(new AssayPlatformCriteria(
                    new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY)));
            return performKMCopyNumberQuery(copyNumberQuery);
        }
        
        return null;
    }


    public ResultsContainer performKMCopyNumberQuery(SNPProbeSet snpDE,InstitutionCriteria instCrit)
			throws Exception {
        if (snpDE != null  && instCrit != null) {
            SNPCriteria snpCrit = new SNPCriteria();
            snpCrit.setSNPIdentifier(snpDE);
            ComparativeGenomicQuery copyNumberQuery = (ComparativeGenomicQuery) QueryManager
                    .createQuery(QueryType.CGH_QUERY_TYPE);
            copyNumberQuery.setQueryName("CopyNumberKMPlot");
            copyNumberQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            copyNumberQuery.setSNPCrit(snpCrit);
            copyNumberQuery.setInstitutionCriteria(instCrit);
            copyNumberQuery.setAssayPlatformCrit(new AssayPlatformCriteria(
                    new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY)));
            return performKMCopyNumberQuery(copyNumberQuery);
        }
        return null;
    }
}

