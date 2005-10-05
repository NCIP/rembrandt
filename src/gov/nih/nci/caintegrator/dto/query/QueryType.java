package gov.nih.nci.caintegrator.dto.query;


/**
 * @author BhattarR
 */
abstract public class QueryType {
   
    String queryType;
    
    public final static GeneExprQueryType GENE_EXPR_QUERY_TYPE = new GeneExprQueryType();
    public final static CGHQueryType CGH_QUERY_TYPE = new CGHQueryType ();
	public final static ClinicalQueryType CLINICAL_DATA_QUERY_TYPE = new ClinicalQueryType ();

    public QueryType(String queryType) {
        this.queryType = queryType;
    }
    
    public final static class GeneExprQueryType extends QueryType {
         GeneExprQueryType () {
         super("GeneQueryType");
         }
    }
    public final static class CGHQueryType extends QueryType {
        CGHQueryType () {
        super("CGHQueryType ");
        }
    }
	public final static class ClinicalQueryType extends QueryType {
         ClinicalQueryType () {
         super("ClinicalQueryType");
         }
    }
}
