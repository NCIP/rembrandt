import org.apache.struts.action.*;
import javax.servlet.http.*;

public class DoFirst extends Action	{
	public ActionForward perform(ActionMapping aMapping, ActionForm aForm,	HttpServletRequest aRequest, HttpServletResponse aResponse)	{
			return aMapping.findForward("success");
		}

}

