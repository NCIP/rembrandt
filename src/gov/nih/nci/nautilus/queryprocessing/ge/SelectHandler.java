package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.util.ThreadPool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(SelectHandler.class);
    private gov.nih.nci.nautilus.queryprocessing.ge.GEReporterIDCriteria reporterIDCritObj;
    private Collection allProbeIDS;
    private Collection allCloneIDS;
     private List eventList = Collections.synchronizedList(new ArrayList());


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
           //PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();


           //ReportQueryByCriteria c = reporterIDCritObj.getCloneIDsSubQuery();
           Collection probeIDS = Collections.synchronizedCollection(new HashSet());
           Collection cloneIDS = Collections.synchronizedCollection(new HashSet());

           Collection probeQueries = reporterIDCritObj.getMultipleProbeIDsSubQueries();
           executeSubQueries(probeQueries, probeIDS);
           //ReportQueryByCriteria p = reporterIDCritObj.getProbeIDsSubQuery();
           Collection cloneQueries = reporterIDCritObj.getMultipleCloneIDsSubQueries();
           executeSubQueries(cloneQueries, cloneIDS);
           try {
                ThreadController.sleepOnEvents(eventList);
           } catch (InterruptedException e) {
               // should never happen.  If happens log it and continue
               e.printStackTrace();
           }


           //executeProbeCloneIDSubQuery(c, cloneIDS);
           //_BROKER.close();
           allProbeIDS.addAll(probeIDS);
           allCloneIDS.addAll(cloneIDS);
           getDbEvent().setCompleted(true);
           /*InheritableThreadLocal tl;
           tl.get() = new InheritableThreadLocal();*/
   }

    private void executeSubQueries(Collection probeQueries, final Collection probeIDS) {
        for (Iterator iterator = probeQueries.iterator(); iterator.hasNext();) {
            final ReportQueryByCriteria query =  (ReportQueryByCriteria)iterator.next();
            String threadID = new Long(System.currentTimeMillis()).toString();
            final DBEvent.SubQueryEvent event = new DBEvent.SubQueryEvent(threadID);
            eventList.add(event);
            logger.debug("NEW THREADS: " + threadID);
            //new Thread(new Executor(query, probeIDS, event)).start();
            ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                          public void codeToRun() {
                              new Executor(query, probeIDS, event).execute();
                          }
                       }
                      );
             System.out.println("BEGIN: (from SelectHandler.executeSubQueries()) Thread Count: " + ThreadPool.THREAD_COUNT);
             t.start();
        }
    }

    private void executeProbeCloneIDSubQuery(ReportQueryByCriteria query, Collection probeORCloneIDs, DBEvent.SubQueryEvent event) {
        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
        _BROKER.clearCache();
        if ( query != null) {
            Iterator iter = _BROKER.getReportQueryIteratorByQuery(query);
            while (iter.hasNext()) {
                Object[] prbIDS = (Object[]) iter.next();
                BigDecimal bdpID = (BigDecimal)prbIDS[0];
                Long ldpID = new Long(bdpID.longValue());
                probeORCloneIDs.add(ldpID);
            }
        }
        _BROKER.close();
        event.setCompleted(true);
        //ReportQueryByCriteria c = reporterIDCritObj.getCloneIDsSubQuery();
        /*
        if ( c != null) {
            Iterator iter = _BROKER.getReportQueryIteratorByQuery(c);
            while (iter.hasNext()) {
                Object[] cloneIDS = (Object[]) iter.next();
                BigDecimal bdcID = (BigDecimal)cloneIDS[0];
                Long ldpID = new Long(bdcID.longValue());
                allCloneIDS.add(ldpID);
            }
        }
        */
    }
    private class Executor {
       ReportQueryByCriteria query;
       Collection probeORCloneIDS;
       DBEvent.SubQueryEvent event;

        private Executor(ReportQueryByCriteria query, Collection probeORCloneIDS, DBEvent.SubQueryEvent event) {
            this.query = query;
            this.probeORCloneIDS = probeORCloneIDS;
            this.event = event;
        }

        public void execute() {
             executeProbeCloneIDSubQuery(query, probeORCloneIDS, event);
        }

    }
}
