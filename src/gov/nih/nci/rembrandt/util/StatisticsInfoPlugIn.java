/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.util;

import gov.nih.nci.caintegrator.application.mail.MailConfig;
import gov.nih.nci.caintegrator.application.schedule.DeleteOldFilesJob;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;



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
/**
 * The StatisticsScheduler class is a class for the jsp tag that allows the code
 * to schedule a job for run later using Quartz.
 * <P>
 */
public class StatisticsInfoPlugIn implements PlugIn
{
	private static Logger logger = Logger.getLogger(StatisticsInfoPlugIn.class);
	private static Scheduler sm_scheduler;
	private String startOnLoad;
	public String getStartOnLoad() {
		return startOnLoad;
	}
	public void setStartOnLoad(String startOnLoad) {
		this.startOnLoad = startOnLoad;
	}
	/**
	* The destroy method is called when this object is destroyed
	*/
	public void destroy()
	{
	}
	/**
	 * The init method is called when this plugin is initialized
	 * <P>
	 * @param actionServlet The struts ActionServlet
	 * @param moduleConfig The struts ModuleConfig
	 */
	public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)
			throws ServletException
	{
		 // Retrieve the ServletContext
		  ServletContext ctx = actionServlet.getServletContext();
		  
		  // The Quartz Scheduler
		  Scheduler scheduler = null;

		  // Retrieve the factory from the ServletContext.
		  // It will be put there by the Quartz Servlet
		  StdSchedulerFactory factory = (StdSchedulerFactory) 
		      ctx.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
		    
		  try
		  {
		        // Retrieve the scheduler from the factory
		        scheduler = factory.getScheduler();
		        // Start the scheduler in case, it isn't started yet
		        if (startOnLoad != null && 
		            startOnLoad.equals(Boolean.TRUE.toString()))
		        {
		            scheduler.start();
		        }
		  }
		  catch (Exception e)
		  {
		     logger.error("Error setting up scheduler", e);
		  }
		  
		  sm_scheduler = scheduler;
	}

	/**
	 * The init method is called when this plugin is initialized
	 * <P>
	 * @param actionServlet The struts ActionServlet
	 * @param moduleConfig The struts ModuleConfig
	 */
	public static void startScheduler(ServletContext context)
	{
		  // The Quartz Scheduler
		  Scheduler scheduler = null;

		  // Retrieve the factory from the ServletContext.
		  // It will be put there by the Quartz Servlet
		  StdSchedulerFactory factory = (StdSchedulerFactory) 
		      context.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
		    
		  try
		  {
		        // Retrieve the scheduler from the factory
		        scheduler = factory.getScheduler();
		        scheduler.start();
		  }
		  catch (Exception e)
		  {
		     logger.error("Error setting up scheduler: " + e.getMessage());
		  }
		  
		  sm_scheduler = scheduler;
	}
	
	/**
	 * The scheduleWork task is called within the application to actually
	 * schedule a job for later execution.
	 * <P>
	 * @param jobDetail The details of the job to schedule
	 * @param trigger The trigger conditions to use for scheduling
	 * @throws SchedulerException
	 */
	//public static void scheduleWork(JobDetail jobDetail, Trigger trigger)
	public static void scheduleWork(int schedulerType, ServletContext context)
		throws SchedulerException
	{
		if (sm_scheduler == null){
			StatisticsInfoPlugIn.startScheduler(context);
			
			Trigger trigger = null;
			if (schedulerType == 1)
				trigger = TriggerUtils.makeDailyTrigger("rembrandtDailyTrigger", 0, 0);
			else if (schedulerType == 2)
				trigger = TriggerUtils.makeWeeklyTrigger("rembrandtWeeklyTrigger", 1, 0, 0);
			
			JobDetail rembrandtstatisticsInfoJob = new JobDetail("rembrandtstatisticsInfoJob", null, StatisticsInfoJob.class);
			JobDetail deleteOldResultsFilesJob = new JobDetail("DeleteOldResultsFilesJob", null, DeleteOldFilesJob.class);
			// Trigger name must be unique so include type and email
		  
			// Add the form and email address to the job details
			if (deleteOldResultsFilesJob != null)
			{				
				String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
				String fileRetentionPeriodInDays = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES).getFileRetentionPeriodInDays();
				if(fileRetentionPeriodInDays == null){
					fileRetentionPeriodInDays = "5";
				}
				deleteOldResultsFilesJob.getJobDataMap().put("dirPath", dirPath);
				deleteOldResultsFilesJob.getJobDataMap().put("fileRetentionPeriodInDays", fileRetentionPeriodInDays);

			}		
			
			sm_scheduler.scheduleJob(rembrandtstatisticsInfoJob, trigger);
			sm_scheduler.scheduleJob(deleteOldResultsFilesJob, TriggerUtils.makeDailyTrigger("DeleteOldResultsFilesJob", 0, 0)); //run it daily at midnight			
			logger.info("Scheduler started......");
		}
	}

	/**
	 * The scheduleWork task is called within the application to actually
	 * schedule a job for later execution.
	 * <P>
	 * @param jobDetail The details of the job to schedule
	 * @param trigger The trigger conditions to use for scheduling
	 * @throws SchedulerException
	 */
	public static void scheduleWork(JobDetail jobDetail, Trigger trigger)
		throws SchedulerException
	{
			sm_scheduler.scheduleJob(jobDetail, trigger);
	}
}
