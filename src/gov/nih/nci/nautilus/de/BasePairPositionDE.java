package gov.nih.nci.nautilus.de;

//caintergator classes

/**
 * This abstract class encapsulates the properties of an caintergator 
 * BasePairPositionDE object.
 * It contains two child/nested classes:StartPosition & EndPosition
 *  
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */


abstract public class BasePairPositionDE extends DomainElement {
	

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
   /**
	* type of positon
    */
   private String positionType;
   
   /**
	* start base postion
    */
   public final static String START_POSITION = "StartPosition";
  
   /**
	* end base postion
    */
   public final static String END_POSITION = "StartPosition";
   
   
   // ****************************************************
  //                   CONSTRUCTOR(S)
  // *****************************************************



  /**
	* private parent constructor utilized in the two nested/childe classes
    */
   private BasePairPositionDE(String positionType, Integer value) {
      super(value);
      this.positionType = positionType;
    }

   /**
	* nested child class: StartPosition
    */
   public final static class StartPosition extends BasePairPositionDE {
     public StartPosition(Integer startPosition) {
        super(START_POSITION, startPosition);
        }
	
    }
  
   /**
	* nested child class: EndPosition
    */
   public final static class EndPosition extends BasePairPositionDE {
      public EndPosition(Integer endPosition) {
        super(END_POSITION, endPosition);
        }
	 
    }
	
	 
  /**
    * Returns the positionType for this BasePairPositionDE obect.
    * @return the positionType for this <code>BasePairPositionDE</code> object
    */	
    public String getPositionType() {
      return positionType;
    }

   /**
    * Sets the value for this <code>BasePairPositionDE</code> object
    * @param object the value    
	*/   
    public void setValue(Object obj) throws Exception {
      if (! (obj instanceof Integer) )
       throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Integer)obj);
    }

  /**
    * Returns the basePairPosition for this BasePairPositionDE obect.
    * @return the basePairPosition for this <code>BasePairPositionDE</code> object
    */	
    public Integer getValueObject() {
        return (Integer) getValue();
    }

	/**
    * Sets the basePairPosition for this <code>BasePairPositionDE</code> object
    * @param basePairPosition the basePairPosition    
	*/ 
    public void setValueObject(Integer basePairPosition) {
        if(basePairPosition != null){
           this.value = basePairPosition;
		}
    }
}
