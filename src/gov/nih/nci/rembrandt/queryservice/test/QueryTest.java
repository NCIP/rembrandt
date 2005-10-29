package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AssayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.CopyNumberCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.de.AgeAtDiagnosisDE;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CopyNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.SurvivalDE;
import gov.nih.nci.caintegrator.dto.de.AgeAtDiagnosisDE.UpperAgeLimit;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.GroupType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.service.findings.Resultant;
import gov.nih.nci.caintegrator.service.findings.ResultsContainer;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetProcessor;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryProcessor;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author BhattarR
 */
public class QueryTest extends TestCase {
	 private static final DecimalFormat resultFormat = new DecimalFormat("0.00");
     DiseaseOrGradeCriteria diseaseCrit;
     FoldChangeCriteria foldCrit;
     SampleCriteria sampleCrit;
     CopyNumberCriteria copyNumberCrit;
     GeneIDCriteria  geneIDCrit;
     GeneOntologyCriteria ontologyCrit;
     PathwayCriteria pathwayCrit;
     RegionCriteria regionCrit;
     CloneOrProbeIDCriteria cloneCrit;
     CloneOrProbeIDCriteria probeCrit;
     ArrayPlatformCriteria allPlatformCrit;
     ArrayPlatformCriteria affyOligoPlatformCrit;
     ArrayPlatformCriteria cdnaPlatformCrit;
     SNPCriteria snpCrit;
     SurvivalCriteria survivalCrit;
	 AgeCriteria ageCriteria;
	 GenderCriteria genderCrit;
     AllGenesCriteria allGenesCriteria;


    protected void setUp() throws Exception {
        buildSampleIDCrit();

        // the following two are mutually exclusive
        buildDiseaseTypeCrit();
        //buildAllDiseaseTypeCrit();

        buildSurvivalCrit();
        buildAgeCrit();
        buildGenderCrit();
        buildRegionCrit();
        buildPlatformCrit();
        buildCloneCrit();
        buildProbeCrit();
        buildFoldChangeCrit();

        // the following two are mutually exclusive
        //buildGeneIDFromFileCrit();
        //buildGeneIDCrit();
        buildAllGenesCriteria();

        buildOntologyCrit();
        buildPathwayCrit();
        buildSNPCrit();
        buildCopyChangeCrit();
    }
    public static class GeneExpression extends QueryTest {
          public void testGeneExprQuery() {
        GeneExpressionQuery q = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
             q.setQueryName("Test Gene Query");
             //q.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
              q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
             //q.setGeneIDCrit(geneIDCrit);
             q.setAllGenesCrit(allGenesCriteria);
             //q.setGeneOntologyCrit(ontologyCrit);

            //q.setPathwayCrit(pathwayCrit);

            //q.setGeneOntologyCrit(ontologyCrit);
            //q.setRegionCrit(regionCrit);
             //q.setPathwayCrit(pathwayCrit);


            q.setArrayPlatformCrit(allPlatformCrit);
            //q.setArrayPlatformCrit(affyOligoPlatformCrit);
           //q.setArrayPlatformCrit(cdnaPlatformCrit);

            //q.setCloneOrProbeIDCrit(cloneCrit);
            //q.setCloneProbeCrit(probeCrit);
            //q.setDiseaseOrGradeCrit(diseaseCrit);
            q.setSampleIDCrit(sampleCrit);
            q.setFoldChgCrit(foldCrit);

            try {
            	//CompoundQuery myCompoundQuery = new CompoundQuery(q);
                ResultSet[] geneExprObjects = QueryProcessor.execute(q);
                System.out.println("NUMBER OF RECORDS: " + geneExprObjects.length);

                print(geneExprObjects);
               //if (geneExprObjects.length > 0)
                 //   testResultset(geneExprObjects);

            } catch(Throwable t ) {
                t.printStackTrace();
            }

    }

