package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SNPIdentifierDE;
import gov.nih.nci.nautilus.de.SampleIDDE;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ram, BauerD
 */
public class SampleCriteria extends Criteria implements Serializable, Cloneable {
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
	private Collection sampleIDs;

	public Collection getSampleIDs() {
		return sampleIDs;
	}

	public void setSampleIDs(Collection sampleIDValues)
			throws InvalidParameterException {
		for (Iterator iterator = sampleIDValues.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof SampleIDDE) {
				getSampleIDDEsMember().add(obj);
			} else {
				throw new InvalidParameterException(
						"Collection must be of SampleIDDEs");
			}
		}
	}

	private Collection getSampleIDDEsMember() {
		if (sampleIDs == null)
			sampleIDs = new ArrayList();
		return sampleIDs;
	}

	public void setSampleID(SampleIDDE sampleID) {
		if (sampleID != null) {
			getSampleIDDEsMember().add(sampleID);
		}
	}

	public SampleCriteria() {
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
		SampleCriteria myClone = null;
		myClone = (SampleCriteria) super.clone();
		if(sampleIDs!=null) {
			myClone.sampleIDs = new ArrayList();
			for (Iterator i = sampleIDs.iterator(); i.hasNext();) {
				myClone.sampleIDs.add(((SampleIDDE) i.next()).clone());
			}
		}
		return myClone;
	}
}
