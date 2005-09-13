/*
 * Created on Oct 12, 2004
 *
 */
package gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier;

import org.jfree.data.xy.XYDataItem;

/**
 * This class extends the JFreeChart class XYDataItem and 
 * adds a third parameter for census. If census == true,
 * draw a "+" at that point.
 */
public class KaplanMeierPlotPoint extends XYDataItem {
	
    private XYDataItem xyDataPair;
	private boolean checked = false; 
	
	public KaplanMeierPlotPoint(Number x, Number y){
		super(x,y);
        xyDataPair = new XYDataItem(x,y);
	}
	public KaplanMeierPlotPoint(Number x, Number y, boolean b){
        super(x,y);
        xyDataPair = new XYDataItem(x,y);
		this.checked = b; 
	}

    /**
	 * @return Returns the isCensus
	 */
	public boolean isChecked() {
		return checked;
	}
	public String toString(){
	    return ("( "+xyDataPair.getX()+", "+xyDataPair.getY()+")  Census:"+checked);
	}
}
