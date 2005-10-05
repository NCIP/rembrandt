package gov.nih.nci.caintegrator.dto.critieria;

import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author BhattarR, BauerD
 */
public class GeneOntologyCriteria extends Criteria implements Serializable,
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
	private Collection goIdentifiers;

	public Collection getGOIdentifiers() {
		return goIdentifiers;
	}

	public void setGOIdentifiers(Collection goIdentifiersObjs) {
		for (Iterator iterator = goIdentifiers.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof GeneOntologyDE) {
				getGOIdentifierMember().add(obj);
			}
		}
	}

	public void setGOIdentifier(GeneOntologyDE geneOntology) {
		getGOIdentifierMember().add(geneOntology);
	}

	private Collection getGOIdentifierMember() {
		if (goIdentifiers == null)
			goIdentifiers = new ArrayList();
		return goIdentifiers;
	}

	public GeneOntologyCriteria() {
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
		GeneOntologyCriteria myClone = null;
		myClone = (GeneOntologyCriteria) super.clone();
		if(this.goIdentifiers!=null) {
			myClone.goIdentifiers = new ArrayList();
			for (Iterator i = goIdentifiers.iterator(); i.hasNext();) {
				myClone.goIdentifiers.add(((GeneOntologyDE) i.next()).clone());
			}
		}
		return myClone;
	}
}
