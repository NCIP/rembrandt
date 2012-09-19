package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.task.GPTask;
import gov.nih.nci.caintegrator.service.task.GPTask.TaskType;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.struts.form.GpIntegrationForm;
import gov.nih.nci.rembrandt.web.struts.form.IgvIntegrationForm;
import gov.nih.nci.caintegrator.enumeration.AnalysisType;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.genepattern.client.GPClient;
import org.genepattern.webservice.Parameter;

public class IgvIntegrationAction extends GPIntegrationAction {
	
	private static Logger logger = Logger.getLogger(GPIntegrationAction.class);
	 
	public ActionForward setup(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	    	IgvIntegrationForm igvForm = (IgvIntegrationForm) form;
	        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
	        GroupRetriever groupRetriever = new GroupRetriever();
	        igvForm.setExistingGroupsList(groupRetriever.getClinicalGroupsCollection(request.getSession()));
	        igvForm.setAnnotationsList(getAnnotationsCollection());  
	    
	        return mapping.findForward("success");
	    }

	@Override
	protected Map getKeyMethodMap() {
		 
       HashMap<String,String> map = new HashMap<String,String>();
              
       //Setup
       map.put("IgvIntegrationAction.setup", "setup");

       //Submit Query Button using class comparison submittal method
       map.put("buttons_tile.submittalButton", "submit");

       return map;
       
	   }
	
	private List<LabelValueBean> getAnnotationsCollection(){
		List<LabelValueBean> annotationsCollection = new ArrayList<LabelValueBean>();
		
		annotationsCollection.add(new LabelValueBean("Subject ID", "subject_id"));
		annotationsCollection.add(new LabelValueBean("Disease Type", "disease_type"));
		annotationsCollection.add(new LabelValueBean("Whole Grade", "whole_grade"));
		annotationsCollection.add(new LabelValueBean("Age Range", "age_range"));
		annotationsCollection.add(new LabelValueBean("Gender", "gender"));
		annotationsCollection.add(new LabelValueBean("Range", "survival_length_range"));
		annotationsCollection.add(new LabelValueBean("Censoring Status", "censoring_status"));
		
        return annotationsCollection;
    
    }

    @SuppressWarnings("unchecked")
	public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
     
	   IgvIntegrationForm igvForm = (IgvIntegrationForm) form;
	   String sessionId = request.getSession().getId();
       HttpSession session = request.getSession();
       
   	   String[] patientGroups = igvForm.getSelectedGroups();
   	   

   	   
   	   String platformName = igvForm.getArrayPlatform();
   	   String snpPlatformName = igvForm.getSnpArrayPlatform();
   	   String snpAnalysis = igvForm.getSnpAnalysis();
   	   platformName = platformName!=null?platformName:"";
   	   snpPlatformName = snpPlatformName!=null?snpPlatformName:"";
   	   //Now get the R-binary file name:
		
		String r_fileName = null;
		String a_fileName = null;
		String cn_fileName = null;
		
		if(platformName.equalsIgnoreCase(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()) && 
				snpPlatformName.equalsIgnoreCase(ArrayPlatformType.AFFY_100K_SNP_ARRAY.toString() ) ) {
			
			r_fileName = System.getProperty("gov.nih.nci.rembrandt.affy_data_matrix");
			a_fileName = System.getProperty("gov.nih.nci.rembrandt.affy_data_annotation_igv");
		    AnalysisType analysisType = getAnalysisType(snpAnalysis);
		    cn_fileName = getCNFileName(analysisType);
		    List<String> filePathList = extractPatientGroups(request, session, patientGroups, analysisType,"ge-cp");
			runGpExpSegTask( request, igvForm,  session, filePathList,  r_fileName,  cn_fileName,  a_fileName);
		}
		else if(platformName.equalsIgnoreCase(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString())) {
	   
			r_fileName = System.getProperty("gov.nih.nci.rembrandt.affy_data_matrix");
			a_fileName = System.getProperty("gov.nih.nci.rembrandt.affy_data_annotation_igv");
		   List<String> filePathList = extractPatientGroups(request, session, patientGroups, null,"ge");
		   runGpTask(request, igvForm, session, filePathList, r_fileName, a_fileName);

		 
	   }
	   else if(snpPlatformName.equalsIgnoreCase(ArrayPlatformType.AFFY_100K_SNP_ARRAY.toString())) {
		   AnalysisType analysisType = getAnalysisType(snpAnalysis);
		   r_fileName = getCNFileName(analysisType);
		   List<String> filePathList = extractPatientGroups(request, session, patientGroups,analysisType,"cp");
   
		   runGpSegTask(request, igvForm, session, filePathList, r_fileName, a_fileName);
	   }


