package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.log4j.Logger;

import java.util.*;

import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.util.ThreadPool;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class AnnotationHandler {
    private static Logger logger = Logger.getLogger(AnnotationHandler.class);
    private final static int VALUES_PER_THREAD = 50;

    /**  This method will return the reporters and corresponding gene symbol in a Hash Map
     *
     * @param reporters Reporters to be queried
     * @return This method retunrs a Map using reporter as key and corresponding gene symbols as value
     */
    public Map<String, String> getGeneSymbolsFor(List reporters) throws Exception{
        List annotationsEventList = Collections.synchronizedList(new ArrayList());
        Map h = execQueryGeneSymbols(reporters, annotationsEventList);
        try {
            ThreadController.sleepOnEvents(annotationsEventList);
        } catch (InterruptedException e) {
            // no big deal Log it and ignore it
            logger.debug("Thread Interrupted during Annotations Retrieval", e);
        }
        return h;
    }

     /**
     * This method retrieves GenePathways, GeneOntologies, LocusLinks & Accessions
     * @param reporters ReporterNames for which Annotations are requested
     * @return Returns Map in which ReporterName is the key and associated ReporterAnnotations
     * as the value
     * @throws Exception
     */
    public Map<String, ReporterAnnotations> getAllAnnotationsFor(List reporters) throws Exception{

        Map<String, ReporterAnnotations> allAnnotations = new HashMap<String, ReporterAnnotations> ();
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String repName =  (String) iterator.next();
            // create a ReporterAnnotations object for each of the reporters
            ReporterAnnotations ra = new ReporterAnnotations();
            ra.setReporterName(repName);
            allAnnotations.put(repName, ra);
        }

        // 1.0 Retrieve all GeneSymbols corresponding to these reporters
        Map<String, String> reportersAndGenes = getGeneSymbolsFor(reporters);
        // 1.1 pupulate the above ReporterAnnotations object with the corresponding genesymbols
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String repName =  (String) iterator.next();
            ReporterAnnotations ra = allAnnotations.get(repName);
            ra.setGeneSymbol(reportersAndGenes.get(repName));
        }



        // 2.0 Retrieve GeneAnnotations (GenePathways & GeneOntology) for these gene symbols
        Collection geneSymbols = reportersAndGenes.values();
        List geneAnnotEventList = Collections.synchronizedList(new ArrayList());
        Map<String, GeneExpr.GeneAnnotation> geneAnnotations = Collections.synchronizedMap(new HashMap());
        GeneAnnotationsHandler gh = new GeneAnnotationsHandler(geneAnnotEventList );
        gh.populateAnnotationsFor(geneSymbols, geneAnnotations);


        // 3.0 Retrieve ReporterDimension (LocusLinks & Accns) for these reporters
        List reporterAnnotEventList = Collections.synchronizedList(new ArrayList());
        ReporterAnnotationsHandler rh = new ReporterAnnotationsHandler();
        Map<String,ReporterDimension> repAnnotations = rh.execAnnotationQuery(reporters, reporterAnnotEventList) ;

        // 4.0 sleep until the above GeneAnnotations & ReporterDimension are fully retrieved
        try {
            ThreadController.sleepOnEvents(geneAnnotEventList);
            ThreadController.sleepOnEvents(reporterAnnotEventList);
        } catch (InterruptedException e) {
            // no big deal Log it and ignore it
            logger.debug("Thread Interrupted during Annotations Retrieval", e);
        }

        // 5.0 populate the above ReporterAnnotations object with the corresponding GeneAnnotations
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String repName =  (String) iterator.next();
            ReporterAnnotations ra = allAnnotations.get(repName);
            String gs = ra.getGeneSymbol();

            // set goIDS & pathways ONLY if Gene Annotations are aivailable annotations
            GeneExpr.GeneAnnotation ga = geneAnnotations.get(gs);
            if (ga != null)  {
                ra.setGoIDS(ga.getGoIDs());
                ra.setPathways(ga.getPathwayNames());
            }
        }

        // 6.0 populate the above ReporterAnnotations object with the corresponding ReporetrDimensions
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String repName =  (String) iterator.next();
            ReporterAnnotations ra = allAnnotations.get(repName);

            // set LocusLinks & goIDs ONLY if ReporterAnnotations are available
            ReporterDimension rd = repAnnotations.get(ra.getReporterName());
            if (rd != null) {
                ra.setLocusLinks(rd.getLocusLinks());
                ra.setAccessions(rd.getAccessions());
            }
        }

        return allAnnotations;
    }

    private Map<String, String> execQueryGeneSymbols(List reporters, List annotationsEventList) {
        final Map<String, String> genesAndReporters = Collections.synchronizedMap(new HashMap<String, String>());
        for (int i = 0; i < reporters.size();)   {
            Collection values = new ArrayList();
            int begIndex = i;
            i += VALUES_PER_THREAD ;
            int endIndex = (i < reporters.size()) ? endIndex = i : (reporters.size());
            values.addAll(reporters.subList(begIndex,  endIndex));
            final Criteria reporterCrit = new Criteria();
            reporterCrit.addIn(ProbesetDim.PROBESET_NAME, values);
            String threadID = "AnnotationHandler.ThreadID:" +i;

            final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
            annotationsEventList.add(dbEvent);

            ThreadPool.AppThread t =
                    ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                          public void codeToRun() {
                              // retrieve GeneSymbols for the probesetNames above
                              final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                              pb.clearCache();
                              org.apache.ojb.broker.query.Query annotQuery =
                              QueryFactory.newReportQuery(ProbesetDim.class,new String[] {ProbesetDim.PROBESET_NAME, ProbesetDim.GENE_SYMBOL}, reporterCrit, false);
                              assert(annotQuery  != null);
                              Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);

                              while (iter.hasNext()) {
                                   Object[] geneAttrs = (Object[]) iter.next();
                                   String reporter = (String)geneAttrs[0];
                                   String geneSymbol = (String)geneAttrs[1];
                                   genesAndReporters.put(reporter, geneSymbol);
                              }
                              pb.close();
                              dbEvent.setCompleted(true);
                          }
                       }
                    );
               logger.debug("BEGIN: (from AnnotationHandler.getGeneSymbolsFor()) Thread Count: " + ThreadPool.THREAD_COUNT);
               t.start();
         }
         return genesAndReporters;
    }



}
