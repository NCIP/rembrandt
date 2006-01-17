/*
 * @author: SahniH Created on Oct 31, 2004
 * 
 * @version $ Revision: 1.0 $
 * 
 * The caBIO Software License, Version 1.0
 * 
 * Copyright 2004 SAIC. This software was developed in conjunction with the
 * National Cancer Institute, and so to the extent government employees are
 * co-authors, any rights in such works shall be subject to Title 17 of the
 * United States Code, section 105.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the disclaimer of Article 3, below.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 2. The end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment:
 * 
 * "This product includes software developed by the SAIC and the National Cancer
 * Institute."
 * 
 * If no such end-user documentation is to be included, this acknowledgment
 * shall appear in the software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be
 * used to endorse or promote products derived from this software.
 * 
 * 4. This license does not authorize the incorporation of this software into
 * any proprietary programs. This license does not authorize the recipient to
 * use any trademarks owned by either NCI or SAIC-Frederick.
 * 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *  
 */
package gov.nih.nci.rembrandt.queryservice;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.dto.view.GroupType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.dto.query.UnifiedGeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprGroup;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr.UnifiedGeneExprGroup;
import gov.nih.nci.rembrandt.queryservice.resultset.AddConstrainsToQueriesHelper;
import gov.nih.nci.rembrandt.queryservice.resultset.CompoundResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.ShowAllValuesHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.filter.CopyNumberFilter;
import gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot.KaplanMeierPlotHandler;

import java.util.Collection;
import java.util.Iterator;



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

public class ResultsetManager {
	public static Resultant executeCompoundQuery(CompoundQuery queryToExecute)
			throws Exception {
		Resultant resultant = null;
		if (queryToExecute != null) {
			resultant = new Resultant();
			Viewable associatedView = queryToExecute.getAssociatedView();
			Thread.sleep(5);//To make sure all threads are closed
			CompoundResultSet compoundResultSet = QueryManager
					.executeCompoundQuery(queryToExecute);
			Collection results = compoundResultSet.getResults();
			if (results != null) {
				for (Iterator resultsIterator = results.iterator(); resultsIterator
						.hasNext();) {
					Object obj = resultsIterator.next();
					if (obj instanceof ResultSet[]) {
						ResultSet[] resultsets = (ResultSet[]) obj;
						if (resultsets instanceof GeneExprSingle[]) {
							GroupType groupType = GroupType.DISEASE_TYPE_GROUP;
							if (associatedView instanceof GeneExprSampleView) {
								GeneExprSampleView geneExprSampleView = (GeneExprSampleView) associatedView;
								groupType = geneExprSampleView.getGroupType();
							}
							ResultsContainer resultsContainer = ResultsetProcessor
									.handleGeneExprSingleView(resultant,
											(GeneExprSingle[]) resultsets,
											groupType);
							resultant.setResultsContainer(resultsContainer);
							resultant.setAssociatedQuery(queryToExecute);
							resultant.setAssociatedView(associatedView);
						} else if (resultsets instanceof GeneExprGroup[]) {
							ResultsContainer resultsContainer = ResultsetProcessor
									.handleGeneExprDiseaseView(resultant,
											(GeneExprGroup[]) resultsets);
							resultant.setResultsContainer(resultsContainer);
							resultant.setAssociatedQuery(queryToExecute);
							resultant.setAssociatedView(associatedView);
						} else if (resultsets instanceof CopyNumber[]) {
							GroupType groupType = GroupType.DISEASE_TYPE_GROUP;
							if (associatedView instanceof CopyNumberSampleView) {
								CopyNumberSampleView copyNumberView = (CopyNumberSampleView) associatedView;
								groupType = copyNumberView.getGroupType();
							}
							ResultsContainer resultsContainer = ResultsetProcessor
									.handleCopyNumberSingleView(resultant,
											(CopyNumber[]) resultsets,
											groupType);
							resultant.setResultsContainer(resultsContainer);
							resultant.setAssociatedQuery(queryToExecute);
							resultant.setAssociatedView(associatedView);
						} else if (resultsets instanceof PatientData[]) {
							ResultsContainer resultsContainer = ResultsetProcessor
									.handleClinicalSampleView(resultant,
											(PatientData[]) resultsets);
							resultant.setResultsContainer(resultsContainer);
							resultant.setAssociatedQuery(queryToExecute);
							resultant.setAssociatedView(associatedView);
						}
					}
				}
			}
		}
		if(resultant.getResultsContainer()==null) {
			resultant = null;
		}
		return resultant;
	}

