package gov.nih.nci.nautilus.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import org.apache.ojb.broker.*;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 17, 2004
 * Time: 4:10:25 PM
 * To change this template use Options | File Templates.
 */
public class OJBTest extends TestCase{
    PersistenceBroker broker;

    private static Class CLASS = OJBTest.class;
    public OJBTest(String s) {
        super(s);
    }
    public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(OJBTest.class));
        return suit;
	}


    public static void main (String[] args) {
            junit.textui.TestRunner.run(suite());

        }

   public void setUp()
    {
       try {
           broker = PersistenceBrokerFactory.defaultPersistenceBroker();
       } catch (PBFactoryException e) {
           e.printStackTrace();
       }

   }
    public void tearDown()
    {
        broker.close();
    }

    /**
	 * test EqualTo Criteria
	 */
    public void testCriteria()
    {
        long exprSampleID = 1;
        Criteria exprCrit = new Criteria();
        /*exprCrit.addEqualTo("desId", new Long(exprSampleID));
        Query exprQuery = QueryFactory.newQuery(DifferentialExpressionSfact.class, exprCrit);
        Collection exprObjects = broker.getCollectionByQuery(exprQuery);
        for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
            DifferentialExpressionSfact expObj = (DifferentialExpressionSfact) iterator.next();
            Criteria geneSrit = new Criteria();
            geneSrit.addEqualTo("geneId", expObj.getGeneId());
            geneSrit.addEqualTo("geneSymbol", "CYP2A6");
            Query geneDimQuery = QueryFactory.newQuery(ProbesetDim.class, geneSrit);
            ProbesetDim geneObj = (ProbesetDim) broker.getObjectByQuery(geneDimQuery);
            System.out.println("geneObj.title: " + geneObj.getGeneTitle() + geneObj.getGenomeVersion() );

        }*/
        Criteria geneCrit = new Criteria();
        geneCrit.addEqualTo("geneSymbol", "CCL5");
        Query probeDimQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {"probesetId"}, geneCrit, true);

        Criteria dExprCrit= new Criteria();
        dExprCrit.addIn("probesetId", probeDimQuery);
        Query geneExprQuery = QueryFactory.newQuery(DifferentialExpressionSfact.class, dExprCrit);
        DifferentialExpressionSfact exprObj = (DifferentialExpressionSfact) broker.getObjectByQuery(geneExprQuery);
        System.out.println("expr:");


       /*
        assertNotNull(results);
        assertTrue(results.size() > 0);*/

    }
}
