/*
 * Created on Oct 18, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.test;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompoundQueryTest extends TestCase {
    ArrayPlatformCriteria allPlatformCrit;
    ArrayPlatformCriteria affyOligoPlatformCrit;
    ArrayPlatformCriteria cdnaPlatformCrit;
    CloneOrProbeIDCriteria cloneCrit;
    CloneOrProbeIDCriteria probeCrit;
    GeneIDCriteria geneCrit;
    GeneExpressionQuery probeQuery;
    GeneExpressionQuery cloneQuery;
    GeneExpressionQuery geneQuery;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        buildPlatformCrit();
        buildCloneCrit();
        buildProbeCrit();
        buildGeneIDCrit();
        buildGeneExprCloneSingleViewQuery();
        buildGeneExprProbeSetSingleViewQuery();
        buildGeneExprGeneSingleViewQuery();        
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCompoundQueryProcessor() {
		try {
			CompoundQuery myCompoundQuery = new CompoundQuery(geneQuery);
			ResultSet[] geneExprObjects = QueryManager.executeQuery(myCompoundQuery);
			System.out.println("SingleQuery:\n"+ myCompoundQuery.toString());
			print(geneExprObjects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    /**
	 * @param geneExprObjects
	 */
	private void print(ResultSet[] geneExprObjects) {
		if(geneExprObjects != null){
	        for (int i =0; i <= geneExprObjects.length; i++) {
	        	DifferentialExpressionSfact expObj = (DifferentialExpressionSfact) geneExprObjects[i];
	            System.out.println( "uID: " +expObj.getDesId()+ "|geneSymbol: " + expObj.getGeneSymbol() + " |CloneId: " +expObj.getCloneId()+ " |probesetId: " +expObj.getProbesetId()+"|expObj.biospecimenID: " +expObj.getBiospecimenId() );
	        }
		}
		
	}

	public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(CompoundQueryTest.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }
	public void testExecute() {
	}
	private void buildProbeCrit() {
        probeCrit = new CloneOrProbeIDCriteria();
        //1555146_at is a probeSet for ATF2
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("1555146_at"));
    }

    private void buildCloneCrit() {
        cloneCrit = new CloneOrProbeIDCriteria();
        //IMAGE:2014733 is a CloneID for AFT2
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.IMAGEClone("IMAGE:2014733"));

    }
    private void buildGeneIDCrit() {
        geneCrit = new GeneIDCriteria();
        //Both IMAGE:2014733 and 1555146_at should be subsets of ATF2
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol("ATF2"));

    }
    private void buildGeneExprProbeSetSingleViewQuery(){
        probeQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        probeQuery.setQueryName("ProbeSetQuery");
        probeQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        probeQuery.setCloneOrProbeIDCrit(probeCrit);
    }
    private void buildGeneExprCloneSingleViewQuery(){
        cloneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        cloneQuery.setQueryName("CloneQuery");
        cloneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        cloneQuery.setCloneOrProbeIDCrit(cloneCrit);
    }
    private void buildGeneExprGeneSingleViewQuery(){
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("GeneQuery");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(allPlatformCrit);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
        affyOligoPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM));
        cdnaPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.CDNA_ARRAY_PLATFORM));
    }
    private void changeQueryView(Query query,ViewType view){
    	if(query !=null){
    		query.setAssociatedView(view);
    	}
    }
}
