/*
 * Created on Nov 19, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.struts.form;

import gov.nih.nci.nautilus.query.CompoundQuery;

/**
 * @author bauerd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
