package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

/**
 * This class encapsulates the properties of an caintergator SurvivalDE object.
 * 
 * @author Dana Zhang, BauerD
 */

public class SurvivalDE extends DomainElement implements Serializable,
		Cloneable {

	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */

	// the type of survival limit
	private String survivalRangeType = null;

	// final String indicates a upper survival range
	public static final String UPPER_SURVIVAL_RANGE = "upper_survival_range";

	// fianl String indicates a lower survival range
	public static final String LOWER_SURVIVAL_RANGE = "lower_survival_range";

	// initializes a SurvivalDE object with the survival limit type and actual
	// survival limit
	private SurvivalDE(String survivalRangeType, Integer survivalLimit) {
		super(survivalLimit);
		this.survivalRangeType = survivalRangeType;
	}

	// final class UpperSurvivalRange indicating upper survival range
	public static final class UpperSurvivalRange extends SurvivalDE {
		public UpperSurvivalRange(Integer upperSurvivalRange) {
			super(UPPER_SURVIVAL_RANGE, upperSurvivalRange);
		}
	}

	// final class LowerSurvivalRange indicating lower survival range
	public static final class LowerSurvivalRange extends SurvivalDE {
		public LowerSurvivalRange(Integer lowerSurvivalRange) {
			super(LOWER_SURVIVAL_RANGE, lowerSurvivalRange);
		}
	}

	// return the type of survival : upper or lower
	public String getSurvivalLimitType() {
		return survivalRangeType;
	}

	// implements the upper class abstract method
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof Integer)) {
			throw new Exception(
					"Could not set the value. Parameter is of invalid type :"
							+ obj);
		}
		this.setValueObject((Integer) obj);
	}

	// sets the survival limit
	public void setValueObject(Integer survivalLimit) {
		this.value = survivalLimit;
	}

	// returns the survival limit
	public Integer getValueObject() {
		return (Integer) getValue();
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		SurvivalDE myClone = (SurvivalDE) super.clone();
		return myClone;
	}

}
