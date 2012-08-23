package gov.nih.nci.rembrandt.schedule;

import gov.nih.nci.caintegrator.application.mail.MailManager;
import gov.nih.nci.caintegrator.application.zip.FileNameGenerator;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * The SearchSubjectJob class is the job to search for subjects using the FTP
 * method for large results. This is a quartz job that will be scheduled to run
 * when there are more than MAX_RESULTS returned.
 * <P>
 * 
 * @author sahnih
 * @see import org.quartz.Job;
 */
public class GenerateReportJob implements Job {
	private static Logger logger = Logger.getLogger(GenerateReportJob.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory
			.getPresentationTierCache();
	private final MailManager mailManager = new MailManager(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES);

	public void execute(JobExecutionContext context)
		{
		// Set the thread priority to a nicer value for background processing
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY+4);
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		String now = dateFormat.format(new Date());
			
		logger.debug("SearchSubjectJob is running");
		logger.debug("Our priority is " + Thread.currentThread().getPriority());
		String taskId = context.getJobDetail().getJobDataMap().getString(
				"taskId");
		String userName = context.getJobDetail().getJobDataMap().getString(
				"userName");
		String email = context.getJobDetail().getJobDataMap()
				.getString("email");
		String sessionId = context.getJobDetail().getJobDataMap().getString(
				"sessionId");

		if (userName != null && email != null && taskId != null
				&& sessionId != null) {
			// Get all the needed elements from the form and build the query
			logger.debug("Email is " + email);
			RembrandtTaskResult taskResult = (RembrandtTaskResult) presentationTierCache
					.getTaskResult(sessionId, taskId);
			if (taskResult != null) {
				String query = (String) taskResult.getTask().getQueryDTO().toString();
				if(taskResult.getTask().getQueryDTO() instanceof Queriable){
					query = ((Queriable)taskResult.getTask().getQueryDTO()).toString();

				}
				final String queryText = "Your query started at " + now + " was\n" + query;

				FindingStatus status = taskResult.getTask().getStatus();
				switch (status) {
				case Email: {					
					scheduleJob(taskResult, userName, email, queryText);
					break;

				}
				case Completed: {
			    	String reportBeanCacheKey = taskResult.getReportBeanCacheKey();
			    	ReportBean reportBean = (ReportBean) presentationTierCache.getReportBean(sessionId, reportBeanCacheKey);
					if (reportBean != null) {
						String filename = null;
						try {
							filename = sereializeReportBean(userName, reportBean);
						} catch (Exception e) {
							sendErrorMail(email, queryText);
							logger.error("Error from GenerateReportJob for Query: \n" + queryText, e);
							logger.error(e.getMessage());
						}
						if (filename != null) {
							sendMail(email, filename, queryText);
						}
					}
					break;

				}
				case Error: {
					sendErrorMail(email, queryText);
					break;

				}
				}
			}

		}
	}

	private void scheduleJob(RembrandtTaskResult taskResult, String userName,
			String email, String queryText) {
		ReportBean reportBean = null;
		try {
			Queriable query = (Queriable) taskResult.getTask().getQueryDTO();
			String uniqueFilename = FileNameGenerator.generateFileName(userName);
			uniqueFilename = uniqueFilename + "-"+ query.getQueryName();
			query.setQueryName(uniqueFilename);
			ReportGeneratorHelper rgHelper = null;
			try {
				rgHelper = new ReportGeneratorHelper(query, new HashMap());
			}catch(Exception e) {
				logger.error("Unable to create the ReportBean");
				logger.error(e);
			}
			reportBean = rgHelper.getReportBean();
			if (reportBean != null) {
				String filename = null;
					filename = sereializeReportBean(uniqueFilename, reportBean);				
				if (filename != null) {
					sendMail(email, filename, queryText);
				}
			}
		} catch (Exception e) {
			sendErrorMail(email, queryText);
			logger.error("Error from GenerateReportJob for Query: \n" + queryText, e);
			logger.error(e.getMessage());
		} catch (ValidationException e) {
			logger.error(e.getMessage());
		}

	}

	private void sendErrorMail(String email, String queryText) {
		mailManager.sendFTPErrorMail(email, queryText);

	}

	private void sendMail(String email, String filename, String queryText) {
		// Now send the notification email
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(filename);
		mailManager.sendFTPMail(email, fileList, queryText);

	}

	private String sereializeReportBean(String uniqueFilename, ReportBean reportBean)
			throws Exception {
		try {
			reportBean.setResultantCacheKey(uniqueFilename);
			String dirPath = System
					.getProperty("gov.nih.nci.rembrandt.data_directory");
			String filename = dirPath + File.separator + uniqueFilename;
			FileOutputStream fo = new FileOutputStream(filename);
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(reportBean);
			so.flush();
			so.close();
			fo.close();
			return filename;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
}
