package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.DescriptorRepository;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.apache.xerces.impl.xs.dom.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:06:52 PM
 * To change this template use Options | File Templates.
 */
abstract public class QueryHandler {
    private static HashMap deBeanMappings = new HashMap();
    private final static String FILE_NAME ="/deToBeanAttrMappings.xml";
    private static Document doc;
    abstract Map handle(Query query) throws Exception;
    static {
       //TODO: complete this
       try {
           InputStream inStream = QueryHandler.class.getResourceAsStream(FILE_NAME);
           assert(inStream != null);
           DOMParser p = new DOMParser();
           p.parse(new InputSource(inStream));
           doc = p.getDocument();
           assert(doc != null);
           new DEBeanMappingsHandler().populate();
           System.out.println("Populating completed");

       } catch(Throwable t) {
           //TODO: rethrow this exception with some message
           t.printStackTrace();
       }
    }
    final static DEBeanAttrMapping getBeanAttrMappingFor(String deClassName) throws Exception {
        return getBeanAttrMappingFor(deClassName, null);
    }
    final static DEBeanAttrMapping getBeanAttrMappingFor(String deClassName, String inBeanName) throws Exception {
        ArrayList valueObjects = (ArrayList) deBeanMappings.get(deClassName);
        DEBeanAttrMapping value = null;

        if (valueObjects == null)
           throw new Exception("DEBeanAttrMapping  could not be found for: " + deClassName);

        if (inBeanName != null)
            for (int i = 0; i < valueObjects.size(); i++) {
                value = (DEBeanAttrMapping) valueObjects.get(i);
                if (value.mappedBean.equals(inBeanName)) return value;
            }
        else
            value = (DEBeanAttrMapping) valueObjects.get(0);

        if (value != null) return value;
        else throw new Exception("DEBeanAttrMapping  could not be found for: " + deClassName + "within " + inBeanName);
    }

    protected static String getColumnName(PersistenceBroker pb, String deClassName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        DEBeanAttrMapping mappingObj = getBeanAttrMappingFor(deClassName);
        String beanName = mappingObj.mappedBean;
        String beanAttrName = mappingObj.mappedBeanAttribute;
        ClassDescriptor cd = dr.getDescriptorFor( beanName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(beanAttrName);
        return fd.getColumnName();
    }
    protected static String getAttrNameForTheDE(String deClassName, String inBeanName) throws Exception{
        DEBeanAttrMapping  mappingsObj = getBeanAttrMappingFor(deClassName, inBeanName);
        return mappingsObj.mappedBeanAttribute;
    }
    protected static String getColumnName(PersistenceBroker pb, String deClassName, String inBeanName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        DEBeanAttrMapping mappingObj = getBeanAttrMappingFor(deClassName, inBeanName);
        String beanName = mappingObj.mappedBean;
        String beanAttrName = mappingObj.mappedBeanAttribute;
        ClassDescriptor cd = dr.getDescriptorFor( beanName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(beanAttrName);
        return fd.getColumnName();
    }
     // used in report queries for columnNames attrributes
     protected static String getColumnNameForBean(PersistenceBroker pb, String beanClassName, String attrName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        ClassDescriptor cd = dr.getDescriptorFor( beanClassName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(attrName);
        return fd.getColumnName();
    }

    private final static class DEBeanMappingsHandler {
        private final static String DOMAIN_ELEMENT = "DomainElement";
        private final static String NAME = "name";
        private final static String MAPPED_BEAN= "mappedBean";
        private final static String ATTRIBUTE= "attribute";

        public void populate() {

         Element root = doc.getDocumentElement();
         NodeList nodes = root.getChildNodes();
         for (int i = 0; i < nodes.getLength(); i++) {
             Node node = nodes.item(i);
             if (!node.getNodeName().equalsIgnoreCase(DOMAIN_ELEMENT)) continue;
             String nameKey = null;
             NamedNodeMap attrs = node.getAttributes();
             for (int j = 0; j < attrs.getLength(); ++j) {
                 Node attribute = attrs.item(j);
                 if (attribute.getNodeName().equalsIgnoreCase(NAME)) {
                      nameKey = attribute.getNodeValue();
                 }
             }

             NodeList childNodes = node.getChildNodes();
             for (int j = 0; j < childNodes.getLength(); j++) {
                 Node childNode = childNodes.item(j);
                 if (! childNode.getNodeName().equals(MAPPED_BEAN)) continue;
                  DEBeanAttrMapping deMapObj = new DEBeanAttrMapping();
                  NamedNodeMap childAttrs = childNode.getAttributes();
                  for (int k = 0; k < childAttrs.getLength(); ++k) {
                        Node attribute = childAttrs.item(k);
                        if (attribute.getNodeName().equalsIgnoreCase(NAME)) {
                            deMapObj.mappedBean = attribute.getNodeValue();
                        }
                        if (attribute.getNodeName().equalsIgnoreCase(ATTRIBUTE)) {
                            deMapObj.mappedBeanAttribute = attribute.getNodeValue();
                            }
                  }
                  if (nameKey != null) {
                        ArrayList mappingObjs = null;
                        if (deBeanMappings.get(nameKey) != null) {
                            mappingObjs = (ArrayList) deBeanMappings.get(nameKey);
                        } else {
                            mappingObjs = new ArrayList();
                            deBeanMappings.put(nameKey, mappingObjs);
                        }
                        mappingObjs.add(deMapObj);
                  }
              }
            }
        }
    }
    protected static class DEBeanAttrMapping  {
        String mappedBean;
        String mappedBeanAttribute;
        String name;
        public DEBeanAttrMapping() {}
        public DEBeanAttrMapping(String name, String mappedBeanName, String beanAttributeName) {
            this.name = name;
            this.mappedBean = mappedBeanName;
            this.mappedBeanAttribute = beanAttributeName;
        }
    }
}
