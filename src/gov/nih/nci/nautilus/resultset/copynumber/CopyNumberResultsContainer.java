/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;

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
public class CopyNumberResultsContainer {
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
     * @param geneSymbol
	 * @return cytobandResultset Returns cytobandResultset to this geneSymbol.
	 */
    public CytobandResultset getCytobandResultset(String geneSymbol){
    	if(geneSymbol != null){
			return (CytobandResultset) cytobands.get(geneSymbol);
		}
    		return null;
    }
    /**
     * @param geneSymbol
	 * @return reporterResultset Returns reporterResultset for this geneSymbol.
	 */
    public Collection getRepoterResultsets(String geneSymbol){
    	if(geneSymbol != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(geneSymbol);
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
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getGroupByResultsets(String geneSymbol,String reporterName){
    	if(geneSymbol!= null && reporterName != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) cytobandResultset.getRepoterResultset(reporterName);
			return reporterResultset.getGroupByResultsets();
		}
    		return null;
    }
}
