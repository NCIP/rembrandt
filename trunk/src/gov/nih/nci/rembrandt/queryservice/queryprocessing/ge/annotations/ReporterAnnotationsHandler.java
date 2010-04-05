package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.log4j.Logger;

import java.util.*;

import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.util.ThreadPool;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class ReporterAnnotationsHandler {

    private static Logger logger = Logger.getLogger(ReporterAnnotationsHandler.class);
    private final static int VALUES_PER_THREAD = 50;


    public  Map<String,ReporterDimension>  execAnnotationQuery(List probeNames, List annotationsEventList) throws Exception {
            final Map<String, ReporterDimension> probeAnnotations = Collections.synchronizedMap(new HashMap());
            for (int i = 0; i < probeNames.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < probeNames.size()) ? endIndex = i : (probeNames.size());
                values.addAll(probeNames.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(ProbesetDim.PROBESET_NAME, values);
                long time = System.currentTimeMillis();
                String threadID = "AnnotationHelper.ThreadID:" +time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationsEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();
                final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.LOCUS_LINK);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.ACCESSION_NUMBER);
                final String probeIDColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_NAME);
                _BROKER.close();
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                          public void codeToRun()  {
                              final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                              pb.clearCache();
                              Query annotQuery =
                              QueryFactory.newReportQuery(ProbesetDim.class,new String[] {probeIDColName , locusLinkColName, accessionColName }, annotCrit, false);
                              assert(annotQuery != null);
                              Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                              while (iter.hasNext()) {
                                   Object[] probeAttrs = (Object[]) iter.next();
                                   String probeName = (String)probeAttrs[0];
                                   ReporterDimension r = (ReporterDimension)probeAnnotations.get(probeName);
                                   if (r == null) {
                                       r = new ReporterDimension(new ArrayList<String>(), new ArrayList<String>(), probeName);
                                       probeAnnotations.put(probeName , r);
                                   }
                                   r.getLocusLinks().add((String)probeAttrs[1]);
                                   r.getAccessions().add((String)probeAttrs[2]);
                              }
                              pb.close();
                              dbEvent.setCompleted(true);
                      }
                   }
               );
               logger.debug("BEGIN (from AnnotationHandler.executeProbeAnnotationQuery()): Thread Count: " + ThreadPool.THREAD_COUNT);
               t.start();
            }

         return probeAnnotations;
     }

}
