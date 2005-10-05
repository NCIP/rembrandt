package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * CloneIdentifierDE object. It contains two child/nested classes:BACClone,
 * IMAGEClone
 * 
 * @author Dana Zhang, BauerD
 */
abstract public class CloneIdentifierDE extends DomainElement implements
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
	 * type of cloneID
	 */
	private String cloneIDType;

	public static final String BAC_CLONE = "BACClone";

	public static final String IMAGE_CLONE = "IMAGEClone";

	public static final String PROBE_SET = "ProbesetClone";

	/**
	 * private parent constructor utilized in the two nested/childe classes
	 */
	private CloneIdentifierDE(String cloneIDType, String value) {
		super(value);
		this.cloneIDType = cloneIDType;
	}

	/**
	 * nested child class: BACClone
	 */
	public final static class BACClone extends CloneIdentifierDE {
		public BACClone(String bacClineID) {
			super(BAC_CLONE, bacClineID);
		}

	}

	/**
	 * nested child class: IMAGEClone
	 */
	public final static class IMAGEClone extends CloneIdentifierDE {
		public IMAGEClone(String imageCloneID) {
			super(IMAGE_CLONE, imageCloneID);
		}

		public Object clone() {
			IMAGEClone myClone = (IMAGEClone) super.clone();
			return myClone;
		}
	}

	public final static class ProbesetID extends CloneIdentifierDE {
		public ProbesetID(String imageCloneID) {
			super(PROBE_SET, imageCloneID);

		}

		public Object clone() {
			ProbesetID myClone = (ProbesetID) super.clone();
			return myClone;
		}

	}

	/**
	 * Returns the cloneIDType for this CloneIdentifierDE obect.
	 * 
	 * @return the cloneIDType for this <code>CloneIdentifierDE</code> object
	 */
	public String getCloneIDType() {
		return cloneIDType;
	}

	/**
	 * Sets the value for this <code>CloneIdentifierDE</code> object
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
	 * Returns the cloneIDType for this CloneIdentifierDE obect.
	 * 
	 * @return the cloneIDType for this <code>CloneIdentifierDE</code> object
	 */
	public String getValueObject() {
		return (String) getValue();
	}

	/**
	 * Sets the cloneName for this <code>GeneIdentifierDE</code> object
	 * 
	 * @param cloneName
	 *            the cloneName
	 */

	public void setValueObject(String cloneName) {
		this.value = cloneName;
	}

	/**
	 * Sets the cloneID for this <code>CloneIdentifierDE</code> object
	 * 
	 * @param cloneID
	 *            the cloneID
	 */
	public void setCloneID(String cloneID) {
		if (cloneID != null) {
			value = cloneID;
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
		CloneIdentifierDE myClone = (CloneIdentifierDE) super.clone();
		return myClone;
	}
}
