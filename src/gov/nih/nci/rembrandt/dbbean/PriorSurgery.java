package gov.nih.nci.rembrandt.dbbean;

import java.sql.Date;

import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

public class PriorSurgery implements ResultSet {
	
	public final static String PRIOR_SURGERY_ID = "PriorSurgeryId";
	public final static String PATIENT_DID = "patientDid";  
	public final static String INSTITUTION_ID = "institutionId";
	public final static String DATASET_ID = "datasetId";
	public final static String TIME_POINT = "timePoint";
	public final static String PROCEDURE_TITLE = "procedureTitle";
	public final static String TUMOR_HISTOLOGY = "tumorHistology";
	public final static String SURGERY_DATE = "surgeryDate";
	public final static String SURGERY_OUTCOME = "surgeryOutcome";
	
	
	 private Long PriorSurgeryId;
	 private Long patientDid;
	 private Long institutionId;	    
	 private Long datasetId;
	 private String timePoint; 
	 private String procedureTitle; 
	 private String tumorHistology; 
	 private Date surgeryDate;
	 private String surgeryOutcome;
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
	public Long getPriorSurgeryId() {
		return PriorSurgeryId;
	}
	/**
	 * @param priorSurgeryId The priorSurgeryId to set.
	 */
	public void setPriorSurgeryId(Long priorSurgeryId) {
		PriorSurgeryId = priorSurgeryId;
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
	 * @return Returns the tumorHistology.
	 */
	public String getTumorHistology() {
		return tumorHistology;
	}
	/**
	 * @param tumorHistology The tumorHistology to set.
	 */
	public void setTumorHistology(String tumorHistology) {
		this.tumorHistology = tumorHistology;
	} 
	 
	 

}
