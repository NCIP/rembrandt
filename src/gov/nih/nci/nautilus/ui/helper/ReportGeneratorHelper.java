package gov.nih.nci.nautilus.ui.helper;

import org.javaby.jbyte.Template;

import gov.nih.nci.nautilus.cache.CacheOverlord;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsetManager;

import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.report.ReportGenerator;
import gov.nih.nci.nautilus.ui.report.ReportGeneratorFactory;


/**
 * The ReportGeneratorHelper was written to act as a Report Generation
 * manager for the UI.  It provides a single avenue where, if a UI element 
 * has a Query to execute or the cache key to a previously stored Resultant,
 * the necesary calls are made to generate the correct (desired) report 
 * presentation format (THis is based on what we are calling a Skin.  It is
 * really just some text file that uses required tags). It will then pass
 * the completed Template along in the form of a org.javaby.jbyte.Template
 * stored in a ReportBean that will also contain the cache key where the 
 * resultant can be called again, if needed.
 *  
 * @author BauerD Feb 8, 2005
 *  
 */
public class ReportGeneratorHelper {
	static public String REPORT_ACTION = "generatedReport.do";
    private ReportBean reportBean;
    private Template reportTemplate;
    
	public ReportGeneratorHelper(Queriable query) {
		Resultant resultant = null;
        String cacheKey = null;
  
        if(query instanceof CompoundQuery) {
            try {
                resultant = ResultsetManager.executeCompoundQuery((CompoundQuery)query);
                
                /*
                 * We know that we are executing a compoundQuery which implies
                 * that this is the first time that this resultSet will have been 
                 * generated.  So let's store it in the cache just in case we 
                 * need it later. 
                 */
                CacheOverlord.getSessionCache("test");
                
                /*
                 * store the resultant in the cache:
                 * create a key for the resultant based on name
                 */
            }catch (Throwable t) {
                  
             }
        }
        
        /* This logic should determine the type of Skin to use based
         * on input (what exactly, you ask? I don't know yet.) 
         */
        if(resultant!=null) {
            ReportGenerator reportGen = ReportGeneratorFactory.getReportGenerator(resultant);
            reportTemplate = reportGen.getReportTemplate(resultant, ReportGenerator.HTML_SKIN);
            reportBean.setResultantCacheKey(cacheKey);
            reportBean.setReportTemplate(reportTemplate);
        }
	}
    
    /*
     * This constructor to use in the instance that there may be 
     * a preexisting resultSet stored in the cache.
     * 
     */
   	public ReportGeneratorHelper(String resultantCacheKey) {
    	//Used to retrieve the resultant from the cache
        //and if needed retrieve the associated query and
        // rerun the query
        
    }

    public ReportBean getReportBean() {
		return reportBean;
	}
}
