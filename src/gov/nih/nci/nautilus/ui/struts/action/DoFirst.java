
package gov.nih.nci.nautilus.ui.struts.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DoFirst extends Action {
	public ActionForward perform(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse) {
		aRequest.getSession().setAttribute("currentPage", "0");
		return aMapping.findForward("success");

	}

}

