/**
 * 
 */
package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;

import java.util.Collection;

/**
 * @author sahnih
 *
 */
public class PrincipalComponentAnalysisQueryDTOImpl implements PrincipalComponentAnalysisQueryDTO {
	private String queryName;
	private ClinicalQueryDTO comparisonGroup;
	private Collection<GeneIdentifierDE> geneIdentifierDEs;
	private Collection<CloneIdentifierDE> reporterIdentifierDEs;
	private ArrayPlatformDE arrayPlatformDE;
	private InstitutionDE institutionDE;
	private GeneVectorPercentileDE geneVectorPercentileDE;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PrincipalComponentAnalysisQueryDTOImpl() {
		super();
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getQueryName()
	 */
	public String getQueryName() {
		return queryName;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setQueryName(java.lang.String)
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getArrayPlatformDE()
	 */
	public ArrayPlatformDE getArrayPlatformDE() {
		return arrayPlatformDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setArrayPlatformDE(gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE)
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
		this.arrayPlatformDE = arrayPlatformDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getComparisonGroup()
	 */
	public ClinicalQueryDTO getComparisonGroup() {
		return comparisonGroup;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setComparisonGroup(gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO)
	 */
	public void setComparisonGroup(ClinicalQueryDTO comparisonGroup) {
		this.comparisonGroup = comparisonGroup;
	}



	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getInstitutionNameDE()
	 */
	public InstitutionDE getInstitutionDE() {
		return institutionDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setInstitutionNameDE(gov.nih.nci.caintegrator.dto.de.InstitutionNameDE)
	 */
	public void setInstitutionDE(InstitutionDE institutionDE) {
		this.institutionDE = institutionDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getGeneIdentifierDEs()
	 */
	public Collection<GeneIdentifierDE> getGeneIdentifierDEs() {
		return geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setGeneIdentifierDEs(java.util.Collection)
	 */
	public void setGeneIdentifierDEs(Collection<GeneIdentifierDE> geneIdentifierDEs) {
		this.geneIdentifierDEs = geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getGeneVectorPercentileDE()
	 */
	public GeneVectorPercentileDE getGeneVectorPercentileDE() {
		return geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setGeneVectorPercentileDE(gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE)
	 */
	public void setGeneVectorPercentileDE(
			GeneVectorPercentileDE geneVectorPercentileDE) {
		this.geneVectorPercentileDE = geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#getReporterIdentifierDEs()
	 */
	public Collection<CloneIdentifierDE> getReporterIdentifierDEs() {
		return reporterIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTO#setReporterIdentifierDEs(java.util.Collection)
	 */
	public void setReporterIdentifierDEs(
			Collection<CloneIdentifierDE> reporterIdentifierDEs) {
		this.reporterIdentifierDEs = reporterIdentifierDEs;
	}



}
