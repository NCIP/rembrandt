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

private PathwayDE pathwayDE;
public PathwayCriteria(){}

public void setPathwayDE(PathwayDE pathwayDE){
 if(pathwayDE != null){
  this.pathwayDE = pathwayDE;
   }
 }
public PathwayDE getPathwayDE(){
  return pathwayDE;
 }
public boolean isValid(){
  return true;
 }
 
}
