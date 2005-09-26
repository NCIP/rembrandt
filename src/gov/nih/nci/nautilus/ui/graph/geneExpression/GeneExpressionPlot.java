
package gov.nih.nci.nautilus.ui.graph.geneExpression;

import gov.nih.nci.caintegrator.ui.graphing.legend.LegendCreator;

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
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
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
	
	
	public static HashMap generateBarChart(String gene, HttpSession session, PrintWriter pw) {
		String filename = null;
		String ffilename = null;
		String legendHtml = null;
		HashMap charts = new HashMap();
		
		try {
			final GenePlotDataSet gpds = new GenePlotDataSet(gene);
			
			DefaultStatisticalCategoryDataset dataset = (DefaultStatisticalCategoryDataset) gpds.getDataSet();
			CategoryDataset fdataset = (CategoryDataset) gpds.getFdataset();
			
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
	        
	        /**
	         * 
	         * okay, heres where it gets tricky...
	         * we need to generate another chart, with a seperate dataset -
	         * one that does not include the errorbars for std deviation
	         * we generate this fchart (aka "fake chart", then use it later to map the coords
	         * for the tooltip.  we will need to customize the fchart and the
	         * frenderer, faxis ext to look exactly like the current chart w/error bars
	         * in order for the coords to match.  In the end, we will display the image w/the
	         * error bars, but render the map coords of the image w/o the error bars....
	         * confusing, but the only way to add tooltips to the chart w/errorbars without
	         * rewriting components of the api.  this is actually not a complete waste, because
	         * it will allow us to toggle between showing chart w/ and w/o the errorbars
	         * 
	         * -RL
	         */
	        JFreeChart fchart = ChartFactory.createBarChart(
		            "Gene Expression Plot ("+gene.toUpperCase()+")",         // chart title
		            "Groups",               // domain axis label
		            "Mean Expression Intensity",                  // range axis label
		            fdataset,                  // data
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
			axis.setCategoryMargin(0.20); // 20 percent
			axis.setUpperMargin(0.02); // two percent
			
			
			//same for our fake chart - just to get the tooltips
			fchart.setBackgroundPaint(java.awt.Color.white);
			CategoryPlot fplot = fchart.getCategoryPlot();
			CategoryAxis faxis = fplot.getDomainAxis();
			faxis.setLowerMargin(0.02); // two percent
			faxis.setCategoryMargin(0.20); // 20 percent
			faxis.setUpperMargin(0.02); // two percent
			
			
			//customise the renderer...
	        StatisticalBarRenderer renderer = new StatisticalBarRenderer();
	        
			//BarRenderer renderer = (BarRenderer) plot.getRenderer();
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
			
			
			//customize the fake renderer
				BarRenderer frenderer = (BarRenderer) fplot.getRenderer();
				frenderer.setItemMargin(0.01); // one percent
				frenderer.setDrawBarOutline(true);
				frenderer.setOutlinePaint(Color.BLACK);
				frenderer.setToolTipGenerator(new CategoryToolTipGenerator() {

		            public String generateToolTip(CategoryDataset dataset, int series, int item) {
		               HashMap pv = gpds.getPValuesHashMap();
		               String currentPV = (String) pv.get(dataset.getRowKey(series)+"::"+dataset.getColumnKey(item));
		               return "Probeset : " + dataset.getRowKey(series)+ "<br/>Intensity : " + dataset.getValue(series, item) +"<br>PVALUE : "+currentPV+"<br/>";
		            }
		           
		        });
			
			// LegendTitle lg = chart.getLegend();
			plot.setRenderer(renderer);
		
			// lets generate a custom legend - assumes theres only one source?
			LegendItemCollection lic = chart.getLegend().getSources()[0].getLegendItems();
			legendHtml = LegendCreator.buildLegend(lic, "Probesets");
			//pw.print(LegendCreator.buildLegend(lic, "Probesets"));
			
			chart.removeLegend();
			fchart.removeLegend();
			
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, 650, 400, info, session);
	        
			//clear the first one and overwrite info with our second one - no error bars
			info.clear(); //lose the first one
			fplot.setRenderer(frenderer);
			ffilename = ServletUtilities.saveChartAsPNG(fchart, 650, 400, info, session);

			//  Write the image map to the PrintWriter
			//can use a different writeImageMap to pass tooltip and URL custom

			ChartUtilities.writeImageMap(pw, filename, info, new CustomOverlibToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator() );
			//ChartUtilities.writeImageMap(pw, filename, info, true);

			pw.flush();

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		//return filename;
		charts.put("errorBars", filename);
		charts.put("noErrorBars", ffilename);
		charts.put("legend", legendHtml);
		
		return charts;
		
	}


}