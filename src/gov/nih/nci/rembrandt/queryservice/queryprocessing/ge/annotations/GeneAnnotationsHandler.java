/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;

import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.util.ThreadPool;

import java.util.*;
/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class GeneAnnotationsHandler {
    private static Logger logger = Logger.getLogger(GeneAnnotationsHandler .class);
    private final static int VALUES_PER_THREAD = 100;

    GeneDimension.PathwayDimension pathwayDim;
    GeneDimension.OntologyDimension ontologyDim ;

    List annotationEventList = null;

    private interface Annotation {
        void addAnnotation(GeneExpr.GeneAnnotation annotObj, String annot);
    }

    public GeneAnnotationsHandler(List annotationEventList) {
        this.annotationEventList = annotationEventList;
        pathwayDim = new GeneDimension.PathwayDimension();
        ontologyDim = new GeneDimension.OntologyDimension();
    }

    /**
     *  This method populates the GeneOntology and GenePathway annotations
     * @param geneSymbols GeneSymbols for which the annotations will be retrieved
     * @param geneAnnotations The map that will get populated using gene symbol as key and GeneExpr.GeneAnnotation as value
     * @throws Exception
     */
    public void populateAnnotationsFor(Collection geneSymbols, Map<String, GeneExpr.GeneAnnotation> geneAnnotations) {
        executeGeneAnnotationQuery(geneSymbols, pathwayDim, new PathwayAnnotationHandler(), geneAnnotations);
        executeGeneAnnotationQuery(geneSymbols, ontologyDim, new OntologyAnnotationHandler(), geneAnnotations);
    }

     protected void executeGeneAnnotationQuery(Collection geneSymbols, final GeneDimension dimClassObj, final Annotation handler, final Map<String, GeneExpr.GeneAnnotation> geneAnnotToBePopulated) {
            ArrayList arraySymbols = new ArrayList(geneSymbols);
            for (int i = 0; i < arraySymbols.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arraySymbols.size()) ? endIndex = i : (arraySymbols.size());
                values.addAll(arraySymbols.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(dimClassObj.getGeneSymbolAttr(), values);
                String threadID = "GeneAnnotationsHandler.ThreadID:" + System.currentTimeMillis();
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                            public void codeToRun()  {
                                final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                                pb.clearCache();
                                ReportQueryByCriteria annotQuery =
                                    QueryFactory.newReportQuery(dimClassObj.getDimensionClass(), annotCrit, false);
                                annotQuery.setAttributes(new String[] {dimClassObj.getGeneSymbolAttr(), dimClassObj.getAttr()});
                                Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                                while (iter.hasNext()) {
                                   Object[] geneAttrs = (Object[]) iter.next();
                                   String geneSymbolName = (String)geneAttrs[0];
                                   String attr = (String)geneAttrs[1];
                                     GeneExpr.GeneAnnotation a = (GeneExpr.GeneAnnotation)geneAnnotToBePopulated.get(geneSymbolName);
                                   if (null == a) {
                                       a = new GeneExpr.GeneAnnotation(new ArrayList(), new ArrayList(), geneSymbolName);
                                       geneAnnotToBePopulated.put(geneSymbolName, a);
                                   }
                                   handler.addAnnotation(a, attr);
                               }
                               pb.close();
                               dbEvent.setCompleted(true);
                      }
                   }
               );
               t.start();
            }
    }
    private class PathwayAnnotationHandler implements Annotation{
        public void addAnnotation(GeneExpr.GeneAnnotation annotObj, String annot) {
            annotObj.getPathwayNames().add(annot);
        }
    }

    private class OntologyAnnotationHandler implements Annotation{
        public void addAnnotation(GeneExpr.GeneAnnotation annotObj, String annot) {
            annotObj.getGoIDs().add(annot);
        }
    }
}
