package gov.nih.nci.nautilus.ui.struts.action;

import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.criteria.GeneOntologyCriteria;
import gov.nih.nci.nautilus.criteria.PathwayCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.GeneExpressionForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

public class GeneExpressionAction extends LookupDispatchAction {
    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
	
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
		GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;

		// Create Query Objects
		GeneExpressionQuery geneExpQuery = createGeneExpressionQuery(geneExpressionForm);

		
		    logger.debug("This is a Gene Expression Submital");
		    try {
				//Set query in Session.
				if (!geneExpQuery.isEmpty()) {
					// Get SessionQueryBag from session if available
					SessionQueryBag queryCollection = (SessionQueryBag) request
							.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
					if (queryCollection == null) {
					    logger.debug("SessionQueryBag class in Session is empty");
						queryCollection = new SessionQueryBag();
					}
					queryCollection.putQuery(geneExpQuery);
                    request.getSession().setAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY,queryCollection);
                    
             	} else {
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
											"gov.nih.nci.nautilus.ui.struts.form.query.geneexp.error"));
					this.saveErrors(request, errors);
					return mapping.findForward("backToGeneExp");
				}
			}// end of try
			catch (Exception e) {
				logger.error("Exception in GeneExpressionAction.java");
				logger.error(e);
			}
			return mapping.findForward("advanceSearchMenu");
		}
	
	

	//	If this is a Preview Report do the following	
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession().setAttribute("currentPage", "0");
		request.getSession().removeAttribute("currentPage2");
		GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;

		logger.debug("This is a Gene Expression Preview");
		// Create Query Objects
		GeneExpressionQuery geneExpQuery = createGeneExpressionQuery(geneExpressionForm);
	    request.setAttribute("previewForm",geneExpressionForm.cloneMe());
        logger.debug("This is a Preview Report");
	    CompoundQuery compoundQuery = new CompoundQuery(geneExpQuery);
        compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        SessionQueryBag collection = new SessionQueryBag();
        collection.setCompoundQuery(compoundQuery);
        request.setAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY, collection);
		return mapping.findForward("previewReport");
	}
	
	
	
	private GeneExpressionQuery createGeneExpressionQuery(GeneExpressionForm geneExpressionForm){
	    GeneExpressionQuery geneExpQuery = (GeneExpressionQuery)QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	    geneExpQuery.setQueryName(geneExpressionForm.getQueryName());
		// Change this code later to get view type directly from Form !!
	    String thisView = geneExpressionForm.getResultView();
		if (thisView.equalsIgnoreCase("sample")) {
			geneExpQuery.setAssociatedView(ViewFactory
					.newView(ViewType.CLINICAL_VIEW));
		} else if (thisView.equalsIgnoreCase("gene")) {
			geneExpQuery.setAssociatedView(ViewFactory
					.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
		}
		// Set gene criteria
		GeneIDCriteria geneIDCrit = geneExpressionForm.getGeneIDCriteria();
		if (!geneIDCrit.isEmpty())
			geneExpQuery.setGeneIDCrit(geneIDCrit);
		SampleCriteria sampleIDCrit = geneExpressionForm.getSampleCriteria();
		if (!sampleIDCrit.isEmpty())
			geneExpQuery.setSampleIDCrit(sampleIDCrit);
		FoldChangeCriteria foldChangeCrit = geneExpressionForm
				.getFoldChangeCriteria();
		if (!foldChangeCrit.isEmpty())
			geneExpQuery.setFoldChgCrit(foldChangeCrit);
		RegionCriteria regionCrit = geneExpressionForm.getRegionCriteria();
		if (!regionCrit.isEmpty())
			geneExpQuery.setRegionCrit(regionCrit);
		DiseaseOrGradeCriteria diseaseOrGradeCriteria = geneExpressionForm
				.getDiseaseOrGradeCriteria();
		if (!diseaseOrGradeCriteria.isEmpty())
			geneExpQuery.setDiseaseOrGradeCrit(diseaseOrGradeCriteria);
		CloneOrProbeIDCriteria cloneOrProbeIDCriteria = geneExpressionForm
				.getCloneOrProbeIDCriteria();
		if (!cloneOrProbeIDCriteria.isEmpty())
			geneExpQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
		GeneOntologyCriteria geneOntologyCriteria = geneExpressionForm
				.getGeneOntologyCriteria();
		if (!geneOntologyCriteria.isEmpty())
			geneExpQuery.setGeneOntologyCrit(geneOntologyCriteria);
		PathwayCriteria pathwayCriteria = geneExpressionForm
				.getPathwayCriteria();
		if (!pathwayCriteria.isEmpty())
			geneExpQuery.setPathwayCrit(pathwayCriteria);
		ArrayPlatformCriteria arrayPlatformCriteria = geneExpressionForm
				.getArrayPlatformCriteria();
		if (!arrayPlatformCriteria.isEmpty())
			geneExpQuery.setArrayPlatformCrit(arrayPlatformCriteria);
	    return geneExpQuery;
	}
	
	protected Map getKeyMethodMap() {
		 
       HashMap map = new HashMap();
       
       //Submit Query Button using gene expression submittal method
       map.put("submittalButton", "submittal");
       
       //Preview Query Button using gene expression preview method
       map.put("previewButton", "preview");
       
       return map;
       
       }
}