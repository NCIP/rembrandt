package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * CopyNumberDE object.
 * It contains three child/nested classes:Amplification, Deletion & Unchange
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
abstract public class CopyNumberDE extends DomainElement {

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
  /**
	* type of CGH
    */
    private String CGHType;
	
  /**
	* Amplification
    */
    public static final String AMPLIFICATION = "Amplification";
	
  /**
	* Deletion
    */
    public static final String DELETION= "Deletion";
	
	
  /**
	* Unchange
    */
    public static final String UNCHANGE= "Unchange";

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************

    private CopyNumberDE() {
        super();        
    }
	
   
  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private CopyNumberDE(String CGHType, Float value) {
        super(value);
        this.CGHType = CGHType;
    }
	
  /**
	* nested child class: Amplification
    */
    public final static class Amplification extends CopyNumberDE {
        public Amplification(Float AmplificationNumber) {
            super(AMPLIFICATION, AmplificationNumber);
        }
		 public Amplification() {
            super();
        }
    }

 /**
	* nested child class: Deletion
    */	
    public final static class Deletion extends CopyNumberDE {
       public Deletion(Float deletionNumber) {
            super(DELETION, deletionNumber);

       }
	   public Deletion() {
            super();

       }
    }


  /**
	* nested child class: Unchange
    */	
    public final static class Unchange extends CopyNumberDE {
       public Unchange(Float unchangeNumber) {
            super(UNCHANGE, unchangeNumber);
       }
	   public Unchange() {
            super();
       }
    }
    
	 
  /**
    * Returns the CGHTpye for this CopyNumberDE obect.
    * @return the CGHTpye for this <code>CopyNumberDE</code> object
    */	
	public String getCGHType() {
        return CGHType;
    }
	
  /**
    * Sets the value for this <code>CopyNumberDE</code> object
    * @param object the value    
	*/  
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Float) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Float)obj);
    }

	
  /**
    * Returns the CGHType for this CopyNumberDE obect.
    * @return the CGHType for this <code>CopyNumberDE</code> object
    */
    public Float getValueObject() {
        return (Float) getValue();
    }
	
  /**
    * Sets the copyNumber for this <code>CopyNumberDE</code> object
    * @param copyNumber the copyNumber    
	*/ 
    public void setValueObject(Float copyNumber) {
        if(copyNumber != null){
           this.value = copyNumber;
		 }
    }
  /**
    * Sets the CGHType for this <code>CopyNumberDE</code> object
    * @param CGHType the CGHType    
	*/ 
    public void GetCGHType(String CGHType) {
        if(CGHType != null){
           value = CGHType;
		 }
    }
	
}
