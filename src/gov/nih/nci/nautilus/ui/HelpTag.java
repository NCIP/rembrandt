package gov.nih.nci.nautilus.ui;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class HelpTag implements Tag, Serializable {

	private PageContext pc = null;
	private Tag parent = null;
	private String help = null;
	private String label = null;

	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public void setHelp(String s) {
		help = s;
	}
	
	public String getHelp() {
		return help;
	}

	public void setLabel(String s) {
		label = s;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int doStartTag() throws JspException {
		try {

		if(help != null) {
			if(label != null)	{
				pc.getOut().write("<a href=\"javascript:void(0);\" onmouseover=\"return overlib('"+help+"', CAPTION, 'Help');\" onmouseout=\"return nd();\">"+label+"</a>");	
			}
			else	{
				pc.getOut().write("<a href=\"javascript:void(0);\" onmouseover=\"return overlib('"+help+"', CAPTION, 'Help');\" onmouseout=\"return nd();\">[?]</a>");
			}
		} else {
			pc.getOut().write("");
		}

		} catch(IOException e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
		pc = null;
		parent = null;
		help = null;
	}
}