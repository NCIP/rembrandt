/*
 * Created on Nov 19, 2004
 *
 */
package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.query.CompoundQuery;

/**
 * @author bauerd
 *
 */
public class ReportGeneratorForm extends BaseForm {

    private CompoundQuery requestQuery;
    
	/**
	 * @return Returns the query.
	 */
	public CompoundQuery getRequestQuery() {
		return requestQuery;
	}
	
    /**
	 * @param query The query to set.
	 */
	public void setRequestQuery(CompoundQuery query) {
		this.requestQuery = query;
	}
}
