package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.UntranslatedRegionDE;
import gov.nih.nci.nautilus.criteria.Criteria;

import java.util.*;

/**
 * This  class encapsulates UntranslatedRegion criteria.
 * It contains a collection of UntranslatedRegionDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class UntranslatedRegionCriteria extends Criteria{

  private UntranslatedRegionDE.UTR_5 utr_5;
  private UntranslatedRegionDE.UTR_3 utr_3;
  
  public UntranslatedRegionCriteria(){}
  
  public void setUTR_5(UntranslatedRegionDE.UTR_5 utr_5){
    if(utr_5 != null){
      this.utr_5 = utr_5;   
      }   
   }
  
  public void setUTR_3(UntranslatedRegionDE.UTR_3 utr_3){
    if(utr_3 != null){
	  this.utr_3 = utr_3;
	}
  }

  public UntranslatedRegionDE.UTR_5 getUTR_5(){
   return utr_5;
   }
   
  public  UntranslatedRegionDE.UTR_3 getUTR_3(){
   return utr_3;
   }
 
 public boolean isValid() {
        // TODO: see if we need any validation on untranslatedRegion
       return true;
   }
}
