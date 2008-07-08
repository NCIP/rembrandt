package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class DownloadForm extends ActionForm{
	private static Logger logger = Logger.getLogger(RefineQueryForm.class);

	private ActionErrors errors = new ActionErrors();;
	
	
	
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	//caArray Download Fields
	private String arrayPlatform = "";
	private String groupNameCompare = "";
	private String fileType = "";
	
	//BRB download fields
	
	
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
	
}
