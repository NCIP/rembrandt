package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.criteria.GeneOntologyCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.PathwayCriteria;
import gov.nih.nci.nautilus.de.GeneOntologyDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.de.PathwayDE;
import gov.nih.nci.nautilus.data.GeneOntology;
import gov.nih.nci.nautilus.data.GenePathway;
import gov.nih.nci.nautilus.data.Pathway;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 23, 2004
 * Time: 4:58:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenePathwayHandler {
    static ReporterIDCriteria  buildPathwayCriteria( PathwayCriteria pathwayCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception {
            Collection pathwayNames = pathwayCrit.getPathwayNames();
            ArrayList pathwayNameValues = new ArrayList();
            if (pathwayNames  != null && pathwayNames .size() > 0) {
                 for (Iterator iterator = pathwayNames.iterator(); iterator.hasNext();)
                     pathwayNameValues.add(((PathwayDE) iterator.next()).getValueObject());
            }

            String geneSymbolColumn = QueryHandler.getColumnNameForBean(pb, GenePathway.class.getName(), GenePathway.GENE_SYMBOL);
            Criteria pthwayOJBCrit = new Criteria();
            pthwayOJBCrit.addIn(GenePathway.PATHWAY_NAME, pathwayNameValues);
            ReportQueryByCriteria geneSymbolSubQueryCrit = QueryFactory.newReportQuery(GenePathway.class,
                   new String[] {geneSymbolColumn}, pthwayOJBCrit, true);

            Collection geneSymbols = new ArrayList();
            Iterator iter = pb.getReportQueryIteratorByQuery(geneSymbolSubQueryCrit);
            while (iter.hasNext()) {
                Object[] objs = (Object[]) iter.next();
                String symbol = (String) objs[0];
                geneSymbols.add(new GeneIdentifierDE.GeneSymbol(symbol));
            }
            GeneIDCriteria geneIDCrit = new GeneIDCriteria();
            geneIDCrit.setGeneIdentifiers(geneSymbols);

            // build ProbesetID and CloneIDs for GeneSymbols
            return GeneIDCriteriaHandler.buildGeneIDCriteria(geneIDCrit, includeClones, includeProbes, pb);
    }
}
