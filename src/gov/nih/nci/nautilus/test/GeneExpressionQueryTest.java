package gov.nih.nci.nautilus.test;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.resultset.ResultsetProcessor;
import gov.nih.nci.nautilus.resultset.*;
import java.text.DecimalFormat;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 20, 2004
 * Time: 1:41:11 PM
 * To change this template use Options | File Templates.
 */
public class GeneExpressionQueryTest extends TestCase {
	 private static final DecimalFormat resultFormat = new DecimalFormat("0.00");
     FoldChangeCriteria foldCrit;
     GeneIDCriteria  geneIDCrit;
     GeneOntologyCriteria ontologyCrit;
     PathwayCriteria pathwayCrit;
     RegionCriteria regionCrit;
     CloneOrProbeIDCriteria cloneCrit;
     CloneOrProbeIDCriteria probeCrit;
     ArrayPlatformCriteria allPlatformCrit;
     ArrayPlatformCriteria affyOligoPlatformCrit;
     ArrayPlatformCriteria cdnaPlatformCrit;

    protected void setUp() throws Exception {
        buildRegionCrit();
        buildPlatformCrit();
        buildCloneCrit();
        buildProbeCrit();
        buildFoldChangeCrit();
        buildGeneIDCrit();
        buildOntologyCrit();
        buildPathwayCrit();
    }
    public void testGeneExprQuery() {
        GeneExpressionQuery q = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
             q.setQueryName("Test Gene Query");
             q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
             //q.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
            //q.setGeneIDCrit(geneIDCrit);
            //q.setGeneOntologyCrit(ontologyCrit);
            //q.setRegionCrit(regionCrit);
            q.setPathwayCrit(pathwayCrit);

            q.setArrayPlatformCrit(allPlatformCrit);
           //q.setPlatCriteria(affyOligoPlatformCrit);
           //q.setPlatCriteria(cdnaPlatformCrit);

            //q.setCloneProbeCrit(cloneCrit);
            //q.setCloneProbeCrit(probeCrit);

            q.setFoldChgCrit(foldCrit);

            try {
                ResultSet[] geneExprObjects = QueryManager.executeQuery(q);
                print(geneExprObjects);
                testResultset(geneExprObjects);
            } catch(Throwable t ) {
                t.printStackTrace();
            }

    }
      private void print(ResultSet[] geneExprObjects) {
            int count = 0;
            HashSet probeIDS = new HashSet();
            HashSet cloneIDs = new HashSet();


          for (int i = 0; i < geneExprObjects.length; i++) {
              ResultSet obj = geneExprObjects[i];
                GeneExpr exprObj = null;
                if (obj instanceof GeneExpr.GeneExprGroup)  {
                    exprObj = (GeneExpr.GeneExprGroup) obj;
                }
                 else if (obj instanceof GeneExpr.GeneExprSingle)  {
                    exprObj = (GeneExpr.GeneExprSingle) obj;
                }
                if (exprObj.getProbesetId() != null) {
                    System.out.println("Disease: "+ exprObj.getDiseaseType());
                  // System.out.println("ProbesetID: " + exprObj.getProbesetId() + " :Exp Value: "
                    //            + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol() );
                    probeIDS.add(exprObj.getProbesetId());
                }
                if ( exprObj.getCloneId() != null) {
                   // System.out.println("CloneID: " + exprObj.getCloneId()+ " :Exp Value: "
                     //           + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol());
                    cloneIDs.add(exprObj.getCloneId() );
                }

                 ++count;
            }
            System.out.println("Total Number Of Samples: " + count);
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

            return ;
    }
      public void testResultset(ResultSet[] geneExprObjects){
    	ResultsetProcessor resultsetProc = new ResultsetProcessor();
    	assertNotNull(geneExprObjects);
        assertTrue(geneExprObjects.length > 0);
    	resultsetProc.handleGeneView(geneExprObjects, GroupType.DISEASE_TYPE_GROUP);
    	GeneViewContainer geneViewContainer = resultsetProc.getGeneViewContainer();
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
        		Collection groupTypes = reporterResultset.getGroupResultsets();
            	//System.out.println("Group Count: "+groupTypes.size());
        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
        			GroupResultset groupResultset = (GroupResultset)groupIterator.next();
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
                               		BioSpecimenResultset biospecimenResultset = groupResultset.getBioSpecimenResultset(sampleId);
                               		if(biospecimenResultset != null){
                               			Double ratio = (Double)biospecimenResultset.getFoldChangeRatioValue().getValue();
                               			stringBuffer.append(resultFormat.format(ratio)+"\t");                                 
                               		}                                    		
                               	}
//            		}
                               	System.out.println(stringBuffer.toString());
        		}

    		}

    	}
	
    }
     public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(GeneExpressionQueryTest.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }


    private void buildGeneIDCrit() {
        ArrayList inputIDs = new ArrayList();
        //inputIDs.add(0, "220988");    // Locus Link for hnRNPA3
        //inputIDs.add(0, "BF195526");      // accession numbers for hnRNPA3 gene are AW080932, AA527502, AA528233
          inputIDs.add(0, "hnRNPA3");
         // GeneIdentifierDE geIDObj =
           //       new GeneIdentifierDE.LocusLink((String)inputIDs.get(0));
       // GeneIdentifierDE geIDObj =
        //                new GeneIdentifierDE.GenBankAccessionNumber((String)inputIDs.get(0));
        GeneIdentifierDE geIDObj =
                        new GeneIdentifierDE.GeneSymbol((String)inputIDs.get(0));
        Vector geneIDentifiers = new Vector();

        geneIDentifiers.add(geIDObj);
        geneIDCrit = new GeneIDCriteria();
        geneIDCrit.setGeneIdentifiers(geneIDentifiers);
    }

    private void buildRegionCrit() {
        regionCrit = new RegionCriteria();

        // cytoband and start & end positions are mutually exclusive
        //regionCrit.setCttoband(new CytobandDE("p36.23"));
        regionCrit.setStart(new BasePairPositionDE.StartPosition(new Integer(6900000)));
        regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Integer(8800000)));

        // Chromosome Number is mandatory
        regionCrit.setChromNumber(new ChromosomeNumberDE(new String("chr1")));
    }

    private void buildOntologyCrit() {
        ontologyCrit = new GeneOntologyCriteria();
        ontologyCrit.setGOIdentifier(new GeneOntologyDE(new Integer(4)));
    }

    private void buildPathwayCrit() {
        pathwayCrit = new PathwayCriteria();
        PathwayDE obj1 = new PathwayDE("AcetaminophenPathway");
        PathwayDE obj2 = new PathwayDE("41bbPathway");
        Collection pathways = new ArrayList();
        pathways.add(obj1); pathways.add(obj2);
        pathwayCrit.setPathwayNames(pathways);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
        affyOligoPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM));
        cdnaPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.CDNA_ARRAY_PLATFORM));
    }

    private void buildProbeCrit() {
        probeCrit = new CloneOrProbeIDCriteria();
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("204655_at"));
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("243387_at"));
    }

    private void buildCloneCrit() {
        cloneCrit = new CloneOrProbeIDCriteria();
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:755299"));
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:1287390"));
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:2709102"));
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.BACClone("IMAGE:3303708"));
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
}
