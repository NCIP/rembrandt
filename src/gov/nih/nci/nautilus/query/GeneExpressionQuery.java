package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.GeneExprQueryHandler;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:46:14 PM
 * To change this template use Options | File Templates.
 */
public class GeneExpressionQuery extends Query {

    private GeneIDCriteria geneIDCrit;
    private RegionCriteria regionCrit;
    private FoldChangeCriteria foldChgCrit;
    private QueryHandler HANDLER;

    public QueryHandler getQueryHandler() throws Exception  {
        return (HANDLER == null) ? new GeneExprQueryHandler() : HANDLER;
    }

    public GeneExpressionQuery() {
        super();
    }

    public GeneIDCriteria getGeneIDCrit() {
        return geneIDCrit;
    }

    public void setGeneIDCrit(GeneIDCriteria geneIDCrit) {
        this.geneIDCrit = geneIDCrit;
    }

    public RegionCriteria getRegionCrit() {
        return regionCrit;
    }

    public void setRegionCrit(RegionCriteria regionCrit) {
        this.regionCrit = regionCrit;
    }

    public FoldChangeCriteria getFoldChgCrit() {
        return foldChgCrit;
    }

    public void setFoldChgCrit(FoldChangeCriteria foldChgCrit) {
        this.foldChgCrit = foldChgCrit;
    }

    class Handler {

    }
}
