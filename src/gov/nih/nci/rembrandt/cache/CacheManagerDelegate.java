package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.dto.finding.FindingsResultset;
import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
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
 * future delegates will always be compliant with what other classes in the
 * application expect and will greatly minimize the work if we change out the
 * caching mechanism. 
 * 
 * @author BauerD
 * Feb 9, 2005
 * Oct 10, 2005 Added by SahniH
 * public SessionCriteriaBag getSessionCriteriaBag(String sessionId) and
 * public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag);
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
     * *  IMPORTANT! Signature missing from the ConvenientCache Inteface
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
  	 * *  IMPORTANT! Signature missing from the ConvenientCache Inteface
  	 * 
  	 * @param sessionId
  	 * @return
  	 */
    private Cache getSessionCache(String sessionId) {
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getObjectFromSessionCache(java.lang.String, java.lang.String)
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#removeSessionCache(java.lang.String)
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getCacheList()
	 */
    public String[] getCacheList() {
    	manager.getCacheNames();
        return manager.getCacheNames();
    }
    
    
    /**
     * When ever a cache is created this method notifies all
     * registered CacheListeners
     * *  IMPORTANT! Signature missing from the ConvenientCache Inteface
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
     * Whenever a cache is destroyed this method notifies all
     * registered CacheListeners.
     * *  IMPORTANT! Signature missing from the ConvenientCache Inteface
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
    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#addCacheListener(gov.nih.nci.rembrandt.cache.CacheListener)
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#removeCacheListener(gov.nih.nci.rembrandt.cache.CacheListener)
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
    /* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getReportBean(java.lang.String, java.lang.String)
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
		}catch(CacheException ce) {
			logger.error("Getting the ReportBean from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a ReportBean");
			logger.error(cce);
		}
		return reportBean;
	}
   
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getReportBean(java.lang.String, java.lang.String, gov.nih.nci.caintegrator.dto.view.View)
	 */
	public ReportBean getReportBean(String sessionId, String queryName, View view) {
		ReportBean reportBean = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(queryName);
			if(element!=null) {
				reportBean = (ReportBean)element.getValue();
				//Check the reportBean for view incompatabilites
				View cachedView = (View)reportBean.getAssociatedQuery().getAssociatedView();
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
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#addToSessionCache(java.lang.String, java.io.Serializable, java.io.Serializable)
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getTempReportName(java.lang.String)
	 */
	public String getTempReportName(String sessionId) {
		String tempReportName = null;
		Cache sessionCache = getSessionCache(sessionId);
		try {
			Element element = sessionCache.get(RembrandtConstants.REPORT_COUNTER);
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
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#checkLookupCache(java.lang.String)
	 */
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
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#addToApplicationCache(java.io.Serializable, java.io.Serializable)
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getQuery(java.lang.String, java.lang.String)
	 */
	public CompoundQuery getQuery(String sessionId, String queryName) {
		
		//Use the getReportBean(String, String) method as we will
		//continue to use the same view as was used previously
		ReportBean bean = getReportBean(sessionId, queryName);
		CompoundQuery compoundQuery = null;
		if(bean!=null) {
			Queriable queriable = bean.getAssociatedQuery();
			if(queriable instanceof CompoundQuery) {
				compoundQuery = (CompoundQuery)queriable;
				/*
				 * This is here because we have found instances where a
				 * reportBean has been stored without the associated query
				 * having a sessionId in it.  This check really shouldn't be
				 * here but it is a good fix for .51.  We will revisit later.
				 */
				compoundQuery.setSessionId(sessionId);
			}
		}
		return compoundQuery;
	
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getSessionQueryBag(java.lang.String)
	 */
	public SessionQueryBag getSessionQueryBag(String sessionId) {
		Cache sessionCache =  this.getSessionCache(sessionId);
		SessionQueryBag theBag = null;
		try {
			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_QUERY_BAG_KEY);
			theBag = (SessionQueryBag)cacheElement.getValue();
		}catch(CacheException ce) {
			logger.error("Retreiving the SessionQueryBag threw an exception for session: "+sessionId);
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("Someone put something other than a SessionQueryBag in the cache as a SessionQueryBag");
			logger.error(cce);
		}catch(NullPointerException npe){
			logger.debug("There is no query bag for session: "+sessionId);		
		}
		/**
		 * There is no SessionQueryBag for this session, create one
		 */
		if(theBag==null) {
			
			logger.debug("Creating new SessionQueryBag");
			theBag = new SessionQueryBag();
		}
		return theBag;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getSampleSetNames(java.lang.String)
	 */
	public List getSampleSetNames(String sessionId) {
		List names = new ArrayList();
		Cache sessionCache = getSessionCache(sessionId);
		try {
			List keys = sessionCache.getKeys();
			for(Iterator i = keys.iterator();i.hasNext();) {
				Element element = sessionCache.get((String)i.next());
				Object object = element.getValue();
				if(object instanceof ReportBean) {
					ReportBean bean = (ReportBean)object;
					if(bean.isSampleSetQuery()) {
						names.add(bean.getResultantCacheKey());
					}
				}
			}
		}catch(CacheException ce) {
			logger.error(ce);
		}
		return names;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getAllSampleSetReportBeans(java.lang.String)
	 */
	public Collection getAllSampleSetReportBeans(String sessionId) {
		Collection beans = new ArrayList();
		List beanNames = getSampleSetNames(sessionId);
		for(Iterator i = beanNames.iterator();i.hasNext();) {
			beans.add(this.getReportBean(sessionId, (String)i.next()));
		}
		return beans;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getAllReportBeans(java.lang.String)
	 */
	public Collection getAllReportBeans(String sessionId) {
		Collection beans = new ArrayList();
		Cache sessionCache = getSessionCache(sessionId);
		try {
			List keys = sessionCache.getKeys();
			for(Iterator i = keys.iterator();i.hasNext();) {
				Element element = sessionCache.get((String)i.next());
				Object object = element.getValue();
				if(object instanceof ReportBean) {
						beans.add(object);
				}
			}
		}catch(CacheException ce) {
			logger.error(ce);
		}
		return beans;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#putSessionQueryBag(java.lang.String, gov.nih.nci.rembrandt.web.bean.SessionQueryBag)
	 */
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag) {
		this.addToSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY, theBag );
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#addSessionGraphingData(java.lang.String, gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData)
	 */
	public void addSessionGraphingData(String sessionId, CachableGraphData graphData) {
		this.addToSessionCache(sessionId,graphData.getId(),graphData);
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getSessionGraphingData(java.lang.String, java.lang.String)
	 */
	public CachableGraphData getSessionGraphingData(String sessionId, String graphId) {
		Cache sessionCache = this.getSessionCache(sessionId);
		CachableGraphData graphData = null;
		try {
				Element element = sessionCache.get(graphId);
				graphData = (CachableGraphData)element.getValue();
			} catch (IllegalStateException e) {
				logger.error(e);
			} catch (CacheException e) {
				logger.error(e);
			}catch(ClassCastException cce) {
				logger.error(cce);
			}
			return graphData;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getSessionCriteriaBag(java.lang.String)
	 */
	public SessionCriteriaBag getSessionCriteriaBag(String sessionId) {
		Cache sessionCache =  this.getSessionCache(sessionId);
		SessionCriteriaBag theBag = null;
		try {
			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_CRITERIA_BAG_KEY);
			theBag = (SessionCriteriaBag)cacheElement.getValue();
		}catch(CacheException ce) {
			logger.error("Retreiving the SessionCriteriaBag threw an exception for session: "+sessionId);
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("Someone put something other than a SessionCriteriaBag in the cache as a SessionQueryBag");
			logger.error(cce);
		}catch(NullPointerException npe){
			logger.debug("There is no query bag for session: "+sessionId);		
		}
		/**
		 * There is no SessionQueryBag for this session, create one
		 */
		if(theBag==null) {
			
			logger.debug("Creating new SessionCriteriaBag");
			theBag = new SessionCriteriaBag();
		}
		return theBag;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#putSessionCriteriaBag(java.lang.String, gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag)
	 */
	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag) {
		this.addToSessionCache(sessionId,RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag );
		
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getAllFindingsResultsets(java.lang.String)
	 */
	public Collection<Finding> getAllFindings(String sessionId){
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
	 * @see gov.nih.nci.rembrandt.cache.ConvenientCache#getAllFindingsResultsets(java.lang.String, java.lang.String)
	 */
	public Finding getFinding(String sessionId, String taskId){
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

}
