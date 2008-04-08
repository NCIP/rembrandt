package gov.nih.nci.rembrandt.web.ajax;

import javax.servlet.http.HttpSession;

import org.genepattern.client.GPServer;

import uk.ltd.getahead.dwr.ExecutionContext;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.service.task.GPTask;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import java.io.Serializable;

public class GenePatternHelper {
	public GenePatternHelper() {
    } 

	public String checkGPStatus(String sid) {
		//System.out.println("checkGPStatus called...");
		HttpSession session = ExecutionContext.get().getSession();
		GPServer gpServer = (GPServer)session.getAttribute("genePatternServer");

		
		int jobNumber = Integer.parseInt(sid);
		boolean done = false;
		String message = null;
		try {
			done = gpServer.isComplete(jobNumber);
		} catch (Exception e) {//(WebServiceException wse){
			message = "error";
		}
		if (message == null)
			message = done?"completed":"running";
		
		if (done){
			PresentationTierCache ptc = CacheFactory.getPresentationTierCache();							
			if(ptc!=null){
		    	GPTask gpTask = (GPTask)ptc.getNonPersistableObjectFromSessionCache(
		    			session.getId(), "latestGpTask");
		    	gpTask.setStatus(FindingStatus.Completed);
		    	ptc.addNonPersistableToSessionCache(session.getId(), gpTask.getJobId(),(Serializable) gpTask);
    			ptc.removeObjectFromNonPersistableSessionCache(session.getId(), "latestGpTask");
			}
		}
		return message;
	}
}
