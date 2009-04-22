package gov.nih.nci.rembrandt.web.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.application.lists.ListOrigin;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.workspace.TreeStructureType;
import gov.nih.nci.caintegrator.application.workspace.Workspace;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.util.RembrandtListLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

public class WorkspaceHelper {
    private static RembrandtListLoader myListLoader = (RembrandtListLoader) SpringContext.getBean("listLoader");

	public WorkspaceHelper()	{}
	public static Workspace fetchWorkspaceFromDB(Long userId){
		Workspace workspace = null;
		if(userId != null){
		 workspace = myListLoader.loadTreeStructure(userId, TreeStructureType.LIST);
		}		 
		return workspace;
	}
	public static String fetchTreeStructures()	{
		String trees = "";
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpSession sess = req.getSession(); 
		JSONArray jsa = generateJSONArray(sess);

		trees = jsa.toString();

		//store it to DB when DAOs are ready
		sess.setAttribute(RembrandtConstants.OLIST_STRUCT, trees);
		//}
				
		return trees;
	}

	public static JSONArray generateJSONArray(HttpSession sess) {
		String trees;
		UserCredentials credentials = (UserCredentials)sess.getAttribute(RembrandtConstants.USER_CREDENTIALS);
		
		////// NOTE: we want to read this from the DB, if null then create and persist
		Workspace workspace = fetchWorkspaceFromDB(credentials.getUserId());
		if(workspace != null  && workspace.getTreeStructure()!= null)	{
				trees = workspace.getTreeStructure();
			
			sess.setAttribute(RembrandtConstants.WORKSPACE, workspace);
			}else{
				trees = null;
		}
		//check the DB first (using session for testing), create initial struct if none exists
		//if(trees == null)	{

			////////////// TEST DATA String
			//trees = "[ { 'id' : 'root', 'txt' : 'Lists', 'editable': false, 'items' : [ { 'id' : 'ast', 'txt' : 'ASTROCYTOMA', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t21', 'txt' : 'high survival patients', 'img' : 'folder.gif', 'items' : [ { 'id': 'sub11', 'txt': 'my patient list', 'editable': false, 'acceptdrop' : false } ] }, { 'id' : 'branch_t22', 'txt' : 'ALL GLIOMA', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t23', 'txt' : 'MySaved Patients', 'editable': false, 'acceptdrop' : false }, { 'id' : 'branch_t24', 'txt' : 'Patients_good_survival', 'editable': false, 'acceptdrop' : false }, { 'id' : 'trash', 'last' : true, 'draggable' : false, 'txt' : 'Trash', 'img' : 'trash.gif', 'imgopen' : 'trash.gif', 'imgclose' : 'trash.gif', 'open' : false, 'editable': false, 'tooltip' : 'Items here will be removed when the workspace is saved', 'items' : [ { 'id': 'sub1', 'txt': 'one to delete' , 'editable': false, 'acceptdrop' : false } ] } ] } ]";
			
			//generate the Tree on the first time thru, and persist
			UserListBeanHelper ulbh = new UserListBeanHelper(sess.getId());
			List<UserList> uls = ulbh.getAllLists(); //getAllLists() for testing,  getAllCustomLists() for prod
			//convert this into the JSON format we need
			JSONArray jsa = new JSONArray();
			JSONObject root = null;
			JSONObject customList = null;
			JSONArray customItems = null;
			JSONArray rootItems = null;
			if(trees != null){
				List<String> listNames = new ArrayList<String>();
				Object obj=JSONValue.parse(trees);
				jsa=(JSONArray)obj;
				Iterator jsa_iterator = jsa.iterator();
				while(jsa_iterator.hasNext()){
					obj = jsa_iterator.next();
					if( obj instanceof JSONObject){
						root = (JSONObject)obj;
						if(root.containsValue("Lists")){
							rootItems = (JSONArray) root.get("items");
							Iterator root_iterator = rootItems.iterator();
							while(root_iterator.hasNext()){
								obj = root_iterator.next();
								if( obj instanceof JSONObject){
									JSONObject jsaObj = (JSONObject)obj;
									String txt = (String)jsaObj.get("txt");
									String id = (String)jsaObj.get("id");
									if(txt != null && id !=null && txt.equals(id)){
										listNames.add(id);
									}
									if(jsaObj.containsValue("Custom Lists")){
										customList = jsaObj;
										customItems = (JSONArray) customList.get("items");
									}
								}
							}
						}
					}
				}
				JSONArray newItems = new JSONArray();
				for(String name: listNames){
						for(UserList ul : uls){
							if(ul.getListOrigin().equals(ListOrigin.Custom)){
								if(!name.equals(ul.getName())){
										JSONObject theList = new JSONObject();
										theList.put("id", ul.getName()); //can nodes contain spaces?
										theList.put("editable", false);
										theList.put("txt", ul.getName());
										theList.put("acceptDrop", false);
										theList.put("tooltip", ul.getListOrigin() + " " + ul.getListType() + " (" + ul.getItemCount() + ")");
										//TODO: set the style att for a CSS class that will color by list type?
										newItems.add(theList);
									}
								}
							}
					}
				customItems.addAll(newItems);
				
			}else {
			root= new JSONObject();
			root.put("id", "root");
			root.put("txt", "Lists");
			root.put("editable", false);
			rootItems = new JSONArray();
			//for each list, add it to the root folder, since they arent organized yet
			customList = new JSONObject();
			customList.put("id", "custom");
			customList.put("txt", "Custom Lists");
			customList.put("editable", false);
			customItems = new JSONArray();
			for(UserList ul : uls){
				if(ul.getListOrigin().equals(ListOrigin.Custom)){
					//if(!jsaObj.containsValue(ul.getName())){
							JSONObject theList = new JSONObject();
							theList.put("id", ul.getName()); //can nodes contain spaces?
							theList.put("editable", false);
							theList.put("txt", ul.getName());
							theList.put("acceptDrop", false);
							theList.put("tooltip", ul.getListOrigin() + " " + ul.getListType() + " (" + ul.getItemCount() + ")");
							//TODO: set the style att for a CSS class that will color by list type?
							customItems.add(theList);
					//	}
					}
			}
			customList.put("items", customItems);
			rootItems.add(customList);
	        root.put("items", customItems);
			//add the trash
	        JSONObject trashList = new JSONObject();
			trashList.put("id", "trash");
			trashList.put("last", true);
	        trashList.put("draggable", false);
	        trashList.put("txt", "Trash");
	        trashList.put("editable", false);
	        trashList.put("img", "trash.gif");
	        trashList.put("imgopen", "trash.gif");
	        trashList.put("imgclose", "trash.gif");
	        trashList.put("open", false);
	        trashList.put("tooltip", "Items here will be removed when the workspace is saved");
			rootItems.add(trashList);
	        root.put("items", rootItems);
			
	        jsa.add(root);
	}
		return jsa;
	}
	
