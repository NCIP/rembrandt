package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * This class encapsulates the properties of an caintergator AgeAtDiagnosisDE
 * object.
 * 
 * @author Dana Zhang, BauerD
 */
public class AgeAtDiagnosisDE extends DomainElement implements Serializable,
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

	// the type of age limit
	private String ageLimitType = null;

	// indicates a lower age limit
	public static final String LOWER_AGE_LIMIT = "lower_age_limit";

	// indicates a upper age limit
	public static final String UPPER_AGE_LIMIT = "upper_age_limit";

	// ****************************************************
	// CONSTRUCTOR(S)
	// ****************************************************

	// initializes a new AgeAtDiagnosisDE object with the age limit type and
	// actual ages
	private AgeAtDiagnosisDE(String ageLimitType, Integer ageLimit) {
		super(ageLimit);
		this.ageLimitType = ageLimitType;
	}

	// final class indicating lowerAgeLimit
	public static final class LowerAgeLimit extends AgeAtDiagnosisDE implements
			Serializable, Cloneable {
		public LowerAgeLimit(Integer lowerAge) {
			super(LOWER_AGE_LIMIT, lowerAge);
		}

		public Object clone() {
			LowerAgeLimit myClone = (LowerAgeLimit) super.clone();
			return myClone;
		}
	}

	// final class indicating upperAgeLimit
	public static final class UpperAgeLimit extends AgeAtDiagnosisDE implements
			Serializable, Cloneable {
		public UpperAgeLimit(Integer upperAge) {
			super(UPPER_AGE_LIMIT, upperAge);
		}

		public Object clone() {
			UpperAgeLimit myClone = (UpperAgeLimit) super.clone();
			return myClone;
		}
	}

	// returns the ageLimitType
	public String getAgeLimitType() {
		return ageLimitType;
	}

	// overrides upper class' method to set the age
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof Integer)) {
			throw new Exception(
					"Could not set the value. Parameter is of invalid data type: "
							+ obj);
		}
		setValueObject((Integer) obj);
	}

	// sets the age
	public void setValueObject(Integer ageLimit) {
		this.value = ageLimit;
	}

	// returns the actual age
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
		AgeAtDiagnosisDE myClone = (AgeAtDiagnosisDE) super.clone();
		return myClone;
	}
}
