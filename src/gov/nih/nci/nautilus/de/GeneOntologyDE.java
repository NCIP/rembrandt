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
    public GeneOntologyDE(Integer ontologyID) {
        super(ontologyID);
    }


    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof Integer) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((Integer)obj);
    }

    public Integer getValueObject() {
        return (Integer) getValue();
    }


    public void setValueObject(Integer ontologyID) {
	  if(ontologyID != null){
        value = ontologyID;
		}
    }
}
