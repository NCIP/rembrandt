package gov.nih.nci.nautilus.cache;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.report.SessionTempReportCounter;
import gov.nih.nci.nautilus.view.GeneExprDiseaseView;
import gov.nih.nci.nautilus.view.View;
import gov.nih.nci.nautilus.view.ViewType;
import gov.nih.nci.nautilus.view.Viewable;
import gov.nih.nci.nautilus.view.ViewFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
/**
 * The CacheManagerDelegate is intended to act as the initializer for the 
 * application CacheManager and a factory for the session cache.  It is an 
 * observable class in that it accepts CacheListeners that will be notified 
 * whenever a sessionCache is created or removed. 
 *  
 * Later, I suppose for the 1.0 release, there will be a new kind of cache that
 * persists a users result sets between sessions and server shutdowns.  Also it
 * may be that we start caching based on the userId and not the sessionId.
 * These are all considerations for a later time, but would be easily added
 * to this class.
 * 
 * IMPORTANT! Any convenience methods that are added to this class, should first 
 * be added to the ConvenientCache interface.  This will make sure that any 
 * future delegates will always be comlpiant with what other classes in the
 * application expect and will greatly minimize the work if we change out the
 * cacheing mechanism. 
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class CacheManagerDelegate implements ConvenientCache{
	
    //This value must match the name of the cache in the configuration xml file
    static final private String REMBRANDT_CACHE = "applicationCache"; 
    static private transient List cacheListeners;
    static private Logger logger = Logger.getLogger(CacheManagerDelegate.class);
    static private CacheManager manager = null;
    static private CacheManagerDelegate instance = null;       
    //Create the CacheManager and the ApplicationCache
    static {
     	try {
           instance = new CacheManagerDelegate();
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
 
    private CacheManagerDelegate() {}
    /**
     * returns the WebApplication cache. If the first time this has been called
     * it will instantiate the cache.
     * 
     * @return  The Application Cache
     */
    private Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(CacheManagerDelegate.REMBRANDT_CACHE)) {
        	applicationCache = new Cache(CacheManagerDelegate.REMBRANDT_CACHE, 1, true, false, 5, 2);
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
        	applicationCache = manager.getCache(CacheManagerDelegate.REMBRANDT_CACHE);
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
    private Cache getSessionCache(String sessionId) {
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
     * 
     * @param sessionId Session to remove
     * @return True-cache found and removed, False-Cache found for that session
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
    
    public String[] getCacheList() {
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
     * Adds a cache listener to the CacheManagerDelegate
     * 
     * @param cacheListener
     */
    public void addCacheListener(CacheListener cacheListener) {
        if(cacheListeners==null) {
            cacheListeners = new ArrayList();
        }
        logger.debug("New CacheListener added");
        cacheListeners.add(cacheListener);
    }
    /**
     * removes a cache listener from the CacheManagerDelegate
     * 
     * @param cacheListener
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
    /**
     * Returns the singlton instance  
     * 
     * @return the single instance of the CacheManagerDelegate
     */
    public static CacheManagerDelegate getInstance() {
    	return instance;
    }
    /**
     * DANGER!										DANGER! 
     * This method is only intended to be used by the UI
     * to retrieve an existing result set that should be there 
     * already.  It should only be called in the instance that you
     * know that the desired view of the report has not changed from
     * when it was cached. If the view has changed, it will not check
     * to see if the resultant is compatable with the desired view.
     * It is possible to get a ClassCastException when using this method
     * with the report generators and can cause unpredictable application
     * behavior. 
     * DANGER!   									DANGER!
     *	--D Bauer
     *
     * @param sessionId
     * @param queryName
     * @return
     */
    public ReportBean getReportBean(String sessionId, String queryName) {
    	ReportBean reportBean = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(queryName);
			if(element!=null) {
				reportBean = (ReportBean)element.getValue();
			}
		}catch(IllegalStateException ise) {
			logger.error("Getting the ReportBean from cache threw IllegalStateException");
			logger.error(ise);
		}
		catch(CacheException ce) {
			logger.error("Getting the ReportBean from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a ReportBean");
			logger.error(cce);
		}
		return reportBean;
	}
   
	/**
	 * Returns a ReportBean if the Report is found in the cache.  It also contains
	 * some logic for handling incompatable views and result sets. Since
	 * Rembrandt .5, the GENE_GROUP_SAMPLE_VIEW uses a different Fact Table in
	 * the database than the GENE_SINGLE_SAMPLE_VIEW and the CLINICAL_VIEW.
	 * 
	 * @param sessionId used to get the session cache.
	 * @param queryName the desired result set (used as a key to get from the cache)
	 * @param view  The desired view for the query
	 */
	public ReportBean getReportBean(String sessionId, String queryName, View view) {
		ReportBean reportBean = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(queryName);
			if(element!=null) {
				reportBean = (ReportBean)element.getValue();
				//Check the reportBean for view incompatabilites
				View cachedView = (View)reportBean.getResultant().getAssociatedView();
				//If the requested view is a Gene Group view and...
				if(view.equals(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW))) {
					//the cached view is not a Gene Group View
					if(!view.equals(cachedView)) {
						//act like nothing was found
						//as this resultant is not compatable with the
						//desired view
						reportBean = null;
					}
				}else {
					//or if the requested view is a non Gene Group View
					//and the cachedView is a Gene Group View
					if(cachedView.equals(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW))) {
						//act like nothing was found
						//as this resultant is not compatable with the
						//desired view
						reportBean = null;
					}
				}
			}
		}catch(IllegalStateException ise) {
			logger.error("Getting the ReportBean from cache threw IllegalStateException");
			logger.error(ise);
		}
		catch(CacheException ce) {
			logger.error("Getting the ReportBean from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a ReportBean");
			logger.error(cce);
		}
		return reportBean;
	}
	/**
	 * 
	 * @param sessionId
	 * @param key
	 * @param value
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
	
	public String getTempReportName(String sessionId) {
		String tempReportName = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(NautilusConstants.REPORT_COUNTER);
			if(element!=null) {
				tempReportName = ((SessionTempReportCounter)element.getValue()).getNewTempReportName();
			}
		}catch(IllegalStateException ise) {
			logger.error("Getting the ReportBean from cache threw IllegalStateException");
			logger.error(ise);
		}
		catch(CacheException ce) {
			logger.error("Getting the ReportBean from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a ReportBean");
			logger.error(cce);
		}
		return tempReportName;
	}
	
	public Collection checkLookupCache(String lookupType) {
		Collection results = null;
		Cache applicationCache = getApplicationCache();
		try {
			Element element = applicationCache.get(lookupType);
			results = (Collection)element.getValue();
		}catch(IllegalStateException ise) {
			logger.error("Checking applicationCache threw IllegalStateException");
			logger.error(ise);
		}
		catch(CacheException ce) {
			logger.error("Checking applicationCache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a Collection");
			logger.error(cce);
		}catch(NullPointerException npe) {
			logger.debug("Lookup: "+lookupType+" not found in ApplicationCache");
		}
		return results;
	}

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
}
