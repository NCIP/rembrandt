/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;

public interface GeneExpressionQueryInterface {

	public abstract void setQueryName(String name);
	
	public abstract String getQueryName();
	
	public abstract InstitutionCriteria getInstitutionCriteria();
	
	public void setInstitutionCriteria(InstitutionCriteria institutionCriteria);
	
	public abstract GeneIDCriteria getGeneIDCrit();

	public abstract void setGeneIDCrit(GeneIDCriteria geneIDCrit);

	public abstract SampleCriteria getSampleIDCrit();

	public abstract void setSampleIDCrit(SampleCriteria sampleIDCrit);

	public abstract FoldChangeCriteria getFoldChgCrit();

	public abstract void setFoldChgCrit(FoldChangeCriteria foldChgCrit);

	/**
	 * @return Returns the arrayPlatformCriteria.
	 */
	public abstract ArrayPlatformCriteria getArrayPlatformCriteria();

	/**
	 * @param arrayPlatformCriteria The arrayPlatformCriteria to set.
	 */
	public abstract void setArrayPlatformCrit(
			ArrayPlatformCriteria arrayPlatformCriteria);

}