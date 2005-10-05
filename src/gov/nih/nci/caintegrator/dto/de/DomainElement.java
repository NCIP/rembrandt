package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * DomainElement object, it is a parent class for all data element objects.
 * 
 * @author BhattarR, BauerD
 */
abstract public class DomainElement implements Serializable, Cloneable {

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
	 * the object value, it repesents generic value for data element
	 */
	protected Object value;

	/**
	 * Sets the value for all <code>subclass</code> objects
	 * 
	 * @param object
	 *            the value
	 */
	abstract public void setValue(Object obj) throws Exception;

	/**
	 * Returns the generic value for all subclass obects.
	 * 
	 * @return the generic value for all <code>subclass</code> objects
	 */
	public Object getValue() {
		return value;
	}

	// ****************************************************
	// CONSTRUCTOR(S)
	// ****************************************************

	/**
	 * Initializes a newly created <code>DomainElement</code> object so that
	 * it represents an DomainElement.
	 */
	protected DomainElement(Object value) {
		if (value != null) {
			this.value = value;
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

		DomainElement myClone = null;
		try {
			myClone = (DomainElement) super.clone();
		} catch (CloneNotSupportedException e) {
			/*
			 * THis will never happen!
			 */
		}
		return myClone;
	}

}