       request.setAttribute("gpTaskType", "IGV");
       
    	return mapping.findForward("viewJob");
    }
    

	protected GPTask createGpTask(String tid, String analysisResultName) {
		GPTask gpTask = new GPTask(tid, analysisResultName, FindingStatus.Running, TaskType.IGV_GENE_EXP);
		return gpTask;
	}
	
	protected GPTask createGpSegTask(String tid, String analysisResultName) {
		GPTask gpTask = new GPTask(tid, analysisResultName, FindingStatus.Running, TaskType.IGV_COPY_NUMBER);
		return gpTask;
	}

	protected void runGpSegTask(HttpServletRequest request,
			GpIntegrationForm gpForm, HttpSession session,
			List<String> filePathList, String r_fileName, String a_fileName)
			throws Exception {
		//		*** RUN TASK ON THE GP SERVER
				String tid = "209";
				String gpModule =  System.getProperty("gov.nih.nci.caintegrator.gp.seg.modulename");						
				String rembrandtUser = null;
				GPClient gpClient = null;
				String ticketString = "";
				String analysisResultName = gpForm.getAnalysisResultName();
				
				String gpserverURL = System.getProperty("gov.nih.nci.caintegrator.gp.server")!=null ? 
				(String)System.getProperty("gov.nih.nci.caintegrator.gp.server") : "localhost:8080"; //default to localhost
				try {
				 	   rembrandtUser = retreiveRembrandtUser(request, session, rembrandtUser);
						
						ticketString = retrieveTicketString(rembrandtUser, gpserverURL);
			            
						String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
						gpClient = new GPClient(gpserverURL, rembrandtUser, password);
						int size = filePathList.size();
						Parameter[] par = new Parameter[5];
						int currpos= 0;
					
						par[currpos] = new Parameter("input.file" , filePathList.get(currpos));

						par[++currpos] = new Parameter("project.name", "rembrandt");

						//r_fileName = "'/usr/local/genepattern/resources/DataMatrix_ISPY_306cDNA_17May07.Rda'";
						par[++currpos] = new Parameter("array.filename", r_fileName);
						
						par[++currpos] = new Parameter("analysis.name", analysisResultName);

						//always just 2
						par[++currpos] = new Parameter("output.seg.file",analysisResultName+".seg");
						
						//JobResult preprocess = gpServer.runAnalysis(gpModule, par);
						int nowait = gpClient.runAnalysisNoWait(gpModule, par);

						tid = String.valueOf(nowait);
						//LSID = urn:lsid:8080.root.localhost:genepatternmodules:20:2.1.7
						request.setAttribute("jobId", tid);
						request.setAttribute("gpStatus", "running");
						session.setAttribute("genePatternServer", gpClient);
						request.setAttribute("genePatternURL", ticketString);
						request.getSession().setAttribute("gptid", tid);
						request.getSession().setAttribute("gpUserId", rembrandtUser);
						request.getSession().setAttribute("ticketString", ticketString);
						GPTask gpTask = createGpSegTask(tid, analysisResultName);
						RembrandtPresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
						_cacheManager.addNonPersistableToSessionCache(request.getSession().getId(), "latestGpTask",(Serializable) gpTask); 
						
					} catch (Exception e) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						logger.error(sw.toString());
						logger.error(gpModule + " failed...." + e.getMessage());
						throw new Exception(e.getMessage());
					}
	}
	
	//ConvertExpAndCNforIGV
	protected void runGpExpSegTask(HttpServletRequest request,
			GpIntegrationForm gpForm, HttpSession session,
			List<String> filePathList, String exp_fileName, String cn_fileName, String a_fileName)
			throws Exception {
		//		*** RUN TASK ON THE GP SERVER
				String tid = "209";
				String gpModule =  System.getProperty("gov.nih.nci.caintegrator.gp.exp.seg.modulename");						
				String rembrandtUser = null;
				GPClient gpClient = null;
				String ticketString = "";
				String analysisResultName = gpForm.getAnalysisResultName();
				
				String gpserverURL = System.getProperty("gov.nih.nci.caintegrator.gp.server")!=null ? 
				(String)System.getProperty("gov.nih.nci.caintegrator.gp.server") : "localhost:8080"; //default to localhost
				try {
				 	   rembrandtUser = retreiveRembrandtUser(request, session, rembrandtUser);
						
						ticketString = retrieveTicketString(rembrandtUser, gpserverURL);
			            
						String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
						gpClient = new GPClient(gpserverURL, rembrandtUser, password);
						int size = filePathList.size();
						Parameter[] par = new Parameter[filePathList.size() + 8];
						int currpos= 1;
						for (int i = 0; i < filePathList.size(); i++){
							par[i] = new Parameter("input.filename" + currpos++, filePathList.get(i));
						}
						par[--currpos] = new Parameter("project.name", "rembrandt");

						//r_fileName = "'/usr/local/genepattern/resources/DataMatrix_ISPY_306cDNA_17May07.Rda'";
						par[++currpos] = new Parameter("array.exp.filename", exp_fileName);
						par[++currpos] = new Parameter("annotation.filename", a_fileName);
						par[++currpos] = new Parameter("array.cn.filename", cn_fileName);						
						par[++currpos] = new Parameter("analysis.name", analysisResultName);

						par[++currpos] = new Parameter("output.cls.filename",analysisResultName+".cls");
						par[++currpos] = new Parameter("output.gct.filename",analysisResultName+".gct");
						par[++currpos] = new Parameter("output.seg.filename",analysisResultName+".seg");
												
						//JobResult preprocess = gpServer.runAnalysis(gpModule, par);
						int nowait = gpClient.runAnalysisNoWait(gpModule, par);

						tid = String.valueOf(nowait);
						//LSID = urn:lsid:8080.root.localhost:genepatternmodules:20:2.1.7
						request.setAttribute("jobId", tid);
						request.setAttribute("gpStatus", "running");
						session.setAttribute("genePatternServer", gpClient);
						request.setAttribute("genePatternURL", ticketString);
						request.getSession().setAttribute("gptid", tid);
						request.getSession().setAttribute("gpUserId", rembrandtUser);
						request.getSession().setAttribute("ticketString", ticketString);
						GPTask gpTask = createGpSegTask(tid, analysisResultName);
						RembrandtPresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
						_cacheManager.addNonPersistableToSessionCache(request.getSession().getId(), "latestGpTask",(Serializable) gpTask); 
						
					} catch (Exception e) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						logger.error(sw.toString());
						logger.error(gpModule + " failed...." + e.getMessage());
						throw new Exception(e.getMessage());
					}
	}
	private AnalysisType getAnalysisType(String snpAnalysis){
 
		AnalysisType analysisType = null;
		if(snpAnalysis != null){
			   if ( snpAnalysis.equals("paired") ){
				   analysisType = AnalysisType.PAIRED;
			   }
			   else if ( snpAnalysis.equals("unpaired") ){
				   analysisType = AnalysisType.UNPAIRED;
			   }
			   else{ //BLOOD
				   analysisType = AnalysisType.NORMAL; //which is BLOOD
			   }
		   }
		   return  analysisType;
	}
	private String getCNFileName(AnalysisType analysisType){
		String cnFileName = null; 
		switch(analysisType){
		case PAIRED:
			cnFileName =  System.getProperty("gov.nih.nci.rembrandt.paired_affy_data_matrix");
			break;
		case UNPAIRED:
			cnFileName =  System.getProperty("gov.nih.nci.rembrandt.unpaired_affy_data_matrix");
			break;
		case NORMAL:
			cnFileName =  System.getProperty("gov.nih.nci.rembrandt.blood_affy_data_matrix");
			break;
		}
		   return  cnFileName;
}
}
