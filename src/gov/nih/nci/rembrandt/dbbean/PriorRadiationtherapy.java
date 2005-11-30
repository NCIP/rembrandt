package gov.nih.nci.rembrandt.dbbean;

import java.sql.Date;

import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

public class PriorRadiationtherapy implements ResultSet {
	
	public final static String PRIOR_RADIATIONTRX_ID = "priorRadiationtrxId";
	public final static String PATIENT_DID = "patientDid";  
	public final static String INSTITUTION_ID = "institutionId";
	public final static String DATASET_ID = "datasetId";
	public final static String TIME_POINT = "timePoint";
	public final static String RADIATION_SITE = "RadiationSite";
	public final static String DOSE_START_DATE = "doseStartDate";
	public final static String DOSE_STOP_DATE = "doseStopDate";
	public final static String FRACTION_DOSE = "fractionDose";
	public final static String FRACTION_NUMBER = "fractionNumber";
	public final static String RADIATION_TYPE = "radiationType";
	
	
	 private Long priorRadiationtrxId;
	 private Long patientDid;
	 private Long institutionId;	    
	 private Long datasetId;
	 private String timePoint; 
	 private String RadiationSite; 
	 private Date doseStartDate; 
	 private Date doseStopDate; 
	 private Long fractionDose;
	 private Long fractionNumber;
	 private String radiationType;
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
	 * @return Returns the fractionDose.
	 */
	public Long getFractionDose() {
		return fractionDose;
	}
	/**
	 * @param fractionDose The fractionDose to set.
	 */
	public void setFractionDose(Long fractionDose) {
		this.fractionDose = fractionDose;
	}
	/**
	 * @return Returns the fractionNumber.
	 */
	public Long getFractionNumber() {
		return fractionNumber;
	}
	/**
	 * @param fractionNumber The fractionNumber to set.
	 */
	public void setFractionNumber(Long fractionNumber) {
		this.fractionNumber = fractionNumber;
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
	 * @return Returns the priorRadiationtrxId.
	 */
	public Long getPriorRadiationtrxId() {
		return priorRadiationtrxId;
	}
	/**
	 * @param priorRadiationtrxId The priorRadiationtrxId to set.
	 */
	public void setPriorRadiationtrxId(Long priorRadiationtrxId) {
		this.priorRadiationtrxId = priorRadiationtrxId;
	}
	/**
	 * @return Returns the radiationSite.
	 */
	public String getRadiationSite() {
		return RadiationSite;
	}
	/**
	 * @param radiationSite The radiationSite to set.
	 */
	public void setRadiationSite(String radiationSite) {
		RadiationSite = radiationSite;
	}
	/**
	 * @return Returns the radiationType.
	 */
	public String getRadiationType() {
		return radiationType;
	}
	/**
	 * @param radiationType The radiationType to set.
	 */
	public void setRadiationType(String radiationType) {
		this.radiationType = radiationType;
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
