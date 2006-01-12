package gov.nih.nci.rembrandt.dto.lookup;

/**
 * @author SahniH
 * Date: Nov 12, 2004
 * 
 */
public interface PatientDataLookup {
	public abstract String getGender();

	public abstract Long getAge();

	public abstract Long getBiospecimenId();

	public abstract String getCensoringStatus();

	public abstract Long getPatientDid();

	public abstract Long getPopulationTypeId();

	public abstract String getSampleId();

	public abstract Long getSurvivalLength();
	
	public abstract String getRace();
}