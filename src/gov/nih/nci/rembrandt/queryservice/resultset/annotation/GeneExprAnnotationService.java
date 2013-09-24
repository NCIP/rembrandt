/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset.annotation;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class GeneExprAnnotationService {
	private static Logger logger = Logger.getLogger(ClinicalDataValidator.class);	

	public static List<GeneResultset> getAnnotationsForGeneSymbols(List<String> geneSymbols) throws Exception{
		List<GeneIdentifierDE.GeneSymbol> geneIdentifierDEs = new ArrayList<GeneIdentifierDE.GeneSymbol>();
		if(geneSymbols != null){
			for(String geneSymbol:geneSymbols){
				geneIdentifierDEs.add(new GeneIdentifierDE.GeneSymbol(geneSymbol));
			}
			return getAnnotationsForGeneIdentifierDE(geneIdentifierDEs);
		}
		return null;
	}

	public static List<GeneResultset> getAnnotationsForGeneIdentifierDE(List<GeneIdentifierDE.GeneSymbol> genes) throws Exception{
		GeneExpressionQuery geneExpressionQuery = createQuery();
		List<GeneResultset> geneResultsetOrderedList = new ArrayList<GeneResultset>();
		 if(geneExpressionQuery != null  && genes != null){
			 GeneIDCriteria geneIDCriteria = new GeneIDCriteria();
			 geneIDCriteria.setGeneIdentifiers(genes);
			 geneExpressionQuery.setGeneIDCrit(geneIDCriteria);
			 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
			  if(geneExprResultsContainer!=null) {
					for(GeneIdentifierDE geneID: genes){
						String geneSymbol ;
						geneSymbol = geneID.getValueObject();
						geneResultsetOrderedList.add(geneExprResultsContainer.getGeneResultset(geneSymbol));
					}
					return geneResultsetOrderedList;
				}
		 }
		return null;
	}
	public static List<ReporterResultset> getAnnotationsForReporters(List<String> reporterIDs) throws Exception{
		List<CloneIdentifierDE> reporterDEs = new ArrayList<CloneIdentifierDE>();
		if( reporterIDs != null){
				for(String reporterID:reporterIDs){
					if(reporterID != null  && reporterID.indexOf("IMAGE")>0){
						reporterDEs.add(new CloneIdentifierDE.IMAGEClone(reporterID));
					}
					else{
						reporterDEs.add(new CloneIdentifierDE.ProbesetID(reporterID));
					}
				}
		}
		return getAnnotationsForCloneIdentifierDEs(reporterDEs);
	}
	public static Map <String,ReporterResultset> getAnnotationsMapForReporters(List<String> reporterIDs) throws Exception{
		List<CloneIdentifierDE> reporterDEs = new ArrayList<CloneIdentifierDE>();
		if( reporterIDs != null){
				for(String reporterID:reporterIDs){
					if(reporterID != null  && reporterID.indexOf("IMAGE")>0){
						reporterDEs.add(new CloneIdentifierDE.IMAGEClone(reporterID));
					}
					else{
						reporterDEs.add(new CloneIdentifierDE.ProbesetID(reporterID));
					}
				}
		}
		return getAnnotationsMapForCloneIdentifierDEs(reporterDEs);
	}
	public static Map <String,ReporterResultset> getAnnotationsMapForCloneIdentifierDEs(List<CloneIdentifierDE> reporters) throws Exception{
		Map <String,ReporterResultset> reporterResultsetMap= new HashMap<String,ReporterResultset>();
		GeneExpressionQuery geneExpressionQuery = createQuery();
		List<ReporterResultset> reporterResultsetOrderedList = new ArrayList<ReporterResultset>();

		 if(geneExpressionQuery != null  && reporters != null){
			 CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
			 reporterCriteria.setIdentifiers(reporters);
			 geneExpressionQuery.setCloneOrProbeIDCrit(reporterCriteria);
			 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
			  if(geneExprResultsContainer!=null) {
					for(CloneIdentifierDE reporter: reporters){
						String reporterID = reporter.getValueObject();
						reporterResultsetMap.put(reporterID,geneExprResultsContainer.getReporterResultset(reporterID));
					}
					return reporterResultsetMap;
				}
		 }
		 return null;
	}
	public static List<ReporterResultset> getAnnotationsForCloneIdentifierDEs(List<CloneIdentifierDE> reporters) throws Exception{
			GeneExpressionQuery geneExpressionQuery = createQuery();
			List<ReporterResultset> reporterResultsetOrderedList = new ArrayList<ReporterResultset>();
	
			 if(geneExpressionQuery != null  && reporters != null){
				 CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
				 reporterCriteria.setIdentifiers(reporters);
				 geneExpressionQuery.setCloneOrProbeIDCrit(reporterCriteria);
				 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
				  if(geneExprResultsContainer!=null) {
						for(CloneIdentifierDE reporter: reporters){
							String reporterID = reporter.getValueObject();
							reporterResultsetOrderedList.add(geneExprResultsContainer.getReporterResultset(reporterID));
						}
						return reporterResultsetOrderedList;
					}
			 }
		return null;
	}
	
	public static ReporterResultset getAnnotationsForReporter(String reporterID) throws Exception{
		if(reporterID != null){
			CloneIdentifierDE cloneIdentifierDE = null ;
			if(reporterID != null  && reporterID.indexOf("IMAGE")>0){
				cloneIdentifierDE = new CloneIdentifierDE.IMAGEClone(reporterID);
			}
			else{
				cloneIdentifierDE = new CloneIdentifierDE.ProbesetID(reporterID);
			}
		return getAnnotationsForCloneIdentifierDE(cloneIdentifierDE);
		}
		return null;
	}
	public static ReporterResultset getAnnotationsForCloneIdentifierDE(CloneIdentifierDE reporter) throws Exception{
		GeneExpressionQuery geneExpressionQuery = createQuery();
		//List<ReporterResultset> reporterResultsetOrderedList = new ArrayList<ReporterResultset>();

		 if(geneExpressionQuery != null  && reporter != null ){
			 CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
			 reporterCriteria.setCloneIdentifier(reporter);
			 geneExpressionQuery.setCloneOrProbeIDCrit(reporterCriteria);
			 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
			  if(geneExprResultsContainer!=null) {
						return geneExprResultsContainer.getReporterResultset(reporter.getValueObject());

				}
		 }
		return null;
	}
	private static GeneExpressionQuery createQuery(){

//		create a GeneExpressionQuery to contrain by GeneIds and Disease 
		 GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
		 geneExpressionQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
		 ArrayPlatformCriteria arrayPlatformCrit = new ArrayPlatformCriteria();
		 arrayPlatformCrit.setPlatform(new ArrayPlatformDE(Constants.ALL_PLATFROM));
		 geneExpressionQuery.setArrayPlatformCrit(arrayPlatformCrit);
		 //DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		 //diseaseCrit.setDisease(new DiseaseNameDE(RembrandtConstants.ALL));
		 SampleCriteria sampleCrit = new SampleCriteria();
		 sampleCrit.setSampleID(new SampleIDDE("HF1220"));
		 geneExpressionQuery.setSampleIDCrit(sampleCrit);
		 return geneExpressionQuery;
	}
	private static GeneExprResultsContainer executeGeneExprSampleQuery(GeneExpressionQuery geneExpressionQuery) throws Exception{
		Resultant resultant = null;
		GeneExprSingleViewResultsContainer geneExprSingleViewResultsContainer = null;
		try {
			CompoundQuery compoundQuery = new CompoundQuery(geneExpressionQuery);
			resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
 		}
 		catch (Throwable t)	{
 			logger.error("Error Executing the query/n"+ t.getMessage());
 			throw new Exception("Error executing geneExpressionQuery/n"+t.getMessage());
 		}
  		if(resultant != null) {      

  			if(resultant.getResultsContainer() instanceof DimensionalViewContainer) {
  				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer)resultant.getResultsContainer();
  				geneExprSingleViewResultsContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
  			}
	 	}
  		return geneExprSingleViewResultsContainer;

	}
}
