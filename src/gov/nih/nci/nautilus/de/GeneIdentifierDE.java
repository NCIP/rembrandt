package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * GeneIdentifierDE object.
 * It contains three child/nested classes:LocusLink, GenBankAccessionNumber & GeneSymbol
 *  
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class GeneIdentifierDE extends DomainElement {

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
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
        assert(geneName != null);
        this.value = geneName;
    }
	
  /**
    * Sets the geneID for this <code>GeneIdentifierDE</code> object
    * @param regulation the regulation    
	*/ 
    public void setGeneID(String geneID) {
        assert(geneID != null);
        value = geneID;
    }
}
