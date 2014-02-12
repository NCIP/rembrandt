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

//	/**
//	 * The validate method is called when the form is submitted and is
//	 * responsible for making sure the input is valid.
//	 * <P>
//	 * @param mapping The ActionMapping for the posted action
//	 * @param request The HttpServletRequest for this post
//	 */
//	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
//	{
//		ActionErrors errors = new ActionErrors();
//		
//		// Validate that they have selected a study and a query type
//		//if ((getEmail() == null) || (getEmail().length() < 1))
//		//	errors.add("email", new ActionMessage("error.email.required"));
//		//else
//		if ((getEmail() != null) && (getEmail().length() > 1))
//		{
//			// Validate the email address is somewhat valid
//			String email = getEmail();
//			StringBuffer sb1 = new StringBuffer("@");
//			StringBuffer sb2 = new StringBuffer(".");
//			if ((!email.contains(sb1)) || (!email.contains(sb2)))
//				errors.add("email", new ActionMessage("gov.nih.nci.rembrandt.ui.struts.form.emailQuery.email.invalid.error"));
//		}
//		
//		return errors;
//	}
	
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
