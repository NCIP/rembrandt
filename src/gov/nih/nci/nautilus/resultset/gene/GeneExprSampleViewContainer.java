/*
 * Created on Oct 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.gene;

import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GeneExprSampleViewContainer implements ResultsContainer{
	private GeneExprSingleViewResultsContainer geneExprSingleViewContainer = null;
	private SampleViewResultsContainer sampleViewResultsContainer = null;
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
}
