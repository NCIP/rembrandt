package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.rembrandt.dbbean.GenePathway;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
 */
public class GenePathwayHandler {
    static GEReporterIDCriteria  buildPathwayCriteria( PathwayCriteria pathwayCrit, boolean includeClones, boolean includeProbes) throws Exception {
            PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
            pb.clearCache();
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
            pb.close();
            // executeQuery ProbesetID and CloneIDs for GeneSymbols
            return gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(geneIDCrit, includeClones, includeProbes);
    }
}
