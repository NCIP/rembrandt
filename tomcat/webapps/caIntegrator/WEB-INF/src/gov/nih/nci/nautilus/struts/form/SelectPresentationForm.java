// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl
package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;


import java.util.*;




/**
 * selectPresentationForm.java
 * created on 10-05-2004
 *
 * @struts:form name="selectPresentationForm"
 */
public class SelectPresentationForm extends BaseForm {

	// --------------------------------------------------------- Instance Variables
	private String sampleListFrom;
	private String sampleListTo;
	private String geneFilterBy;
	private String geneViewBy;
	private String geneListFrom;
	private String geneListTo;
	

	// Collections used for Lookup values.
	private ArrayList queryNameColl;

	// --------------------------------------------------------- Methods
	public SelectPresentationForm(){
		super();
	}

	/**
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		return errors;
	}



	/**
	 * Method reset.
	 * Reset all properties to their default values.
	 * @param ActionMapping mapping used to select this instance.
	 * @param HttpServletRequest request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {


		sampleListFrom="";
		sampleListTo="";
		geneFilterBy="";
		geneViewBy="";
		geneListFrom="";
		geneListTo="";
	}


	/**
	 * Returns the sampleListFrom.
	 * @return String
	 */
	public String getSampleListFrom() {

		return sampleListFrom;
	}

	/**
	 * Set the sampleListFrom.
	 * @param sampleList The sampleList to set
	 */
	public void setSampleListFrom(String sampleList) {
		this.sampleListFrom = sampleList;

	}
	public String getMethod() {

		return "";
	}

	/**
	 * Returns the sampleListTo.
	 * @return String
	 */
	public String getSampleListTo() {

		return sampleListTo;
	}

	/**
	 * Set the sampleListTo.
	 * @param sampleList The sampleList to set
	 */
	public void setSampleListTo(String sampleList) {
		this.sampleListTo = sampleList;

	}

	/**
	 * Returns the geneFilterBy.
	 * @return String
	 */
	public String getGeneFilterBy() {

		return geneFilterBy;
	}

	/**
	 * Set the geneFilterBy.
	 * @param geneFilterBy The geneFilterBy to set
	 */
	public void setGeneFilterBy(String geneFilterBy) {
		this.sampleListTo = geneFilterBy;

	}

	/**
	 * Returns the geneFilterBy.
	 * @return String
	 */
	public String getGeneViewBy() {

		return geneViewBy;
	}

	/**
	 * Set the geneFilterBy.
	 * @param sampleList The sampleList to set
	 */
	public void setGeneViewBy(String geneViewBy) {
		this.geneViewBy = geneViewBy;

	}
	/**
	 * Returns the geneListFrom.
	 * @return String
	 */
	public String getGeneListFrom() {

		return geneListFrom;
	}

	/**
	 * Set the geneListFrom.
	 * @param geneListFrom The geneListFrom to set
	 */
	public void setGeneListFrom(String geneListFrom) {
		this.geneListFrom = geneListFrom;

	}
	
	/**
	 * Returns the geneListTo.
	 * @return String
	 */
	public String getGeneListTo() {

		return geneListTo;
	}

	/**
	 * Set the geneListTo.
	 * @param geneListTo The geneListTo to set
	 */
	public void setGeneListTo(String geneListTo) {
		this.geneListTo = geneListTo;

	}
	public ArrayList getQueryNameColl(){
	   return queryNameColl;
	   }

}
