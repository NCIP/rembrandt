package gov.nih.nci.caintegrator.dto.finding;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;

public abstract class  ClassComparisonFindingsResultset implements FindingsResultset{
	protected ClassComparisonResult classComparisonResult;
	protected ClassComparisonFindingsResultset(ClassComparisonResult classComparisonResult){
		this.classComparisonResult = classComparisonResult;
	}
}
