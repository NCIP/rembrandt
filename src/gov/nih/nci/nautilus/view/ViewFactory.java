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
        if (viewType instanceof ViewType.GeneCentricView) {
            return ViewType.GENE_VIEW_TYPE;
        }
        if (viewType instanceof ViewType.SampleCentricView) {
            return ViewType.SAMPLE_VIEW_TYPE;
        }
        return null;
    }
}
