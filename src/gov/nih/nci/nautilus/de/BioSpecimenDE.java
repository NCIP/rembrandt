/*
 * Created on Sep 13, 2004
 *
 */
package gov.nih.nci.nautilus.de;

/**
 * @author SahniH
 * Date: September 13, 2004 
 * @version $Revision: 1.1 $
 * This  class encapsulates the properties of an caintergator 
 * BioSpecimanDE object.
 * 
 *
 * */
public class BioSpecimenDE extends DomainElement{
	  //****************************************************
	  //                   CONSTRUCTOR(S)
	  // ****************************************************
	    
	   
	   /**
	    * Initializes a newly created <code>BioSpecimenDE</code> object so that it represents an AlleleFrequencyDE.
	    */
	    public BioSpecimenDE(String sampleID) {
	        super(sampleID);
	    }
	   

	  /**
	    * Sets the value for this <code>BioSpecimenDE</code> object
	    * @param object the value    
		*/  	
	    public void setValue(Object obj) throws Exception {
	        if (! (obj instanceof String) )
	            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
	        setValueObject((String)obj);
	    }

	  /**
	    * Returns the sampleID for this BioSpecimenDE obect.
	    * @return the sampleID for this <code>BioSpecimenDE</code> object
	    */	
	    public String getValueObject() {
	        return (String) getValue();
	    }

	  /**
	    * Sets the sampleID for this <code>BioSpecimenDE</code> object
	    * @param sampleID the sampleID    
		*/ 
	    public void setValueObject(String sampleID) {
		  if(sampleID != null){
	        value = sampleID;
			} 
	    }
	public static void main(String[] args) {
	}
}
