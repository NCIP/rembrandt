package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.ReporterAll;
import gov.nih.nci.nautilus.data.GeneOntology;
import gov.nih.nci.nautilus.data.DifferentialExpressionGfact;
import gov.nih.nci.nautilus.view.ViewType;

import java.util.*;


import org.apache.ojb.broker.query.*;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:14:46 PM
 * To change this template use Options | File Templates.
 */
final public class GeneExprQueryHandler extends QueryHandler {
    DEFactHandler factHandler = null;
    ReporterIDCriteria porbeClonePlatformCrit = null;
    ReporterIDCriteria geneIDCrit = null;
    ReporterIDCriteria regionCrit = null;
    ReporterIDCriteria ontologyCrit = null;
    ReporterIDCriteria pathwayCrit = null;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    boolean includeClones;
    boolean includeProbes;


    private Collection allProbeIDS = Collections.synchronizedCollection(new HashSet());
    private Collection allCloneIDS = Collections.synchronizedCollection(new HashSet());
    private List eventList = Collections.synchronizedList(new ArrayList());
    InheritableThreadLocal tl = new InheritableThreadLocal();
    PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();

    class Listner {
        void retrieveCompleted() {
            //boolean sleep = true;
             for (Iterator iterator = eventList.iterator(); iterator.hasNext();) {
                DBEvent eventObj = (DBEvent)iterator.next();
                if (! eventObj.isCompleted()) {
                    //sleep = true;
                    return;
                }
             }
             // means all events are done i.e status completed
            ThreadGroup parentThreadGroup = Thread.currentThread().getThreadGroup().getParent();
            Thread[] allParentThreads = new Thread[parentThreadGroup.activeCount()];
            parentThreadGroup.enumerate(allParentThreads);
            for (int i = 0; i < allParentThreads.length; i++) {
                Thread thread = allParentThreads[i];
                if (thread.getName().equals("Main Thread")) {
                    thread.interrupt();
                    return;
                }
            }
        }
    }
    public Map handle(gov.nih.nci.nautilus.query.Query query) throws Exception {
        GeneExpressionQuery geQuery = (GeneExpressionQuery) query;

        if (query.getAssociatedView().equals(ViewType.GENE_SINGLE_SAMPLE_VIEW)) {
                factHandler = new DEFactHandler.SingleDEFactHandler();
        }
        if (query.getAssociatedView().equals(ViewType.GENE_GROUP_SAMPLE_VIEW)) {
                factHandler = new DEFactHandler.GroupDEFactHanlder();
        }
        // make sure that platform (for the resulting smaples) is specified
        ArrayPlatformCriteria platObj = geQuery.getArrayPlatformCriteria();
        assert(platObj != null);
        populateProbeAndCloneIncludeFlags(platObj);

        Listner myListner = new Listner();
        tl.set(myListner);
        Thread.currentThread().setName("Main Thread");
        ThreadGroup tg = new ThreadGroup("childGroup");

        if (geQuery.getCloneOrProbeIDCriteria() != null) {
            porbeClonePlatformCrit = CloneProbePlatfromHandler.buildCloneProbePlatformCriteria(geQuery.getCloneOrProbeIDCriteria(), platObj, _BROKER);
            assert(porbeClonePlatformCrit != null);
            SelectHandler handler = new SelectHandler.ProbeCloneIDSelectHandler(porbeClonePlatformCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneIDCrit() != null && geQuery.getGeneIDCrit().getGeneIdentifiers().size() > 0) {
            //String className = (GeneIDCriteriaHandler.getGeneIDClassName(geQuery.getGeneIDCrit())).getName();
            //if (className.equals( GeneIdentifierDE.GeneSymbol.class.getName()))
            geneIDCrit = GeneIDCriteriaHandler.buildGeneIDCriteria(geQuery.getGeneIDCrit(), includeClones, includeProbes, _BROKER);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getRegionCrit() != null) {
            regionCrit = ChrRegionCriteriaHandler.buildRegionCriteria(geQuery.getRegionCrit(), includeClones, includeProbes, _BROKER);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneOntologyCriteria() != null) {
            ontologyCrit = GeneOntologyHandler.buildGeneOntologyIDCriteria(geQuery.getGeneOntologyCriteria(), includeClones, includeProbes, _BROKER);
            assert(ontologyCrit != null);
            SelectHandler handler = new SelectHandler.OntologySelectHandler(ontologyCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getPathwayCriteria() != null) {
           pathwayCrit = GenePathwayHandler.buildPathwayCriteria(geQuery.getPathwayCriteria(), includeClones, includeProbes, _BROKER);
            assert(pathwayCrit != null);
            SelectHandler handler = new SelectHandler.PathwaySelectHandler(pathwayCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

//        Criteria sampleCrit= new Criteria();
//        if (geQuery.getFoldChgCrit() != null)
  //          FoldChangeCriteriaHandler.addFoldChangeCriteria(geQuery.getFoldChgCrit(), _BROKER, sampleCrit);

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

       return factHandler.executeSampleQuery(allProbeIDS, allCloneIDS, geQuery.getFoldChgCrit());
   }

    private Criteria getCombinedIDs() {
        Criteria combinedIDs = null;
        Criteria cloneIDs = new Criteria();
        if (allCloneIDS.size() > 0) {
            cloneIDs.addIn(DifferentialExpressionSfact.CLONE_ID, allCloneIDS);
        }
        Criteria probeIDs = new Criteria();
        if (allProbeIDS.size() > 0) {
            probeIDs.addIn(DifferentialExpressionSfact.PROBESET_ID, allProbeIDS);
        }

        if (cloneIDs.getElements().hasMoreElements() && probeIDs.getElements().hasMoreElements()) {
            cloneIDs.addOrCriteria(probeIDs);
            combinedIDs = cloneIDs;
        }
        else if (cloneIDs.getElements().hasMoreElements()) combinedIDs = cloneIDs;
        else if (probeIDs.getElements().hasMoreElements()) combinedIDs = probeIDs;
        return combinedIDs;
    }


    private void populateProbeAndCloneIncludeFlags(ArrayPlatformCriteria platObj) throws Exception {
        if ((platObj != null) && platObj.getPlatform() != null) {
            ArrayPlatformDE platDE = platObj.getPlatform();
            if (platDE != null) {
                if (platDE.getValueObject().equalsIgnoreCase(Constants.ALL_PLATFROM) ) {
                    includeProbes = true;
                    includeClones = true;
                }
                else if (platDE.getValueObject().equalsIgnoreCase(Constants.AFFY_OLIGO_PLATFORM)) {
                    includeProbes = true;
                }
                else if (platDE.getValueObject().equalsIgnoreCase(Constants.CDNA_ARRAY_PLATFORM)) {
                    includeClones = true;
                }
            }
        }
        else throw new Exception("Array Platform can not be null");
    }


}
