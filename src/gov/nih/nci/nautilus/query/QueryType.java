package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.view.ViewType;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 7:29:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class QueryType {
    String queryType;
    public final static GeneExprQueryType GENE_EXPR_QUERY_TYPE = new GeneExprQueryType();
    public final static CGHQueryType CGH_QUERY_TYPE = new CGHQueryType ();

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
}
