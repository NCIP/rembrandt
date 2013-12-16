/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.service.findings;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.germline.FindingsManager;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.strategies.RembrandtAsynchronousFileRetrivalStrategy;
import gov.nih.nci.rembrandt.service.findings.strategies.RembrandtAsynchronousFindingStrategy;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

public class RembrandtAsynchronousFindingManagerImpl extends FindingsManager {
	public static final String REMBRANDT_TASK_RESULT = "RembrandtTaskResult:";
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	   /**
     * Submit query looks at the list of strategies it has available
     * and chooses the correct strategy based on the queryDTO type.
     * It then creates a new Task to be handed by to the user, while
     * it called the execute method of the strategy asynchronously.
     */
    public Task submitQuery(HttpSession session, QueryDTO queryDTO) throws FindingsQueryException {
           Task task = new Task(REMBRANDT_TASK_RESULT+queryDTO.getQueryName(),session.getId(),FindingStatus.Running,queryDTO);
            task.setQueryDTO(queryDTO);    
            RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
         	//presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
         	//		taskResult.getTask().getId(), taskResult);
        	RembrandtAsynchronousFindingStrategy strategy = new RembrandtAsynchronousFindingStrategy(taskResult, session);
            strategy.executeQuery();  
            return task;
    }
	   /**
     * Submit query looks at the list of strategies it has available
     * and chooses the correct strategy based on the queryDTO type.
     * It then creates a new Task to be handed by to the user, while
     * it called the execute method of the strategy asynchronously.
	 * @throws UnsupportedEncodingException 
     */
    public Task retrieveResultsFromFile(String sessionID, String reportName, String userName, HttpSession session) throws FindingsQueryException {
 		Task task = new Task(RembrandtAsynchronousFindingManagerImpl.REMBRANDT_TASK_RESULT+reportName,sessionID,FindingStatus.Retrieving,null);
        RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
        taskResult.setReportBeanCacheKey(reportName);
        RembrandtAsynchronousFileRetrivalStrategy strategy = new RembrandtAsynchronousFileRetrivalStrategy(taskResult, reportName, userName, session);
        strategy.executeStrategy();
        return task;
    }
    /**
     * This method locates the desired Task by calling chooseStrategy
     * in order to use the correct strategy to retrieve the Task and
     * it status.
     * @param task
     * @return Task
      */    
    public Task checkStatus(Task task){        
        SessionBasedFindingStrategy strategy =  new RembrandtAsynchronousFindingStrategy();
        TaskResult taskResult = strategy.retrieveTaskResult(task);
        if(taskResult != null){
        	task = taskResult.getTask();
        }
        return task;
    }
    /**
     * This method locates the desired TaskResult by calling chooseStrategy
     * in order to use the correct strategy to retrieve the result.
     * @param task
     * @return TaskResult
      */
     public TaskResult getTaskResult(Task task) {
    	 SessionBasedFindingStrategy strategy =  new RembrandtAsynchronousFindingStrategy();
         TaskResult taskResult = strategy.retrieveTaskResult(task);
         return taskResult;
     }
}
