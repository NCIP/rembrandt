/*
 * Created on Nov 17, 2004
 */
package gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;



/**
 * @author bauerd
 * This class was written to store Kaplan-Meier data points 
 * as they are input. It extends the XYSeries from the JFreeChart
 * package.  This resolves an issue with the superclass sorting
 * the inputs by X and Y, messing up the step graph data lines.
 * 
 * This may not be necessary in later versions of JFreeChart
 *
 */
public class KaplanMeierPlotPointSeries extends XYSeries {
    
	private SeriesType myType;
    
    public KaplanMeierPlotPointSeries(String string, boolean arg) {
        super(string, arg);
    }
    public void setSeriesType(SeriesType type) {
    	myType = type;
    }
    
    public SeriesType getType() {
    	return myType;
    }
    public void add(float x, float y, int index) {
        data.add(index, new XYDataItem(x,y));
    }
    public void add(float x, float y) {
        data.add(new XYDataItem(x,y));
    }
  
    public void add(XYDataItem xyDataPair) {
        data.add(xyDataPair);
    }
    
    public void add(XYDataItem xyDataPair, int index) {
    	data.add(index, xyDataPair);
    }

    public XYDataItem getDataPair(int index) {
   	    return (XYDataItem)data.get(index);
    }
    
    public Number getXValue(int i) {
        return  ((XYDataItem)(data.get(i))).getX();
    }
    
    public Number getYValue(int i) {
        return  ((XYDataItem)(data.get(i))).getY();
    }
    
    public int getItemCount() {
    	return data.size();
    }
    
    public enum SeriesType{PROBABILITY,CENSOR}
}
