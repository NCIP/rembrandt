/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import gov.nih.nci.rembrandt.web.bean.LabelValueBean;

public class GroupRetriever {
    private List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();
    private List<LabelValueBean> geneGroupsCollection = new ArrayList<LabelValueBean>();
    private List<LabelValueBean> cloneGroupsCollection = new ArrayList<LabelValueBean>();
    private List<LabelValueBean> snpGroupsCollection = new ArrayList<LabelValueBean>();

    
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
    
    public List<LabelValueBean> getGeneGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> geneLists = helper.getLists(ListType.Gene);
        
        for(UserList geneList: geneLists){
            geneGroupsCollection.add(new LabelValueBean(geneList.getName(),geneList.getName()));
            
        }
        return geneGroupsCollection;
    
    }
    
    public List<LabelValueBean> getGeneGroupsSubTypeCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> geneLists = helper.getLists(ListType.Gene, ListSubType.GENESYMBOL);
        
        for(UserList geneList: geneLists){
            geneGroupsCollection.add(new LabelValueBean(geneList.getName(),geneList.getName()));
            
        }
        return geneGroupsCollection;
    
    }

    
    public List<LabelValueBean> getCloneGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<ListSubType> lst = new ArrayList();
        lst.add(ListSubType.IMAGE_CLONE);
        lst.add(ListSubType.AFFY_HGU133PLUS2_PROBE_SET);
        List<UserList> rLists = helper.getLists(ListType.Reporter,lst);
        
        for(UserList rList: rLists){
            cloneGroupsCollection.add(new LabelValueBean(rList.getName(),rList.getName()));
        }
        return cloneGroupsCollection;
    
    } 
    
    public List<LabelValueBean> getSnpGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<ListSubType> lst = new ArrayList();
        lst.add(ListSubType.AFFY_100K_SNP_PROBE_SET);
        lst.add(ListSubType.DBSNP);
        List<UserList> rLists = helper.getLists(ListType.Reporter,lst);
        for(UserList rList: rLists){
            snpGroupsCollection.add(new LabelValueBean(rList.getName(),rList.getName()));
        }
        return snpGroupsCollection;
    
    }
}
