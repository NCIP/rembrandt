package gov.nih.nci.nautilus.de;

import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class GeneIdentifierDE extends DomainElement {
    private String geneIDType;
    public static final String LOCUS_LINK = "LocusLink";
    public static final String GENBANK_ACCESSION_NUMBER= "GenBankAccessionNumber";

    public final static class LocusLink extends GeneIdentifierDE {
        public LocusLink(String locusLinkID) {
            super(LOCUS_LINK, locusLinkID);
        }
    }
    public final static class GenBankAccessionNumber extends GeneIdentifierDE {
       public GenBankAccessionNumber(String genBankAccessionNumber) {
            super(GENBANK_ACCESSION_NUMBER, genBankAccessionNumber);

       }
    }

    private GeneIdentifierDE(String geneIDType, String value) {
        super(value);
        this.geneIDType = geneIDType;
    }

    public String getGeneIDType() {
        return geneIDType;
    }
    public void setValue(Object geneID) throws Exception {
         if (! (geneID instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)geneID);
    }

    public String getValueObject() {
        return (String) getValue();
    }

    public void setValueObject(String geneID) {
        assert(geneID != null);
        value = geneID;
    }
}
