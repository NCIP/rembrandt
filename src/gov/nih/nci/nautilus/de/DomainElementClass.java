package gov.nih.nci.nautilus.de;

//caBIO classes
import gov.nih.nci.nautilus.util.HashCodeUtil;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 5, 2004
 * Time: 6:33:52 PM
 * To change this template use Options | File Templates.
 */
abstract public class DomainElementClass {
    public abstract String getName();

   public final  static DomainElementClass PROBESET = new DomainElementClass() {
        public final String getName() {
            return ProbeSetDE.class.getName();
        }
   };

   public final  static DomainElementClass IMAGE_CLONE = new DomainElementClass() {
    public final String getName() {
        return CloneIdentifierDE.IMAGEClone.class.getName();
    }
};
   public final  static DomainElementClass BAC_CLONE = new DomainElementClass() {
    public final String getName() {
        return CloneIdentifierDE.BACClone.class.getName();
    }
};
   public final  static DomainElementClass EXPRFOLDCHANGE = new DomainElementClass() {
        public final String getName() {
            return ExprFoldChangeDE.class.getName();
        }
   };

   public final  static DomainElementClass LOCUS_LINK = new DomainElementClass() {
        public final String getName() {
            return GeneIdentifierDE.LocusLink.class.getName();
        }
   };

   public final  static DomainElementClass GENBANK_ACCESSION_NUMBER = new DomainElementClass() {
        public final String getName() {
            return GeneIdentifierDE.GenBankAccessionNumber.class.getName();
        }
   };

    public final  static DomainElementClass CHROMOSOME_NUMBER = new DomainElementClass() {
        public final String getName() {
            return ChromosomeNumberDE.class.getName();
        }
    };

    public final  static DomainElementClass PATHWAY = new DomainElementClass() {
        public final String getName() {
            return PathwayDE.class.getName();
        }
    };

    public final  static DomainElementClass DISEASE_NAME = new DomainElementClass() {
        public final String getName() {
            return DiseaseNameDE.class.getName();
        }
    };

    public int hashCode() {
        int result = HashCodeUtil.SEED;
        result = HashCodeUtil.hash( result, getName());
        return result;
    }
}
