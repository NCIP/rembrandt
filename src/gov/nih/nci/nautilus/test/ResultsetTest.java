package gov.nih.nci.nautilus.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import org.apache.ojb.broker.*;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author SahniH
 * Date: September 20, 2004 
 * @version $Revision: 1.1 $
 * This junit test encapsulates the query and resultset tests
 * 
 * 
 *
 * */
public class ResultsetTest extends TestCase{
    PersistenceBroker broker;

    private static Class CLASS = ResultsetTest.class;
    public ResultsetTest(String s) {
        super(s);
    }
   	public static junit.framework.Test suite() {	
    	junit.framework.TestSuite suite = new junit.framework.TestSuite();
        suite.addTest(new ResultsetTest("testCriteria"));
    	return suite;
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
           fail("Got " + e.getClass().getName() + ": " + e.getMessage());
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
    	try{
        Criteria exprCrit = new Criteria();
        exprCrit.addEqualTo("geneSymbol", "CYP2A6");
        Query exprQuery = QueryFactory.newQuery(DifferentialExpressionSfact.class, exprCrit);
        Collection exprObjects = broker.getCollectionByQuery(exprQuery);
		System.err.println("Got " + exprObjects.size() + " exprObjects objects.");
	        for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
	            DifferentialExpressionSfact expObj = (DifferentialExpressionSfact) iterator.next();
	            System.out.println("expObj.geneSymbol: " + expObj.getGeneSymbol() + "expObj.probesetName: " +expObj.getProbesetName() );
	        }
 		} catch (Exception ex) {
 			ex.printStackTrace();
 			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
 		}

       /*
        assertNotNull(results);
        assertTrue(results.size() > 0);*/

    }
}
