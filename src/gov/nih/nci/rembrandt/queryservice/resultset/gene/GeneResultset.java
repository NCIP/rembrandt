/*
 * Created on Sep 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset.gene;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * @author SahniH
 *
 * This class encapulates a collection of ReporterResultset objects.
 */
public class GeneResultset {

	  private GeneIdentifierDE.GeneSymbol geneSymbol = null;
	  private boolean isAnonymousGene = false;
	  //private DataSetDE. dataset;
	  private SortedMap reporters = new TreeMap();


	/**
	 * @return Returns the geneSymbol.
	 */
	public GeneIdentifierDE.GeneSymbol getGeneSymbol() {
		return geneSymbol;
	}
	/**
	 * @param geneSymbol The geneSymbol to set.
	 */
	public void setGeneSymbol(GeneIdentifierDE.GeneSymbol geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	/**
	 * @param reporterResultset Adds reporterResultset to this GeneResultset object.
	 */
	public void addReporterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.put(reporterResultset.getReporter().getValue().toString(), reporterResultset);
		}
	}
	/**
	 * @param reporterResultset Removes reporterResultset from this GeneResultset object.
	 */
	public void removeRepoterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
	}
    /**
     * @param reporter
	 * @return reporterResultset Returns reporterResultset for this GeneResultset.
	 */
    public ReporterResultset getRepoterResultset(String reporter){
    	if(reporter != null){
			return (ReporterResultset) reporters.get(reporter);
		}
    		return null;
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this GeneResultset object.
	 */
    public Collection getReporterResultsets(){
    		return reporters.values();
    }
	/**
	 * @param none Removes all reporterResultset in this GeneResultset object.
	 */
    public void removeAllReporterResultsets(){
    	reporters.clear();
    }

	/**
	 * For genes that do not have a Gene Symbol associated with it
	 */
	public void setAnonymousGene() {
		isAnonymousGene = true;
		
	}
	/**
	 * @return Returns the isAnonymousGene.
	 */
	public boolean isAnonymousGene() {
		return this.isAnonymousGene;
	}
    public List getReporterNames(){
    	return new ArrayList(reporters.keySet());
    }
    
    
}
