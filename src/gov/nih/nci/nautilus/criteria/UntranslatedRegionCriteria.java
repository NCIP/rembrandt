package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.UntranslatedRegionDE;
import gov.nih.nci.nautilus.criteria.Criteria;

import java.util.*;

/**
 * This  class encapsulates UntranslatedRegion criteria.
 * It contains a collection of UntranslatedRegionDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class UntranslatedRegionCriteria extends Criteria{

 private Collection untranslatedRegions;
 
 public UntranslatedRegionCriteria(){}
 
 // this is to deal with single UntranslatedRegion entry
 public void setUntranslatedRegion(UntranslatedRegionDE untranslatedRegionDE){
   if(untranslatedRegionDE != null){
     getUntranslatedRegionMembers().add(untranslatedRegionDE);   
      }  
   }
 
 // this is to deal with a collection of  UntranslatedRegionDE entry
  public void setUntranslatedRegions(Collection multiUntranslatedRegions){
    if(multiUntranslatedRegions != null){
	   Iterator iter = multiUntranslatedRegions.iterator();
	   while(iter.hasNext()){
	     UntranslatedRegionDE untranslatedRegionde = (UntranslatedRegionDE)iter.next();		
		 getUntranslatedRegionMembers().add(untranslatedRegionde);		    
	      }	    
	   } 
   }
 
 private Collection getUntranslatedRegionMembers(){
  if(untranslatedRegions == null){
     untranslatedRegions = new ArrayList();
   }
  return untranslatedRegions;
  }
  
public Collection getUntranslatedRegions(){
  return untranslatedRegions;
   }

public boolean isValid() {
        // TODO: see if we need any validation on untranslatedRegion
       return true;
   }
}
