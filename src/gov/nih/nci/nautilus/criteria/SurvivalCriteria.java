package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SurvivalDE;

/**
 * This  class encapsulates the properties of an caintergator 
 * SurvivalCriteria object.
 *  
 * Dana Zhang
 * Date: September 1, 2004 
 * Version 1.0
 */


public class SurvivalCriteria extends Criteria{

// make sure later if Collection data structure is needed or not
  private SurvivalDE.LowerSurvivalRange lowerSurvivalRange;
  private SurvivalDE.UpperSurvivalRange upperSurvivalRange;
  
  public SurvivalCriteria(){}
  
  public void setLowerSurvivalRange(SurvivalDE.LowerSurvivalRange lowerSurvivalRange){
    this.lowerSurvivalRange = lowerSurvivalRange;   
   }
  
  public void setUpperSurvivalRange(SurvivalDE.UpperSurvivalRange upperSurvivalRange){
    this.upperSurvivalRange = upperSurvivalRange;
   }
   
  public SurvivalDE.LowerSurvivalRange getLowerSurvivalRange(){
    return lowerSurvivalRange;
  }
  
  public SurvivalDE.UpperSurvivalRange getUpperSurvivalRange(){
    return upperSurvivalRange;
  }
   
   // validate to make sure the upper and lower survival limits are correctly entered.
  public boolean isValid(){
    if(lowerSurvivalRange == null && upperSurvivalRange != null){
	  return false;
	 }
	else if (lowerSurvivalRange != null && upperSurvivalRange == null){
	 return false;
	 }
	else if (lowerSurvivalRange != null && upperSurvivalRange != null){
	  if(upperSurvivalRange.getValueObject().intValue() < lowerSurvivalRange.getValueObject().intValue()){
	    return false;
	    }	
	 }
   return true;      
   } 
}
