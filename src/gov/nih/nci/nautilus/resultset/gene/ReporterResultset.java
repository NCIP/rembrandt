/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.gene;
import java.util.*;

import gov.nih.nci.nautilus.de.*;
/**
 * @author SahniH
 *
 This class encapulates a collection of SampleResultset objects.
 */
public class ReporterResultset {
	private DatumDE reporter = null;
	private SortedMap groupTypes = new TreeMap();

	/**
	 * 
	 */
	public ReporterResultset(DatumDE repoter) {
		setReporter(repoter);
	}
	/**
	 * @param groupResultset Adds groupResultset to this ReporterResultset object.
	 */
	public void addGroupByResultset(Groupable groupResultset){
		if(groupResultset != null && groupResultset.getType() != null){
			groupTypes.put(groupResultset.getType().getValue().toString(), groupResultset);
		}
	}
	/**
	 * @param groupResultset Removes groupResultset to this ReporterResultset object.
	 */
	public void removeGroupByResultset(Groupable groupResultset){
		if(groupResultset != null && groupResultset.getType() != null){
			groupTypes.remove(groupResultset.getType().getValue().toString());
		}
	}
    /**
     * @param disease
	 * @return groupResultset Returns reporterResultset for this ReporterResultset.
	 */
    public Groupable getGroupByResultset(String groupType){
    	if(groupType != null){
			return (Groupable) groupTypes.get(groupType);
		}
    		return null;
    }
	/**
	 * @return Collection Returns collection of GroupResultsets to this ReporterResultset object.
	 */
    public Collection getGroupByResultsets(){
    		return groupTypes.values();
    }
	/**
	 * @param none Removes all groupResultset in this ReporterResultset object.
	 */
    public void removeAllGroupByResultset(){
    	groupTypes.clear();
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
