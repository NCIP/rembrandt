package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.data.*;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.cgh.CGHReporterIDCriteria;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.PersistenceBroker;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 10, 2004
 * Time: 4:22:02 PM
 * To change this template use Options | File Templates.
 */
public class GeneIDCriteriaHandler {
    private final static long SNP_KB_INTERVAL = 50;
    /*public static GEReporterIDCriteria buildGeneIDCriteria( GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb ) throws Exception {

        Class deClass = getGeneIDClassName(geneIDCrit);
        ArrayList geneIDs = getGeneIDValues(geneIDCrit);

        return buildReporterIDCritForGEQuery(deClass,geneIDs, includeClones, includeProbes, pb);
     }*/

    private static ArrayList getGeneIDValues(GeneIDCriteria geneIDCrit) {
        Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        ArrayList geneIDs = new ArrayList();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();)
            geneIDs.add(((GeneIdentifierDE) iterator.next()).getValueObject());
        return geneIDs;
    }


    public static CGHReporterIDCriteria  buildReporterIDCritForCGHQuery(GeneIDCriteria  geneIDCrit, boolean includeSNPs, boolean includeCGH, PersistenceBroker pb) throws Exception {
            Class deClass = getGeneIDClassName(geneIDCrit);
            ArrayList geneIDs = getGeneIDValues(geneIDCrit);
            CGHReporterIDCriteria  snpProbeIDCrit = new CGHReporterIDCriteria ();

            if ( includeSNPs) {
                String geneIDCol = QueryHandler.getAttrNameForTheDE(deClass.getName(), GeneLlAcc.class.getName());
                String startPos = QueryHandler.getColumnNameForBean(pb, GeneLlAcc.class.getName(), GeneLlAcc.START_POSITION);
                String chromsomeCol = QueryHandler.getColumnNameForBean(pb, GeneLlAcc.class.getName(), GeneLlAcc.CHROMOSOME);
                String endPos = QueryHandler.getColumnNameForBean(pb, GeneLlAcc.class.getName(), GeneLlAcc.END_POSITION);
                Criteria c = new Criteria();
                c.addIn(geneIDCol, geneIDs);
                ReportQueryByCriteria positionSubQuery = QueryFactory.newReportQuery(GeneLlAcc.class,
                        new String[] {chromsomeCol, startPos, endPos}, c, true );
                Iterator iter = pb.getReportQueryIteratorByQuery(positionSubQuery);
                Criteria snpProbesetIDCrit = new  Criteria();

                while (iter.hasNext()) {
                    Criteria crit = new Criteria();

                    Object[] objs = (Object[]) iter.next();
                    if (objs[0] != null && objs[1] != null && objs[2] != null) {
                        String chromosome = (String)objs[0];
                        Long cStart = new Long(((BigDecimal)objs[1]).longValue() - SNP_KB_INTERVAL);
                        Long cEnd = new Long(((BigDecimal)objs[2]).longValue() + SNP_KB_INTERVAL);

                        crit.addEqualTo(SnpProbesetDim.CHROMOSOME, chromosome);
                        crit.addGreaterOrEqualThan(SnpProbesetDim.PHYSICAL_POSITION, cStart);
                        crit.addLessOrEqualThan(SnpProbesetDim.PHYSICAL_POSITION, cEnd);

                        snpProbesetIDCrit.addOrCriteria(crit);
                    }
                }

                String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.SNP_PROBESET_ID);
                ReportQueryByCriteria snpSubQuery = QueryFactory.newReportQuery(SnpProbesetDim.class,
                        new String[] {snpProbeIDCol}, snpProbesetIDCrit, true );
                snpProbeIDCrit.setSnpProbeIDsSubQuery(snpSubQuery);
            }
            if (includeCGH)  {
                // TODO: Post Nautilus
            }
            return snpProbeIDCrit;
    }

    public static GEReporterIDCriteria buildReporterIDCritForGEQuery(GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception {
       Class deClass = getGeneIDClassName(geneIDCrit);
       ArrayList geneIDs = getGeneIDValues(geneIDCrit);
       GEReporterIDCriteria cloneIDProbeIDCrit = new GEReporterIDCriteria();

        if ( includeProbes) {
            String probeIDColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
            String deMappingAttrName = QueryHandler.getAttrNameForTheDE(deClass.getName(), ProbesetDim.class.getName());
            Criteria c = new Criteria();
            c.addIn(deMappingAttrName, geneIDs);
            ReportQueryByCriteria probeIDSubQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {probeIDColumn}, c, true );
            cloneIDProbeIDCrit.setProbeIDsSubQuery(probeIDSubQuery);
        }
        if (includeClones) {
            String cloneIDColumn = QueryHandler.getColumnNameForBean(pb, GeneClone.class.getName(), GeneClone.CLONE_ID);
            String deMappingAttrName = QueryHandler.getAttrNameForTheDE(deClass.getName(), GeneClone.class.getName());
            Criteria c = new Criteria();
            c.addIn(deMappingAttrName, geneIDs);
            ReportQueryByCriteria cloneIDSubQuery = QueryFactory.newReportQuery(GeneClone.class, new String[] {cloneIDColumn}, c, true );
            cloneIDProbeIDCrit.setCloneIDsSubQuery(cloneIDSubQuery);
        }
        return cloneIDProbeIDCrit;
    }


   private static Class getGeneIDClassName(GeneIDCriteria geneIDCrit ) {
            Collection geneIDs = geneIDCrit.getGeneIdentifiers();
            GeneIdentifierDE obj =  (GeneIdentifierDE) geneIDs.iterator().next();
            return obj.getClass();
    }
    private static class AccStartEndPosition {
        Long start;
        Long end;
        String chromosome;

        public AccStartEndPosition(Long start, Long end, String chromosome) {
            this.start = start;
            this.end = end;
            this.chromosome = chromosome;
        }
    }

}
