// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package gov.nih.nci.nautilus.ui.struts.form;

import org.apache.log4j.Logger;


/** 
 * DeleteQueryForm.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 09-12-2004
 * 
 * XDoclet definition:
 * @struts:form name="DeleteQueryForm"
 */
public class DeleteQueryForm extends BaseForm {
   private static Logger logger = Logger.getLogger(DeleteQueryForm.class);
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
