package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.OccurrenceDE;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class encapsulates Occurrence criteria. It contains a collection of
 * OccurrenceDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class OccurrenceCriteria extends Criteria implements Serializable,
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
	private Collection occurrences;

	public OccurrenceCriteria() {
	}

	// this is to deal with one OccurrenceDE object entry
	public void setOccurrence(OccurrenceDE occurrenceDE) {
		if (occurrenceDE != null) {
			getOccurrenceMembers().add(occurrenceDE);
		}
	}

	// this is to deal w/ a collection of OccurrenceDE
	public void setOccurrences(Collection multiOccurrences) {
		if (multiOccurrences != null) {
			Iterator iter = multiOccurrences.iterator();
			while (iter.hasNext()) {
				OccurrenceDE occurrencede = (OccurrenceDE) iter.next();
				getOccurrenceMembers().add(occurrencede);
			}
		}
	}

	private Collection getOccurrenceMembers() {
		if (occurrences == null) {
			occurrences = new ArrayList();
		}
		return occurrences;
	}

	public Collection getOccurrences() {
		return occurrences;
	}

	public boolean isValid() {
		// find out later to see if we need validate occurence
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
		OccurrenceCriteria myClone = null;
		myClone = (OccurrenceCriteria) super.clone();
		if(this.occurrences!=null) {
		myClone.occurrences = new ArrayList();
			for (Iterator i = occurrences.iterator(); i.hasNext();) {
				myClone.occurrences.add(((OccurrenceDE) i.next()).clone());
			}
		}
		return myClone;
	}

}
