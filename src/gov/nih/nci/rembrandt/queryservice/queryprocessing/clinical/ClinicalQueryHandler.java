package gov.nih.nci.rembrandt.queryservice.queryprocessing.clinical;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RaceCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CommonFactHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
 */
public class ClinicalQueryHandler extends QueryHandler {

    public ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery clinicalQuery = (ClinicalDataQuery) query;
        Criteria allCriteria = new Criteria();

        buildSurvivalRangeCrit(clinicalQuery, allCriteria);
        buildAgeRangeCrit(clinicalQuery, allCriteria);
        buildGenderCrit(clinicalQuery, allCriteria);
        buildRaceCrit(clinicalQuery, allCriteria);

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
                        PatientData.SAMPLE_ID, PatientData.SURVIVAL_LENGTH_RANGE,
                        PatientData.RACE} );

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
            String race = (String)objs[6];
            PatientData p = new PatientData();
            p.setBiospecimenId(bspID);
            p.setGender(gender);           
            p.setDiseaseType(diseaseType);
            p.setAgeGroup(ageGroup);
            p.setSampleId(sampleID);
            p.setSurvivalLengthRange(survLenRange);  
			p.setRace(race);         

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
    
    private void buildRaceCrit(ClinicalDataQuery clinicalQuery, Criteria raceCrit){
    	  RaceCriteria crit = clinicalQuery.getRaceCriteria();    	  
    	  
          if (crit != null) {
        	  ArrayList raceTypes = new ArrayList();
        	  for (Iterator iterator = crit.getRaces().iterator(); iterator.hasNext();) {
        		  raceTypes.add(((RaceDE) iterator.next()).getValueObject());
        	  }
        	 raceCrit.addIn(PatientData.RACE, raceTypes);
          }          
         
       }
     }