/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings;

import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.ClinicalFinding;
import gov.nih.nci.caintegrator.service.findings.CopyNumberFinding;
import gov.nih.nci.caintegrator.service.findings.FindingsFactory;
import gov.nih.nci.caintegrator.service.findings.GEIntensityFinding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.KMFinding;
import gov.nih.nci.caintegrator.service.findings.PCAFinding;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.service.findings.strategies.ClassComparisonFindingStrategy;


import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class RembrandtFindingsFactory implements FindingsFactory {
	private Logger logger = Logger.getLogger(RembrandtFindingsFactory.class);
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createKMFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public KMFinding createKMFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createCopyNumberFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public CopyNumberFinding createCopyNumberFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createClinicalFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public ClinicalFinding createClinicalFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createClassComparisonFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public ClassComparisonFinding createClassComparisonFinding(ClassComparisonQueryDTO queryDTO, String sessionID, String taskID) throws FrameworkException  {
		ClassComparisonFinding finding = null;
		try {
			ClassComparisonFindingStrategy strategy = new  ClassComparisonFindingStrategy(sessionID,queryDTO.getQueryName(),queryDTO );
			strategy.createQuery();
			//classComparisonFindingStrategy.executeQuery();
			strategy.analyzeResultSet();
			finding = (ClassComparisonFinding)strategy.getFinding();

		} catch (ValidationException e) {
			logger.error(e);
			throw(e);
		} catch (FindingsQueryException e) {
			logger.error(e);
			throw(e);
		} catch (FindingsAnalysisException e) {
			logger.error(e);
			throw(e);
		}
		return finding;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createPCAFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public PCAFinding createPCAFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createHCAFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public HCAFinding createHCAFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createGEIntensityFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public GEIntensityFinding createGEIntensityFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createCustomFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public Object createCustomFinding(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	public KMFinding createKMFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public CopyNumberFinding createCopyNumberFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClinicalFinding createClinicalFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public PCAFinding createPCAFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public HCAFinding createHCAFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public GEIntensityFinding createGEIntensityFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object createCustomFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}


}
