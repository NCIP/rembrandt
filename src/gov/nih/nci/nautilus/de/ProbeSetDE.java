package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * ProbeSetDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class ProbeSetDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   /**
    * Initializes a newly created <code>ProbeSetDE</code> object so that it represents an ProbeSetDE.
    */
    public ProbeSetDE(String probeSetID) {
        super(probeSetID);
    }
   

  /**
    * Sets the value for this <code>ProbeSetDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the probeSetID for this ProbeSetDE obect.
    * @return the probeSetID for this <code>ProbeSetDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the probeSetID for this <code>ProbeSetDE</code> object
    * @param probeSetID the probeSetID    
	*/ 
    public void setValueObject(String probeSetID) {
	  if(probeSetID != null){
        value = probeSetID;
		}
    }
}
