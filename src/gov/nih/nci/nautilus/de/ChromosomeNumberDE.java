package gov.nih.nci.nautilus.de;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2003
 * Time: 3:07:53 PM
 * To change this template use Options | File Templates.
 */
public class ChromosomeNumberDE extends DomainElement{
    private String chromsomeNumber;
    public static String LABEL = (String) ApplicationContext.getLabelProperties().get("ChromosomeNumber");

    public Object getValue() {
        return chromsomeNumber;
    }

    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

    public Long getValueObject() {
        return (Long) getValue();
    }

    public void setValueObject(String obj) {
        chromsomeNumber = obj;
    }

}
