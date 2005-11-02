package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.rembrandt.web.struts.form.ClassComparisonForm;

import java.util.ArrayList;
import java.util.Collection;

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
        ClassComparisonQueryDTO classComparisonQueryDTO = createClassComparisonQueryDTO(classComparisonForm,sessionId);
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createClassComparisonFinding(classComparisonQueryDTO,sessionId,classComparisonQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        
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
        
    private ClassComparisonQueryDTO createClassComparisonQueryDTO(ClassComparisonForm classComparisonQueryForm, String sessionId){

        ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
        classComparisonQueryDTO.setQueryName(classComparisonQueryForm.getAnalysisResultName());
        
        
        //Create the clinical query DTO collection from the selected groups in the form
        Collection<ClinicalQueryDTO> clinicalQueryCollection = new ArrayList<ClinicalQueryDTO>();
        
            if(classComparisonQueryForm.getSelectedGroups() != null && classComparisonQueryForm.getSelectedGroups().length == 2 ){
                SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
                for(int i=0; i<classComparisonQueryForm.getSelectedGroups().length; i++){
                    ClinicalDataQuery clinicalDataQuery= sampleBasedQueriesRetriever.getQuery(sessionId, classComparisonQueryForm.getSelectedGroups()[i]);
                    clinicalQueryCollection.add(clinicalDataQuery);
                }
                classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);
            }
        
        //Create the foldChange criteria
       
            
            if (classComparisonQueryForm.getFoldChange().equals("list")){
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeAuto()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);
            }
            if (classComparisonQueryForm.getFoldChange().equals("specify")){        
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeManual()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);                   
            }
            
        
        //Create arrayPlatfrom criteria
            if(classComparisonQueryForm.getArrayPlatform() != "" || classComparisonQueryForm.getArrayPlatform().length() != 0){       
                ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonQueryForm.getArrayPlatform());
                classComparisonQueryDTO.setArrayPlatformDE(arrayPlatformDE);
            }
            
        //Create class comparison criteria
           if(!classComparisonQueryForm.getComparisonAdjustment().equalsIgnoreCase("NONE")){
                MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.valueOf(MultiGroupComparisonAdjustmentType.class, classComparisonQueryForm.getComparisonAdjustment()));        
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.adjustedpValue);
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
                
            }
            else{
                MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.valueOf(MultiGroupComparisonAdjustmentType.class, classComparisonQueryForm.getComparisonAdjustment()));        
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.pValue);  
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
                
            }
            
            
            if(classComparisonQueryForm.getStatisticalMethod() != "" || classComparisonQueryForm.getStatisticalMethod().length() != 0){
                StatisticTypeDE statisticTypeDE = new StatisticTypeDE(StatisticalMethodType.valueOf(StatisticalMethodType.class, classComparisonQueryForm.getStatisticalMethod()));
                classComparisonQueryDTO.setStatisticTypeDE(statisticTypeDE);
            }
            
            return classComparisonQueryDTO;
    }
    
    
}