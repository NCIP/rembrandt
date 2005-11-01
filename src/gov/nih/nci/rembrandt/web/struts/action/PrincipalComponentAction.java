package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.rembrandt.web.struts.form.PrincipalComponentForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PrincipalComponentAction extends DispatchAction {
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
        
        return mapping.findForward("principalComponentSetup");
    }
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        PrincipalComponentForm principalComponentForm = (PrincipalComponentForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        String sessionId = request.getSession().getId();        
        SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
        principalComponentForm.setExistingGroupsList(sampleBasedQueriesRetriever.getAllPredefinedAndSampleSetNames(sessionId));
        
        
        return mapping.findForward("backToPrincipalComponent");
    }
        
    
    
    
}