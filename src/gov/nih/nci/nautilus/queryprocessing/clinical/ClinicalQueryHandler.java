package gov.nih.nci.nautilus.queryprocessing.clinical;

import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;
import gov.nih.nci.nautilus.de.SurvivalDE;
import gov.nih.nci.nautilus.criteria.SurvivalCriteria;
import gov.nih.nci.nautilus.criteria.AgeCriteria;
import gov.nih.nci.nautilus.criteria.GenderCriteria;
import gov.nih.nci.nautilus.data.PatientData;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Nov 4, 2004
 * Time: 12:20:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalQueryHandler extends QueryHandler {
    private Collection allBIOSpecimenIDs = Collections.synchronizedCollection(new HashSet());
    private List eventList = Collections.synchronizedList(new ArrayList());
    protected ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery cghQuery = (ClinicalDataQuery) query;

        if (cghQuery.getSurvivalCriteria() != null) {
            BIOSpecimenIDCriteria survivalCrit = buildSurvivalRangeCrit(cghQuery);
            assert(survivalCrit!= null);
            SelectHandler handler = new SelectHandler.SurivalRangeSelectHandler(survivalCrit, allBIOSpecimenIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }

        if (cghQuery.getAgeCriteria() != null) {
            BIOSpecimenIDCriteria ageCrit = buildAgeRangeCrit(cghQuery);
            assert(ageCrit != null);
            SelectHandler handler = new SelectHandler.AgeRangeSelectHandler(ageCrit, allBIOSpecimenIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }

         if (cghQuery.getGenderCriteria() != null) {
            BIOSpecimenIDCriteria genderCrit = buildGenderCrit(cghQuery);
            assert(genderCrit != null);
            SelectHandler handler = new SelectHandler.GenderSelectHandler(genderCrit, allBIOSpecimenIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }

        ThreadController.sleepOnEvents(eventList);
        return null;
    }

    private BIOSpecimenIDCriteria buildSurvivalRangeCrit(ClinicalDataQuery cghQuery) {
        SurvivalCriteria crit = cghQuery.getSurvivalCriteria();
        long lowerLmtInMons = crit.getLowerSurvivalRange().getValueObject().longValue();
        long lowerLmtInDays  = lowerLmtInMons * 30;
        long upperLmtInMons = crit.getUpperSurvivalRange().getValueObject().longValue();
        long upperLmtInDays = upperLmtInMons * 30;
        Criteria survivalCrit = new Criteria();
        survivalCrit.addBetween(PatientData.SURVIVAL_LENGTH, new Long(lowerLmtInDays), new Long(upperLmtInDays));
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  survivalCrit, true );
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
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  ageCrit , true );
        bioIDQuery.setAttributes(new String[] {PatientData.BIOSPECIMEN_ID} );
        BIOSpecimenIDCriteria bioCrit = new BIOSpecimenIDCriteria();
        bioCrit.setBioSpecimenIDSubQuery(bioIDQuery);
        return bioCrit;
    }
    private BIOSpecimenIDCriteria buildGenderCrit(ClinicalDataQuery cghQuery) {
        GenderCriteria genderCrit = cghQuery.getGenderCriteria();

        Criteria ageCrit = new Criteria();
        ageCrit.addEqualTo(PatientData.GENDER, genderCrit.getGenderDE().getValueObject());
        ReportQueryByCriteria bioIDQuery = QueryFactory.newReportQuery(PatientData.class,  ageCrit, true );
        bioIDQuery.setAttributes(new String[] {PatientData.BIOSPECIMEN_ID} );
        BIOSpecimenIDCriteria bioCrit = new BIOSpecimenIDCriteria();
        bioCrit.setBioSpecimenIDSubQuery(bioIDQuery);
        return bioCrit;
    }
}