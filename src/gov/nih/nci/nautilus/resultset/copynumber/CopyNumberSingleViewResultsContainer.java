/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.copynumber;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.nautilus.resultset.sample.BioSpecimenResultset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyNumberSingleViewResultsContainer extends CopyNumberResultsContainer{
	/**
	 * @return Returns the biospecimenLabels.
	 */
	public Collection getBiospecimenLabels(String groupLabel) {
		return (SortedSet) groupsLabels.get(groupLabel);
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
     * @param cytoband,reporterName,groupType
	 * @return groupResultset Returns groupResultset for this groupType, reporterName , cytoband.
	 */
    public Collection getBioSpecimentResultsets(String cytoband,String reporterName, String groupType){
    	if(cytoband != null && reporterName != null && groupType != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(cytoband);
    		if(cytobandResultset != null){
    			ReporterResultset reporterResultset = (ReporterResultset) cytobandResultset.getRepoterResultset(reporterName);
			if(reporterResultset != null){
				ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(groupType);
					return groupResultset.getBioSpecimenResultsets();
				}
    		}
		}
    		return null;
    }
    /**
     * @param cytoband,reporterName,groupType,bioSpecimenID
	 * @return bioSpecimenResultset Returns BioSpecimenResultset for this bioSpecimenID,groupType, reporterName , cytoband.
	 */
    public BioSpecimenResultset getBioSpecimentResultset(String cytoband,String reporterName, String groupType, String bioSpecimenID){
    	if(cytoband != null && reporterName != null && groupType != null  && bioSpecimenID != null){
    		CytobandResultset cytobandResultset = (CytobandResultset) cytobands.get(cytoband);
    		if(cytobandResultset != null){
    			ReporterResultset reporterResultset = (ReporterResultset) cytobandResultset.getRepoterResultset(reporterName);
    			if(reporterResultset != null){
    				ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(groupType);
    				if(groupResultset != null){
    					return groupResultset.getBioSpecimenResultset(bioSpecimenID);
    				}
    			}
    		}
		}
    		return null;
    }
    
    public List getCytobandNames(){  
    	List list = new ArrayList();
    	list.addAll(cytobands.keySet());
    	return list;
    }

    
    
}
