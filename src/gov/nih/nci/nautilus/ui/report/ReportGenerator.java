package gov.nih.nci.nautilus.ui.report;

import gov.nih.nci.nautilus.resultset.Resultant;

import org.dom4j.Document;

/**
 * This interface defines the operations needed to generate a
 * report 
 * @author BauerD
 * Feb 8, 2005
 * 
 */
public interface ReportGenerator {
    
	 /*
     * This method should take a resultant and then
     * return an XML string that will be sent
     * to the reportJSP.
	 */
    public Document getReportXML(Resultant resultant);
}
