package gov.nih.nci.nautilus.struts.form;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.graph.kaplanMeier.KMDataSeries;
import gov.nih.nci.nautilus.graph.kaplanMeier.KMDrawingPoint;
import gov.nih.nci.nautilus.graph.kaplanMeier.KaplanMeier;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.jfree.data.XYSeriesCollection;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class KMDataSetForm extends ActionForm implements DatasetProducer,
		Serializable {

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
    
  
    /**
     * This method is called by the ceWolf chart tag to create the data
     * for the plot.
     */
	public Object produceDataset(Map params) throws DatasetProduceException {
		XYSeriesCollection dataset = new XYSeriesCollection();
        ResultsContainer resultsContainer = null;
		try {
			resultsContainer = performKaplanMeierPlotQuery();
		} catch (Exception e) {
            System.err.println("KMDataSetForm has thrown an exception");
            e.printStackTrace();
		}
		if (resultsContainer !=null && resultsContainer instanceof KaplanMeierPlotContainer) {
			
            KaplanMeierPlotContainer kmPlotContainer =  (KaplanMeierPlotContainer)resultsContainer;
            
            //All Sample Series
			//KMDataSeries[] allSamples = getDataSeries(kmPlotContainer, ALLSAMPLES, "All Samples");
			//UpRegulated Samples Series 
			KMDataSeries[] upSamples = getDataSeries(kmPlotContainer,UPREGULATED, geneSymbol+" Upregulated "+upFold+"X");
			// Down Regulation Series
			KMDataSeries[] downSamples = getDataSeries(kmPlotContainer,DOWNREGULATED, geneSymbol+" Downregulated "+downFold+"X");
			HashMap hashMap = (HashMap)params;
            //Store in DataSet
            if(((Boolean)(hashMap.get("censusPlot"))).booleanValue()) {
            	//store and return the Census Data Series
                dataset.addSeries(upSamples[1]);
                //dataset.addSeries(allSamples[1]);
                dataset.addSeries(downSamples[1]);
            }else {
                //store and return the Step Line Data Series
            	dataset.addSeries(upSamples[0]);
            	//dataset.addSeries(allSamples[0]);
            	dataset.addSeries(downSamples[0]);
            }
		}else {
			throw new DatasetProduceException("ResultsContainer is either not Kaplan-Meier or NULL");
        }
	    
        return dataset;
	}
	
	/***
     * 
	 */
	public boolean hasExpired(Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime()) > 5000;
	}
	
	public String getProducerId() {
		return "KMDataSetForm";
	}
 
    /**
	 * @return Returns the geneSymbol.
	 */
	public String getGeneSymbol() {
		return geneSymbol;
	}

	/**
	 * @param geneSymbol
	 *            The geneSymbol to set.
	 */
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	/**
	 * @return Returns the geneFolds.
	 */
	public int getUpFold() {
		return upFold;
	}

	/**
	 * @param geneFolds
	 *            The geneFolds to set.
	 */
	public void setUpFold(int upFolds) {
		this.upFold = upFolds;
	}
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
		
	/**
	 * @return Returns the folds.
	 */
	public ArrayList getFolds() {
		if(folds.isEmpty()) {
            for(int i = 1;i<11;i++) {
            	folds.add(new Integer(i));
            }
        }
        return folds;
	}
	
    /**
     * @return Returns the downFold.
     */
    public int getDownFold() {
        return downFold;
    }
    /**
     * @param downFold The downFold to set.
     */
    public void setDownFold(int downFold) {
        this.downFold = downFold;
    }
   
	/**
	 * @return Returns the chartTitle.
	 */
	public String getChartTitle() {
        chartTitle = "Kaplan-Meier Survival Plot for Samples with Differential "+geneSymbol+" Gene Expression";
		return chartTitle;
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
        switch(regulated) {
            case ALLSAMPLES:
                samples = container.getBioSpecimenResultsets();
                break;
            case DOWNREGULATED:
                regulation = new ExprFoldChangeDE.DownRegulation(new Float(downFold));
                samples = container.getSampleKaplanMeierPlotResultsets(regulation);
                break;
            case UPREGULATED:
                regulation = new ExprFoldChangeDE.UpRegulation(new Float(upFold));
                samples = container.getSampleKaplanMeierPlotResultsets(regulation);
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
        for (int i = 0; i < dataPoints.length; i++) {
            dataSeries.add(dataPoints[i],i);
        }
        
        //Create the Census Series
        KMDataSeries censusSeries = new KMDataSeries(seriesName+" Census Points", true);
        for (int i = 0; i < dataPoints.length; i++) {
        	if(dataPoints[i].isCensus()) {
        		censusSeries.add(dataPoints[i]);
            }
        }
        KMDataSeries[] results = {dataSeries, censusSeries};
        return results;
    }

}