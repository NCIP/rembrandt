package gov.nih.nci.nautilus.criteria;

import java.io.Serializable;

import gov.nih.nci.nautilus.de.GenderDE;

/**
 * This class encapsulates GenderDE criteria. It contains a collection of
 * GenderDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class GenderCriteria extends Criteria implements Serializable, Cloneable {
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
	private GenderDE genderDE;

	public GenderCriteria() {
	}

	public void setGenderDE(GenderDE genderDE) {
		if (genderDE != null) {
			this.genderDE = genderDE;
		}
	}

	public GenderDE getGenderDE() {
		return genderDE;
	}

	public boolean isValid() {
		// find out later to see if we need validate genders
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
		GenderCriteria myClone = null;
		myClone = (GenderCriteria) super.clone();
		if(genderDE!=null) {
			myClone.genderDE = (GenderDE) genderDE.clone();
		}
		return myClone;
	}

}
