package gov.nih.nci.nautilus.queryprocessing.clinical;

import gov.nih.nci.nautilus.queryprocessing.DBEvent;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 10, 2004
 * Time: 6:48:50 PM
 * To change this template use Options | File Templates.
 */
public abstract class SelectHandler implements Runnable {
    private BIOSpecimenIDCriteria bioSpecimenIDCritObj;
    private Collection allBioSPecimenIDS;

    DBEvent dbEvent = null;
    public DBEvent getDbEvent() {
        return dbEvent;
    }

    public void setDbEvent(DBEvent dbEvent) {
        this.dbEvent = dbEvent;
    }

    final static class SurivalRangeSelectHandler extends SelectHandler{
       public SurivalRangeSelectHandler(BIOSpecimenIDCriteria bioIDCritObj, Collection allBioIDs) {
           super(bioIDCritObj, allBioIDs, new DBEvent.SurvivalRangeRetrieveEvent());
       }
    }
    final static class AgeRangeSelectHandler extends SelectHandler{
       public AgeRangeSelectHandler (BIOSpecimenIDCriteria bioIDCritObj, Collection allBioIDs) {
           super(bioIDCritObj, allBioIDs, new DBEvent.AgeRangeRetrieveEvent());
       }
    }
    final static class GenderSelectHandler extends SelectHandler{
       public GenderSelectHandler (BIOSpecimenIDCriteria bioIDCritObj, Collection allBioIDs) {
           super(bioIDCritObj, allBioIDs, new DBEvent.GenderRetrieveEvent());
       }
    }
    public SelectHandler(BIOSpecimenIDCriteria bioIDCritObj, Collection allBioIDs,  DBEvent event) {
        this.bioSpecimenIDCritObj = bioIDCritObj;
        allBioSPecimenIDS = allBioIDs;
        dbEvent = event;
    }

    public void run() {
           PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
           _BROKER.clearCache();
           ReportQueryByCriteria p = bioSpecimenIDCritObj.getBioSpecimenIDSubQuery();
           if ( p != null) {
               Iterator iter = _BROKER.getReportQueryIteratorByQuery(p);
               while (iter.hasNext()) {
                   Object[] prbIDS = (Object[]) iter.next();
                   BigDecimal bdpID = (BigDecimal)prbIDS[0];
                   Long ldpID = new Long(bdpID.longValue());
                   allBioSPecimenIDS.add(ldpID);
               }
           }
           _BROKER.close();
           getDbEvent().setCompleted(true);
   }

}
