/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import java.util.*;

import gov.nih.nci.nautilus.de.*;
/**
 * @author SahniH
 *
 This class encapulates a collection of SampleResultset objects.
 */
public class ReporterResultset {
	private DatumDE reporter = null;
	private SortedMap diseases = new TreeMap();

	/**
	 * 
	 */
	public ReporterResultset(DatumDE repoter) {
		setReporter(repoter);
	}
	/**
	 * @param diseaseResultset Adds diseaseResultset to this ReporterResultset object.
	 */
	public void addDiseaseResultset(DiseaseResultset diseaseResultset){
		if(diseaseResultset != null && diseaseResultset.getDieaseType() != null){
			diseases.put(diseaseResultset.getDieaseType().getValue().toString(), diseaseResultset);
		}
	}
	/**
	 * @param diseaseResultset Removes diseaseResultset to this ReporterResultset object.
	 */
	public void removeDiseaseResultset(DiseaseResultset diseaseResultset){
		if(diseaseResultset != null && diseaseResultset.getDieaseType() != null){
			diseases.remove(diseaseResultset.getDieaseType());
		}
	}
    /**
     * @param disease
	 * @return diseaseResultset Returns reporterResultset for this ReporterResultset.
	 */
    public DiseaseResultset getDiseaseResultset(String disease){
    	if(disease != null){
			return (DiseaseResultset) diseases.get(disease);
		}
    		return null;
    }
	/**
	 * @return Collection Returns collection of DiseaseResultsets to this ReporterResultset object.
	 */
    public Collection getDiseaseResultsets(){
    		return diseases.values();
    }
	/**
	 * @param none Removes all diseaseResultset in this ReporterResultset object.
	 */
    public void removeAllDiseaseResultset(){
    	diseases.clear();
    }
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the reporterName.
	 */
	public DatumDE getReporter() {
		return reporter;
	}
	/**
	 * @param reporterID The reporterID to set.
	 */
	public void setReporter(DatumDE reporter) {
		this.reporter = reporter;
	}
	/**
	 * @return Returns the reporterType.
	 */
	public String getReporterType() {
		return reporter.getType();
	}
	
}
