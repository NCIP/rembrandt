package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * ChromosomeNumberDE object.
 *  
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2003
 * Time: 3:07:53 PM
 * To change this template use Options | File Templates.
 */
public class ChromosomeNumberDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

   /**
    * Initializes a newly created <code>ChromosomeNumberDE</code> object so that it represents an ChromosomeNumberDE.
    */
    public ChromosomeNumberDE(String chromosomeNumber) {
        super(chromosomeNumber);
    }
   

  /**
    * Sets the value for this <code>ChromosomeNumberDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the chromosomeNumber for this ChromosomeNumberDE obect.
    * @return the chromosomeNumber for this <code>ChromosomeNumberDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the chromosomeNumber for this <code>ChromosomeNumberDE</code> object
    * @param chromosomeNumber the chromosomeNumber    
	*/ 
    public void setValueObject(String chromosomeNumber) {
        value = chromosomeNumber;
    }
}
