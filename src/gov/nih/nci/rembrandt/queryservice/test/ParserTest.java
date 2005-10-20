/*
 * ParserTest.java
 * Created on Sep 28, 2004
 *Owner
 * 
 */
package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;

import java.util.Vector;

import junit.framework.TestCase;

/**
 * Add one sentence class summary here
 * Add class description here
 * @author Owner
 * @version 1.0, Sep 28, 2004
 *
**/
public class ParserTest extends TestCase {

	private Vector v1;
	/**
	 * Constructor for ParserTest.
	 * @param name
	 */
	public ParserTest(String name) {
		super(name);
	}

	/*
	 * @see TestCase#setUp()
	 */
	GeneExpressionQuery q1 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	GeneExpressionQuery q2 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	GeneExpressionQuery q3 = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);

	ComparativeGenomicQuery q4 = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
	ComparativeGenomicQuery q5 = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
	ComparativeGenomicQuery q6 = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);

	ClinicalDataQuery q7 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
	ClinicalDataQuery q8 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
	ClinicalDataQuery q9 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);

	protected void setUp() throws Exception {
		

		q1.setQueryName("Query 1");
		q2.setQueryName("Query 2");
		q3.setQueryName("Query 3");
		
		super.setUp();
		v1 = new Vector();
		v1.addElement(q1);
		v1.addElement("PRB");
		v1.addElement("(");
		v1.addElement(q2);
		v1.addElement("OR");
		v1.addElement(q3);
		v1.addElement(")");
		
		
		
		
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParser() {
		try {
// Parser Test 			
//			Parser p = new Parser(v1);
//			p.expression();
//			Queriable q = p.getCompoundQuery();
//			System.out.println("Compound Query is "+q.toString());
//			CompoundQuery x = new CompoundQuery(OperatorType.AND, q1,q2);
//			System.out.println("Query is "+x.toString());
// Test Valid Views in Compound Query
			CompoundQuery x = new CompoundQuery(OperatorType.AND, q1,q4);
			CompoundQuery y = new CompoundQuery(OperatorType.OR, q1, q7);
			ViewType[] validv = y.getValidViews();
			
			assertNotNull(validv);
			for (int i = 0; i < validv.length; i++) {
				System.out.println("View Type "+validv[i].getClass().getName());
			}
	
		}catch (Exception e){
			System.out.println("Error is " + e.getMessage());
		}
			
		
	}


}
