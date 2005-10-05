package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

//caintergator classes

/**
 * This abstract class encapsulates the properties of an caintergator
 * BasePairPositionDE object. It contains two child/nested classes:StartPosition &
 * EndPosition
 * 
 * @author BhattarR, BauerD
 */

abstract public class BasePairPositionDE extends DomainElement implements
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
	 * type of positon
	 */
	private String positionType;

	/**
	 * start base postion
	 */
	public final static String START_POSITION = "StartPosition";

	/**
	 * end base postion
	 */
	public final static String END_POSITION = "StartPosition";

	// ****************************************************
	// CONSTRUCTOR(S)
	// *****************************************************

	/**
	 * private parent constructor utilized in the two nested/childe classes
	 */
	private BasePairPositionDE(String positionType, Long value) {
		super(value);
		this.positionType = positionType;
	}

	/**
	 * nested child class: StartPosition
	 */
	public final static class StartPosition extends BasePairPositionDE {
		public StartPosition(Long startPosition) {
			super(START_POSITION, startPosition);
		}

		public Object clone() {
			StartPosition myClone = (StartPosition) super.clone();
			return myClone;
		}

	}

	/**
	 * nested child class: EndPosition
	 */
	public final static class EndPosition extends BasePairPositionDE {
		public EndPosition(Long endPosition) {
			super(END_POSITION, endPosition);
		}

		public Object clone() {
			EndPosition myClone = (EndPosition) super.clone();
			return myClone;
		}

	}

	/**
	 * Returns the positionType for this BasePairPositionDE obect.
	 * 
	 * @return the positionType for this <code>BasePairPositionDE</code>
	 *         object
	 */
	public String getPositionType() {
		return positionType;
	}

	/**
	 * Sets the value for this <code>BasePairPositionDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof Integer))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((Long) obj);
	}

	/**
	 * Returns the basePairPosition for this BasePairPositionDE obect.
	 * 
	 * @return the basePairPosition for this <code>BasePairPositionDE</code>
	 *         object
	 */
	public Long getValueObject() {
		return (Long) getValue();
	}

	/**
	 * Sets the basePairPosition for this <code>BasePairPositionDE</code>
	 * object
	 * 
	 * @param basePairPosition
	 *            the basePairPosition
	 */
	public void setValueObject(Long basePairPosition) {
		if (basePairPosition != null) {
			this.value = basePairPosition;
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
		BasePairPositionDE myClone = (BasePairPositionDE) super.clone();
		return myClone;
	}
}
