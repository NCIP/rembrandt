package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * InstitutionNameDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class InstitutionNameDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

    public InstitutionNameDE() {
        super();
    }
   
   /**
    * Initializes a newly created <code>InstitutionNameDE</code> object so that it represents an InstitutionNameDE.
    */
    public InstitutionNameDE(String institutionName) {
        super(institutionName);
    }
   

  /**
    * Sets the value for this <code>InstitutionNameDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the institutionName for this InstitutionNameDE obect.
    * @return the institutionName for this <code>InstitutionNameDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the institutionName for this <code>InstitutionNameDE</code> object
    * @param institutionName the institutionName    
	*/ 
    public void setValueObject(String institutionName) {
	  if(institutionName != null){
        value = institutionName;
		}
    }
}
