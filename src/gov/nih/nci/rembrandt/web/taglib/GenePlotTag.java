package gov.nih.nci.rembrandt.web.taglib;

import gov.nih.nci.caintegrator.enumeration.GeneExpressionDataSetType;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.graphing.data.GeneExpressionPlot;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class GenePlotTag extends AbstractGraphingTag {
	
	public String algorithm = "";
	
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String geneSymbol = "";
			//can come from the action or via a get param..never both, never neither
			if(request.getAttribute("geneSymbol") != null)	{
				geneSymbol = (String) request.getAttribute("geneSymbol");
			}
			else if(request.getParameter("geneSymbol") != null)	{
				geneSymbol = (String) request.getParameter("geneSymbol");
			}
			
			GeneExpressionDataSetType geType = algorithm.equals(RembrandtConstants.REPORTER_SELECTION_AFFY) ? GeneExpressionDataSetType.GeneExpressionDataSet : GeneExpressionDataSetType.UnifiedGeneExpressionDataSet;
			
			HashMap charts = GeneExpressionPlot.generateBarChart(geneSymbol, pageContext.getSession(), new PrintWriter(out), geType);
			String filename = (String) charts.get("errorBars");
			String ffilename = (String) charts.get("noErrorBars");
			String legendHtml = (String) charts.get("legend");
			
			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
			String fgraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + ffilename;
			
			out.print("<div style=\"text-align:left;margin-left:120px; \"> <span style='font-weight:bold'>Reporter Selection:</span> ");
			
			if(algorithm.equals(RembrandtConstants.REPORTER_SELECTION_UNI))
				out.print("<a href=\"graph.do?geneSymbol="+geneSymbol+"&alg="+RembrandtConstants.REPORTER_SELECTION_AFFY+"\">"+RembrandtConstants.REPORTER_SELECTION_AFFY+"</a>");		
			else
				out.print(RembrandtConstants.REPORTER_SELECTION_AFFY);
			
			out.print(" | ");
			
			if(algorithm.equals(RembrandtConstants.REPORTER_SELECTION_AFFY))
				out.print("<a href=\"graph.do?geneSymbol="+geneSymbol+"&alg="+RembrandtConstants.REPORTER_SELECTION_UNI+"\">"+RembrandtConstants.REPORTER_SELECTION_UNI+"</a>");		
			else
				out.print(RembrandtConstants.REPORTER_SELECTION_UNI);

			out.print("</div><br/>");
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
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
}