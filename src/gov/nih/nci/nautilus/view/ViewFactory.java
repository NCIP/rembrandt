package gov.nih.nci.nautilus.view;

import gov.nih.nci.nautilus.query.Query;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 7:03:12 PM
 * To change this template use Options | File Templates.
 */
public class ViewFactory {
    public static ViewType newView(ViewType viewType) {
        if (viewType instanceof ViewType.GeneSingleSampleView) {
            return ViewType.GENE_SINGLE_SAMPLE_VIEW;
        }
        else if (viewType instanceof ViewType.GeneGroupSampleView) {
            return ViewType.GENE_GROUP_SAMPLE_VIEW ;
        }
        else if (viewType instanceof ViewType.ClinicalView) {
            return ViewType.CLINICAL_VIEW;
        }
        return null;
    }
}
