package gov.nih.nci.nautilus.queryprocessing.clinical;

import gov.nih.nci.nautilus.criteria.AgeCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GenderCriteria;
import gov.nih.nci.nautilus.criteria.SurvivalCriteria;
import gov.nih.nci.nautilus.data.PatientData;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.queryprocessing.ge.FactCriteriaHandler;
import gov.nih.nci.nautilus.resultset.ResultSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Nov 4, 2004
 * Time: 12:20:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalQueryHandler extends QueryHandler {
    private Collection allBIOSpecimenIDs = Collections.synchronizedCollection(new HashSet());
    private Collection bioSpecimenLists = Collections.synchronizedCollection(new Vector());
    private List eventList = Collections.synchronizedList(new ArrayList());
    protected ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery cghQuery = (ClinicalDataQuery) query;
        
        if (cghQuery.getSurvivalCriteria() != null) {
            BIOSpecimenIDCriteria survivalCrit = buildSurvivalRangeCrit(cghQuery);
            assert(survivalCrit!= null);
            Collection bioSpecimenIDs = new HashSet();
            SelectHandler handler = new SelectHandler.SurivalRangeSelectHandler(survivalCrit, bioSpecimenIDs);            
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();   
            	bioSpecimenLists.add(bioSpecimenIDs);
        }

        if (cghQuery.getAgeCriteria() != null) {
            BIOSpecimenIDCriteria ageCrit = buildAgeRangeCrit(cghQuery);
            assert(ageCrit != null);
            Collection bioSpecimenIDs = new HashSet();
            SelectHandler handler = new SelectHandler.AgeRangeSelectHandler(ageCrit, bioSpecimenIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();           
            	bioSpecimenLists.add(bioSpecimenIDs);
        }

         if (cghQuery.getGenderCriteria() != null) {
            BIOSpecimenIDCriteria genderCrit = buildGenderCrit(cghQuery);
            assert(genderCrit != null);
            Collection bioSpecimenIDs = new HashSet();
            SelectHandler handler = new SelectHandler.GenderSelectHandler(genderCrit, bioSpecimenIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
            	bioSpecimenLists.add(bioSpecimenIDs);
        }
        ThreadController.sleepOnEvents(eventList);
        allBIOSpecimenIDs = ensureDistinctBioSpenimenIds();
        PatientData[] results = executeQuery(cghQuery);
        //return null;
        return results;
    }

    /**
	 * @return
	 */
	private Collection ensureDistinctBioSpenimenIds() {
		Set sampleIds =  new HashSet();
		for(Iterator iterator = bioSpecimenLists.iterator(); iterator.hasNext();){
			Collection  bioSpecimenIDCollection = (Collection) iterator.next();
			if(sampleIds.isEmpty()){
				sampleIds.addAll(bioSpecimenIDCollection);
			}
			sampleIds.retainAll(bioSpecimenIDCollection);
		}
		//	in place to ensure that if none of the conditions are met
		//  then no records are selected as no records will ever have the -1 id
		if(sampleIds.isEmpty() && bioSpecimenLists.size() > 0){  
			Long ldpID = new Long(-1);
			sampleIds.add(ldpID);
		}
		return sampleIds;
	}

	private PatientData[] executeQuery(ClinicalDataQuery cghQuery) throws Exception {
        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        final Criteria sampleCrit = new Criteria();
        DiseaseOrGradeCriteria diseaseCrit = cghQuery.getDiseaseOrGradeCriteria();
        if(allBIOSpecimenIDs.size() > 0){
        	sampleCrit.addIn(PatientData.BIOSPECIMEN_ID, allBIOSpecimenIDs);
        }
        if (diseaseCrit != null)
        FactCriteriaHandler.addDiseaseCriteria(diseaseCrit, PatientData.class, pb, sampleCrit);
        ReportQueryByCriteria sampleQuery = QueryFactory.newReportQuery(PatientData.class, sampleCrit, true);
        sampleQuery.setAttributes(new String[] {
                        PatientData.BIOSPECIMEN_ID, PatientData.GENDER,
                        PatientData.DISEASE_TYPE, PatientData.AGE_GROUP,
                        PatientData.SAMPLE_ID,
                        PatientData.SURVIVAL_LENGTH_RANGE} );

        assert(sampleQuery != null);
        Iterator patientDataObjects =  pb.getReportQueryIteratorByQuery(sampleQuery );
        ArrayList results = new ArrayList();

        populateResults(patientDataObjects, results);
        PatientData[] finalResult = new PatientData[results.size()];
        for (int i = 0; i < results.size(); i++) {
            PatientData patientData = (PatientData) results.get(i);
            finalResult[i]  = patientData ;
        }

        return finalResult;
    }

    private void populateResults(Iterator patientDataObjects, ArrayList results) {
        while(patientDataObjects.hasNext()) {
            Object[] objs = (Object[]) patientDataObjects.next();
            Long bspID = new Long(((BigDecimal)objs[0]).longValue());
            String gender = (String)objs[1];
            String diseaseType = (String)objs[2];
            String ageGroup = (String)objs[3];
            String sampleID = (String)objs[4];
            String survLenRange = (String)objs[5];
            PatientData p = new PatientData();
            p.setBiospecimenId(bspID);
            p.setGender(gender);
            p.setDiseaseType(diseaseType);
            p.setAgeGroup(ageGroup);
            p.setSampleId(sampleID);
            p.setSurvivalLengthRange(survLenRange);

            results.add(p );
        }
    }

    private BIOSpecimenIDCriteria buildSurvivalRangeCrit(ClinicalDataQuery cghQuery) {
        SurvivalCriteria crit = cghQuery.getSurvivalCriteria();
        long lowerLmtInMons = crit.getLowerSurvivalRange().getValueObject().longValue();
        long lowerLmtInDays  = lowerLmtInMons * 30;
        long upperLmtInMons = crit.getUpperSurvivalRange().getValueObject().longValue();
        long upperLmtInDays = upperLmtInMons * 30;
        Criteria survivalCrit = new Criteria();
        survivalCrit.addBetween(PatientData.SURVIVAL_LENGTH, new Long(lowerLmtInDays), new Long(upperLmtInDays));
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  survivalCrit, false );
        bioIDQuery.setAttributes(new String[] {PatientData.BIOSPECIMEN_ID} );
        BIOSpecimenIDCriteria bioCrit = new BIOSpecimenIDCriteria();
        bioCrit.setBioSpecimenIDSubQuery(bioIDQuery);
        return bioCrit;
    }
    private BIOSpecimenIDCriteria buildAgeRangeCrit(ClinicalDataQuery cghQuery) {
        AgeCriteria crit = cghQuery.getAgeCriteria();
        long lowerLmtInYrs= crit.getLowerAgeLimit().getValueObject().longValue();
        long upperLmtInYrs  = crit.getUpperAgeLimit().getValueObject().longValue();
        Criteria ageCrit = new Criteria();
        ageCrit.addBetween(PatientData.AGE, new Long(lowerLmtInYrs), new Long(upperLmtInYrs));
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  ageCrit , false );
        bioIDQuery.setAttributes(new String[] {PatientData.BIOSPECIMEN_ID} );
        BIOSpecimenIDCriteria bioCrit = new BIOSpecimenIDCriteria();
        bioCrit.setBioSpecimenIDSubQuery(bioIDQuery);
        return bioCrit;
    }
    private BIOSpecimenIDCriteria buildGenderCrit(ClinicalDataQuery cghQuery) {
        GenderCriteria genderCrit = cghQuery.getGenderCriteria();
        Criteria ageCrit = new Criteria();
        ageCrit.addEqualTo(PatientData.GENDER, genderCrit.getGenderDE().getValueObject());
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  ageCrit, false );
        bioIDQuery.setAttributes(new String[] {PatientData.BIOSPECIMEN_ID} );
        BIOSpecimenIDCriteria bioCrit = new BIOSpecimenIDCriteria();
        bioCrit.setBioSpecimenIDSubQuery(bioIDQuery);
        return bioCrit;
    }
}