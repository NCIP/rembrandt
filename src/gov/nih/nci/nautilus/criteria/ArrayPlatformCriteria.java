package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.criteria.Criteria;

import java.util.*;

/**
 * This  class encapsulates ArrayPlatform criteria.
 * It contains a collection of ArrayPlatformDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class ArrayPlatformCriteria extends Criteria{

 private Collection arrayPlatforms;
 
 public ArrayPlatformCriteria(){}
 
 // this is to deal with single array platform entry
 public void setArrayPlatform(ArrayPlatformDE arrayPlatformDE){
   if(arrayPlatformDE != null){
     getArrayPlatformMembers().add(arrayPlatformDE);   
      }  
   }
 
 // this is to deal with a collection of  ArrayPlatformDE entry
  public void setArrayPlatforms(Collection multiArrayPlatforms){
    if(multiArrayPlatforms != null){
	   Iterator iter = multiArrayPlatforms.iterator();
	   while(iter.hasNext()){
	     ArrayPlatformDE arrayPlatformde = (ArrayPlatformDE)iter.next();		
		 getArrayPlatformMembers().add(arrayPlatformde);		    
	      }	    
	   } 
   }
 
 private Collection getArrayPlatformMembers(){
  if(arrayPlatforms == null){
     arrayPlatforms = new ArrayList();
   }
  return arrayPlatforms;
  }
  
public Collection getArrayPlatforms(){
  return arrayPlatforms;
   }

public boolean isValid() {
        // TODO: see if we need any validation on ArrayPlatform
       return true;
   }
}
