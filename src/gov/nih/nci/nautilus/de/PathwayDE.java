package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * PathwayDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class PathwayDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************
  


   /**
    * Initializes a newly created <code>PathwayDE</code> object so that it represents an PathwayDE.
    */
	
    public PathwayDE(String pathwayName) {
        super(pathwayName);
    }
   

  /**
    * Sets the value for this <code>PathwayDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the pathwayName for this PathwayDE obect.
    * @return the pathwayName for this <code>PathwayDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the pathwayName for this <code>PathwayDE</code> object
    * @param pathwayName the pathwayName    
	*/ 
    public void setValueObject(String pathwayName) {
	  if(pathwayName != null){
        value = pathwayName;
		}
    }
}
