package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * GenderDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class GenderDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************


   /**
    * Initializes a newly created <code>GenderDE</code> object so that it represents an GenderDE.
    */
    public GenderDE(String gender) {
        super(gender);
    }
   

  /**
    * Sets the value for this <code>GenderDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the gender for this GenderDE obect.
    * @return the gender for this <code>GenderDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the gender for this <code>GenderDE</code> object
    * @param gender the gender    
	*/ 
    public void setValueObject(String gender) {
	  if(gender != null){
        value = gender;
		}
    }
}
