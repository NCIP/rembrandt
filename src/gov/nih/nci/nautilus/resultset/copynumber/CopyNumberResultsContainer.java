/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;

import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CytobandResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyNumberResultsContainer implements ResultsContainer{
	protected SortedMap cytobands = new TreeMap();
	protected SortedMap groupsLabels = new TreeMap();
	/**
	 * @return Returns the groupsLabels.
	 */
	public Collection getGroupsLabels() {
		return  this.groupsLabels.keySet();
	}

	/**
	 * @param cytobandResultset Adds cytobandResultset to this CopyNumberResultsContainer object.
	 */
	public void addCytobandResultset(CytobandResultset cytobandResultset){
		if(cytobandResultset != null && cytobandResultset.getCytoband() != null){
			cytobands.put(cytobandResultset.getCytoband().getValue().toString(), cytobandResultset);
		}
	}
	/**
	 * @param cytobandResultset Removes cytobandResultset to this CopyNumberResultsContainer object.
	 */
	public void removeCytobandResultset(CytobandResultset cytobandResultset){
		if(cytobandResultset != null && cytobandResultset.getCytoband() != null){
			cytobands.remove(cytobandResultset.getCytoband().toString());
		}
	}
	/**
	 * @return cytobandResultset Returns cytobandResultset to this CopyNumberResultsContainer object.
	 */
    public Collection getCytobandResultsets(){
    		return cytobands.values();
    }
    /**
     * @param cytoband
	 * @return cytobandResultset Returns cytobandResultset to this cytoband.
	 */
    public CytobandResultset getCytobandResultset(String cytoband){
    	if(cytoband != null){
			return (CytobandResultset) cytobands.get(cytoband);
		}
    		return null;
    }
    /**
     * @param cytoband
	 * @return reporterResultset Returns reporterResultset for this cytoband.
	 */
    public Collection getRepoterResultsets(String cytoband){
    	if(cytoband != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(cytoband);
			return cytobandResultset.getReporterResultsets();
		}
    		return null;
    }
	/**
	 * @param none Removes all cytobandResultset in this CopyNumberResultsContainer object.
	 */
    public void removeAllCytobandResultset(){
    	cytobands.clear();
    }
    /**
     * @param cytoband,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & cytoband.
	 */
    public Collection getGroupByResultsets(String cytoband,String reporterName){
    	if(cytoband!= null && reporterName != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(cytoband);
    		ReporterResultset reporterResultset = (ReporterResultset) cytobandResultset.getRepoterResultset(reporterName);
			return reporterResultset.getGroupByResultsets();
		}
    		return null;
    }
}
