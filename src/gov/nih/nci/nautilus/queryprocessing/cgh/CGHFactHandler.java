package gov.nih.nci.nautilus.queryprocessing.cgh;

import gov.nih.nci.nautilus.data.ArrayGenoAbnFact;
import gov.nih.nci.nautilus.data.GeneLlAccSnp;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.queryprocessing.CommonFactHandler;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.resultset.ResultSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 26, 2004
 * Time: 3:58:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class CGHFactHandler {
    private static Logger logger = Logger.getLogger(CGHFactHandler.class);
    Map cghObjects = Collections.synchronizedMap(new HashMap());
    Map annotations = Collections.synchronizedMap(new HashMap());
    private final static int VALUES_PER_THREAD = 100;
    List factEventList = Collections.synchronizedList(new ArrayList());
    List annotationEventList = Collections.synchronizedList(new ArrayList());
    abstract void addToResults(Collection results);
    abstract ResultSet[] executeSampleQuery(final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
    throws Exception;

    protected void executeQuery(final String snpOrCGHAttr, Collection cghOrSNPIDs, final Class targetFactClass, ComparativeGenomicQuery cghQuery) throws Exception {
            ArrayList arrayIDs = new ArrayList(cghOrSNPIDs);
            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria IDs = new Criteria();
                IDs.addIn(snpOrCGHAttr, values);
                String threadID = "FoldChangeCriteriaHandler.ThreadID:" + snpOrCGHAttr + ":" +i;

                final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                factEventList.add(dbEvent);
                PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();

                final Criteria sampleCrit = new Criteria();
                CommonFactHandler.addDiseaseCriteria(cghQuery, targetFactClass, _BROKER, sampleCrit);
                CopyNumberCriteriaHandler.addCopyNumberCriteria(cghQuery, targetFactClass, _BROKER, sampleCrit);
                CommonFactHandler.addSampleIDCriteria(cghQuery, targetFactClass, sampleCrit);

                _BROKER.close();

                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          pb.clearCache();
                          sampleCrit.addAndCriteria(IDs);
                          Query sampleQuery =
                          QueryFactory.newQuery(targetFactClass,sampleCrit, true);
                          assert(sampleQuery != null);
                          Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                          addToResults(exprObjects);
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }

     protected void executeGeneAnnotationQuery(Collection cghOrSNPIDs) throws Exception {
            ArrayList arrayIDs = new ArrayList(cghOrSNPIDs);
            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(GeneLlAccSnp.SNP_PROBESET_ID, values);
                long time = System.currentTimeMillis();
                String threadID = "FoldChangeCriteriaHandler.ThreadID:" + time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          ReportQueryByCriteria annotQuery =
                          QueryFactory.newReportQuery(GeneLlAccSnp.class, annotCrit, true);
                          annotQuery.setAttributes(new String[] {GeneLlAccSnp.SNP_PROBESET_ID, GeneLlAccSnp.GENE_SYMBOL, GeneLlAccSnp.LOCUS_LINK_ID, GeneLlAccSnp.ACCESSION});
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] attrs = (Object[]) iter.next();
                               Long snpProbID = new Long(((BigDecimal)attrs[0]).longValue());
                               CopyNumber.SNPAnnotation a = (CopyNumber.SNPAnnotation)annotations.get(snpProbID );
                               if (a == null) {
                                   a = new CopyNumber.SNPAnnotation(snpProbID, new HashSet(), new HashSet(), new HashSet());
                                   annotations.put(snpProbID, a);
                               }
                               a.getGeneSymbols().add(attrs[1]);
                               a.getLocusLinkIDs().add(attrs[2]);
                               a.getAccessionNumbers().add(attrs[3]);
                          }
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }
    final static class SingleCGHFactHandler extends CGHFactHandler {
        ResultSet[] executeSampleQuery( final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
        throws Exception {
            logger.debug("Total Number Of SNP_PROBES:" + allSNPProbeIDs.size());
            executeQuery(ArrayGenoAbnFact.SNP_PROBESET_ID, allSNPProbeIDs, ArrayGenoAbnFact.class, cghQuery);

            ThreadController.sleepOnEvents(factEventList);
            executeGeneAnnotationQuery(allSNPProbeIDs);
            ThreadController.sleepOnEvents(annotationEventList);

            // by now CopyNumberObjects and annotations would have populated
            Object[]objs = (cghObjects.values().toArray());
            CopyNumber[] results = new CopyNumber[objs.length];
            for (int i = 0; i < objs.length; i++) {
                CopyNumber obj = (CopyNumber) objs[i];
                if (obj.getSnpProbesetId() != null) {
                    obj.setAnnotations((CopyNumber.SNPAnnotation)annotations.get(obj.getSnpProbesetId()));
                }
                results[i] = obj;
                //logger.debug("SAMPLE_ID: " + obj.getSampleId() + "    Copy Number: " + obj.getCopyNumber() +
                //                    "    DISEASE_TYPE: " + obj.getDiseaseType());
            }
            return results;
        }

        void addToResults(Collection factObjects) {
           for (Iterator iterator = factObjects.iterator(); iterator.hasNext();) {
                ArrayGenoAbnFact factObj = (ArrayGenoAbnFact) iterator.next();
                CopyNumber resObj = new CopyNumber();
                copyTo(resObj, factObj);
                cghObjects.put(factObj.getAgaId(), resObj);
                resObj = null;
            }
        }
        private void copyTo(CopyNumber resultObj, ArrayGenoAbnFact factObj) {
            resultObj.setAgeGroup(factObj.getAgeGroup());
            resultObj.setBiospecimenId(factObj.getBiospecimenId());
            resultObj.setChannelRatio(factObj.getChannelRatio());
            resultObj.setCopyNumber(factObj.getCopyNumber());
            resultObj.setCytoband(factObj.getCytoband());
            resultObj.setDiseaseType(factObj.getDiseaseType());
            resultObj.setGenderCode(factObj.getGenderCode());
            resultObj.setLoh(factObj.getLoh());
            resultObj.setLossGain(factObj.getLossGain());
            resultObj.setSampleId(factObj.getSampleId());
            resultObj.setSnpProbesetId(factObj.getSnpProbesetId());
            resultObj.setSnpProbesetName(factObj.getSnpProbesetName());
            resultObj.setSurvivalLengthRange(factObj.getSurvivalLengthRange());
            resultObj.setTimecourseId(factObj.getTimecourseId());
            resultObj.setPhysicalPosition(factObj.getPhysicalPosition());
        }
    }
}


