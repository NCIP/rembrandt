package gov.nih.nci.rembrandt.queryservice.view;


/**
 * @author BhattarR
 */
public class ViewFactory {
    public static View newView(ViewType viewType) {
        if (viewType instanceof ViewType.GeneSingleSampleView) {
            return new GeneExprSampleView();
        }
        else if (viewType instanceof ViewType.GeneGroupSampleView) {
            return new GeneExprDiseaseView();
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
