// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl
package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;

import java.util.*;
import java.lang.reflect.*;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;




/** 
 * ClinicalDataForm.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:form name="clinicalDataForm"
 */
public class ClinicalDataForm extends BaseForm {

	// --------------------------------------------------------- Instance Variables               
          
	
	/** queryName property */
	private String queryName;	
	
	private String resultView;
	
   /** tumorType property */
	private String tumorType;
	
	/** tumorGrade property */
	private String tumorGrade;	
	
	/** occurence first presentation property */
	private String firstPresentation;
	
	/** occurence recurrence property */
	private String recurrence;
	
	/** occurence recurrence type property */
	private String recurrenceType;
	
	/** radiation group property */
	private String radiation;
	
	/** radiation type property */
	private String radiationType;
	
	/** chemo agent group property */
	private String chemo;
	
	/** chemo agent type property */
	private String chemoType;
	
	/** surgery group property */
	private String surgery;
	
	/** surgery type property */
	private String surgeryType;
	
	/** survival lower property */
	private String survivalLower;
	
	/** survival upper property */
	private String survivalUpper;
	
	/** age lower property */
	private String ageLower;
	
	/** age upper property */
	private String ageUpper;
		
	/** gender  property */
	private String genderType;
	

	
	// Collections used for Lookup values.  
	//private ArrayList diseaseType;// moved to the upper class: BaseForm.java
	private ArrayList recurrenceTypeColl;
	private ArrayList radiationTypeColl;
	private ArrayList chemoAgentTypeColl;
	private ArrayList surgeryTypeColl;
	private ArrayList survivalLowerColl;
	private ArrayList survivalUpperColl;
	private ArrayList ageLowerColl;
	private ArrayList ageUpperColl;
	private ArrayList genderTypeColl;
	
	// criteria objects 
	private DiseaseOrGradeCriteria diseaseOrGradeCriteria;	
	private OccurrenceCriteria occurrenceCriteria;
	private RadiationTherapyCriteria radiationTherapyCriteria;
	private ChemoAgentCriteria chemoAgentCriteria;	
	private SurgeryTypeCriteria surgeryTypeCriteria;	
	private SurvivalCriteria survivalCriteria;
	private AgeCriteria ageCriteria;
	private GenderCriteria genderCriteria;
	
	
	
// Hashmap to store Domain elements 
    private HashMap diseaseDomainMap;
	private HashMap gradeDomainMap;// this one may not be used for this release.
	private HashMap occurrenceDomainMap;
	private HashMap radiationDomainMap;
	private HashMap chemoAgentDomainMap;
	private HashMap surgeryDomainMap;
	private HashMap survivalDomainMap;
	private HashMap ageDomainMap;
	private HashMap genderDomainMap;
	
	
	private HttpServletRequest thisRequest;
	

	// --------------------------------------------------------- Methods
	public ClinicalDataForm(){
	    super();		
		// Create Lookups for Clinical Data screens 
		setClinicalDataLookup();

	}

