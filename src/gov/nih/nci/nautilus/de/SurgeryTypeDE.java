package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * SurgeryTypeDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class SurgeryTypeDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

     public SurgeryTypeDE() {
        super();
    }
	
   /**
    * Initializes a newly created <code>SurgeryTypeDE</code> object so that it represents an SurgeryTypeDE.
    */
    public SurgeryTypeDE(String surgeryType) {
        super(surgeryType);
    }
	
  

  /**
    * Sets the value for this <code>SurgeryTypeDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the surgeryType for this SurgeryTypeDE obect.
    * @return the surgeryType for this <code>SurgeryTypeDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the surgeryType for this <code>SurgeryTypeDE</code> object
    * @param surgeryType the surgeryType    
	*/ 
    public void setValueObject(String surgeryType) {
	  if(surgeryType != null){
        value = surgeryType;
		}
    }
}
