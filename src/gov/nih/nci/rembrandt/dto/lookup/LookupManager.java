package gov.nih.nci.rembrandt.dto.lookup;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dbbean.ArrayGenoAbnFact;
import gov.nih.nci.rembrandt.dbbean.BiospecimenDim;
import gov.nih.nci.rembrandt.dbbean.CytobandPosition;
import gov.nih.nci.rembrandt.dbbean.DataStatistics;
import gov.nih.nci.rembrandt.dbbean.DifferentialExpressionSfact;
import gov.nih.nci.rembrandt.dbbean.DiseaseTypeDim;
import gov.nih.nci.rembrandt.dbbean.DownloadFile;
import gov.nih.nci.rembrandt.dbbean.ExpPlatformDim;
import gov.nih.nci.rembrandt.dbbean.GESpecimen;
import gov.nih.nci.rembrandt.dbbean.GenePathway;
import gov.nih.nci.rembrandt.dbbean.InstitutionLookup;
import gov.nih.nci.rembrandt.dbbean.Pathway;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dbbean.SNPSpecimen;
import gov.nih.nci.rembrandt.queryservice.validation.QueryExecuter;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
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


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class LookupManager{
    private static Logger logger = Logger.getLogger(LookupManager.class);
    private static PatientDataLookup[] patientData;
	private static CytobandLookup[] cytobands;
	private static ExpPlatformLookup[] expPlatforms;
	private static DiseaseTypeLookup[] diseaseTypes;
	private static Map<String,PathwayLookup>  pathwayMap;
	private static List<InstitutionLookup> insitutionLookup;
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
	private static final String DOWNLOAD_FILES = "downloadFiles";
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
	@SuppressWarnings({"deprecation","unchecked"})
	public static Map getPathwayMap() {
		if(pathwayMap == null){
			pathwayMap = new HashMap<String,PathwayLookup>();
			Criteria crit = new Criteria();
			crit.addColumnEqualTo("DATA_SOURCE","CGAP_KEGG");
			crit.addNotNull("PATHWAY_NAME");

			ReportQueryByCriteria qbc = new ReportQueryByCriteria(GenePathway.class, crit, false);
			qbc.addOrderByAscending("pathwayDesc");
			String[] fields = {GenePathway.PATHWAY_NAME, GenePathway.GENE_SYMBOL, 
					GenePathway.DATA_SOURCE, GenePathway.PATHWAY_DESC};
			qbc.setAttributes(fields);
			try {
				Collection collection = QueryExecuter.executeReportQuery(qbc);

				for(Iterator it = collection.iterator(); it.hasNext();){
					
					Object[] obj = (Object[])it.next();
					GenePathway genePathway = getGenePathways(obj);
					if(!pathwayMap.containsKey(genePathway.getPathwayName())){
					//create a new pathway
					PathwayLookup pathwayLookup = new Pathway();
					pathwayLookup.setDataSource(genePathway.getDataSource());
					pathwayLookup.setPathwayName(genePathway.getPathwayName());
					pathwayLookup.setPathwayDesc(genePathway.getPathwayDesc());
					List geneList = new ArrayList();
					geneList.add(genePathway.getGeneSymbol());
					pathwayLookup.setGeneSymbols(geneList);
					pathwayMap.put(genePathway.getPathwayName(), pathwayLookup);
					}
					else{ //add the gene symbol
						PathwayLookup pathwayLookup = pathwayMap.get(genePathway.getPathwayName());
						List geneList = pathwayLookup.getGeneSymbols();
						geneList.add(genePathway.getGeneSymbol());						
					}
				}				
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return pathwayMap;
	}
	
	/**
	 * @return Returns the pathways.
	 */
	@SuppressWarnings({"deprecation","unchecked"})
	public static List getDownloadBRBFileList() {
		List<DownloadFileLookup> downloadFileList
			= new ArrayList<DownloadFileLookup>();
		Criteria crit = new Criteria();
		crit.addColumnEqualTo("FILE_TYPE","BRB");
		String filePath = System.getProperty("gov.nih.nci.rembrandt.brb_filepath");
		ReportQueryByCriteria qbc = new ReportQueryByCriteria(DownloadFile.class, crit, false);
		qbc.addOrderByAscending("fileId");
		String[] fields = {DownloadFile.FILE_ID, DownloadFile.FILE_NAME, 
				DownloadFile.FILE_TYPE, DownloadFile.ACCESS_CODE};
		qbc.setAttributes(fields);
		try {
			Collection collection = QueryExecuter.executeReportQuery(qbc);

			for(Iterator it = collection.iterator(); it.hasNext();){
				Object[] obj = (Object[])it.next();
				DownloadFile downloadFile = new DownloadFile();
				downloadFile.setFileId(new Long(((BigDecimal)obj[0]).longValue()));
				downloadFile.setFileName((String)obj[1]);
				downloadFile.setFileType((String)obj[2]);
				downloadFile.setAccessCode(new Long(((BigDecimal)obj[3]).longValue()));

				downloadFile.setFilePath(filePath);
				downloadFileList.add(downloadFile);
			}				
		} catch (Exception e) {
			logger.error(e);
		}
		return downloadFileList;
	}
	
	/**
	 * @return Returns the patientData.
	 * @throws Exception
	 */
	public static PatientDataLookup[] getPatientData() throws Exception {
		if(patientData == null){
			Criteria crit = new Criteria();
			patientData = (PatientDataLookup[])(QueryExecuter.executeQuery(PatientData.class,crit,QueryExecuter.NO_CACHE, true).toArray(new PatientDataLookup[1]));
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
				if(patients[i].getSpecimenName()!= null){
					String key = patients[i].getSpecimenName().toString();
					PatientDataLookup patient = patients[i];				
					patientDataMap.put(key,patient);	
				}			
			}
		}
		return patientDataMap;
		
	}
	/**
	 * @return Returns the patientDataMap.
	 * @throws Exception
	 * BiospecimenId is the key & PatientDataLookup is the returned object
	 */
	public static Map getPatientDataMapForKM() throws Exception{
		PatientDataLookup[] patients = getPatientData();
		Map<String,PatientDataLookup> patientDataMap = new HashMap<String,PatientDataLookup>();
		if(patients != null){
			for (int i = 0;i < patients.length;i++){
				String key = patients[i].getSampleId().toString();
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
			//crit.addOrderByAscending("diseaseTypeId");
			crit.addOrderByAscending("diseaseType");
			DiseaseTypeLookup[] diseases = (DiseaseTypeLookup[])(QueryExecuter.executeQuery(DiseaseTypeDim.class,crit,QueryExecuter.NO_CACHE,true).toArray(new DiseaseTypeLookup[1]));
			List<DiseaseTypeLookup> diseaseList = new ArrayList<DiseaseTypeLookup>(Arrays.asList(diseases));
			for(DiseaseTypeLookup diseaseTypeLookup: diseaseList){
				if(diseaseTypeLookup.getDiseaseType().equals(RembrandtConstants.UNCLASSIFIED)){
					diseaseList.remove(diseaseTypeLookup);
				}
			}
			diseaseTypes = diseaseList.toArray(new DiseaseTypeLookup[diseaseList.size()]);
		}
		
		return diseaseTypes;
	}
	
	
	
	/**
	 * @return Returns the lookUpClinicalQueryTermValues List.
	 * @throws Exception
	 */
	public static List lookUpClinicalQueryTermValues(Class bean,String fieldToSelect) throws Exception {
		Criteria crit = new Criteria();	
		Collection col = QueryExecuter.lookUpClinicalQueryTermValues(bean,crit,fieldToSelect, true);
		
		List list = new ArrayList();            
		if(col != null){
			Iterator e = col.iterator();   
			while(e.hasNext()){
				Object ojb = e.next();
				if(ojb instanceof String) {
			       	  String termToLookup = (String)ojb;
			     	  if(termToLookup != null && !termToLookup.equals("")){
			   		  list.add(new String(termToLookup));
			   	   }
				  }
				else if(ojb instanceof BigDecimal) {
					 Long termToLookup = new Long(((BigDecimal)ojb).longValue());
					 if(termToLookup != null && !termToLookup.equals("")){
				   		  list.add(termToLookup.toString());
				  }
			    }
			}
		}
		return list;
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
	
	private static GenePathway getGenePathways(Object[] obj)
	{
		GenePathway way = new GenePathway();
		way.setPathwayName((String)obj[0]);
		way.setGeneSymbol((String)obj[1]);
		way.setDataSource((String)obj[2]);
		way.setPathwayDesc((String)obj[3]);
		
		return way;
	}
	
	public static List getPathwayGeneSymbols(String pathwayName)
	{
		if (pathwayMap == null){
			pathwayMap = getPathwayMap();
		}
		if (pathwayName == null || pathwayName.length() == 0)
			return new ArrayList<String>();
		else {
			return pathwayMap.get(pathwayName).getGeneSymbols();
		}
	}
	public static List<SampleIDDE> getSampleIDDEs(String diseaseType,Collection<InstitutionDE> insitutions){
		List<SampleIDDE> sampleIDList = new ArrayList<SampleIDDE>();
        PatientDataLookup[] patientDataLookups;
			try {
				if(diseaseType != null && insitutions != null){
		        patientDataLookups = LookupManager.getPatientData();
		        	for(PatientDataLookup  patientDataLookup:patientDataLookups){
		        		if(patientDataLookup.getDiseaseType() != null &&
		        				diseaseType.compareToIgnoreCase(patientDataLookup.getDiseaseType())== 0){
		        			for(InstitutionDE insitution :insitutions){
		        				if(insitution.getValueObject().equals(patientDataLookup.getInstitutionId())){
		        					sampleIDList.add(new SampleIDDE(patientDataLookup.getSampleId()));
		        				}
		        			}
		        		}
		        	}
		        				
		        	}
			} catch (Exception e1) {
				logger.error(e1);
				return null;
	
			}
		return sampleIDList;
		}
	public static List<String> getSpecimanNames(String diseaseType,Collection<InstitutionDE> insitutions){
		List<String> specimenNameList = new ArrayList<String>();
        PatientDataLookup[] patientDataLookups;
			try {
				if(diseaseType != null && insitutions != null){
		        patientDataLookups = LookupManager.getPatientData();
		        	for(PatientDataLookup  patientDataLookup:patientDataLookups){
		        		if(patientDataLookup.getDiseaseType() != null &&
		        				diseaseType.compareToIgnoreCase(patientDataLookup.getDiseaseType())== 0){
		        			for(InstitutionDE insitution :insitutions){
		        				if(patientDataLookup.getInstitutionId() != null 
		        						&& patientDataLookup.getSpecimenName()!= null 
		        						&& insitution.getValueObject().equals(patientDataLookup.getInstitutionId())){
		        					specimenNameList.add(patientDataLookup.getSpecimenName());
		        				}
		        			}
		        		}
		        	}
		        				
		        	}
			} catch (Exception e1) {
				logger.error(e1);
				return null;
	
			}
		return specimenNameList;
		}
	/**
	 * @return Returns the InsitutionLookup[].
	 * @throws Exception
	 * 
	 */
	public static List<InstitutionLookup> getInsitutionLookup() throws Exception{
			if(insitutionLookup == null){
				Criteria crit = new Criteria();
				insitutionLookup = (List<InstitutionLookup>)(QueryExecuter.executeQuery(InstitutionLookup.class,crit,QueryExecuter.NO_CACHE, true));
			}
			return insitutionLookup;
		}
	/**
	 * @param SpecimenNames
	 * @return SpecimenIDs
	 * @return
	 */
	public static List<String> getSampleIDs(Collection<String> specimenNames){
		List<String> sampleIDList = new ArrayList<String>();
			try {
				if(specimenNames != null && specimenNames.size() > 0){
					Criteria crit = new Criteria();
					crit.addIn(BiospecimenDim.SPECIMEN_NAME,specimenNames);
					Collection col = QueryExecuter.lookUpClinicalQueryTermValues(BiospecimenDim.class,crit,BiospecimenDim.SAMPLE_ID, true);
					if(col != null){
						for(Object ojb:col){
							if(ojb instanceof String) {
						       	  String termToLookup = (String)ojb;
						     	  if(termToLookup != null && !termToLookup.equals("")){
						     		 sampleIDList.add(termToLookup);
						     	  }
							  }
							}
						}
					}
			} catch (Exception e1) {
				logger.error(e1);
				return null;
	
			}
		return sampleIDList;
		}
	/**
	 * @param accessInstitutions 
	 * @param specimenIds
	 * @return SpecimenNames
	 */
	public static List<String> getSpecimenNames(Collection<String> sampleIDS){
		Collection<InstitutionDE> accessInstitutions = Collections.emptyList();
		return getSpecimenNames( sampleIDS, accessInstitutions);
	}

	public static List<String> getSpecimenNames(Collection<String> sampleIDS, Collection<InstitutionDE> accessInstitutions){
		List<String> specimenNamesList = new ArrayList<String>();
		List<String> accessIds = new ArrayList<String>();
		for(InstitutionDE insitution :accessInstitutions){
			if(insitution.getValueObject()!= null){
				accessIds.add(insitution.getValueObject().toString());
			}
		}
			try {
				if(sampleIDS != null && sampleIDS.size() > 0){
					Criteria crit = new Criteria();
					crit.addIn(BiospecimenDim.SAMPLE_ID,sampleIDS);
					if(accessIds.size()>0){
						crit.addIn("INSTITUTION_ID",accessIds);
					}
					crit.addNotEqualTo("SPECIMEN_TYPE", "CELL_LINE");
					Collection col = QueryExecuter.lookUpClinicalQueryTermValues(BiospecimenDim.class,crit,"SPECIMEN_NAME", true);
					if(col != null){
						for(Object ojb:col){
							if(ojb instanceof String) {
						       	  String termToLookup = (String)ojb;
						     	  if(termToLookup != null && !termToLookup.equals("")){
						     		 specimenNamesList.add(termToLookup);
						     	  }
							  }
							}
						}
					}
			} catch (Exception e1) {
				logger.error(e1);
				return null;
	
			}
		return specimenNamesList;
		}
	public static Integer getClinicalStudyParticipantCount(Set<Long> institutionIds) throws Exception{
		//select count(PATIENT_DID) from PATIENT_DATA where institution_id = 8;
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
		Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"PT_CLIN", false);
		if(col != null){
			for(Object obj:col){
				if(obj instanceof BigDecimal) {
					BigDecimal bigDec = (BigDecimal)obj;
					count = count + new Integer(bigDec.intValue());
				  }
				}
			}
		return count;
	}
	public static Integer getClinicalSpecimenCount(Set<Long> institutionIds) throws Exception{
		//		select count(BIOSPECIMEN_ID) from PATIENT_DATA 	where institution_id = 8;
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
			Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"SPECI_CLIN", false);
			if(col != null){
				for(Object obj:col){
					if(obj instanceof BigDecimal) {
						BigDecimal bigDec = (BigDecimal)obj;
						count = count + new Integer(bigDec.intValue());
					  }
					}
				}
			return count;
	}
	public static Integer getGESpecimenCount(Set<Long> institutionIds) throws Exception{
		//		select count(unique BIOSPECIMEN_ID) from DIFFERENTIAL_EXPRESSION_SFACT  where institution_id = 8;
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
			Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"SPECI_GE", true);
			if(col != null){
				for(Object obj:col){
					if(obj instanceof BigDecimal) {
						BigDecimal bigDec = (BigDecimal)obj;
						count = count + new Integer(bigDec.intValue());
					  }
					}
				}
			return count;
	}
	public static Integer getGESampleCount(Set<Long> institutionIds) throws Exception{
		//		select count(unique sample_ID) from DIFFERENTIAL_EXPRESSION_SFACT 	where institution_id = 8;
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
			Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"PT_GE", false);
			if(col != null ){
				for(Object obj:col){
					if(obj instanceof BigDecimal) {
						BigDecimal bigDec = (BigDecimal)obj;
						count = count + new Integer(bigDec.intValue());
					  }
					}
				}
			return count;
	}
	public static Integer getCNSpecimenCount(Set<Long> institutionIds) throws Exception{
		//		select count(unique BIOSPECIMEN_ID)	from ARRAY_GENO_ABN_FACT where institution_id = 8;
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
			Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"SPECI_CP", true);
			if(col != null  ){
				for(Object obj:col){
					if(obj instanceof BigDecimal) {
						BigDecimal bigDec = (BigDecimal)obj;
						count = count + new Integer(bigDec.intValue());
					  }
					}
				}
			return count;
	}
	public static Integer getCNSampleCount(Set<Long> institutionIds) throws Exception{
		Integer count = 0;
		Criteria crit = new Criteria();
		crit.addIn("INSTITUTION_ID",institutionIds);
		
		Collection col = QueryExecuter.lookUpClinicalQueryTermValues(DataStatistics.class,crit,"PT_CP", true);
		if(col != null  ){
			for(Object obj:col){
				if(obj instanceof BigDecimal) {
					BigDecimal bigDec = (BigDecimal)obj;
					count = count + new Integer(bigDec.intValue());
				  }
				}
			}
		return count;
	}
}
