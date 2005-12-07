package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ChemoAgentCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.KarnofskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.LanskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.MRIClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.NeuroExamClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OccurrenceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PriorSurgeryTitleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RaceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RadiationTherapyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurgeryOutcomeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.de.ChemoAgentDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.dto.de.KarnofskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.LanskyClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.MRIClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.NeuroExamClinicalEvalDE;
import gov.nih.nci.caintegrator.dto.de.PriorSurgeryTitleDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;
import gov.nih.nci.caintegrator.dto.de.OccurrenceDE;
import gov.nih.nci.caintegrator.dto.de.RadiationTherapyDE;
import gov.nih.nci.caintegrator.dto.de.SurgeryOutcomeDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.clinical.ClinicalQueryHandler;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
/**
 * This class is used by the pre-Rembrandt 1.0 query mechanism.  At this time
 * the ClinicalQueryDTO in the caIntegrator framework is just a marker interface
 * but will later change to contain the getters and setters required for the DEs
 * of the query criteria. 
 */

public class ClinicalDataQuery extends Query implements Serializable,Cloneable,ClinicalQueryDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private Logger logger = Logger.getLogger(ClinicalDataQuery.class);

	private AgeCriteria ageCriteria;

	private ChemoAgentCriteria chemoAgentCriteria;

	private GenderCriteria genderCriteria;

	private OccurrenceCriteria occurrenceCriteria;

	private QueryHandler HANDLER;

	private RadiationTherapyCriteria radiationTherapyCriteria;

	private SurgeryOutcomeCriteria surgeryOutcomeCriteria;
	
	private PriorSurgeryTitleCriteria priorSurgeryTitleCriteria;

	private SurvivalCriteria survivalCriteria;
	
	private RaceCriteria raceCriteria;
	
	
	private  KarnofskyClinicalEvalCriteria karnofskyCriteria;
	    
	private  LanskyClinicalEvalCriteria lanskyCriteria ;
	    
	private  MRIClinicalEvalCriteria mriCriteria ;	    
	    
	private  NeuroExamClinicalEvalCriteria neuroExamCriteria ;    

	public QueryHandler getQueryHandler() throws Exception {
		return (HANDLER == null) ? new ClinicalQueryHandler() : HANDLER;
	}

	public QueryType getQueryType() throws Exception {
		return QueryType.CLINICAL_DATA_QUERY_TYPE;
	}

	public ClinicalDataQuery() {
		super();
	}

	public String toString() {
		ResourceBundle labels = null;
		String OutStr = "<B>Clinical Data Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>"
				+ this.getQueryName();

		try {

			labels = ResourceBundle.getBundle(
					RembrandtConstants.APPLICATION_RESOURCES, Locale.US);

			// starting DiseaseOrGradeCriteria
			DiseaseOrGradeCriteria thisDiseaseCrit = this
					.getDiseaseOrGradeCriteria();
			if ((thisDiseaseCrit != null) && !thisDiseaseCrit.isEmpty()
					&& labels != null) {
				Collection diseaseColl = thisDiseaseCrit.getDiseases();

				String thisCriteria = thisDiseaseCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = diseaseColl.iterator();
				while (iter.hasNext()) {
					DiseaseNameDE diseaseDE = (DiseaseNameDE) iter.next();
					OutStr += "&nbsp;&nbsp;" + ((String) diseaseDE.getValue())
							+ " ";
				}
			} else {
				logger
						.debug("Disease Criteria is empty or Application Resources file is missing");
			} // end of DiseaseOrGradeCriteria

			// starting OccurrenceCriteria
			OccurrenceCriteria thisOccurrenceCriteria = this
					.getOccurrenceCriteria();
			if ((thisOccurrenceCriteria != null)
					&& thisOccurrenceCriteria.isEmpty() && labels != null) {
				String thisCriteria = thisOccurrenceCriteria.getClass()
						.getName();
				OutStr += "<BR>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1));

				Collection occurrenceColl = thisOccurrenceCriteria
						.getOccurrences();
				Iterator iter = occurrenceColl.iterator();

				while (iter.hasNext()) {
					OccurrenceDE occurrenceDE = (OccurrenceDE) iter.next();
					String occurrenceStr = occurrenceDE.getClass().getName();
					if (occurrenceDE.getValueObject().equalsIgnoreCase(
							"first Presentation")) {
						OutStr += "<BR>&nbsp;&nbsp;" + ": "
								+ occurrenceDE.getValue() + "";
					} else {
						OutStr += "<BR>&nbsp;&nbsp;" + ": "
								+ occurrenceDE.getValue() + " (recurrence)";
					}

				}
			}

			else {
				logger
						.debug("OccurrenceCriteria is empty or Application Resources file is missing.");
			}// end of OccurrenceCriteria

			SampleCriteria thisSampleIDCrit = this.getSampleIDCrit();

			if ((thisSampleIDCrit != null) && !thisSampleIDCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisSampleIDCrit.getClass().getName();

				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection sampleIDObjects = thisSampleIDCrit.getSampleIDs();
				int count = 0;
				for (Iterator iter = sampleIDObjects.iterator(); iter.hasNext()
						&& count < 5;) {
					count++;
					DomainElement de = (DomainElement) iter.next();
					String thisDomainElement = de.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(thisDomainElement
									.substring(thisDomainElement
											.lastIndexOf(".") + 1)) + ": "
							+ de.getValue();
				}
				if (sampleIDObjects != null && sampleIDObjects.size() > 5) {
					OutStr += "<BR>&nbsp;&nbsp;...";
				}
			} else
				logger
						.debug("Sample ID Criteria is empty or Application Resources file is missing");

			// starting RadiationTherapyCriteria
			RadiationTherapyCriteria thisRadiationTherapyCriteria = this.getRadiationTherapyCriteria();
			if ((thisRadiationTherapyCriteria != null)&& !thisRadiationTherapyCriteria.isEmpty()&& labels != null) {
				
				
				Collection radiationColl = thisRadiationTherapyCriteria.getRadiations();

				String thisCriteria = thisRadiationTherapyCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = radiationColl.iterator();
				while (iter.hasNext()) {
					RadiationTherapyDE radiationTherapyDE = (RadiationTherapyDE) iter.next();
					OutStr += "" + ((String) radiationTherapyDE.getValue())
							+ " ";
				}
			
					
			}

			else {
				logger
						.debug("Radiation Criteria is empty or Application Resources file is missing.");
			}// end of RadiationCriteria
			
			
			
			// starting chemoTherapyCriteria
			ChemoAgentCriteria thisChemoTherapyCriteria = this.getChemoAgentCriteria();
			if ((thisChemoTherapyCriteria != null)&& !thisChemoTherapyCriteria.isEmpty()&& labels != null) {
				
				
				Collection chemoColl = thisChemoTherapyCriteria.getAgents();

				String thisCriteria = thisChemoTherapyCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = chemoColl.iterator();
				while (iter.hasNext()) {
					ChemoAgentDE chemoAgentDE = (ChemoAgentDE) iter.next();
					OutStr += "" + ((String) chemoAgentDE.getValue())
							+ " ";
				}
			
					
			}

			else {
				logger
						.debug("Chemo Agent Criteria is empty or Application Resources file is missing.");
			}// end of Chemo Agent Criteria



			// starting SurgeryTypeCriteria
			SurgeryOutcomeCriteria thisSurgeryOutcomeCriteria = this
					.getSurgeryOutcomeCriteria();
			if ((thisSurgeryOutcomeCriteria != null)
					&& !thisSurgeryOutcomeCriteria.isEmpty() && labels != null) {
				
				Collection outcomeColl = thisSurgeryOutcomeCriteria.getOutcomes();

				String thisCriteria = thisSurgeryOutcomeCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = outcomeColl.iterator();
				while (iter.hasNext()) {
					SurgeryOutcomeDE surgeryOutcomeDE = (SurgeryOutcomeDE) iter.next();
					OutStr += "" + ((String) surgeryOutcomeDE.getValue())
							+ " ";
				}
			
					
			}
				
				else {
				logger
						.debug("SurgeryOutcomeCriteria is empty or Application Resources file is missing.");
			}// end of SurgeryTypeCriteria

			
			// starting PriorSurgeryTitleCriteria
			PriorSurgeryTitleCriteria thisPriorSurgeryTitleCriteria = this
					.getPriorSurgeryTitleCriteria();
			if ((thisPriorSurgeryTitleCriteria != null)
					&& !thisPriorSurgeryTitleCriteria.isEmpty() && labels != null) {
				
				Collection titleColl = thisPriorSurgeryTitleCriteria.getTitles();

				String thisCriteria = thisPriorSurgeryTitleCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = titleColl.iterator();
				while (iter.hasNext()) {
					PriorSurgeryTitleDE priorSurgeryTitleDE = (PriorSurgeryTitleDE) iter.next();
					OutStr += "" + ((String) priorSurgeryTitleDE.getValue())
							+ " ";
				}
			
					
			}				
				
				
		  else {
				logger
						.debug("PriorSurgeryTitleCriteria is empty or Application Resources file is missing.");
			}// end of SurgeryTypeCriteria

			SurvivalCriteria thisSurvivalCriteria = this.getSurvivalCriteria();
			if ((thisSurvivalCriteria != null)
					&& !thisSurvivalCriteria.isEmpty() && labels != null) {
				String thisCriteria = thisSurvivalCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</b>";

				DomainElement survivalLowerDE = thisSurvivalCriteria
						.getLowerSurvivalRange();
				DomainElement survivalUpperDE = thisSurvivalCriteria
						.getUpperSurvivalRange();
				if (survivalLowerDE != null && survivalUpperDE != null) {
					String survivalLowerStr = survivalLowerDE.getClass()
							.getName();
					String survivalUpperStr = survivalUpperDE.getClass()
							.getName();
					OutStr += "<BR><B class='otherBold'>&nbsp;&nbsp;"
							+ labels.getString(survivalLowerStr
									.substring(survivalLowerStr
											.lastIndexOf(".") + 1))
							+ ":</b><br />&nbsp;&nbsp;&nbsp;"
							+ survivalLowerDE.getValue() + " (months)";
					OutStr += "<BR><B class='otherBold'>&nbsp;&nbsp;"
							+ labels.getString(survivalUpperStr
									.substring(survivalUpperStr
											.lastIndexOf(".") + 1))
							+ ":</b><br />&nbsp;&nbsp;&nbsp;"
							+ survivalUpperDE.getValue() + " (months)";
				}
			} else {
				logger
						.debug("SurvivalCriteria is empty or Application Resources file is missing.");
			}// end of SurvivalCriteria

			// starting AgeCriteria
			AgeCriteria thisAgeCriteria = this.getAgeCriteria();
			if ((thisAgeCriteria != null) && !thisAgeCriteria.isEmpty()
					&& labels != null) {
				String thisCriteria = ageCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</b>";
				DomainElement LowerAgeLimit = thisAgeCriteria
						.getLowerAgeLimit();
				DomainElement UpperAgeLimit = thisAgeCriteria
						.getUpperAgeLimit();
				if (LowerAgeLimit != null && UpperAgeLimit != null) {
					String ageLowerStr = LowerAgeLimit.getClass().getName();
					String ageUpperStr = UpperAgeLimit.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;<B class='otherBold'>"
							+ labels
									.getString(ageLowerStr
											.substring(ageLowerStr
													.lastIndexOf(".") + 1))
							+ ":</b><br />&nbsp;&nbsp;&nbsp;"
							+ LowerAgeLimit.getValue() + " (years)";
					OutStr += "<BR>&nbsp;&nbsp;<B class='otherBold'>"
							+ labels
									.getString(ageUpperStr
											.substring(ageUpperStr
													.lastIndexOf(".") + 1))
							+ ":</b><br />&nbsp;&nbsp;&nbsp;"
							+ UpperAgeLimit.getValue() + " (years)";

				}
			} else {
				logger
						.debug("AgeCriteria is empty or Application Resources file is missing.");
			}// end of AgeCriteria

			// starting GenderCriteria

			GenderCriteria thisGenderCriteria = this.getGenderCriteria();
			if ((thisGenderCriteria != null) && !thisGenderCriteria.isEmpty()
					&& labels != null) {
				GenderDE genderDE = thisGenderCriteria.getGenderDE();
				String genderStr = genderDE.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(genderStr.substring(genderStr
								.lastIndexOf(".") + 1)) + ":</B><BR> ";
				OutStr += "&nbsp;&nbsp;" + ((String) genderDE.getValue()) + " ";
			} else {
				logger
						.debug("GenderCriteria is empty or Application Resources file is missing.");
			}// end of GenderCriteria
			
			
			
			// starting RaceCriteria
			RaceCriteria thisRaceCriteria = this.getRaceCriteria();
			if ((thisRaceCriteria != null)
					&& !thisRaceCriteria.isEmpty() && labels != null) {
				String thisCriteria = thisRaceCriteria.getClass()
						.getName();				
				
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";				
				

				Collection raceColl = thisRaceCriteria.getRaces();
				Iterator iter = raceColl.iterator();

				while (iter.hasNext()) {
					RaceDE raceDE = (RaceDE) iter.next();
					OutStr += "&nbsp;&nbsp;" + ((String) raceDE.getValue())
					+ " ";

				}		

				
			}

			else {
				logger
						.debug("RaceCriteria is empty or Application Resources file is missing.");
			}// end of RaceCriteria

			
			
            //	starting karnofskyCriteria
			KarnofskyClinicalEvalCriteria thisKarnofskyCriteria = this.getKarnofskyCriteria();
			if ((thisKarnofskyCriteria != null)
					&& !thisKarnofskyCriteria.isEmpty() && labels != null) {
				KarnofskyClinicalEvalDE karnofskyDE = thisKarnofskyCriteria.getKarnofskyClinicalEvalDE();
				String karnofskyStr = karnofskyDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(karnofskyStr.substring(karnofskyStr
								.lastIndexOf(".") + 1)) + ": "
						+ karnofskyDE.getValue() + "";
			}

			else {
				logger
						.debug("karnofskyCriteria is empty or Application Resources file is missing.");
			}// end of karnofskyCriteria
			
			
			 //	starting lanskyCriteria
			LanskyClinicalEvalCriteria thisLanskyCriteria = this.getLanskyCriteria();
			if ((thisLanskyCriteria != null)
					&& !thisLanskyCriteria.isEmpty() && labels != null) {
				LanskyClinicalEvalDE lanskyDE = thisLanskyCriteria.getLanskyClinicalEvalDE();
				String lanskyStr = lanskyDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(lanskyStr.substring(lanskyStr
								.lastIndexOf(".") + 1)) + ": "
						+ lanskyDE.getValue() + "";
			}

			else {
				logger
						.debug("lanskyCriteria is empty or Application Resources file is missing.");
			}// end of lanskyCriteria
			
			
			 //	starting mriCriteria
			MRIClinicalEvalCriteria thisMRICriteria = this.getMriCriteria();
			if ((thisMRICriteria != null)
					&& !thisMRICriteria.isEmpty() && labels != null) {
				MRIClinicalEvalDE mriDE = thisMRICriteria.getMRIClinicalEvalDE();
				String mriStr = mriDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(mriStr.substring(mriStr
								.lastIndexOf(".") + 1)) + ": "
						+ mriDE.getValue() + "";
			}

			else {
				logger
						.debug("mriCriteria is empty or Application Resources file is missing.");
			}// end of mriCriteria
    
			// start NeuroExamClinicalEvalCriteria
			NeuroExamClinicalEvalCriteria thisNeuroExamCriteria = this.getNeuroExamCriteria();
			if ((thisNeuroExamCriteria != null)
					&& !thisNeuroExamCriteria.isEmpty() && labels != null) {
				NeuroExamClinicalEvalDE neuroExamDE = thisNeuroExamCriteria.getNeuroExamClinicalEvalDE();
				String neuroExamStr = neuroExamDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(neuroExamStr.substring(neuroExamStr
								.lastIndexOf(".") + 1)) + ": "
						+ neuroExamDE.getValue() + "";
			}

			else {
				logger
						.debug("NeuroExamClinicalEvalCriteria is empty or Application Resources file is missing.");
			}// end of NeuroExamClinicalEvalCriteria

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

	public void setRadiationTherapyCrit(
			RadiationTherapyCriteria radiationTherapyCriteria) {
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

	public SampleCriteria getSampleIDCrit() {
		return sampleIDCrit;
	}

	public void setSampleIDCrit(SampleCriteria sampleIDCrit) {
		this.sampleIDCrit = sampleIDCrit;
	}

	public SurgeryOutcomeCriteria getSurgeryOutcomeCriteria() {
		return surgeryOutcomeCriteria;
	}

	public void setSurgeryOutcomeCrit(SurgeryOutcomeCriteria surgeryOutcomeCriteria) {
		this.surgeryOutcomeCriteria = surgeryOutcomeCriteria;
	}

	public void setPriorSurgeryTitleCrit(PriorSurgeryTitleCriteria priorSurgeryTitleCriteria) {
		this.priorSurgeryTitleCriteria = priorSurgeryTitleCriteria;
	}
	public SurvivalCriteria getSurvivalCriteria() {
		return survivalCriteria;
	}

	public void setSurvivalCrit(SurvivalCriteria survivalCriteria) {
		this.survivalCriteria = survivalCriteria;
	}

	public AgeCriteria getAgeCriteria() {
		return ageCriteria;
	}

	public void setAgeCrit(AgeCriteria ageCriteria) {
		this.ageCriteria = ageCriteria;
	}

	public GenderCriteria getGenderCriteria() {
		return genderCriteria;
	}

	public void setGenderCrit(GenderCriteria genderCriteria) {
		this.genderCriteria = genderCriteria;
	}
	
	public RaceCriteria getRaceCriteria() {
		return raceCriteria;
	}
	
	public void setRaceCrit(RaceCriteria raceCriteria) {
		this.raceCriteria = raceCriteria;
	}
	
	
	/**
	 * @return Returns the karnofskyCriteria.
	 */
	public KarnofskyClinicalEvalCriteria getKarnofskyCriteria() {
		return karnofskyCriteria;
	}

	/**
	 * @param karnofskyCriteria The karnofskyCriteria to set.
	 */
	public void setKarnofskyCriteria(KarnofskyClinicalEvalCriteria karnofskyCriteria) {
		this.karnofskyCriteria = karnofskyCriteria;
	}

	/**
	 * @return Returns the lanskyCriteria.
	 */
	public LanskyClinicalEvalCriteria getLanskyCriteria() {
		return lanskyCriteria;
	}

	/**
	 * @param lanskyCriteria The lanskyCriteria to set.
	 */
	public void setLanskyCriteria(LanskyClinicalEvalCriteria lanskyCriteria) {
		this.lanskyCriteria = lanskyCriteria;
	}

	/**
	 * @return Returns the mriCriteria.
	 */
	public MRIClinicalEvalCriteria getMriCriteria() {
		return mriCriteria;
	}

	/**
	 * @param mriCriteria The mriCriteria to set.
	 */
	public void setMriCriteria(MRIClinicalEvalCriteria mriCriteria) {
		this.mriCriteria = mriCriteria;
	}

	/**
	 * @return Returns the neuroExamCriteria.
	 */
	public NeuroExamClinicalEvalCriteria getNeuroExamCriteria() {
		return neuroExamCriteria;
	}

	/**
	 * @param neuroExamCriteria The neuroExamCriteria to set.
	 */
	public void setNeuroExamCriteria(NeuroExamClinicalEvalCriteria neuroExamCriteria) {
		this.neuroExamCriteria = neuroExamCriteria;
	}

	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		ClinicalDataQuery myClone = null;
		// Call the Query class clone method.
		myClone = (ClinicalDataQuery) super.clone();
		// add all the data fields of this subclass

        if(ageCriteria != null){
            myClone.ageCriteria = (AgeCriteria) ageCriteria.clone();
        }
        if(chemoAgentCriteria != null){
            myClone.chemoAgentCriteria = (ChemoAgentCriteria) chemoAgentCriteria.clone();
        }
        if(genderCriteria != null){
            myClone.genderCriteria = (GenderCriteria) genderCriteria.clone();
        }
        if(occurrenceCriteria != null){
            myClone.occurrenceCriteria = (OccurrenceCriteria) occurrenceCriteria.clone();
        }
        if(radiationTherapyCriteria != null){
            myClone.radiationTherapyCriteria = (RadiationTherapyCriteria) radiationTherapyCriteria.clone();
        }
        if(surgeryOutcomeCriteria != null){
            myClone.surgeryOutcomeCriteria = (SurgeryOutcomeCriteria) surgeryOutcomeCriteria.clone();
        }
        if(survivalCriteria != null){
            myClone.survivalCriteria = (SurvivalCriteria) survivalCriteria.clone();
        }
        
        if(raceCriteria != null){
            myClone.raceCriteria = (RaceCriteria) raceCriteria.clone();
        }
        
        if(karnofskyCriteria != null){
            myClone.karnofskyCriteria = (KarnofskyClinicalEvalCriteria) karnofskyCriteria.clone();
        }
        
        if(lanskyCriteria != null){
            myClone.lanskyCriteria = (LanskyClinicalEvalCriteria) lanskyCriteria.clone();
        }
        
        if(neuroExamCriteria != null){
            myClone.neuroExamCriteria = (NeuroExamClinicalEvalCriteria) neuroExamCriteria.clone();
        }
        
        if(mriCriteria != null){
            myClone.mriCriteria = (MRIClinicalEvalCriteria) mriCriteria.clone();
        }
        
		return myClone;
	}

	/**
	 * Returns an exact copy ClinicalDataQuery using the classes public clone()
	 * method
	 * 
	 * @return --a copy of this ClinicalDataQuery
	 */
	public ClinicalDataQuery cloneMe() {
		ClinicalDataQuery myClone = (ClinicalDataQuery) this.clone();
		return myClone;

	}

	class Handler {
	}

	public boolean validate() throws ValidationException {
	/** TO DO
	 /	if(((ageCriteria != null && ageCriteria.isValid() ) ||
			((agechemoAgentCriteria != null && chemoAgentCriteria.isValid() ) ||
			((occurrenceCriteria != null && occurrenceCriteria.isValid() ) ||
			((radiationTherapyCriteria != null && radiationTherapyCriteria.isValid() ) ||
			((surgeryTypeCriteria != null && surgeryTypeCriteria.isValid() ) ||
			((survivalCriteria != null && survivalCriteria.isValid() ) )
			{
				return true;
			}
     return false;
     */
		return true;
	}

	/**
	 * @return Returns the priorSurgeryTitleCriteria.
	 */
	public PriorSurgeryTitleCriteria getPriorSurgeryTitleCriteria() {
		return priorSurgeryTitleCriteria;
	}
}
