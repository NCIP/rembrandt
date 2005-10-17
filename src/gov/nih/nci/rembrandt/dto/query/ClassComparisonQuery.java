package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ClassComparisonAnalysisCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

import java.util.Collection;

/**
 * @author sahnih
 * ClassComprisonQuery encapulates the necessary criteria objects 
 * for Run Time Class Comparison Analysis 
 *
 */
public class ClassComparisonQuery extends Query implements gov.nih.nci.caintegrator.dto.query.ClassComparisonQuery{
    private String name;
	private ClassComparisonAnalysisCriteria classComparisionAnalysisCriteria;
	private InstitutionCriteria institutionCriteria;
	private FoldChangeCriteria foldChangeCriteria;
	private ArrayPlatformCriteria arrayPlatformCriteria;
	private Collection<ClinicalDataQuery> clinicalDataQueryCollection;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ClassComparisonQuery() {
		super();
	}
	/**
	 * @return Returns the clinicalDataQuery.
	 */
	public Collection<ClinicalDataQuery> getClinicalDataQueryCollection() {
		return clinicalDataQueryCollection;
	}
	/**
	 * @param clinicalDataQuery The clinicalDataQuery to set.
	 */
	public void setClinicalDataQueryCollection(Collection<ClinicalDataQuery> clinicalDataQueryCollection) {
		this.clinicalDataQueryCollection = clinicalDataQueryCollection;
	}
	@Override
	public QueryHandler getQueryHandler() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public QueryType getQueryType() throws Exception {
		return QueryType.CLASS_COMPARISON_QUERY;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Returns the arrayPlatformCriteria.
	 */
	public ArrayPlatformCriteria getArrayPlatformCriteria() {
		return arrayPlatformCriteria;
	}

	/**
	 * @param arrayPlatformCriteria The arrayPlatformCriteria to set.
	 */
	public void setArrayPlatformCriteria(ArrayPlatformCriteria arrayPlatformCriteria) {
		this.arrayPlatformCriteria = arrayPlatformCriteria;
	}

	/**
	 * @return Returns the classComparisionAnalysisCriteria.
	 */
	public ClassComparisonAnalysisCriteria getClassComparisionAnalysisCriteria() {
		return classComparisionAnalysisCriteria;
	}

	/**
	 * @param classComparisionAnalysisCriteria The classComparisionAnalysisCriteria to set.
	 */
	public void setClassComparisionAnalysisCriteria(
			ClassComparisonAnalysisCriteria classComparisionAnalysisCriteria) {
		this.classComparisionAnalysisCriteria = classComparisionAnalysisCriteria;
	}

	/**
	 * @return Returns the foldChangeCriteria.
	 */
	public FoldChangeCriteria getFoldChangeCriteria() {
		return foldChangeCriteria;
	}

	/**
	 * @param foldChangeCriteria The foldChangeCriteria to set.
	 */
	public void setFoldChangeCriteria(FoldChangeCriteria foldChangeCriteria) {
		this.foldChangeCriteria = foldChangeCriteria;
	}

	/**
	 * @return Returns the institutionCriteria.
	 */
	public InstitutionCriteria getInstitutionCriteria() {
		return institutionCriteria;
	}

	/**
	 * @param institutionCriteria The institutionCriteria to set.
	 */
	public void setInstitutionCriteria(InstitutionCriteria institutionCriteria) {
		this.institutionCriteria = institutionCriteria;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	public boolean validate() throws ValidationException {
		if(getClinicalDataQueryCollection() != null && getClinicalDataQueryCollection().size() >= 2){
			return true;
		}
		else throw new ValidationException("ClinicalDataQueryCollection has to have 2 or more groups")
		return false;
	}


}
