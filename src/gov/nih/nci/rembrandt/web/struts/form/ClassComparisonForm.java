package gov.nih.nci.rembrandt.web.struts.form;



import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.enumeration.*;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQueryDTOImpl;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import uk.ltd.getahead.dwr.ExecutionContext;


public class ClassComparisonForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String [] existingGroups;
    
    private static List existingGroupsList;
    
    private String [] selectedGroups;
    
    private String analysisResultName = "";
    
    private String statistic = "default";
    
    private String statisticalMethod = "TTest";
    
    private ArrayList statisticalMethodCollection = new ArrayList();
    
    private String comparisonAdjustment = "NONE";
    
    private ArrayList comparisonAdjustmentCollection = new ArrayList();
    
    private String foldChange = "list";
    
    private String foldChangeAuto = "2";
    
    private List foldChangeAutoList = new ArrayList();
    
    private String foldChangeManual;
    
    private Double statisticalSignificance = .05;
    
    private String arrayPlatform = "";
    
   

	public ClassComparisonForm(){
			
		// Create Lookups for ClassComparisonForm screens 
        for (MultiGroupComparisonAdjustmentType multiGroupComparisonAdjustmentType : MultiGroupComparisonAdjustmentType.values()){
            comparisonAdjustmentCollection.add(new LabelValueBean(multiGroupComparisonAdjustmentType.toString(),multiGroupComparisonAdjustmentType.name()));
        }
        
        
        for (StatisticalMethodType statisticalMethodType : StatisticalMethodType.values()){
            statisticalMethodCollection.add(new LabelValueBean(statisticalMethodType.toString(),statisticalMethodType.name()));  
        }
        
        for (int i=0; i<RembrandtConstants.FOLD_CHANGE_DEFAULTS.length;i++){
            foldChangeAutoList.add(new LabelValueBean(RembrandtConstants.FOLD_CHANGE_DEFAULTS[i],RembrandtConstants.FOLD_CHANGE_DEFAULTS[i]));
        }
        
    }



    /**
     * @return Returns the existingGroups.
     */
    public String[] getExistingGroups() {
        return existingGroups;
    }



    /**
     * @param existingGroups The existingGroups to set.
     */
    public void setExistingGroups(String [] existingGroups) {
        this.existingGroups = existingGroups;
    }



    /**
     * @return Returns the existingGroupsList.
     */
    public List getExistingGroupsList() {
        return ClassComparisonForm.existingGroupsList;
    }



    /**
     * @param existingGroupsList The existingGroupsList to set.
     */
    public void setExistingGroupsList(List existingGroupsList) {
        ClassComparisonForm.existingGroupsList = existingGroupsList;
    }



    /**
     * @return Returns the selectedGroups.
     */
    public String [] getSelectedGroups() {
        return selectedGroups;
    }



    /**
     * @param selectedGroups The selectedGroups to set.
     */
    public void setSelectedGroups(String [] selectedGroups) {
        this.selectedGroups = selectedGroups;
    }


    /**
     * @return Returns the analysisResultName.
     */
    public String getAnalysisResultName() {
        return analysisResultName;
    }



    /**
     * @param analysisResultName The analysisResultName to set.
     */
    public void setAnalysisResultName(String analysisResultName) {
        this.analysisResultName = analysisResultName;
    }



    /**
     * @return Returns the arrayPlatform.
     */
    public String getArrayPlatform() {
        return arrayPlatform;
    }



    /**
     * @param arrayPlatform The arrayPlatform to set.
     */
    public void setArrayPlatform(String arrayPlatform) {
        this.arrayPlatform = arrayPlatform;
    }



    /**
     * @return Returns the comparisonAdjustment.
     */
    public String getComparisonAdjustment() {
        return comparisonAdjustment;
    }



    /**
     * @param comparisonAdjustment The comparisonAdjustment to set.
     */
    public void setComparisonAdjustment(String comparisonAdjustment) {
        this.comparisonAdjustment = comparisonAdjustment;
    }



    /**
     * @return Returns the foldChange.
     */
    public String getFoldChange() {
        return foldChange;
    }



    /**
     * @param foldChange The foldChange to set.
     */
    public void setFoldChange(String foldChange) {
        this.foldChange = foldChange;
    }



   



    /**
     * @return Returns the statistic.
     */
    public String getStatistic() {
        return statistic;
    }



    /**
     * @param statistic The statistic to set.
     */
    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }



    /**
     * @return Returns the statisticalMethod.
     */
    public String getStatisticalMethod() {
        return statisticalMethod;
    }



    /**
     * @param statisticalMethod The statisticalMethod to set.
     */
    public void setStatisticalMethod(String statisticalMethod) {
        this.statisticalMethod = statisticalMethod;
    }
    
    /**
     * @return Returns the comparisonAdjustmentCollection.
     */
    public ArrayList getComparisonAdjustmentCollection() {
        return comparisonAdjustmentCollection;
    }



    /**
     * @param comparisonAdjustmentCollection The comparisonAdjustmentCollection to set.
     */
    public void setComparisonAdjustmentCollection(
            ArrayList comparisonAdjustmentCollection) {
        this.comparisonAdjustmentCollection = comparisonAdjustmentCollection;
    }



    /**
     * @return Returns the statisticalMethodCollection.
     */
    public ArrayList getStatisticalMethodCollection() {
        return statisticalMethodCollection;
    }



    /**
     * @param statisticalMethodCollection The statisticalMethodCollection to set.
     */
    public void setStatisticalMethodCollection(ArrayList statisticalMethodCollection) {
        this.statisticalMethodCollection = statisticalMethodCollection;
    }
    
    /**
     * @return Returns the foldChangeAuto.
     */
    public String getFoldChangeAuto() {
        return foldChangeAuto;
    }



    /**
     * @param foldChangeAuto The foldChangeAuto to set.
     */
    public void setFoldChangeAuto(String foldChangeAuto) {
        this.foldChangeAuto = foldChangeAuto;
    }



    /**
     * @return Returns the foldChangeManual.
     */
    public String getFoldChangeManual() {
        return foldChangeManual;
    }



    /**
     * @param foldChangeManual The foldChangeManual to set.
     */
    public void setFoldChangeManual(String foldChangeManual) {
        this.foldChangeManual = foldChangeManual;
    }
    
    /**
     * @return Returns the statisticalSignificance.
     */
    public Double getStatisticalSignificance() {
        return statisticalSignificance;
    }



    /**
     * @param statisticalSignificance The statisticalSignificance to set.
     */
    public void setStatisticalSignificance(Double statisticalSignificance) {
        this.statisticalSignificance = statisticalSignificance;
    }
        

    /**
     * Method validate
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
        
        
        //Analysis Query Name cannot be blank

        if (selectedGroups != null && selectedGroups.length != 2){
            errors.add("selectedGroups", new ActionError(
                    "gov.nih.nci.nautilus.ui.struts.form.groups.more.error"));
        }
       

        return errors;
    }
    
   
    /**
     * Method reset
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        analysisResultName = "";        
        statistic = "default";        
        comparisonAdjustment = "NONE";        
        foldChange = "list";      
        foldChangeAuto = "2"; 
        statisticalSignificance = .05;        
        arrayPlatform = "";             
        statisticalMethod = "TTest";
    }



    /**
     * @return Returns the foldChangeAutoList.
     */
    public List getFoldChangeAutoList() {
        return foldChangeAutoList;
    }



    /**
     * @param foldChangeAutoList The foldChangeAutoList to set.
     */
    public void setFoldChangeAutoList(List foldChangeAutoList) {
        this.foldChangeAutoList = foldChangeAutoList;
    }



   


    
}
