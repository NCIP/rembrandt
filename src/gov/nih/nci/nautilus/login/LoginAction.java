package gov.nih.nci.nautilus.login;

import gov.nih.nci.nautilus.constants.NautilusConstants;

import java.io.FileInputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSException;

public final class LoginAction extends Action
{
    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
	public ActionForward perform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        LoginForm f = (LoginForm) form;
        if(f.getUserLoggedIn()){
            session.setAttribute("logged", "yes");
            session.setAttribute("name", f.getUserName());
            return (mapping.findForward("success"));
        }
        else
            return (mapping.findForward("failure"));
        
        
    }
        
        /**old login method...should be deleted when time is appropriate
         * 
         * -kevin rosso
		LoginForm f = (LoginForm) form;
        String userName=f.getUserName();
        String password = f.getPassword();
        
        boolean valid = false;
        
        //load the props file
		Properties props = new Properties();
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();

	    try {
	        logger.debug("loading props...");
	   		props.load(new FileInputStream(context.getRealPath("WEB-INF")+"/users.properties"));
	
		   
	   		logger.debug("props file length: " + props.size());
	
			int accounts = Integer.parseInt(props.getProperty("accounts"));
			String u = "";
			String p = "";
			String r = "";
			String n = "";
	
			
			for(int t=1; t<accounts+1; t++)	{
				u = props.getProperty("User"+t);
				p = props.getProperty("Pass"+t);
				r = props.getProperty("Role"+t);
				n = props.getProperty("Name"+t);
				
				if(userName.equals(u) && password.equals(p))
				{
					//set something in the session here
					valid = true;

					session.setAttribute("logged", "yes");
					session.setAttribute("name", n);
					session.setAttribute("role", r);
					return (mapping.findForward("success"));	
				}
			}
		} 
		catch (Exception e) {
		    logger.error("Can't read user props");
		    logger.error(e);
		}
		
		if(valid)
			return (mapping.findForward("success"));
		else
			return (mapping.findForward("failure"));
			
		
	}**/
}
