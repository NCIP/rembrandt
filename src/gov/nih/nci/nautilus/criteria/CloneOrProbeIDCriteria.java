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
    private Collection identifiers;
    public Collection getIdentifiers() {
        return identifiers;
    }
    public void setIdentifiers(Collection cloneIdentifiersObjs) {
        for (Iterator iterator = cloneIdentifiersObjs.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof CloneIdentifierDE) {
                getIdentifiersMember().add(obj);
            }
        }
    }

    public void setCloneIdentifier(CloneIdentifierDE cloneIdentifier) {
        getIdentifiersMember().add(cloneIdentifier);
    }
    private Collection getIdentifiersMember() {
        if (identifiers == null)
            identifiers  = new ArrayList();
        return identifiers ;
    }



    public CloneOrProbeIDCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any validation
        return true;
    }
}
