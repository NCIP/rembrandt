package gov.nih.nci.nautilus.ui.graph.kaplanMeier;
import java.util.*;
public class KMSampleComparator implements Comparator {
	public int compare(Object o1, Object o2) throws ClassCastException {
		int val;
		float i1 = ((KMSampleInfo) o1).getTime();
		float i2 = ((KMSampleInfo) o2).getTime();
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