package gov.nih.nci.rembrandt.web.struts2.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action to handle "Home" link to go back to the front page for guest user (RBTuser)
 * 
 * @author yangs8
 *
 */
public class GuestHomeAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest servletRequest;
	
	public String execute() {
		this.servletRequest.getSession().invalidate();
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
		
	}

	
	
}
