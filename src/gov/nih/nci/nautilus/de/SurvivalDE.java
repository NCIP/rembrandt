package gov.nih.nci.nautilus.de;


/**
 * This  class encapsulates the properties of an caintergator 
 * SurvivalDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */

public class SurvivalDE extends DomainElement{

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************

  // the type of survival limit

   private String survivalRangeType = null;
   
   // final String indicates a upper survival range
   public static final String UPPER_SURVIVAL_RANGE = "upper_survival_range";
   
   // fianl String indicates a lower survival range
   public static final String LOWER_SURVIVAL_RANGE = "lower_survival_range";
   
   
   // initializes a SurvivalDE object with the survival limit type and actual survival limit
   private SurvivalDE(String survivalRangeType, Integer survivalLimit){
     super(survivalLimit);
	 this.survivalRangeType = survivalRangeType;     
    }

  // final class UpperSurvivalRange indicating upper survival range
  public static final class UpperSurvivalRange extends SurvivalDE{
    public UpperSurvivalRange(Integer upperSurvivalRange){
	  super(UPPER_SURVIVAL_RANGE,upperSurvivalRange);
	 }
  }	
	
 // final class LowerSurvivalRange indicating lower survival range 	
 public static final class LowerSurvivalRange extends SurvivalDE{
   public LowerSurvivalRange(Integer lowerSurvivalRange){
     super(LOWER_SURVIVAL_RANGE, lowerSurvivalRange);
     }
   }	

// return the type of survival : upper or lower
 public String getSurvivalLimitType(){
   return survivalRangeType;
  }
 
 //implements the upper class abstract method
 public void setValue(Object obj) throws Exception {
   if(!(obj instanceof Integer)){
       throw new Exception("Could not set the value. Parameter is of invalid type :" + obj);
	 }
   this.setValueObject((Integer)obj);
  } 
   
 // sets the survival limit
 public void setValueObject(Integer survivalLimit){
    this.value = survivalLimit;
   }
   
  // returns the survival limit
 public Integer getValueObject(){
   return (Integer)getValue();
   } 
  
}
