package nautilus.login;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

public final class LoginAction extends Action
{
	public ActionForward perform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		LoginForm f = (LoginForm) form;
		String userName=f.getUserName();
		String password = f.getPassword();
		
		boolean valid = false;		
		//load the props file
		Properties props = new Properties();
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();

	    try {
			System.out.println("loading props...");
	   		props.load(new FileInputStream(context.getRealPath("WEB-INF")+"/users.properties"));
	
		   
		   	System.out.println("*********** props file length: " + props.size());
	
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
	    System.out.println("\n\n ---------------- cant read user props ----------------- \n\n");
		}
		
		if(valid)
			return (mapping.findForward("success"));
		else
			return (mapping.findForward("failure"));
			
		/*
		if(userName.equals("admin") && password.equals("admin123"))
		{
		//set something in the session here
		HttpSession session = request.getSession();
		session.setAttribute("logged", "yes");
		return (mapping.findForward("success"));			
		}

		else
		return (mapping.findForward("failure"));
		*/
	}
}
