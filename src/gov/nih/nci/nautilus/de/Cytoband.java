package gov.nih.nci.nautilus.de;

import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2003
 * Time: 3:07:53 PM
 * To change this template use Options | File Templates.
 */
public class Cytoband extends DomainElement{
    private String value;
    public static String LABEL = (String) ApplicationContext.getLabelProperties().get("Cytoband");

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
