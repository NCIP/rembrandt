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
	public abstract String getRace();

	/**
	 * @param Race The Race to set.
	 */
	public abstract void setRace(String race);
	
	public abstract Long getKarnofskyScore();
	public abstract String getKarnofskyScores();
	public abstract Long getLanskyScore();
	public abstract String getLanskyScores();
	public abstract Long getNeuroExam();
	public abstract String getNeuroExams();
	public abstract Long getMriCtScore();
	public abstract String getMriCtScores();
	public abstract String getTimePoint();
	public abstract String getTimePoints();
	public abstract Date getFollowupDate();	
	public abstract String getFollowupDates();	
	public abstract Long getFollowupMonth();
	public abstract String getFollowupMonths();
	public abstract Date getNeuroEvaluationDate();
	public abstract String getNeuroEvaluationDates();
	public abstract String getSteroidDoseStatus();
	public abstract String getSteroidDoseStatuses();
	public abstract String getAntiConvulsantStatus();
	public abstract String getAntiConvulsantStatuses();
	
	
	public abstract void setKarnofskyScore(Long arnofskyScore);	
	public abstract void setKarnofskyScores(String arnofskyScore);	
	public abstract void setLanskyScore(Long lanskyScore);	
	public abstract void setLanskyScores(String lanskyScores);	
	public abstract void setNeuroExam(Long neuroExam);
	public abstract void setNeuroExams(String neuroExams);
	public abstract void setMriCtScore(Long mriCtScore);
	public abstract void setMriCtScores(String mriCtScores);
	public abstract void setTimePoint(String timePoint);
	public abstract void setTimePoints(String timePoints);
	public abstract void setFollowupDate(Date followupDate);
	public abstract void setFollowupDates(String followupDates);
	public abstract void setFollowupMonth(Long followupMonth);
	public abstract void setFollowupMonths(String followupMonths);
	public abstract void setNeuroEvaluationDate(Date neuroEvaluationDate);
	public abstract void setNeuroEvaluationDates( String neuroEvaluationDates);
	public abstract void setSteroidDoseStatus(String steroidDoseStatus);
	public abstract void setSteroidDoseStatuses(String steroidDoseStatuses);
	public abstract void setAntiConvulsantStatus(String antiConvulsantStatus);
	public abstract void setAntiConvulsantStatuses(String antiConvulsantStatuses);
	
	
	
	


}