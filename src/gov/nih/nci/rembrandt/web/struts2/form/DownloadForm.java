/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import org.apache.log4j.Logger;

public class DownloadForm {
	
	private static Logger logger = Logger.getLogger(RefineQueryForm.class);
	
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	//caArray Download Fields
	private String arrayPlatform = "";
	private String groupNameCompare = "";
	private String fileType = "";
	private String downloadFile = "";
	private String idfile = "";
	private List fileId = new ArrayList();
	//BRB download fields
	
	
	/**
	 * @return the downloadFile
	 */
	public String getDownloadFile() {
		return downloadFile;
	}
	/**
	 * @param downloadFile the downloadFile to set
	 */
	public void setDownloadFile(String downloadFile) {
		this.downloadFile = downloadFile;
	}
	/**
	 * @return the idfile
	 */
	public String getIdfile() {
		return idfile;
	}
	/**
	 * @param idfile the idfile to set
	 */
	public void setIdfile(String idfile) {
		this.idfile = idfile;
	}
	public DownloadForm() {
		super();
	}
	/**
     * @return Returns the arrayPlatform.
     */
    public String getArrayPlatform() {
        return arrayPlatform;
    }



    /**
     * @param arrayPlatform The arrayPlatform to set.
     */
    public void setArrayPlatform(String arrayPlatform) {
        this.arrayPlatform = arrayPlatform;
    }


	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		DownloadForm.logger = logger;
	}
	public RembrandtPresentationTierCache getPresentationTierCache() {
		return presentationTierCache;
	}
	public void setPresentationTierCache(
			RembrandtPresentationTierCache presentationTierCache) {
		this.presentationTierCache = presentationTierCache;
	}
	public String getGroupNameCompare() {
		return groupNameCompare;
	}
	public void setGroupNameCompare(String groupNameCompare) {
		this.groupNameCompare = groupNameCompare;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the fileId
	 */
	public List getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(List fileId) {
		this.fileId = fileId;
	}

	
}
