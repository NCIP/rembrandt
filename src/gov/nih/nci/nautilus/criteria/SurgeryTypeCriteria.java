package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SurgeryTypeDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates SurgeryTypeDE criteria.
 * It contains a collection of SurgeryTypeDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class SurgeryTypeCriteria extends Criteria {

    private Collection surgeryTypes;
	public SurgeryTypeCriteria(){}
	
	// this is to deal with one SurgeryTypeDE object entry
	public void setSurgeryType(SurgeryTypeDE surgeryTypeDE){
	  if(surgeryTypeDE != null){
	     getSurgeryTypeMembers().add(surgeryTypeDE);	  
	     }	  
	  }
	
	// this is to deal w/ a collection of SurgeryTypeDE
	public void setChemoAgent(Collection multiSurgeryTypes){
	  if(multiSurgeryTypes != null){
	     Iterator iter = multiSurgeryTypes.iterator();
	     while(iter.hasNext()){
	        SurgeryTypeDE surgeryTypede = (SurgeryTypeDE)iter.next();		
		    getSurgeryTypeMembers().add(surgeryTypede);		    
	      }	    
	   } 
   }
	
   private Collection getSurgeryTypeMembers(){
     if(surgeryTypes == null){
	   surgeryTypes = new ArrayList();
	   }
	  return  surgeryTypes;
   }
   
   public Collection getSurgeryTypes() {
        return surgeryTypes;
    }
 
   public boolean isValid() {    
    // find out later to see if we need validate SurgeryTypes
    return true;
    }
	
}
