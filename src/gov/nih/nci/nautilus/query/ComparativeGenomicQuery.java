package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.AllGenesCriteria;
import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.de.AlleleFrequencyDE;
import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ComparativeGenomicQuery extends Query implements Serializable,Cloneable{
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private AllGenesCriteria allGenesCrit;

	private Logger logger = Logger
			.getLogger(ComparativeGenomicQuery.class);

	private GeneIDCriteria geneIDCriteria;

	private CopyNumberCriteria copyNumberCriteria;

	private RegionCriteria regionCriteria;

	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;

	private SNPCriteria snpCriteria;

	private AlleleFrequencyCriteria alleleFrequencyCriteria;

	private AssayPlatformCriteria assayPlatformCriteria;

	private QueryHandler HANDLER;

	public QueryHandler getQueryHandler() throws Exception {
		return (HANDLER == null) ? new gov.nih.nci.nautilus.queryprocessing.cgh.CGHQueryHandler()
				: HANDLER;
	}

	public QueryType getQueryType() throws Exception {
		return QueryType.CGH_QUERY_TYPE;
	}

	public ComparativeGenomicQuery() {
		super();
	}

	public String toString() {
		ResourceBundle labels = null;
		String OutStr = "<B>Comparative Genomic Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>"
				+ this.getQueryName();

		try {

			labels = ResourceBundle.getBundle(
					NautilusConstants.APPLICATION_RESOURCES, Locale.US);

			// starting DiseaseOrGradeCriteria
			DiseaseOrGradeCriteria thisDiseaseCrit = this
					.getDiseaseOrGradeCriteria();
			if ((thisDiseaseCrit != null) && !thisDiseaseCrit.isEmpty()
					&& labels != null) {
				Collection diseaseColl = thisDiseaseCrit.getDiseases();
				String thisCriteria = thisDiseaseCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B><BR>";

				Iterator iter = diseaseColl.iterator();
				while (iter.hasNext()) {
					DiseaseNameDE diseaseDE = (DiseaseNameDE) iter.next();
					OutStr += "&nbsp;&nbsp;" + ((String) diseaseDE.getValue())
							+ " ";
				}
			} else {
				logger
						.debug("Disease Criteria is empty or Application Resources file is missing");
			} // end of DiseaseOrGradeCriteria

			// start All Genes Criteria
			AllGenesCriteria thisAllGenesCrit = this.getAllGenesCrit();
			if (thisAllGenesCrit != null && !thisAllGenesCrit.isEmpty()) {
				OutStr += "<br /><b class='otherbold'>Gene</b><br />&nbsp;&nbsp;&nbsp;All Genes";
			} else
				logger.debug("This is not an All Genes Query");

			// starting CopyNumberCriteria
			CopyNumberCriteria thisCopyNumberCrit = this
					.getCopyNumberCriteria();

			if ((thisCopyNumberCrit != null) && !thisCopyNumberCrit.isEmpty()
					&& labels != null) {
				logger.debug(" I am in the CopyNumberCriteria");
				String thisCriteria = thisCopyNumberCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection copyNoObjects = thisCopyNumberCrit.getCopyNummbers();

				for (Iterator iter = copyNoObjects.iterator(); iter.hasNext();) {
					DomainElement de = (DomainElement) iter.next();
					String thisDomainElement = de.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(thisDomainElement
									.substring(thisDomainElement
											.lastIndexOf(".") + 1)) + ": "
							+ de.getValue();
				}
			} else {
				logger
						.debug("Copy Number Criteria is empty or Application Resources file is missing");
			} // end of CopyNumberCriteria

			GeneIDCriteria thisGeneIDCrit = this.getGeneIDCriteria();
			if ((thisGeneIDCrit != null) && !thisGeneIDCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisGeneIDCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection geneIDObjects = thisGeneIDCrit.getGeneIdentifiers();
				int count = 0;
				for (Iterator iter = geneIDObjects.iterator(); iter.hasNext()
						&& count < 5;) {
					count++;
					DomainElement de = (DomainElement) iter.next();
					String thisDomainElement = de.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(thisDomainElement
									.substring(thisDomainElement
											.lastIndexOf(".") + 1)) + ": "
							+ de.getValue();
				}
				if (geneIDObjects != null && geneIDObjects.size() > 5) {
					OutStr += "<BR>&nbsp;&nbsp;...";
				}
			} else
				logger
						.debug("Gene ID Criteria is empty or Application Resources file is missing");

			SampleCriteria thisSampleIDCrit = this.getSampleIDCrit();

			if ((thisSampleIDCrit != null) && !thisSampleIDCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisSampleIDCrit.getClass().getName();

				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection sampleIDObjects = thisSampleIDCrit.getSampleIDs();
				int count = 0;
				for (Iterator iter = sampleIDObjects.iterator(); iter.hasNext()
						&& count < 5;) {
					count++;
					DomainElement de = (DomainElement) iter.next();
					String thisDomainElement = de.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(thisDomainElement
									.substring(thisDomainElement
											.lastIndexOf(".") + 1)) + ": "
							+ de.getValue();
				}
				if (sampleIDObjects != null && sampleIDObjects.size() > 5) {
					OutStr += "<BR>&nbsp;&nbsp;...";
				}
			} else
				logger
						.debug("Sample ID Criteria is empty or Application Resources file is missing");

			// starting RegionCriteria
			RegionCriteria thisRegionCrit = this.getRegionCriteria();
			if ((thisRegionCrit != null) && !thisRegionCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisRegionCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				DomainElement cytoBandDE = thisRegionCrit.getCytoband();
				DomainElement cytoBandEndDE = thisRegionCrit.getEndCytoband();

				DomainElement chromosomeDE = thisRegionCrit.getChromNumber();
				DomainElement chrStartDE = thisRegionCrit.getStart();
				DomainElement chrEndDE = thisRegionCrit.getEnd();

				if (chromosomeDE != null) {
					String chromosomeDEStr = chromosomeDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels
									.getString(chromosomeDEStr
											.substring(chromosomeDEStr
													.lastIndexOf(".") + 1))
							+ ": " + chromosomeDE.getValue();

					if (cytoBandDE != null && cytoBandEndDE != null) {
						String cytoBandStr = cytoBandDE.getClass().getName();
						String cytoBandEndStr = cytoBandEndDE.getClass()
								.getName();
						OutStr += "<BR>&nbsp;&nbsp;"
								+ labels
										.getString(cytoBandStr
												.substring(cytoBandStr
														.lastIndexOf(".") + 1))
								+ ": " + cytoBandDE.getValue();
						OutStr += "&nbsp;&nbsp;to " + cytoBandEndDE.getValue();
					} else if (cytoBandDE != null && cytoBandEndDE == null) {
						String cytoBandStr = cytoBandDE.getClass().getName();
						OutStr += "<BR>&nbsp;&nbsp;"
								+ labels
										.getString(cytoBandStr
												.substring(cytoBandStr
														.lastIndexOf(".") + 1))
								+ ": " + cytoBandDE.getValue();
					} else {
						if (chrStartDE != null && chrEndDE != null) {
							String chrStartDEStr = chrStartDE.getClass()
									.getName();
							String chrEndDEStr = chrEndDE.getClass().getName();
							OutStr += "<BR>&nbsp;&nbsp;"
									+ labels.getString(chrStartDEStr.substring(
											chrStartDEStr.lastIndexOf(".") + 1,
											chrStartDEStr.lastIndexOf("$")))
									+ "(kb)";
							OutStr += "<BR>&nbsp;&nbsp;&nbsp;"
									+ labels.getString(chrStartDEStr
											.substring(chrStartDEStr
													.lastIndexOf(".") + 1))
									+ ": " + chrStartDE.getValue();
							OutStr += "<BR>&nbsp;&nbsp;&nbsp;"
									+ labels.getString(chrEndDEStr
											.substring(chrEndDEStr
													.lastIndexOf(".") + 1))
									+ ": " + chrEndDE.getValue();
						}
					}
				}
			} else {
				logger
						.debug("Region Criteria is empty or Application Resources file is missing");
			}// end of RegionCriteria

			// starting cloneorProbeCriteria

			CloneOrProbeIDCriteria thisCloneOrProbeCriteria = this
					.getCloneOrProbeIDCriteria();
			if ((thisCloneOrProbeCriteria != null)
					&& !thisCloneOrProbeCriteria.isEmpty() && labels != null) {
				String thisCriteria = thisCloneOrProbeCriteria.getClass()
						.getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection cloneColl = thisCloneOrProbeCriteria
						.getIdentifiers();
				Iterator iter = cloneColl.iterator();
				int count = 0;
				while (iter.hasNext() && count > 5) {
					CloneIdentifierDE cloneIdentifierDE = (CloneIdentifierDE) iter
							.next();
					String cloneStr = cloneIdentifierDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(cloneStr.substring(cloneStr
									.lastIndexOf(".") + 1)) + ": "
							+ cloneIdentifierDE.getValue() + "";
				}
				if (cloneColl != null && cloneColl.size() > 5) {
					OutStr += "<BR>&nbsp;and&nbsp;...";
				}
			} else {
				logger
						.debug("Clone or Probe Criteria is empty or Application Resources file is missing.");
			}// end of cloneorProbeCriteria

			// starting snpCriteria:
			SNPCriteria thisSNPCriteria = this.getSNPCriteria();
			if ((thisSNPCriteria != null) && !thisSNPCriteria.isEmpty()
					&& labels != null) {
				String thisCriteria = thisSNPCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection cloneColl = thisSNPCriteria.getIdentifiers();
				Iterator iter = cloneColl.iterator();
				int count = 0;
				while (iter.hasNext() && count < 5) {
					count++;
					SNPIdentifierDE snpIdentifierDE = (SNPIdentifierDE) iter
							.next();
					String snpIdStr = snpIdentifierDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(snpIdStr.substring(snpIdStr
									.lastIndexOf(".") + 1)) + ": "
							+ snpIdentifierDE.getValue() + "";
				}
				if (cloneColl != null && cloneColl.size() > 5) {
					OutStr += "<BR>&nbsp;&nbsp;...";
				}
			} else {
				logger
						.debug("SNP Criteria is empty or Application Resources file is missing.");
			}// end of cloneorProbeCriteria

			// starting AlleleFrequencyCriteria:

			AlleleFrequencyCriteria thisAlleleFrequencyCriteria = this
					.getAlleleFrequencyCriteria();
			if ((thisAlleleFrequencyCriteria != null)
					&& !thisAlleleFrequencyCriteria.isEmpty() && labels != null) {
				AlleleFrequencyDE alleleFrequencyDE = thisAlleleFrequencyCriteria
						.getAlleleFrequencyDE();
				String alleleStr = alleleFrequencyDE.getClass().getName();

				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(alleleStr.substring(alleleStr
								.lastIndexOf(".") + 1)) + "</B>";
				OutStr += "<BR>&nbsp;&nbsp;" + alleleFrequencyDE.getValue();

			} else {
				logger
						.debug("SNP Criteria is empty or Application Resources file is missing.");
			}// end of AlleleFrequencyCriteria

			// starting AssayPlatformCriteria
			AssayPlatformCriteria thisAssayPlatformCriteria = this
					.getAssayPlatformCriteria();
			if ((thisAssayPlatformCriteria != null)
					&& !thisAssayPlatformCriteria.isEmpty() && labels != null) {
				AssayPlatformDE assayPlatformDE = thisAssayPlatformCriteria
						.getAssayPlatformDE();
				String assayStr = assayPlatformDE.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(assayStr.substring(assayStr
								.lastIndexOf(".") + 1)) + "</B>";
				OutStr += "<BR>&nbsp;&nbsp;" + assayPlatformDE.getValue();

			} else {
				logger
						.debug("AssayPlatform Criteria is empty or Application Resources file is missing.");
			}
		}// end of try
		catch (Exception ie) {
			logger.error("Error in ResourceBundle in CGH query - ");
			logger.error(ie);
		}

		OutStr += "<BR><BR>";
		return OutStr;
	}

	public GeneIDCriteria getGeneIDCriteria() {
		return geneIDCriteria;
	}

	public void setGeneIDCrit(GeneIDCriteria geneIDCriteria) {
		this.geneIDCriteria = geneIDCriteria;
	}

	public AllGenesCriteria getAllGenesCrit() {
		return allGenesCrit;
	}

	public void setAllGenesCrit(AllGenesCriteria allGenes) {
		this.allGenesCrit = allGenes;
	}

	public RegionCriteria getRegionCriteria() {
		return regionCriteria;
	}

	public void setRegionCrit(RegionCriteria regionCriteria) {
		this.regionCriteria = regionCriteria;
	}

	public CopyNumberCriteria getCopyNumberCriteria() {
		return copyNumberCriteria;
	}

	public void setCopyNumberCrit(CopyNumberCriteria copyNumberCriteria) {
		this.copyNumberCriteria = copyNumberCriteria;
	}

	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria() {
		return cloneOrProbeIDCriteria;
	}

	public void setCloneOrProbeIDCrit(
			CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
		this.cloneOrProbeIDCriteria = cloneOrProbeIDCriteria;
	}

	public SNPCriteria getSNPCriteria() {
		return snpCriteria;
	}

	public void setSNPCrit(SNPCriteria snpCriteria) {
		this.snpCriteria = snpCriteria;
	}

	public AlleleFrequencyCriteria getAlleleFrequencyCriteria() {
		return alleleFrequencyCriteria;
	}

	public void setAlleleFrequencyCrit(
			AlleleFrequencyCriteria alleleFrequencyCriteria) {
		this.alleleFrequencyCriteria = alleleFrequencyCriteria;
	}

	public AssayPlatformCriteria getAssayPlatformCriteria() {
		return assayPlatformCriteria;
	}

	public void setAssayPlatformCrit(AssayPlatformCriteria assayPlatformCriteria) {
		this.assayPlatformCriteria = assayPlatformCriteria;
	}

	/**
	 * Returns a boolean true if the AllGenesCriteria has been set
	 * 
	 * @return
	 */
	public boolean isAllGenesQuery() {
		if (allGenesCrit != null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		ComparativeGenomicQuery myClone = null;
		myClone = (ComparativeGenomicQuery) super.clone();
		if(alleleFrequencyCriteria != null){
            myClone.alleleFrequencyCriteria = (AlleleFrequencyCriteria) alleleFrequencyCriteria.clone();
        }
		if(allGenesCrit != null){		
            myClone.allGenesCrit = (AllGenesCriteria) allGenesCrit.clone();
        }
        if(assayPlatformCriteria != null){
            myClone.assayPlatformCriteria = (AssayPlatformCriteria) assayPlatformCriteria.clone();
        }
        if(cloneOrProbeIDCriteria !=null){
            myClone.cloneOrProbeIDCriteria = (CloneOrProbeIDCriteria) cloneOrProbeIDCriteria.clone();
        }
        if(copyNumberCriteria != null){
            myClone.copyNumberCriteria = (CopyNumberCriteria) copyNumberCriteria.clone();
        }
        if(geneIDCriteria != null){
            myClone.geneIDCriteria = (GeneIDCriteria) geneIDCriteria.clone();
        }
        if(regionCriteria != null){
            myClone.regionCriteria = (RegionCriteria) regionCriteria.clone();
        }
        if(snpCriteria != null){
            myClone.snpCriteria = (SNPCriteria) snpCriteria.clone();
        }
		return myClone;
	}

	class Handler {
	}
}