      private void print(ResultSet[] geneExprObjects) {
            int count = 0;
            HashSet probeIDS = new HashSet();
            HashSet cloneIDs = new HashSet();
            HashSet gs = new HashSet();

          for (int i = 0; i < geneExprObjects.length; i++) {
              ResultSet obj = geneExprObjects[i];
                GeneExpr exprObj = null;
                if (obj instanceof GeneExpr.GeneExprGroup)  {
                    exprObj = (GeneExpr.GeneExprGroup) obj;
                }
                 else if (obj instanceof GeneExpr.GeneExprSingle)  {
                    exprObj = (GeneExpr.GeneExprSingle) obj;
                     System.out.println("\n\n ******* SAMPLE ID: " + ((GeneExpr.GeneExprSingle)exprObj).getSampleId()
                                        + "*******\n\n");
                }
                if (exprObj.getGeneSymbol() != null) {
                    gs.add(exprObj.getGeneSymbol());
                    System.out.println("Gene Annotations For GeneSymbol: " + exprObj.getGeneSymbol());
                    GeneExpr.Annotaion  a =  exprObj.getAnnotation();
                    if (a != null && a.getGeneAnnotation() != null) {

                       // display pathwayNames
                       Collection pathways = a.getGeneAnnotation().getPathwayNames();
                       for (Iterator iterator = pathways.iterator(); iterator.hasNext();) {
                           String pathwayName =  (String) iterator.next();
                           System.out.println("                     PathwayName: " + pathwayName);
                       }

                       // display GOIDs
                       Collection goIDs = a.getGeneAnnotation().getPathwayNames();
                       for (Iterator iterator = goIDs.iterator(); iterator.hasNext();) {
                           String goID =  (String) iterator.next();
                           System.out.println("                     GeneOntologyName: " + goID);
                       }

                    }
                }
                if (exprObj.getProbesetId() != null) {
                    System.out.println("Disease: "+ exprObj.getDiseaseType());

                  System.out.println("ProbesetID: " + exprObj.getProbesetId() + " :Exp Value: "
                                + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol()
                            );
                    probeIDS.add(exprObj.getProbesetId());
                }
                if ( exprObj.getCloneId() != null) {
                    System.out.println("CloneID: " + exprObj.getCloneId()+ " :Exp Value: "
                                + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol());
                    cloneIDs.add(exprObj.getCloneId() );
                }

                 ++count;
            }
            System.out.println("Total Number Of GeneExpression Facts: " + count);
            StringBuffer p = new StringBuffer();
            for (Iterator iterator = probeIDS.iterator(); iterator.hasNext();) {
                Long aLong = (Long) iterator.next();
                p.append(aLong.toString() + ",");
            }
            System.out.println("Total Probes: " + probeIDS.size());
            System.out.println(p.toString());
            StringBuffer c = new StringBuffer();
            for (Iterator iterator = cloneIDs.iterator(); iterator.hasNext();) {
                Long aLong = (Long) iterator.next();
                c.append(aLong.toString() + ",");
            }
            System.out.println("Total clones: " + cloneIDs.size());
            System.out.println(c.toString());

            int gsCount = 0;
            for (Iterator iterator = gs.iterator(); iterator.hasNext();) {
                String s = (String) iterator.next();
                ++gsCount;
            }
            System.out.println("Total Number Genes: " + gsCount);
            return ;
    }
      public void testResultset(ResultSet[] geneExprObjects){
    	assertNotNull(geneExprObjects);
        assertTrue(geneExprObjects.length > 0);
        ResultsContainer resultsContainer = ResultsetProcessor.handleGeneExprSingleView(new Resultant(),(GeneExprSingle[]) geneExprObjects, GroupType.DISEASE_TYPE_GROUP);
		if (resultsContainer instanceof DimensionalViewContainer){
			DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
	        GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();

	    	Collection genes = geneViewContainer.getGeneResultsets();
	    	Collection labels = geneViewContainer.getGroupsLabels();
	    	Collection sampleIds = null;
	    	StringBuffer header = new StringBuffer();
	    	StringBuffer sampleNames = new StringBuffer();
	        StringBuffer stringBuffer = new StringBuffer();
	    	header.append("Gene\tReporter\t");
	    	sampleNames.append("Name\tName\t\tType\t");
	    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
	        	String label = (String) labelIterator.next();
	        	header.append("Disease: "+label);
	        	sampleIds = geneViewContainer.getBiospecimenLabels(label);
	           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	            	sampleNames.append(sampleIdIterator.next()+"\t");
	            	header.append("\t");
	           	}

	    	}

