/*
 * Created on Oct 12, 2004
 *
 */
package gov.nih.nci.nautilus.graph.kaplanMeier;

import org.jfree.data.XYDataPair;

/**
 * @author XiaoN
 * This class extends the JFree class XYDataPair and 
 * adds a third parameter for census. If census == true,
 * draw a "+" at that point.
 */
public class KMDrawingPoint extends XYDataPair {
	
    private XYDataPair xyDataPair;
	private boolean census; 
	
	public KMDrawingPoint(Number x, Number y){
		super(x,y);
        xyDataPair = new XYDataPair(x,y);
	}
	public KMDrawingPoint(Number x, Number y, boolean b){
        super(x,y);
		this.census=b; 
	}

    /**
	 * @return Returns the isChecked.
	 */
	public boolean isCensus() {
		return census;
	}
}
