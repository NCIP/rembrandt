package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.caintegrator.security.UserCredentials;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class LoginForm extends ActionForm {
	private boolean userLoggedIn = false;

	private String userName;

	private String password;

	private static SecurityManager securityManager = SecurityManager
			.getInstance();

	private Logger logger = Logger.getLogger(LoginForm.class);

	private UserCredentials credentials;

	public boolean getUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserName(String argUserName) {
		userName = argUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String argPassword) {
		password = argPassword;
	}

	public String getPassword() {
		return password;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (userName == null || password.equals("")) {
			errors.add("User Name:", new ActionError("error.userName"));
			errors.add("Password:", new ActionError("error.password"));
		}
		try {
			credentials = securityManager.authenticate(userName, password);
		} catch (AuthenticationException e) {
			logger.debug(e);
		}

		if (credentials != null && credentials.authenticated()) {
			userLoggedIn = true;
			request.getSession().setAttribute("UserCredentials",credentials);
		} else {
			errors.add("Authentication", new ActionError(
					"User is not authenticated"));
			request.getSession().invalidate();
		}

		return errors;
	}

}
