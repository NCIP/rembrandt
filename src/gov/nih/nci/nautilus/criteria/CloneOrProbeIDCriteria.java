package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;

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
public class CloneOrProbeIDCriteria extends Criteria {
    private Collection cloneIdentifiers;
    public Collection getCloneIdentifiers() {
        return cloneIdentifiers;
    }
    public void setCloneIdentifiers(Collection cloneIdentifiersObjs) {
        for (Iterator iterator = cloneIdentifiersObjs.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof CloneIdentifierDE) {
                getCloneIdentifiers().add(obj);
            }
        }
    }

    public void setGeneIdentifier(CloneIdentifierDE cloneIdentifier) {
        getGeneIdentifiersMember().add(cloneIdentifier);
    }
    private Collection getGeneIdentifiersMember() {
        if (cloneIdentifiers == null)
            cloneIdentifiers  = new ArrayList();
        return cloneIdentifiers ;
    }

    public CloneOrProbeIDCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any validation
        return true;
    }
}
