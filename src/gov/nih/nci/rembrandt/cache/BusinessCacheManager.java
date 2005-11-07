package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.helper.SessionTempReportCounter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import org.apache.log4j.Logger;

public class BusinessCacheManager implements BusinessTierCache{
	
    //This value must match the name of the cache in the configuration xml file
    static final private String REMBRANDT_CACHE = "applicationCache"; 
    static private transient List cacheListeners;
    static private Logger logger = Logger.getLogger(BusinessCacheManager.class);
    static private CacheManager manager = null;
    static private BusinessCacheManager instance = null;       
    //Create the CacheManager and the ApplicationCache
    static {
     	try {
           instance = new BusinessCacheManager();
     	   //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
          if(manager==null) {
        	  manager = CacheManager.create();
        	 logger.debug("Creating ehCache manager instance");
          }else {
        	  manager = CacheManager.getInstance();
        	  logger.debug("Getting ehCache manager instance");
          }
        }catch(Throwable t) {
            logger.error("FATAL: CacheManager and Business Cache not created!");
            logger.error(t);
            throw new ExceptionInInitializerError(t);
        }
    }
 
    private BusinessCacheManager() {}
   
    private Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(BusinessCacheManager.REMBRANDT_CACHE)) {
        	applicationCache = new Cache(BusinessCacheManager.REMBRANDT_CACHE, 1, true, false, 5, 2);
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
        	applicationCache = manager.getCache(BusinessCacheManager.REMBRANDT_CACHE);
        }
      
    	return applicationCache;
    }
  	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getSessionCache(java.lang.String)
	 */
    public Cache getSessionCache(String sessionId) {
        Cache sessionCache = null; 
        if( manager!=null && !manager.cacheExists(sessionId) ) {
            sessionCache = new Cache(sessionId, 1000, true, true, 1200, 600);
            logger.debug("New SessionCache created: "+sessionId);
            Element counter = new Element(RembrandtConstants.REPORT_COUNTER, new SessionTempReportCounter());
            
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

    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getObjectFromSessionCache(java.lang.String, java.lang.String)
	 */
    public Object getObjectFromSessionCache(String sessionId, String key) {
    	Cache sessionCache = getSessionCache(sessionId);
    	Object returnObject = null;
    	try {
			Element element = sessionCache.get(key);
			returnObject = element.getValue();
		} catch (IllegalStateException e) {
			logger.error(e);
		} catch (CacheException e) {
			logger.error(e);
		}
    	return returnObject;
    }
    
    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#removeSessionCache(java.lang.String)
	 */
    public boolean removeSessionCache(String sessionId) {
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
    
     /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getCacheList()
	 */
    public String[] getCacheList() {
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

    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#addCacheListener(gov.nih.nci.rembrandt.cache.CacheListener)
	 */
    @SuppressWarnings("unchecked")
	public void addCacheListener(CacheListener cacheListener) {
        if(cacheListeners==null) {
            cacheListeners = new ArrayList();
        }
        logger.debug("New CacheListener added");
        cacheListeners.add(cacheListener);
    }

    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#removeCacheListener(gov.nih.nci.rembrandt.cache.CacheListener)
	 */
    public void removeCacheListener(CacheListener cacheListener) {
        if(cacheListener!=null) {
            logger.debug("CacheListener removed");
            cacheListeners.remove(cacheListener);
        }
        if(cacheListeners.isEmpty()) {
            cacheListeners = null;
            logger.debug("Setting CacheListeners to null, there are no more listeners");
        }
    }
 
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#addToSessionCache(java.lang.String, java.io.Serializable, java.io.Serializable)
	 */
	public void addToSessionCache(String sessionId, Serializable key, Serializable value) {
		Cache sessionCache = getSessionCache(sessionId);
		Element element = new Element(key, value);
		try {	
			sessionCache.put(element);
		}catch(IllegalStateException ise) {
			logger.error("Placing object in SessionCache threw IllegalStateException");
			logger.error(ise);
		}catch(IllegalArgumentException iae) {
			logger.error("Placing object in SessionCache threw IllegalArgumentException");
			logger.error(iae);
		}
	}
 
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#addToApplicationCache(java.io.Serializable, java.io.Serializable)
	 */
	public void addToApplicationCache(Serializable key, Serializable value) {
		Cache applicationCache = getApplicationCache();
		try {
			Element element = new Element(key, value);
			applicationCache.put(element);
		}catch(IllegalStateException ise) {
			logger.error("Checking applicationCache threw IllegalStateException");
			logger.error(ise);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a Collection");
			logger.error(cce);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getAllSessionFindings(java.lang.String)
	 */
	public Collection<Finding> getAllSessionFindings(String sessionId){
		Collection<Finding> beans = new ArrayList<Finding>();
		Cache sessionCache = getSessionCache(sessionId);
		try {
			List keys = sessionCache.getKeys();
			for(Iterator i = keys.iterator();i.hasNext();) {
				Element element = sessionCache.get((String)i.next());
				Object object = element.getValue();
				if(object instanceof Finding) {
					beans.add((Finding)object);
				}
			}
		}catch(CacheException ce) {
			logger.error(ce);
		}
		return beans;
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getSessionFinding(java.lang.String, java.lang.String)
	 */
	public Finding getSessionFinding(String sessionId, String taskId){
		Finding finding = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(taskId);
			if(element!=null) {
				if(element.getValue() instanceof Finding)
					finding = (Finding)element.getValue();
			}
		}catch(IllegalStateException ise) {
			logger.error("Getting the FindingsResultset from cache threw IllegalStateException");
			logger.error(ise);
		}catch(CacheException ce) {
			logger.error("Getting the FindingsResultset from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a FindingsResultset");
			logger.error(cce);
		}
		return finding;
	}
	
	 /**
     * Returns the singlton instance  
     * 
     * @return the single instance of the BusinessCacheManager
     */
    public static BusinessCacheManager getInstance() {
    	return instance;
    }
	public Object getFromApplicationCache(String lookupType) {
		Cache applicationCache = this.getApplicationCache();
		Object applicationCacheObject = null;
		try {
			Element element = applicationCache.get(lookupType);
			applicationCacheObject = element.getValue();
		
		}catch(IllegalStateException ise) {
			logger.error("Getting the FindingsResultset from cache threw IllegalStateException");
			logger.error(ise);
		}catch(CacheException ce) {
			logger.error("Getting the FindingsResultset from cache threw a new CacheException");
			logger.error(ce);
		}
		return applicationCacheObject;
	}

}
