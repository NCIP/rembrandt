package gov.nih.nci.nautilus.queryprocessing.cgh;

import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.math.BigDecimal;

import gov.nih.nci.nautilus.queryprocessing.ge.*;
import gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 10, 2004
 * Time: 6:48:50 PM
 * To change this template use Options | File Templates.
 */
public abstract class SelectHandler implements Runnable {
    private CGHReporterIDCriteria reporterIDCritObj;
    private Collection allSNPProbeIDS;
    //private Collection allCloneIDS;
    DBEvent dbEvent = null;

    public DBEvent getDbEvent() {
        return dbEvent;
    }

    public void setDbEvent(DBEvent dbEvent) {
        this.dbEvent = dbEvent;
    }


    final static class RegionSelectHandler extends SelectHandler{
       public RegionSelectHandler(CGHReporterIDCriteria reporterIDCritObj, Collection allSNPProbIDs) {
           super(reporterIDCritObj, allSNPProbIDs, new DBEvent.RegionRetrieveEvent());
       }
    }
    final static class GeneIDSelectHandler extends SelectHandler{
       public GeneIDSelectHandler(CGHReporterIDCriteria reporterIDCritObj, Collection allSNPProbIDs) {
           super(reporterIDCritObj, allSNPProbIDs, new DBEvent.GeneIDRetrieveEvent());
       }
    }
    final static class SNPSelectHandler extends SelectHandler{
       public SNPSelectHandler(CGHReporterIDCriteria reporterIDCritObj, Collection allSNPProbIDs) {
           super(reporterIDCritObj, allSNPProbIDs, new DBEvent.SNPRetrieveEvent());
       }
    }

   /* final static class GeneIDSelectHandler extends SelectHandler {
       public GeneIDSelectHandler(CGHReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs, PersistenceBroker _BROKER ) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.GeneIDRetrieveEvent(), _BROKER);
       }
    }*/

    public SelectHandler(CGHReporterIDCriteria reporterIDCritObj, Collection allSNPProbIDs,  DBEvent event) {
        this.reporterIDCritObj = reporterIDCritObj;
        allSNPProbeIDS = allSNPProbIDs;
        dbEvent = event;
    }

    public void run() {
           PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
           _BROKER.clearCache();
           ReportQueryByCriteria p = reporterIDCritObj.getSnpProbeIDsSubQuery();
           if ( p != null) {
               Iterator iter = _BROKER.getReportQueryIteratorByQuery(p);
               while (iter.hasNext()) {
                   Object[] prbIDS = (Object[]) iter.next();
                   BigDecimal bdpID = (BigDecimal)prbIDS[0];
                   Long ldpID = new Long(bdpID.longValue());
                   allSNPProbeIDS.add(ldpID);
               }
           }
           getDbEvent().setCompleted(true);
           _BROKER.close();
   }

}
