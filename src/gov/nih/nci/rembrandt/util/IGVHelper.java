/**
 * 
 */
package gov.nih.nci.rembrandt.util;


import java.io.BufferedReader;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
	private String igvGctFileName = null;
	private String clinicalFile = null;
	private String igvJNPL = null;
	private String genome = null;
	private String igvSessionURL = null;
	private String cnURL = null;
	private String rembrandtUser = null;


public IGVHelper(){
	igvFilePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	clinicalFile = System.getProperty("rembrandt.igv.clinical.filename");
	if(igvFilePath != null  && clinicalFile != null){
		igvClinicalFileName = igvFilePath+File.separator+clinicalFile;
	}
}

public IGVHelper(String sessionID, String genome, String locus, String appURL, String cnUrl, String rembrandtUser) throws MalformedURLException, IOException {
	super();
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
		this.cnURL = cnUrl;
		this.rembrandtUser = rembrandtUser;
		 FileInputStream is = new FileInputStream(templateLocation);
		String igvTemplateString =  readInputStreamAsString(is);
		 is = new FileInputStream(jnlpTemplateLocation);
			String igvJnplTemplateString =  readInputStreamAsString(is);
		generateFileName(sessionID);
		igvTemplateString = replaceTemplateTokens(igvTemplateString, genome, locus, appURL,sessionID);
		writeStringtoFile(igvTemplateString , igvFileName);
		//igvURL = replaceUrlTokens( igvJnlpUrl ,  locus, appURL,sessionID);
		igvJNPL = replaceUrlTokens( igvJnplTemplateString ,  locus, appURL,sessionID);
	}
	
	public IGVHelper(String sessionID, String genome, String locus, String appURL) throws IOException {
		this(sessionID, genome, locus, appURL, "", "");
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
private String replaceTemplateTokens(String igvTemplateString , String genome, String locus, String appUrl, String sessionID)  throws MalformedURLException, IOException{
	if(!igvClinicalFileExists()){
		createIGVSampleDataFile();
	}
	if(igvTemplateString != null){
		igvTemplateString = igvTemplateString.replace("{genome}", (genome !=null)? genome  : "None");
		igvTemplateString = igvTemplateString.replace("{locus}", (locus!=null)? locus : "None");
		if ( cnURL.equals("")) {
			cnURL = "igvFileDownload.do?method=igvFileDownload"+"&amp;cn="+ sessionID+"-"+ igvCopyNumberFileName;
			cnURL = appUrl + cnURL;
		}
		else {
			// IGV VIEWER
	        downloadGctFile();
			cnURL = igvFilePath + igvGctFileName;
		}
		String clURL = "igvFileDownload.do?method=igvFileDownload"+"&amp;cl="+ clinicalFile;
		clURL = appUrl + clURL;
		igvTemplateString = igvTemplateString.replace("{rembrandt-cn}", (cnURL!=null)? cnURL : "None");
		igvTemplateString = igvTemplateString.replace("{rembrandt-clinical}", (clURL!=null)? clURL : "None");		
	}
	return igvTemplateString;
}

private void downloadGctFile() throws MalformedURLException, IOException  {
	URL gctFile;
	InputStream is = null;
	FileOutputStream fos = null;
	try {
		gctFile = new URL(cnURL);
		
		URLConnection conn = gctFile.openConnection();
		String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
		
		String loginPassword = rembrandtUser + ":" + password; 
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes()); 
		conn.setRequestProperty ("Authorization", "Basic " + encoded); 
		
		is = conn.getInputStream();

		File file = new File(igvFilePath, igvGctFileName);
		fos = new FileOutputStream(file);
	    byte[] buf = new byte[100000];
	    int j;
	    int i = 0;

	    while ((j = is.read(buf, 0, buf.length)) != -1) {
	    	fos.write(buf, 0, j);
	    }
	}
	finally {
	if (is != null) {
	    try {
	        is.close();
	    } catch (IOException e) {

	    }
	}
	if (fos != null) {
	    try {
	        fos.close();
	    } catch (IOException e) {

	    }
	}
      }
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
	
	
	igvGctFileName = uniqueName + ".gct";
	
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
/*        	stringBuffer.append(patient.getSpecimenName()+
        			"\t"+patient.getSampleId()+
					"\t"+patient.getDiseaseType()+
					"\t"+patient.getWhoGrade()+
					"\t"+patient.getAgeGroup()+
					"\t"+patient.getGender()+
					"\t"+patient.getSurvivalLengthMonth()+
					"\t"+patient.getCensoringStatus()+
					"\t"+patient.getInstitutionName()+
					"\n");  */

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

public String getIgvGctFileName() {
	return igvGctFileName;
}


}