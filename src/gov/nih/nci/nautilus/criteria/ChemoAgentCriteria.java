package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ChemoAgentDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates ChemoAgentDE criteria.
 * It contains a collection of ChemoAgentDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class ChemoAgentCriteria extends Criteria {

    private Collection chemoAgents;
	public ChemoAgentCriteria(){}
	
	// this is to deal with one ChemoAgentDE object entry
	public void setRadiationTherapy(ChemoAgentDE chemoAgentDE){
	  if(chemoAgentDE != null){
	     getChemoAgentMembers().add(chemoAgentDE);	  
	     }	  
	  }
	
	// this is to deal w/ a collection of ChemoAgentDE
	public void setChemoAgent(Collection multiChemoAgent){
	  if(multiChemoAgent != null){
	     Iterator iter = multiChemoAgent.iterator();
	     while(iter.hasNext()){
	        ChemoAgentDE chemoAgentde = (ChemoAgentDE)iter.next();		
		    getChemoAgentMembers().add(chemoAgentde);		    
	      }	    
	   } 
   }
	
   private Collection getChemoAgentMembers(){
     if(chemoAgents == null){
	   chemoAgents = new ArrayList();
	   }
	  return  chemoAgents;
   }
   
   public Collection getChemoAgents() {
        return chemoAgents;
    }
 
   public boolean isValid() {    
    // find out later to see if we need validate chemoAgents
    return true;
    }
	
}
