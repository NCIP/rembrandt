package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.UserPreferencesBean;
import gov.nih.nci.rembrandt.web.struts.form.LoginForm;
import gov.nih.nci.rembrandt.web.struts.form.LogoutForm;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class LogoutAction extends Action
{
    private static Logger logger = Logger.getLogger(RembrandtConstants.LOGGER);
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
    	
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        LogoutForm f = (LogoutForm) form;
        //null out the sesssion vars
        session.setAttribute("logged", null);
        //kill the session and unbinds any objects bound to it.
         session.invalidate();
          
            return (mapping.findForward("logoutSaved"));
        
        
    }
}
