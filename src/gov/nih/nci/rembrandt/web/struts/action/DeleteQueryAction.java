package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.DeleteQueryForm;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class DeleteQueryAction extends DispatchAction {
    private Logger logger = Logger.getLogger(DeleteQueryAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
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
		   String sessionId = request.getSession().getId();
		   SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
		   if(queryBag != null){			     
			  Collection queryColl = queryBag.getQueries();	
			  String queryKey = deleteQueryForm.getQueryKey();
			  logger.debug("queryKey is ************:"+queryKey);		  
			  queryColl.remove(queryBag.getQuery(queryKey));	 
			}  	 	
		   return mapping.findForward("menuPage");		
	     }
	
	public ActionForward editQuery(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
	    		String queryKey = "";
	    		
			   DeleteQueryForm deleteQueryForm = (DeleteQueryForm) form;	
			   String sessionId = request.getSession().getId();
			   SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
			   if(queryBag != null){			     
				  queryKey = deleteQueryForm.getQueryKey();
				  //store this somewhere
				  request.setAttribute("queryKey", queryKey);
				}  	 	
			   String editForward = "";
			   //by default, should be reset below, always
			   editForward = "editClinical";
			   
			   Query query = queryBag.getQuery(queryKey);               //use this to get query
			   if(query instanceof ComparativeGenomicQuery){
			       editForward = "editCGH";
               }
			   else  if(query instanceof ClinicalDataQuery){
			       editForward = "editClinical";
               }
			   else  if(query instanceof GeneExpressionQuery){
			       editForward = "editGE";
               }

			   return mapping.findForward(editForward);		
		     }

	public ActionForward copyQuery(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
	    		String queryKey = "";
	    		
			   DeleteQueryForm deleteQueryForm = (DeleteQueryForm) form;	
			   String sessionId = request.getSession().getId();
			   SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
			   if(queryBag != null){			     
				  queryKey = deleteQueryForm.getQueryKey();
				  //store this somewhere
				  request.setAttribute("queryKey", queryKey);
				}  	 	
			   String editForward = "";
			   //by default, should be reset below, always
			   editForward = "editClinical";
			   
			   Query query = queryBag.getQuery(queryKey);               //use this to get query
			   if(query instanceof ComparativeGenomicQuery){
			       editForward = "editCGH";
               }
			   else  if(query instanceof ClinicalDataQuery){
			       editForward = "editClinical";
               }
			   else  if(query instanceof GeneExpressionQuery){
			       editForward = "editGE";
               }
			   request.setAttribute("copy", "true");
			   
			   return mapping.findForward(editForward);		
		     }
	
	
	public ActionForward deleteAll(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
	 String sessionId = request.getSession().getId();	
	 DeleteQueryForm deleteQueryForm = (DeleteQueryForm) form;
	 String page = (String)request.getSession().getAttribute("currentPage");
	 SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
	 Collection queryColl = queryBag.getQueries();
	 /**
	  * @todo Need to make sure this actually clearing out the Collection
	  * 	--Dave
	  */
	 queryColl.clear();
	 return mapping.findForward("menuPage");			
	
	}
	

}
