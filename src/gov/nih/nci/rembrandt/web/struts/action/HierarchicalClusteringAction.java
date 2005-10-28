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
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.rembrandt.web.struts.form.ClassComparisonForm;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.HierarchicalClusteringForm;
import gov.nih.nci.rembrandt.web.struts.form.PrincipalComponentForm;
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

public class HierarchicalClusteringAction extends DispatchAction {
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
        
        return mapping.findForward("hierarchicalClusteringSetup");
    }
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        String sessionId = request.getSession().getId();        
        
        return mapping.findForward("backToHierarchicalClustering");
    }
        
    
    
    
}