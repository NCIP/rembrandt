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
	private ReportBean reportBean = null;
	public RembrandtTaskResult(Task task) {
		this.setTask(task);
	}
	/**
	 * @return the reportBean
	 */
	public ReportBean getReportBean() {
		return reportBean;
	}
	/**
	 * @param reportBean the reportBean to set
	 */
	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}



}
