package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.rembrandt.dbbean.ArraySNPSegmentFact;
import gov.nih.nci.rembrandt.dbbean.CytobandPosition;
import gov.nih.nci.rembrandt.dbbean.GeneClone;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.dbbean.SnpProbesetDim;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CGHReporterIDCriteria;

import java.math.BigDecimal;
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

final public class ChrRegionCriteriaHandler {
    private final static String CHR_NUMBER_MISSING = "Chromosome Can not be null";
    abstract private static class RegionHandler {
        abstract StartEndPosition  buildStartEndPosition(RegionCriteria regionCrit, PersistenceBroker pb)
        throws Exception;
    }
    final private static class CytobandHandler extends RegionHandler {
        StartEndPosition  buildStartEndPosition(RegionCriteria regionCrit, PersistenceBroker pb) throws Exception {
            StartEndPosition posObj = getStartEndPostions(pb, regionCrit, regionCrit.getChromNumber());
            assert(posObj != null);
            return posObj;
        }
    }
    final private static class PositionHandler extends RegionHandler {
        StartEndPosition  buildStartEndPosition(RegionCriteria regionCrit, PersistenceBroker pb) throws Exception {
            StartEndPosition posObj = new StartEndPosition(regionCrit.getStart(), regionCrit.getEnd(), regionCrit.getChromNumber());
            assert(posObj != null);
            return posObj;
        }
    }

    public static GEReporterIDCriteria buildGERegionCriteria( RegionCriteria regionCrit, boolean includeClones, boolean includeProbes) throws Exception {
        PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        pb.clearCache();
        GEReporterIDCriteria gEReporterIDCriteria = null;
        assert (regionCrit != null);
        StartEndPosition posObj = getPositionObject(regionCrit, pb);
        gEReporterIDCriteria = buildGECloneIDProbeIDCrit(posObj, includeProbes, pb, includeClones);
        pb.close();
        return gEReporterIDCriteria;
    }
    public static CGHReporterIDCriteria  buildCGHRegionCriteria( RegionCriteria regionCrit, boolean includeSNPs, boolean includeCGH, PersistenceBroker pb) throws Exception {
        assert (regionCrit != null);
        StartEndPosition posObj = getPositionObject(regionCrit, pb);
        return buildCGHCloneIDProbeIDCrit(posObj, includeSNPs, pb, includeCGH);
    }

    public static StartEndPosition getPositionObject(RegionCriteria regionCrit, PersistenceBroker pb) throws Exception {
        if (regionCrit.getChromNumber() == null) throw new Exception(CHR_NUMBER_MISSING );

        RegionHandler h = null;
        if (regionCrit.getCytoband() != null)
            h = new CytobandHandler();
        else if (regionCrit.getStart() != null && regionCrit.getEnd() != null)
            h = new PositionHandler();
        else
            return new StartEndPosition(null, null, regionCrit.getChromNumber());
        //throw new Exception("Either cytoband or Start/End Position is required");
        StartEndPosition posObj = h.buildStartEndPosition(regionCrit, pb);
        return posObj;
    }

