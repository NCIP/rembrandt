/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.test;

import java.util.Iterator;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.lookup.CytobandLookup;
import gov.nih.nci.nautilus.lookup.ExpPlatformLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.lookup.PatientDataLookup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LookupManagerTest extends TestCase {
	/**
	 * @param string
	 */
	public LookupManagerTest(String string) {
		super(string);
	}

	public static Test suite() {
		TestSuite suite =  new TestSuite();
        //suite.addTest(new LookupManagerTest("testGetCytobandPositions"));
        suite.addTest(new LookupManagerTest("testgetCytobandDEs"));
        //suite.addTest(new LookupManagerTest("testGetExpPlatforms"));
        //suite.addTest(new LookupManagerTest("testGetPathways"));
        //suite.addTest(new LookupManagerTest("testGetPatientData"));
        //suite.addTest(new LookupManagerTest("testPatientData"));


        return suite;
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCytobandPositions() {
		try {
			CytobandLookup[] cytobands = LookupManager.getCytobandPositions();
			assertNotNull(cytobands);
			System.out.println("cbEndPos"+
					"\tcbStart"+
					"\tchromosome"+
					"\tcytoband"+
					"\tcytobandPositionId"+
					"\torganism");
	        for (int i =0;i<cytobands.length;i++) {
	        	CytobandLookup cytoband = cytobands[i];
	            System.out.println(cytoband.getCbEndPos()+
	            					"\t"+cytoband.getCbStart()+
									"\t"+cytoband.getChromosome()+
									"\t"+cytoband.getCytoband()+
									"\t"+cytoband.getCytobandPositionId()+
									"\t"+cytoband.getOrganism());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void testgetCytobandDEs(){
		ChromosomeNumberDE[] chromosomes;
		try {
			chromosomes = LookupManager.getChromosomeDEs();
			if(chromosomes != null){
				for(int i =0; i < chromosomes.length; i++){
					System.out.println("Chr:"+ chromosomes[i].getValueObject());
					CytobandDE[] cytobands = LookupManager.getCytobandDEs(chromosomes[i]);
					if(cytobands != null){
						for(int k = 0; k < cytobands.length; k++){
							System.out.println("Cytos:"+ cytobands[k].getValueObject());
						}
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void testGetPathways() {
		//TODO Implement getPathways().
	}

	public void testGetPatientData() {
		//TODO Implement getPatientData().
	}
    public void testGetExpPlatforms(){
	    /*try{
	    	ExpPlatformLookup[] expPlatforms = LookupManager.getExpPlatforms();
			assertNotNull(expPlatforms);
	        for (int i =0;i<expPlatforms.length;i++) {
	        	ExpPlatformLookup platform = expPlatforms[i];
	            System.out.println("expPlatformName"+ platform.getExpPlatformName()+
	            					"\t expPlatformDesc"+platform.getExpPlatformDesc()+
									"\t expPlatformId"+platform.getExpPlatformId());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    }
    public void testPatientData(){
	    try{
	    	PatientDataLookup[] patientData = LookupManager.getPatientData();
			assertNotNull(patientData);
			System.out.println("patientID"+
					"\t survival"+
					"\t censor");
	        for (int i =0;i<patientData.length;i++) {
	        	PatientDataLookup patient = patientData[i];
	        	System.out.println(patient.getSampleId()+
    					"\t"+patient.getSurvivalLength()+
						"\t"+patient.getCensoringStatus());

	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
