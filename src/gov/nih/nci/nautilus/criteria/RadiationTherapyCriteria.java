package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.RadiationTherapyDE;

import java.io.Serializable;

/**
 * This class encapsulates RadiationTherapyDE criteria. It contains a collection
 * of RadiationTherapyDE.
 * 
 * Dana Zhang Date: August 30, 2004 Version 1.0
 */

public class RadiationTherapyCriteria extends Criteria implements Serializable,
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
	private RadiationTherapyDE radiationTherapyDE;

	public RadiationTherapyCriteria() {
	}

	public void setRadiationTherapyDE(RadiationTherapyDE radiationTherapyDE) {
		if (radiationTherapyDE != null) {
			this.radiationTherapyDE = radiationTherapyDE;
		}
	}

	public RadiationTherapyDE getRadiationTherapyDE() {
		return radiationTherapyDE;
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
		RadiationTherapyCriteria myClone = null;
		myClone = (RadiationTherapyCriteria) super.clone();
		if(this.radiationTherapyDE!=null) {
			myClone.radiationTherapyDE = (RadiationTherapyDE) radiationTherapyDE
					.clone();
		}
		return myClone;
	}

}
