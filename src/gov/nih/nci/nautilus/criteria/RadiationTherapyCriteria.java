package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.RadiationTherapyDE;

/**
 * This  class encapsulates RadiationTherapyDE criteria.
 * It contains a collection of RadiationTherapyDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */



public class RadiationTherapyCriteria extends Criteria {

    private RadiationTherapyDE radiationTherapyDE;
	public RadiationTherapyCriteria(){}
	
	public void setRadiationTherapyDE(RadiationTherapyDE radiationTherapyDE){
	 if(radiationTherapyDE != null){
	   this.radiationTherapyDE = radiationTherapyDE;
	   }
	 }
	
	public  RadiationTherapyDE getRadiationTherapyDE(){
	  return radiationTherapyDE;
	 }
    
	public boolean isValid(){
	  return true;
	 } 
	
}
