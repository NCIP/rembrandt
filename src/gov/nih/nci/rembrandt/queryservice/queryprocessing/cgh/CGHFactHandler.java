package gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh;

import gov.nih.nci.rembrandt.dbbean.ArrayGenoAbnFact;
import gov.nih.nci.rembrandt.dbbean.GeneLlAccSnp;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.AllGenesCritValidator;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CommonFactHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber.SNPAnnotation;
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
import org.apache.ojb.broker.query.ReportQueryByCriteria;

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

abstract public class CGHFactHandler {
    private static Logger logger = Logger.getLogger(CGHFactHandler.class);
    Map cghObjects = Collections.synchronizedMap(new HashMap());
    Map annotations = Collections.synchronizedMap(new HashMap());
    private final static int VALUES_PER_THREAD = 20;
    List factEventList = Collections.synchronizedList(new ArrayList());
    List annotationEventList = Collections.synchronizedList(new ArrayList());
    abstract void addToResults(Collection results);
    abstract ResultSet[] executeSampleQuery(final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
    throws Exception;
    abstract ResultSet[] executeSampleQueryForAllGenes(final ComparativeGenomicQuery cghQuery)
    throws Exception;

    private static void addCopyNumbFactCriteria(ComparativeGenomicQuery cghQuery, final Class targetFactClass, PersistenceBroker _BROKER, final Criteria sampleCrit) throws Exception {
            CommonFactHandler.addDiseaseCriteria(cghQuery, targetFactClass, _BROKER, sampleCrit);
            CopyNumberCriteriaHandler.addCopyNumberCriteriaForAllGenes(cghQuery, targetFactClass, _BROKER, sampleCrit);
            CommonFactHandler.addSampleIDCriteria(cghQuery, targetFactClass, sampleCrit);
            CommonFactHandler.addAccessCriteria(cghQuery, targetFactClass, sampleCrit);
    }

    protected void executeQuery(final String snpOrCGHAttr, Collection cghOrSNPIDs, final Class targetFactClass, ComparativeGenomicQuery cghQuery) throws Exception {
            ArrayList arrayIDs = new ArrayList(cghOrSNPIDs);
            for (int i = 0; i < arrayIDs.size();) {
                Collection values = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                values.addAll(arrayIDs.subList(begIndex,  endIndex));
                final Criteria IDs = new Criteria();
                IDs.addIn(snpOrCGHAttr, values);
                final String threadID = "CopyNumberChangeCriteriaHandler.ThreadID:" + i;

                final DBEvent.FactRetrieveEvent dbEvent = new DBEvent.FactRetrieveEvent(threadID);
                factEventList.add(dbEvent);
                PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                _BROKER.clearCache();

                final Criteria sampleCrit = new Criteria();
                CommonFactHandler.addDiseaseCriteria(cghQuery, targetFactClass, _BROKER, sampleCrit);
                CopyNumberCriteriaHandler.addCopyNumberCriteria(cghQuery, targetFactClass, _BROKER, sampleCrit);
                CommonFactHandler.addSampleIDCriteria(cghQuery, targetFactClass, sampleCrit);
                CommonFactHandler.addAccessCriteria(cghQuery, targetFactClass, sampleCrit);
                _BROKER.close();

                 ThreadPool.AppThread t = ThreadPool.newAppThread(
                           new ThreadPool.MyRunnable() {
                              public void codeToRun() {
                          final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          pb.clearCache();
                          sampleCrit.addAndCriteria(IDs);
                          logger.debug("Criteria To be exucuted for " + targetFactClass.getName() + ": ");
                          logger.debug(sampleCrit.toString());
                          Query sampleQuery = QueryFactory.newQuery(targetFactClass,sampleCrit, false);
                          assert(sampleQuery != null);
                          Collection exprObjects =  pb.getCollectionByQuery(sampleQuery );
                          addToResults(exprObjects);
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                   }
               );
               t.start();
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
                annotCrit.addIn(GeneLlAccSnp.SNP_PROBESET_ID, values);
                long time = System.currentTimeMillis();
                String threadID = "CGHHandler.ThreadID:" + time;
                final DBEvent.AnnotationRetrieveEvent dbEvent = new DBEvent.AnnotationRetrieveEvent(threadID);
                annotationEventList.add(dbEvent);

                ThreadPool.AppThread t = ThreadPool.newAppThread(
                           new ThreadPool.MyRunnable() {
                              public void codeToRun() {
                                  final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                          ReportQueryByCriteria annotQuery =
                          QueryFactory.newReportQuery(GeneLlAccSnp.class, annotCrit, false);
                          annotQuery.setAttributes(new String[] {GeneLlAccSnp.SNP_PROBESET_ID, GeneLlAccSnp.GENE_SYMBOL, GeneLlAccSnp.LOCUS_LINK_ID, GeneLlAccSnp.ACCESSION});
                          assert(annotQuery != null);
                          Iterator iter =  pb.getReportQueryIteratorByQuery(annotQuery);
                          while (iter.hasNext()) {
                               Object[] attrs = (Object[]) iter.next();
                               Long snpProbID = new Long(((BigDecimal)attrs[0]).longValue());
                              if (snpProbID != null) { 
                                CopyNumber.SNPAnnotation a = (CopyNumber.SNPAnnotation)annotations.get(snpProbID );
                                 if (a == null) {
                                   a = new CopyNumber.SNPAnnotation(snpProbID, new HashSet(), new HashSet(), new HashSet());
                                   annotations.put(snpProbID, a);
                                 }
                                 a.getGeneSymbols().add(attrs[1]);
                                 a.getLocusLinkIDs().add(attrs[2]);
                                 a.getAccessionNumbers().add(attrs[3]);
                              }
                          }
                          pb.close();
                          dbEvent.setCompleted(true);
                      }
                   }
               );
                t.start();
            }
    }

    final static class SingleCGHFactHandler extends CGHFactHandler {
        ResultSet[] executeSampleQueryForAllGenes(final ComparativeGenomicQuery cghQuery)
        throws Exception {
            //logger.debug("Total Number Of SNP_PROBES:" + allSNPProbeIDs.size());
            //executeQuery(ArrayGenoAbnFact.SNP_PROBESET_ID, allSNPProbeIDs, ArrayGenoAbnFact.class, cghQuery);
            AllGenesCritValidator.validateSampleIDCrit(cghQuery);

            PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
            final Criteria sampleCrit = new Criteria();
            addCopyNumbFactCriteria(cghQuery, ArrayGenoAbnFact.class, _BROKER, sampleCrit);
            org.apache.ojb.broker.query.Query sampleQuery =
                QueryFactory.newQuery(ArrayGenoAbnFact.class, sampleCrit, false);



            Collection exprObjects =  _BROKER.getCollectionByQuery(sampleQuery );
            addToResults(exprObjects);
            logger.debug("Length: " + exprObjects.size());
            _BROKER.close();

            Collection allSNPProbeIDs = new ArrayList();
            Collection col = cghObjects.values();
            for (Iterator iterator = col.iterator(); iterator.hasNext();) {
                CopyNumber o =  (CopyNumber)iterator.next();
                allSNPProbeIDs.add(o.getSnpProbesetId());
            }

            executeGeneAnnotationQuery(allSNPProbeIDs);
            ThreadController.sleepOnEvents(annotationEventList);

            // by now CopyNumberObjects and annotations would have populated
            Collection c = cghObjects.values();
            for (Iterator iterator = c.iterator(); iterator.hasNext();) {
                CopyNumber obj =  (CopyNumber)iterator.next();
                if (obj.getSnpProbesetId() != null) {
                    obj.setAnnotations((CopyNumber.SNPAnnotation)annotations.get(obj.getSnpProbesetId()));
                }
            }

            logger.debug("Annotations Retrieved");
            Object[]objs = (cghObjects.values().toArray());
            CopyNumber[] results = new CopyNumber[objs.length];
            for (int j = 0; j < objs.length; j++) {
                results[j] = (CopyNumber) objs[j];
            }
            return results;

        }


        ResultSet[] executeSampleQuery( final Collection allSNPProbeIDs, final ComparativeGenomicQuery cghQuery)
        throws Exception {
            logger.debug("Total Number Of SNP_PROBES:" + allSNPProbeIDs.size());
            executeQuery(ArrayGenoAbnFact.SNP_PROBESET_ID, allSNPProbeIDs, ArrayGenoAbnFact.class, cghQuery);

            ThreadController.sleepOnEvents(factEventList);
            executeGeneAnnotationQuery(allSNPProbeIDs);
            ThreadController.sleepOnEvents(annotationEventList);

            // by now CopyNumberObjects and annotations would have populated
            Object[]objs = (cghObjects.values().toArray());
            CopyNumber[] results = new CopyNumber[objs.length];
            for (int i = 0; i < objs.length; i++) {
                CopyNumber obj = (CopyNumber) objs[i];
                if (obj.getSnpProbesetId() != null) {
                    obj.setAnnotations((CopyNumber.SNPAnnotation)annotations.get(obj.getSnpProbesetId()));
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
                cghObjects.put(resObj.getAgaID(), resObj);
                resObj = null;
            }
        }
        private void copyTo(CopyNumber resultObj, ArrayGenoAbnFact factObj) {
            resultObj.setAgaID(factObj.getAgaId());
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
            resultObj.setPhysicalPosition(factObj.getPhysicalPosition());
            resultObj.setChromosome(factObj.getChromosome());
        }
    }
}


