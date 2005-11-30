
package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ChemoAgentCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.KarnofskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.LanskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.MRIClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.NeuroExamClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OccurrenceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RaceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RadiationTherapyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurgeryTypeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.de.AgeAtDiagnosisDE;
import gov.nih.nci.caintegrator.dto.de.ChemoAgentDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.GradeDE;
import gov.nih.nci.caintegrator.dto.de.KarnofskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.LanskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.MRIClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.NeuroExamClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.OccurrenceDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;
import gov.nih.nci.caintegrator.dto.de.RadiationTherapyDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.SurgeryTypeDE;
import gov.nih.nci.caintegrator.dto.de.SurvivalDE;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

public class ClinicalDataForm extends BaseForm {

    private static Logger logger = Logger.getLogger(ClinicalDataForm.class);

    /** queryName property */
    private String queryName;

    private String resultView;
    
    /** clinical_evaluation  lansky property */
    private String lansky;

    /** clinical_evaluation lansky type property */
    private String lanskyType;
    
    /** clinical_evaluation  neuroExam property */
    private String neuroExam;

    /** clinical_evaluation neuroExam type property */
    private String neuroExamType;
    
    /** clinical_evaluation  mri property */
    private String mri;

    /** clinical_evaluation mri type property */
    private String mriType;
    
    /** clinical_evaluation  karnofsky property */
    private String karnofsky;

    /** clinical_evaluation karnofsky type property */
    private String karnofskyType;

    
    /** caucasion property */
    private String caucasion;
    
    /** africanAmerican property */
    private String africanAmerican;
    
    /** latino property */
    private String latino;
    
    /** asianAmerican property */
    private String asianAmerican;
    
    /** nativeAmerican property */
    private String nativeAmerican;  
    
 
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

    /** gender property */
    private String genderType;
    
    private AgeAtDiagnosisDE.LowerAgeLimit lowerAgeLimit = null;
    private AgeAtDiagnosisDE.UpperAgeLimit upperAgeLimit = null;
    private SurvivalDE.UpperSurvivalRange upperSurvivalRange = null;
    private SurvivalDE.LowerSurvivalRange lowerSurvivalRange = null;
    
   
	
	

    // Collections used for Lookup values.
    //private ArrayList diseaseType;// moved to the upper class: BaseForm.java
    private ArrayList recurrenceTypeColl = new ArrayList();

    private ArrayList radiationTypeColl = new ArrayList();

    private ArrayList chemoAgentTypeColl = new ArrayList();

    private ArrayList surgeryTypeColl = new ArrayList();

    private ArrayList survivalLowerColl = new ArrayList();

    private ArrayList survivalUpperColl = new ArrayList();

    private ArrayList ageLowerColl = new ArrayList();

    private ArrayList ageUpperColl = new ArrayList();

    private ArrayList genderTypeColl = new ArrayList();
    
    private ArrayList karnofskyTypeColl = new ArrayList();
    
    private ArrayList lanskyTypeColl = new ArrayList();
    
    private ArrayList neuroExamTypeColl = new ArrayList();
    
    private ArrayList mriTypeColl = new ArrayList();
   

    private OccurrenceCriteria occurrenceCriteria = new OccurrenceCriteria();

    private RadiationTherapyCriteria radiationTherapyCriteria;

    private ChemoAgentCriteria chemoAgentCriteria;

    private SurgeryTypeCriteria surgeryTypeCriteria;

    private SurvivalCriteria survivalCriteria = new SurvivalCriteria();

    private AgeCriteria ageCriteria = new AgeCriteria();

    private GenderCriteria genderCriteria;
    
    private  RaceCriteria raceCriteria = new RaceCriteria();
    
    private KarnofskyClinicalEvalCriteria karnofskyCriteria;
    
    private  LanskyClinicalEvalCriteria lanskyCriteria ;
    
    private  MRIClinicalEvalCriteria mriCriteria ;
    
    
    private  NeuroExamClinicalEvalCriteria neuroExamCriteria ;    
   

  
  

    

