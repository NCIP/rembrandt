package gov.nih.nci.nautilus.ui.graph.kaplanMeier;

import gov.nih.nci.nautilus.de.ExprFoldChangeDE;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.jfree.data.XYSeriesCollection;

/**
 * @author BauerD
 * Dec 15, 2004
 * This class generates a Kaplan-Meier Survival plot data set
 * that is used by the ceWolf tags to display the data. 
 */
public class KMGraphGenerator {
    private String method;
    private String geneSymbol;
    private int upFold = 2;
    private int downFold = 2;
    private String chartTitle = null;
    private ArrayList folds = new ArrayList();
   
    private static final int UPREGULATED = 1;
    private static final int DOWNREGULATED = 2;
    private static final int ALLSAMPLES = 3;
    private static final int INTRERMEDIATE = 4;

    private KaplanMeier kaplanMeier = null;
    private KMDataSeries[] allSamples;
    private KMDataSeries[] upSamples;
    private KMDataSeries[] downSamples;
    private KMDataSeries[] intSamples;
    private ActionErrors myActionErrors = new ActionErrors();
    private XYSeriesCollection censorDataseries = new XYSeriesCollection();
    private XYSeriesCollection lineDataseries = new XYSeriesCollection();
    
    private static Logger logger = Logger.getLogger(KMGraphGenerator.class);
    
    public KMGraphGenerator(int _upFold, int _downFold, String _geneName, KMSampleInfo[] samples) {
        setDownFold( _downFold);
        setUpFold( _upFold);
        geneSymbol = _geneName;
       /* ResultsContainer resultsContainer = null;
        try {
            resultsContainer = performKaplanMeierPlotQuery();
        } catch (Exception e) {
            logger.error("KMDataSetForm has thrown an exception");
            logger.error(e);
        }
        */
        if (samples != null) {
            
     
            
           // KaplanMeierPlotContainer kmPlotContainer =  (KaplanMeierPlotContainer)resultsContainer;

            //All Sample Series
           allSamples = getDataSeries(samples, ALLSAMPLES, "All Samples");

           //UpRegulated Samples Series 
           upSamples = getDataSeries(samples,UPREGULATED, geneSymbol+" Upregulated "+upFold+"X");
            // Down Regulation Series
           downSamples = getDataSeries(samples,DOWNREGULATED, geneSymbol+" Downregulated "+downFold+"X");

           // intermediate samples
           intSamples = getDataSeries(samples,INTRERMEDIATE, geneSymbol+" Intermediate (> "+downFold+"X"+" & < "+upFold+"X");

           
           lineDataseries.addSeries(upSamples[0]);
           lineDataseries.addSeries(allSamples[0]);
           lineDataseries.addSeries(downSamples[0]);
           lineDataseries.addSeries(intSamples[0]);
           censorDataseries.addSeries(upSamples[1]);
           censorDataseries.addSeries(allSamples[1]);
           censorDataseries.addSeries(downSamples[1]);
           censorDataseries.addSeries(intSamples[1]);
          

        }else {
            myActionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord", geneSymbol));
        }
    }
    
   
    /***
     * 
     * @param container
     * @param regulated
     * @param seriesName
     * @return
     */
    private KMDataSeries[] getDataSeries(KMSampleInfo[] KMsampleInfos, int regulated, String seriesName) {
        ArrayList samples;
        ExprFoldChangeDE regulation;
        Object[] array;

        kaplanMeier = new KaplanMeier(KMsampleInfos, this.getUpFold(), this.getDownFold());

        switch(regulated) {
            
            case ALLSAMPLES:
                samples = kaplanMeier.getAllSamples();
                break;
            case DOWNREGULATED:
                samples = kaplanMeier.getDownSamples();
                array = samples.toArray();
                logger.debug(geneSymbol+" Downregulated: "+this.downFold);
                /*for(int i = array.length;i>0;i--) {
                    SampleKaplanMeierPlotResultset result = ((SampleKaplanMeierPlotResultset)array[i-1]);
                    logger.debug(result);
                }*/
                break;
            case UPREGULATED:
                samples = kaplanMeier.getUpSamples();
                array = samples.toArray();
                logger.debug(geneSymbol+" Upregulated: "+this.upFold);
                /*for(int i = array.length;i>0;i--) {
                    SampleKaplanMeierPlotResultset result = ((SampleKaplanMeierPlotResultset)array[i-1]);
                    logger.debug(result);
                }
                */
                break;
            case INTRERMEDIATE:
                samples = kaplanMeier.getIntSamples();
                array = samples.toArray();
                logger.debug(geneSymbol+" Intregulated: "+this.downFold + "AND "+ this.upFold);
                /*for(int i = array.length;i>0;i--) {
                    SampleKaplanMeierPlotResultset result = ((SampleKaplanMeierPlotResultset)array[i-1]);
                    logger.debug(result);
                }
                */
                break;
            default:
                throw new RuntimeException("Invalid Criteria for KM Plot");
        }

        KMDrawingPoint[] samplePoints = kaplanMeier.getDrawingPoints(samples);
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
            if(dataPoints[i].isChecked()) {
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
    /**
     * @return Returns the downFold.
     */
    public int getDownFold() {
        return this.downFold;
    }
    /**
     * @param downFold The downFold to set.
     */
    public void setDownFold(int downFold) {
        this.downFold = downFold;
    }
    /**
     * @return Returns the upFold.
     */
    public int getUpFold() {
        return this.upFold;
    }
    /**
     * @param upFold The upFold to set.
     */
    public void setUpFold(int upFold) {
        this.upFold = upFold;
    }
}
