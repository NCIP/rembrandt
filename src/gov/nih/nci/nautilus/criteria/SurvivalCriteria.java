package gov.nih.nci.nautilus.criteria;

import java.io.Serializable;

import gov.nih.nci.nautilus.de.SurvivalDE;

/**
 * This class encapsulates the properties of an caintergator SurvivalCriteria
 * object.
 * 
 * @author Dana Zhang, BauerD
 */

public class SurvivalCriteria extends Criteria implements Serializable,
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
	private SurvivalDE.LowerSurvivalRange lowerSurvivalRange;

	private SurvivalDE.UpperSurvivalRange upperSurvivalRange;

	public SurvivalCriteria() {
	}

	public void setLowerSurvivalRange(
			SurvivalDE.LowerSurvivalRange lowerSurvivalRange) {
		this.lowerSurvivalRange = lowerSurvivalRange;
	}

	public void setUpperSurvivalRange(
			SurvivalDE.UpperSurvivalRange upperSurvivalRange) {
		this.upperSurvivalRange = upperSurvivalRange;
	}

	public SurvivalDE.LowerSurvivalRange getLowerSurvivalRange() {
		return lowerSurvivalRange;
	}

	public SurvivalDE.UpperSurvivalRange getUpperSurvivalRange() {
		return upperSurvivalRange;
	}

	// validate to make sure the upper and lower survival limits are correctly
	// entered.
	public boolean isValid() {
		if (lowerSurvivalRange == null && upperSurvivalRange != null) {
			return false;
		} else if (lowerSurvivalRange != null && upperSurvivalRange == null) {
			return false;
		} else if (lowerSurvivalRange != null && upperSurvivalRange != null) {
			if (upperSurvivalRange.getValueObject().intValue() < lowerSurvivalRange
					.getValueObject().intValue()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		SurvivalCriteria myClone = null;
		myClone = (SurvivalCriteria) super.clone();
		myClone.lowerSurvivalRange = (SurvivalDE.LowerSurvivalRange) lowerSurvivalRange
				.clone();
		myClone.upperSurvivalRange = (SurvivalDE.UpperSurvivalRange) upperSurvivalRange
				.clone();
		return myClone;
	}
}
