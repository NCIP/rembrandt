package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class GroupRetriever {
    private List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();
    private static List<String> geneGroupsCollection = new ArrayList<String>();
    
    
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public List<LabelValueBean> getClinicalGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        
        for(UserList patientList: patientLists){
            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        }
        return clinicalGroupsCollection;
    
    }
    
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types, but does not list value in canonical form like the method above.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public List<LabelValueBean> getClinicalGroupsCollectionNoPath(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        
        for(UserList patientList: patientLists){
            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getName()));
        }
        return clinicalGroupsCollection;
    
    }
    
    public static List<String> getGeneGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> geneLists = helper.getLists(ListType.Gene);
        
        for(UserList geneList: geneLists){
            geneGroupsCollection.add(geneList.getName());
        }
        return geneGroupsCollection;
    
    }
}
