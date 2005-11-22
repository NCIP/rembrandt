package gov.nih.nci.rembrandt.dto.lookup;

import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dbbean.AllGeneAlias;
import gov.nih.nci.rembrandt.dbbean.CloneDim;
import gov.nih.nci.rembrandt.dbbean.CytobandPosition;
import gov.nih.nci.rembrandt.dbbean.DiseaseTypeDim;
import gov.nih.nci.rembrandt.dbbean.ExpPlatformDim;
import gov.nih.nci.rembrandt.dbbean.GEPatientData;
import gov.nih.nci.rembrandt.dbbean.GeneLlAccSnp;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.queryservice.validation.QueryExecuter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;

/**
 * This class provide a single point for UI related classes to 
 * get lookup data (data that a user can select to mofify a query)
 * It uses that BusinessCacheManager to determine if the data
 * has already been loaded.  If not it executes the query and
 * stores it in the ApplicationCache, else it retrives that data
 * from the Cache and returns it to the UI.
 * 
 * @author SahniH
 */
public class LookupManager{
    private static Logger logger = Logger.getLogger(LookupManager.class);
    private static PatientDataLookup[] patientData;
	private static CytobandLookup[] cytobands;
	private static Lookup[] pathways;
	private static ExpPlatformLookup[] expPlatforms;
	private static DiseaseTypeLookup[] diseaseTypes;
    //private static GeneAliasMap aliasMap = null;
    //private static Set geneSymbols = null;
    
	
	//Lookup Types
	private static final String CHROMOSOME_DE = "chromosomeDE";
	private static final String CYTOBAND_DE = "cytobandDE";
	private static final String CYTOBAND_POSITION = "cytobandPosition";
	private static final String DISEASE_TYPE = "diseaseType";
	private static final String DISEASE_TYPE_MAP = "diseaseTypeMap";
	private static final String EXP_PLATFORMS = "expPlatforms";
	private static final String GENE_SYMBOLS = "geneSymbols";
	private static final String PATIENT_DATA = "patientData";
	private static final String PATIENT_DATA_MAP = "patientDataMap";
	private static final String PATHWAYS = "pathways";
	private static PresentationTierCache presentationTierCache;
	
	
	
	/**
	 * @return Returns the cytobands.
	 */
	public static CytobandLookup[] getCytobandPositions() throws Exception{
		
		Criteria crit = new Criteria();
		crit.addOrderByAscending("chrCytoband");
		cytobands = (CytobandLookup[]) QueryExecuter.executeQuery(CytobandPosition.class, crit,LookupManager.CYTOBAND_POSITION, true).toArray(new CytobandLookup[1]);
		
		return cytobands;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public static ChromosomeNumberDE[] getChromosomeDEs() throws Exception {
		Collection<ChromosomeNumberDE> chromosomeDEs = new ArrayList<ChromosomeNumberDE>();
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
		Collection<CytobandDE> cytobandDEs = new ArrayList<CytobandDE>();
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
			patientData = (PatientDataLookup[])(QueryExecuter.executeQuery(PatientData.class,crit,LookupManager.PATIENT_DATA, true).toArray(new PatientDataLookup[1]));
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
		Map<String,PatientDataLookup> patientDataMap = new HashMap<String,PatientDataLookup>();
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
			diseaseTypes = (DiseaseTypeLookup[])(QueryExecuter.executeQuery(DiseaseTypeDim.class,crit,LookupManager.DISEASE_TYPE,true).toArray(new DiseaseTypeLookup[1]));
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
		Map<String,DiseaseTypeLookup> patientDataMap = new HashMap<String,DiseaseTypeLookup>();
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
			expPlatforms = (ExpPlatformLookup[]) QueryExecuter.executeQuery(ExpPlatformDim.class,crit,LookupManager.EXP_PLATFORMS, true).toArray(new ExpPlatformLookup[1]);
		}
		return expPlatforms;
	}
}