    // --------------------------------------------------------- Methods
    public ClinicalDataForm() {
        super();
        // Create Lookups for Clinical Data screens
        setClinicalDataLookup();
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

        recurrenceTypeColl.add(new LabelValueBean("Any", "any"));
        recurrenceTypeColl.add(new LabelValueBean("1", "1"));
        recurrenceTypeColl.add(new LabelValueBean("2", "2"));
        recurrenceTypeColl.add(new LabelValueBean("3", "3"));

        radiationTypeColl.add(new LabelValueBean("Any", "any"));
        radiationTypeColl.add(new LabelValueBean("Photon", "photon"));

        chemoAgentTypeColl.add(new LabelValueBean("Any", "any"));

        surgeryTypeColl.add(new LabelValueBean("Any", "any"));
        surgeryTypeColl.add(new LabelValueBean("Complete Resection(CR)",
                "Complete Resection(CR)"));
        surgeryTypeColl.add(new LabelValueBean("Partial Resection(PR)",
                "Partial Resection(PR)"));
        surgeryTypeColl.add(new LabelValueBean("Bioposy Only(BX)",
                "Bioposy Only(BX)"));

        survivalLowerColl.add(new LabelValueBean("", ""));
        survivalLowerColl.add(new LabelValueBean("0", "0"));
        survivalLowerColl.add(new LabelValueBean("10", "10"));
        survivalLowerColl.add(new LabelValueBean("20", "20"));
        survivalLowerColl.add(new LabelValueBean("30", "30"));
        survivalLowerColl.add(new LabelValueBean("40", "40"));
        survivalLowerColl.add(new LabelValueBean("50", "50"));
        survivalLowerColl.add(new LabelValueBean("60", "60"));
        survivalLowerColl.add(new LabelValueBean("70", "70"));
        survivalLowerColl.add(new LabelValueBean("80", "80"));
        survivalLowerColl.add(new LabelValueBean("90", "90"));

        survivalUpperColl.add(new LabelValueBean("", ""));
        survivalUpperColl.add(new LabelValueBean("0", "0"));
        survivalUpperColl.add(new LabelValueBean("10", "10"));
        survivalUpperColl.add(new LabelValueBean("20", "20"));
        survivalUpperColl.add(new LabelValueBean("30", "30"));
        survivalUpperColl.add(new LabelValueBean("40", "40"));
        survivalUpperColl.add(new LabelValueBean("50", "50"));
        survivalUpperColl.add(new LabelValueBean("60", "60"));
        survivalUpperColl.add(new LabelValueBean("70", "70"));
        survivalUpperColl.add(new LabelValueBean("80", "80"));
        survivalUpperColl.add(new LabelValueBean("90", "90"));
        //	survivalUpperColl.add( new LabelValueBean( "90+", "90+" ) );

        ageLowerColl.add(new LabelValueBean("", ""));
        ageLowerColl.add(new LabelValueBean("0", "0"));
        ageLowerColl.add(new LabelValueBean("10", "10"));
        ageLowerColl.add(new LabelValueBean("20", "20"));
        ageLowerColl.add(new LabelValueBean("30", "30"));
        ageLowerColl.add(new LabelValueBean("40", "40"));
        ageLowerColl.add(new LabelValueBean("50", "50"));
        ageLowerColl.add(new LabelValueBean("60", "60"));
        ageLowerColl.add(new LabelValueBean("70", "70"));
        ageLowerColl.add(new LabelValueBean("80", "80"));
        ageLowerColl.add(new LabelValueBean("90", "90"));

        ageUpperColl.add(new LabelValueBean("", ""));
        ageUpperColl.add(new LabelValueBean("0", "0"));
        ageUpperColl.add(new LabelValueBean("10", "10"));
        ageUpperColl.add(new LabelValueBean("20", "20"));
        ageUpperColl.add(new LabelValueBean("30", "30"));
        ageUpperColl.add(new LabelValueBean("40", "40"));
        ageUpperColl.add(new LabelValueBean("50", "50"));
        ageUpperColl.add(new LabelValueBean("60", "60"));
        ageUpperColl.add(new LabelValueBean("70", "70"));
        ageUpperColl.add(new LabelValueBean("80", "80"));
        ageUpperColl.add(new LabelValueBean("90", "90"));
        //	ageUpperColl.add( new LabelValueBean( "90+", "90+" ) );

        genderTypeColl.add(new LabelValueBean("", ""));
        genderTypeColl.add(new LabelValueBean("Male", "M"));
        genderTypeColl.add(new LabelValueBean("Female", "F"));
        genderTypeColl.add(new LabelValueBean("Other", "O"));
        
        
        karnofskyTypeColl.add(new LabelValueBean("", ""));
        karnofskyTypeColl.add(new LabelValueBean("0", "0"));
        karnofskyTypeColl.add(new LabelValueBean("20", "20"));
        karnofskyTypeColl.add(new LabelValueBean("30", "30"));
        karnofskyTypeColl.add(new LabelValueBean("40", "40"));
        karnofskyTypeColl.add(new LabelValueBean("50", "50"));
        karnofskyTypeColl.add(new LabelValueBean("60", "60"));
        karnofskyTypeColl.add(new LabelValueBean("70", "70"));
        karnofskyTypeColl.add(new LabelValueBean("80", "80"));
        karnofskyTypeColl.add(new LabelValueBean("90", "90"));
        karnofskyTypeColl.add(new LabelValueBean("100", "100"));
        
        lanskyTypeColl.add(new LabelValueBean("", ""));
        
        neuroExamTypeColl.add(new LabelValueBean("", ""));        
        neuroExamTypeColl.add(new LabelValueBean("-2", "-2")); 
        neuroExamTypeColl.add(new LabelValueBean("-1", "-1")); 
        neuroExamTypeColl.add(new LabelValueBean("0", "0")); 
        neuroExamTypeColl.add(new LabelValueBean("1", "1")); 
        neuroExamTypeColl.add(new LabelValueBean("2", "2")); 
        
        mriTypeColl.add(new LabelValueBean("", "")); 
        mriTypeColl.add(new LabelValueBean("-3", "-3"));  
        mriTypeColl.add(new LabelValueBean("-2", "-2"));  
        mriTypeColl.add(new LabelValueBean("-1", "-1"));  
        mriTypeColl.add(new LabelValueBean("0", "0"));  
        mriTypeColl.add(new LabelValueBean("1", "1"));  
        mriTypeColl.add(new LabelValueBean("2", "2"));  
        mriTypeColl.add(new LabelValueBean("3", "3")); 

    }

