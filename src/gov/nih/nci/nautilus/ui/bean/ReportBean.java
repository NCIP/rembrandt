package gov.nih.nci.nautilus.ui.bean;

import org.dom4j.Document;


/**
 * @author BauerD
 * Feb 8, 2005
 * This class is intended to be passed the report.jsp where
 * the attributes will be used to render the final report.
 * 
 */
public class ReportBean {
	
    private String resultantCacheKey;
	private Document reportXML;
    
	public ReportBean() {}

	/**
	 * @return Returns the reportCacheKey.
	 */
	public String getResultantCacheKey() {
		return resultantCacheKey;
	}
	/**
	 * @return Returns the reportXML.
	 */
	public Document getReportXML() {
		return reportXML;
	}
	/**
	 * @param reportCacheKey The reportCacheKey to set.
	 */
	public void setResultantCacheKey(String reportCacheKey) {
		this.resultantCacheKey = reportCacheKey;
	}
	/**
	 * @param reportXML The reportXML to set.
	 */
	public void setReportXML(Document reportTemplate) {
		this.reportXML = reportTemplate;
	}
}
