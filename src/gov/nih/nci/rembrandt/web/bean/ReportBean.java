package gov.nih.nci.rembrandt.web.bean;

import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprDiseaseView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.net.URLEncoder;

import org.dom4j.Document;
 

/**
 * This class is intended to be passed the report.jsp where
 * the attributes will be used to render the final report.
 * 
 * IMPORTANT!
 * Any operations on the stored Resultant should check to 
 * make sure that it is not of class NullResultant
 * 
 * @author BauerD
 * Feb 8, 2005
 */
public class ReportBean implements Serializable{
	private Queriable associatedQuery;
	private Resultant resultant;
    private String resultantCacheKey;
    private String encodedResultantCacheKey;
	private Document reportXML;
	private Map filterParams = new HashMap();
	private boolean isSampleSetQuery = false;
	private String beanText;
    private Viewable associatedView;
    private String beanView;
	
	
    
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
		setAssociatedBeanView(resultant);
	}
	
    public void setBeanText(Resultant resultant){
	    Resultant myResultant = resultant;
	   
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
		if(associatedQuery != null){
			if (associatedQuery != null && associatedQuery instanceof CompoundQuery) {
				CompoundQuery cQuery = (CompoundQuery)associatedQuery;
				isAllGenesQuery = cQuery.isAllGenesQuery();
			}
		}
	return isAllGenesQuery;
	}
	/**
	 * @return Returns the associatedQuery.
	 */
	public Queriable getAssociatedQuery() {
		return associatedQuery;
	}
	/**
	 * @param associatedQuery The associatedQuery to set.
	 */
	public void setAssociatedQuery(Queriable associatedQuery) {
		this.associatedQuery = associatedQuery;
		 if(associatedQuery!=null) {
	    	this.beanText = ((String) associatedQuery.toString());
	    }
	}
	public void setAssociatedBeanView(Resultant resultant) {
	    this.associatedView = resultant.getAssociatedView();
	    if(associatedView instanceof ClinicalSampleView){
	        this.beanView = ResourceBundle.getBundle(RembrandtConstants.APPLICATION_RESOURCES, Locale.US).getString("gov.nih.nci.caintegrator.dto.view.ViewType$ClinicalView");
	    }
	    if(associatedView instanceof CopyNumberSampleView){
	        this.beanView = ResourceBundle.getBundle(RembrandtConstants.APPLICATION_RESOURCES, Locale.US).getString("gov.nih.nci.caintegrator.dto.view.ViewType$CopyNumberSampleView");
	    }
	    if(associatedView instanceof GeneExprDiseaseView){
	        this.beanView = ResourceBundle.getBundle(RembrandtConstants.APPLICATION_RESOURCES, Locale.US).getString("gov.nih.nci.caintegrator.dto.view.ViewType$GeneGroupSampleView");
	    }
	    if(associatedView instanceof GeneExprSampleView){
	        this.beanView = ResourceBundle.getBundle(RembrandtConstants.APPLICATION_RESOURCES, Locale.US).getString("gov.nih.nci.caintegrator.dto.view.ViewType$GeneSingleSampleView");
	    }
	    else{
	        this.beanView = "View cannot be determined";
	    }
	}
	public Viewable getAssociatedView() {
		return associatedView;
	}
	public String getBeanView() {
		return beanView;
	}
	
	
}
