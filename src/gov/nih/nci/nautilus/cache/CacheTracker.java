package gov.nih.nci.nautilus.cache;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * CacheTracker watches the CacheManagerWrapper for the addition and removal
 * of sessionCaches so that it knows at given moment how many sessionCaches 
 * currently exist, the max number of simultaneous sessionCaches created, and
 * when the currently existing sessionCaches were created.
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class CacheTracker implements CacheListener {
    //Class Logger
	private static Logger logger = Logger.getLogger(CacheTracker.class);
	//All current sessionCaches
	static private transient HashMap activeSessionCaches;
    //max number of active sessions since reboot
	static private int maxSessionCacheCount = 0;
	
	public CacheTracker() {}
	/**
	 * Is fired whenever a new sessionCache is created and creates and stores a
	 * CacheData object to track creation time and cacheId.
	 * 
	 * @param cacheId
	 */
	public void cacheCreated(String cacheId) {
		CacheData cacheInfo = new CacheData(System.currentTimeMillis(), cacheId);
        logger.debug("New CacheInfo registered for sessionCacheId: "+cacheId);
        if(activeSessionCaches==null) {
        	activeSessionCaches = new HashMap();
        }
        activeSessionCaches.put(cacheId, cacheInfo);
        
        if(activeSessionCaches.size()>maxSessionCacheCount) {
        	maxSessionCacheCount = activeSessionCaches.size();
        }
	}
	/**
	 * Is fired whenever a sessionCache is removed. It removes any reference
	 * to the sessionCache from the active session caches
	 */
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
    /**
     * Inner class to track creation time of caches.
     * @author BauerD
     * Feb 10, 2005
     * CacheTracker
     */
    private class CacheData{
        private long creationTime;
        private String cacheKey;
        
       CacheData(long creationTime, String cacheKey){
            this.creationTime = creationTime;
            this.cacheKey = cacheKey;
        }
    
    }
	
	
}
