package gov.nih.nci.caintegrator.application.download.caarray;
import java.net.MalformedURLException;


public class RembrandtCaArrayFileDownloadManager extends CaArrayFileDownloadManager{
    public static final String SERVER_URL = "gov.nci.nih.caintegrator.rembrandt.caarray.server.url";
    
    public static final String EXPERIMENT_NAME = "gov.nci.nih.caintegrator.rembrandt.experiment.name";
    
    public static final String USER_NAME = "gov.nci.nih.caintegrator.rembrandt.user.name";
    
    public static final String PWD = "gov.nci.nih.caintegrator.rembrandt.user.pwd";
    
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
