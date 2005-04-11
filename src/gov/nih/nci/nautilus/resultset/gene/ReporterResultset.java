/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.gene;
import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.DatumDE;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * @author SahniH
 *
 This class encapulates a collection of SampleResultset objects.
 */
public class ReporterResultset {
	private DatumDE reporter = null;
    private DatumDE value = null;
    private BasePairPositionDE.StartPosition startPhysicalLocation = null;
	private SortedMap groupTypes = new TreeMap();
    private Collection assiciatedGeneSymbols = null;
    private Collection assiciatedLocusLinkIDs =  null;
    private Collection assiciatedGenBankAccessionNos = null;
    private Collection associatedPathways = null;
    private Collection associatedGOIds = null;
	/**
	 * 
	 */
	public ReporterResultset(DatumDE repoter) {
		setReporter(repoter);
	}
	/**
	 * @param groupResultset Adds groupResultset to this ReporterResultset object.
	 */
	public void addGroupByResultset(Groupable groupResultset){
		if(groupResultset != null && groupResultset.getType() != null){
			groupTypes.put(groupResultset.getType().getValue().toString(), groupResultset);
		}
	}
	/**
	 * @param groupResultset Removes groupResultset to this ReporterResultset object.
	 */
	public void removeGroupByResultset(Groupable groupResultset){
		if(groupResultset != null && groupResultset.getType() != null){
			groupTypes.remove(groupResultset.getType().getValue().toString());
		}
	}
    /**
     * @param disease
	 * @return groupResultset Returns reporterResultset for this ReporterResultset.
	 */
    public Groupable getGroupByResultset(String groupType){
    	if(groupType != null){
			return (Groupable) groupTypes.get(groupType);
		}
    		return null;
    }
	/**
	 * @return Collection Returns collection of GroupResultsets to this ReporterResultset object.
	 */
    public Collection getGroupByResultsets(){
    		return groupTypes.values();
    }
	/**
	 * @param none Removes all groupResultset in this ReporterResultset object.
	 */
    public void removeAllGroupByResultset(){
    	groupTypes.clear();
    }
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the reporterName.
	 */
	public DatumDE getReporter() {
		return reporter;
	}
	/**
	 * @param reporterID The reporterID to set.
	 */
	public void setReporter(DatumDE reporter) {
		this.reporter = reporter;
	}
	/**
	 * @return Returns the reporterType.
	 */
	public String getReporterType() {
		return reporter.getType();
	}
	
	/**
	 * @return Returns the assiciatedGenBankAccessionNos.
	 */
	public Collection getAssiciatedGenBankAccessionNos() {
		return assiciatedGenBankAccessionNos;
	}
	/**
	 * @param assiciatedGenBankAccessionNos The assiciatedGenBankAccessionNos to set.
	 */
	public void setAssiciatedGenBankAccessionNos(
			Collection assiciatedGenBankAccessionNos) {
		this.assiciatedGenBankAccessionNos = assiciatedGenBankAccessionNos;
	}
	/**
	 * @return Returns the assiciatedGeneSymbols.
	 */
	public Collection getAssiciatedGeneSymbols() {
		return assiciatedGeneSymbols;
	}
	/**
	 * @param assiciatedGeneSymbols The assiciatedGeneSymbols to set.
	 */
	public void setAssiciatedGeneSymbols(Collection assiciatedGeneSymbols) {
		this.assiciatedGeneSymbols = assiciatedGeneSymbols;
	}
	/**
	 * @return Returns the assiciatedLocusLinkIDs.
	 */
	public Collection getAssiciatedLocusLinkIDs() {
		return assiciatedLocusLinkIDs;
	}
	/**
	 * @param assiciatedLocusLinkIDs The assiciatedLocusLinkIDs to set.
	 */
	public void setAssiciatedLocusLinkIDs(Collection assiciatedLocusLinkIDs) {
		this.assiciatedLocusLinkIDs = assiciatedLocusLinkIDs;
	}
    public BasePairPositionDE.StartPosition getStartPhysicalLocation() {
        return startPhysicalLocation;
    }
    
    public void setStartPhysicalLocation(BasePairPositionDE.StartPosition startPhysicalLocation) {
        this.startPhysicalLocation = startPhysicalLocation;
    }
    public DatumDE getValue() {
        return value;
    }
    
    public void setValue(DatumDE value) {
        this.value = value;
    }
     
	/**
	 * @return Returns the associatedPathways.
	 */
	public Collection getAssociatedPathways() {
		return associatedPathways;
	}
	/**
	 * @param associatedPathways The associatedPathways to set.
	 */
	public void setAssociatedPathways(Collection associatedPathways) {
		this.associatedPathways = associatedPathways;
	}

	/**
	 * @return Returns the associatedGOIds.
	 */
	public Collection getAssociatedGOIds() {
		return associatedGOIds;
	}
	/**
	 * @param associatedGOIds The associatedGOIds to set.
	 */
	public void setAssociatedGOIds(Collection associatedGOIds) {
		this.associatedGOIds = associatedGOIds;
	}
}
