package gov.nih.nci.nautilus.de;

import java.io.Serializable;

/**
 * @author SahniH, BauerD
 */
public class DatumDE extends DomainElement implements Serializable, Cloneable {
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
	/**
	 * type of datum
	 */
	private String datumType;

	/**
	 * Ration's PValue
	 */
	public final static String FOLD_CHANGE_RATIO_PVAL = "GroupRatioPValue";

	/**
	 * FoldChangeRatio
	 */
	public final static String FOLD_CHANGE_RATIO = "FoldChangeRatio";

	/**
	 * FoldChangeSampleIntensity
	 */
	public final static String FOLD_CHANGE_SAMPLE_INTENSITY = "FoldChangeSampleIntensity";

	/**
	 * FoldChangeNormalIntensity
	 */
	public final static String FOLD_CHANGE_NORMAL_INTENSITY = "FoldChangeNormalIntensity";

	/**
	 * CopyNumber
	 */
	public final static String COPY_NUMBER = "CopyNumber";

	/**
	 * CopyNumber
	 */
	public final static String COPY_NUMBER_CHANNEL_RATIO = "CopyNumberChannelRatio";

	/**
	 * CopyNumber
	 */
	public final static String COPY_NUMBER_RATIO_PVAL = "CopyNumberRatioPval";

	/**
	 * CopyNumber
	 */
	public final static String COPY_NUMBER_LOH = "CopyNumberLOH";

	/**
	 * CLONE_ID
	 */
	public final static String CLONE_ID = "CloneID";

	/**
	 * PROBESET_ID
	 */
	public final static String PROBESET_ID = "ProbeSetID";

	/**
	 * AgeGroup
	 */
	public final static String AGE_GROUP = "AgeGroup";

	/**
	 * SurvivalLengthRange
	 */
	public final static String SURVIVAL_LENGTH_RANGE = "SurvivalLengthRange";

	/**
	 * SurvivalLength
	 */
	public final static String SURVIVAL_LENGTH = "SurvivalLength";

	/**
	 * Censor
	 */
	public final static String CENSOR = "Censor";

	/**
	 * Censor
	 */
	// ****************************************************
	// CONSTRUCTOR(S)
	// ****************************************************
	/**
	 * Initializes a newly created <code>DatumDE</code> object so that it
	 * represents an AlleleFrequencyDE.
	 */
	public DatumDE(String datumType, Object value) {
		super(value);
		this.datumType = datumType;
	}

	/**
	 * Initializes a newly created <code>DatumDE</code> object so that it
	 * represents an AlleleFrequencyDE.
	 */

	public String getType() {
		return datumType;
	}

	/*
	 * @see gov.nih.nci.nautilus.de.DomainElement#setValue(java.lang.Object)
	 */
	public void setValue(Object obj) throws Exception {
		if (obj == null) {
			throw new Exception(
					"Could not set the value.  Parameter is a null object");
		} else {
			value = obj;
		}
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		DatumDE myClone = (DatumDE) super.clone();
		return myClone;
	}
}
