package gov.nih.nci.nautilus.ui.graph.kaplanMeier;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;

import java.text.NumberFormat;
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
    private String geneSymbol;
    private double upFold = 2.0;
    private double downFold = 2.0;
    private String chartTitle = null;
    private static final int UPREGULATED = 1;
    private static final int DOWNREGULATED = 2;
    private static final int ALLSAMPLES = 3;
    private static final int INTERMEDIATE = 4;

    private KaplanMeier kaplanMeier = null;
    private KMDataSeries[] allSamples;
    private KMDataSeries[] upSamples;
    private KMDataSeries[] downSamples;
    private KMDataSeries[] intSamples;
    private ActionErrors myActionErrors = new ActionErrors();
    private XYSeriesCollection censorDataseries = new XYSeriesCollection();
    private XYSeriesCollection lineDataseries = new XYSeriesCollection();
    private String plotType = NautilusConstants.GENE_EXP_KMPLOT;
    private static Logger logger = Logger.getLogger(KMGraphGenerator.class);
    private String upLabel;
    private String downLabel;
    private Double upVsDownPvalue = null;
    private Double upVsIntPvalue = null;
    private Double downVsIntPvalue = null;
    private Double upVsRest = null;
    private Double downVsRest = null;
    private Double intVsRest = null;
    private Integer upSampleCount = new Integer(0);
    private Integer downSampleCount = new Integer(0);
    private Integer intSampleCount = new Integer(0);
    private Integer allSampleCount = new Integer(0);
    
    public KMGraphGenerator(int _upFold, int _downFold, String _geneName, KMSampleInfo[] samples, String _plotType) {


        geneSymbol = _geneName;
        setPlotType(_plotType);
        setUpFold( _upFold);
        setDownFold( _downFold);
        if (samples != null) {
            
            kaplanMeier = new KaplanMeier(samples, this.getUpFold(), this.getDownFold());
            
           // KaplanMeierPlotContainer kmPlotContainer =  (KaplanMeierPlotContainer)resultsContainer;

            //All Sample Series
           allSamples = getDataSeries(samples, ALLSAMPLES, "All Samples ");

           //UpRegulated Samples Series 
           upSamples = getDataSeries(samples,UPREGULATED, geneSymbol+getUpLabel()+" >= "+upFold+"X ");
            // Down Regulation Series
           downSamples = getDataSeries(samples,DOWNREGULATED, geneSymbol+getDownLabel()+" <= "+1/downFold+"X ");

           // intermediate samples
           intSamples = getDataSeries(samples,INTERMEDIATE, geneSymbol+" Intermediate ");

           
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
            case INTERMEDIATE:
                samples = kaplanMeier.getIntSamples();
                array = samples.toArray();
                logger.debug(geneSymbol+" Up: <"+this.getUpFold() + "AND Down: >"+ this.getDownFold()+" ");
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
        KMDataSeries censusSeries = new KMDataSeries(seriesName+"Censor Points ", true);
        
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
	public ArrayList getAllSamples() {
		return kaplanMeier.getAllSamples();
	}
	/**
	 * @return Returns the downSamples.
	 */
	public ArrayList getDownSamples() {
		return kaplanMeier.getDownSamples();
	}
	/**
	 * @return Returns the upSamples.
	 */
	public ArrayList getUpSamples() {
		return kaplanMeier.getUpSamples();
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
    public double getDownFold() {
        return this.downFold;
    }
    /**
     * @param downFold The downFold to set.
     */
    private void setDownFold(double downFold) {
//      set down fold
        if(getPlotType() != null){
            if(getPlotType().equals(NautilusConstants.GENE_EXP_KMPLOT)) {
                this.downFold = 1/downFold;
            }
            else if(getPlotType().equals(NautilusConstants.COPY_NUMBER_KMPLOT)){
                this.downFold = downFold;
            }
            else {
            	this.downFold = downFold;
            }
        }
    }
    /**
     * @return Returns the upFold.
     */
    public double getUpFold() {
        return this.upFold;
    }
    /**
     * @param upFold The upFold to set.
     */
    private void setUpFold(double upFold) {
        this.upFold = upFold;
    }


    public String getPlotType() {
        return plotType;
    }
    


    public void setPlotType(String plotType) {
        if(plotType != null &&
                plotType.equals(NautilusConstants.GENE_EXP_KMPLOT) ||
                plotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)){
            this.plotType = plotType;
        }        
    }
    


    public String getDownLabel() {
        if(plotType != null){
            if(plotType.equals(NautilusConstants.GENE_EXP_KMPLOT)){
                downLabel = " Down-Reg.";
            }
            else if(plotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)){
                downLabel = " Deleted";
            }
        }
        return downLabel;
    }
    


    public String getUpLabel() {
        if(plotType != null){
            if(plotType.equals(NautilusConstants.GENE_EXP_KMPLOT)){
                upLabel = " Up-Reg.";
            }
            else if(plotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)){
                upLabel = " Amplified";
            }
        }
        return upLabel;
    }


    public Integer getAllSampleCount() {
        if(getAllSamples() != null){
            allSampleCount = new Integer(getAllSamples().size());
        }
        return allSampleCount;
    }
    


    public Integer getDownSampleCount() {
        if(getDownSamples() != null){
            downSampleCount = new Integer(getDownSamples().size());
        }
        return downSampleCount;
    }
    


    public Double getDownVsIntPvalue() {
            return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getDownSamples(),kaplanMeier.getIntSamples()));
    }
    


    public Double getDownVsRest() {
            return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getDownSamples()));
    }
    


    public Integer getIntSampleCount() {
        if(getIntSamples() != null){
            intSampleCount = new Integer(getIntSamples().size());
        }
        return intSampleCount;
    }
    


    public ArrayList getIntSamples() {
        return kaplanMeier.getIntSamples();
    }
    


    public Double getIntVsRest() {
            return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getIntSamples()));

    }
    


    public Integer getUpSampleCount() {
        if(getUpSamples() != null){
            upSampleCount = new Integer(getUpSamples().size());
        }
        return upSampleCount;
    }
    


    public Double getUpVsDownPvalue() {
           return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getUpSamples(),kaplanMeier.getDownSamples()));
    }
    


    public Double getUpVsIntPvalue() {
            return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getUpSamples(),kaplanMeier.getIntSamples()));
    }
    


    public Double getUpVsRest() {
            return new Double(kaplanMeier.getLogRankPValue(kaplanMeier.getUpSamples()));
    }
    
    
}
