package gov.nih.nci.nautilus.criteria;

import java.io.Serializable;

/**
 * @author BhattarR, BauerD
 */
public class AllGenesCriteria extends Criteria implements Serializable, Cloneable{
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
	 * @param allGenes
	 */
    public AllGenesCriteria(boolean allGenes) {
        super();
        setAllGenes(allGenes);
    }
    public boolean isAllGenes() {
        return allGenes;
    }

    public void setAllGenes(boolean allGenes) {
        this.allGenes = allGenes;
       }

    boolean allGenes = false;

    public boolean isValid() {
        return true;
    }

    // TODO: The followig method checks if a given Criteria is empty
    public boolean isEmpty() {
        if (allGenes)
           return false;
        else
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
       	AllGenesCriteria myClone = (AllGenesCriteria)super.clone();
     	return myClone;
      }
}
