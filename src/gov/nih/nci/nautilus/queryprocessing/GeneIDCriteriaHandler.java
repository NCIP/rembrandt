package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.CloneDim;
import gov.nih.nci.nautilus.data.GeneClone;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 10, 2004
 * Time: 4:22:02 PM
 * To change this template use Options | File Templates.
 */
public class GeneIDCriteriaHandler {
    public static ReporterIDCriteria buildGeneIDCriteria( GeneIDCriteria  geneIDCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb ) throws Exception {
        Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        Class deClass = getGeneIDClassName(geneIDCrit);
        ArrayList geneIDs = new ArrayList();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();)
            geneIDs.add(((GeneIdentifierDE) iterator.next()).getValueObject());

        return buildAllIDsQueryBasedOnGeneIDCrit(deClass,geneIDs, includeClones, includeProbes, pb);
     }

    private static Criteria prepareGeneSymbolSubQueryCrit(PersistenceBroker pb, String deClassName, Collection values) throws Exception {
        Criteria geneIDOJBCrit = new Criteria();
        String colName = QueryHandler.getColumnName(pb, deClassName);
        geneIDOJBCrit.addIn(colName, values);
        String geneSymbolColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.GENE_SYMBOL);
        ReportQueryByCriteria geneSymbolQuery = QueryFactory.newReportQuery(ProbesetDim.class,
                    new String[] {geneSymbolColumn}, geneIDOJBCrit, true);
        Criteria geneSymbolSubQueryCrit = new Criteria();
        geneSymbolSubQueryCrit.addIn(ProbesetDim.GENE_SYMBOL, geneSymbolQuery);
        return geneSymbolSubQueryCrit;
    }

    private static Criteria prepareGeneQueryCrit(PersistenceBroker pb, String deClassName, Collection values) throws Exception {
        Criteria geneIDOJBCrit = new Criteria();
        String colName = QueryHandler.getColumnName(pb, deClassName);
        geneIDOJBCrit.addIn(colName, values);
        String geneSymbolColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.GENE_SYMBOL);
        ReportQueryByCriteria geneSymbolQuery = QueryFactory.newReportQuery(ProbesetDim.class,
                    new String[] {geneSymbolColumn}, geneIDOJBCrit, true);
        Criteria geneSymbolSubQueryCrit = new Criteria();
        geneSymbolSubQueryCrit.addIn(ProbesetDim.GENE_SYMBOL, geneSymbolQuery);
        return geneSymbolSubQueryCrit;
    }
    static ReporterIDCriteria buildAllIDsQueryBasedOnGeneIDCrit(Class idDEClass, Collection geneIDs, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception {
        ReporterIDCriteria cloneIDProbeIDCrit = new ReporterIDCriteria();

        if ( includeProbes) {
            String probeIDColumn = QueryHandler.getColumnNameForBean(pb, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
            String deMappingAttrName = QueryHandler.getAttrNameForTheDE(idDEClass.getName(), ProbesetDim.class.getName());
            Criteria c = new Criteria();
            c.addIn(deMappingAttrName, geneIDs);
            ReportQueryByCriteria probeIDSubQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {probeIDColumn}, c, true );
            cloneIDProbeIDCrit.setProbeIDsSubQuery(probeIDSubQuery);
        }
        if (includeClones) {
            String cloneIDColumn = QueryHandler.getColumnNameForBean(pb, GeneClone.class.getName(), GeneClone.CLONE_ID);
            String deMappingAttrName = QueryHandler.getAttrNameForTheDE(idDEClass.getName(), GeneClone.class.getName());
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

}
