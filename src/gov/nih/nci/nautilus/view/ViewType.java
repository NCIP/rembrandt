package gov.nih.nci.nautilus.view;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 7:29:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class ViewType {
    abstract ViewType getViewType();
    public final static GeneCentricView Gene_VIEW_TYPE = new GeneCentricView();
    public final static SampleCentricView SAMPLE_VIEW_TYPE = new SampleCentricView();
    public static class GeneCentricView extends ViewType {
       public ViewType getViewType() {
           return new GeneCentricView();
       }
    }
    public static class SampleCentricView extends ViewType {
       public ViewType getViewType() {
           return new SampleCentricView();
       }
    }
}
