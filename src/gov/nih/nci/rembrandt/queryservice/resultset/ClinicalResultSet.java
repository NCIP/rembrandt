/*
 * Created on Nov 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset;

import java.sql.Date;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClinicalResultSet {
	/**
	 * @return Returns the ageGroup.
	 */
	public abstract String getAgeGroup();

	/**
	 * @param ageGroup The ageGroup to set.
	 */
	public abstract void setAgeGroup(String ageGroup);

	/**
	 * @return Returns the biospecimenId.
	 */
	public abstract Long getBiospecimenId();

	/**
	 * @param biospecimenId The biospecimenId to set.
	 */
	public abstract void setBiospecimenId(Long biospecimenId);

	/**
	 * @return Returns the diseaseType.
	 */
	public abstract String getDiseaseType();

	/**
	 * @param diseaseType The diseaseType to set.
	 */
	public abstract void setDiseaseType(String diseaseType);

	/**
	 * @return Returns the genderCode.
	 */
	public abstract String getGenderCode();

	/**
	 * @param genderCode The genderCode to set.
	 */
	public abstract void setGenderCode(String genderCode);

	/**
	 * @return Returns the sampleId.
	 */
	public abstract String getSampleId();

	/**
	 * @param sampleId The sampleId to set.
	 */
	public abstract void setSampleId(String sampleId);

	/**
	 * @return Returns the survivalLengthRange.
	 */
	public abstract String getSurvivalLengthRange();

	/**
	 * @param survivalLengthRange The survivalLengthRange to set.
	 */
	public abstract void setSurvivalLengthRange(String survivalLengthRange);
	
	
	/**
	 * @return Returns the race.
	 */
	public  String getRace();

	/**
	 * @param Race The Race to set.
	 */
	public  void setRace(String race);
	
	public  Long getKarnofskyScore();
	public  String getKarnofskyScores();
	public  Long getLanskyScore();
	public  String getLanskyScores();
	public  Long getNeuroExam();
	public  String getNeuroExams();
	public  Long getMriCtScore();
	public  String getMriCtScores();
	public  String getTimePoint();
	public  String getTimePoints();
	public  Date getFollowupDate();	
	public  String getFollowupDates();	
	public  Long getFollowupMonth();
	public  String getFollowupMonths();
	public  Date getNeuroEvaluationDate();
	public  String getNeuroEvaluationDates();
	public  String getSteroidDoseStatus();
	public  String getSteroidDoseStatuses();
	public  String getAntiConvulsantStatus();
	public  String getAntiConvulsantStatuses();
	
	public  String getPriorRadiationTimePoints();
	public  String getPriorRadiationRadiationSites();
	public  String getPriorRadiationDoseStartDates();
	public  String getPriorRadiationDoseStopDates();
	public  String getPriorRadiationFractionDoses();
	public  String getPriorRadiationFractionNumbers();
	public  String getPriorRadiationRadiationTypes(); 
	
	public String getPriorChemoTimePoints();
	public String getPriorChemoagentIds();
	public String getPriorChemoAgentNames();
	public String getPriorChemoCourseCounts() ;
	public String getPriorChemoDoseStartDates() ;
	public String getPriorChemoDoseStopDates();
	public String getPriorChemoStudySources() ;
	public String getPriorChemoProtocolNumbers();
	
	public String getPriorSurgeryTimePoints();
	public String getPriorSurgeryProcedureTitles();
	public String getPriorSurgeryTumorHistologys() ;
	public String getPriorSurgerySurgeryDates();
	public String getPriorSurgerySurgeryOutcomes();
	   
	  
	   
	
	
	public  void setKarnofskyScore(Long arnofskyScore);	
	public  void setKarnofskyScores(String arnofskyScore);	
	public  void setLanskyScore(Long lanskyScore);	
	public  void setLanskyScores(String lanskyScores);	
	public  void setNeuroExam(Long neuroExam);
	public  void setNeuroExams(String neuroExams);
	public  void setMriCtScore(Long mriCtScore);
	public  void setMriCtScores(String mriCtScores);
	public  void setTimePoint(String timePoint);
	public  void setTimePoints(String timePoints);
	public  void setFollowupDate(Date followupDate);
	public  void setFollowupDates(String followupDates);
	public  void setFollowupMonth(Long followupMonth);
	public  void setFollowupMonths(String followupMonths);
	public  void setNeuroEvaluationDate(Date neuroEvaluationDate);
	public  void setNeuroEvaluationDates( String neuroEvaluationDates);
	public  void setSteroidDoseStatus(String steroidDoseStatus);
	public  void setSteroidDoseStatuses(String steroidDoseStatuses);
	public  void setAntiConvulsantStatus(String antiConvulsantStatus);
	public  void setAntiConvulsantStatuses(String antiConvulsantStatuses);	
	
	
	public  void setPriorRadiationTimePoints(String timePoints);
	public  void setPriorRadiationRadiationSites(String radiationSites);
	public  void setPriorRadiationDoseStartDates(String doseStartDates);
	public  void setPriorRadiationDoseStopDates(String doseStopDates);
	public  void setPriorRadiationFractionDoses(String fractionDoses);
	public  void setPriorRadiationFractionNumbers(String fractionNumbers);
	public  void setPriorRadiationRadiationTypes(String radiationTypes ); 
	
	public void setPriorChemoTimePoints(String priorChemoTimePoints);
	public void setPriorChemoagentIds(String priorChemoagentIds);
	public void setPriorChemoAgentNames(String priorChemoAgentNames);
	public void setPriorChemoCourseCounts(String priorChemoCourseCounts);
	public void setPriorChemoDoseStartDates(String priorChemoDoseStartDates) ;
	public void setPriorChemoDoseStopDates(String priorChemoDoseStopDates);
	public void setPriorChemoStudySources(String priorChemoStudySources) ;
	public void setPriorChemoProtocolNumbers(String priorChemoProtocolNumbers);
	
	public void setPriorSurgeryTimePoints(String priorSurgeryTimePoints);
	public void setPriorSurgeryProcedureTitles(String priorSurgeryProcedureTitles) ;
	public void setPriorSurgeryTumorHistologys(String priorSurgeryTumorHistologys);
	public void setPriorSurgerySurgeryDates(String priorSurgerySurgeryDates) ;
	public void setPriorSurgerySurgeryOutcomes(String priorSurgerySurgeryOutcomes) ;
	
	
	
	


}