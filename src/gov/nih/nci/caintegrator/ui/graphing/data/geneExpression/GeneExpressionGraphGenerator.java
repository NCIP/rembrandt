package gov.nih.nci.caintegrator.ui.graphing.data.geneExpression;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.DiseaseGeneExprPlotResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.view.ViewFactory;
import gov.nih.nci.rembrandt.queryservice.view.ViewType;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.struts.form.QuickSearchForm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.AxisChartDataSet;
import org.krysalis.jcharts.chartData.DataSeries;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.AxisTypeProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.ClusteredBarChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.types.ChartType;

public class GeneExpressionGraphGenerator {

	private Logger logger = Logger.getLogger(GeneExpressionGraphGenerator.class);

	private String[] groups;

	private String geneSymbol;

	private String[] probeSets = new String[0];

	private double[] intensityValues = new double[0];

	private double[][] intValuesArray = new double[0][0];

	private ArrayList pValues = new ArrayList();

	private HashMap plotData = new HashMap();

	private HashMap xLegend = new HashMap();

	private ActionErrors errors = new ActionErrors();
    
    private ImageMap imageMap = null;
    
    private AxisChart axisChart = null;

	@SuppressWarnings("unchecked")
	public GeneExpressionGraphGenerator(ActionForm form) {

		QuickSearchForm qsForm = (QuickSearchForm) form;
		String gene = qsForm.getQuickSearchName();
		String chartType = qsForm.getPlot();
		GeneExpressionQuery geneQuery;
		GeneIDCriteria geneCrit = new GeneIDCriteria();
		geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
		geneQuery = (GeneExpressionQuery) QueryManager
				.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
		geneQuery.setQueryName("GeneExpressionPlot");
		geneQuery.setAssociatedView(ViewFactory
				.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
		geneQuery.setGeneIDCrit(geneCrit);
		geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(
				new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
		Resultant resultant = null;
				
		try {
			resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);
		} catch (Exception e) {
			logger.error("Resultset Manager Threw an Exception");
			logger.error(e);
		}
        if (resultant != null) {
			ResultsContainer resultsContainer = resultant.getResultsContainer();

			
			   if (resultsContainer instanceof GeneExprDiseasePlotContainer) {
				GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;
				final DecimalFormat resultFormat = new DecimalFormat("0.00");
				final DecimalFormat pValueFormat = new DecimalFormat("0.0000");
				logger.debug("Gene:"
						+ geneExprDiseasePlotContainer.getGeneSymbol());
				geneSymbol = geneExprDiseasePlotContainer.getGeneSymbol()
						.getValue().toString();

				Collection diseases = geneExprDiseasePlotContainer
						.getDiseaseGeneExprPlotResultsets();
				StringBuffer header = new StringBuffer();
				StringBuffer stringBuffer = new StringBuffer();
				header.append("Diseases\tReporter Name\tIntensity\tPvalue");
				logger.debug(header.toString());
				groups = new String[diseases.size()];
				int probeSetSize = 0;
				for (Iterator diseasesIterator = diseases.iterator(); diseasesIterator
						.hasNext();) {
					DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset) diseasesIterator
							.next();
					Collection reporters = diseaseResultset
							.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
					probeSetSize = reporters.size();
				}

				int icounter = 0;
				DiseaseTypeLookup[] diseaseTypes = null;
				try {
					diseaseTypes = LookupManager.getDiseaseType();
				} catch (Exception e) {

				}

                intValuesArray = new double[probeSetSize][diseaseTypes.length];
				logger.debug("set intValuesArray: [" + probeSetSize + "]["
						+ diseaseTypes.length + "]");

				for (int i = 0; i < diseaseTypes.length; i++) {

					logger.debug("id :" + diseaseTypes[i].getDiseaseTypeId()
							+ "\tType: " + diseaseTypes[i].getDiseaseType()
							+ "\tDesc :" + diseaseTypes[i].getDiseaseDesc());

					xLegend.put(diseaseTypes[i].getDiseaseType(),
							diseaseTypes[i].getDiseaseDesc());

					DiseaseNameDE disease = new DiseaseNameDE(diseaseTypes[i]
							.toString());
					//DiseaseGeneExprPlotResultset diseaseResultset =
					// (DiseaseGeneExprPlotResultset)diseasesIterator.next();
					DiseaseGeneExprPlotResultset diseaseResultset = geneExprDiseasePlotContainer
							.getDiseaseGeneExprPlotResultset(diseaseTypes[i]
									.getDiseaseType().toString());

					String diseaseName = diseaseResultset.getType().getValue()
							.toString();
					stringBuffer.append(diseaseName + "\n");

					if (diseaseName
							.equalsIgnoreCase(RembrandtConstants.ASTRO)) {
						groups[icounter] = diseaseName.substring(0, 6);
					} else {
						groups[icounter] = diseaseName;
					}

					Collection reporters = diseaseResultset
							.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();

					probeSets = new String[reporters.size()];
					intensityValues = new double[reporters.size()];

					int counter = 0;
					for (Iterator reporterIterator = reporters.iterator(); reporterIterator
							.hasNext();) {
						ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset) reporterIterator
								.next();
						String reporterName = reporterResultset.getReporter()
								.getValue().toString();
						Double intensityValue = (Double) reporterResultset
								.getFoldChangeIntensity().getValue();
						Double pvalue = (Double) reporterResultset
								.getRatioPval().getValue();
						if (diseaseResultset.getType().getValue().toString()
								.compareTo(RembrandtConstants.NORMAL) == 0) {
							stringBuffer.append(reporterName + "\t"
									+ resultFormat.format(intensityValue)
									+ "\tN/A\n");
						} else {
							stringBuffer
									.append(reporterName
											+ "\t"
											+ resultFormat
													.format(intensityValue)
											+ "\t"
											+ pValueFormat.format(pvalue)
											+ "\n");
						}
						//fill up our lists
						//probeSets.add(reporterName);
						probeSets[counter] = reporterName;
						//intensityValues[counter] =
						// Double.valueOf(resultFormat.format(intensityValue)).doubleValue();
						intensityValues[counter] = intensityValue.doubleValue();
						if (diseaseResultset.getType().getValue().toString()
								.compareTo(RembrandtConstants.NORMAL) == 0) {
							pValues.add("N/A");
						} else {
							pValues.add(pValueFormat.format(pvalue));
						}
						intValuesArray[counter][icounter] = intensityValue
								.doubleValue();
						logger.debug("intValuesArray[" + counter + "]["
								+ icounter + "] = "
								+ intensityValue.doubleValue());
						counter++;
					}
					//intValuesArray[icounter] = intensityValues;
					icounter++;
				}

				if (groups.length > 0 && probeSets.length > 0
						&& intValuesArray.length > 0) {

					//lets show the group names for fun
					for (int g = 0; g < groups.length; g++)
						logger.debug("group" + g + ": " + groups[g]);
					try {
						logger.debug("Generating the chart...");
						String[] xAxisLabels = groups;
						String xAxisTitle = "Groups";
						String yAxisTitle = "Mean Expression Intensity";
						String title = "Gene Expression Plot (" + geneSymbol
								+ ")";
						DataSeries dataSeries = new DataSeries(xAxisLabels,
								xAxisTitle, yAxisTitle, title);

						double[][] data = new double[probeSets.length][groups.length];
						logger
								.debug("creating data array size: ["
										+ probeSets.length + "]["
										+ groups.length + "]");

						data = intValuesArray;
						logger.debug("Data sets: " + data.length);
						logger.debug("Should be: " + probeSetSize);

						String[] legendLabels = probeSets;

						Paint[] paintsBigList = { Color.RED, Color.BLUE,
								Color.YELLOW, Color.LIGHT_GRAY, Color.GREEN,
								Color.ORANGE, Color.CYAN, Color.DARK_GRAY,
								Color.BLACK, Color.MAGENTA, Color.PINK };
						Paint[] paints = new Paint[probeSets.length];

						int paintIndex = 0;
						for (int i = 0; i < probeSets.length; i++, paintIndex++) {
							if (paintIndex > paintsBigList.length)
								paintIndex = i - paintsBigList.length;
							paints[paintIndex] = paintsBigList[i];
						}
						logger.debug("there are: " + paints.length + " colors");
						logger.debug("should be: " + probeSetSize);

						ClusteredBarChartProperties clusteredBarChartProperties = new ClusteredBarChartProperties();

						clusteredBarChartProperties.setWidthPercentage(.40f);
						AxisChartDataSet axisChartDataSet = new AxisChartDataSet(
								data, legendLabels, paints,
								ChartType.BAR_CLUSTERED,
								clusteredBarChartProperties);
						dataSeries.addIAxisPlotDataSet(axisChartDataSet);

						ChartProperties chartProperties = new ChartProperties();
						AxisProperties axisProperties = new AxisProperties();
						ChartFont xScaleChartFont = new ChartFont(new Font(
								"Georgia", Font.PLAIN, 10), Color.black);
						axisProperties.getXAxisProperties().setScaleChartFont(
								xScaleChartFont);
						ChartFont yScaleChartFont = new ChartFont(new Font(
								"Georgia", Font.PLAIN, 13), Color.black);
						axisProperties.getYAxisProperties().setScaleChartFont(
								yScaleChartFont);
						axisProperties.getYAxisProperties().setShowGridLines(
								AxisTypeProperties.GRID_LINES_ALL);
						axisProperties.getXAxisProperties().setShowTicks(
								AxisTypeProperties.TICKS_NONE);
						ChartFont titleFont = new ChartFont(new Font("Georgia",
								Font.PLAIN, 16), Color.black);
						chartProperties.setTitleFont(titleFont);
						LegendProperties legendProperties = new LegendProperties();
						legendProperties.setChartFont(new ChartFont(new Font(
								"Georgia", Font.PLAIN, 12), Color.black));
						legendProperties.setNumColumns(3);
						axisChart = new AxisChart(dataSeries,
								chartProperties, axisProperties,
								legendProperties, 600, 400);
						if (axisChart == null)
							logger.debug("No chart to put in session");
						axisChart.renderWithImageMap();
						imageMap = axisChart.getImageMap();
			
				} catch (Exception e) {
						logger.debug("error generating graph");
						logger.error(e);
						errors
								.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												"gov.nih.nci.caintegrator.ui.graphing.data.geneExpression.GraphError"));
					}
				} else {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("gov.nih.nci.caintegrator.ui.graphing.data.geneExpression.GraphError"));

				}
			} // instance of
		} // resultant != null
      else{
          logger.debug("resultant is null");
      }
	}

	/**
	 * @return Returns the xLegend.
	 */
	public HashMap getXLegend() {
		return xLegend;
	}

	/**
	 * @return Returns the pValues.
	 */
	public ArrayList getPValues() {
		return pValues;
	}
	
	/**
	 * @return Returns the imageMap.
	 */
	public ImageMap getImageMap() {
		return imageMap;
	}
	
	/**
	 * @return Returns the axisChart.
	 */
	public AxisChart getAxisChart() {
		return axisChart;
	}

	/**
	 * @return Returns the errors.
	 */
	public ActionErrors getErrors() {
		return errors;
	}
    
    public void setRequestAttributes(HttpServletRequest request) {
         request.setAttribute("xLegend", this.getXLegend());
         request.setAttribute("pValues", this.getPValues());
         request.setAttribute("map", this.getImageMap());
    }
   
    public void setSessionAttributes(HttpSession session) {
        session.setAttribute("chart", this.getAxisChart());
    }
	
}