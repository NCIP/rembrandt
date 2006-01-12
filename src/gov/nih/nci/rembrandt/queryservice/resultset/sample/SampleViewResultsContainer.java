package gov.nih.nci.rembrandt.queryservice.resultset.sample;

import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author SahniH
 * Date: Oct 21, 2004
 * 
 */
public class SampleViewResultsContainer implements ResultsContainer{
	protected SortedMap<String,SampleResultset> samples = new TreeMap<String, SampleResultset>();
	/**
	* the object type, it repesents generic typpe for data element
    */
    protected DomainElement type;
	/**
	 * @param sampleResultset Adds sampleResultset to this SampleViewResultsContainer object.
	 */
	public void addSampleResultset(SampleResultset sampleResultset){
		if(sampleResultset != null && sampleResultset.getSampleIDDE() != null){
			samples.put(sampleResultset.getSampleIDDE().getValue().toString(), sampleResultset);
		}
	}
	/**
	 * @param sampleResultset Removes sampleResultset to this SampleViewResultsContainer object.
	 */
	public void removeSampleResultset(SampleResultset sampleResultset){
		if(sampleResultset != null && sampleResultset.getSampleIDDE() != null){
			samples.remove(sampleResultset.getSampleIDDE());
		}
	}
	/**
	 * @return sampleResultset Returns sampleResultset to this SampleViewResultsContainer object.
	 */
    public Collection<SampleResultset> getSampleResultsets(){
    	Collection<SampleResultset> sampleValues = samples.values();	
        return sampleValues;
    }
	/**
	 * @return Collection<String> Returns key set to this SampleViewResultsContainer object.
	 */
   public Collection<String> getSampleIDs(){
   	Collection<String> sampleIDS = samples.keySet();	
    return sampleIDS;
   }
    /**
     * @param samplId
	 * @return sampleResultset Returns reporterResultset for this SampleViewResultsContainer.
	 */
    public SampleResultset getSampleResultset(String samplId){
    	if(samplId != null){
			return (SampleResultset) samples.get(samplId);
		}
    		return null;
    }
	/**
	 * @param none Removes all sampleResultset in this SampleViewResultsContainer object.
	 */
    public void removeAllSampleResultset(){
    	samples.clear();	
    }
}
