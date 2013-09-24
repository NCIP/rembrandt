/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

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
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null  && bioSpecimenResultset.getBiospecimen().getSpecimenName()!= null){
			samples.put(bioSpecimenResultset.getBiospecimen().getSpecimenName(), bioSpecimenResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this ViewByGroupResultset object.
	 */
	public void removeBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null  && bioSpecimenResultset.getBiospecimen().getSpecimenName()!= null){
			samples.remove(bioSpecimenResultset.getBiospecimen().getSpecimenName());
		}
	}
	/**
	 * @return bioSpecimenResultset Returns bioSpecimenResultset to this ViewByGroupResultset object.
	 */
    public Collection getBioSpecimenResultsets(){
    		return samples.values();
    }
    /**
     * @param sampleName
	 * @return bioSpecimenResultset Returns reporterResultset for this GeneResultset.
	 */
    public BioSpecimenResultset getBioSpecimenResultset(String sampleName){
    	if(sampleName!= null){
			return (BioSpecimenResultset) samples.get(sampleName);
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
