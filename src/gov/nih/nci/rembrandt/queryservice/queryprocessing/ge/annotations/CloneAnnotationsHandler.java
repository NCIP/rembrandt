/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import gov.nih.nci.rembrandt.dbbean.GeneClone;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.util.ThreadPool;

/**
 * User: Himanso Sahni <BR>
 * Date: July 24, 2008 <BR>
 * Version: 1.0 <BR>
 */
public class CloneAnnotationsHandler {

    private static Logger logger = Logger.getLogger(CloneAnnotationsHandler.class);
    private final static int VALUES_PER_THREAD = 50;


   public Map<String,ReporterDimension> executeAnnotationQuery(Collection probeOrCloneIDs,  List annotationEventList) throws Exception {
        final Map<String, ReporterDimension> cloneAnnotations = Collections.synchronizedMap(new HashMap());
        ArrayList arrayIDs = new ArrayList(probeOrCloneIDs);
        for (int i = 0; i < arrayIDs.size();) {
            Collection values = new ArrayList();
            int begIndex = i;
            i += VALUES_PER_THREAD ;
            int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
            values.addAll(arrayIDs.subList(begIndex,  endIndex));
            final Criteria annotCrit = new Criteria();
            annotCrit.addIn(GeneClone.CLONE_ID, values);
            long time = System.currentTimeMillis();
            String threadID = "GEFactHandler.ThreadID:" +time;
            final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
            annotationEventList.add(dbEvent);
            final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
            _BROKER.clearCache();
            final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.LOCUS_LINK);
            final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.ACCESSION_NUMBER);
            final String cloneIDColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.CLONE_ID);
            _BROKER.close();
            ThreadPool.AppThread t = ThreadPool.newAppThread(
                   new ThreadPool.MyRunnable() {
                        public void codeToRun() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          pb.clearCache();
                          Query annotQuery =
                          QueryFactory.newReportQuery(GeneClone.class,new String[] {cloneIDColName, locusLinkColName, accessionColName }, annotCrit, false);
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] cloneAttrs = (Object[]) iter.next();
                               Long cloneID = new Long(((BigDecimal)cloneAttrs[0]).longValue());
                               ReporterDimension r = (ReporterDimension)cloneAnnotations.get(cloneID);
                               if (r == null) {
                                   r = new ReporterDimension(new ArrayList<String>(), new ArrayList<String>(), cloneID.toString());
                                   cloneAnnotations.put(cloneID.toString() , r);
                               }
                               r.getLocusLinks().add((String)cloneAttrs[1]);
                               r.getAccessions().add((String)cloneAttrs[2]);
                          }
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                }
           );
           logger.debug("BEGIN (from GEFactHandler.executeCloneAnnotationQuery()): Thread Count: " + ThreadPool.THREAD_COUNT);
           t.start();
        }
		return cloneAnnotations;
}

}
