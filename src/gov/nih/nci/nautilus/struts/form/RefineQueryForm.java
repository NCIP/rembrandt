// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl
package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;

import gov.nih.nci.nautilus.constants.*;
import gov.nih.nci.nautilus.query.*;

import java.util.*;




/**
 * RefineQueryForm.java
 * created on 10-05-2004
 *
 * @struts:form name="refineQueryForm"
 */
public class RefineQueryForm extends BaseForm {

	// --------------------------------------------------------- Instance Variables
	private String queryName1;
	private String leftParen1;
	private String rightParen1;
	private String operatorType1;

	private String queryName2;
	private String leftParen2;
	private String rightParen2;
	private String operatorType2;

	private String queryName3;
	private String leftParen3;
	private String rightParen3;
	private String operatorType3;
	
	private String queryText;
	private String compoundView;
	private String resultsetName;
	
	private String method;

	// Collections used for Lookup values.
	private ArrayList queryNameColl;
	private ArrayList compoundViewColl;
	



	// --------------------------------------------------------- Methods
	public RefineQueryForm(){

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
		
		// Only one PRB in Query
		String queryName1 = this.getQueryName1().trim();
		String queryName2 = this.getQueryName2().trim();
		String queryName3 = this.getQueryName3().trim();
		
		// 
		System.out.println(queryName1.length());
		if (queryName1.length() < 1 && (queryName2.length()>0 || queryName3.length()>0)) {
			errors.add("queryName1", new ActionError("gov.nih.nci.nautilus.struts.form.query.empty"));
		}
		
		if (queryName2.length() < 1 && queryName3.length()>0) {
			errors.add("queryName2", new ActionError("gov.nih.nci.nautilus.struts.form.query.empty"));
		}

		if ((this.getOperatorType1().equalsIgnoreCase("PRB")) && (this.getOperatorType2().equalsIgnoreCase("PRB"))){
			errors.add("operatorType2", new ActionError("gov.nih.nci.nautilus.struts.form.operatortype.no.prb"));
			
		}
		
		if (this.getOperatorType1().trim().length() >= 1 && (this.getQueryName2().trim().length() < 1 || this.getQueryName1().trim().length() < 1)) {
			errors.add("operatorType1", new ActionError("gov.nih.nci.nautilus.struts.form.operatortype.no.query"));
		}
		
		if (this.getOperatorType1().trim().length() < 1 && this.getQueryName2().trim().length() >= 1 && this.getQueryName1().trim().length() >= 1) {
			errors.add("operatorType1", new ActionError("gov.nih.nci.nautilus.struts.form.operatortype.no.operator"));
		}

		if (this.getOperatorType2().trim().length() < 1 && this.getQueryName2().trim().length() >= 1 && this.getQueryName3().trim().length() >= 1) {
			errors.add("operatorType2", new ActionError("gov.nih.nci.nautilus.struts.form.operatortype.no.operator"));
		}

		if (this.getOperatorType2().trim().length() >= 1 && (this.getQueryName3().trim().length() < 1 || this.getQueryName2().trim().length() < 1)) {
			errors.add("operatorType2", new ActionError("gov.nih.nci.nautilus.struts.form.operatortype.no.query"));
		}
		
		return errors;
	}


	public void setRefineQueryLookups(HttpServletRequest request) {

		// Get the Query Collection from the session

		QueryCollection queryCollect = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);
		queryNameColl = new ArrayList();
		queryNameColl.add( new LabelValueBean( " ", " " ));

		if (queryCollect != null) {
			Collection collectionOfQueries = queryCollect.getQueries();
		
			for (Iterator iter = collectionOfQueries.iterator(); iter.hasNext();) {
				Query thisQuery = (Query) iter.next();

				queryNameColl.add( new LabelValueBean( thisQuery.getQueryName(), thisQuery.getQueryName() ) );
			
			}		
		}else {
		
			System.out.println("No Query Collection Object in Session");
		}

	}


	/**
	 * Method reset.
	 * Reset all properties to their default values.
	 * @param ActionMapping mapping used to select this instance.
	 * @param HttpServletRequest request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {


		queryName1="";
		leftParen1="";
		rightParen1="";
		operatorType1="";

		queryName2="";
		leftParen2="";
		rightParen2="";
		operatorType2="";

		queryName3="";
		leftParen3="";
		rightParen3="";
		queryText="";
		compoundView="";
		resultsetName="";


		
		setRefineQueryLookups(request);
		compoundViewColl = new ArrayList();
		compoundViewColl.add( new LabelValueBean( " ", " " ));


	}

	
	/**
	 * Returns the queryName1.
	 * @return String
	 */
	public String getQueryName1() {

		return queryName1;
	}

	/**
	 * Set the queryName1.
	 * @param queryName1 The queryName to set
	 */
	public void setQueryName1(String queryName1) {
		this.queryName1 = queryName1;

	}
	/**
	 * Returns the leftParen1.
	 * @return String
	 */
	public String getLeftParen1() {

		return leftParen1;
	}

	/**
	 * Set the leftParen1.
	 * @param leftParen1 The leftParen to set
	 */
	public void setLeftParen1(String leftParen1) {
		this.leftParen1 = leftParen1;

	}

	/**
	 * Returns the rightParen1.
	 * @return String
	 */
	public String getRightParen1() {

		return rightParen1;
	}

	/**
	 * Set the rightParen1.
	 * @param rightParen1 The rightParen to set
	 */
	public void setRightParen1(String rightParen1) {
		this.rightParen1 = rightParen1;

	}

	/**
	 * Returns the operatorType1.
	 * @return String
	 */
	public String getOperatorType1() {

		return operatorType1;
	}

	/**
	 * Set the operatorType1.
	 * @param operatorType1 The operatorType to set
	 */
	public void setOperatorType1(String operatorType1) {
		this.operatorType1 = operatorType1;

	}

