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

import java.util.Collection;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 20, 2004
 * Time: 1:41:11 PM
 * To change this template use Options | File Templates.
 */
public class GeneExpressionQueryTest extends TestCase {
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
            q.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
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
                QueryManager.executeQuery(q);
                
            } catch(Throwable t ) {
                t.printStackTrace();
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
