package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.ReporterAll;
import gov.nih.nci.nautilus.data.GeneOntology;
import gov.nih.nci.nautilus.data.DifferentialExpressionGfact;
import gov.nih.nci.nautilus.view.ViewType;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.ge.*;
import gov.nih.nci.nautilus.queryprocessing.ge.CloneProbePlatfromHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.GEFactHandler;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;

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
    GEFactHandler factHandler = null;
    //GEReporterIDCriteria porbeClonePlatformCrit = null;
    //GEReporterIDCriteria geneIDCrit = null;
    //GEReporterIDCriteria regionCrit = null;
    //GEReporterIDCriteria ontologyCrit = null;
    //GEReporterIDCriteria pathwayCrit = null;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    boolean includeClones;
    boolean includeProbes;



    private Collection allProbeIDS = Collections.synchronizedCollection(new HashSet());
    private Collection allCloneIDS = Collections.synchronizedCollection(new HashSet());
    private List eventList = Collections.synchronizedList(new ArrayList());
    InheritableThreadLocal tl = new InheritableThreadLocal();
    PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();

    public ResultSet[] handle(gov.nih.nci.nautilus.query.Query query) throws Exception {
        GeneExpressionQuery geQuery = (GeneExpressionQuery) query;

        if (query.getAssociatedView().equals(ViewType.GENE_SINGLE_SAMPLE_VIEW) ||
                query.getAssociatedView().equals(ViewType.CLINICAL_VIEW) )
                factHandler = new GEFactHandler.SingleGEFactHandler();
        else if (query.getAssociatedView().equals(ViewType.GENE_GROUP_SAMPLE_VIEW))
                factHandler = new GEFactHandler.GroupGEFactHanlder();
        else throw new Exception("Illegal View.  This view is not supported in this Query:");

        // make sure that platform (for the resulting smaples) is specified
        ArrayPlatformCriteria platObj = geQuery.getArrayPlatformCriteria();
        assert(platObj != null);
        populateProbeAndCloneIncludeFlags(platObj);

        ThreadGroup tg = new ThreadGroup("childGroup");

        if (geQuery.getCloneOrProbeIDCriteria() != null) {
            GEReporterIDCriteria porbeClonePlatformCrit = CloneProbePlatfromHandler.buildCloneProbePlatformCriteria(geQuery.getCloneOrProbeIDCriteria(), platObj, _BROKER);
            assert(porbeClonePlatformCrit != null);
            SelectHandler handler = new SelectHandler.ProbeCloneIDSelectHandler(porbeClonePlatformCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneIDCrit() != null && geQuery.getGeneIDCrit().getGeneIdentifiers().size() > 0) {
            GEReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(geQuery.getGeneIDCrit(), includeClones, includeProbes, _BROKER);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getRegionCrit() != null) {
            GEReporterIDCriteria regionCrit = gov.nih.nci.nautilus.queryprocessing.ge.ChrRegionCriteriaHandler.buildGERegionCriteria(geQuery.getRegionCrit(), includeClones, includeProbes, _BROKER);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneOntologyCriteria() != null) {
            GEReporterIDCriteria ontologyCrit  = GeneOntologyHandler.buildGeneOntologyIDCriteria(geQuery.getGeneOntologyCriteria(), includeClones, includeProbes, _BROKER);
            assert(ontologyCrit != null);
            SelectHandler handler = new SelectHandler.OntologySelectHandler(ontologyCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getPathwayCriteria() != null) {
            GEReporterIDCriteria pathwayCrit = GenePathwayHandler.buildPathwayCriteria(geQuery.getPathwayCriteria(), includeClones, includeProbes, _BROKER);
            assert(pathwayCrit != null);
            SelectHandler handler = new SelectHandler.PathwaySelectHandler(pathwayCrit, allProbeIDS, allCloneIDS, _BROKER);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

       ThreadController.sleepOnEvents(eventList);

       return factHandler.executeSampleQuery(allProbeIDS, allCloneIDS, geQuery);
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
