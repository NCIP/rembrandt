/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.resultset;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author SahniH
 * GeneViewContainer contains a collection for GeneResultset object
 * 
 *
 * 
 */
public class GeneViewContainer {

	private SortedMap genes = new TreeMap();
	/**
	 * @param geneResultset Adds geneResultset to this GeneViewContainer object.
	 */
	public void addGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGenbankAccessionNo() != null){
			genes.put(geneResultset.getGenbankAccessionNo(), geneResultset);
		}
	}
	/**
	 * @param geneResultset Removes geneResultset to this GeneViewContainer object.
	 */
	public void removeDiseaseResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGenbankAccessionNo() != null){
			genes.remove(geneResultset.getGenbankAccessionNo());
		}
	}
	/**
	 * @return geneResultset Returns geneResultset to this GeneViewContainer object.
	 */
    public GeneResultset[] getGeneResultsets(){
    		return (GeneResultset[]) genes.values().toArray();
    }
    /**
     * @param getGenBankAccessionNo
	 * @return geneResultset Returns geneResultset to this genBankAccessionNumber.
	 */
    public GeneResultset getGeneResultsets(String genBankAccessionNo){
    	if(genBankAccessionNo != null){
			return (GeneResultset) genes.get(genBankAccessionNo);
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
