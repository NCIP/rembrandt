package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.ui.bean.ReportBean;

/**
 * @author bauerd
 *
 */
public class ReportGeneratorForm extends BaseForm {
	private String queryName;
	private ReportBean reportBean;
    private CompoundQuery requestQuery;
    private String resultSetName;
    
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
	/**
	 * @return Returns the reportBean.
	 */
	public ReportBean getReportBean() {
		return reportBean;
	}
	/**
	 * @param reportBean The reportBean to set.
	 */
	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}
	/**
	 * @return Returns the queryName.
	 */
	public String getQueryName() {
		return queryName;
	}
	/**
	 * @param queryName The queryName to set.
	 */
	public void setQueryName(String queryName) {
		this.queryName =queryName;
		this.resultSetName =queryName;
	}
	/**
	 * @return Returns the resultSetName.
	 */
	public String getResultSetName() {
		return resultSetName;
	}
	/**
	 * @param resultSetName The resultSetName to set.
	 */
	public void setResultSetName(String resultSetName) {
		this.resultSetName = resultSetName;
		this.queryName = resultSetName;
	}
}
