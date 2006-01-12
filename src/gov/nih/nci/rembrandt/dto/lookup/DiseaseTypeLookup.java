package gov.nih.nci.rembrandt.dto.lookup;

/**
 * @author SahniH
 * Date: Nov 16, 2004
 * 
 */
public interface DiseaseTypeLookup {
	public abstract String getDiseaseDesc();

	public abstract String getDiseaseType();

	public abstract Long getDiseaseTypeId();
}