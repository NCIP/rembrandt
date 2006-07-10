package gov.nih.nci.rembrandt.web.helper;

import java.util.List;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;

public class RembrandtListValidator implements ListValidator{
	private ListType listType;
	private ListSubType listSubType;
	private List<String> unvalidatedList;
	private List<String> validatedList;
	public RembrandtListValidator(){};
	public RembrandtListValidator(ListSubType listSubType, ListType listType, List<String> unvalidatedList) {
		super();
		this.listSubType = listSubType;
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
	}

    public List getValidList(ListType listType, List<String> unvalidatedList) {
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return unvalidatedList;
    }

    public List getInvalidList(ListType listType, List<String> unvalidatedList) {
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return unvalidatedList;
    }

	public List getValidList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getInvalidList() {
		// TODO Auto-generated method stub
		return null;
	}

}
