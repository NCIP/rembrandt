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
	
	private Collection reportBeans = new ArrayList();
	private Collection compoundQueries = new ArrayList();

	private String selectedResultSet = null;
	
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();

	private String arrayPlatform = "";
	private String groupNameCompare = null;
	private Collection<LabelValueBean> sampleGroupsList;
	
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
	 * Method reset. Reset all properties to their default values.
	 * 
	 * @param ActionMapping
	 *            mapping used to select this instance.
	 * @param HttpServletRequest
	 *            request The servlet request we are processing.
	 */

	//public void reset(ActionMapping mapping, HttpServletRequest request) {
	//	setViewResultsLookups(request);
	//}

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

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		
		return errors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		arrayPlatform = "";
		GroupRetriever groupRetriever = new GroupRetriever();
		List<LabelValueBean> al = new ArrayList<LabelValueBean>();
//		al.add(new LabelValueBean("all", ""));
		al.addAll(groupRetriever.getClinicalGroupsCollectionNoPath(request.getSession()));
		
		//specifically remove only these values, not to effect the groupRetriever
		LabelValueBean tmp = new LabelValueBean("UNKNOWN", "UNKNOWN");
		al.remove(tmp);
		tmp = new LabelValueBean("ALL", "ALL");
		al.remove(tmp);
		tmp = new LabelValueBean("NON_TUMOR", "NON_TUMOR");
		al.remove(tmp);
		sampleGroupsList = al;
		
	}
	public String getGroupNameCompare() {
		return groupNameCompare;
	}
	public void setGroupNameCompare(String groupNameCompare) {
		this.groupNameCompare = groupNameCompare;
	}
	
	public Collection getSampleGroupsList() {
		return sampleGroupsList;
	}

	public void setSampleGroupsList(Collection sampleGroupsList) {
		this.sampleGroupsList = sampleGroupsList;
	}
}
