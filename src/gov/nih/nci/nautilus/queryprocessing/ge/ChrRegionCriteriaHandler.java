package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.CloneDim;
import gov.nih.nci.nautilus.data.CytobandPosition;
import gov.nih.nci.nautilus.data.GeneClone;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.cgh.CGHReporterIDCriteria;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

import java.util.Iterator;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 6, 2004
 * Time: 5:10:55 PM
 * To change this template use Options | File Templates.
 */
final public class ChrRegionCriteriaHandler {
    private final static String CHR_NUMBER_MISSING = "Chromosome Can not be null";
    abstract private static class RegionHandler {
        abstract StartEndPosition  buildStartEndPosition(RegionCriteria regionCrit, PersistenceBroker pb)
        throws Exception;
    }
    final private static class CytobandHandler extends RegionHandler {
        StartEndPosition  buildStartEndPosition(RegionCriteria regionCrit, PersistenceBroker pb) throws Exception {
            StartEndPosition posObj = getStartEndPostions(pb, regionCrit.getCytoband(), regionCrit.getChromNumber());
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
    final static public class StartEndPosition {
        BasePairPositionDE startPosition;
        BasePairPositionDE endPosition;
        ChromosomeNumberDE chrNumber;

        public BasePairPositionDE getStartPosition() {
            return startPosition;
        }
        public BasePairPositionDE getEndPosition() {
            return endPosition;
        }
        public ChromosomeNumberDE getChrNumber() {
            return chrNumber;
        }

        public StartEndPosition(BasePairPositionDE startPosition, BasePairPositionDE endPosition, ChromosomeNumberDE chrNumber) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.chrNumber = chrNumber;
        }
    }

    static GEReporterIDCriteria buildGERegionCriteria( RegionCriteria regionCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception {
        assert (regionCrit != null);
        StartEndPosition posObj = getPositionObject(regionCrit, pb);

        return buildGECloneIDProbeIDCrit(posObj, includeProbes, pb, includeClones);
    }
    static GEReporterIDCriteria buildCGHRegionCriteria( RegionCriteria regionCrit, boolean includeSNPs, boolean includeCGH, PersistenceBroker pb) throws Exception {
        assert (regionCrit != null);
        StartEndPosition posObj = getPositionObject(regionCrit, pb);

        //return buildCGHCloneIDProbeIDCrit(posObj, includeSNPs, pb, includeCGH);
       return null;
    }

    public static StartEndPosition getPositionObject(RegionCriteria regionCrit, PersistenceBroker pb) throws Exception {
        if (regionCrit.getChromNumber() == null) throw new Exception(CHR_NUMBER_MISSING );

        RegionHandler h = null;
        if (regionCrit.getCytoband() != null)
            h = new CytobandHandler();
        else if (regionCrit.getStart() != null && regionCrit.getEnd() != null)
            h = new PositionHandler();

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
                    ReportQueryByCriteria probeIDSubQuery = buildProbeIDCrit(pb, posObj);
                    //cloneIDProbeIDCrit.setProbeIDsSubQuery(probeIDSubQuery);
                }
                if (includeCGH) {
                   ReportQueryByCriteria colneIDSubQuery = buildCloneIDCrit(pb, posObj);
                   //cloneIDProbeIDCrit.setCloneIDsSubQuery(colneIDSubQuery);
                }
            }
            return reporterIDCrit;
    }
    private static ReportQueryByCriteria buildProbeIDCrit(PersistenceBroker pb, StartEndPosition posObj) throws Exception {
        String probeIDColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
        String deMappingAttrNameForStartPos = QueryHandler.getColumnName(pb, BasePairPositionDE.StartPosition.class.getName(), ProbesetDim.class.getName());
        String deMappingAttrNameForEndPos = QueryHandler.getColumnName(pb, BasePairPositionDE.EndPosition.class.getName(), ProbesetDim.class.getName());
        String deMappingAttrNameForChrNum = QueryHandler.getColumnName(pb, ChromosomeNumberDE.class.getName(), ProbesetDim.class.getName());
        Criteria c = new Criteria();
        c.addColumnEqualTo(deMappingAttrNameForChrNum, posObj.chrNumber.getValueObject());
        c.addGreaterOrEqualThan(deMappingAttrNameForStartPos, new Long(posObj.startPosition.getValueObject().longValue()));
        c.addLessOrEqualThan(deMappingAttrNameForEndPos, new Long(posObj.endPosition.getValueObject().longValue()));
        ReportQueryByCriteria probeIDSubQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {probeIDColumn}, c, true );
        return probeIDSubQuery;
    }
    private static ReportQueryByCriteria buildCloneIDCrit(PersistenceBroker pb, StartEndPosition posObj) throws Exception {
        String cloneIDColumn = QueryHandler.getColumnNameForBean(pb, GeneClone.class.getName(), GeneClone.CLONE_ID);
        String deMappingAttrNameForStartPos = QueryHandler.getColumnName(pb, BasePairPositionDE.StartPosition.class.getName(), GeneClone.class.getName());
        String deMappingAttrNameForEndPos = QueryHandler.getColumnName(pb, BasePairPositionDE.EndPosition.class.getName(), GeneClone.class.getName());
        String deMappingAttrNameForChrNum = QueryHandler.getColumnName(pb, ChromosomeNumberDE.class.getName(), GeneClone.class.getName());
        Criteria c = new Criteria();
        c.addColumnEqualTo(deMappingAttrNameForChrNum, posObj.chrNumber.getValueObject());
        c.addGreaterOrEqualThan(deMappingAttrNameForStartPos, new Long(posObj.startPosition.getValueObject().longValue()));
        c.addLessOrEqualThan(deMappingAttrNameForEndPos, new Long(posObj.endPosition.getValueObject().longValue()));
        ReportQueryByCriteria coleIDSubQuery = QueryFactory.newReportQuery(GeneClone.class, new String[] {cloneIDColumn}, c, true );
        return coleIDSubQuery;
    }
    private static StartEndPosition getStartEndPostions(PersistenceBroker pb, CytobandDE cytoband, ChromosomeNumberDE chrNumber) throws Exception {
        String cytobandCol = QueryHandler.getColumnName(pb, CytobandDE.class.getName(), CytobandPosition.class.getName());
        String chrNumberCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CHROMOSOME);
        Criteria cytobandCrit = new Criteria();
        cytobandCrit.addColumnEqualTo(cytobandCol, cytoband.getValueObject());
        cytobandCrit.addColumnEqualTo(chrNumberCol, chrNumber.getValueObject());

        String cbStartCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CB_START);
        String cbEndCol = QueryHandler.getColumnNameForBean(pb, CytobandPosition.class.getName(), CytobandPosition.CB_ENDPOS);

        ReportQueryByCriteria cytobandQuery = QueryFactory.newReportQuery(CytobandPosition.class,
                new String[] {cbStartCol, cbEndCol }, cytobandCrit, true);
        Iterator iter = pb.getReportQueryIteratorByQuery(cytobandQuery);
        StartEndPosition posObj = null;
        // there supposed to be only one row
        if (iter.hasNext()) {
            Object[] values = (Object[]) iter.next();
            BigDecimal cbStartPos = (BigDecimal)values[0];
            BigDecimal cbEndPos = (BigDecimal)values[1];
            BasePairPositionDE startPosition = new BasePairPositionDE.StartPosition(new Integer(cbStartPos.intValue()));
            BasePairPositionDE endPosition = new BasePairPositionDE.EndPosition(new Integer(cbEndPos.intValue()));
            posObj = new StartEndPosition(startPosition, endPosition,  chrNumber);
        }
        return posObj;
    }

}
