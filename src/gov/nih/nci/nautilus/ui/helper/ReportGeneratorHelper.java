package gov.nih.nci.nautilus.ui.helper;

import org.javaby.jbyte.Template;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsetManager;

import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.report.ReportGenerator;
import gov.nih.nci.nautilus.ui.report.ReportGeneratorFactory;
import gov.nih.nci.nautilus.view.ClinicalSampleView;
import gov.nih.nci.nautilus.view.CopyNumberSampleView;
import gov.nih.nci.nautilus.view.GeneExprDiseaseView;
import gov.nih.nci.nautilus.view.GeneExprSampleView;
import gov.nih.nci.nautilus.view.Viewable;

/**
 * @author BauerD Feb 8, 2005
 *  
 */
public class ReportGeneratorHelper {
	static public String reportAction = "generatedReport.do";
    private ReportBean reportBean;
    private Template template;
    
	public ReportGeneratorHelper(Queriable query) {
		Resultant resultant = null;
        String cacheKey = null;
  
        if(query instanceof CompoundQuery) {
            try {
                resultant = ResultsetManager.executeCompoundQuery((CompoundQuery)query);
                /*
                 * store the resultant in the cache:
                 * create a key for the resultant
                 */
            }catch (Throwable t) {
                  
             }
        }
        if(resultant!=null) {
            ReportGenerator reportGen = ReportGeneratorFactory.getReportGenerator(resultant);
            template = reportGen.getReportTemplate(resultant, ReportGenerator.HTML_SKIN);
            reportBean.setResultantCacheKey(cacheKey);
            reportBean.setReportTemplate(template);
        }
	}
    
   	public ReportGeneratorHelper(String resultantCacheKey) {
    	//Used to retrieve the resultant from the cache
        //and if needed retrieve the associated query and
        // rerun the query    
    }
  
	
	/**
	 * @return Returns the template.
	 */
	public Template getTemplate() {
		return template;
	}
	/**
	 * @param template The template to set.
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}
	/**
	 * @return Returns the reportBean.
	 */
	public ReportBean getReportBean() {
		return reportBean;
	}
	/**
	 * @param reportBean The reportBean to set.
	 */
	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}
}
