package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.ui.bean.SelectedQueryBean;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.RefineQueryForm;
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.view.ViewType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
/**
 * This class is intended to take the selected queries from the refine query
 * page...
 * @author BauerD
 * Mar 23, 2005
 *
 */
public class UIRefineQueryValidator {

	private static Logger logger = Logger.getLogger(UIRefineQueryValidator.class);
	private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
	
	public RefineQueryForm processCompoundQuery(ActionForm form,
                                                     HttpServletRequest request) 
                                                     throws Exception {
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		//this should turn off the run report buttons in the refine query page
		refineQueryForm.setRunFlag("no");
		//Clean out the old stored compound query from the bag, and form
		CompoundQuery compoundQuery = null;
		refineQueryForm.setQueryText("");
		refineQueryForm.setCompoundViewColl(new ArrayList());
		String sessionId = request.getSession().getId();
		SessionQueryBag queryBag = cacheManager.getSessionQueryBag(sessionId);
		//clears out the old compound query, in case something bombs
		queryBag.setCompoundQuery(compoundQuery);
		ActionErrors errors = new ActionErrors();
		boolean isAllGenesQuery = refineQueryForm.isAllGenesQuery();
		List selectedQueries = refineQueryForm.getSelectedQueries();
		String selectedResultSet = refineQueryForm.getResultSetName();
		//remove any incidental or incorrect selectedQueries from the List
		selectedQueries = scrubTheLazyList(selectedQueries);
		/* CASE 1:
		 * This is an "All Genes" query. Overwrite the selectedQueries list with
		 * a list that only contains the selected all gene query
		 */
		if(isAllGenesQuery) {
			try {
				selectedQueries = getAllGenesQuery(refineQueryForm.getAllGeneQuery(), sessionId, selectedResultSet);
			}catch(IllegalStateException ise) {
				/*
				 * This is thrown in the instance that the QueryBag gets hosed up.
				 * Not really sure what to do in this case... so for now just log it
				 */
				logger.error(ise);
				
			}catch(OperationNotSupportedException onse) {
				/*
				 * This should get thrown in the instance that there is only an 
				 * all genes query selected in the RefineQueryPage. There must also be
				 * a result set specified... 
				 */
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.allgenequery"));
			}
		}
        
        /* CASE 1(step 2) & CASE 2:
         * This is called in the instance there is 1 query stored in the selectedQuery list.   
         * This can be either a selected "All Gene Query", or a regular query.
         */
        if(selectedQueries.size()==1) {
           SelectedQueryBean query = (SelectedQueryBean)selectedQueries.get(0);
           if(!query.getQueryName().equals("")&&!query.getQueryName().equals(" ")) {
           	//They have chosen a query
		  	compoundQuery = new CompoundQuery(queryBag.getQuery(query.getQueryName()));
		   }else {
		     //They have not chosen a query
		     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.no.query"));
		    }
        }else if(selectedQueries.size()>1) {
        	/*CASE 3:
             * There is more than 1 query selected
        	 */
            Vector vectorOfTokens = new Vector();
            
            //Tokenize the selected queries
            for(int i = 0;i<selectedQueries.size(); i++)
            {
            	SelectedQueryBean query = (SelectedQueryBean)selectedQueries.get(i);
                String leftParen = query.getLeftParen();
                String queryName = query.getQueryName();
                String rightParen = query.getRightParen();
                String operatorType = query.getOperand();
                
                if (leftParen != null && leftParen.length() > 0) 
                       vectorOfTokens.add(leftParen);
                if (query != null) 
                       vectorOfTokens.add(queryBag.getQuery(query.getQueryName()));
                if (rightParen != null && rightParen.length() > 0) 
                       vectorOfTokens.add(rightParen);
                if (operatorType != null && operatorType.length() > 0) 
                       vectorOfTokens.add(operatorType);
            }
            //Parse the selected queries
            try {
    			Parser queryParser = new Parser(vectorOfTokens);
    			queryParser.expression();
    			compoundQuery = queryParser.getCompoundQuery();
     			
    		}catch (Exception e) {
    			logger.debug("Error Parsing Query and/or creating Compound Query "+ e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.parse.error",e.getMessage()));
    			refineQueryForm.setErrors(errors);
    			logger.error(e);
    		}
        }
           
		if(compoundQuery!=null) {
			//apply the selected result set if one was chosen
	        if(selectedResultSet!=null&&!"".equals(selectedResultSet)) {
	        	/*
	        	 * APPLY THE RESULT SET TO THE COMPOUND QUERY!
	        	 * HOW I DON"T KNOW JUST YET... tomorrow!
	        	 */
	        }
			//store the sessionId that the compound query is associated with
			compoundQuery.setSessionId(sessionId);
            //Returned String representation of the final query
            refineQueryForm.setQueryText(compoundQuery.toString());
            // Get collection of view types
            List viewCollection = setRefineQueryView(compoundQuery, request);
            // Set collection of view types in Form
            refineQueryForm.setCompoundViewColl(viewCollection);
            //Stuff compoundquery in queryCollection
            queryBag.setCompoundQuery((CompoundQuery) compoundQuery);
            refineQueryForm.setRunFlag("yes");
        }else if(isAllGenesQuery){
        	
        }
        refineQueryForm.setErrors(errors);
        return refineQueryForm;
 	}

	public static List setRefineQueryView(CompoundQuery cQuery,
			HttpServletRequest request) {
		ArrayList queryViewColl = new ArrayList();
		Properties props = new Properties();
		props = ApplicationContext.getLabelProperties();

		if (cQuery != null && props != null) {

			ViewType[] availableViewTypes = cQuery.getValidViews();
			//Set the View Types array in request to be used on return trip
			request.getSession().setAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY, availableViewTypes);

			for (int viewIndex = 0; viewIndex < availableViewTypes.length; viewIndex++) {
				ViewType thisViewType = (ViewType) availableViewTypes[viewIndex];
				String viewText = (String) props.get(thisViewType.getClass().getName());
				queryViewColl.add(new LabelValueBean(viewText, Integer.toString(viewIndex)));
			}
		} else {
			queryViewColl.add(new LabelValueBean(" ", " "));
			logger.debug("Compound Query passed is null");
		}
		return queryViewColl;
	}
		
