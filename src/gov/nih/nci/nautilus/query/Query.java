package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.view.View;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.criteria.*;

import java.util.Collection;
import java.util.ArrayList;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:19:55 PM
 * To change this template use Options | File Templates.
 */
abstract public class Query {

    private String queryName;
    private View associatedView;
    public abstract QueryHandler getQueryHandler() throws Exception;
    
    //added by Prashant
    public abstract QueryType getQueryType() throws Exception;
    public abstract String toString();
    
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
					&& (currMethod.getModifiers() == Modifier.PUBLIC))
				{
						Object thisObj = currMethod.invoke(this, null);
	
						if (thisObj != null) {
							if (Criteria.class.isInstance(thisObj)) {
								Criteria thisCriteria = (Criteria) thisObj;
								if (!thisCriteria.isEmpty()) {return false;}
							}
						}
				}
			}
				
		 } catch (Throwable e) {
				e.printStackTrace();
		 }
		 return true;
	}

    

    Query() {
       // criteriaCol = new ArrayList();
    }

    /*
    public Collection getCriteriaCol() {
        return criteriaCol;
    }

    public void setCriteriaCol(Collection criteriaCol) {
        this.criteriaCol = criteriaCol;
    }
    */

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public View getAssociatedView() {
        return associatedView;
    }

    public void setAssociatedView(View associatedViewObj) {
        this.associatedView = associatedViewObj;
    }
}