	/** 
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		
		// Query Name cannot be blank
		if ((queryName == null || queryName.length() < 1))
			errors.add("queryName", new ActionError("gov.nih.nci.nautilus.struts.form.queryname.no.error"));

		// survival range validations
		if (this.survivalLower != null && this.survivalUpper != null) {
		  try{
		    if(Integer.parseInt(survivalLower) > Integer.parseInt(survivalUpper)){
			   errors.add("Survival Range", new ActionError("error:gov.nih.nci.nautilus.struts.form.survivalRange.must.be.less.than.upperRange"));
			  }		  
		  	 }
		  catch(NumberFormatException ex){
		    ex.printStackTrace();
		   }	 
		 }  

		if (this.ageLower != null && this.ageUpper != null) {
		  try{
		    if(Integer.parseInt(ageLower) > Integer.parseInt(ageUpper)){
			   errors.add("Age Range", new ActionError("error:gov.nih.nci.nautilus.struts.form.ageRange.must.be.less.than.upperRange"));
			  }		  
		  	 }
		  catch(NumberFormatException ex){
		    ex.printStackTrace();
		   }	 
		 }  
		
		if (errors.isEmpty()) {		
		
		    createDiseaseCriteriaObject();
			createOccurrenceCriteriaObject();
			createRadiationTherapyCriteriaObject();
			createChemoAgentCriteriaObject();
			createSurgeryTypeCriteriaObject();
			createSurvivalCriteriaObject();
			createAgeCriteriaObject();
			createGenderCriteriaObject();		
		}

		return errors;
	}

	 
	private void createDiseaseCriteriaObject(){
	  //look thorugh the diseaseDomainMap to extract out the domain elements and create respective Criteria Objects
	  Set keys = diseaseDomainMap.keySet();
	  Iterator iter = keys.iterator();
	  while(iter.hasNext()){
	     Object key = iter.next();
		 
		 try{
	        String strDiseaseDomainClass = (String)diseaseDomainMap.get(iter.next());//use key to get value
		    Constructor[] diseaseConstructors = Class.forName(strDiseaseDomainClass).getConstructors();
			Object [] parameterObjects = {key};
			DiseaseNameDE diseaseNameDEObj = (DiseaseNameDE) diseaseConstructors[0].newInstance(parameterObjects);
		    diseaseOrGradeCriteria.setDisease(diseaseNameDEObj);			
		   } 
		 catch (Exception ex) {
				System.out.println("Error in createDiseaseCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} 
		  catch (LinkageError le) {
				System.out.println("Linkage Error in createDiseaseCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}	    
	      }
	  }
	private void createOccurrenceCriteriaObject() {


		// Loop thru the HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = occurrenceDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + occurrenceDomainMap.get(key));
			
			try {
				String strOccurenceClass = (String) occurrenceDomainMap.get(key);
				Constructor [] occurrenceConstructors = Class.forName(strOccurenceClass).getConstructors();
				Object [] parameterObjects = {key};

				OccurrenceDE occurrenceDE = (OccurrenceDE) occurrenceConstructors[0].newInstance(parameterObjects);
				occurrenceCriteria.setOccurrence(occurrenceDE);
				
				System.out.println("Occurrence Domain Element Value==> "+occurrenceDE.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createOccurrenceCriteriaObject() method:  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createOccurrenceCriteriaObject() method "+ le.getMessage());
				le.printStackTrace();
			}
					
			}
		}

	private void createRadiationTherapyCriteriaObject() {

		Set keys = radiationDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + radiationDomainMap.get(key));
			
			try {
				String strRadiationClass = (String) radiationDomainMap.get(key);
				Constructor [] radiationConstructors = Class.forName(strRadiationClass).getConstructors();
				Object [] parameterObjects = {(String) key};

				RadiationTherapyDE radiationTherapyDE = (RadiationTherapyDE) radiationConstructors[0].newInstance(parameterObjects);					
				radiationTherapyCriteria.setRadiationTherapyDE(radiationTherapyDE);

				System.out.println("Radiation Domain Element Value is ==>"+radiationTherapyDE.getValueObject());
				
				
			} catch (Exception ex) {
				System.out.println("Error in createRadiationTherapyCriteriaObject() mehtod :"+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createRadiationTherapyCriteriaObject() method :"+ le.getMessage());
				le.printStackTrace();
			}
								
			}			
	}

	private void createChemoAgentCriteriaObject() {

		Set keys = chemoAgentDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + chemoAgentDomainMap.get(key));
			
			try {
				String strChemoDomainClass = (String) chemoAgentDomainMap.get(key);
				Constructor [] chemoConstructors = Class.forName(strChemoDomainClass).getConstructors();
				Object [] parameterObjects = {(String) key};

				ChemoAgentDE chemoAgentDE = (ChemoAgentDE) chemoConstructors[0].newInstance(parameterObjects);					
				chemoAgentCriteria.setChemoAgentDE(chemoAgentDE);

				System.out.println("Chemo Agent Domain Element Value is ==>"+chemoAgentDE.getValueObject());
			
						
			} catch (Exception ex) {
				System.out.println("Error in createChemoAgentCriteriaObject() method: "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createChemoAgentCriteriaObject() method: "+ le.getMessage());
				le.printStackTrace();
			}
			
				
					
			}

	}

	
	private void createSurgeryTypeCriteriaObject() {


		// Loop thru the surgeryDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = surgeryDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + surgeryDomainMap.get(key));
			
			try {
				String strSurgeryDomainClass = (String) surgeryDomainMap.get(key);
				Constructor [] surgeryConstructors = Class.forName(strSurgeryDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				SurgeryTypeDE surgeryTypeDE = (SurgeryTypeDE) surgeryConstructors[0].newInstance(parameterObjects);
				surgeryTypeCriteria.setSurgeryTypeDE(surgeryTypeDE);
				
				System.out.println("Surgery Domain Element Value==> "+surgeryTypeDE.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createSurgeryTypeCriteriaObject() method: "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createSurgeryTypeCriteriaObject() method: "+ le.getMessage());
				le.printStackTrace();
			}				
					
			}		

	}
	
		
		private void createSurvivalCriteriaObject() {

		// Loop thru the survivalDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = survivalDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + survivalDomainMap.get(key));
			
			try {
				String strSurvivalDomainClass = (String) survivalDomainMap.get(key);
				Constructor [] survivalConstructors = Class.forName(strSurvivalDomainClass).getConstructors();
				
				if(strSurvivalDomainClass.endsWith("LowerSurvivalRange")){
				  Object [] parameterObjects = {Integer.valueOf((String) key)};				 
                  SurvivalDE.LowerSurvivalRange lowerSurvivalRange = (SurvivalDE.LowerSurvivalRange)survivalConstructors[0].newInstance(parameterObjects);
				  survivalCriteria.setLowerSurvivalRange(lowerSurvivalRange);
				  }
			   else if(strSurvivalDomainClass.endsWith("UpperSurvivalRange")){
			      Object [] parameterObjects = {Integer.valueOf((String) key)};				 
                  SurvivalDE.UpperSurvivalRange upperSurvivalRange = (SurvivalDE.UpperSurvivalRange)survivalConstructors[0].newInstance(parameterObjects);
				  survivalCriteria.setUpperSurvivalRange(upperSurvivalRange);			
			   }		
			} catch (Exception ex) {
				System.out.println("Error in createSurvivalCriteriaObject() method: "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createSurvivalCriteriaObject() method: "+ le.getMessage());
				le.printStackTrace();
			}							
					
			}		
		}
	
	private void createAgeCriteriaObject() {

		// Loop thru the ageDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = ageDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + ageDomainMap.get(key));
			
			try {
			    String strAgeDomainClass = (String) ageDomainMap.get(key);
				Constructor [] ageConstructors = Class.forName(strAgeDomainClass).getConstructors();
				
				if(strAgeDomainClass.endsWith("LowerAgeLimit")){
				  Object [] parameterObjects = {Integer.valueOf((String) key)};				 
                  AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit = (AgeAtDiagnosisDE.LowerAgeLimit)ageConstructors[0].newInstance(parameterObjects);
				  ageCriteria.setLowerAgeLimit(lowerAgeLimit);
				  }
			   else if(strAgeDomainClass.endsWith("UpperAgeLimit")){
			      Object [] parameterObjects = {Integer.valueOf((String) key)};				 
                  AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit = (AgeAtDiagnosisDE.UpperAgeLimit)ageConstructors[0].newInstance(parameterObjects);
				  ageCriteria.setUpperAgeLimit(upperAgeLimit);			
			   }		
				} catch (Exception ex) {
				System.out.println("Error in createGeneCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGeneCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}			
					
			}	
	}

	
	
		private void createGenderCriteriaObject() {

		// Loop thru the genderDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = genderDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + genderDomainMap.get(key));
			
			try {
				String strGenderDomainClass = (String) genderDomainMap.get(key);
				Constructor [] genderConstructors = Class.forName(strGenderDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				GenderDE genderDE = (GenderDE)genderConstructors[0].newInstance(parameterObjects);
				genderCriteria.setGenderDE(genderDE);
				
				System.out.println("Gender Domain Element Value==> "+genderDE.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createGenderCriteriaObject() method:  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGenderCriteriaObject() method: "+ le.getMessage());
				le.printStackTrace();
			}
								
			}	

	}
	
	
	public void setClinicalDataLookup() {

		
		recurrenceTypeColl = new ArrayList();
		radiationTypeColl = new ArrayList();
		chemoAgentTypeColl = new ArrayList(); 
		surgeryTypeColl = new ArrayList();
		survivalLowerColl = new ArrayList();
		survivalUpperColl = new ArrayList();
		ageLowerColl = new ArrayList(); 
		ageUpperColl = new ArrayList(); 
		genderTypeColl = new ArrayList(); 		
		
		
		recurrenceTypeColl.add( new LabelValueBean( "Any", "any" ) );
		recurrenceTypeColl.add( new LabelValueBean( "1", "1" ) );
		recurrenceTypeColl.add( new LabelValueBean( "2", "2" ) );
		recurrenceTypeColl.add( new LabelValueBean( "3", "3" ) );
		
		radiationTypeColl.add( new LabelValueBean( "Any", "any" ) );
		radiationTypeColl.add( new LabelValueBean( "Photon", "photon" ) );
		
		
		chemoAgentTypeColl.add( new LabelValueBean( "Any", "any" ) );
		
		surgeryTypeColl.add( new LabelValueBean( "Any", "any" ) );
		surgeryTypeColl.add( new LabelValueBean( "Complete Resection(CR)", "cr" ) );
		surgeryTypeColl.add( new LabelValueBean( "Partial Resection(PR)", "pr" ) );
		surgeryTypeColl.add( new LabelValueBean( "Bioposy Only(BX)", "bx" ) );
		
		
		survivalLowerColl.add( new LabelValueBean( "0", "0" ) );
		survivalLowerColl.add( new LabelValueBean( "10", "10" ) );
		survivalLowerColl.add( new LabelValueBean( "20", "20" ) );
		survivalLowerColl.add( new LabelValueBean( "30", "30" ) );
		survivalLowerColl.add( new LabelValueBean( "40", "40" ) );
		survivalLowerColl.add( new LabelValueBean( "50", "50" ) );
		survivalLowerColl.add( new LabelValueBean( "60", "60" ) );
		survivalLowerColl.add( new LabelValueBean( "70", "70" ) );
		survivalLowerColl.add( new LabelValueBean( "80", "80" ) );
		survivalLowerColl.add( new LabelValueBean( "90", "90" ) );
		
		survivalUpperColl.add( new LabelValueBean( "0", "0" ) );
		survivalUpperColl.add( new LabelValueBean( "10", "10" ) );
		survivalUpperColl.add( new LabelValueBean( "20", "20" ) );
		survivalUpperColl.add( new LabelValueBean( "30", "30" ) );
		survivalUpperColl.add( new LabelValueBean( "40", "40" ) );
		survivalUpperColl.add( new LabelValueBean( "50", "50" ) );
		survivalUpperColl.add( new LabelValueBean( "60", "60" ) );
		survivalUpperColl.add( new LabelValueBean( "70", "70" ) );
		survivalUpperColl.add( new LabelValueBean( "80", "80" ) );
		survivalUpperColl.add( new LabelValueBean( "90", "90" ) );
		survivalUpperColl.add( new LabelValueBean( "90+", "90+" ) );		
		
		
		ageLowerColl.add( new LabelValueBean( "0", "0" ) );
		ageLowerColl.add( new LabelValueBean( "10", "10" ) );
		ageLowerColl.add( new LabelValueBean( "20", "20" ) );
		ageLowerColl.add( new LabelValueBean( "30", "30" ) );
		ageLowerColl.add( new LabelValueBean( "40", "40" ) );
		ageLowerColl.add( new LabelValueBean( "50", "50" ) );
		ageLowerColl.add( new LabelValueBean( "60", "60" ) );
		ageLowerColl.add( new LabelValueBean( "70", "70" ) );
		ageLowerColl.add( new LabelValueBean( "80", "80" ) );
		ageLowerColl.add( new LabelValueBean( "90", "90" ) );
		
		ageUpperColl.add( new LabelValueBean( "0", "0" ) );
		ageUpperColl.add( new LabelValueBean( "10", "10" ) );
		ageUpperColl.add( new LabelValueBean( "20", "20" ) );
		ageUpperColl.add( new LabelValueBean( "30", "30" ) );
		ageUpperColl.add( new LabelValueBean( "40", "40" ) );
		ageUpperColl.add( new LabelValueBean( "50", "50" ) );
		ageUpperColl.add( new LabelValueBean( "60", "60" ) );
		ageUpperColl.add( new LabelValueBean( "70", "70" ) );
		ageUpperColl.add( new LabelValueBean( "80", "80" ) );
		ageUpperColl.add( new LabelValueBean( "90", "90" ) );
		ageUpperColl.add( new LabelValueBean( "90+", "90+" ) );	
		
		
		genderTypeColl.add( new LabelValueBean( "all", "all" ) );
		genderTypeColl.add( new LabelValueBean( "Male", "male" ) );
		genderTypeColl.add( new LabelValueBean( "Female", "female" ) );
		genderTypeColl.add( new LabelValueBean( "Other", "other" ) );

	}
	
	
	/** 
	 * Method reset.
	 * Reset all properties to their default values.
	 * @param ActionMapping mapping used to select this instance.
	 * @param HttpServletRequest request The servlet request we are processing.
	 */
	 
