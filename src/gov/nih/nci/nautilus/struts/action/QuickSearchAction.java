package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.DiseaseGeneExprPlotResultset;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;
import gov.nih.nci.nautilus.struts.form.KMDataSetForm;
import gov.nih.nci.nautilus.struts.form.QuickSearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.lookup.DiseaseTypeLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.view.*;
//import gov.nih.nci.nautilus.constants.Constants;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.criteria.Constants;

import java.awt.*;
import org.krysalis.jcharts.*;
import org.krysalis.jcharts.chartData.*;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.types.ChartType;
import org.krysalis.jcharts.axisChart.*;
import org.krysalis.jcharts.test.TestDataGenerator;
import org.krysalis.jcharts.encoders.JPEGEncoder13;
import org.krysalis.jcharts.encoders.*;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.imageMap.*;
import org.krysalis.jcharts.encoders.ServletEncoderHelper;

// RCL

public class QuickSearchAction extends DispatchAction {
    static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);

	/**
	 * Method execute
	 * 
	 * @param ActionMapping
	 *            mapping
	 * @param ActionForm
	 *            form
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward doGeneExpPlot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        String[] groups;
		
		String geneSymbol;
		
		String[] probeSets = new String[0];
		
		double[] intensityValues = new double[0];
		double[][] intValuesArray = new double[0][0];

		ArrayList pValues = new ArrayList();
		HashMap plotData = new HashMap();
		
		HashMap xLegend = new HashMap();
		
	   QuickSearchForm qsForm = (QuickSearchForm) form;	
	   String gene = qsForm.getQuickSearchName();
	   String chartType = qsForm.getPlot();
	   
	   logger.debug("chartType: " + chartType);
	     
	   GeneExpressionQuery geneQuery;
	   GeneIDCriteria geneCrit = new GeneIDCriteria();
       geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
       geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
       geneQuery.setQueryName("GeneExpressionPlot");
       geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
       geneQuery.setGeneIDCrit(geneCrit);
       geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));

       Resultant resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);

       if(resultant != null)	{
		ResultsContainer resultsContainer = resultant.getResultsContainer();
		
		if(resultsContainer == null)	{
			ActionErrors errors = new ActionErrors();
			ActionError ae = new ActionError("gov.nih.nci.nautilus.struts.action.quickSearch.badgene");
			errors.add(ActionErrors.GLOBAL_ERROR, ae);
			this.saveErrors(request, errors);
			logger.debug("invalid gene");
			logger.debug(errors.size() + " errors");
			return mapping.findForward("badgraph");
		}
			
		if (resultsContainer instanceof GeneExprDiseasePlotContainer)	{
			GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;

			final DecimalFormat resultFormat = new DecimalFormat("0.00");	
			final DecimalFormat pValueFormat = new DecimalFormat("0.0000");
			logger.debug("Gene:"+geneExprDiseasePlotContainer.getGeneSymbol());
			geneSymbol = geneExprDiseasePlotContainer.getGeneSymbol().getValue().toString();
			
	    	Collection diseases = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultsets();
	    	StringBuffer header = new StringBuffer();
	        StringBuffer stringBuffer = new StringBuffer();
	        String label = null;
	    	
	    	header.append("Diseases\tReporter Name\tIntensity\tPvalue");
	    	logger.debug(header.toString());
			
			groups = new String[diseases.size()];
			
			int probeSetSize = 0;
			int diseaseSize = 0;
			
			diseaseSize = diseases.size();
			
			for (Iterator diseasesIterator = diseases.iterator(); diseasesIterator.hasNext();) {
	    		DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset)diseasesIterator.next();
	    		Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
	    		probeSetSize=reporters.size();
			}
			
    		
    		int icounter = 0;
    		DiseaseTypeLookup[] diseaseTypes = LookupManager.getDiseaseType();
    		
    		intValuesArray = new double[probeSetSize][diseaseTypes.length];
    		logger.debug("set intValuesArray: ["+probeSetSize+"]["+diseaseTypes.length+"]");
    		
    		
	    	for (int i = 0; i< diseaseTypes.length ; i++) {
	    		
	    	    logger.debug("id :"+diseaseTypes[i].getDiseaseTypeId()+"\tType: "+diseaseTypes[i].getDiseaseType()+"\tDesc :"+diseaseTypes[i].getDiseaseDesc() );
	    		
	    		xLegend.put(diseaseTypes[i].getDiseaseType(), diseaseTypes[i].getDiseaseDesc());
	    		
	    		DiseaseNameDE disease = new DiseaseNameDE(diseaseTypes[i].toString());
	    		//DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset)diseasesIterator.next();
	    		DiseaseGeneExprPlotResultset diseaseResultset = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(diseaseTypes[i].getDiseaseType().toString());
	    		
	    		String diseaseName = diseaseResultset.getType().getValue().toString();
	    		stringBuffer.append(diseaseName+"\n");
	
		    		if(diseaseName.equalsIgnoreCase(gov.nih.nci.nautilus.constants.NautilusConstants.ASTRO))	{
		    			groups[icounter] = diseaseName.substring(0,6);
		    		}
		    		else	{
		    			groups[icounter] = diseaseName;
		    		}	
	    		
	    		Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();

	    		probeSets = new String[reporters.size()];
	    		intensityValues = new double[reporters.size()];

	    		int counter = 0;
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	    			ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset)reporterIterator.next();
	        		String reporterName = reporterResultset.getReporter().getValue().toString();
	       			Double intensityValue = (Double)reporterResultset.getFoldChangeIntensity().getValue();
	       			Double pvalue = (Double)reporterResultset.getRatioPval().getValue();
	       			if(diseaseResultset.getType().getValue().toString().compareTo(NautilusConstants.NORMAL)==0){
		       			stringBuffer.append(reporterName+"\t"+resultFormat.format(intensityValue)+"\tN/A\n");
	       			}
	       			else{
	       				stringBuffer.append(reporterName+"\t"+resultFormat.format(intensityValue)+"\t"+pValueFormat.format(pvalue)+"\n");
	       			}
	       			//fill up our lists
	       			//probeSets.add(reporterName);
	       			probeSets[counter] = reporterName;
	       			//intensityValues[counter] = Double.valueOf(resultFormat.format(intensityValue)).doubleValue();
	       			intensityValues[counter] = intensityValue.doubleValue();
	       			if(diseaseResultset.getType().getValue().toString().compareTo(NautilusConstants.NORMAL)==0){
	       				pValues.add("N/A");
	       			}
	       			else{
	       				pValues.add(pValueFormat.format(pvalue));
	       			}
	       			intValuesArray[counter][icounter] = intensityValue.doubleValue();
	       			logger.debug("intValuesArray["+counter+"]["+icounter+"] = "+intensityValue.doubleValue());
	       			counter ++;
	    		}
	    		//intValuesArray[icounter] = intensityValues;
	    		icounter++;
	    	}
	    	logger.debug(stringBuffer.toString());	
		
	 
	    	if( groups.length > 0 && probeSets.length > 0 && intValuesArray.length > 0 )	{

	    		
	    		//lets show the group names for fun
	    		for(int g=0; g<groups.length; g++)
	    		    logger.debug("group"+g+": "+groups[g]);
	       	try {
	       	 logger.debug("Generating the chart...");
		       	String[] xAxisLabels = groups;
		       	String xAxisTitle= "Groups";
		       	String yAxisTitle= "Mean Expression Intensity";
		       	String title= "Gene Expression Plot ("+geneSymbol+")";
		       	DataSeries dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, title );
	
		       	//double[][] data= new double[][]{ { 250, 45, 36, 66, 22 }, { 150, 15, 6, 62, 21 }, { 20, 145, 36, 6, 33 }, { 250, 45, 36, 66, 57 }, { 150, 15, 6, 62, 12 }, { 20, 145, 36, 6, 29 } };
		       	double[][] data = new double[probeSets.length][groups.length];
		       	logger.debug("creating data array size: ["+probeSets.length+"]["+groups.length+"]");
       	
		       	data = intValuesArray;
		       	logger.debug("Data sets: " + data.length);
		       	logger.debug("Should be: "+ probeSetSize);
		       	
		       	String[] legendLabels = probeSets;
		       	
		       	Paint[] paintsBigList= {Color.RED, Color.BLUE, Color.YELLOW, Color.LIGHT_GRAY, Color.GREEN, Color.ORANGE, Color.CYAN, Color.DARK_GRAY, Color.BLACK, Color.MAGENTA, Color.PINK };
		       	Paint[] paints = new Paint[probeSets.length];
		       	
		       	int paintIndex = 0;
		       	for(int i=0; i<probeSets.length; i++, paintIndex++)
		       	{
		       		if(paintIndex > paintsBigList.length)
		       			paintIndex = i-paintsBigList.length;
		       		paints[paintIndex] = paintsBigList[i];       		
		       	}
		       	logger.debug("there are: " + paints.length + " colors");
		       	logger.debug("should be: "+probeSetSize);
		       	
		       	ClusteredBarChartProperties clusteredBarChartProperties= new ClusteredBarChartProperties();
		       	
		       	clusteredBarChartProperties.setWidthPercentage( .40f );
		       	AxisChartDataSet axisChartDataSet= new AxisChartDataSet( data, legendLabels, paints, ChartType.BAR_CLUSTERED, clusteredBarChartProperties );
		       	dataSeries.addIAxisPlotDataSet( axisChartDataSet );
	
		       	ChartProperties chartProperties= new ChartProperties();
		       	AxisProperties axisProperties= new AxisProperties();
		       	ChartFont xScaleChartFont= new ChartFont( new Font( "Georgia", Font.PLAIN, 10 ), Color.black );
		       	axisProperties.getXAxisProperties().setScaleChartFont( xScaleChartFont );
		       	ChartFont yScaleChartFont= new ChartFont( new Font( "Georgia", Font.PLAIN, 13 ), Color.black );
		       	axisProperties.getYAxisProperties().setScaleChartFont( yScaleChartFont );
		       	axisProperties.getYAxisProperties().setShowGridLines( AxisTypeProperties.GRID_LINES_ALL );
		       	axisProperties.getXAxisProperties().setShowTicks( AxisTypeProperties.TICKS_NONE );
		       	ChartFont titleFont= new ChartFont( new Font( "Georgia", Font.PLAIN, 16 ), Color.black );
		       	chartProperties.setTitleFont(titleFont);
		       	LegendProperties legendProperties= new LegendProperties();
		       	legendProperties.setChartFont( new ChartFont( new Font( "Georgia", Font.PLAIN, 12 ), Color.black ) );
		       	legendProperties.setNumColumns( 3 );
		       	AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 600, 400 );
		       if(axisChart == null)
		           logger.debug("No chart to put in session");
		       	axisChart.renderWithImageMap();
		       	ImageMap imageMap= axisChart.getImageMap();
		       	
		       	request.setAttribute( "xLegend", xLegend );
		       	
		       	request.setAttribute( "pValues", pValues );
		       	request.setAttribute( "map", imageMap );
		       	request.getSession(true).setAttribute( "chart", axisChart );
		   }
		   catch(Exception e){
		       logger.debug("error generating graph");
			   	e.printStackTrace();
		       	return mapping.findForward("badgraph");
		   }
	       	// Good get the graph
		   logger.debug("Going to get the image...");
		   return mapping.findForward("histogram");	
       }
       else	{
       	//something went horribly wrong
       	// TODO: add some global error here
           logger.debug("Mismatch");
       	return mapping.findForward("mismatch");
       }
	} // instance of
} // resultant != null
		return mapping.findForward("error");
}
	   
    /***
     * This method gathers the input parameters from the quickSearchForm
     * and creates and forwards the request to the KMPlot jsp.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doKMPlot(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        KMDataSetForm kmForm = (KMDataSetForm)form;
        kmForm.setGeneSymbol((String)request.getAttribute("geneSymbol"));
        return mapping.findForward("kmplot");
    }

    public ActionForward redrawKMPlot(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        KMDataSetForm kmForm = (KMDataSetForm)form;
        return mapping.findForward("kmplot");
    }

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuickSearchForm qsForm = (QuickSearchForm) form;
		String chartType = qsForm.getPlot();
		if (chartType.equalsIgnoreCase("kapMaiPlot")) {
            request.setAttribute("geneSymbol",qsForm.getQuickSearchName());
			return mapping.findForward("kmplot");
		} else if (chartType.equalsIgnoreCase("geneExpPlot")) {
			return doGeneExpPlot(mapping, qsForm, request, response);
		} else {
			return mapping.findForward("error");
		}
	}

}