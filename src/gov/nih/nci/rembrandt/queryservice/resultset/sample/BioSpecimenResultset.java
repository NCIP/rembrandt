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
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;

/**
 * @author SahniH
 *
 This class encapulates a sample Id and foldchange or copy number values..
 */
public abstract class  BioSpecimenResultset {
	private BioSpecimenIdentifierDE biospecimen = null;
	private Long age = null;
	private SampleIDDE sampleIDDE = null;
	private DatumDE censor = null;
	private GenderDE genderCode = null;
	private KarnofskyClinicalEvalDE karnofskyClinicalEvalDE = null;
	private LanskyClinicalEvalDE lanskyClinicalEvalDE = null;
	private NeuroExamClinicalEvalDE neuroExamClinicalEvalDE = null;
	private MRIClinicalEvalDE mriClinicalEvalDE = null;
	private String timePoint = null;	
	private String whoGrade = null;
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
	private String neuroExamDescs;
	private String mriScoreDescs;

	
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
	  
	  
	  private String onStudyRadiationTimePoints;
	    private String onStudyRadiationRadiationSites;
	    private String onStudyRadiationDoseStartDates;
	    private String onStudyRadiationDoseStopDates;
	    private String onStudyRadiationFractionDoses;
	    private String onStudyRadiationFractionNumbers;
	    private String onStudyRadiationRadiationTypes;
	    private String onStudyRadiationNeurosisStatuses;
	    
	    
	    private String onStudyChemoTimePoints;
	    private String onStudyChemoagentIds ;
	    private String onStudyChemoAgentNames ;							
	    private String onStudyChemoCourseCounts ;
	    private String onStudyChemoDoseStartDates;	
	    private String onStudyChemoDoseStopDates ;
	    private String onStudyChemoStudySources ;
	    private String onStudyChemoProtocolNumbers ;
	    private String onStudyChemoRegimenNumbers;
	    
	    private String onStudySurgeryTimePoints ;
	    private String onStudySurgeryProcedureTitles ;
	    private String onStudySurgeryHistoDiagnoses ;							
	    private String onStudySurgerySurgeryDates ;
	    private String onStudySurgerySurgeryOutcomes ;
	    private String onStudySurgeryIndications ;
	    //private DatumDE survivalLength ;
	    private Long survivalLength ;
	
	
	
	/**
		 * @return Returns the mriScoreDescs.
		 */
		public String getMriScoreDescs() {
			return mriScoreDescs;
		}
		/**
		 * @param mriScoreDescs The mriScoreDescs to set.
		 */
		public void setMriScoreDescs(String mriScoreDescs) {
			this.mriScoreDescs = mriScoreDescs;
		}
		/**
		 * @return Returns the neuroExamDescs.
		 */
		public String getNeuroExamDescs() {
			return neuroExamDescs;
		}
		/**
		 * @param neuroExamDescs The neuroExamDescs to set.
		 */
		public void setNeuroExamDescs(String neuroExamDescs) {
			this.neuroExamDescs = neuroExamDescs;
		}
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
	public Long getSurvivalLength() {
		return survivalLength;
	}
	/**
	 * @param survivalLength The survivalLength to set.
	 */
	public void setSurvivalLength(Long survivalLength) {
		this.survivalLength = survivalLength;
	}

