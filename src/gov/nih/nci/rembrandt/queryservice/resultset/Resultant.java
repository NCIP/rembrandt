package gov.nih.nci.rembrandt.queryservice.resultset;

import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.Queriable;

import java.io.Serializable;

/**
 * @author SahniH
 * Date: Oct 21, 2004
 * 
 */
public class Resultant implements Serializable {
private Queriable associatedQuery;
private ResultsContainer resultsContainer;
private Viewable associatedView;
private Throwable returnedException;
private boolean isException = false;

/**
 * @return Returns the returnedException.
 */
public Throwable getReturnedException() {
	return returnedException;
}
/**
 * @param returnedException The returnedException to set.
 */
public void setReturnedException(Throwable returnedException) {
	this.returnedException = returnedException;
}
/**
 * @return Returns the associatedView.
 */
public Viewable getAssociatedView() {
	return associatedView;
}
/**
 * @param associatedView The associatedView to set.
 */
public void setAssociatedView(Viewable associatedView) {
	this.associatedView = associatedView;
}
/**
 * @return Returns the resultsContainer.
 */
public ResultsContainer getResultsContainer() {
	return resultsContainer;
}
/**
 * @param resultsContainer The resultsContainer to set.
 */
public void setResultsContainer(ResultsContainer resultsContainer) {
	this.resultsContainer = resultsContainer;
}

/**
 * @return Returns the associatedQuery.
 */
public Queriable getAssociatedQuery() {
	return this.associatedQuery;
}
/**
 * @param associatedQuery The associatedQuery to set.
 */
public void setAssociatedQuery(Queriable associatedQuery) {
	this.associatedQuery = associatedQuery;
}
/**
 * @return Returns the isException.
 */
public boolean isException() {
	if(returnedException != null){
		isException = true;
	}
	return isException;
}

}
