package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * @author SahniH Date: Sep 24, 2004
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

public class CompoundQuery implements Queriable, Serializable, Cloneable {
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
	private transient static Logger logger = Logger.getLogger(CompoundQuery.class);

	private Queriable leftQuery = null;

	private Queriable rightQuery = null;

	private OperatorType operatorType = null;

	private Viewable associatedView = null;

	private String queryName;
	
	//private static boolean isClinicalSampleSet = false;
	
    private InstitutionCriteria institutionCriteria;

	// Session that this compoundQuery is associated with
	private String sessionId = null;
	
	private final String TOKEN = "@$%&***-!!";

	public CompoundQuery(OperatorType operator, Queriable leftQuery,
			Queriable rightQuery) throws Exception {
		setOperatorType(operator);
		setLeftQuery(leftQuery);
		setRightQuery(rightQuery);
	}

	public CompoundQuery(Queriable rightQuery) throws Exception {
		setRightQuery(rightQuery);
		setOperatorType(null);
		setLeftQuery(null);
	}

	public Viewable getAssociatedView() {
		return associatedView;
	}

	public void setAssociatedView(Viewable viewable) {
		associatedView = viewable;
	}

	/**
	 * @return Returns the operatorType.
	 */
	public OperatorType getOperatorType() {
		return this.operatorType;
	}

