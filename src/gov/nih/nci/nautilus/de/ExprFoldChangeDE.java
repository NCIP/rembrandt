package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * ExprFoldChangeDE object. It contains three child/nested classes:UpRegulation,
 * DownRegulation & UnChangedRegulation
 * 
 * @author BhattarR, BauerD
 */
abstract public class ExprFoldChangeDE extends DomainElement implements
		Serializable, Cloneable {

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

	/**
	 * type of regulation
	 */
	private String regulationType;

	/**
	 * UpRegulation
	 */
	public final static String UP_REGULATION = "UpRegulation";

	/**
	 * DownRegulation
	 */
	public final static String DOWN_REGULATION = "DownRegulation";

	/**
	 * UnchangedRegulationUpperLimit
	 */
	public final static String UNCHANGED_REGULATION_UPPER_LIMIT = "UnchangedRegulationUpperLimit";

	/**
	 * UnchangedRegulationUpperLimit
	 */
	public final static String UNCHANGED_REGULATION_DOWN_LIMIT = "UnchangedRegulationDownLimit";

	// ****************************************************
	// CONSTRUCTOR(S)
	// *****************************************************

	/**
	 * private parent constructor utilized in the two nested/childe classes
	 */
	private ExprFoldChangeDE(String regulationType, Float value) {
		super(value);
		this.regulationType = regulationType;
	}

	/**
	 * nested child class: UpRegulation
	 */
	public final static class UpRegulation extends ExprFoldChangeDE {
		public UpRegulation(Float upRegValue) {
			super(UP_REGULATION, upRegValue);
		}
	}

	/**
	 * nested child class: DownRegulation
	 */
	public final static class DownRegulation extends ExprFoldChangeDE {
		public DownRegulation(Float downRegValue) {
			super(DOWN_REGULATION, downRegValue);
		}

	}

	/**
	 * nested child class: UnChangedRegulation
	 */
	public final static class UnChangedRegulationUpperLimit extends
			ExprFoldChangeDE {
		public UnChangedRegulationUpperLimit(Float unChangedRegUpperValue) {
			super(UNCHANGED_REGULATION_UPPER_LIMIT, unChangedRegUpperValue);
		}

	}

	/**
	 * nested child class: UnChangedRegulation
	 */
	public final static class UnChangedRegulationDownLimit extends
			ExprFoldChangeDE {
		public UnChangedRegulationDownLimit(Float unChangedRegDownValue) {
			super(UNCHANGED_REGULATION_DOWN_LIMIT, unChangedRegDownValue);
		}

	}

	/**
	 * Returns the regulationType for this ExprFoldChangeDE obect.
	 * 
	 * @return the regulationType for this <code>ExprFoldChangeDE</code>
	 *         object
	 */
	public String getRegulationType() {
		return regulationType;
	}

	/**
	 * Sets the value for this <code>ExprFoldChangeDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof Float))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((Float) obj);
	}

	/**
	 * Returns the regulationType for this ExprFoldChangeDE obect.
	 * 
	 * @return the regulationType for this <code>ExprFoldChangeDE</code>
	 *         object
	 */
	public Float getValueObject() {
		return (Float) getValue();
	}

	/**
	 * Sets the regulation for this <code>ExprFoldChangeDE</code> object
	 * 
	 * @param regulation
	 *            the regulation
	 */
	public void setValueObject(Float regulation) {
		if (regulation != null) {
			this.value = regulation;
		}
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		ExprFoldChangeDE myClone = (ExprFoldChangeDE) super.clone();
		return myClone;
	}
}
