package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SurgeryTypeDE;

/**
 * This  class encapsulates SurgeryTypeDE criteria.
 * It contains a collection of SurgeryTypeDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class SurgeryTypeCriteria extends Criteria {

    private SurgeryTypeDE surgeryTypeDE;
	public SurgeryTypeCriteria(){}
	
	public void setSurgeryTypeDE(SurgeryTypeDE surgeryTypeDE){
	  if(surgeryTypeDE != null){
	    this.surgeryTypeDE = surgeryTypeDE; 
	   }
	 }

	public SurgeryTypeDE getSurgeryTypeDE(){
	 return surgeryTypeDE;
	 }  
 
   public boolean isValid() {    
    // find out later to see if we need validate SurgeryTypes
    return true;
    }
}
