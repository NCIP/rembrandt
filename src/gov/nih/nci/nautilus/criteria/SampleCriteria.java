package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.SampleIDDE;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ram
 */
public class SampleCriteria extends Criteria {
    private Collection sampleIDs;
    public Collection getSampleIDs() {
		return sampleIDs;
	}
   	public void setSampleIDs(Collection sampleIDValues) throws InvalidParameterException{
		for (Iterator iterator = sampleIDValues.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof SampleIDDE) {
				getSampleIDDEsMember().add(obj);
			}else {
				throw new InvalidParameterException("Collection must be of SampleIDDEs");
			}
		}
	}
    private Collection getSampleIDDEsMember() {
		if (sampleIDs == null)
			sampleIDs = new ArrayList();
		return sampleIDs;
	}
    public void setSampleID(SampleIDDE sampleID) {
		if (sampleID != null) {
			getSampleIDDEsMember().add(sampleID);
		}
	}

	public SampleCriteria() {
	}

    public boolean isValid() {
        return true;
    }
}
