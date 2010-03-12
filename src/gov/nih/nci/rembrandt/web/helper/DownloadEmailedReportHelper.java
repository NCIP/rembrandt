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

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

/**
 * @author sahnih
 *
 */
public class DownloadEmailedReportHelper {
	private static Logger logger = Logger.getLogger(DownloadEmailedReportHelper.class); 
    private static TaskExecutor taskExecutor = ApplicationContext.getTaskExecutor();
	private static RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private static String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	
	public static  void retrieveReport(final String reportName, final String sessionId, final String userName){
		String fileRetentionPeriodInDays = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES).getFileRetentionPeriodInDays();
		if(fileRetentionPeriodInDays == null){
			fileRetentionPeriodInDays = "5";
		}
		final String  finalFileRetentionPeriodInDays = fileRetentionPeriodInDays;
		if (!reportName.contains(userName)){
			// NOT  the users report
			////
			FindingStatus errorStatus = FindingStatus.Error;
			errorStatus.setComment("Sorry, reports are available only to users that generated them"); 
			 Task task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,errorStatus ,null);
			 RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
            taskResult.setReportBeanCacheKey(reportName);
        presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
        		taskResult.getTask().getId(), taskResult);	
		}
		else{
		       Runnable load = new Runnable() {
		             public void run() {
			         		Task task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,FindingStatus.Loading,null);
			                RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
			                taskResult.setReportBeanCacheKey(reportName);
			                presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
			                		taskResult.getTask().getId(), taskResult);	
		                 try {
		                	 
			         		ReportBean reportBean = deSerializeReportBean(reportName);
			        	    if(reportBean!=null) {	    		
			        	    		QueryDTO queryDTO = reportBean.getResultant().getAssociatedQuery();
			        	    		queryDTO.setQueryName(reportName);
			        	    		task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,FindingStatus.Completed,queryDTO);
			        	            task.setQueryDTO(queryDTO);    
			        	            taskResult = new RembrandtTaskResult(task);
			        	            taskResult.setReportBeanCacheKey(reportName);
			        	    		presentationTierCache.addNonPersistableToSessionCache(sessionId,reportName,reportBean);
			        		}else { //post an error in the cache
			        			FindingStatus errorStatus = FindingStatus.Error;
			        			errorStatus.setComment("Could not locate "+ reportName + " as after "+ finalFileRetentionPeriodInDays  +" days the emailed query results are deleted from the Rembrandt server."); 
			        			task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionId,errorStatus ,null);
			        			taskResult = new RembrandtTaskResult(task);
			                    taskResult.setReportBeanCacheKey(reportName);
			        			
			        		}//to generate error or completed.
			                 } catch(Exception e) {
				            		 logger.error("Download Retrieval error", e);
				            		 FindingStatus status = FindingStatus.Error;
				                     status.setComment(e.getMessage());
				                     taskResult.getTask().setStatus(status);
			                 }

		                 presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(), 
		                		 taskResult.getTask().getId(), taskResult);
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
