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
	  private Long geneId;

	public static void main(String[] args) {
	}
	/**
	 * @return Returns the genbankAccessionNo.
	 */
	public String getGenbankAccessionNo() {
		return genbankAccessionNo.getValueObject();
	}
	/**
	 * @param genbankAccessionNo The genbankAccessionNo to set.
	 */
	public void setGenbankAccessionNo(String genbankAccessionNo) {
		this.genbankAccessionNo.setValueObject(genbankAccessionNo);
	}
	/**
	 * @return Returns the geneId.
	 */
	public Long getGeneId() {
		return geneId;
	}
	/**
	 * @param geneId The geneId to set.
	 */
	public void setGeneId(Long geneId) {
		this.geneId = geneId;
	}
	/**
	 * @return Returns the geneSymbol.
	 */
	public String getGeneSymbol() {
		return geneSymbol.getValueObject();
	}
	/**
	 * @param geneSymbol The geneSymbol to set.
	 */
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol.setValueObject(geneSymbol);
	}
	/**
	 * @return Returns the locusLinkID.
	 */
	public String getLocusLinkID() {
		return locusLinkID.getValueObject();
	}
	/**
	 * @param locusLinkID The locusLinkID to set.
	 */
	public void setLocusLinkID(String locusLinkID) {
		this.locusLinkID.setValueObject(locusLinkID);
	}
	/**
	 * @param reporterResultset Adds reporterResultset to this GeneResultset object.
	 */
	public void addReporterResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporterID() != null){
			reporters.put(reporterResultset.getReporterID(), reporterResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this GeneResultset object.
	 */
	public void removeDiseaseResultset(ReporterResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporterType() != null){
			reporters.remove(reporterResultset.getReporterType());
		}
	}
	/**
	 * @return reporterResultset Returns reporterResultset to this GeneResultset object.
	 */
    public ReporterResultset[] getReporterResultsets(){
    		return (ReporterResultset[]) reporters.values().toArray();
    }
	/**
	 * @param none Removes all reporterResultset in this GeneResultset object.
	 */
    public void removeAllDiseaseResultset(){
    	reporters.clear();
    }
}
