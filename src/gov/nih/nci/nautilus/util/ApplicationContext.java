package gov.nih.nci.nautilus.util;

import gov.nih.nci.nautilus.constants.NautilusConstants;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 5, 2004
 * Time: 7:08:35 PM
 * To change this template use Options | File Templates.
 */
public class ApplicationContext {
    private static Properties props = null;
    static {
       props = PropertyLoader.loadProperties(NautilusConstants.APPLICATION_RESOURCES);
    }
    public static Properties getLabelProperties() {
        return props;
    }
}
