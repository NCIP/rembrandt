package gov.nih.nci.nautilus.cache;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.report.SessionTempReportCounter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
/**
 * The CacheManagerWrapper is intended to act as the initializer for the 
 * application CacheManager and a factory for the session cache.  It is an 
 * observable class in that it accepts CacheListeners that will be notified 
 * whenever a sessionCache is created or removed. In case you are wondering I 
 * had to call it an overlord as CacheManager was already taken...
 * 
 * Later, I suppose for the 1.0 release, there will be a new kind of cache that
 * persists a users result sets between sessions and server shutdowns.  Also it
 * may be that we start caching based on the userId and not the sessionId.
 * These are all considerations for a later time, but would be easily added
 * to this class.
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class CacheManagerWrapper{
	
    //This value must match the name of the cache in the configuration xml file
    static final private String REMBRANDT_CACHE = "applicationCache"; 
    static private transient List cacheListeners;
    static private Logger logger = Logger.getLogger(CacheManagerWrapper.class);
    static private CacheManager manager = null;
           
    //Create the CacheManager and the ApplicationCache
    static {
     	try {
           //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
    		manager = CacheManager.create();
    		logger.debug("CacheManger created");
        }catch(Throwable t) {
            logger.error("FATAL: CacheManager and Application Cache not created!");
            logger.error(t);
            throw new ExceptionInInitializerError(t);
        }
    }
 
    private CacheManagerWrapper() {}
    /**
     * returns the WebApplication cache. If the first time this has been called
     * it will instantiate the cache.
     * 
     * @return  The Application Cache
     */
    static public Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(CacheManagerWrapper.REMBRANDT_CACHE)) {
        	applicationCache = new Cache(CacheManagerWrapper.REMBRANDT_CACHE, 1, true, false, 5, 2);
            logger.debug("New ApplicationCache created");
            try {
            	manager.addCache(applicationCache);
            }catch(ObjectExistsException oee) {
                logger.error("ApplicationCache creation failed.");
                logger.error(oee);
            }catch(CacheException ce) {
                logger.error("ApplicationCache creation failed.");
                logger.error(ce);
            }
        }else if(manager!=null){
        	applicationCache = manager.getCache(CacheManagerWrapper.REMBRANDT_CACHE);
        }
      
    	return applicationCache;
    }
  	/**
  	 * Returns a cache for the given sessionId. If there is no cache currently
  	 * created it will create one, store it and return the new instance.
  	 * 
  	 * @param sessionId
  	 * @return
  	 */
    static public Cache getSessionCache(String sessionId) {
        Cache sessionCache = null; 
        if( manager!=null && !manager.cacheExists(sessionId) ) {
            sessionCache = new Cache(sessionId, 1000, true, true, 1200, 600);
            logger.debug("New SessionCache created: "+sessionId);
            Element counter = new Element(NautilusConstants.REPORT_COUNTER, new SessionTempReportCounter());
            
            try {
            	manager.addCache(sessionCache);
            	fireCacheAddEvent(sessionId);
            	sessionCache.put(counter);
            }catch(ObjectExistsException oee) {
                logger.error("Attempted to create the same session cache twice.");
                logger.error(oee);
            }catch(CacheException ce) {
                logger.error("Attempt to create session cache failed.");
                logger.error(ce);
            }
        }else if(manager!=null){
        	sessionCache = manager.getCache(sessionId);
        }
        return sessionCache;
    }
    /**
     * Removes the cache for the specified sessionId, if one exists.
     * @param sessionId Session to remove
     * @return True-cache found and removed, False-Cache found for that session
     */
    static public boolean removeSessionCache(String sessionId) {
    	if(manager!=null && manager.cacheExists(sessionId)) {
    		manager.removeCache(sessionId);
            logger.debug("SessionCache removed: "+ sessionId);
            fireCacheRemoveEvent(sessionId);
            //cache found and removed
            return true;
        }else {
        	//there was no sessionCache to remove
        	logger.debug("There was no sessionCache for : "+ sessionId+" to remove.");
        	return false;
        }
    }
    
    static public String[] getCacheList() {
    	manager.getCacheNames();
        return manager.getCacheNames();
    }
    
    
    /**
     * When ever a cache is created this method notifies all
     * registered CacheListeners
     * 
     * @param cacheId the cache id
     */
    static private void fireCacheAddEvent(String cacheId) {
        if(cacheListeners!=null && !cacheListeners.isEmpty()) {
            logger.debug("Fire cacheAddEvent");
            for(Iterator i = cacheListeners.iterator();i.hasNext();) {
                ((CacheListener)i.next()).cacheCreated(cacheId);
            }
        	
        }
    }
    /**
     * When ever a cache is destroyed this method notifies all
     * registered CacheListeners.
     * 
     * @param cacheId the cache id
     */
    static private void fireCacheRemoveEvent(String cacheId) {
        if(cacheListeners!=null && !cacheListeners.isEmpty()) {
            logger.debug("Fire cacheRemoveEvent");
            for(Iterator i = cacheListeners.iterator();i.hasNext();) {
                ((CacheListener)i.next()).cacheRemoved(cacheId);
            }
        }
    }
    /**
     * Adds a cache listener to the CacheManagerWrapper
     * 
     * @param cacheListener
     */
    static public void addCacheListener(CacheListener cacheListener) {
        if(cacheListeners==null) {
            cacheListeners = new ArrayList();
        }
        logger.debug("New CacheListener added");
        cacheListeners.add(cacheListener);
    }
    /**
     * removes a cache listener from the CacheManagerWrapper
     * @param cacheListener
     */
    static public void removeCacheListener(CacheListener cacheListener) {
        if(cacheListener!=null) {
            logger.debug("CacheListener removed");
            cacheListeners.remove(cacheListener);
        }
        if(cacheListeners.isEmpty()) {
            cacheListeners = null;
            logger.debug("Setting CacheListeners to null, there are no more listeners");
        }
    }
}
