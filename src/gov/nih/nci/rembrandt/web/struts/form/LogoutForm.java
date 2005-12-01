package gov.nih.nci.rembrandt.web.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class LogoutForm extends ActionForm {
	
	//Default to saving the logout
	private String procedure = "logoutSave";

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
