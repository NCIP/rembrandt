package gov.nih.nci.nautilus.ui.bean;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.Resultant;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;

import org.dom4j.Document;
 

/**
 * This class is intended to be passed the report.jsp where
 * the attributes will be used to render the final report.
 * 
 * @author BauerD
 * Feb 8, 2005
 */
public class ReportBean implements Serializable{
	private Resultant resultant;
    private String resultantCacheKey;
    private String encodedResultantCacheKey;
	private Document reportXML;
	private Map filterParams = new HashMap();
	private boolean isSampleSetQuery = false;
	private String beanText;	
	
    
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
        setEncodedResultantCacheKey(resultantCacheKey);        
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
        //now grab the compound query text using resultant
		setBeanText(resultant);
	}
	public void setBeanText(Resultant resultant){
	    Resultant myResultant = resultant;
	    this.beanText = ((String) myResultant.getAssociatedQuery().toString());
	}
	public String getBeanText(){
	    return this.beanText;
	}
	/**
     * @param encodedResultantCacheKey
	 * @throws UnsupportedEncodingException
     */
    public void setEncodedResultantCacheKey(String resultantCacheKey){
         this.encodedResultantCacheKey = URLEncoder.encode(resultantCacheKey);
     }
    
    /**
     * @param encodedResultantCacheKey
     */
    public String getEncodedResultantCacheKey() {
        return this.encodedResultantCacheKey;
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
	 * @return Returns the isSampleSetQuery.
	 */
	public boolean isSampleSetQuery() {
		return isSampleSetQuery;
	}
	/**
	 * @param isSampleSetQuery The isSampleSetQuery to set.
	 */
	public void setSampleSetQuery(boolean isSampleSetQuery) {
		this.isSampleSetQuery = isSampleSetQuery;
	}
	public boolean getIsSampleSetQuery() {
	    return this.isSampleSetQuery;
	}
	public boolean isAllGenesQuery(){
	boolean isAllGenesQuery = false;	
	
		if(getResultant() != null){
			Queriable queriable = getResultant().getAssociatedQuery();
			if (queriable != null && queriable instanceof CompoundQuery) {
				CompoundQuery cQuery = (CompoundQuery)queriable;
				isAllGenesQuery = cQuery.isAllGenesQuery();
			}
		}
	return isAllGenesQuery;
	}
}
