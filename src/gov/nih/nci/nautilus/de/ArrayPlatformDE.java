package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * ArrayPlatformDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class ArrayPlatformDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   
   
   /**
    * Initializes a newly created <code>ArrayPlatformDE</code> object so that it represents an ArrayPlatformDE.
    */
    public ArrayPlatformDE(String arrayName) {
        super(arrayName);
    }
   

  /**
    * Sets the value for this <code>ArrayPlatformDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the arrayName for this ArrayPlatformDE obect.
    * @return the arrayName for this <code>ArrayPlatformDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the arrayName for this <code>ArrayPlatformDE</code> object
    * @param arrayName the arrayName    
	*/ 
    public void setValueObject(String arrayName) {
	   if(arrayName != null){
         value = arrayName;
		 }
    }
}
