package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.AlleleFrequencyDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates the properties of an caintergator 
 * AlleleFrequencyCriteria object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */

public class AlleleFrequencyCriteria extends Criteria {
   private AlleleFrequencyDE alleleFrequencyDE;
   public AlleleFrequencyCriteria(){}
   
   public void setAlleleFrequencyDE(AlleleFrequencyDE alleleFrequencyDE){
      if(alleleFrequencyDE != null){
          this.alleleFrequencyDE = alleleFrequencyDE;
		 }
	 }
   public AlleleFrequencyDE getAlleleFrequencyDE(){
     return alleleFrequencyDE;
	}	 
  public boolean isValid(){
     return true;
   } 
}
