package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.ClinicalDataForm;
import gov.nih.nci.nautilus.ui.struts.form.ComparativeGenomicForm;
import gov.nih.nci.nautilus.ui.struts.form.DeleteQueryForm;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @author LandyR
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditCGHAction extends Action {
    private Logger logger = Logger.getLogger(DeleteQueryAction.class);
    private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();

	public ActionForward perform(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	{
	    
		    HttpSession session = request.getSession();
			if (form == null) {
		          
			    form = new ComparativeGenomicForm();
		            if ("request".equals(mapping.getScope()))
		                request.setAttribute(mapping.getAttribute(), form);
		            else
		                session.setAttribute(mapping.getAttribute(), form);
			}
			ComparativeGenomicForm cdForm = (ComparativeGenomicForm) form;	
			   String sessionId = request.getSession().getId();
			   SessionQueryBag queryBag = cacheManager.getSessionQueryBag(sessionId);
			   String queryKey = (String) request.getAttribute("queryKey");
			   if(queryBag != null){			     
			       //get the Form from the sessionQB
			       ComparativeGenomicForm origCdForm = (ComparativeGenomicForm) queryBag.getFormBeanMap().get(queryKey); 
				  try {
                    //try this, else call each setter
                     PropertyUtils.copyProperties(cdForm, origCdForm);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			    
				}  	
			   
			   String editForward = "";
		       editForward = "goEditCGH";
			   
			   return mapping.findForward(editForward);		
		     }

}
