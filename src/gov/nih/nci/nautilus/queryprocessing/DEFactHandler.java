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

    abstract Map executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
    throws Exception;

    final static class SingleDEFactHandler extends DEFactHandler {
        Map executeSampleQuery( final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
        throws Exception {
            final String fieldName = DifferentialExpressionSfact.BIOSPECIMEN_ID ;
            System.out.println("Total Number Of Probes:" + allProbeIDs.size());
            build(DifferentialExpressionSfact.PROBESET_ID, allProbeIDs, foldCrit, fieldName);
            build(DifferentialExpressionSfact.CLONE_ID, allCloneIDs, foldCrit, fieldName);
                /*
                sampleQuery.setAttributes(new String[] {
                        DifferentialExpressionSfact.DES_ID,
                        DifferentialExpressionSfact.PROBE_NAME,
                        DifferentialExpressionSfact.CLONE_NAME,
                        DifferentialExpressionSfact.PROBESET_ID,
                        DifferentialExpressionSfact.tyCLONE_ID,
                        DifferentialExpressionSfact.GENE_SYMBOL,
                        DifferentialExpressionSfact.BIOSPECIMEN_ID,
                        DifferentialExpressionSfact.EXPRESSION_RATIO
                } );
                */
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

            print();
            return geneExprObjects;

        }

        private void build(final String probeOrCloneIDAttr, Collection probeOrCloneIDs, final FoldChangeCriteria foldCrit, final String fieldName) throws Exception {
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
                FoldChangeCriteriaHandler.addFoldChangeCriteria(foldCrit, DifferentialExpressionSfact.class, _BROKER, sampleCritBasedOnProbes);
                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          sampleCritBasedOnProbes.addAndCriteria(IDs);
                          org.apache.ojb.broker.query.Query sampleQuery =
                          QueryFactory.newQuery(DifferentialExpressionSfact.class,sampleCritBasedOnProbes, true);
                          assert(sampleQuery != null);
                          Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                          addToResults(exprObjects);
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
        }

        private void addToResults(Collection exprObjects) {
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

        private void print() {
            int count = 0;
            HashSet probeIDS = new HashSet();
            HashSet cloneIDs = new HashSet();
            Set keys = geneExprObjects.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                Long desID =  (Long) iterator.next();
                GeneExpr.GeneExprSingle exprObj = (GeneExpr.GeneExprSingle)geneExprObjects.get(desID);
                if (exprObj.getProbesetId() != null) {
                  // System.out.println("ProbesetID: " + exprObj.getProbesetId() + " :Exp Value: "
                    //            + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol() );
                    probeIDS.add(exprObj.getProbesetId());
                }
                if ( exprObj.getCloneId() != null) {
                   // System.out.println("CloneID: " + exprObj.getCloneId()+ " :Exp Value: "
                     //           + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol());
                    cloneIDs.add(exprObj.getCloneId() );
                }

                 ++count;
            }
            System.out.println("Total Number Of Samples: " + count);
            StringBuffer p = new StringBuffer();
            for (Iterator iterator = probeIDS.iterator(); iterator.hasNext();) {
                Long aLong = (Long) iterator.next();
                p.append(aLong.toString() + ",");
            }
            System.out.println("Total Probes: " + probeIDS.size());
            System.out.println(p.toString());
            StringBuffer c = new StringBuffer();
            for (Iterator iterator = cloneIDs.iterator(); iterator.hasNext();) {
                Long aLong = (Long) iterator.next();
                c.append(aLong.toString() + ",");
            }
            System.out.println("Total clones: " + cloneIDs.size());
            System.out.println(c.toString());

            return ;
        }
    }

    static class GroupDEFactHanlder extends DEFactHandler {
        Map executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, final FoldChangeCriteria foldCrit)
        throws Exception {
            //TODO:
            return null;
        }
    }


}
