package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ChemoAgentDE;

/**
 * This  class encapsulates ChemoAgentDE criteria.
 * It contains a collection of ChemoAgentDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class ChemoAgentCriteria extends Criteria {
    private ChemoAgentDE chemoAgentDE;   
	public ChemoAgentCriteria(){}
	
	public void setChemoAgentDE(ChemoAgentDE chemoAgentDE){
	   if(chemoAgentDE != null){
	    this.chemoAgentDE = chemoAgentDE;
	   }
	 } 
	
	public ChemoAgentDE getChemoAgentDE(){
	  return chemoAgentDE;
	  }
	 
	public boolean isValid(){
	  return true;
	 }  
}
