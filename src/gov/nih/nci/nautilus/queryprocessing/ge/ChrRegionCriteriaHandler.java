package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.data.CytobandPosition;
import gov.nih.nci.nautilus.data.GeneClone;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.SnpProbesetDim;
import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.cgh.CGHReporterIDCriteria;

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
        String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.SNP_PROBESET_ID);
        String positionCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.PHYSICAL_POSITION);
        String chrCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.CHROMOSOME);

        Criteria c = new Criteria();
        c.addColumnEqualTo(chrCol, posObj.getChrNumber().getValueObject());
        c.addGreaterOrEqualThan(positionCol, new Long(posObj.getStartPosition().getValueObject().longValue()));
        c.addLessOrEqualThan(positionCol, new Long(posObj.getEndPosition().getValueObject().longValue()));

        ReportQueryByCriteria snpProbeIDQuery = QueryFactory.newReportQuery(SnpProbesetDim.class, new String[] {snpProbeIDCol}, c, true );
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

        if(posObj != null)  {
             if (posObj.startPosition != null && posObj.endPosition != null) {
                c.addGreaterOrEqualThan(deMappingAttrNameForStartPos, new Long(posObj.startPosition.getValueObject().longValue()));
                c.addLessOrEqualThan(deMappingAttrNameForEndPos, new Long(posObj.endPosition.getValueObject().longValue()));
             }
        }

        ReportQueryByCriteria coleIDSubQuery = QueryFactory.newReportQuery(GeneClone.class, new String[] {cloneIDColumn}, c, true );
        return coleIDSubQuery;
    }
    private static StartEndPosition getStartEndPostions(PersistenceBroker pb, RegionCriteria regionCrit, ChromosomeNumberDE chrNumber) throws Exception {
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
