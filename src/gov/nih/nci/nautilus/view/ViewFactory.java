package gov.nih.nci.nautilus.view;


/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 7:03:12 PM
 * To change this template use Options | File Templates.
 */
public class ViewFactory {
    public static View newView(ViewType viewType) {
        if (viewType instanceof ViewType.GeneSingleSampleView) {
            return new GeneExprSampleView();
        }
        else if (viewType instanceof ViewType.GeneGroupSampleView) {
            return new GeneSingleDiseaseView();
        }
        else if (viewType instanceof ViewType.ClinicalView) {
            return new ClinicalSampleView();
        }
        else if (viewType instanceof ViewType.CopyNumberSampleView) {
            return new CopyNumberSampleView();
        }
        return null;
    }
}
