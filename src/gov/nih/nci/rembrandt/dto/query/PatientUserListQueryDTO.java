/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class PatientUserListQueryDTO implements ClinicalQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String queryName;    
    private boolean isBaseline = false;	
	private List<String> patientDIDs = new ArrayList<String>();
    
	
	public PatientUserListQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    public PatientUserListQueryDTO(HttpSession session, String listName) {
        super();
        setQueryName(listName);
        setPatientDIDs(session);
    }
	
    public void setPatientDIDs(HttpSession session){        
        UserListBeanHelper helper = new UserListBeanHelper(session.getId());
        patientDIDs = helper.getItemsFromList(this.queryName);        
    }
    public void setPatientDIDs(List<String> sampleIds){        
        patientDIDs = sampleIds;        
    }
    
    /**
     * @return Returns the patientDIDs.
     */
    public List<String> getPatientDIDs() {
        return patientDIDs;
    }

	public void setQueryName(String name) {
	  this.queryName = name;		
	}


	public String getQueryName() {
	  return queryName;
	}


    /**
     * @return Returns the isBaseline.
     */
    public boolean isBaseline() {
        return isBaseline;
    }


    /**
     * @param isBaseline The isBaseline to set.
     */
    public void setBaseline(boolean isBaseline) {
        this.isBaseline = isBaseline;
    }
    
    
}
