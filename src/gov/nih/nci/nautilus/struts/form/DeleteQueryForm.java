// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;

import gov.nih.nci.nautilus.constants.NautilusConstants;
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
public class DeleteQueryForm extends BaseForm {
   private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
   private String method = null;
   private String queryKey = null;
   
   public String getQueryKey()
   {
   		return queryKey;
   }
   
   public void setQueryKey(String str)
   {
   		queryKey = str;
   }
   
   public void setMethod(String method){   
    logger.debug("the method is :*************"+method);
    this.method = method;
	}	
  public String getMethod(){
    return this.method;
   }		
}
