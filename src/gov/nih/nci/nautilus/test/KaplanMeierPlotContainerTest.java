/*
 *  @author: SahniH
 *  Created on Nov 15, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
package gov.nih.nci.nautilus.test;

import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.graph.kaplanMeier.KMDrawingPoint;
import gov.nih.nci.nautilus.graph.kaplanMeier.KaplanMeier;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.SampleKaplanMeierPlotResultset;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
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
		//test Single Query
		try {
			buildKaplanMeierPlotQuery();
			System.out.println("Building  Kaplan Meier Plot Query>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery compoundQuery = new CompoundQuery(geneQuery);
			Resultant resultant = ResultsetManager.executeKaplanMeierPlotQuery(compoundQuery);
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
	    		System.out.println(sample.getBiospecimen().getValue()+
									" \t"+sample.getSurvivalLength().getValue()+
	    							" \t"+sample.getCensor().getValue());
		   	}
		}
		
	}


	public void displatKMDrawingPoint(Collection samples) {
		KaplanMeier km = new KaplanMeier(samples); 
		KMDrawingPoint[] points = km.getDrawingPoints(); 
		System.out.println("\nOutput points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isCensus()); 
		}

	}

}
