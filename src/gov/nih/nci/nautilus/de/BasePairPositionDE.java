package gov.nih.nci.nautilus.de;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class BasePairPositionDE extends DomainElement {
    private String positionType;
    private Integer value;

   public final static class StartPosition extends BasePairPositionDE {
     static {
         LABEL = (String) ApplicationContext.getLabelProperties().get("StartPosition");
      }
       public StartPosition() {
            super("StartPosition");
       }
    }
    public final static class EndPosition extends BasePairPositionDE {
      static {
            LABEL = (String) ApplicationContext.getLabelProperties().get("EndPosition");
       }
       public EndPosition() {
            super("EndPosition");
       }
    }

    private BasePairPositionDE(String positionType) {
        this.positionType = positionType;
    }

    public String getRegulationTypeType() {
        return positionType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Integer) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Integer)obj);
    }

    public Integer getValueObject() {
        return (Integer) getValue();
    }

    public void setValueObject(Integer value) {
        this.value = value;
    }
}
