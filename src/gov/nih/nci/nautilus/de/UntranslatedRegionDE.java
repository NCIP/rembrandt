package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * UntranslatedRegionDE object.
 * It contains two child/nested classes:5UTR, 3UTR.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
abstract public class UntranslatedRegionDE extends DomainElement {

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
  /**
	* type of UTRID
    */
    private String UTRIDType;
	
  /**
	* 5UTR
    */
    public static final String 5UTR = "5UTR";
	
  /**
	* 3UTR
    */
    public static final String 3UTR = "3UTR";	
	
  

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************

  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private UntranslatedRegionDE(String UTRIDType, String value) {
        super(value);
        this.UTRIDType = UTRIDType;
    }
	
  /**
	* nested child class: 5UTR
    */
    public final static class 5UTR extends UntranslatedRegionDE {
        public 5UTR(String 5UTR) {
            super(5UTR, 5UTR);
        }
    }

 /**
	* nested child class: 3UTR
    */	
    public final static class 3UTR extends UntranslatedRegionDE {
       public 3UTR(String 3UTR) {
            super(3UTR, 3UTR);

       }
    }


  /**
    * Returns the UTRIDType for this UntranslatedRegionDE obect.
    * @return the UTRIDType for this <code>UntranslatedRegionDE</code> object
    */	
	public String getUTRIDType() {
        return UTRIDType;
    }
	
  /**
    * Sets the value for this <code>UntranslatedRegionDE</code> object
    * @param object the value    
	*/  
    public void setValue(Object UTRID) throws Exception {
         if (! (geneID instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)UTRID);
    }

	
  /**
    * Returns the UTRIDType for this UntranslatedRegionDE obect.
    * @return the UTRIDType for this <code>UntranslatedRegionDE</code> object
    */
    public String getValueObject() {
        return (String) getValue();
    }

	
  /**
    * Sets the UTRID for this <code>UntranslatedRegionDE</code> object
    * @param UTRID the regulation    
	*/ 
    public void geneID(String UTRID) {
        assert(UTRID != null);
        value = UTRID;
    }
}
