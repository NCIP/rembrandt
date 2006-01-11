package gov.nih.nci.rembrandt.web.struts.form;



import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;


 /**
 * This class encapsulates the properties of an caintergator
 * BaseForm object, it is a parent class for all form objects 
 * 
 * 
 * @author BhattarR,ZhangD
 *
 */


public class BaseForm extends ActionForm implements Serializable{
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
		
	// Collections used for Lookup values.  
	private ArrayList<LabelValueBean> diseaseType;
	private ArrayList<LabelValueBean> geneTypeColl;
	
    private String method;
    protected DiseaseOrGradeCriteria diseaseOrGradeCriteria;
    protected SampleCriteria sampleCriteria;
    protected String tumorType;   
	protected String sampleList;
	protected transient FormFile sampleFile;
	protected transient HttpServletRequest thisRequest;	
	protected String sampleGroup;



	public BaseForm(){
			
		// Create Lookups for Gene Expression screens 
		setLookups();

	}
	
	
	private ArrayList addDiseaseTypes( DiseaseTypeLookup diseaseTypeLookup) {
		String diseaseTypeStr = diseaseTypeLookup.getDiseaseType();
		String diseaseDesc = diseaseTypeLookup.getDiseaseDesc();
		diseaseType.add(new LabelValueBean(diseaseTypeStr,diseaseDesc ) );
		return diseaseType;	
		
	}
	private void setDiseaseTypes() {
		try {
			
			DiseaseTypeLookup[] diseaseTypeLookup = LookupManager.getDiseaseType();
			for(int i = 0; i<diseaseTypeLookup.length;i++) {
				addDiseaseTypes(diseaseTypeLookup[i]);
			
			}
		}catch(Exception e) {
			diseaseType = null;
			logger.error("Unable to get disease names  from the LookupManager");
			logger.error(e);
		}
	}
	public void setLookups() {

		diseaseType = new ArrayList<LabelValueBean>();
		geneTypeColl = new ArrayList<LabelValueBean>();
		
		setDiseaseTypes();
		
		//geneTypeColl.add( new LabelValueBean( "All Genes", "allgenes" ) );
		geneTypeColl.add( new LabelValueBean( "Name/Symbol", "genesymbol" ) );
		geneTypeColl.add( new LabelValueBean( "Locus Link Id", "genelocus" ) );
		geneTypeColl.add( new LabelValueBean( "GenBank AccNo.", "genbankno" ) );

	}

    //Getter methods for Gene Expression Lookup
	public ArrayList getDiseaseType() 
	{ return diseaseType; }

	public ArrayList getGeneTypeColl() 
	{ return geneTypeColl; }

	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Collection getChromosomeValue(){

			Collection<LabelValueBean> returnColl = new ArrayList<LabelValueBean>();
			ChromosomeNumberDE[] chromosomes;
			try {
				chromosomes = LookupManager.getChromosomeDEs();
				TreeSet<Integer> chrNum = new TreeSet<Integer>();
				TreeSet<String> chrStr = new TreeSet<String>();

				if(chromosomes != null){
					for(int i =0; i < chromosomes.length; i++){

						try {
							chrNum.add(new Integer(chromosomes[i].getValueObject()));
						}catch(NumberFormatException ex){
							chrStr.add(chromosomes[i].getValueObject());
						}

					}
				}
				returnColl.add(new LabelValueBean("",""));
				for (Iterator iter = chrNum.iterator(); iter.hasNext();) {
					String idxValue = ((Integer)iter.next()).toString(); 
					returnColl.add(new LabelValueBean(idxValue, idxValue));
				}
				for (Iterator iter = chrStr.iterator(); iter.hasNext();) {
					String idxValue = (String) iter.next();
					returnColl.add(new LabelValueBean(idxValue, idxValue));
				}
			} catch (Exception e) {
			    logger.error("Error reading Chromosome values from table:");
			    logger.error(e);
			}
			return returnColl;

		}

