package gov.nih.nci.nautilus.queryprocessing.cgh;

import org.apache.ojb.broker.query.*;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

import java.util.*;
import java.math.BigDecimal;

import gov.nih.nci.nautilus.data.*;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.queryprocessing.ge.FactCriteriaHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.queryprocessing.ge.GEFactHandler;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;



/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 26, 2004
 * Time: 3:58:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class CGHFactHandler {
    Map cghObjects = Collections.synchronizedMap(new HashMap());
    Map annotations = Collections.synchronizedMap(new HashMap());
    private final static long SLEEP_TIME= 10;
    private final static int VALUES_PER_THREAD = 50;

    List factEventList = Collections.synchronizedList(new ArrayList());
    abstract void addToResults(Collection results);
    List annotationEventList = Collections.synchronizedList(new ArrayList());
    abstract ResultSet[] executeSampleQuery(final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
    throws Exception;


    protected void executeQuery(final String snpOrCGHAttr, Collection cghOrSNPIDs, final Class targetFactClass, ComparativeGenomicQuery cghQuery) throws Exception {

            final CopyNumberCriteria copyCrit = cghQuery.getCopyNumberCriteria();
            final DiseaseOrGradeCriteria diseaseCrit = cghQuery.getDiseaseOrGradeCriteria();

            if (cghOrSNPIDs.size() > 0) {
                ArrayList arrayIDs = new ArrayList(cghOrSNPIDs);
                for (int i = 0; i < arrayIDs.size();) {
                    Collection values = new ArrayList();
                    int begIndex = i;
                    i += VALUES_PER_THREAD ;
                    int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                    values.addAll(arrayIDs.subList(begIndex,  endIndex));
                    final Criteria IDs = new Criteria();
                    IDs.addIn(snpOrCGHAttr, values);
                    String threadID = "CGHFactHandler.ThreadID:" + snpOrCGHAttr + ":" +i;

                    final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                    factEventList.add(dbEvent);
                    PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();

                    final Criteria sampleCrit = new Criteria();
                    if (diseaseCrit != null)
                       CopyNumberCriteriaHandler.addDiseaseCriteria(diseaseCrit, targetFactClass, _BROKER, sampleCrit);

                    if (copyCrit != null)
                       CopyNumberCriteriaHandler.addCopyNumberCriteria(copyCrit, targetFactClass, _BROKER, sampleCrit);

                    new Thread(
                       new Runnable() {
                          public void run() {
                              final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                              sampleCrit.addAndCriteria(IDs);
                              Query sampleQuery =
                              QueryFactory.newQuery(targetFactClass,sampleCrit, true);
                              assert(sampleQuery != null);
                              Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                              addToResults(exprObjects);
                              dbEvent.setCompleted(true);
                          }
                       }
                   ).start();
                }
            }
            else {
                {
                    System.out.println("Hello");
                }

                /*  this will retrieve everything so it will run out of memory
                PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                 final Criteria sampleCrit = new Criteria();
                 if (diseaseCrit != null)
                     CopyNumberCriteriaHandler.addDiseaseCriteria(diseaseCrit, targetFactClass, pb, sampleCrit);
                 if (copyCrit != null)
                     CopyNumberCriteriaHandler.addCopyNumberCriteria(copyCrit, targetFactClass, pb, sampleCrit);

                 Query sampleQuery = QueryFactory.newQuery(targetFactClass,sampleCrit, true);
                  assert(sampleQuery != null);
                  Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                  addToResults(exprObjects);

                  */
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
                annotCrit.addIn(SnpAssociatedGene.SNP_PROBESET_ID, values);
                long time = System.currentTimeMillis();
                String threadID = "CGHFactHandler.ThreadID:" +time;

                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                final String geneSymbolCol = QueryHandler.getColumnNameForBean(_BROKER, SnpAssociatedGene.class.getName(), SnpAssociatedGene.GENE_SYMBOL);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, SnpAssociatedGene.class.getName(), SnpAssociatedGene.ACCESSION_NUMBER);
                final String snpProbesetColName = QueryHandler.getColumnNameForBean(_BROKER, SnpAssociatedGene.class.getName(), SnpAssociatedGene.SNP_PROBESET_ID);

                new Thread(
                   new Runnable() {
                      public void run() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          Query annotQuery =
                          QueryFactory.newReportQuery(SnpAssociatedGene.class,new String[] {snpProbesetColName , geneSymbolCol, accessionColName }, annotCrit, true);
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] attrs = (Object[]) iter.next();
                               Long snpProbID = new Long(((BigDecimal)attrs[0]).longValue());
                               CopyNumber.Annotation a = ( CopyNumber.Annotation)annotations.get(snpProbID );
                               if (a == null) {
                                   a = new  CopyNumber.Annotation(new ArrayList(), new ArrayList(), snpProbID);
                                   annotations.put(snpProbID, a);
                               }
                               a.geneSymbols.add(attrs[1]);
                               a.accessions.add(attrs[2]);
                          }
                          dbEvent.setCompleted(true);
                      }
                   }
               ).start();
            }
    }
    final static class SingleCGHFactHandler extends CGHFactHandler {
        ResultSet[] executeSampleQuery( final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
        throws Exception {
            System.out.println("Total Number Of SNP_PROBES:" + allSNPProbeIDs.size());
            executeQuery(ArrayGenoAbnFact.SNP_PROBESET_ID, allSNPProbeIDs, ArrayGenoAbnFact.class, cghQuery);
            //sleepOnFactEvents();
            ThreadController.sleepOnEvents(factEventList);
            executeGeneAnnotationQuery(allSNPProbeIDs);
            //sleepOnAnnotationEvents();
            ThreadController.sleepOnEvents(annotationEventList);

            // by now CopyNumberObjects and annotations would have populated
            Object[]objs = (cghObjects.values().toArray());
            CopyNumber[] results = new CopyNumber[objs.length];
            for (int i = 0; i < objs.length; i++) {
                CopyNumber obj = (CopyNumber) objs[i];
                if (obj.getSnpProbesetId() != null) {
                    obj.setAnnotations((CopyNumber.Annotation)annotations.get(obj.getSnpProbesetId()));
                }
                results[i] = obj;
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
        }
    }

    }


