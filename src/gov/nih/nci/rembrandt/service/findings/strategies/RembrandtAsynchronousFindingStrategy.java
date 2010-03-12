package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.application.service.strategy.AsynchronousFindingStrategy;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationCacheManager;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

public class RembrandtAsynchronousFindingStrategy extends AsynchronousFindingStrategy {
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private TaskExecutor taskExecutor = ApplicationContext.getTaskExecutor();
    private static Logger logger = Logger.getLogger(RembrandtAsynchronousFindingStrategy.class);   
	public RembrandtAsynchronousFindingStrategy(RembrandtTaskResult taskResult) {
		this.setTaskResult(taskResult);
	}

	public RembrandtAsynchronousFindingStrategy() {
	}

	public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    protected void executeStrategy() {
        logger.info("Task has been set to running and placed in cache, query will be run");
     	presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(),
     			getTaskResult().getTask().getId(), getTaskResult());

       Runnable task = new Runnable() {
             public void run() {
                 try {
                	 
                     ReportGeneratorHelper reportHelper = new ReportGeneratorHelper((Queriable)getTaskResult().getTask().getQueryDTO(),new HashMap()); 
                	 if(reportHelper.getReportBean()!= null ){
  
                		 getTaskResult().setReportBeanCacheKey(reportHelper.getReportBean().getAssociatedQuery().getQueryName());
                		 getTaskResult().getTask().setStatus(FindingStatus.Completed);

                	 }else{
                		 	 FindingStatus status = FindingStatus.Error;
	                         status.setComment("Error occued while executing the query");
	                         getTaskResult().getTask().setStatus(status);
                	 };
                 	
                 //to generate error or completed.
                 } catch(Exception e) {
	            		 logger.error("Error issuing query", e);
	            		 FindingStatus status = FindingStatus.Error;
	                     status.setComment(e.getMessage());
	                     getTaskResult().getTask().setStatus(status);
                 }

                 presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(), 
                   getTaskResult().getTask().getId(), getTaskResult());
                 logger.info("Query has completed, task has been placed back in cache");
     
             }
             
         };
         taskExecutor.execute(task);



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
	}



}
