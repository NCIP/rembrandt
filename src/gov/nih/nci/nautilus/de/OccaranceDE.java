package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * OccaranceDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class OccaranceDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   /**
    * Initializes a newly created <code>OccaranceDE</code> object so that it represents an OccaranceDE.
    */
    public OccaranceDE(String occarance) {
        super(occarance);
    }
   

  /**
    * Sets the value for this <code>OccaranceDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the occarance for this OccaranceDE obect.
    * @return the occarance for this <code>OccaranceDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the occarance for this <code>OccaranceDE</code> object
    * @param occarance the occarance    
	*/ 
    public void setValueObject(String occarance) {
	   if(occarance != null){
         value = occarance;
		 }
    }
}
