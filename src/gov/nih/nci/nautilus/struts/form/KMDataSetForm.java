package gov.nih.nci.nautilus.struts.form;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
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
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.jfree.data.XYSeries;
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
    private ArrayList folds = new ArrayList();
  
	public Object produceDataset(Map params) throws DatasetProduceException {
		XYSeriesCollection dataset = new XYSeriesCollection();
		buildKaplanMeierPlotQuery();
		Resultant resultant = null;
		try {
			resultant = ResultsetManager.executeKaplanMeierPlotQuery(geneQuery);
		} catch (Exception e) {
		}
		if (resultant != null) {
			ResultsContainer resultsContainer = resultant.getResultsContainer();
			if (resultsContainer instanceof KaplanMeierPlotContainer) {

				//All Sample Series
			    KaplanMeierPlotContainer kaplanMeierPlotContainer = (KaplanMeierPlotContainer) resultsContainer;
				Collection allSamples = kaplanMeierPlotContainer
						.getBioSpecimenResultsets();
				KaplanMeier kmAllSamples = new KaplanMeier(allSamples);
				KMDrawingPoint[] allSamplesPoints = kmAllSamples
						.getDrawingPoints();
				XYSeries series1 = createSeries(allSamplesPoints, "All Samples");

				//Upregulation Series
				ExprFoldChangeDE upRegulation = new ExprFoldChangeDE.UpRegulation(new Float(upFold));
				Collection geneSamples = kaplanMeierPlotContainer
						.getSampleKaplanMeierPlotResultsets(upRegulation);
				KaplanMeier kmGeneSamples = new KaplanMeier(geneSamples);
				KMDrawingPoint[] geneSamplePoints = kmGeneSamples
						.getDrawingPoints();
				XYSeries series2 = createSeries(geneSamplePoints, geneSymbol+" Upregulated "+upFold+"X");
				
				// Down Regulation Series
				ExprFoldChangeDE downRegulation = new ExprFoldChangeDE.DownRegulation(new Float(downFold));
				geneSamples = kaplanMeierPlotContainer
					.getSampleKaplanMeierPlotResultsets(downRegulation);
				kmGeneSamples = new KaplanMeier(geneSamples);
				geneSamplePoints = kmGeneSamples.getDrawingPoints();
				XYSeries series3 = createSeries(geneSamplePoints, geneSymbol+" Downregulated "+downFold+"X");
				
				
				dataset.addSeries(series1);
				dataset.addSeries(series2);
				dataset.addSeries(series3);	
			}
		}
		return dataset;
	}

	/**
	 * @param allSamplesPoints
	 */
	private XYSeries createSeries(KMDrawingPoint[] dataPoints, String seriesName) {
		XYSeries dataSeries = new XYSeries(seriesName);
		for (int i = 0; i < dataPoints.length; i++) {
			dataSeries.add(dataPoints[i].getX(), dataPoints[i].getY());
		}
		return dataSeries;
	}

	public boolean hasExpired(Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime()) > 5000;
	}

	public String getProducerId() {
		return "KMDataSetForm";
	}

	private void buildKaplanMeierPlotQuery() {
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
}