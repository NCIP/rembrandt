package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.PathwayDE;
import gov.nih.nci.nautilus.criteria.Criteria;

import java.util.*;

/**
 * This  class encapsulates Pathway criteria.
 * It contains a collection of PathwayDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class PathwayCriteria extends Criteria{

 private Collection pathways;
 
 public PathwayCriteria(){}
 
 // this is to deal with single pathway entry
 public void setPathway(PathwayDE pathwayDE){
   if(pathwayDE != null){
     getPathwayMembers().add(pathwayDE);   
      }  
   }
 
 // this is to deal with multiple pathway entries
  public void setPathways(Collection multiPathways){
    if(multiPathways != null){
	   Iterator iter = multiPathways.iterator();
	   while(iter.hasNext()){
	     PathwayDE pathwayde = (PathwayDE)iter.next();		
		 getPathwayMembers().add(pathwayde);		    
	      }	    
	   } 
   }
 
 private Collection getPathwayMembers(){
  if(pathways == null){
     pathways = new ArrayList();
   }
  return pathways;
  }
  
public Collection getPathways(){
  return pathways;
   }

public boolean isValid() {
        // TODO: see if we need any validation on pathway
       return true;
   }
}
