// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.ui.struts.action;


import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.de.DomainElementClass;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.SelectPresentationForm;
import gov.nih.nci.nautilus.view.View;
import gov.nih.nci.nautilus.view.Viewable;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





/**
 */
public class SelectPresentDispatchAction extends DispatchAction {

    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */

	public ActionForward runReport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		
		SelectPresentationForm thisForm = (SelectPresentationForm) form;
		
		DomainElementClass [] allDomainElems = thisForm.getSelectedElements();
		Collection selectedDomainElems = new ArrayList();
		String [] selectedIndexes = thisForm.getListTo();
		SessionQueryBag queryCollect = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
		
		for (int i = 0; i < selectedIndexes.length; i++) {
			int selectedIndex = Integer.parseInt(selectedIndexes[i]);
			selectedDomainElems.add(allDomainElems[selectedIndex]);
		}

		// Get View from SessionQueryBag and set selected elements in the View
		if (queryCollect != null) { 
			if (queryCollect.hasCompoundQuery()) {

					try {
						// Get View from Query Collection that user selected
						Viewable thisView = queryCollect.getCompoundQuery().getAssociatedView();
						// Set the selected presentation elements in the View
						((View) thisView).setSelectedElements(selectedDomainElems);
						
						// Execute the query and place the query in session
						ResultSet[] queryResultSetObjects = QueryManager.executeQuery(queryCollect.getCompoundQuery());
						print(queryResultSetObjects);
						request.getSession().setAttribute(NautilusConstants.RESULTSET_KEY,queryResultSetObjects);
						
						ActionForward thisForward = mapping.findForward("success");
						return thisForward;
					}
					catch (Exception e) {
						logger.error(e);
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e.getMessage()));
						this.saveErrors(request, errors);
					}


			} else {
			        logger.debug("SelectPresentationForm - SessionQueryBag does not have a CompoundQuery");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.executequery.querycoll.no.error"));
					this.saveErrors(request, errors);
			}
		} else {
		    logger.debug("SelectPresentationForm - Query Collection is null");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
		}
		
		ActionForward thisForward = mapping.findForward("backtopresent");

		return thisForward;

	 }

	private void print(ResultSet[] geneExprObjects) {
		if(geneExprObjects != null){
		    logger.debug("Number of Records:"+ geneExprObjects.length);
			for (int i =0; i < geneExprObjects.length; i++) {
				GeneExpr.GeneExprSingle expObj = (GeneExpr.GeneExprSingle) geneExprObjects[i];
				if(expObj != null){
				    logger.debug( "uID: " + expObj.getDesId() + "|geneSymbol: " + expObj.getGeneSymbol() +"|clone: " + expObj.getCloneName()+"|probeSet: "+expObj.getProbesetName()+"|biospecimenID: " + expObj.getBiospecimenId() );
				}
			}
		}
		
	}
	
}
