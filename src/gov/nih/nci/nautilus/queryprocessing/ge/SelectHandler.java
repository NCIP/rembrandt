package gov.nih.nci.nautilus.queryprocessing.ge;

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
    private gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj;
    private Collection allProbeIDS;
    private Collection allCloneIDS;

    public DBEvent getDbEvent() {
        return dbEvent;
    }

    public void setDbEvent(DBEvent dbEvent) {
        this.dbEvent = dbEvent;
    }

    DBEvent dbEvent = null;
    //private PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();

    final static class RegionSelectHandler extends SelectHandler{
       public RegionSelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.RegionRetrieveEvent());
       }
    }

    final static class OntologySelectHandler extends SelectHandler{
       public OntologySelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.OntologyRetrieveEvent());
       }
    }

    final static class PathwaySelectHandler extends SelectHandler{
       public PathwaySelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.PathwayRetrieveEvent());
       }
    }

    final static class ProbeCloneIDSelectHandler extends SelectHandler{
       public ProbeCloneIDSelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.ProbeIDCloneIDRetrieveEvent());
       }
    }

    final static class GeneIDSelectHandler extends SelectHandler {
       public GeneIDSelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs ) {
           super(reporterIDCritObj, allProbIDs, allClnIDs, new DBEvent.GeneIDRetrieveEvent());
       }
    }

    public SelectHandler(gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj, Collection allProbIDs, Collection allClnIDs, DBEvent event) {
        this.reporterIDCritObj = reporterIDCritObj;
        allProbeIDS = allProbIDs;
        allCloneIDS = allClnIDs;
        dbEvent = event;
        //_BROKER = pb;
    }

    public void run() {
           PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
           ReportQueryByCriteria p = reporterIDCritObj.getProbeIDsSubQuery();
           if ( p != null) {
               Iterator iter = _BROKER.getReportQueryIteratorByQuery(p);
               while (iter.hasNext()) {
                   Object[] prbIDS = (Object[]) iter.next();
                   BigDecimal bdpID = (BigDecimal)prbIDS[0];
                   Long ldpID = new Long(bdpID.longValue());
                   allProbeIDS.add(ldpID);
               }
           }
           ReportQueryByCriteria c = reporterIDCritObj.getCloneIDsSubQuery();
           if ( c != null) {
               Iterator iter = _BROKER.getReportQueryIteratorByQuery(c);
               while (iter.hasNext()) {
                   Object[] cloneIDS = (Object[]) iter.next();
                   BigDecimal bdcID = (BigDecimal)cloneIDS[0];
                   Long ldpID = new Long(bdcID.longValue());
                   allCloneIDS.add(ldpID);
               }
           }
           _BROKER.close();
           getDbEvent().setCompleted(true);
           /*InheritableThreadLocal tl;
           tl.get() = new InheritableThreadLocal();*/
   }

}
