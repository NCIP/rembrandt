package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.OccurrenceDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates Occurrence criteria.
 * It contains a collection of OccurrenceDE.
 *
 * Dana Zhang
 * Date: August 30, 2004
 * Version 1.0
 */



public class OccurrenceCriteria extends Criteria {

    private Collection occurrences;
	public OccurrenceCriteria(){}

	// this is to deal with one OccurrenceDE object entry
	public void setOccurrence(OccurrenceDE occurrenceDE){
	  if(occurrenceDE != null){
	     getOccurrenceMembers().add(occurrenceDE);
	     }
	  }

	// this is to deal w/ a collection of OccurrenceDE
	public void setOccurrences(Collection multiOccurrences){
	  if(multiOccurrences != null){
	     Iterator iter = multiOccurrences.iterator();
	     while(iter.hasNext()){
	        OccurrenceDE occurrencede = (OccurrenceDE)iter.next();
		    getOccurrenceMembers().add(occurrencede);
	      }
	   }
   }

   private Collection getOccurrenceMembers(){
     if(occurrences == null){
	   occurrences = new ArrayList();
	   }
	  return  occurrences;
   }

   public Collection getOccurrences() {
        return occurrences;
    }

   public boolean isValid() {
    // find out later to see if we need validate occurence
    return true;
    }

}
