package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.RadiationTherapyDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates RadiationTherapyDE criteria.
 * It contains a collection of RadiationTherapyDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class RadiationTherapyCriteria extends Criteria {

    private Collection radiationTherapies;
	public RadiationTherapyCriteria(){}
	
	// this is to deal with one RadiationTherapyDE object entry
	public void setRadiationTherapy(RadiationTherapyDE radiationTherapyDE){
	  if(radiationTherapyDE != null){
	     getRadiationTherapyMembers().add(radiationTherapyDE);	  
	     }	  
	  }
	
	// this is to deal w/ a collection of RadiationTherapyDE
	public void setRadiationTherapies(Collection multiRadiationTherapies){
	  if(multiRadiationTherapies != null){
	     Iterator iter = multiRadiationTherapies.iterator();
	     while(iter.hasNext()){
	        RadiationTherapyDE radiationTherapyde = (RadiationTherapyDE)iter.next();		
		    getRadiationTherapyMembers().add(radiationTherapyde);		    
	      }	    
	   } 
   }
	
   private Collection getRadiationTherapyMembers(){
     if(radiationTherapies == null){
	   radiationTherapies = new ArrayList();
	   }
	  return  radiationTherapies;
   }
   
   public Collection getRadiationTherapies() {
        return radiationTherapies;
    }
 
   public boolean isValid() {    
    // find out later to see if we need validate radiationTherapies
    return true;
    }
	
}
