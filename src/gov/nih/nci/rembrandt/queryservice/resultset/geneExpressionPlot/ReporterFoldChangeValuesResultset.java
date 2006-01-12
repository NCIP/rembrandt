package gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.FoldChange;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public class ReporterFoldChangeValuesResultset implements FoldChange{
	private DatumDE foldChangeRatioValue = null;
	private DatumDE foldChangeIntensity = null;
	private DatumDE foldChangeNonTumorIntensity = null;
	private DatumDE ratioPval = null;
	private DatumDE reporter = null;
	private DatumDE standardDeviationRatio = null;
	/**
	 * @param reporter
	 */
	public ReporterFoldChangeValuesResultset(DatumDE reporter) {		
		setReporter(reporter);
	}
	/**
	 * @return Returns the foldChangeIntensity.
	 */
	public DatumDE getFoldChangeIntensity() {
		return this.foldChangeIntensity;
	}
	/**
	 * @param foldChangeIntensity The foldChangeIntensity to set.
	 */
	public void setFoldChangeIntensity(DatumDE foldChangeSampleIntensity) {
		this.foldChangeIntensity = foldChangeSampleIntensity;
	}
	/**
	 * @return Returns the ratioPval.
	 */
	public DatumDE getRatioPval() {
		return this.ratioPval;
	}
	/**
	 * @param ratioPval The ratioPval to set.
	 */
	public void setRatioPval(DatumDE ratioPval) {
		this.ratioPval = ratioPval;
	}
	/**
	 * @return Returns the reporterName.
	 */
	public DatumDE getReporter() {
		return this.reporter;
	}
	/**
	 * @param reporterName The reporterName to set.
	 */
	public void setReporter(DatumDE reporterName) {
		this.reporter = reporterName;
	}
	/**
	 * @return Returns the foldChangeNonTumorIntensity.
	 */
	public DatumDE getFoldChangeNonTumorIntensity() {
		return foldChangeNonTumorIntensity;
	}
	/**
	 * @param foldChangeNonTumorIntensity The foldChangeNonTumorIntensity to set.
	 */
	public void setFoldChangeNonTumorIntensity(
			DatumDE foldChangeNonTumorIntensity) {
		this.foldChangeNonTumorIntensity = foldChangeNonTumorIntensity;
	}
	/**
	 * @return Returns the foldChangeRatioValue.
	 */
	public DatumDE getFoldChangeRatioValue() {
		return foldChangeRatioValue;
	}
	/**
	 * @param foldChangeRatioValue The foldChangeRatioValue to set.
	 */
	public void setFoldChangeRatioValue(DatumDE foldChangeRatioValue) {
		this.foldChangeRatioValue = foldChangeRatioValue;
	}
	public DatumDE getStandardDeviationRatio() {
		return standardDeviationRatio;
	}
	public void setStandardDeviationRatio(DatumDE standardDeviationRatio) {
		this.standardDeviationRatio = standardDeviationRatio;
	}
}
