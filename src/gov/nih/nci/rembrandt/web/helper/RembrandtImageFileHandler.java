package gov.nih.nci.rembrandt.web.helper;

import java.io.File;

import org.apache.log4j.Logger;

import gov.nih.nci.caintegrator.ui.graphing.util.FileDeleter;
import gov.nih.nci.rembrandt.cache.RembrandtContextListener;
/**
 * This class handles image files for the rembrandt application.  Since this 
 * effort should be application specific, it is named RembrandtImageFileHandler.
 * When given a user session, and image extension, and an image size, it will
 * creat all the necessary strings and directories for handling dynamically
 * created images for the application.
 * 
 * @author DBauer
 *
 */
public class RembrandtImageFileHandler {
	private String finalPath = "";
	private String URLPath;
	private String chartName;
	//default values
	private int imageHeight = 400;
	private int imageWidth = 600;
	//The specific temp directory for the application
	private static String tempDir = "";
	private static Logger logger = Logger.getLogger(RembrandtImageFileHandler.class);
	
	public RembrandtImageFileHandler(String userSessionId,String imageTypeExtension, int width, int height) {
		if(tempDir.equals("")) {
			/**
			 * TOTAL HACK! Since I am not sure how to handle dynamic
			 * image content when running in a compressed war I am grabbing
			 * the temp directory from JBoss and walking through the directories
			 * until I either find the expanded Rembrandt war or nothing. This is
			 * always assuming that I am running in JBoss for now (but should be moved
			 * out later).  If the expanded archive is not found, then it just sets
			 * the temp directory to the Context.  
			 */
			String jbossTempDirPath = System.getProperty("jboss.server.temp.dir");
	    	String jbossTempDeployDirPath = jbossTempDirPath+"/deploy/";
	    	File directory = new File(jbossTempDeployDirPath);
			String[] list = directory.list();
			File[] fileList = directory.listFiles();
			for(File file:fileList) {
				if(file.isDirectory()) {
					if(file.getName().contains("rembrandt")&&file.getName().contains(".war")){
						tempDir = file.getPath();
						break;
					}
				}
			}
			
			if(tempDir.equals("")) {
				tempDir = RembrandtContextListener.getContextPath();
			}
		}
		logger.debug("Temp Directory has been set to:"+tempDir);
		if(width!=0&&height!=0) {
			imageWidth = width;
			imageHeight = height;
		}
		String sessionTempPath = tempDir+"/images/"+userSessionId+"/";
		//Path that will be used in the <img /> tag without the file name
		URLPath = "\\images\\"+userSessionId+"\\";
		//the actual unique chart name
		chartName = createUniqueChartName(imageTypeExtension);
		/*
		 * Creates the session image temp folder if the
		 * folder does not already exist
		 */
		File dir = new File(sessionTempPath);
		boolean dirCreated = dir.mkdir();
		setFinalPath(sessionTempPath+"/"+chartName);
		/*
		 * Cleans out the session image temp folder if it did already
		 * exist.  However, because of threading issues it appears to work
		 * intermitently, but it does work.
		 */
		if(!dirCreated) {
			FileDeleter fd = new FileDeleter();
			//This could probably be a * but I am not sure just yet, need to test
			//fd.deleteFiles(getSessionWebAppImagePath(), imageTypeExtension);
		 }
	}

	public String createUniqueChartName(String extension) {
		double time = (double)System.currentTimeMillis();
		double random = (1-Math.random());
		String one = String.valueOf(random*time);
		String finalAnswer = one.substring(10);
		return finalAnswer+"."+extension;
	}
    
    public String createUniqueMapName() {
        double time = (double)System.currentTimeMillis();
        double random = (1-Math.random());
        String one = String.valueOf(random*time);
        String finalAnswer = one.substring(10);
        return finalAnswer;
    }
	/**
	 * @return Returns the uRLPath.
	 */
	public String getURLPath() {
		return URLPath;
	}
	private void setFinalPath(String path) {
		this.finalPath = path;
	}
	public String getFinalPath() {
		return this.finalPath;
	}
	
	public String getFinalURLPath() {
		return URLPath+chartName;
	}
	public String getImageTag() {
		return "<img src=\""+getFinalURLPath()+"\" width="+imageWidth+" height="+imageHeight+" border=0>";
	}
    public String getImageTag(String mapFileName){
        return "<img src=\""+getFinalURLPath()+"\" usemap=\"#"+mapFileName + "\"" + "id=\"geneChart\"" + " border=0>";
    }
}
