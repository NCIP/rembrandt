/**
 * 
 */
package gov.nih.nci.nautilus.ui.graph.geneExpression;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.lookup.DiseaseTypeLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.DiseaseGeneExprPlotResultset;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * @author landyr
 *
 */
public class GenePlotDataSet {

	protected DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	private Logger logger = Logger.getLogger(GeneExpressionGraphGenerator.class);
	protected HashMap pValues = new HashMap();
	
	   public GenePlotDataSet() throws ParseException {
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

	        dataset.addValue(1.0, series1, category1);
	        dataset.addValue(4.0, series1, category2);
	        dataset.addValue(3.0, series1, category3);
	        dataset.addValue(5.0, series1, category4);
	        dataset.addValue(5.0, series1, category5);
	        dataset.addValue(7.0, series1, category6);

	        dataset.addValue(5.0, series2, category1);
	        dataset.addValue(7.0, series2, category2);
	        dataset.addValue(6.0, series2, category3);
	        dataset.addValue(8.0, series2, category4);
	        dataset.addValue(4.0, series2, category5);
	        dataset.addValue(5.0, series2, category6);

	        dataset.addValue(4.0, series3, category1);
	        dataset.addValue(3.0, series3, category2);
	        dataset.addValue(2.0, series3, category3);
	        dataset.addValue(3.0, series3, category4);
	        dataset.addValue(6.0, series3, category5);
	        dataset.addValue(2.0, series3, category6);	
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
				ArrayList catArrayList = new ArrayList();
				ArrayList serArrayList = new ArrayList();
				
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
					//add the disease name to our list
					//catArrayList.add(diseaseName);
					
					//stringBuffer.append(diseaseName + "\n");

					//concat for ASTRO for some reason
					if (diseaseName.equalsIgnoreCase(gov.nih.nci.nautilus.constants.NautilusConstants.ASTRO)) {
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
						//add the reporter to the list
						//serArrayList.add(reporterName);
						
						Double intensityValue = (Double) reporterResultset.getFoldChangeIntensity().getValue();
						Double pvalue = (Double) reporterResultset.getRatioPval().getValue();
						/*
						if (diseaseResultset.getType().getValue().toString().compareTo(NautilusConstants.NORMAL) == 0) {
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
						*/
						//fill up our lists
						probeSets[counter] = reporterName;
						intensityValues[counter] = intensityValue.doubleValue();
						
						if (diseaseResultset.getType().getValue().toString().compareTo(NautilusConstants.NORMAL) == 0) {
							pValues.put(reporterName+"::"+diseaseName, "N/A");
						} else {
							pValues.put(reporterName+"::"+diseaseName, pValueFormat.format(pvalue));
						}
						
						
						//the money
						dataset.addValue(intensityValue.doubleValue(), reporterName, diseaseName);
						
						/*
						intValuesArray[counter][icounter] = intensityValue.doubleValue();
						logger.debug("intValuesArray[" + counter + "]["
								+ icounter + "] = "
								+ intensityValue.doubleValue());
						*/
						counter++;
					}
					icounter++;
				}
				
				
				
				/*
				boolean setSeries = false;
				int catCounter = 0;
				int serCounter = 0;
				
				//for each disease, add a category to the category Array 
				for (Iterator diseasesIterator = diseases.iterator(); diseasesIterator.hasNext();) {
					
					DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset) diseasesIterator.next();
					String diseaseName = diseaseResultset.getType().getValue().toString();
					
					catArrayList.add(new String(diseaseName));
					
					Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets();
					//for each reporter add a series, then add the data point
					for (Iterator reportersIterator = reporters.iterator(); reportersIterator.hasNext();) {
						
						ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset) reportersIterator.next();
						String reporterName = reporterResultset.getReporter().getValue().toString();
						
						if(!setSeries)	{
							//only do this once - not once for every disease
							serArrayList.add(reporterName);
						}
						
						Double intensityValue = (Double) reporterResultset.getFoldChangeIntensity().getValue();
						Double pvalue = (Double) reporterResultset.getRatioPval().getValue();
						
						dataset.addValue(intensityValue, (String) serArrayList.get(serCounter), (String) catArrayList.get(catCounter));
						serCounter++;
					}
					setSeries = true;
					catCounter++;

				}
				*/
				
			}

		   //define your series (items per group)
		   
		   //define your categories/groups
		   
		   //add to the data set
	   }
	   
	   public DefaultCategoryDataset getDataSet() {
		   return dataset;
	   }
	   
	   public HashMap getPValuesHashMap()	{
		   return pValues;
	   }
	   
}