	    	//System.out.println("Gene Count: "+genes.size());
			System.out.println(header.toString());
			System.out.println(sampleNames.toString());
	    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		Collection reporters = geneResultset.getReporterResultsets();
	        	//System.out.println("Repoter Count: "+reporters.size());
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		Collection groupTypes = reporterResultset.getGroupByResultsets();
	            	//System.out.println("Group Count: "+groupTypes.size());
	        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
	        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
	        			String label = groupResultset.getType().getValue().toString();
	        			sampleIds = geneViewContainer.getBiospecimenLabels(label);
	//        			Collection biospecimens = groupResultset.getBioSpecimenResultsets();
	//                	System.out.println("Biospecimen Count: "+biospecimens.size());
	//            		for (Iterator biospecimenIterator = biospecimens.iterator(); biospecimenIterator.hasNext();) {
	//            			BioSpecimenResultset biospecimenResultset = (BioSpecimenResultset)biospecimenIterator.next();
	        			            stringBuffer = new StringBuffer();
	                	            stringBuffer.append(geneResultset.getGeneSymbol().getValueObject().toString()+"\t"+
	                	            					reporterResultset.getReporter().getValue().toString()+"\t\t");
														//"| GroupType : "+groupResultset.getType().getValue().toString()+"\t");
	                               	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	                               		String sampleId = (String) sampleIdIterator.next();
	                               		SampleFoldChangeValuesResultset samplesResultset = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
	                               		if(samplesResultset != null){
	                               			Double ratio = (Double)samplesResultset.getFoldChangeRatioValue().getValue();
	                               			stringBuffer.append(resultFormat.format(ratio)+"\t");
	                               		}
	                               	}
	//            		}
	                               	System.out.println(stringBuffer.toString());
	        		}

	    		}

	    	}
		  }
        }
    }
    public static class CGH extends QueryTest {
       public void testCGHExprQuery() {
        ComparativeGenomicQuery q = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
             q.setQueryName("Test CGH Query");
             //q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
             //q.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
            //q.setGeneIDCrit(geneIDCrit);
            //q.setGeneOntologyCrit(ontologyCrit);
            //q.setRegionCrit(regionCrit);
            //q.setPathwayCrit(pathwayCrit);

            AssayPlatformCriteria crit = new AssayPlatformCriteria();
            crit.setAssayPlatformDE(new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY));
            q.setAssayPlatformCrit(crit);
            //q.setRegionCrit(regionCrit);
            //q.setSNPCrit(snpCrit);
            q.setAllGenesCrit(allGenesCriteria);
            q.setDiseaseOrGradeCrit(diseaseCrit);
            q.setCopyNumberCrit(copyNumberCrit);
            q.setSampleIDCrit(sampleCrit);
            try {
                //ResultSetInterface[] cghObjects = QueryManager.executeQuery(q);
                ResultSet[] cghObjects = QueryProcessor.execute(q);
                //print(geneExprObjects);
                //testResultset(geneExprObjects);
                System.out.println("Size: " + cghObjects.length);
                for (int i = 0; i < cghObjects.length; i++) {
                    CopyNumber cghObject =
                            (CopyNumber) cghObjects[i];
                    System.out.println("SampleID: " + cghObject.getSampleId() + " || Copy Number: "
                    + cghObject.getCopyNumber() + " || SNPProbesetName: " + cghObject.getSnpProbesetName()
                    + " || Chromosome: " + cghObject.getCytoband() );
                    if (cghObject.getAnnotations() != null)
                    System.out.println( "Annotation GeneSymbols: " +
                            cghObject.getAnnotations().getGeneSymbols()+
                       "  LocusLinks: " + cghObject.getAnnotations().getLocusLinkIDs() +
                    "  Accessions Numbers: " + cghObject.getAnnotations().getAccessionNumbers());
                }
            } catch(Throwable t ) {
                t.printStackTrace();
            }

        }
    }
     public static class Clinical extends QueryTest {
       public void testClinicalQuery() {
        ClinicalDataQuery q = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
            q.setQueryName("Test Clinical Query");

            //q.setSurvivalCrit(survivalCrit);
            q.setGenderCrit(genderCrit);
            q.setAgeCrit(ageCriteria);
            q.setDiseaseOrGradeCrit(diseaseCrit);
            q.setSampleIDCrit(sampleCrit);
            try {
                ResultSet[] patientDataObjects = QueryProcessor.execute(q);
                print(patientDataObjects);
                //testResultset(geneExprObjects);
            } catch(Throwable t ) {
                t.printStackTrace();
            }

        }

        /**
         * @param cghObjects
         */
        private void print(ResultSet[] patientDataObjects) {
            for(int i =0; i < patientDataObjects.length ;i++){
                PatientData patientData = (PatientData)patientDataObjects[i];
                System.out.println( patientData.getSampleId()+
                                    "\t"+patientData.getGender()+
                                    "\t"+patientData.getDiseaseType()+
                                    "\t"+patientData.getSurvivalLengthRange()+
                                    "\t"+patientData.getAgeGroup());
            }

        }
    }


     public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(GeneExpression.class));
        //suit.addTest(new TestSuite(CGH.class));
        //suit.addTest(new TestSuite(Clinical.class));

        //suit.addTest(new TestSuite(OJBSubSelectTest.GeneExpressionSubSelect.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }

    private void buildGeneIDFromFileCrit() {
        BufferedReader inFile = null;
        String inputLine = null;
        Collection geneSymbols = new ArrayList();
        try {
                inFile = new BufferedReader( new InputStreamReader(new FileInputStream("C:\\RembrandtDocs\\AllGeneSymbols.txt")));
                while ((inputLine = inFile.readLine()) != null) {
                    GeneIdentifierDE.GeneSymbol o = new GeneIdentifierDE.GeneSymbol(inputLine);
                    geneSymbols.add(o);
                }
         } catch (IOException e) {
                e.printStackTrace();
         }
         geneIDCrit = new GeneIDCriteria();
         geneIDCrit.setGeneIdentifiers(geneSymbols);
    }
    private void buildGeneIDCrit() {
        ArrayList inputIDs = new ArrayList();
        inputIDs.add(0, "10050");
        inputIDs.add(1, "10056");
        //inputIDs.add(0, "220988");    // Locus Link for hnRNPA3
        //inputIDs.add(0, "BF195526");      // accession numbers for hnRNPA3 gene are AW080932, AA527502, AA528233
         // inputIDs.add(0, "hnRNPA3");
        // GeneIdentifierDE.GeneSymbol gs = new GeneIdentifierDE.GeneSymbol("CTNND2");
        // inputIDs.add(gs);
         GeneIdentifierDE llObj1 =
                  new GeneIdentifierDE.LocusLink((String)inputIDs.get(0));
         GeneIdentifierDE llObj2 =
                  new GeneIdentifierDE.LocusLink((String)inputIDs.get(1));
         GeneIdentifierDE.GeneSymbol gs = new GeneIdentifierDE.GeneSymbol("VEGF");

         inputIDs.add(gs);
        // GeneIdentifierDE geIDObj =
           //       new GeneIdentifierDE.LocusLink((String)inputIDs.get(0));
       // GeneIdentifierDE geIDObj =
        //                new GeneIdentifierDE.GenBankAccessionNumber((String)inputIDs.get(0));
        //GeneIdentifierDE geIDObj =
          //              new GeneIdentifierDE.GeneSymbol((String)inputIDs.get(0));
        ArrayList geneIDentifiers = new ArrayList();

        geneIDentifiers.add(gs);
        //geneIDentifiers.add(llObj2);
        geneIDCrit = new GeneIDCriteria();
        geneIDCrit.setGeneIdentifiers(geneIDentifiers);
    }

    private void buildRegionCrit() {
        regionCrit = new RegionCriteria();

       // cytoband and start & end positions are mutually exclusive
       //regionCrit.setCytoband(new CytobandDE("p21.32"));
       //regionCrit.setStart(new BasePairPositionDE.StartPosition(new Integer(6900000)));
       //regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Integer(8800000)));

        // Chromosome Number is mandatory
        regionCrit.setChromNumber(new ChromosomeNumberDE(new String("1")));
        //regionCrit.setStart(new BasePairPositionDE.StartPosition(new Integer(1)));
        //regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Integer(5000000)));

        regionCrit.setStartCytoband(new CytobandDE("p36.33"));
        regionCrit.setEndCytoband(new CytobandDE("p36.11"));
    }

    private void buildOntologyCrit() {
        ontologyCrit = new GeneOntologyCriteria();
        //ontologyCrit.setGOIdentifier(new GeneOntologyDE("GO:0016021"));
        ontologyCrit.setGOIdentifier(new GeneOntologyDE("GO:0005634"));
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

    private void buildPathwayCrit() {
        pathwayCrit = new PathwayCriteria();
        PathwayDE obj1 = new PathwayDE("AcetaminophenPathway");
        PathwayDE obj2 = new PathwayDE("41bbPathway");
        PathwayDE obj3 = new PathwayDE("egfPathway");
        Collection pathways = new ArrayList();
        //pathways.add(obj1); pathways.add(obj2);
        pathways.add(obj3);
        pathwayCrit.setPathwayNames(pathways);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
        affyOligoPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM));
        cdnaPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.CDNA_ARRAY_PLATFORM));
    }
    private void buildAllGenesCriteria() {
        allGenesCriteria = new AllGenesCriteria(true);
        allGenesCriteria.setAllGenes(true);
    }
    private void buildDiseaseTypeCrit() {
         diseaseCrit = new DiseaseOrGradeCriteria();
         diseaseCrit.setDisease(new DiseaseNameDE("GBM"));
    }
    private void buildAllDiseaseTypeCrit() {
       diseaseCrit = new DiseaseOrGradeCriteria();
       diseaseCrit.setDisease(new DiseaseNameDE("OLIG"));
       diseaseCrit.setDisease(new DiseaseNameDE("GBM"));
        diseaseCrit.setDisease(new DiseaseNameDE("ASTROCYTOMA"));
        diseaseCrit.setDisease(new DiseaseNameDE("MIXED"));
        diseaseCrit.setDisease(new DiseaseNameDE("GLIOMA"));
    }
    private void buildSampleIDCrit() {
        sampleCrit = new SampleCriteria();
        Collection sampleIDs = new ArrayList();
        SampleIDDE s0 = new SampleIDDE("1057");
        SampleIDDE s1 = new SampleIDDE("118");
        SampleIDDE s2 = new SampleIDDE("305");
        SampleIDDE s3 = new SampleIDDE("990");
        SampleIDDE s4 = new SampleIDDE("1219");
        SampleIDDE s5 = new SampleIDDE("1227");
        SampleIDDE s6 = new SampleIDDE("1325");
        SampleIDDE s7 = new SampleIDDE("1348");
        SampleIDDE s8 = new SampleIDDE("1489");
        SampleIDDE s9 = new SampleIDDE("329");
        SampleIDDE s10 = new SampleIDDE("332");
        SampleIDDE s11 = new SampleIDDE("434");
        SampleIDDE s12 = new SampleIDDE("835");
        SampleIDDE s13 = new SampleIDDE("87");
        SampleIDDE s14 = new SampleIDDE("931");

        sampleIDs.add(s0);
        sampleIDs.add(s1);
        sampleIDs.add(s2);
         sampleIDs.add(s3);
       sampleIDs.add(s4); sampleIDs.add(s5); sampleIDs.add(s6); sampleIDs.add(s7);
       sampleIDs.add(s8); sampleIDs.add(s9);
        // sampleIDs.add(s10); sampleIDs.add(s11);
       // sampleIDs.add(s12); sampleIDs.add(s13); sampleIDs.add(s14);

        String[] IDs = new String[] {
               "1057", "1139","118","1219",
                "1220",
                "1223",
                "1226",
                "1227",
                "1232",
                "1269",
                "1297",
                "1307",
                "1325",
                "1326",
                "1338",
                "1348",
                "1397",
                "1409",
                "1458",
                "1489",
                "17",
                "189",
                "223",
                "252",
                "26",
                "305",
                "329",
                "332",
                "350",
                "434",
                "442",
                "453",
                "471",
                "50",
                "608",
                "615",
                "627",
                "652",
                "719",
                "813",
                "828",
                "835",
                "87",
                "89",
                "894",
                "931",
                "936",
                "953",
                "962",
                "986",
                "990"
        };
        ArrayList all52Samples = new ArrayList(52);
        for (int i = 0; i < IDs.length; i++) {
            all52Samples.add(new SampleIDDE(IDs[i]));
        }
        sampleCrit.setSampleIDs(sampleIDs);
        //sampleCrit.setSampleIDs(all52Samples);
    }

    private void buildSurvivalCrit() {
         survivalCrit  = new SurvivalCriteria();
         survivalCrit.setLowerSurvivalRange(
                 new SurvivalDE.LowerSurvivalRange(new Integer("40")));
         survivalCrit.setUpperSurvivalRange(
                 new SurvivalDE.UpperSurvivalRange(new Integer("60")));

    }
    private void buildAgeCrit() {
    	ageCriteria  = new AgeCriteria();
    	ageCriteria.setLowerAgeLimit(
                new AgeAtDiagnosisDE.LowerAgeLimit(new Integer("40")));
    	ageCriteria.setUpperAgeLimit(
                new UpperAgeLimit(new Integer("60")));

   }
    private void buildGenderCrit(){
        genderCrit = new GenderCriteria();
        genderCrit.setGenderDE(new GenderDE("M"));
    }
    private void buildProbeCrit() {
        probeCrit = new CloneOrProbeIDCriteria();
        //probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("204655_at"));
       // probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("243387_at"));
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("210354_at"));

    }

    private void buildCloneCrit() {
        cloneCrit = new CloneOrProbeIDCriteria();
        //cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:755299"));
        //cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:1287390"));
        //cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:2709102"));
        //cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:3303708"));
        //cloneCrit.setCloneIdentifier(new CloneIdentifierDE.IMAGEClone("1579639"));
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.IMAGEClone("IMAGE:143995"));
    }


    private void buildFoldChangeCrit() {
        Float upRegExpected = new Float(4.0);
        Float downRegExpected = new Float(3.8);
        ExprFoldChangeDE.UpRegulation upRegObj = new ExprFoldChangeDE.UpRegulation(upRegExpected );
        ExprFoldChangeDE.DownRegulation downRegObj = new ExprFoldChangeDE.DownRegulation(downRegExpected );
        ExprFoldChangeDE.UnChangedRegulationUpperLimit upUnChangedObj = new ExprFoldChangeDE.UnChangedRegulationUpperLimit(upRegExpected  );
        ExprFoldChangeDE.UnChangedRegulationDownLimit downUnChangedRegObj = new ExprFoldChangeDE.UnChangedRegulationDownLimit(downRegExpected );

        foldCrit = new FoldChangeCriteria();
        Collection objs = new ArrayList(4);
        //objs.add(upRegObj);
        objs.add(downRegObj);
        //objs.add(upRegObj); //objs.add(downUnChangedRegObj);
        foldCrit.setFoldChangeObjects(objs);
    }

     private void buildCopyChangeCrit() {
        Float amplification = new Float(10.0);
        Float deletion = new Float(1.0);
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
}
