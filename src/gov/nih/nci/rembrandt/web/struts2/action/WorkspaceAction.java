package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.rembrandt.web.struts2.form.ImportWorkspaceForm;

import com.opensymphony.xwork2.ActionSupport;

public class WorkspaceAction extends ActionSupport {
	
	ImportWorkspaceForm form;
	
	public String manageLists() throws Exception {
		
		return "manageLists";
	}

	public String organize() throws Exception {
		
		return "organizeWorkspace";
	}
	
	public String importWorkspace() throws Exception {
		
		form = new ImportWorkspaceForm();
		return "importWorkspace";
	}
	
	public String exportWorkspace() throws Exception {
		
		return "exportWorkspace";
	}

	public ImportWorkspaceForm getForm() {
		return form;
	}

	public void setForm(ImportWorkspaceForm form) {
		this.form = form;
	}
	
	
}
