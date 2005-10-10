package gov.nih.nci.caintegrator.dto.de;

import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;

import java.io.Serializable;

/**
 * This class encapsulates the properties of an caintergator StatisticTypeDE object.
 * 
 * @author SahniH
 */
public class StatisticTypeDE extends DomainElement implements Serializable, Cloneable {

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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Initializes a newly created <code>StatisticTypeDE</code> object so that it
	 * represents an StatisticTypeDE.
	 */
	public StatisticTypeDE(StatisticalMethodType statisticType) {
		super(statisticType);
	}

	/**
	 * Sets the value for this <code>StatisticTypeDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof String))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((StatisticalMethodType) obj);
	}

	/**
	 * Returns the statisticType for this StatisticTypeDE obect.
	 * 
	 * @return the statisticType for this <code>StatisticTypeDE</code> object
	 */
	public StatisticalMethodType getValueObject() {
		return (StatisticalMethodType) getValue();
	}

	/**
	 * Sets the statisticType for this <code>StatisticTypeDE</code> object
	 * 
	 * @param statisticType
	 *            the statisticType
	 */
	public void setValueObject(StatisticalMethodType statisticType) {
		if (statisticType != null) {
			value = statisticType;
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
		StatisticTypeDE myClone = (StatisticTypeDE) super.clone();
		return myClone;
	}
}
