/*
 *  @author: SahniH
 *  Created on Nov 10, 2004
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
