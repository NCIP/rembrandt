package gov.nih.nci.rembrandt.web.struts.action;


import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.schedule.GenerateReportJob;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.util.StatisticsInfoPlugIn;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.ComparativeGenomicForm;
import gov.nih.nci.rembrandt.web.struts.form.EmailQueryForm;
import gov.nih.nci.rembrandt.web.struts.form.GeneExpressionForm;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.quartz.JobDetail;
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
			return forward;
		}
		else{
			request.getSession().removeAttribute("emailform-init");
		String goBack=null;	
        if(form instanceof GeneExpressionForm) {
            request.setAttribute("geneexpressionForm", request.getAttribute("previewForm"));
            goBack = "backToGeneExp";
        }else if(form instanceof ClinicalDataForm) {
            request.setAttribute("clinicaldataForm", request.getAttribute("previewForm"));
            goBack = "backToClinical";
        }else if(form instanceof ComparativeGenomicForm) {
            request.setAttribute("comparitivegenomicForm", request.getAttribute("previewForm"));
            goBack = "backToCGH";
        }
        request.setAttribute("queryName",RembrandtConstants.PREVIEW_RESULTS);
        logger.debug("back: " + goBack);
		// Figure out the state of the form
		EmailQueryForm dForm = (EmailQueryForm)form;
		String email = dForm.getEmail();
		Date now = new Date();
		// Schedule the job for immediate execution and only once
		JobDetail jobDetail = null;
		Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 0);
			// If the query was association then setup the association job
			if (email != null  && email.length()>0)
			{
				jobDetail = new JobDetail("generateReportJob" + email + now, null, GenerateReportJob.class);
				// Trigger name must be unique so include type and email
				trigger.setName("immediateTriggerReportJob" + email + now);
			}
			
			String sessionId = request.getSession().getId();
			String queryName = (String) request.getSession().getAttribute("emailQueryName");
			String userName = (String) request.getSession().getAttribute("name");
			// Add the form and email address to the job details
			if (jobDetail != null)
			{
				jobDetail.getJobDataMap().put("email", email);
				jobDetail.getJobDataMap().put("userName", userName);
				jobDetail.getJobDataMap().put("queryName", queryName);
				jobDetail.getJobDataMap().put("sessionId", sessionId);
			}

		if (jobDetail != null)
			StatisticsInfoPlugIn.scheduleWork(jobDetail, trigger);
		
		// If there were errors then return to the input page else go on
	    if (!errors.isEmpty())
	    {
	      addErrors(request, errors);
	      forward = new ActionForward(mapping.getInput());
	    }
	    else
	    	forward = mapping.findForward("advanceSearchMenu");
		}
		return forward;
	}

    protected Map getKeyMethodMap() {
		 
        HashMap<String,String> map = new HashMap<String,String>();
        
        
        
        //Submit Query Button using  submittal method
        map.put("buttons_tile.submittalButton", "submittal");
        

        
        return map;
        
        }
}
