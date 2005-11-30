package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class LogoutForm extends ActionForm {
	
	private String procedure;

      /**
     * @return Returns the procedure.
     */
    public String getProcedure() {
        return procedure;
    }



    /**
     * @param procedure The procedure to set.
     */
    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		

		return errors;
	}



  
}
