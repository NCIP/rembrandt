package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.criteria.Criteria;

import java.util.*;

/**
 * This  class encapsulates AssayPlatform criteria.
 * It contains a collection of AssayPlatformDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class AssayPlatformCriteria extends Criteria{

 private Collection assayPlatforms;
 
 public AssayPlatformCriteria(){}
 
 // this is to deal with single assay platform entry
 public void setAssayPlatform(AssayPlatformDE assayPlatformDE){
   if(assayPlatformDE != null){
     getAssayPlatformMembers().add(assayPlatformDE);   
      }  
   }
 
 // this is to deal with a collection of AssayPlatformDE entry
  public void setAssayPlatforms(Collection multiAssayPlatforms){
    if(multiAssayPlatforms != null){
	   Iterator iter = multiAssayPlatforms.iterator();
	   while(iter.hasNext()){
	     AssayPlatformDE assayPlatformde = (AssayPlatformDE)iter.next();		
		 getAssayPlatformMembers().add(assayPlatformde);		    
	      }	    
	   } 
   }
 
 private Collection getAssayPlatformMembers(){
  if(assayPlatforms == null){
     assayPlatforms = new ArrayList();
   }
  return assayPlatforms;
  }
  
public Collection getAssayPlatforms(){
  return assayPlatforms;
   }

public boolean isValid() {
        // TODO: see if we need any validation on ArrayPlatform
       return true;
   }
}
