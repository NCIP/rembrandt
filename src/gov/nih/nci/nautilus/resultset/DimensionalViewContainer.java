/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;

import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DimensionalViewContainer implements ResultsContainer {
	private GeneExprSingleViewResultsContainer geneExprSingleViewContainer = null;
	private CopyNumberSingleViewResultsContainer copyNumberSingleViewContainer = null;
	private SampleViewResultsContainer sampleViewResultsContainer = null;

	/**
	 * @return Returns the copyNumberSingleViewContainer.
	 */
	public CopyNumberSingleViewResultsContainer getCopyNumberSingleViewContainer() {
		return copyNumberSingleViewContainer;
	}
	/**
	 * @param copyNumberSingleViewContainer The copyNumberSingleViewContainer to set.
	 */
	public void setCopyNumberSingleViewContainer(
			CopyNumberSingleViewResultsContainer copyNumberSingleViewContainer) {
		this.copyNumberSingleViewContainer = copyNumberSingleViewContainer;
	}
	/**
	 * @return Returns the sampleViewResultsContainer.
	 */
	public SampleViewResultsContainer getSampleViewResultsContainer() {
		return sampleViewResultsContainer;
	}
	/**
	 * @param sampleViewResultsContainer The sampleViewResultsContainer to set.
	 */
	public void setSampleViewResultsContainer(
			SampleViewResultsContainer sampleViewResultsContainer) {
		this.sampleViewResultsContainer = sampleViewResultsContainer;
	}
	
	/**
	 * @return Returns the geneExprSingleViewContainer.
	 */
	public GeneExprSingleViewResultsContainer getGeneExprSingleViewContainer() {
		return geneExprSingleViewContainer;
	}
	/**
	 * @param geneExprSingleViewContainer The geneExprSingleViewContainer to set.
	 */
	public void setGeneExprSingleViewContainer(
			GeneExprSingleViewResultsContainer geneExprSingleViewContainer) {
		this.geneExprSingleViewContainer = geneExprSingleViewContainer;
	}
}
