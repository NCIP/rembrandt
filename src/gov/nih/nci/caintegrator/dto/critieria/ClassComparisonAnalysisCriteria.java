/**
 * 
 */
package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
/**
 * @author sahnih
 *
 */
public class ClassComparisonAnalysisCriteria extends Criteria {

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
	StatisticalSignificanceDE statisticalSignificanceDE ;
	StatisticTypeDE statisticTypeDE ;
	MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE ;
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
	public MultiGroupComparisonAdjustmentTypeDE getMultiGroupComparisonAdjustmentTypeDE() {
		return multiGroupComparisonAdjustmentTypeDE;
	}
	public void setMultiGroupComparisonAdjustmentTypeDE(
			MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE) {
		this.multiGroupComparisonAdjustmentTypeDE = multiGroupComparisonAdjustmentTypeDE;
	}
	public StatisticalSignificanceDE getStatisticalSignificanceDE() {
		return statisticalSignificanceDE;
	}
	public void setStatisticalSignificanceDE(
			StatisticalSignificanceDE statisticalSignificanceDE) {
		this.statisticalSignificanceDE = statisticalSignificanceDE;
	}
	public StatisticTypeDE getStatisticTypeDE() {
		return statisticTypeDE;
	}
	public void setStatisticTypeDE(StatisticTypeDE statisticTypeDE) {
		this.statisticTypeDE = statisticTypeDE;
	}
    

}
