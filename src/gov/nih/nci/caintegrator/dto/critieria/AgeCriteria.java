package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.AgeAtDiagnosisDE;

import java.io.Serializable;

/**
 * This class encapsulates the properties of an caintergator AgeCriteria object.
 * 
 * @author Dana Zhang, BauerD
 */

public class AgeCriteria extends Criteria implements Serializable, Cloneable {

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
	private AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit;

	private AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit;

	public AgeCriteria() {
	}

	public void setLowerAgeLimit(AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit) {
		this.lowerAgeLimit = lowerAgeLimit;
	}

	public AgeAtDiagnosisDE.LowerAgeLimit getLowerAgeLimit() {
		return lowerAgeLimit;
	}

	public void setUpperAgeLimit(AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit) {
		this.upperAgeLimit = upperAgeLimit;
	}

	public AgeAtDiagnosisDE.UpperAgeLimit getUpperAgeLimit() {
		return upperAgeLimit;
	}

	// validate the upper and lower age limit entries
	public boolean isValid() {

		if (lowerAgeLimit == null && upperAgeLimit != null) {
			return false;
		} else if (lowerAgeLimit != null && upperAgeLimit == null) {
			return false;
		} else if (lowerAgeLimit != null && upperAgeLimit != null) {
			if (upperAgeLimit.getValueObject().intValue() < lowerAgeLimit
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
		AgeCriteria myClone = (AgeCriteria) super.clone();
		if(lowerAgeLimit!=null) {
			myClone.lowerAgeLimit = (AgeAtDiagnosisDE.LowerAgeLimit) lowerAgeLimit
				.clone();
		}
		if(upperAgeLimit!=null) {
			myClone.upperAgeLimit = (AgeAtDiagnosisDE.UpperAgeLimit) upperAgeLimit
					.clone();
		}
		return myClone;
	}

}
