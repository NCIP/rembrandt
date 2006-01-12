package gov.nih.nci.rembrandt.queryservice.resultset.gene;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.BioSpecimenResultset;


/**
 * @author SahniH
 * Date: Oct 22, 2004
 * 
 */
public class SampleFoldChangeValuesResultset extends BioSpecimenResultset implements FoldChange{
	private DatumDE foldChangeRatioValue = null;
	private DatumDE foldChangeIntensity = null;
	private DatumDE foldChangeNonTumorIntensity = null;
	private DatumDE ratioPval = null;
	private boolean highlighted = false;
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
	 * @return Returns the foldChangeIntensity.
	 */
	public DatumDE getFoldChangeIntensity() {
		return foldChangeIntensity;
	}
	/**
	 * @param foldChangeIntensity The foldChangeIntensity to set.
	 */
	public void setFoldChangeIntensity(DatumDE foldChangeIntensity) {
		this.foldChangeIntensity = foldChangeIntensity;
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
	 * @return Returns the highlighted.
	 */
	public boolean isHighlighted() {
		return this.highlighted;
	}
	/**
	 * @param highlighted The highlighted to set.
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
