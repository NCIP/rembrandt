package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.PathwayDE;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class encapsulates Pathway criteria. It contains a collection of
 * PathwayDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class PathwayCriteria extends Criteria implements Serializable,
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
	private Collection pathwayNames;

	public Collection getPathwayNames() {
		return pathwayNames;
	}

	public void setPathwayNames(Collection pathwayNameObjs) {
		for (Iterator iterator = pathwayNameObjs.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof PathwayDE) {
				getPathwayIDsMember().add(obj);
			}
		}
	}

	public void setPathwayName(PathwayDE pathwayName) {
		getPathwayIDsMember().add(pathwayName);
	}

	private Collection getPathwayIDsMember() {
		if (pathwayNames == null)
			pathwayNames = new ArrayList();
		return pathwayNames;
	}

	public PathwayCriteria() {
	}

	public boolean isValid() {
		// TODO: see if we need any validation
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
		PathwayCriteria myClone = null;
		myClone = (PathwayCriteria) super.clone();
		if(this.pathwayNames!=null) {
			myClone.pathwayNames = new ArrayList();
			for (Iterator i = pathwayNames.iterator(); i.hasNext();) {
				myClone.pathwayNames.add(((PathwayDE) i.next()).clone());
			}
		}
		return myClone;
	}
}
