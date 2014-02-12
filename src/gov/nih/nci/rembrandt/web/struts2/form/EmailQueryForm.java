/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import javax.servlet.http.HttpServletRequest;

/**
 * The DownloadForm class is the form validator for the download form.
 * <P>
 * @author mholck
 * @see org.apache.struts.action.ActionForm
 */
public class EmailQueryForm extends BaseForm
{
	// Serial version ID for serializable
	private static final long serialVersionUID = 4360701217770869403L;
	
	// email is the email address the user enters
	private String email;
	private String queryName = "";
	
	/**
	 * The reset method is called on the loading of this form and anytime the
	 * user selects the reset button.  It can be used to set the default starting
	 * values for the form.
	 * <P>
	 * @param mapping The ActionMapping for the posted action
	 * @param request The HttpServletRequest for this post
	 */
//	public void reset()
//	{
//		String addr = (String)request.getSession().getAttribute("email");
//		this.email = addr;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * @param queryName the queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

}
