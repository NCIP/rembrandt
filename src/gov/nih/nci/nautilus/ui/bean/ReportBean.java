package gov.nih.nci.nautilus.ui.bean;

import org.javaby.jbyte.Template;

/**
 * @author BauerD
 * Feb 8, 2005
 * This class is intended to be passed the report.jsp where
 * the attributes will be used to render the final report.
 * 
 */
public class ReportBean {
	
    private String resultantCacheKey;
	private Template reportTemplate;
    
	public ReportBean() {}

	/**
	 * @return Returns the reportCacheKey.
	 */
	public String getResultantCacheKey() {
		return resultantCacheKey;
	}
	/**
	 * @return Returns the reportTemplate.
	 */
	public Template getReportTemplate() {
		return reportTemplate;
	}
	/**
	 * @param reportCacheKey The reportCacheKey to set.
	 */
	public void setResultantCacheKey(String reportCacheKey) {
		this.resultantCacheKey = reportCacheKey;
	}
	/**
	 * @param reportTemplate The reportTemplate to set.
	 */
	public void setReportTemplate(Template reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
}
