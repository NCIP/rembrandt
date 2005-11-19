package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.rembrandt.dbbean.*;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.UnifiedGeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.AllGenesCritValidator;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CommonFactHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.DBEvent;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.util.ThreadPool;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * @author BhattarR
 */
abstract public class UnifiedGEFactHandler {
    private static Logger logger = Logger.getLogger(UnifiedGEFactHandler.class);
    Map geneExprObjects = Collections.synchronizedMap(new HashMap());
    private final static int VALUES_PER_THREAD = 50;
    abstract void addToResults(Collection results);

    abstract ResultSet[] executeFactQuery(UnifiedGeneExpressionQuery query) throws Exception;

    protected void executeQuery(final Class targetFactClass, String geneSymbolAttr, UnifiedGeneExpressionQuery geQuery ) throws Exception {
                GeneIDCriteria geneIDCrit = geQuery.getGeneIDCrit();
                final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
                pb.clearCache();

                // 1. add Gene ID (symbol) criteria
                ArrayList arrayIDs = GeneIDCriteriaHandler.getGeneIDValues(geneIDCrit);
                Criteria c = new Criteria();
                c.addIn(geneSymbolAttr, arrayIDs);

                // 2. add Institution criteria
                CommonFactHandler.addAccessCriteria(geQuery, targetFactClass, c);

                Query factQuery = QueryFactory.newQuery(targetFactClass,c, false);
                assert(factQuery  != null);
                Collection exprObjects =  pb.getCollectionByQuery(factQuery);
                addToResults(exprObjects);
                pb.close();
    }

    public final static class SingleHandler extends UnifiedGEFactHandler {

        public ResultSet[] executeFactQuery(UnifiedGeneExpressionQuery query) throws Exception {

            // 1. first execute against fact table
            executeQuery(DiffExpressionGeneSFact.class, DiffExpressionGeneSFact.GENE_SYMBOL, query);

            // 2. format it to results (by now geneExprObjects must have been populated with facts
            UnifiedGeneExpr.UnifiedGeneExprSingle[] results = new UnifiedGeneExpr.UnifiedGeneExprSingle[geneExprObjects.size()];
            Collection facts = geneExprObjects.values();
            int count = 0;
            for (Iterator iterator = facts.iterator(); iterator.hasNext();) {
                UnifiedGeneExpr.UnifiedGeneExprSingle obj = (UnifiedGeneExpr.UnifiedGeneExprSingle) iterator.next();
                results[count++] = obj;
            }

            return results;
        }

        void addToResults(Collection exprObjects) {
            for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                DiffExpressionGeneSFact exprObj = (DiffExpressionGeneSFact) iterator.next();
                UnifiedGeneExpr.UnifiedGeneExprSingle singleExprObj = new UnifiedGeneExpr.UnifiedGeneExprSingle();
                copyTo(singleExprObj, exprObj);
                geneExprObjects.put(singleExprObj.getID(), singleExprObj);
                exprObj = null;
            }
        }
        public static void copyTo(UnifiedGeneExpr.UnifiedGeneExprSingle singleExprObj, DiffExpressionGeneSFact exprObj) {
            singleExprObj.setExpressionRatio(exprObj.getExpressionRatio());
            singleExprObj.setGeneSymbol(exprObj.getGeneSymbol());
            singleExprObj.setID(exprObj.getDegsId());
            singleExprObj.setNormalIntensity(exprObj.getNormalIntensity());
            singleExprObj.setSampleId(exprObj.getSampleId());
            singleExprObj.setSampleIntensity(exprObj.getSampleIntensity());
            singleExprObj.setUnifiedGeneID(exprObj.getUnifiedGene());
            singleExprObj.setDiseaseType(exprObj.getDiseaseType());
        }
    }
    public final static class GroupHandler extends UnifiedGEFactHandler {

        public ResultSet[] executeFactQuery(UnifiedGeneExpressionQuery query) throws Exception {
            // 1. first execute against fact table
            executeQuery(DiffExpressionGeneGFact.class, DiffExpressionGeneGFact.GENE_SYMBOL, query);

            // 2. format it to results (by now geneExprObjects must have been populated with facts
            UnifiedGeneExpr.UnifiedGeneExprGroup[] results = new UnifiedGeneExpr.UnifiedGeneExprGroup[geneExprObjects.size()];
            Collection facts = geneExprObjects.values();
            int count = 0;
            for (Iterator iterator = facts.iterator(); iterator.hasNext();) {
                UnifiedGeneExpr.UnifiedGeneExprGroup obj = (UnifiedGeneExpr.UnifiedGeneExprGroup) iterator.next();
                results[count++] = obj;
            }

            return results;
        }

        void addToResults(Collection exprObjects) {
            for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                DiffExpressionGeneGFact exprObj = (DiffExpressionGeneGFact) iterator.next();
                UnifiedGeneExpr.UnifiedGeneExprGroup singleExprObj = new UnifiedGeneExpr.UnifiedGeneExprGroup();
                copyTo(singleExprObj, exprObj);
                geneExprObjects.put(singleExprObj.getID(), singleExprObj);
                exprObj = null;
            }
        }
        public static void copyTo(UnifiedGeneExpr.UnifiedGeneExprGroup singleExprObj, DiffExpressionGeneGFact exprObj) {
            singleExprObj.setExpressionRatio(exprObj.getExpressionRatio());
            singleExprObj.setGeneSymbol(exprObj.getGeneSymbol());
            singleExprObj.setID(exprObj.getDeggId());
            singleExprObj.setNormalIntensity(exprObj.getNormalIntensity());
            singleExprObj.setSampleIntensity(exprObj.getSampleIntensity());
            singleExprObj.setUnifiedGeneID(exprObj.getUnifiedGene());
            singleExprObj.setDiseaseType(exprObj.getDiseaseType());
            singleExprObj.setRatioPval(exprObj.getRatioPval());
            singleExprObj.setStandardDeviation(exprObj.getRatioStd());
        }
    }
}

