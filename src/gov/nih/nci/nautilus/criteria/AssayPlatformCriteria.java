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

private AssayPlatformDE assayPlatformDE;
public AssayPlatformCriteria(){}

public void setAssayPlatformDE(AssayPlatformDE assayPlatformDE){
 if(assayPlatformDE != null){
   this.assayPlatformDE = assayPlatformDE;  
     }
}

public AssayPlatformDE getAssayPlatformDE(){
  return assayPlatformDE;
 }

public boolean isValid(){
  return true; 
 }

}
