package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.QueryCollection;
import gov.nih.nci.nautilus.ui.helper.SelectedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;

public class RefineQueryForm extends BaseForm implements Factory {
    private static Logger logger = Logger.getLogger(RefineQueryForm.class);
    private ActionErrors errors = new ActionErrors();;
	private String queryText;
	private String compoundView;
	private String resultSetName;
	private String runFlag;
	private String method;
    // Collections used for Lookup values.
	private ArrayList queryNameColl = new ArrayList();
	private ArrayList compoundViewColl = new ArrayList();
    private List selectedQueries = LazyList.decorate(new ArrayList(), this);
	public RefineQueryForm(){

		super();

	}

	/**
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		String paramValue = request.getParameter(mapping.getParameter());
			 
		//Validate Query validation
		if(paramValue!=null) {
 
		//Run Report Validations
		if (paramValue.equalsIgnoreCase("displayresult")) {
			if (this.getCompoundView().trim().length() < 1){ 
				if (this.getQueryText().trim().length() >= 1) {
					this.setQueryText("");
					errors.add("compoundView", new ActionError("gov.nih.nci.nautilus.ui.struts.form.refinequery.no.view"));
				}
				if (this.getQueryText().trim().length() < 1) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.no.error"));
				}
			}
		}
        }
        
		return errors;
	}


	private void setRefineQueryLookups(HttpServletRequest request) {

		// Get the Query Collection from the session

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		queryNameColl = new ArrayList();
		queryNameColl.add( new LabelValueBean( " ", " " ));

		if (queryCollect != null) {
			Collection collectionOfQueries = queryCollect.getQueries();
		
			for (Iterator iter = collectionOfQueries.iterator(); iter.hasNext();) {
				Query thisQuery = (Query) iter.next();

				queryNameColl.add( new LabelValueBean( thisQuery.getQueryName(), thisQuery.getQueryName() ) );
			
			}		
		}else {
		
			logger.debug("No Query Collection Object in Session");
		}

	}


	/**
	 * Method reset.
	 * Reset all properties to their default values.
	 * @param ActionMapping mapping used to select this instance.
	 * @param HttpServletRequest request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {

     	queryText="";
		compoundView="";
		resultSetName="";
	
		setRefineQueryLookups(request);
		compoundViewColl = new ArrayList();
		compoundViewColl.add( new LabelValueBean( " ", " " ));


	}
	
  /**
  * Returns the queryName1.
  * @return String
  */
 public String getQueryText() {

	 return queryText;
 }

 /**
  * Set the queryName1.
  * @param queryName1 The queryName to set
  */
 public void setQueryText(String queryText) {
	 this.queryText = queryText;

 }

  
   /**
   * Returns the compoundView.
   * @return String
   */
  public String getCompoundView() {

	  return compoundView;
  }

  /**
   * Set the compoundView.
   * @param compoundView The compoundView to set
   */
  public void setCompoundView(String compoundView) {
	  this.compoundView = compoundView;

  }

  /**
  * Returns the resultsetName.
  * @return String
  */
 public String getResultSetName() {

	 return resultSetName;
 }

 /**
  * Set the resultsetName.
  * @param resultsetName The resultsetName to set
  */
 public void setResultSetName(String resultsetName) {
	 this.resultSetName = resultsetName;

 }
 /**
  * Set the Run Report button flag.
  * @param runFlag The runFlag to set
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

	public ArrayList getQueryNameColl(){
	   return queryNameColl;
	   }


	public ArrayList getCompoundViewColl(){
	   return compoundViewColl;
	   }

	public void setCompoundViewColl(ArrayList viewColl) {
		this.compoundViewColl = viewColl;
	}
	
	/**
	 * @return Returns the errors.
	 */
	public ActionErrors getErrors() {
		return errors;
	}
	/**
	 * @param errors The errors to set.
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
         if(selectedQueries.isEmpty()) {
            SelectedQuery newQuery = new SelectedQuery();
            newQuery.setQueryName("");
            selectedQueries.add(newQuery);
         }
		 return selectedQueries;
	}
	/**
	 * @param selectedQueries The selectedQueries to set.
	 */
	public void setSelectedQueries(ArrayList selectedQueries) {
		this.selectedQueries = selectedQueries;
	}
 
    public SelectedQuery getSelectedQuery(int index) {
        return (SelectedQuery) this.getSelectedQueries().get(index);
    }
    
    public void addSelectedQuery() {
    	List selectedQueries = this.getSelectedQueries();
        SelectedQuery newQuery = new SelectedQuery();
        newQuery.setQueryName("");
        selectedQueries.add(newQuery);
    }

	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Factory#create()
	 */
	public Object create() {
		SelectedQuery newQuery = new SelectedQuery();
        newQuery.setQueryName("");
        return newQuery;
	}
}