	/**
	 * @return Returns the onStudyChemoagentIds.
	 */
	public String getOnStudyChemoagentIds() {
		return onStudyChemoagentIds;
	}
	/**
	 * @param onStudyChemoagentIds The onStudyChemoagentIds to set.
	 */
	public void setOnStudyChemoagentIds(String onStudyChemoagentIds) {
		this.onStudyChemoagentIds = onStudyChemoagentIds;
	}
	/**
	 * @return Returns the onStudyChemoAgentNames.
	 */
	public String getOnStudyChemoAgentNames() {
		return onStudyChemoAgentNames;
	}
	/**
	 * @param onStudyChemoAgentNames The onStudyChemoAgentNames to set.
	 */
	public void setOnStudyChemoAgentNames(String onStudyChemoAgentNames) {
		this.onStudyChemoAgentNames = onStudyChemoAgentNames;
	}
	/**
	 * @return Returns the onStudyChemoCourseCounts.
	 */
	public String getOnStudyChemoCourseCounts() {
		return onStudyChemoCourseCounts;
	}
	/**
	 * @param onStudyChemoCourseCounts The onStudyChemoCourseCounts to set.
	 */
	public void setOnStudyChemoCourseCounts(String onStudyChemoCourseCounts) {
		this.onStudyChemoCourseCounts = onStudyChemoCourseCounts;
	}
	/**
	 * @return Returns the onStudyChemoDoseStartDates.
	 */
	public String getOnStudyChemoDoseStartDates() {
		return onStudyChemoDoseStartDates;
	}
	/**
	 * @param onStudyChemoDoseStartDates The onStudyChemoDoseStartDates to set.
	 */
	public void setOnStudyChemoDoseStartDates(String onStudyChemoDoseStartDates) {
		this.onStudyChemoDoseStartDates = onStudyChemoDoseStartDates;
	}
	/**
	 * @return Returns the onStudyChemoDoseStopDates.
	 */
	public String getOnStudyChemoDoseStopDates() {
		return onStudyChemoDoseStopDates;
	}
	/**
	 * @param onStudyChemoDoseStopDates The onStudyChemoDoseStopDates to set.
	 */
	public void setOnStudyChemoDoseStopDates(String onStudyChemoDoseStopDates) {
		this.onStudyChemoDoseStopDates = onStudyChemoDoseStopDates;
	}
	/**
	 * @return Returns the onStudyChemoProtocolNumbers.
	 */
	public String getOnStudyChemoProtocolNumbers() {
		return onStudyChemoProtocolNumbers;
	}
	/**
	 * @param onStudyChemoProtocolNumbers The onStudyChemoProtocolNumbers to set.
	 */
	public void setOnStudyChemoProtocolNumbers(String onStudyChemoProtocolNumbers) {
		this.onStudyChemoProtocolNumbers = onStudyChemoProtocolNumbers;
	}
	/**
	 * @return Returns the onStudyChemoRegimenNumbers.
	 */
	public String getOnStudyChemoRegimenNumbers() {
		return onStudyChemoRegimenNumbers;
	}
	/**
	 * @param onStudyChemoRegimenNumbers The onStudyChemoRegimenNumbers to set.
	 */
	public void setOnStudyChemoRegimenNumbers(String onStudyChemoRegimenNumbers) {
		this.onStudyChemoRegimenNumbers = onStudyChemoRegimenNumbers;
	}
	/**
	 * @return Returns the onStudyChemoStudySources.
	 */
	public String getOnStudyChemoStudySources() {
		return onStudyChemoStudySources;
	}
	/**
	 * @param onStudyChemoStudySources The onStudyChemoStudySources to set.
	 */
	public void setOnStudyChemoStudySources(String onStudyChemoStudySources) {
		this.onStudyChemoStudySources = onStudyChemoStudySources;
	}
	/**
	 * @return Returns the onStudyChemoTimePoints.
	 */
	public String getOnStudyChemoTimePoints() {
		return onStudyChemoTimePoints;
	}
	/**
	 * @param onStudyChemoTimePoints The onStudyChemoTimePoints to set.
	 */
	public void setOnStudyChemoTimePoints(String onStudyChemoTimePoints) {
		this.onStudyChemoTimePoints = onStudyChemoTimePoints;
	}
	/**
	 * @return Returns the onStudyRadiationDoseStartDates.
	 */
	public String getOnStudyRadiationDoseStartDates() {
		return onStudyRadiationDoseStartDates;
	}
	/**
	 * @param onStudyRadiationDoseStartDates The onStudyRadiationDoseStartDates to set.
	 */
	public void setOnStudyRadiationDoseStartDates(
			String onStudyRadiationDoseStartDates) {
		this.onStudyRadiationDoseStartDates = onStudyRadiationDoseStartDates;
	}
	/**
	 * @return Returns the onStudyRadiationDoseStopDates.
	 */
	public String getOnStudyRadiationDoseStopDates() {
		return onStudyRadiationDoseStopDates;
	}
	/**
	 * @param onStudyRadiationDoseStopDates The onStudyRadiationDoseStopDates to set.
	 */
	public void setOnStudyRadiationDoseStopDates(
			String onStudyRadiationDoseStopDates) {
		this.onStudyRadiationDoseStopDates = onStudyRadiationDoseStopDates;
	}
	/**
	 * @return Returns the onStudyRadiationFractionDoses.
	 */
	public String getOnStudyRadiationFractionDoses() {
		return onStudyRadiationFractionDoses;
	}
	/**
	 * @param onStudyRadiationFractionDoses The onStudyRadiationFractionDoses to set.
	 */
	public void setOnStudyRadiationFractionDoses(
			String onStudyRadiationFractionDoses) {
		this.onStudyRadiationFractionDoses = onStudyRadiationFractionDoses;
	}
	/**
	 * @return Returns the onStudyRadiationFractionNumbers.
	 */
	public String getOnStudyRadiationFractionNumbers() {
		return onStudyRadiationFractionNumbers;
	}
	/**
	 * @param onStudyRadiationFractionNumbers The onStudyRadiationFractionNumbers to set.
	 */
	public void setOnStudyRadiationFractionNumbers(
			String onStudyRadiationFractionNumbers) {
		this.onStudyRadiationFractionNumbers = onStudyRadiationFractionNumbers;
	}
	/**
	 * @return Returns the onStudyRadiationNeurosisStatuses.
	 */
	public String getOnStudyRadiationNeurosisStatuses() {
		return onStudyRadiationNeurosisStatuses;
	}
	/**
	 * @param onStudyRadiationNeurosisStatuses The onStudyRadiationNeurosisStatuses to set.
	 */
	public void setOnStudyRadiationNeurosisStatuses(
			String onStudyRadiationNeurosisStatuses) {
		this.onStudyRadiationNeurosisStatuses = onStudyRadiationNeurosisStatuses;
	}
	/**
	 * @return Returns the onStudyRadiationRadiationSites.
	 */
	public String getOnStudyRadiationRadiationSites() {
		return onStudyRadiationRadiationSites;
	}
	/**
	 * @param onStudyRadiationRadiationSites The onStudyRadiationRadiationSites to set.
	 */
	public void setOnStudyRadiationRadiationSites(
			String onStudyRadiationRadiationSites) {
		this.onStudyRadiationRadiationSites = onStudyRadiationRadiationSites;
	}
	/**
	 * @return Returns the onStudyRadiationRadiationTypes.
	 */
	public String getOnStudyRadiationRadiationTypes() {
		return onStudyRadiationRadiationTypes;
	}
	/**
	 * @param onStudyRadiationRadiationTypes The onStudyRadiationRadiationTypes to set.
	 */
	public void setOnStudyRadiationRadiationTypes(
			String onStudyRadiationRadiationTypes) {
		this.onStudyRadiationRadiationTypes = onStudyRadiationRadiationTypes;
	}
	/**
	 * @return Returns the onStudyRadiationTimePoints.
	 */
	public String getOnStudyRadiationTimePoints() {
		return onStudyRadiationTimePoints;
	}
	/**
	 * @param onStudyRadiationTimePoints The onStudyRadiationTimePoints to set.
	 */
	public void setOnStudyRadiationTimePoints(String onStudyRadiationTimePoints) {
		this.onStudyRadiationTimePoints = onStudyRadiationTimePoints;
	}
	/**
	 * @return Returns the onStudySurgeryHistoDiagnoses.
	 */
	public String getOnStudySurgeryHistoDiagnoses() {
		return onStudySurgeryHistoDiagnoses;
	}
	/**
	 * @param onStudySurgeryHistoDiagnoses The onStudySurgeryHistoDiagnoses to set.
	 */
	public void setOnStudySurgeryHistoDiagnoses(String onStudySurgeryHistoDiagnoses) {
		this.onStudySurgeryHistoDiagnoses = onStudySurgeryHistoDiagnoses;
	}
	/**
	 * @return Returns the onStudySurgeryIndications.
	 */
	public String getOnStudySurgeryIndications() {
		return onStudySurgeryIndications;
	}
	/**
	 * @param onStudySurgeryIndications The onStudySurgeryIndications to set.
	 */
	public void setOnStudySurgeryIndications(String onStudySurgeryIndications) {
		this.onStudySurgeryIndications = onStudySurgeryIndications;
	}
	/**
	 * @return Returns the onStudySurgeryProcedureTitles.
	 */
	public String getOnStudySurgeryProcedureTitles() {
		return onStudySurgeryProcedureTitles;
	}
	/**
	 * @param onStudySurgeryProcedureTitles The onStudySurgeryProcedureTitles to set.
	 */
	public void setOnStudySurgeryProcedureTitles(
			String onStudySurgeryProcedureTitles) {
		this.onStudySurgeryProcedureTitles = onStudySurgeryProcedureTitles;
	}
	/**
	 * @return Returns the onStudySurgerySurgeryDates.
	 */
	public String getOnStudySurgerySurgeryDates() {
		return onStudySurgerySurgeryDates;
	}
	/**
	 * @param onStudySurgerySurgeryDates The onStudySurgerySurgeryDates to set.
	 */
	public void setOnStudySurgerySurgeryDates(String onStudySurgerySurgeryDates) {
		this.onStudySurgerySurgeryDates = onStudySurgerySurgeryDates;
	}
	/**
	 * @return Returns the onStudySurgerySurgeryOutcomes.
	 */
	public String getOnStudySurgerySurgeryOutcomes() {
		return onStudySurgerySurgeryOutcomes;
	}
	/**
	 * @param onStudySurgerySurgeryOutcomes The onStudySurgerySurgeryOutcomes to set.
	 */
	public void setOnStudySurgerySurgeryOutcomes(
			String onStudySurgerySurgeryOutcomes) {
		this.onStudySurgerySurgeryOutcomes = onStudySurgerySurgeryOutcomes;
	}
	/**
	 * @return Returns the onStudySurgeryTimePoints.
	 */
	public String getOnStudySurgeryTimePoints() {
		return onStudySurgeryTimePoints;
	}
	/**
	 * @param onStudySurgeryTimePoints The onStudySurgeryTimePoints to set.
	 */
	public void setOnStudySurgeryTimePoints(String onStudySurgeryTimePoints) {
		this.onStudySurgeryTimePoints = onStudySurgeryTimePoints;
	}
	/**
	 * @return Returns the whoGrade.
	 */
	public String getWhoGrade() {
		return whoGrade;
	}
	/**
	 * @param whoGrade The whoGrade to set.
	 */
	public void setWhoGrade(String whoGrade) {
		this.whoGrade = whoGrade;
	}

	/**
	 * @return Returns the sampleIDDE.
	 */
	public SampleIDDE getSampleIDDE() {
		return sampleIDDE;
	}
	/**
	 * @param sampleIDDE The sampleIDDE to set.
	 */
	public void setSampleIDDE(SampleIDDE sampleIDDE) {
		this.sampleIDDE = sampleIDDE;
	}
	/**
	 * @return Returns the censor.
	 */
	public DatumDE getCensor() {
		return this.censor;
	}
	/**
	 * @param censor The censor to set.
	 */
	public void setCensor(DatumDE censor) {
		this.censor = censor;
	}
	/**
	 * @return Returns the age.
	 */
	public Long getAge() {
		return age;
	}
	/**
	 * @param age The age to set.
	 */
	public void setAge(Long age) {
		this.age = age;
	}


}
