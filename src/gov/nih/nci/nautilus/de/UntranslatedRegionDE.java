package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * UntranslatedRegionDE object. It contains two child/nested classes:5UTR, 3UTR.
 * 
 * @author Dana Zhang, BauerD
 */
abstract public class UntranslatedRegionDE extends DomainElement implements
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
	 * type of UTRID
	 */
	private String UTRIDType;

	/**
	 * 5UTR
	 */
	public static final String UTR5 = "UTR_5";

	/**
	 * 3UTR
	 */
	public static final String UTR3 = "UTR_3";

	// ****************************************************
	// CONSTRUCTOR(S)
	// *****************************************************

	/**
	 * private parent constructor utilized in the two nested/childe classes
	 */
	private UntranslatedRegionDE(String UTRIDType, Boolean value) {
		super(value);
		this.UTRIDType = UTRIDType;
	}

	/**
	 * nested child class: UTR_5
	 */
	public final static class UTR_5 extends UntranslatedRegionDE {
		public UTR_5(Boolean UTR_5) {
			super(UTR5, UTR_5);
		}
	}

	/**
	 * nested child class: UTR_3
	 */
	public final static class UTR_3 extends UntranslatedRegionDE {
		public UTR_3(Boolean UTR_3) {
			super(UTR3, UTR_3);
		}
	}

	/**
	 * Returns the UTRIDType for this UntranslatedRegionDE obect.
	 * 
	 * @return the UTRIDType for this <code>UntranslatedRegionDE</code> object
	 */
	public String getUTRIDType() {
		return UTRIDType;
	}

	/**
	 * Sets the value for this <code>UntranslatedRegionDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof Boolean))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((Boolean) obj);
	}

	/**
	 * Returns the UTRIDType for this UntranslatedRegionDE obect.
	 * 
	 * @return the UTRIDType for this <code>UntranslatedRegionDE</code> object
	 */
	public String getValueObject() {
		return (String) getValue();
	}

	/**
	 * Sets the utr for this <code>UntranslatedRegionDE</code> object
	 * 
	 * @param utr
	 *            the cloneName
	 */

	public void setValueObject(Boolean utr) {
		this.value = utr;
	}

	/**
	 * Sets the UTRID for this <code>UntranslatedRegionDE</code> object
	 * 
	 * @param UTRID
	 *            the regulation
	 */
	public void setURTID(String UTRID) {
		if (UTRID != null) {
			value = UTRID;
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
		UntranslatedRegionDE myClone = (UntranslatedRegionDE) super.clone();
		return myClone;
	}

}
