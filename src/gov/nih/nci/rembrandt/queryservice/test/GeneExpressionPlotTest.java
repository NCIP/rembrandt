package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
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
import gov.nih.nci.rembrandt.util.ApplicationContext;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author SahniH
 * Date: Nov 10, 2004
 * 
 */
public class GeneExpressionPlotTest extends TestCase {
	GeneExpressionQuery geneQuery;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext.init();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    private void buildGeneExprDiseasePlotQuery(){
        GeneIDCriteria geneCrit = new GeneIDCriteria();
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol("VEGF"));
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("GeneExpressionPlot");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
        
    }
	public void testGeneExprDiseaseView(){
		//test Single Query
		try {
				for(int i = 0; i < 2000; i++){
					System.out.println("Count:"+i);
				buildGeneExprDiseasePlotQuery();
				System.out.println("Building  Gene Expression Plot Query>>>>>>>>>>>>>>>>>>>>>>>");
				Resultant resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);
				//System.out.println("DiseaseQuery:\n"+ geneQuery.toString());
				//assertNotNull(resultant);
				if(resultant != null){
					System.out.println("Testing Disease Gene Query >>>>>>>>>>>>>>>>>>>>>>>");
					//System.out.println("Associated Query/n"+resultant.getAssociatedQuery());
					ResultsContainer resultsContainer = resultant.getResultsContainer();
					System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
					if (resultsContainer instanceof GeneExprDiseasePlotContainer){
						GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;
				        displayGeneExprPlotData(geneExprDiseasePlotContainer);
	
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

}

	/**
	 * @param geneExprDiseasePlotContainer
	 * @throws Exception
	 */
	private void displayGeneExprPlotData(GeneExprDiseasePlotContainer geneExprDiseasePlotContainer) throws Exception {
		System.out.println("inside display diease");
		final DecimalFormat resultFormat = new DecimalFormat("0.0000");	
		assertNotNull(geneExprDiseasePlotContainer);
		System.out.println("Gene:"+geneExprDiseasePlotContainer.getGeneSymbol());
		
    	Collection diseases = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultsets();
    	StringBuffer header = new StringBuffer();
    	//get group size (as Disease or Agegroup )from label.size
        String label = null;
    	
        //set up the header for the table
    	header.append("Diseases\tReporter Name\tIntensity\tPvalue");
		System.out.println(header.toString());
		DiseaseTypeLookup[] diseaseTypes = LookupManager.getDiseaseType();
		for(int i = 0; i< diseaseTypes.length ; i++){
			System.out.println("id :"+diseaseTypes[i].getDiseaseTypeId()+"\tType: "+diseaseTypes[i].getDiseaseType()+"\tDesc :"+diseaseTypes[i].getDiseaseDesc() );
			DiseaseNameDE disease = new DiseaseNameDE(diseaseTypes[i].toString());
    		DiseaseGeneExprPlotResultset diseaseResultset = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultset(diseaseTypes[i].getDiseaseType().toString());
    		String diseaseName = diseaseResultset.getType().getValue().toString();
    		System.out.println(diseaseName);
    		Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
    			ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset)reporterIterator.next();
        		String reporterName = reporterResultset.getReporter().getValue().toString();
       			Double intensityValue = (Double)reporterResultset.getFoldChangeIntensity().getValue();
       			Double pvalue = (Double)reporterResultset.getRatioPval().getValue();
       			System.out.println(reporterName+"\t"+resultFormat.format(intensityValue)+"\t"+resultFormat.format(pvalue));  
    		}

		}
	}
}
