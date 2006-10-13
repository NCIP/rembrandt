/**
 * The userlist bean contains all lists created by the user and has session
 * scope. It is accessed by the UserListBean Helper
 */

package gov.nih.nci.rembrandt.web.bean;
//package gov.nih.nci.caintegrator.application.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rossok
 *
 */
public class RembrandtUserListBean implements Serializable,Cloneable {

    List<RembrandtUserList> rembrandtUserLists = new ArrayList<RembrandtUserList>();
    
    public void addList(RembrandtUserList userList){
   
    	rembrandtUserLists.add(userList);
    }

    public void removeList(String listName){       
        for(RembrandtUserList list: rembrandtUserLists){        
           if(list.getName().equals(listName)){
        	   rembrandtUserLists.remove(list);
           break;
          }
        }
    }
    
    public List<RembrandtUserList> getEntireList(){
        return rembrandtUserLists;
    }
    
    public RembrandtUserList getList(String listName){
        for(RembrandtUserList list : rembrandtUserLists){
            if(list.getName().equals(listName)){
                return list;
            }            
        }
        return null;
        
    }

}