    /**
     * Method reset. Reset all properties to their default values.
     * 
     * @param ActionMapping
     *            mapping used to select this instance.
     * @param HttpServletRequest
     *            request The servlet request we are processing.
     */

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        queryName = "";
        resultView = "";    
        lansky= "";     
        lanskyType ="";   
        neuroExam = "";      
        neuroExamType = "";        
        mri = "";       
        mriType ="";        
        karnofsky ="";    
        karnofskyType = "";

        caucasion= "";       
        africanAmerican= "";      
        latino= "";      
        asianAmerican= "";       
        nativeAmerican= "";
        tumorType = "";
        tumorGrade = "";
        firstPresentation = "";
        recurrence = "";
        recurrenceType = "";
        radiation = "";
        radiationType = "";
        chemo = "";
        chemoType = "";
        surgery = "";
        surgeryType = "";
        survivalLower = "";
        survivalUpper = "";
        ageLower = "";
        ageUpper = "";
        genderType = "";
        sampleGroup = "";
		sampleList = "";
		sampleFile = null;

        diseaseOrGradeCriteria = new DiseaseOrGradeCriteria();
        occurrenceCriteria = new OccurrenceCriteria();
        radiationTherapyCriteria = new RadiationTherapyCriteria();
        chemoAgentCriteria = new ChemoAgentCriteria();
        surgeryTypeCriteria = new SurgeryTypeCriteria();
        survivalCriteria = new SurvivalCriteria();
        ageCriteria = new AgeCriteria();
        genderCriteria = new GenderCriteria();
        sampleCriteria = new SampleCriteria();  
        raceCriteria = new RaceCriteria();
       

