/*
 * Created on Nov 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClinicalResultSet {
	/**
	 * @return Returns the ageGroup.
	 */
	public abstract String getAgeGroup();

	/**
	 * @param ageGroup The ageGroup to set.
	 */
	public abstract void setAgeGroup(String ageGroup);

	/**
	 * @return Returns the biospecimenId.
	 */
	public abstract Long getBiospecimenId();

	/**
	 * @param biospecimenId The biospecimenId to set.
	 */
	public abstract void setBiospecimenId(Long biospecimenId);

	/**
	 * @return Returns the diseaseType.
	 */
	public abstract String getDiseaseType();

	/**
	 * @param diseaseType The diseaseType to set.
	 */
	public abstract void setDiseaseType(String diseaseType);

	/**
	 * @return Returns the genderCode.
	 */
	public abstract String getGenderCode();

	/**
	 * @param genderCode The genderCode to set.
	 */
	public abstract void setGenderCode(String genderCode);

	/**
	 * @return Returns the sampleId.
	 */
	public abstract String getSampleId();

	/**
	 * @param sampleId The sampleId to set.
	 */
	public abstract void setSampleId(String sampleId);

	/**
	 * @return Returns the survivalLengthRange.
	 */
	public abstract String getSurvivalLengthRange();

	/**
	 * @param survivalLengthRange The survivalLengthRange to set.
	 */
	public abstract void setSurvivalLengthRange(String survivalLengthRange);


}