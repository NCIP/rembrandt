package gov.nih.nci.nautilus.ui.report;

import gov.nih.nci.nautilus.resultset.Resultant;
import org.javaby.jbyte.Template;

/**
 * This interface defines the operations needed to generate a 
 * @author BauerD
 * Feb 8, 2005
 * 
 */
public interface ReportGenerator {
    
	//This defines the template (skin) to use for HTML rendered reports
    public static String HTML_SKIN = "someHTML.txtfile";
    //This defines the template (skin) to use for the CSV rendered reports
    public static String CSV_SKIN = "someCSV.skinfile";
	
    
    /*
     * This method should take a resultant and then
     * return a populated Template that will be sent
     * to the reportJSP.
	 */
    public Template getReportTemplate(Resultant resultant, String skin);
}
