/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;

import gov.nih.nci.nautilus.de.CytobandDE;
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
public class CytobandResultset {
	/**
	 * @return Returns the cytoband.
	 */
	public CytobandDE getCytoband() {
		return cytoband;
	}
	/**
	 * @param cytoband The cytoband to set.
	 */
	public void setCytoband(CytobandDE cytoband) {
		this.cytoband = cytoband;
	}
	/**
	 * @return Returns the isAnonymousCytoband.
	 */
	public boolean isAnonymousCytoband() {
		return isAnonymousCytoband;
	}
	/**
	 * @param isAnonymousCytoband The isAnonymousCytoband to set.
	 */
	public void setAnonymousCytoband(boolean isAnonymousCytoband) {
		this.isAnonymousCytoband = isAnonymousCytoband;
	}
	  private CytobandDE cytoband = null;
	  private boolean isAnonymousCytoband = false;
	  private SortedMap reporters = new TreeMap();

	public static void main(String[] args) {
	}


	/**
	 * @param reporterResultset Adds reporterResultset to this CytobandResulset object.
	 */
	public void addReporterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.put(reporterResultset.getReporter().getValue().toString(), reporterResultset);
		}
	}
	/**
	 * @param reporterResultset Removes reporterResultset from this CytobandResulset object.
	 */
	public void removeRepoterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
	}
    /**
     * @param reporter
	 * @return reporterResultset Returns reporterResultset for this CytobandResulset.
	 */
    public ReporterResultset getRepoterResultset(String reporter){
    	if(reporter != null){
			return (ReporterResultset) reporters.get(reporter);
		}
    		return null;
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this CytobandResulset object.
	 */
    public Collection getReporterResultsets(){
    		return reporters.values();
    }
	/**
	 * @param none Removes all reporterResultset in this CytobandResulset object.
	 */
    public void removeAllReporterResultsets(){
    	reporters.clear();
    }


}
