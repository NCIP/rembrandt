package gov.nih.nci.rembrandt.web.graphing.data;

import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.enumeration.GeneExpressionDataSetType;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.legend.LegendCreator;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class GeneExpressionPlot {

	public static HashMap generateBarChart(String gene, HttpSession session,
			PrintWriter pw, GeneExpressionDataSetType geType) {
		String filename = null;
		String ffilename = null;
		String legendHtml = null;
		HashMap charts = new HashMap();

		try {
			InstitutionCriteria institutionCriteria = InsitutionAccessHelper.getInsititutionCriteria(session);

			final GenePlotDataSet gpds = new GenePlotDataSet(gene, institutionCriteria, geType);
			//final GenePlotDataSet gpds = new GenePlotDataSet(gene, institutionCriteria,GeneExpressionDataSetType.GeneExpressionDataSet );

			DefaultStatisticalCategoryDataset dataset = (DefaultStatisticalCategoryDataset) gpds.getDataSet();
			CategoryDataset fdataset = (CategoryDataset) gpds.getFdataset();

			// create the chart...
			JFreeChart chart = ChartFactory.createBarChart(
					"Gene Expression Plot (" + gene.toUpperCase() + ")", // chart
																			// title
					"Groups", // domain axis label
					"Mean Expression Intensity", // range axis label
					dataset, // data
					PlotOrientation.VERTICAL, // orientation
					true, // include legend
					true, // tooltips?
					false // URLs?
					);

			/**
			 * okay, heres where it gets tricky... we need to generate another
			 * chart, with a seperate dataset - one that does not include the
			 * errorbars for std deviation we generate this fchart (aka "fake
			 * chart", then use it later to map the coords for the tooltip. we
			 * will need to customize the fchart and the frenderer, faxis ext to
			 * look exactly like the current chart w/error bars in order for the
			 * coords to match. In the end, we will display the image w/the
			 * error bars, but render the map coords of the image w/o the error
			 * bars.... confusing, but the only way to add tooltips to the chart
			 * w/errorbars without rewriting components of the api. this is
			 * actually not a complete waste, because it will allow us to toggle
			 * between showing chart w/ and w/o the errorbars
			 * 
			 * -RL
			 */
			JFreeChart fchart = ChartFactory.createBarChart(
					"Gene Expression Plot (" + gene.toUpperCase() + ")", // chart
																			// title
					"Groups", // domain axis label
					"Mean Expression Intensity", // range axis label
					fdataset, // data
					PlotOrientation.VERTICAL, // orientation
					true, // include legend
					true, // tooltips?
					false // URLs?
					);

			chart.setBackgroundPaint(java.awt.Color.white);
			// lets start some customization to retro fit w/jcharts lookand feel
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis axis = plot.getDomainAxis();
			axis.setLowerMargin(0.02); // two percent
			axis.setCategoryMargin(0.20); // 20 percent
			axis.setUpperMargin(0.02); // two percent

			// same for our fake chart - just to get the tooltips
			fchart.setBackgroundPaint(java.awt.Color.white);
			CategoryPlot fplot = fchart.getCategoryPlot();
			CategoryAxis faxis = fplot.getDomainAxis();
			faxis.setLowerMargin(0.02); // two percent
			faxis.setCategoryMargin(0.20); // 20 percent
			faxis.setUpperMargin(0.02); // two percent

			// customise the renderer...
			StatisticalBarRenderer renderer = new StatisticalBarRenderer();

			// BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setItemMargin(0.01); // one percent
			renderer.setDrawBarOutline(true);
			renderer.setOutlinePaint(Color.BLACK);
			renderer.setToolTipGenerator(new CategoryToolTipGenerator() {

				public String generateToolTip(CategoryDataset dataset,
						int series, int item) {
					HashMap pv = gpds.getPValuesHashMap();
					HashMap std_d = gpds.getStdDevMap();
					
					String currentPV = (String) pv.get(dataset
							.getRowKey(series)
							+ "::" + dataset.getColumnKey(item));
					String stdDev = (String) std_d.get(dataset
							.getRowKey(series)
							+ "::" + dataset.getColumnKey(item));
					
					return "Probeset : " + dataset.getRowKey(series)
							+ "<br/>Intensity : "
							+ dataset.getValue(series, item) + "<br/>PVALUE : "
							+ currentPV + "<br/>Std. Dev.: " + stdDev + "<br/>";
				}

			});

			// customize the fake renderer
			BarRenderer frenderer = (BarRenderer) fplot.getRenderer();
			frenderer.setItemMargin(0.01); // one percent
			frenderer.setDrawBarOutline(true);
			frenderer.setOutlinePaint(Color.BLACK);
			frenderer.setToolTipGenerator(new CategoryToolTipGenerator() {

				public String generateToolTip(CategoryDataset dataset,
						int series, int item) {
					HashMap pv = gpds.getPValuesHashMap();
					HashMap std_d = gpds.getStdDevMap();
					String currentPV = (String) pv.get(dataset
							.getRowKey(series)
							+ "::" + dataset.getColumnKey(item));
					String stdDev = (String) std_d.get(dataset
							.getRowKey(series)
							+ "::" + dataset.getColumnKey(item));
					return "Probeset : " + dataset.getRowKey(series)
							+ "<br/>Intensity : "
							+ dataset.getValue(series, item) + "<br>PVALUE : "
							+ currentPV + "<br/>Std. Dev.: " + stdDev + "<br/>";
				}

			});

			// LegendTitle lg = chart.getLegend();
			plot.setRenderer(renderer);

			// lets generate a custom legend - assumes theres only one source?
			LegendItemCollection lic = chart.getLegend().getSources()[0]
					.getLegendItems();
			legendHtml = LegendCreator.buildLegend(lic, "Probesets");
			// pw.print(LegendCreator.buildLegend(lic, "Probesets"));

			chart.removeLegend();
			fchart.removeLegend();

			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, 650, 400, info,
					session);

			// clear the first one and overwrite info with our second one - no
			// error bars
			info.clear(); // lose the first one
			fplot.setRenderer(frenderer);
			ffilename = ServletUtilities.saveChartAsPNG(fchart, 650, 400, info,
					session);

			// Write the image map to the PrintWriter
			// can use a different writeImageMap to pass tooltip and URL custom

			ChartUtilities.writeImageMap(pw, filename, info,
					new CustomOverlibToolTipTagFragmentGenerator(),
					new StandardURLTagFragmentGenerator());
			// ChartUtilities.writeImageMap(pw, filename, info, true);

			pw.flush();

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		// return filename;
		charts.put("errorBars", filename);
		charts.put("noErrorBars", ffilename);
		charts.put("legend", legendHtml);

		return charts;
	}
}
