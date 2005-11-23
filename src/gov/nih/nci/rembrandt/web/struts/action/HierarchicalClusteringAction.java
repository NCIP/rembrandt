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
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.HierarchicalClusteringForm;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.DistanceMatrixType;
import gov.nih.nci.caintegrator.enumeration.ClusterByType;
import gov.nih.nci.caintegrator.enumeration.LinkageMethodType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.service.findings.Finding;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class HierarchicalClusteringAction extends DispatchAction {
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
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        String sessionId = request.getSession().getId();
        HierarchicalClusteringQueryDTO hierarchicalClusteringQueryDTO = createHierarchicalClusteringQueryDTO(hierarchicalClusteringForm,sessionId); 
        
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
        DistanceMatrixTypeDE distanceMatrixTypeDE = new DistanceMatrixTypeDE(DistanceMatrixType.valueOf(DistanceMatrixType.class,hierarchicalClusteringForm.getDistanceMatrix()));
        hierarchicalClusteringQueryDTO.setDistanceMatrixTypeDE(distanceMatrixTypeDE);
        
        // create linkageMethodDEs
        LinkageMethodTypeDE linkageMethodTypeDE = new LinkageMethodTypeDE(LinkageMethodType.valueOf(LinkageMethodType.class,hierarchicalClusteringForm.getLinkageMethod()));
        hierarchicalClusteringQueryDTO.setLinkageMethodTypeDE(linkageMethodTypeDE);
        
        //create ArrayPlatformDE
        if(hierarchicalClusteringForm.getArrayPlatform()!=null || hierarchicalClusteringForm.getArrayPlatform().length()!=0){
            hierarchicalClusteringQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(hierarchicalClusteringForm.getArrayPlatform()));
        }
        
        if(!hierarchicalClusteringForm.getClusterBy().equals("")){
        	hierarchicalClusteringQueryDTO.setClusterTypeDE(new ClusterTypeDE(ClusterByType.valueOf(ClusterByType.class,hierarchicalClusteringForm.getClusterBy())));
        }
        
        return hierarchicalClusteringQueryDTO;
    }

    
    
}