package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.struts.form.GpIntegrationForm;
import gov.nih.nci.rembrandt.web.struts.form.IgvIntegrationForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

public class IgvIntegrationAction extends LookupDispatchAction {
	
	private static Logger logger = Logger.getLogger(GPIntegrationAction.class);
	 
	public ActionForward setup(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	    	IgvIntegrationForm igvForm = (IgvIntegrationForm) form;
	        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
	        GroupRetriever groupRetriever = new GroupRetriever();
	        igvForm.setExistingGroupsList(groupRetriever.getClinicalGroupsCollection(request.getSession()));
	        igvForm.setAnnotationsList(getAnnotationsCollection());  
	    
	        return mapping.findForward("success");
	    }

	@Override
	protected Map getKeyMethodMap() {
		 
       HashMap<String,String> map = new HashMap<String,String>();
              
       //Setup
       map.put("IgvIntegrationAction.setup", "setup");

       //Submit Query Button using class comparison submittal method
       map.put("buttons_tile.submittalButton", "submit");

       return map;
       
	   }
	
	private List<LabelValueBean> getAnnotationsCollection(){
		List<LabelValueBean> annotationsCollection = new ArrayList<LabelValueBean>();
		
		annotationsCollection.add(new LabelValueBean("Subject ID", "subject_id"));
		annotationsCollection.add(new LabelValueBean("Disease Type", "disease_type"));
		annotationsCollection.add(new LabelValueBean("Whole Grade", "whole_grade"));
		annotationsCollection.add(new LabelValueBean("Age Range", "age_range"));
		annotationsCollection.add(new LabelValueBean("Gender", "gender"));
		annotationsCollection.add(new LabelValueBean("Range", "survival_length_range"));
		annotationsCollection.add(new LabelValueBean("Censoring Status", "censoring_status"));
		
        return annotationsCollection;
    
    }

    @SuppressWarnings("unchecked")
	public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
     
	   IgvIntegrationForm igvForm = (IgvIntegrationForm) form;
	   String sessionId = request.getSession().getId();
       HttpSession session = request.getSession();
       
   	   String[] patientGroups = igvForm.getSelectedGroups();
    	return mapping.findForward("viewJob");
    }
	
}
