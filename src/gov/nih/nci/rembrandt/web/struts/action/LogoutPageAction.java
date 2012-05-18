package gov.nih.nci.rembrandt.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutPageAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
			String sID = request.getHeader("Referer");
	    	
	    	// prevents Referer Header injection
	    	if ( sID != null && sID != "" && !sID.contains("rembrandt")) {
	    		return (mapping.findForward("failure"));
	    	}
	    	
	    	return (mapping.findForward("logout"));
	}
}