package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * CloneIdentifierDE object.
 * It contains two child/nested classes:BACClone, IMAGEClone
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
abstract public class CloneIdentifierDE extends DomainElement {

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
  /**
	* type of cloneID
    */
    private String cloneIDType;
	
  /**
	* BACClone
    */
    public static final String BAC_CLONE = "BACClone";
	
  /**
	* IMAGEClone
    */
    public static final String IMAGE_CLONE= "IMAGEClone";
    public static final String PROBE_SET= "ProbesetClone";



   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************

    private CloneIdentifierDE() {
        super();
        
    }
	
  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private CloneIdentifierDE(String cloneIDType, String value) {
        super(value);
        this.cloneIDType = cloneIDType;
    }
	
  /**
	* nested child class: BACClone
    */
    public final static class BACClone extends CloneIdentifierDE {
        public BACClone(String bacClineID) {
            super(BAC_CLONE, bacClineID);
        }
	   public BACClone() {
            super();
        }
    }

 /**
	* nested child class: IMAGEClone
    */	
    public final static class IMAGEClone extends CloneIdentifierDE {
       public IMAGEClone(String imageCloneID) {
            super(IMAGE_CLONE, imageCloneID);

       }
	   public IMAGEClone() {
            super();

       }
    }

    public final static class ProbesetID extends CloneIdentifierDE {
       public ProbesetID(String imageCloneID) {
            super(PROBE_SET, imageCloneID);

       }
    }

  /**
    * Returns the cloneIDType for this CloneIdentifierDE obect.
    * @return the cloneIDType for this <code>CloneIdentifierDE</code> object
    */	
	public String getCloneIDType() {
        return cloneIDType;
    }
	
  /**
    * Sets the value for this <code>CloneIdentifierDE</code> object
    * @param object the value    
	*/  
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)obj);
    }

	
  /**
    * Returns the cloneIDType for this CloneIdentifierDE obect.
    * @return the cloneIDType for this <code>CloneIdentifierDE</code> object
    */
    public String getValueObject() {
        return (String) getValue();
    }

	
  /**
    * Sets the cloneName for this <code>GeneIdentifierDE</code> object
    * @param cloneName the cloneName    
	*/ 
	
    public void setValueObject(String cloneName) {
        this.value = cloneName;
    }
	
  /**
    * Sets the cloneID for this <code>CloneIdentifierDE</code> object
    * @param cloneID the cloneID    
	*/ 
    public void setCloneID(String cloneID) {
	  if(cloneID != null){
        value = cloneID;
	  }
    }
}