        thisRequest = request;

    }
 
    
    

   
	/**
	 * @return Returns the karnofsky.
	 */
	public String getKarnofsky() {
		return karnofsky;
	}





	/**
	 * @param karnofsky The karnofsky to set.
	 */
	public void setKarnofsky(String karnofsky) {
		this.karnofsky = karnofsky;
	}





	/**
	 * @return Returns the karnofskyType.
	 */
	public String getKarnofskyType() {
		return karnofskyType;
	}





	/**
	 * @param karnofskyType The karnofskyType to set.
	 */
	public void setKarnofskyType(String karnofskyType) {
		this.karnofskyType = karnofskyType;
		
		   if (thisRequest != null) {
	            // this is to check if thisKarnofsky option is selected
	            String thisKarnofsky = thisRequest.getParameter("karnofsky");
	            karnofskyCriteria = new KarnofskyClinicalEvalCriteria();
	            
	            // this is to check the type of Karnofsky
	            String thisKarnofskyType = thisRequest.getParameter("karnofskyType");

	            if (thisKarnofsky != null && thisKarnofskyType != null
	                    && !thisKarnofskyType.equals("")) {
	            	KarnofskyClinicalEvalDE karnofskyClinicalEvalDE = new KarnofskyClinicalEvalDE(this.karnofskyType);
	            	karnofskyCriteria.setKarnofskyClinicalEvalDE(karnofskyClinicalEvalDE);	              
	            }
	        }
	}





	/**
	 * @return Returns the lansky.
	 */
	public String getLansky() {
		return lansky;
	}





	/**
	 * @param lansky The lansky to set.
	 */
	public void setLansky(String lansky) {
		this.lansky = lansky;
	}





	/**
	 * @return Returns the lanskyType.
	 */
	public String getLanskyType() {
		return lanskyType;
	}





	/**
	 * @param lanskyType The lanskyType to set.
	 */
	public void setLanskyType(String lanskyType) {
		this.lanskyType = lanskyType;
		
		if (thisRequest != null) {
            // this is to check if thisKarnofsky option is selected
            String thisLansky = thisRequest.getParameter("lansky");
            lanskyCriteria = new LanskyClinicalEvalCriteria();
            
            // this is to check the type of lanskyType
            String thisLanskyType = thisRequest.getParameter("lanskyType");

            if (thisLansky != null && thisLanskyType != null
                    && !thisLanskyType.equals("")) {
            	LanskyClinicalEvalDE lanskyClinicalEvalDE = new LanskyClinicalEvalDE(this.lanskyType);
            	lanskyCriteria.setLanskyClinicalEvalDE(lanskyClinicalEvalDE);	              
            }
        }
	}





	/**
	 * @return Returns the mri.
	 */
	public String getMri() {
		return mri;
	}





	/**
	 * @param mri The mri to set.
	 */
	public void setMri(String mri) {
		this.mri = mri;
	}





	/**
	 * @return Returns the mriType.
	 */
	public String getMriType() {
		return mriType;
	}





	/**
	 * @param mriType The mriType to set.
	 */
	public void setMriType(String mriType) {
		this.mriType = mriType;
		
		if (thisRequest != null) {
            // this is to check if thisKarnofsky option is selected
            String thisMRI= thisRequest.getParameter("mri");
            mriCriteria = new MRIClinicalEvalCriteria();
            
            // this is to check the type of lanskyType
            String thisMRIType = thisRequest.getParameter("mriType");

            if (thisMRI != null && thisMRIType != null
                    && !thisMRIType.equals("")) {
            	MRIClinicalEvalDE mriClinicalEvalDE = new MRIClinicalEvalDE(this.mriType);
            	mriCriteria.setMRIClinicalEvalDE(mriClinicalEvalDE);	              
            }
        }
	}





	/**
	 * @return Returns the neuroExam.
	 */
	public String getNeuroExam() {
		return neuroExam;
	}





	/**
	 * @param neuroExam The neuroExam to set.
	 */
	public void setNeuroExam(String neuroExam) {
		this.neuroExam = neuroExam;
	}





	/**
	 * @return Returns the neuroExamType.
	 */
	public String getNeuroExamType() {
		return neuroExamType;
	}


	/**
	 * @param neuroExamType The neuroExamType to set.
	 */
	public void setNeuroExamType(String neuroExamType) {
		this.neuroExamType = neuroExamType;
		
		if (thisRequest != null) {
            // this is to check if thisKarnofsky option is selected
            String thisNeuroExam= thisRequest.getParameter("neuroExam");
            neuroExamCriteria = new NeuroExamClinicalEvalCriteria();
            
            // this is to check the type of lanskyType
            String thisNeuroExamType = thisRequest.getParameter("neuroExamType");

            if (thisNeuroExam != null && thisNeuroExamType != null
                    && !thisNeuroExamType.equals("")) {
            	NeuroExamClinicalEvalDE neuroExamClinicalEvalDE = new NeuroExamClinicalEvalDE(this.neuroExamType);
            	neuroExamCriteria.setNeuroExamClinicalEvalDE(neuroExamClinicalEvalDE);	              
            }
        }
	}





	/**
	 * @return Returns the africanAmerican.
	 */
	public String getAfricanAmerican() {
		return africanAmerican;
	}





	/**
	 * @param africanAmerican The africanAmerican to set.
	 */
	public void setAfricanAmerican(String africanAmerican) {
		this.africanAmerican = africanAmerican;
		 if (africanAmerican != null) {
	            if (africanAmerican.equalsIgnoreCase("on")) {
	                //this.africanAmerican = "African American";
	            	this.africanAmerican = "Black";
	            }
	            RaceDE raceDE = new RaceDE(this.africanAmerican );
	            raceCriteria.setRace(raceDE);
	          
	        }
	}


	/**
	 * @return Returns the asianAmerican.
	 */
	public String getAsianAmerican() {
		return asianAmerican;
	}

	/**
	 * @param asianAmerican The asianAmerican to set.
	 */
	public void setAsianAmerican(String asianAmerican) {
		this.asianAmerican = asianAmerican;
		  if (asianAmerican != null) {
	            if (asianAmerican.equalsIgnoreCase("on")) {
	                this.asianAmerican = "Asian American";
	                
	            }
	            RaceDE raceDE = new RaceDE(this.asianAmerican );
	            raceCriteria.setRace(raceDE);
	          
	        }
	}


	/**
	 * @return Returns the caucasion.
	 */
	public String getCaucasion() {
		return caucasion;
	}

	/**
	 * @param caucasion The caucasion to set.
	 */
	public void setCaucasion(String caucasion) {
		this.caucasion = caucasion;	
		 if (caucasion != null) {
	            if (caucasion.equalsIgnoreCase("on")) {
	                //this.caucasion = "Caucasion";	     
	            	this.caucasion = "White";	 
	            }
	            RaceDE raceDE = new RaceDE(this.caucasion );
	            raceCriteria.setRace(raceDE);
	          
	        }
	}

	/**
	 * @return Returns the latino.
	 */
	public String getLatino() {
		return latino;
	}
	/**
	 * @param latino The latino to set.
	 */
	public void setLatino(String latino) {
		this.latino = latino;
		 if (latino != null) {
	            if (latino.equalsIgnoreCase("on")) {
	                this.latino = "Latino";	                
	            }
	            RaceDE raceDE = new RaceDE(this.latino);
	            raceCriteria.setRace(raceDE);
	          
	        }
	}

	/**
	 * @return Returns the nativeAmerican.
	 */
	public String getNativeAmerican() {
		return nativeAmerican;
	}





	/**
	 * @param nativeAmerican The nativeAmerican to set.
	 */
	public void setNativeAmerican(String nativeAmerican) {
		this.nativeAmerican = nativeAmerican;
		if (nativeAmerican != null) {
	            if (nativeAmerican.equalsIgnoreCase("on")) {
	                this.nativeAmerican = "Native American";	                
	            }
	            RaceDE raceDE = new RaceDE(this.nativeAmerican);
	            raceCriteria.setRace(raceDE);
	          
	        }
	}





  
   
	
	

    /**
     * Returns the tumorGrade.
     * 
     * @return String
     */
    public String getTumorGrade() {
        return tumorGrade;
    }

    /**
     * Set the tumorGrade.
     * 
     * @param tumorType
     *            The tumorGrade to set
     */
    public void setTumorGrade(String tumorGrade) {
    	this.tumorGrade = tumorGrade;    	
     
     }

  
  

    /**
     * Set the firstPresentation.
     * 
     * @param firstPresentation
     *            The firstPresentation to set
     */
    public void setFirstPresentation(String firstPresentation) {
        if (firstPresentation != null) {
            if (firstPresentation.equalsIgnoreCase("on")) {
                this.firstPresentation = "first presentation";
                
            }
            OccurrenceDE occurrenceDE = new OccurrenceDE(this.firstPresentation );
            occurrenceCriteria.setOccurrence(occurrenceDE);
          
        }
    }

    /**
     * Returns the firstPresentation.
     * 
     * @return String
     */
    public String getFirstPresentation() {
        return firstPresentation;
    }

    /**
     * Set the recurrence.
     * 
     * @param recurrence
     *            The recurrence to set
     */
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
        if (thisRequest != null) {
            String recurrenceStr = (String) thisRequest.getParameter("recur");
            String recurrenceSpecify = (String) thisRequest
                    .getParameter("recurrence");
            if (recurrenceStr != null && !recurrenceStr.equals("")&& recurrenceSpecify != null && !recurrenceSpecify.equals("")) {            	 
           	     OccurrenceDE occurrenceDE = new OccurrenceDE(this.recurrence);
                 occurrenceCriteria.setOccurrence(occurrenceDE);
                
            }
        }
    }

    /**
     * Returns the recurrence.
     * 
     * @return String
     */
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Set the recurrenceType.
     * 
     * @param recurrenceType
     *            The recurrenceType to set
     */
    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
        if(this.recurrenceType !=null && !this.recurrenceType.equals("")){
           OccurrenceDE occurrenceDE = new OccurrenceDE(this.recurrenceType);
           occurrenceCriteria.setOccurrence(occurrenceDE);
        }
       
    }

    /**
     * Returns the recurrenceType.
     * 
     * @return String
     */
    public String getRecurrenceType() {
        return recurrenceType;
    }

    /**
     * Set the radiation.
     * 
     * @param radiation
     *            The radiation to set
     */
    public void setRadiation(String radiation) {
        this.radiation = radiation;

    }

    /**
     * Returns the radiation.
     * 
     * @return String
     */
    public String getRadiation() {
        return radiation;
    }

    /**
     * Set the radiationType.
     * 
     * @param radiationType
     *            The radiationType to set
     */
    public void setRadiationType(String radiationType) {
        this.radiationType = radiationType;
        if (thisRequest != null) {
            // this is to check if radiation option is selected
            String thisRadiation = thisRequest.getParameter("radiation");
            radiationTherapyCriteria = new RadiationTherapyCriteria();
            
            // this is to check the type of radiation
            String thisRadiationType = thisRequest.getParameter("radiationType");

            if (thisRadiation != null && thisRadiationType != null
                    && !thisRadiationType.equals("")) {
            	  RadiationTherapyDE radiationTherapyDE = new RadiationTherapyDE(this.radiationType);
                  radiationTherapyCriteria.setRadiationTherapyDE(radiationTherapyDE);
              
            }
        }
    }

    /**
     * Returns the radiationType.
     * 
     * @return String
     */
    public String getRadiationType() {
        return radiationType;
    }

    /**
     * Set the chemo.
     * 
     * @param chemo
     *            The chemo to set
     */
    public void setChemo(String chemo) {
        this.chemo = chemo;

    }

    /**
     * Returns the chemo.
     * 
     * @return String
     */
    public String getChemo() {
        return chemo;
    }

    /**
     * Set the chemoType.
     * 
     * @param chemoType
     *            The chemoType to set
     */
    public void setChemoType(String chemoType) {
        this.chemoType = chemoType;
        if (thisRequest != null) {
            // this is to check if the chemo option is selected
            String thisChemo = thisRequest.getParameter("chemo");
            chemoAgentCriteria = new ChemoAgentCriteria();
            // this is to check the chemo type
            String thisChemoType = thisRequest.getParameter("chemoType");
            if (thisChemo != null && thisChemoType != null
                    && !thisChemoType.equals("")) {            	
              
                    ChemoAgentDE chemoAgentDE = new ChemoAgentDE(this.chemoType);
                    chemoAgentCriteria.setChemoAgentDE(chemoAgentDE);
                  
                }
              
            }
        }
   

    /**
     * Returns the chemoType.
     * 
     * @return String
     */
    public String getChemoType() {
        return chemoType;
    }

    /**
     * Set the surgery.
     * 
     * @param surgery
     *            The surgery to set
     */
    public void setSurgery(String surgery) {
        this.surgery = surgery;

    }

    /**
     * Returns the surgery.
     * 
     * @return String
     */
    public String getSurgery() {
        return surgery;
    }

    /**
     * Set the surgeryType.
     * 
     * @param surgeryType
     *            The surgeryType to set
     */
    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
        if (thisRequest != null) {
            String thisSurgery = thisRequest.getParameter("sugery");
            String thisSurgeryType = thisRequest.getParameter("surgeryType");
            surgeryTypeCriteria = new SurgeryTypeCriteria();
            if (thisSurgery != null && thisSurgeryType != null
                    && !thisSurgeryType.equals("")) {
            	 SurgeryTypeDE surgeryTypeDE = new SurgeryTypeDE(this.surgeryType);
          	     surgeryTypeCriteria.setSurgeryTypeDE(surgeryTypeDE);                
            }
        }
    }

    /**
     * Returns the surgeryType.
     * 
     * @return String
     */
    public String getSurgeryType() {
        return surgeryType;
    }

    /**
     * Set the survivalLower.
     * 
     * @param survivalLower
     *            The survivalLower to set
     */
    public void setSurvivalLower(String survivalLower) {
        this.survivalLower = survivalLower;      
        if(this.survivalLower != null && this.survivalLower.trim().length()>=1){
        	try{
		        lowerSurvivalRange = new SurvivalDE.LowerSurvivalRange(Integer.valueOf(this.survivalLower));
		        survivalCriteria.setLowerSurvivalRange(lowerSurvivalRange);
	        	}
        	catch(NumberFormatException e){}
	     } 
        
    }

    /**
     * Returns the survivalLower.
     * 
     * @return String
     */
    public String getSurvivalLower() {
        return survivalLower;
    }

    /**
     * Set the survivalUpper.
     * 
     * @param survivalUpper
     *            The survivalUpper to set
     */
    public void setSurvivalUpper(String survivalUpper) {
        this.survivalUpper = survivalUpper;       
       
        if(this.survivalUpper != null && this.survivalUpper.trim().length()>=1){
        	try{
	        upperSurvivalRange = new SurvivalDE.UpperSurvivalRange(Integer.valueOf(this.survivalUpper));
	        survivalCriteria.setUpperSurvivalRange(upperSurvivalRange);
           }
        	catch(NumberFormatException e){}
        }
      
    }

    /**
     * Returns the survivalUpper.
     * 
     * @return String
     */
    public String getSurvivalUpper() {
        return survivalUpper;
    }

    /**
     * Set the ageLower.
     * 
     * @param ageLower
     *            The ageLower to set
     */
   
    public void setAgeLower(String ageLower) {
        this.ageLower = ageLower;
        
       if(this.ageLower  != null && this.ageLower.trim().length()>=1){
        	try{        	
	          lowerAgeLimit = new AgeAtDiagnosisDE.LowerAgeLimit(Integer.valueOf(this.ageLower));	       
	          ageCriteria.setLowerAgeLimit(lowerAgeLimit);
        	}      
        
           catch(NumberFormatException e){}       
       
        }
  
    }
  
    /**
     * Returns the ageLower.
     * 
     * @return String
     */
    public String getAgeLower() {
        return ageLower;
    }

    /**
     * Set the ageUpper.
     * 
     * @param ageUpper
     *            The ageUpper to set
     */
    public void setAgeUpper(String ageUpper) {
        this.ageUpper = ageUpper;
        
      if(this.ageUpper != null && this.ageUpper.trim().length()>=1){
        	try{        	
	         upperAgeLimit = new AgeAtDiagnosisDE.UpperAgeLimit(Integer.valueOf(this.ageUpper));	        	  
	         ageCriteria.setUpperAgeLimit(upperAgeLimit);
        	} 
        	
        catch(NumberFormatException e){}
    }
    
    
    }

    /**
     * Returns the genderType.
     * 
     * @return String
     */
    public String getAgeUpper() {
        return ageUpper;
    }

    /**
     * Set the genderType.
     * 
     * @param genderType
     *            The genderType to set
     */
    public void setGenderType(String genderType) {
        this.genderType = genderType;

        if (this.genderType != null && !this.genderType.trim().equals("")) {        	
           	  genderCriteria = new GenderCriteria();
           	  GenderDE genderDE = new GenderDE(this.genderType );
           	  genderCriteria.setGenderDE(genderDE);
         }

    }

    /**
     * Returns the genderType.
     * 
     * @return String
     */
    public String getGenderType() {
        return genderType;
    }

    /**
     * Set the queryName.
     * 
     * @param queryName
     *            The queryName to set
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * Returns the queryName.
     * 
     * @return String
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * Returns the resultView.
     * 
     * @return String
     */
    public String getResultView() {
        return resultView;
    }

    /**
     * Set the resultView.
     * 
     * @param resultView
     *            The resultView to set
     */
    public void setResultView(String resultView) {
        this.resultView = resultView;
    }    
    

    public OccurrenceCriteria getOccurrenceCriteria() {
        return this.occurrenceCriteria;
    }

    public SurgeryTypeCriteria getSurgeryTypeCriteria() {
        return this.surgeryTypeCriteria;
    }

    public RadiationTherapyCriteria getRadiationTherapyCriteria() {
        return this.radiationTherapyCriteria;
    }

    public ChemoAgentCriteria getChemoAgentCriteria() {
        return this.chemoAgentCriteria;
    }

    public SurvivalCriteria getSurvivalCriteria() {
        return this.survivalCriteria;
    }

    public AgeCriteria getAgeCriteria() {
        return this.ageCriteria;
    }

    public GenderCriteria getGenderCriteria() {
        return this.genderCriteria;
    }

    public RaceCriteria getRaceCriteria() {
        return this.raceCriteria;
    }
    
    
    
    /**
	 * @return Returns the karnofskyCriteria.
	 */
	public KarnofskyClinicalEvalCriteria getKarnofskyCriteria() {
		return karnofskyCriteria;
	}



	/**
	 * @return Returns the lanskyCriteria.
	 */
	public LanskyClinicalEvalCriteria getLanskyCriteria() {
		return lanskyCriteria;
	}


	/**
	 * @return Returns the mriCriteria.
	 */
	public MRIClinicalEvalCriteria getMriCriteria() {
		return mriCriteria;
	}

	/**
	 * @return Returns the neuroExamCriteria.
	 */
	public NeuroExamClinicalEvalCriteria getNeuroExamCriteria() {
		return neuroExamCriteria;
	}



	public ArrayList getRecurrenceTypeColl() {
        return recurrenceTypeColl;
    }

    public ArrayList getRadiationTypeColl() {
        return radiationTypeColl;
    }

    public ArrayList getChemoAgentTypeColl() {
        return chemoAgentTypeColl;
    }

    public ArrayList getSurgeryTypeColl() {
        return surgeryTypeColl;
    }

    public ArrayList getSurvivalLowerColl() {
        return survivalLowerColl;
    }

    public ArrayList getSurvivalUpperColl() {
        return survivalUpperColl;
    }

    public ArrayList getAgeLowerColl() {
        return ageLowerColl;
    }

    public ArrayList getAgeUpperColl() {
        return ageUpperColl;
    }

    public ArrayList getGenderTypeColl() {
        return genderTypeColl;
    }


	/**
	 * @return Returns the karnofskyTypeColl.
	 */
	public ArrayList getKarnofskyTypeColl() {
		return karnofskyTypeColl;
	}
