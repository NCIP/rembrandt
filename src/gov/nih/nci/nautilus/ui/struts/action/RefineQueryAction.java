package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.QueryCollection;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.ui.helper.SelectedQuery;
import gov.nih.nci.nautilus.ui.helper.UIRefineQueryValidator;
import gov.nih.nci.nautilus.ui.struts.form.RefineQueryForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;


public class RefineQueryAction extends LookupDispatchAction {
    private static Logger logger = Logger.getLogger(RefineQueryAction.class);
	
    /** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward validateQuery(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

        RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		ActionErrors errors = new ActionErrors();
        QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
        if (queryCollect == null) {
            //Query Collection is null. Go back and display error
        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.empty.error"));
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        } else {
            //Process the existing querries
            refineQueryForm = UIRefineQueryValidator.processCompoundQuery(form, request, queryCollect);
            if(refineQueryForm.getErrors()!=null) {
                //Processing returned some errors
            	saveErrors(request, refineQueryForm.getErrors());
                return (new ActionForward(mapping.getInput()));
            }
            return mapping.findForward("displayQuery");
        }
     }
  
	public ActionForward displayResult(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;


		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		// Get the viewType array from session 
		ViewType [] availableViewTypes = (ViewType []) request.getSession().getAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY);
// 		Set ViewType array in session to null, we dont need it anymore
//		request.getSession().setAttribute(Constants.VALID_QUERY_TYPES_KEY, null);
		
		if (queryCollect != null) {
			if (queryCollect.hasCompoundQuery()) {
				CompoundQuery cQuery = (CompoundQuery) queryCollect.getCompoundQuery();
				logger.debug(refineQueryForm.getCompoundView());
				ViewType selectView = availableViewTypes[Integer.parseInt(refineQueryForm.getCompoundView())];
				logger.debug(selectView);
				// Set View in compoundQuery
				cQuery.setAssociatedView(ViewFactory.newView(selectView));

				// Execute the query and place the query in session
//				ResultSet[] queryResultSetObjects = QueryManager.executeQuery(queryCollect.getCompoundQuery());
//				print(queryResultSetObjects);
//				request.getSession().setAttribute(Constants.RESULTSET_KEY,queryResultSetObjects);
				
			}else {
				logger.debug("QueryCollection has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
				ActionForward thisForward = mapping.findForward("failure");
			}
		}else{	
			logger.debug("QueryCollection object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
			ActionForward thisForward = mapping.findForward("failure");
		}
//Send to the appropriate view as per selection!!
		ActionForward thisForward = mapping.findForward("success");
		
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
    
    public ActionForward storeResults(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
   
        RefineQueryForm refineQueryForm = (RefineQueryForm)form;
        refineQueryForm.setRunFlag("yes");
        return mapping.findForward("displayQuery");
    }
    
    public ActionForward operandChange(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
   
        RefineQueryForm refineQueryForm = (RefineQueryForm)form;
        List selectedQuerries = refineQueryForm.getSelectedQueries();
        SelectedQuery lastQuery = (SelectedQuery)selectedQuerries.get(selectedQuerries.size()-1);
        //This logic is to prevent the adding of unnecesary rows...
        if(!lastQuery.getOperand().equals("")) {
        	refineQueryForm.addSelectedQuery();
        }else {
        	for(int i = 0; i < selectedQuerries.size();i++) {
        		if(((SelectedQuery)selectedQuerries.get(i)).getOperand().equals("")&& i!=selectedQuerries.size()-1) {
        			for(int j = selectedQuerries.size()-1; j>=i;j--) {
                       selectedQuerries.remove(j);
                    }
                    break;
                }
           }
        
        }
        return mapping.findForward("displayQuery");
    }
    
  
	/**
     * Creates and returns the key-value map for methods called based on which 
     * button is used to submit the form.
     * @return key-method pairs for the RefineQueryAction
	 */
	protected Map getKeyMethodMap() {
		 
        HashMap map = new HashMap();
        
        //Validate Query Button using validate method
        map.put("RefineQueryAction.validateButton", "validateQuery");
        
        //Run Report Button using displayResult method
        map.put("RefineQueryAction.runReportButton", "displayResult");
       
        //Store the Query in the session as a ResultQuery
        map.put("RefineQueryAction.storeResultsButton","storeResults");
        
        //store current selected queries and add another row
        map.put("RefineQueryAction.queryOperandChange","operandChange");
     
        return map;
	}
      
}
