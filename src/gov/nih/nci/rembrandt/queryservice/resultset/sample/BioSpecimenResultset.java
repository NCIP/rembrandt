/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset.sample;
import java.sql.Date;

import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.KarnofskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.LanskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.MRIClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.NeuroExamClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;

/**
 * @author SahniH
 *
 This class encapulates a sample Id and foldchange or copy number values..
 */
public abstract class  BioSpecimenResultset {
	private BioSpecimenIdentifierDE biospecimen = null;
	private GenderDE genderCode = null;
	private KarnofskyClinicalEvalDE karnofskyClinicalEvalDE = null;
	private LanskyClinicalEvalDE lanskyClinicalEvalDE = null;
	private NeuroExamClinicalEvalDE neuroExamClinicalEvalDE = null;
	private MRIClinicalEvalDE mriClinicalEvalDE = null;
	private String timePoint = null;	
	private Date followupDate;  
	private Long followupMonth;
	private Date neuroEvaluationDate;    
    private String steroidDoseStatus;	    
	private String antiConvulsantStatus;
	private RaceDE raceDE = null;
	private DatumDE ageGroup = null;
	private DatumDE survivalLengthRange = null;	
	private String timePoints = null;
	private String followupDates; 
	private String followupMonths;
	private String neuroEvaluationDates;
	private String steroidDoseStatuses;	
	private String antiConvulsantStatuses;

	
	private String priorRadiationTimePoints;
	  private String priorRadiationRadiationSites;
	  private String priorRadiationDoseStartDates;
	  private String priorRadiationDoseStopDates;
	  private String priorRadiationFractionDoses;
	  private String priorRadiationFractionNumbers;
	  private String priorRadiationRadiationTypes;
	  
	  
	  private String priorChemoTimePoints;
	  private String priorChemoagentIds ;
	  private String priorChemoAgentNames ;							
	  private String priorChemoCourseCounts ;
	  private String priorChemoDoseStartDates;	
	  private String priorChemoDoseStopDates ;
	  private String priorChemoStudySources ;
	  private String priorChemoProtocolNumbers ;
	  
	  private String priorSurgeryTimePoints ;
	  private String priorSurgeryProcedureTitles ;
	  private String priorSurgeryTumorHistologys ;							
	  private String priorSurgerySurgeryDates ;
	  private String priorSurgerySurgeryOutcomes ;	

