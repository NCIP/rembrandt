/**
 * The UserList will define an uploaded list by name, the type of list and the 
 * contained list itself.
 */
package gov.nih.nci.rembrandt.web.bean;
//package gov.nih.nci.caintegrator.application.lists;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.ComparativeGenomicForm;
import gov.nih.nci.rembrandt.web.struts.form.GeneExpressionForm;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.action.ActionForm;

/**
 * @author rossok
 *
 */
public class RembrandtUserList implements Serializable,Cloneable { 
    private String name = "";
    private ListType listType;
    //a list can actually have several sub-types
    private List<ListSubType> listSubTypes;
    //user attached notes for a list
    private String notes;
    private List<String> list = new ArrayList<String>();
    private List<String> invalidList = new ArrayList<String>();
    private Date dateCreated;
    private int itemCount = 0;
   
    public RembrandtUserList(){}
    
    //base constructor: no subtype, no notes
    public RembrandtUserList(String name, ListType listType, List<String> list, List<String> invalidList, Date dateCreated){
        this.name = name;
        this.listType = listType;
        //Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        this.list = list;
        this.invalidList = invalidList;
        this.dateCreated = dateCreated;
        this.itemCount = list.size();
    }
    
    //full constructor
    public RembrandtUserList(UserList userList)	{
        this.name = userList.getName();
        this.listType = userList.getListType();
        this.listSubTypes = userList.getListSubType();
        //Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        this.list = userList.getList();
        this.invalidList = userList.getInvalidList();
        this.dateCreated = userList.getDateCreated();
        this.itemCount = userList.getItemCount(); 
        this.notes = userList.getNotes();
    }

    
    /**
     * @return Returns the dateCreated.
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * @param dateCreated The dateCreated to set.
     * @throws ParseException 
     */
    public void setDateCreated(Date dateCreated) throws ParseException {
        this.dateCreated = dateCreated;
    }
    /**
     * @return Returns the itemCount.
     */
    public int getItemCount() {
        return itemCount;
    }
    /**
     * @param itemCount The itemCount to set.
     */
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    /**
     * @return Returns the list.
     */
    public List<String> getList() {
        return list;
    }
    /**
     * @param list The list to set.
     */
    public void setList(List<String> list) {
        this.list = list;
    }
    /**
     * @return Returns the listType.
     */
    public ListType getListType() {
        return listType;
    }
    /**
     * @param listType The listType to set.
     */
    public void setListType(ListType listType) {
        this.listType = listType;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    /**
     * @return Returns the invalidList.
     */
    public List<String> getInvalidList() {
        return invalidList;
    }
    /**
     * @param invalidList The invalidList to set.
     */
    public void setInvalidList(List<String> invalidList) {
        this.invalidList = invalidList;
    }

	public List<ListSubType> getListSubType() {
		return listSubTypes;
	}

	public void setListSubType(List<ListSubType> listSubTypes) {
		this.listSubTypes = listSubTypes;
	}
	
	//conv. in case you only want to add one
	public void setListSubType(ListSubType listSubType){
		//if(this.listSubTypes.isEmpty())	{
			ArrayList<ListSubType> st = new ArrayList();
			st.add(listSubType);
			setListSubType(st);
	//	}
	//	else if(!this.listSubTypes.contains(listSubType))	{
	//		this.listSubTypes.add(listSubType);
	//	}
	}
	
	public boolean hasSubType(ListSubType listSubType)	{
		if(listSubTypes.contains(listSubType))	{
			return true;
		}
		return false;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public UserList getUserList(){
		UserList userList = 
	    	new UserList(this.getName(),
			        	this.getListType(),
			        	this.getListSubType(),
			        	this.getList(),
			        	this.getInvalidList(),
			        	this.getDateCreated());
		userList.setNotes(this.getNotes());
		
		return userList;
	}

    /**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
/*
	public Object clone() {
		RembrandtUserList myUserList = null;
		
		myUserList = new RembrandtUserList();
		Map<String, Query> clonedQueryMap = null;
	    if(queryMap != null){
	    	clonedQueryMap = new TreeMap<String, Query>();
        	Set keys = queryMap.keySet(); 
    		for(Object elementKey: keys) {
    			Query it = queryMap.get(elementKey);
    			Query q = (Query)it;
    			Query itClone = (Query)q.clone();
    			clonedQueryMap.put((String)elementKey,itClone);
    		}
        }
	    
	    
	    private String name = "";
	    private ListType listType;
	    //a list can actually have several sub-types
	    private List<ListSubType> listSubTypes;
	    //user attached notes for a list
	    private String notes;
	    private List<String> list = new ArrayList<String>();
	    private List<String> invalidList = new ArrayList<String>();
	    private Date dateCreated;
	    private int itemCount = 0;
	    
	    myClone.queryMap = clonedQueryMap;
	    Map<String,ActionForm> clonedformBeanMap = null;
	    if(formBeanMap != null){
	    	clonedformBeanMap = new HashMap<String,ActionForm>();
        	Set keys = formBeanMap.keySet(); 
    		for(Object elementKey: keys) {
    			ActionForm it = formBeanMap.get(elementKey);
    			ActionForm itClone = null;
    			if(it instanceof GeneExpressionForm) {
    				GeneExpressionForm gef = (GeneExpressionForm)it;
    				itClone = gef.cloneMe();
    			}else if(it instanceof ClinicalDataForm) {
    				ClinicalDataForm cdf = (ClinicalDataForm)it;
    				itClone = cdf.cloneMe();
    			}else if(it instanceof ComparativeGenomicForm) {
    				ComparativeGenomicForm cgf = (ComparativeGenomicForm)it;
    				itClone = cgf.cloneMe();
    			}else {
    				logger.error("Unsupported FormType to clone");
    			}
    			clonedformBeanMap.put((String)elementKey,itClone);
    		}
    		 myClone.formBeanMap = clonedformBeanMap;
        }
		return myClone;
	}
	*/
}
