package gov.nih.nci.rembrandt.service.findings;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.germline.FindingsManager;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
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
    public Task submitQuery(String sessionID, QueryDTO queryDTO) throws FindingsQueryException {
           Task task = new Task(REMBRANDT_TASK_RESULT+queryDTO.getQueryName(),sessionID,FindingStatus.Running,queryDTO);
            task.setQueryDTO(queryDTO);    
            RembrandtTaskResult taskResult = new RembrandtTaskResult(task);
         	//presentationTierCache.addNonPersistableToSessionCache(taskResult.getTask().getCacheId(),
         	//		taskResult.getTask().getId(), taskResult);
        	RembrandtAsynchronousFindingStrategy strategy = new RembrandtAsynchronousFindingStrategy(taskResult);
            strategy.executeQuery();  
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
