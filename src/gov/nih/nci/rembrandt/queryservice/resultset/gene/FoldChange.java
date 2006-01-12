package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public interface FoldChange {
	/**
	 * @return Returns the foldChangeNonTumorIntensity.
	 */
	public abstract DatumDE getFoldChangeNonTumorIntensity();

	/**
	 * @param foldChangeNonTumorIntensity The foldChangeNonTumorIntensity to set.
	 */
	public abstract void setFoldChangeNonTumorIntensity(
			DatumDE foldChangeNonTumorIntensity);

	/**
	 * @return Returns the foldChangeRatioValue.
	 */
	public abstract DatumDE getFoldChangeRatioValue();

	/**
	 * @param foldChangeRatioValue The foldChangeRatioValue to set.
	 */
	public abstract void setFoldChangeRatioValue(DatumDE foldChangeRatioValue);

	/**
	 * @return Returns the foldChangeSampleIntensity.
	 */
	public abstract DatumDE getFoldChangeIntensity();

	/**
	 * @param foldChangeSampleIntensity The foldChangeSampleIntensity to set.
	 */
	public abstract void setFoldChangeIntensity(
			DatumDE foldChangeSampleIntensity);

	/**
	 * @return Returns the ratioPval.
	 */
	public abstract DatumDE getRatioPval();

	/**
	 * @param ratioPval The ratioPval to set.
	 */
	public abstract void setRatioPval(DatumDE ratioPval);
}