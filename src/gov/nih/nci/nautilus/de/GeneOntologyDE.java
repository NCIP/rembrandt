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


    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

    public String getValueObject() {
        return (String) getValue();
    }


    public void setValueObject(String ontologyID) {
	  if(ontologyID != null){
        value = ontologyID;
		}
    }
}
