package gov.nih.nci.nautilus.criteria;

import java.io.Serializable;

import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;

/**
 * @author BhattarR, BauerD
 */
public class RegionCriteria extends Criteria implements Serializable, Cloneable {
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
	private CytobandDE startCytoband;

	private CytobandDE endCytoband;

	private ChromosomeNumberDE chromNumber;

	private BasePairPositionDE.StartPosition start;

	private BasePairPositionDE.EndPosition end;

	private boolean empty = true;

	public CytobandDE getStartCytoband() {
		return startCytoband;
	}

	public void setStartCytoband(CytobandDE startCytoband) {
		this.startCytoband = startCytoband;
	}

	public CytobandDE getEndCytoband() {
		return endCytoband;
	}

	public void setEndCytoband(CytobandDE endCytoband) {
		this.endCytoband = endCytoband;
	}

	public boolean isValid() {
		// TODO: DO we need to add any more validation here?

		/*
		 * if cytoband is specified, then chromosomeNumber, start and end
		 * positions should not be specified
		 */
		if (getCytoband() != null && (end != null || start != null))
			return false;

		// if specified, both start & end posistions together should be
		// specified
		if ((end == null && start != null) || (end != null && start == null))
			return false;
		else {
			// Chromosome Number is not null
			if (chromNumber == null)
				return false;

			// Start Position should be less than End Position
			if (end.getValueObject().intValue() < start.getValueObject()
					.intValue())
				return false;
		}
		return true;
	}

	public CytobandDE getCytoband() {
		return getStartCytoband();
	}

	public void setCytoband(CytobandDE cytoband) {
		// assert(cytoband != null);
		if (cytoband != null) {
			setStartCytoband(cytoband);
		}
	}

	public BasePairPositionDE.StartPosition getStart() {
		return start;
	}

	public void setStart(BasePairPositionDE.StartPosition start) {
		this.start = start;
	}

	public BasePairPositionDE.EndPosition getEnd() {
		return end;
	}

	public void setEnd(BasePairPositionDE.EndPosition end) {
		this.end = end;
	}

	public ChromosomeNumberDE getChromNumber() {
		return chromNumber;
	}

	public void setChromNumber(ChromosomeNumberDE chromNumber) {
		this.chromNumber = chromNumber;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		RegionCriteria myClone = null;
		myClone = (RegionCriteria) super.clone();
		myClone.chromNumber = (ChromosomeNumberDE) chromNumber.clone();
		myClone.end = (BasePairPositionDE.EndPosition) end.clone();
		myClone.start = (BasePairPositionDE.StartPosition) start.clone();
		myClone.endCytoband = (CytobandDE) endCytoband.clone();
		myClone.startCytoband = (CytobandDE) startCytoband.clone();
		return myClone;
	}
}
