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
    private final static String FILE_NAME = "/"+NautilusConstants.APPLICATION_RESOURCES+".properties";
    static {
        try {
            props = new Properties();
            props.load(ApplicationContext.class.getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static Properties getLabelProperties() {
        return props;
    }
}