	public static String saveTreeStructures(String treeString)	{
		//this should be an array or hash
		//for testing, its just 1 string now
		//need to decouple this from DWR, so other classes (i.e. logoutAction can call this and access the session)
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpSession sess = req.getSession(); 
		sess.setAttribute(RembrandtConstants.OLIST_STRUCT, treeString);
		saveWorkspace( req.getSession().getId(), req, sess );
		return "pass";
	}

	public static String saveWorkspace(String sessionId, HttpServletRequest req, HttpSession sess ){
		/*
    	 * User has selected to save the current session and log out of the
    	 * application
    	 */
    	UserCredentials credentials = (UserCredentials)req.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
    	
    	/*******************************************************************
    	 * This is only here to prevent a user logged in on the public
    	 * account "RBTuser" from persisting their session
    	 *******************************************************************/
    	if(!"RBTuser".equals(credentials.getUserName())) {
    		//Check to see user also created some custom lists.

    		UserListBeanHelper userListBeanHelper = new UserListBeanHelper(req.getSession().getId());
    		
    		//UserListBean userListBean = (UserListBean) session.getAttribute(CacheConstants.USER_LISTS);
    		
    		List<UserList> customLists = userListBeanHelper.getAllCustomLists();
    		if (!customLists.isEmpty()){
    			myListLoader.saveUserCustomLists(req.getSession().getId(), credentials.getUserName());        			
    		}
    		List<UserList> removedLists = userListBeanHelper.getAllDeletedCustomLists();
    		if (!removedLists.isEmpty()){
    			myListLoader.deleteUserCustomLists(req.getSession().getId(), credentials.getUserName());        			
    		}
    		//get Tree from session to save to DB
			String tree = (String) req.getSession().getAttribute(RembrandtConstants.OLIST_STRUCT);
			Workspace workspace = (Workspace) req.getSession().getAttribute(RembrandtConstants.WORKSPACE);
			Long userId = credentials.getUserId();
			//Save Lists
			if(tree != null && userId != null){
				myListLoader.saveTreeStructure(userId, TreeStructureType.LIST, tree, workspace);
			}
			return "pass";
    	}
		return "fail";
	}
}
