package gov.nih.nci.caintegrator.dto.finding;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.rembrandt.dto.finding.ClassComparisonFindingsResultset;

public interface FindingsResultsetHandlerInterface {
	public  ClassComparisonFindingsResultset processClassComparisonAnalsisResult(ClassComparisonResult classComparisonResult);

}