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

 private ArrayPlatformDE arrayPlatformDE;
 public ArrayPlatformCriteria(){}
 
 public void setArrayPlatform(ArrayPlatformDE arrayPlatformDE){
  if(arrayPlatformDE != null){
     this.arrayPlatformDE = arrayPlatformDE;
    }
  }

 public ArrayPlatformDE getArrayPlatformDE(){
   return  arrayPlatformDE;
   }
 
 public boolean isValid(){
   return true;    
   }
}