    private static GEReporterIDCriteria  buildGECloneIDProbeIDCrit(StartEndPosition posObj, boolean includeProbes, PersistenceBroker pb, boolean includeClones) throws Exception {
            GEReporterIDCriteria cloneIDProbeIDCrit = new GEReporterIDCriteria();
            if (posObj != null) {
                if (includeProbes) {
                    ReportQueryByCriteria probeIDSubQuery = buildProbeIDCrit(pb, posObj);
                    cloneIDProbeIDCrit.setProbeIDsSubQuery(probeIDSubQuery);
                }
                if (includeClones) {
                   ReportQueryByCriteria colneIDSubQuery = buildCloneIDCrit(pb, posObj);
                   cloneIDProbeIDCrit.setCloneIDsSubQuery(colneIDSubQuery);
                }
            }
            return cloneIDProbeIDCrit;
    }
     private static CGHReporterIDCriteria  buildCGHCloneIDProbeIDCrit(StartEndPosition posObj, boolean includeSNPs, PersistenceBroker pb, boolean includeCGH) throws Exception {
            CGHReporterIDCriteria reporterIDCrit = new CGHReporterIDCriteria ();
            if (posObj != null) {
                if (includeSNPs) {
                    ReportQueryByCriteria snpProbeIDSubQuery = buildSNPProbeIDCrit(pb, posObj);
                    reporterIDCrit.setSnpProbeIDsSubQuery(snpProbeIDSubQuery);
                }
                if (includeCGH) {
                    // TODO: Next release
                   //ReportQueryByCriteria cghProbeIDSubQuery = buildCloneIDCrit(pb, posObj);
                   //cloneIDProbeIDCrit.setCloneIDsSubQuery(colneIDSubQuery);
                }
            }
            return reporterIDCrit;
    }
    private static ReportQueryByCriteria buildSNPProbeIDCrit(PersistenceBroker pb, StartEndPosition posObj) throws Exception {
        String snpSegmentIDCol = QueryHandler.getColumnNameForBean(pb, ArraySNPSegmentFact.class.getName(), ArraySNPSegmentFact.SNP_SEGMENT_ID);
        String startPositionCol = QueryHandler.getColumnNameForBean(pb, ArraySNPSegmentFact.class.getName(), ArraySNPSegmentFact.CHR_SEGMENT_START);
        String endPositionCol = QueryHandler.getColumnNameForBean(pb, ArraySNPSegmentFact.class.getName(), ArraySNPSegmentFact.CHR_SEGMENT_END);

        String chrCol = QueryHandler.getColumnNameForBean(pb, ArraySNPSegmentFact.class.getName(), ArraySNPSegmentFact.CHROMOSOME);

        Criteria c = new Criteria();
        c.addColumnEqualTo(chrCol, posObj.getChrNumber().getValueObject());
        c.addGreaterOrEqualThan(startPositionCol, new Long(posObj.getStartPosition().getValueObject().longValue()));
        c.addLessOrEqualThan(endPositionCol, new Long(posObj.getEndPosition().getValueObject().longValue()));

        ReportQueryByCriteria snpProbeIDQuery = QueryFactory.newReportQuery(ArraySNPSegmentFact.class, new String[] {snpSegmentIDCol}, c, false );
        return snpProbeIDQuery;
    }