	private List getAllGenesQuery(String allGeneQueryName, String sessionId, String selectedResultSet)throws IllegalStateException, OperationNotSupportedException {
		SelectedQueryBean bean = null;
		List queries = new ArrayList();
		if(allGeneQueryName!=null&&!allGeneQueryName.equals("")) {
			SessionQueryBag bag = cacheManager.getSessionQueryBag(sessionId);
			Map allGenesQueries = bag.getAllGenesQueries();
			if(selectedResultSet!=null&&!"".equals(selectedResultSet)) {
				if(allGenesQueries!=null&&allGenesQueries.containsKey(allGeneQueryName)) {
					bean = new SelectedQueryBean();
					bean.setQueryName(allGeneQueryName);
					bean.setAllGeneQuery(true);
					queries.add(bean);
				}else {
					throw new IllegalStateException("There isn't an All Genes Query by the name: "+allGeneQueryName);
				}
			}else {
				throw new OperationNotSupportedException("No result set specified for the All Gene Query...this is required at this time");
			}
		}
		return queries;
	}
	
	private List scrubTheLazyList(List selectedQueries) {
		if(selectedQueries!=null) {
			Iterator iter = selectedQueries.iterator();
			while(iter.hasNext()) {
				SelectedQueryBean bean = (SelectedQueryBean)iter.next();
				if(bean.getQueryName()==null||bean.getQueryName().equals("")) {
					//drop the bean
					selectedQueries.remove(bean);
					//reset the iterator
					iter = selectedQueries.iterator();
				}
			}
		}
		return selectedQueries;
	}
    
  
}