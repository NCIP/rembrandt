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
 * @author Himanso
 *
 */
public class InstitutionLookup {
private Long institutionId;
private String institutionName;
private String displayName;
/**
 * @return Returns the displayName.
 */
public String getDisplayName() {
	return displayName;
}
/**
 * @param displayName The displayName to set.
 */
public void setDisplayName(String displayName) {
	this.displayName = displayName;
}
/**
 * @return Returns the institutionName.
 */
public String getInstitutionName() {
	return institutionName;
}
/**
 * @param institutionName The institutionName to set.
 */
public void setInstitutionName(String institutionName) {
	this.institutionName = institutionName;
}
/**
 * @return Returns the institutionId.
 */
public Long getInstitutionId() {
	return institutionId;
}
/**
 * @param institutionId The institutionId to set.
 */
public void setInstitutionId(Long institutionId) {
	this.institutionId = institutionId;
}
}
