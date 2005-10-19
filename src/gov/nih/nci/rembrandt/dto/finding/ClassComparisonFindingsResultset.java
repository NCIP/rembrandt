/**
 * 
 */
package gov.nih.nci.rembrandt.dto.finding;

import java.util.List;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;


/**
 * @author sahnih
 *
 */
public class ClassComparisonFindingsResultset extends gov.nih.nci.caintegrator.dto.finding.ClassComparisonFindingsResultset{
	//TODO:Change to Domain objects returns

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param result
	 */
	public ClassComparisonFindingsResultset(ClassComparisonResult result) {
		super(result);
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult#arePvaluesAdjusted()
	 */
	public boolean arePvaluesAdjusted() {
		return classComparisonResult.arePvaluesAdjusted();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult#getGroup1()
	 */
	public SampleGroup getGroup1() {
		return classComparisonResult.getGroup1();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult#getGroup2()
	 */
	public SampleGroup getGroup2() {
		return classComparisonResult.getGroup2();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult#getNumResultEntries()
	 */
	public int getNumResultEntries() {
		return classComparisonResult.getNumResultEntries();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult#getResultEntries()
	 */
	public List<ClassComparisonResultEntry> getResultEntries() {
		return classComparisonResult.getResultEntries();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.AnalysisResult#getSessionId()
	 */
	public String getSessionId() {
		return classComparisonResult.getSessionId();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.analysis.messaging.AnalysisResult#getTaskId()
	 */
	public String getTaskId() {
		return classComparisonResult.getTaskId();
	}
}
