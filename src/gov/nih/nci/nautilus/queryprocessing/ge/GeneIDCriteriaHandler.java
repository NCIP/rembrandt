package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.CloneDim;
import gov.nih.nci.nautilus.data.GeneClone;
import gov.nih.nci.nautilus.data.SnpAssociatedGene;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.cgh.CGHReporterIDCriteria;
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
                String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SnpAssociatedGene.class.getName(), SnpAssociatedGene.SNP_PROBESET_ID);
                String deMappingAttrName = QueryHandler.getAttrNameForTheDE(deClass.getName(), SnpAssociatedGene.class.getName());
                Criteria c = new Criteria();
                c.addIn(deMappingAttrName, geneIDs);
                ReportQueryByCriteria snpProbeIDSubQuery = QueryFactory.newReportQuery(SnpAssociatedGene.class, new String[] {snpProbeIDCol}, c, true );
                snpProbeIDCrit.setSnpProbeIDsSubQuery(snpProbeIDSubQuery);
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

}
