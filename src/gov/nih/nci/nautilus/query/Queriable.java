/*
 * Created on Sep 24, 2004
 */
package gov.nih.nci.nautilus.query;

import java.io.Serializable;

import gov.nih.nci.nautilus.view.Viewable;

/**
 * @author SahniH
 */
public interface Queriable extends Serializable {
    public Viewable getAssociatedView();
    public void setAssociatedView(Viewable view);
    public String toString();
    public String getQueryName();
    public void setQueryName(String queryName);
	public Query[] getAssociatiedQueries();
}
