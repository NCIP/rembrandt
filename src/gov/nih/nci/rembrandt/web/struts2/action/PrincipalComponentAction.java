package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.util.ClassHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.util.RembrandtListFilter;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.helper.ListConvertor;
import gov.nih.nci.rembrandt.web.struts2.form.PrincipalComponentForm;
import gov.nih.nci.rembrandt.web.struts2.form.UIFormValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;



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

//public class PrincipalComponentAction extends DispatchAction {
public class PrincipalComponentAction extends ActionSupport implements ServletRequestAware {
	HttpServletRequest servletRequest;
	PrincipalComponentForm principalComponentForm;
	
    private static Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;
    private Collection<CloneIdentifierDE> cloneIdentifierDECollection;
    private SessionCriteriaBag sessionCriteriaBag;
    private UserCredentials credentials;
    
    
    /**
     * Method submittal
     * 
     * @param ActionMapping
     *            mapping
     * @param ActionForm
     *            form
     * @param HttpServletRequest
     *            request
     * @param HttpServletResponse
     *            response
     * @return ActionForward
     * @throws Exception
     */
    public String submit()
            throws Exception {
/*        if (!isTokenValid(request)) {
			return mapping.findForward("failure");
		}
*/		
		List<String> errors = validateFormData();
		if (errors.size() > 0) {
			for (String error : errors)
				addActionError(error);
			
			return "backToHierarchicalClustering";
		}
        
        String sessionId = getServletRequest().getSession().getId();
        PrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = createPrincipalComponentAnalysisQueryDTO(principalComponentForm,getServletRequest().getSession());
        
        /*Create the InstituteDEs using credentials from the local session.
         * May want to put these in the cache eventually.
         */        
        if(getServletRequest().getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS)!=null){
            principalComponentAnalysisQueryDTO.setInstitutionDEs(InsitutionAccessHelper.getInsititutionCollection(getServletRequest().getSession()));
        }
        if (principalComponentAnalysisQueryDTO!=null) {
            SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
            queryBag.putQueryDTO(principalComponentAnalysisQueryDTO, principalComponentForm);
            presentationTierCache.putSessionQueryBag(sessionId, queryBag);
        } 
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createPCAFinding(principalComponentAnalysisQueryDTO,sessionId,principalComponentAnalysisQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        //Ensure a finding with the same name does not already exist in the session, so remove it
        presentationTierCache.removeObjectFromNonPersistableSessionCache(sessionId,principalComponentAnalysisQueryDTO.getQueryName());
//        resetToken(request);

        return "viewResults";
    }
    
    
    public String setup()
    throws Exception {
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        HttpSession session = getServletRequest().getSession();
        String sessionId = getServletRequest().getSession().getId(); 
        GroupRetriever groupRetriever = new GroupRetriever();
        getPrincipalComponentForm().setExistingGroupsList(groupRetriever.getClinicalGroupsCollection(getServletRequest().getSession()));
//        saveToken(request);

        return "backToPrincipalComponent";
    }
    
