/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

/**
 * @author sahnih
 *
 */


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

public class StrategyHelper {
	/**
	 * 
	 * @param container --this is the container that you want the sample ids from
	 * @return --A collection of all the ResultsContainer sampleId Domain Elements
	 */
	@SuppressWarnings("unchecked")
	public static Collection<SampleIDDE> extractSampleIDDEs(ResultsContainer container)throws OperationNotSupportedException{
		Collection<SampleIDDE> sampleIds = new ArrayList<SampleIDDE>();
		SampleViewResultsContainer svrContainer = null;
		/*
		 * These are currently the only two results containers that we have to
		 * worry about at this time, I believe.
		 */
		if(container instanceof DimensionalViewContainer) {
			//Get the SampleViewResultsContainer from the DimensionalViewContainer
			DimensionalViewContainer dvContainer = (DimensionalViewContainer)container;
			svrContainer = dvContainer.getSampleViewResultsContainer();
		}else if(container instanceof SampleViewResultsContainer) {
			svrContainer = (SampleViewResultsContainer)container;
		}
		//Handle the SampleViewResultsContainers if that is what we got
		if(svrContainer!=null) {
			Collection bioSpecimen = svrContainer.getSampleResultsets();
			for(Iterator i = bioSpecimen.iterator();i.hasNext();) {
				SampleResultset sampleResultset =  (SampleResultset)i.next();
				sampleIds.add(sampleResultset.getSampleIDDE());
			}
		}else {
			throw new OperationNotSupportedException("We are not able to able to extract SampleIds from: "+container.getClass());
		}
		return sampleIds;
	}
	public static Collection<String> extractReportersFromGene(Collection<GeneIdentifierDE> geneDEs)throws OperationNotSupportedException{
		Collection<String> reporters = new ArrayList<String>();
		
		Resultant resultant;
		GeneIDCriteria geneIDCrit = new GeneIDCriteria();
        geneIDCrit.setGeneIdentifiers(geneDEs);
        GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneExpressionQuery.setQueryName("EXTRACT REPORTERS");
        geneExpressionQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneExpressionQuery.setGeneIDCrit(geneIDCrit);
		try {
			resultant = ResultsetManager.executeGeneExpressPlotQuery(geneExpressionQuery);
		} catch (Exception e) {
			throw new OperationNotSupportedException( "We are not able to extract Reporters "+e.getMessage());
		}
		if (resultant!= null){
			reporters = extractReporters(resultant.getResultsContainer());
		}
		return reporters;
	}
	public static Collection<String> extractReporters(ResultsContainer container)throws OperationNotSupportedException{
		Collection<String> reporters = new ArrayList<String>();
		DimensionalViewContainer dvContainer = null;
		/*
		 * These are currently the only two results containers that we have to
		 * worry about at this time, I believe.
		 */
		if(container instanceof DimensionalViewContainer) {
			//Get the DimensionalViewContainer
			dvContainer = (DimensionalViewContainer)container;
	
			if(dvContainer!=null) {
				GeneExprSingleViewResultsContainer gesvContainer = (GeneExprSingleViewResultsContainer) dvContainer.getGeneExprSingleViewContainer();
				List reporterList =gesvContainer.getAllReporterNames();
				for(Iterator i = reporterList.iterator();i.hasNext();) {
					reporters.add((String)i.next());
				}
			}else {
				throw new OperationNotSupportedException("We are not able to able to extract Reporters from: "+container.getClass());
			}
		}
		return reporters;
	}
	public static Collection<String> extractReporters(Collection<CloneIdentifierDE> reporterDEs)throws OperationNotSupportedException{
		Collection<String> reporters = new ArrayList<String>();
		for(CloneIdentifierDE reporter: reporterDEs){
			reporters.add(reporter.getValueObject());
		}
		return reporters;
	}
	public static Collection<String> extractSamples(Collection<SampleIDDE> sampleDEs)throws OperationNotSupportedException{
		Collection<String> samples = new ArrayList<String>();
		for(SampleIDDE reporter: sampleDEs){
			samples.add(reporter.getValueObject());
		}
		return samples;
	}
	public static Collection<String> extractSamples(ResultsContainer container)throws OperationNotSupportedException{
		return extractSamples(extractSampleIDDEs( container));
	}
	public static Collection<String> extractGenes(Collection<GeneIdentifierDE> geneDEs)throws OperationNotSupportedException{
		Collection<String> genes = new ArrayList<String>();
		for(GeneIdentifierDE reporter: geneDEs){
			genes.add(reporter.getValueObject());
		}
		return genes;
	}
	public static Collection<SampleIDDE> convertToSampleIDDEs(Collection<String> sampleIDs)throws OperationNotSupportedException{
		Collection<SampleIDDE> samplesDEs = new ArrayList<SampleIDDE>();
		if(sampleIDs != null){
			for(String sampleID: sampleIDs){
				samplesDEs.add(new SampleIDDE(sampleID));
			}
		}
		return samplesDEs;
	}
}
