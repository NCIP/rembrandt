/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings;

import gov.nih.nci.caintegrator.dto.finding.ClassComparisonFindingsResultset;

import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClinicalFinding;
import gov.nih.nci.caintegrator.service.findings.CopyNumberFinding;
import gov.nih.nci.caintegrator.service.findings.GEIntensityFinding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.KMFinding;
import gov.nih.nci.caintegrator.service.findings.PCAFinding;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQuery;
import gov.nih.nci.rembrandt.service.findings.strategies.ClassComparisonFindingStrategy;


import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class FindingsFactory implements
		gov.nih.nci.caintegrator.service.findings.FindingsFactory {
	private Logger logger = Logger.getLogger(FindingsFactory.class);
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createKMFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public KMFinding createKMFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createCopyNumberFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public CopyNumberFinding createCopyNumberFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createClinicalFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public ClinicalFinding createClinicalFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createClassComparisonFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public void createClassComparisonFinding(gov.nih.nci.caintegrator.dto.query.ClassComparisonQuery query, String sessionID, String taskID) {
		try {
			ClassComparisonQuery classComparisonQuery = (ClassComparisonQuery) query;
			ClassComparisonFindingStrategy classComparisonFindingStrategy = new  ClassComparisonFindingStrategy(sessionID,classComparisonQuery.getQueryName(),classComparisonQuery );
			classComparisonFindingStrategy.createQuery();
			//classComparisonFindingStrategy.executeQuery();//TODO:DEBUG
			classComparisonFindingStrategy.analyzeResultSet();
		
		} catch (ValidationException e) {
			logger.error(e);
		} catch (FindingsQueryException e) {
			logger.error(e);
		} catch (FindingsAnalysisException e) {
			logger.error(e);
		}

	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createPCAFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public PCAFinding createPCAFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createHCAFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public HCAFinding createHCAFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createGEIntensityFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public GEIntensityFinding createGEIntensityFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createCustomFinding(gov.nih.nci.caintegrator.dto.query.Query)
	 */
	public Object createCustomFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}


}
