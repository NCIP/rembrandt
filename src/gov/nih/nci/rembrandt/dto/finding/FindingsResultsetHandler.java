/**
 * 
 */
package gov.nih.nci.rembrandt.dto.finding;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.dto.finding.FindingsResultsetHandlerInterface;
import gov.nih.nci.caintegrator.exceptions.AnalysisServerException;

/**
 * @author sahnih
 *
 */
public class FindingsResultsetHandler implements FindingsResultsetHandlerInterface {
public ClassComparisonFindingsResultset processClassComparisonAnalsisResult(ClassComparisonResult classComparisonResult){
	ClassComparisonFindingsResultset classComparisonFindingsResultset = new ClassComparisonFindingsResultset(classComparisonResult);
	return classComparisonFindingsResultset;
}
public FindingsResultsetException processAnalysisServerException(AnalysisServerException analysisServerException){
	FindingsResultsetException findingsResultsetException = new FindingsResultsetException(analysisServerException);
	return findingsResultsetException;
}
/**
public static ClassComparisonFindingsResultset processClassComparisonAnalsisResult(ClassComparisonResult classComparisonResult){
	ClassComparisonFindingsResultset classComparisonFindingsResultset = new ClassComparisonFindingsResultset(classComparisonResult);
	return classComparisonFindingsResultset;
}
public static ClassComparisonFindingsResultset processClassComparisonAnalsisResult(ClassComparisonResult classComparisonResult){
	ClassComparisonFindingsResultset classComparisonFindingsResultset = new ClassComparisonFindingsResultset(classComparisonResult);
	return classComparisonFindingsResultset;
}
**/
}
