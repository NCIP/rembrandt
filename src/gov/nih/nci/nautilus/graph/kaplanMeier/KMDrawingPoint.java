/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package gov.nih.nci.nautilus.graph.kaplanMeier;

/**
 * @author XiaoN
 *
 * Each of the KMDrawingPoint object has x, y co-ordinates, and a boolean value
 * (isChecked). Simply link each pair of consecutive points with a straight
 * line. If isChecked == true, draw a "+" at that point.
 * 
 */
public class KMDrawingPoint {
	private float x; //time
	private float y; //survival
	private boolean checked; //draw cross at the point
	
	public KMDrawingPoint(float x, float y){
		this.x=x; 
		this.y=y; 
	}
	public KMDrawingPoint(float x, float y, boolean checked){
		this.x=x; 
		this.y=y; 
		this.checked=checked; 
	}
	/**
	 * @return Returns the isChecked.
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @return Returns the x.
	 */
	public float getX() {
		return x;
	}
	/**
	 * @return Returns the y.
	 */
	public float getY() {
		return y;
	}
}
