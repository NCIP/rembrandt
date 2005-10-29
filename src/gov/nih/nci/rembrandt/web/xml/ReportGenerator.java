package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.caintegrator.service.findings.Resultant;

import java.util.Map;

import org.dom4j.Document;

/**
 * This interface defines the operations needed to generate a
 * report 
 * @author BauerD,LandyR
 * Feb 8, 2005
 * 
 */
public interface ReportGenerator {
    
	 /*
     * This method should take a resultant and then
     * return an XML string that will be sent
     * to the reportJSP.
	 */
    public Document getReportXML(Resultant resultant, Map filterMapParams);
}
