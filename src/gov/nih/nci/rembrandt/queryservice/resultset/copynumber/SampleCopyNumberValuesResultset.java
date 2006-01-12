package gov.nih.nci.rembrandt.queryservice.resultset.copynumber;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.BioSpecimenResultset;

/**
 * @author SahniH
 * Date: Oct 22, 2004
 * 
 */
public class SampleCopyNumberValuesResultset extends BioSpecimenResultset{
	private DatumDE copyNumber = null;
	private DatumDE channelRatioValue;
	private DatumDE copyNumberPvalue;
	private DatumDE loh;
	private boolean highlighted = false;
	/**
	 * @return Returns the channelRatioValue.
	 */
	public DatumDE getChannelRatioValue() {
		return channelRatioValue;
	}
	/**
	 * @param channelRatioValue The channelRatioValue to set.
	 */
	public void setChannelRatioValue(DatumDE channelRatioValue) {
		this.channelRatioValue = channelRatioValue;
	}
	/**
	 * @return Returns the copyNumber.
	 */
	public DatumDE getCopyNumber() {
		return copyNumber;
	}
	/**
	 * @param copyNumber The copyNumber to set.
	 */
	public void setCopyNumber(DatumDE copyNumber) {
		this.copyNumber = copyNumber;
	}
	/**
	 * @return Returns the copyNumberPvalue.
	 */
	public DatumDE getCopyNumberPvalue() {
		return copyNumberPvalue;
	}
	/**
	 * @param copyNumberPvalue The copyNumberPvalue to set.
	 */
	public void setCopyNumberPvalue(DatumDE copyNumberPvalue) {
		this.copyNumberPvalue = copyNumberPvalue;
	}
	/**
	 * @return Returns the loh.
	 */
	public DatumDE getLOH() {
		return loh;
	}
	/**
	 * @param loh The lOH to set.
	 */
	public void setLOH(DatumDE loh) {
		this.loh = loh;
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
