package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.OccurrenceDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author BhattarR, BauerD
 */
public class GeneIDCriteria extends Criteria implements Serializable, Cloneable {
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
	private Collection geneIdentifiers;

	public Collection getGeneIdentifiers() {
		return geneIdentifiers;
	}

	public void setGeneIdentifiers(Collection geneIdentifiers) {
		for (Iterator iterator = geneIdentifiers.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof GeneIdentifierDE) {
				getGeneIdentifiersMember().add(obj);
			}
		}
	}

	public void setGeneIdentifier(GeneIdentifierDE geneIdentifier) {
		// assert(geneIdentifier != null);
		if (geneIdentifier != null) {
			getGeneIdentifiersMember().add(geneIdentifier);
		}
	}

	private Collection getGeneIdentifiersMember() {
		if (geneIdentifiers == null)
			geneIdentifiers = new ArrayList();
		return geneIdentifiers;
	}

	public GeneIDCriteria() {
	}

	public boolean isValid() {
		// We need this finished
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
		GeneIDCriteria myClone = null;
		myClone = (GeneIDCriteria) super.clone();
		myClone.geneIdentifiers = new ArrayList();
		for (Iterator i = geneIdentifiers.iterator(); i.hasNext();) {
			myClone.geneIdentifiers.add(((GeneIdentifierDE) i.next()).clone());
		}
		return myClone;
	}
}