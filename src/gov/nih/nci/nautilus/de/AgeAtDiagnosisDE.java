package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * AgeAtDiagnosisDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class AgeAtDiagnosisDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

  
   /**
    * Initializes a newly created <code>AgeAtDiagnosisDE</code> object so that it represents an AgeAtDiagnosisDE.
    */
    public AgeAtDiagnosisDE(String ageGroup) {
        super(ageGroup);
    }
   

  /**
    * Sets the value for this <code>AgeAtDiagnosisDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the ageGroup for this AgeAtDiagnosisDE obect.
    * @return the ageGroup for this <code>AgeAtDiagnosisDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the ageGroup for this <code>AgeAtDiagnosisDE</code> object
    * @param ageGroup the ageGroup    
	*/ 
    public void setValueObject(String ageGroup) {
	  if(ageGroup != null){
        value = ageGroup;
		} 
    }
}
