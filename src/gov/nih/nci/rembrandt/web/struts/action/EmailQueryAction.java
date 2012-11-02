package gov.nih.nci.rembrandt.web.struts.action;


import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.schedule.GenerateReportJob;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.util.StatisticsInfoPlugIn;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.ComparativeGenomicForm;
import gov.nih.nci.rembrandt.web.struts.form.EmailQueryForm;
import gov.nih.nci.rembrandt.web.struts.form.GeneExpressionForm;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

/**
 * The EmailQueryAction class is called when the EmailQuery form posts.
 * <P>
 * @author sahnih
 * @see org.apache.struts.action.Action
 */
public class EmailQueryAction extends Action
{
	private static Logger logger = Logger.getLogger(DownloadAction.class);
    private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private String  taskId = null;
    private String 	userName = null;
    private String  sessionId = null;
    private String  reportBeanCacheKey = null;
    private String  email = null;
	/**
	 * execute is called when this action is posted to
	 * <P>
	 * @param mapping The ActionMapping for this action as configured in struts
	 * @param form The ActionForm that posted to this action if any
	 * @param request The HttpServletRequest for the current post
	 * @param response The HttpServletResponse for the current post
	 */
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		ActionForward forward = mapping.findForward("success");
		ActionMessages errors = new ActionMessages();

		if( request.getSession().getAttribute("emailform-init")== null){
			request.getSession().setAttribute("emailform-init", true);
			String taskId = request.getParameter("taskId");
			request.getSession().setAttribute("taskId", taskId);
			return forward;
		}
		else{

		EmailQueryForm dForm = (EmailQueryForm)form;
		email = dForm.getEmail();
		sessionId = request.getSession().getId();
		taskId = (String) request.getSession().getAttribute("taskId");
		if(taskId != null){
		//get the specified report bean from the cache using the query name as the key
		RembrandtTaskResult taskResult = (RembrandtTaskResult) presentationTierCache.getTaskResult(sessionId, taskId);
		FindingStatus status = taskResult.getTask().getStatus();
		userName = (String) request.getSession().getAttribute("name");
		if( userName == null ) {
			userName = "RBTuser";
		}
			switch (status){
				case Running:{
					 FindingStatus newStatus = FindingStatus.Email;
					 newStatus.setComment("Upon competion of this query, an email will be sent to "+email);
                     taskResult.getTask().setStatus(newStatus);
                     presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
                    		 taskResult.getTask().getId(), taskResult);
                           logger.info("Query has been email, task has been placed back in cache");
					 generateJob();
					 cancelJob(request.getSession(),taskId);
					 break;

				}
				case Completed:{
					errors.add("email", new ActionMessage("gov.nih.nci.rembrandt.ui.struts.form.emailQuery.email.completed.error"));
					 break;

				}
				case Error:{
					errors.add("email", new ActionMessage("gov.nih.nci.rembrandt.ui.struts.form.emailQuery.email.error.error"));
					 break;

				}
			}
		}


		// If there were errors then return to the input page else go on
	    if (!errors.isEmpty())
	    {
	      addErrors(request, errors);
	      request.getSession().removeAttribute("emailform-init");
	      request.getSession().removeAttribute("taskId");
	      forward = new ActionForward(mapping.getInput());
	    }
	    else{
	    	request.getSession().removeAttribute("emailform-init");
	    	request.getSession().removeAttribute("taskId");
	    	forward = mapping.findForward("close");
	    }
		}
		return forward;
	}

    protected Map getKeyMethodMap() {

        HashMap<String,String> map = new HashMap<String,String>();



        //Submit Query Button using  submittal method
        map.put("buttons_tile.submittalButton", "submittal");



        return map;

        }
    private void generateJob() throws SchedulerException{
    	 //request.setAttribute("queryName",RembrandtConstants.PREVIEW_RESULTS);
		// Figure out the state of the form
		Date now = new Date();
		// Schedule the job for immediate execution and only once
		JobDetail jobDetail = null;
		Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 0);
		if (email != null &&  taskId != null && sessionId != null){
			// If the query was association then setup the association job
			if (email != null  && email.length()>0)
			{
				jobDetail = new JobDetail("generateReportJob" + email + now, null, GenerateReportJob.class);
				// Trigger name must be unique so include type and email
				trigger.setName("immediateTriggerReportJob" + email + now);
			}


			// Add the form and email address to the job details
			if (jobDetail != null)
			{
				jobDetail.getJobDataMap().put("email", email);
				/*if(userName == null || userName.equals("RBTuser")){
					//if its a guest user than use the email user name
				 String [] temp = email.split("@");
				 userName = temp[0];
				}*/
				jobDetail.getJobDataMap().put("taskId", taskId);
				jobDetail.getJobDataMap().put("userName", userName);
				jobDetail.getJobDataMap().put("sessionId", sessionId);
			}

		if (jobDetail != null)
			StatisticsInfoPlugIn.scheduleWork(jobDetail, trigger);
		}
    }
    private void cancelJob(HttpSession session, String taskId){

    	Map<String,Future<?>> futureTaskMap = (Map<String,Future<?>>) session.getAttribute("FutureTaskMap");

    	if(futureTaskMap != null){
    		for(String taskKey: futureTaskMap.keySet()){
    			if(taskKey.equals(taskId)){
    				Future<?> future = futureTaskMap.get(taskKey);
    				if(future != null && !future.isDone()){
    					System.out.println("cancel" + taskKey);
    					future.cancel(true);
    				}
    			}

    		}
    	}

    }
}
