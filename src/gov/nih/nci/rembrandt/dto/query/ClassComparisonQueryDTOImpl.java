package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionNameDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import java.util.Collection;
/**
 * @author sahnih
 */
public class ClassComparisonQueryDTOImpl implements ClassComparisonQueryDTO {
	/**
	 * This class captures the significance and/or magnitude of the difference between groups of biological
	 * specimen of which the gene expression is measured by BioAssays.
	 * 
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private static final long serialVersionUID = 1L;
    private String queryName;
	private StatisticalSignificanceDE statisticalSignificanceDE ;
	private StatisticTypeDE statisticTypeDE ;
	private MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE ;
	private ArrayPlatformDE arrayPlatformDE;
	private ExprFoldChangeDE exprFoldChangeDE;
	private Collection<ClinicalQueryDTO> comparisonGroups;
	private InstitutionNameDE institutionNameDE;

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getMultiGroupComparisonAdjustmentTypeDE()
	 */
	public MultiGroupComparisonAdjustmentTypeDE getMultiGroupComparisonAdjustmentTypeDE() {
		return multiGroupComparisonAdjustmentTypeDE;
		
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setMultiGroupComparisonAdjustmentTypeDE(gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE)
	 */
	public void setMultiGroupComparisonAdjustmentTypeDE(
			MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE) {
		this.multiGroupComparisonAdjustmentTypeDE = multiGroupComparisonAdjustmentTypeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getStatisticalSignificanceDE()
	 */
	public StatisticalSignificanceDE getStatisticalSignificanceDE() {
		return statisticalSignificanceDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setStatisticalSignificanceDE(gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE)
	 */
	public void setStatisticalSignificanceDE(
			StatisticalSignificanceDE statisticalSignificanceDE) {
		this.statisticalSignificanceDE = statisticalSignificanceDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getStatisticTypeDE()
	 */
	public StatisticTypeDE getStatisticTypeDE() {
		return statisticTypeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setStatisticTypeDE(gov.nih.nci.caintegrator.dto.de.StatisticTypeDE)
	 */
	public void setStatisticTypeDE(StatisticTypeDE statisticTypeDE) {
		this.statisticTypeDE = statisticTypeDE;
	}
	public void setQueryName(String name) {
		this.queryName = name;
	}
	public String getQueryName() {
		return queryName;
	}
	public boolean validate() throws ValidationException {
		boolean _valid = true;
		boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
		//		 Now assertsEnabled is set to the correct value 
        String errorMsg = " In ClassComparisonQueryDTO ";
		try {
			//assert(this.institutionNameDE != null);
			assert this.arrayPlatformDE != null:errorMsg+"arrayPlatformDE cannot be Null";
			assert this.comparisonGroups != null:errorMsg+"comparisonGroups cannot be Null";
			assert this.exprFoldChangeDE != null:errorMsg+"exprFoldChangeDE cannot be Null";
			assert this.multiGroupComparisonAdjustmentTypeDE!= null:errorMsg+"multiGroupComparisonAdjustmentTypeDE cannot be Null";
			assert this.queryName != null:errorMsg+"queryName cannot be Null";
			assert this.statisticalSignificanceDE != null:errorMsg+"statisticalSignificanceDE cannot be Null";
			assert this.statisticTypeDE != null:errorMsg+"statisticTypeDE cannot be Null";
				switch (multiGroupComparisonAdjustmentTypeDE.getValueObject()){
					case NONE:
						assert(statisticalSignificanceDE.getStatisticType() == StatisticalSignificanceType.pValue):
							errorMsg+"When multiGroupComparisonAdjustmentTypeDE is NONE, Statistical Type cannot equal pValue";
						break;
					case FWER:
					case FDR:
						assert(statisticalSignificanceDE.getStatisticType() == StatisticalSignificanceType.adjustedpValue):
							errorMsg+"When multiGroupComparisonAdjustmentTypeDE is FWER or FDR, Statistical Type cannot equal adjusted pValue";
						break;
					default:
							throw(new ValidationException("multiGroupComparisonAdjustmentTypeDE is does not match any options"));					
				}
			} catch (AssertionError e) {
				e.printStackTrace();
				throw(new ValidationException(e.getMessage()));
			}
		
		return _valid;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getArrayPlatformDE()
	 */
	public ArrayPlatformDE getArrayPlatformDE() {
		return arrayPlatformDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setArrayPlatformDE(gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE)
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
		this.arrayPlatformDE = arrayPlatformDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getExprFoldChangeDE()
	 */
	public ExprFoldChangeDE getExprFoldChangeDE() {
		return exprFoldChangeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setExprFoldChangeDE(gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE)
	 */
	public void setExprFoldChangeDE(ExprFoldChangeDE exprFoldChangeDE) {
		this.exprFoldChangeDE = exprFoldChangeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getComparisonGroups()
	 */
	public Collection<ClinicalQueryDTO> getComparisonGroups() {
		return comparisonGroups;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setComparisonGroups(java.util.Collection)
	 */
	public void setComparisonGroups(Collection<ClinicalQueryDTO> comparisonGroups) {
		this.comparisonGroups = comparisonGroups;
	}
	/**
	 * @return Returns the institutionNameDE.
	 */
	public InstitutionNameDE getInstitutionNameDE() {
		return institutionNameDE;
	}
	/**
	 * @param institutionNameDE The institutionNameDE to set.
	 */
	public void setInstitutionNameDE(InstitutionNameDE institutionNameDE) {
		this.institutionNameDE = institutionNameDE;
	}


    

}
