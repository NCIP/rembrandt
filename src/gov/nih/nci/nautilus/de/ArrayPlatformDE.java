package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * This class encapsulates the properties of an caintergator ArrayPlatformDE
 * object.
 * 
 * @author Dana Zhang, BauerD
 */
public class ArrayPlatformDE extends DomainElement implements Serializable,
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

	/**
	 * Initializes a newly created <code>ArrayPlatformDE</code> object so that
	 * it represents an ArrayPlatformDE.
	 */
	public ArrayPlatformDE(String arrayName) {
		super(arrayName);
	}

	/**
	 * Sets the value for this <code>ArrayPlatformDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof String))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((String) obj);
	}

	/**
	 * Returns the arrayName for this ArrayPlatformDE obect.
	 * 
	 * @return the arrayName for this <code>ArrayPlatformDE</code> object
	 */
	public String getValueObject() {
		return (String) getValue();
	}

	/**
	 * Sets the arrayName for this <code>ArrayPlatformDE</code> object
	 * 
	 * @param arrayName
	 *            the arrayName
	 */
	public void setValueObject(String arrayName) {
		if (arrayName != null) {
			value = arrayName;
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
		ArrayPlatformDE myClone = (ArrayPlatformDE) super.clone();
		return myClone;
	}
}
