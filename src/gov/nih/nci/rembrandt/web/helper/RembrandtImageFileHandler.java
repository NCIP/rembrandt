package gov.nih.nci.rembrandt.web.helper;

import java.io.File;

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
	private String URLPath;
	private String chartName;
	//default values
	private int imageHeight = 400;
	private int imageWidth = 600;
	//	Path to webapp context with in the server
	private static String contextPath = RembrandtContextListener.getContextPath();
	
	public RembrandtImageFileHandler(String userSessionId,String imageTypeExtension, int width, int height) {
		if(width!=0&&height!=0) {
			imageWidth = width;
			imageHeight = height;
		}
		//Path that will be used in the <img /> tag without the file name
		URLPath = "\\images\\"+userSessionId+"\\";
		//the actual unique chart name
		chartName = createUniqueChartName(imageTypeExtension);
		/*
		 * Creates the session image temp folder if the
		 * folder does not already exist
		 */
		File dir = new File(getSessionWebAppImagePath());
		boolean dirCreated = dir.mkdir();
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

	private String createUniqueChartName(String extension) {
		double time = (double)System.currentTimeMillis();
		double random = (1-Math.random());
		String one = String.valueOf(random*time);
		String finalAnswer = one.substring(10);
		return finalAnswer+"."+extension;
	}
	/**
	 * @return Returns the uRLPath.
	 */
	public String getURLPath() {
		return URLPath;
	}
	public String getSessionWebAppImagePath() {
		return contextPath+URLPath;
	}
	public String getFinalPath() {
		// TODO Auto-generated method stub
		return getSessionWebAppImagePath() + chartName;
	}
	public String getFinalURLPath() {
		return URLPath+chartName;
	}
	public String getImageTag() {
		return "<img src=\""+getFinalURLPath()+"\" width="+imageWidth+" height="+imageHeight+" border=0>";
	}
}
