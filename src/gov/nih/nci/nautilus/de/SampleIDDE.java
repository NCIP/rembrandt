package gov.nih.nci.nautilus.de;

//caintergator classes

/**
 * This  class CytobandDE the properties of an caintergator 
 * CytobandDE object.
 *  
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2003
 * Time: 3:07:53 PM
 * To change this template use Options | File Templates.
 */
public class SampleIDDE extends DomainElement{

  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   
	
  /**
    * Initializes a newly created <code>CytobandDE</code> object so that it represents an CytobandDE.
    */
    public SampleIDDE(String sampleID) {
        super(sampleID);
    }
	
  /**
    * Sets the value for this <code>CytobandDE</code> object
    * @param object the value    
	*/  
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }
	
  /**
    * Returns the cytobandLocation for this CytobandDE obect.
    * @return the cytobandLocation for this <code>CytobandDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }
	
  /**
    * Sets the cytobandLocation for this <code>CytobandDE</code> object
    * @param sampleID the cytobandLocation
	*/ 
    public void setValueObject(String sampleID) {
	   if(sampleID != null){
         value = sampleID;
		 }
    }
}
