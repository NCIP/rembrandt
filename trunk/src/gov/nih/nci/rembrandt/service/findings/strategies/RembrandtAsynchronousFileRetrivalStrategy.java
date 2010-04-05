package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.application.mail.MailConfig;
import gov.nih.nci.caintegrator.application.service.strategy.AsynchronousFindingStrategy;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.service.findings.RembrandtAsynchronousFindingManagerImpl;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

public class RembrandtAsynchronousFileRetrivalStrategy extends AsynchronousFindingStrategy {
    private static Logger logger = Logger.getLogger(RembrandtAsynchronousFileRetrivalStrategy.class);   
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private TaskExecutor taskExecutor = ApplicationContext.getTaskExecutor();
	private String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	private String reportName;
	private String userName;

	public RembrandtAsynchronousFileRetrivalStrategy(RembrandtTaskResult taskResult,String reportName, String userName) {
		this.setTaskResult(taskResult);
		this.reportName = reportName;
		this.userName = userName;
	}

	public RembrandtAsynchronousFileRetrivalStrategy() {
	}

	public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void executeStrategy() {
		String fileRetentionPeriodInDays = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES).getFileRetentionPeriodInDays();
		if(fileRetentionPeriodInDays == null){
			fileRetentionPeriodInDays = "5";
		}
		final String  finalFileRetentionPeriodInDays = fileRetentionPeriodInDays;
        logger.info("Task has been set to running and placed in cache, query will be run");
     	presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(),
     			getTaskResult().getTask().getId(), getTaskResult());
     	if(reportName != null && userName != null){
	     	if (!reportName.contains(userName)){
				// NOT  the users report
				////
				FindingStatus errorStatus = FindingStatus.Error;
				errorStatus.setComment("Sorry, reports are available only to users that generated them"); 
	             getTaskResult().getTask().setStatus(errorStatus);
	             presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
	        		taskResult.getTask().getId(), getTaskResult());	
			}
			else{
	       Runnable task = new Runnable() {
	             public void run() {
	                 try {
	                	 ReportBean reportBean =  presentationTierCache.getReportBean( sessionId,  reportName);
	                	 if(reportBean == null){ //if not in cache already than lets try to deserialize it
	                    	reportBean = deSerializeReportBean(reportName); 
	                	 }
	                	 if(reportBean!= null ){	  
	                		 getTaskResult().setReportBeanCacheKey(reportBean.getAssociatedQuery().getQueryName());
	                		 getTaskResult().getTask().setStatus(FindingStatus.Completed);                		 
	                		 QueryDTO queryDTO = reportBean.getResultant().getAssociatedQuery();
	                		 //queryDTO.setQueryName(reportName);
	        	    		 //getTaskResult().getTask().setQueryDTO(queryDTO);
  	        	    		 presentationTierCache.addNonPersistableToSessionCache(sessionId,queryDTO.getQueryName(),reportBean);	                		 
	
	                	 }else{
	                		 	 FindingStatus status = FindingStatus.Error;
				        		 status.setComment("Could not locate "+ reportName + " as after "+ finalFileRetentionPeriodInDays  +" days the emailed query results are deleted from the Rembrandt server."); 
		                         getTaskResult().getTask().setStatus(status);
	                	 };
	                 	
	                 //to generate error or completed.
	                 } catch(Exception e) {
		            		 logger.error("Exception occued while retrieving the results", e);
		            		 FindingStatus status = FindingStatus.Error;
	                         status.setComment("Error occued while retrieving the results");
	                         getTaskResult().getTask().setStatus(status);
	                 }
	                 System.out.println("Writing to cache Id: "+ getTaskResult().getTask().getId()+" Status: "+getTaskResult().getTask().getStatus());
	                 presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(), 
	                   getTaskResult().getTask().getId(), getTaskResult());
	                 logger.info("File retrieval has completed, task has been placed back in cache");
	     
	             }
	             
	         };
	         taskExecutor.execute(task);
			}
     	}else{
   		 FindingStatus status = FindingStatus.Error;
         status.setComment("Error occued while retrieving the results");
         getTaskResult().getTask().setStatus(status);
         presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(), 
                 getTaskResult().getTask().getId(), getTaskResult());
               logger.info("File retrieval has completed, task has been placed back in cache");
     	}
     	


    }

	/**
	 * @return the taskResult
	 */
	public RembrandtTaskResult getTaskResult() {
		return (RembrandtTaskResult) taskResult;
	}

	/**
	 * @param taskResult the taskResult to set
	 */
	public void setTaskResult(RembrandtTaskResult taskResult) {
		this.taskResult = taskResult;
		this.sessionId = taskResult.getTask().getCacheId();
	}

	private  ReportBean deSerializeReportBean(String reportName) throws Exception{
		ReportBean reportBean = null;
		if (doesFileExists(reportName)){
		String filePath = dirPath+File.separator+reportName;
			System.out.print("Start deserialization:"+ System.currentTimeMillis());
			FileInputStream fi = new FileInputStream(filePath);
			ObjectInputStream si = new ObjectInputStream(fi);  
			reportBean = (ReportBean) si.readObject();				
            si.close();
            fi.close();
			System.out.print("Stop deserialization:"+ System.currentTimeMillis());

		}
		return reportBean;    		
}
	private boolean doesFileExists(String zipFileName) {
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

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
