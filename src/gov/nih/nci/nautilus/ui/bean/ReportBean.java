package gov.nih.nci.nautilus.ui.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;

import org.dom4j.Document;


/**
 * @author BauerD
 * Feb 8, 2005
 * This class is intended to be passed the report.jsp where
 * the attributes will be used to render the final report.
 * 
 */
public class ReportBean implements Serializable{
	private Resultant resultant;
    private String resultantCacheKey;
	private Document reportXML;
	private Map filterParams = new HashMap();
	private boolean isResultSetQuery = false;
    
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
	/**
	 * @return Returns the resultant.
	 */
	public Resultant getResultant() {
		return resultant;
	}
	/**
	 * @param resultant The resultant to set.
	 */
	public void setResultant(Resultant resultant) {
		this.resultant = resultant;
	}
	/**
	 * @return Returns the filterParams.
	 */
	public Map getFilterParams() {
		return filterParams;
	}
	/**
	 * @param filterParams The filterParams to set.
	 */
	public void setFilterParams(Map filterParams) {
		this.filterParams = filterParams;
	}
	/**
	 * @return Returns the isResultSetQuery.
	 */
	public boolean isResultSetQuery() {
		return isResultSetQuery;
	}
	/**
	 * @param isResultSetQuery The isResultSetQuery to set.
	 */
	public void setResultSetQuery(boolean isResultSetQuery) {
		this.isResultSetQuery = isResultSetQuery;
	}
}
