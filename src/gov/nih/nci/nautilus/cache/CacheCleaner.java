package gov.nih.nci.nautilus.cache;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.util.PropertyLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * CacheCleaner checks in with the CacheTracker
 * at "check.session.cache.tracker" ms interval and reviews the
 * current SessionCaches for unused caches.  An unused
 * sessionCache is any cache that is associated with a session
 * that has been idle for longer than "session.cache.timeout"
 * property file time.  
 * 
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class CacheCleaner extends Thread {
	private static Logger logger = Logger.getLogger(CacheCleaner.class);
    private CacheTracker cacheTracker;
    private SessionTracker sessionTracker;
    
    /***************** Configurable properties *******************/
    //Time to check the sessionCacheChecker: default 5 minutes (300000 ms) 
    private static long CHECK_CACHE_INTERVAL = 3000;
    //Cache Timeout in milliseconds: default 10 minutes (600000 ms)
    private static long CACHE_TIME_OUT = 6000;
    
    /**
     * Constructor for the CacheCleaner.  Attempts to load a spcified property
     * file and warns if not found. Default values will be used.
     *  
     * @param cacheTracker required to get the current Caches
     * @param sessionTracker required to get the current HttpSessions
     */
     public CacheCleaner(CacheTracker cacheTracker, SessionTracker sessionTracker) {
		super("CacheCleaner");
        this.cacheTracker = cacheTracker;
        this.sessionTracker = sessionTracker;
		Properties cacheProperties = PropertyLoader.loadProperties(NautilusConstants.CACHE_PROPERTIES,ClassLoader.getSystemClassLoader());
		String intervalString = null;
		String idleString = null;
		if(cacheProperties!=null) {
		 intervalString = cacheProperties.getProperty("check.session.cache.tracker");
		 idleString = cacheProperties.getProperty("session.cache.timeout");
		}else {
			logger.warn("Cache Property file: "+NautilusConstants.CACHE_PROPERTIES+" was not found, using default settings!");	
		}
        if(intervalString!=null) {
        	CHECK_CACHE_INTERVAL = Long.parseLong(intervalString);
        }else {
        	logger.warn("property \"check.session.cache.tracker\" not found");
        	logger.warn("Using default value "+CHECK_CACHE_INTERVAL+" ms between checking caches");
        }
        if(idleString!=null) {
        	CACHE_TIME_OUT = Long.parseLong(idleString);
        }else {
        	logger.warn("property \"session.cache.timeout\" not found");
        	logger.warn("Using default value "+CACHE_TIME_OUT+" ms for idle caches");
        	
        }
	}
	/**
	 * This is the run method that will be in it's own thread.
	 * It get's the latest list of Sessions and Caches and iterates
	 * through the cache keys getting all the associated sessions.
	 * It checks the sessions last accessed time and sees how long
	 * the session has been idle. If it has been idle longer than the 
	 * set idle time ("session.cache.timeout") it will remove the cache.
	 * 
	 *  It should perform this action based on the setting of 
	 *  "check.session.cache.tracker" ms in the resources file.
	 * 
	 */
	public void run() {
		logger.debug("Starting CacheCleaner run() method");
		Thread myThread = Thread.currentThread();
		while (SessionTracker.isAppplicationRunning()){
			logger.debug("CacheCleaner awake: "+System.currentTimeMillis());
			logger.debug("Checking Session Caches");
			HashMap caches = CacheTracker.getActiveSessionCaches();
			SessionMap sessions = SessionTracker.getSessions();
			/*
			 * Only check the caches if there are any.
			 */
			if(caches!=null && !caches.isEmpty()) {
				Set cacheKeys = caches.keySet();
				for(Iterator i = cacheKeys.iterator();i.hasNext();) {
					String sessionId = (String)i.next();
					HttpSession session = sessions.getSession(sessionId);
					/*
					 * This is the check to see if the session has been idle
					 * long enough to toss out it's associated cache, 
					 * if it has one.  Another check that I could implement
					 * would be to check the cache statistics and see when
					 * any of the elements were last accessed, their size and
					 * other considerations to determine if they should be
					 * purged from the existing cache, rather than throwing
					 * the whole cache out.
					 */
					long idleTime = System.currentTimeMillis() - session.getLastAccessedTime();
					if(session!=null && idleTime > CACHE_TIME_OUT) {
						logger.debug("Session "+sessionId+" idle too long. Removing cache");
						CacheManagerWrapper.removeSessionCache(sessionId);
					}
				}
			}
				
	        try {
	        	logger.debug("CacheCleaner sleeping: " +System.currentTimeMillis());
	            Thread.sleep(CHECK_CACHE_INTERVAL);
	        } catch (InterruptedException e){
		            // the VM doesn't want us to sleep anymore,
		            // so get back to work
	        }
			
	    }
	}

}
