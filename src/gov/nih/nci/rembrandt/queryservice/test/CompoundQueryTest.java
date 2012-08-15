package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AssayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.CopyNumberCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CopyNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.BioSpecimenResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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

public class CompoundQueryTest extends TestCase {
    ArrayPlatformCriteria allPlatformCrit;
    ArrayPlatformCriteria affyOligoPlatformCrit;
    ArrayPlatformCriteria cdnaPlatformCrit;
    AssayPlatformCriteria snpPlatformCrit;
    CloneOrProbeIDCriteria cloneCrit;
    CloneOrProbeIDCriteria probeCrit;
    GeneOntologyCriteria ontologyCrit;
    PathwayCriteria pathwayCrit;
    GeneIDCriteria geneCrit;
    FoldChangeCriteria foldCrit;
    GeneExpressionQuery probeQuery;
    GeneExpressionQuery cloneQuery;
    GeneExpressionQuery geneQuery;
    ComparativeGenomicQuery genomicQuery;
	CopyNumberCriteria copyNumberCrit;
	DiseaseOrGradeCriteria diseaseCrit;
	RegionCriteria regionCrit;
	/**
	 * @param string
	 */
	public CompoundQueryTest(String string) {
		super(string);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext.init();
        buildPlatformCrit();
        buildFoldChangeCrit();
        buildCopyChangeCrit();
        buildCloneCrit();
        buildProbeCrit();
        buildGeneIDCrit();
        buildDiseaseTypeCrit();
        buildOntologyCrit();
        buildPathwayCrit();
        buildRegionCrit();
        buildGeneExprCloneSingleViewQuery();
        buildGeneExprProbeSetSingleViewQuery();
        buildGeneExprGeneSingleViewQuery();
        buildCopyNumberSingleViewQuery();

	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSingleQueryInCompoundQueryProcessor() {
		try {
			CompoundQuery myCompoundQuery;
			Resultant resultant;
			//for(int i =0; i < 5; i++){//test Single Query
			
			System.out.println("Testing Single Gene Query>>>>>>>>>>>>>>>>>>>>>>>");
			myCompoundQuery = new CompoundQuery(geneQuery);
			resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("SingleQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
			
			//test copy query
			/**
			System.out.println("Testing Single Copy Query>>>>>>>>>>>>>>>>>>>>>>>");
			myCompoundQuery = new CompoundQuery(genomicQuery);
			resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("SingleQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
			**/
			//}
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	public void testCompoundQueryANDProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery GeneQuery AND ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.AND,geneQuery,probeQuery);
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testCompoundQueryNOTProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery GeneQuery NOT ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.NOT,geneQuery,probeQuery);
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testCompoundQueryORProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery CloneQuery OR ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.OR,geneQuery,probeQuery);
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testGeneExprANDCopyNumberQuery() {
		try {
			//test CompoundQuery Query
			for(int i = 0; i < 5; i++){
			System.out.println("Testing CompoundQuery GeneExprQuery AND GenomicQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.AND,geneQuery,genomicQuery);
			SessionQueryBag queryCollection = new SessionQueryBag();
			myCompoundQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			//System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			//queryCollection.setCompoundQuery(myCompoundQuery);
			//String theColors[] = {"0073E6","FFFF61"};
			//System.out.println(ReportGenerator.displayReport( queryCollection, theColors,false));
			print(resultant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testGeneExprANDCopyNumberQueryORGeneExpr() {
		try {
			//test CompoundQuery Query
			//for(int i= 0; i < 100; i++){
			System.out.println("Testing CompoundQuery GeneExprQuery AND GenomicQuery OR Probe Query>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery1 = new CompoundQuery(OperatorType.AND,geneQuery,genomicQuery);
			
			CompoundQuery myCompoundQuery2 = new CompoundQuery(OperatorType.OR,myCompoundQuery1,geneQuery);

			myCompoundQuery1.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery2);
			//System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			//queryCollection.setCompoundQuery(myCompoundQuery);
			//String theColors[] = {"0073E6","FFFF61"};
			//System.out.println(ReportGenerator.displayReport( queryCollection, theColors,false));
			print(resultant);
			//System.err.println("Count= "+i);
			Thread.sleep( 5 );
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
    /**
	 * @param geneExprObjects
	 */
	private void print(Resultant resultant) {
		if(resultant != null){
			ResultsContainer resultsContainer = resultant.getResultsContainer();
			if (resultsContainer instanceof DimensionalViewContainer){
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
		        GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
		        if (geneViewContainer != null){
			        displayGeneExprSingleView(geneViewContainer);
		        }
				CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
		        if (copyNumberContainer != null){
		        	displayCopyNumberSingleView(copyNumberContainer);
		        }
		        /*
		        SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
		        if (sampleViewContainer != null){
			        displaySampleView(sampleViewContainer);	
		        }
		        */
			}
		}
		
	}
	public void testShowAllValuesHandler() {
		
		try {
			//CompoundQuery myCompoundQuery1 = new CompoundQuery(OperatorType.AND,geneQuery,genomicQuery);
			CompoundQuery myCompoundQuery2 = new CompoundQuery(genomicQuery);

			myCompoundQuery2.setAssociatedView(ViewFactory.newView(ViewType.COPYNUMBER_GENE_SAMPLE_VIEW));
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery2);
			System.out.println("Printing Query Output>>>>>>>>>>>>>>>>>>>>>>>");
			print(resultant);
			Resultant resultant2 = ResultsetManager.executeShowAllQuery(resultant);
			System.out.println("Printing ShowAllValuesQuery Output>>>>>>>>>>>>>>>>>>>>>>>");
			print(resultant2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void testContraintQueryWithSamplesHandler() {
		
		try {
			CompoundQuery myCompoundQuery1 = new CompoundQuery(OperatorType.AND,geneQuery,genomicQuery);
			List sampleIDList = new ArrayList();
			sampleIDList.add("608");
			sampleIDList.add("118");
			sampleIDList.add("305");
			sampleIDList.add("990");
			sampleIDList.add("1219");
			sampleIDList.add("1227");
			sampleIDList.add("1325");
			String[] sampleIDs = (String[])sampleIDList.toArray(new String[sampleIDList.size()]);
			myCompoundQuery1.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
			
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery1);
			System.out.println("Printing Original Query Output>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("For the following samples");
			for (int i = 0; i < sampleIDs.length;i++){
				System.out.println(i+") "+ sampleIDs[i]);
			}
			
			print(resultant);
			System.out.println("Now Constraint it by the following samples only");
			for (int i = 0; i < sampleIDs.length;i++){
				System.out.println(i+") "+ sampleIDs[i]);
			}
			resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery1,sampleIDs);
			print(resultant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void testCompoundQuerywithInstitutionCrit(){
		try {
			//test CompoundQuery Query with InsitutionCrit
			System.out.println("Testing CompoundQuery GeneQuery AND ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.AND,geneQuery,probeQuery);
			InstitutionCriteria institutionCrit = new InstitutionCriteria();
			institutionCrit.setInsitution(new InstitutionDE("HENRY FORD(RETRO)",new Long(1)));
			myCompoundQuery.setInstitutionCriteria(institutionCrit);
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(resultant);
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	public void testCopyNumberFilter() {
		
		try {
			CompoundQuery myCompoundQuery1 = new CompoundQuery(genomicQuery);
			List sampleIDList = new ArrayList();
			sampleIDList.add("1139");
			sampleIDList.add("1297");
			sampleIDList.add("1223");
			sampleIDList.add("118");
			sampleIDList.add("1057");
			sampleIDList.add("1397");
			sampleIDList.add("1409");
			String[] sampleIDs = (String[])sampleIDList.toArray(new String[sampleIDList.size()]);
			myCompoundQuery1.setAssociatedView(ViewFactory.newView(ViewType.COPYNUMBER_GENE_SAMPLE_VIEW));
			

			System.out.println("Now Constraint it by the following samples only");
			for (int i = 0; i < sampleIDs.length;i++){
				System.out.println(i+") "+ sampleIDs[i]);
			}
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery1); //,sampleIDs);
			print(resultant);
			Collection newSampleIDs = ResultsetManager.getSampleIdsforCopyNumberFilter(resultant,new Integer(2),new Integer(20),OperatorType.OR);
			//DEBUG
			for (Iterator sampleIDsIterator = newSampleIDs.iterator(); sampleIDsIterator.hasNext();) {
				String sampleID = (String) sampleIDsIterator.next();
				System.out.println(sampleID);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void displaySampleView(SampleViewResultsContainer sampleViewContainer){
		   System.out.println("Printing Sample View for the Query >>>>>>>>>>>>>>>>>>>>>>>");
	       Collection samples = sampleViewContainer.getSampleResultsets();
		   System.out.println("SAMPLE\tAGE\tGENDER\tSURVIVAL\tDISEASE");
		   StringBuffer stringBuffer = new StringBuffer();
		for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();

			System.out.println(sampleResultset.getSampleIDDE().getValue()+
					"\t"+sampleResultset.getAgeGroup().getValue()+
					"\t"+sampleResultset.getGenderCode().getValue()+
					"\t"+sampleResultset.getSurvivalLengthRange().getValue()+
					"\t"+sampleResultset.getDisease().getValue()+
					"\t"+((sampleResultset.getGeneExprSingleViewResultsContainer() != null) ? "GE-Link" : "")+
					"\t"+((sampleResultset.getCopyNumberSingleViewResultsContainer() != null) ? "CN-Link" : ""));
 		}
	}
	private void displayCopyNumberSingleView(CopyNumberSingleViewResultsContainer copyNumberContainer) {
		final DecimalFormat resultFormat = new DecimalFormat("0.00");		 
    	Collection cytobands = copyNumberContainer.getCytobandResultsets();
    	Collection labels = copyNumberContainer.getGroupsLabels();
    	Collection sampleIds = null;
    	StringBuffer header = new StringBuffer();
    	StringBuffer sampleNames = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
    	//get group size (as Disease or Agegroup )from label.size
        System.out.println("Printing Copy Number View>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("GroupSize= "+labels.size());
        for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        	String label = (String) labelIterator.next();
        	System.out.println(label);
        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 
        	//For each group get the number of samples in it from sampleIds.size()
            System.out.println("SampleSize= "+sampleIds.size());
           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
           		System.out.println(sampleIdIterator.next()); 
           	}
           	 
    	}
	}
	private void displayGeneExprSingleView(GeneExprSingleViewResultsContainer geneViewContainer){
		Collection labels = geneViewContainer.getGroupsLabels();
    	Collection sampleIds = null;
    	//get group size (as Disease or Agegroup )from label.size
        System.out.println("Printing Gene Expr View>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("GroupSize= "+labels.size());
        for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        	String label = (String) labelIterator.next();
        	System.out.println(label);
        	sampleIds = geneViewContainer.getBiospecimenLabels(label); 
        	//For each group get the number of samples in it from sampleIds.size()
            System.out.println("SampleSize= "+sampleIds.size());
           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
           		System.out.println(sampleIdIterator.next()); 
           	}
           	 
    	}
	}
	private void displayBoxAndWisherOutput(GeneExprSingleViewResultsContainer geneViewContainer){
		Collection genes = geneViewContainer.getGeneResultsets();
    	String geneSymbol = null;
    	if(genes != null && genes.size() == 1){
    		for(Object gene:genes){
    			GeneResultset geneResultset = (GeneResultset)gene;
    			geneSymbol = geneResultset.getGeneSymbol().getValueObject();
    		}
    	}
    	Collection reporterNames = geneViewContainer.getAllReporterNames();
    	Collection labels = geneViewContainer.getGroupsLabels();
    	Collection sampleIds = null;
    	        //get group size (as Disease or Agegroup )from label.size
        System.out.println("Printing BoxAndWisherOutput>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("GroupSize= "+labels.size());
        for (Object label:labels) {
        	System.out.println("DiseaseName:"+label);
			for (Object reporterName: reporterNames) {
        	sampleIds = geneViewContainer.getBioSpecimentResultsets(geneSymbol,(String)reporterName,(String)label); 
        	//For each group get the number of samples in it from sampleIds.size()
            System.out.println("Reporter Name="+ reporterName+"/tSampleSize= "+sampleIds.size());
            	for(Object sample:sampleIds){
            		if(sample instanceof SampleFoldChangeValuesResultset ){
            			SampleFoldChangeValuesResultset folgChangeResultset = (SampleFoldChangeValuesResultset) sample;
            			Double ratio = (Double)folgChangeResultset.getFoldChangeRatioValue().getValue();
               			Double intensity = (Double)folgChangeResultset.getFoldChangeIntensity().getValue();
               			Double log2Intensity = (Double)folgChangeResultset.getFoldChangeLog2Intensity().getValue();
               			System.out.println("sampleID="+folgChangeResultset.getSampleIDDE().getValueObject()+ "\tratio= "+ratio+"\tintensity= "+intensity+"\tlog2Intensity= "+log2Intensity);
            			
            		}
            	}
        	}
           	 
    	}
	}
	public static Test suite() {
		TestSuite suite =  new TestSuite();
        //suite.addTest(new CompoundQueryTest("testCompoundQueryANDProcessor"));
        //suite.addTest(new CompoundQueryTest("testCompoundQueryNOTProcessor"));
        //suite.addTest(new CompoundQueryTest("testCompoundQueryORProcessor"));
        //suite.addTest(new CompoundQueryTest("testSingleQueryInCompoundQueryProcessor"));
        //suite.addTest(new CompoundQueryTest("testGeneExprANDCopyNumberQuery"));


        return suite;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }
	public void testExecute() {
	}
    private void buildDiseaseTypeCrit() {
        diseaseCrit = new DiseaseOrGradeCriteria();
        diseaseCrit.setDisease(new DiseaseNameDE("OLIG"));
   }
	private void buildProbeCrit() {
        probeCrit = new CloneOrProbeIDCriteria();
        //1555146_at is a probeSet for ATF2
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("1555146_at"));
    }

    private void buildCloneCrit() {
        cloneCrit = new CloneOrProbeIDCriteria();
        //IMAGE:2014733 is a CloneID for AFT2
            cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:434972"));
            cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:434990"));
            cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:434992"));
    }
    private void buildGeneIDCrit() {
        geneCrit = new GeneIDCriteria();
        //Both IMAGE:2014733 and 1555146_at should be subsets of ATF2
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol("WT1"));

    }
    private void buildPathwayCrit() {
        pathwayCrit = new PathwayCriteria();
        PathwayDE obj1 = new PathwayDE("Lis1Pathway");
        PathwayDE obj2 = new PathwayDE("TPOPathway");
  		PathwayDE obj3 = new PathwayDE("Ccr5Pathway");
        Collection pathways = new ArrayList();
        pathways.add(obj1); 
        //pathways.add(obj2);
        //pathways.add(obj3);
        pathwayCrit.setPathwayNames(pathways);
    }
    private void buildFoldChangeCrit() {
        Float upRegExpected = new Float(2.0);
        Float downRegExpected = new Float(2.0);
        ExprFoldChangeDE.UpRegulation upRegObj = new ExprFoldChangeDE.UpRegulation(upRegExpected );
        ExprFoldChangeDE.DownRegulation downRegObj = new ExprFoldChangeDE.DownRegulation(downRegExpected );
        //ExprFoldChangeDE.UnChangedRegulationUpperLimit upUnChangedObj = new ExprFoldChangeDE.UnChangedRegulationUpperLimit(upperUnchangedExpected );
        //ExprFoldChangeDE.UnChangedRegulationDownLimit downUnChangedRegObj = new ExprFoldChangeDE.UnChangedRegulationDownLimit(downUnChangedExpected );

        foldCrit = new FoldChangeCriteria();
        Collection objs = new ArrayList(4);
        objs.add(upRegObj);
        //objs.add(downRegObj);
        //objs.add(upUnChangedObj); objs.add(downUnChangedRegObj);
        foldCrit.setFoldChangeObjects(objs);
    }
    private void buildOntologyCrit() {
        ontologyCrit = new GeneOntologyCriteria();
        //ontologyCrit.setGOIdentifier(new GeneOntologyDE("GO:0000004"));
        ontologyCrit.setGOIdentifier(new GeneOntologyDE("GO:0050794"));
    }
    private void buildRegionCrit() {
        regionCrit = new RegionCriteria();

        // cytoband and start & end positions are mutually exclusive
      regionCrit.setCytoband(new CytobandDE("p21.3"));
       //regionCrit.setStart(new BasePairPositionDE.StartPosition(new Integer(6900000)));
       // regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Integer(8800000)));

        // Chromosome Number is mandatory
      regionCrit.setChromNumber(new ChromosomeNumberDE(new String("9")));
      
    }
    private void buildCopyChangeCrit() {
        Float amplification = new Float(2.0);
        Float deletion = new Float(1.0);
        CopyNumberDE.Amplification ampObj = new CopyNumberDE.Amplification(amplification );
        CopyNumberDE.Deletion deletionObj = new CopyNumberDE.Deletion(deletion);
        //CopyNumberDE.UnChangedCopyNumberUpperLimit upCopyNumberObj = new CopyNumberDE.UnChangedCopyNumberUpperLimit(amplification);
        //CopyNumberDE.UnChangedCopyNumberDownLimit  downCopyNumberObj = new CopyNumberDE.UnChangedCopyNumberDownLimit(deletion);

        copyNumberCrit = new CopyNumberCriteria();
        Collection objs = new ArrayList(4);
        //objs.add(deletionObj);
        objs.add(ampObj);
        //objs.add(downCopyNumberObj);
        copyNumberCrit.setCopyNumbers(objs);
    }
    private void buildGeneExprProbeSetSingleViewQuery(){
        probeQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        probeQuery.setQueryName("ProbeSetQuery");
        probeQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        probeQuery.setCloneOrProbeIDCrit(probeCrit);
        probeQuery.setArrayPlatformCrit(affyOligoPlatformCrit);
        probeQuery.setFoldChgCrit(foldCrit);
        probeQuery.setDiseaseOrGradeCrit(diseaseCrit);
    }
    private void buildGeneExprCloneSingleViewQuery(){
        cloneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        cloneQuery.setQueryName("CloneQuery");
        cloneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        cloneQuery.setCloneOrProbeIDCrit(cloneCrit);
        cloneQuery.setArrayPlatformCrit(cdnaPlatformCrit);
        cloneQuery.setFoldChgCrit(foldCrit);
        cloneQuery.setDiseaseOrGradeCrit(diseaseCrit);
    }
    private void buildGeneExprGeneSingleViewQuery(){
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("GeneQuery");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        //geneQuery.setCloneOrProbeIDCrit(cloneCrit);
        geneQuery.setGeneIDCrit(geneCrit);
        //geneQuery.setPathwayCrit(pathwayCrit);
        //geneQuery.setGeneOntologyCrit(ontologyCrit);
        geneQuery.setArrayPlatformCrit(affyOligoPlatformCrit);
        //geneQuery.setFoldChgCrit(foldCrit);
    }
    private void buildCopyNumberSingleViewQuery(){
        genomicQuery = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
        genomicQuery.setQueryName("CopyNumberQuery");
        genomicQuery.setGeneIDCrit(geneCrit);
        genomicQuery.setAssociatedView(ViewFactory.newView(ViewType.COPYNUMBER_GENE_SAMPLE_VIEW));
        //genomicQuery.setRegionCrit(regionCrit);
        genomicQuery.setAssayPlatformCrit(snpPlatformCrit);
        genomicQuery.setCopyNumberCrit(copyNumberCrit);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
        affyOligoPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM));
        cdnaPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.CDNA_ARRAY_PLATFORM));
        snpPlatformCrit = new AssayPlatformCriteria();
        snpPlatformCrit.setAssayPlatformDE(new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY));
        
    }
    private void changeQueryView(Query query,Viewable view){
    	if(query !=null){
    		query.setAssociatedView(view);
    	}
    }
    public void testBoxAndWiskerOutput(){
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("B&WQuery");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(affyOligoPlatformCrit);
		System.out.println("Testing BoxandWisker Output>>>>>>>>>>>>>>>>>>>>>>>");
		CompoundQuery myCompoundQuery = null;
		try {
			myCompoundQuery = new CompoundQuery(geneQuery);
			myCompoundQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
			Resultant resultant;
	
				resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			if(resultant != null){
				ResultsContainer resultsContainer = resultant.getResultsContainer();
				if (resultsContainer instanceof DimensionalViewContainer){
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
			        GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
			        if (geneViewContainer != null){
			        	displayBoxAndWisherOutput(geneViewContainer);
			        }
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
