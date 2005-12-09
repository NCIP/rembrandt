package gov.nih.nci.rembrandt.dbbean;

import java.sql.Date;

import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

public class OnStudySurgery implements ResultSet {
	
	public final static String PT_SURGERY_ID = "ptSurgeryId";
	public final static String PATIENT_DID = "patientDid";  
	public final static String INSTITUTION_ID = "institutionId";
	public final static String DATASET_ID = "datasetId";
	public final static String TIME_POINT = "timePoint";
	public final static String PROCEDURE_TITLE = "PROCEDURE_TITLE";
	public final static String INDICATION = "indication";
	public final static String SURGERY_DATE = "surgeryDate";
	public final static String SURGERY_OUTCOME = "SURGERY_OUTCOME";
	public final static String HISTO_DIAGNOSIS = "histoDiagnosis";
	
	
	 private Long ptSurgeryId;
	 private Long patientDid;
	 private Long institutionId;	    
	 private Long datasetId;
	 private String timePoint; 
	 private String procedureTitle; 
	 private String indication; 
	 private Date surgeryDate;
	 private String surgeryOutcome;
	 private String histoDiagnosis;
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
	 * @return Returns the priorSurgeryId.
	 */
	public Long getPtSurgeryId() {
		return ptSurgeryId;
	}
	/**
	 * @param priorSurgeryId The priorSurgeryId to set.
	 */
	public void setPtSurgeryId(Long ptSurgeryId) {
		ptSurgeryId = ptSurgeryId;
	}
	/**
	 * @return Returns the procedureTitle.
	 */
	public String getProcedureTitle() {
		return procedureTitle;
	}
	/**
	 * @param procedureTitle The procedureTitle to set.
	 */
	public void setProcedureTitle(String procedureTitle) {
		this.procedureTitle = procedureTitle;
	}
	/**
	 * @return Returns the surgeryDate.
	 */
	public Date getSurgeryDate() {
		return surgeryDate;
	}
	/**
	 * @param surgeryDate The surgeryDate to set.
	 */
	public void setSurgeryDate(Date surgeryDate) {
		this.surgeryDate = surgeryDate;
	}
	/**
	 * @return Returns the surgeryOutcome.
	 */
	public String getSurgeryOutcome() {
		return surgeryOutcome;
	}
	/**
	 * @param surgeryOutcome The surgeryOutcome to set.
	 */
	public void setSurgeryOutcome(String surgeryOutcome) {
		this.surgeryOutcome = surgeryOutcome;
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
	/**
	 * @return Returns the histoDiagnosis.
	 */
	public String getHistoDiagnosis() {
		return histoDiagnosis;
	}
	/**
	 * @param histoDiagnosis The histoDiagnosis to set.
	 */
	public void setHistoDiagnosis(String histoDiagnosis) {
		this.histoDiagnosis = histoDiagnosis;
	}
	/**
	 * @return Returns the indication.
	 */
	public String getIndication() {
		return indication;
	}
	/**
	 * @param indication The indication to set.
	 */
	public void setIndication(String indication) {
		this.indication = indication;
	}
	
	 
	 

}
