package gov.nih.nci.nautilus.de;

import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class ExprFoldChangeDE extends DomainElement {
    private String regulationType;
    public final static String UP_REGULATION =  "UpRegulation";
    public final static String DOWN_REGULATION =  "DownRegulation";
    public final static String UNCHANGED_REGULATION =  "DownRegulation";

    public final static class UpRegulation extends ExprFoldChangeDE {
       public UpRegulation(Float upRegValue) {
            super(UP_REGULATION, upRegValue);
       }
    }
    public final static class DownRegulation extends ExprFoldChangeDE {
      public DownRegulation(Float downRegValue) {
            super(DOWN_REGULATION, downRegValue);
      }
    }
    public final static class UnChangedRegulation extends ExprFoldChangeDE {
      public static String LABEL = (String) ApplicationContext.getLabelProperties().get("UnChangedRegulation");
      public UnChangedRegulation(Float unChangedRegValue) {
            super(UNCHANGED_REGULATION, unChangedRegValue);
      }
    }

    private ExprFoldChangeDE(String regulationType, Float value) {
        super(value);
        this.regulationType = regulationType;
    }

    public String getRegulationTypeType() {
        return regulationType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Float) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Float)obj);
    }

    public Float getValueObject() {
        return (Float) getValue();
    }

    public void setValueObject(Float obj) {
        value = obj;
    }
}
