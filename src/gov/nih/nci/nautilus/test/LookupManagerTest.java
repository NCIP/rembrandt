/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.test;

import java.util.Iterator;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.lookup.CytobandLookup;
import gov.nih.nci.nautilus.lookup.ExpPlatformLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.lookup.PatientDataLookup;
import junit.framework.TestCase;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LookupManagerTest extends TestCase {

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

	public void testGetCytobands() {
		/*try {
			CytobandLookup[] cytobands = LookupManager.getCytobands();
			assertNotNull(cytobands);
	        for (int i =0;i<cytobands.length;i++) {
	        	CytobandLookup cytoband = cytobands[i];
	            System.out.println("cbEndPos"+ cytoband.getCbEndPos()+
	            					"\t cbStart"+cytoband.getCbStart()+
									"\t chromosome"+cytoband.getChromosome()+
									"\t cytoband"+cytoband.getCytoband()+
									"\t cytobandPositionId"+cytoband.getCytobandPositionId()+
									"\t organism"+cytoband.getOrganism());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
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
	        for (int i =0;i<patientData.length;i++) {
	        	PatientDataLookup patient = patientData[i];
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
