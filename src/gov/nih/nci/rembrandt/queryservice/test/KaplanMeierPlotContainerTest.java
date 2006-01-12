package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot.SampleKaplanMeierPlotResultset;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author SahniH
 * Date: Nov 15, 2004
 * 
 */
public class KaplanMeierPlotContainerTest extends TestCase {
	GeneExpressionQuery geneQuery;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    private void buildKaplanMeierPlotQuery(){
        GeneIDCriteria geneCrit = new GeneIDCriteria();
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol("VEGF"));
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("KaplanMeierPlot");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));
    }

	/**
	 * @param geneExprDiseasePlotContainer
	 */

	public void testGetSampleKaplanMeierPlotResultsets() {
/*		//test Single Query
		try {
			buildKaplanMeierPlotQuery();
			System.out.println("Building  Kaplan Meier Plot Query>>>>>>>>>>>>>>>>>>>>>>>");
			Resultant resultant = ResultsetManager.executeKaplanMeierPlotQuery(geneQuery);
			if(resultant != null){
				System.out.println("Testing DbuildKaplanMeierPlotQuery Query >>>>>>>>>>>>>>>>>>>>>>>");
				ResultsContainer resultsContainer = resultant.getResultsContainer();
				System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
				if (resultsContainer instanceof KaplanMeierPlotContainer){
					KaplanMeierPlotContainer kaplanMeierPlotContainer = (KaplanMeierPlotContainer) resultsContainer;
					System.out.println(">>>>Display All samples");
			        display(kaplanMeierPlotContainer.getBioSpecimenResultsets());
			        displatKMDrawingPoint(kaplanMeierPlotContainer.getBioSpecimenResultsets());
					System.out.println(">>>>Display Gene Express for UP Regulated 3 Folds");
			        ExprFoldChangeDE upRegulation = new ExprFoldChangeDE.UpRegulation(new Float(3.0));
		    		display(kaplanMeierPlotContainer.getSampleKaplanMeierPlotResultsets(upRegulation));
			        displatKMDrawingPoint(kaplanMeierPlotContainer.getSampleKaplanMeierPlotResultsets(upRegulation));
			        System.out.println(">>>>Display Gene Express for Down Regulated 3 Folds");
			        ExprFoldChangeDE downRegulation = new ExprFoldChangeDE.DownRegulation(new Float(3.0));
		    		display(kaplanMeierPlotContainer.getSampleKaplanMeierPlotResultsets(downRegulation));
			        displatKMDrawingPoint(kaplanMeierPlotContainer.getSampleKaplanMeierPlotResultsets(downRegulation));

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
*/
	}


	/**
	 * @param kaplanMeierPlotContainer
	 */
	private void display(Collection samples) {
		if(samples != null){
			System.out.println("Number of Gene Expression Sample:"+samples.size());
    		System.out.println("ID: \tSurvival Length: \tCensor Status:" );

		   	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
	    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
	    		System.out.println(sample.getSampleIDDE().getValue()+
									" \t"+sample.getSurvivalLength()+
	    							" \t"+sample.getCensor().getValue());
		   	}
		}
		
	}


	public void displatKMDrawingPoint(Collection samples) {
/*		KaplanMeier km = new KaplanMeier(samples); 
		KMDrawingPoint[] points = km.getDrawingPoints(); 
		System.out.println("\nOutput points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isCensus()); 
		}
*/
	}

}
