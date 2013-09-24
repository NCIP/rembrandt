/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.mail.MailConfig;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.RembrandtAsynchronousFindingManagerImpl;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class DownloadEmailedReportHelper {
	private static Logger logger = Logger.getLogger(DownloadEmailedReportHelper.class); 
    private static ThreadPoolExecutor taskExecutor = ApplicationContext.getTaskExecutor();
	private static RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private static String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	
	public static  void retrieveReport(final String reportName, final String sessionId, final String userName){
		String fileRetentionPeriodInDays = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES).getFileRetentionPeriodInDays();
		if(fileRetentionPeriodInDays == null){
			fileRetentionPeriodInDays = "5";
		}
		final String  finalFileRetentionPeriodInDays = fileRetentionPeriodInDays;
 		Task task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,FindingStatus.Retrieving,null);
        RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
        taskResult.setReportBeanCacheKey(reportName);
        presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
        		taskResult.getTask().getId(), taskResult);	
		if (!reportName.contains(userName)){
			// NOT  the users report
			////
			FindingStatus errorStatus = FindingStatus.Error;
			errorStatus.setComment("Sorry, reports are available only to users that generated them"); 
			  task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,errorStatus ,null);
			  taskResult = new RembrandtTaskResult(task);
            taskResult.setReportBeanCacheKey(reportName);
        presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
        		taskResult.getTask().getId(), taskResult);	
		}
		else{
		       Runnable load = new Runnable() {
		             public void run() {
		                 try {		          
		              		Task newtask = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,FindingStatus.Retrieving,null);
		                    RembrandtTaskResult newTaskResult = new RembrandtTaskResult(newtask);
			         		ReportBean reportBean = deSerializeReportBean(reportName);
			        	    if(reportBean!=null) {	    		
			        	    		QueryDTO queryDTO = reportBean.getResultant().getAssociatedQuery();
			        	    		queryDTO.setQueryName(reportName);
			        	    		newtask = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,FindingStatus.Completed,queryDTO);
			        	    		newtask.setQueryDTO(queryDTO);    
			        	            newTaskResult = new RembrandtTaskResult(newtask);
			        	            newTaskResult.setReportBeanCacheKey(reportName);
			        	    		presentationTierCache.addNonPersistableToSessionCache(sessionId,reportName,reportBean);
			        		}else { //post an error in the cache
			        			FindingStatus errorStatus = FindingStatus.Error;
			        			errorStatus.setComment("Could not locate "+ reportName + " as after "+ finalFileRetentionPeriodInDays  +" days the emailed query results are deleted from the Rembrandt server."); 
			        			newtask = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,errorStatus ,null);
			        			newTaskResult = new RembrandtTaskResult(newtask);
			        			newTaskResult.setReportBeanCacheKey(reportName);
			        			
			        		}//to generate error or completed.
			                 presentationTierCache.addNonPersistableToSessionCache(newTaskResult.getTask().getCacheId(), 
			                		 newTaskResult.getTask().getId(), newTaskResult);
			                 } catch(Exception e) {
			            		 logger.error("Download Retrieval error", e);
 			            		    FindingStatus errorStatus = FindingStatus.Error;
 			            		    errorStatus.setComment(e.getMessage());
					              	Task errorTask = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,errorStatus,null);
					                RembrandtTaskResult errorTaskResult = new RembrandtTaskResult(errorTask);
  				                    presentationTierCache.addNonPersistableToSessionCache(errorTaskResult.getTask().getCacheId(), 
					                		 errorTaskResult.getTask().getId(), errorTaskResult);
			                 }

		                 logger.info("Download Retrieval has completed, task has been placed back in cache");
		     
		             }
		             
		         };
		         taskExecutor.execute(load);
		
			
		}
			
			/////
			
			
			

    	
	}
	private static ReportBean deSerializeReportBean(String reportName) throws Exception{
		ReportBean reportBean = null;
		if (doesFileExists(reportName)){
		String filePath = dirPath+File.separator+reportName;
			
			FileInputStream fi = new FileInputStream(filePath);
			ObjectInputStream si = new ObjectInputStream(fi);  
			reportBean = (ReportBean) si.readObject();				
            si.close();
            fi.close();
		}
		return reportBean;    		
}
	private static boolean doesFileExists(String zipFileName) {
		if(zipFileName != null){
			String filePath = dirPath+File.separator+zipFileName;
			File file = null;
				file = new File(filePath);
				if (file.exists()){
					return true;
				}
		}
		return false;
	}
}
