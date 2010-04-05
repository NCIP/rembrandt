/**
 * 
 */
package gov.nih.nci.rembrandt.dbbean;

/**
 * @author sahnih
 * Bean For SPECIMEN_GE_MV
 */

public class SNPSpecimen {
    public final static String SPECIMEN_NAME = "specimenName";
	private String specimenName; //SPECIMEN_NAME

	/**
	 * @return Returns the specimenName.
	 */
	public String getSpecimenName() {
		return specimenName;
	}

	/**
	 * @param specimenName The specimenName to set.
	 */
	public void setSpecimenName(String specimenName) {
		this.specimenName = specimenName;
	}

}
