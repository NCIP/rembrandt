package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.UntranslatedRegionDE;

import java.io.Serializable;

/**
 * This class encapsulates UntranslatedRegion criteria. It contains a collection
 * of UntranslatedRegionDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class UntranslatedRegionCriteria extends Criteria implements
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
	private UntranslatedRegionDE.UTR_5 utr_5;

	private UntranslatedRegionDE.UTR_3 utr_3;

	public UntranslatedRegionCriteria() {
	}

	public void setUTR_5(UntranslatedRegionDE.UTR_5 utr_5) {
		if (utr_5 != null) {
			this.utr_5 = utr_5;
		}
	}

	public void setUTR_3(UntranslatedRegionDE.UTR_3 utr_3) {
		if (utr_3 != null) {
			this.utr_3 = utr_3;
		}
	}

	public UntranslatedRegionDE.UTR_5 getUTR_5() {
		return utr_5;
	}

	public UntranslatedRegionDE.UTR_3 getUTR_3() {
		return utr_3;
	}

	public boolean isValid() {
		// TODO: see if we need any validation on untranslatedRegion
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
		UntranslatedRegionCriteria myClone = (UntranslatedRegionCriteria) super
				.clone();
		return myClone;
	}
}
