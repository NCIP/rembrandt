package gov.nih.nci.nautilus.queryprocessing.clinical;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.data.PatientData;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.queryprocessing.CommonFactHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.FoldChangeCriteriaHandler;
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

    protected ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery clinicalQuery = (ClinicalDataQuery) query;
        Criteria allCriteria = new Criteria();

        buildSurvivalRangeCrit(clinicalQuery, allCriteria);
        buildAgeRangeCrit(clinicalQuery, allCriteria);
        buildGenderCrit(clinicalQuery, allCriteria);

        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
        Class targetFactClass = PatientData.class;
        CommonFactHandler.addDiseaseCriteria(clinicalQuery, targetFactClass, _BROKER, allCriteria);
        CommonFactHandler.addSampleIDCriteria(clinicalQuery, targetFactClass, allCriteria);
        _BROKER.close();

        PatientData[] results = executeQuery(allCriteria);
        return results;
    }

	private PatientData[] executeQuery(Criteria allCriteria) throws Exception {
        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        ReportQueryByCriteria sampleQuery = QueryFactory.newReportQuery(PatientData.class, allCriteria, true);
        sampleQuery.setAttributes(new String[] {
                        PatientData.BIOSPECIMEN_ID, PatientData.GENDER,
                        PatientData.DISEASE_TYPE, PatientData.AGE_GROUP,
                        PatientData.SAMPLE_ID,
                        PatientData.SURVIVAL_LENGTH_RANGE} );

        Iterator patientDataObjects =  pb.getReportQueryIteratorByQuery(sampleQuery );
        ArrayList results = new ArrayList();
        populateResults(patientDataObjects, results);

        PatientData[] finalResult = new PatientData[results.size()];
        for (int i = 0; i < results.size(); i++) {
            PatientData patientData = (PatientData) results.get(i);
            finalResult[i]  = patientData ;
        }
        pb.close();
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

    private void buildSurvivalRangeCrit(ClinicalDataQuery cghQuery, Criteria survivalCrit) {
        SurvivalCriteria crit = cghQuery.getSurvivalCriteria();
        if (crit != null) {
            long lowerLmtInMons = crit.getLowerSurvivalRange().getValueObject().longValue();
            long lowerLmtInDays  = lowerLmtInMons * 30;
            long upperLmtInMons = crit.getUpperSurvivalRange().getValueObject().longValue();
            long upperLmtInDays = upperLmtInMons * 30;
            survivalCrit.addBetween(PatientData.SURVIVAL_LENGTH, new Long(lowerLmtInDays), new Long(upperLmtInDays));
        }
    }
    private void buildAgeRangeCrit(ClinicalDataQuery cghQuery, Criteria ageCrit ) {
        AgeCriteria crit = cghQuery.getAgeCriteria();
        if (crit != null) {
            long lowerLmtInYrs= crit.getLowerAgeLimit().getValueObject().longValue();
            long upperLmtInYrs  = crit.getUpperAgeLimit().getValueObject().longValue();
            ageCrit.addBetween(PatientData.AGE, new Long(lowerLmtInYrs), new Long(upperLmtInYrs));
        }
    }
    private void buildGenderCrit(ClinicalDataQuery cghQuery, Criteria genderCrit) {
        GenderCriteria crit = cghQuery.getGenderCriteria();
        if (crit != null) {
            genderCrit.addEqualTo(PatientData.GENDER, crit.getGenderDE().getValueObject());
        }
    }
}