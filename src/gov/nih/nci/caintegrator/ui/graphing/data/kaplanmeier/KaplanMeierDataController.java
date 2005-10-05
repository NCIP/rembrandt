package gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier;

import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 *  
 * This class generates all the Kaplan-Meier Survival plot data sets for 
 * GeneExpression and copy number related data.  It is entirely possible that
 * we will want to eventutally create KM plots based on other data, which will
 * change the Algorithm applied and the legends that are to be displayed
 * 
 * @author BauerD 
 */
public class KaplanMeierDataController {

	private KaplanMeierStoredData storedData;
	
	private String geneSymbol;

	private double upFold = 2.0;

	private double downFold = 2.0;

	private static enum Regulation{UPREGULATED, DOWNREGULATED, ALL_SAMPLES, INTERMEDIATE};
	
	private KaplanMeierAlgorithms kaplanMeier = null;

	private Collection<KaplanMeierPlotPointSeriesSet> plotPointSeriesSetCollection;

	private String plotType = RembrandtConstants.GENE_EXP_KMPLOT;

	private static Logger logger = Logger.getLogger(KaplanMeierDataController.class);

	private String upLabel;

	private String downLabel;

	public KaplanMeierDataController(double _upFold, double _downFold, String _geneName,
			KaplanMeierSampleInfo[] samples, String plotType) {

		geneSymbol = _geneName;
		setPlotType(plotType);
		setUpFold(_upFold);
		setDownFold(_downFold);
		if (samples != null) {
			plotPointSeriesSetCollection = new ArrayList<KaplanMeierPlotPointSeriesSet>();
			kaplanMeier = new KaplanMeierAlgorithms(samples, this.getUpFold(),
					this.getDownFold());
			// All Sample Series
			plotPointSeriesSetCollection.add(getDataSeries(samples, Regulation.ALL_SAMPLES,
					"All Samples ", Color.BLUE));
			// UpRegulated Samples Series
			plotPointSeriesSetCollection.add(getDataSeries(samples, Regulation.UPREGULATED,
					geneSymbol + getUpLabel() + " >= " + upFold + "X ",Color.RED));
			// Down Regulation Series
			plotPointSeriesSetCollection
					.add(getDataSeries(samples, Regulation.DOWNREGULATED, geneSymbol
							+ getDownLabel() + " <= " + 1 / downFold + "X ",Color.GREEN));
			// intermediate samples
			plotPointSeriesSetCollection.add(getDataSeries(samples, Regulation.INTERMEDIATE,
					geneSymbol + " Intermediate ",Color.ORANGE));
		} else {
			logger.error("gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord");
			// throw new exception
		}
		populateStoredData();
	}
	/**
	 * This method will create and ecapsulate all the required stored data 
	 * associated with the Kaplan-Meier plot that we generated
	 *
	 */
	private void populateStoredData() {
		storedData = new KaplanMeierStoredData();
		storedData.setGeneSymbol(this.geneSymbol);
		storedData.setUpFold(upFold);
		storedData.setDownFold(downFold);
		storedData.setDownLabel(getDownLabel());
		storedData.setUpLabel(getUpLabel());
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getNumberInstance();
		numberFormatter.setMaximumFractionDigits(5);
		Collection<KaplanMeierSampleInfo> allSamples = kaplanMeier.getAllSamples();
		Collection<KaplanMeierSampleInfo> upSamples = kaplanMeier.getUpSamples();
		Collection<KaplanMeierSampleInfo> downSamples = kaplanMeier.getDownSamples();
		Collection<KaplanMeierSampleInfo> intSamples = kaplanMeier.getIntSamples();
		storedData.setPlotPointSeriesCollection(plotPointSeriesSetCollection);
		storedData.setAllSamples(allSamples);
		storedData.setIntSamples(intSamples);
		storedData.setDownSamples(downSamples);
		storedData.setUpSamples(upSamples);
		storedData.setUpSampleCount(getSampleCount(upSamples));
		storedData.setDownSampleCount(getSampleCount(downSamples));
		storedData.setIntSampleCount(getSampleCount(intSamples));
		storedData.setUpVsDownPvalue(new Double(kaplanMeier.getLogRankPValue(upSamples,downSamples)));
		storedData.setUpVsIntPvalue(new Double(kaplanMeier.getLogRankPValue(upSamples,intSamples)));
		storedData.setDownVsIntPvalue(new Double(kaplanMeier.getLogRankPValue(downSamples,intSamples)));
		storedData.setUpVsRestPvalue(new Double(kaplanMeier.getLogRankPValue(upSamples)));
		storedData.setDownVsRestPvalue(new Double(kaplanMeier.getLogRankPValue(downSamples)));
		storedData.setIntVsRestPvalue(new Double(kaplanMeier.getLogRankPValue(intSamples)));
		storedData.setNumberOfPlots(getNumberOfPlots());
	}

