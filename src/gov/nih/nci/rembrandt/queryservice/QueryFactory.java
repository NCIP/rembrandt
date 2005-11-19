package gov.nih.nci.rembrandt.queryservice;

import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.dto.query.*;

/**
 * @author BhattarR. sahnih
 * add Class comparision
 * 
 */
class QueryFactory {
    public static Query newQuery(QueryType queryType) {
          if (queryType == QueryType.GENE_EXPR_QUERY_TYPE) {
              return new GeneExpressionQuery();
          }

          else if (queryType == QueryType.UNIFIED_GENE_EXPR_QUERY_TYPE) {
            return new UnifiedGeneExpressionQuery();
          }

          else if (queryType == QueryType.CGH_QUERY_TYPE) {
              return new ComparativeGenomicQuery();
          }
          else if (queryType == QueryType.CLINICAL_DATA_QUERY_TYPE) {
              return new ClinicalDataQuery();
          }
          return null;
    }


}
