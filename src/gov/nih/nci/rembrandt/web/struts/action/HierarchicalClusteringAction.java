package gov.nih.nci.rembrandt.web.struts.action;

import java.util.Collection;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ClusterTypeDE;
import gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.LinkageMethodTypeDE;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.EnumCaseChecker;
import gov.nih.nci.rembrandt.web.struts.form.HierarchicalClusteringForm;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.DistanceMatrixType;
import gov.nih.nci.caintegrator.enumeration.ClusterByType;
import gov.nih.nci.caintegrator.enumeration.LinkageMethodType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class HierarchicalClusteringAction extends DispatchAction {
    private Logger logger = Logger.getLogger(HierarchicalClusteringAction.class);
    private UserCredentials credentials;
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;
    private Collection<CloneIdentifierDE> cloneIdentifierDECollection;
    private SessionCriteriaBag sessionCriteriaBag;
    /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
    private DistanceMatrixType ERROR_DISTANCE_MATRIX_TYPE = DistanceMatrixType.Correlation;
    private LinkageMethodType ERROR_LINKAGE_METHOD_TYPE = LinkageMethodType.Average;
    private ClusterByType ERROR_CLUSTER_BY_TYPE = ClusterByType.Samples;
   
    
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
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        logger.debug("Selected Distance Matrix in HttpServletRequest: " + request.getParameter("distanceMatrix"));
        String sessionId = request.getSession().getId();
        HierarchicalClusteringQueryDTO hierarchicalClusteringQueryDTO = createHierarchicalClusteringQueryDTO(hierarchicalClusteringForm,sessionId); 
        /*Create the InstituteDEs using credentials from the local session.
         * May want to put these in the cache eventually.
         */
        
        if(request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS)!=null){
            credentials = (UserCredentials) request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
            hierarchicalClusteringQueryDTO.setInstitutionDEs(credentials.getInstitutes());
        }
        
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createHCAFinding(hierarchicalClusteringQueryDTO,sessionId,hierarchicalClusteringQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        
        return mapping.findForward("viewResults");
    }
  
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        String sessionId = request.getSession().getId();        
        
        return mapping.findForward("backToHierarchicalClustering");
    }
        
    private HierarchicalClusteringQueryDTO createHierarchicalClusteringQueryDTO(HierarchicalClusteringForm hierarchicalClusteringForm, String sessionId) {
        HierarchicalClusteringQueryDTO hierarchicalClusteringQueryDTO = (HierarchicalClusteringQueryDTO)ApplicationFactory.newQueryDTO(QueryType.HC_QUERY);
        
        hierarchicalClusteringQueryDTO.setQueryName(hierarchicalClusteringForm.getAnalysisResultName());
        sessionCriteriaBag = presentationTierCache.getSessionCriteriaBag(sessionId);
        
        // create GeneVectorPercentileDE
        if(hierarchicalClusteringForm.getConstraintVariance().equalsIgnoreCase("constraintVariance") && hierarchicalClusteringForm.getConstraintVariance()!=null){
            hierarchicalClusteringQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(hierarchicalClusteringForm.getVariancePercentile()),Operator.GE));
        }
        
        /*create GeneIdentifierDEs by looking in the cache for 
        the specified GeneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.*/
        
        if(hierarchicalClusteringForm.getGeneSetName()!=null || hierarchicalClusteringForm.getGeneSetName().length()!=0){
            geneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.GeneIdentifierSet,hierarchicalClusteringForm.getGeneSetName());
            if (geneIdentifierDECollection!=null){
                logger.debug("geneIdentifierDECollection was found in the cache");
                //hierarchicalClusteringQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found in the cache");
            }
        }
        
        /*create CloneIdentifierDEs by looking in the cache for 
        the specified CloneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.
        The CloneIdentifierDEs will be set as "reporterDECollection" */
        if(hierarchicalClusteringForm.getReporterSetName()!=null || hierarchicalClusteringForm.getReporterSetName().length()!=0){
            cloneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.CloneProbeSetIdentifierSet,hierarchicalClusteringForm.getReporterSetName());
            if (cloneIdentifierDECollection!=null){
                logger.debug("cloneIdentifierDECollection was found in the cache");
               //hierarchicalClusteringQueryDTO.setReporterIdentifierDEs(cloneIdentifierDECollection);
            }
            else{
                logger.debug("cloneIdentifierDECollection could not be found in the cache");
            }
        }
        
        // create distance matrix DEs
        logger.debug("Selected DistanceMatrixType in ActionForm:"+hierarchicalClusteringForm.getDistanceMatrix());
        /*
         * This code is here to deal with an observed problem with the changing 
         * of case in request parameters.  See the class EnumCaseChecker for 
         * enlightenment.
         */
        DistanceMatrixType distanceMatrixType = null;
        String myTypeString = EnumCaseChecker.getEnumTypeName(hierarchicalClusteringForm.getDistanceMatrix(),DistanceMatrixType.values());
        if(myTypeString!=null) {
        	distanceMatrixType = DistanceMatrixType.valueOf(myTypeString);
        }else {
        	logger.error("Invalid DistanceMatrixType value given in request");
        	logger.error("Selected DistanceMatrixType value = "+hierarchicalClusteringForm.getDistanceMatrix());
        	logger.error("Using the safety DistanceMatrixType value = "+ERROR_DISTANCE_MATRIX_TYPE);
        	distanceMatrixType = ERROR_DISTANCE_MATRIX_TYPE;
        }
        DistanceMatrixTypeDE distanceMatrixTypeDE = new DistanceMatrixTypeDE(distanceMatrixType);
        hierarchicalClusteringQueryDTO.setDistanceMatrixTypeDE(distanceMatrixTypeDE);
        
        // create linkageMethodDEs
        /*
         * This code is here to deal with an observed problem with the changing 
         * of case in request parameters.  See the class EnumCaseChecker for 
         * enlightenment.
         */
        LinkageMethodType linkageMethodType = null;
        String linkageMethodTypeName = EnumCaseChecker.getEnumTypeName(hierarchicalClusteringForm.getLinkageMethod(),LinkageMethodType.values());
        if(linkageMethodTypeName!=null) {
        	linkageMethodType = LinkageMethodType.valueOf(linkageMethodTypeName);
        }else {
        	logger.error("Invalid LinkageMethodType value given in request");
        	logger.error("Selected LinkageMethodType value = "+hierarchicalClusteringForm.getLinkageMethod());
        	logger.error("Using the safety LinkageMethodType value = "+ERROR_LINKAGE_METHOD_TYPE);
        	linkageMethodType = ERROR_LINKAGE_METHOD_TYPE;
        }
        LinkageMethodTypeDE linkageMethodTypeDE = new LinkageMethodTypeDE(linkageMethodType);
        hierarchicalClusteringQueryDTO.setLinkageMethodTypeDE(linkageMethodTypeDE);
        
        //create ArrayPlatformDE
        if(hierarchicalClusteringForm.getArrayPlatform()!=null || hierarchicalClusteringForm.getArrayPlatform().length()!=0){
            hierarchicalClusteringQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(hierarchicalClusteringForm.getArrayPlatform()));
        }
        
        if(!hierarchicalClusteringForm.getClusterBy().equals("")){
        	 /*
             * This code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumCaseChecker for 
             * enlightenment.
             */
        	ClusterByType clusterByType = null;
            String clusterByTypeName = EnumCaseChecker.getEnumTypeName(hierarchicalClusteringForm.getClusterBy(),ClusterByType.values());
            if(clusterByTypeName!=null) {
            	clusterByType = ClusterByType.valueOf(clusterByTypeName);
            }else {
            	logger.error("Invalid ClusterByType value given in request");
            	logger.error("Selected ClusterByType value = "+hierarchicalClusteringForm.getClusterBy());
            	logger.error("Using the safety ClusterByType value = "+ERROR_CLUSTER_BY_TYPE);
            	clusterByType = ERROR_CLUSTER_BY_TYPE;
            }
        	hierarchicalClusteringQueryDTO.setClusterTypeDE(new ClusterTypeDE(clusterByType));
        }
        
        return hierarchicalClusteringQueryDTO;
    }

    
    
}