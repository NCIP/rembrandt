/*
 * Created on Nov 17, 2004
 */
package gov.nih.nci.nautilus.graph.kaplanMeier;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.XYDataPair;
import org.jfree.data.XYSeries;

/**
 * @author bauerd
 * This class was written to store Kaplan-Meier data points 
 * as they are input. It extends the XYSeries from the JFree
 * package.  This resolves an issue with the superclass sorting
 * the imputs by X and Y, messing up the step graph data lines.
 *
 */
public class KMDataSeries extends XYSeries{
    private List data;
    
    public KMDataSeries(String string, boolean arg) {
        super(string, arg);
        data = new ArrayList();
    }

    public void add(double x, double y, int index) {
        data.add(index, new XYDataPair(x,y));
    }
    public void add(double x, double y) {
        data.add(new XYDataPair(x,y));
    }
  
    public void add(XYDataPair xyDataPair) {
        data.add(xyDataPair);
    }
    
    public void add(XYDataPair xyDataPair, int index) {
    	data.add(index, xyDataPair);
    }

    public XYDataPair getDataPair(int index) {
   	    return (XYDataPair)data.get(index);
    }
    
    public Number getXValue(int i) {
        return  ((XYDataPair)(data.get(i))).getX();
    }
    
    public Number getYValue(int i) {
        return  ((XYDataPair)(data.get(i))).getY();
    }
    
    public int getItemCount() {
    	return data.size();
    }
}
