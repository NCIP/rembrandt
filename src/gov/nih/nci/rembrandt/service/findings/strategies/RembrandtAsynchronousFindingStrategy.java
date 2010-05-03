package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.application.service.strategy.AsynchronousFindingStrategy;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uk.ltd.getahead.dwr.ExecutionContext;

public class RembrandtAsynchronousFindingStrategy extends AsynchronousFindingStrategy {
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private ThreadPoolExecutor threadPoolExecutor = ApplicationContext.getTaskExecutor();
    private static Logger logger = Logger.getLogger(RembrandtAsynchronousFindingStrategy.class);   
    private HttpSession session = null;
    private Map<String,Future<?>> futureTaskMap = null;
	public RembrandtAsynchronousFindingStrategy(RembrandtTaskResult taskResult, HttpSession session) {
		this.setTaskResult(taskResult);
		this.session = session;
	}

	public RembrandtAsynchronousFindingStrategy() {
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    protected void executeStrategy() {
        logger.info("Task has been set to running and placed in cache, query will be run");
     	presentationTierCache.addNonPersistableToSessionCache(getTaskResult().getTask().getCacheId(),
     			getTaskResult().getTask().getId(), getTaskResult());
     	FutureTask<RembrandtTaskResult> future = new FutureTask<RembrandtTaskResult>(
               getTaskResult());    	
               
         Future<?> future2 = threadPoolExecutor.submit(future);
        
         futureTaskMap = (Map<String,Future<?>>) session.getAttribute("FutureTaskMap");
         if(futureTaskMap == null){
        	 futureTaskMap = new HashMap<String,Future<?>>();
         }
         futureTaskMap.put(getTaskResult().getTask().getId(),future2);
         session.setAttribute("FutureTaskMap",futureTaskMap);
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
