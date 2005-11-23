package gov.nih.nci.rembrandt.web.struts.action;

import java.util.Collection;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.rembrandt.web.helper.UserPreferencesHelper;
import gov.nih.nci.rembrandt.web.struts.form.PrincipalComponentForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PrincipalComponentAction extends DispatchAction {
    private Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;
    private Collection<CloneIdentifierDE> cloneIdentifierDECollection;
    private SessionCriteriaBag sessionCriteriaBag;
    
    
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
        
        PrincipalComponentForm principalComponentForm = (PrincipalComponentForm) form;
        String sessionId = request.getSession().getId();
        PrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = createPrincipalComponentAnalysisQueryDTO(principalComponentForm,sessionId);
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createPCAFinding(principalComponentAnalysisQueryDTO,sessionId,principalComponentAnalysisQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
                
        return mapping.findForward("viewResults");
    }
    
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        PrincipalComponentForm principalComponentForm = (PrincipalComponentForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        HttpSession session = request.getSession();
        String sessionId = request.getSession().getId();        
        SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
        UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(session);
        principalComponentForm.setExistingGroupsList(sampleBasedQueriesRetriever.getAllPredefinedAndSampleSetNames(sessionId));
        principalComponentForm.setGeneSetName(userPreferencesHelper.getGeneSetName());
        principalComponentForm.setReporterSetName(userPreferencesHelper.getReporterSetName());
        principalComponentForm.setVariancePercentile(userPreferencesHelper.getVariancePercentile());
        
        return mapping.findForward("backToPrincipalComponent");
    }
    
    private PrincipalComponentAnalysisQueryDTO createPrincipalComponentAnalysisQueryDTO(PrincipalComponentForm principalComponentForm, String sessionId) {
        PrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = (PrincipalComponentAnalysisQueryDTO)ApplicationFactory.newQueryDTO(QueryType.PCA_QUERY);
        principalComponentAnalysisQueryDTO.setQueryName(principalComponentForm.getAnalysisResultName());
        sessionCriteriaBag = presentationTierCache.getSessionCriteriaBag(sessionId);
        
        
        //Create the clinical Query for this anlysisQuery DTO
        
        ClinicalDataQuery group1 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
        
        if(principalComponentForm.getSelectedGroupName().equals("")||principalComponentForm.getSelectedGroupName().length()==0){
            group1.setQueryName("allSampleClinicalQuery");
            principalComponentAnalysisQueryDTO.setComparisonGroup(group1);
        }
        
        //get either predefined group or user selected
        else if(!principalComponentForm.getSelectedGroupName().equals("")||principalComponentForm.getSelectedGroupName().length()!=0){
            SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
            group1 = sampleBasedQueriesRetriever.getQuery(sessionId, principalComponentForm.getSelectedGroupName());
            if(group1!=null){
                /*DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
                diseaseCrit.setDisease(new DiseaseNameDE(principalComponentForm.getSelectedGroupName()));
                group1.setDiseaseOrGradeCrit(diseaseCrit);*/
                group1.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
                principalComponentAnalysisQueryDTO.setComparisonGroup(group1);
                
            }
            
        }
        
        //create GeneVectorPercentileDE
        if(principalComponentForm.getConstraintVariance().equalsIgnoreCase("constraintVariance") && principalComponentForm.getConstraintVariance()!=null){
            principalComponentAnalysisQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(principalComponentForm.getVariancePercentile()),Operator.GE));
        }
        
        /*create GeneIdentifierDEs by looking in the cache for 
        the specified GeneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.*/
        
        if(principalComponentForm.getGeneSetName()!= null && (!principalComponentForm.getGeneSetName().equals("") || principalComponentForm.getGeneSetName().length()!=0)){
            geneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.GeneIdentifierSet,principalComponentForm.getGeneSetName());
            if (geneIdentifierDECollection!=null){
                logger.debug("geneIdentifierDECollection was found in the cache");
                principalComponentAnalysisQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found in the cache");
            }
        }
        
        /*create CloneIdentifierDEs by looking in the cache for 
        the specified CloneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.
        The CloneIdentifierDEs will be set as "reporterDECollection" */
        if(principalComponentForm.getReporterSetName()!= null && (!principalComponentForm.getReporterSetName().equals("") || principalComponentForm.getReporterSetName().length()!=0)){
            cloneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.CloneProbeSetIdentifierSet,principalComponentForm.getReporterSetName());
            if (cloneIdentifierDECollection!=null){
                logger.debug("cloneIdentifierDECollection was found in the cache");
                principalComponentAnalysisQueryDTO.setReporterIdentifierDEs(cloneIdentifierDECollection);
            }
            else{
                logger.debug("cloneIdentifierDECollection could not be found in the cache");
            }
        }
        
        
        //create ArrayPlatformDE
        if(principalComponentForm.getArrayPlatform()!=null || principalComponentForm.getArrayPlatform().length()!=0){
            principalComponentAnalysisQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(principalComponentForm.getArrayPlatform()));
        }
        
        return principalComponentAnalysisQueryDTO;
        
    }

        
    
    
    
}