package gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier;

/**
 * @author XiaoN
 */
public class KaplanMeierSampleInfo {
	/**
	 * WE NEED TO ADD SAMPLE ID TO THIS CLASS
	 */
	String name; 
	int time; 
	int censor; //(0/1)
	double value; //Expr/CN value
	
	public KaplanMeierSampleInfo(String name,int t, int c, double v) {
		setTime(t); 
		setCensor(c); 
		setValue(v); 
		setName(name);
	}
	
	public KaplanMeierSampleInfo(int t, int c, double v) {
		setTime(t); 
		setCensor(c); 
		setValue(v); 
	}
	/**
	 * @return Returns the censor.
	 */
	public int getCensor() {
		return censor;
	}
	/**
	 * @param censor The censor to set.
	 */
	public void setCensor(int censor) {
		this.censor = censor;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the time.
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
