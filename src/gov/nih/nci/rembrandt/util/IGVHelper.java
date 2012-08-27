/**
 * 
 */
package gov.nih.nci.rembrandt.util;

import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author sahnih
 *
 */


public class IGVHelper {
	private String IGV_TEMPLATE_LOC = "rembrandt.igv.template.location";
	private String IGV_JNLP_URL = "rembrandt.igv.jnlp.url";
	private String IGV_JNPL_TEMPLATE_LOC = "rembrandt.igv.jnpl.template.location";
	private String igvFileName = null;
	private String igvFilePath = null;
	private String igvCopyNumberFileName = null;
	private String igvClinicalFileName = null;
	private String clinicalFile = null;
	private String igvJNPL = null;
	private String genome = null;
	private String igvSessionURL = null;


public IGVHelper(){
	igvFilePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	clinicalFile = System.getProperty("rembrandt.igv.clinical.filename");
	if(igvFilePath != null  && clinicalFile != null){
		igvClinicalFileName = igvFilePath+File.separator+clinicalFile;
	}
}

public IGVHelper(String sessionID, String locus, String appURL) throws IOException {
		igvFilePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
		clinicalFile = System.getProperty("rembrandt.igv.clinical.filename");
		genome = System.getProperty("rembrandt.igv.genome.build");
		if(igvFilePath != null  && clinicalFile != null){
			igvClinicalFileName = igvFilePath+File.separator+clinicalFile;
		}
		String templateLocation = System.getProperty(IGV_TEMPLATE_LOC);
		String igvJnlpUrl = System.getProperty(IGV_JNLP_URL);
		String jnlpTemplateLocation = System.getProperty(IGV_JNPL_TEMPLATE_LOC);
		//InputStream is = Thread.currentThread().getContextClassLoader()
		//.getResourceAsStream(templateLocation);
		 FileInputStream is = new FileInputStream(templateLocation);
		String igvTemplateString =  readInputStreamAsString(is);
		 is = new FileInputStream(jnlpTemplateLocation);
			String igvJnplTemplateString =  readInputStreamAsString(is);
		generateFileName(sessionID);
		igvTemplateString = replaceTemplateTokens(igvTemplateString, locus, appURL,sessionID);
		writeStringtoFile(igvTemplateString , igvFileName);
		//igvURL = replaceUrlTokens( igvJnlpUrl ,  locus, appURL,sessionID);
		igvJNPL = replaceUrlTokens( igvJnplTemplateString ,  locus, appURL,sessionID);
	}
private String readInputStreamAsString(FileInputStream fileInput) throws IOException {
    StringBuilder sb = new StringBuilder();
    int ch;
    try {
    	while( (ch = fileInput.read()) != -1){
    		sb.append((char)ch);
    	}
    	fileInput.close(); 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return sb.toString();
  }

private String replaceUrlTokens(String igvJnlpUrl ,  String locus, String appUrl, String sessionID){
	if(igvJnlpUrl != null){
		igvJnlpUrl = igvJnlpUrl.replace("{locus}", (locus!=null)? locus : "None");
		igvSessionURL = "igvFileDownload.do?method=igvFileDownload"+"&amp;igv="+ sessionID+"-"+getIgvFileName();
		igvSessionURL = appUrl+igvSessionURL;
		igvJnlpUrl = igvJnlpUrl.replace("{session_url}", (igvSessionURL!=null)? igvSessionURL : "None");		
	}
	return igvJnlpUrl;
}
private String replaceTemplateTokens(String igvTemplateString , String locus, String appUrl, String sessionID){
	if(!igvClinicalFileExists()){
		createIGVSampleDataFile();
	}
	if(igvTemplateString != null){
		igvTemplateString = igvTemplateString.replace("{genome}", (genome !=null)? genome  : "None");
		igvTemplateString = igvTemplateString.replace("{locus}", (locus!=null)? locus : "None");
		String cnURL = "igvFileDownload.do?method=igvFileDownload"+"&amp;cn="+ sessionID+"-"+ igvCopyNumberFileName;
		cnURL = appUrl + cnURL;
		String clURL = "igvFileDownload.do?method=igvFileDownload"+"&amp;cl="+ clinicalFile;
		clURL = appUrl + clURL;
		igvTemplateString = igvTemplateString.replace("{rembrandt-cn}", (cnURL!=null)? cnURL : "None");
		igvTemplateString = igvTemplateString.replace("{rembrandt-clinical}", (clURL!=null)? clURL : "None");		
	}
	return igvTemplateString;
}
public void writeStringtoFile(String string, String fileName) throws IOException{
	if(string != null) {
		String xmlFile = igvFilePath+fileName;
		File file = new File(xmlFile);
		FileUtils.writeStringToFile(file, string);
	}
}
/**
 * @return the igvFileName
 */
public String getIgvFileName() {
	return igvFileName;
}
private void generateFileName(String sessionId){
	String tempDir = "";
	igvFilePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	if(igvFilePath != null){
		igvFilePath = igvFilePath + File.separator + sessionId+File.separator;
	}

	/*
	 * Creates the session image temp folder if the
	 * folder does not already exist
	 */
	File dir = new File(igvFilePath);
	if(!dir.exists()){
		boolean dirCreated = dir.mkdir();
	}

	//the actual unique file name
	String uniqueName = createUniqueFileName();
	
	igvFileName = uniqueName+".xml";
	
	igvCopyNumberFileName = uniqueName+"_cn"+".seg";
	
	
}
private String createUniqueFileName() {
	double time = (double)System.currentTimeMillis();
	double random = (1-Math.random());
	String one = String.valueOf(random*time);
	String finalAnswer = one.substring(10);
	return finalAnswer;
}
/**
 * @return the igvFilePath
 */
public String getIgvFilePath() {
	return igvFilePath;
}
/**
 * @return the igvCopyNumberFileName
 */
public String getIgvCopyNumberFileName() {
	return igvCopyNumberFileName;
}
/**
 * @return the igvClinicalFileName
 */
public String getIgvClinicalFileName() {
	return igvClinicalFileName;
}
/**
 * @return the igvURL
 */
//public String getIgvURL() {
//	return igvURL;
//}
/**
 * @return the igvSessionURL
 */
public String getIgvSessionURL() {
	return igvSessionURL ; //"http://www.broadinstitute.org/tumorscape/textReader/IGV/all_tumors_session.xml";
}
/**
 * @return the igvJNPL
 */
public String getIgvJNPL() {
	return igvJNPL;
}
//Create a Sample annotation File for IGV viewer
public void createIGVSampleDataFile(){
    try{
    	PatientDataLookup[] patientData = LookupManager.getPatientData();
    	StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("NAME"+
				"\t SAMPLE_ID"+
				"\t DISEASE_TYPE"+
				"\t WHO_GRADE"+
				"\t AGE_GROUP"+
				"\t GENDER"+
				"\t SURVIVAL_LENGTH_IN_MONTHS"+
				"\t CENSORING_STATUS"+
				"\t INSTITUTION_NAME"+
				"\n" );
        for (int i =0;i<patientData.length;i++) {
        	PatientDataLookup patient = patientData[i];
        	stringBuffer.append(patient.getSpecimenName()+
        			"\t"+patient.getSampleId()+
					"\t"+patient.getDiseaseType()+
					"\t"+patient.getWhoGrade()+
					"\t"+patient.getAgeGroup()+
					"\t"+patient.getGender()+
					"\t"+patient.getSurvivalLengthMonth()+
					"\t"+patient.getCensoringStatus()+
					"\t"+patient.getInstitutionName()+
					"\n");

        }
    	if(igvClinicalFileName != null){
			File file = new File(igvClinicalFileName);
			FileUtils.writeStringToFile(file, stringBuffer.toString());
    	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

public String getIgvClinicalFile() {
	return clinicalFile;
}

public boolean igvClinicalFileExists(){
	if(igvClinicalFileName != null){
		File file = new File(igvClinicalFileName);
		return file.exists();
	}
	return false;
}

}