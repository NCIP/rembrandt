package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.SurgeryTypeDE;

import java.io.Serializable;

/**
 * This class encapsulates SurgeryTypeDE criteria. It contains a collection of
 * SurgeryTypeDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class SurgeryTypeCriteria extends Criteria implements Serializable,
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
	private SurgeryTypeDE surgeryTypeDE;

	public SurgeryTypeCriteria() {
	}

	public void setSurgeryTypeDE(SurgeryTypeDE surgeryTypeDE) {
		if (surgeryTypeDE != null) {
			this.surgeryTypeDE = surgeryTypeDE;
		}
	}

	public SurgeryTypeDE getSurgeryTypeDE() {
		return surgeryTypeDE;
	}

	public boolean isValid() {
		// find out later to see if we need validate SurgeryTypes
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
		SurgeryTypeCriteria myClone = null;
		myClone = (SurgeryTypeCriteria) super.clone();
		if(this.surgeryTypeDE!=null) {
			myClone.surgeryTypeDE = (SurgeryTypeDE) surgeryTypeDE.clone();
		}
		return myClone;
	}
}
