package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.CopyNumberDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class CopyNumberCriteria extends Criteria {

    private Collection copyNumbers;
	public CopyNumberCriteria(){}
	
	// this is to deal with one copyNumberDE object entry
	public void setCopyNumber(CopyNumberDE copyNumberDE){
	  if(copyNumberDE != null){
	     getCopyNumberMembers().add(copyNumberDE);	  
	  }
	}
	
	// this is to deal w/ a collection of copyNumberDE
	public void setCopyNumbers(Collection multiCopyNumbers){
       for (Iterator iterator = multiCopyNumbers.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof CopyNumberDE ) {
                getCopyNumberMembers().add(obj);
            }
        }
   }
	
   private Collection getCopyNumberMembers(){
     if(copyNumbers == null){
	   copyNumbers = new ArrayList();
	   }
	  return  copyNumbers;
   }
   
   public Collection getCopyNummbers() {
        return copyNumbers;
    }
 
   public boolean isValid() { 
   
    if(copyNumbers != null && !copyNumbers.isEmpty()){
	  Iterator iter = copyNumbers.iterator();
	  while(iter.hasNext()){
	    CopyNumberDE copyNumberde = (CopyNumberDE)iter.next();	
		if(copyNumberde.getValueObject() != null){
			Float copyNumVal = copyNumberde.getValueObject();
			if(!copyNumVal.isNaN() && copyNumVal.floatValue()>0){
			    return true;
			    }	
		     }
	     }
	  }        		
    return false;
    }
	
}
