package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.ChemoAgentDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
import gov.nih.nci.nautilus.de.GenderDE;
import gov.nih.nci.nautilus.de.OccurrenceDE;
import gov.nih.nci.nautilus.de.RadiationTherapyDE;
import gov.nih.nci.nautilus.de.SurgeryTypeDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.clinical.ClinicalQueryHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ClinicalDataQuery extends Query {

    private static Logger logger = Logger.getLogger(ClinicalDataQuery.class);
	private OccurrenceCriteria occurrenceCriteria;
	private RadiationTherapyCriteria radiationTherapyCriteria;
	private ChemoAgentCriteria chemoAgentCriteria;	
	private SurgeryTypeCriteria surgeryTypeCriteria;	
	private SurvivalCriteria survivalCriteria;
	private AgeCriteria ageCriteria;
	private GenderCriteria genderCriteria;
    private QueryHandler HANDLER;

    public QueryHandler getQueryHandler() throws Exception  {
        return (HANDLER == null) ? new ClinicalQueryHandler() : HANDLER;
    }
	public QueryType getQueryType() throws Exception {
		return QueryType.CLINICAL_DATA_QUERY_TYPE;
	}
    public ClinicalDataQuery() {
        super();
    }
    public String toString(){
		ResourceBundle labels = null;
		String OutStr = "<B>Clinical Data Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>" + this.getQueryName();


	try {

		labels = ResourceBundle.getBundle(NautilusConstants.APPLICATION_RESOURCES, Locale.US);
	  
	    // starting DiseaseOrGradeCriteria
		DiseaseOrGradeCriteria thisDiseaseCrit = this.getDiseaseOrGradeCriteria();	
		if ((thisDiseaseCrit != null)&&!thisDiseaseCrit.isEmpty() && labels != null) { 
		    Collection diseaseColl = thisDiseaseCrit.getDiseases();

			String thisCriteria = thisDiseaseCrit.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+"</B><BR>";

			Iterator iter = diseaseColl.iterator();
			while(iter.hasNext()){
			  DiseaseNameDE  diseaseDE = (DiseaseNameDE)iter.next();
			  OutStr += "&nbsp;&nbsp;"+((String) diseaseDE.getValue())+" ";
		       }	 	   
		   }
		else{
		    logger.debug("Disease Criteria is empty or Application Resources file is missing");
		  } //end of DiseaseOrGradeCriteria
		  
		   // starting  OccurrenceCriteria		  
		  OccurrenceCriteria thisOccurrenceCriteria = this.getOccurrenceCriteria();
		  if((thisOccurrenceCriteria != null) && thisOccurrenceCriteria.isEmpty()&& labels != null){
		     String thisCriteria = thisOccurrenceCriteria.getClass().getName();
			 OutStr += "<BR>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1));
			
		     Collection occurrenceColl = thisOccurrenceCriteria.getOccurrences();			
			 Iterator iter = occurrenceColl.iterator();
			 
			 while(iter.hasNext()){
			   OccurrenceDE occurrenceDE = (OccurrenceDE)iter.next();
			   String occurrenceStr = occurrenceDE.getClass().getName();	
			   if(occurrenceDE.getValueObject().equalsIgnoreCase("first Presentation")){	      
		         OutStr += "<BR>&nbsp;&nbsp;"+": "+occurrenceDE.getValue()+"";
			   }
			   else{
			     OutStr += "<BR>&nbsp;&nbsp;"+": "+occurrenceDE.getValue()+" (recurrence)";
			     }
		
			  }
		    } 
		
		 else{
		     logger.debug("OccurrenceCriteria is empty or Application Resources file is missing.");
			 }// end of OccurrenceCriteria
		
		 // starting RadiationTherapyCriteria
		 RadiationTherapyCriteria thisRadiationTherapyCriteria = this.getRadiationTherapyCriteria();
		 if((thisRadiationTherapyCriteria != null) && !thisRadiationTherapyCriteria.isEmpty() && labels != null){		   
		       RadiationTherapyDE radiationTherapyDE = thisRadiationTherapyCriteria.getRadiationTherapyDE();				
			   String radiationStr = radiationTherapyDE.getClass().getName();
			   OutStr += "<BR>"+labels.getString(radiationStr.substring(radiationStr.lastIndexOf(".")+1))+": "+radiationTherapyDE.getValue()+"";
				    
			    }
			else{
			    logger.debug("RadiationTherapyCriteria is empty or Application Resources file is missing.");
			}// end of  RadiationTherapyCriteria
		
		  // starting ChemoAgentCriteria
		  ChemoAgentCriteria thisChemoAgentCriteria = this.getChemoAgentCriteria();
		  if((thisChemoAgentCriteria != null) && !thisChemoAgentCriteria.isEmpty() && labels != null){
		       ChemoAgentDE chemoAgentDE = thisChemoAgentCriteria.getChemoAgentDE();				
			   String chemoStr = chemoAgentDE.getClass().getName();
			   OutStr += "<BR>"+labels.getString(chemoStr.substring(chemoStr.lastIndexOf(".")+1))+": "+chemoAgentDE.getValue()+"";
			  }
		  
		  else{
		      logger.debug("ChemoAgentCriteria is empty or Application Resources file is missing.");
			}// end of  ChemoAgentCriteria
		 
		  
		 // starting SurgeryTypeCriteria 
		 SurgeryTypeCriteria thisSurgeryTypeCriteria = this.getSurgeryTypeCriteria();
		 if((thisSurgeryTypeCriteria != null) && !thisSurgeryTypeCriteria.isEmpty()&& labels != null){
		   SurgeryTypeDE surgeryTypeDE = thisSurgeryTypeCriteria.getSurgeryTypeDE();
		   String surgeryStr = surgeryTypeDE.getClass().getName();
		   OutStr += "<BR>"+labels.getString(surgeryStr.substring(surgeryStr.lastIndexOf(".")+1))+": "+surgeryTypeDE.getValue()+"";
			  }		  
		 else{
		     logger.debug("SurgeryTypeCriteria is empty or Application Resources file is missing.");
			}// end of  SurgeryTypeCriteria
		 
		 		 
		 SurvivalCriteria thisSurvivalCriteria = this.getSurvivalCriteria();
		 if((thisSurvivalCriteria != null) && !thisSurvivalCriteria.isEmpty() && labels != null){
		      String thisCriteria = thisSurvivalCriteria.getClass().getName();
			  OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1)) + "</b>";
			
			  DomainElement survivalLowerDE = thisSurvivalCriteria.getLowerSurvivalRange();
		      DomainElement survivalUpperDE = thisSurvivalCriteria.getUpperSurvivalRange();
		     if(survivalLowerDE != null && survivalUpperDE != null){			     
			     String survivalLowerStr  = survivalLowerDE.getClass().getName();
				 String survivalUpperStr = survivalUpperDE.getClass().getName();
				 OutStr += "<BR><B class='otherBold'>&nbsp;&nbsp;" + labels.getString(survivalLowerStr.substring(survivalLowerStr.lastIndexOf(".")+1)) +":</b><br />&nbsp;&nbsp;&nbsp;"+survivalLowerDE.getValue()+" (months)";
				 OutStr += "<BR><B class='otherBold'>&nbsp;&nbsp;" + labels.getString(survivalUpperStr.substring(survivalUpperStr.lastIndexOf(".")+1)) +":</b><br />&nbsp;&nbsp;&nbsp;"+survivalUpperDE.getValue()+" (months)";
			   	}		   
		    }
		  else{ 
		      logger.debug("SurvivalCriteria is empty or Application Resources file is missing.");
		   }// end of SurvivalCriteria
		 
		 // starting AgeCriteria
		 AgeCriteria thisAgeCriteria = this.getAgeCriteria();
		 if((thisAgeCriteria != null) && !thisAgeCriteria.isEmpty() && labels != null){
		    String thisCriteria = ageCriteria.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1)) + "</b>";
		    DomainElement LowerAgeLimit = thisAgeCriteria.getLowerAgeLimit();
		    DomainElement UpperAgeLimit = thisAgeCriteria.getUpperAgeLimit();
			if(LowerAgeLimit != null && UpperAgeLimit != null){
			     String ageLowerStr  = LowerAgeLimit.getClass().getName();
				 String ageUpperStr = UpperAgeLimit.getClass().getName();
				 OutStr += "<BR>&nbsp;&nbsp;<B class='otherBold'>" + labels.getString(ageLowerStr.substring(ageLowerStr.lastIndexOf(".")+1)) +":</b><br />&nbsp;&nbsp;&nbsp;"+LowerAgeLimit.getValue()+" (years)";
				 OutStr += "<BR>&nbsp;&nbsp;<B class='otherBold'>" + labels.getString(ageUpperStr.substring(ageUpperStr.lastIndexOf(".")+1)) +":</b><br />&nbsp;&nbsp;&nbsp;"+UpperAgeLimit.getValue()+" (years)";
		 	
			 } 
		  }
		  else{ 
		      logger.debug("AgeCriteria is empty or Application Resources file is missing.");
		   }// end of AgeCriteria
		
		 
		 // starting GenderCriteria 
		 
		   GenderCriteria thisGenderCriteria = this.getGenderCriteria();
		   if((thisGenderCriteria != null) && !thisGenderCriteria.isEmpty() && labels != null){		      
		     GenderDE genderDE = thisGenderCriteria.getGenderDE();
			 String genderStr = genderDE.getClass().getName();
		     OutStr += "<BR><B class='otherBold'>"+labels.getString(genderStr.substring(genderStr.lastIndexOf(".")+1))+":</B><BR> ";
			 OutStr += "&nbsp;&nbsp;"+((String) genderDE.getValue())+" ";
			 }  
		  else{
		      logger.debug("GenderCriteria is empty or Application Resources file is missing.");
		   }// end of GenderCriteria
		  
		  
		 
			  
		}// end of try
	catch (Exception ie) { 
		logger.error("Error in ResourceBundle in clinical Data Query - ");
		logger.error(ie);
	}

		OutStr += "<BR><BR>";
    	return OutStr;
    }
    

	
    public void setOccurrenceCrit(OccurrenceCriteria occurrenceCriteria) {
        this.occurrenceCriteria = occurrenceCriteria;
    }

    public OccurrenceCriteria getOccurrenceCriteria() {
        return occurrenceCriteria;
    }

    public void setRadiationTherapyCrit(RadiationTherapyCriteria radiationTherapyCriteria) {
        this.radiationTherapyCriteria = radiationTherapyCriteria;
    }

   public RadiationTherapyCriteria getRadiationTherapyCriteria() {
        return radiationTherapyCriteria;
    }
	
    public ChemoAgentCriteria getChemoAgentCriteria() {
        return chemoAgentCriteria;
    }

    public void setChemoAgentCrit(ChemoAgentCriteria chemoAgentCriteria) {
        this.chemoAgentCriteria = chemoAgentCriteria;
    }

	
	 public SurgeryTypeCriteria  getSurgeryTypeCriteria() {
        return surgeryTypeCriteria;
    }

    public void setSurgeryTypeCrit(SurgeryTypeCriteria surgeryTypeCriteria) {
        this.surgeryTypeCriteria = surgeryTypeCriteria;
    }
	
	public SurvivalCriteria  getSurvivalCriteria() {
        return survivalCriteria;
    }

    public void setSurvivalCrit(SurvivalCriteria survivalCriteria) {
        this.survivalCriteria = survivalCriteria;
    }
	
	public AgeCriteria  getAgeCriteria() {
        return ageCriteria;
    }

    public void setAgeCrit(AgeCriteria ageCriteria) {
        this.ageCriteria = ageCriteria;
    }
	
	public GenderCriteria  getGenderCriteria() {
        return genderCriteria;
    }

    public void setGenderCrit(GenderCriteria genderCriteria) {
        this.genderCriteria = genderCriteria;
    }
    class Handler {
    }
}
