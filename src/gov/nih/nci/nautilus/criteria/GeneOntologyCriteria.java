package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.GeneOntologyDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:44:58 PM
 * To change this template use Options | File Templates.
 */
public class GeneOntologyCriteria extends Criteria {
    private Collection goIdentifiers;
    public Collection getGOIdentifiers() {
        return goIdentifiers;
    }
    public void setGOIdentifiers(Collection goIdentifiersObjs) {
        for (Iterator iterator = goIdentifiers.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof GeneOntologyDE) {
                getGOIdentifierMember().add(obj);
            }
        }
    }

    public void setGeneIdentifier(GeneIdentifierDE geneIdentifier) {
        getGOIdentifierMember().add(geneIdentifier);
    }
    private Collection getGOIdentifierMember() {
        if (goIdentifiers == null)
            goIdentifiers = new ArrayList();
        return goIdentifiers;
    }

    public GeneOntologyCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any validation
        return true;
    }
}