	public void reset(ActionMapping mapping, HttpServletRequest request) {	
	
	 queryName ="";  
	 resultView = ""; 
	 tumorType="";
	 tumorGrade="";	
	 firstPresentation="";	
	 recurrence="";	
	 recurrenceType ="";	
	 radiation="";
	 radiationType="";
	 chemo="";
	 chemoType="";
	 surgery="";
	 surgeryType="";
	 survivalLower="";
	 survivalUpper="";	
	 ageLower="";	
	 ageUpper="";
	 genderType="";
		
	
	diseaseOrGradeCriteria = new DiseaseOrGradeCriteria();	
	occurrenceCriteria = new OccurrenceCriteria(); 
	radiationTherapyCriteria = new RadiationTherapyCriteria();
	chemoAgentCriteria = new ChemoAgentCriteria();	
	surgeryTypeCriteria = new SurgeryTypeCriteria();	
	survivalCriteria = new SurvivalCriteria();
	ageCriteria = new AgeCriteria();
	genderCriteria = new GenderCriteria();	
	
	
    diseaseDomainMap = new HashMap();
	gradeDomainMap = new HashMap();
	occurrenceDomainMap = new HashMap();
	radiationDomainMap = new HashMap();
	chemoAgentDomainMap = new HashMap();
	surgeryDomainMap = new HashMap();
	survivalDomainMap = new HashMap();
	ageDomainMap = new HashMap();
	genderDomainMap = new HashMap();	
	
	thisRequest = request;	
		
	}
	
