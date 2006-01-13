package gov.nih.nci.rembrandt.analysis.server;

import gov.nih.nci.caintegrator.analysis.messaging.AnalysisRequest;
import gov.nih.nci.caintegrator.analysis.messaging.AnalysisRequestSender;
import gov.nih.nci.caintegrator.analysis.messaging.AnalysisResult;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.AnalysisServerException;
import gov.nih.nci.caintegrator.service.findings.AnalysisFinding;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.queryservice.resultset.annotation.GeneExprAnnotationService;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * This object is used by the Rembrandt application send analysis requests to and receive results from the 
 * analysis server(s). There is only one instance of this object (singleton object) for the application. 
 * Communication with the analysis server(s) is implemented using the JBossMQ JMS implementation. Requests are sent to the
 * AnalysisRequest JMS queue and results are returned to the AnalysisResponse JMS queue. 
 * 
 * @author sahnih, harrismic
 * 
 */
public class AnalysisServerClientManager implements MessageListener, ExceptionListener, AnalysisRequestSender{
	private static Logger logger = Logger.getLogger(AnalysisServerClientManager.class);
	private BusinessTierCache _cacheManager = ApplicationFactory.getBusinessTierCache();
	
    //private Properties messagingProps;
	private QueueSession queueSession;
 
	//private QueueSender requestSender;
	private QueueReceiver resultReceiver;

	private Queue requestQueue;
	private Queue resultQueue;
	private QueueConnection queueConnection;
    private static AnalysisServerClientManager instance = null;
    private static final long reconnectWaitTimeMS = 5000L;
	/**
	 * @param properties
	 * @throws NamingException 
	 * @throws JMSException 
	 */
	@SuppressWarnings("unchecked")
	private AnalysisServerClientManager() throws NamingException, JMSException {
		 try {
			logger.debug("AnalysisServerClientManager constructor start");
			
			establishQueueConnection();
			
			logger.debug("AnalysisServerClientManager constructor finished successfully");
	    }catch(Throwable t) {
	    	logger.error("Constructor has thrown an exception of type:"+t.getClass());
	    	logger.error(t);
	    }
	}
	
	
	/**
	 * Establish a connection to the JMS queues.  If it is not possible
	 * to connect then this method will sleep for reconnectWaitTimeMS milliseconds and
	 * then try to connect again.  
	 *
	 */
	private void establishQueueConnection() {
        
		boolean connected = false;
		int numConnectAttempts = 0;
		Properties messagingProps = ApplicationContext.getJMSProperties();
		
		while (!connected) {
		
			try {
					
				//logger.debug("AnalysisServerClientManager constructor start");
				//Properties messagingProps = ApplicationContext.getJMSProperties();
				
				logger.info("Attempting to establish queue connection with provider: " + messagingProps.getProperty("JBOSS_URL"));
				
				// Populate with needed properties
				Hashtable props = new Hashtable();
				props.put(Context.INITIAL_CONTEXT_FACTORY,
						"org.jnp.interfaces.NamingContextFactory");
				props.put(Context.PROVIDER_URL, messagingProps
						.getProperty("JBOSS_URL"));
				props.put("java.naming.rmi.security.manager", "yes");
				props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");
			
				// Get the initial context with given properties
				Context context = new InitialContext(props);
			
				// Get the connection factory
			
				QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
						.lookup(messagingProps.getProperty("FACTORY_JNDI"));
			
				// Create the connection
			
				queueConnection = queueConnectionFactory.createQueueConnection();
			
				queueConnection.setExceptionListener(this);
			
				// Create the session
				queueSession = queueConnection.createQueueSession(
				// No transaction
						false,
						// Auto ack
						Session.AUTO_ACKNOWLEDGE);
			
				// Look up the destination
				requestQueue = (Queue) context.lookup("queue/AnalysisRequest");
				resultQueue = (Queue) context.lookup("queue/AnalysisResponse");
			
				// Create a publisher
				resultReceiver = queueSession.createReceiver(resultQueue);
				resultReceiver.setMessageListener(this);
			
				queueConnection.start();
		
			    connected = true;
			    numConnectAttempts = 0;
			  
			    logger.info("  successfully established queue connection with provider=" + messagingProps.getProperty("JBOSS_URL"));
			    logger.info("Now listening for requests...");
			}
			catch (Exception ex) {
			    numConnectAttempts++;
			  
			    if (numConnectAttempts <= 10) {
			      logger.warn("  could not establish connection with provider=" + messagingProps.getProperty("JBOSS_URL") + " after numAttempts=" + numConnectAttempts + "  Will try again in  " + Long.toString(reconnectWaitTimeMS/1000L) + " seconds...");
			      if (numConnectAttempts == 10) {
			        logger.warn("  Will only print connection attempts every 600 atttempts to reduce log size.");
			      }
			    }
			    else if ((numConnectAttempts % 600) == 0) {
				  logger.info("  could not establish connection after numAttempts=" + numConnectAttempts + " will keep trying every " + Long.toString(reconnectWaitTimeMS/1000L) + " seconds...");
			    }
			  
			    try { 
			      Thread.sleep(reconnectWaitTimeMS);
			    }
			    catch (Exception ex2) {
			      logger.error("Caugh exception while trying to sleep.." + ex2.getMessage());
			      logger.error(ex2);
			      //ex2.printStackTrace(System.out);
			      return;
			    }
		    }
		}
	}
	
	/**
	 * JMS notification about a new message
	 */
	public void onMessage(Message message) {
		 //String msg = ((TextMessage)m).getText();
		  logger.debug("onMessage has been called");
	      ObjectMessage msg = (ObjectMessage) message;
	      try {
			Object result = msg.getObject();
			if( result instanceof AnalysisResult){
				receiveResult((AnalysisResult) result);
			}
			else if( result instanceof AnalysisServerException){
				receiveException((AnalysisServerException) result);
			}
			
		} catch (JMSException e) {
			logger.error(e);
		}
		
	}
	
