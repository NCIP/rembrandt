package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.enumeration.GeneExpressionDataSetType;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierDataController;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierSampleInfo;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot.KMPlotManager;
import gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.KMDataSetHelper;
import gov.nih.nci.rembrandt.web.struts.form.KMDataSetForm;
import gov.nih.nci.rembrandt.web.struts.form.QuickSearchForm;
import gov.nih.nci.rembrandt.web.struts.form.UIFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class QuickSearchAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(QuickSearchAction.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	/**
	 * Method execute
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
	private ActionForward doGeneExpPlot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		GeneExpressionGraphGenerator generator = new GeneExpressionGraphGenerator(
				form);
		if (generator.getErrors().size() > 0) {
			this.saveErrors(request, generator.getErrors());
			return mapping.findForward("badgraph");
		}
		generator.setRequestAttributes(request);
		generator.setSessionAttributes(request.getSession(true));
		return mapping.findForward("histogram");
		*/
		
		//		old school
		//GeneExpressionGraphGenerator generator = new GeneExpressionGraphGenerator(form);
		/*
		if (generator.getErrors().size() > 0) {
			this.saveErrors(request, generator.getErrors());
			return mapping.findForward("badgraph");
		}
		*/
		
		//we will handle the error elsewhere, so no need to findForward("badgraph")
		QuickSearchForm qsForm = (QuickSearchForm) form;
		//need this to pass the geneSymbol to the JSP
		request.setAttribute("geneSymbol", qsForm.getGeneSymbol());
		
        //generator.setRequestAttributes(request);
        //generator.setSessionAttributes(request.getSession(true));
        return mapping.findForward("histogram");
		
	}

	/***************************************************************************
	 * This method gathers the input parameters from the quickSearchForm and
	 * creates and forwards the request to the KMPlot jsp.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doKMPlot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KMDataSetForm kmForm = (KMDataSetForm) form;
		ActionErrors errors = new ActionErrors();
		String quickSearchVariableName = (String) request.getAttribute("quickSearchName");
		if (quickSearchVariableName != null) {
			quickSearchVariableName = quickSearchVariableName.toUpperCase();
		}
       
		String quickSearchType = (String) request.getAttribute("quickSearchType");
		if (quickSearchType == null) {
			quickSearchType = RembrandtConstants.GENE_SYMBOL;
		}
		double upFold = kmForm.getUpFold();
		double downFold = kmForm.getDownFold();

		String kmplotType = (String) request.getAttribute("plotType");
       	kmForm.setPlotType(kmplotType);
		KaplanMeierPlotContainer kmResultsContainer = null;
		KaplanMeierSampleInfo[] kmSampleInfos = {new KaplanMeierSampleInfo(0,0,0)};
		if (kmplotType.equals(CaIntegratorConstants.GENE_EXP_KMPLOT)) {			
            kmResultsContainer = performKMGeneExpressionQuery(quickSearchVariableName, GeneExpressionDataSetType.GeneExpressionDataSet);
           	if(kmResultsContainer!=null) {
				kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
				if(kmResultsContainer.getGeneSymbol()!= null){
					kmForm.setGeneOrCytoband(kmResultsContainer.getGeneSymbol().getValue().toString());
				}
			}
			kmForm.setUpOrAmplified("Up Regulated");
			kmForm.setDownOrDeleted("Down Regulated");			
			kmForm.setPlotVisible(true);
		} else if (kmplotType.equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
			kmForm.setUpOrAmplified("Amplified");
			kmForm.setDownOrDeleted("Deleted");
			if(quickSearchType.equals(RembrandtConstants.GENE_SYMBOL)){
			   kmResultsContainer = performKMCopyNumberQuery(quickSearchVariableName, quickSearchType);
			   if(kmResultsContainer != null  && kmResultsContainer.getCytobandDE()!= null){
				   String cytobandGeneSymbol = kmResultsContainer.getCytobandDE().getValue().toString();
				   kmForm.setGeneOrCytoband(quickSearchVariableName+"("+cytobandGeneSymbol+")");
				   kmForm.setPlotVisible(false); 
			   }
			 }else if(quickSearchType.equals(RembrandtConstants.SNP_PROBESET_ID)){
				 kmResultsContainer = performKMCopyNumberQuery(quickSearchVariableName, quickSearchType);
				 if(kmResultsContainer != null){
				 kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(quickSearchVariableName);
				 kmForm.setGeneOrCytoband(quickSearchVariableName); 
				 kmForm.setPlotVisible(true); 
				 }
			 }

		}
		if(kmSampleInfos != null && kmSampleInfos.length <= 1 ){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord",
					quickSearchType, quickSearchVariableName));
			this.saveErrors(request, errors);
		}
		else{
			KaplanMeierDataController dataGenerator = new KaplanMeierDataController(upFold, downFold, quickSearchVariableName, kmSampleInfos, kmplotType);
			KaplanMeierStoredData storedData = dataGenerator.getStoredData();
			storedData.setId("KAPLAN");
			kmForm.setStoredData(storedData);
            kmForm = KMDataSetHelper.populateReporters(kmResultsContainer.getAssociatedReporters(), kmplotType, kmForm);
			kmForm.setSelectedDataset("KAPLAN");
			presentationTierCache.addToSessionCache(request.getSession().getId(),"MyKaplainMeierContainer",kmResultsContainer);
			presentationTierCache.addSessionGraphingData(request.getSession().getId(), storedData);
		
		}
		/**
		 * Select the mapping to follow
		 */
		
		if (errors.isEmpty()) {
			return mapping.findForward("kmplot");
		} else {
			return mapping.findForward("badgraph");
		}
	}

	public ActionForward redrawKMPlot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KMDataSetForm kmForm = (KMDataSetForm) form;
		KaplanMeierSampleInfo[] kmSampleInfos = null;
		// kmForm.setReporters(populateReporters());
		String kmplotType = kmForm.getPlotType();
		double upRegulation = kmForm.getUpFold();
		double downRegulation = kmForm.getDownFold();
        String algorithm = kmForm.getAlgorithm(); 
        KaplanMeierPlotContainer kmResultsContainer = null;
        if(algorithm.equals("unified")){
            kmResultsContainer = performKMGeneExpressionQuery(kmForm.getGeneOrCytoband(), GeneExpressionDataSetType.UnifiedGeneExpressionDataSet);
            kmForm.setSelectedReporter("");
        }
        else{
            kmResultsContainer = getKmResultsContainer(request.getSession().getId());
        }
		if (kmResultsContainer != null	&& kmForm.getSelectedReporter() != null){
			if ((kmForm.getSelectedReporter().trim().length() > 0)) {                
				if (kmplotType.equals(CaIntegratorConstants.GENE_EXP_KMPLOT )) {
					if (kmForm.getSelectedReporter().equals(
							CaIntegratorConstants.GRAPH_DEFAULT)) {
						kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
					} else {
						kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(kmForm.getSelectedReporter());
					}
				} else if (kmplotType.equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
					kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(kmForm.getSelectedReporter());
				}
				kmForm.setPlotVisible(true);
			} else { // empty graph
				KaplanMeierSampleInfo[] km = {new KaplanMeierSampleInfo(0, 0, 0)};
				kmSampleInfos = km;
				kmForm.setPlotVisible(false);
			}
			KaplanMeierDataController dataGenerator = new KaplanMeierDataController(upRegulation, downRegulation, kmForm.getGeneOrCytoband(), kmSampleInfos, kmplotType);
			KaplanMeierStoredData storedData = dataGenerator.getStoredData();
			storedData.setId("KAPLAN");
			kmForm.setStoredData(storedData);
			kmForm = KMDataSetHelper.populateReporters(kmResultsContainer.getAssociatedReporters(), kmplotType, kmForm);
			kmForm.setSelectedDataset("KAPLAN");
			presentationTierCache.addSessionGraphingData(request.getSession().getId(), storedData);
			kmForm = KMDataSetHelper.populateReporters(kmResultsContainer.getAssociatedReporters(), kmplotType, kmForm);
			kmForm.setDownFold(downRegulation);
			kmForm.setUpFold(upRegulation);
			return mapping.findForward("kmplot");
		}
		return mapping.findForward("badgraph");
	}
	
	private KaplanMeierPlotContainer getKmResultsContainer(String sessionId) {
		return (KaplanMeierPlotContainer)presentationTierCache.getObjectFromSessionCache(sessionId,"MyKaplainMeierContainer");
	}

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuickSearchForm qsForm = (QuickSearchForm) form;
		ActionErrors errors = new ActionErrors();
		if (qsForm.getQuickSearchType() != null
				&& qsForm.getQuickSearchType().equals(
						RembrandtConstants.GENE_SYMBOL)) {
			errors = UIFormValidator.validateGeneSymbol(qsForm, errors);
		}
		if (errors.isEmpty()) {
			String chartType = qsForm.getPlot();

			if (chartType.equalsIgnoreCase("kapMaiPlotGE")) {
				logger.debug("user requested geneExp kapMai w/ genesymbol");
				request.setAttribute("quickSearchName", qsForm
						.getQuickSearchName());
				request.setAttribute("quickSearchType", qsForm
						.getQuickSearchType());
				request.setAttribute("plotType",
						CaIntegratorConstants.GENE_EXP_KMPLOT);
				return mapping.findForward("kmplot");
			}
			if (chartType.equalsIgnoreCase("kapMaiPlotCN")) {
				logger.debug("user rquested SNP kapMaiPlotCN");
				request.setAttribute("quickSearchType", qsForm
						.getQuickSearchType());
				request.setAttribute("quickSearchName", qsForm
						.getQuickSearchName());
				request.setAttribute("plotType",
						CaIntegratorConstants.COPY_NUMBER_KMPLOT);
				return mapping.findForward("kmplot");
			}

			else if (chartType.equalsIgnoreCase("geneExpPlot")) {
				try {
					logger.debug("user has requested geneExpPlot");
					return doGeneExpPlot(mapping, qsForm, request, response);
				} catch (Exception e) {
					logger.error("Gene Expression Plot Flopped");
					logger.error(e);
					return mapping.findForward("error");
				}
			}

		}
		this.saveErrors(request, errors);
		return mapping.findForward("mismatch");
	}

	/**
	 * @return Returns the kmResultsContainer.
	 * @throws Exception
	 */
	private KaplanMeierPlotContainer performKMGeneExpressionQuery(
			String geneSymbol,GeneExpressionDataSetType geneExpressionDataSetType) throws Exception {
		KMPlotManager kmPlotManager = new KMPlotManager();
		KaplanMeierPlotContainer kaplanMeierPlotContainer = null;
            
		switch(geneExpressionDataSetType){
		case GeneExpressionDataSet:
		default:
			
            kaplanMeierPlotContainer = (KaplanMeierPlotContainer) kmPlotManager
			.performKMGeneExpressionQuery(geneSymbol);
			break;
		case UnifiedGeneExpressionDataSet:
			kaplanMeierPlotContainer = (KaplanMeierPlotContainer) kmPlotManager
			.performUnifiedKMGeneExpressionQuery(geneSymbol);
			break;
		}
		return kaplanMeierPlotContainer;
	}

	/**
	 * @return Returns the kmResultsContainer.
	 * @throws Exception
	 */
	private KaplanMeierPlotContainer performKMCopyNumberQuery(String name,
			String type) throws Exception {
		KMPlotManager kmPlotManager = new KMPlotManager();
		KaplanMeierPlotContainer kmResultsContainer = null;
		if (type.equals(RembrandtConstants.GENE_SYMBOL)) {
			GeneIdentifierDE.GeneSymbol genesymbolDE = new GeneIdentifierDE.GeneSymbol(
					name);
			kmResultsContainer = (KaplanMeierPlotContainer) kmPlotManager
					.performKMCopyNumberQuery(genesymbolDE);

		}
		/**
		 * TODO:FOr 1.0 if(type.equals(RembrandtConstants.CYTOBAND)){ CytobandDE
		 * cytobandDE = new CytobandDE(name); this.kmResultsContainer =
		 * kmPlotManager.performKMCopyNumberQuery(cytobandDE); }
		 */
		if (type.equals(RembrandtConstants.SNP_PROBESET_ID)) {
			SNPIdentifierDE.SNPProbeSet snpDE = new SNPIdentifierDE.SNPProbeSet(
					name);
			kmResultsContainer = (KaplanMeierPlotContainer) kmPlotManager
					.performKMCopyNumberQuery(snpDE);

		}
		return kmResultsContainer;
	}

	public ActionForward redrawKaplanMeierGeneExpressionPlot(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * This will need to be changes to reflect the new distinction between
		 * copy number and gene expression km plots
		 */
		return mapping.findForward("kmplot");
	}

}
