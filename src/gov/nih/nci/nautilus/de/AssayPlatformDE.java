package gov.nih.nci.nautilus.de;

//caintergator classes


/**
 * This  class encapsulates the properties of an caintergator 
 * AssayPlatformDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class AssayPlatformDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************
    
   
   /**
    * Initializes a newly created <code>AssayPlatformDE</code> object so that it represents an AssayPlatformDE.
    */
    public AssayPlatformDE(String assayName) {
        super(assayName);
    }
   

  /**
    * Sets the value for this <code>AssayPlatformDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the assayName for this AssayPlatformDE obect.
    * @return the assayName for this <code>AssayPlatformDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the assayName for this <code>AssayPlatformDE</code> object
    * @param assayName the assayName    
	*/ 
    public void setValueObject(String assayName) {
	  if(assayName != null){
        value = assayName;
		} 
    }
}
