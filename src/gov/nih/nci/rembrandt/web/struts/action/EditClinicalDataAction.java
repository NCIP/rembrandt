package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author LandyR
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditClinicalDataAction extends Action {
    private Logger logger = Logger.getLogger(DeleteQueryAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	
    public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	{

			    HttpSession session = request.getSession();
				if (form == null) {
			          
				    form = new ClinicalDataForm();
			            if ("request".equals(mapping.getScope()))
			                request.setAttribute(mapping.getAttribute(), form);
			            else
			                session.setAttribute(mapping.getAttribute(), form);
				}
			   ClinicalDataForm cdForm = (ClinicalDataForm) form;	
			   String sessionId = request.getSession().getId();
			   SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
			   String queryKey = (String) request.getAttribute("queryKey");
			   if(queryBag != null){			     
			       //get the Form from the sessionQB
			       ClinicalDataForm origCdForm = (ClinicalDataForm) queryBag.getFormBeanMap().get(queryKey); 
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
			     //  cdForm = origCdForm.cloneMe();
			     
				}  	
			   
			   String editForward = "";
			   if(request.getAttribute("copy")!=null && ((String) request.getAttribute("copy")).equals("true"))
			       cdForm.setQueryName(cdForm.getQueryName() + "_copy");
			  
			  editForward = "goEditClinical";
			   
			   return mapping.findForward(editForward);		
		     }

}