	private DatumDE survivalLength ;

	
	
	
	/**
	 * @return Returns the priorChemoagentIds.
	 */
	public String getPriorChemoagentIds() {
		return priorChemoagentIds;
	}
	/**
	 * @param priorChemoagentIds The priorChemoagentIds to set.
	 */
	public void setPriorChemoagentIds(String priorChemoagentIds) {
		this.priorChemoagentIds = priorChemoagentIds;
	}
	/**
	 * @return Returns the priorChemoAgentNames.
	 */
	public String getPriorChemoAgentNames() {
		return priorChemoAgentNames;
	}
	/**
	 * @param priorChemoAgentNames The priorChemoAgentNames to set.
	 */
	public void setPriorChemoAgentNames(String priorChemoAgentNames) {
		this.priorChemoAgentNames = priorChemoAgentNames;
	}
	/**
	 * @return Returns the priorChemoCourseCounts.
	 */
	public String getPriorChemoCourseCounts() {
		return priorChemoCourseCounts;
	}
	/**
	 * @param priorChemoCourseCounts The priorChemoCourseCounts to set.
	 */
	public void setPriorChemoCourseCounts(String priorChemoCourseCounts) {
		this.priorChemoCourseCounts = priorChemoCourseCounts;
	}
	/**
	 * @return Returns the priorChemoDoseStartDates.
	 */
	public String getPriorChemoDoseStartDates() {
		return priorChemoDoseStartDates;
	}
	/**
	 * @param priorChemoDoseStartDates The priorChemoDoseStartDates to set.
	 */
	public void setPriorChemoDoseStartDates(String priorChemoDoseStartDates) {
		this.priorChemoDoseStartDates = priorChemoDoseStartDates;
	}
	/**
	 * @return Returns the priorChemoDoseStopDates.
	 */
	public String getPriorChemoDoseStopDates() {
		return priorChemoDoseStopDates;
	}
	/**
	 * @param priorChemoDoseStopDates The priorChemoDoseStopDates to set.
	 */
	public void setPriorChemoDoseStopDates(String priorChemoDoseStopDates) {
		this.priorChemoDoseStopDates = priorChemoDoseStopDates;
	}
	/**
	 * @return Returns the priorChemoProtocolNumbers.
	 */
	public String getPriorChemoProtocolNumbers() {
		return priorChemoProtocolNumbers;
	}
	/**
	 * @param priorChemoProtocolNumbers The priorChemoProtocolNumbers to set.
	 */
	public void setPriorChemoProtocolNumbers(String priorChemoProtocolNumbers) {
		this.priorChemoProtocolNumbers = priorChemoProtocolNumbers;
	}
	/**
	 * @return Returns the priorChemoStudySources.
	 */
	public String getPriorChemoStudySources() {
		return priorChemoStudySources;
	}
	/**
	 * @param priorChemoStudySources The priorChemoStudySources to set.
	 */
	public void setPriorChemoStudySources(String priorChemoStudySources) {
		this.priorChemoStudySources = priorChemoStudySources;
	}
	/**
	 * @return Returns the priorChemoTimePoints.
	 */
	public String getPriorChemoTimePoints() {
		return priorChemoTimePoints;
	}
	/**
	 * @param priorChemoTimePoints The priorChemoTimePoints to set.
	 */
	public void setPriorChemoTimePoints(String priorChemoTimePoints) {
		this.priorChemoTimePoints = priorChemoTimePoints;
	}
	/**
	 * @return Returns the priorRadiationDoseStartDates.
	 */
	public String getPriorRadiationDoseStartDates() {
		return priorRadiationDoseStartDates;
	}
	/**
	 * @param priorRadiationDoseStartDates The priorRadiationDoseStartDates to set.
	 */
	public void setPriorRadiationDoseStartDates(String priorRadiationDoseStartDates) {
		this.priorRadiationDoseStartDates = priorRadiationDoseStartDates;
	}
	/**
	 * @return Returns the priorRadiationDoseStopDates.
	 */
	public String getPriorRadiationDoseStopDates() {
		return priorRadiationDoseStopDates;
	}
	/**
	 * @param priorRadiationDoseStopDates The priorRadiationDoseStopDates to set.
	 */
	public void setPriorRadiationDoseStopDates(String priorRadiationDoseStopDates) {
		this.priorRadiationDoseStopDates = priorRadiationDoseStopDates;
	}
	/**
	 * @return Returns the priorRadiationFractionDoses.
	 */
	public String getPriorRadiationFractionDoses() {
		return priorRadiationFractionDoses;
	}
	/**
	 * @param priorRadiationFractionDoses The priorRadiationFractionDoses to set.
	 */
	public void setPriorRadiationFractionDoses(String priorRadiationFractionDoses) {
		this.priorRadiationFractionDoses = priorRadiationFractionDoses;
	}
	/**
	 * @return Returns the priorRadiationFractionNumbers.
	 */
	public String getPriorRadiationFractionNumbers() {
		return priorRadiationFractionNumbers;
	}
	/**
	 * @param priorRadiationFractionNumbers The priorRadiationFractionNumbers to set.
	 */
	public void setPriorRadiationFractionNumbers(
			String priorRadiationFractionNumbers) {
		this.priorRadiationFractionNumbers = priorRadiationFractionNumbers;
	}
	/**
	 * @return Returns the priorRadiationRadiationSites.
	 */
	public String getPriorRadiationRadiationSites() {
		return priorRadiationRadiationSites;
	}
	/**
	 * @param priorRadiationRadiationSites The priorRadiationRadiationSites to set.
	 */
	public void setPriorRadiationRadiationSites(String priorRadiationRadiationSites) {
		this.priorRadiationRadiationSites = priorRadiationRadiationSites;
	}
	/**
	 * @return Returns the priorRadiationRadiationTypes.
	 */
	public String getPriorRadiationRadiationTypes() {
		return priorRadiationRadiationTypes;
	}
	/**
	 * @param priorRadiationRadiationTypes The priorRadiationRadiationTypes to set.
	 */
	public void setPriorRadiationRadiationTypes(String priorRadiationRadiationTypes) {
		this.priorRadiationRadiationTypes = priorRadiationRadiationTypes;
	}
	/**
	 * @return Returns the priorRadiationTimePoints.
	 */
	public String getPriorRadiationTimePoints() {
		return priorRadiationTimePoints;
	}
	/**
	 * @param priorRadiationTimePoints The priorRadiationTimePoints to set.
	 */
	public void setPriorRadiationTimePoints(String priorRadiationTimePoints) {
		this.priorRadiationTimePoints = priorRadiationTimePoints;
	}
	/**
	 * @return Returns the priorSurgeryProcedureTitles.
	 */
	public String getPriorSurgeryProcedureTitles() {
		return priorSurgeryProcedureTitles;
	}
	/**
	 * @param priorSurgeryProcedureTitles The priorSurgeryProcedureTitles to set.
	 */
	public void setPriorSurgeryProcedureTitles(String priorSurgeryProcedureTitles) {
		this.priorSurgeryProcedureTitles = priorSurgeryProcedureTitles;
	}
	/**
	 * @return Returns the priorSurgerySurgeryDates.
	 */
	public String getPriorSurgerySurgeryDates() {
		return priorSurgerySurgeryDates;
	}
	/**
	 * @param priorSurgerySurgeryDates The priorSurgerySurgeryDates to set.
	 */
	public void setPriorSurgerySurgeryDates(String priorSurgerySurgeryDates) {
		this.priorSurgerySurgeryDates = priorSurgerySurgeryDates;
	}
	/**
	 * @return Returns the priorSurgerySurgeryOutcomes.
	 */
	public String getPriorSurgerySurgeryOutcomes() {
		return priorSurgerySurgeryOutcomes;
	}
	/**
	 * @param priorSurgerySurgeryOutcomes The priorSurgerySurgeryOutcomes to set.
	 */
	public void setPriorSurgerySurgeryOutcomes(String priorSurgerySurgeryOutcomes) {
		this.priorSurgerySurgeryOutcomes = priorSurgerySurgeryOutcomes;
	}
	/**
	 * @return Returns the priorSurgeryTimePoints.
	 */
	public String getPriorSurgeryTimePoints() {
		return priorSurgeryTimePoints;
	}
	/**
	 * @param priorSurgeryTimePoints The priorSurgeryTimePoints to set.
	 */
	public void setPriorSurgeryTimePoints(String priorSurgeryTimePoints) {
		this.priorSurgeryTimePoints = priorSurgeryTimePoints;
	}
	/**
	 * @return Returns the priorSurgeryTumorHistologys.
	 */
	public String getPriorSurgeryTumorHistologys() {
		return priorSurgeryTumorHistologys;
	}
	/**
	 * @param priorSurgeryTumorHistologys The priorSurgeryTumorHistologys to set.
	 */
	public void setPriorSurgeryTumorHistologys(String priorSurgeryTumorHistologys) {
		this.priorSurgeryTumorHistologys = priorSurgeryTumorHistologys;
	}
	/**
	 * @return Returns the antiConvulsantStatuses.
	 */
	public String getAntiConvulsantStatuses() {
		return antiConvulsantStatuses;
	}
	/**
	 * @param antiConvulsantStatuses The antiConvulsantStatuses to set.
	 */
	public void setAntiConvulsantStatuses(String antiConvulsantStatuses) {
		this.antiConvulsantStatuses = antiConvulsantStatuses;
	}
	/**
	 * @return Returns the followupMonths.
	 */
	public String getFollowupMonths() {
		return followupMonths;
	}
	/**
	 * @param followupMonths The followupMonths to set.
	 */
	public void setFollowupMonths(String followupMonths) {
		this.followupMonths = followupMonths;
	}
	/**
	 * @return Returns the neuroEvaluationDates.
	 */
	public String getNeuroEvaluationDates() {
		return neuroEvaluationDates;
	}
	/**
	 * @param neuroEvaluationDates The neuroEvaluationDates to set.
	 */
	public void setNeuroEvaluationDates(String neuroEvaluationDates) {
		this.neuroEvaluationDates = neuroEvaluationDates;
	}
	/**
	 * @return Returns the steroidDoseStatuses.
	 */
	public String getSteroidDoseStatuses() {
		return steroidDoseStatuses;
	}
	/**
	 * @param steroidDoseStatuses The steroidDoseStatuses to set.
	 */
	public void setSteroidDoseStatuses(String steroidDoseStatuses) {
		this.steroidDoseStatuses = steroidDoseStatuses;
	}
	/**
	 * @return Returns the timePoints.
	 */
	public String getTimePoints() {
		return timePoints;
	}
	/**
	 * @param timePoints The timePoints to set.
	 */
	public void setTimePoints(String timePoints) {
		this.timePoints = timePoints;
	}
	/**
	 * @return Returns the followupDates.
	 */
	public String getFollowupDates() {
		return followupDates;
	}
	/**
	 * @param followupDates The followupDates to set.
	 */
	public void setFollowupDates(String followupDates) {
		this.followupDates = followupDates;
	}
	/**
	 * @return Returns the antiConvulsantStatus.
	 */
	public String getAntiConvulsantStatus() {
		return antiConvulsantStatus;
	}
	/**
	 * @param antiConvulsantStatus The antiConvulsantStatus to set.
	 */
	public void setAntiConvulsantStatus(String antiConvulsantStatus) {
		this.antiConvulsantStatus = antiConvulsantStatus;
	}
	/**
	 * @return Returns the followupMonth.
	 */
	public Long getFollowupMonth() {
		return followupMonth;
	}
	/**
	 * @param followupMonth The followupMonth to set.
	 */
	public void setFollowupMonth(Long followupMonth) {
		this.followupMonth = followupMonth;
	}
	/**
	 * @return Returns the neuroEvaluationDate.
	 */
	public Date getNeuroEvaluationDate() {
		return neuroEvaluationDate;
	}
	/**
	 * @param neuroEvaluationDate The neuroEvaluationDate to set.
	 */
	public void setNeuroEvaluationDate(Date neuroEvaluationDate) {
		this.neuroEvaluationDate = neuroEvaluationDate;
	}
	/**
	 * @return Returns the steroidDoseStatus.
	 */
	public String getSteroidDoseStatus() {
		return steroidDoseStatus;
	}
	/**
	 * @param steroidDoseStatus The steroidDoseStatus to set.
	 */
	public void setSteroidDoseStatus(String steroidDoseStatus) {
		this.steroidDoseStatus = steroidDoseStatus;
	}
	/**
	 * @return Returns the followupDate.
	 */
	public Date getFollowupDate() {
		return followupDate;
	}
	/**
	 * @param followupDate The followupDate to set.
	 */
	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
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
	 * @return Returns the raceDE.
	 */
	public RaceDE getRaceDE() {
		return raceDE;
	}
	/**
	 * @param raceDE The raceDE to set.
	 */
	public void setRaceDE(RaceDE raceDE) {
		this.raceDE = raceDE;
	}
	
