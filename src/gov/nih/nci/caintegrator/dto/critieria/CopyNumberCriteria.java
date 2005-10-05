package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.CopyNumberDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Dana Zhang, BauerD
 */

public class CopyNumberCriteria extends Criteria implements Serializable,
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
	private Collection copyNumbers;

	public CopyNumberCriteria() {
	}

	// this is to deal with one copyNumberDE object entry
	public void setCopyNumber(CopyNumberDE copyNumberDE) {
		if (copyNumberDE != null) {
			getCopyNumberMembers().add(copyNumberDE);
		}
	}

	// this is to deal w/ a collection of copyNumberDE
	public void setCopyNumbers(Collection multiCopyNumbers) {
		for (Iterator iterator = multiCopyNumbers.iterator(); iterator
				.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof CopyNumberDE) {
				getCopyNumberMembers().add(obj);
			}
		}
	}

	private Collection getCopyNumberMembers() {
		if (copyNumbers == null) {
			copyNumbers = new ArrayList();
		}
		return copyNumbers;
	}

	public Collection getCopyNummbers() {
		return copyNumbers;
	}

	public boolean isValid() {

		if (copyNumbers != null && !copyNumbers.isEmpty()) {
			Iterator iter = copyNumbers.iterator();
			while (iter.hasNext()) {
				CopyNumberDE copyNumberde = (CopyNumberDE) iter.next();
				if (copyNumberde.getValueObject() != null) {
					Float copyNumVal = copyNumberde.getValueObject();
					if (!copyNumVal.isNaN() && copyNumVal.floatValue() > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		CopyNumberCriteria myClone = null;
		myClone = (CopyNumberCriteria) super.clone();
		if(copyNumbers!=null) {
			myClone.copyNumbers = new ArrayList();
			for (Iterator i = copyNumbers.iterator(); i.hasNext();) {
				myClone.copyNumbers.add(((CopyNumberDE) i.next()).clone());
			}
		}
		return myClone;
	}

}
