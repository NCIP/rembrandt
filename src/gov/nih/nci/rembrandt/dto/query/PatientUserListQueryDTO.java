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
        UserListBeanHelper helper = new UserListBeanHelper(session);
        patientDIDs = helper.getItemsFromList(this.queryName);        
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
