package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SNPIdentifierDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class encapsulates the properties of an caintergator SNPCriteria object.
 * 
 * @author Dana Zhang, BauerD
 */

public class SNPCriteria extends Criteria implements Serializable, Cloneable {
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
	private Collection snpIdentifiers;

	public Collection getIdentifiers() {
		return snpIdentifiers;
	}

	public void setSNPIdentifiers(Collection snpIdentifiersObjs) {
		for (Iterator iterator = snpIdentifiersObjs.iterator(); iterator
				.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof SNPIdentifierDE) {
				getIdentifiersMember().add(obj);
			}
		}
	}

	public void setSNPIdentifier(SNPIdentifierDE snpIdentifier) {
		getIdentifiersMember().add(snpIdentifier);
	}

	private Collection getIdentifiersMember() {
		if (snpIdentifiers == null)
			snpIdentifiers = new ArrayList();
		return snpIdentifiers;
	}

	public SNPCriteria() {
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
		SNPCriteria myClone = null;
		myClone = (SNPCriteria) super.clone();
		if(snpIdentifiers!=null) {
			myClone.snpIdentifiers = new ArrayList();
			for (Iterator i = snpIdentifiers.iterator(); i.hasNext();) {
				myClone.snpIdentifiers.add(((SNPIdentifierDE) i.next()).clone());
			}
		}
		return myClone;
	}
}
