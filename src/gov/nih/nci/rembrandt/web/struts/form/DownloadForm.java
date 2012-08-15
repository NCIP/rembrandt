package gov.nih.nci.rembrandt.web.struts.form;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DownloadForm extends ActionForm{
	private static Logger logger = Logger.getLogger(RefineQueryForm.class);

	private ActionErrors errors = new ActionErrors();;
	
	
	
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

   

	/**
	 * @return Returns the errors.
	 */
	public ActionErrors getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            The errors to set.
	 */
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public void addActionError(String string, ActionError error) {
		this.errors.add(string, error);
	}


	public void reset(ActionMapping mapping, HttpServletRequest request) {
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
