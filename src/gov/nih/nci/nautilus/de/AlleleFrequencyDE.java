package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * AlleleFrequencyDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class AlleleFrequencyDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************
    
   
   /**
    * Initializes a newly created <code>AlleleFrequencyDE</code> object so that it represents an AlleleFrequencyDE.
    */
    public AlleleFrequencyDE(String allelName) {
        super(allelName);
    }
   

  /**
    * Sets the value for this <code>AlleleFrequencyDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the allelName for this AlleleFrequencyDE obect.
    * @return the allelName for this <code>AlleleFrequencyDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the allelName for this <code>AlleleFrequencyDE</code> object
    * @param allelName the allelName    
	*/ 
    public void setValueObject(String allelName) {
	  if(allelName != null){
        value = allelName;
		} 
    }
}
