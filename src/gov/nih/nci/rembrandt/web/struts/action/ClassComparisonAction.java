package gov.nih.nci.rembrandt.web.struts.action;

import java.util.ArrayList;
import java.util.Collection;


import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ClassComparisonAnalysisCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQuery;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.FindingsFactory;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.rembrandt.web.struts.form.ClassComparisonForm;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.enumeration.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ClassComparisonAction extends DispatchAction {
    private Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
    
  
    
    /**
     * Method submittal
     * 
     * @param ActionMapping
     *            mapping
     * @param ActionForm
     *            form
     * @param HttpServletRequest
     *            request
     * @param HttpServletResponse
     *            response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ClassComparisonForm classComparisonForm = (ClassComparisonForm) form;
        String sessionId = request.getSession().getId();
        ClassComparisonQuery classComparisonQuery = createClassComparisonQuery(classComparisonForm,sessionId);
        
        FindingsFactory factory = new FindingsFactory();
        factory.createClassComparisonFinding(classComparisonQuery,sessionId,classComparisonQuery.getQueryName());
        Collection results = cacheManager.getAllFindingsResultsets(sessionId);
        
        
        return mapping.findForward("classComparisonSetup");
    }
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        ClassComparisonForm classComparisonForm = (ClassComparisonForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        String sessionId = request.getSession().getId();        
        SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
        classComparisonForm.setExistingGroupsList(sampleBasedQueriesRetriever.getAllPredefinedAndSampleSetNames(sessionId));
        
        return mapping.findForward("backToClassComparison");
    }
        
    private ClassComparisonQuery createClassComparisonQuery(ClassComparisonForm classComparisonQueryForm, String sessionId){

        ClassComparisonQuery classComparisonQuery = (ClassComparisonQuery) QueryManager.createQuery(QueryType.CLASS_COMPARISON_QUERY);
        classComparisonQuery.setQueryName(classComparisonQueryForm.getAnalysisResultName());
        
        
        //Create the clinical query collection from the selected groups in the form
        Collection<ClinicalDataQuery> clinicalQueryCollection = new ArrayList();
        
            if(classComparisonQueryForm.getSelectedGroups() != null && classComparisonQueryForm.getSelectedGroups().length == 2 ){
                SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
                for(int i=0; i<classComparisonQueryForm.getSelectedGroups().length; i++){
                    ClinicalDataQuery clinicalDataQuery= sampleBasedQueriesRetriever.getQuery(sessionId, classComparisonQueryForm.getSelectedGroups()[i]);
                    clinicalQueryCollection.add(clinicalDataQuery);
                }
                classComparisonQuery.setClinicalDataQueryCollection(clinicalQueryCollection);
            }
        
        //Create the foldChange criteria
        FoldChangeCriteria foldChangeCriteria = new FoldChangeCriteria();
            Float foldChangeFloatValue;
            if (classComparisonQueryForm.getFoldChange().equals("list")){
                try {
                    foldChangeFloatValue = Float.parseFloat(classComparisonQueryForm.getFoldChangeAuto());
                    UpRegulation exprFoldChangeDE = new UpRegulation(foldChangeFloatValue);
                    foldChangeCriteria.setFoldChangeObject(exprFoldChangeDE);
                    classComparisonQuery.setFoldChangeCriteria(foldChangeCriteria);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (classComparisonQueryForm.getFoldChange().equals("specify")){
                try {
                    foldChangeFloatValue = Float.parseFloat(classComparisonQueryForm.getFoldChangeManual());
                    UpRegulation exprFoldChangeDE = new UpRegulation(foldChangeFloatValue);
                    foldChangeCriteria.setFoldChangeObject(exprFoldChangeDE);
                    classComparisonQuery.setFoldChangeCriteria(foldChangeCriteria);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        
        //Create arrayPlatfrom criteria
        ArrayPlatformCriteria arrayPlatformCriteria = new ArrayPlatformCriteria();
            if(classComparisonQueryForm.getArrayPlatform() != "" || classComparisonQueryForm.getArrayPlatform().length() != 0){       
                ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonQueryForm.getArrayPlatform());
                arrayPlatformCriteria.setPlatform(arrayPlatformDE);
                classComparisonQuery.setArrayPlatformCriteria(arrayPlatformCriteria);
            }
            
        //Create class comparison criteria
        ClassComparisonAnalysisCriteria classComparisonAnalysisCriteria = new ClassComparisonAnalysisCriteria();
        Double statisticalSignificanceDouble = new Double(classComparisonQueryForm.getStatisticalSignificance());
        
            if(!classComparisonQueryForm.getComparisonAdjustment().equalsIgnoreCase("NONE")){
                MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.valueOf(MultiGroupComparisonAdjustmentType.class, classComparisonQueryForm.getComparisonAdjustment()));        
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(statisticalSignificanceDouble,Operator.LE,StatisticalSignificanceType.adjustedpValue);
                classComparisonAnalysisCriteria.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonAnalysisCriteria.setStatisticalSignificanceDE(statisticalSignificanceDE);
                classComparisonQuery.setClassComparisonAnalysisCriteria(classComparisonAnalysisCriteria);
            }
            else{
                MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.valueOf(MultiGroupComparisonAdjustmentType.class, classComparisonQueryForm.getComparisonAdjustment()));        
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(statisticalSignificanceDouble,Operator.LE,StatisticalSignificanceType.pValue);  
                classComparisonAnalysisCriteria.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonAnalysisCriteria.setStatisticalSignificanceDE(statisticalSignificanceDE);
                classComparisonQuery.setClassComparisonAnalysisCriteria(classComparisonAnalysisCriteria);
            }
            
            
            if(classComparisonQueryForm.getStatisticalMethod() != "" || classComparisonQueryForm.getStatisticalMethod().length() != 0){
                StatisticTypeDE statisticTypeDE = new StatisticTypeDE(StatisticalMethodType.valueOf(StatisticalMethodType.class, classComparisonQueryForm.getStatisticalMethod()));
                classComparisonAnalysisCriteria.setStatisticTypeDE(statisticTypeDE);
                classComparisonQuery.setClassComparisonAnalysisCriteria(classComparisonAnalysisCriteria);
            }
            
            return classComparisonQuery;
    }
    
    
}