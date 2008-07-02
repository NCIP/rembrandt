package gov.nih.nci.rembrandt.download.caarray;
import gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager;

import java.net.MalformedURLException;


public class RembrandtCaArrayFileDownloadManager extends CaArrayFileDownloadManager{
    public static final String SERVER_URL = "rembrandt.caarray.server.url";
    
    public static final String EXPERIMENT_NAME = "rembrandt.caarray.experiment.name";
    
    public static final String USER_NAME = "rembrandt.caarray.user.name";
    
    public static final String PWD = "rembrandt.caarray.user.pwd";
    
	protected RembrandtCaArrayFileDownloadManager(String caarrayUrl,
			String experimentName, String username, String password)
			throws MalformedURLException {
		super(caarrayUrl, experimentName, username, password);
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
			instance = new RembrandtCaArrayFileDownloadManager(caarrayUrl, experimentName, username, password);
		}
		return instance;
	}
}
