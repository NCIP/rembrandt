package gov.nih.nci.nautilus.util;

import gov.nih.nci.nautilus.constants.NautilusConstants;

import java.util.Properties;

public class ApplicationContext {
    private static Properties labelProps = null;
    
    static {
       labelProps = PropertyLoader.loadProperties(NautilusConstants.APPLICATION_RESOURCES);
    }
    public static Properties getLabelProperties() {
        return labelProps;
    }
}
