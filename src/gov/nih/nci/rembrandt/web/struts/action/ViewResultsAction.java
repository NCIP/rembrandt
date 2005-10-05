package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * This action is associated with the refine_tile.jsp tile and is mapped
 * for buttons on the page.  This is basicly the UI mechanism for creating
 * and running a compound query.
 * 
 * @author BauerD
 * Feb 15, 2005
 *
 */

public class ViewResultsAction extends Action{
    private Logger logger = Logger.getLogger(RefineQueryAction.class);
	private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
   
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
	ActionForward thisForward = mapping.findForward("success");
	return thisForward;
 }
      
}
