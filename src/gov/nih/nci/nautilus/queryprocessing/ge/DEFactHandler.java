package gov.nih.nci.nautilus.queryprocessing.ge;

import org.apache.ojb.broker.query.*;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

import java.util.*;
import java.math.BigDecimal;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.DifferentialExpressionGfact;
import gov.nih.nci.nautilus.data.GeneClone;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 26, 2004
 * Time: 3:58:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class DEFactHandler {

    //Map geneAnnotations = Collections.synchronizedMap(new HashMap());
    Map geneExprObjects = Collections.synchronizedMap(new HashMap());
    Map cloneAnnotations = Collections.synchronizedMap(new HashMap());
    Map probeAnnotations = Collections.synchronizedMap(new HashMap());
    private final static long SLEEP_TIME= 10;
    private final static int VALUES_PER_THREAD = 50;

    List factEventList = Collections.synchronizedList(new ArrayList());
    abstract void addToResults(Collection results);
    List annotationEventList = Collections.synchronizedList(new ArrayList());
    abstract ResultSet[] executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
    throws Exception;



    protected void sleepOnFactEvents() throws InterruptedException {
        boolean sleep = true;
        do {
            Thread.sleep(SLEEP_TIME);
            sleep = false;
            for (Iterator iterator = factEventList.iterator(); iterator.hasNext();) {
                DBEvent eventObj = (DBEvent)iterator.next();
                if (! eventObj.isCompleted()) {
                    sleep = true;
                    break;
                }
            }
        } while (sleep);
        return;
    }
    protected void sleepOnAnnotationEvents() throws InterruptedException {
        boolean sleep = true;
        do {
            Thread.sleep(SLEEP_TIME);
            sleep = false;
            for (Iterator iterator = annotationEventList.iterator(); iterator.hasNext();) {
                DBEvent eventObj = (DBEvent)iterator.next();
                if (! eventObj.isCompleted()) {
                    sleep = true;
                    break;
                }
            }
        } while (sleep);
        return;
    }
    protected void executeQuery(final String probeOrCloneIDAttr, Collection probeOrCloneIDs, final Class targetFactClass, final FoldChangeCriteria foldCrit) throws Exception {
            ArrayList arrayIDs = new ArrayList(probeOrCloneIDs);

            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria IDs = new Criteria();
                IDs.addIn(probeOrCloneIDAttr, values);
                String threadID = "DEFactHandler.ThreadID:" + probeOrCloneIDAttr + ":" +i;

                final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                factEventList.add(dbEvent);
                PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                final Criteria sampleCritBasedOnProbes = new Criteria();
                FoldChangeCriteriaHandler.addFoldChangeCriteria(foldCrit, targetFactClass, _BROKER, sampleCritBasedOnProbes);
                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          sampleCritBasedOnProbes.addAndCriteria(IDs);
                          org.apache.ojb.broker.query.Query sampleQuery =
                          QueryFactory.newQuery(targetFactClass,sampleCritBasedOnProbes, true);
                          assert(sampleQuery != null);
                          Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                          addToResults(exprObjects);
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }

     protected void executeCloneAnnotationQuery(Collection probeOrCloneIDs) throws Exception {
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
                String threadID = "DEFactHandler.ThreadID:" +time;

                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.LOCUS_LINK);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.ACCESSION_NUMBER);
                final String cloneIDColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.CLONE_ID);

                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          Query annotQuery =
                          QueryFactory.newReportQuery(GeneClone.class,new String[] {cloneIDColName, locusLinkColName, accessionColName }, annotCrit, true);
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] cloneAttrs = (Object[]) iter.next();
                               Long cloneID = new Long(((BigDecimal)cloneAttrs[0]).longValue());
                               GeneExpr.Annotaion c = (GeneExpr.CloneAnnotaion)cloneAnnotations.get(cloneID);
                               if (c == null) {
                                   c = new GeneExpr.CloneAnnotaion(new ArrayList(), new ArrayList(), cloneID);
                                   cloneAnnotations.put(cloneID, c);
                               }
                               c.locusLinks.add(cloneAttrs[1]);
                               c.accessions.add(cloneAttrs[2]);
                          }
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }
    protected void executeProbeAnnotationQuery(Collection probeOrCloneIDs) throws Exception {
            ArrayList arrayIDs = new ArrayList(probeOrCloneIDs);

            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(ProbesetDim.PROBESET_ID, values);
                long time = System.currentTimeMillis();
                String threadID = "DEFactHandler.ThreadID:" +time;

                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.LOCUS_LINK);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.ACCESSION_NUMBER);
                final String probeIDColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);

                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          Query annotQuery =
                          QueryFactory.newReportQuery(ProbesetDim.class,new String[] {probeIDColName , locusLinkColName, accessionColName }, annotCrit, true);
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] probeAttrs = (Object[]) iter.next();
                               Long probeID = new Long(((BigDecimal)probeAttrs[0]).longValue());
                               GeneExpr.Annotaion p = (GeneExpr.ProbeAnnotaion)probeAnnotations.get(probeID );
                               if (p == null) {
                                   p = new GeneExpr.ProbeAnnotaion(new ArrayList(), new ArrayList(), probeID );
                                   probeAnnotations.put(probeID , p);
                               }
                               p.locusLinks.add(probeAttrs[1]);
                               p.accessions.add(probeAttrs[2]);
                          }
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }
    final static class SingleDEFactHandler extends DEFactHandler {
        ResultSet[] executeSampleQuery( final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
        throws Exception {
            //final String fieldName = DifferentialExpressionSfact.BIOSPECIMEN_ID ;
            System.out.println("Total Number Of Probes:" + allProbeIDs.size());
            executeQuery(DifferentialExpressionSfact.PROBESET_ID, allProbeIDs, DifferentialExpressionSfact.class, foldCrit );
            executeQuery(DifferentialExpressionSfact.CLONE_ID, allCloneIDs, DifferentialExpressionSfact.class, foldCrit);
            sleepOnFactEvents();

            executeCloneAnnotationQuery(allCloneIDs);
            executeProbeAnnotationQuery(allProbeIDs );
            sleepOnAnnotationEvents();

            // by now geneExprObjects,  cloneAnnotations, probeAnnotations would have populated
            Object[]objs = (geneExprObjects.values().toArray());
            GeneExpr.GeneExprSingle[] results = new GeneExpr.GeneExprSingle[objs.length];
            for (int i = 0; i < objs.length; i++) {
                GeneExpr.GeneExprSingle obj = (GeneExpr.GeneExprSingle) objs[i];
                if (obj.getProbesetId() != null) {
                    obj.setAnnotation((GeneExpr.ProbeAnnotaion)probeAnnotations.get(obj.getProbesetId()));
                }
                else if (obj.getCloneId() != null) {
                    obj.setAnnotation((GeneExpr.CloneAnnotaion)cloneAnnotations.get(obj.getCloneId()));
                }
                results[i] = obj;
            }
            return results;
        }
        void addToResults(Collection exprObjects) {
            for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                DifferentialExpressionSfact exprObj = (DifferentialExpressionSfact) iterator.next();
                GeneExpr.GeneExprSingle singleExprObj = new GeneExpr.GeneExprSingle();
                copyTo(singleExprObj, exprObj);
                geneExprObjects.put(singleExprObj.getDesId(), singleExprObj);
                exprObj = null;
            }
        }
        private void copyTo(GeneExpr.GeneExprSingle singleExprObj, DifferentialExpressionSfact exprObj) {
            singleExprObj.setDesId(exprObj.getDesId());
            singleExprObj.setAgeGroup(exprObj.getAgeGroup());
            singleExprObj.setAgentId(exprObj.getAgentId());
            singleExprObj.setBiospecimenId(exprObj.getBiospecimenId());
            singleExprObj.setCloneId(exprObj.getCloneId());
            singleExprObj.setCloneName(exprObj.getCloneName());
            singleExprObj.setCytoband(exprObj.getCytoband());
            singleExprObj.setDiseaseTypeId(exprObj.getDiseaseTypeId());
            singleExprObj.setDiseaseType(exprObj.getDiseaseType());
            singleExprObj.setExpressionRatio(exprObj.getExpressionRatio());
            singleExprObj.setGeneSymbol(exprObj.getGeneSymbol());
            if (exprObj.getProbesetId() != null ) {
                singleExprObj.setProbesetId(exprObj.getProbesetId());

            }
            singleExprObj.setProbesetName(exprObj.getProbesetName());
            singleExprObj.setSurvivalLengthRange(exprObj.getSurvivalLengthRange());
            singleExprObj.setTimecourseId(exprObj.getTimecourseId());
        }
    }
        final static class GroupDEFactHanlder extends DEFactHandler {

            ResultSet[] executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
            throws Exception {
                executeQuery(DifferentialExpressionGfact.PROBESET_ID, allProbeIDs, DifferentialExpressionGfact.class, foldCrit );
                executeQuery(DifferentialExpressionGfact.CLONE_ID, allCloneIDs, DifferentialExpressionGfact.class, foldCrit);
                sleepOnFactEvents();

                // geneExprObjects would have populated by this time Convert these in to Result objects
                Object[]objs = (geneExprObjects.values().toArray());
                GeneExpr.GeneExprGroup[] results = new GeneExpr.GeneExprGroup[objs.length];
                for (int i = 0; i < objs.length; i++) {
                    GeneExpr.GeneExprGroup obj = (GeneExpr.GeneExprGroup) objs[i];
                    results[i] = obj;
                }
                return results;
            }

            void addToResults(Collection exprObjects) {
                for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                    DifferentialExpressionGfact exprObj = (DifferentialExpressionGfact) iterator.next();
                    GeneExpr.GeneExprGroup groupExprObj = new GeneExpr.GeneExprGroup();
                    copyTo(groupExprObj, exprObj);
                    geneExprObjects.put(groupExprObj.getDegId(), groupExprObj);
                    exprObj = null;
                }
            }
            private void copyTo(GeneExpr.GeneExprGroup groupExprObj, DifferentialExpressionGfact  exprObj) {
                groupExprObj.setDegId(exprObj.getDegId());

                groupExprObj.setCloneId(exprObj.getCloneId());
                groupExprObj.setCloneName(exprObj.getCloneName());
                groupExprObj.setDiseaseTypeId(exprObj.getDiseaseTypeId());
                groupExprObj.setDiseaseType(exprObj.getDiseaseType());
                groupExprObj.setExpressionRatio(exprObj.getExpressionRatio());
                groupExprObj.setGeneSymbol(exprObj.getGeneSymbol());
                groupExprObj.setProbesetId(exprObj.getProbesetId());
                groupExprObj.setProbesetName(exprObj.getProbesetName());
                groupExprObj.setNormalIntensity(exprObj.getNormalIntensity());
                groupExprObj.setSampleIntensity(exprObj.getSampleGIntensity());
                groupExprObj.setRatioPval(exprObj.getRatioPval());
                groupExprObj.setTimecourseId(exprObj.getTimecourseId());
            }
        }
    }


