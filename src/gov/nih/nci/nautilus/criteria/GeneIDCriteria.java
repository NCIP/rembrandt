package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;
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
public class GeneIDCriteria extends Criteria {
    private Collection geneIdentifiers;
    public Collection getGeneIdentifiers() {
        return geneIdentifiers;
    }
    public void setGeneIdentifiers(Collection geneIdentifiers) {
        for (Iterator iterator = geneIdentifiers.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof GeneIdentifierDE ) {
                getGeneIdentifiersMember().add(obj);
            }
        }
    }

    public void setGeneIdentifier(GeneIdentifierDE geneIdentifier) {
       // assert(geneIdentifier != null);
	   if(geneIdentifier != null){
        getGeneIdentifiersMember().add(geneIdentifier);
		}
    }
    private Collection getGeneIdentifiersMember() {
        if (geneIdentifiers == null)
            geneIdentifiers = new ArrayList();
        return geneIdentifiers;
    }

    public GeneIDCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any validation on GeneSymbols/LocusLinkID/GenBank etc
        return true;
    }
}