	/**
	 * JMS notification about an exception
	 */
    public void onException(JMSException jmsException) {
    	 //System.out.println("onException: caught JMSexception: " + exception.getMessage());
  	  logger.error("onException: caught JMSexception: " + jmsException.getMessage());
  	  try
        {
  		 if (queueConnection != null) {
             queueConnection.setExceptionListener(null);
             //close();
             queueConnection.close();
  		 }
        }
        catch (JMSException c)
        {
      	logger.info("Ignoring exception thrown when closing broken connection msg=" + c.getMessage());
          //System.out.println("Ignoring exception thrown when closing broken connection msg=" + c.getMessage());
          //c.printStackTrace(System.out);
        }
  	  
  	    //attempt to re-establish the queue connection
  	    establishQueueConnection();
	}
    /***
     * Receive an analysis result
     * 
     * @param analysisResult is the result 
     */
    public void receiveResult(AnalysisResult analysisResult) {
    	String sessionId = analysisResult.getSessionId();
		String taskId = analysisResult.getTaskId();
		logger.debug("AnalysisResult session: "+sessionId+" & task: "+taskId+" has been returned");
		logger.debug("Retreiving finding for session: "+sessionId+" & task: "+taskId+" from cache");
		AnalysisFinding finding = (AnalysisFinding)_cacheManager.getSessionFinding(sessionId, taskId);
		if(finding != null){
			finding.setAnalysisResult(analysisResult);
			if(finding instanceof ClassComparisonFinding){
				//generate the annotations
				List<ClassComparisonResultEntry> classComparisonResultEntrys = ((ClassComparisonFinding) finding).getResultEntries();
				List<String> reporterIds = new ArrayList<String>();
				Map<String,ReporterResultset> reporterResultsetMap = null;
				for (ClassComparisonResultEntry classComparisonResultEntry: classComparisonResultEntrys){
					if(classComparisonResultEntry.getReporterId() != null){
						reporterIds.add(classComparisonResultEntry.getReporterId());
					}
				}
		        try {
		        	reporterResultsetMap = GeneExprAnnotationService.getAnnotationsMapForReporters(reporterIds);
		        }
		        catch(Exception e){}
		        
		        ((ClassComparisonFinding) finding).setReporterAnnotationsMap(reporterResultsetMap);
			}
			finding.setStatus(FindingStatus.Completed);
			logger.debug("Following task has been completed:/n  SessionId: "+sessionId+"/n  TaskId: "+taskId);
			_cacheManager.addToSessionCache(sessionId,taskId,finding);
			logger.debug("Following finding has been placed in cache:/n  SessionId: "+sessionId+"/n  TaskId: "+taskId);
		}
	}

    /**
     * Receive an analysis exception.  Analysis exceptions can be returned from an analysis server
     * where there is a problem completing the computation.  Exceptions can be caused by invalid request parameters or parameters
     * that would produce statistically invalid results.
     *  
     * @param analysisServerException the exception.
     */
	public void receiveException(AnalysisServerException analysisServerException) {
		String sessionId = analysisServerException.getFailedRequest().getSessionId();
		String taskId = analysisServerException.getFailedRequest().getTaskId();
		logger.debug("AnalysisServerException session: "+sessionId+" & task: "+taskId+" has been returned");
		AnalysisFinding finding = (AnalysisFinding)_cacheManager.getSessionFinding(sessionId, taskId);
		if(finding != null){
			FindingStatus newStatus = FindingStatus.Error;
			newStatus.setComment(analysisServerException.getMessage());
			finding.setStatus(newStatus);
			logger.debug("Retreiving finding for session: "+sessionId+" & task: "+taskId+" from cache");
			_cacheManager.addToSessionCache(sessionId,taskId+"_analysisServerException",analysisServerException);
			_cacheManager.addToSessionCache(sessionId,taskId,finding);
			logger.debug("Following finding has been placed in cache:/n  SessionId: "+sessionId+"/n  TaskId: "+taskId);
			logger.error(analysisServerException);
		}
	}

	/**
	 * @return Returns the instance.
	 */
	public static AnalysisServerClientManager getInstance()  throws NamingException, JMSException{
		//first time
		if(instance == null){
			try {
				instance = new AnalysisServerClientManager();
			} catch (NamingException e) {
				logger.error(e.getMessage());
				throw e;
			} catch (JMSException e) {
				logger.error(e.getMessage());
				throw e;
			}
		}
		return instance;
	}

	/**
	 * Send an AnalysisRequest to the JMS request queue. Note this method does not store anything
	 * in the cache. 
	 * @throws JMSException 
	 * @see sendRequest(Query query, AnalysisRequest request)
	 */
	public void sendRequest(AnalysisRequest request) throws JMSException {
		ObjectMessage msg;
		try {
			
			QueueSession requestSession = queueConnection.createQueueSession(
				      // No transaction
				      false,
				      // Auto ack
				      Session.AUTO_ACKNOWLEDGE);
			
		    // Create a message
			msg = requestSession.createObjectMessage(request);
			QueueSender requestSender = requestSession.createSender(requestQueue);
			// Send the message
		    requestSender.send(msg, DeliveryMode.NON_PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
		    requestSender.close();
		    requestSession.close();
			logger.debug("sendRequest session: "+request.getSessionId()+" & task: "+request.getTaskId()+" has been sent to the JMQ");

		} catch (JMSException e) {
			logger.error(e);
			throw e;
		} 
	}
	
}
