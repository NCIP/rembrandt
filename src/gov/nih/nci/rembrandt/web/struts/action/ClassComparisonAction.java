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
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.EnumCaseChecker;
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
	
	private UserCredentials credentials;  
	private Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
    private MultiGroupComparisonAdjustmentType ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE = MultiGroupComparisonAdjustmentType.FWER;
    private StatisticalMethodType ERROR_STATISTICAL_METHOD_TYPE = StatisticalMethodType.TTest;
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
        
        /*Create the InstituteDEs using credentials from the local session.
         * May want to put these in the cache eventually.
         */
        
        if(request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS)!=null){
            credentials = (UserCredentials) request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
            classComparisonQueryDTO.setInstitutionDEs(credentials.getInstitutes());
        }
        
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createClassComparisonFinding(classComparisonQueryDTO,sessionId,classComparisonQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        
        return mapping.findForward("viewResults");
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
                    //add logic to if there is no predefined query.. use the given samples from the user
                    
                    //bag and construct a clinical query to add into the collection
                    clinicalQueryCollection.add(clinicalDataQuery);
                }
                classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);
            }
        
        //Create the foldChange DEs
       
            
            if (classComparisonQueryForm.getFoldChange().equals("list")){
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeAuto()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);
            }
            if (classComparisonQueryForm.getFoldChange().equals("specify")){        
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeManual()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);                   
            }
            
        
        //Create arrayPlatfrom DEs
            if(classComparisonQueryForm.getArrayPlatform() != "" || classComparisonQueryForm.getArrayPlatform().length() != 0){       
                ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonQueryForm.getArrayPlatform());
                classComparisonQueryDTO.setArrayPlatformDE(arrayPlatformDE);
            }
            
           //Create class comparison DEs
            /*
             * This following code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumCaseChecker for 
             * enlightenment.
             */
           MultiGroupComparisonAdjustmentType mgAdjustmentType; 
           String multiGroupComparisonAdjustmentTypeString= EnumCaseChecker.getEnumTypeName(classComparisonQueryForm.getComparisonAdjustment(),MultiGroupComparisonAdjustmentType.values());
           if(multiGroupComparisonAdjustmentTypeString!=null) {
        	   mgAdjustmentType = MultiGroupComparisonAdjustmentType.valueOf(multiGroupComparisonAdjustmentTypeString);
           }else {
        	   	logger.error("Invalid MultiGroupComparisonAdjustmentType value given in request");
           		logger.error("Selected MultiGroupComparisonAdjustmentType value = "+classComparisonQueryForm.getComparisonAdjustment());
           		logger.error("Using the default MultiGroupComparisonAdjustmentType value = "+ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE);
           		mgAdjustmentType = ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE;
           }
           MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(mgAdjustmentType);  ;
           if(!classComparisonQueryForm.getComparisonAdjustment().equalsIgnoreCase("NONE")){
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.adjustedpValue);
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
            }
            else{
            	StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.pValue);  
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
                
            }
           
            if(classComparisonQueryForm.getStatisticalMethod() != "" || classComparisonQueryForm.getStatisticalMethod().length() != 0){
            	/*
                 * This following code is here to deal with an observed problem with the changing 
                 * of case in request parameters.  See the class EnumCaseChecker for 
                 * enlightenment.
                 */
            	StatisticalMethodType statisticalMethodType; 
            	String statisticalMethodTypeString= EnumCaseChecker.getEnumTypeName(classComparisonQueryForm.getStatisticalMethod(),StatisticalMethodType.values());
                 if(statisticalMethodTypeString!=null) {
                	 statisticalMethodType = StatisticalMethodType.valueOf(statisticalMethodTypeString);
                 }else {
              	   	logger.error("Invalid StatisticalMethodType value given in request");
             		logger.error("Selected StatisticalMethodType value = "+classComparisonQueryForm.getStatisticalMethod());
             		logger.error("Using the default StatisticalMethodType type of :"+ERROR_STATISTICAL_METHOD_TYPE);
             		statisticalMethodType = ERROR_STATISTICAL_METHOD_TYPE;
                 }
                StatisticTypeDE statisticTypeDE = new StatisticTypeDE(statisticalMethodType);
                classComparisonQueryDTO.setStatisticTypeDE(statisticTypeDE);
            }
            
            return classComparisonQueryDTO;
    }
    
    
}