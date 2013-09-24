/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.test;

import java.util.List;

import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.rembrandt.util.RembrandtListLoader;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class ListManagementTest extends
        AbstractDependencyInjectionSpringContextTests {

    private HibernateTemplate hibernateTemplate;
    private RembrandtListLoader listLoader;
    
    
    /**
     * @return Returns the listLoader.
     */
    public RembrandtListLoader getListLoader() {
        return listLoader;
    }

    /**
     * @param listLoader The listLoader to set.
     */
    public void setListLoader(RembrandtListLoader listLoader) {
        this.listLoader = listLoader;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public String[] getConfigLocations() {
        return new String[] {
                "file:C:/dev/rembrandt/test/applicationContext-junit.xml"};
    }

    public void testListRetrieval() {
        String institutionName = "NOB";
        List<UserList> userLists = (List<UserList>)this.listLoader.loadUserListsByInstitution(institutionName);
        for(UserList l: userLists){
            System.out.println(l.getName());
        }
                

    }
   
}
