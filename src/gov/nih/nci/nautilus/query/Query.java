package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.criteria.Criteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.view.Viewable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

abstract public class Query implements Queriable, Serializable, Cloneable{
	/**
	 * IMPORTANT! This class has a clone method! This requires that any new data
	 * field that is added to this class also be cloneable and be added to clone
	 * calls in the clone method.If you do not do this, you will not seperate 
	 * the references of at least one data field when we generate a copy of this
	 * object.This means that if the data field ever changes in one copy or the 
	 * other it will affect both instances... this will be hell to track down if
	 * you aren't ultra familiar with the code base, so don't make the mistake
	 * and add those methods now! (Not necesary for primitives.)
	 * 
	 * @author BauerD
	 */
	
    private Logger logger = Logger.getLogger(Query.class);
    
    //This attribute required for caching mechanism
    private String sessionId = null;

	private String queryName;

	private Viewable associatedView;
    
    protected DiseaseOrGradeCriteria diseaseOrGradeCriteria;

    protected SampleCriteria sampleIDCrit;

	public abstract QueryHandler getQueryHandler() throws Exception;
    
    public abstract QueryType getQueryType() throws Exception;

    public abstract String toString();
    
    public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
		return diseaseOrGradeCriteria;
	}

	public void setDiseaseOrGradeCrit(
			DiseaseOrGradeCriteria diseaseOrGradeCriteria) {
		this.diseaseOrGradeCriteria = diseaseOrGradeCriteria;
	}

	public SampleCriteria getSampleIDCrit() {
		return sampleIDCrit;
	}

	public void setSampleIDCrit(SampleCriteria sampleIDCrit) {
		this.sampleIDCrit = sampleIDCrit;
	}
	
	//TODO: The followig method checks if a given Query is empty
	public boolean isEmpty() {
		try {

			String currObjectName = this.getClass().getName();
			Class currClass = Class.forName(currObjectName);
			Method[] allPublicmethods = currClass.getMethods();

			for (int i = 0; i < allPublicmethods.length; i++) {
				Method currMethod = allPublicmethods[i];
				String currMethodString = currMethod.getName();
				if ((currMethodString.toUpperCase().startsWith("GET"))
						&& (currMethod.getModifiers() == Modifier.PUBLIC)) {
					Object thisObj = currMethod.invoke(this, null);

					if (thisObj != null) {
						if (Criteria.class.isInstance(thisObj)) {
							Criteria thisCriteria = (Criteria) thisObj;
							if (!thisCriteria.isEmpty()) {
								return false;
							}
						}
					}
				}
			}

		} catch (Throwable e) {
			logger.error(e);
       	}
		return true;
	}

	Query() {
		// criteriaCol = new ArrayList();
	}

	/*
	 * public Collection getCriteriaCol() { return criteriaCol; }
	 * 
	 * public void setCriteriaCol(Collection criteriaCol) { this.criteriaCol =
	 * criteriaCol; }
	 */

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Viewable getAssociatedView() {
		return associatedView;
	}

	public void setAssociatedView(Viewable associatedViewObj) {
		this.associatedView = associatedViewObj;
	}

	public Query[] getAssociatiedQueries() {
		Query[] queries = { this };
		return queries;
	}
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Object clone() {
    	Query myClone = null;
    	try {
    		myClone = (Query)super.clone();
    		myClone.associatedView = (Viewable)associatedView.clone();
    		myClone.diseaseOrGradeCriteria = (DiseaseOrGradeCriteria)diseaseOrGradeCriteria.clone();
    		myClone.sampleIDCrit = (SampleCriteria)sampleIDCrit.clone();
    	}catch(CloneNotSupportedException cnse) {
        		/*
        		 * This is meaningless as it will still perform
        		 * the shallow copy, and then let you know that
        		 * the object did not implement the Cloneable inteface.
        		 * Kind of a stupid implementation if you ask me...
        		 */
        	}
       	return myClone;
    }
}