package gov.nih.nci.rembrandt.dbbean;

import java.sql.Date;

import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

public class PriorChemotherapy implements ResultSet{
	
	public final static String PRIOR_CHEMOTRX_ID = "priorChemotrxId";
	public final static String PATIENT_DID = "patientDid";  
	public final static String INSTITUTION_ID = "institutionId";
	public final static String DATASET_ID = "datasetId";
	public final static String TIME_POINT = "timePoint";
	public final static String AGENT_ID = "agentId";
	public final static String AGENT_NAME = "agentName";
	public final static String COURSE_COUNT = "courseCount";
	public final static String DOSE_START_DATE = "doseStartDate";
	public final static String DOSE_STOP_DATE = "doseStopDate";
	public final static String STUDY_SOURCE = "studySource";
	public final static String PROTOCOL_NUMBER = "protocolNumber";
	
	 private Long priorChemotrxId;
	 private Long patientDid;
	 private Long institutionId;	    
	 private Long datasetId;
	 private String timePoint; 
	 private Long agentId;
	 private String agentName; 
	 private Long courseCount;
	 private Date doseStartDate; 
	 private Date doseStopDate; 
	 private String studySource; 
	 private String protocolNumber;
	/**
	 * @return Returns the agentId.
	 */
	public Long getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId The agentId to set.
	 */
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return Returns the courseCount.
	 */
	public Long getCourseCount() {
		return courseCount;
	}
	/**
	 * @param courseCount The courseCount to set.
	 */
	public void setCourseCount(Long courseCount) {
		this.courseCount = courseCount;
	}
	/**
	 * @return Returns the datasetId.
	 */
	public Long getDatasetId() {
		return datasetId;
	}
	/**
	 * @param datasetId The datasetId to set.
	 */
	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}
	/**
	 * @return Returns the doseStartDate.
	 */
	public Date getDoseStartDate() {
		return doseStartDate;
	}
	/**
	 * @param doseStartDate The doseStartDate to set.
	 */
	public void setDoseStartDate(Date doseStartDate) {
		this.doseStartDate = doseStartDate;
	}
	/**
	 * @return Returns the doseStopDate.
	 */
	public Date getDoseStopDate() {
		return doseStopDate;
	}
	/**
	 * @param doseStopDate The doseStopDate to set.
	 */
	public void setDoseStopDate(Date doseStopDate) {
		this.doseStopDate = doseStopDate;
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
	/**
	 * @return Returns the patientDid.
	 */
	public Long getPatientDid() {
		return patientDid;
	}
	/**
	 * @param patientDid The patientDid to set.
	 */
	public void setPatientDid(Long patientDid) {
		this.patientDid = patientDid;
	}
	/**
	 * @return Returns the priorChemotrxId.
	 */
	public Long getPriorChemotrxId() {
		return priorChemotrxId;
	}
	/**
	 * @param priorChemotrxId The priorChemotrxId to set.
	 */
	public void setPriorChemotrxId(Long priorChemotrxId) {
		this.priorChemotrxId = priorChemotrxId;
	}
	/**
	 * @return Returns the protocolNumber.
	 */
	public String getProtocolNumber() {
		return protocolNumber;
	}
	/**
	 * @param protocolNumber The protocolNumber to set.
	 */
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	/**
	 * @return Returns the studySource.
	 */
	public String getStudySource() {
		return studySource;
	}
	/**
	 * @param studySource The studySource to set.
	 */
	public void setStudySource(String studySource) {
		this.studySource = studySource;
	}
	/**
	 * @return Returns the timePoint.
	 */
	public String getTimePoint() {
		return timePoint;
	}
	/**
	 * @param timePoint The timePoint to set.
	 */
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	} 
	 
}
