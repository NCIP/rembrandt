package gov.nih.nci.nautilus.constants;

public final class NautilusConstants {

    //Specifies the location in the webapp classes directory
    //to find ApplicationResources.properties
    public static final String  APPLICATION_RESOURCES = "ApplicationResources";
    //Specifies where in the webapps classes directory to find
 	//log4j.properties
 	public static final String LOGGING_PROPERTIES = "log4j.properties";

    public static final String CACHE_PROPERTIES = "rembrandtCache";

	public static final String SESSION_QUERY_BAG_KEY = "nautilus.queryMap";

	public static final String RESULTSET_KEY = "geneViewResultSet";

	public static final String VALID_QUERY_TYPES_KEY = "nautilus.request.validQuerytypes";

	public static final String NORMAL = "NON_TUMOR";

	public static final String ASTRO = "ASTROCYTOMA";

	public static final String LOGGER = "gov.nih.nci.nautilus";

	public static final String JSP_LOGGER = "gov.nih.nci.nautilus.jsp";

	public static final int MAX_FILEFORM_COUNT = 40000;
	public static final String REPORT_BEAN = "reportBean";
	public static final String REPORT_XML = "reportXML";
}
