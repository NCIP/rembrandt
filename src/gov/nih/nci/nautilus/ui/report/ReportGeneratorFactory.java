package gov.nih.nci.nautilus.ui.report;

import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.view.ClinicalSampleView;
import gov.nih.nci.nautilus.view.CopyNumberSampleView;
import gov.nih.nci.nautilus.view.GeneExprDiseaseView;
import gov.nih.nci.nautilus.view.GeneExprSampleView;
import gov.nih.nci.nautilus.view.Viewable;

/**
 * @author BauerD
 * Feb 8, 2005
 * 
 */
public class ReportGeneratorFactory {

	public static ReportGenerator getReportGenerator(Viewable viewType) {
   
        ReportGenerator reportGen;
        if(viewType instanceof ClinicalSampleView) {
           reportGen = new ClinicalSampleReport();
        }else if(viewType instanceof CopyNumberSampleView) {
            reportGen = new CopyNumberSampleReport();
        }else if(viewType instanceof GeneExprDiseaseView) {
            reportGen = new GeneExprDiseaseReport();
        }else if(viewType instanceof GeneExprSampleView) {
            reportGen = new GeneExprSampleReport();
        }else {
        	//Unsupported View Type
            reportGen = null;
        }
     	return reportGen;
    }
    
    public static ReportGenerator getReportGenerator(Resultant resultant) {
    	return getReportGenerator(resultant.getAssociatedView());
    }
   
}
