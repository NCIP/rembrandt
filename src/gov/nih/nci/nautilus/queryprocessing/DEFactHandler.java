package gov.nih.nci.nautilus.queryprocessing;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.ValueCriteria;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

import java.util.*;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.DifferentialExpressionGfact;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.resultset.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 26, 2004
 * Time: 3:58:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class DEFactHandler {
    Class sfactClass = DifferentialExpressionSfact.class;
    Map geneExprObjects = Collections.synchronizedMap(new HashMap());
    List eventList = Collections.synchronizedList(new ArrayList());
    abstract void addToResults(Collection results);
    abstract ResultSet[] executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
    throws Exception;
    protected void sleep() throws InterruptedException {
        boolean sleep = true;
        do {
            Thread.sleep(10);
            sleep = false;
            for (Iterator iterator = eventList.iterator(); iterator.hasNext();) {
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
                i += 50;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size() - 1);
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria IDs = new Criteria();
                IDs.addIn(probeOrCloneIDAttr, values);
                String threadID = "DEFactHandler.ThreadID:" + probeOrCloneIDAttr + ":" +i;

                final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                eventList.add(dbEvent);
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
    final static class SingleDEFactHandler extends DEFactHandler {
        ResultSet[] executeSampleQuery( final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
        throws Exception {
            //final String fieldName = DifferentialExpressionSfact.BIOSPECIMEN_ID ;
            System.out.println("Total Number Of Probes:" + allProbeIDs.size());
            executeQuery(DifferentialExpressionSfact.PROBESET_ID, allProbeIDs, DifferentialExpressionSfact.class, foldCrit );
            executeQuery(DifferentialExpressionSfact.CLONE_ID, allCloneIDs, DifferentialExpressionSfact.class, foldCrit);
            sleep();
            // geneExprObjects would have populated by this time Convert these in to Result objects
            // geneExprObjects would have populated by this time Convert these in to Result objects
            Object[]objs = (geneExprObjects.values().toArray());
            GeneExpr.GeneExprSingle[] results = new GeneExpr.GeneExprSingle[objs.length];
            for (int i = 0; i < objs.length; i++) {
                GeneExpr.GeneExprSingle obj = (GeneExpr.GeneExprSingle) objs[i];
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
            singleExprObj.setExpressionRatio(exprObj.getExpressionRatio());
            singleExprObj.setGeneSymbol(exprObj.getGeneSymbol());
            singleExprObj.setProbesetId(exprObj.getProbesetId());
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
                sleep();

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


