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
import org.apache.ojb.broker.PersistenceBrokerFactory;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 10, 2004
 * Time: 4:22:02 PM
 * To change this template use Options | File Templates.
 */
public class GeneIDCriteriaHandler {

     static HashMap data = new HashMap();
        static {
            data.put(GeneIdentifierDE.GeneSymbol.class.getName(),
                            new TargetClass(GeneSnp.class, GeneSnp.SNP_PROBESET_ID));
            data.put(GeneIdentifierDE.LocusLink.class.getName(),
                            new TargetClass(LlSnp.class, LlSnp.SNP_PROBESET_ID));
            data.put(GeneIdentifierDE.GenBankAccessionNumber.class.getName(),
                            new TargetClass(GeneSnp.class, GeneSnp.SNP_PROBESET_ID));
        }
    /*public static GEReporterIDCriteria buildGeneIDCriteria( GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb ) throws Exception {

        Class deClass = getGeneIDClassName(geneIDCrit);
        ArrayList geneIDs = getGeneIDValues(geneIDCrit);

        return buildReporterIDCritForGEQuery(deClass,geneIDs, includeClones, includeProbes, pb);
     }*/

    private static ArrayList getGeneIDValues(GeneIDCriteria geneIDCrit) {
        Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        ArrayList geneIDs = new ArrayList();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();) {
            GeneIdentifierDE obj  = (GeneIdentifierDE) iterator.next();
            String value = null;
            if (obj.getGeneIDType().equals(GeneIdentifierDE.GENESYMBOL))
               value = obj.getValueObject().toUpperCase();
            else value = obj.getValueObject();
            geneIDs.add(value);//no need to call next again, just set the value
        }
        return geneIDs;
    }


    public static CGHReporterIDCriteria  buildReporterIDCritForCGHQuery(GeneIDCriteria  geneIDCrit, boolean includeSNPs, boolean includeCGH, PersistenceBroker pb) throws Exception {
            Class deClass = getGeneIDClassName(geneIDCrit);
            ArrayList geneIDs = getGeneIDValues(geneIDCrit);
            CGHReporterIDCriteria  snpProbeIDCrit = new CGHReporterIDCriteria ();

            if ( includeSNPs) {
                TargetClass t = (TargetClass) data.get(deClass.getName());
                snpProbeIDCrit = buildGeneSymbolCrit(deClass, geneIDs, t.target, t.attrToSearch);
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

    private static CGHReporterIDCriteria buildGeneSymbolCrit(Class deClass, ArrayList geneIDs, Class targetClass, String attrToRetrieve) throws Exception {
        CGHReporterIDCriteria snpProbeIDCrit = new CGHReporterIDCriteria();
        String geneIDCol = QueryHandler.getAttrNameForTheDE(deClass.getName(), targetClass.getName());
        Criteria snpProbesetIDCrit = new  Criteria();
        snpProbesetIDCrit.addIn(geneIDCol, geneIDs);
        //String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, GeneSnp.class.getName(), );
        ReportQueryByCriteria snpSubQuery = QueryFactory.newReportQuery(targetClass,snpProbesetIDCrit, true );
        snpSubQuery.setAttributes(new String[] {attrToRetrieve}) ;
        snpProbeIDCrit.setSnpProbeIDsSubQuery(snpSubQuery);
        return  snpProbeIDCrit;
    }
    /*public static GEReporterIDCriteria buildReporterIDCritForGEQuery(GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception
    {
        Collection geneIDs = geQuery.getGeneIDCrit().getGeneIdentifiers();
                   ArrayList arrayGeneIDs = new ArrayList(geneIDs);
                   for (int i = 0; i < arrayGeneIDs.size();) {
                       Collection critIDS = new ArrayList();
                       int begIndex = i;
                       i += 100 ;
                       int endIndex = (i < arrayGeneIDs.size()) ? endIndex = i : (arrayGeneIDs.size());
                       critIDS.addAll(arrayGeneIDs.subList(begIndex,  endIndex));
                       GeneIDCriteria gCrit = new GeneIDCriteria();
                       gCrit.setGeneIdentifiers(critIDS);
                       //final Criteria IDs = new Criteria();
                       //IDs.addIn(probeOrCloneIDAttr, values);
                       GEReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(gCrit, includeClones, includeProbes, _BROKER);
                       String threadID = "GEFactHandler.ThreadID:" + probeOrCloneIDAttr + ":" +i;
                   }
    }
    */
    public static GEReporterIDCriteria buildReporterIDCritForGEQuery(GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes) throws Exception {
        PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
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
