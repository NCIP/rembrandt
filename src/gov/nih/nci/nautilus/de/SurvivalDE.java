package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


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
  //                   CONSTRUCTOR(S)
  // ****************************************************

    public SurvivalDE() {
        super();
    }
   /**
    * Initializes a newly created <code>SurvivalDE</code> object so that it represents an SurvivalDE.
    */
    public SurvivalDE(String survival) {
        super(survival);
    }
   

  /**
    * Sets the value for this <code>SurvivalDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the survival for this SurvivalDE obect.
    * @return the survival for this <code>SurvivalDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the survival for this <code>SurvivalDE</code> object
    * @param survival the survival    
	*/ 
    public void setValueObject(String survival) {
	  if(survival != null){
        value = survival;
		}
    }
}
