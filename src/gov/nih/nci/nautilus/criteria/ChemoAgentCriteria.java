package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ChemoAgentDE;

import java.io.Serializable;

/**
 * This class encapsulates ChemoAgentDE criteria. It contains a collection of
 * ChemoAgentDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class ChemoAgentCriteria extends Criteria implements Serializable,
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
	private ChemoAgentDE chemoAgentDE;

	public ChemoAgentCriteria() {
	}

	public void setChemoAgentDE(ChemoAgentDE chemoAgentDE) {
		if (chemoAgentDE != null) {
			this.chemoAgentDE = chemoAgentDE;
		}
	}

	public ChemoAgentDE getChemoAgentDE() {
		return chemoAgentDE;
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
		ChemoAgentCriteria myClone = null;
		myClone = (ChemoAgentCriteria) super.clone();
		if(chemoAgentDE!=null) {
			myClone.chemoAgentDE = (ChemoAgentDE) chemoAgentDE.clone();
		}
		return myClone;
	}
}
