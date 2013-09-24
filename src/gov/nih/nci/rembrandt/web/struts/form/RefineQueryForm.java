/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.bean.SelectedQueryBean;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;



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

public class RefineQueryForm extends BaseForm implements Factory {
	private static Logger logger = Logger.getLogger(RefineQueryForm.class);

	private ActionErrors errors = new ActionErrors();;

	private String queryText;

	private String compoundView;
	private String[] instituteView;

	private String runFlag = "no";

	private String method;
	
	// Collections used for Lookup values.
	private List nonAllGenesQueries = new ArrayList();
	
	private List allGenesQueries = new ArrayList();

	private List compoundViewColl = new ArrayList();
	private Collection<InstitutionDE> institueViewColl; // = new ArrayList();

	private List selectedQueries = LazyList.decorate(new ArrayList(), this);
	
	private String allGeneQuery = "";
	
	private Collection resultSets = new ArrayList();

	private String selectedResultSet = null;
    
    SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
	
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private boolean isAllGenesQuery = false ;
    
    private String queryDetails = "";
 
	public RefineQueryForm() {
		super();
	}
	
	public List getAllGenesQueries() {
		return allGenesQueries;
	}

	

	/**
	 * Method reset. Reset all properties to their default values.
	 * 
	 * @param ActionMapping
	 *            mapping used to select this instance.
	 * @param HttpServletRequest
	 *            request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		queryText = "";
		compoundView = "";
		instituteView = new String[0];
		//institueViewColl = new ArrayList();
		//institueViewColl.add(new LabelValueBean(" ", " "));
		selectedResultSet = "";
		setRefineQueryLookups(request);
		compoundViewColl = new ArrayList();
		compoundViewColl.add(new LabelValueBean(" ", " "));
		
		institueViewColl = InsitutionAccessHelper.getInsititutionCollectionWithDisplayNames(request.getSession());
		//Collections.sort((List) institueViewColl);
		//To remove the All Institutions entry
		for (Iterator it = institueViewColl.iterator(); it.hasNext();){
			InstitutionDE de = (InstitutionDE)it.next();
			if ("ALL INSTITUTIONS".equalsIgnoreCase(de.getInstituteName())){
				institueViewColl.remove(de);
				break;
			}
		}
	}

	/**
	 * Returns the queryName1.
	 * 
	 * @return String
	 */
	public String getQueryText() {

		return queryText;
	}

	/**
	 * Set the queryName1.
	 * 
	 * @param queryName1
	 *            The queryName to set
	 */
	public void setQueryText(String queryText) {
		this.queryText = queryText;

	}

	/**
	 * Returns the compoundView.
	 * 
	 * @return String
	 */
	public String getCompoundView() {

		return compoundView;
	}

	/**
	 * Set the compoundView.
	 * 
	 * @param compoundView
	 *            The compoundView to set
	 */
	public void setCompoundView(String compoundView) {
		this.compoundView = compoundView;

	}

	/**
	 * Set the Run Report button flag.
	 * 
	 * @param runFlag
	 *            The runFlag to set
	 */
	public void setRunFlag(String runValue) {
		this.runFlag = runValue;

	}

	public String getRunFlag() {
		return runFlag;

	}

	public String getMethod() {

		return "";
	}

	public void setMethod(String method) {

		this.method = method;
	}

	public List getNonAllGeneQueries() {
		return nonAllGenesQueries;
	}

	public List getCompoundViewColl() {
		return compoundViewColl;
	}

	public void setCompoundViewColl(List viewColl) {
		this.compoundViewColl = viewColl;
	}

	/**
	 * @return Returns the errors.
	 */
	public ActionErrors getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            The errors to set.
	 */
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public void addActionError(String string, ActionError error) {
		this.errors.add(string, error);
	}

	/**
	 * @return Returns the selectedQueries.
	 */
	public List getSelectedQueries() {
		if (selectedQueries.isEmpty()) {
			SelectedQueryBean newQuery = new SelectedQueryBean();
			newQuery.setQueryName("");
			selectedQueries.add(newQuery);
		}
		return selectedQueries;
	}

	/**
	 * @param selectedQueries
	 *            The selectedQueries to set.
	 */
	public void setSelectedQueries(ArrayList selectedQueries) {
		this.selectedQueries = selectedQueries;
	}

	public SelectedQueryBean getSelectedQuery(int index) {
		return (SelectedQueryBean) this.getSelectedQueries().get(index);
	}

