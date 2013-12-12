package gov.nih.nci.rembrandt.web.bean;

import java.io.Serializable;


/**
 * A simple JavaBean to represent label-value pairs. This is most commonly used
 * when constructing user interface elements which have a label to be displayed
 * to the user, and a corresponding value to be returned to the server. One
 * example is the <code>&lt;html:options&gt;</code> tag.
 *
 * @author  Craig R. McClanahan
 * @author  Martin F N Cooper
 * @version $Revision: 1.2 $ $Date: 2002/03/12 06:30:56 $
 */

public class LabelValueBean implements Serializable {
    /**
     * Construct an instance with the supplied property values.
     *
     * @param label The label to be displayed to the user.
     * @param value The value to be returned to the server.
     */
    public LabelValueBean(String label, String value) {
        this.label = label;
        this.value = value;
    }


    /**
     * The property which supplies the option label visible to the end user.
     */
    private String label;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    /**
     * The property which supplies the value returned to the server.
     */
    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return a string representation of this object.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("LabelValueBean[");
        sb.append(this.label);
        sb.append(", ");
        sb.append(this.value);
        sb.append("]");
        return (sb.toString());
    }


}
