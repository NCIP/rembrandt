package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.BioSpecimenResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author SahniH
 * Date: Nov 11, 2004
 * 
 */
public class SampleKaplanMeierPlotResultset extends SampleResultset{
	private Map reporters = new HashMap();
	/**
	 * @param biospecimenID
	 */
	public SampleKaplanMeierPlotResultset(SampleIDDE sampleIDDE) {		
		this.setSampleIDDE(sampleIDDE);
	}

	/**
	 * @param reporterResultset Adds reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
	public void addReporterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.put(reporterResultset.getReporter().getValue().toString(), reporterResultset);
		}
	}
	/**
	 * @param reporterResultset Removes reporterResultset from this DiseaseGeneExprPlotResultset object.
	 */
	public void removeResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
	}
    /**
     * @param reporter
	 * @return reporterResultset Returns reporterResultset for this DiseaseGeneExprPlotResultset.
	 */
    public ReporterResultset getReporterResultset(String reporter){
    	if(reporter != null){
			return (ReporterResultset) reporters.get(reporter);
		}
    		return null;
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
    public Collection getReporterResultsets(){
    		return reporters.values();
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
    public DatumDE getSummaryReporterFoldChange(){
    DatumDE foldChangeRatioValue = null;    
	Collection reporters = getReporterResultsets();
	int numberOfReporters = reporters.size();
	if(numberOfReporters > 0){
		double reporterValues = 0.0;
		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			ReporterResultset reporter = (ReporterResultset) reporterIterator.next();
			double foldchange = new Double(reporter.getValue().getValue().toString()).doubleValue();
			reporterValues += foldchange;
		}
		Double geneExprAverage = new Double (reporterValues / numberOfReporters);
		foldChangeRatioValue = new DatumDE(DatumDE.FOLD_CHANGE_RATIO,geneExprAverage);
	}
	return foldChangeRatioValue;
    }
	/**
	 * @param none Removes all reporterResultset in this DiseaseGeneExprPlotResultset object.
	 */
    public void removeAllReporterResultsets(){
    	reporters.clear();
    }

	/**
	 * @return Returns the reporters.
	 */
	public Map getReporters() {
		return reporters;
	}
	/**
	 * @return Returns the reporter Names.
	 */
	public List getReporterNames() {
        List reporterNames = new ArrayList();
        Collection reporterList = reporters.keySet();
        reporterNames.addAll(reporterList);
		return reporterNames;
	}
	/**
	 * @param reporters The reporters to set.
	 */
	public void setReporters(Map reporters) {
		this.reporters = reporters;
	}
    
    public String toString() {
    	if(this.getCensor() != null && getSurvivalLength() != null){
    		return "Census: "+this.getCensor().getValue()+" Survival Length: "+ getSurvivalLength();
    	}
    		return super.toString();
    }
}