	/**********************************
	 * @param container
	 * @param regulated
	 * @param seriesName
	 * @return
	 */
	private KaplanMeierPlotPointSeriesSet getDataSeries(
			KaplanMeierSampleInfo[] KMsampleInfos, Regulation regulation, String seriesName, Color color) {
		ArrayList<KaplanMeierSampleInfo> samples;
		switch (regulation) {
		case ALL_SAMPLES:
			samples = kaplanMeier.getAllSamples();
			break;
		case DOWNREGULATED:
			samples = kaplanMeier.getDownSamples();
			logger.debug(geneSymbol + " Downregulated: " + this.downFold);
			break;
		case UPREGULATED:
			samples = kaplanMeier.getUpSamples();
			logger.debug(geneSymbol + " Upregulated: " + this.upFold);
			break;
		case INTERMEDIATE:
			samples = kaplanMeier.getIntSamples();
			logger.debug(geneSymbol + " Up: <" + this.getUpFold()
					+ "AND Down: >" + this.getDownFold() + " ");
			break;
		default:
			throw new RuntimeException("Invalid Criteria for KM Plot");
		}

		KaplanMeierPlotPoint[] samplePoints = kaplanMeier
				.getDrawingPoints(samples);

		return createSeries(samplePoints, seriesName, samples, color);

	}

	/***************************************************************************
	 * Creates two data series. One of all data points used to create the step
	 * graph, dataSeries. The second contains the census data that will be
	 * overlaid onto the previous step graph to complete the KM Graph,
	 * censusSeries. It takes the KaplanMeierPlotPoints and breaks them into two
	 * sets of XY points based on whether the data point is checked or not. A
	 * checked datapoint shows that it is actually a censor point and should be
	 * placed in the scatter plot.
	 * 
	 * @param dataPoints
	 * @param seriesName
	 * @return
	 */
	private KaplanMeierPlotPointSeriesSet createSeries(
			KaplanMeierPlotPoint[] dataPoints, String seriesName, ArrayList<KaplanMeierSampleInfo> samples, Color color) {

		// Create the DataPoint Series
		KaplanMeierPlotPointSeries dataSeries = new KaplanMeierPlotPointSeries(
				seriesName, true);
		KaplanMeierPlotPointSeries censusSeries = new KaplanMeierPlotPointSeries(
				seriesName + "Censor Points ", true);
		logger.debug(seriesName);

		for (int i = 0; i < dataPoints.length; i++) {
			logger.debug(dataPoints[i]);
			dataSeries.add(dataPoints[i], i);
			if (dataPoints[i].isChecked()) {
				censusSeries.add(dataPoints[i]);
			}
		}
		dataSeries.setDescription(seriesName);
		censusSeries.setDescription(seriesName);

		KaplanMeierPlotPointSeriesSet kmPointSet = new KaplanMeierPlotPointSeriesSet();
		kmPointSet.setColor(color);
		kmPointSet.setName(seriesName);
		kmPointSet.setCensorPlotPoints(censusSeries);
		kmPointSet.setProbabilityPlotPoints(dataSeries);
		kmPointSet.setLegendTitle(seriesName);
		return kmPointSet;
	}

	/**
	 * @return Returns the downFold.
	 */
	public double getDownFold() {
		return this.downFold;
	}

	/**
	 * @param downFold
	 *            The downFold to set.
	 */
	private void setDownFold(double downFold) {
		// set down fold
		if (getPlotType() != null) {
			if (getPlotType().equals(RembrandtConstants.GENE_EXP_KMPLOT)) {
				this.downFold = 1 / downFold;
			} else if (getPlotType().equals(
					RembrandtConstants.COPY_NUMBER_KMPLOT)) {
				this.downFold = downFold;
			} else {
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
	 * @param upFold
	 *            The upFold to set.
	 */
	private void setUpFold(double upFold) {
		this.upFold = upFold;
	}

	public String getPlotType() {
		return plotType;
	}

	private void setPlotType(String plotType) {
		if (plotType != null
				&& plotType.equals(RembrandtConstants.GENE_EXP_KMPLOT)
				|| plotType.equals(RembrandtConstants.COPY_NUMBER_KMPLOT)) {
			this.plotType = plotType;
		}
	}

	private String getDownLabel() {
		if (plotType != null) {
			if (plotType.equals(RembrandtConstants.GENE_EXP_KMPLOT)) {
				downLabel = " Down-Reg.";
			} else if (plotType.equals(RembrandtConstants.COPY_NUMBER_KMPLOT)) {
				downLabel = " Deleted";
			}
		}
		return downLabel;
	}

	private String getUpLabel() {
		if (plotType != null) {
			if (plotType.equals(RembrandtConstants.GENE_EXP_KMPLOT)) {
				upLabel = " Up-Reg.";
			} else if (plotType.equals(RembrandtConstants.COPY_NUMBER_KMPLOT)) {
				upLabel = " Amplified";
			}
		}
		return upLabel;
	}

	private Integer getSampleCount(Collection<KaplanMeierSampleInfo> samples) {
		Integer count = null;
		if (samples != null) {
			count = new Integer(samples.size());
		}
		return count;
	}

	/**
	 * @return
	 */
	private Integer getNumberOfPlots() {
		int count = 0;
		if (kaplanMeier.getDownSamples() != null
				&& kaplanMeier.getDownSamples().size() > 0) {
			count++;
		}
		if (kaplanMeier.getUpSamples() != null
				&& kaplanMeier.getUpSamples().size() > 0) {
			count++;
		}
		if (kaplanMeier.getIntSamples() != null
				&& kaplanMeier.getIntSamples().size() > 0) {
			count++;
		}
		return new Integer(count);
	}
	
	/**
	 * @return Returns the read only attribute storedData.
	 */
	public KaplanMeierStoredData getStoredData() {
		return storedData;
	}

}
