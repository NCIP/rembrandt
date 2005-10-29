/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset.copynumber;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.service.findings.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Himanso
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CopyNumberResultsContainer implements ResultsContainer {
	protected SortedMap cytobands = new TreeMap();

	protected SortedMap groupsLabels = new TreeMap();

	protected SortedSet reporterNames = new TreeSet();

    //TODO: protected DatumDE[] reporterDatumDEs;
	/**
	 * @return Returns the groupsLabels.
	 */
	public Collection getGroupsLabels() {
		return this.groupsLabels.keySet();
	}

	/**
	 * @param cytobandResultset
	 *            Adds cytobandResultset to this CopyNumberResultsContainer
	 *            object.
	 */
	public void addCytobandResultset(CytobandResultset cytobandResultset) {
		if (cytobandResultset != null
				&& cytobandResultset.getCytoband() != null) {
			cytobands.put(
					cytobandResultset.getCytoband().getValue().toString(),
					cytobandResultset);
			if (cytobandResultset.getReporterNames() != null) {
				reporterNames.addAll(cytobandResultset.getReporterNames());
			}
		}
	}

	/**
	 * @param cytobandResultset
	 *            Removes cytobandResultset to this CopyNumberResultsContainer
	 *            object.
	 */
	public void removeCytobandResultset(CytobandResultset cytobandResultset) {
		if (cytobandResultset != null
				&& cytobandResultset.getCytoband() != null) {
			cytobands.remove(cytobandResultset.getCytoband().toString());
			if (cytobandResultset.getReporterNames() != null) {
				reporterNames.removeAll(cytobandResultset.getReporterNames());
			}
		}
	}

	/**
	 * @return cytobandResultset Returns cytobandResultset to this
	 *         CopyNumberResultsContainer object.
	 */
	public Collection getCytobandResultsets() {
		return cytobands.values();
	}

	/**
	 * @param cytoband
	 * @return cytobandResultset Returns cytobandResultset to this cytoband.
	 */
	public CytobandResultset getCytobandResultset(String cytoband) {
		if (cytoband != null) {
			return (CytobandResultset) cytobands.get(cytoband);
		}
		return null;
	}

	/**
	 * @param cytoband
	 * @return reporterResultset Returns reporterResultset for this cytoband.
	 */
	public Collection getRepoterResultsets(String cytoband) {
		if (cytoband != null) {
			CytobandResultset cytobandResultset = (CytobandResultset) cytobands
					.get(cytoband);
			return cytobandResultset.getReporterResultsets();
		}
		return null;
	}

	/**
	 * @param none
	 *            Removes all cytobandResultset in this
	 *            CopyNumberResultsContainer object.
	 */
	public void removeAllCytobandResultset() {
		cytobands.clear();
		reporterNames.clear();
	}

	/**
	 * @param cytoband,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName &
	 *         cytoband.
	 */
	public Collection getGroupByResultsets(String cytoband, String reporterName) {
		if (cytoband != null && reporterName != null) {
			CytobandResultset cytobandResultset = (CytobandResultset) cytobands
					.get(cytoband);
			if (cytobandResultset != null) {
				ReporterResultset reporterResultset = (ReporterResultset) cytobandResultset
						.getRepoterResultset(reporterName);
				return reporterResultset.getGroupByResultsets();
			}
		}
		return null;
	}

	public SampleCopyNumberValuesResultset getSampleCopyNumberValuesResultsets(
			String cytoband, String reporterName, String groupType,
			String bioSpecimenID) {
		if (cytoband != null && reporterName != null && groupType != null
				&& bioSpecimenID != null) {
			CytobandResultset cytobandResultset = (CytobandResultset) cytobands
					.get(cytoband);
			if (cytobandResultset != null) {
				ReporterResultset reporterResultset = cytobandResultset
						.getRepoterResultset(reporterName);
				if (reporterResultset != null) {
					ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset
							.getGroupByResultset(groupType);
					if (groupResultset != null) {
						return (SampleCopyNumberValuesResultset) groupResultset
								.getBioSpecimenResultset(bioSpecimenID);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return Returns the reporterNames.
	 */
	public List getReporterNames() {
		List list = new ArrayList();
		list.addAll(reporterNames);
		return list;
	}

}