package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.BioSpecimenResultset;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * @author SahniH
 * Date: Oct 12, 2004
 * 
 */
public abstract class ViewByGroupResultset implements Groupable{
	private SortedMap samples = new TreeMap();
	/**
	* the object type, it repesents generic typpe for data element
    */
    protected DomainElement type;
   

	/**
	 * @return Returns the type.
	 */
	abstract public DomainElement getType(); 
	/**
	 * @param type The type to set.
	 */
	abstract public void setType(DomainElement type) throws Exception;
	/**
	 * @param bioSpecimenResultset Adds bioSpecimenResultset to this ViewByGroupResultset object.
	 */
	public void addBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getSampleIDDE() != null){
			samples.put(bioSpecimenResultset.getSampleIDDE().getValue().toString(), bioSpecimenResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this ViewByGroupResultset object.
	 */
	public void removeBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getSampleIDDE() != null){
			samples.remove(bioSpecimenResultset.getSampleIDDE());
		}
	}
	/**
	 * @return bioSpecimenResultset Returns bioSpecimenResultset to this ViewByGroupResultset object.
	 */
    public Collection getBioSpecimenResultsets(){
    		return samples.values();
    }
    /**
     * @param sampleId
	 * @return bioSpecimenResultset Returns reporterResultset for this GeneResultset.
	 */
    public BioSpecimenResultset getBioSpecimenResultset(String sampleId){
    	if(sampleId != null){
			return (BioSpecimenResultset) samples.get(sampleId);
		}
    		return null;
    }
	/**
	 * @param none Removes all bioSpecimenResultset in this ViewByGroupResultset object.
	 */
    public void removeAllBioSpecimenResultset(){
    	samples.clear();
    }

}
