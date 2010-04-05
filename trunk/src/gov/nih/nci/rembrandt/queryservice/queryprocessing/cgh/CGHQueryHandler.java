package gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.AssayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.de.AssayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.rembrandt.dbbean.SnpProbesetDim;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.ChrRegionCriteriaHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneIDCriteriaHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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

public class CGHQueryHandler extends QueryHandler {
    boolean includeCGH;
    boolean includeSNPs;
    private List eventList = Collections.synchronizedList(new ArrayList());

    private Collection allSNPProbesetIDs = Collections.synchronizedCollection(new HashSet());
    public ResultSet[] handle(Query query) throws Exception{
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) query;

        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        populateIncludeCGHAndSNPFlags(cghQuery.getAssayPlatformCriteria());

        AllGenesCriteria allGenesCrit = cghQuery.getAllGenesCrit();
        if (allGenesCrit!=null && allGenesCrit.isAllGenes() ) {
             if (null == cghQuery.getSampleIDCrit())
                throw new Exception("Sample IDs are required when All Genes is specified");
             return new CGHFactHandler.SingleCGHFactHandler().executeSampleQueryForAllGenes(cghQuery);
        }

        handleSNPCriteria( cghQuery, pb);

        if(cghQuery.getCloneOrProbeIDCriteria() != null) {
            throw new Exception (" Only BACClone will be implemented post Nautilus ");
        }

        pb.close();
        ThreadController.sleepOnEvents(eventList);

        return new CGHFactHandler.SingleCGHFactHandler().executeSampleQuery(allSNPProbesetIDs, cghQuery);

    }

    private CGHReporterIDCriteria buildSNPCriteria(SNPCriteria c, PersistenceBroker pb) throws Exception {
        String snpType = getType(c);
        Collection inputIDs = new ArrayList();
        ReportQueryByCriteria snpProbeIDsQuery = null;

        for (Iterator iterator = c.getIdentifiers().iterator(); iterator.hasNext();)
             inputIDs.add(((SNPIdentifierDE) iterator.next()).getValueObject());

        String nameCol  = null;
        if (snpType.equals(SNPIdentifierDE.DBSNP)) {
           nameCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.DB_SNP_ID );
        }
        else if (snpType.equals(SNPIdentifierDE.SNP_PROBESET)) {
           nameCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.PROBESET_NAME);
        }

        Criteria sCrit = new Criteria();
        sCrit.addColumnIn(nameCol, inputIDs);
        String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.SNP_PROBESET_ID);

        snpProbeIDsQuery =  QueryFactory.newReportQuery(SnpProbesetDim.class, new String[] {snpProbeIDCol}, sCrit, false);
        CGHReporterIDCriteria reporterIDCrit = new CGHReporterIDCriteria ();
        if (includeSNPs) {
            reporterIDCrit.setSnpProbeIDsSubQuery(snpProbeIDsQuery);
        }
        if (includeCGH) {
           // TODO: Post Nautilus i.e when we have CGH data
        }
        return reporterIDCrit;
    }

    public static String getType(SNPCriteria snpCrit) {
        if (snpCrit != null ) {
            Collection snpIDs = snpCrit.getIdentifiers();
             if (snpIDs != null && snpIDs.size() > 0) {
                 SNPIdentifierDE obj =  (SNPIdentifierDE) snpIDs.iterator().next();
                 return obj.getSNPType();
             }
        }
        return "";
    }
    private void populateIncludeCGHAndSNPFlags(AssayPlatformCriteria assayPlatformCrit)
    throws Exception {
        if (assayPlatformCrit != null && assayPlatformCrit.getAssayPlatformDE() != null) {
            AssayPlatformDE platform = assayPlatformCrit.getAssayPlatformDE();
            if (platform.getValueObject().equalsIgnoreCase(Constants.AFFY_100K_SNP_ARRAY)) {
                includeSNPs = true;
            }
            /* TODO: Next release
            if (platform.getValueObject().equalsIgnoreCase(Constants.ARRAY_CGH)) {
                includeCGH = true;
            }
           */
            else throw new Exception("This Platform not currently not supported: " );
         }
        else throw new Exception("Assay Platform can not be null");
    }
    private void handleSNPCriteria(ComparativeGenomicQuery cghQuery,PersistenceBroker pb) throws Exception{
        if (cghQuery.getGeneIDCriteria() != null) {
            CGHReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForCGHQuery(cghQuery.getGeneIDCriteria(), includeSNPs, includeCGH, pb);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }
        if (cghQuery.getRegionCriteria() != null) {
            CGHReporterIDCriteria  regionCrit = ChrRegionCriteriaHandler.buildCGHRegionCriteria(cghQuery.getRegionCriteria(), includeSNPs, includeCGH, pb);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }

        if (cghQuery.getSNPCriteria() != null) {
            CGHReporterIDCriteria  snpCrit = buildSNPCriteria(cghQuery.getSNPCriteria(), pb);
            assert(snpCrit != null);
            SelectHandler handler = new SelectHandler.SNPSelectHandler(snpCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }
    }

	public Integer getCount(Query query) throws Exception {
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) query;

        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        populateIncludeCGHAndSNPFlags(cghQuery.getAssayPlatformCriteria());

        handleSNPCriteria( cghQuery, pb);

        if(cghQuery.getCloneOrProbeIDCriteria() != null) {
            throw new Exception (" Only BACClone will be implemented post Nautilus ");
        }

        pb.close();
        ThreadController.sleepOnEvents(eventList);

        return new CGHFactHandler.SingleCGHFactHandler().getSampleQueryCount(allSNPProbesetIDs, cghQuery);
	}

}
