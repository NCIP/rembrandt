package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.PathwayDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates Pathway criteria.
 * It contains a collection of PathwayDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class PathwayCriteria extends Criteria{

private Collection pathwayNames;
    public Collection getPathwayNames() {
        return pathwayNames;
    }
    public void setPathwayNames(Collection pathwayNameObjs) {
        for (Iterator iterator = pathwayNameObjs.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof PathwayDE) {
                getPathwayIDsMember().add(obj);
            }
        }
    }

    public void setPathwayName(PathwayDE pathwayName) {
        getPathwayIDsMember().add(pathwayName);
    }
    private Collection getPathwayIDsMember() {
        if (pathwayNames == null)
            pathwayNames = new ArrayList();
        return pathwayNames;
    }

    public PathwayCriteria() {
    }

    public boolean isValid() {
        // TODO: see if we need any validation
        return true;
    }
}
