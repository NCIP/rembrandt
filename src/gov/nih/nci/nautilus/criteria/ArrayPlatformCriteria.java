package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import java.util.*; 


/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 26, 2004
 * Time: 7:58:06 PM
 * To change this template use Options | File Templates.
 */
public class ArrayPlatformCriteria extends Criteria {
   
    ArrayPlatformDE platform;
	public ArrayPlatformCriteria(){}

    public ArrayPlatformDE getPlatform() {
        return platform;
    }

    public void setPlatform(ArrayPlatformDE platform) {
        this.platform = platform;
    }

    public ArrayPlatformCriteria(ArrayPlatformDE platform) {
        this.platform = platform;
    }

    public boolean isValid() {
        //TODO: check for valid platforms
        return true;
    }
}
