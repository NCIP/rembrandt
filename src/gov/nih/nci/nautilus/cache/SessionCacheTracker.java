package gov.nih.nci.nautilus.cache;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * SessionCacheTracker watches the CacheOverlord for the addition and removal
 * of sessionCaches so that it knows at given moment how many sessionCaches 
 * currently exist, the max number of simultaneous sessionCaches created, and
 * when the currently existing sessionCaches were created.
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class SessionCacheTracker implements CacheListener {
    private static Logger logger = Logger.getLogger(SessionCacheTracker.class);
	static private transient HashMap activeSessionCaches;
    static private int maxSessionCacheCount = 0;
	public SessionCacheTracker() {}

	public void cacheCreated(String cacheId) {
		CacheInformation cacheInfo = new CacheInformation(System.currentTimeMillis(), cacheId);
        logger.debug("New CacheInfo registered for sessionCacheId: "+cacheId);
        if(activeSessionCaches==null) {
        	activeSessionCaches = new HashMap();
        }
        activeSessionCaches.put(cacheId, cacheInfo);
        
        if(activeSessionCaches.size()>maxSessionCacheCount) {
        	maxSessionCacheCount = activeSessionCaches.size();
        }
	}

	public void cacheRemoved(String cacheId) {
        activeSessionCaches.remove(cacheId);
        logger.debug("Removing registery for sessionCacheId: "+cacheId);
	}
    
	/**
	 * @return Returns the maxCacheCount.
	 */
	public static int getMaxSessionCacheCount() {
		return maxSessionCacheCount;
	}
    
    /**
     * @return Returns a HashMap of SessionId--CacheInformation
     * key/value pairs that are used to determine when a session
     * cache should be thrown out.
     */
    public static HashMap getActiveSessionCaches() {
        return activeSessionCaches;
    }
    
    private class CacheInformation{
        private long creationTime;
        private String cacheKey;
        
       CacheInformation(long creationTime, String cacheKey){
            this.creationTime = creationTime;
            this.cacheKey = cacheKey;
        }
    
    }
	
	
}
