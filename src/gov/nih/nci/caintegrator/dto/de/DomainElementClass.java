package gov.nih.nci.caintegrator.dto.de;

import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.HashCodeUtil;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author BhattarR, BauerD
 */
abstract public class DomainElementClass implements Serializable, Cloneable {
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
	public abstract String getName();

	public String getLabel() {
		Properties props = ApplicationContext.getLabelProperties();
		String key = getName().substring(getName().lastIndexOf(".") + 1);
		String value = (String) props.get(key);
		return value;
	}

	public final static DomainElementClass PROBESET = new DomainElementClass() {
		public final String getName() {
			return ProbeSetDE.class.getName();
		}
	};

	public final static DomainElementClass IMAGE_CLONE = new DomainElementClass() {
		public final String getName() {
			return CloneIdentifierDE.IMAGEClone.class.getName();
		}
	};

	public final static DomainElementClass BAC_CLONE = new DomainElementClass() {
		public final String getName() {
			return CloneIdentifierDE.BACClone.class.getName();
		}
	};

	public final static DomainElementClass EXPRFOLDCHANGE = new DomainElementClass() {
		public final String getName() {
			return ExprFoldChangeDE.class.getName();
		}
	};

	public final static DomainElementClass LOCUS_LINK = new DomainElementClass() {
		public final String getName() {
			return GeneIdentifierDE.LocusLink.class.getName();
		}
	};

	public final static DomainElementClass GENBANK_ACCESSION_NUMBER = new DomainElementClass() {
		public final String getName() {
			return GeneIdentifierDE.GenBankAccessionNumber.class.getName();
		}
	};

	public final static DomainElementClass CHROMOSOME_NUMBER = new DomainElementClass() {
		public final String getName() {
			return ChromosomeNumberDE.class.getName();
		}
	};

	public final static DomainElementClass PATHWAY = new DomainElementClass() {
		public final String getName() {
			return PathwayDE.class.getName();
		}
	};

	public final static DomainElementClass DISEASE_NAME = new DomainElementClass() {
		public final String getName() {
			return DiseaseNameDE.class.getName();
		}
	};

	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, getName());
		return result;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		DomainElementClass myClone = null;
		try {
			myClone = (DomainElementClass) super.clone();
		} catch (CloneNotSupportedException e) {
			// This will never happen!
		}
		return myClone;
	}
}
