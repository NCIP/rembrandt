package gov.nih.nci.nautilus.de;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class ExprFoldChangeDE extends DomainElement {
    private String regulationType;
    private Float value;


   public final static class UpRegulation extends ExprFoldChangeDE {
       public static String LABEL = (String) ApplicationContext.getLabelProperties().get("UpRegulation");
       public UpRegulation() {
            super("UpRegulation");
       }
    }
    public final static class DownRegulation extends ExprFoldChangeDE {
      public static String LABEL = (String) ApplicationContext.getLabelProperties().get("DownRegulation");
      public DownRegulation() {
            super("DownRegulation");
      }
    }
    public final static class UnChangedRegulation extends ExprFoldChangeDE {
      public static String LABEL = (String) ApplicationContext.getLabelProperties().get("UnChangedRegulation");
      public UnChangedRegulation() {
            super("UnChangedRegulation");
      }
    }

    private ExprFoldChangeDE(String regulationType) {
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

    public String getValueObject() {
        return (String) getValue();
    }

    public void setValueObject(Float obj) {
        value = obj;
    }
}
