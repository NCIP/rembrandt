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

	 private GenderDE genderDE;
	 public GenderCriteria(){}

	 public void setGenderDE(GenderDE genderDE){
		  if(genderDE != null){
		    this.genderDE = genderDE;
		   }
		 }

	 public GenderDE getGenderDE(){
		 return genderDE;
		 }



   public boolean isValid() {
    // find out later to see if we need validate genders
    return true;
    }

}
