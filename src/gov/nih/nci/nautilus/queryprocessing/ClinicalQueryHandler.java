package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;


import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.OccurrenceCriteria;
import gov.nih.nci.nautilus.criteria.RadiationTherapyCriteria;
import gov.nih.nci.nautilus.criteria.ChemoAgentCriteria;
import gov.nih.nci.nautilus.criteria.SurgeryTypeCriteria;
import gov.nih.nci.nautilus.criteria.SurvivalCriteria;
import gov.nih.nci.nautilus.criteria.AgeCriteria;
import gov.nih.nci.nautilus.criteria.GenderCriteria;



import java.util.*;
import java.util.Iterator;


/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:14:46 PM
 * To change this template use Options | File Templates.
 */
public class ClinicalQueryHandler extends QueryHandler {

	DiseaseOrGradeCriteria diseaseOrGradeCrit;	
	OccurrenceCriteria occurrenceCrit;
	RadiationTherapyCriteria radiationTherapyCrit;
	ChemoAgentCriteria chemoAgentCrit;	
	SurgeryTypeCriteria surgeryTypeCrit;	
	SurvivalCriteria survivalCrit;
	AgeCriteria ageCrit;
	GenderCriteria genderCrit;



    //public void handle(Query query) {
    public Map handle(Query query) {
        ClinicalDataQuery clinicalQuery = (ClinicalDataQuery) query;

        diseaseOrGradeCrit = clinicalQuery.getDiseaseOrGradeCriteria();
        occurrenceCrit = clinicalQuery.getOccurrenceCriteria();
        radiationTherapyCrit = clinicalQuery.getRadiationTherapyCriteria();
        chemoAgentCrit = clinicalQuery.getChemoAgentCriteria();
        surgeryTypeCrit = clinicalQuery.getSurgeryTypeCriteria();
        survivalCrit = clinicalQuery.getSurvivalCriteria();
        ageCrit = clinicalQuery.getAgeCriteria();
        genderCrit = clinicalQuery.getGenderCriteria();

        /*Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();) {
            GeneIdentifierDE o = (GeneIdentifierDE) iterator.next();
            if (o instanceof GeneIdentifierDE.LocusLink) {
                System.out.println("LocuLink: " + o.getValueObject());
            }
        }*/
        return null;// return null for now
    }
}