   /** 
	 * Set the tumorType.
	 * @param tumorType The tumorType to set
	 */
     public void setTumorType(String tumorType) {
		this.tumorType = tumorType;
		diseaseDomainMap.put(this.tumorType, DiseaseNameDE.class.getName());
	 }
	 
   /** 
	 * Returns the tumorType.
	 * @return String
	 */
	public String getTumorType() {
		return tumorType;
	}  
	
	  /** 
	 * Set the firstPresentation.
	 * @param firstPresentation The firstPresentation to set
	 */
     public void setFirstPresentation(String firstPresentation) {
		this.firstPresentation = firstPresentation;
		occurrenceDomainMap.put(this.firstPresentation, OccurrenceDE.class.getName());
	 }
	 
	  /** 
	 * Returns the firstPresentation.
	 * @return String
	 */
	public String getFirstPresentation() {
		return firstPresentation;
	}  
	
 /** 
	 * Set the recurrence.
	 * @param recurrence The recurrence to set
	 */
     public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
		occurrenceDomainMap.put(this.recurrence, OccurrenceDE.class.getName());
	 }
	 
	 /** 
	 * Returns the recurrence.
	 * @return String
	 */
	public String getRecurrence() {
		return recurrence;
	}  
	
	  /** 
	 * Set the recurrenceType.
	 * @param recurrenceType The recurrenceType to set
	 */
     public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
		occurrenceDomainMap.put(this.recurrenceType, OccurrenceDE.class.getName());
	 }
	 
	/** 
	 * Returns the recurrenceType.
	 * @return String
	 */
	public String getRecurrenceType() {
		return recurrenceType;
	}  

	 /** 
	 * Set the radiation.
	 * @param radiation The radiation to set
	 */
     public void setRadiation(String radiation) {
		this.radiation = radiation;
		radiationDomainMap.put(this.radiation, RadiationTherapyDE.class.getName());
	 }
	 
   /** 
	 * Returns the radiation.
	 * @return String
	 */
	public String getRadiation() {
		return radiation;
	}  
	
   /** 
	 * Set the radiationType.
	 * @param radiationType The radiationType to set
	 */
     public void setRadiationType(String radiationType) {
		this.radiationType = radiationType;
		radiationDomainMap.put(this.radiationType, RadiationTherapyDE.class.getName());
	 }
	 
	 
   /** 
	 * Returns the radiationType.
	 * @return String
	 */
	public String getRadiationType() {
		return radiationType;
	}  
	 
	  /** 
	 * Set the chemo.
	 * @param chemo The chemo to set
	 */
     public void setChemo(String chemo) {
		this.chemo = chemo;
		chemoAgentDomainMap.put(this.chemo, ChemoAgentDE.class.getName());
	 }
	 
	 /** 
	 * Returns the chemo.
	 * @return String
	 */
	public String getChemo() {
		return chemo;
	}  
	 
   /** 
	 * Set the chemoType.
	 * @param chemoType The chemoType to set
	 */
     public void setChemoType(String chemoType) {
		this.chemoType = chemoType;
		chemoAgentDomainMap.put(this.chemoType, ChemoAgentDE.class.getName());
	 }
	 
   /** 
	 * Returns the chemoType.
	 * @return String
	 */
	public String getChemoType() {
		return chemoType;
	} 
	 
	 /** 
	 * Set the surgery.
	 * @param surgery The surgery to set
	 */
     public void setSurgery(String surgery) {
		this.surgery = surgery;
		surgeryDomainMap.put(this.surgery, SurgeryTypeDE.class.getName());
	 }
	 
   /** 
	 * Returns the surgery.
	 * @return String
	 */
	public String getSurgery() {
		return surgery;
	} 
	 
   /** 
	 * Set the surgeryType.
	 * @param surgeryType The surgeryType to set
	 */
     public void setSurgeryType(String surgeryType) {
		this.surgeryType = surgeryType;
		surgeryDomainMap.put(this.surgeryType, SurgeryTypeDE.class.getName());
	 }
	 
   /** 
	 * Returns the surgeryType.
	 * @return String
	 */
	public String getSurgeryType() {
		return surgeryType;
	} 
	
	/** 
	 * Set the survivalLower.
	 * @param survivalLower The survivalLower to set
	 */
	public void setSurvivalLower(String survivalLower) {
		this.survivalLower = survivalLower;
	    survivalDomainMap.put(this.survivalLower, SurvivalDE.class.getName());
	 }
	 
   /** 
	 * Returns the survivalLower.
	 * @return String
	 */
	public String getSurvivalLower() {
		return survivalLower;
	} 
	
   /** 
	 * Set the survivalUpper.
	 * @param survivalUpper The survivalUpper to set
	 */
	public void setSurvivalUpper(String survivalUpper) {
		this.survivalUpper = survivalUpper;
	    survivalDomainMap.put(this.survivalUpper, SurvivalDE.class.getName());
	 }
	 
	 
   /** 
	 * Returns the survivalUpper.
	 * @return String
	 */
	public String getSurvivalUpper() {
		return survivalUpper;
	} 
	
   /** 
	 * Set the ageLower.
	 * @param ageLower The ageLower to set
	 */
	public void setAgeLower(String ageLower) {
		this.ageLower = ageLower;
	    ageDomainMap.put(this.ageLower, AgeAtDiagnosisDE.class.getName());
	 }
	 
    /** 
	 * Returns the ageLower.
	 * @return String
	 */
	public String getAgeLower() {
		return ageLower;
	} 
	  /** 
	 * Set the ageUpper.
	 * @param ageUpper The ageUpper to set
	 */
	public void setAgeUpper(String ageUpper) {
		this.ageUpper = ageUpper;
	    ageDomainMap.put(this.ageUpper, AgeAtDiagnosisDE.class.getName());
	 }	
	 
	 /** 
	 * Returns the genderType.
	 * @return String
	 */
	public String getAgeUpper() {
		return ageUpper;
	} 
   /** 
	 * Set the genderType.
	 * @param genderType The genderType to set
	 */
	public void setGenderType(String genderType) {
		this.genderType = genderType;
	    genderDomainMap.put(this.genderType, GenderDE.class.getName());
	 }	
	 
	 /** 
	 * Returns the genderType.
	 * @return String
	 */
	public String getGenderType() {
		return genderType;
	}
	
	/** 
	 * Set the queryName.
	 * @param queryName The queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	/** 
	 * Returns the queryName.
	 * @return String
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * Returns the resultView.
	 * @return String
	 */
	public String getResultView() {
		return resultView;
	}

	/**
	 * Set the resultView.
	 * @param resultView The resultView to set
	 */
	public void setResultView(String resultView) {
		this.resultView = resultView;
	}
	
	public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
		return this.diseaseOrGradeCriteria;
	}
	   
	public OccurrenceCriteria getOccurrenceCriteria() {
		return this.occurrenceCriteria;
	}
	
	public SurgeryTypeCriteria getSurgeryTypeCriteria(){
	   return this.surgeryTypeCriteria;	
	 }
	 
	
	public RadiationTherapyCriteria getRadiationTherapyCriteria(){
	  return this.radiationTherapyCriteria;
	  }
	  
	public ChemoAgentCriteria getChemoAgentCriteria(){
	  return this.chemoAgentCriteria;
	  }
	
	  
	public SurvivalCriteria getSurvivalCriteria(){
	  return this.survivalCriteria;
	  }
	  
	public AgeCriteria getAgeCriteria(){
	  return this.ageCriteria;
	  }
	  
	public GenderCriteria getGenderCriteria(){
	  return this.genderCriteria;
	  }
	
	

}
