/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package gov.nih.nci.nautilus.ui.graph.kaplanMeier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import weka.core.Statistics;
/**
 * @author XiaoN
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class KaplanMeier {
	
	private ArrayList allSamples; 
	private ArrayList upSamples; 
	private ArrayList intSamples; 
	private ArrayList downSamples; 
	private double upperThreshold; 
	private double lowerThreshold; 

	
	public KaplanMeier(KMSampleInfo[] samples, double upper, double lower) {
		allSamples = new ArrayList(); 
		upSamples = new ArrayList(); 
		downSamples = new ArrayList(); 
		intSamples = new ArrayList(); 
		allSamples.addAll(Arrays.asList(samples)); 
		Collections.sort(allSamples, new KMSampleComparator());
		this.upperThreshold = upper; 
		this.lowerThreshold = lower; 
		createSampleGroups(); 
	}
	
	public void createSampleGroups() {
		upSamples.clear(); 
		intSamples.clear(); 
		downSamples.clear(); 
		for (int i = 0; i<allSamples.size(); i++) {
			KMSampleInfo s = (KMSampleInfo) allSamples.get(i); 
			double value = s.getValue(); 
			if (value >= upperThreshold) {
				upSamples.add(s);
			} else if (value <= lowerThreshold) {
				downSamples.add(s); 
			} else {
				intSamples.add(s);
			}
		}
	}

	public void resetThresholds(double upper, double lower) {
		this.upperThreshold = upper; 
		this.lowerThreshold = 1/lower; 
		createSampleGroups(); 
	}	
	
	//create drawing data points for any set of sample
	public KMDrawingPoint[] getDrawingPoints(ArrayList samples) {		
		float surv = 1;
		float prevSurvTime = 0;
		float curSurvTime = 0;
		int d = 0;
		int r = samples.size();
		int left = samples.size();
		ArrayList points = new ArrayList();	
		System.out.println("Sorted input data: "); 
		for (int i = 0; i < samples.size(); i++) {
			curSurvTime = ((KMSampleInfo) samples.get(i)).getTime();
			System.out.println("Survival time: " + curSurvTime + "\tcensor:" + ((KMSampleInfo) samples.get(i)).getCensor()); 
			if (curSurvTime > prevSurvTime) {
				if (d>0) { 
					points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), false));
					surv = surv * (r-d)/r; 
					points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), false));
				} else {
					points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), true));
				}
				prevSurvTime = curSurvTime;
				d = 0;
				r = left; 	
			}
			if (((KMSampleInfo) samples.get(i)).getCensor() == 1) {
				d++;
			}
			left--;
		}
		if (d>0) { 
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), false));
			surv = surv * (r-d)/r; 
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), false));
		} else {
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(surv), true));
		}
		KMDrawingPoint[] kmDrawingPoints = (KMDrawingPoint[]) points.toArray(new KMDrawingPoint[points.size()]); 
		return kmDrawingPoints; 
	}
	
	//compute the p-value between two sample series
	public double getLogRankPValue(ArrayList group1, ArrayList group2) {
		//need to 
		ArrayList samples = new ArrayList(); 
		samples.addAll(group1); 
		samples.addAll(group2);
		Collections.sort(samples, new KMSampleComparator()); 
		double u = 0.0; 
		double v = 0.0; 
		int a = 0; 
		int b = 0; 
		int c = group1.size(); 
		int d = group2.size(); 
		float t = 0; 
		for (int i=0; i<samples.size(); i++) {
			KMSampleInfo event = (KMSampleInfo) samples.get(i); 
			if (event.getTime()>t) {
				u += a - (a+b)*(a+c)/(c+d); 
				v += (a+b)*(c+d)*(a+c)*(b+d)/((b+d-1)*(Math.pow((b+d),2)));
				a=0; 
				b=0; 
			}
			if (group1.contains(event)) {
				a++; 
				c--;
			} else {
				b++; 
				d--;
			}			
		}
		
		if (v>0) {
			return Statistics.chiSquaredProbability(Math.pow(u,2)/v, 1); 
		} else {
			return -100.0; 
		}
	}
	
	//compute the p-value for a sample series against the rest
	public double getLogRankPValue(ArrayList group1) {
		ArrayList rest = (ArrayList) allSamples.clone(); 
		rest.removeAll(group1); 
		return getLogRankPValue(group1, rest); 
	}
	
	/**
	 * @return Returns the downSamples.
	 */
	public ArrayList getDownSamples() {
		return downSamples;
	}

	/**
	 * @return Returns the intSamples.
	 */
	public ArrayList getIntSamples() {
		return intSamples;
	}

	/**
	 * @return Returns the upSamples.
	 */
	public ArrayList getUpSamples() {
		return upSamples;
	}
	
	/**
	 * @return Returns the allSamples.
	 */
	public ArrayList getAllSamples() {
		return allSamples;
	}
	public static void main(String [] args) {
		double upper = 3.0; 
		double lower = 3.0; 
		ArrayList kms = new ArrayList(); 
		//create fake data
		kms.add(new KMSampleInfo(1, 0, 0.5)); 
		kms.add(new KMSampleInfo(2, 0, 0.1)); 
		kms.add(new KMSampleInfo(3, 1, 0.1)); 
		kms.add(new KMSampleInfo(3, 0, 3.5)); 
		kms.add(new KMSampleInfo(4, 0, 1.5)); 
		kms.add(new KMSampleInfo(4, 1, 0.5)); 
		kms.add(new KMSampleInfo(5, 0, 0.5)); 
		kms.add(new KMSampleInfo(6, 0, 0.5)); 
		kms.add(new KMSampleInfo(7, 0, 3.5)); 
		kms.add(new KMSampleInfo(8, 0, 0.5)); 
		kms.add(new KMSampleInfo(9, 1, 8.5)); 
		kms.add(new KMSampleInfo(10, 0, 0.5)); 
		kms.add(new KMSampleInfo(11, 0, 0.05)); 
		kms.add(new KMSampleInfo(12, 1, 0.5)); 
		kms.add(new KMSampleInfo(13, 0, 0.05)); 
		kms.add(new KMSampleInfo(21, 0, 0.5)); 
		kms.add(new KMSampleInfo(22, 0, 0.1)); 
		kms.add(new KMSampleInfo(23, 1, 0.1)); 
		kms.add(new KMSampleInfo(23, 0, 3.5)); 
		kms.add(new KMSampleInfo(24, 0, 1.5)); 
		kms.add(new KMSampleInfo(24, 1, 0.5)); 
		kms.add(new KMSampleInfo(25, 0, 0.5)); 
		kms.add(new KMSampleInfo(26, 0, 0.5)); 
		kms.add(new KMSampleInfo(27, 0, 3.5)); 
		kms.add(new KMSampleInfo(28, 0, 0.5)); 
		kms.add(new KMSampleInfo(29, 1, 8.5)); 
		kms.add(new KMSampleInfo(30, 0, 0.5)); 
		kms.add(new KMSampleInfo(31, 0, 0.05)); 
		kms.add(new KMSampleInfo(32, 1, 0.5)); 
		kms.add(new KMSampleInfo(33, 0, 0.05)); 
		
		KMSampleInfo[] samples = new KMSampleInfo[kms.size()]; 		
		for (int j=0; j<kms.size(); j++) {
			samples[j] = (KMSampleInfo) kms.get(j);
			System.out.println("data" + j + ":" + samples[j].getValue());
		}
		
		System.out.println("Array size = " + samples.length); 
		KaplanMeier km = new KaplanMeier(samples, upper, lower); 
		
		//testing output of plotting points
		KMDrawingPoint[] points = km.getDrawingPoints(km.getAllSamples()); 
		System.out.println("\nAll Sample Output points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isChecked()); 
		}
		
		points = km.getDrawingPoints(km.getDownSamples()); 
		System.out.println("\nDown-regulated Sample Output points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isChecked()); 
		}
		points = km.getDrawingPoints(km.getUpSamples()); 
		System.out.println("\nUp-regulated Sample Output points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isChecked()); 
		}
		points = km.getDrawingPoints(km.getIntSamples()); 
		System.out.println("\nIntermediate Sample Output points: "); 
		for (int i=0; i<points.length; i++) {
			System.out.println(points[i].getX() 
							+ "\t" + points[i].getY()
							+ "\t" + points[i].isChecked()); 
		}
		
		//testing calculation of p-values
		System.out.println("up vs down" + km.getLogRankPValue(km.getUpSamples(), km.getDownSamples())); 
		System.out.println("up vs int" + km.getLogRankPValue(km.getUpSamples(), km.getIntSamples())); 
		System.out.println("int vs down" + km.getLogRankPValue(km.getIntSamples(), km.getDownSamples())); 
		System.out.println("up" + km.getLogRankPValue(km.getUpSamples())); 
		System.out.println("int" + km.getLogRankPValue(km.getIntSamples())); 
		System.out.println("down" + km.getLogRankPValue(km.getDownSamples())); 		
	}

}
