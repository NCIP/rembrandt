/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.schedule;

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
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

/**
 * The SchedulerPlugIn class is a plugin for struts that allows the code
 * to schedule a job for run later using Quartz.
 * <P>
 * @author mholck
 * @see org.apache.struts.action.PlugIn
 */
public class SchedulerPlugIn implements PlugIn
{
	private String startOnLoad;
	private static Scheduler sm_scheduler;
	private static Logger logger = Logger.getLogger(SchedulerPlugIn.class);
	
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

	public String getStartOnLoad()
	{
		return startOnLoad;
	}

	public void setStartOnLoad(String startOnLoad)
	{
		this.startOnLoad = startOnLoad;
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
