package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.AgeAtDiagnosisDE;


/**
 * This  class encapsulates the properties of an caintergator 
 * AgeCriteria object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */


public class AgeCriteria{

// make sure later if Collection needs to be used in the class


  private AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit;
  private AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit;
  
  public AgeCriteria(){}
  
  public void setLowerAgeLimit(AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit){
    this.lowerAgeLimit = lowerAgeLimit;
    }
  public AgeAtDiagnosisDE.LowerAgeLimit getLowerAgeLimit(){
    return lowerAgeLimit;
   }
  
  public void setUpperAgeLimit(AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit){
    this.upperAgeLimit = upperAgeLimit;
    }
  public AgeAtDiagnosisDE.UpperAgeLimit getUpperAgeLimit(){
    return upperAgeLimit;
  } 
  
  // validate the upper and lower age limit entries
  public boolean isValid(){
  
    if(lowerAgeLimit == null && upperAgeLimit != null){
	   return false;
	}
    else if (lowerAgeLimit != null && upperAgeLimit == null){
	  return false;
	 }
	else if(lowerAgeLimit != null && upperAgeLimit != null){
	 if(upperAgeLimit.getValueObject().intValue() < lowerAgeLimit.getValueObject().intValue()){
	  return false;
	   }
	 }
	return true; 
  }

 }
