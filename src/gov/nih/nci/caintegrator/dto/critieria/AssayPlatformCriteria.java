package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;

import java.io.Serializable;

/**
 * This class encapsulates AssayPlatform criteria. It contains a collection of
 * AssayPlatformDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class AssayPlatformCriteria extends Criteria implements Serializable,
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
	private AssayPlatformDE assayPlatformDE;

	public AssayPlatformCriteria() {
	}

	/**
	 * @param platformDE
	 */
	public AssayPlatformCriteria(AssayPlatformDE platformDE) {

		setAssayPlatformDE(platformDE);
	}

	public void setAssayPlatformDE(AssayPlatformDE assayPlatformDE) {
		if (assayPlatformDE != null) {
			this.assayPlatformDE = assayPlatformDE;
		}
	}

	public AssayPlatformDE getAssayPlatformDE() {
		return assayPlatformDE;
	}

	public boolean isValid() {
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
		AssayPlatformCriteria myClone = null;
		myClone = (AssayPlatformCriteria) super.clone();
		if(assayPlatformDE!=null) {
			myClone.assayPlatformDE = (AssayPlatformDE) assayPlatformDE.clone();
		}
		return myClone;
	}

}