    private static ReportQueryByCriteria buildProbeIDCrit(PersistenceBroker pb, StartEndPosition posObj) throws Exception {
        String probeIDColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
        String deMappingAttrNameForStartPos = QueryHandler.getColumnName(pb, BasePairPositionDE.StartPosition.class.getName(), ProbesetDim.class.getName());
        String deMappingAttrNameForEndPos = QueryHandler.getColumnName(pb, BasePairPositionDE.EndPosition.class.getName(), ProbesetDim.class.getName());
        String deMappingAttrNameForChrNum = QueryHandler.getColumnName(pb, ChromosomeNumberDE.class.getName(), ProbesetDim.class.getName());
        Criteria c = new Criteria();
        c.addColumnEqualTo(deMappingAttrNameForChrNum, posObj.chrNumber.getValueObject());
        if (posObj.startPosition != null && posObj.endPosition != null) {
                c.addGreaterOrEqualThan(deMappingAttrNameForStartPos, new Long(posObj.startPosition.getValueObject().longValue()));
                c.addLessOrEqualThan(deMappingAttrNameForEndPos, new Long(posObj.endPosition.getValueObject().longValue()));
        }
        ReportQueryByCriteria probeIDSubQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {probeIDColumn}, c, false );
        return probeIDSubQuery;
    }
    private static ReportQueryByCriteria buildCloneIDCrit(PersistenceBroker pb, StartEndPosition posObj) throws Exception {
        String cloneIDColumn = QueryHandler.getColumnNameForBean(pb, GeneClone.class.getName(), GeneClone.CLONE_ID);
        String deMappingAttrNameForStartPos = QueryHandler.getColumnName(pb, BasePairPositionDE.StartPosition.class.getName(), GeneClone.class.getName());
        String deMappingAttrNameForEndPos = QueryHandler.getColumnName(pb, BasePairPositionDE.EndPosition.class.getName(), GeneClone.class.getName());
        String deMappingAttrNameForChrNum = QueryHandler.getColumnName(pb, ChromosomeNumberDE.class.getName(), GeneClone.class.getName());
        Criteria c = new Criteria();
        c.addColumnEqualTo(deMappingAttrNameForChrNum, posObj.chrNumber.getValueObject());

        if(posObj != null)  {
             if (posObj.startPosition != null && posObj.endPosition != null) {
                c.addGreaterOrEqualThan(deMappingAttrNameForStartPos, new Long(posObj.startPosition.getValueObject().longValue()));
                c.addLessOrEqualThan(deMappingAttrNameForEndPos, new Long(posObj.endPosition.getValueObject().longValue()));
             }
        }

        ReportQueryByCriteria coleIDSubQuery = QueryFactory.newReportQuery(GeneClone.class, new String[] {cloneIDColumn}, c, false );
        return coleIDSubQuery;
    }
    public static StartEndPosition getStartEndPostions(PersistenceBroker pb, RegionCriteria regionCrit, ChromosomeNumberDE chrNumber) throws Exception {
        String cytobandCol = QueryHandler.getColumnName(pb, CytobandDE.class.getName(), CytobandPosition.class.getName());
        String chrNumberCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CHROMOSOME);

        CytobandDE startCytoband = regionCrit.getStartCytoband();
        CytobandDE endCytoband = regionCrit.getEndCytoband();

        Criteria cytobandCrit = new Criteria();
        if (startCytoband != null && startCytoband.getValueObject() != null) {
            cytobandCrit.addColumnEqualTo(cytobandCol, startCytoband.getValueObject());
        }
        if (endCytoband != null && endCytoband .getValueObject() != null) {
            Criteria c = new Criteria();
            c.addColumnEqualTo(cytobandCol, endCytoband.getValueObject());
            cytobandCrit.addOrCriteria(c);
        }

        cytobandCrit.addColumnEqualTo(chrNumberCol, chrNumber.getValueObject());

        String cbStartCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CB_START);
        String cbEndCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CB_ENDPOS);

        ReportQueryByCriteria cytobandQuery = QueryFactory.newReportQuery(CytobandPosition.class,
                new String[] {cbStartCol, cbEndCol }, cytobandCrit, true);
        Iterator iter = pb.getReportQueryIteratorByQuery(cytobandQuery);

        BigDecimal cbStartPos =  null;
        BigDecimal cbEndPos = null;
        while (iter.hasNext()) {
            Object[] values = (Object[]) iter.next();
            cbStartPos = (cbStartPos == null) ? (BigDecimal)values[0]:
                          new BigDecimal(String.valueOf(Math.min(cbStartPos.longValue(), ((BigDecimal)values[0]).longValue())));
            cbEndPos = (cbEndPos == null) ? (BigDecimal)values[1]:
                           new BigDecimal(String.valueOf(Math.max(cbEndPos.longValue(), ((BigDecimal)values[1]).longValue())));
        }

        StartEndPosition posObj = null;
        BasePairPositionDE startPosition = new BasePairPositionDE.StartPosition(new Long(cbStartPos.intValue()));
        BasePairPositionDE endPosition = new BasePairPositionDE.EndPosition(new Long(cbEndPos.intValue()));
        posObj = new StartEndPosition(startPosition, endPosition,  chrNumber);

        return posObj;
    }

}
