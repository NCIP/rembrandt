package gov.nih.nci.nautilus.ui.struts.action;

import java.util.List;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KMPlotManager;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.nautilus.ui.graph.geneExpression.GeneExpressionGraphGenerator;
import gov.nih.nci.nautilus.ui.graph.kaplanMeier.KMGraphGenerator;
import gov.nih.nci.nautilus.ui.graph.kaplanMeier.KMSampleInfo;
import gov.nih.nci.nautilus.ui.struts.form.KMDataSetForm;
import gov.nih.nci.nautilus.ui.struts.form.QuickSearchForm;
import gov.nih.nci.nautilus.ui.struts.form.UIFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class QuickSearchAction extends DispatchAction {
	static Logger logger = Logger.getLogger(QuickSearchAction.class);
	private KaplanMeierPlotContainer kmResultsContainer = null;
	private String chartType;
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
		} else {
			generator.setRequestAttributes(request);
			generator.setSessionAttributes(request.getSession(true));
			return mapping.findForward("histogram");
		}

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
		kmForm.setGeneSymbol((String) request.getAttribute("geneSymbol"));
		kmForm.setReporters(populateReporters());
		KMPlotManager kmPlotManager = new KMPlotManager();
		kmResultsContainer = (KaplanMeierPlotContainer) kmPlotManager.performKMGeneExpressionQuery((String) request.getAttribute("geneSymbol"));
		KMSampleInfo[] kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
		KMGraphGenerator generator = new KMGraphGenerator(kmForm.getUpFold(),
				kmForm.getDownFold(), (String) request
						.getAttribute("geneSymbol"), kmSampleInfos);
		if (generator.getMyActionErrors().size() > 0) {
			this.saveErrors(request, generator.getMyActionErrors());
			return mapping.findForward("badgraph");
		}
		kmForm.setCensorDataset(generator.getCensorDataseries());
		kmForm.setLineDataset(generator.getLineDataseries());

		return mapping.findForward("kmplot");
	}

	public ActionForward redrawKMPlot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KMDataSetForm kmForm = (KMDataSetForm) form;
		KMSampleInfo[] kmSampleInfos = null;
		if( kmResultsContainer != null && kmForm.getSelectedReporter() != null){
			if(kmForm.getSelectedReporter().equals(NautilusConstants.GRAPH_DEFAULT)){
				kmSampleInfos = kmResultsContainer.getSummaryKMPlotSamples();
			}
			else{
				kmSampleInfos = kmResultsContainer.getKMPlotSamplesForReporter(kmForm.getSelectedReporter());
			}
			KMGraphGenerator generator = new KMGraphGenerator(kmForm.getUpFold(),
					kmForm.getDownFold(), kmForm.getGeneSymbol(),kmSampleInfos);
			if (generator.getMyActionErrors().size() > 0) {
				this.saveErrors(request, generator.getMyActionErrors());
				return mapping.findForward("badgraph");
			} else {
				kmForm.setCensorDataset(generator.getCensorDataseries());
				kmForm.setLineDataset(generator.getLineDataseries());
			}
			return mapping.findForward("kmplot");
		}
		return mapping.findForward("badgraph");
	}

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuickSearchForm qsForm = (QuickSearchForm) form;
		ActionErrors errors = new ActionErrors();
		errors = UIFormValidator.validateGeneSymbol(qsForm, errors);
		if(errors.isEmpty()){
		chartType = qsForm.getPlot();
		
			if (chartType.equalsIgnoreCase("kapMaiPlotGE")) {
			    System.out.println("wants kapMai w/ genesymbol");
				request.setAttribute("geneSymbol", qsForm.getQuickSearchName());
				return mapping.findForward("kmplot");
			} else if (chartType.equalsIgnoreCase("geneExpPlot")) {
				try {
				    System.out.println("wants gePlot w/ genesymbol");
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
	private List populateReporters(){
		List reporters = null;
		if( kmResultsContainer != null){
			reporters = kmResultsContainer.getAssociatedReporters();
			if (chartType.equalsIgnoreCase("geneExpPlot")){
				reporters.add(0,NautilusConstants.GRAPH_DEFAULT);
			}
		}
		return reporters;
	}
}