    private PrincipalComponentAnalysisQueryDTO createPrincipalComponentAnalysisQueryDTO(PrincipalComponentForm principalComponentForm, HttpSession session) {
        PrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = (PrincipalComponentAnalysisQueryDTO)ApplicationFactory.newQueryDTO(QueryType.PCA_QUERY);
        principalComponentAnalysisQueryDTO.setQueryName(principalComponentForm.getAnalysisResultName());
        //sessionCriteriaBag = presentationTierCache.getSessionCriteriaBag(sessionId);
        UserListBeanHelper userListBeanHelper = new UserListBeanHelper(session);
        
        
        //Create the clinical queries,if any, for this anlysisQuery DTO
        Collection<ClinicalQueryDTO> clinicalQueryCollection = new ArrayList<ClinicalQueryDTO>();
        
        //If user selects all samples, populate an "empty" clinical query with no specified samples         
        if(principalComponentForm.getGroupsOption().equalsIgnoreCase("allSamples")){
            ClinicalDataQuery emptyClinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
            emptyClinicalDataQuery.setQueryName("allSampleClinicalQuery");
            clinicalQueryCollection.add(emptyClinicalDataQuery);
            principalComponentAnalysisQueryDTO.setComparisonGroups(clinicalQueryCollection);
            
        }
        
        
        //If user selects certain samples, get either predefined group or user selected
        else if(principalComponentForm.getGroupsOption().equalsIgnoreCase("variousSamples")){   
            if(principalComponentForm.getSelectedGroups() != null || principalComponentForm.getSelectedGroups().length<=0){
            //Create the clinical query DTO collection from the selected groups in the form
                
                  //SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
                        for(int i=0; i<principalComponentForm.getSelectedGroups().length; i++){
                            
                        
                            //ClinicalDataQuery clinicalDataQuery= sampleBasedQueriesRetriever.getQuery(sessionId, principalComponentForm.getSelectedGroups()[i]);
                            String[] uiDropdownString = principalComponentForm.getSelectedGroups()[i].split("#");
                            String myClassName = uiDropdownString[0];
                            String myValueName = uiDropdownString[1];
                           
                            Class myClass = ClassHelper.createClass(myClassName);
                            
                            if(myClass.isInstance(new UserList())){
                                PatientUserListQueryDTO patientQueryDTO = new PatientUserListQueryDTO(session,myValueName);
                                clinicalQueryCollection.add(patientQueryDTO);
                            }
                        }
                        principalComponentAnalysisQueryDTO.setComparisonGroups(clinicalQueryCollection);               
            }
        }
        
        //create GeneVectorPercentileDE
            principalComponentAnalysisQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(principalComponentForm.getVariancePercentile()),Operator.GE));
       
        /*create GeneIdentifierDEs by looking in the cache for 
        the specified GeneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.*/
        
        if(principalComponentForm.getGeneSetName()!= null && (!principalComponentForm.getGeneSetName().equals("") || !principalComponentForm.getGeneSetName().equals("none"))){
            UserList geneList = userListBeanHelper.getUserList(principalComponentForm.getGeneSetName());
            if(geneList!=null){
                ListSubType geneListSubTypes = geneList.getListSubType();
                List<ListSubType> allSubTypes = RembrandtListFilter.getSubTypesForType(ListType.Gene);
                        for(ListSubType sb : allSubTypes){                
                            if(sb.equals(geneListSubTypes)){
                                geneIdentifierDECollection = ListConvertor.convertToGeneIdentifierDE(geneList.getList(),sb);
                            }
                        }
                   
            if (geneIdentifierDECollection!=null && !geneIdentifierDECollection.isEmpty()){
                logger.debug("geneIdentifierDECollection was found");
                principalComponentAnalysisQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found");
            }
           }
        }
        
        /*create CloneIdentifierDEs by looking in the cache for 
        the specified CloneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.
        The CloneIdentifierDEs will be set as "reporterDECollection" */
        
        if(principalComponentForm.getReporterSetName()!= null && (!principalComponentForm.getReporterSetName().equals("") || principalComponentForm.getReporterSetName().length()!=0)){
            UserList reporterList = userListBeanHelper.getUserList(principalComponentForm.getReporterSetName());
            if(reporterList!=null){
                ListSubType reporterListSubTypes = reporterList.getListSubType();
                List<ListSubType> allSubTypes = RembrandtListFilter.getSubTypesForType(ListType.Reporter);
               for(ListSubType sb : allSubTypes){
                        if(sb.equals(reporterListSubTypes)){
                            cloneIdentifierDECollection = ListConvertor.convertToCloneIdentifierDE(reporterList.getList(),sb);
                        }
                    }
                    
            }if (cloneIdentifierDECollection!=null && !cloneIdentifierDECollection.isEmpty()){
                logger.debug("cloneIdentifierDECollection was found");
                principalComponentAnalysisQueryDTO.setReporterIdentifierDEs(cloneIdentifierDECollection);
            }
            else{
                logger.debug("cloneIdentifierDECollection could not be found");
            }
        }
        
        
        //create ArrayPlatformDE
        if(principalComponentForm.getArrayPlatform()!=null || principalComponentForm.getArrayPlatform().length()!=0){
            principalComponentAnalysisQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(principalComponentForm.getArrayPlatform()));
        }
        
        return principalComponentAnalysisQueryDTO;
        
    }

    
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}


	public PrincipalComponentForm getPrincipalComponentForm() {
		if (principalComponentForm == null) {
			principalComponentForm = new PrincipalComponentForm();
		}
		return principalComponentForm;
	}

	public PrincipalComponentForm getForm() {
		return getPrincipalComponentForm();
	}	

	public void setPrincipalComponentForm(
			PrincipalComponentForm principalComponentForm) {
		this.principalComponentForm = principalComponentForm;
	} 
	
    public List<String> validateFormData() {
		List<String> errors = new ArrayList<String>();

       // Analysis name cannot be blank
        errors.addAll(UIFormValidator.validateQueryName(getPrincipalComponentForm().getAnalysisResultName(), errors));
       
       // Validate group field
        errors.addAll(UIFormValidator.validateSelectedGroup(getPrincipalComponentForm().getGroupsOption(), getPrincipalComponentForm().getSelectedGroups(), errors));
       
        return errors;
    }	
    
}
