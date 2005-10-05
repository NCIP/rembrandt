package gov.nih.nci.rembrandt.queryservice;

import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;

/**
 * @author BhattarR
 */
class QueryFactory {
    public static Query newQuery(QueryType queryType) {
          if (queryType instanceof QueryType.GeneExprQueryType) {
              return new GeneExpressionQuery();
          }
          else if (queryType instanceof QueryType.CGHQueryType) {              
              return new ComparativeGenomicQuery();
          }
		  else if (queryType instanceof QueryType.ClinicalQueryType) {              
              return new ClinicalDataQuery();
          }
        return null;
    }


}
