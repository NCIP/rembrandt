package gov.nih.nci.rembrandt.queryservice.resultset.sample;

import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;



/**
 * @author SahniH
 * Date: Oct 22, 2004
 * 
 */
public class SampleResultset extends BioSpecimenResultset {
	private DiseaseNameDE disease = null;
	
	private GeneExprSingleViewResultsContainer geneExprSingleViewResultsContainer = null;
	private CopyNumberSingleViewResultsContainer copyNumberSingleViewResultsContainer = null;

	/**
	 * @return Returns the disease.
	 */
	public DiseaseNameDE getDisease() {
		return this.disease;
	}
	/**
	 * @param disease The disease to set.
	 */
	public void setDisease(DiseaseNameDE disease) {
		this.disease = disease;
	}
	/**
	 * @return Returns the geneExprSingleViewResultsContainer.
	 */
	public GeneExprSingleViewResultsContainer getGeneExprSingleViewResultsContainer() {
		return this.geneExprSingleViewResultsContainer;
	}
	/**
	 * @param geneExprSingleViewResultsContainer The geneExprSingleViewResultsContainer to set.
	 */
	public void setGeneExprSingleViewResultsContainer(
			GeneExprSingleViewResultsContainer geneExprSingleViewResultsContainer) {
		this.geneExprSingleViewResultsContainer = geneExprSingleViewResultsContainer;
	}

	/**
	 * @return Returns the copyNumberSingleViewResultsContainer.
	 */
	public CopyNumberSingleViewResultsContainer getCopyNumberSingleViewResultsContainer() {
		return copyNumberSingleViewResultsContainer;
	}
	/**
	 * @param copyNumberSingleViewResultsContainer The copyNumberSingleViewResultsContainer to set.
	 */
	public void setCopyNumberSingleViewResultsContainer(
			CopyNumberSingleViewResultsContainer copyNumberSingleViewResultsContainer) {
		this.copyNumberSingleViewResultsContainer = copyNumberSingleViewResultsContainer;
	}
}
