/*
 * Created on Aug 4, 2004
 *
 */
package gov.nih.nci.nautilus.de;

import java.util.Collection;

import gov.nih.nci.nautilus.de.GradeDE;
import gov.nih.nci.nautilus.de.PathwayDE;


import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

import junit.framework.TestCase;

/**
 * @author Joshua Phillips
 *
 */
public class PBTest extends TestCase {

	private static Class CLASS = PBTest.class;

	/**
	 *
	 */
	public PBTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public PBTest(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		try {
		    
			junit.textui.TestRunner.main(new String[]{CLASS.getName()});
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static junit.framework.Test suite() {	
		junit.framework.TestSuite suite = new junit.framework.TestSuite();
		//suite.addTest(new PBTest("testRetrieveGradeDE"));
		suite.addTest(new PBTest("testRetrievePathwayDE"));
		
		return suite;
	}

	public void testRetrieveGradeDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GradeDE.class, new Criteria(), true);			    			
			Collection gradeDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + gradeDE.size() + " gradeDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

public void testRetrievePathwayDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();									
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					PathwayDE.class, new Criteria(), true);			    			
			Collection pathDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + pathDE.size() + " PathwayDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}



}
