/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing;

import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr;
import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.CompoundResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;



/**
 * @author SahniH
 * Date: Oct 15, 2004
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

public class CompoundQueryProcessor {
	private static Logger logger = Logger.getLogger(CompoundQueryProcessor.class);
	
    public static CompoundResultSet execute(CompoundQuery compoundQuery) throws Exception{
		ResultSet[] resultSets = null;
		CompoundResultSet compoundResultet= null;
		Set sampleIds = new HashSet();
		Collection results = new Vector();
		if(compoundQuery != null){
			Queriable leftQuery = compoundQuery.getLeftQuery();
			Queriable rightQuery = compoundQuery.getRightQuery();
			OperatorType operator = compoundQuery.getOperatorType();
			Viewable view = compoundQuery.getAssociatedView();
			CompoundResultSet leftResultSets = null;
			CompoundResultSet rightResultSets = null;
				if (leftQuery != null) {
					if (leftQuery instanceof CompoundQuery) {
						leftQuery.setAssociatedView(view);
						leftResultSets = execute((CompoundQuery)leftQuery);
						results.addAll(retriveResultSetsFromCompoundResultset(leftResultSets));
					}else if (leftQuery instanceof Query){
						leftQuery.setAssociatedView(view);						
						leftResultSets = createCompoundResultset((ResultSet[])QueryManager.executeQuery((Query)leftQuery));
						results.add(leftResultSets.getResults().toArray());
					}
					
				}
				
				if (rightQuery != null) {
					if (rightQuery instanceof CompoundQuery) {
						rightQuery.setAssociatedView(view);
						rightResultSets = execute((CompoundQuery)rightQuery);
						results.addAll(retriveResultSetsFromCompoundResultset(rightResultSets));
						}
					else if (rightQuery instanceof Query){
						rightQuery.setAssociatedView(view);
						rightResultSets = createCompoundResultset((ResultSet[]) QueryManager.executeQuery((Query)rightQuery));
						
						results.add(rightResultSets.getResults().toArray());
					}
					
				}
	
			if (operator != null) {
					if(operator.equals(OperatorType.AND)){
						sampleIds.addAll(resultSetInterSection(leftResultSets,rightResultSets));
					}
					else if(operator.equals(OperatorType.OR)){
						sampleIds.addAll(resultSetUnion(leftResultSets,rightResultSets));
					}
					else if(operator.equals(OperatorType.NOT)){
						sampleIds.addAll(resultSetDifference(leftResultSets,rightResultSets));
					}
					else if(operator.equals(OperatorType.PROJECT_RESULTS_BY)){
						sampleIds.addAll(resultSetProjectResultsBy(leftResultSets,rightResultSets));
					}
				compoundResultet = processCompoundQuery(results,sampleIds);					
			}else{ //then its the right query				
				compoundResultet = processCompoundQuery(results,rightResultSets.getSampleIds());	//rightResultSets;	
				
			}
				
		}
		return compoundResultet;	
	}
    private static CompoundResultSet createCompoundResultset(ResultSet[]results){
    	Set ids = new HashSet();
    	if(results instanceof ResultSet[]){
    		ResultSet[] resultsets = (ResultSet[])results;
    		if(resultsets instanceof ClinicalResultSet[]){
	    		for(int i = 0; i < resultsets.length; i++){
	    			ClinicalResultSet clinicalResultset = (ClinicalResultSet) resultsets[i];
	    			ids.add(clinicalResultset.getBiospecimenId());
	    			
	    		}
	    	}else if(resultsets instanceof GeneExpr.GeneExprGroup[]){
	    		for(int i = 0; i < resultsets.length; i++){
	    			GeneExpr.GeneExprGroup groupResultset = (GeneExpr.GeneExprGroup) resultsets[i];
	    			ids.add(groupResultset.getDiseaseTypeId());
	    		}
	    	}
    		
	    		
	    	
    	}
    	return new CompoundResultSet(Arrays.asList(results),ids);
    }
	private static boolean isGroupResultsets(CompoundResultSet leftResultSets,CompoundResultSet rightResultSets){
		boolean isGroup = false;
		if(leftResultSets != null && rightResultSets != null &&
			leftResultSets.getResults() != null && rightResultSets.getResults() != null
			&& leftResultSets.getResults().toArray() instanceof GeneExpr.GeneExprGroup[] 
			&& rightResultSets.getResults().toArray() instanceof GeneExpr.GeneExprGroup[] ){
			isGroup = true;
		}
		return isGroup;
	}
	private static Collection retriveResultSetsFromCompoundResultset(CompoundResultSet compoundResultSet){
		Collection resultSetCollection = new Vector();
		Collection results = compoundResultSet.getResults();
		for (Iterator resultsIterator = results.iterator(); resultsIterator.hasNext();) {
			Object resultObj = resultsIterator.next();
			if(resultObj instanceof ResultSet[]){
				resultSetCollection.add(resultObj);
			}
		}
		return resultSetCollection;
	}
	
	/**
	 * @param results
	 * @param sampleIds
	 * @return
	 */
	private static CompoundResultSet processCompoundQuery(Collection results, Set sampleIds) {
		Collection finalResultsets = new Vector();
		for (Iterator resultsIterator = results.iterator(); resultsIterator.hasNext();) {
			Set resultset = new HashSet();
			Object obj = resultsIterator.next();
			if(obj instanceof ResultSet[]){
	    		ResultSet[] resultsets = (ResultSet[])obj;
	    		if(resultsets instanceof ClinicalResultSet[]){
		    		for(int i = 0; i < resultsets.length; i++){
		    			ClinicalResultSet clinicalResultset = (ClinicalResultSet) resultsets[i];
		    			/*
		    			 * It was necesary to put this null check here because for some unknown reason,
		    			 * the resultsSets[] can have many null values.
		    			 */
		    			if(clinicalResultset!=null) {
			    			for(Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();){
			    				Long id = (Long)sampleIdIterator.next();
					    		if(clinicalResultset.getBiospecimenId() != null && clinicalResultset.getBiospecimenId().equals(id)){
					    			resultset.add(clinicalResultset);
					    		}
			    			}
		    			}
		    		}
		    		if(resultsets instanceof  GeneExpr.GeneExprSingle[]){
		    			if(resultsets.length > 0){
		    				finalResultsets.add(resultset.toArray(new GeneExpr.GeneExprSingle[resultsets.length]));
		    			}
		    		}
		    		else if(resultsets instanceof  UnifiedGeneExpr.UnifiedGeneExprSingle[]){
		    			if(resultsets.length > 0){
		    				finalResultsets.add(resultset.toArray(new UnifiedGeneExpr.UnifiedGeneExprSingle[resultsets.length]));
		    			}
		    		}
		    		else if(resultsets instanceof  CopyNumber[]){
		    			if(resultsets.length > 0){
		    				finalResultsets.add(resultset.toArray(new CopyNumber[resultsets.length]));
		    			}
		    		}
		    		else if(resultsets instanceof  PatientData[]){
		    			if(resultsets.length > 0){
		    				finalResultsets.add(resultset.toArray(new PatientData[resultsets.length]));
		    			}
		    		}
	    		}else if(resultsets instanceof GeneExpr.GeneExprGroup[]){
	    			/*
		    		for(int i = 0; i < resultsets.length; i++){
		    			GeneExpr.GeneExprGroup groupResultset = (GeneExpr.GeneExprGroup) resultsets[i];
		    			for(Iterator groupIterator = sampleIds.iterator(); groupIterator.hasNext();){
		    				Long id = (Long)groupIterator.next();
				    		if(groupResultset.getDiseaseTypeId().equals(id)){
				    			resultset.add(groupResultset);
				    		}
		    			}
		    		}
		    		*/
		    		if(resultsets.length > 0){
		    			finalResultsets.add(resultsets); //.toArray(new GeneExpr.GeneExprGroup[resultsets.length]));
		    		}
		    	}
			}
		}
		return new CompoundResultSet(finalResultsets,sampleIds);
	}
	/**
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return
	 */
	private static Set resultSetGroupProjectResultsBy(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * ResultSetDifference (minus): set of every element that is in the leftResultset but not in the rightResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @param sampleIds
	 * @return resultSet[] which is set of every element that is in the leftResultset but not in the rightResultset
	 */
	private static Set resultSetGroupDifference(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		Set diffset  = new HashSet();
		GeneExpr.GeneExprGroup[] results = null;
		if(leftResultSets != null && rightResultSets != null &&
			leftResultSets.getResults() != null && rightResultSets.getResults() != null){
			 results = (GeneExpr.GeneExprGroup[]) leftResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[leftResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				leftResults.add(groupResultSet.getDiseaseTypeId());
			}
			results =  (GeneExpr.GeneExprGroup[]) rightResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[rightResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				rightResults.add(groupResultSet.getDiseaseTypeId());
			}
			//the removeAll operation effectively modifies this set so that its value is the asymmetric set difference of the two sets.
			logger.debug("L:"+leftResults.size());			
			logger.debug("R:"+rightResults.size());
			diffset = new HashSet(leftResults);
			diffset.removeAll(rightResults);
			
		}
		return diffset;
	}
	/**
	 * ResultSetUnion: a resultSet containing every element that is in either the leftResultSet or the rightResultSet, or both
	 * (must be union-compatible: same number of objects and corresponding objects have identical )
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in either the leftResultSet or the rightResultSet, or both
	 */
	private static Set resultSetGroupUnion(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		Set unionSet = new HashSet();
		GeneExpr.GeneExprGroup[] results = null;
		if(leftResultSets != null && rightResultSets != null &&
			leftResultSets.getResults() != null && rightResultSets.getResults() != null){
			 results = (GeneExpr.GeneExprGroup[]) leftResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[leftResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				leftResults.add(groupResultSet.getDiseaseTypeId());
			}
			results =  (GeneExpr.GeneExprGroup[]) rightResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[rightResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				rightResults.add(groupResultSet.getDiseaseTypeId());
			}
			//the addAll operation effectively modifies this set so that its value is the union of the two sets.
			unionSet = new HashSet(leftResults);
			logger.debug("L:"+leftResults.size());
			logger.debug("R:"+rightResults.size());
			unionSet.addAll(rightResults);
			logger.debug("Union:"+unionSet.size());	
			
		}
		return unionSet;
	}
	/**
	 * ResultSetIntersection (intersect): a resultset containing every element that is in both rightResultSet and LeftResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in both rightResultSet and LeftResultset
	 */
	private static Set resultSetGroupInterSection(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		GeneExpr.GeneExprGroup[] results = null;
		Set interSectSet = new HashSet();
		if(leftResultSets != null && rightResultSets != null &&
			leftResultSets.getResults() != null && rightResultSets.getResults() != null){
			 results = (GeneExpr.GeneExprGroup[]) leftResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[leftResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				leftResults.add(groupResultSet.getDiseaseTypeId());
			}
			results =  (GeneExpr.GeneExprGroup[]) rightResultSets.getResults().toArray(new GeneExpr.GeneExprGroup[rightResultSets.getResults().size()]);
			for(int i =0;i < results.length;i++){
				GeneExpr.GeneExprGroup groupResultSet = (GeneExpr.GeneExprGroup) results[i];
				rightResults.add(groupResultSet.getDiseaseTypeId());
			}
			//this operation effectively modifies this set so that its value is the intersection of the two sets.
			interSectSet = new HashSet(leftResults);
			logger.debug("L:"+leftResults.size());
			logger.debug("R:"+rightResults.size());
			interSectSet.retainAll(rightResults);
			
	    	
		}
		return interSectSet; //(ResultSetInterface[])finalResults.toArray(new ResultSetInterface[1]);
	}
	/**
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return
	 */
	private static Set resultSetProjectResultsBy(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * ResultSetDifference (minus): set of every element that is in the leftResultset but not in the rightResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @param sampleIds
	 * @return resultSet[] which is set of every element that is in the leftResultset but not in the rightResultset
	 */
	private static Set resultSetDifference(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		ClinicalResultSet[] results = null;
		Set diffset = new HashSet();
		leftResults.addAll(leftResultSets.getSampleIds());
		rightResults.addAll(rightResultSets.getSampleIds());

		//the removeAll operation effectively modifies this set so that its value is the asymmetric set difference of the two sets.
		logger.debug("L:"+leftResults.size());			
		logger.debug("R:"+rightResults.size());
		diffset = new HashSet(leftResults);
		diffset.removeAll(rightResults);
			
		return diffset;
	}
	/**
	 * ResultSetUnion: a resultSet containing every element that is in either the leftResultSet or the rightResultSet, or both
	 * (must be union-compatible: same number of objects and corresponding objects have identical )
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in either the leftResultSet or the rightResultSet, or both
	 */
	private static Set resultSetUnion(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		ClinicalResultSet[] results = null;
		Set unionSet = new HashSet();
		leftResults.addAll(leftResultSets.getSampleIds());
		rightResults.addAll(rightResultSets.getSampleIds());
		
		//the addAll operation effectively modifies this set so that its value is the union of the two sets.
		unionSet = new HashSet(leftResults);
		logger.debug("L:"+leftResults.size());
		logger.debug("R:"+rightResults.size());
		unionSet.addAll(rightResults);
		logger.debug("Union:"+unionSet.size());	
		return unionSet;
	}
	/**
	 * ResultSetIntersection (intersect): a resultset containing every element that is in both rightResultSet and LeftResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in both rightResultSet and LeftResultset
	 */
	private static Set resultSetInterSection(CompoundResultSet leftResultSets, CompoundResultSet rightResultSets) {
		Collection leftResults = new HashSet();
		Collection rightResults = new HashSet();
		ClinicalResultSet[] results = null;
		Set interSectSet = new HashSet();
		leftResults.addAll(leftResultSets.getSampleIds());
		rightResults.addAll(rightResultSets.getSampleIds());
		
		//this operation effectively modifies this set so that its value is the intersection of the two sets.
		interSectSet = new HashSet(leftResults);
		logger.debug("L:"+leftResults.size());
		logger.debug("R:"+rightResults.size());
		interSectSet.retainAll(rightResults);
		logger.debug("InterSect:"+interSectSet.size());	
		return interSectSet; 
	}
	public static Integer getMaxCount(CompoundQuery compoundQuery) throws Exception{
			Integer maxCount = 0;
			Collection<Integer> results = new Vector<Integer>();
			if(compoundQuery != null){
				Queriable leftQuery = compoundQuery.getLeftQuery();
				Queriable rightQuery = compoundQuery.getRightQuery();
				OperatorType operator = compoundQuery.getOperatorType();
				Viewable view = compoundQuery.getAssociatedView();
				CompoundResultSet leftResultSets = null;
				CompoundResultSet rightResultSets = null;
					if (leftQuery != null) {
						if (leftQuery instanceof CompoundQuery) {
							leftQuery.setAssociatedView(view);
							Integer leftCount = getMaxCount((CompoundQuery)leftQuery);
							results.add(leftCount);
						}else if (leftQuery instanceof Query){
							leftQuery.setAssociatedView(view);						
							Integer leftCount = QueryManager.getCount((Query)leftQuery);
							results.add(leftCount);
						}
						
					}
					
					if (rightQuery != null) {
						if (rightQuery instanceof CompoundQuery) {
							rightQuery.setAssociatedView(view);
							Integer rightCount = getMaxCount((CompoundQuery)rightQuery);
							results.add(rightCount);
							}
						else if (rightQuery instanceof Query){
							rightQuery.setAssociatedView(view);
							Integer rightCount = QueryManager.getCount((Query)rightQuery);
							
							results.add(rightCount);
						}
						
					}
			}
			for(Integer count: results){
				if(count > maxCount){
					maxCount = count; //get the largestCount
				}
			}
			return maxCount;
	}
}
