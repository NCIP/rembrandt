package gov.nih.nci.caintegrator.ui.graphing.chart.plot;

import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeries;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeriesSet;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeries.SeriesType;

import java.awt.Color;
import java.util.Collection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;


public class KaplanMeierPlot{

	private Collection<KaplanMeierPlotPointSeriesSet> kaplanMeierPlotSets;
	private JFreeChart kmChart;
	private XYSeriesCollection finalDataCollection;
	
	public KaplanMeierPlot(Collection<KaplanMeierPlotPointSeriesSet> kmPlotSets) {
		kaplanMeierPlotSets = kmPlotSets;
		finalDataCollection = new XYSeriesCollection();
		/**
		 * Repackage all the datasets to go into the XYSeriesCollection
		 */
		for(KaplanMeierPlotPointSeriesSet dataSet: kaplanMeierPlotSets) {
			finalDataCollection.addSeries(dataSet.getCensorPlotPoints());
			finalDataCollection.addSeries(dataSet.getProbabilityPlotPoints());
		}
		createChart(finalDataCollection);
	}
	
	private void createChart(XYDataset dataset) {
		//Create the chart, dropping in the data set
		JFreeChart chart = ChartFactory.createXYLineChart(
	            "",
	            "Days in Study",
	            "Probability of Survival",
	            dataset,
	            PlotOrientation.VERTICAL,
	            false,//legend
	            true,//tooltips
	            false//urls
	            );
		LegendTitle legend = chart.getLegend();
		XYPlot plot = (XYPlot) chart.getPlot();
	    /********************************************************
	     * IMPORTANT:
	     * Ideally I would create the actual Renderer settings for
	     * the at the time I start to march through the 
	     * KaplanMeierPlotPointSeriesSets, adding them to the actual
	     * Data Set that is going to be going into the Chart plotter.
	     * But you have no idea how they are going to be sitting in
	     * the Plot dataset so there is no guarantee that setting the
	     * renderer based on a supposed index will actually work. In fact
	     * it didn't work when I wrote this.
	     * 
	     */
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for(int i = 0; i < finalDataCollection.getSeriesCount();i++) {
			KaplanMeierPlotPointSeries kmSeries = (KaplanMeierPlotPointSeries)finalDataCollection.getSeries(i);
			if(kmSeries.getType() == SeriesType.CENSOR) {
				renderer.setSeriesLinesVisible(i, false);
		        renderer.setSeriesShapesVisible(i, true);
		    }else if(kmSeries.getType()==SeriesType.PROBABILITY){
		    	renderer.setSeriesLinesVisible(i, true);
		        renderer.setSeriesShapesVisible(i, false);
		        
			}else {
				//don't show this set as it is not a known type
				renderer.setSeriesLinesVisible(i, false);
		        renderer.setSeriesShapesVisible(i, false);
			}
			renderer.setSeriesPaint(i, getKMSetColor(kmSeries.getKey(), kmSeries.getType()),true);
		}
	
		renderer.setToolTipGenerator(new StandardXYToolTipGenerator());
        renderer.setDefaultEntityRadius(6);
        plot.setRenderer(renderer);
        //change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.
		rangeAxis.setAutoRange(true);
		rangeAxis.setRange(0.0,1.0);
		kmChart=chart;
	}

	/**
	 * @return Returns the kmChart.
	 */
	public JFreeChart getKmChart() {
		return kmChart;
	}
	
	private Color getKMSetColor(Comparable setKey, SeriesType type) {
		for(KaplanMeierPlotPointSeriesSet seriesSet: kaplanMeierPlotSets) {
			 if(seriesSet.getHashKey() == setKey ) {
				 return seriesSet.getColor();
			 }
		}
		//If nothing was found that matched return Color.BLACK
		return Color.BLACK;
	}
}