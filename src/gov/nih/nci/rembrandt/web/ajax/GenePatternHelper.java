package gov.nih.nci.rembrandt.web.ajax;

import javax.servlet.http.HttpSession;

import org.genepattern.client.GPServer;

import uk.ltd.getahead.dwr.ExecutionContext;

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
		return message;
	}

}
