/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.lookup;

public interface AccessionNoLookup {

	/**
	 * @return Returns the accession.
	 */
	public abstract String getAccession();

	/**
	 * @param accession The accession to set.
	 */
	public abstract void setAccession(String accession);

}