/**
	 * @return Returns the lanskyTypeColl.
	 */
	public ArrayList getLanskyTypeColl() {
		return lanskyTypeColl;
	}

/**
	 * @return Returns the mriTypeColl.
	 */
	public ArrayList getMriTypeColl() {
		return mriTypeColl;
	}




	/**
	 * @return Returns the neuroExamTypeColl.
	 */
	public ArrayList getNeuroExamTypeColl() {
		return neuroExamTypeColl;
	}
    
    public ClinicalDataForm cloneMe() {
        ClinicalDataForm form = new ClinicalDataForm();
        form.setQueryName(queryName);
        form.setTumorGrade(tumorGrade);
        form.setTumorType(tumorType);
        form.setResultView(resultView);
        form.setFirstPresentation(firstPresentation);
        form.setRecurrence(recurrence);
        form.setRecurrenceType(recurrenceType);
        form.setRadiation(radiation);
        form.setLansky(lansky);       
        form.setLanskyType(lanskyType);        
        form.setNeuroExam(neuroExam);        
        form.setNeuroExamType(neuroExamType);
        form.setMri(mri);        
        form.setMriType(mriType);       
        form.setKarnofsky(karnofsky);       
        form.setKarnofskyType(karnofskyType);
        form.setRadiationType(radiationType);
        form.setChemo(chemo);
        form.setChemoType(chemoType);
        form.setSurgery(surgery);
        form.setSurgeryType(surgeryType);
        form.setSurvivalLower(survivalLower);
        form.setSurvivalUpper(survivalUpper);
        form.setAgeLower(ageLower);
        form.setAgeUpper(ageUpper);
        form.setGenderType(genderType);
        form.setSampleList(sampleList);
        form.setSampleFile(sampleFile);
        form.setSampleGroup(sampleGroup);
        form.setAfricanAmerican(africanAmerican);
        form.setCaucasion(caucasion);
        form.setAsianAmerican(asianAmerican);
        form.setLatino(latino);
        form.setNativeAmerican(nativeAmerican);        
        return form;
    }
    /**
     * Method validate
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        // Query Name cannot be blank

        if ((queryName == null || queryName.length() < 1))
            errors.add("queryName", new ActionError(
                    "gov.nih.nci.nautilus.ui.struts.form.queryname.no.error"));

        // survival range validations
        if (this.survivalLower != null && !this.survivalLower.equals("") && this.survivalUpper != null && !this.survivalUpper.equals("")) {
            try {
                if((survivalLower.trim().length() > 0 && !(survivalUpper.trim().length() > 0)) ||
                        (survivalUpper.trim().length() > 0 && !(survivalLower.trim().length() > 0))){
                    errors.add("survivalUpper",new ActionError("gov.nih.nci.nautilus.ui.struts.form.survivalRange.upperOrLowerMissing.error"));                    
                }
                else if (Integer.parseInt(survivalLower) >= Integer.parseInt(survivalUpper)) {
                    errors.add("survivalUpper",new ActionError("gov.nih.nci.nautilus.ui.struts.form.survivalRange.upperRange.error"));
                }
            } catch (NumberFormatException ex) {
              logger.error(ex);
            }
        }

        if (this.ageLower != null &&!this.ageLower.equals("")&& this.ageUpper != null && !this.ageUpper.equals("")) {
            try {
                if((ageLower.trim().length() > 0 && !(ageUpper.trim().length() > 0)) ||
                        (ageUpper.trim().length() > 0 && !(ageLower.trim().length() > 0))){
                    errors.add("ageUpper",new ActionError("gov.nih.nci.nautilus.ui.struts.form.survivalRange.upperOrLowerMissing.error"));                    
                }
                else if(Integer.parseInt(ageLower) >= Integer.parseInt(ageUpper)) {
                    errors.add("ageUpper",
                                  new ActionError("gov.nih.nci.nautilus.ui.struts.form.ageRange.upperRange.error"));
                }
            } catch (NumberFormatException ex) {
             
            }
        }

       

        return errors;
    }






}