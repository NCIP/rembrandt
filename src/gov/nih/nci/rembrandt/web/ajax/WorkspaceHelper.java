package gov.nih.nci.rembrandt.web.ajax;

import java.util.List;
import java.util.TreeMap;

import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

public class WorkspaceHelper {
	public WorkspaceHelper()	{}
	
	public static String fetchTreeStructures()	{
		String trees = "";
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpSession sess = req.getSession(); 
		
		
		////// NOTE: we want to read this from the DB, if null then create and persist
		//Create on FIRST TIME ONLY, if DB returns null
		//////// TODO: move this to a sep method
		
		//check the DB first (using session for testing), create initial struct if none exists
		if(sess.getAttribute("oListStruct")==null)	{

			////////////// TEST DATA String
			//trees = "[ { 'id' : 'root', 'txt' : 'Lists', 'editable': false, 'items' : [ { 'id' : 'ast', 'txt' : 'ASTROCYTOMA', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t21', 'txt' : 'high survival patients', 'img' : 'folder.gif', 'items' : [ { 'id': 'sub11', 'txt': 'my patient list', 'editable': false, 'acceptdrop' : false } ] }, { 'id' : 'branch_t22', 'txt' : 'ALL GLIOMA', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t23', 'txt' : 'MySaved Patients', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t24', 'txt' : 'Patients_good_survival', 'editable': false, 'acceptdrop' : false }, { 'id' : 'trash', 'last' : true, 'draggable' : false, 'txt' : 'Trash', 'img' : 'trash.gif', 'imgopen' : 'trash.gif', 'imgclose' : 'trash.gif', 'open' : false, 'editable': false, 'tooltip' : 'Items here will be removed when the workspace is saved', 'items' : [ { 'id': 'sub1', 'txt': 'one to delete' , 'editable': false, 'acceptdrop' : false } ] } ] } ]";
			
			//generate the Tree on the first time thru, and persist
			UserListBeanHelper ulbh = new UserListBeanHelper(sess.getId());
			List<UserList> uls = ulbh.getAllLists(); //getAllLists() for testing,  getAllCustomLists() for prod
			//convert this into the JSON format we need
			JSONArray jsa = new JSONArray();
			JSONObject root = new JSONObject();
			root.put("id", "root");
			root.put("txt", "Lists");
			root.put("editable", false);
			JSONArray rootItems = new JSONArray();
			//for each list, add it to the root folder, since they arent organized yet
			JSONObject theList = new JSONObject();
			for(UserList ul : uls){
				theList = new JSONObject();
				theList.put("id", ul.getName()); //can nodes contain spaces?
				theList.put("editable", false);
				theList.put("txt", ul.getName());
				theList.put("acceptDrop", false);
				theList.put("tooltip", ul.getListOrigin() + " " + ul.getListType() + " (" + ul.getItemCount() + ")");
				//TODO: set the style att for a CSS class that will color by list type?
				rootItems.add(theList);
			}
			//add the trash
			theList = new JSONObject();
			theList.put("id", "trash");
			theList.put("last", true);
	        theList.put("draggable", false);
	        theList.put("txt", "Trash");
	        theList.put("editable", false);
	        theList.put("img", "trash.gif");
	        theList.put("imgopen", "trash.gif");
	        theList.put("imgclose", "trash.gif");
	        theList.put("open", false);
	        theList.put("tooltip", "Items here will be removed when the workspace is saved");
			rootItems.add(theList);
	        root.put("items", rootItems);
			
	        jsa.add(root);
	        //need to do this below too, not on the array, on the first object
	        TreeMap theIndex = new TreeMap(root);
	        if(theIndex.containsValue("GBM"))	{
	        	System.out.println("***********  FOUND GBM ***************");
	        }
	        
	        trees = jsa.toString();
			//store it to DB when DAOs are ready
			sess.setAttribute("oListStruct", trees);
		}
		else	{
			//fetch from DB, using session for testing store
			trees = (String) sess.getAttribute("oListStruct");
		}
				
		return trees;
	}
	
	public static String saveTreeStructures(String treeString)	{
		//this should be an array or hash
		//for testing, its just 1 string now
		//need to decouple this from DWR, so other classes (i.e. logoutAction can call this and access the session)
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpSession sess = req.getSession(); 
		sess.setAttribute("oListStruct", treeString);
		return "pass";
	}

}
