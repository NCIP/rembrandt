package gov.nih.nci.nautilus.de;

//caintergator classes


/**
 * This  class encapsulates the properties of an caintergator 
 * DiseaseNameDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class DiseaseNameDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

 
   
   /**
    * Initializes a newly created <code>DiseaseNameDE</code> object so that it represents an DiseaseNameDE.
    */
    public DiseaseNameDE(String diseaseName) {
        super(diseaseName);
    }
   

  /**
    * Sets the value for this <code>DiseaseNameDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the diseaseName for this DiseaseNameDE obect.
    * @return the diseaseName for this <code>DiseaseNameDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the diseaseName for this <code>DiseaseNameDE</code> object
    * @param diseaseName the diseaseName    
	*/ 
    public void setValueObject(String diseaseName) {
	  if(diseaseName != null){
        value = diseaseName;
		}
    }
}
