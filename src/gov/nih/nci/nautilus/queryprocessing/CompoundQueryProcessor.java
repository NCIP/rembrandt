/*
 *  @author: SahniH
 *  Created on Oct 15, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.OperatorType;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.resultset.ResultSet;

import java.util.*;


/**
 * @author SahniH
 * Date: Oct 15, 2004
 * 
 */
public class CompoundQueryProcessor {
	public static ResultSet[] execute(CompoundQuery compoundQuery){
		ResultSet[] resultSets = null;
		if(compoundQuery != null){
			Queriable leftQuery = compoundQuery.getLeftQuery();
			Queriable rightQuery = compoundQuery.getRightQuery();
			OperatorType operator = compoundQuery.getOperatorType();
			ResultSet[] leftResultSets = null;
			ResultSet[] rightResultSets = null;
			try {
				if (leftQuery != null) {
					if (leftQuery instanceof CompoundQuery) {
						leftResultSets = execute((CompoundQuery)leftQuery);
						}
					else if (leftQuery instanceof Query){
						leftResultSets = QueryManager.executeQuery((Query)leftQuery);			
					}
				}
				
				if (rightQuery != null) {
					if (rightQuery instanceof CompoundQuery) {
						rightResultSets = execute((CompoundQuery)rightQuery);
						}
					else if (rightQuery instanceof Query){
						rightResultSets = QueryManager.executeQuery((Query)rightQuery);							
					}
				}
			}catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Error "+ex.getMessage());
			}
			
			if (operator != null) {
				if(operator.equals(OperatorType.AND)){
					resultSets = ResultSetInterSection(leftResultSets,rightResultSets);
				}
				else if(operator.equals(OperatorType.OR)){
					resultSets = ResultSetUnion(leftResultSets,rightResultSets);
				}
				else if(operator.equals(OperatorType.NOT)){
					resultSets = ResultSetDifference(leftResultSets,rightResultSets);
				}
				else if(operator.equals(OperatorType.PROJECT_RESULTS_BY)){
					resultSets = ResultSetProjectResultsBy(leftResultSets,rightResultSets);
				}
			}
		}
		return resultSets;	
	}
	/**
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return
	 */
	private static ResultSet[] ResultSetProjectResultsBy(ResultSet[] leftResultSets, ResultSet[] rightResultSets) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * ResultSetDifference (minus): set of every element that is in the leftResultset but not in the rightResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] which is set of every element that is in the leftResultset but not in the rightResultset
	 */
	private static ResultSet[] ResultSetDifference(ResultSet[] leftResultSets, ResultSet[] rightResultSets) {
		Collection leftResults = Arrays.asList(leftResultSets);
		Collection rightResults = Arrays.asList(rightResultSets);
		//the removeAll operation effectively modifies this set so that its value is the asymmetric set difference of the two sets.
		Set diffSet = new HashSet(leftResults);
		diffSet.removeAll(rightResults);
		return (ResultSet[]) diffSet.toArray();
	}
	/**
	 * ResultSetUnion: a resultSet containing every element that is in either the leftResultSet or the rightResultSet, or both
	 * (must be union-compatible: same number of objects and corresponding objects have identical )
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in either the leftResultSet or the rightResultSet, or both
	 */
	private static ResultSet[] ResultSetUnion(ResultSet[] leftResultSets, ResultSet[] rightResultSets) {
		Collection leftResults = Arrays.asList(leftResultSets);
		Collection rightResults = Arrays.asList(rightResultSets);
		Set diffSet = new HashSet(leftResults);
		//the addAll operation effectively modifies this set so that its value is the union of the two sets.
		diffSet.addAll(rightResults);
		return (ResultSet[]) diffSet.toArray();
	}
	/**
	 * ResultSetIntersection (intersect): a resultset containing every element that is in both rightResultSet and LeftResultset
	 * @param leftResultSets
	 * @param rightResultSets
	 * @return resultSet[] containing every element that is in both rightResultSet and LeftResultset
	 */
	private static ResultSet[] ResultSetInterSection(ResultSet[] leftResultSets, ResultSet[] rightResultSets) {
		Collection leftResults = Arrays.asList(leftResultSets);
		Collection rightResults = Arrays.asList(rightResultSets);
		//this operation effectively modifies this set so that its value is the intersection of the two sets.
		Set diffSet = new HashSet(leftResults);
		diffSet.retainAll(rightResults);
		return (ResultSet[]) diffSet.toArray();
	}

}
