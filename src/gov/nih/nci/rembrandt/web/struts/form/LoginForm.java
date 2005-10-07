package gov.nih.nci.rembrandt.web.struts.form;
import gov.nih.nci.rembrandt.web.struts.form.UIFormValidator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class LoginForm extends ActionForm
{
private boolean userLoggedIn = false;    
private String userName;
private String password;

public boolean getUserLoggedIn(){
    return userLoggedIn;
}

public void setUserName(String argUserName)
{
    userName = argUserName;
}
public String getUserName()
{
    return userName;
}
public void setPassword(String argPassword)
{
    password = argPassword;
}
public String getPassword()
{
    return password;
}
public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request)
{
    ActionErrors errors=new ActionErrors();
    if(userName == null || password.equals(""))
    {
        errors.add("User Name:",new ActionError("error.userName"));
        errors.add("Password:",new ActionError("error.password"));
    }
    errors = UIFormValidator.validateLDAP(userName, password, errors);
    
    if (errors.isEmpty()) {
        userLoggedIn = true;
    }
    
    return errors;
}

}
