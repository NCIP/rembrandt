/*
 * Created on Aug 4, 2004
 *
 */
package gov.nih.nci.nautilus.de;

import java.util.Collection;


import gov.nih.nci.nautilus.de.*;


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
		//suite.addTest(new PBTest("testRetrievePathwayDE"));
		//suite.addTest(new PBTest("testRetrieveGeneSymbol"));
		//suite.addTest(new PBTest("testRetrieveLocusLink"));		
		//suite.addTest(new PBTest("testRetrieveGenBankAccessionNumber"));
		//suite.addTest(new PBTest("testRetrieveChromosomeNumberDE"));
		//suite.addTest(new PBTest("testRetrieveCytobandDE"));
		//suite.addTest(new PBTest("testRetrieveCytobandDE"));
		//suite.addTest(new PBTest("testRetrieveStartBasePair"));
		//suite.addTest(new PBTest("testRetrieveEndPositionPair"));
		//suite.addTest(new PBTest("testRetrieveUpRegulation"));
		//suite.addTest(new PBTest("testRetrieveDownRegulation"));
		//suite.addTest(new PBTest("testRetrieveUnChangedRegulationUpperLimit"));
		//suite.addTest(new PBTest("testRetrieveUnChangedRegulationDownLimit"));
		//suite.addTest(new PBTest("testRetrieveBACClone"));
		//suite.addTest(new PBTest("testRetrieveIMAGEClone"));
		//suite.addTest(new PBTest("testRetrieveProbeSetDE"));
		//suite.addTest(new PBTest("testRetrieveDiseaseNameDE"));
		//suite.addTest(new PBTest("testRetrieveInstitutionNameDE"));
		//suite.addTest(new PBTest("testRetrieveUTR_5"));
		//suite.addTest(new PBTest("testRetrieveUTR_3"));
		//suite.addTest(new PBTest("testRetrieveChemoAgentDE"));	
		//suite.addTest(new PBTest("testRetrieveTSC"));		
		//suite.addTest(new PBTest("testRetrieveSBSNP"));	
		//suite.addTest(new PBTest("testRetrieveSNPProbeSet"));	
		//suite.addTest(new PBTest("testRetrieveGeneOntologyDE"));	
		//suite.addTest(new PBTest("testRetrieveArrayPlatformDE"));	
		//suite.addTest(new PBTest("testRetrieveGenderDE"));	
		//suite.addTest(new PBTest("testRetrieveAlleleFrequencyDE"));	
		//suite.addTest(new PBTest("testRetrieveAgeAtDiagnosisDE"));	
		//suite.addTest(new PBTest("testRetrieveSurvivalDE"));
		//suite.addTest(new PBTest("testRetrieveAmplification"));
		//suite.addTest(new PBTest("testRetrieveDeletion"));
		suite.addTest(new PBTest("testRetrieveUnchange"));
		
		return suite;
	}
	
	public void testRetrieveUnchange() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				CopyNumberDE$UnChangedCopyNumberUpperLimit.class, new Criteria(), true);			    			
			Collection unchange = pb.getCollectionByQuery(q);
			System.err.println("Got " + unchange.size() + " unchange objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 public void testRetrieveDeletion() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				CopyNumberDE$Deletion.class, new Criteria(), true);			    			
			Collection deletion = pb.getCollectionByQuery(q);
			System.err.println("Got " + deletion.size() + " deletion objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 public void testRetrieveAmplification() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				CopyNumberDE$Amplification.class, new Criteria(), true);			    			
			Collection amplification = pb.getCollectionByQuery(q);
			System.err.println("Got " + amplification.size() + " amplification objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveSurvivalDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				SurvivalDE.class, new Criteria(), true);			    			
			Collection survivalDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + survivalDE.size() + " survivalDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveAgeAtDiagnosisDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				AgeAtDiagnosisDE.class, new Criteria(), true);			    			
			Collection ageAtDiagnosisDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + ageAtDiagnosisDE.size() + " ageAtDiagnosisDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveAlleleFrequencyDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				AlleleFrequencyDE.class, new Criteria(), true);			    			
			Collection alleleFrequencyDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + alleleFrequencyDE.size() + " alleleFrequencyDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveArrayPlatformDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
				 ArrayPlatformDE.class, new Criteria(), true);			    			
			Collection arrayPlatformDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + arrayPlatformDE.size() + " arrayPlatformDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 public void testRetrieveGenderDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GenderDE.class, new Criteria(), true);			    			
			Collection genderDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + genderDE.size() + " genderDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveGeneOntologyDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GeneOntologyDE.class, new Criteria(), true);			    			
			Collection geneOntologyDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + geneOntologyDE.size() + " geneOntologyDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveSNPProbeSet() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					SNPIdentifierDE$SNPProbeSet.class, new Criteria(), true);			    			
			Collection sNPProbeSet = pb.getCollectionByQuery(q);
			System.err.println("Got " + sNPProbeSet.size() + " sNPProbeSet objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 public void testRetrieveSBSNP() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					SNPIdentifierDE$SBSNP.class, new Criteria(), true);			    			
			Collection sNPProbeSet = pb.getCollectionByQuery(q);
			System.err.println("Got " + sNPProbeSet.size() + " sNPProbeSet objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveTSC() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					SNPIdentifierDE$TSC.class, new Criteria(), true);			    			
			Collection tsc = pb.getCollectionByQuery(q);
			System.err.println("Got " + tsc.size() + " tsc objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }

	public void testRetrieveChemoAgentDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ChemoAgentDE.class, new Criteria(), true);			    			
			Collection chemoAgentDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + chemoAgentDE.size() + " chemoAgentDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveUTR_3() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					UntranslatedRegionDE$UTR_3.class, new Criteria(), true);			    			
			Collection uTR_3 = pb.getCollectionByQuery(q);
			System.err.println("Got " + uTR_3.size() + " uTR_3 objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveUTR_5() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					UntranslatedRegionDE$UTR_5.class, new Criteria(), true);			    			
			Collection uTR_5 = pb.getCollectionByQuery(q);
			System.err.println("Got " + uTR_5.size() + " uTR_5 objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveInstitutionNameDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					InstitutionNameDE.class, new Criteria(), true);			    			
			Collection institutionNameDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + institutionNameDE.size() + " institutionNameDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveDiseaseNameDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					DiseaseNameDE.class, new Criteria(), true);			    			
			Collection diseaseNameDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + diseaseNameDE.size() + " diseaseNameDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveProbeSetDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ProbeSetDE.class, new Criteria(), true);			    			
			Collection probeSetDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + probeSetDE.size() + " probeSetDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 
	public void testRetrieveIMAGEClone() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					CloneIdentifierDE$IMAGEClone.class, new Criteria(), true);			    			
			Collection iMAGEClone = pb.getCollectionByQuery(q);
			System.err.println("Got " + iMAGEClone.size() + " iMAGEClone objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveBACClone() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					CloneIdentifierDE$BACClone.class, new Criteria(), true);			    			
			Collection bACClone = pb.getCollectionByQuery(q);
			System.err.println("Got " + bACClone.size() + " bACClone objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveUnChangedRegulationDownLimit() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ExprFoldChangeDE$UnChangedRegulationDownLimit.class, new Criteria(), true);			    			
			Collection unChangedRegulationDownLimit = pb.getCollectionByQuery(q);
			System.err.println("Got " + unChangedRegulationDownLimit.size() + " unChangedRegulationDownLimit objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 
	public void testRetrieveUnChangedRegulationUpperLimit() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ExprFoldChangeDE$UnChangedRegulationUpperLimit.class, new Criteria(), true);			    			
			Collection unChangedRegulationUpperLimit = pb.getCollectionByQuery(q);
			System.err.println("Got " + unChangedRegulationUpperLimit.size() + " unChangedRegulationUpperLimit objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveDownRegulation() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ExprFoldChangeDE$DownRegulation.class, new Criteria(), true);			    			
			Collection downRegulation = pb.getCollectionByQuery(q);
			System.err.println("Got " + downRegulation.size() + " downRegulation objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveUpRegulation() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ExprFoldChangeDE$UpRegulation.class, new Criteria(), true);			    			
			Collection upRegulation = pb.getCollectionByQuery(q);
			System.err.println("Got " + upRegulation.size() + " upRegulation objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveEndPositionPair() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					BasePairPositionDE$EndPosition.class, new Criteria(), true);			    			
			Collection endPosition = pb.getCollectionByQuery(q);
			System.err.println("Got " + endPosition.size() + " endPosition objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveStartBasePair() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					BasePairPositionDE$StartPosition.class, new Criteria(), true);			    			
			Collection startBasePair = pb.getCollectionByQuery(q);
			System.err.println("Got " + startBasePair.size() + " startBasePair objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 
	public void testRetrieveCytobandDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					CytobandDE.class, new Criteria(), true);			    			
			Collection cytobandDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + cytobandDE.size() + " cytobandDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	
	public void testRetrieveChromosomeNumberDE() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					ChromosomeNumberDE.class, new Criteria(), true);			    			
			Collection chromosomeNumberDE = pb.getCollectionByQuery(q);
			System.err.println("Got " + chromosomeNumberDE.size() + " chromosomeNumberDE objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveGenBankAccessionNumber() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GeneIdentifierDE$GenBankAccessionNumber.class, new Criteria(), true);			    			
			Collection genBankAccessionNumber = pb.getCollectionByQuery(q);
			System.err.println("Got " + genBankAccessionNumber.size() + " genBankAccessionNumber objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	 
	public void testRetrieveLocusLink() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GeneIdentifierDE$LocusLink.class, new Criteria(), true);			    			
			Collection locusLink = pb.getCollectionByQuery(q);
			System.err.println("Got " + locusLink.size() + " locusLink objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	 }
	public void testRetrieveGeneSymbol() {
		try {
		   
			PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();						
			QueryByCriteria q = (QueryByCriteria) QueryFactory.newQuery(
					GeneIdentifierDE$GeneSymbol.class, new Criteria(), true);			    			
			Collection geneSymbol = pb.getCollectionByQuery(q);
			System.err.println("Got " + geneSymbol.size() + " geneSymbol objects.");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
		}
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
