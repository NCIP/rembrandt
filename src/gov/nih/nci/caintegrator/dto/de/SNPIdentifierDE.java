package gov.nih.nci.caintegrator.dto.de;

import java.io.Serializable;

/**
 * This abstract class encapsulates the properties of an caintergator
 * SNPIdentifierDE object. It contains three child/nested classes:TSC, DBSNP &
 * SNPProbeSet.
 * 
 * 
 * @author Dana Zhang, BauerD
 */

abstract public class SNPIdentifierDE extends DomainElement implements
		Serializable, Cloneable {

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
	 * type of SNP
	 */
	private String SNPType;

	/**
	 * TSC
	 */
	public final static String TSC = "TSC";

	/**
	 * DBSNP
	 */
	public final static String DBSNP = "DBSNP";

	/**
	 * SNPProbeSet
	 */
	public final static String SNP_PROBESET = "SNPProbeSet";

	// ****************************************************
	// CONSTRUCTOR(S)
	// *****************************************************

	/**
	 * private parent constructor utilized in the two nested/childe classes
	 */
	private SNPIdentifierDE(String SNPType, String value) {
		super(value);
		this.SNPType = SNPType;
	}

	/**
	 * nested child class: TSC
	 */
	public final static class TSC extends SNPIdentifierDE {
		public TSC(String TSCId) {
			super(TSC, TSCId);
		}
	}

	/**
	 * nested child class: DBSNP
	 */
	public final static class DBSNP extends SNPIdentifierDE {
		public DBSNP(String DBSNPId) {
			super(DBSNP, DBSNPId);
		}
	}

	/**
	 * nested child class: SNPProbeSet
	 */
	public final static class SNPProbeSet extends SNPIdentifierDE {
		public SNPProbeSet(String SNPProbeSetId) {
			super(SNP_PROBESET, SNPProbeSetId);
		}
	}

	/**
	 * Returns the SNPType for this SNPIdentifierDE obect.
	 * 
	 * @return the SNPType for this <code>SNPIdentifierDE</code> object
	 */
	public String getSNPType() {
		return SNPType;
	}

	/**
	 * Sets the value for this <code>SNPIdentifierDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof String))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((String) obj);
	}

	/**
	 * Returns the SNPPostion for this SNPIdentifierDE obect.
	 * 
	 * @return the SNPPostion for this <code>SNPIdentifierDE</code> object
	 */
	public String getValueObject() {
		return (String) getValue();
	}

	/**
	 * Sets the SNPPostion for this <code>SNPIdentifierDE</code> object
	 * 
	 * @param SNPPostion
	 *            the basePairPosition
	 */
	public void setValueObject(String SNPPosition) {
		if (SNPPosition != null) {
			this.value = SNPPosition;
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
		SNPIdentifierDE myClone = (SNPIdentifierDE) super.clone();
		return myClone;
	}
}