	/**
	 * @param operatorType
	 *            The operatorType to set.
	 */
	public void setOperatorType(OperatorType operatorType) throws Exception {
		// PROJECTS results by make sure the two views are different
		this.operatorType = operatorType;
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}

	}

	/**
	 * @return Returns the leftQuery.
	 */
	public Queriable getLeftQuery() {
		return this.leftQuery;
	}

	/**
	 * @param leftQuery
	 *            The leftQuery to set.
	 */
	public void setLeftQuery(Queriable leftQuery) throws Exception {
		this.leftQuery = leftQuery;
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}
	}

	/**
	 * @return Returns the rightQuery.
	 */
	public Queriable getRightQuery() {
		return this.rightQuery;
	}

	/**
	 * @param rightQuery
	 *            The rightQuery to set.
	 */
	public void setRightQuery(Queriable rightQuery) throws Exception {
		this.rightQuery = rightQuery;
		// The right query's AssociatedView is always the "winning view"
		if (rightQuery != null && rightQuery.getAssociatedView() != null) {			
			
			setAssociatedView(rightQuery.getAssociatedView());
			//setQueryName(rightQuery.getQueryName());
		}
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}
	}

	/**
	 * @return validationStatus as true or false
	 * 
	 */
	public boolean validateProjectResultsBy() {
		// PROJECTS results by make sure the two views are different
		if ((operatorType != null && operatorType
				.equals(OperatorType.PROJECT_RESULTS_BY))
				&& (getRightQuery() != null && getLeftQuery() != null)) {
			if ((getRightQuery().getAssociatedView() != null && getLeftQuery()
					.getAssociatedView() != null)) {
				if ((getRightQuery().getAssociatedView().equals(
						ViewType.GENE_GROUP_SAMPLE_VIEW) || getRightQuery()
						.getAssociatedView().equals(
								ViewType.GENE_SINGLE_SAMPLE_VIEW))
						&& (!getLeftQuery().getAssociatedView().equals(
								ViewType.CLINICAL_VIEW))
						|| ((getLeftQuery().getAssociatedView().equals(
								ViewType.GENE_GROUP_SAMPLE_VIEW) || getLeftQuery()
								.getAssociatedView().equals(
										ViewType.GENE_SINGLE_SAMPLE_VIEW)) && (!getRightQuery()
								.getAssociatedView().equals(
										ViewType.CLINICAL_VIEW)))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public String toString() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		OperatorType operator = this.getOperatorType();
		String leftString = "";
		String rightString = "";
		String outString = "";

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftString += ((CompoundQuery) leftQuery).toString();
				} else if (leftQuery instanceof Query) {
					leftString += ((Query) leftQuery).getQueryName();
				}
			}

			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightString += ((CompoundQuery) rightQuery).toString();
				} else if (rightQuery instanceof Query) {
					rightString += ((Query) rightQuery).getQueryName();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		if (operator != null) {
			outString += "( " + leftString + " " + operator.getOperatorType()
					+ " " + rightString + " )";
		} else {
			outString = rightString;
		}
		return outString;
	}
	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public String getTokenizedName() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		OperatorType operator = this.getOperatorType();
		String leftString = "";
		String rightString = "";
		String outString = "";
		

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftString += ((CompoundQuery) leftQuery).getTokenizedName();
				} else if (leftQuery instanceof Query) {
					leftString += ((Query) leftQuery).getQueryName();
				}
			}

			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightString += ((CompoundQuery) rightQuery).getTokenizedName();
				} else if (rightQuery instanceof Query) {
					rightString += ((Query) rightQuery).getQueryName();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		if (operator != null) {
			outString += "(" + TOKEN + leftString + TOKEN + operator.getOperatorType()
					+ TOKEN + rightString + TOKEN + ")";
		} else {
			outString = rightString;
		}
		return outString;
	}
	
	public String getQueryNameWithLink(){
		final String LINKSTART = "<a href=\"javascript:void(0);\"" +
						" onmouseover=\"return QueryDetailHelper.getQueryDetails('";
		final String LINKMIDDLE = "');\" onmouseout=\"return nd();\">";
		final String LINKEND = "</a>";
		String token = this.getQueryName() + "#$#";
		final String SPACE = " ";
		String tokenizedName = this.getTokenizedName();
		List<String> allSubQueryNames = getAllSubQueryNames();
		
		if (allSubQueryNames == null || allSubQueryNames.isEmpty())
			return "";
		Map<String, String> map = new HashMap<String, String>();
		//Map replacements = new String[allSubQueryNames.size()];

		for (String name : allSubQueryNames){
			map.put(name, LINKSTART + token + name + LINKMIDDLE + name + LINKEND);
		}
		
		StringTokenizer tokenizer = new StringTokenizer(tokenizedName, TOKEN);
		StringBuffer buffer = new StringBuffer();
		String key = null;
		while (tokenizer.hasMoreTokens()){
			key = tokenizer.nextToken();
			if (map.containsKey(key)){
				buffer.append(map.get(key));
				buffer.append(SPACE);
			}
			else {
				buffer.append(key);
				buffer.append(SPACE);
			}
		}
		return buffer.toString();
	}
	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public String toStringForSideBar() {
		final String TITLE = "<B>Compound Query</B><br><B class='otherBold'>Compound Query Name</B><br>";
		final String BREAKER = "<BR><BR>";
		
		String string = getQueryNameWithLink();

		return TITLE + string + BREAKER;
	}

	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public List<String> getAllSubQueryNames() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		List<String> queryNames = new ArrayList<String>();

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					queryNames.addAll(((CompoundQuery)leftQuery).getAllSubQueryNames());
				} else if (leftQuery instanceof Query) {
					queryNames.add(((Query) leftQuery).getQueryName());
				}
			}

			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					queryNames.addAll(((CompoundQuery)rightQuery).getAllSubQueryNames());
				} else if (rightQuery instanceof Query) {
					queryNames.add(((Query) rightQuery).getQueryName());
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return queryNames;
	}
	
	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public String getQueryDetails(String queryName) {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();

		String queryString = "";

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					queryString = ((CompoundQuery)leftQuery).getQueryDetails(queryName);
				} else if (leftQuery instanceof Query) {
					if (queryName.equals(leftQuery.getQueryName()))
						queryString = leftQuery.toString();
				}
			}
			if (queryString.length() > 0)
				return queryString;
			
			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					queryString = ((CompoundQuery) rightQuery).getQueryDetails(queryName);
				} else if (rightQuery instanceof Query) {
					if (queryName.equals(rightQuery.getQueryName()))
						queryString = rightQuery.toString();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return queryString;
	}
	public Collection getValidViewStrings(){
		ViewType[] types = this.getValidViews();
		Collection queryViewColl = new ArrayList();
		if (types.length == 0)
			return queryViewColl;

		Properties props = new Properties();
		props = ApplicationContext.getLabelProperties();

		for (int viewIndex = 0; viewIndex < types.length; viewIndex++) {
			ViewType thisViewType = (ViewType) types[viewIndex];
			String viewText = (String) props.get(thisViewType.getClass().getName());
			queryViewColl.add(new LabelValueBean(viewText, Integer.toString(viewIndex)));
		}
		return queryViewColl;

	}
	public ViewType[] getValidViews() {
		ViewType[] validViewTypes = null;
		ArrayList queryTypesCollection = null;
		boolean isGEQuery = false;
		boolean isCGHQuery = false;
		boolean isClinical = false;

		boolean constrainBySamples = false;
		Query[] q = this.getAssociatiedQueries();
		for(Query qu : q){
			if(qu.getSampleIDCrit()!=null)	{
				constrainBySamples = true;
			}
		}
		
		queryTypesCollection = getQueryTypes(this);

		for (Iterator iter = queryTypesCollection.iterator(); iter.hasNext();) {
			QueryType thisQuery = (QueryType) iter.next();
			if (thisQuery == QueryType.GENE_EXPR_QUERY_TYPE)
				isGEQuery = true;
			if (thisQuery == QueryType.CGH_QUERY_TYPE)
				isCGHQuery = true;
			if (thisQuery == QueryType.CLINICAL_DATA_QUERY_TYPE)
				isClinical = true;
		}
		
		//if(isClinical== false) {			
		//	isClinical = isClinicalSampleSet;
		//	isClinicalSampleSet = false;
		//	} 
		// Gene Expression Only
		if (isGEQuery && !isCGHQuery && !isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW,
					ViewType.GENE_GROUP_SAMPLE_VIEW };
		}
		
		
		// Genomic Only
		else if (!isGEQuery && isCGHQuery && !isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}
		// Clinical Only
		else if (!isGEQuery && !isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW };
		}
		// Gene Expression and Clinical Only
		else if (isGEQuery && !isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW };
		}
		// Genomic and Clinical Only
		else if (!isGEQuery && isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}
		
      
		// The rest compound queries
		else {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}
		
		//see if we are constraining by a specific sample set...if we are, the group view doesnt make sense
		List<ViewType> vvt = Arrays.asList(validViewTypes);
		if(vvt.contains(ViewType.GENE_GROUP_SAMPLE_VIEW) && constrainBySamples){
			//remove it
			ArrayList al = new ArrayList();
			al.addAll(vvt);
			al.remove(ViewType.GENE_GROUP_SAMPLE_VIEW);
			//validViewTypes = null;
			validViewTypes = new ViewType[al.size()]; 
			al.toArray(validViewTypes);
			
		}
		

		return validViewTypes;
	}

	public ArrayList getQueryTypes(CompoundQuery cQuery) {
		ArrayList queryType = new ArrayList();
		Queriable lQuery;
		Queriable rQuery;
		lQuery = cQuery.getLeftQuery();
		rQuery = cQuery.getRightQuery();

		try {
			if (lQuery != null) {
				if (lQuery instanceof CompoundQuery)
					queryType.addAll(getQueryTypes((CompoundQuery) lQuery));
				else if (lQuery instanceof Query) {
					queryType.add(((Query) lQuery).getQueryType());
				}
			}

			if (rQuery != null) {
				if (rQuery instanceof CompoundQuery)
					queryType.addAll(getQueryTypes((CompoundQuery) rQuery));
				else if (rQuery instanceof Query) {
					queryType.add(((Query) rQuery).getQueryType());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return queryType;
	}

	public Query[] getAssociatiedQueries() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		
		Collection queries = new Vector();

		try {
			if (leftQuery != null) {
				queries.addAll(Arrays.asList((Query[]) leftQuery
						.getAssociatiedQueries()));
			}
			if (rightQuery != null) {
				
				queries.addAll(Arrays.asList((Query[]) rightQuery
						.getAssociatiedQueries()));
				// This is to take care of cinical sample set
				//if(rightQuery instanceof ClinicalDataQuery ) {						
					//isClinicalSampleSet = true;					
				//}
			}
			
		} catch (Exception ex) {
			logger.error(ex);
		}
		return (Query[]) queries.toArray(new Query[queries.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.nautilus.query.Queriable#getQueryName()
	 */
	public String getQueryName() {
		if (queryName == null)
			queryName = this.toString();
		return queryName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.nautilus.query.Queriable#setQueryName(java.lang.String)
	 */

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            The sessionId to set.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Overrides the protected Object.clone() method exposing it as public. It
	 * performs a 2 tier copy, that is, it does a memcopy of the instance and
	 * then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		CompoundQuery myClone = null;
		try {
			myClone = (CompoundQuery) super.clone();
		} catch (CloneNotSupportedException e) {
			// This will never happen...
		}
		if(associatedView!=null) {
			myClone.associatedView = (Viewable) associatedView.clone();
		}
		if(leftQuery!=null) {
			myClone.leftQuery = (Queriable) leftQuery.clone();
		}
		if(rightQuery!=null) {		
			myClone.rightQuery = (Queriable) rightQuery.clone();
		
		}
		if(operatorType!=null) {
			myClone.operatorType = (OperatorType) operatorType.clone();
		}
		return myClone;
	}
	/**
	 * This method will return the if the compoundQuery contains an all all genes querries.
	 * It iterates through the current list of queries and checks for
	 * isAllGenesQuery(). There is no
	 * setter for this property as it is only a subset of the current queries
	 *  in the compound query.
	 * 
	 * @return -- a boolean ifl the All Genes Query is detected
	 */
	public boolean isAllGenesQuery() {
		Query[] queries = getAssociatiedQueries();
		if(queries != null){
			for(int i = 0; i < queries.length; i++) {
				
				Query query = (Query)queries[i];
				if(query instanceof ComparativeGenomicQuery) {
					ComparativeGenomicQuery cgQuery = (ComparativeGenomicQuery)query;
					if(cgQuery.isAllGenesQuery()) {
						return true;
					}
				}else if(query instanceof GeneExpressionQuery) {
					GeneExpressionQuery geQuery = (GeneExpressionQuery)query;
					if(geQuery.isAllGenesQuery()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public InstitutionCriteria getInstitutionCriteria() {
		return institutionCriteria;
	}

	public void setInstitutionCriteria(InstitutionCriteria institutionCriteria) {
		this.institutionCriteria = institutionCriteria;
	}
}
