package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class ViewResultsForm extends BaseForm {
	private Logger logger = Logger.getLogger(RefineQueryForm.class);

	private ActionErrors errors = new ActionErrors();;
	
	private Collection reportBeans = new ArrayList();

	private String selectedResultSet = null;
	
	private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
	
	 
	public ViewResultsForm() {
		super();
	}
	
	
	/**
	 * Method reset. Reset all properties to their default values.
	 * 
	 * @param ActionMapping
	 *            mapping used to select this instance.
	 * @param HttpServletRequest
	 *            request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		setViewResultsLookups(request);
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

	/**
	 * @param resultSets
	 *            The resultSets to set.
	 */
	public void setReportBeans(Collection reportBeans) {
		this.reportBeans = reportBeans;
	}
	
	/**
	 * @return Returns the resultSets.
	 */
	public Collection getReportBeans() {
		return reportBeans;
	}

	/**
	 * @return Returns the selectedResultSet.
	 */
	public String getSelectedResultSet() {
		return selectedResultSet;
	}

	
	/**
	 * @param selectedResultSet
	 *            The selectedResultSet to set.
	 */
	public void setSelectedResultSet(String selectedResultSet) {
		this.selectedResultSet = selectedResultSet;
	}

	/**
	 * Method validate
	 * 
	 * @param ActionMapping
	 *            mapping
	 * @param HttpServletRequest
	 *            request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		
		return errors;
	}
	/**
	 * This method is essential in maintaining the drop down menus of the 
	 * refineQueryPage. It currently grabs the SessionQueryBag from the 
	 * sessionCache and retrieves a map to the current list of all gene queries
	 * and non all gene queries, converting them to Collections for use by the
	 * refine_tile.jsp.  It also grabs and stores the current list of all the
	 * result sets that are stored in the cache.
	 * 
	 * @param request
	 */
	private void setViewResultsLookups(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		setReportBeans(cacheManager.getAllReportBeans(sessionId));
		
		//setResultsets();//grabs report beans that meet crit.
		//setQueryTextList(); // run through loop for each resultset and set text
		//setCompoundView(); // loop through for each resultset and set view
		
		
	}
}