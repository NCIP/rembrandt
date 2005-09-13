package gov.nih.nci.caintegrator.ui.graphing.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class KaplanMeierPlot extends AbstractGraphingTag {
	private String width = "";
	private String height= "";
	private String legend= "";
	
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print("<B>New Kaplan-Meier plot will go here</B>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() throws JspException {
		
		return doAfterEndTag(EVAL_PAGE);
	}
	public void reset() {
		//chartDefinition = createChartDefinition();
	}
}