	public HashMap getCytoBandForChr(){
		
			Collection chrValues = this.getChromosomeValue();
			HashMap<String,String> cytobandCollections = new HashMap<String,String>();
			
			for (Iterator chrVal = chrValues.iterator(); chrVal.hasNext();) {
				LabelValueBean lbl = (LabelValueBean) chrVal.next();
				String thisChromosome = lbl.getValue();
				if (thisChromosome.length() > 0){
					try {
						CytobandDE[] cytobands = LookupManager.getCytobandDEs(new ChromosomeNumberDE(thisChromosome));
						String cytoString = "";
						for (int cytoIndex = 1; cytoIndex < cytobands.length; cytoIndex++) {
							if (cytoString.length() > 0) cytoString += ",";
							cytoString += '"'+cytobands[cytoIndex].getValueObject()+'"';
						}
						cytobandCollections.put(thisChromosome,cytoString);
					
					}catch(Exception ex){
					    logger.error("Error reading Cytobands from table");
					    logger.error(ex);
					}
				}
			}
			
			return cytobandCollections;
	}
	 /**
     * Returns the tumorType.
     * 
     * @return String
     */
    public String getTumorType() {
        return tumorType;
    }
	/**
     * Set the tumorType.
     * 
     * @param tumorType
     *            The tumorType to set
     */  
	 public void setTumorType(String tumorType) {
		  
	        this.tumorType = tumorType;
	        if(tumorType!=null){ 
	        	
	            diseaseOrGradeCriteria = new DiseaseOrGradeCriteria();           	
	             
		        if (this.tumorType.equalsIgnoreCase("ALL GLIOMA")) {
		            ArrayList allDiseases = this.getDiseaseType();
		            for (Iterator diseaseIter = allDiseases.iterator(); diseaseIter
		                    .hasNext();) {
		                LabelValueBean thisLabelBean = (LabelValueBean) diseaseIter
		                        .next();
		                String thisDiseaseType = thisLabelBean.getValue();               
		              
		                if (thisDiseaseType.equalsIgnoreCase("ASTROCYTOMA")|| thisDiseaseType.equalsIgnoreCase("GBM")
		                    || thisDiseaseType.equalsIgnoreCase("MIXED")||thisDiseaseType.equalsIgnoreCase("OLIGODENDROGLIOMA")
		                    ||thisDiseaseType.equalsIgnoreCase("UNCLASSIFIED") ) {
		                	DiseaseNameDE diseaseDE = new DiseaseNameDE(thisDiseaseType);
		 	                diseaseOrGradeCriteria.setDisease(diseaseDE);
		                }
		            }
		        }
		        
		        else {
		        	DiseaseNameDE diseaseDE = new DiseaseNameDE(this.tumorType);
		            diseaseOrGradeCriteria.setDisease(diseaseDE);
		          
		        }
	          }
	        }
	 public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
			return this.diseaseOrGradeCriteria;
		}
	 
	 /**
		 * Returns the sampleList.
		 * 
		 * @return String
		 */
		public String getSampleList() {

			return sampleList;
		}

		/**
		 * Set the sampleList.
		 * 
		 * @param sampleList
		 *            The sampleList to set
		 */
		public void setSampleList(String sampleList) {
			this.sampleList = sampleList;
			if (thisRequest != null) {

				String thisSampleGroup = this.thisRequest
						.getParameter("sampleGroup");

				if ((thisSampleGroup != null)
						&& thisSampleGroup.equalsIgnoreCase("Specify")
						&& (this.sampleList.length() > 0)) {
					sampleCriteria = new SampleCriteria();

					String[] splitSampleValue = this.sampleList.split("\\x2C");

					for (int i = 0; i < splitSampleValue.length; i++) {
					    SampleIDDE sampleIDDEObj = new SampleIDDE(splitSampleValue[i].trim());						                                       						
					    sampleCriteria.setSampleID(sampleIDDEObj);	
						
					}
				}

			}
		}

		/**
		 * Returns the sampleFile.
		 * 
		 * @return String
		 */
		public FormFile getSampleFile() {
			return sampleFile;
		}
		/**
		 * Set the sampleFile.
		 * 
		 * @param sampleFile
		 *            The sampleFile to set
		 */
		public void setSampleFile(FormFile sampleFile) {
			this.sampleFile = sampleFile;
			if (thisRequest != null) {
				String thisSampleGroup = this.thisRequest
						.getParameter("sampleGroup");
				// retrieve the file name & size
				String fileName = sampleFile.getFileName();
				int fileSize = sampleFile.getFileSize();
				sampleCriteria = new SampleCriteria();

				if ((thisSampleGroup != null)
						&& thisSampleGroup.equalsIgnoreCase("Upload")
						&& (this.sampleFile != null)
						&& (this.sampleFile.getFileName().endsWith(".txt") || this.sampleFile.getFileName().endsWith(".TXT"))
						&& (this.sampleFile.getContentType().equals("text/plain"))) {
					try {
						InputStream stream = sampleFile.getInputStream();
						String inputLine = null;
						BufferedReader inFile = new BufferedReader(
								new InputStreamReader(stream));

						int count = 0;
						while ((inputLine = inFile.readLine()) != null
								&& count < RembrandtConstants.MAX_FILEFORM_COUNT) {
							if (UIFormValidator.isAscii(inputLine)) { // make sure
																		// all data
																		// is ASCII
							    inputLine = inputLine.trim();
							    count++;
							    SampleIDDE sampleIDDEObj = new SampleIDDE(inputLine);						                                       						
							    sampleCriteria.setSampleID(sampleIDDEObj);				    
								
							}
						}// end of while

						inFile.close();
					} catch (IOException ex) {
						logger.error("Errors when uploading sample file:"
								+ ex.getMessage());
					}

				}
			}
		}
		public SampleCriteria getSampleCriteria() {
			return this.sampleCriteria;
		}
		
		/**
		 * Set the sampleGroup.
		 * 
		 * @param sampleGroup
		 *            The sampleGroup to set
		 */
		public void setSampleGroup(String sampleGroup) {
			this.sampleGroup = sampleGroup;
		}
		/**
		 * Returns the geneGroup.
		 * 
		 * @return String
		 */
		public String getSampleGroup() {
			return sampleGroup;
		}
}
