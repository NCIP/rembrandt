package gov.nih.nci.rembrandt.download.caarray;
import gov.nih.nci.caarray.services.ServerConnectionException;
import gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager;

import java.io.File;
import java.net.MalformedURLException;

import javax.security.auth.login.LoginException;


public class RembrandtCaArrayFileDownloadManager extends CaArrayFileDownloadManager{
    public static final String SERVER_URL = "rembrandt.caarray.server.url";
    
    public static final String EXPERIMENT_NAME = "rembrandt.caarray.experiment.name";
    
    public static final String USER_NAME = "rembrandt.caarray.user.name";
    
    public static final String PWD = "rembrandt.caarray.user.pwd";
    
    public static final String INPUT_DIR = "rembrandt.caarray.download.input.dir";
    
    public static final String OUTPUT_ZIP_DIR = "rembrandt.caarray.download.output.zip.dir";
    
    public static final String DIR_IN_ZIP = "rembrandt.caarray.download.dirInZip";
    
    public static final String ZIP_FILE_URL = "rembrandt.caarray.download.zip.url";
    
	protected RembrandtCaArrayFileDownloadManager(String caarrayUrl,
			String experimentName, String username, String password,
			String inputDirectory, String outputZipDirectory, String directoryInZip, String zipFileUrl) 
			throws MalformedURLException, LoginException, ServerConnectionException {
		super(caarrayUrl, experimentName, username, password, inputDirectory, outputZipDirectory, directoryInZip,  zipFileUrl); 
	}
	public synchronized static CaArrayFileDownloadManager getInstance() throws Exception
	{
		if (instance == null)
		{
			  //Get the caArray properties from the rembrandt.properties file
			  String caarrayUrl = System.getProperty(SERVER_URL);
			  String experimentName = System.getProperty(EXPERIMENT_NAME);
			  String username = System.getProperty(USER_NAME);
			  String password = System.getProperty(PWD);
			  String inputDirectory = System.getProperty(INPUT_DIR);
			  String outputZipDirectory = System.getProperty(OUTPUT_ZIP_DIR);
			  String directoryInZip = System.getProperty(DIR_IN_ZIP);
			  String zipFileUrl = System.getProperty(ZIP_FILE_URL);
			  File inputDir = new File(inputDirectory);
			  if(!inputDir.isDirectory()){
				  inputDir.mkdir();
			  }
			  File outputZipDir = new File(outputZipDirectory);
			  if(!outputZipDir.isDirectory()){
				  outputZipDir.mkdir();
			  }
			instance = new RembrandtCaArrayFileDownloadManager(caarrayUrl, experimentName, username, password,inputDirectory, outputZipDirectory, directoryInZip,  zipFileUrl); 
		}
		return instance;
	}
}
