package gov.nih.nci.nautilus.queryprocessing.clinical;

import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.ClinicalDataQuery;
import gov.nih.nci.nautilus.de.SurvivalDE;
import gov.nih.nci.nautilus.criteria.SurvivalCriteria;
import gov.nih.nci.nautilus.data.PatientData;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Nov 4, 2004
 * Time: 12:20:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalQueryHandler extends QueryHandler {

    protected ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery cghQuery = (ClinicalDataQuery) query;
        PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        if (cghQuery.getSurvivalCriteria() != null) {
            SurvivalCriteria crit = cghQuery.getSurvivalCriteria();
            long lowerLimit = crit.getLowerSurvivalRange().getValueObject().longValue();
            long upperLimit = crit.getUpperSurvivalRange().getValueObject().longValue();

            Criteria survivalCrit = new Criteria();
            survivalCrit.addBetween(PatientData.SURVIVAL_LENGTH, new Long(lowerLimit), new Long(upperLimit));
            
        }
       return null;
    }
}