	public void addSelectedQuery() {
		List selectedQueries = this.getSelectedQueries();
		SelectedQueryBean newQuery = new SelectedQueryBean();
		newQuery.setQueryName("");
		selectedQueries.add(newQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections.Factory#create()
	 */
	public Object create() {
		SelectedQueryBean newQuery = new SelectedQueryBean();
		newQuery.setQueryName("");
		return newQuery;
	}

	/**
	 * @return Returns the resultSets.
	 */
	public Collection getResultSets() {
		return resultSets;
	}

	/**
	 * @return Returns the selectedResultSet.
	 */
	public String getSelectedResultSet() {
		return selectedResultSet;
	}

	/**
	 * @param resultSets
	 *            The resultSets to set.
	 */
	public void setResultSets(Collection resultSets) {
		this.resultSets = resultSets;
	}

	/**
	 * @param selectedResultSet
	 *            The selectedResultSet to set.
	 */
	public void setSelectedResultSet(String selectedResultSet) {
		this.selectedResultSet = selectedResultSet;
	}

	
	public String getQueryDetails() {
		return queryDetails;
	}
	public void setQueryDetails(String queryDetails) {
		this.queryDetails = queryDetails;
	}
	
	/**
	 * Method validate
	 * 
	 * @param ActionMapping
	 *            mapping
	 * @param HttpServletRequest
	 *            request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		String paramValue = request.getParameter(mapping.getParameter());

		// Validate Query validation
		if (paramValue != null) {

			// Run Report Validations
			if (paramValue.equalsIgnoreCase("displayresult")) {
				if (this.getCompoundView().trim().length() < 1) {
					if (this.getQueryText().trim().length() >= 1) {
						this.setQueryText("");
						errors
								.add(
										"compoundView",
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.form.refinequery.no.view"));
					}
					if (this.getQueryText().trim().length() < 1) {
						errors
								.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.action.refinequery.querycoll.no.error"));
					}
				}
			}
		}

		return errors;
	}
	/**
	 * This method is essential in maintaining the drop down menus of the 
	 * refineQueryPage. It currently grabs the SessionQueryBag from the 
	 * sessionCache and retrieves a map to the current list of all gene queries
	 * and non all gene queries, converting them to Collections for use by the
	 * refine_tile.jsp.  It also grabs and stores the current list of all the
	 * result sets that are stored in the cache.
	 * 
	 * @param request
	 */
	private void setRefineQueryLookups(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		//Retrieve the session query bag from the cacheManager
		SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
		//setup the List of Queries
		nonAllGenesQueries = new ArrayList();
		allGenesQueries = new ArrayList();
		if (queryBag != null) {
			//Setup the Non All Gene Queries
			Map queries = queryBag.getNonAllGeneQueries();
			if(queries!=null) {
				Set keys = queries.keySet();
				for (Iterator iter = keys.iterator(); iter.hasNext();) {
					nonAllGenesQueries.add(queries.get(iter.next()));
				}
			}
			//Now setup all of the All Genes Queries
			queries = queryBag.getAllGenesQueries();
			if(queries!=null) {
				Set keys = queries.keySet();
				for (Iterator iter = keys.iterator(); iter.hasNext();) {
					allGenesQueries.add(queries.get(iter.next()));
				}
			}
		//Now setup all of the current result sets
		//setResultSets(presentationTierCache.getSampleSetNames(sessionId));
        UserListBeanHelper ul= new UserListBeanHelper(request.getSession());
        Collection listss =  ul.getAllCustomListsForType(ListType.PatientDID);
        setResultSets(listss);
        //setResultSets(sampleBasedQueriesRetriever.getAllSampleSetNames(sessionId));    
            	
		} else {
			logger.debug("No Query Collection Object in Session");
		}
	}
	/**
	 * @return Returns the allGeneQuery.
	 */
	public String getAllGeneQuery() {
		return allGeneQuery;
	}

	/**
	 * @param allGeneQuery The allGeneQuery to set.
	 */
	public void setAllGeneQuery(String allGeneQuery) {
		this.allGeneQuery = allGeneQuery;
	}

	/**
	 * @return Returns the nonAllGenesQueries.
	 */
	public List getNonAllGenesQueries() {
		return nonAllGenesQueries;
	}
	/**
	 * @param nonAllGenesQueries The nonAllGenesQueries to set.
	 */
	public void setNonAllGenesQueries(List nonAllGenesQueries) {
		this.nonAllGenesQueries = nonAllGenesQueries;
	}
	/**
	 * @return Returns the isAllGenesQuery.
	 */
	public boolean isAllGenesQuery() {
		return isAllGenesQuery;
	}
	/**
	 * @param isAllGenesQuery The isAllGenesQuery to set.
	 */
	public void setIsAllGenesQuery(boolean isAllGenesQuery) {
		this.isAllGenesQuery = isAllGenesQuery;
	}

	public Collection<InstitutionDE> getInstitueViewColl() {
		return institueViewColl;
	}

	public void setInstitueViewColl(Collection<InstitutionDE> institueViewColl) {
		this.institueViewColl = institueViewColl;
	}

	public String[] getInstituteView() {
		return instituteView;
	}

	public void setInstituteView(String[] instituteView) {
		this.instituteView = instituteView;
	}
}