//***************
  /**
   * Returns the queryName2.
   * @return String
   */
  public String getQueryName2() {

	  return queryName2;
  }

  /**
   * Set the queryName2.
   * @param queryName2 The queryName to set
   */
  public void setQueryName2(String queryName2) {
	  this.queryName2 = queryName2;

  }
  /**
   * Returns the leftParen1.
   * @return String
   */
  public String getLeftParen2() {

	  return leftParen2;
  }

  /**
   * Set the leftParen1.
   * @param leftParen1 The leftParen to set
   */
  public void setLeftParen2(String leftParen2) {
	  this.leftParen2 = leftParen2;

  }

  /**
   * Returns the rightParen1.
   * @return String
   */
  public String getRightParen2() {

	  return rightParen2;
  }

  /**
   * Set the rightParen1.
   * @param rightParen1 The rightParen to set
   */
  public void setRightParen2(String rightParen2) {
	  this.rightParen2 = rightParen2;

  }

  /**
   * Returns the operatorType1.
   * @return String
   */
  public String getOperatorType2() {

	  return operatorType2;
  }

  /**
   * Set the operatorType1.
   * @param operatorType1 The operatorType to set
   */
  public void setOperatorType2(String operatorType2) {
	  this.operatorType2 = operatorType2;

  }

//***

  /**
   * Returns the queryName3.
   * @return String
   */
  public String getQueryName3() {

	  return queryName3;
  }

  /**
   * Set the queryName1.
   * @param queryName1 The queryName to set
   */
  public void setQueryName3(String queryName3) {
	  this.queryName3 = queryName3;

  }
  /**
   * Returns the leftParen1.
   * @return String
   */
  public String getLeftParen3() {

	  return leftParen3;
  }

  /**
   * Set the leftParen1.
   * @param leftParen1 The leftParen to set
   */
  public void setLeftParen3(String leftParen3) {
	  this.leftParen3 = leftParen3;

  }

  /**
   * Returns the rightParen1.
   * @return String
   */
  public String getRightParen3() {

	  return rightParen3;
  }

  /**
   * Set the rightParen1.
   * @param rightParen1 The rightParen to set
   */
  public void setRightParen3(String rightParen3) {
	  this.rightParen3 = rightParen3;

  }

  /**
  * Returns the queryName1.
  * @return String
  */
 public String getQueryText() {

	 return queryText;
 }

 /**
  * Set the queryName1.
  * @param queryName1 The queryName to set
  */
 public void setQueryText(String queryText) {
	 this.queryText = queryText;

 }

  
   /**
   * Returns the compoundView.
   * @return String
   */
  public String getCompoundView() {

	  return compoundView;
  }

  /**
   * Set the compoundView.
   * @param compoundView The compoundView to set
   */
  public void setCompoundView(String compoundView) {
	  this.compoundView = compoundView;

  }

  /**
  * Returns the resultsetName.
  * @return String
  */
 public String getResultsetName() {

	 return resultsetName;
 }

 /**
  * Set the resultsetName.
  * @param resultsetName The resultsetName to set
  */
 public void setResultsetName(String resultsetName) {
	 this.resultsetName = resultsetName;

 }

 public String getMethod() {

	 return "";
 }

 public void setMethod(String method) {

	 this.method = method;
 }

	public ArrayList getQueryNameColl(){
	   return queryNameColl;
	   }


	public ArrayList getCompoundViewColl(){
	   return compoundViewColl;
	   }

	public void setCompoundViewColl(ArrayList viewColl) {
		this.compoundViewColl = viewColl;
	}
	
}
