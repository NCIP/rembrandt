/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.resultset;
import java.util.*;


/**
 * @author SahniH
 * GeneViewContainer contains a collection for GeneResultset object
 * 
 *
 * 
 */
public class GeneViewContainer {

	private SortedMap genes = new TreeMap();
	private SortedMap groupsLabels = new TreeMap(); 
	
	
	/**
	 * @return Returns the biospecimenLabels.
	 */
	public Collection getBiospecimenLabels(String groupLabel) {
		return (SortedSet) groupsLabels.get(groupLabel);
	}
	/**
	 * @return Returns the groupsLabels.
	 */
	public Collection getGroupsLabels() {
		return  this.groupsLabels.keySet();
	}
	/**
	 * @param groupsLabels The groupsLabels to set.
	 */
	public void addBiospecimensToGroups(String groupLabel, String BiospecimenId) {
		SortedSet biospecimenLabels = null;
		if(groupsLabels.containsKey(groupLabel)){
			biospecimenLabels = (SortedSet) groupsLabels.get(groupLabel);
		}
		else { ///key does not exsist
			biospecimenLabels = new TreeSet();			
		}
		biospecimenLabels.add(BiospecimenId);
		groupsLabels.put(groupLabel,biospecimenLabels);
	}
	/**
	 * @param geneResultset Adds geneResultset to this GeneViewContainer object.
	 */
	public void addGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.put(geneResultset.getGeneSymbol().getValue().toString(), geneResultset);
		}
	}
	/**
	 * @param geneResultset Removes geneResultset to this GeneViewContainer object.
	 */
	public void removeDiseaseResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.remove(geneResultset.getGeneSymbol().toString());
		}
	}
	/**
	 * @return geneResultset Returns geneResultset to this GeneViewContainer object.
	 */
    public Collection getGeneResultsets(){
    		return genes.values();
    }
    /**
     * @param geneSymbol
	 * @return geneResultset Returns geneResultset to this geneSymbol.
	 */
    public GeneResultset getGeneResultset(String geneSymbol){
    	if(geneSymbol != null){
			return (GeneResultset) genes.get(geneSymbol);
		}
    		return null;
    }
	/**
	 * @param none Removes all geneResultset in this GeneViewContainer object.
	 */
    public void removeAllDiseaseResultset(){
    	genes.clear();
    }
	public static void main(String[] args) {
	}
}
