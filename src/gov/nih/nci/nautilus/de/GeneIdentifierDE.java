package gov.nih.nci.nautilus.de;

import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class GeneIdentifierDE extends DomainElement {
    private String geneIDType;
    private String value;


   public final static class LocusLink extends GeneIdentifierDE {
        public static String LABEL = (String) ApplicationContext.getLabelProperties().get("LocusLink");
        public LocusLink() {
            super("LocusLink");
        }
    }
    public final static class GenBankAccessionNumber extends GeneIdentifierDE {
       public static String LABEL = (String) ApplicationContext.getLabelProperties().get("GenBankAccessionNumber");
       public GenBankAccessionNumber() {
            super("GenBankAccessionNumber");
       }
    }

    private GeneIdentifierDE(String geneIDType) {
        this.geneIDType = geneIDType;
    }

    public String getGeneIDType() {
        return geneIDType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((String)obj);
    }

    public String getValueObject() {
        return (String) getValue();
    }

    public void setValueObject(String obj) {
        value = obj;
    }
}
