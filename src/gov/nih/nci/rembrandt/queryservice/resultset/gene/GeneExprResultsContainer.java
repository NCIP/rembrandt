package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
 */
public class GeneExprResultsContainer implements ResultsContainer{
	public static final String NO_GENE_SYMBOL = "zzzzzzzzzzzzzz";
	protected SortedMap genes = new TreeMap();
	protected SortedMap groupsLabels = new TreeMap();
	protected Set allReporterNames = new HashSet();
	/**
	 * @return Returns the groupsLabels.
	 */
	public Collection getGroupsLabels() {
		return  this.groupsLabels.keySet();
	}

	/**
	 * @param geneResultset Adds geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void addGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.put(geneResultset.getGeneSymbol().getValue().toString(), geneResultset);
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.addAll(geneResultset.getReporterNames());
			}
		}
		else if(geneResultset != null && geneResultset.isAnonymousGene() == true){
			genes.put(NO_GENE_SYMBOL, geneResultset);
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.addAll(geneResultset.getReporterNames());
			}
		}
	}
	/**
	 * @param geneResultset Removes geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void removeGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.remove(geneResultset.getGeneSymbol().toString());
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.removeAll(geneResultset.getReporterNames());
			}
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
        return (GeneResultset) genes.get(NO_GENE_SYMBOL);
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
     * @param reporterName
	 * @return reporterResultset Returns reporterResultset for this geneSymbol.
	 */
    public ReporterResultset getReporterResultset(String reporterName){
    	if(reporterName != null  && allReporterNames.contains(reporterName)){
    		Collection geneResultsets = genes.values();
    		for(Object obj :geneResultsets){
    			if(obj instanceof GeneResultset){
    				GeneResultset geneResultset = (GeneResultset) obj;
    				if(geneResultset.getRepoterResultset(reporterName)!= null){
    					return geneResultset.getRepoterResultset(reporterName);
    				}
    			}
    		}
		}
    		return null;
    }
	/**
	 * @param none Removes all geneResultset in this GeneExprSingleViewResultsContainer object.
	 */
    public void removeAllGeneResultset(){
    	genes.clear();
    	allReporterNames.clear();
    }
    /**
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getGroupByResultsets(String geneSymbol,String reporterName){
    	if(geneSymbol!= null && reporterName != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) geneResultset.getRepoterResultset(reporterName);
			return reporterResultset.getGroupByResultsets();
		}
    		return null;
    }
    /**
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getFilteredGroupByResultsets(String[] groupLabels, boolean isShow){
    	Collection geneResults = new ArrayList();
    	
    	Collection geneCollection = genes.values();
    	if(isShow){ //get all geneResultant objects that are in the collection
    		for (Iterator geneIterator = geneCollection.iterator(); geneIterator.hasNext();) {
 	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		Collection reporters = geneResultset.getReporterResultsets();
    		
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		for (int i = 0; i < groupLabels.length ; i++) {
	    	        	String label = (String) groupLabels[i];
	    	        	Groupable groupableResultset = reporterResultset.getGroupByResultset(label);
	    	        	if(groupableResultset != null){
	    	        		geneResults.add(geneResultset);
	    	        	}
	        		}
	    		}
    		}
	    	  
    	}
       	else{//return everything besides the ones that are in the collection
    		geneResults.addAll(geneCollection);
       		for (Iterator geneIterator = geneCollection.iterator(); geneIterator.hasNext();) {
 	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		Collection reporters = geneResultset.getReporterResultsets();
    		
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		for (int i = 0; i < groupLabels.length ; i++) {
	    	        	String label = (String) groupLabels[i];
	    	        	Groupable groupableResultset = reporterResultset.getGroupByResultset(label);
	    	        	if(groupableResultset != null){
	    	        		geneResults.remove(geneResultset);
	    	        	}
	        		}
	    		}
    		}
    	}
    	return geneResults;
    }    
	/**
	 * @param diseaseType
	 */
	public void addDiseaseTypes(String diseaseType) {
		groupsLabels.put(diseaseType,null);
		
	}
	//getPaginatedGeneResultsets(int start, int count) – returns gene resultant from start to start+count
    public Collection  getPaginatedGeneResultsets(int start, int count){
    	Set keys = genes.keySet();
    	String[] geneNames = (String[]) keys.toArray(new String[genes.size()]);
    	Collection geneResults = new ArrayList();
    	int size = count;
    	if( geneNames.length < count) {
    		size = geneNames.length;
    	}
    	for (int i = start; i < size; i++ ){
    		geneResults.add(genes.get(geneNames[i]));
    	}
    	return geneResults;
    }
//  getFilteredGeneResultsets( GeneIdentifierDE.GeneSymbol[] geneSymbols, boolean isShow ) – returns gene resultant Select to show/hide genes listed
    public Collection  getFilteredGeneResultsets( GeneIdentifierDE.GeneSymbol[] geneSymbols, boolean isShow ){
    	Collection geneResults = new ArrayList();
    	if(isShow){ //get all geneResultant objects that are in the collection
	    	for (int i = 0; i < geneSymbols.length; i++ ){
	    		String geneSymbol = geneSymbols[i].getValue().toString();
	    		if(genes.containsKey(geneSymbol)){
	    			geneResults.add(genes.get(geneSymbol));
	    		}
	    	}
    	}
       	else{//return everything besides the ones that are in the collection
       		Set symbols = genes.keySet();
	    	for (int i = 0; i < geneSymbols.length; i++ ){
	    		String geneSymbol = geneSymbols[i].getValue().toString();
	    		if(genes.containsKey(geneSymbol)){
	    			symbols.remove((String) genes.get(geneSymbol));
	    		}
	    	}
	    	Collection geneKeys = new ArrayList();
	    	for(Iterator symbolsIterator = symbols.iterator(); symbolsIterator.hasNext();){
	    		String symbol = (String) symbolsIterator.next();
	    		geneKeys.add(new GeneIdentifierDE.GeneSymbol(symbol));
	    	}
	    	geneResults = getFilteredGeneResultsets((GeneIdentifierDE.GeneSymbol[])geneKeys.toArray(new GeneIdentifierDE.GeneSymbol[geneKeys.size()]),true);
    	}
    	return geneResults;
    }
	/**
	 * @return Returns the allReporterNames.
	 */
	public List getAllReporterNames() {
		List list = new ArrayList();
		list.addAll(allReporterNames);
		return list;
	}
}
