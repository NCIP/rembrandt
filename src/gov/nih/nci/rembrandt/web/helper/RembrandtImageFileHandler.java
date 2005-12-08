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
	private static String SEPERATOR = File.separator;
	
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
	    	String jbossTempDeployDirPath = jbossTempDirPath+SEPERATOR+"deploy"+SEPERATOR;
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
		String relativeSessionTempPath = tempDir+SEPERATOR+"images"+SEPERATOR+userSessionId+SEPERATOR;
		//Path that will be used in the <img /> tag without the file name
		URLPath = "images"+SEPERATOR+userSessionId+SEPERATOR;
		//the actual unique chart name
		chartName = createUniqueChartName(imageTypeExtension);
		/*
		 * Creates the session image temp folder if the
		 * folder does not already exist
		 */
		File dir = new File(relativeSessionTempPath);
		boolean dirCreated = dir.mkdir();
		setSessionTempFolder(relativeSessionTempPath+SEPERATOR+chartName);
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
	private void setSessionTempFolder(String path) {
		this.finalPath = path;
	}
	/**
	 * This will return the final complete local path to the folder
	 * for storing any temporary files for the given session.
	 * 
	 * @return local path to store temp files
	 */
	public String getSessionTempFolder() {
		return this.finalPath;
	}
	
	public String getFinalURLPath() {
		return URLPath+chartName;
	}
	public String getImageTag() {
		String tag = null;
		
		if ((imageWidth < 0)&&(imageHeight < 0)) {
		  //this is for a case where the image width and height are not known.
		  tag = "<img src=\""+getFinalURLPath()+"\"  id=\"rbt_image\" onmouseover=\"\" onmouseout=\"\" name=\"rbt_image\" border=\"0\" />";
		}
		else {
		  tag = "<img src=\""+getFinalURLPath()+"\" width=\""+imageWidth+"\" height=\""+imageHeight+"\" border=\"0\" />";
		}
		
		logger.debug("Returned Image Tag: "+tag);
		return tag;
	}
	/**
	 * Returns an image tag adding the image map file name to the tag
	 * @param mapFileName the name of the image map file name you want added
	 * @return the final image tag
	 */
    public String getImageTag(String mapFileName){
    	String tag = "<img src=\""+getFinalURLPath()+"\" usemap=\"#"+mapFileName + "\"" + " id=\"geneChart\"" + " border=\"0\" />";
    	logger.debug("Returned Image Tag: "+tag);
    	return tag;
    }

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
}
