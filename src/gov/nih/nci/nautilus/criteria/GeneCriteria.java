package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:44:58 PM
 * To change this template use Options | File Templates.
 */
public class GeneCriteria extends Criteria {
    GeneIdentifierDE geneType;

    public boolean validate() {
        return false;
    }
}
