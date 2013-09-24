/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

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
	        saveToken(request);
	    	
	    	return (mapping.findForward("logout"));
	}
}
