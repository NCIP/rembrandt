package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * GeneIdentifierDE object.
 * It contains three child/nested classes:LocusLink, GenBankAccessionNumber & GeneSymbol
 *
 * @author BhattarR, BauerD
 */
abstract public class GeneIdentifierDE extends DomainElement implements Serializable, Cloneable{

	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */

  /**
	* type of geneID
    */
    private String geneIDType;

  /**
	* LocusLink
    */
    public static final String LOCUS_LINK = "LocusLink";

  /**
	* GenBankAccessionNumber
    */
    public static final String GENBANK_ACCESSION_NUMBER= "GenBankAccessionNumber";


  /**
	* GeneSymbol
    */
    public static final String GENESYMBOL= "GeneSymbol";

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************
   


  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private GeneIdentifierDE(String geneIDType, String value) {
        super(value);
        this.geneIDType = geneIDType;
    }

  /**
	* nested child class: LocusLink
    */
    public final static class LocusLink extends GeneIdentifierDE {
        public LocusLink(String locusLinkID) {
            super(LOCUS_LINK, locusLinkID);
        }	
    }

 /**
	* nested child class: GenBankAccessionNumber
    */
    public final static class GenBankAccessionNumber extends GeneIdentifierDE {
       public GenBankAccessionNumber(String genBankAccessionNumber) {
            super(GENBANK_ACCESSION_NUMBER, genBankAccessionNumber);
       }
	  }


  /**
	* nested child class: GeneSymbol
    */
	
    public final static class GeneSymbol extends GeneIdentifierDE {
       public GeneSymbol(String geneSymbol) {
            super(GENESYMBOL, geneSymbol);
       }	 
    }


  /**
    * Returns the geneIDType for this GeneIdentifierDE obect.
    * @return the geneIDType for this <code>GeneIdentifierDE</code> object
    */
	public String getGeneIDType() {
        return geneIDType;
    }

  /**
    * Sets the value for this <code>GeneIdentifierDE</code> object
    * @param object the value
	*/
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)obj);
    }


  /**
    * Returns the geneIDType for this GeneIdentifierDE obect.
    * @return the geneIDType for this <code>GeneIdentifierDE</code> object
    */
    public String getValueObject() {
        return (String) getValue();
    }


  /**
    * Sets the gene for this <code>GeneIdentifierDE</code> object
    * @param gene the gene
	*/
    public void setValueObject(String geneName) {
        this.value = geneName;
    }

  /**
    * Sets the geneID for this <code>GeneIdentifierDE</code> object
    * @param regulation the regulation
	*/
    public void setGeneID(String geneID) {
       if(geneID != null){
        value = geneID;
		}
    }
    /**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
    public Object clone() {
    	GeneIdentifierDE myClone = (GeneIdentifierDE) super.clone();
		return myClone;
	}
}
