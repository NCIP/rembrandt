package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.ui.bean.SelectedQueryBean;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.RefineQueryForm;
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.view.ViewType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class UIRefineQueryValidator {

	private static Logger logger = Logger.getLogger(UIRefineQueryValidator.class);

	public static RefineQueryForm processCompoundQuery(ActionForm form,
                                                     HttpServletRequest request,
                                                     SessionQueryBag queryCollect) 
                                                     throws Exception {
		CompoundQuery compoundQuery = null;
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		List selectedQueries = refineQueryForm.getSelectedQueries();
        ActionErrors errors = new ActionErrors();
        /* CASE 1:
         * There is only one selected query to execute
         */
        if(selectedQueries.size()==1) {
           SelectedQueryBean query = (SelectedQueryBean)selectedQueries.get(0);
           if(!query.getQueryName().equals("")&&!query.getQueryName().equals(" ")) {
            //They have chosen a query
           	compoundQuery = new CompoundQuery(queryCollect.getQuery(query.getQueryName()));
           }else {
           	    //They have not chosen a query
           	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.action.refinequery.no.query"));
           }
        }else {
        	/*CASE 2:
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
                       vectorOfTokens.add(queryCollect.getQuery(query.getQueryName()));
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
			//store the sessionId that the compound query is associated with
			compoundQuery.setSessionId(request.getSession().getId());
            //Returned String representation of the final query
            refineQueryForm.setQueryText(compoundQuery.toString());
            // Get collection of view types
            List viewCollection = setRefineQueryView((CompoundQuery) compoundQuery, request);
            // Set collection of view types in Form
            refineQueryForm.setCompoundViewColl(viewCollection);
            //Stuff compoundquery in queryCollection
            queryCollect.setCompoundQuery((CompoundQuery) compoundQuery);
            refineQueryForm.setRunFlag("yes");
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
    
  
}