/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset.sample;
import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;

/**
 * @author SahniH
 *
 This class encapulates a sample Id and foldchange or copy number values..
 */
public abstract class  BioSpecimenResultset {
	private BioSpecimenIdentifierDE biospecimen = null;
	private GenderDE genderCode = null;
	private DatumDE ageGroup = null;
	private DatumDE survivalLengthRange = null;
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

}
