package gov.nih.nci.nautilus.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ObjectExistsException;
/**
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class CacheOverlord {
	
    //This value must match the name of the cache in the configuration file
    static final private String REMBRANDT_CACHE = "applicationCache"; 
    static private transient List cacheListeners;
    
    //Maintains a reference to all active sessions
    static private SessionMap activeSessions = new SessionMap();
    static Logger logger = Logger.getLogger(CacheOverlord.class);
    static private CacheManager manager = null;
    static Cache applicationCache;
    private static CacheOverlord instance = new CacheOverlord();
    
    static {
     	try {
           //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
    		manager = CacheManager.create();
        }catch(Throwable t) {
            logger.error("FATAL: CacheManager and Application Cache not created!");
            logger.error(t);
        }
     }
    private CacheOverlord() {}
  
    static public Cache getApplicationCache() {
        if(manager!=null && applicationCache==null) {
        	applicationCache = manager.getCache(REMBRANDT_CACHE);
        }
        return applicationCache;
    }
  	
    static public Cache getSessionCache(String sessionId) {
        Cache sessionCache = null; 
        if( manager!=null && !manager.cacheExists(sessionId) ) {
            sessionCache = new Cache(sessionId, 1, true, false, 5, 2);
            logger.debug("New SessionCache created: "+sessionId);
            fireCacheAddEvent(sessionId);
            
            try {
            	manager.addCache(sessionCache);
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
    /*
     * Removes cache for the session specified
     */
    static public void removeSessionCache(String sessionId) {
    	if(manager!=null && manager.cacheExists(sessionId)) {
    		manager.removeCache(sessionId);
            logger.debug("SessionCache removed: "+ sessionId);
            fireCacheRemoveEvent(sessionId);
        }
    }
    
    static public String[] getCacheList() {
    	manager.getCacheNames();
        return manager.getCacheNames();
    }
    
    
    
    static private void fireCacheAddEvent(String cacheId) {
        if(cacheListeners!=null && !cacheListeners.isEmpty()) {
            logger.debug("Fire cacheAddEvent");
            for(Iterator i = cacheListeners.iterator();i.hasNext();) {
                ((CacheListener)i.next()).cacheCreated(cacheId);
            }
        	
        }
    }
    
    static private void fireCacheRemoveEvent(String cacheId) {
        if(cacheListeners!=null && !cacheListeners.isEmpty()) {
            logger.debug("Fire cacheRemoveEvent");
            for(Iterator i = cacheListeners.iterator();i.hasNext();) {
                ((CacheListener)i.next()).cacheRemoved(cacheId);
            }
        }
    }
    
    static public CacheOverlord getInstance() {
        return instance;
    }
    
   /*
    * adds a session to the activeSessions HashMap
    */
   public static void addSession(HttpSession session) {
   	    activeSessions.put(session.getId(), session);
        logger.debug("Storing reference to new session: "+session.getId());
   }
   
   /*
    * removes a session from the activeSessions SessionMap
    * and then removes the cache if there was one.
    */
   public static void removeSession(HttpSession session) {
        activeSessions.remove(session.getId());
        logger.debug("Removing reference to session: "+session.getId());
        //remove the cache for the dead session
        removeSessionCache(session.getId());
   }

	/**
	 * @return Returns the activeSessions.
	 */
	public static SessionMap getSessions() {
		return activeSessions;
	}
    
    static public void addCacheListener(CacheListener cacheListener) {
        if(cacheListeners==null) {
            cacheListeners = new ArrayList();
        }
        logger.debug("New CacheListener added");
        cacheListeners.add(cacheListener);
    }
    
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
