package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.graph.geneExpression.GeneExpressionGraphGenerator;
import gov.nih.nci.nautilus.ui.graph.kaplanMeier.KMGraphGenerator;
import gov.nih.nci.nautilus.ui.struts.form.KMDataSetForm;
import gov.nih.nci.nautilus.ui.struts.form.QuickSearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
		KMGraphGenerator generator = new KMGraphGenerator(kmForm.getUpFold(),
				kmForm.getDownFold(), (String) request
						.getAttribute("geneSymbol"));
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
		KMGraphGenerator generator = new KMGraphGenerator(kmForm.getUpFold(),
				kmForm.getDownFold(), kmForm.getGeneSymbol());
		if (generator.getMyActionErrors().size() > 0) {
			this.saveErrors(request, generator.getMyActionErrors());
			return mapping.findForward("badgraph");
		} else {
			kmForm.setCensorDataset(generator.getCensorDataseries());
			kmForm.setLineDataset(generator.getLineDataseries());
		}
		return mapping.findForward("kmplot");
	}

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuickSearchForm qsForm = (QuickSearchForm) form;
		String chartType = qsForm.getPlot();
		if (chartType.equalsIgnoreCase("kapMaiPlotGE")) {
			request.setAttribute("geneSymbol", qsForm.getQuickSearchName());
			return mapping.findForward("kmplot");
		} else if (chartType.equalsIgnoreCase("geneExpPlot")) {
			try {
				return doGeneExpPlot(mapping, qsForm, request, response);
			} catch (Exception e) {
				logger.error("Gene Expression Plot Flopped");
				logger.error(e);
				return mapping.findForward("error");
			}
		} else {
			return mapping.findForward("error");
		}
	}

}