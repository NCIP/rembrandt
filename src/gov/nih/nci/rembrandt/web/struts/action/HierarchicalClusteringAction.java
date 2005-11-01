package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.web.struts.form.HierarchicalClusteringForm;

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