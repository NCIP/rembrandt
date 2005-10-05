package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.view.ClinicalSampleView;
import gov.nih.nci.rembrandt.queryservice.view.CopyNumberSampleView;
import gov.nih.nci.rembrandt.queryservice.view.GeneExprDiseaseView;
import gov.nih.nci.rembrandt.queryservice.view.GeneExprSampleView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author BhattarR
 */
final public class GeneExprQueryHandler extends QueryHandler {
    GEFactHandler factHandler = null;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    boolean includeClones;
    boolean includeProbes;
    private Collection allProbeIDS = Collections.synchronizedCollection(new HashSet());
    private Collection allCloneIDS = Collections.synchronizedCollection(new HashSet());
    private List eventList = Collections.synchronizedList(new ArrayList());

    public ResultSet[] handle(gov.nih.nci.caintegrator.dto.query.Query query) throws Exception {
        GeneExpressionQuery geQuery = (GeneExpressionQuery) query;
                                                      
        if (query.getAssociatedView() instanceof GeneExprSampleView ||
                query.getAssociatedView()instanceof ClinicalSampleView ||
				query.getAssociatedView()instanceof CopyNumberSampleView)
                factHandler = new GEFactHandler.SingleGEFactHandler();
        else if (query.getAssociatedView() instanceof GeneExprDiseaseView)
                factHandler = new GEFactHandler.GroupGEFactHanlder();
        else throw new Exception("Illegal View.  This view is not supported in this Query:");

        // make sure that platform (for the resulting smaples) is specified
        ArrayPlatformCriteria platObj = geQuery.getArrayPlatformCriteria();
        assert(platObj != null);
        populateProbeAndCloneIncludeFlags(platObj);
        AllGenesCriteria allGenesCrit = geQuery.getAllGenesCrit();
        if (allGenesCrit!=null&&allGenesCrit.isAllGenes() ) {

            if (! (factHandler instanceof GEFactHandler.SingleGEFactHandler))
            throw new Exception("AllGenes criteria is not allowed for Disease view");

            if (null == geQuery.getSampleIDCrit())
                throw new Exception("Sample IDs are required when All Genes is specified");

            return factHandler.executeSampleQueryForAllGenes(geQuery);
        }
        
        ThreadGroup tg = new ThreadGroup("childGroup");

        if (geQuery.getCloneOrProbeIDCriteria() != null) {
            GEReporterIDCriteria porbeClonePlatformCrit = CloneProbePlatfromHandler.buildCloneProbePlatformCriteria(geQuery.getCloneOrProbeIDCriteria(), platObj);
            assert(porbeClonePlatformCrit != null);
            SelectHandler handler = new SelectHandler.ProbeCloneIDSelectHandler(porbeClonePlatformCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneIDCrit() != null && geQuery.getGeneIDCrit().getGeneIdentifiers().size() > 0) {
            GEReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(geQuery.getGeneIDCrit(), includeClones, includeProbes);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
         
        }

        if (geQuery.getRegionCrit() != null) {
            GEReporterIDCriteria regionCrit = gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.ChrRegionCriteriaHandler.buildGERegionCriteria(geQuery.getRegionCrit(), includeClones, includeProbes);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneOntologyCriteria() != null) {
            GEReporterIDCriteria ontologyCrit  = GeneOntologyHandler.buildGeneOntologyIDCriteria(geQuery.getGeneOntologyCriteria(), includeClones, includeProbes);
            assert(ontologyCrit != null);
            SelectHandler handler = new SelectHandler.OntologySelectHandler(ontologyCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getPathwayCriteria() != null) {
            GEReporterIDCriteria pathwayCrit = GenePathwayHandler.buildPathwayCriteria(geQuery.getPathwayCriteria(), includeClones, includeProbes);
            assert(pathwayCrit != null);
            SelectHandler handler = new SelectHandler.PathwaySelectHandler(pathwayCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        //_BROKER.close();

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
