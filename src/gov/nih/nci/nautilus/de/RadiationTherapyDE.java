package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * RadiationTherapyDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class RadiationTherapyDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************


	
   /**
    * Initializes a newly created <code>RadiationTherapyDE</code> object so that it represents an RadiationTherapyDE.
    */
    public RadiationTherapyDE(String radiationTherapy) {
        super(radiationTherapy);
    }
	
  

  /**
    * Sets the value for this <code>RadiationTherapyDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the radiationTherapy for this RadiationTherapyDE obect.
    * @return the radiationTherapy for this <code>RadiationTherapyDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the radiationTherapy for this <code>RadiationTherapyDE</code> object
    * @param radiationTherapy the radiationTherapy    
	*/ 
    public void setValueObject(String radiationTherapy) {
	  if(radiationTherapy != null){
        value = radiationTherapy;
		}
    }
}
