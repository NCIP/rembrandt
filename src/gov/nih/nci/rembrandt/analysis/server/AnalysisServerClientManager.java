/**
 * 
 */
package gov.nih.nci.rembrandt.analysis.server;

import gov.nih.nci.caintegrator.analysis.messaging.AnalysisRequest;
import gov.nih.nci.caintegrator.analysis.messaging.AnalysisResult;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.server.AnalysisRequestSender;
import gov.nih.nci.caintegrator.analysis.server.AnalysisResultReceiver;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.exceptions.AnalysisServerException;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.finding.ClassComparisonFindingsResultset;
import gov.nih.nci.rembrandt.dto.finding.FindingsResultsetHandler;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;

import java.util.Hashtable;
import java.util.Properties;

import javax.jms.DeliveryMode;
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
 * @author sahnih
 * singleton object
 */
public class AnalysisServerClientManager implements MessageListener, AnalysisRequestSender, AnalysisResultReceiver{
	private static Logger logger = Logger.getLogger(AnalysisServerClientManager.class);
	private FindingsResultsetHandler findingsResultsetHandler = new FindingsResultsetHandler();
	private ConvenientCache _cacheManager = CacheManagerDelegate.getInstance();
	
    private Properties messagingProps;
	 private QueueSession queueSession;
 
	 private QueueSender requestSender;
	 private QueueReceiver resultReceiver;

	 private Queue requestQueue;
	 private Queue resultQueue;
	private QueueConnection queueConnection;
    private static AnalysisServerClientManager instance = null;
	/**
	 * @param properties
	 * @throws NamingException 
	 * @throws JMSException 
	 */
	@SuppressWarnings("unchecked")
	private AnalysisServerClientManager() throws NamingException, JMSException {
		super();
		messagingProps = ApplicationContext.getJMSProperties();
//		 Populate with needed properties
		Hashtable props = new Hashtable();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
		    "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.PROVIDER_URL, messagingProps.getProperty("JBOSS_URL"));
		props.put("java.naming.rmi.security.manager", "yes");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");
		
//		   Get the initial context with given properties
		Context context = new InitialContext(props);  
		  
	    // Get the connection factory
	    QueueConnectionFactory queueConnectionFactory =
	      (QueueConnectionFactory)context.lookup(messagingProps.getProperty("FACTORY_JNDI"));

	    // Create the connection
	    queueConnection = queueConnectionFactory.createQueueConnection();

	    // Create the session
	    queueSession = queueConnection.createQueueSession(
	      // No transaction
	      false,
	      // Auto ack
	      Session.AUTO_ACKNOWLEDGE);

	    // Look up the destination
	    requestQueue = (Queue)context.lookup("queue/AnalysisRequest");
	    resultQueue = (Queue)context.lookup("queue/AnalysisResponse");

    
	    
	    // Create a publisher
	    requestSender = queueSession.createSender(requestQueue);
		resultReceiver = queueSession.createReceiver(resultQueue);
		resultReceiver.setMessageListener(this);
		queueConnection.start();
	}
	
	public void onMessage(Message message) {
		 //String msg = ((TextMessage)m).getText();
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

	public void receiveResult(AnalysisResult analysisResult) {
		ClassComparisonResult classComparisonResult = (ClassComparisonResult) analysisResult;
		ClassComparisonFindingsResultset findingResultset = findingsResultsetHandler.processClassComparisonAnalsisResult(classComparisonResult);
		String sessionId = classComparisonResult.getSessionId();
		String taskId = classComparisonResult.getTaskId();
		setRequestComplete(sessionId,taskId);
		_cacheManager.addToSessionCache(sessionId,taskId,findingResultset);

		
	}

	public void receiveException(AnalysisServerException analysisServerException) {
		String sessionId = analysisServerException.getFailedRequest().getSessionId();
		String taskId = analysisServerException.getFailedRequest().getTaskId();
		setRequestComplete(sessionId,taskId);		
		_cacheManager.addToSessionCache(sessionId,taskId,analysisServerException);
		logger.error(analysisServerException);
		
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

	public void sendRequest(AnalysisRequest request) {
	    ObjectMessage msg;
		try {
		    // Create a message
			msg = queueSession.createObjectMessage(request);

		    // Send the message
		    requestSender.send(msg, DeliveryMode.NON_PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);

		} catch (JMSException e) {
			logger.error(e);
		}
	

		
	}
	private void setRequestComplete(String sessionId, String taskId){
    	SessionQueryBag queryBag = _cacheManager.getSessionQueryBag(sessionId);
    	Query query = queryBag.getQuery(taskId);
		query.setIsTaskComplete(true);		
	}


}
