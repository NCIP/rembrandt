package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;


/** 
* DeleteQueryForm.java created by EasyStruts - XsltGen.
* http://easystruts.sf.net
* created on 09-12-2004
* 
* XDoclet definition:
* @struts:form name="DeleteQueryForm"
*/
public class QuickSearchForm extends BaseForm {
private String plot = null;
private String quickSearchName = null;

public String getPlot()
{
		return plot;
}

public void setPlot(String str)
{
		plot = str;
}

public void setQuickSearchName(String str){   
 this.quickSearchName = str;
	}	
public String getQuickSearchName(){
 return this.quickSearchName;
}		

public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

			ActionErrors errors = new ActionErrors();

			return errors;

}

}
