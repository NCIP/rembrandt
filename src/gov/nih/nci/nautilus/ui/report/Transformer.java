package gov.nih.nci.nautilus.ui.report;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
 
/**
 * This class transforms an XML document to HTML
 * using a style sheet
 * @author wollnyj, LandyR
 * http://forum.java.sun.com/thread.jspa?threadID=579355&messageID=2921653
 */
public class Transformer {
 
    private static TransformerFactory m_tFactory;
    private Map m_params;
    private File m_xsl;
    private Templates m_template;
 
   /**
     * Constructor for Transformer.
     * @param xsl The style sheet
     */
    public Transformer(File xsl) {
        this(xsl,new HashMap(0));
    }
    
    /**
     * Constructor for Transformer.
     * @param xsl The style sheet
     * @param params style sheet parameters
     */
    public Transformer(File xsl,Map params) {
        
        if (m_tFactory == null) {
            synchronized(Transformer.class) {
                if (m_tFactory == null) {
 
                    //and instantiate a factory
                    m_tFactory = TransformerFactory.newInstance();
                }
            }
        }
 
        m_xsl = xsl;
        m_params = params;        
        
        try {
            createTemplate(new StreamSource(m_xsl));
        } catch (TransformerException e) {
            //@todo handle this
        }
    }
    
    /**
     * Set the parameters used by the style sheet
     * @param params The style sheet parameters
     */
    public void setParameters(Map params) {
        m_params = params;   
    }
    
    /**
     * Transform the input document
     * @param xml The input xml
     * @return The rendered HTML
     * @throws IOException
     */
    public Document transform(Document document) throws IOException {
 
        String renderedHTML="";            
 
        javax.xml.transform.Transformer transformer;
        
        Document transformedDoc = null;
		
        try {
    
            transformer = m_template.newTransformer();
            assignParameters(transformer,m_params);
            DocumentSource fileSource = new DocumentSource( document );
            //Source fileSource = new StreamSource(xml);
            //Result result = new StreamResult(renderedHTML);
            DocumentResult result = new DocumentResult();
            
            transformer.transform(fileSource,result); 
            
            transformedDoc = result.getDocument();
            
        } catch (TransformerConfigurationException e) {
            throw new IOException(e.getMessage());
        } catch (TransformerException e) {
            throw new IOException(e.getMessage());
        }
 
        return transformedDoc; 
    }
    
    /**
     * Returns the xsl.
     * @return The style sheet file
     */
    public File getXsl() {
        return m_xsl;
    }
 
    /**
     * Sets the xsl.
     * @param xsl The xsl file to set
     */
    public void setXsl(File xsl) {
        m_xsl = xsl;
    }
 
    private void createTemplate(Source xslSource) throws TransformerException {
     
        if (m_template == null) {
            m_template = m_tFactory.newTemplates(xslSource);
        }
    }  
    
    private void assignParameters(javax.xml.transform.Transformer transformer,Map params) {
 
        if (params != null) {
            for(Iterator it=params.entrySet().iterator();it.hasNext();) {
    
                Map.Entry entry = (Map.Entry)it.next();
    
                transformer.setParameter(entry.getKey().toString(),entry.getValue().toString());
            }
        }
    }
}
