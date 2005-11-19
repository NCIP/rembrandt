package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprDiseaseView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.UnifiedGeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author BhattarR
 */
final public class UnifiedGeneExprQueryHandler extends QueryHandler {
    UnifiedGEFactHandler factHandler = null;

    public ResultSet[] handle(gov.nih.nci.rembrandt.dto.query.Query query) throws Exception {
        UnifiedGeneExpressionQuery geQuery = (UnifiedGeneExpressionQuery) query;
                                                      
        if (query.getAssociatedView() instanceof GeneExprSampleView)
                factHandler = new UnifiedGEFactHandler.SingleHandler();
        else if (query.getAssociatedView() instanceof GeneExprDiseaseView)
                factHandler = new UnifiedGEFactHandler.GroupHandler();
        else throw new Exception("Illegal View.  This view is not supported in this Query:");

        return factHandler.executeFactQuery(geQuery);
    }

}
