// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.struts.form.GeneExpressionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** 
 * GeneexpressionAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:action path="/geneexpression" name="geneexpressionForm" input="/jsp/geneexpression.jsp" validate="true"
 * @struts:action-forward name="/jsp/advanceSearchMenu.jsp" path="/jsp/advanceSearchMenu.jsp"
 * @struts:action-forward name="/jsp/queryPreview.jsp" path="/jsp/queryPreview.jsp"
 */
public class GeneexpressionAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
		
		return mapping.findForward("advanceSearchMenu");
	}

}
