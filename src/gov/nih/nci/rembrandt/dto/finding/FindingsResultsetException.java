/**
 * 
 */
package gov.nih.nci.rembrandt.dto.finding;

import gov.nih.nci.caintegrator.exceptions.AnalysisServerException;

/**
 * @author sahnih
 *
 */
public class FindingsResultsetException extends gov.nih.nci.caintegrator.dto.finding.FindingsResultsetException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected FindingsResultsetException(AnalysisServerException analysisServerException) {
		super(analysisServerException);
		// TODO Auto-generated constructor stub
	}

}
