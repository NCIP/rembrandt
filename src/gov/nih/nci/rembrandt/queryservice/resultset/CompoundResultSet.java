package gov.nih.nci.rembrandt.queryservice.resultset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * @author SahniH
 * Date: Nov 17, 2004
 * 
 */
public class CompoundResultSet {
	private Collection results = new Vector();
	private Set sampleIds = new HashSet();

	/**
	 * @param results
	 */
	public CompoundResultSet(Collection results,Set sampleIds) {
		setResults(results);
		setSampleIds(sampleIds);
	}
	/**
	 * @return Returns the sampleIds.
	 */
	public Set getSampleIds() {
		return this.sampleIds;
	}
	/**
	 * @param sampleIds The sampleIds to set.
	 */
	public void setSampleIds(Set sampleIds) {
		this.sampleIds = sampleIds;
	}
	/**
	 * @return Returns the results.
	 */
	public Collection getResults() {
		return results;
	}
	/**
	 * @param results The results to set.
	 */
	public void setResults(Collection results) {
		this.results = results;
	}
}
