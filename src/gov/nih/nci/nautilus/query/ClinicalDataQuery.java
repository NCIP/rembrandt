package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.AgeCriteria;
import gov.nih.nci.nautilus.criteria.ChemoAgentCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GenderCriteria;
import gov.nih.nci.nautilus.criteria.OccurrenceCriteria;
import gov.nih.nci.nautilus.criteria.RadiationTherapyCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.criteria.SurgeryTypeCriteria;
import gov.nih.nci.nautilus.criteria.SurvivalCriteria;
import gov.nih.nci.nautilus.de.ChemoAgentDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
import gov.nih.nci.nautilus.de.GenderDE;
import gov.nih.nci.nautilus.de.OccurrenceDE;
import gov.nih.nci.nautilus.de.RadiationTherapyDE;
import gov.nih.nci.nautilus.de.SurgeryTypeDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.clinical.ClinicalQueryHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ClinicalDataQuery extends Query implements Serializable,Cloneable {
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

	private SurgeryTypeCriteria surgeryTypeCriteria;

	private SurvivalCriteria survivalCriteria;

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
					NautilusConstants.APPLICATION_RESOURCES, Locale.US);

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
			RadiationTherapyCriteria thisRadiationTherapyCriteria = this
					.getRadiationTherapyCriteria();
			if ((thisRadiationTherapyCriteria != null)
					&& !thisRadiationTherapyCriteria.isEmpty()
					&& labels != null) {
				RadiationTherapyDE radiationTherapyDE = thisRadiationTherapyCriteria
						.getRadiationTherapyDE();
				String radiationStr = radiationTherapyDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(radiationStr.substring(radiationStr
								.lastIndexOf(".") + 1)) + ": "
						+ radiationTherapyDE.getValue() + "";

			} else {
				logger
						.debug("RadiationTherapyCriteria is empty or Application Resources file is missing.");
			}// end of RadiationTherapyCriteria

			// starting ChemoAgentCriteria
			ChemoAgentCriteria thisChemoAgentCriteria = this
					.getChemoAgentCriteria();
			if ((thisChemoAgentCriteria != null)
					&& !thisChemoAgentCriteria.isEmpty() && labels != null) {
				ChemoAgentDE chemoAgentDE = thisChemoAgentCriteria
						.getChemoAgentDE();
				String chemoStr = chemoAgentDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(chemoStr.substring(chemoStr
								.lastIndexOf(".") + 1)) + ": "
						+ chemoAgentDE.getValue() + "";
			}

			else {
				logger
						.debug("ChemoAgentCriteria is empty or Application Resources file is missing.");
			}// end of ChemoAgentCriteria

			// starting SurgeryTypeCriteria
			SurgeryTypeCriteria thisSurgeryTypeCriteria = this
					.getSurgeryTypeCriteria();
			if ((thisSurgeryTypeCriteria != null)
					&& !thisSurgeryTypeCriteria.isEmpty() && labels != null) {
				SurgeryTypeDE surgeryTypeDE = thisSurgeryTypeCriteria
						.getSurgeryTypeDE();
				String surgeryStr = surgeryTypeDE.getClass().getName();
				OutStr += "<BR>"
						+ labels.getString(surgeryStr.substring(surgeryStr
								.lastIndexOf(".") + 1)) + ": "
						+ surgeryTypeDE.getValue() + "";
			} else {
				logger
						.debug("SurgeryTypeCriteria is empty or Application Resources file is missing.");
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

	public SurgeryTypeCriteria getSurgeryTypeCriteria() {
		return surgeryTypeCriteria;
	}

	public void setSurgeryTypeCrit(SurgeryTypeCriteria surgeryTypeCriteria) {
		this.surgeryTypeCriteria = surgeryTypeCriteria;
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
        if(surgeryTypeCriteria != null){
            myClone.surgeryTypeCriteria = (SurgeryTypeCriteria) surgeryTypeCriteria.clone();
        }
        if(survivalCriteria != null){
            myClone.survivalCriteria = (SurvivalCriteria) survivalCriteria.clone();
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
}
