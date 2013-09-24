/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.rembrandt.dbbean;

/**
 * @author sahnih
 * Bean For SPECIMEN_GE_MV
 */

public class GESpecimen {
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
