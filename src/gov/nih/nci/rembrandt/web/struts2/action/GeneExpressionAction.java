/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.util.ApplicationContext;
import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.RembrandtAsynchronousFindingManagerImpl;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ChromosomeBean;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ChromosomeHelper;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.helper.ListConvertor;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;
import gov.nih.nci.rembrandt.web.struts2.form.GeneExpressionForm;
import gov.nih.nci.rembrandt.web.struts2.form.UIFormValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


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

public class GeneExpressionAction extends ActionSupport implements SessionAware, ServletRequestAware, Preparable {
//extends LookupDispatchAction {
    private static Logger logger = Logger.getLogger(GeneExpressionAction.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private UserCredentials credentials;
    
    HttpServletRequest servletRequest;
    Map<String, Object> sessionMap;
    
    GeneExpressionForm geneExpressionForm;
    GeneExpressionForm geneExpressionFormInSession;
    
    
    String chromosomeNumber;
    
    
	@Override
	public void prepare() throws Exception {
		
		geneExpressionFormInSession = (GeneExpressionForm)sessionMap.get("geneExpressionForm");
		if (geneExpressionFormInSession == null) {
			geneExpressionFormInSession = new GeneExpressionForm();
			sessionMap.put("geneExpressionForm", geneExpressionFormInSession);
		}
		
		//Do we reset everytime
		geneExpressionFormInSession.reset(this.servletRequest);
		//geneExpressionForm = form;
	}

	/**
     * Method setup
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
    
    //Setup the gene Expression form from menu page
    public String setup() {
	//throws Exception {
    	
    	String sID = this.servletRequest.getHeader("Referer");
    	
    	// prevents Referer Header injection
    	if ( sID != null && sID != "" && !sID.contains("rembrandt")) {
    		return "failure";
    	}
    	
    	geneExpressionForm = geneExpressionFormInSession;

		//GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
		//Since Chromosomes is a static variable there is no need to set it twice.
		//It is only a lookup option collection
		if(geneExpressionForm.getChromosomes()==null||geneExpressionForm.getChromosomes().isEmpty()) {
			//set the chromsomes list in the form 
			logger.debug("Setup the chromosome values for the form");
			geneExpressionForm.setChromosomes(ChromosomeHelper.getInstance().getChromosomes());
		}
       //GroupRetriever groupRetriever = new GroupRetriever();
      // geneExpressionForm.setSavedSampleList(groupRetriever.getClinicalGroupsCollectionNoPath(request.getSession()));
        
		//saveToken(request);
       
		sessionMap.put("geneExpressionForm", geneExpressionForm);
        return "backToGeneExp";
    }
    
    /*This method is needed for the apparant problem with LookupDispatchAction class in Struts
     *  (non-Javadoc)
     * Doesn't appear that the developer can make a call to any of the methods in this
     * class via a url path (e.g. myaction.do?method=setup). The work around is not specifying
     * a method, in which case struts will call the following "unspecified" method call below.
     * In this case the desired effect is to reset the form(setup) with the prefilled dropdowns...
     * so ALL this method does is call the setup method.
     */
    /**TODO change the action to a DispatchAction for more flexibility in the future.
     * -KR
     */
    
    public String unspecified()
    throws Exception {
        this.setup();
        //saveToken(request);

        return "backToGeneExp";
    }
    
   //if multiUse button clicked (with styles de-activated) forward back to page
    public String multiUse()
	throws Exception {
        //saveToken(request);

		return "backToGeneExp";
    }
    
    
    
    /**
     * Method submitAllGenes
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
    
    //If this is an All Genes submit
    public String submitAllGenes()
	throws Exception {
    	
//        if (!isTokenValid(request)) {
//			return mapping.findForward("failure");
//		}
        
        sessionMap.put("currentPage", "0");
		//request.getSession().removeAttribute("currentPage2");
        sessionMap.remove("currentPage2");
		
        GeneExpressionForm geneExpressionForm = geneExpressionFormInSession;
         
		geneExpressionForm.setGeneGroup("");
		geneExpressionForm.setRegulationStatus("up");
		geneExpressionForm.setFoldChangeValueUp(RembrandtConstants.ALL_GENES_GENE_EXP_REGULATION);
		geneExpressionForm.setFoldChangeValueUDUp(RembrandtConstants.ALL_GENES_GENE_EXP_REGULATION);
		geneExpressionForm.setFoldChangeValueDown(RembrandtConstants.ALL_GENES_GENE_EXP_REGULATION);
		geneExpressionForm.setFoldChangeValueUDDown(RembrandtConstants.ALL_GENES_GENE_EXP_REGULATION);
		logger.debug("set former gene values to null");
		logger.debug("This is an All Genes Gene Expression Submital");
        
		//resetToken(request);
		sessionMap.put("geneExpressionForm", geneExpressionForm);
		return "showAllGenes";
    }
    
    /**
     * Method submitStandard
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
    
    //If this is a standard submit
    public String submitStandard()
	throws Exception {
        sessionMap.put("currentPage", "0");
		sessionMap.remove("currentPage2");
		
		//GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
		// set form back to standard state and clear default value
		geneExpressionFormInSession.setRegulationStatus("");
		geneExpressionFormInSession.setFoldChangeValueUp(RembrandtConstants.STANDARD_GENE_EXP_REGULATION);
		geneExpressionFormInSession.setFoldChangeValueUDUp(RembrandtConstants.STANDARD_GENE_EXP_REGULATION);
		geneExpressionFormInSession.setFoldChangeValueDown(RembrandtConstants.STANDARD_GENE_EXP_REGULATION);
		geneExpressionFormInSession.setFoldChangeValueUDDown(RembrandtConstants.STANDARD_GENE_EXP_REGULATION);
		
		logger.debug("This is an Standard Gene Expression Submital");
		
		geneExpressionForm = geneExpressionFormInSession;
		//sessionMap.put("geneExpressionForm", geneExpressionForm);
		return "backToGeneExp";
    }
    
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
    //If this is a Submittal do the following	
	public String submittal()
			throws Exception {
//        if (!isTokenValid(request)) {
//			return mapping.findForward("failure");
//		}
        
		sessionMap.put("currentPage", "0");
		sessionMap.remove("currentPage2");
		String sessionId = this.servletRequest.getSession().getId();
		//GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
 
	 /*The following 15 lines of code/logic will eventually need to be moved/re-organized. All Genes queries should have their own actions, forms, etc. For
	  * now, in order to properly validate an all genes query and STILL be able to forward back to
	  * the appropriate query page (tile order), this logic has been placed in the action itself, so
	  * that there can be proper forwarding when errors are generated from an all genes submission.
	  * This logic checks if the query is an all genes query and then if the fold change value is
	  * less than 4 with a corresponding regulation status uplon submission. If it is less than 4,
	  * an error is created and sent with the request to the forward.
	  */
		   if (geneExpressionForm.getIsAllGenes()){
		      try{
		        int intFoldChangeValueUp = Integer.parseInt(geneExpressionForm.getFoldChangeValueUp());
		        int intFoldChangeValueDown = Integer.parseInt(geneExpressionForm.getFoldChangeValueDown());
		        int intFoldChangeValueUDUp = Integer.parseInt(geneExpressionForm.getFoldChangeValueUDUp());
		        int intFoldChangeValueUDDown = Integer.parseInt(geneExpressionForm.getFoldChangeValueUDDown());
			        if((intFoldChangeValueUp < 4 && geneExpressionForm.getRegulationStatus().equalsIgnoreCase("up")) || 
			                (intFoldChangeValueDown < 4 && geneExpressionForm.getRegulationStatus().equalsIgnoreCase("down")) ||
			                (intFoldChangeValueUDUp < 4 && geneExpressionForm.getRegulationStatus().equalsIgnoreCase("updown")) || 
			                (intFoldChangeValueUDDown < 4 && geneExpressionForm.getRegulationStatus().equalsIgnoreCase("updown"))){
			            
			        	String msg = ApplicationContext.getLabelProperties().getProperty(
			        			"gov.nih.nci.nautilus.ui.struts.form.regulationStatus.allGenes.error");
			        	addActionError(msg);
					    return "showAllGenes"; 
			        }
		      }catch (NumberFormatException ex) {
		            String msg = ApplicationContext.getLabelProperties().getProperty(
		        			"gov.nih.nci.nautilus.ui.struts.form.regulationStatus.allGenes.error");
		        	addActionError(msg);
				    return "showAllGenes";    
	  		    }
		    }
		   //All Genes validation ENDS HERE 
		   
		
		// Create Query Objects
		GeneExpressionQuery geneExpQuery = createGeneExpressionQuery(geneExpressionForm, this.servletRequest.getSession());
	    
        //Check user credentials and constrain query by Institutions
        if(geneExpQuery != null){
        	geneExpQuery.setInstitutionCriteria(InsitutionAccessHelper.getInsititutionCriteria(this.servletRequest.getSession()));
            }
        logger.debug("This is a Gene Expression Submital");
	   
		if (!geneExpQuery.isEmpty()) {
			SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
            queryBag.putQuery(geneExpQuery, geneExpressionForm);
            presentationTierCache.putSessionQueryBag(sessionId, queryBag);
            
     	} else {			
			String msg = ApplicationContext.getLabelProperties().getProperty(
        			"gov.nih.nci.nautilus.ui.struts.form.query.geneexp.error");
        	addActionError(msg);
		    return "backToGeneExp"; 
		}
        
		//resetToken(request);
        
		return "advanceSearchMenu";
	}

