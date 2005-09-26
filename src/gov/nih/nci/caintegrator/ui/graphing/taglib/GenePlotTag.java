package gov.nih.nci.caintegrator.ui.graphing.taglib;

import gov.nih.nci.nautilus.ui.graph.geneExpression.GeneExpressionPlot;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class GenePlotTag extends AbstractGraphingTag {
	
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String geneSymbol = (String) request.getAttribute("geneSymbol");
			HashMap charts = GeneExpressionPlot.generateBarChart(geneSymbol, pageContext.getSession(), new PrintWriter(out));
			String filename = (String) charts.get("errorBars");
			String ffilename = (String) charts.get("noErrorBars");
			String legendHtml = (String) charts.get("legend");
			
			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
			String fgraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + ffilename;
			
			out.print("<img src=\""+ graphURL+"\" border=0 usemap=\"#"+filename+"\" id=\"geneChart\">");
			out.print("<div id=\"legend\">" + legendHtml + "</div>"); //this is for the custom legend
			out.print("<br/><a href=\"javascript:toggleGenePlot('"+filename+"','"+ffilename+"');\">Toggle Error Bars</a><br/> ");
			
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