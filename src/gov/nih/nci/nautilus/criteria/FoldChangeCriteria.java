package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.OccurrenceDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author BhattarR, BauerD
 */
public class FoldChangeCriteria extends Criteria implements Serializable,
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
	private Collection foldChangeObjects;

	public Collection getFoldChangeObjects() {
		return foldChangeObjects;
	}

	public void setFoldChangeObjects(Collection expFoldChangeObjeccts) {
		for (Iterator iterator = expFoldChangeObjeccts.iterator(); iterator
				.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof ExprFoldChangeDE) {
				getFoldChangeObjectsMember().add(obj);
			}
		}
	}

	public void setFoldChangeObject(ExprFoldChangeDE foldChangeObj) {
		getFoldChangeObjectsMember().add(foldChangeObj);
	}

	private Collection getFoldChangeObjectsMember() {
		if (foldChangeObjects == null)
			foldChangeObjects = new ArrayList();
		return foldChangeObjects;
	}

	public FoldChangeCriteria() {
	}

	public boolean isValid() {
		// TODO: see if we need any upper/limit validations
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
		FoldChangeCriteria myClone = null;
		myClone = (FoldChangeCriteria) super.clone();
		if(foldChangeObjects!=null) {
			myClone.foldChangeObjects = new ArrayList();
			for (Iterator i = foldChangeObjects.iterator(); i.hasNext();) {
				myClone.foldChangeObjects
						.add(((ExprFoldChangeDE) i.next()).clone());
			}
		}
		return myClone;
	}
}
