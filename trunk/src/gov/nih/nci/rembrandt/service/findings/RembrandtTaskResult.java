/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings;

import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResultImpl;
import gov.nih.nci.rembrandt.web.bean.ReportBean;

/**
 * @author sahnih
 *
 */
public class RembrandtTaskResult extends TaskResultImpl {

	private static final long serialVersionUID = -1459623075327806364L;
	private String reportBeanCacheKey = null;
	public RembrandtTaskResult(Task task) {
		this.setTask(task);
	}
	/**
	 * @return the reportBeanCacheKey
	 */
	public String getReportBeanCacheKey() {
		return reportBeanCacheKey;
	}
	/**
	 * @param reportBeanCacheKey the reportBeanCacheKey to set
	 */
	public void setReportBeanCacheKey(String reportBeanCacheKey) {
		this.reportBeanCacheKey = reportBeanCacheKey;
	}




}
