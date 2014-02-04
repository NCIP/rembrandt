/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutPageAction extends ActionSupport implements ServletRequestAware {
	
	HttpServletRequest servletRequest;
	
	public String execute()
	{
			String sID = servletRequest.getHeader("Referer");
	    	
	    	// prevents Referer Header injection
	    	if ( sID != null && sID != "" && !sID.contains("rembrandt")) {
	    		return "failure";
	    	}
	    	
	    	return "logout";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
		
	}
	
	
}
