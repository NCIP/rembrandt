package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.GeneOntologyCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.GeneOntologyDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.data.GeneOntology;
import gov.nih.nci.nautilus.queryprocessing.ge.*;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
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
public class GeneOntologyHandler {
    static GEReporterIDCriteria  buildGeneOntologyIDCriteria( GeneOntologyCriteria ontologyCrit, boolean includeClones, boolean includeProbes, PersistenceBroker pb) throws Exception {
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
                geneSymbols.add(new GeneIdentifierDE.GeneSymbol(symbol));
            }
            GeneIDCriteria geneIDCrit = new GeneIDCriteria();
            geneIDCrit.setGeneIdentifiers(geneSymbols);

            // executeQuery ProbesetID and CloneIDs for GeneSymbols
            return gov.nih.nci.nautilus.queryprocessing.ge.GeneIDCriteriaHandler.buildGeneIDCriteria(geneIDCrit, includeClones, includeProbes, pb);
    }
}
