// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.ui.struts.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.ui.struts.form.DeleteQueryForm;
import gov.nih.nci.nautilus.view.*;
import gov.nih.nci.nautilus.constants.NautilusConstants;



/** 
 * ClinicalDataAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:action path="/ClinicalData" name="ClinicalDataActionForm" input="/jsp/clinicalDataAction.jsp" validate="true"
 * @struts:action-forward name="/jsp/advanceSearchMenu.jsp" path="/jsp/advanceSearchMenu.jsp"
 * @struts:action-forward name="/jsp/queryPreview.jsp" path="/jsp/queryPreview.jsp"
 */
public class DeleteQueryAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward deleteQuery(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		   DeleteQueryForm deleteQueryForm = (DeleteQueryForm) form;	
		   String page = (String)request.getSession().getAttribute("currentPage");
		   logger.debug("the current page is :"+page);
		    
		   QueryCollection queryCollection = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		   if(queryCollection != null){			     
			  Collection queryColl = queryCollection.getQueries();	
			  String queryKey = deleteQueryForm.getQueryKey();
			  logger.debug("queryKey is ************:"+queryKey);		  
			  queryColl.remove(queryCollection.getQuery(queryKey));	 
			}  	 	
		 
		 
		   return mapping.findForward("menuPage");		
	     }
	
	public ActionForward deleteAll(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		 DeleteQueryForm deleteQueryForm = (DeleteQueryForm) form;
		 String page = (String)request.getSession().getAttribute("currentPage");
		 QueryCollection queryCollection = (QueryCollection) request.getSession().getAttribute(NautilusConstants.QUERY_KEY);
		 if(queryCollection != null){			     
			Collection queryColl = queryCollection.getQueries();
			queryColl.clear();
			}			 
		
		return mapping.findForward("menuPage");			
	
	}
	

}
