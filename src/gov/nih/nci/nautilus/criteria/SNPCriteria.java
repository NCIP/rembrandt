package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SNPIdentifierDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates the properties of an caintergator 
 * SNPCriteria object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */

public class SNPCriteria extends Criteria {

    private Collection snpIdentifiers;
    public Collection getIdentifiers() {
        return snpIdentifiers;
    }
    public void setSNPIdentifiers(Collection snpIdentifiersObjs) {
        for (Iterator iterator = snpIdentifiersObjs.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof SNPIdentifierDE) {
                getIdentifiersMember().add(obj);
            }
        }
    }

    public void setSNPIdentifier(SNPIdentifierDE snpIdentifier) {
        getIdentifiersMember().add(snpIdentifier);
    }
    private Collection getIdentifiersMember() {
        if (snpIdentifiers == null)
            snpIdentifiers  = new ArrayList();
        return snpIdentifiers ;
    }
 
   public SNPCriteria(){}   
  
  public boolean isValid(){
     return true;
   } 
}
