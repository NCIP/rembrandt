package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.GradeDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This  class encapsulates both Disease and Grade criteria.
 * It contains the collections of both DiseaseNameDE & GradeDE.
 *  
 * Dana Zhang
 * Date: August 30, 2004 
 * Version 1.0
 */

public class DiseaseOrGradeCriteria extends Criteria implements Serializable{

 private Collection diseases;
 private Collection grades;
 
 public DiseaseOrGradeCriteria(){}
 
 // this is to deal with single disease entry
 public void setDisease(DiseaseNameDE diseaseNameDE){
  if(diseaseNameDE != null){
    getDiseaseMembers().add(diseaseNameDE);
    }
  }
  
  // this is to deal with multiple disease entries
 public void setDiseases(Collection multiDiseases){
    if(multiDiseases != null){
	   Iterator iter = multiDiseases.iterator();
	   while(iter.hasNext()){
	     DiseaseNameDE diseaseNamede = (DiseaseNameDE)iter.next();		
		 getDiseaseMembers().add(diseaseNamede);		    
	      }	    
	   } 
   }
   
 private Collection getDiseaseMembers(){
    if(diseases == null){
        diseases = new ArrayList();
     }
    return diseases;
  }
  
 public Collection getDiseases(){
    return diseases;
   }

   // this is to deal with single grade entry
 public void setGrade(GradeDE gradeDE){
   if(gradeDE != null){
     getGradeMembers().add(gradeDE);   
      }  
   }
   
 //this is to deal with multiple grade entries
  public void setGrades(Collection multiGrades){
    if(multiGrades != null){
	   Iterator iter = multiGrades.iterator();
	   while(iter.hasNext()){
	     GradeDE gradede = (GradeDE)iter.next();		
		 getGradeMembers().add(gradede);		    
	      }	    
	   } 
   }
   
 private Collection getGradeMembers(){
    if(grades == null){
       grades = new ArrayList();
       }
    return grades;
  }
  
 public Collection getGrades(){
    return grades;
   }
 
    
public boolean isValid() {
  // need to have  a disease entry(entries) first
     if(diseases == null || grades == null){
	    return false;
		}
	 else if(diseases != null & diseases.isEmpty()){
	    return false;	 
	   }
     return true;
   }
   
   
}
