package gov.nih.nci.caintegrator.dto.critieria;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * @author BhattarR, BauerD
 */
abstract public class Criteria implements Serializable, Cloneable {
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
	abstract public boolean isValid();

	// TODO: The followig method checks if a given Criteria is empty
	public boolean isEmpty() {
		try {

			String currObjectName = this.getClass().getName();
			Class currClass = Class.forName(currObjectName);
			Method[] allPublicmethods = currClass.getMethods();

			for (int i = 0; i < allPublicmethods.length; i++) {
				Method currMethod = allPublicmethods[i];
				String currMethodString = currMethod.getName();
				if ((currMethodString.toUpperCase().startsWith("GET"))
						&& (currMethod.getModifiers() == Modifier.PUBLIC)
						&& (!currMethodString.equalsIgnoreCase("getclass"))) {
					Object thisObj = currMethod.invoke(this, null);

					if (thisObj != null) {
						if (Collection.class.isInstance(thisObj)) {
							Collection thisCollection = (Collection) thisObj;
							if (!thisCollection.isEmpty()) {
								return false;
							}
						} else {
							return false;
						}
					}
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
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
		Criteria myClone = null;
		try {
			myClone = (Criteria) super.clone();
		} catch (CloneNotSupportedException cns) {
			/*
			 * This is meaningless as it will still perform the shallow copy,
			 * and then let you know that the object did not implement the
			 * Cloneable inteface. Kind of a stupid implementation if you ask
			 * me...
			 */
		}
		return myClone;
	}

}
