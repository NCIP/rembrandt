package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

//caintergator classes

/**
 * This class encapsulates the properties of an caintergator ChemoAgentDE
 * object.
 * 
 * @author Dana Zhang, BauerD
 */
public class ChemoAgentDE extends DomainElement implements Serializable,
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
	 * Initializes a newly created <code>ChemoAgentDE</code> object so that it
	 * represents an ChemoAgentDE.
	 */
	public ChemoAgentDE(String chemoAgentName) {
		super(chemoAgentName);
	}

	/**
	 * Sets the value for this <code>ChemoAgentDE</code> object
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
	 * Returns the chemoAgentName for this ChemoAgentDE obect.
	 * 
	 * @return the chemoAgentName for this <code>ChemoAgentDE</code> object
	 */
	public String getValueObject() {
		return (String) getValue();
	}

	/**
	 * Sets the chemoAgentName for this <code>ChemoAgentDE</code> object
	 * 
	 * @param chemoAgentName
	 *            the chemoAgentName
	 */
	public void setValueObject(String chemoAgentName) {
		if (chemoAgentName != null) {
			value = chemoAgentName;
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
		ChemoAgentDE myClone = (ChemoAgentDE) super.clone();
		return myClone;
	}
}
