package gov.nih.nci.rembrandt.web.ajax;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.genepattern.webservice.AnalysisWebServiceProxy;
import org.genepattern.webservice.JobInfo;
import org.genepattern.webservice.WebServiceException;

import uk.ltd.getahead.dwr.ExecutionContext;
import gov.nih.nci.caintegrator.service.task.GPTask;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;

public class GenePatternHelper {
    private static Logger logger = Logger.getLogger(GenePatternHelper.class);
	public GenePatternHelper() {
    } 

	public String checkGPStatus(String sid) {
		HttpSession session = ExecutionContext.get().getSession();

		int jobNumber = Integer.parseInt(sid);
		String message  = null;
        JobInfo info = null;
        FindingStatus findingStatus = FindingStatus.Running;
        
    	AnalysisWebServiceProxy analysisProxy = (AnalysisWebServiceProxy)session.getAttribute("AnalysisWebServiceProxy");
    	if (analysisProxy == null){
    		String gpserverURL = System.getProperty("gov.nih.nci.caintegrator.gp.server");
    		String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
    		String gpUserId = (String)session.getAttribute("gpUserId");
		
    		try{
    			analysisProxy = new AnalysisWebServiceProxy(gpserverURL, gpUserId, password, false);
    			session.setAttribute("AnalysisWebServiceProxy", analysisProxy);
    		}catch (WebServiceException we){
    			logger.error("Failed to obtain AnalysisWebServiceProxy: " + we.getMessage());
    		}
    	}
		try {

			if(analysisProxy != null){
				analysisProxy.setTimeout(Integer.MAX_VALUE);
				info = analysisProxy.checkStatus(jobNumber);
			}
			
		} catch (WebServiceException e1) {
			message  = "error";
			logger.error(e1.getMessage());
		}
		if (info != null && info.getStatus() != null){
			String status = info.getStatus();
			if (status.equalsIgnoreCase("ERROR")){
				message = "error";
				findingStatus = FindingStatus.Error;
			}
			else if (status.equalsIgnoreCase("Finished")) {
				message = "completed";
				findingStatus = FindingStatus.Completed;
			}
		}
		if (findingStatus != FindingStatus.Running){
			//PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
			RembrandtPresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
			if(ptc!=null){
		    	GPTask gpTask = (GPTask)ptc.getNonPersistableObjectFromSessionCache(
		    			session.getId(), "latestGpTask");
		    	if (gpTask != null){
		    		gpTask.setStatus(findingStatus);
		    		ptc.addNonPersistableToSessionCache(session.getId(), gpTask.getJobId(),(Serializable) gpTask);
		    		ptc.removeObjectFromNonPersistableSessionCache(session.getId(), "latestGpTask");
		    	}
			}
		}
		return message;
	}
}
