package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SampleIDDE;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Dec 28, 2004
 * Time: 9:50:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleCriteria {
    private Collection sampleIDs;
    public Collection getSampleIDs() {
		return sampleIDs;
	}
	public void setSampleIDs(Collection sampleIDValues) {
		for (Iterator iterator = sampleIDValues.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof SampleIDDE) {
				getSampleIDDEsMember().add(obj);
			}
		}
	}
    private Collection getSampleIDDEsMember() {
		if (sampleIDs == null)
			sampleIDs = new ArrayList();
		return sampleIDs;
	}

	public SampleCriteria() {
	}
}
