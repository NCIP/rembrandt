package gov.nih.nci.nautilus.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import gov.nih.nci.nautilus.de.*;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.Criteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.view.ViewType;
import gov.nih.nci.nautilus.view.SampleCentricView;
import gov.nih.nci.nautilus.view.ViewFactory;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.lang.reflect.Constructor;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.PBFactoryException;
//import org.jaxen.expr.Expr;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 11, 2004
 * Time: 3:33:29 PM
 * To change this template use Options | File Templates.
 */
public class CriteriaTest extends TestCase {
      static FoldChangeCriteria foldCrit;
      static  GeneIDCriteria  geneIDCrit;
      static  RegionCriteria regionCrit;
    public static class RegionCriteriaTest extends CriteriaTest {
        protected void setUp() {
            regionCrit = new RegionCriteria();
            regionCrit.setChromNumber(new ChromosomeNumberDE("chr17"));
            regionCrit.setStart(new BasePairPositionDE.StartPosition(new Integer("34344251")));
            regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Integer("34344297")));
            regionCrit.setCytoband(new CytobandDE("17q11.2-q12"));
        }
        public RegionCriteriaTest () {
        }

        public void testRegionCriteria() {
            // TODO: really nothing
        }
    }
    public static class FoldChangeCriteriaTest extends CriteriaTest {
        Float upRegExpected = new Float(2.0);
        Float downRegExpected = new Float(1.0);
        //Float upperUnchangedExpected = new Float(4.0);
        //Float downUnChangedExpected = new Float(2.0);

        protected void setUp() {
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
        public void testFoldChangeCriteria() {
            Collection col = foldCrit.getFoldChangeObjects();
            for (Iterator iterator = col.iterator(); iterator.hasNext();) {
                ExprFoldChangeDE o = (ExprFoldChangeDE) iterator.next();
                Float result = o.getValueObject();
            }
            // TODO: verify result
        }
        public FoldChangeCriteriaTest() {
        }
    }
    public static class GeneIDCriteriaTest extends CriteriaTest {
        ArrayList inputIDs = new ArrayList();
	    protected void setUp() {
            inputIDs.add(0, "6352");
            inputIDs.add(1, "187011");
            GeneIdentifierDE.LocusLink geLocusObj =
                    new GeneIdentifierDE.LocusLink((String)inputIDs.get(0));
            GeneIdentifierDE.GenBankAccessionNumber geGenBankObj =
                            new GeneIdentifierDE.GenBankAccessionNumber((String)inputIDs.get(1));
            Vector geneIDentifiers = new Vector();
            geneIDentifiers.add(geLocusObj);
            geneIDentifiers.add(geGenBankObj);
            geneIDCrit = new GeneIDCriteria();
            geneIDCrit.setGeneIdentifiers(geneIDentifiers);

        }
        public void testGeneCriteria() {
            Collection objs = geneIDCrit.getGeneIdentifiers();
            ArrayList resultantIDs = new ArrayList();
            for (Iterator iterator = objs.iterator(); iterator.hasNext();) {
                GeneIdentifierDE o = (GeneIdentifierDE) iterator.next();
                resultantIDs.add(o.getValueObject());
            }
            assertTrue(resultantIDs.containsAll(inputIDs));
        }

        public GeneIDCriteriaTest() {
        }

	}
    public static class GeneExprQueryTest extends CriteriaTest {
        public GeneExprQueryTest() {
        }

        protected void setUp() {
            GeneExpressionQuery q = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
            q.setQueryName("Test Gene Query");
            q.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
            q.setGeneIDCrit(geneIDCrit);
            q.setRegionCrit(regionCrit);
            q.setFoldChgCrit(foldCrit);
            try {
                QueryManager.executeQuery(q);
            } catch(Throwable t ) {
                t.printStackTrace();
            }

        }
        public void testGeneExprQuery() {

        }
    }
    public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(GeneIDCriteriaTest.class));
        suit.addTest(new TestSuite(FoldChangeCriteriaTest.class));
        suit.addTest(new TestSuite(RegionCriteriaTest.class));
        suit.addTest(new TestSuite(GeneExprQueryTest.class));

        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }

}
