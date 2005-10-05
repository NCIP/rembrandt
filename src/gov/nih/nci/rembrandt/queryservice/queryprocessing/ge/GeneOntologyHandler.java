package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.rembrandt.dbbean.GeneOntology;
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
public class GeneOntologyHandler {
    static GEReporterIDCriteria  buildGeneOntologyIDCriteria( GeneOntologyCriteria ontologyCrit, boolean includeClones, boolean includeProbes) throws Exception {
            PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
            pb.clearCache();
            Collection goIDs = ontologyCrit.getGOIdentifiers();

            ArrayList goIDValues = new ArrayList();
            if (goIDs != null && goIDs.size() > 0) {
                 for (Iterator iterator = goIDs.iterator(); iterator.hasNext();)
                     goIDValues.add(((GeneOntologyDE) iterator.next()).getValueObject());
            }
            String geneSymbolColumn = QueryHandler.getColumnNameForBean(pb, GeneOntology.class.getName(), GeneOntology.GENE_SYMBOL);
            Criteria goIDOJBCrit = new Criteria();
            goIDOJBCrit.addIn(GeneOntology.GO_ID, goIDValues);
            ReportQueryByCriteria geneSymbolSubQueryCrit = QueryFactory.newReportQuery(GeneOntology.class,
                   new String[] {geneSymbolColumn}, goIDOJBCrit, true);

            Collection geneSymbols = new ArrayList();
            Iterator iter = pb.getReportQueryIteratorByQuery(geneSymbolSubQueryCrit);
            while (iter.hasNext()) {
                Object[] objs = (Object[]) iter.next();
                String symbol = (String) objs[0];
                geneSymbols.add(new GeneIdentifierDE.GeneSymbol(symbol.toUpperCase()));
            }
            if (geneSymbols.size() > 0) {
                GeneIDCriteria geneIDCrit = new GeneIDCriteria();
                geneIDCrit.setGeneIdentifiers(geneSymbols);

                // executeQuery ProbesetID and CloneIDs for GeneSymbols
                return gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(geneIDCrit, includeClones, includeProbes);
            }
            // means no data
            pb.close();
            return new GEReporterIDCriteria();
    }
}
