/*
 * Created on Sep 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.de.*;
import java.util.*;
/**
 * @author SahniH
 *
 * This class encapulates a collection of ReporterResultset objects.
 */
public class GeneResultset {

	  private GeneIdentifierDE.GeneSymbol geneSymbol = null;
	  private GeneIdentifierDE.GenBankAccessionNumber genbankAccessionNo = null;
	  private GeneIdentifierDE.LocusLink locusLinkID = null;
	  //private DataSetDE. dataset;
	  private SortedMap reporters = new TreeMap();

	public static void main(String[] args) {
	}

	/**
	 * @return Returns the genbankAccessionNo.
	 */
	public GeneIdentifierDE.GenBankAccessionNumber getGenbankAccessionNo() {
		return genbankAccessionNo;
	}
	/**
	 * @param genbankAccessionNo The genbankAccessionNo to set.
	 */
	public void setGenbankAccessionNo(
			GeneIdentifierDE.GenBankAccessionNumber genbankAccessionNo) {
		this.genbankAccessionNo = genbankAccessionNo;
	}
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
	 * @return Returns the locusLinkID.
	 */
	public GeneIdentifierDE.LocusLink getLocusLinkID() {
		return locusLinkID;
	}
	/**
	 * @param locusLinkID The locusLinkID to set.
	 */
	public void setLocusLinkID(GeneIdentifierDE.LocusLink locusLinkID) {
		this.locusLinkID = locusLinkID;
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
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this GeneResultset object.
	 */
	public void removeDiseaseResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
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
    public void removeAllDiseaseResultset(){
    	reporters.clear();
    }
}
