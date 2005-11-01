/*
 *  @author: SahniH
 *  Created on Oct 26, 2004
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
package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AssayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.CopyNumberCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CopyNumberDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.dto.view.GroupType;
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
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CytobandResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.DiseaseGroupResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author SahniH
 * Date: Oct 26, 2004
 * 
 */
public class ResultsetViewTest extends TestCase {
    ArrayPlatformCriteria allPlatformCrit;
    GeneIDCriteria geneCrit;
    FoldChangeCriteria foldCrit;
    SNPCriteria snpCrit;
    CopyNumberCriteria copyNumberCrit;
    GeneExpressionQuery geneQuery1;
    GeneExpressionQuery geneQuery2;
    ComparativeGenomicQuery	snpQuery;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        buildPlatformCrit();
        buildFoldChangeCrit();
        buildGeneIDCrit();
        buildSNPCrit();
        buildCopyChangeCrit();
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(ResultsetViewTest.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }
    private void buildGeneIDCrit() {
        geneCrit = new GeneIDCriteria();
        Collection geneList = new ArrayList();
        geneList.add( new GeneIdentifierDE.GeneSymbol("KIF1B"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("EGFR"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("SHREW1"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("VEGF"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("NEK1"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("AGA"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("AADAT"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("IRX4"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("CTNND2"));
        geneList.add( new GeneIdentifierDE.GeneSymbol("CDH12"));
        geneCrit.setGeneIdentifiers(geneList);
    }
    private void buildFoldChangeCrit() {
        Float upRegExpected = new Float(2.0);
        Float downRegExpected = new Float(1.0);
        ExprFoldChangeDE.UpRegulation upRegObj = new ExprFoldChangeDE.UpRegulation(upRegExpected );
        ExprFoldChangeDE.DownRegulation downRegObj = new ExprFoldChangeDE.DownRegulation(downRegExpected );
        //ExprFoldChangeDE.UnChangedRegulationUpperLimit upUnChangedObj = new ExprFoldChangeDE.UnChangedRegulationUpperLimit(upperUnchangedExpected );
        //ExprFoldChangeDE.UnChangedRegulationDownLimit downUnChangedRegObj = new ExprFoldChangeDE.UnChangedRegulationDownLimit(downUnChangedExpected );

        foldCrit = new FoldChangeCriteria();
        Collection objs = new ArrayList(4);
        objs.add(upRegObj);
        objs.add(downRegObj);
        //objs.add(upUnChangedObj); objs.add(downUnChangedRegObj);
        foldCrit.setFoldChangeObjects(objs);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
    }
    private void buildGeneExprGeneSingleViewQuery(){
        geneQuery1 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery1.setQueryName("GeneSampleQuery");
        geneQuery1.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery1.setGeneIDCrit(geneCrit);
        geneQuery1.setArrayPlatformCrit(allPlatformCrit);
        geneQuery1.setFoldChgCrit(foldCrit);
    }
    private void buildGeneExprDiseaseViewQuery(){
        geneQuery2 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery2.setQueryName("GeneDiseaseQuery");
        geneQuery2.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
        geneQuery2.setGeneIDCrit(geneCrit);
        geneQuery2.setArrayPlatformCrit(allPlatformCrit);
        geneQuery2.setFoldChgCrit(foldCrit);
    }
    private void buildSNPCrit() {
        ArrayList inputIDs = new ArrayList();
        // build DBSNPs
        SNPIdentifierDE.DBSNP snp1 = new SNPIdentifierDE.DBSNP("rs1396904");
        SNPIdentifierDE.DBSNP snp2 = new SNPIdentifierDE.DBSNP("rs10489139");
        SNPIdentifierDE.DBSNP snp3 = new SNPIdentifierDE.DBSNP("rs4475768");
        SNPIdentifierDE.DBSNP snp4 = new SNPIdentifierDE.DBSNP("rs10492941");
        SNPIdentifierDE.DBSNP snp5 = new SNPIdentifierDE.DBSNP("rs966366");


        // build SNPProbesets
        /*SNPIdentifierDE.SNPProbeSet snp1 = new SNPIdentifierDE.SNPProbeSet ("SNP_A-1676650");
        SNPIdentifierDE.SNPProbeSet snp2 = new SNPIdentifierDE.SNPProbeSet ("SNP_A-1748913");
        SNPIdentifierDE.SNPProbeSet snp3 = new SNPIdentifierDE.SNPProbeSet ("SNP_A-1667950");
        SNPIdentifierDE.SNPProbeSet snp4 = new SNPIdentifierDE.SNPProbeSet ("SNP_A-1642581");
        SNPIdentifierDE.SNPProbeSet snp5 = new SNPIdentifierDE.SNPProbeSet ("SNP_A-1657367");
        */

        inputIDs.add(snp1 ); inputIDs.add(snp2);  inputIDs.add(snp3);
         inputIDs.add(snp4);  inputIDs.add(snp5);
        snpCrit = new SNPCriteria();
        snpCrit.setSNPIdentifiers(inputIDs);
    }
    private void buildCopyChangeCrit() {
        Float amplification = new Float(2.0);
        Float deletion = new Float(0.8);
        CopyNumberDE.Amplification ampObj = new CopyNumberDE.Amplification(amplification );
        CopyNumberDE.Deletion deletionObj = new CopyNumberDE.Deletion(deletion);
        CopyNumberDE.UnChangedCopyNumberUpperLimit upCopyNumberObj = new CopyNumberDE.UnChangedCopyNumberUpperLimit(amplification);
        CopyNumberDE.UnChangedCopyNumberDownLimit  downCopyNumberObj = new CopyNumberDE.UnChangedCopyNumberDownLimit(deletion);

        copyNumberCrit = new CopyNumberCriteria();
        Collection objs = new ArrayList(4);
        objs.add(ampObj);
        //objs.add(deletionObj);
        //objs.add(upCopyNumberObj); objs.add(downCopyNumberObj);
        copyNumberCrit.setCopyNumbers(objs);
    }
    private void buildComparativeGenomicQuery(){
        snpQuery = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
        snpQuery.setQueryName("SNPSampleQuery");
        snpQuery.setAssociatedView(ViewFactory.newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
        //snpQuery.setGeneIDCrit(geneCrit);
        snpQuery.setSNPCrit(snpCrit);
        AssayPlatformCriteria assayPlatformCrit = new AssayPlatformCriteria();
        assayPlatformCrit.setAssayPlatformDE(new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY));
        snpQuery.setAssayPlatformCrit(assayPlatformCrit);
        snpQuery.setCopyNumberCrit(copyNumberCrit);
    }
    public void testGeneExprSampleView(){
		//test Single Query
		try {
	        buildGeneExprGeneSingleViewQuery();
			System.out.println("Building Single Gene Compound Query>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(geneQuery1);
			GeneExprSampleView geneCentricView = new GeneExprSampleView();
			geneCentricView.setGroupType(GroupType.DISEASE_TYPE_GROUP);			
			Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
			System.out.println("SingleQuery:\n"+ myCompoundQuery.toString());
			assertNotNull(resultant.getResultsContainer());
			if(resultant != null){
				System.out.println("Testing Single Gene Query >>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Associated Query/n"+resultant.getAssociatedQuery());
				ResultsContainer resultsContainer = resultant.getResultsContainer();
				System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
				if (resultsContainer instanceof DimensionalViewContainer){
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
			        GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
			        if (geneViewContainer != null){
				        displayGeneExprSingleView(geneViewContainer);
				        SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
				        displaySampleView(sampleViewContainer);	
				        doGeneViewForEverySample(sampleViewContainer);
			        }
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	    public void testGeneExprDiseaseView(){
			//test Single Query
			try {
		        buildGeneExprDiseaseViewQuery();
				System.out.println("Building Group Gene Compound Query>>>>>>>>>>>>>>>>>>>>>>>");
				CompoundQuery myCompoundQuery = new CompoundQuery(geneQuery2);
				Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
				System.out.println("DiseaseQuery:\n"+ myCompoundQuery.toString());
				assertNotNull(resultant.getResultsContainer());
				if(resultant != null){
					System.out.println("Testing Disease Gene Query >>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println("Associated Query/n"+resultant.getAssociatedQuery());
					ResultsContainer resultsContainer = resultant.getResultsContainer();
					System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
					if (resultsContainer instanceof GeneExprResultsContainer){
						GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;
				        displayGeneExprDiseaseView(geneExprDiseaseContainer);

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

    }
	    public void testCopyNumberSampleView(){
			//test Single Query
			try {
				buildComparativeGenomicQuery();
				System.out.println("Building SNP Compound Query>>>>>>>>>>>>>>>>>>>>>>>");
				CompoundQuery myCompoundQuery = new CompoundQuery(snpQuery);
				Resultant resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
				System.out.println("SNPQuery:\n"+ myCompoundQuery.toString());
				assertNotNull(resultant.getResultsContainer());
				if(resultant != null){
					System.out.println("Testing SNP Gene Query >>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println("Associated Query/n"+resultant.getAssociatedQuery());
					ResultsContainer resultsContainer = resultant.getResultsContainer();
					System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
					if (resultsContainer instanceof DimensionalViewContainer){
						DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
						CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
				        if (copyNumberContainer != null){
				        	displayCopyNumberSingleView(copyNumberContainer);
					        SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
					        displaySampleView(sampleViewContainer);	
					        doCopyNumberForEverySample(sampleViewContainer);
				        }
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

    }
	/**
		 * @param sampleViewContainer
		 */
		private void doCopyNumberForEverySample(SampleViewResultsContainer sampleViewContainer) {
			   System.out.println("Testing CopyNumber View for Every Sample >>>>>>>>>>>>>>>>>>>>>>>");
		       Collection samples = sampleViewContainer.getBioSpecimenResultsets();
			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
				//String sampleId = sampleResultset.getBiospecimen().getValue().toString();
			    CopyNumberSingleViewResultsContainer copyNumberSingleViewResultsContainer = sampleResultset.getCopyNumberSingleViewResultsContainer();
			    if(copyNumberSingleViewResultsContainer != null){
			    displayCopyNumberSingleView(copyNumberSingleViewResultsContainer);
			    }
	 		}
			
		}
	/**
		 * @param copyNumberContainer
		 */
		private void displayCopyNumberSingleView(CopyNumberSingleViewResultsContainer copyNumberContainer) {
			final DecimalFormat resultFormat = new DecimalFormat("0.00");		 
	    	Collection cytobands = copyNumberContainer.getCytobandResultsets();
	    	Collection labels = copyNumberContainer.getGroupsLabels();
	    	Collection sampleIds = null;
	    	StringBuffer header = new StringBuffer();
	    	StringBuffer sampleNames = new StringBuffer();
	        StringBuffer stringBuffer = new StringBuffer();
	    	//get group size (as Disease or Agegroup )from label.size
	        
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
	    	
	        //set up the header for the table
	    	header.append("Cytoband\tReporter\t");
	    	sampleNames.append("Name\tName\t\t");
		   
	    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
	        	String label = (String) labelIterator.next();
	        	header.append("|"+label.substring(0,3)+"\t"); //remove this for table
	        	sampleIds = copyNumberContainer.getBiospecimenLabels(label);        	
		           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
		            	sampleNames.append(sampleIdIterator.next()+"\t"); 
		            	header.append("\t");
		           	}
	           	header.deleteCharAt(header.lastIndexOf("\t"));
	    	}
	    	header.append("|"); 

	    	//System.out.println("Cytoband Count: "+cytobands.size());
			System.out.println(header.toString());
			System.out.println(sampleNames.toString());
	    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
	    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
	    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
	    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); //geneResultset.getReporterResultsets();
	        	//System.out.println("Repoter Count: "+reporters.size());
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		String reporterName = reporterResultset.getReporter().getValue().toString();
	        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); //reporterResultset.getGroupResultsets();
	        		stringBuffer = new StringBuffer();
	            	//System.out.println("Group Count: "+groupTypes.size());
	        		if(reporterName.length()< 10){ //Remove this from table
	        			reporterName= reporterName+"        ";
	        			reporterName = reporterName.substring(0,10);
	        		}
	        		//get the gene name, and reported Name
	        		
	        		stringBuffer.append(cytoband+"\t"+
	    					reporterName+"\t");
	        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
	        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
	        			String label = groupResultset.getType().getValue().toString();
	        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
	                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	                       		String sampleId = (String) sampleIdIterator.next();
	                       		SampleCopyNumberValuesResultset sampleResultset = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);//geneViewContainer.getBioSpecimentResultset(geneSymbol,reporterName,label,sampleId);
	                       		if(sampleResultset != null){
	                       			Double ratio = (Double)sampleResultset.getCopyNumber().getValue();
	                       			stringBuffer.append(resultFormat.format(ratio)+"\t");  
	                       			}
	                       		else 
	                       		{
	                       			stringBuffer.append("\t");
	                       		}
	                       	}
	         		}
	        		System.out.println(stringBuffer.toString());
	    		}

	    	}
		
			
		}
	/**
		 * @param geneExprDiseaseContainer
		 */
		private void displayGeneExprDiseaseView(GeneExprResultsContainer geneExprDiseaseContainer) {
			System.out.println("inside display diease");
			final DecimalFormat resultFormat = new DecimalFormat("0.00");		 
	    	Collection genes = geneExprDiseaseContainer.getGeneResultsets();
	    	Collection labels = geneExprDiseaseContainer.getGroupsLabels();
	    	Collection sampleIds = null;
	    	StringBuffer header = new StringBuffer();
	    	StringBuffer sampleNames = new StringBuffer();
	        StringBuffer stringBuffer = new StringBuffer();
	    	//get group size (as Disease or Agegroup )from label.size
	        String label = null;
	    	
	        //set up the header for the table
	    	header.append("Gene\tReporter\t");
	    	sampleNames.append("Name\tName\t\t");
		   
	    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
	        	label = (String) labelIterator.next();
	        	header.append("|"+label+"\t"); //remove this for table
	    	}
	    	header.append("|"); 

	    	//System.out.println("Gene Count: "+genes.size());
			System.out.println(header.toString());
			System.out.println(sampleNames.toString());
	    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
	    		Collection reporters = geneExprDiseaseContainer.getRepoterResultsets(geneSymbol); //geneResultset.getReporterResultsets();
	        	//System.out.println("Repoter Count: "+reporters.size());
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		String reporterName = reporterResultset.getReporter().getValue().toString();
	        		Collection groupTypes = geneExprDiseaseContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();
	        		stringBuffer = new StringBuffer();
	            	//System.out.println("Group Count: "+groupTypes.size());
	        		if(reporterName.length()< 10){ //Remove this from table
	        			reporterName= reporterName+"        ";
	        			reporterName = reporterName.substring(0,10);
	        		}
	        		//get the gene name, and reported Name
	        		
	        		stringBuffer.append(geneSymbol+"\t"+
	    					reporterName+"\t");
	        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
	    	        	label = (String) labelIterator.next();
	    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
	    	        	if(diseaseResultset != null){
                   			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
                   			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
                   			stringBuffer.append(resultFormat.format(ratio)+"("+resultFormat.format(pvalue)+")"+"\t");  
                   			}
                   		else 
                   		{
                   			stringBuffer.append("\t");
                   		}
	    	    	}
	        		System.out.println(stringBuffer.toString());
	    		}

	    	}
			
		}
	private void displayGeneExprSingleView(GeneExprSingleViewResultsContainer geneViewContainer){
		final DecimalFormat resultFormat = new DecimalFormat("0.00");		 
    	Collection genes = geneViewContainer.getGeneResultsets();
    	Collection labels = geneViewContainer.getGroupsLabels();
    	Collection sampleIds = null;
    	StringBuffer header = new StringBuffer();
    	StringBuffer sampleNames = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
    	//get group size (as Disease or Agegroup )from label.size
        
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
    	
        //set up the header for the table
    	header.append("Gene\tReporter\t");
    	sampleNames.append("Name\tName\t\t");
	   
    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        	String label = (String) labelIterator.next();
        	header.append("|"+label.substring(0,3)+"\t"); //remove this for table
        	sampleIds = geneViewContainer.getBiospecimenLabels(label);        	
	           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	            	sampleNames.append(sampleIdIterator.next()+"\t"); 
	            	header.append("\t");
	           	}
           	header.deleteCharAt(header.lastIndexOf("\t"));
    	}
    	header.append("|"); 

    	//System.out.println("Gene Count: "+genes.size());
		System.out.println(header.toString());
		System.out.println(sampleNames.toString());
    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
    		String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
    		Collection reporters = geneViewContainer.getRepoterResultsets(geneSymbol); //geneResultset.getReporterResultsets();
        	//System.out.println("Repoter Count: "+reporters.size());
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
        		String reporterName = reporterResultset.getReporter().getValue().toString();
        		Collection groupTypes = geneViewContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();
        		stringBuffer = new StringBuffer();
            	//System.out.println("Group Count: "+groupTypes.size());
        		if(reporterName.length()< 10){ //Remove this from table
        			reporterName= reporterName+"        ";
        			reporterName = reporterName.substring(0,10);
        		}
        		//get the gene name, and reported Name
        		
        		stringBuffer.append(geneSymbol+"\t"+
    					reporterName+"\t");
        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
        			String label = groupResultset.getType().getValue().toString();
        			sampleIds = geneViewContainer.getBiospecimenLabels(label);
                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
                       		String sampleId = (String) sampleIdIterator.next();
                       		SampleFoldChangeValuesResultset sampleResultset = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);//geneViewContainer.getBioSpecimentResultset(geneSymbol,reporterName,label,sampleId);
                       		if(sampleResultset != null){
                       			Double ratio = (Double)sampleResultset.getFoldChangeRatioValue().getValue();
                       			stringBuffer.append(resultFormat.format(ratio)+"\t");  
                       			}
                       		else 
                       		{
                       			stringBuffer.append("\t");
                       		}
                       	}
         		}
        		System.out.println(stringBuffer.toString());
    		}

    	}
	
	}
	private void displaySampleView(SampleViewResultsContainer sampleViewContainer){
		   System.out.println("Testing Sample View for entire Query >>>>>>>>>>>>>>>>>>>>>>>");
	       Collection samples = sampleViewContainer.getBioSpecimenResultsets();
 		   System.out.println("SAMPLE\tAGE\tGENDER\tSURVIVAL\tDISEASE");
  		   StringBuffer stringBuffer = new StringBuffer();
   		for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
   			SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();

   			System.out.println(sampleResultset.getBiospecimen().getValue()+
   					"\t"+sampleResultset.getAgeGroup().getValue()+
					"\t"+sampleResultset.getGenderCode().getValue()+
					"\t"+sampleResultset.getSurvivalLengthRange().getValue()+
					"\t"+sampleResultset.getDisease().getValue());
    		}
	}
	private void doGeneViewForEverySample(SampleViewResultsContainer sampleViewContainer){
		   System.out.println("Testing Gene View for Every Sample >>>>>>>>>>>>>>>>>>>>>>>");
	       Collection samples = sampleViewContainer.getBioSpecimenResultsets();
		for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
			//String sampleId = sampleResultset.getBiospecimen().getValue().toString();
		    GeneExprSingleViewResultsContainer geneViewContainer = sampleResultset.getGeneExprSingleViewResultsContainer();
		    if(geneViewContainer != null){
		    displayGeneExprSingleView(geneViewContainer);
		    }
 		}
	}
    private void changeQueryView(Query query,Viewable view){
    	if(query !=null){
    		query.setAssociatedView(view);
    	}
    }
}
