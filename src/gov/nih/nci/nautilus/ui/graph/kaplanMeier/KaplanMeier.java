/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package gov.nih.nci.nautilus.ui.graph.kaplanMeier;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.resultset.kaplanMeierPlot.SampleKaplanMeierPlotResultset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author XiaoN
 * 
 * Construct a KaplanMeier object, input an array of float (times) and an array
 * of int (censor) (equal length). Call the getKMDrawingPoints() method, which
 * returns an array of KMDrawingPoint object. If you have multiple series of
 * data (over-expressed samples, under-experessed samples, intermediate, etc.,
 * just repeat the above process to get multiple series of points to draw.
 *  
 */
public class KaplanMeier {
	private float[] times;
	private int[] censors;
	private ArrayList kmEvents;

	KMDrawingPoint[] kmDrawingPoints;
    static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);

	public KaplanMeier(Collection samples) {
		kmEvents = new ArrayList();
		//TODO: Make this typed collection
		if (samples != null) {
			for (Iterator sampleIterator = samples.iterator(); sampleIterator
					.hasNext();) {
				SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset) sampleIterator
						.next();
				Long time = (Long) (sample.getSurvivalLength().getValue());
				Integer censor = new Integer((sample.getCensor().getValue()
						.toString()));
				kmEvents.add(new KMEvent(time.floatValue(), censor.intValue()));
			}
		}
		Collections.sort(kmEvents, new KMEventComparator());
		createDrawingPoints();
	}

	public KaplanMeier(float[] times, int[] censors) {
		this.times = times;
		this.censors = censors;
		kmEvents = new ArrayList();
		for (int i = 0; i < times.length; i++) {
			kmEvents.add(new KMEvent(times[i], censors[i]));
		}
		Collections.sort(kmEvents, new KMEventComparator());
		createDrawingPoints();
	}

	private void createDrawingPoints() {
		float surv = 1;
		float prevSurvTime = 0;
		float curSurvTime = 0;
		int d = 0;
		int r = kmEvents.size();
		int left = kmEvents.size();
		ArrayList points = new ArrayList();
		logger.debug("Sorted input data: ");
		for (int i = 0; i < kmEvents.size(); i++) {
			curSurvTime = ((KMEvent) kmEvents.get(i)).getTime();
			logger.debug("Survival time: " + curSurvTime + "\tcensor:"
					+ ((KMEvent) kmEvents.get(i)).getCensor());
			if (curSurvTime > prevSurvTime) {
				if (d > 0) {
					points.add(new KMDrawingPoint(new Float(prevSurvTime),
							new Float(surv), false));
					surv = surv * (r - d) / r;
				
					points.add(new KMDrawingPoint(new Float(prevSurvTime),
							new Float(surv), false));
		
					logger.debug("New Point Added, ("+prevSurvTime+", "+surv+")");
				} else {
				  	points.add(new KMDrawingPoint(new Float(prevSurvTime),
							new Float(surv), true));
					logger.debug("New Point Added, ("+prevSurvTime+", "+surv+")");
				}
				prevSurvTime = curSurvTime;
				d = 0;
				r = left;
			}
			if (((KMEvent) kmEvents.get(i)).getCensor() == 1) {
				d++;
			}
			left--;
		}
		if (d > 0) {
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(
					surv), false));
			surv = surv * (r - d) / r;
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(
					surv), false));
		} else {
			points.add(new KMDrawingPoint(new Float(prevSurvTime), new Float(
					surv), true));
		}
		kmDrawingPoints = (KMDrawingPoint[]) points
				.toArray(new KMDrawingPoint[points.size()]);
	}

	public KMDrawingPoint[] getDrawingPoints() {
		return kmDrawingPoints;
	}

	public class KMEvent {
		private float time;

		private int censor;

		public KMEvent(float time, int censor) {
			this.censor = censor;
			this.time = time;
		}

		/**
		 * @return Returns the censor.
		 */
		public int getCensor() {
			return censor;
		}

		/**
		 * @return Returns the time.
		 */
		public float getTime() {
			return time;
		}
	}

	public class KMEventComparator implements Comparator {
		public int compare(Object o1, Object o2) throws ClassCastException {
			int val;
			float i1 = ((KMEvent) o1).getTime();
			float i2 = ((KMEvent) o2).getTime();
			if (i1 > i2) {
				val = 1;
			} else if (i1 == i2) {
				val = 0;
			} else {
				val = -1;
			}
			return val;
		}
	}

	public static void main(String[] args) {
		float[] t = { 4, 4, 4, 5, 1, 2, 3, 3, 6, 7 };
		int[] c = { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 };
		KaplanMeier km = new KaplanMeier(t, c);
		KMDrawingPoint[] points = km.getDrawingPoints();
		logger.debug("\nOutput points: ");
		for (int i = 0; i < points.length; i++) {
			logger.debug(points[i].getX() + "\t" + points[i].getY()
					+ "\t" + points[i].isCensus());
		}
	}
}