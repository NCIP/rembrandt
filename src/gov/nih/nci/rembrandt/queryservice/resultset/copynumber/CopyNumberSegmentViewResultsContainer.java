/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset.copynumber;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

public class CopyNumberSegmentViewResultsContainer implements ResultsContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 466679826250796567L;
	protected SortedMap<String,SampleCopyNumberValuesResultset> segments = new TreeMap<String,SampleCopyNumberValuesResultset>();
	/**
	 * @param cytobandResultset
	 *            Adds cytobandResultset to this CopyNumberResultsContainer
	 *            object.
	 */
	public void addSampleCopyNumberValuesResultset(final SampleCopyNumberValuesResultset segmentResultset) {
		if (segmentResultset != null
				&& segmentResultset.getSegment() != null) {
			segments.put(
					segmentResultset.getSegment().getValue().toString(),
					segmentResultset);
		}
	}

	/**
	 * @param segmentResultset
	 *            Removes segmentResultset to this CopyNumberResultsContainer
	 *            object.
	 */
	public void removeSampleCopyNumberValuesResultset(final SampleCopyNumberValuesResultset segmentResultset) {
		if (segmentResultset != null
				&& segmentResultset.getSegment() != null) {
			segments.remove(segmentResultset.getSegment().toString());
		}
	}

	/**
	 * @return segmentResultset Returns segmentResultset to this
	 *         CopyNumberResultsContainer object.
	 */
	public Collection getSampleCopyNumberValuesResultsets() {
		return segments.values();
	}

	/**
	 * @param segment
	 * @return segmentResultset Returns segmentResultset to this segment.
	 */
	public SampleCopyNumberValuesResultset getSampleCopyNumberValuesResultset(final String segment) {
		if (segment != null) {
			return (SampleCopyNumberValuesResultset) segments.get(segment);
		}
		return null;
	}
}
