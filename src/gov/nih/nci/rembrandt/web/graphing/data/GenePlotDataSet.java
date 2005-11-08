package gov.nih.nci.rembrandt.web.graphing.data;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
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
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
/**
 * @author landyr
 *
 */
public class GenePlotDataSet {

//	protected DefaultCategoryDataset dataset_old = new DefaultCategoryDataset();
	protected DefaultBoxAndWhiskerCategoryDataset bwdataset = new DefaultBoxAndWhiskerCategoryDataset();
	
	protected DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
	protected DefaultCategoryDataset fdataset = new DefaultCategoryDataset();

	private Logger logger = Logger.getLogger(GenePlotDataSet.class);
	protected HashMap pValues = new HashMap();
	
	   public GenePlotDataSet() throws ParseException {
/*
		   //		 row keys... each per cluster
	        String series1 = "Probeset1";
	        String series2 = "Probeset2";
	        String series3 = "Probeset3";


	        // column keys...  "clustered" bars
	        String category1 = "GBM";
	        String category2 = "Olig";
	        String category3 = "Astroc";
	        String category4 = "Mixed";
	        String category5 = "All";
	        String category6 = "Non-tumor";

	        // create the dataset...

	        dataset_old.addValue(1.0, series1, category1);
	        dataset_old.addValue(4.0, series1, category2);
	        dataset_old.addValue(3.0, series1, category3);
	        dataset_old.addValue(5.0, series1, category4);
	        dataset_old.addValue(5.0, series1, category5);
	        dataset_old.addValue(7.0, series1, category6);

	        dataset_old.addValue(5.0, series2, category1);
	        dataset_old.addValue(7.0, series2, category2);
	        dataset_old.addValue(6.0, series2, category3);
	        dataset_old.addValue(8.0, series2, category4);
	        dataset_old.addValue(4.0, series2, category5);
	        dataset_old.addValue(5.0, series2, category6);

	        dataset_old.addValue(4.0, series3, category1);
	        dataset_old.addValue(3.0, series3, category2);
	        dataset_old.addValue(2.0, series3, category3);
	        dataset_old.addValue(3.0, series3, category4);
	        dataset_old.addValue(6.0, series3, category5);
	        dataset_old.addValue(2.0, series3, category6);	
	    */
	    }
	   
	   /**
	    * real deal, takes in the gene and build the jfree data set
	    * @param gene
	    */
	   public GenePlotDataSet(String sgene)	{
		   
		   	String gene = sgene;
		   	String geneSymbol = "";

			GeneExpressionQuery geneQuery;
			GeneIDCriteria geneCrit = new GeneIDCriteria();
			geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
			geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
			geneQuery.setQueryName("GeneExpressionPlot");
			geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
			geneQuery.setGeneIDCrit(geneCrit);
			geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
			Resultant resultant = null;
			try {
				resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);
			} catch (Exception e) {
				logger.error("Resultset Manager Threw an Exception in gene plot");
				logger.error(e);
			}
			if (resultant != null) {
				ResultsContainer resultsContainer = resultant.getResultsContainer();
			
				//if instanceof ...
				GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;
				final DecimalFormat resultFormat = new DecimalFormat("0.00");
				final DecimalFormat pValueFormat = new DecimalFormat("0.0000");
				logger.debug("Gene:"+ geneExprDiseasePlotContainer.getGeneSymbol());
				geneSymbol = geneExprDiseasePlotContainer.getGeneSymbol().getValue().toString();
				
				//hold our categories and series
				//ArrayList catArrayList = new ArrayList();
				//ArrayList serArrayList = new ArrayList();
				
				Collection diseases = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultsets();
				
				//start ref
				String[] groups = new String[diseases.size()];
				
				int probeSetSize = 0;
				int diseaseSize = 0;
				
				diseaseSize = diseases.size();
				for (Iterator diseasesIterator = diseases.iterator(); diseasesIterator.hasNext();) {
					DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset) diseasesIterator.next();
					Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
					probeSetSize = reporters.size();
				}
				
				//now we know how many diseases adn how many probeset/reporter per disease

				int icounter = 0;
				DiseaseTypeLookup[] diseaseTypes = null;
				try {
					diseaseTypes = LookupManager.getDiseaseType();
				} catch (Exception e) {
					logger.debug("cant get diseases from lookup");
				}
				
				//loop through each disease
				for(int i = 0; i < diseaseTypes.length; i++) {

					DiseaseGeneExprPlotResultset diseaseResultset = geneExprDiseasePlotContainer
							.getDiseaseGeneExprPlotResultset(diseaseTypes[i].getDiseaseType().toString());

					String diseaseName = diseaseResultset.getType().getValue().toString();

					//concat for ASTRO for some reason
					if (diseaseName.equalsIgnoreCase(RembrandtConstants.ASTRO)) {
						groups[icounter] = diseaseName.substring(0, 6);
					} else {
						groups[icounter] = diseaseName;
					}

					Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); 

					String[] probeSets = new String[reporters.size()];
					double[] intensityValues = new double[reporters.size()];

					int counter = 0;
					for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
						ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset) reporterIterator.next();
						String reporterName = reporterResultset.getReporter().getValue().toString();
						
						Double intensityValue = (Double) reporterResultset.getFoldChangeIntensity().getValue();

						Double pvalue = (Double) reporterResultset.getRatioPval().getValue();

						//fill up our lists
						probeSets[counter] = reporterName;
						intensityValues[counter] = intensityValue.doubleValue();
						
						if (diseaseResultset.getType().getValue().toString().compareTo(RembrandtConstants.NORMAL) == 0) {
							pValues.put(reporterName+"::"+diseaseName, "N/A");
						} else {
							pValues.put(reporterName+"::"+diseaseName, pValueFormat.format(pvalue));
						}
						
						
						double stdDev = 200; //for testing purposes
						
						//the money = actually build the jfree dataset
						dataset.add(intensityValue.doubleValue(), stdDev, reporterName, diseaseName);
						fdataset.addValue(intensityValue.doubleValue(), reporterName, diseaseName);
						
						//dataset.addValue(intensityValue.doubleValue(), reporterName, diseaseName);
					
						counter++;
					}
					icounter++;
				}	
			}
	   }
	   
	   public DefaultStatisticalCategoryDataset getDataSet() {
		   return dataset;
	   }
	   
	   public HashMap getPValuesHashMap()	{
		   return pValues;
	   }

	public DefaultCategoryDataset getFdataset() {
		return fdataset;
	}
	   
}
