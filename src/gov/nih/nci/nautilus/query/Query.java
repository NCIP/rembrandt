package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.view.View;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;

import java.util.Collection;
import java.util.ArrayList;

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
