package gov.nih.nci.rembrandt.dto.query;

import java.util.Collection;

import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionNameDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
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
	private StatisticalSignificanceDE statisticalSignificanceDE ;
	private StatisticTypeDE statisticTypeDE ;
	private MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE ;
	private ArrayPlatformDE arrayPlatformDE;
	private ExprFoldChangeDE exprFoldChangeDE;
	private Collection<ClinicalQueryDTO> comparisonGroups;
	private InstitutionNameDE institutionNameDE;
	
	/**
	 * This validates the ClassComparisonQueryDTOImpl
	 * @return
	 */
	public boolean isValid() {
		boolean _valid = false;
		if ((multiGroupComparisonAdjustmentTypeDE != null)&& (statisticalSignificanceDE != null)){
			switch (multiGroupComparisonAdjustmentTypeDE.getValueObject()){
				case NONE:{
					if (statisticalSignificanceDE.getStatisticType() == StatisticalSignificanceType.pValue){
						_valid = true;
					}
				}
				break;
				case FWER:
				case FDR:{
					if (statisticalSignificanceDE.getStatisticType() == StatisticalSignificanceType.adjustedpValue){
						_valid = true;
					}
				}
				break;					
			}
		}
		return _valid;
	}
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
		// TODO Auto-generated method stub
		
	}
	public String getQueryName() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean validate() throws ValidationException {
		// TODO Auto-generated method stub
		return false;
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
