package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * GeneOntologyDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class GeneOntologyDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   /**
    * Initializes a newly created <code>GeneOntologyDE</code> object so that it represents an GeneOntologyDE.
    */
    public GeneOntologyDE(String ontologyID) {
        super(ontologyID);
    }
   

  /**
    * Sets the value for this <code>GeneOntologyDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the ontologyID for this GeneOntologyDE obect.
    * @return the ontologyID for this <code>GeneOntologyDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the probeSetID for this <code>GeneOntologyDE</code> object
    * @param probeSetID the probeSetID    
	*/ 
    public void setValueObject(String ontologyID) {
        value = ontologyID;
    }
}
