
package gov.nih.nci.nautilus.ui.struts.action;
import org.apache.struts.action.*;
import javax.servlet.http.*;

public class DoFirst extends Action {
	public ActionForward perform(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse) {
		aRequest.getSession().setAttribute("currentPage", "0");
		return aMapping.findForward("success");

	}

}

