package gov.nih.nci.rembrandt.web.ajax;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.genepattern.client.GPServer;
import org.genepattern.webservice.AnalysisWebServiceProxy;
import org.genepattern.webservice.JobInfo;
import org.genepattern.webservice.TaskIntegratorProxy;
import org.genepattern.webservice.WebServiceException;

import uk.ltd.getahead.dwr.ExecutionContext;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.service.task.GPTask;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.action.GPProcessAction;

import java.io.Serializable;

public class GenePatternHelper {
    private static Logger logger = Logger.getLogger(GenePatternHelper.class);
	public GenePatternHelper() {
    } 

	public String checkGPStatus(String sid) {
		//System.out.println("checkGPStatus called...");
		HttpSession session = ExecutionContext.get().getSession();
		String gpserverURL = System.getProperty("gov.nih.nci.caintegrator.gp.server");
		String gpUserId = (String)session.getAttribute("gpUserId");
		String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
		int jobNumber = Integer.parseInt(sid);
        AnalysisWebServiceProxy analysisProxy = null;
		String message  = null;
        JobInfo info = null;
        FindingStatus findingStatus = FindingStatus.Running;
		try {
			analysisProxy = new AnalysisWebServiceProxy(gpserverURL, gpUserId, password, false);
		
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
