package gov.nih.nci.rembrandt.util;

import gov.nih.nci.rembrandt.analysis.server.AnalysisServerClientManager;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.impl.xs.dom.DOMParser;
/**
 * @todo comment this!
 * @author BauerD
 *
 */
public class ApplicationContext{
	private static Map mappings = new HashMap();
	private static Logger logger = Logger.getLogger(ApplicationContext.class);
	private static Properties labelProps = null;
	private static Properties messagingProps = null;
    private static Document doc =null;
   /**
    * COMMENT THIS
    * @return
    */
    public static Properties getLabelProperties() {
        return labelProps;
    }
    public static Map getDEtoBeanAttributeMappings() {
    	return mappings;
    }
    public static Properties getJMSProperties(){
    	return messagingProps;
    }
    @SuppressWarnings("unused")
	public static void init() {
    	 logger.debug("Loading Application Resources");
         labelProps = PropertyLoader.loadProperties(RembrandtConstants.APPLICATION_RESOURCES);
         messagingProps = PropertyLoader.loadProperties(RembrandtConstants.JMS_PROPERTIES);
         try {
	          logger.debug("Bean to Attribute Mappings");
	          InputStream inStream = QueryHandler.class.getResourceAsStream(RembrandtConstants.DE_BEAN_FILE_NAME);
	          assert true:inStream != null;
	          DOMParser p = new DOMParser();
	          p.parse(new InputSource(inStream));
	          doc = p.getDocument();
	          assert(doc != null);
	          logger.debug("Begining DomainElement to Bean Mapping");
	          mappings = new DEBeanMappingsHandler().populate(doc);
	          logger.debug("DomainElement to Bean Mapping is completed");
	          QueryHandler.init();
	      } catch(Throwable t) {
	         logger.error(new IllegalStateException("Error parsing deToBeanAttrMappings.xml file: Exception: " + t));
	      }
      //Start the JMS Lister
      try {
		@SuppressWarnings("unused") AnalysisServerClientManager analysisServerClientManager = AnalysisServerClientManager.getInstance();
		} catch (NamingException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch (JMSException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch(Throwable t) {
			logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(t.getMessage());
			logger.error(t);
		}
    }
}
