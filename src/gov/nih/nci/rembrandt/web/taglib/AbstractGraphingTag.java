package gov.nih.nci.rembrandt.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

public abstract class AbstractGraphingTag extends TagSupport {

	protected Logger log = Logger.getLogger(this.getClass());
    
	protected final int doAfterEndTag(int returnVal) {
		reset();
		return returnVal;
	}

	protected abstract void reset();

	public int doEndTag() throws JspException {
		return doAfterEndTag(super.doEndTag());
	}

}