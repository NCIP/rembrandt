package gov.nih.nci.nautilus.cache;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.util.PropertyLoader;

import java.util.Properties;



/**
 * SessionCacheCleaner checks in with the SessionCacheTracker
 * at "check.session.cache.tracker" interval and reviews the
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
public class SessionCacheCleaner extends Thread {
	
    private SessionCacheTracker sessionCacheTracker;
    
    //Time to check the sessionCacheChecker 
    private long checkCachesInterval;
    
    //Cache Timeout in milliseconds
    private long cacheTimeOut;
    
    /**
	 * 
	 */
	public SessionCacheCleaner(SessionCacheTracker sct) {
		super("SessionCacheCleaner");
        this.sessionCacheTracker = sct;
        Properties cacheProperties = PropertyLoader.loadProperties(NautilusConstants.CACHE_PROPERTIES,ClassLoader.getSystemClassLoader());
        checkCachesInterval = Long.parseLong(cacheProperties.getProperty("check.session.cache.tracker"));
        cacheTimeOut = Long.parseLong(cacheProperties.getProperty("session.cache.timeout"));
	}

	public void run() {
        
        //HashMap caches = SessionCacheTracker.getActiveSessionCaches();
        //SessionMap sessions = CacheOverlord.getSessions();
        //Set keys = caches.keySet();
             
				
	}

}
