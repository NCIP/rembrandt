/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.lookup;

import gov.nih.nci.nautilus.data.CytobandPosition;
import gov.nih.nci.nautilus.data.DiseaseTypeDim;
import gov.nih.nci.nautilus.data.ExpPlatformDim;
import gov.nih.nci.nautilus.data.PatientData;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LookupManager{
	private static PatientDataLookup[] patientData;
	private static CytobandLookup[] cytobands;
	private static Lookup[] pathways;
	private static ExpPlatformLookup[] expPlatforms;
	private static DiseaseTypeLookup[] diseaseTypes;
	
	private  static Collection executeQuery(Class bean, Criteria crit)throws Exception{
			   Collection resultsetObjs = null;
			   PersistenceBroker broker = PersistenceBrokerFactory.defaultPersistenceBroker();
	           resultsetObjs = createQuery(bean, crit, broker);
	           broker.close();
	           return resultsetObjs;
	       
	}
	private static Collection createQuery(Class bean, Criteria crit, PersistenceBroker broker) throws Exception{
			//Criteria crit = new Criteria();
			Collection resultsetObjs = null;
	        Query exprQuery = QueryFactory.newQuery(bean, crit,true);
	        resultsetObjs = broker.getCollectionByQuery(exprQuery);
			System.out.println("Got " + resultsetObjs.size() + " resultsetObjs objects.");
     
		    return resultsetObjs;
	}

	/**
	 * @return Returns the cytobands.
	 */
	public static CytobandLookup[] getCytobandPositions() throws Exception{
		if(cytobands == null){
			Criteria crit = new Criteria();
			crit.addOrderByAscending("chrCytoband");
			cytobands = (CytobandLookup[]) executeQuery(CytobandPosition.class, crit).toArray(new CytobandLookup[1]);
		}
		return cytobands;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public static ChromosomeNumberDE[] getChromosomeDEs() throws Exception {
		Collection chromosomeDEs = new ArrayList();
		ChromosomeNumberDE chromosomeDE ;
		CytobandLookup[] cytobandLookups = getCytobandPositions();
		if(cytobandLookups != null){
			for (int i = 0;i < cytobandLookups.length;i++){
				chromosomeDE = new ChromosomeNumberDE(cytobandLookups[i].getChromosome());
				chromosomeDEs.add(chromosomeDE);			
			}
		}
		return (ChromosomeNumberDE[]) chromosomeDEs.toArray(new ChromosomeNumberDE[1]);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public static CytobandDE[] getCytobandDEs(ChromosomeNumberDE chromosomeDE) throws Exception {
		Collection cytobandDEs = new ArrayList();
		CytobandDE cytobandDE ;
		CytobandLookup[] cytobandLookups = getCytobandPositions();
		if(cytobandLookups != null){
			for (int i = 0;i < cytobandLookups.length;i++){
				if(chromosomeDE.getValue().toString().equals(cytobandLookups[i].getChromosome())){
					cytobandDE = new CytobandDE(cytobandLookups[i].getCytoband());
					cytobandDEs.add(cytobandDE);	
				}
			}
		}
		return (CytobandDE[]) cytobandDEs.toArray(new CytobandDE[1]);
	}
	/**
	 * @return Returns the pathways.
	 */
	public Lookup[] getPathways() {
		return pathways;
	}
	/**
	 * @return Returns the patientData.
	 * @throws Exception
	 */
	public static PatientDataLookup[] getPatientData() throws Exception {
		if(patientData == null){
			Criteria crit = new Criteria();
			patientData = (PatientDataLookup[])(executeQuery(PatientData.class,crit).toArray(new PatientDataLookup[1]));
		}
		return patientData;
	}
	/**
	 * @return Returns the patientDataMap.
	 * @throws Exception
	 * BiospecimenId is the key & PatientDataLookup is the returned object
	 */
	public static Map getPatientDataMap() throws Exception{
		PatientDataLookup[] patients = getPatientData();
		Map patientDataMap = new HashMap();
		if(patients != null){
			for (int i = 0;i < patients.length;i++){
				String key = patients[i].getBiospecimenId().toString();
				PatientDataLookup patient = patients[i];				
				patientDataMap.put(key,patient);				
			}
		}
		return patientDataMap;
		
	}
	/**
	 * @return Returns the diseaseTypes.
	 * @throws Exception
	 */
	public static DiseaseTypeLookup[] getDiseaseType() throws Exception {
		if(diseaseTypes == null){
			Criteria crit = new Criteria();
			crit.addOrderByAscending("diseaseTypeId");
			diseaseTypes = (DiseaseTypeLookup[])(executeQuery(DiseaseTypeDim.class,crit).toArray(new DiseaseTypeLookup[1]));
		}
		return diseaseTypes;
	}
	/**
	 * @return Returns the patientDataMap.
	 * @throws Exception
	 * BiospecimenId is the key & PatientDataLookup is the returned object
	 */
	public static Map getDiseaseTypeMap() throws Exception{
		DiseaseTypeLookup[] diseases = getDiseaseType();
		Map patientDataMap = new HashMap();
		if(diseases != null){
			for (int i = 0;i < diseases.length;i++){
				String key = diseases[i].getDiseaseType().toString();
				DiseaseTypeLookup disease = diseases[i];				
				patientDataMap.put(key,disease);				
			}
		}
		return patientDataMap;
		
	}
	/**
	 * @return Returns the expPlatforms.
	 * @throws Exception
	 */
	public static ExpPlatformLookup[] getExpPlatforms() throws Exception {
		if(expPlatforms == null){
			Criteria crit = new Criteria();
			expPlatforms = (ExpPlatformLookup[]) executeQuery(ExpPlatformDim.class,crit).toArray(new ExpPlatformLookup[1]);
		}
		return expPlatforms;
	}
}
