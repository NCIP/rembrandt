package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;

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
public class FoldChangeCriteria extends Criteria {
    private Collection foldChangeObjects;
    public Collection getFoldChangeObjects() {
        return foldChangeObjects;
    }
    public void setFoldChangeObjects(Collection expFoldChangeObjeccts) {
        for (Iterator iterator = expFoldChangeObjeccts.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof ExprFoldChangeDE ) {
                getFoldChangeObjectsMember().add(obj);
            }
        }
    }

    public void setFoldChangeObject(ExprFoldChangeDE foldChangeObj) {
        getFoldChangeObjectsMember().add(foldChangeObj);
    }
    private Collection getFoldChangeObjectsMember() {
        if (foldChangeObjects == null) foldChangeObjects = new ArrayList();
        return foldChangeObjects;
    }

    public FoldChangeCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any upper/limit validations
        return true;
    }
}
