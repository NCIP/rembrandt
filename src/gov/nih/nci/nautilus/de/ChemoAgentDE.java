package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * ChemoAgentDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class ChemoAgentDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

  
   
   /**
    * Initializes a newly created <code>ChemoAgentDE</code> object so that it represents an ChemoAgentDE.
    */
    public ChemoAgentDE(String chemoAgentName) {
        super(chemoAgentName);
    }
   

  /**
    * Sets the value for this <code>ChemoAgentDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the chemoAgentName for this ChemoAgentDE obect.
    * @return the chemoAgentName for this <code>ChemoAgentDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the chemoAgentName for this <code>ChemoAgentDE</code> object
    * @param chemoAgentName the chemoAgentName    
	*/ 
    public void setValueObject(String chemoAgentName) {
	  if(chemoAgentName != null){
        value = chemoAgentName;
		}
    }
}
