package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.ui.bean.SelectedQueryBean;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class RefineQueryForm extends BaseForm implements Factory {
	private Logger logger = Logger.getLogger(RefineQueryForm.class);

	private ActionErrors errors = new ActionErrors();;

	private String queryText;

	private String compoundView;

	private String resultSetName;

	private String runFlag = "no";

	private String method;
	
	// Collections used for Lookup values.
	private List nonAllGenesQueries = new ArrayList();
	
	private List allGenesQueries = new ArrayList();

	private List compoundViewColl = new ArrayList();

	private List selectedQueries = LazyList.decorate(new ArrayList(), this);
	
	private String allGeneQuery = "";
	
	private Collection resultSets = new ArrayList();

	private String selectedResultSet = null;
	
	private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
	
	private boolean isAllGenesQuery = false ;
 
	public RefineQueryForm() {
		super();
	}
	
	public List getAllGenesQueries() {
		return allGenesQueries;
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
		queryText = "";
		compoundView = "";
		resultSetName = "";
		setRefineQueryLookups(request);
		compoundViewColl = new ArrayList();
		compoundViewColl.add(new LabelValueBean(" ", " "));
	}

	/**
	 * Returns the queryName1.
	 * 
	 * @return String
	 */
	public String getQueryText() {

		return queryText;
	}

	/**
	 * Set the queryName1.
	 * 
	 * @param queryName1
	 *            The queryName to set
	 */
	public void setQueryText(String queryText) {
		this.queryText = queryText;

	}

	/**
	 * Returns the compoundView.
	 * 
	 * @return String
	 */
	public String getCompoundView() {

		return compoundView;
	}

	/**
	 * Set the compoundView.
	 * 
	 * @param compoundView
	 *            The compoundView to set
	 */
	public void setCompoundView(String compoundView) {
		this.compoundView = compoundView;

	}

	/**
	 * Returns the resultsetName.
	 * 
	 * @return String
	 */
	public String getResultSetName() {

		return resultSetName;
	}

	/**
	 * Set the resultsetName.
	 * 
	 * @param resultsetName
	 *            The resultsetName to set
	 */
	public void setResultSetName(String resultsetName) {
		this.resultSetName = resultsetName;

	}

	/**
	 * Set the Run Report button flag.
	 * 
	 * @param runFlag
	 *            The runFlag to set
	 */
	public void setRunFlag(String runValue) {
		this.runFlag = runValue;

	}

	public String getRunFlag() {
		return runFlag;

	}

	public String getMethod() {

		return "";
	}

	public void setMethod(String method) {

		this.method = method;
	}

	public List getNonAllGeneQueries() {
		return nonAllGenesQueries;
	}

	public List getCompoundViewColl() {
		return compoundViewColl;
	}

	public void setCompoundViewColl(List viewColl) {
		this.compoundViewColl = viewColl;
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
	 * @return Returns the selectedQueries.
	 */
	public List getSelectedQueries() {
		if (selectedQueries.isEmpty()) {
			SelectedQueryBean newQuery = new SelectedQueryBean();
			newQuery.setQueryName("");
			selectedQueries.add(newQuery);
		}
		return selectedQueries;
	}

	/**
	 * @param selectedQueries
	 *            The selectedQueries to set.
	 */
	public void setSelectedQueries(ArrayList selectedQueries) {
		this.selectedQueries = selectedQueries;
	}

	public SelectedQueryBean getSelectedQuery(int index) {
		return (SelectedQueryBean) this.getSelectedQueries().get(index);
	}

	public void addSelectedQuery() {
		List selectedQueries = this.getSelectedQueries();
		SelectedQueryBean newQuery = new SelectedQueryBean();
		newQuery.setQueryName("");
		selectedQueries.add(newQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections.Factory#create()
	 */
	public Object create() {
		SelectedQueryBean newQuery = new SelectedQueryBean();
		newQuery.setQueryName("");
		return newQuery;
	}

	/**
	 * @return Returns the resultSets.
	 */
	public Collection getResultSets() {
		return resultSets;
	}

	/**
	 * @return Returns the selectedResultSet.
	 */
	public String getSelectedResultSet() {
		return selectedResultSet;
	}

	/**
	 * @param resultSets
	 *            The resultSets to set.
	 */
	public void setResultSets(Collection resultSets) {
		this.resultSets = resultSets;
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

		ActionErrors errors = new ActionErrors();
		String paramValue = request.getParameter(mapping.getParameter());

		// Validate Query validation
		if (paramValue != null) {

			// Run Report Validations
			if (paramValue.equalsIgnoreCase("displayresult")) {
				if (this.getCompoundView().trim().length() < 1) {
					if (this.getQueryText().trim().length() >= 1) {
						this.setQueryText("");
						errors
								.add(
										"compoundView",
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.form.refinequery.no.view"));
					}
					if (this.getQueryText().trim().length() < 1) {
						errors
								.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.no.error"));
					}
				}
			}
		}

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
	private void setRefineQueryLookups(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		//Retrieve the session query bag from the cacheManager
		SessionQueryBag queryBag = cacheManager.getSessionQueryBag(sessionId);
		//setup the List of Queries
		nonAllGenesQueries = new ArrayList();
		allGenesQueries = new ArrayList();
		if (queryBag != null) {
			//Setup the Non All Gene Queries
			Map queries = queryBag.getNonAllGeneQueries();
			if(queries!=null) {
				Set keys = queries.keySet();
				for (Iterator iter = keys.iterator(); iter.hasNext();) {
					nonAllGenesQueries.add(queries.get(iter.next()));
				}
			}
			//Now setup all of the All Genes Queries
			queries = queryBag.getAllGenesQueries();
			if(queries!=null) {
				Set keys = queries.keySet();
				for (Iterator iter = keys.iterator(); iter.hasNext();) {
					allGenesQueries.add(queries.get(iter.next()));
				}
			}
		//Now setup all of the current result sets
		setResultSets(cacheManager.getResultSetNames(sessionId));
			
		} else {
			logger.debug("No Query Collection Object in Session");
		}
	}
	/**
	 * @return Returns the allGeneQuery.
	 */
	public String getAllGeneQuery() {
		return allGeneQuery;
	}

	/**
	 * @param allGeneQuery The allGeneQuery to set.
	 */
	public void setAllGeneQuery(String allGeneQuery) {
		this.allGeneQuery = allGeneQuery;
	}

	/**
	 * @return Returns the nonAllGenesQueries.
	 */
	public List getNonAllGenesQueries() {
		return nonAllGenesQueries;
	}
	/**
	 * @param nonAllGenesQueries The nonAllGenesQueries to set.
	 */
	public void setNonAllGenesQueries(List nonAllGenesQueries) {
		this.nonAllGenesQueries = nonAllGenesQueries;
	}
	/**
	 * @return Returns the isAllGenesQuery.
	 */
	public boolean isAllGenesQuery() {
		return isAllGenesQuery;
	}
	/**
	 * @param isAllGenesQuery The isAllGenesQuery to set.
	 */
	public void setIsAllGenesQuery(boolean isAllGenesQuery) {
		this.isAllGenesQuery = isAllGenesQuery;
	}
}
