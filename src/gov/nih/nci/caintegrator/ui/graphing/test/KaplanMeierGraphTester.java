package gov.nih.nci.caintegrator.ui.graphing.test;



import gov.nih.nci.caintegrator.ui.graphing.chart.plot.KaplanMeierPlot;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeries;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeriesSet;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class KaplanMeierGraphTester extends ApplicationFrame {

	public KaplanMeierGraphTester(String title) {
		super(title);
		Collection<KaplanMeierPlotPointSeriesSet> sets = new ArrayList<KaplanMeierPlotPointSeriesSet>();
		KaplanMeierPlotPointSeriesSet set1 = createKMPlotPointSeriesSet("Up Regulated", 10, .15);
		set1.setColor(Color.BLUE);
		sets.add(set1);
		
		KaplanMeierPlotPointSeriesSet set2 = createKMPlotPointSeriesSet("Down Regulated", 15, .25);
		set2.setColor(Color.RED);
		sets.add(set2);
		
		KaplanMeierPlotPointSeriesSet set3 = createKMPlotPointSeriesSet("Intermediate", 18, .07);
		set3.setColor(Color.GREEN);
		sets.add(set3);
		
		
		KaplanMeierPlot kmPlot = new KaplanMeierPlot(sets);
		JFreeChart chart = kmPlot.getKmChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
		setContentPane(chartPanel);
	}
	/**
	 * Main Testing method
	 * @param args
	 */
	public static void main(String[] args) {
		
		KaplanMeierGraphTester demo = new KaplanMeierGraphTester(
				"KaplanMeierAlgorithms Graph Test");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	
	private static KaplanMeierPlotPointSeriesSet createKMPlotPointSeriesSet(String setName, double xInterval,
			double yInterval){
		KaplanMeierPlotPointSeries probabalitySeries = new KaplanMeierPlotPointSeries(setName, true);
		KaplanMeierPlotPointSeries censorSeries = new KaplanMeierPlotPointSeries(setName, true);
		double x = 0;
		double y = 1.0;
		boolean census = false;
		while (true) {
			if (!census) {
				probabalitySeries.add(x, y);
				x = x + xInterval;
				census = true;
			} else {
				if (y >= 0.0) {
					probabalitySeries.add(x, y);
					censorSeries.add(x, y);
					y = y - yInterval;
				}
				census = false;
			}
			if (y <= 0) {
				break;
			}
		}
		KaplanMeierPlotPointSeriesSet theSet = new KaplanMeierPlotPointSeriesSet();
		theSet.setCensorPlotPoints(censorSeries);
		theSet.setProbabilityPlotPoints(probabalitySeries);
		return theSet;
	}
/*
   private void generateSVG(JFreeChart chart) {
		// THE FOLLOWING CODE BASED ON THE EXAMPLE IN THE BATIK DOCUMENTATION...
		// Get a DOMImplementation
		DOMImplementation domImpl = GenericDOMImplementation
				.getDOMImplementation();
		// Create an instance of org.w3c.dom.Document
		Document document = domImpl.createDocument(null, "svg", null);
		// Create an instance of the SVG Generator
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		// Ask the chart to render into the SVG Graphics2D implementation
		chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, 800, 600), null);
		// Finally, stream out SVG to a file using UTF-8 character to
		// byte encoding
		boolean useCSS = true;
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(new File(
					"/test.svg")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			svgGenerator.stream(out, useCSS);
		} catch (SVGGraphics2DIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
}
