package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExprQueryHandler;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class GeneExpressionQuery extends Query implements Serializable,Cloneable{
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

	private static Logger logger = Logger.getLogger(GeneExpressionQuery.class);

	private GeneIDCriteria geneIDCrit;

	private RegionCriteria regionCrit;

	private FoldChangeCriteria foldChgCrit;

	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;

	private GeneOntologyCriteria geneOntologyCriteria;

	private PathwayCriteria pathwayCriteria;

	private ArrayPlatformCriteria arrayPlatformCriteria;

	private QueryHandler HANDLER;

	public QueryHandler getQueryHandler() throws Exception {
		return (HANDLER == null) ? new GeneExprQueryHandler() : HANDLER;
	}

	public QueryType getQueryType() throws Exception {
		return QueryType.GENE_EXPR_QUERY_TYPE;
	}

	public GeneExpressionQuery() {
		super();
	}

	public String toString() {
		ResourceBundle labels = null;
		String OutStr = "<B>Gene Expression Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>"
				+ this.getQueryName();

		try {

			labels = ResourceBundle.getBundle(
					RembrandtConstants.APPLICATION_RESOURCES, Locale.US);

			FoldChangeCriteria thisFoldChangeCrit = this.getFoldChgCrit();

			if ((thisFoldChangeCrit != null) && !thisFoldChangeCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisFoldChangeCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection foldChangeObjects = thisFoldChangeCrit
						.getFoldChangeObjects();
		
				for (Iterator iter = foldChangeObjects.iterator(); iter
						.hasNext();) {
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
						.debug("Fold Change Criteria is empty or Application Resources file is missing");
			}
			AllGenesCriteria thisAllGenesCrit = this.getAllGenesCrit();
			if (thisAllGenesCrit != null && !thisAllGenesCrit.isEmpty()) {
				OutStr += "<br /><b class='otherbold'>Gene</b><br />&nbsp;&nbsp;&nbsp;All Genes";
			} else
				logger.debug("This is not an All Genes Query");

			GeneIDCriteria thisGeneIDCrit = this.getGeneIDCrit();
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

			RegionCriteria thisRegionCrit = this.getRegionCrit();
			if ((thisRegionCrit != null) && !thisRegionCrit.isEmpty()
					&& labels != null) {
				String thisCriteria = thisRegionCrit.getClass().getName();
				logger.debug("thisCriteria is :" + thisCriteria);
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
			}

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
							+ "<BR> ";
				}
			} else {
				logger
						.debug("Disease Criteria is empty or Application Resources file is missing");
			} // end of DiseaseOrGradeCriteria

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
				while (iter.hasNext() && count < 5) {
					count++;
					CloneIdentifierDE cloneIdentifierDE = (CloneIdentifierDE) iter
							.next();
					String cloneStr = cloneIdentifierDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"
							+ labels.getString(cloneStr.substring(cloneStr
									.lastIndexOf(".") + 1)) + ": "
							+ cloneIdentifierDE.getValue() + "";
				}
				if (cloneColl != null && cloneColl.size() > 5) {
					OutStr += "<BR>&nbsp;&nbsp;...";
				}
			} else {
				logger
						.debug("Clone or Probe Criteria is empty or Application Resources file is missing.");
			}// end of cloneorProbeCriteria

			// starting of GeneOntologyCriteria
			GeneOntologyCriteria thisGeneOntologyCriteria = this
					.getGeneOntologyCriteria();
			if ((thisGeneOntologyCriteria != null)
					&& !thisGeneOntologyCriteria.isEmpty() && labels != null) {
				Collection goColl = thisGeneOntologyCriteria.getGOIdentifiers();
				Iterator iter = goColl.iterator();
				while (iter.hasNext()) {
					GeneOntologyDE geneOntologyDE = (GeneOntologyDE) iter
							.next();
					String goStr = geneOntologyDE.getClass().getName();
					OutStr += "<BR>"
							+ labels.getString(goStr.substring(goStr
									.lastIndexOf(".") + 1)) + ": "
							+ geneOntologyDE.getValue() + "";
				}
			} else {
				logger
						.debug("GeneOntolgoy Criteria is empty or Application Resources file is missing.");
			}// end of GeneOntologyCriteria

			// starting PathwayCriteria
			PathwayCriteria thisPathwayCriteria = this.getPathwayCriteria();

			if ((thisPathwayCriteria != null) && !thisPathwayCriteria.isEmpty()
					&& labels != null) {

				String thisCriteria = thisPathwayCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels.getString(thisCriteria.substring(thisCriteria
								.lastIndexOf(".") + 1)) + "</B>";
				Collection pathwayColl = thisPathwayCriteria.getPathwayNames();
				Iterator iter = pathwayColl.iterator();
				while (iter.hasNext()) {
					PathwayDE pathwayDE = (PathwayDE) iter.next();
					String pathwayStr = pathwayDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;" + pathwayDE.getValue();
				}
			} else {
				logger
						.debug("PathwayCriteria is empty or Application Resources file is missing");

			}

			/*
			 * TODO: Prashant Can you please fix this commented code. I have
			 * changed the PathwayCriteria class definition So the below code
			 * needs to be readjusted
			 */
			/*
			 * // start of pathway criteria PathwayCriteria thisPathwayCriteria =
			 * this.getPathwayCriteria(); if(!thisPathwayCriteria.isEmpty() &&
			 * labels != null){ PathwayDE pathwayDE =
			 * thisPathwayCriteria.getPathwayNames(); String pathwayStr =
			 * pathwayDE.getClass().getName(); OutStr += " <BR>
			 * "+labels.getString(pathwayStr.substring(pathwayStr.lastIndexOf(".")+1))+":
			 * "+pathwayDE.getValue()+""; } else{ logger.debug("PathwayCriteria
			 * is empty or Application Resources file is missing."); } // end of
			 * PathwayCriteria
			 */

			// start of ArrayPlatformCriteria
			ArrayPlatformCriteria thisArrayPlatformCriteria = this
					.getArrayPlatformCriteria();
			if ((thisArrayPlatformCriteria != null)
					&& !thisArrayPlatformCriteria.isEmpty() && labels != null) {
				ArrayPlatformDE arrayPlatformDE = thisArrayPlatformCriteria
						.getPlatform();
				String arrayPlatformStr = arrayPlatformDE.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
						+ labels
								.getString(arrayPlatformStr
										.substring(arrayPlatformStr
												.lastIndexOf(".") + 1))
						+ "</B>";
				OutStr += "<BR>&nbsp;&nbsp;" + arrayPlatformDE.getValue() + " ";
			} else {
				logger
						.debug("ArrayPlatformCriteria is empty or Application Resources file is missing.");
			} // end of PathwayCriteria
//			 start institution Criteria
	/*		InstitutionCriteria thisInstitutionCriteria = this.getInstitutionCriteria();
			if ((thisInstitutionCriteria != null)&& !thisInstitutionCriteria.isEmpty() && labels != null) {
				Collection institutionColl = thisInstitutionCriteria.getInstitutions();
				String thisCriteria = thisInstitutionCriteria.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"
					+ labels.getString(thisCriteria.substring(thisCriteria
							.lastIndexOf(".") + 1)) + "</B><BR>";
				Iterator iter = institutionColl.iterator();
				while (iter.hasNext()) {
					InstitutionDE institutionDE= (InstitutionDE) iter.next();
					OutStr += "" + ((String) institutionDE.getInstituteName())
							+ "<BR>";
				}
			
			}

			else {
				logger.debug("institution Criteria is empty or Application Resources file is missing.");
			}// end of institution Criteria
			
*/
		}// end of try
		catch (Exception ie) {
			logger.error("Error in ResourceBundle - ");
			logger.error(ie);
			ie.printStackTrace();
		}

		OutStr += "<BR><BR>";
		return OutStr;
	}

	public AllGenesCriteria getAllGenesCrit() {
		return allGenesCrit;
	}

	public void setAllGenesCrit(AllGenesCriteria allGenes) {
		this.allGenesCrit = allGenes;
	}

	public GeneIDCriteria getGeneIDCrit() {
		return geneIDCrit;
	}

	public void setGeneIDCrit(GeneIDCriteria geneIDCrit) {
		this.geneIDCrit = geneIDCrit;
	}

	public SampleCriteria getSampleIDCrit() {
		return sampleIDCrit;
	}

	public void setSampleIDCrit(SampleCriteria sampleIDCrit) {
		this.sampleIDCrit = sampleIDCrit;
	}

	public RegionCriteria getRegionCrit() {
		return regionCrit;
	}

	public void setRegionCrit(RegionCriteria regionCrit) {
		this.regionCrit = regionCrit;
	}

	public FoldChangeCriteria getFoldChgCrit() {
		return foldChgCrit;
	}

	public void setFoldChgCrit(FoldChangeCriteria foldChgCrit) {
		this.foldChgCrit = foldChgCrit;
	}

	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria() {
		return cloneOrProbeIDCriteria;
	}

	public void setCloneOrProbeIDCrit(
			CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
		this.cloneOrProbeIDCriteria = cloneOrProbeIDCriteria;
	}

	public GeneOntologyCriteria getGeneOntologyCriteria() {
		return geneOntologyCriteria;
	}

	public void setGeneOntologyCrit(GeneOntologyCriteria geneOntologyCriteria) {
		this.geneOntologyCriteria = geneOntologyCriteria;
	}

	public PathwayCriteria getPathwayCriteria() {
		return pathwayCriteria;
	}

	public void setPathwayCrit(PathwayCriteria pathwayCriteria) {
		this.pathwayCriteria = pathwayCriteria;
	}

	public ArrayPlatformCriteria getArrayPlatformCriteria() {
		return arrayPlatformCriteria;
	}

	public void setArrayPlatformCrit(ArrayPlatformCriteria arrayPlatformCriteria) {
		this.arrayPlatformCriteria = arrayPlatformCriteria;
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
		GeneExpressionQuery myClone = null;
		myClone = (GeneExpressionQuery) super.clone();
        if(allGenesCrit != null){
            myClone.allGenesCrit = (AllGenesCriteria) allGenesCrit.clone();
        }
        if(arrayPlatformCriteria != null){
		myClone.arrayPlatformCriteria = (ArrayPlatformCriteria) arrayPlatformCriteria
				.clone();
        }
        if(cloneOrProbeIDCriteria != null){
		myClone.cloneOrProbeIDCriteria = (CloneOrProbeIDCriteria) cloneOrProbeIDCriteria
				.clone();
        }
        if(foldChgCrit != null){
		myClone.foldChgCrit = (FoldChangeCriteria) foldChgCrit.clone();
        }
        if(geneIDCrit != null){
		myClone.geneIDCrit = (GeneIDCriteria) geneIDCrit.clone();
        }
        if(geneOntologyCriteria != null){
		myClone.geneOntologyCriteria = (GeneOntologyCriteria) geneOntologyCriteria
				.clone();
        }
        if(pathwayCriteria != null){
		myClone.pathwayCriteria = (PathwayCriteria) pathwayCriteria.clone();
        }
        if(regionCrit != null){
            myClone.regionCrit = (RegionCriteria) regionCrit.clone();
        }
		return myClone;
	}

	class Handler {
	}
}
