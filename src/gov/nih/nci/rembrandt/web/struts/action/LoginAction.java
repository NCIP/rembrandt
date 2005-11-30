package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.UserPreferencesBean;
import gov.nih.nci.rembrandt.web.struts.form.LoginForm;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class LoginAction extends Action
{
    private static Logger logger = Logger.getLogger(RembrandtConstants.LOGGER);
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
    	/**
    	 * This needs to be modified to take advantage of the new UserCredentials
    	 * object.
    	 * 
    	 * It also needs to have the logic added for reinstating a previously
    	 * saved session.
    	 */
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        LoginForm f = (LoginForm) form;
        if(f.getUserLoggedIn()){
            session.setAttribute("logged", "yes");
            session.setAttribute("name", f.getUserName());
            UserPreferencesBean userPreferencesBean = new UserPreferencesBean();
            session.setAttribute(RembrandtConstants.USER_PREFERENCES,userPreferencesBean);
            
            return (mapping.findForward("success"));
        }
        else
            return (mapping.findForward("failure"));
        
        
    }
}
