package gov.nih.nci.nautilus.de;


/**
 * This  class encapsulates the properties of an caintergator 
 * AgeAtDiagnosisDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class AgeAtDiagnosisDE extends DomainElement{


   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************

  // the type of age limit
   private String ageLimitType = null;
   
   // indicates a lower age limit
   public static final String LOWER_AGE_LIMIT = "lower_age_limit";
   
   // indicates a upper age limit
   public static final String UPPER_AGE_LIMIT = "upper_age_limit";   
   
 
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

  // initializes a new AgeAtDiagnosisDE object with the age limit type and actual ages
   private AgeAtDiagnosisDE(String ageLimitType, Integer ageLimit){
      super(ageLimit);
	  this.ageLimitType = ageLimitType;
   }
    
  // final class indicating lowerAgeLimit 
  public static final class LowerAgeLimit extends AgeAtDiagnosisDE {
     public LowerAgeLimit(Integer lowerAge){
	    super(LOWER_AGE_LIMIT,lowerAge);	
		}    
   }
   
   // final class indicating upperAgeLimit  
  public static final class UpperAgeLimit extends  AgeAtDiagnosisDE{
     public UpperAgeLimit(Integer upperAge){
	   super(UPPER_AGE_LIMIT, upperAge);
	  }
    }
	
// returns the ageLimitType	
  public String getAgeLimitType(){
     return ageLimitType;   
   }

  // overrides upper class' method to set the age
  public void setValue(Object obj) throws Exception{  
    if(!(obj instanceof Integer)) {
	   throw new Exception("Could not set the value. Parameter is of invalid data type: " + obj);	  
	  }
    setValueObject((Integer)obj);
    }
	
// sets the age	
  public void setValueObject(Integer ageLimit){
     this.value = ageLimit;
   }	
	
// returns the actual age	
  public Integer getValueObject(){
    return (Integer)getValue();
   }	
}


