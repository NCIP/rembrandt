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
