package gov.nih.nci.nautilus.cache;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 *	SessionTracker is notified whenever an HttpSession is created or destroyed
 *	it stores this information in a Map for easy access by other interested
 *	classes.  It also is responsible for removing any cache that was established
 *	for a session when that session is destroyed.  It accomplished this by 
 *	calling the CacheManagerWrapper.removeSessionCache(String sessionId) method.
 *
 *  At instantiation it creates the CacheTracker which watches the 
 *  CacheManagerWrapper for any cache creations or removals.  A CacheCleaner
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

	private static int totalActiveSessions = 0;
	
	public SessionTracker() {
		theCacheTracker = new CacheTracker();	
		CacheManagerWrapper.addCacheListener(theCacheTracker);
		new CacheCleaner(theCacheTracker, this).start();
	}

	public void sessionCreated(HttpSessionEvent evt) {
		totalActiveSessions++;
		activeSessions.put(evt.getSession().getId(), evt.getSession());
		logger.debug("New session added: " + evt.getSession().getId());
		logger.debug("Storing reference to new session: " + evt.getSession().getId());
		logger.debug("Total Active Sessions: " + totalActiveSessions);
	}

	public void sessionDestroyed(HttpSessionEvent evt) {
		totalActiveSessions--;
		activeSessions.remove(evt.getSession().getId());
		logger.debug("Session Destroyed: " + evt.getSession().getId());
		logger.debug("Removing reference to session: " + evt.getSession().getId());
		logger.debug("Total Active Sessions: " + totalActiveSessions);
		//remove the cache for the dead session
		CacheManagerWrapper.removeSessionCache(evt.getSession().getId());
	}
	/**
	 * @return Returns the totalActiveSessions.
	 */
	public int getTotalActiveSessions() {
		
		return totalActiveSessions;
	}
	/**
	 * @return Returns the activeSessions.
	 */
	public static SessionMap getSessions() {
		return activeSessions;
	}

}
