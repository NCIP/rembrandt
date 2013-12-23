package gov.nih.nci.rembrandt.web.struts2.form;


import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.rembrandt.util.MoreStringUtils;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

public class GpIntegrationForm implements RootForm {
	 private static Logger logger = Logger.getLogger(GpIntegrationForm.class);
	 private List existingGroupsList;
	 private String analysisResultName = "";
	 private String [] existingGroups ;
	 protected String [] selectedGroups;
	 private String groupsOption;
	 private String arrayPlatform = "";
	   
	  
	  

	public String getArrayPlatform() {
		return arrayPlatform;
	}

	public void setArrayPlatform(String arrayPlatform) {
		this.arrayPlatform = arrayPlatform;
	}

	public String getGroupsOption() {
		return groupsOption;
	}

	public void setGroupsOption(String groupsOption) {
		this.groupsOption = groupsOption;
	}

	public String[] getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(String[] selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public List getExistingGroupsList() {
		return existingGroupsList;
	}

	public void setExistingGroupsList(List existingGroupsList) {
		this.existingGroupsList = existingGroupsList;
	}

	public String getAnalysisResultName() {
		return analysisResultName;
	}

	public void setAnalysisResultName(String analysisResultName) {
		if (analysisResultName != null )
			analysisResultName = MoreStringUtils.cleanJavascript(analysisResultName);

		this.analysisResultName = analysisResultName;
	}

	public String[] getExistingGroups() {
		return existingGroups;
	}

	public void setExistingGroups(String[] existingGroups) {
		this.existingGroups = existingGroups;
	}

   public void reset(ActionMapping mapping, HttpServletRequest request) {            
	        arrayPlatform = "";
	        
	        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
	       GroupRetriever groupRetriever = new GroupRetriever();
	        setExistingGroupsList(groupRetriever.getClinicalGroupsCollection(request.getSession()));         
	    
	  }
   
   /**
    * Method validate
    * 
    * @param ActionMapping
    *            mapping
    * @param HttpServletRequest
    *            request
    * @return ActionErrors
    */
/*	public ActionErrors validate(ActionMapping mapping,
           HttpServletRequest request) {

       ActionErrors errors = new ActionErrors();

       // Analysis name cannot be blank
       errors = UIFormValidator.validateQueryName(analysisResultName, errors);
      
      // Validate group field
       errors = UIFormValidator.validateSelectedGroups(selectedGroups,2, errors);
       
      
       return errors;
   }  
  */
   
       
  
}

