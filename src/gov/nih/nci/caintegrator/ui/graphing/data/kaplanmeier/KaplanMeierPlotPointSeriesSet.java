package gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier;

import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeries.SeriesType;

import java.awt.Color;
import java.util.ArrayList;
/**
 * This Class contains two KaplainMeierPlotPointSeries. One for the probability
 * line and a second series of Censor plot points.  This series also allows the
 * user to define certain attributes of how they would like the actual pair to 
 * be rendered on the JFreeChart.  Because both series are related by the sample
 * set that was used to generate them, they will overlay each other and appear
 * as one plot.  For this reason it is assumed that the entire set will be
 * the same color.  If no color is set, black will be used.
 * 
 *  
 * @author BauerD
 *
 */
public class KaplanMeierPlotPointSeriesSet{
	private KaplanMeierPlotPointSeries censorPlotPoints = null;
	private KaplanMeierPlotPointSeries probabilityPlotPoints = null;
	private ArrayList<KaplanMeierSampleInfo> samples;
	private String legendTitle = "";
	private String name = "";
	private Comparable hashKey = 0;
	//Set the default color
	private Color color = Color.BLACK;
	
	public KaplanMeierPlotPointSeriesSet() {
		//create a hash set for this series
		hashKey = createHash();
	}

	/**
	 * @return Returns the censorPlotPoints.
	 */
	public KaplanMeierPlotPointSeries getCensorPlotPoints() {
		return censorPlotPoints;
	}
	/**
	 * @param censorPlotPoints The censorPlotPoints to set.
	 */
	public void setCensorPlotPoints(KaplanMeierPlotPointSeries censorScatterDataPoints) {
		this.censorPlotPoints = censorScatterDataPoints;
		this.censorPlotPoints.setKey(hashKey);
		this.censorPlotPoints.setSeriesType(SeriesType.CENSOR);
	}
	/**
	 * @return Returns the probabilityPlotPoints.
	 */
	public KaplanMeierPlotPointSeries getProbabilityPlotPoints() {
		return probabilityPlotPoints;
	}
	/**
	 * @param probabilityPlotPoints The probabilityPlotPoints to set.
	 */
	public void setProbabilityPlotPoints(KaplanMeierPlotPointSeries xyLineDataPoints) {
		this.probabilityPlotPoints = xyLineDataPoints;
		this.probabilityPlotPoints.setKey(hashKey);
		this.probabilityPlotPoints.setSeriesType(SeriesType.PROBABILITY);
	}
	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color The color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return Returns the legendTitle.
	 */
	public String getLegendTitle() {
		return legendTitle;
	}
	/**
	 * @param legendTitle The legendTitle to set.
	 */
	public void setLegendTitle(String legendTitle) {
		this.legendTitle = legendTitle;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the samples.
	 */
	public ArrayList<KaplanMeierSampleInfo> getSamples() {
		return samples;
	}
	/**
	 * @param samples The samples to set.
	 */
	public void setSamples(ArrayList<KaplanMeierSampleInfo> samples) {
		this.samples = samples;
	}
	/**
	 * This function is required to create a unique id that will be used
	 * to identify it's members later by the Plotting class.  This is necesary
	 * because at the time if this writing, you could not get access to the
	 * data set once it was placed into a JFreeChart.
	 * @return
	 */
	private Comparable createHash(){
		double result = Math.random() * (double)System.currentTimeMillis();
		return result;
	}

	/**
	 * A unique id that represents all members of this set
	 * @return Returns the hashKey.
	 */
	public Comparable getHashKey() {
		return hashKey;
	}
}
