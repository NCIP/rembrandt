package gov.nih.nci.caintegrator.ui.graphing.taglib;

import gov.nih.nci.nautilus.ui.graph.geneExpression.GeneExpressionPlot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class GenePlotTag extends AbstractGraphingTag {
	
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String geneSymbol = (String) request.getAttribute("geneSymbol");
			String filename = GeneExpressionPlot.generateBarChart(geneSymbol, pageContext.getSession(), new PrintWriter(out));
			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;

			out.print("<img src=\""+ graphURL+"\" border=0 usemap=\"#"+filename+"\">");
			
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
		//nada
	}
}