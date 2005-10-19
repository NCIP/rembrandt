package gov.nih.nci.caintegrator.dto.finding;

import gov.nih.nci.caintegrator.analysis.messaging.AnalysisRequest;
import gov.nih.nci.caintegrator.exceptions.AnalysisServerException;

public abstract class  FindingsResultsetException implements FindingsResultset{
	protected AnalysisServerException analysisServerException;
	protected FindingsResultsetException(AnalysisServerException analysisServerException){
		this.analysisServerException = analysisServerException;
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	public Throwable getCause() {
		return analysisServerException.getCause();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.exceptions.AnalysisServerException#getFailedRequest()
	 */
	public AnalysisRequest getFailedRequest() {
		return analysisServerException.getFailedRequest();
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	public String getLocalizedMessage() {
		return analysisServerException.getLocalizedMessage();
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return analysisServerException.getMessage();
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getStackTrace()
	 */
	public StackTraceElement[] getStackTrace() {
		return analysisServerException.getStackTrace();
	}
}