	/**
	 * @return Returns the KarnofskyClinicalEvalDE.
	 */
	public KarnofskyClinicalEvalDE getKarnofskyClinicalEvalDE() {
		return karnofskyClinicalEvalDE;
	}
	/**
	 * @param KarnofskyClinicalEvalDE The KarnofskyClinicalEvalDE to set.
	 */
	public void setKarnofskyClinicalEvalDE(KarnofskyClinicalEvalDE karnofskyClinicalEvalDE) {
		this.karnofskyClinicalEvalDE = karnofskyClinicalEvalDE;
	}
	
	
	
	/**
	 * @return Returns the lanskyClinicalEvalDE.
	 */
	public LanskyClinicalEvalDE getLanskyClinicalEvalDE() {
		return lanskyClinicalEvalDE;
	}
	/**
	 * @param lanskyClinicalEvalDE The lanskyClinicalEvalDE to set.
	 */
	public void setLanskyClinicalEvalDE(LanskyClinicalEvalDE lanskyClinicalEvalDE) {
		this.lanskyClinicalEvalDE = lanskyClinicalEvalDE;
	}
	
	
	/**
	 * @return Returns the mriClinicalEvalDE.
	 */
	public MRIClinicalEvalDE getMriClinicalEvalDE() {
		return mriClinicalEvalDE;
	}
	/**
	 * @param mriClinicalEvalDE The mriClinicalEvalDE to set.
	 */
	public void setMriClinicalEvalDE(MRIClinicalEvalDE mriClinicalEvalDE) {
		this.mriClinicalEvalDE = mriClinicalEvalDE;
	}
	/**
	 * @return Returns the neuroExamClinicalEvalDE.
	 */
	public NeuroExamClinicalEvalDE getNeuroExamClinicalEvalDE() {
		return neuroExamClinicalEvalDE;
	}
	/**
	 * @param neuroExamClinicalEvalDE The neuroExamClinicalEvalDE to set.
	 */
	public void setNeuroExamClinicalEvalDE(
			NeuroExamClinicalEvalDE neuroExamClinicalEvalDE) {
		this.neuroExamClinicalEvalDE = neuroExamClinicalEvalDE;
	}
	/**
	 * @return Returns the ageGroup.
	 */
	public DatumDE getAgeGroup() {
		return this.ageGroup;
	}
	/**
	 * @param ageGroup The ageGroup to set.
	 */
	public void setAgeGroup(DatumDE ageGroup) {
		this.ageGroup = ageGroup;
	}
	/**
	 * @return Returns the genderCode.
	 */
	public GenderDE getGenderCode() {
		return this.genderCode;
	}
	/**
	 * @param genderCode The genderCode to set.
	 */
	public void setGenderCode(GenderDE genderCode) {
		this.genderCode = genderCode;
	}
	/**
	 * @return Returns the survivalLengthRange.
	 */
	public DatumDE getSurvivalLengthRange() {
		return this.survivalLengthRange;
	}
	/**
	 * @param survivalLengthRange The survivalLengthRange to set.
	 */
	public void setSurvivalLengthRange(DatumDE survivalLengthRange) {
		this.survivalLengthRange = survivalLengthRange;
	}
	/**
	 * @return Returns the biospecimen.
	 */
	public BioSpecimenIdentifierDE getBiospecimen() {
		return biospecimen;
	}
	/**
	 * @param biospecimen The biospecimen to set.
	 */
	public void setBiospecimen(BioSpecimenIdentifierDE biospecimen) {
		this.biospecimen = biospecimen;
	}
	/**
	 * @return Returns the survivalLength.
	 */
	public DatumDE getSurvivalLength() {
		return survivalLength;
	}
	/**
	 * @param survivalLength The survivalLength to set.
	 */
	public void setSurvivalLength(DatumDE survivalLength) {
		this.survivalLength = survivalLength;
	}

}
