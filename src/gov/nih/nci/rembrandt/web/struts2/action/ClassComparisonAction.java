package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.caintegrator.application.cache.CacheConstants;
import gov.nih.nci.caintegrator.application.lists.ListItem;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.util.ClassHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.EnumCaseChecker;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.struts2.form.ClassComparisonForm;




import gov.nih.nci.rembrandt.web.struts2.form.UIFormValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


//import org.apache.struts.actions.DispatchAction;
import gov.nih.nci.rembrandt.web.bean.LabelValueBean;

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

//public class ClassComparisonAction extends DispatchAction {
public class ClassComparisonAction extends ActionSupport implements ServletRequestAware {
	
	HttpServletRequest servletRequest;
	ClassComparisonForm classComparisonForm;

	private UserCredentials credentials;  
	private static Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
    private MultiGroupComparisonAdjustmentType ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE = MultiGroupComparisonAdjustmentType.FWER;
    private StatisticalMethodType ERROR_STATISTICAL_METHOD_TYPE = StatisticalMethodType.TTest;
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
    public ActionForward Submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        try {
        	return submit(mapping, form, request, response);
        } catch (Exception e) {
        	throw e;
        }
    }
     */
    
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}    
    
    public String submit() throws Exception {
/*        if (!isTokenValid(request)) {
			return mapping.findForward("failure");
		}
*/
		List<String> errors = validateFormData();
		if (errors.size() > 0) {
			for (String error : errors)
				addActionError(error);
			
			return "backToClassComparison";
		}
		
        String sessionId = servletRequest.getSession().getId();
        ClassComparisonQueryDTO classComparisonQueryDTO = createClassComparisonQueryDTO(classComparisonForm,servletRequest.getSession());
        
        /*Create the InstituteDEs using credentials from the local session.
         * May want to put these in the cache eventually.
         */        
        if(servletRequest.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS)!=null){
           classComparisonQueryDTO.setInstitutionDEs(InsitutionAccessHelper.getInsititutionCollection(servletRequest.getSession()));
        }
        if (classComparisonQueryDTO!=null) {
            SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
            queryBag.putQueryDTO(classComparisonQueryDTO, classComparisonForm);
            presentationTierCache.putSessionQueryBag(sessionId, queryBag);
        }   
        
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
        	
        	if (!"FTest".equals(classComparisonForm.getStatisticalMethod()))
        		finding = factory.createClassComparisonFinding(classComparisonQueryDTO,sessionId,classComparisonQueryDTO.getQueryName());
        	else
        		finding = factory.createFTestFinding(classComparisonQueryDTO,sessionId,classComparisonQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        
        //Ensure a finding with the same name does not already exist in the session, so remove it
        presentationTierCache.removeObjectFromNonPersistableSessionCache(sessionId,classComparisonQueryDTO.getQueryName());
        //resetToken(request);
        
        return "viewResults";
    }
    
    public String setup()
    throws Exception {
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/  
        GroupRetriever groupRetriever = new GroupRetriever();
        
       // saveToken(request);
	    
        // JB:Begin - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
	    // Add the list names (labels) to the existing groups list for UI display
        List<LabelValueBean> existingGroups = groupRetriever.getClinicalGroupsCollection(servletRequest.getSession());
	    existingGroups.add(new LabelValueBean("REST OF ALL SAMPLES", "gov.nih.nci.caintegrator.application.lists.UserList#REST OF ALL SAMPLES"));
	    existingGroups.add(new LabelValueBean("REST OF ALL GLIOMAS", "gov.nih.nci.caintegrator.application.lists.UserList#REST OF ALL GLIOMAS"));
        //classComparisonForm.setExistingGroupsList(groupRetriever.getClinicalGroupsCollection(request.getSession()));
	    getClassComparisonForm().setExistingGroupsList(existingGroups);
        // JB:End - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
        
        if( getClassComparisonForm().getFoldChangeAutoList() != null && !getClassComparisonForm().getFoldChangeAutoList().get(0).equals(" ")) {
        	getClassComparisonForm().getFoldChangeAutoList().add(0," ");
        }
         
        return "backToClassComparison";
    }
        
    private ClassComparisonQueryDTO createClassComparisonQueryDTO(ClassComparisonForm classComparisonQueryForm, HttpSession session){
 
    		UserListBeanHelper userListBeanHelper = new UserListBeanHelper(session.getId());
    		UserListBean userListBean = userListBeanHelper.getUserListBean();
	    	List<String> selectedGroupNames = Arrays.asList(classComparisonQueryForm.getSelectedGroups());
	    	
	        ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
	        classComparisonQueryDTO.setQueryName(classComparisonQueryForm.getAnalysisResultName());
	        
	        
	        //Create the clinical query DTO collection from the selected groups in the form
	        List<ClinicalQueryDTO> clinicalQueryCollection = new ArrayList<ClinicalQueryDTO>();
	        
            if(classComparisonQueryForm.getSelectedGroups() != null && classComparisonQueryForm.getSelectedGroups().length >= 2 ){

                
                // JB:Begin - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
            	
                // Create/Adjust "REST OF..." selection (if they exist to remove overlapping samples)
        		String restOfAllSamplesLabelValue = "gov.nih.nci.caintegrator.application.lists.UserList#REST OF ALL SAMPLES";
        		String restOfAllGliomasLabelValue = "gov.nih.nci.caintegrator.application.lists.UserList#REST OF ALL GLIOMAS";
 
        		List<UserList> allUserLists = userListBean.getEntireList();
            	
            	if ( selectedGroupNames.contains(restOfAllSamplesLabelValue) ) {
                    UserList allSamplesUserList =  userListBeanHelper.getUserList("ALL");
                    List<ListItem> restOfAllSamplesUserListItems =  new ArrayList<ListItem>(allSamplesUserList.getListItems());

                	for ( String selectedGroupName:selectedGroupNames ) {
                        String[] selectedGroupNameValue = selectedGroupName.split("#");
                        String listName = selectedGroupNameValue[1];
                        if ( !listName.equals("REST OF ALL SAMPLES") ) {
	                        List<ListItem> listItems = userListBeanHelper.getUserList(listName).getListItems();
                        	//*******   This isn't working for some reason *************
                        	//if ( restOfAllSamplesUserListItems.containsAll(listItems) ) {
                        	//	restOfAllSamplesUserListItems.removeAll(listItems);
                        	//}
	                        for (ListItem listItem:listItems) {
	                        	for (ListItem restOfAllListItem:restOfAllSamplesUserListItems) {
	                        		if ( restOfAllListItem.getName().equals(listItem.getName()) ) {
	                        			restOfAllSamplesUserListItems.remove(restOfAllListItem);
	                        			break;
	                        		}
	                        	}
	                        }
                    	}
                    }
            	    UserList restOfAllSamplesUserList = 
            	    	new UserList("REST OF ALL SAMPLES", allSamplesUserList.getListType(), 
            	    			restOfAllSamplesUserListItems, allSamplesUserList.getInvalidListItems());
            	    allUserLists.add(restOfAllSamplesUserList);
                	
            	} else if ( selectedGroupNames.contains(restOfAllGliomasLabelValue) ) {
                    UserList allGliomasUserList =  userListBeanHelper.getUserList("ALL GLIOMA");
                    List<ListItem> restOfAllGliomasUserListItems =  allGliomasUserList.getListItems();
                    for(String selectedGroupName:selectedGroupNames){
                        String[] selectedGroupNameValue = selectedGroupName.split("#");
                        String listName = selectedGroupNameValue[1];
                        if ( !listName.equals("REST OF ALL GLIOMAS") ) {
	                        List<ListItem> listItems = userListBeanHelper.getUserList(listName).getListItems();
                        	//*******   This isn't working for some reason *************
                        	//if ( restOfAllGliomasUserListItems.containsAll(listItems) ) {
                        	//	restOfAllGliomasUserListItems.removeAll(listItems);
                        	//}
	                        for (ListItem listItem:listItems) {
	                        	for (ListItem restOfAllGliomaListItem:restOfAllGliomasUserListItems) {
	                        		if ( restOfAllGliomaListItem.getName().equals(listItem.getName()) ) {
	                        			restOfAllGliomasUserListItems.remove(restOfAllGliomaListItem);
	                        			break;
	                        		}
	                        	}
	                        }
                    	}
                    }          		
            	    UserList restOfAllGliomasUserList =
            	    	new UserList("REST OF ALL GLIOMAS", allGliomasUserList.getListType(), 
            	    			restOfAllGliomasUserListItems, allGliomasUserList.getInvalidListItems());
            	    allUserLists.add(restOfAllGliomasUserList);
                }
                // JB:End - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
            	
            	
            	
            	for(int i=0; i<classComparisonQueryForm.getSelectedGroups().length; i++){

                    /*
                     * parse the selected groups, create the appropriate EnumType and add it
                     * to its respective EnumSet
                     */
                    String[] uiDropdownString = classComparisonQueryForm.getSelectedGroups()[i].split("#");
                    String myClassName = uiDropdownString[0];
                    String myValueName = uiDropdownString[1];
                   
                    Class myClass = ClassHelper.createClass(myClassName);
                    
                    if(myClass.isInstance(new UserList())){
                        PatientUserListQueryDTO patientQueryDTO = new PatientUserListQueryDTO(session,myValueName);
                        clinicalQueryCollection.add(patientQueryDTO);
                        if(i==1){//the second group is always baseline
                        	//to set baseline only when the statistical method 
                        	//is not FTest
                        	if(!"FTest".equals(classComparisonQueryForm.getStatisticalMethod()))
                        		patientQueryDTO.setBaseline(true);
                        }
                    }
                }
              classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);
            }
        
        //Create the foldChange DEs
       
            
            if (classComparisonQueryForm.getFoldChange().equals("list")){
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeAuto()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);
            }
            if (classComparisonQueryForm.getFoldChange().equals("specify")){        
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeManual()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);                   
            }
            
        
        //Create arrayPlatfrom DEs
            if(classComparisonQueryForm.getArrayPlatform() != "" || classComparisonQueryForm.getArrayPlatform().length() != 0){       
                ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonQueryForm.getArrayPlatform());
                classComparisonQueryDTO.setArrayPlatformDE(arrayPlatformDE);
            }
            
           //Create class comparison DEs
            /*
             * This following code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumCaseChecker for 
             * enlightenment.
             */
           MultiGroupComparisonAdjustmentType mgAdjustmentType; 
           String multiGroupComparisonAdjustmentTypeString= EnumCaseChecker.getEnumTypeName(classComparisonQueryForm.getComparisonAdjustment(),MultiGroupComparisonAdjustmentType.values());
           if(multiGroupComparisonAdjustmentTypeString!=null) {
        	   mgAdjustmentType = MultiGroupComparisonAdjustmentType.valueOf(multiGroupComparisonAdjustmentTypeString);
           }else {
        	   	logger.error("Invalid MultiGroupComparisonAdjustmentType value given in request");
           		logger.error("Selected MultiGroupComparisonAdjustmentType value = "+classComparisonQueryForm.getComparisonAdjustment());
           		logger.error("Using the default MultiGroupComparisonAdjustmentType value = "+ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE);
           		mgAdjustmentType = ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE;
           }
           MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(mgAdjustmentType);  ;
           if(!classComparisonQueryForm.getComparisonAdjustment().equalsIgnoreCase("NONE")){
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.adjustedpValue);
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
            }
            else{
            	StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.pValue);  
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
                
            }
           
            if(classComparisonQueryForm.getStatisticalMethod() != "" || classComparisonQueryForm.getStatisticalMethod().length() != 0){
            	/*
                 * This following code is here to deal with an observed problem with the changing 
                 * of case in request parameters.  See the class EnumCaseChecker for 
                 * enlightenment.
                 */
            	StatisticalMethodType statisticalMethodType; 
            	String statisticalMethodTypeString= EnumCaseChecker.getEnumTypeName(classComparisonQueryForm.getStatisticalMethod(),StatisticalMethodType.values());
                 if(statisticalMethodTypeString!=null) {
                	 statisticalMethodType = StatisticalMethodType.valueOf(statisticalMethodTypeString);
                 }else {
              	   	logger.error("Invalid StatisticalMethodType value given in request");
             		logger.error("Selected StatisticalMethodType value = "+classComparisonQueryForm.getStatisticalMethod());
             		logger.error("Using the default StatisticalMethodType type of :"+ERROR_STATISTICAL_METHOD_TYPE);
             		statisticalMethodType = ERROR_STATISTICAL_METHOD_TYPE;
                 }
                StatisticTypeDE statisticTypeDE = new StatisticTypeDE(statisticalMethodType);
                classComparisonQueryDTO.setStatisticTypeDE(statisticTypeDE);
            }
            
            // JB:Begin - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
        	
            // Clean up temporary "REST OF..." lists created earlier
    	    userListBean.removeList("REST OF ALL SAMPLES");
    	    userListBean.removeList("REST OF ALL GLIOMAS");
        	userListBeanHelper.addBean(session.getId(),CacheConstants.USER_LISTS,userListBean);
        	
            // JB:End - #5677 (2.0) add a  &quot;vs. rest of samples&quot; option
            
            return classComparisonQueryDTO;
    }
    
	
	protected Map getKeyMethodMap() {
		 
       HashMap<String,String> map = new HashMap<String,String>();
              
       //Setup
       map.put("ClassComparisonAction.setup", "setup");

       //Submit Query Button using class comparison submittal method
       map.put("buttons_tile.submittalButton", "submit");

       return map;
       
       }
	
	public List<String> validateFormData() {
		List<String> errors = new ArrayList<String>();
                
        //Analysis Query Name cannot be blank
        errors.addAll(UIFormValidator.validateAnalysisName(getClassComparisonForm().getAnalysisResultName(), errors));
        
        //User must select exactly 2 comparison Groups if not for FTest
        if (!"FTest".equals(getClassComparisonForm().getStatisticalMethod()))
        	errors.addAll(UIFormValidator.validateSelectedGroups(getClassComparisonForm().getSelectedGroups(), errors));
        //otherwise, it must be at least two.
        else
        	errors.addAll(UIFormValidator.validateSelectedGroups(getClassComparisonForm().getSelectedGroups(), 2, errors));
        
        //look at the manual FC to check for neg value
        //this is validated in the UI, so its put here only as a failsafe
        if(getClassComparisonForm().getFoldChangeManual() < 0){
        	getClassComparisonForm().setFoldChangeManual(Math.abs(getClassComparisonForm().getFoldChangeManual()));
        }
        
        return errors;
	}
	
	public ClassComparisonForm getClassComparisonForm() {
		if( classComparisonForm == null ) {
			classComparisonForm = new ClassComparisonForm();
		}
		
		return classComparisonForm;
	}

	public void setClassComparisonForm(ClassComparisonForm classComparisonForm) {
		this.classComparisonForm = classComparisonForm;
	}
	
	public ClassComparisonForm getForm() {
		return getClassComparisonForm();
	}
	/*
	public void setAnalysisResultName(String name) {
		getClassComparisonForm().setAnalysisResultName(name);
	}
	*/
}
