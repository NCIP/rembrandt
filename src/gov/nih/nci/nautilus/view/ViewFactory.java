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
    public static View newView(ViewType viewType) {
        if (viewType instanceof ViewType.GeneCentricView) {
            return new GeneCentricView();
        }
        if (viewType instanceof ViewType.SampleCentricView) {
            return new SampleCentricView();
        }
        return null;
    }
}
