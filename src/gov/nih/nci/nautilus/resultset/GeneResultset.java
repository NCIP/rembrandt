/*
 * Created on Sep 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.de.*;
/**
 * @author SahniH
 *
 * This class encapulates a collection of ReporterResultset objects.
 */
public class GeneResultset {
	  private DiseaseNameDE diseaseName;
	  private GeneIdentifierDE.GeneSymbol geneSymbol;
	  private GeneIdentifierDE.GenBankAccessionNumber genbankAccessionNo;
	  private GeneIdentifierDE.LocusLink locusLinkID;

	  private Long datasetId;
	  private Long diseaseTypeId;
	  private Long geneId;

	public static void main(String[] args) {
	}
	/**
	 * @return Returns the datasetId.
	 */
	public Long getDatasetId() {
		return datasetId;
	}
	/**
	 * @param datasetId The datasetId to set.
	 */
	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}
	/**
	 * @return Returns the diseaseTypeId.
	 */
	public Long getDiseaseTypeId() {
		return diseaseTypeId;
	}
	/**
	 * @param diseaseTypeId The diseaseTypeId to set.
	 */
	public void setDiseaseTypeId(Long diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
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
}
