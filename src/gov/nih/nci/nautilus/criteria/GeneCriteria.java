package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:44:58 PM
 * To change this template use Options | File Templates.
 */
public class GeneCriteria extends Criteria {
    private Collection geneIdentifiers;

    public Collection getGeneIdentifiers() {
        return geneIdentifiers;
    }

    public void setGeneIdentifiers(Collection geneIdentifiers) {
        this.geneIdentifiers.addAll(geneIdentifiers);
    }

    public void setGeneIdentifier(GeneIdentifierDE geneIdentifier) {
        geneIdentifiers.add(geneIdentifiers);
    }

    public GeneCriteria() {
        geneIdentifiers = new ArrayList();
    }

    public boolean validate() {
        return false;
    }
}
