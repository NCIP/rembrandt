package gov.nih.nci.caintegrator.ui.graphing.chart;

import java.util.Collection;

import gov.nih.nci.caintegrator.ui.graphing.chart.plot.KaplanMeierPlot;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeriesSet;

import org.jfree.chart.JFreeChart;

public class CaIntegratorChartFactory {
	public static JFreeChart getKaplanMeierGraph(Collection<KaplanMeierPlotPointSeriesSet> series) {
		KaplanMeierPlot km = new KaplanMeierPlot(series);
		return km.getKmChart();
	}
}
