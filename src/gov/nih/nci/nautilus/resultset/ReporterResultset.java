/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import java.util.SortedMap;
import java.util.TreeMap;

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
	 * @param diseaseResultset Adds diseaseResultset to this DiseaseResultset object.
	 */
	public void addDiseaseResultset(DiseaseResultset diseaseResultset){
		if(diseaseResultset != null && diseaseResultset.getDieaseType() != null){
			diseases.put(diseaseResultset.getDieaseType(), diseaseResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this DiseaseResultset object.
	 */
	public void removeDiseaseResultset(DiseaseResultset diseaseResultset){
		if(diseaseResultset != null && diseaseResultset.getDieaseType() != null){
			diseases.remove(diseaseResultset.getDieaseType());
		}
	}
	/**
	 * @return diseaseResultset Returns diseaseResultset to this DiseaseResultset object.
	 */
    public DiseaseResultset[] getDiseaseResultset(){
    		return (DiseaseResultset[]) diseases.values().toArray();
    }
	/**
	 * @param none Removes all diseaseResultset in this DiseaseResultset object.
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
