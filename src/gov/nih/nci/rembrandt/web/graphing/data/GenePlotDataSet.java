package gov.nih.nci.rembrandt.web.graphing.data;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.GeneExpressionDataSetType;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.UnifiedGeneExpressionQuery;
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
import java.util.ArrayList;
import java.util.Arrays;
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

public class GenePlotDataSet {

	//this DataSet holds 3 JFree DataSets (1 per GeneExpressionPlotType)
	// 1) this one holds the data for the BoxAndWhisker Plot
	protected DefaultBoxAndWhiskerCategoryDataset bwdataset = new DefaultBoxAndWhiskerCategoryDataset();
	// 2) this one holds the data for the LOG2 plot, w/std deviation error bars
	protected DefaultStatisticalCategoryDataset log2Dataset = new DefaultStatisticalCategoryDataset();
	// 3) this one holds the data for the RAW plot
	protected DefaultCategoryDataset rawDataset = new DefaultCategoryDataset();

	private static Logger logger = Logger.getLogger(GenePlotDataSet.class);
	protected HashMap pValues = new HashMap();
	protected HashMap stdDevMap = new HashMap();
	
	   public GenePlotDataSet() throws ParseException { }
	   
	   /**
	    * takes in the gene and build the jfree data set
	    * @param gene
	    */
	   public GenePlotDataSet(String gene,InstitutionCriteria institutionCriteria ,GeneExpressionDataSetType geneExpressionDataSetType )	{
		   
		   //Determine which type of query we will need to run based on the algorithm we are passing in (GeneExpressionDataSetType enum)
			Resultant resultant = null;
			try{
			    switch (geneExpressionDataSetType){
			    case GeneExpressionDataSet:
			    default:{  
					   	GeneExpressionQuery geneQuery;
						GeneIDCriteria geneCrit = new GeneIDCriteria();
						geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
						geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
						geneQuery.setQueryName("GeneExpressionPlot");
						geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
						geneQuery.setGeneIDCrit(geneCrit);
						geneQuery.setInstitutionCriteria(institutionCriteria);
						geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
		
						resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);
				    	break;
				    }
			    case UnifiedGeneExpressionDataSet:{
					   	UnifiedGeneExpressionQuery unifiedGeneQuery;
						GeneIDCriteria geneCrit = new GeneIDCriteria();
						geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
						unifiedGeneQuery = (UnifiedGeneExpressionQuery) QueryManager.createQuery(QueryType.UNIFIED_GENE_EXPR_QUERY_TYPE);
						unifiedGeneQuery.setQueryName("UnifiedGeneExpressionPlot");
						unifiedGeneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
						unifiedGeneQuery.setGeneIDCrit(geneCrit);
						unifiedGeneQuery.setInstitutionCriteria(institutionCriteria);
						//unifiedGeneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
		
						resultant = ResultsetManager.executeGeneExpressPlotQuery(unifiedGeneQuery);
				    	break;
		
				    }
			    }
			} catch (Exception e) {
				logger.error("Resultset Manager Threw an Exception in gene plot");
				logger.error(e);
			}
			if (resultant != null) {
				ResultsContainer resultsContainer = resultant.getResultsContainer();
			
				//if instanceof ...
				GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;
				final DecimalFormat pValueFormat = new DecimalFormat("0.0000");
				logger.debug("Gene:"+ geneExprDiseasePlotContainer.getGeneSymbol());
				String geneSymbol = geneExprDiseasePlotContainer.getGeneSymbol().getValue().toString();
				
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
					if(diseaseResultset != null){
						Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
						probeSetSize = reporters.size();
					}
				}
				
				//now we know how many diseases and how many probeset/reporter per disease

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
						

						//using 1.5 autoboxing to convert Double to double
						double stdDev = reporterResultset.getStandardDeviationRatio()!=null ? (Double) reporterResultset.getStandardDeviationRatio().getValue() : 0;
						//fill up our lists
						probeSets[counter] = reporterName;
						intensityValues[counter] = intensityValue.doubleValue();
						
						if (diseaseResultset.getType().getValue().toString().compareTo(RembrandtConstants.NON_TUMOR) == 0) {
							pValues.put(reporterName+"::"+diseaseName, "N/A");
						} else {
							pValues.put(reporterName+"::"+diseaseName, pValueFormat.format(pvalue));
						}
						
						stdDevMap.put(reporterName+"::"+diseaseName, pValueFormat.format(stdDev));
						
						//the money = actually build the jfree dataset
						
						//LOG2 w/STD Dev
						log2Dataset.add( (Math.log(intensityValue.doubleValue())/Math.log(2)), stdDev, reporterName, diseaseName);
						
						//RAW
						rawDataset.addValue(intensityValue.doubleValue(), reporterName, diseaseName);
						
						//TESTING
						ArrayList tlist = new ArrayList();
						for (int k = 0; k < 25; k++) {
		                    final double value1 = 10.0 + Math.random() * 3;
		                    tlist.add(new Double(value1));
		                    final double value2 = 11.25 + Math.random(); // concentrate values in the middle
		                    tlist.add(new Double(value2));
		                }
						//B&W
						bwdataset.add(tlist, reporterName, diseaseName);
						
						counter++;
					}
					
					icounter++;
				}	
			}
	   }
	   
	   public DefaultStatisticalCategoryDataset getLog2Dataset() {
		   return log2Dataset;
	   }
	   
	   public HashMap getPValuesHashMap()	{
		   return pValues;
	   }
	   
	   public HashMap getStdDevMap()	{
		   return stdDevMap;
	   }

	public DefaultCategoryDataset getRawDataset() {
		return rawDataset;
	}

	public DefaultBoxAndWhiskerCategoryDataset getBwdataset() {
		return bwdataset;
	}
	   
}
