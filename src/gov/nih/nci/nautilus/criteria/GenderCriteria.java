package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.GenderDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates GenderDE criteria.
 * It contains a collection of GenderDE.
 *
 * Dana Zhang
 * Date: August 30, 2004
 * Version 1.0
 */



public class GenderCriteria extends Criteria {

    private Collection genders;
	public GenderCriteria(){}

	// this is to deal with one GenderDE object entry
	public void setGenderDE(GenderDE genderDE){
	  if(genderDE != null){
	     getGenderMembers().add(genderDE);
	     }
	  }

	// this is to deal w/ a collection of GenderDE
	public void setGenderDEColl(Collection multiGenders){
	  if(multiGenders != null){
	     Iterator iter = multiGenders.iterator();
	     while(iter.hasNext()){
	        GenderDE genderde = (GenderDE)iter.next();
		    getGenderMembers().add(genderde);
	      }
	   }
   }

   private Collection getGenderMembers(){
     if(genders == null){
	   genders = new ArrayList();
	   }
	  return  genders;
   }

   public Collection getGenders() {
        return genders;
    }

   public boolean isValid() {
    // find out later to see if we need validate genders
    return true;
    }

}
