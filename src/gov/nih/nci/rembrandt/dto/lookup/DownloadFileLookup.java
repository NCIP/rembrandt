/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.lookup;

public interface DownloadFileLookup {

	public abstract Long getAccessCode();

	public abstract void setAccessCode(Long accessCode);

	public abstract Long getFileId();

	public abstract void setFileId(Long fileId);

	public abstract String getFileName();

	public abstract void setFileName(String fileName);

	public abstract String getFileType();

	public abstract void setFileType(String fileType);

}