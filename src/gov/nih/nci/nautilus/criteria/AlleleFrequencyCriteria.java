package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.AlleleFrequencyDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class AlleleFrequencyCriteria extends Criteria {

    private Collection alleleFrequencies;
	public AlleleFrequencyCriteria(){}
	
	// this is to deal with one AlleleFrequencyDE object entry
	public void setAlleleFrequency(AlleleFrequencyDE alleleFrequencyDE){
	  if(alleleFrequencyDE != null){
	     getAlleleFrequencyMembers().add(alleleFrequencyDE);	  
	     }	  
	  }
	
	// this is to deal w/ a collection of alleleFrequencyDE
	public void setAlleleFrequencies(Collection multiAlleleFrequencies){
	  if(multiAlleleFrequencies != null){
	     Iterator iter = multiAlleleFrequencies.iterator();
	     while(iter.hasNext()){
	        AlleleFrequencyDE alleleFrequencyde = (AlleleFrequencyDE)iter.next();		
		    getAlleleFrequencyMembers().add(alleleFrequencyde);		    
	      }	    
	   } 
   }
	
   private Collection getAlleleFrequencyMembers(){
     if(alleleFrequencies == null){
	   alleleFrequencies = new ArrayList();
	   }
	  return  alleleFrequencies;
   }
   
   public Collection getAlleleFrequencies() {
        return alleleFrequencies;
    }
 
   public boolean isValid() {    
       return true;
    }
	
}
