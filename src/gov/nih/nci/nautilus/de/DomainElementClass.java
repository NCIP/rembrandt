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

    public int hashCode() {
        int result = HashCodeUtil.SEED;
        result = HashCodeUtil.hash( result, getName());
        return result;
    }
}
