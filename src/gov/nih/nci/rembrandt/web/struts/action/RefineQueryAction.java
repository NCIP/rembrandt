package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SelectedQueryBean;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;
import gov.nih.nci.rembrandt.web.helper.UIRefineQueryValidator;
import gov.nih.nci.rembrandt.web.struts.form.RefineQueryForm;

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
/**
 * This action is associated with the refine_tile.jsp tile and is mapped
 * for buttons on the page.  This is basicly the UI mechanism for creating
 * and running a compound query.
 * 
 * @author BauerD
 * Feb 15, 2005
 *
 */

public class RefineQueryAction extends LookupDispatchAction {
    private Logger logger = Logger.getLogger(RefineQueryAction.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	/**
    *  Responsible for looking at the user constructed compound query from the
    *  refine_tile.jsp.  If the Selected queries and operations are correct
    *  than it will create a compound query that should be executed
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
	public ActionForward validateQuery(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		String sessionId = request.getSession().getId();
        RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		ActionErrors errors = new ActionErrors();
        SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
        UIRefineQueryValidator myValidator = new UIRefineQueryValidator();
        refineQueryForm = myValidator.processCompoundQuery(form, request);
        if(refineQueryForm.getErrors()!=null&&!refineQueryForm.getErrors().isEmpty()) {
            //Processing returned some errors
        	saveErrors(request, refineQueryForm.getErrors());
            return (new ActionForward(mapping.getInput()));
         } 
         return mapping.findForward("displayQuery");
       
     }
	/**
	 * Makes the necessary calls to run a compound query, then forwards the 
	 * request to the report rendering mechanism.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward runReport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		//Get the sessionId
		String sessionId = request.getSession().getId();
        ActionErrors errors = new ActionErrors();
        ActionForward thisForward = null;
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		//Get the SessionQueryBag for this request and session
		SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
		// Get the viewType array from session 
		ViewType [] availableViewTypes = (ViewType []) request.getSession().getAttribute(RembrandtConstants.VALID_QUERY_TYPES_KEY);
		//Make sure that we have a validated CompoundQuery to use
		if (queryBag.hasCompoundQuery()) {
			//Create compound query to execute
			CompoundQuery cQuery = queryBag.getCompoundQuery();
			ViewType selectView = availableViewTypes[Integer.parseInt(refineQueryForm.getCompoundView())];
			cQuery.setAssociatedView(ViewFactory.newView(selectView));
           	//ReportGeneratorHelper will execute the query if necesary, or will
			//retrieve from cache.  It will then generate the XML for the report
			//and store in a reportBean in the cache for later retrieval
			ReportGeneratorHelper rgHelper = new ReportGeneratorHelper(cQuery, new HashMap());
			ReportBean reportBean = rgHelper.getReportBean();
			request.setAttribute("queryName", reportBean.getResultantCacheKey());
			//Send to the appropriate view as per selection!!
			thisForward = new ActionForward();
			thisForward.setPath("/runReport.do?method=runGeneViewReport&resultSetName="+reportBean.getResultantCacheKey());
		}else {
			logger.error("SessionQueryBag has no Compound queries to execute");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.executequery.querycoll.no.error"));
			this.saveErrors(request, errors);
			thisForward = mapping.findForward("failure");
		}
	    return thisForward;
	 }
	/**
	 * A method used for debugging 
	 * @param geneExprObjects
	 */
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
    /**
     * Method called whenever there is change in the operands in the refine 
     * query page.  Currently it modifies the selected querries based on some
     * assumptions that can be made regarding the selected operand.  For instance
     * we can assume that if the user selects no operand for a query than any
     * querries below it are no longer needed as this must be the last query
     * in the compound query. Thus we remove them from the selectedQueries 
     * collection in the refineQueryForm.  The refineQuery tile then renders
     * the appropriate number of available rows to select from.  If the user
     * changes a operand from no operand to OR/AND than we know that they need
     * another row to select a query from.  
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
        //Flag used by the refine_query.jsp to determine if we should show the 
        //run_report button
        refineQueryForm.setRunFlag("no");
        return mapping.findForward("displayQuery");
    }
    
  
	/**
     * Creates and returns the key-value map for methods called based on which 
     * button is used to submit the form in the refine_tile.jsp.
     * @return key-method pairs for the RefineQueryAction
	 */
	protected Map getKeyMethodMap() {
		 
        HashMap<String,String> map = new HashMap<String,String>();
        
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
