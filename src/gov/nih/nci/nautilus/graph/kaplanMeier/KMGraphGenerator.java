package gov.nih.nci.nautilus.graph.kaplanMeier;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.SampleKaplanMeierPlotResultset;
import gov.nih.nci.nautilus.struts.form.KMDataSetForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.jfree.data.XYSeriesCollection;



/**
 * @author BauerD
 * Dec 15, 2004
 * 
 */
public class KMGraphGenerator {
    private GeneExpressionQuery geneQuery;
    private String method;
    private String geneSymbol;
    private int upFold = 3;
    private int downFold = 3;
    private String chartTitle = null;
    private ArrayList folds = new ArrayList();
    private Resultant resultant;
    private static final int UPREGULATED = 1;
    private static final int DOWNREGULATED = 2;
    private static final int ALLSAMPLES = 3;
    private KMDataSeries[] allSamples;
    private KMDataSeries[] upSamples;
    private KMDataSeries[] downSamples;
    private ActionErrors myActionErrors = new ActionErrors();
    private XYSeriesCollection censorDataseries = new XYSeriesCollection();
    private XYSeriesCollection lineDataseries = new XYSeriesCollection();
    
    private static Logger logger = Logger.getLogger(KMDataSetForm.class);
    
    public KMGraphGenerator(int _upFold, int _downFold, String _geneName) {
        upFold = _upFold;
        downFold = _downFold;
        geneSymbol = _geneName;
        ResultsContainer resultsContainer = null;
        try {
            resultsContainer = performKaplanMeierPlotQuery();
        } catch (Exception e) {
            logger.error("KMDataSetForm has thrown an exception");
            logger.error(e);
        }
        if (resultsContainer !=null && resultsContainer instanceof KaplanMeierPlotContainer) {
            
            KaplanMeierPlotContainer kmPlotContainer =  (KaplanMeierPlotContainer)resultsContainer;
            
            //All Sample Series
           allSamples = getDataSeries(kmPlotContainer, ALLSAMPLES, "All Samples");
           
           //UpRegulated Samples Series 
           upSamples = getDataSeries(kmPlotContainer,UPREGULATED, geneSymbol+" Upregulated "+upFold+"X");
            // Down Regulation Series
           downSamples = getDataSeries(kmPlotContainer,DOWNREGULATED, geneSymbol+" Downregulated "+downFold+"X");
           lineDataseries.addSeries(upSamples[0]);
           lineDataseries.addSeries(allSamples[0]);
           lineDataseries.addSeries(downSamples[0]);
          
           censorDataseries.addSeries(upSamples[1]);
           censorDataseries.addSeries(allSamples[1]);
           censorDataseries.addSeries(downSamples[1]);
          

        }else {
            myActionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.struts.form.quicksearch.noRecord", geneSymbol));
        }
    }
    
    /***
     * 
     * @return
     * @throws Exception
     */
    private ResultsContainer performKaplanMeierPlotQuery() throws Exception {
        GeneIDCriteria geneCrit = new GeneIDCriteria();
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(geneSymbol));
        geneQuery = (GeneExpressionQuery) QueryManager
                .createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("KaplanMeierPlot");
        geneQuery.setAssociatedView(ViewFactory
                .newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(
                new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
        resultant = ResultsetManager.executeKaplanMeierPlotQuery(geneQuery);
        return resultant.getResultsContainer();
    }
    
    /***
     * 
     * @param container
     * @param regulated
     * @param seriesName
     * @return
     */
    private KMDataSeries[] getDataSeries(KaplanMeierPlotContainer container, int regulated, String seriesName) {
        Collection samples;
        ExprFoldChangeDE regulation;
        Object[] array;
        switch(regulated) {
            
            case ALLSAMPLES:
                samples = container.getBioSpecimenResultsets();
                break;
            case DOWNREGULATED:
                regulation = new ExprFoldChangeDE.DownRegulation(new Float(downFold));
                samples = container.getSampleKaplanMeierPlotResultsets(regulation);
                array = samples.toArray();
                logger.debug(geneSymbol+" Downregulated: "+this.downFold);
                for(int i = array.length;i>0;i--) {
                    SampleKaplanMeierPlotResultset result = ((SampleKaplanMeierPlotResultset)array[i-1]);
                    logger.debug(result);
                }
                break;
            case UPREGULATED:
                regulation = new ExprFoldChangeDE.UpRegulation(new Float(upFold));
                samples = container.getSampleKaplanMeierPlotResultsets(regulation);
                array = samples.toArray();
                logger.debug(geneSymbol+" Upregulated: "+this.upFold);
                for(int i = array.length;i>0;i--) {
                    SampleKaplanMeierPlotResultset result = ((SampleKaplanMeierPlotResultset)array[i-1]);
                    logger.debug(result);
                }
                break;
            default:
                throw new RuntimeException("Invalid Criteria for KM Plot");
        }
        KaplanMeier km = new KaplanMeier(samples);
        KMDrawingPoint[] samplePoints = km.getDrawingPoints();
        return createSeries(samplePoints, seriesName);
    }
    
    /***
     * Creates two data series.  One of all data points used to create the
     * step graph, dataSeries. The second contains the census data that will be overlaid
     * onto the previous step graph to complete the KM Graph, censusSeries.
     * @param dataPoints
     * @param seriesName
     * @return
     */
    private KMDataSeries[] createSeries(KMDrawingPoint[] dataPoints, String seriesName) {
        
        //Create the DataPoint Series
        KMDataSeries dataSeries = new KMDataSeries(seriesName,true);
        KMDataSeries censusSeries = new KMDataSeries(seriesName+" Censor Points", true);
        
        logger.debug(seriesName);
       
        for (int i = 0; i < dataPoints.length; i++) {
            logger.debug(dataPoints[i]);
            dataSeries.add(dataPoints[i],i);
            if(dataPoints[i].isCensus()) {
                censusSeries.add(dataPoints[i]);
            }
        }
        KMDataSeries[] results = {dataSeries, censusSeries};
        return results;
    }
	/**
	 * @return Returns the allSamples.
	 */
	public KMDataSeries[] getAllSamples() {
		return allSamples;
	}
	/**
	 * @return Returns the downSamples.
	 */
	public KMDataSeries[] getDownSamples() {
		return downSamples;
	}
	/**
	 * @return Returns the upSamples.
	 */
	public KMDataSeries[] getUpSamples() {
		return upSamples;
	}
	/**
	 * @return Returns the myActionErrors.
	 */
	public ActionErrors getMyActionErrors() {
		return myActionErrors;
	}
	/**
	 * @param myActionErrors The myActionErrors to set.
	 */
	public void setMyActionErrors(ActionErrors errors) {
		this.myActionErrors = errors;
	}
	/**
	 * @return Returns the censorDataseries.
	 */
	public XYSeriesCollection getCensorDataseries() {
		return censorDataseries;
	}
	/**
	 * @param censorDataseries The censorDataseries to set.
	 */
	public void setCensorDataseries(XYSeriesCollection censorDataseries) {
		this.censorDataseries = censorDataseries;
	}
	/**
	 * @return Returns the lineDataseries.
	 */
	public XYSeriesCollection getLineDataseries() {
		return lineDataseries;
	}
	/**
	 * @param lineDataseries The lineDataseries to set.
	 */
	public void setLineDataseries(XYSeriesCollection lineDataseries) {
		this.lineDataseries = lineDataseries;
	}
}
