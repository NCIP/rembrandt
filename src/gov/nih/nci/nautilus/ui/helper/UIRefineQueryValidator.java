package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.parser.Parser;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.query.QueryCollection;
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
                                                     QueryCollection queryCollect) 
                                                     throws Exception {
		Queriable compoundQuery = null;
		RefineQueryForm refineQueryForm = (RefineQueryForm) form;
		List selectedQueries = refineQueryForm.getSelectedQueries();
        ActionErrors errors = new ActionErrors();
        /* CASE 1:
         * There is only one selected query to execute
         */
        if(selectedQueries.size()==1) {
           SelectedQuery query = (SelectedQuery)selectedQueries.get(0);
           compoundQuery = new CompoundQuery(queryCollect.getQuery(query.getQueryName()));
        }else {
        	/*CASE 2:
             * There is more than 1 query selected
        	 */
            Vector vectorOfTokens = new Vector();
            
            //Tokenize the selected queries
            for(int i = 0;i<selectedQueries.size(); i++)
            {
            	SelectedQuery query = (SelectedQuery)selectedQueries.get(i);
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
            //Returned String representation of the final query
            refineQueryForm.setQueryText(compoundQuery.toString());
            // Get collection of view types
            Collection viewCollection = setRefineQueryView((CompoundQuery) compoundQuery, request);
            // Set collection of view types in Form
            refineQueryForm.setCompoundViewColl((ArrayList) viewCollection);
             //Stuff compoundquery in queryCollection
            queryCollect.setCompoundQuery((CompoundQuery) compoundQuery);
            refineQueryForm.setRunFlag("yes");
        }
        return refineQueryForm;
 	}

	private static Collection setRefineQueryView(CompoundQuery cQuery,
			HttpServletRequest request) {
		ArrayList queryViewColl = new ArrayList();
		Properties props = new Properties();
		props = ApplicationContext.getLabelProperties();

		if (cQuery != null && props != null) {

			ViewType[] availableViewTypes = cQuery.getValidViews();
			//Set the View Types array in request to be used on return trip
			request.getSession()
					.setAttribute(NautilusConstants.VALID_QUERY_TYPES_KEY,
							availableViewTypes);

			for (int viewIndex = 0; viewIndex < availableViewTypes.length; viewIndex++) {
				ViewType thisViewType = (ViewType) availableViewTypes[viewIndex];
				String viewText = (String) props.get(thisViewType.getClass()
						.getName());

				queryViewColl.add(new LabelValueBean(viewText, Integer
						.toString(viewIndex)));
			}
		} else {
			queryViewColl.add(new LabelValueBean(" ", " "));
			logger.debug("Compound Query passed is null");
		}
		return queryViewColl;
	}
}