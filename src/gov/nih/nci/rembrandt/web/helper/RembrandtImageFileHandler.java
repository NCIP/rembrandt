/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.ui.graphing.util.FileDeleter;
import gov.nih.nci.rembrandt.cache.RembrandtContextListener;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.File;

import org.apache.log4j.Logger;
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
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();

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
		// add path for the temp files
		presentationTierCache.addSessionTempFolderPath(userSessionId, relativeSessionTempPath);
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
		  tag = "<img alt=\"This is a High Order Analysis Image\" src=\""+getFinalURLPath()+"\"  id=\"rbt_image\" onmouseover=\"\" onmouseout=\"\" name=\"rbt_image\" border=\"0\" />";
		}
		else {
		  tag = "<img alt=\"This is a High Order Analysis Image\" src=\""+getFinalURLPath()+"\" width=\""+imageWidth+"\" height=\""+imageHeight+"\" name=\"rbt_image\" id=\"rbt_image\" border=\"0\" />";
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
    	String tag = "<img alt=\"This is a High Order Analysis Image\" src=\""+getFinalURLPath()+"\" usemap=\"#"+mapFileName + "\"" + " id=\"geneChart\"" + " border=\"0\" />";
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
