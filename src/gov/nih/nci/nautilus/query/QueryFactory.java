package gov.nih.nci.nautilus.query;

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
