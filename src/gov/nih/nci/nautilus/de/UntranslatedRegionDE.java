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
    public static final String UTR5 = "UTR_5";
	
  /**
	* 3UTR
    */
    public static final String UTR3 = "UTR_3";	
	
  

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************

  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private UntranslatedRegionDE(String UTRIDType, Boolean value) {
        super(value);
        this.UTRIDType = UTRIDType;
    }
	
  /**
	* nested child class: UTR_5
    */
    public final static class UTR_5 extends UntranslatedRegionDE {
        public UTR_5(Boolean UTR_5) {
            super(UTR5, UTR_5);
        }
    }

 /**
	* nested child class: UTR_3
    */	
    public final static class UTR_3 extends UntranslatedRegionDE {
       public UTR_3(Boolean UTR_3) {
            super(UTR3, UTR_3);

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
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Boolean) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Boolean)obj);
    }

	
  /**
    * Returns the UTRIDType for this UntranslatedRegionDE obect.
    * @return the UTRIDType for this <code>UntranslatedRegionDE</code> object
    */
    public String getValueObject() {
        return (String) getValue();
    }

	
  /**
    * Sets the utr for this <code>UntranslatedRegionDE</code> object
    * @param utr the cloneName    
	*/ 
	
    public void setValueObject(Boolean utr) {
        this.value = utr;
    }	
  /**
    * Sets the UTRID for this <code>UntranslatedRegionDE</code> object
    * @param UTRID the regulation    
	*/ 
    public void setURTID(String UTRID) {
	  if(UTRID != null){
        value = UTRID;
		}
    }
}
