package gov.nih.nci.nautilus.ui.struts.action;

import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.AllGenesCriteria;
import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper;
import gov.nih.nci.nautilus.ui.struts.form.ComparativeGenomicForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComparativeGenomicAction extends LookupDispatchAction {
    private static Logger logger = Logger.getLogger(ComparativeGenomicAction.class);
    
   
    /**
     * Method submitAllGenes
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
    
    //If this is an All Genes submit
    public ActionForward submitAllGenes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        
        request.getSession().setAttribute("currentPage", "0");
		request.getSession().removeAttribute("currentPage2");
		
		ComparativeGenomicForm comparativeGenomicForm = (ComparativeGenomicForm) form;
        //set all Genes query and give copyNumber default value
		comparativeGenomicForm.setCnAmplified("2");
         
		logger.debug("This is an All Genes cgh Submital");
		return mapping.findForward("showAllGenes");
    }
    
    /**
     * Method submitStandard
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
    
    //If this is a standard submit
    public ActionForward submitStandard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        
        request.getSession().setAttribute("currentPage", "0");
		request.getSession().removeAttribute("currentPage2");
		
		ComparativeGenomicForm comparativeGenomicForm = (ComparativeGenomicForm) form;
		////set standard query and clear copyNumber default value
		comparativeGenomicForm.setCopyNumber("");
		comparativeGenomicForm.setCnAmplified("");
		
		logger.debug("This is an Standard cgh Submital");
		return mapping.findForward("backToCGH");
    }
    
    
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
    
     //If this is a Submittal do the following
    public ActionForward submittal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.getSession().setAttribute("currentPage", "0");
        request.getSession().removeAttribute("currentPage2");
        ComparativeGenomicForm comparativeGenomicForm = (ComparativeGenomicForm) form;
        
        logger.debug("This is a Comparative Genomic Submittal");
        //Create Query Objects
        ComparativeGenomicQuery cghQuery = createCGHQuery(comparativeGenomicForm);
        //This is required as struts resets the form.  It is later added back to the request
        request.setAttribute("previewForm", comparativeGenomicForm.cloneMe());
       
        
            try {
                //Set query in Session.
                if (!cghQuery.isEmpty()) {
                    // Get Hashmap from session if available
                    SessionQueryBag queryCollection = (SessionQueryBag) request
                            .getSession().getAttribute(
                                    NautilusConstants.SESSION_QUERY_BAG_KEY);
                    if (queryCollection == null) {
                        queryCollection = new SessionQueryBag();
                    }
                    queryCollection.putQuery(cghQuery);
                    request.getSession().setAttribute(
                            NautilusConstants.SESSION_QUERY_BAG_KEY, queryCollection);

                } else {

                    ActionErrors errors = new ActionErrors();
                    errors
                            .add(
                                    ActionErrors.GLOBAL_ERROR,
                                    new ActionError(
                                            "gov.nih.nci.nautilus.ui.struts.form.query.cgh.error"));
                    this.saveErrors(request, errors);
                    return mapping.findForward("backToCGH");
                }
            }
            catch (Exception e) {
               logger.error(e);
            }
            return mapping.findForward("advanceSearchMenu");
        }
  
    
    /**
     * Method preview
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
    public ActionForward preview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.getSession().setAttribute("currentPage", "0");
        request.getSession().removeAttribute("currentPage2");
        ComparativeGenomicForm comparativeGenomicForm = (ComparativeGenomicForm) form;
        logger.debug("This is a Comparative Genomic Preview");
        //Create Query Objects
        ComparativeGenomicQuery cghQuery = createCGHQuery(comparativeGenomicForm);
        //This is required as struts resets the form.  It is later added back to the request
        request.setAttribute("previewForm", comparativeGenomicForm.cloneMe());
        CompoundQuery compoundQuery = new CompoundQuery(cghQuery);
        compoundQuery.setQueryName(NautilusConstants.PREVIEW_RESULTS);
        logger.debug("Setting query name to:"+compoundQuery.getQueryName());
        compoundQuery.setAssociatedView(ViewFactory
                .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
        logger.debug("Associated View for the Preview:"+compoundQuery.getAssociatedView().getClass());
	    //Save the sessionId that this preview query is associated with
        compoundQuery.setSessionId(request.getSession().getId());
        //Generate the reportXML for the preview.  It will be stored in the session
	    //cache for later retrieval
        ReportGeneratorHelper reportHelper = new ReportGeneratorHelper(compoundQuery);
        return mapping.findForward("previewReport");
    }
            
    
    private ComparativeGenomicQuery createCGHQuery(ComparativeGenomicForm comparativeGenomicForm){
        //Create Query Objects
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) QueryManager
                .createQuery(QueryType.CGH_QUERY_TYPE);
        cghQuery.setQueryName(comparativeGenomicForm.getQueryName());
        String thisView = comparativeGenomicForm.getResultView();
        // Change this code later to get view type directly from Form !!
        if (thisView.equalsIgnoreCase("sample")) {
            cghQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.CLINICAL_VIEW));
        } else if (thisView.equalsIgnoreCase("gene")) {
            cghQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        }

        // set Disease criteria
        DiseaseOrGradeCriteria diseaseOrGradeCriteria = comparativeGenomicForm
                .getDiseaseOrGradeCriteria();
        if (!diseaseOrGradeCriteria.isEmpty()) {
            cghQuery.setDiseaseOrGradeCrit(diseaseOrGradeCriteria);
        }
       
        // Set gene criteria
        GeneIDCriteria geneIDCrit = comparativeGenomicForm.getGeneIDCriteria();
        if (!geneIDCrit.isEmpty()) {
            cghQuery.setGeneIDCrit(geneIDCrit);
        }
        
        // Set all genes criteria
        AllGenesCriteria allGenesCrit = comparativeGenomicForm.getAllGenesCriteria();
		if (!allGenesCrit.isEmpty())
		    cghQuery.setAllGenesCrit(allGenesCrit);
        
        SampleCriteria sampleIDCrit = comparativeGenomicForm.getSampleCriteria();
		if (!sampleIDCrit.isEmpty())
		    cghQuery.setSampleIDCrit(sampleIDCrit);

        // set copy number criteria
        CopyNumberCriteria CopyNumberCrit = comparativeGenomicForm
                .getCopyNumberCriteria();
        if (!CopyNumberCrit.isEmpty()) {
            cghQuery.setCopyNumberCrit(CopyNumberCrit);
        }

        // set region criteria
        RegionCriteria regionCrit = comparativeGenomicForm.getRegionCriteria();
        if (!regionCrit.isEmpty()) {
            cghQuery.setRegionCrit(regionCrit);
        }

        // set clone/probe criteria
        CloneOrProbeIDCriteria cloneOrProbeIDCriteria = comparativeGenomicForm
                .getCloneOrProbeIDCriteria();
        if (!cloneOrProbeIDCriteria.isEmpty()) {
            cghQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
        }

        // set snp criteria
        SNPCriteria snpCrit = comparativeGenomicForm.getSNPCriteria();
        if (!snpCrit.isEmpty()) {
            cghQuery.setSNPCrit(snpCrit);
        }

        // set allele criteria
        AlleleFrequencyCriteria alleleFrequencyCriteria = comparativeGenomicForm
                .getAlleleFrequencyCriteria();
        if (!alleleFrequencyCriteria.isEmpty()) {
            cghQuery.setAlleleFrequencyCrit(alleleFrequencyCriteria);
        }

        AssayPlatformCriteria assayPlatformCriteria = comparativeGenomicForm
                .getAssayPlatformCriteria();
        if (!assayPlatformCriteria.isEmpty()) {
            cghQuery.setAssayPlatformCrit(assayPlatformCriteria);
        }
        return cghQuery;
    }
    
    protected Map getKeyMethodMap() {
		 
      HashMap map = new HashMap();
      
      //Submit Query Button using comparative genomic submittal method
      map.put("buttons_tile.submittalButton", "submittal");
      
      //Preview Query Button using comparative genomic preview method
      map.put("buttons_tile.previewButton", "preview");
      
      //Submit All Genes Button using cgh submitAllGenes method
      map.put("buttons_tile.submitAllGenes", "submitAllGenes");
      
      //Submit Standard Button using cgh expression submitStandard method
      map.put("buttons_tile.submitStandard", "submitStandard");
      
      return map;
      
      }
    
    
}