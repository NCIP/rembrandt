/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.resultset;
import java.util.*;


/**
 * @author SahniH
 * GeneExprSingleViewResultsContainer contains a collection for GeneResultset object
 * 
 *
 * 
 */
public class GeneExprSingleViewResultsContainer implements ResultsContainer{

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
	 * @param geneResultset Adds geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void addGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.put(geneResultset.getGeneSymbol().getValue().toString(), geneResultset);
		}
	}
	/**
	 * @param geneResultset Removes geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void removeGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.remove(geneResultset.getGeneSymbol().toString());
		}
	}
	/**
	 * @return geneResultset Returns geneResultset to this GeneExprSingleViewResultsContainer object.
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
     * @param geneSymbol
	 * @return reporterResultset Returns reporterResultset for this geneSymbol.
	 */
    public Collection getRepoterResultsets(String geneSymbol){
    	if(geneSymbol != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
			return geneResultset.getReporterResultsets();
		}
    		return null;
    }
    /**
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getGroupResultsets(String geneSymbol,String reporterName){
    	if(reporterName != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) geneResultset.getRepoterResultset(reporterName);
			return reporterResultset.getGroupResultsets();
		}
    		return null;
    }
    /**
     * @param geneSymbol,reporterName,groupType
	 * @return groupResultset Returns groupResultset for this groupType, reporterName , geneSymbol.
	 */
    public Collection getBioSpecimentResultsets(String geneSymbol,String reporterName, String groupType){
    	if(reporterName != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) geneResultset.getRepoterResultset(reporterName);
			GroupResultset groupResultset = (GroupResultset) reporterResultset.getGroupResultset(groupType);
			return groupResultset.getBioSpecimenResultsets();
		}
    		return null;
    }
    /**
     * @param geneSymbol,reporterName,groupType,bioSpecimenID
	 * @return bioSpecimenResultset Returns BioSpecimenResultset for this bioSpecimenID,groupType, reporterName , geneSymbol.
	 */
    public BioSpecimenResultset getBioSpecimentResultset(String geneSymbol,String reporterName, String groupType, String BioSpecimenID){
    	if(reporterName != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) geneResultset.getRepoterResultset(reporterName);
			GroupResultset groupResultset = (GroupResultset) reporterResultset.getGroupResultset(groupType);
			return groupResultset.getBioSpecimenResultset(BioSpecimenID);
		}
    		return null;
    }
	/**
	 * @param none Removes all geneResultset in this GeneExprSingleViewResultsContainer object.
	 */
    public void removeAllGeneResultset(){
    	genes.clear();
    }
}
