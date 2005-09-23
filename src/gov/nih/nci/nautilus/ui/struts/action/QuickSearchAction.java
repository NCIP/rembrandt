package gov.nih.nci.nautilus.ui.struts.action;


import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierDataController;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierSampleInfo;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KMPlotManager;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.nautilus.ui.graph.geneExpression.GeneExpressionGraphGenerator;
import gov.nih.nci.nautilus.ui.helper.KMDataSetHelper;
import gov.nih.nci.nautilus.ui.struts.form.KMDataSetForm;
import gov.nih.nci.nautilus.ui.struts.form.QuickSearchForm;
import gov.nih.nci.nautilus.ui.struts.form.UIFormValidator;

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
	static Logger logger = Logger.getLogger(QuickSearchAction.class);

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
		GeneExpressionGraphGenerator generator = new GeneExpressionGraphGenerator(
				form);
		if (generator.getErrors().size() > 0) {
			this.saveErrors(request, generator.getErrors());
			return mapping.findForward("badgraph");
		}
		generator.setRequestAttributes(request);
		generator.setSessionAttributes(request.getSession(true));
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
		String quickSearchVariableName = (String) request.getAttribute("quickSearchName");
		if (quickSearchVariableName != null) {
			quickSearchVariableName = quickSearchVariableName.toUpperCase();
		}
		String quickSearchType = (String) request.getAttribute("quickSearchType");
		if (quickSearchType == null) {
			quickSearchType = NautilusConstants.GENE_SYMBOL;
		}
		double upFold = kmForm.getUpFold();
		double downFold = kmForm.getDownFold();

		String kmplotType = (String) request.getAttribute("plotType");
		kmForm.setPlotType(kmplotType);
		KaplanMeierPlotContainer kmResultsContainer = null;
		KaplanMeierSampleInfo[] kmSampleInfos = {new KaplanMeierSampleInfo(0,0,0)};
		if (kmplotType.equals(NautilusConstants.GENE_EXP_KMPLOT)) {
			kmResultsContainer = performKMGeneExpressionQuery(quickSearchVariableName);
			if(kmResultsContainer!=null) {
				kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
			}
			kmForm.setUpOrAmplified("Up Regulated");
			kmForm.setDownOrDeleted("Down Regulated");
			kmForm.setGeneOrCytoband(kmResultsContainer.getGeneSymbol().getValue().toString());
			kmForm.setPlotVisible(true);
		} else if (kmplotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
			kmForm.setUpOrAmplified("Amplified");
			kmForm.setDownOrDeleted("Deleted");
			if(quickSearchType.equals(NautilusConstants.GENE_SYMBOL)){
			   kmResultsContainer = performKMCopyNumberQuery(quickSearchVariableName, quickSearchType);
			   String cytobandGeneSymbol = kmResultsContainer.getCytobandDE().getValue().toString();
			   kmForm.setGeneOrCytoband(quickSearchVariableName+"("+cytobandGeneSymbol+")");
			   kmForm.setPlotVisible(false); 
			 }else if(quickSearchType.equals(NautilusConstants.SNP_PROBESET_ID)){
				 kmResultsContainer = performKMCopyNumberQuery(quickSearchVariableName, quickSearchType);
				 kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(quickSearchVariableName);
				 kmForm.setGeneOrCytoband(quickSearchVariableName); 
				 kmForm.setPlotVisible(true); 
			 }

		}
		KaplanMeierDataController dataGenerator = new KaplanMeierDataController(upFold, downFold, quickSearchVariableName, kmSampleInfos, kmplotType);
		KaplanMeierStoredData storedData = dataGenerator.getStoredData();
		storedData.setId("KAPLAN");
		kmForm.setStoredData(storedData);
		kmForm = KMDataSetHelper.populateReporters(kmResultsContainer.getAssociatedReporters(), kmplotType, kmForm);
		kmForm.setSelectedDataset("KAPLAN");
		CacheManagerDelegate.getInstance().addToSessionCache(request.getSession().getId(),"MyKaplainMeierContainer",kmResultsContainer);
		CacheManagerDelegate.getInstance().addSessionGraphingData(request.getSession().getId(), storedData);
		

		/**
		 * Select the mapping to follow
		 */
		ActionErrors errors = new ActionErrors();
		if (errors.isEmpty()) {
			return mapping.findForward("kmplot");
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord",
					quickSearchType, quickSearchVariableName));
			this.saveErrors(request, errors);
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
		KaplanMeierPlotContainer kmResultsContainer = getKmResultsContainer(request.getSession().getId());
		if (kmResultsContainer != null	&& kmForm.getSelectedReporter() != null){
			if ((kmForm.getSelectedReporter().trim().length() > 0)) {
				if (kmplotType.equals(NautilusConstants.GENE_EXP_KMPLOT)) {
					if (kmForm.getSelectedReporter().equals(
							NautilusConstants.GRAPH_DEFAULT)) {
						kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
					} else {
						kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(kmForm.getSelectedReporter());
					}
				} else if (kmplotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
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
			CacheManagerDelegate.getInstance().addSessionGraphingData(request.getSession().getId(), storedData);
			kmForm = KMDataSetHelper.populateReporters(kmResultsContainer.getAssociatedReporters(), kmplotType, kmForm);
			kmForm.setDownFold(downRegulation);
			kmForm.setUpFold(upRegulation);
			return mapping.findForward("kmplot");
		}
		return mapping.findForward("badgraph");
	}
	
	private KaplanMeierPlotContainer getKmResultsContainer(String sessionId) {
		return (KaplanMeierPlotContainer)CacheManagerDelegate.getInstance().getObjectFromSessionCache(sessionId,"MyKaplainMeierContainer");
	}

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuickSearchForm qsForm = (QuickSearchForm) form;
		ActionErrors errors = new ActionErrors();
		if (qsForm.getQuickSearchType() != null
				&& qsForm.getQuickSearchType().equals(
						NautilusConstants.GENE_SYMBOL)) {
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
						NautilusConstants.GENE_EXP_KMPLOT);
				return mapping.findForward("kmplot");
			}
			if (chartType.equalsIgnoreCase("kapMaiPlotCN")) {
				logger.debug("user rquested SNP kapMaiPlotCN");
				request.setAttribute("quickSearchType", qsForm
						.getQuickSearchType());
				request.setAttribute("quickSearchName", qsForm
						.getQuickSearchName());
				request.setAttribute("plotType",
						NautilusConstants.COPY_NUMBER_KMPLOT);
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
			String geneSymbol) throws Exception {
		KMPlotManager kmPlotManager = new KMPlotManager();
		return (KaplanMeierPlotContainer) kmPlotManager
				.performKMGeneExpressionQuery(geneSymbol);
	}

	/**
	 * @return Returns the kmResultsContainer.
	 * @throws Exception
	 */
	private KaplanMeierPlotContainer performKMCopyNumberQuery(String name,
			String type) throws Exception {
		KMPlotManager kmPlotManager = new KMPlotManager();
		KaplanMeierPlotContainer kmResultsContainer = null;
		if (type.equals(NautilusConstants.GENE_SYMBOL)) {
			GeneIdentifierDE.GeneSymbol genesymbolDE = new GeneIdentifierDE.GeneSymbol(
					name);
			kmResultsContainer = (KaplanMeierPlotContainer) kmPlotManager
					.performKMCopyNumberQuery(genesymbolDE);

		}
		/**
		 * TODO:FOr 1.0 if(type.equals(NautilusConstants.CYTOBAND)){ CytobandDE
		 * cytobandDE = new CytobandDE(name); this.kmResultsContainer =
		 * kmPlotManager.performKMCopyNumberQuery(cytobandDE); }
		 */
		if (type.equals(NautilusConstants.SNP_PROBESET_ID)) {
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