	public static Resultant executeGeneExpressPlotQuery(Query geneQuery)
			throws Exception {

		//ExecuteQuery
		Resultant resultant = new Resultant();
		ResultsContainer resultsContainer = null;
		if (geneQuery != null){
			ResultSet[] resultsets = QueryManager.executeQuery(geneQuery);
			if(resultsets instanceof GeneExprGroup[]){
				 resultsContainer = ResultsetProcessor
					.handleGeneExpressPlot((GeneExprGroup[]) resultsets);
			}
			else if(resultsets instanceof UnifiedGeneExprGroup[]){
				 resultsContainer = ResultsetProcessor
					.handleUnifiedGeneExpressPlot((UnifiedGeneExprGroup[]) resultsets);
			}
			resultant.setResultsContainer(resultsContainer);
			resultant.setAssociatedQuery(geneQuery);
			resultant.setAssociatedView(geneQuery.getAssociatedView());
			
		}
		if(resultant.getResultsContainer()==null) {
			resultant = null;
		}
		return resultant;
	}

	public static Resultant executeKaplanMeierPlotQuery(Queriable queryToExecute)
			throws Exception {
		Resultant resultant = new Resultant();
		if (queryToExecute != null) {
			if (queryToExecute instanceof GeneExpressionQuery) {
				Viewable associatedView = ViewFactory
						.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW);
				queryToExecute.setAssociatedView(associatedView);
				ResultSet[] resultsets = QueryManager
						.executeQuery(queryToExecute);
				ResultsContainer resultsContainer = KaplanMeierPlotHandler
						.handleKMGeneExprPlotContainer((GeneExpr.GeneExprSingle[]) resultsets);
				resultant.setResultsContainer(resultsContainer);
				resultant.setAssociatedQuery(queryToExecute);
				resultant.setAssociatedView(associatedView);
			}else	if (queryToExecute instanceof UnifiedGeneExpressionQuery) {
					Viewable associatedView = ViewFactory
							.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW);
					queryToExecute.setAssociatedView(associatedView);
					ResultSet[] resultsets = QueryManager
							.executeQuery(queryToExecute);
					ResultsContainer resultsContainer = KaplanMeierPlotHandler
							.handleKMUnifiedGeneExprPlotContainer((UnifiedGeneExpr.UnifiedGeneExprSingle[]) resultsets);
					resultant.setResultsContainer(resultsContainer);
					resultant.setAssociatedQuery(queryToExecute);
					resultant.setAssociatedView(associatedView);
			} else if (queryToExecute instanceof ComparativeGenomicQuery) {
				Viewable associatedView = ViewFactory
						.newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW);
				queryToExecute.setAssociatedView(associatedView);
				ResultSet[] resultsets = QueryManager
						.executeQuery(queryToExecute);
				ResultsContainer resultsContainer = KaplanMeierPlotHandler
						.handleKMCopyNumberPlotContainer((CopyNumber[]) resultsets);
				resultant.setResultsContainer(resultsContainer);
				resultant.setAssociatedQuery(queryToExecute);
				resultant.setAssociatedView(associatedView);
			}
		}
		if(resultant.getResultsContainer()==null) {
			resultant = null;
		}
		return resultant;
	}

	public static Resultant executeShowAllQuery(Resultant resultant)
			throws Exception {

		ShowAllValuesHandler showAllValuesHandler = new ShowAllValuesHandler(
				resultant);

		return showAllValuesHandler.getShowAllValuesResultant();
	}
	
	public static Resultant executeCompoundQuery(CompoundQuery queryToExecute, String[] sampleIDs)
	throws Exception {
		Resultant resultant = null;
		if (queryToExecute != null && sampleIDs != null) {
			AddConstrainsToQueriesHelper sampleHandler = new AddConstrainsToQueriesHelper();
			SampleCriteria sampleCriteria = sampleHandler.createSampleCriteria(sampleIDs);
			CompoundQuery newCQuery = sampleHandler.constrainQueryWithSamples(queryToExecute,sampleCriteria);
			resultant = executeCompoundQuery(newCQuery);
		}

	
	return resultant;
	}
	public static Collection getSampleIdsforCopyNumberFilter(Resultant resultant, Integer consecutiveCalls, Integer percentCalls, OperatorType operator){
        
        return CopyNumberFilter.filterCopyNumber(resultant, consecutiveCalls, percentCalls, operator);
    }
	
	public static Resultant filterCopyNumber(Resultant resultant, Integer consecutiveCalls, Integer percentCalls, OperatorType operator) throws Exception{
        Collection sampleCollection = getSampleIdsforCopyNumberFilter(resultant, consecutiveCalls, percentCalls, operator);
        if(sampleCollection!= null  && sampleCollection.size() > 0){
        	String[] sampleIds = (String[]) sampleCollection.toArray(new String[sampleCollection.size()]);
        	if(resultant.getAssociatedQuery() instanceof CompoundQuery){
        	CompoundQuery compoundQuery = (CompoundQuery) resultant.getAssociatedQuery();
        	CompoundQuery newCompoundQuery =  (CompoundQuery) compoundQuery.clone();
        	return executeCompoundQuery(newCompoundQuery,sampleIds);
        	}
  
        }
        return null;
    }

}
