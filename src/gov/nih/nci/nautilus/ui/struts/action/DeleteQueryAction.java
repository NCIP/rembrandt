package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.DeleteQueryForm;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


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
		    
		   SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
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
		 SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
		 if(queryCollection != null){			     
			Collection queryColl = queryCollection.getQueries();
			queryColl.clear();
			}			 
		
		return mapping.findForward("menuPage");			
	
	}
	

}
