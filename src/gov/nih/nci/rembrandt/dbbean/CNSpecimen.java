/**
 * 
 */
package gov.nih.nci.rembrandt.dbbean;

/**
 * @author sahnih
 * Bean For SPECIMEN_GE_MV
 */

public class CNSpecimen {
    public final static String SPECIMEN_NAME = "specimenName";
    public final static String ANALYSIS_TYPE = "analysisType";

	private String specimenName; //SPECIMEN_NAME
	private String analysisType; //ANALYSIS_TYPE

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

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
