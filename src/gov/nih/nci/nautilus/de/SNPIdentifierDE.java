package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * SNPIdentifierDE object.
 * It contains three child/nested classes:TSC,  DBSNP & SNPProbeSet.
 *  
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */


abstract public class SNPIdentifierDE extends DomainElement {
	

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
  /**
	* type of SNP
    */
   private String SNPType;
   
  /**
	* TSC
    */
   public final static String TSC = "TSC";
  
  /**
	* DBSNP
    */
   public final static String DBSNP = "DBSNP";
   
   
  /**
	* SNPProbeSet
    */
   public final static String SNP_PROBESET = "SNPProbeSet";
   
   
   // ****************************************************
  //                   CONSTRUCTOR(S)
  // *****************************************************


  /**
	* private parent constructor utilized in the two nested/childe classes
    */
   private SNPIdentifierDE(String SNPType, String value) {
      super(value);
      this.SNPType = SNPType;
    }

   /**
	* nested child class: TSC
    */
   public final static class TSC extends SNPIdentifierDE {
     public TSC(String TSCId) {
       super(TSC, TSCId);
        }	
    }
  
   /**
	* nested child class: SBSNP
    */
   public final static class SBSNP extends SNPIdentifierDE {
      public SBSNP(String DBSNPId ) {
        super(DBSNP, DBSNPId);
        }	
    }
	
	 
  /**
	* nested child class: SNPProbeSet
    */
   public final static class SNPProbeSet extends SNPIdentifierDE {
      public SNPProbeSet(String SNPProbeSetId ) {
         super(SNP_PROBESET, SNPProbeSetId);
        }	
    }
	
	 
  /**
    * Returns the SNPType for this SNPIdentifierDE obect.
    * @return the SNPType for this <code>SNPIdentifierDE</code> object
    */	
    public String getSNPType() {
      return SNPType;
    }

   /**
    * Sets the value for this <code>SNPIdentifierDE</code> object
    * @param object the value    
	*/   
    public void setValue(Object obj) throws Exception {
      if (! (obj instanceof String) )
       throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)obj);
    }

  /**
    * Returns the SNPPostion for this SNPIdentifierDE obect.
    * @return the SNPPostion for this <code>SNPIdentifierDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

	/**
    * Sets the SNPPostion for this <code>SNPIdentifierDE</code> object
    * @param SNPPostion the basePairPosition    
	*/ 
    public void setValueObject(String SNPPosition) {
        if(SNPPosition != null){
          this.value = SNPPosition;
		 }
    }
}
