package gov.nih.nci.nautilus.cache;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 *	SessionTracker is notified whenever an HttpSession is created or destroyed
 *	it stores this information in a Map for easy access by other interested
 *	classes.  It also is responsible for removing any cache that was established
 *	for a session when that session is destroyed.  It accomplished this by 
 *	calling the CacheManagerDelegate.removeSessionCache(String sessionId) method.
 *
 *  At instantiation it creates the CacheTracker which watches the 
 *  CacheManagerDelegate for any cache creations or removals.  A CacheCleaner
 *  is also created to use the CacheTracker in determining when a 
 *  sessionCache should be removed. 
 * 
 *  @author BauerD Feb 9, 2005
 * 
 */
public class SessionTracker implements HttpSessionListener {
	// Maintains a reference to all active sessions
	static private SessionMap activeSessions = new SessionMap();
	private static CacheCleaner theCacheCleaner;
	private static CacheTracker theCacheTracker;
	private static Logger logger = Logger.getLogger(SessionTracker.class);
	private static boolean appplicationRunning = false;
	
	public SessionTracker() {
		theCacheTracker = new CacheTracker();	
		CacheManagerDelegate.getInstance().addCacheListener(theCacheTracker);
		new CacheCleaner(theCacheTracker, this).start();
	}

	public void sessionCreated(HttpSessionEvent evt) {
		activeSessions.put(evt.getSession().getId(), evt.getSession());
		logger.debug("New session added: " + evt.getSession().getId());
		logger.debug("Storing reference to new session: " + evt.getSession().getId());
		logger.debug("Total Active Sessions: " + activeSessions.size());
	}

	public void sessionDestroyed(HttpSessionEvent evt) {
		activeSessions.remove(evt.getSession().getId());
		logger.debug("Session Destroyed: " + evt.getSession().getId());
		logger.debug("Removing reference to session: " + evt.getSession().getId());
		logger.debug("Total Active Sessions: " + activeSessions.size());
		//remove the cache for the dead session
		CacheManagerDelegate.getInstance().removeSessionCache(evt.getSession().getId());
	}

	/**
	 * @return Returns the activeSessions.
	 */
	public static SessionMap getSessions() {
		return activeSessions;
	}

	/**
	 * @return Returns the appplicationRunning.
	 */
	public static boolean isAppplicationRunning() {
		return appplicationRunning;
	}
	/**
	 * @param appplicationRunning The appplicationRunning to set.
	 */
	public static void setAppplicationRunning(boolean appplicationRunning) {
		SessionTracker.appplicationRunning = appplicationRunning;
	}
	/**
	 * @ToDo
	 * Create a new method here that will serialize the active sessionIds
	 * to disk when the context is killed, so that we can later check to see
	 * if they are still active when the context is brought back up again.  This
	 * is important because in the instance that the context is bounced there
	 * will still be active sessions when the context comes alive again.  It is
	 * very possible that losing track of sessions could cause a memory leak in
	 * the cache and in other places.
	 * 
	 */
}
