package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.bean.SelectedQueryBean;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper;
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
        SessionQueryBag queryCollect = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
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
  
	public ActionForward runReport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward thisForward = null;
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		SessionQueryBag queryCollect = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
		// Get the viewType array from session 
		ViewType [] availableViewTypes = (ViewType []) request.getSession().getAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY);
		if (queryCollect != null) {
			if (queryCollect.hasCompoundQuery()) {
				
                //Create compound query to execute
                CompoundQuery cQuery = (CompoundQuery) queryCollect.getCompoundQuery();
				ViewType selectView = availableViewTypes[Integer.parseInt(refineQueryForm.getCompoundView())];
				cQuery.setAssociatedView(ViewFactory.newView(selectView));
                String resultSetName = refineQueryForm.getResultSetName();
                
               //Set the name of the compound query
                if(!resultSetName.equals(" ")
                        ||!resultSetName.equals("")) {
                	cQuery.setQueryName(resultSetName);
                }else {
                	//Set the CompoundQueryName to temp
                    cQuery.setQueryName("temp");
                }
                
                ReportGeneratorHelper rgHelper = new ReportGeneratorHelper(cQuery);
                ReportBean reportBean = rgHelper.getReportBean();
                
                request.setAttribute(NautilusConstants.REPORT_BEAN, reportBean);
            }else {
				logger.debug("SessionQueryBag has no Compound queries to execute.  Please select a query to execute");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.executequery.querycoll.no.error"));
				this.saveErrors(request, errors);
				thisForward = mapping.findForward("failure");
			}
		}else{	
			logger.debug("SessionQueryBag object missing in session!!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.missing.error"));
			this.saveErrors(request, errors);
			thisForward = mapping.findForward("failure");
		}
       
        //Send to the appropriate view as per selection!!
		thisForward = mapping.findForward("success");
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
        SessionQueryBag queryCollect = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
        CompoundQuery cquery = queryCollect.getCompoundQuery();
        refineQueryForm.setCompoundViewColl(UIRefineQueryValidator.setRefineQueryView(cquery, request));
        //Validation for the refine query
        String resultSetName = refineQueryForm.getResultSetName();
        refineQueryForm.setRunFlag("yes");
        if(resultSetName!=null && !resultSetName.equals("")) {
        	cquery.setQueryName(refineQueryForm.getResultSetName());
        	return mapping.findForward("displayQuery");
        	
        }else {
        	ActionErrors errors = new ActionErrors();
            ActionError emptyResultSetNameError = new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.missing.resultsetname");
            errors.add("badSetName", emptyResultSetNameError);
            this.saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
    }
    
    public ActionForward operandChange(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
   
        RefineQueryForm refineQueryForm = (RefineQueryForm)form;
        List selectedQuerries = refineQueryForm.getSelectedQueries();
        SelectedQueryBean lastQuery = (SelectedQueryBean)selectedQuerries.get(selectedQuerries.size()-1);
        //This logic is to prevent the adding of unnecesary rows...
        if(!lastQuery.getOperand().equals("")) {
        	refineQueryForm.addSelectedQuery();
        }else {
        	for(int i = 0; i < selectedQuerries.size();i++) {
        		if(((SelectedQueryBean)selectedQuerries.get(i)).getOperand().equals("")&& i!=selectedQuerries.size()-1) {
        			for(int j = selectedQuerries.size()-1; j>=i;j--) {
                       selectedQuerries.remove(j);
                    }
                    break;
                }
           }
        
        }
        refineQueryForm.setRunFlag("no");
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
        map.put("RefineQueryAction.runReportButton", "runReport");
       
        //Store the Query in the session as a ResultQuery
        map.put("RefineQueryAction.storeResultsButton","storeResults");
        
        //store current selected queries and add another row
        map.put("RefineQueryAction.queryOperandChange","operandChange");
     
        return map;
	}
      
}
