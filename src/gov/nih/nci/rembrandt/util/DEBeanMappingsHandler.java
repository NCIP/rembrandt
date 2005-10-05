/*
 * Created on Apr 4, 2005
 */
package gov.nih.nci.rembrandt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author BhattarR, BauerD
 */
final public class DEBeanMappingsHandler {

	private final static String DOMAIN_ELEMENT = "DomainElement";
	private final static String NAME = "name";
	private final static String MAPPED_BEAN = "mappedBean";
	private final static String ATTRIBUTE = "attribute";

	public Map populate(Document doc) {
		HashMap deBeanMappings = new HashMap();
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (!node.getNodeName().equalsIgnoreCase(DOMAIN_ELEMENT))
				continue;
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
				if (!childNode.getNodeName().equals(MAPPED_BEAN))
					continue;
				DEBeanAttrMapping deMapObj = new DEBeanAttrMapping();
				NamedNodeMap childAttrs = childNode.getAttributes();
				for (int k = 0; k < childAttrs.getLength(); ++k) {
					Node attribute = childAttrs.item(k);
					if (attribute.getNodeName().equalsIgnoreCase(NAME)) {
						deMapObj.setMappedBean(attribute.getNodeValue());
					}
					if (attribute.getNodeName().equalsIgnoreCase(ATTRIBUTE)) {
						deMapObj.setMappedBeanAttribute(attribute.getNodeValue());
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
		return deBeanMappings;
	}

}
