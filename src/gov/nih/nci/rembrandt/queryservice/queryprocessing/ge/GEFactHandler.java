package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.rembrandt.dbbean.DifferentialExpressionGfact;
import gov.nih.nci.rembrandt.dbbean.DifferentialExpressionSfact;
import gov.nih.nci.rembrandt.dbbean.GeneClone;
import gov.nih.nci.rembrandt.dbbean.GeneOntology;
import gov.nih.nci.rembrandt.dbbean.GenePathway;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.AllGenesCritValidator;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CommonFactHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.util.ThreadPool;

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

/**
 * @author BhattarR
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

abstract public class GEFactHandler {
    private static Logger logger = Logger.getLogger(GEFactHandler.class);
     Map geneExprObjects = Collections.synchronizedMap(new HashMap());
     Map cloneAnnotations = Collections.synchronizedMap(new HashMap());
     Map probeAnnotations = Collections.synchronizedMap(new HashMap());
     Map geneAnnotations = Collections.synchronizedMap(new HashMap());
     Integer geneExprCount = 0;
     private final static int VALUES_PER_THREAD = 50;
     List factEventList = Collections.synchronizedList(new ArrayList());
     abstract void addToResults(Collection results);
     abstract void addCountToResults(int count);
     List annotationEventList = Collections.synchronizedList(new ArrayList());
     abstract ResultSet[] executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, GeneExpressionQuery query)
     throws Exception;

     abstract ResultSet[] executeSampleQueryForAllGenes(GeneExpressionQuery query)
     throws Exception;


      protected void executeQuery(final String probeOrCloneIDAttr, Collection probeOrCloneIDs, final Class targetFactClass, GeneExpressionQuery geQuery ) throws Exception {
            ArrayList arrayIDs = new ArrayList(probeOrCloneIDs);
            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria IDs = new Criteria();
                IDs.addIn(probeOrCloneIDAttr, values);
                String threadID = "GEFactHandler.ThreadID:" + probeOrCloneIDAttr + ":" +i;

                final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                factEventList.add(dbEvent);

                PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                final Criteria sampleCrit = new Criteria();
                addGEFactCriteria(geQuery, targetFactClass, _BROKER, sampleCrit);
                _BROKER.close();

                ThreadPool.AppThread t = ThreadPool.newAppThread(
                           new ThreadPool.MyRunnable() {
                              public void codeToRun() {
                                  final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                                  pb.clearCache();
                                  sampleCrit.addAndCriteria(IDs);
                                  org.apache.ojb.broker.query.Query sampleQuery =
                                  QueryFactory.newQuery(targetFactClass,sampleCrit, false);
                                  assert(sampleQuery != null);
                                  Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                                  addToResults(exprObjects);
                                  pb.close();
                                  dbEvent.setCompleted(true);
                      }
                   }
               );
               logger.debug("BEGIN: (from GEFactHandler.executeQuery()) Thread Count: " + ThreadPool.THREAD_COUNT);
               t.start();
            }
    }
      protected void executeCountQuery(final String probeOrCloneIDAttr, Collection probeOrCloneIDs, final Class targetFactClass, GeneExpressionQuery geQuery ) throws Exception {
          ArrayList arrayIDs = new ArrayList(probeOrCloneIDs);
          for (int i = 0; i < arrayIDs.size();) {
              Collection values = new ArrayList();
              int begIndex = i;
              i += VALUES_PER_THREAD ;
              int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
              values.addAll(arrayIDs.subList(begIndex,  endIndex));
              final Criteria IDs = new Criteria();
              IDs.addIn(probeOrCloneIDAttr, values);
              String threadID = "GEFactHandler.ThreadID:" + probeOrCloneIDAttr + ":" +i;

              final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
              factEventList.add(dbEvent);

              PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
              final Criteria sampleCrit = new Criteria();
              addGEFactCriteria(geQuery, targetFactClass, _BROKER, sampleCrit);
              _BROKER.close();

              ThreadPool.AppThread t = ThreadPool.newAppThread(
                         new ThreadPool.MyRunnable() {
                            public void codeToRun() {
                                final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                                pb.clearCache();
                                sampleCrit.addAndCriteria(IDs);
                                org.apache.ojb.broker.query.Query sampleQuery =
                                QueryFactory.newQuery(targetFactClass,sampleCrit, false);
                                assert(sampleQuery != null);
                                int exprObjCount =  pb.getCount(sampleQuery );
                                addCountToResults(exprObjCount);
                                pb.close();
                                dbEvent.setCompleted(true);
                    }


                 }
             );
             logger.debug("BEGIN: (from GEFactHandler.executeQuery()) Thread Count: " + ThreadPool.THREAD_COUNT);
             t.start();
          }
  }
    private static void addGEFactCriteria(GeneExpressionQuery geQuery, final Class targetFactClass, PersistenceBroker _BROKER, final Criteria sampleCrit) throws Exception {
       FoldChangeCriteriaHandler.addFoldChangeCriteria(geQuery, targetFactClass, _BROKER, sampleCrit);
       CommonFactHandler.addDiseaseCriteria(geQuery, targetFactClass, _BROKER, sampleCrit);       
       CommonFactHandler.addSampleIDCriteria(geQuery, targetFactClass, sampleCrit);
       CommonFactHandler.addAccessCriteria(geQuery, targetFactClass, sampleCrit);

    }
    private static void addGEFactCriteriaForAllGenes(GeneExpressionQuery geQuery, final Class targetFactClass, PersistenceBroker _BROKER, final Criteria sampleCrit) throws Exception {
        FoldChangeCriteriaHandler.addFoldChangeCriteriaForAllGenes(geQuery, targetFactClass, _BROKER, sampleCrit);
        CommonFactHandler.addDiseaseCriteria(geQuery, targetFactClass, _BROKER, sampleCrit);
        CommonFactHandler.addSampleIDCriteria(geQuery, targetFactClass, sampleCrit);
        CommonFactHandler.addAccessCriteria(geQuery, targetFactClass, sampleCrit);

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
                String threadID = "GEFactHandler.ThreadID:" +time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();
                final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.LOCUS_LINK);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.ACCESSION_NUMBER);
                final String cloneIDColName = QueryHandler.getColumnNameForBean(_BROKER, GeneClone.class.getName(), GeneClone.CLONE_ID);
                _BROKER.close();
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                            public void codeToRun() {
                              final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                              pb.clearCache();
                              Query annotQuery =
                              QueryFactory.newReportQuery(GeneClone.class,new String[] {cloneIDColName, locusLinkColName, accessionColName }, annotCrit, false);
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
                              pb.close();
                              dbEvent.setCompleted(true);
                          }
                    }
               );
               logger.debug("BEGIN (from GEFactHandler.executeCloneAnnotationQuery()): Thread Count: " + ThreadPool.THREAD_COUNT);
               t.start();
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
                String threadID = "GEFactHandler.ThreadID:" +time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();
                final String locusLinkColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.LOCUS_LINK);
                final String accessionColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.ACCESSION_NUMBER);
                final String probeIDColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
                _BROKER.close();
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                            public void codeToRun()  {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          pb.clearCache();
                          Query annotQuery =
                          QueryFactory.newReportQuery(ProbesetDim.class,new String[] {probeIDColName , locusLinkColName, accessionColName }, annotCrit, false);
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
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                   }
               );
               logger.debug("BEGIN (from GEFactHandler.executeProbeAnnotationQuery()): Thread Count: " + ThreadPool.THREAD_COUNT);
               t.start();
            }
    }
    protected void executeGenePathwayAnnotationQuery(Collection geneSymbols) throws Exception {
            ArrayList arraySymbols = new ArrayList(geneSymbols);

            for (int i = 0; i < arraySymbols.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arraySymbols.size()) ? endIndex = i : (arraySymbols.size());
                values.addAll(arraySymbols.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(GenePathway.GENE_SYMBOL, values);
                long time = System.currentTimeMillis();
                String threadID = "GEFactHandler.ThreadID:" +time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();
                final String pathwayColName = QueryHandler.getColumnNameForBean(_BROKER, GenePathway.class.getName(), GenePathway.PATHWAY_NAME);
                final String geneSymbolColName = QueryHandler.getColumnNameForBean(_BROKER, GenePathway.class.getName(), GenePathway.GENE_SYMBOL);
                //final String probeIDColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
                _BROKER.close();
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                            public void codeToRun()  {
                                final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                                pb.clearCache();
                                Query annotQuery =
                                    QueryFactory.newReportQuery(GenePathway.class,new String[] {geneSymbolColName, pathwayColName}, annotCrit, false);
                                Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                                while (iter.hasNext()) {
                                   Object[] geneAttrs = (Object[]) iter.next();
                                   String geneSymbolName = (String)geneAttrs[0];
                                   String pathwayName = (String)geneAttrs[1];
                                     GeneExpr.GeneAnnotation a = (GeneExpr.GeneAnnotation)geneAnnotations.get(geneSymbolName);
                                   if (null == a) {
                                       a = new GeneExpr.GeneAnnotation(new ArrayList(), new ArrayList(), geneSymbolName);
                                       geneAnnotations.put(geneSymbolName, a);
                                   }
                                   a.pathwayNames.add(pathwayName);
                               }
                               pb.close();
                               dbEvent.setCompleted(true);
                      }
                   }
               );
               t.start();
            }
    }
     protected void executeGeneOntologyAnnotationQuery(Collection geneSymbols) throws Exception {
            ArrayList arraySymbols = new ArrayList(geneSymbols);

            for (int i = 0; i < arraySymbols.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arraySymbols.size()) ? endIndex = i : (arraySymbols.size());
                values.addAll(arraySymbols.subList(begIndex,  endIndex));
                final Criteria annotCrit = new Criteria();
                annotCrit.addIn(GeneOntology.GENE_SYMBOL, values);
                long time = System.currentTimeMillis();
                String threadID = "GEFactHandler.ThreadID:" +time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);
                final PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();
                final String goColName = QueryHandler.getColumnNameForBean(_BROKER, GeneOntology.class.getName(), GeneOntology.GO_ID);
                final String geneSymbolColName = QueryHandler.getColumnNameForBean(_BROKER, GeneOntology.class.getName(), GeneOntology.GENE_SYMBOL);
                //final String probeIDColName = QueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
                _BROKER.close();
                ThreadPool.AppThread t = ThreadPool.newAppThread(
                       new ThreadPool.MyRunnable() {
                            public void codeToRun()  {
                                final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                                pb.clearCache();
                                Query annotQuery =
                                    QueryFactory.newReportQuery(GeneOntology.class,new String[] {geneSymbolColName, goColName}, annotCrit, false);
                                Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                                while (iter.hasNext()) {
                                   Object[] geneAttrs = (Object[]) iter.next();
                                   String geneSymbolName = (String)geneAttrs[0];
                                   String goID = (String)geneAttrs[1];
                                     GeneExpr.GeneAnnotation a = (GeneExpr.GeneAnnotation)geneAnnotations.get(geneSymbolName);
                                   if (null == a) {
                                       a = new GeneExpr.GeneAnnotation(new ArrayList(), new ArrayList(), geneSymbolName);
                                       geneAnnotations.put(geneSymbolName, a);
                                   }
                                   a.goIDs.add(goID);
                               }
                               pb.close();
                               dbEvent.setCompleted(true);
                      }
                   }
               );
               t.start();
            }
    }


    public final static class SingleGEFactHandler extends GEFactHandler {
        ResultSet[] executeSampleQueryForAllGenes(GeneExpressionQuery geQuery)
        throws Exception {
            AllGenesCritValidator.validateSampleIDCrit(geQuery);

            PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
            final Criteria sampleCrit = new Criteria();

            addGEFactCriteriaForAllGenes(geQuery, DifferentialExpressionSfact.class, _BROKER, sampleCrit);
            org.apache.ojb.broker.query.Query sampleQuery =
                    QueryFactory.newQuery(DifferentialExpressionSfact.class,sampleCrit, false);
            Collection exprObjects =  _BROKER.getCollectionByQuery(sampleQuery );
            addToResults(exprObjects);
            _BROKER.close();

            Collection allProbeIDs = new HashSet();
            Collection allCloneIDs = new HashSet();

            Object[]objs = (geneExprObjects.values().toArray());
            GeneExpr.GeneExprSingle[] results = new GeneExpr.GeneExprSingle[objs.length];
            for (int i = 0; i < objs.length; i++) {
                GeneExpr obj = (GeneExpr) objs[i];
                if (obj.getProbesetId() != null) allProbeIDs.add(obj.getProbesetId());
                else if (obj.getCloneId() != null) allCloneIDs.add(obj.getCloneId());
            }

            HashSet geneSymbols = new HashSet();
            for (int i = 0; i < objs.length; i++) {
                GeneExpr obj = (GeneExpr.GeneExprSingle) objs[i];
                if (obj.getGeneSymbol() != null) geneSymbols.add(obj.getGeneSymbol());
            }

            executeGenePathwayAnnotationQuery(geneSymbols);
            executeGeneOntologyAnnotationQuery(geneSymbols);
            executeCloneAnnotationQuery(allCloneIDs);
            executeProbeAnnotationQuery(allProbeIDs );
            ThreadController.sleepOnEvents(annotationEventList);

            for (int i = 0; i < objs.length; i++) {
                GeneExpr.GeneExprSingle obj = (GeneExpr.GeneExprSingle) objs[i];
                if (obj.getProbesetId() != null) {
                    obj.setAnnotation((GeneExpr.ProbeAnnotaion)probeAnnotations.get(obj.getProbesetId()));
                }
                else if (obj.getCloneId() != null) {
                    obj.setAnnotation((GeneExpr.CloneAnnotaion)cloneAnnotations.get(obj.getCloneId()));
                }
                if (obj.getGeneSymbol() != null  && obj.getAnnotation() != null) {
                    obj.getAnnotation().setGeneAnnotation((GeneExpr.GeneAnnotation)geneAnnotations.get(obj.getGeneSymbol()));
                }
                results[i] = obj;
            }


            return results;
        }

        ResultSet[] executeSampleQuery( final Collection allProbeIDs, final Collection allCloneIDs, GeneExpressionQuery query )
        throws Exception {
            logger.debug("Total Number Of Probes:" + allProbeIDs.size());
            logger.debug("Total Number Of Clones:" + allCloneIDs.size());

            executeQuery(DifferentialExpressionSfact.PROBESET_ID, allProbeIDs, DifferentialExpressionSfact.class, query);
            executeQuery(DifferentialExpressionSfact.CLONE_ID, allCloneIDs, DifferentialExpressionSfact.class, query);
            ThreadController.sleepOnEvents(factEventList);
            HashSet geneSymbols = new HashSet();
            Object[]objs = (geneExprObjects.values().toArray());
            for (int i = 0; i < objs.length; i++) {
                GeneExpr.GeneExprSingle obj = (GeneExpr.GeneExprSingle) objs[i];
                if (obj.getGeneSymbol() != null) geneSymbols.add(obj.getGeneSymbol());
            }

            executeGenePathwayAnnotationQuery(geneSymbols);
            executeGeneOntologyAnnotationQuery(geneSymbols);
            executeCloneAnnotationQuery(allCloneIDs);
            executeProbeAnnotationQuery(allProbeIDs );
            ThreadController.sleepOnEvents(annotationEventList);

            // by now geneExprObjects,  geneAnnotations, cloneAnnotations, probeAnnotations would have populated
            GeneExpr.GeneExprSingle[] results = new GeneExpr.GeneExprSingle[objs.length];
            for (int i = 0; i < objs.length; i++) {
                GeneExpr.GeneExprSingle obj = (GeneExpr.GeneExprSingle) objs[i];
                if (obj.getProbesetId() != null) {
                    obj.setAnnotation((GeneExpr.ProbeAnnotaion)probeAnnotations.get(obj.getProbesetId()));
                }
                else if (obj.getCloneId() != null) {
                    obj.setAnnotation((GeneExpr.CloneAnnotaion)cloneAnnotations.get(obj.getCloneId()));
                }
                if (obj.getGeneSymbol() != null  && obj.getAnnotation()!= null  && geneAnnotations != null) {
                    obj.getAnnotation().setGeneAnnotation((GeneExpr.GeneAnnotation)geneAnnotations.get(obj.getGeneSymbol()));
                }
                results[i] = obj;
            }
            return results;
        }
        Integer getSampleQueryCount( final Collection allProbeIDs, final Collection allCloneIDs, GeneExpressionQuery query )
        throws Exception {
            logger.debug("Total Number Of Probes:" + allProbeIDs.size());
            logger.debug("Total Number Of Clones:" + allCloneIDs.size());

            executeCountQuery(DifferentialExpressionSfact.PROBESET_ID, allProbeIDs, DifferentialExpressionSfact.class, query);
            executeCountQuery(DifferentialExpressionSfact.CLONE_ID, allCloneIDs, DifferentialExpressionSfact.class, query);
            ThreadController.sleepOnEvents(factEventList);
            
            return geneExprCount;
        }
        void addToResults(Collection exprObjects) {
            for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                DifferentialExpressionSfact exprObj = (DifferentialExpressionSfact) iterator.next();
                GeneExpr.GeneExprSingle singleExprObj = new GeneExpr.GeneExprSingle();
                copyTo(singleExprObj, exprObj);
                geneExprObjects.put(singleExprObj.getDesId(), singleExprObj);
                exprObj = null;
            }
      //      int i = geneExprObjects.size();
        }
		synchronized void addCountToResults(int count) {
			geneExprCount += count;
			
		}
        public static void copyTo(GeneExpr.GeneExprSingle singleExprObj, DifferentialExpressionSfact exprObj) {
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
            singleExprObj.setSampleIntensity(exprObj.getSampleIntensity());
            singleExprObj.setGeneSymbol(exprObj.getGeneSymbol());
            singleExprObj.setSampleId(exprObj.getSampleId());
            singleExprObj.setSpecimenName(exprObj.getSpecimenName());
            singleExprObj.setGenderCode(exprObj.getGenderCode());
            singleExprObj.setInstitutionName(exprObj.getInstitutionName());
            if (exprObj.getProbesetId() != null ) {
                singleExprObj.setProbesetId(exprObj.getProbesetId());

            }
            singleExprObj.setProbesetName(exprObj.getProbesetName());
            singleExprObj.setSurvivalLengthRange(exprObj.getSurvivalLengthRange());
            singleExprObj.setTimecourseId(exprObj.getTimecourseId());
        }
    }
    final static class GroupGEFactHanlder extends GEFactHandler {
            ResultSet[] executeSampleQuery(final Collection allProbeIDs, final Collection allCloneIDs, GeneExpressionQuery query )
            throws Exception {
                if (query.getSampleIDCrit() != null)
                    throw new Exception("Samples can not be applied to this gorup view ");
                executeQuery(DifferentialExpressionGfact.PROBESET_ID, allProbeIDs, DifferentialExpressionGfact.class, query);
                executeQuery(DifferentialExpressionGfact.CLONE_ID, allCloneIDs, DifferentialExpressionGfact.class, query);
                //sleepOnFactEvents();
                ThreadController.sleepOnEvents(factEventList);

                // geneExprObjects would have populated by this time Convert these in to Result objects
                Object[]objs = (geneExprObjects.values().toArray());
                GeneExpr.GeneExprGroup[] results = new GeneExpr.GeneExprGroup[objs.length];
                for (int i = 0; i < objs.length; i++) {
                    GeneExpr.GeneExprGroup obj = (GeneExpr.GeneExprGroup) objs[i];
                    results[i] = obj;
                }
                return results;
            }

            ResultSet[] executeSampleQueryForAllGenes(GeneExpressionQuery query) throws Exception {
                throw new Exception ("This method is not Supported for Disease Group");
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
                groupExprObj.setStandardDeviationRatio(exprObj.getStandardDeviationRatio());
            }

			@Override
			synchronized void addCountToResults(int count) {
				// TODO Auto-generated method stub
				
			}
        }
    }


