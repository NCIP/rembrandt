/*
 * Created on Nov 9, 2004
 *
 */
package gov.nih.nci.rembrandt.queryservice.resultset;

import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;

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
	 * @param sampleID
	 * @return
	 */
	public CopyNumberSingleViewResultsContainer getCopyNumberSingleViewContainerForSample(String sampleID){
		SampleResultset sampleResultset =  (SampleResultset) getSampleViewResultsContainer().getSampleResultset(sampleID);
		return sampleResultset.getCopyNumberSingleViewResultsContainer();
	}
	/**
	 * @param sampleID
	 * @return
	 */
	public GeneExprSingleViewResultsContainer getGeneExprSingleViewResultsContainerForSample(String sampleID){
		SampleResultset sampleResultset =  (SampleResultset) getSampleViewResultsContainer().getSampleResultset(sampleID);
		return sampleResultset.getGeneExprSingleViewResultsContainer();
	}
	/**
	 * @param sampleID
	 * @return
	 */
	public SampleViewResultsContainer getSampleViewResultsContainerForSample(String sampleID){
		SampleViewResultsContainer sampleContainer = new SampleViewResultsContainer();
		sampleContainer.addSampleResultset(getSampleViewResultsContainer().getSampleResultset(sampleID));
		return sampleContainer;
	}
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
