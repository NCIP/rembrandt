package gov.nih.nci.nautilus.ui.struts.form;

import java.util.HashMap;

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
    private String xsltFileName;
    //General Filter values to be used by the XSLT
    private HashMap filterParams = new HashMap();
    private String filter_value1;
    private String filter_value2;
    private String filter_value3;
 
    private String filter_value4;
    private String filter_value5;
    private String filter_value6;
       
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
	
	/**
	 * @return Returns the xsltFileName.
	 */
	public String getXsltFileName() {
		return xsltFileName;
	}
	/**
	 * @param xsltFileName The xsltFileName to set.
	 */
	public void setXsltFileName(String xsltFileName) {
		this.xsltFileName = xsltFileName;
	}
	/**
	 * @return Returns the filter_value1.
	 */
	public String getFilter_value1() {
		return filter_value1;
	}
	/**
	 * @param filter_value1 The filter_value1 to set.
	 */
	public void setFilter_value1(String filter_value1) {
		this.filter_value1 = filter_value1;
		filterParams.put("filter_value1",filter_value1);
		
	}
	/**
	 * @return Returns the filter_value2.
	 */
	public String getFilter_value2() {
		return filter_value2;
	}
	/**
	 * @param filter_value2 The filter_value2 to set.
	 */
	public void setFilter_value2(String filter_value2) {
		this.filter_value2 = filter_value2;
		filterParams.put("filter_value2",filter_value2);
	}
	/**
	 * @return Returns the filter_value3.
	 */
	public String getFilter_value3() {
		return filter_value3;
	}
	/**
	 * @param filter_value3 The filter_value3 to set.
	 */
	public void setFilter_value3(String filter_value3) {
		this.filter_value3 = filter_value3;
		filterParams.put("filter_value3",filter_value3);
	}
	/**
	 * @return Returns the filter_value4.
	 */
	public String getFilter_value4() {
		return filter_value4;
	}
	/**
	 * @param filter_value4 The filter_value4 to set.
	 */
	public void setFilter_value4(String filter_value4) {
		this.filter_value4 = filter_value4;
		filterParams.put("filter_value4",filter_value4);
		
	}
	/**
	 * @return Returns the filter_value5.
	 */
	public String getFilter_value5() {
		return filter_value5;
	}
	/**
	 * @param filter_value5 The filter_value5 to set.
	 */
	public void setFilter_value5(String filter_value5) {
		this.filter_value5 = filter_value5;
		filterParams.put("filter_value5",filter_value5);
		
	}
	/**
	 * @return Returns the filter_value6.
	 */
	public String getFilter_value6() {
		return filter_value6;
	}
	/**
	 * @param filter_value6 The filter_value6 to set.
	 */
	public void setFilter_value6(String filter_value6) {
		this.filter_value6 = filter_value6;
		filterParams.put("filter_value6",filter_value6);
		
	}
	/**
	 * @return Returns the filterParams.
	 */
	public HashMap getFilterParams() {
		return filterParams;
	}
	/**
	 * @param filterParams The filterParams to set.
	 */
	public void setFilterParams(HashMap filterParams) {
		this.filterParams = filterParams;
	}
}
