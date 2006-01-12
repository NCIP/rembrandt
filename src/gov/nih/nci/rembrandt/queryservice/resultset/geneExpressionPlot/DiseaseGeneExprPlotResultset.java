package gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot;

import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public class DiseaseGeneExprPlotResultset {
	private DiseaseNameDE type = null;
	private SortedMap reporters = new TreeMap();
	/**
	 * @param disease
	 */
	public DiseaseGeneExprPlotResultset(DiseaseNameDE disease) {
		
		setType(disease);
	}
	/**
	 * @return Returns the type.
	 */
	public DiseaseNameDE getType() {
		return this.type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(DiseaseNameDE type) {
		this.type = type;
	}
	/**
	 * @param reporterResultset Adds reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
	public void addReporterFoldChangeValuesResultset(ReporterFoldChangeValuesResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.put(reporterResultset.getReporter().getValue().toString(), reporterResultset);
		}
	}
	/**
	 * @param reporterResultset Removes reporterResultset from this DiseaseGeneExprPlotResultset object.
	 */
	public void removeReporterFoldChangeValuesResultset(ReporterFoldChangeValuesResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
	}
    /**
     * @param reporter
	 * @return reporterResultset Returns reporterResultset for this DiseaseGeneExprPlotResultset.
	 */
    public ReporterFoldChangeValuesResultset getReporterFoldChangeValuesResultset(String reporter){
    	if(reporter != null){
			return (ReporterFoldChangeValuesResultset) reporters.get(reporter);
		}
    		return null;
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
    public Collection getReporterFoldChangeValuesResultsets(){
    		return reporters.values();
    }
	/**
	 * @param none Removes all reporterResultset in this DiseaseGeneExprPlotResultset object.
	 */
    public void removeAllReporterFoldChangeValuesResultsets(){
    	reporters.clear();
    }
}
