
package gov.nih.nci.nautilus.ui.graph.geneExpression;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.text.NumberFormat;
import javax.servlet.http.HttpSession;
import org.jfree.data.*;
import org.jfree.data.category.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.plot.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.labels.*;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.urls.*;
import org.jfree.chart.servlet.*;

public class GeneExpressionPlot {

	public static String generateBarChart(String gene, HttpSession session, PrintWriter pw) {
		String filename = null;
		try {
			final GenePlotDataSet gpds = new GenePlotDataSet(gene);
			
			DefaultCategoryDataset dataset = (DefaultCategoryDataset) gpds.getDataSet();
			
			//  create the chart...
	        JFreeChart chart = ChartFactory.createBarChart(
	            "Gene Expression Plot ("+gene.toUpperCase()+")",         // chart title
	            "Groups",               // domain axis label
	            "Mean Expression Intensity",                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            true,                     // include legend
	            true,                     // tooltips?
	            false                     // URLs?
	        );
	        
			chart.setBackgroundPaint(java.awt.Color.white);
			//lets start some customization to retro fit w/jcharts lookand feel
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis axis = plot.getDomainAxis();
			axis.setLowerMargin(0.02); // two percent
			axis.setCategoryMargin(0.20); // ten percent
			axis.setUpperMargin(0.02); // two percent
			
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setItemMargin(0.01); // one percent
			renderer.setDrawBarOutline(true);
			renderer.setOutlinePaint(Color.BLACK);
			renderer.setToolTipGenerator(new CategoryToolTipGenerator() {

	            public String generateToolTip(CategoryDataset dataset, int series, int item) {
	               HashMap pv = gpds.getPValuesHashMap();
	               String currentPV = (String) pv.get(dataset.getRowKey(series)+"::"+dataset.getColumnKey(item));
	            	return "Probeset : " + dataset.getRowKey(series)+ "<br/>Intensity : " + dataset.getValue(series, item) +"<br>PVALUE : "+currentPV+"<br/>";
	            }
	           
	        });
			
			// LegendTitle lg = chart.getLegend();
			
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, 650, 400, info, session);

			//  Write the image map to the PrintWriter
			//can use a different writeImageMap to pass tooltip and URL custom
			ChartUtilities.writeImageMap(pw, filename, info, new CustomOverlibToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator() );
			//			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		return filename;
	}
}