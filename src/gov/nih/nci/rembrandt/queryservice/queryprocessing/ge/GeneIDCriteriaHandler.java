/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.dbbean.GeneClone;
import gov.nih.nci.rembrandt.dbbean.GeneLlAccSnp;
import gov.nih.nci.rembrandt.dbbean.GeneSnp;
import gov.nih.nci.rembrandt.dbbean.GeneSnpSegment;
import gov.nih.nci.rembrandt.dbbean.LlSnp;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CGHReporterIDCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
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

public class GeneIDCriteriaHandler {
     private final static int VALUES_PER_THREAD = 200;
     static HashMap data = new HashMap();
        static {
            data.put(GeneIdentifierDE.GeneSymbol.class.getName(),
                            new TargetClass(GeneSnpSegment.class, GeneSnpSegment.SNP_SEGMENT_ID));
            data.put(GeneIdentifierDE.LocusLink.class.getName(),
                            new TargetClass(LlSnp.class, LlSnp.SNP_PROBESET_ID));
            data.put(GeneIdentifierDE.GenBankAccessionNumber.class.getName(),
                            new TargetClass(GeneLlAccSnp.class, GeneSnp.SNP_PROBESET_ID));
        }

     public static ArrayList getGeneIDValues(GeneIDCriteria geneIDCrit) {
        Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        ArrayList geneIDs = new ArrayList();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();) {
            GeneIdentifierDE obj  = (GeneIdentifierDE) iterator.next();
            String value = null;
           // if (obj.getGeneIDType().equals(GeneIdentifierDE.GENESYMBOL))
               value = obj.getValueObject().toUpperCase();
            //else value = obj.getValueObject();
            geneIDs.add(value);
        }
        return geneIDs;
    }


    public static CGHReporterIDCriteria  buildReporterIDCritForCGHQuery(GeneIDCriteria  geneIDCrit, boolean includeSNPs, boolean includeCGH, PersistenceBroker pb) throws Exception {
            Class deClass = getGeneIDClassName(geneIDCrit);
            ArrayList geneIDs = getGeneIDValues(geneIDCrit);
            CGHReporterIDCriteria  snpProbeIDCrit = new CGHReporterIDCriteria ();

            if ( includeSNPs) {
                TargetClass t = (TargetClass) data.get(deClass.getName());
                snpProbeIDCrit = buildGeneSymbolCrit(pb, deClass, geneIDs, t.target, t.attrToSearch);
            }
            if (includeCGH)  {
                // TODO: Post Nautilus
            }
            return snpProbeIDCrit;
    }
    private static class  TargetClass {
        Class target;
        String attrToSearch;
        public TargetClass(Class targetClass, String attrToSearch) {
            this.target = targetClass;
            this.attrToSearch = attrToSearch;
        }
    }

    private static CGHReporterIDCriteria buildGeneSymbolCrit(PersistenceBroker pb,Class deClass, ArrayList geneIDs, Class targetClass, String attrToRetrieve) throws Exception {
        CGHReporterIDCriteria snpProbeIDCrit = new CGHReporterIDCriteria();
        String geneIDAttr = QueryHandler.getAttrNameForTheDE(deClass.getName(), targetClass.getName());
        String geneIDCol = QueryHandler.getColumnNameForBean(pb, targetClass.getName(), geneIDAttr );

        Criteria snpProbesetIDCrit = new  Criteria();
        snpProbesetIDCrit.addIn(geneIDCol, geneIDs);
        //String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, GeneSnp.class.getName(), );
        ReportQueryByCriteria snpSubQuery = QueryFactory.newReportQuery(targetClass,snpProbesetIDCrit, false );
        snpSubQuery.setAttributes(new String[] {attrToRetrieve}) ;
        snpProbeIDCrit.setSnpProbeIDsSubQuery(snpSubQuery);
        return  snpProbeIDCrit;
    }

    public static GEReporterIDCriteria buildReporterIDCritForGEQuery(GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes) throws Exception {
        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        pb.clearCache();
        Class deClass = getGeneIDClassName(geneIDCrit);
        ArrayList arrayIDs = getGeneIDValues(geneIDCrit);
        GEReporterIDCriteria cloneIDProbeIDCrit = new GEReporterIDCriteria();

         for (int i = 0; i < arrayIDs.size();) {
                Collection geneIDValues = new ArrayList();
                int begIndex = i;
                i += VALUES_PER_THREAD ;
                int endIndex = (i < arrayIDs.size()) ? endIndex = i : (arrayIDs.size());
                geneIDValues.addAll(arrayIDs.subList(begIndex,  endIndex));
                if ( includeProbes) {
                    String probeIDColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
                    String deMappingAttrName = QueryHandler.getAttrNameForTheDE(deClass.getName(), ProbesetDim.class.getName());
                    String deMappingAttrColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), deMappingAttrName);
                    Criteria c = new Criteria();
                    c.addColumnIn( deMappingAttrColumn , geneIDValues);
                    ReportQueryByCriteria probeIDSubQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {probeIDColumn}, c, false );
                    cloneIDProbeIDCrit.getMultipleProbeIDsSubQueries().add(probeIDSubQuery);
                }
                if (includeClones) {
                     String cloneIDColumn = QueryHandler.getColumnNameForBean(pb, GeneClone.class.getName(), GeneClone.CLONE_ID);
                     String deMappingAttrName = QueryHandler.getAttrNameForTheDE(deClass.getName(), GeneClone.class.getName());
                     String deMappingAttrColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), deMappingAttrName);
                     Criteria c = new Criteria();
                     c.addIn( deMappingAttrName , geneIDValues);
                     ReportQueryByCriteria cloneIDSubQuery = QueryFactory.newReportQuery(GeneClone.class, new String[] {cloneIDColumn}, c, false );
                     cloneIDProbeIDCrit.getMultipleCloneIDsSubQueries().add(cloneIDSubQuery);
                }
        }

       	pb.close(); // Release broker instance to the broker-pool
        return cloneIDProbeIDCrit;
    }


    public static Class getGeneIDClassName(GeneIDCriteria geneIDCrit ) {
            Collection geneIDs = geneIDCrit.getGeneIdentifiers();
            GeneIdentifierDE obj =  (GeneIdentifierDE) geneIDs.iterator().next();
            return obj.getClass();
    }
}
