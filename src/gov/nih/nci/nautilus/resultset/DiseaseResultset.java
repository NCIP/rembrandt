/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.de.*;
import java.util.*;
/**
 * @author SahniH
 *
 * This class encapulates a collection of DieaseResultset objects.
 */
public class DiseaseResultset {
	private DiseaseNameDE dieaseType;
	private SortedMap samples = new TreeMap();
	/**
	 * @return Returns the dieaseType.
	 */
	public DiseaseNameDE getDieaseType() {
		return dieaseType;
	}
	/**
	 * @param dieaseType The dieaseType to set.
	 */
	public void setDieaseType(DiseaseNameDE dieaseType) {
		this.dieaseType = dieaseType;
	}
	/**
	 * @param bioSpecimenResultset Adds bioSpecimenResultset to this DiseaseResultset object.
	 */
	public void addBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null){
			samples.put(bioSpecimenResultset.getBiospecimen(), bioSpecimenResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this DiseaseResultset object.
	 */
	public void removeBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null){
			samples.remove(bioSpecimenResultset.getBiospecimen());
		}
	}
	/**
	 * @return bioSpecimenResultset Returns bioSpecimenResultset to this DiseaseResultset object.
	 */
    public BioSpecimenResultset[] getBioSpecimenResultsets(){
    		return (BioSpecimenResultset[]) samples.values().toArray();
    }
	/**
	 * @param none Removes all bioSpecimenResultset in this DiseaseResultset object.
	 */
    public void removeAllBioSpecimenResultset(){
    	samples.clear();
    }
	public static void main(String[] args) {
	}
}