	/**
	 * This action is called when the user has selected the preview
	 * button on the GeneExpression build query page.  It takes
	 * the current values that the user has input and creates a 
	 * GeneExpressionQuery.  It then creates a CompoundQuery with
	 * the query and gives it a temp name. It then calls the 
	 * ReportGeneratorHelper which will construct a report and drop
	 * it in the sessionCache for later retrieval for rendering in a jsp.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String preview()
			throws Exception {
//        if (!isTokenValid(request)) {
//			return mapping.findForward("failure");
//		}
        
		sessionMap.put("currentPage", "0");
		sessionMap.remove("currentPage2");
		//GeneExpressionForm geneExpressionForm = (GeneExpressionForm) form;
		logger.debug("This is a Gene Expression Preview");
		// Create Query Objects
		GeneExpressionQuery geneExpQuery = createGeneExpressionQuery(geneExpressionForm, this.servletRequest.getSession());
        if(geneExpQuery != null){
        	geneExpQuery.setInstitutionCriteria(InsitutionAccessHelper.getInsititutionCriteria(this.servletRequest.getSession()));
            }
	    this.servletRequest.setAttribute("previewForm",geneExpressionForm.cloneMe());
        logger.debug("This is a Preview Report");
	    CompoundQuery compoundQuery = new CompoundQuery(geneExpQuery);
	    //compoundQuery.setQueryName(RembrandtConstants.PREVIEW_RESULTS);
        compoundQuery.setQueryName(RembrandtConstants.PREVIEW_RESULTS);
        logger.debug("Setting query name to:"+compoundQuery.getQueryName());
	    compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        logger.debug("Associated View for the Preview:"+compoundQuery.getAssociatedView().getClass());
	    //Save the sessionId that this preview query is associated with
        compoundQuery.setSessionId(this.servletRequest.getSession().getId());
        //Generate the reportXML for the preview.  It will be stored in the session
	    //cache for later retrieval
        //request.getSession().setAttribute("emailQueryName", geneExpressionForm.getQueryName());
        //ReportGeneratorHelper reportHelper = new ReportGeneratorHelper(compoundQuery, new HashMap());
        //return mapping.findForward("previewReport");
        
        
        RembrandtAsynchronousFindingManagerImpl asynchronousFindingManagerImpl = new RembrandtAsynchronousFindingManagerImpl();
        try {
			asynchronousFindingManagerImpl.submitQuery(this.servletRequest.getSession(), compoundQuery);
		} catch (FindingsQueryException e) {
			logger.error(e.getMessage());
		}
		//wait for few seconds for the jobs to get added to the cache
		Thread.sleep(1000);
        //resetToken(request);

        return "viewResults";
	}
	
	public String getCytobands()
			throws Exception {

		GeneExpressionForm geForm = geneExpressionForm;
		//validateGetCytobands()
		
		//This is the static list of chromosomes that is fetched the first time it is needed
		List chromosomes = geneExpressionFormInSession.getChromosomes();
		
		//IMPORTANT! geForm.chromosomeNumber is NOT the chromosome number.  It is the index
		//into the static chromosomes list where the chromosome can be found.
		String chromosomeIndex = geForm.getChromosomeNumber();
		if(!"".equals(chromosomeIndex)) {
			ChromosomeBean bean = (ChromosomeBean)chromosomes.get(Integer.parseInt(chromosomeIndex));
			//geForm.setCytobands(bean.getCytobands());
			List cyto = bean.getCytobands();
			geneExpressionFormInSession.setChromosomeNumber(chromosomeIndex);
			geneExpressionFormInSession.setChromosomeRegionCriteria(chromosomeIndex);
			geneExpressionFormInSession.setCytobands(bean.getCytobands());
	
		}

		geneExpressionForm = geneExpressionFormInSession;
		//sessionMap.put("geneExpressionForm", geneExpressionForm);
		return "backToGeneExp";
	}
			
			
	
	
	private GeneExpressionQuery createGeneExpressionQuery(GeneExpressionForm geneExpressionForm, HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        
        GeneExpressionQuery geneExpQuery = (GeneExpressionQuery)QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	    geneExpQuery.setQueryName(geneExpressionForm.getQueryName());
		// Change this code later to get view type directly from Form !!
	    String thisView = geneExpressionForm.getResultView();
		if (thisView.equalsIgnoreCase("sample")) {
			geneExpQuery.setAssociatedView(ViewFactory
					.newView(ViewType.CLINICAL_VIEW));
		} else if (thisView.equalsIgnoreCase("gene")) {
			geneExpQuery.setAssociatedView(ViewFactory
					.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
		}
		// Set gene criteria
		GeneIDCriteria geneIDCrit = geneExpressionForm.getGeneIDCriteria();
		//for some reason the gene converter is not with the sample convertor
		//see ListConvertor
		
		//if(geneIDCrit.isEmpty() && geneExpressionForm.getGeneOption().equalsIgnoreCase("geneList")){
		if(geneIDCrit.isEmpty() && (geneExpressionForm.getGeneOption() != null && geneExpressionForm.getGeneOption().equalsIgnoreCase("geneList"))){
			Collection<GeneIdentifierDE> genes = null;
			UserList geneList = helper.getUserList(geneExpressionForm.getGeneFile());
			if(geneList!=null){
				try {	
					//assumes geneList.getListSubType!=null && geneList.getListSubType().get(0) !=null
					genes = ListConvertor.convertToGeneIdentifierDE(geneList.getList(), geneList.getListSubType()); //StrategyHelper.convertToSampleIDDEs(geneList.getList());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!genes.isEmpty()){
					geneIDCrit.setGeneIdentifiers(genes);
				}
			}
		}
		
		if (!geneIDCrit.isEmpty())	{
			geneExpQuery.setGeneIDCrit(geneIDCrit);
		}
		
		//Set sample Criteria
        SampleCriteria sampleIDCrit = geneExpressionForm.getSampleCriteria();
        Collection<SampleIDDE> sampleIds = null;
        if( geneExpressionForm.getSampleGroup()!=null && geneExpressionForm.getSampleGroup().equalsIgnoreCase("Upload")){
           UserList sampleList = helper.getUserList(geneExpressionForm.getSampleFile());
           if(sampleList!=null){
               try {
                   Set<String>list = new HashSet<String>(sampleList.getList());
     				//get the samples associated with these specimens
     				List<String> samples = LookupManager.getSpecimenNames(list);
     				//Add back any samples that were just sampleIds to start with
     				if(samples != null){
     					list.addAll(samples);
     				}
     				sampleIds = ListConvertor.convertToSampleIDDEs(list);
               } catch (OperationNotSupportedException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
               if(!sampleIds.isEmpty()){
                   sampleIDCrit.setSampleIDs(sampleIds);
                   sampleIDCrit.setSampleFile(geneExpressionForm.getSampleFile());
                   sampleIDCrit.setSampleGroup(geneExpressionForm.getSampleGroup());
               }
           }
       }
        
       if ( geneExpressionForm.getSampleGroup()!=null && geneExpressionForm.getSampleGroup().equalsIgnoreCase("Specify")){
    	   geneExpressionForm.setSampleList(geneExpressionForm.getSampleList());
    	   sampleIDCrit = geneExpressionForm.getSampleCriteria();
           sampleIDCrit.setSampleGroup(geneExpressionForm.getSampleGroup());
       }
		if(geneExpressionForm.getExcludeResections() == true){
			sampleIDCrit.setExcludeResections(true);
		}else{
			sampleIDCrit.setExcludeResections(null);
		}
		if (sampleIDCrit != null && !sampleIDCrit.isEmpty())
			geneExpQuery.setSampleIDCrit(sampleIDCrit);
        
		AllGenesCriteria allGenesCrit = geneExpressionForm.getAllGenesCriteria();
		if (!allGenesCrit.isEmpty())
		    geneExpQuery.setAllGenesCrit(allGenesCrit);
		FoldChangeCriteria foldChangeCrit = geneExpressionForm.getFoldChangeCriteria();
		if (!foldChangeCrit.isEmpty())
			geneExpQuery.setFoldChgCrit(foldChangeCrit);
		RegionCriteria regionCrit = geneExpressionForm.getRegionCriteria();
		if (!regionCrit.isEmpty()) {
			regionCrit.setRegion(geneExpressionForm.getRegion());
			geneExpQuery.setRegionCrit(regionCrit);
		}
		DiseaseOrGradeCriteria diseaseOrGradeCriteria = geneExpressionForm
				.getDiseaseOrGradeCriteria();
		if (!diseaseOrGradeCriteria.isEmpty())
			geneExpQuery.setDiseaseOrGradeCrit(diseaseOrGradeCriteria);
		
		CloneOrProbeIDCriteria cloneOrProbeIDCriteria = geneExpressionForm.getCloneOrProbeIDCriteria();
		if(cloneOrProbeIDCriteria.isEmpty() && geneExpressionForm.getCloneId().equalsIgnoreCase("cloneList")){
			Collection<CloneIdentifierDE> clones = null;
			UserList cList = helper.getUserList(geneExpressionForm.getCloneListFile());
			if(cList!=null){
				try {	
					//assumes geneList.getListSubType!=null && geneList.getListSubType().get(0) !=null
					clones = ListConvertor.convertToCloneIdentifierDE(cList.getList(), cList.getListSubType()); //StrategyHelper.convertToSampleIDDEs(geneList.getList());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!clones.isEmpty()){
					cloneOrProbeIDCriteria.setIdentifiers(clones);
				}
			}
		}
		if(!cloneOrProbeIDCriteria.isEmpty())	{
			geneExpQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
		}
		
		
		GeneOntologyCriteria geneOntologyCriteria = geneExpressionForm
				.getGeneOntologyCriteria();
		if (!geneOntologyCriteria.isEmpty())
			geneExpQuery.setGeneOntologyCrit(geneOntologyCriteria);
		PathwayCriteria pathwayCriteria = geneExpressionForm
				.getPathwayCriteria();
		if (!pathwayCriteria.isEmpty())
			geneExpQuery.setPathwayCrit(pathwayCriteria);
		ArrayPlatformCriteria arrayPlatformCriteria = geneExpressionForm
				.getArrayPlatformCriteria();
		if (!arrayPlatformCriteria.isEmpty()) {
			geneExpQuery.setArrayPlatformCrit(arrayPlatformCriteria);
		}else {
			/*
			 * This logic is required for an all genes query.  There
			 * must be an ArrayPlatformDE specified for the all gene's
			 * query, and there was not one being created.  This is 
			 * probably a hack as we may later allow the user to select
			 * from the a list of platforms, and all could be the default.
			 * --Dave
			 */
			arrayPlatformCriteria = new ArrayPlatformCriteria();
			arrayPlatformCriteria.setPlatform(new ArrayPlatformDE(Constants.ALL_PLATFROM));
			geneExpQuery.setArrayPlatformCrit(arrayPlatformCriteria);
		}
		
		if( geneExpQuery.getFoldChgCrit() != null )
			geneExpQuery.getFoldChgCrit().setRegulationStatus(geneExpressionForm.getRegulationStatus() );
        
	    return geneExpQuery;
	}
	
	protected Map getKeyMethodMap() {
		 
       HashMap<String,String> map = new HashMap<String,String>();
       //Gene Expression Query Button using gene expression setup method
       map.put("GeneExpressionAction.setupButton", "setup");
       
       //Submit Query Button using gene expression submittal method
       map.put("buttons_tile.submittalButton", "submittal");
       
       //Preview Query Button using gene expression preview method
       map.put("buttons_tile.previewButton", "preview");
       
       //Submit All Genes Button using gene expression submitAllGenes method
       map.put("buttons_tile.submitAllGenes", "submitAllGenes");
       
       //Submit Standard Button using gene expression submitStandard method
       map.put("buttons_tile.submitStandard", "submitStandard");
       
       //Submit to get the cytobands of the selected chromosome
       map.put("GeneExpressionAction.getCytobands", "getCytobands");
       
       //Submit nothing if multiuse button entered if css turned off
       map.put("buttons_tile.multiUseButton", "multiUse");
       
       return map;
       
       }
	
	public void validateGetCytobandsxx() {
		// if method is "getCytobands" AND they have upload formFiles, do necessary validation for uploaded files
		if ((this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getGeneGroup().equalsIgnoreCase("Upload"))
				|| (this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getCloneId().equalsIgnoreCase("Upload"))
				|| (this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getSampleGroup().equalsIgnoreCase("Upload"))){

			if(this.geneExpressionForm.getGeneGroup().equalsIgnoreCase("Upload")){
				this.geneExpressionForm.setGeneGroup("");
			}
			if(this.geneExpressionForm.getCloneId().equalsIgnoreCase("Upload")){
				this.geneExpressionForm.setCloneId("");
			}
			if(this.geneExpressionForm.getSampleGroup().equalsIgnoreCase("Upload")){
				this.geneExpressionForm.setSampleGroup("");
			}
		}
	}
	
	public void validatex() {

		List<String> errors = new ArrayList<String>();
		
		//if (this.geneExpressionForm == null)
		//	this.geneExpressionForm = new GeneExpressionForm();

		// if method is "getCytobands" AND they have upload formFiles, do necessary validation for uploaded files
//		try{
//			if ((this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getGeneGroup().equalsIgnoreCase("Upload"))
//					|| (this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getCloneId().equalsIgnoreCase("Upload"))
//					|| (this.geneExpressionForm.getMethod().equalsIgnoreCase("GetCytobands") && this.geneExpressionForm.getSampleGroup().equalsIgnoreCase("Upload"))){
//
//				//errors = UIFormValidator.validateFormFieldsWithRegion(geneFile, geneGroup, cloneListFile, cloneId, sampleFile, sampleGroup, errors);
//
//				if(this.geneExpressionForm.getGeneGroup().equalsIgnoreCase("Upload")){
//					this.geneExpressionForm.setGeneGroup("");
//				}
//				if(this.geneExpressionForm.getCloneId().equalsIgnoreCase("Upload")){
//					this.geneExpressionForm.setCloneId("");
//				}
//				if(this.geneExpressionForm.getSampleGroup().equalsIgnoreCase("Upload")){
//					this.geneExpressionForm.setSampleGroup("");
//				}
//			}
//		}catch(NullPointerException e){
//			logger.debug("something was set to null");
//		}

		// if the method of the button is "submit" or "run report", validate
		if (this.geneExpressionForm.getMethod()!=null && (this.geneExpressionForm.getMethod().equalsIgnoreCase("submit")
				|| this.geneExpressionForm.getMethod().equalsIgnoreCase("preview"))) {

			// Query Name cannot be blank
			errors = UIFormValidator.validateQueryName(this.geneExpressionForm.getQueryName(), errors);
			// Chromosomal region validations
			errors = UIFormValidator.validateChromosomalRegion(this.geneExpressionForm.getChromosomeNumber(),
					this.geneExpressionForm.getRegion(), this.geneExpressionForm.getCytobandRegionStart(),
					this.geneExpressionForm.getBasePairStart(), this.geneExpressionForm.getBasePairEnd(), errors);
			// Validate Go Classification
			errors = UIFormValidator.validateGOClassification(this.geneExpressionForm.getGoClassification(), errors);

			//why is this commented out ?
			/* Validate Gene List, Gene File and Gene Group
			errors = UIFormValidator.validateTextFileType(sampleFile, 
			        "sampleGroup", errors);
			 */

			// validate fold change,it has to be numeric
			String regulationStatus = this.geneExpressionForm.getRegulationStatus();
			errors = UIFormValidator.validateFoldChange(regulationStatus,"up",
					this.geneExpressionForm.getFoldChangeValueUp(),"foldChangeValueUp",errors);
			errors = UIFormValidator.validateFoldChange(regulationStatus,"updown",
					this.geneExpressionForm.getFoldChangeValueUDUp(),"foldChangeValueUDUp",errors);
			errors = UIFormValidator.validateFoldChange(regulationStatus,"down",
					this.geneExpressionForm.getFoldChangeValueDown(),"foldChangeValueDown",errors);
			errors = UIFormValidator.validateFoldChange(regulationStatus,"updown",
					this.geneExpressionForm.getFoldChangeValueUDDown(),"foldChangeValueUDDown",errors);
			errors = UIFormValidator.validateFoldChange(regulationStatus,"unchange",
					this.geneExpressionForm.getFoldChangeValueUnchangeFrom(),"foldChangeValueUnchangeFrom",errors);
			errors = UIFormValidator.validateFoldChange(regulationStatus,"unchange",
					this.geneExpressionForm.getFoldChangeValueUnchangeTo(),"foldChangeValueUnchangeTo",errors);

			// Validate minimum criteria's for GE Query
			if (this.geneExpressionForm.getQueryName() != null
					&& this.geneExpressionForm.getQueryName().length() >= 1
					&& (this.geneExpressionForm.getGeneOption() != null && this.geneExpressionForm.getGeneOption().equalsIgnoreCase("standard"))) {
				if (
						(this.geneExpressionForm.getGeneList() == null || this.geneExpressionForm.getGeneList().trim().length() < 1)
						&& (this.geneExpressionForm.getCloneId() == null || this.geneExpressionForm.getCloneId()
						.trim().length() < 1)
						&& (this.geneExpressionForm.getChromosomeNumber() == null || this.geneExpressionForm
						.getChromosomeNumber().trim().length() < 1)
						&& (this.geneExpressionForm.getGoClassification() == null || this.geneExpressionForm
						.getGoClassification().trim().length() < 1)
						&& (this.geneExpressionForm.getPathways() == null || this.geneExpressionForm.getPathways()
						.trim().length() < 1)) {

					String msg = ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.ge.minimum.error");
					addActionError(msg);
				}
			}
		}else {
			logger.debug("This isn't submit or preview report");
		}

	}

	public GeneExpressionForm getGeneExpressionForm() {
		return geneExpressionForm;
	}

	public void setGeneExpressionForm(GeneExpressionForm geneExpressionForm) {
		this.geneExpressionForm = geneExpressionForm;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.servletRequest = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.sessionMap = arg0;
	}

	public String getChromosomeNumber() {
		return chromosomeNumber;
	}

	public void setChromosomeNumber(String chromosomeNumber) {
		this.chromosomeNumber = chromosomeNumber;
	}

	
	
}