package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
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
    private static PresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
    public ActionForward execute(ActionMapping mapping, ActionForm form,
    								HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        LogoutForm f = (LogoutForm) form;
        String logoutSelection = f.getProcedure();
        String forward = "logout";
        if("logoutSave".equals(logoutSelection)) {
        	/*
        	 * User has selected to save the current session and log out of the
        	 * application
        	 */
        	UserCredentials credentials = (UserCredentials)request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
        	
        	/*******************************************************************
        	 * HACK! This is only here to prevent a user logged in on the public
        	 * account "RBTuser" from persisting their session
        	 *******************************************************************/
        	if(!"RBTuser".equals(credentials.getUserName())) {
        		_cacheManager.persistUserSession(credentials.getUserName(), request.getSession().getId());
        	}
        	_cacheManager.deleteSessionCache(session.getId());
        	//null out the sesssion vars
            session.setAttribute("logged", null);
        	//kill the session and unbinds any objects bound to it.
            session.invalidate();
        }else if("logoutNoSave".equals(logoutSelection)) {
        	//null out the sesssion vars
            session.setAttribute("logged", null);
        	//kill the session and unbinds any objects bound to it.
            session.invalidate();
            _cacheManager.deleteSessionCache(session.getId());
        	/*
        	 * User has selected to not save the session data and log out of the
        	 * application
        	 * 
        	 * Do nothing right now
        	 */
        	
        }else if("dontLogout".equals(logoutSelection)) {
        	/*
        	 * User has selected to keep working on the application, not saving
        	 * the data.
        	 */
        	forward = "dontLogout";
        	return (mapping.findForward(forward));
        }
  
        return (mapping.findForward(forward));
    }
}
