/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.de.*;

/**
 * @author SahniH
 *
 This class encapulates a sample Id and foldchange or copy number values..
 */
public class BioSpecimenResultset {
	private BioSpecimenIdentifierDE biospecimen = null;
	private DatumDE foldChangeRatioValue = null;
	private DatumDE foldChangeSampleIntensity = null;
	private DatumDE foldChangeNormalIntensity = null;
	private GenderDE genderCode = null;
	/**
	 * @return Returns the ageGroup.
	 */
	public DatumDE getAgeGroup() {
		return this.ageGroup;
	}
	/**
	 * @param ageGroup The ageGroup to set.
	 */
	public void setAgeGroup(DatumDE ageGroup) {
		this.ageGroup = ageGroup;
	}
	/**
	 * @return Returns the foldChangeNormalIntensity.
	 */
	public DatumDE getFoldChangeNormalIntensity() {
		return this.foldChangeNormalIntensity;
	}
	/**
	 * @param foldChangeNormalIntensity The foldChangeNormalIntensity to set.
	 */
	public void setFoldChangeNormalIntensity(DatumDE foldChangeNormalIntensity) {
		this.foldChangeNormalIntensity = foldChangeNormalIntensity;
	}
	/**
	 * @return Returns the foldChangeSampleIntensity.
	 */
	public DatumDE getFoldChangeSampleIntensity() {
		return this.foldChangeSampleIntensity;
	}
	/**
	 * @param foldChangeSampleIntensity The foldChangeSampleIntensity to set.
	 */
	public void setFoldChangeSampleIntensity(DatumDE foldChangeSampleIntensity) {
		this.foldChangeSampleIntensity = foldChangeSampleIntensity;
	}
	/**
	 * @return Returns the genderCode.
	 */
	public GenderDE getGenderCode() {
		return this.genderCode;
	}
	/**
	 * @param genderCode The genderCode to set.
	 */
	public void setGenderCode(GenderDE genderCode) {
		this.genderCode = genderCode;
	}
	/**
	 * @return Returns the survivalLengthRange.
	 */
	public DatumDE getSurvivalLengthRange() {
		return this.survivalLengthRange;
	}
	/**
	 * @param survivalLengthRange The survivalLengthRange to set.
	 */
	public void setSurvivalLengthRange(DatumDE survivalLengthRange) {
		this.survivalLengthRange = survivalLengthRange;
	}
	private DatumDE ageGroup = null;
	private DatumDE survivalLengthRange = null;
	private DatumDE copyNumberValue = null;
	/**
	 * @return Returns the biospecimen.
	 */
	public BioSpecimenIdentifierDE getBiospecimen() {
		return biospecimen;
	}
	/**
	 * @param biospecimen The biospecimen to set.
	 */
	public void setBiospecimen(BioSpecimenIdentifierDE biospecimen) {
		this.biospecimen = biospecimen;
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
	public DatumDE getCopyNumberValue() {
		return copyNumberValue;
	}
	/**
	 * @param copyNumberValue The copyNumberValue to set.
	 */
	public void setCopyNumberValue(DatumDE copyNumberValue) {
		this.copyNumberValue = copyNumberValue;
	}
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the copyNumberValue.
	 */
}
