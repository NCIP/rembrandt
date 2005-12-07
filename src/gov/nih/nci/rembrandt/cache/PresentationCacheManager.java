package gov.nih.nci.rembrandt.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.helper.SessionTempReportCounter;
/**
 * PresentationCacheManager was written to provide a cache written specifically
 * for the Presentation tier.  At the time of writing, methods have been removed
 * from the ConvenientCache interface and added to the PresentationTierCache 
 * interface.  
 *  
 * @author BauerD
 */
public class PresentationCacheManager implements PresentationTierCache{
	private static final String PRESENTATION_CACHE = "PresentationTierCache";
	//DO NOT change PERSISTED_SESSIONS_CACHE value without modifying ehcache.xml
	private static final String PERSISTED_SESSIONS_CACHE = "persistedSessionsCache";
	private static Logger logger = Logger.getLogger(PresentationCacheManager.class);
	private static PresentationTierCache myInstance;
	static private CacheManager manager = null;
	static private Cache presentationCache = null;
	static {
	   	try {
     		myInstance = new PresentationCacheManager();
     	   //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
     		logger.debug("Getting ehCache manager instance");
         	manager = CacheManager.getInstance();
        	logger.debug("CacheManger available");
        }catch(Throwable t) {
            logger.error("FATAL: Problem creating CacheManager!");
            logger.error(t);
            throw new ExceptionInInitializerError(t);
        }
 	}
	private PresentationCacheManager() {}
	/**
	 * Returns the Cache that is intended to be used to store application scoped
	 * variables 
	 * @return
	 */
	private Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(PresentationCacheManager.PRESENTATION_CACHE)) {
    		/**
        	 * Here are the parameters that we are using for creating the presentation
        	 * tier Application Cache
        	 *  	CacheName = PRESENTATION_CACHE;
        	 *  	Max Elements in Memory = 100;
        	 *  	Overflow to disk = false;
        	 *  	Make the cache eternal = true;
        	 *  	Elements time to live in seconds = 12000 (200 minutes, this not eternal in case the data changes);
        	 *  	Elements time to idle in seconds = 0 (Special setting which means never check);
         	 */
    		presentationCache = new Cache(PresentationCacheManager.PRESENTATION_CACHE, 100, false, true, 120000, 0);
            logger.debug("New ApplicationCache created");
            try {
            	manager.addCache(presentationCache);
            }catch(ObjectExistsException oee) {
                logger.error("ApplicationCache creation failed.");
                logger.error(oee);
            }catch(CacheException ce) {
                logger.error("ApplicationCache creation failed.");
                logger.error(ce);
            }
        }else if(manager!=null){
        	presentationCache = manager.getCache(PresentationCacheManager.PRESENTATION_CACHE);
        }
      
    	return presentationCache;
    }
	/**
	 * Adds the SessionCriteriaBag to the session cache of the session specified
	 * @param sessionId
	 * @param the Bag
	 */
	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag) {
		addToSessionCache(sessionId,RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag );
		
	}
	/**
	 * If the userName has a previously persisted SessionCache it will
	 * reload the old data from the persisted cache into a new sessionCache
	 * @param userName
	 * @param sessionId
	 */
	public boolean reloadSessionCache(String userName, String sessionId) {
		//Create a new Temporary SessionCache
		Cache sessionCache = getSessionCache(sessionId);
		logger.debug("Created new sessionCache");
		HashMap persistedElements = getPersistedElements(userName);
		if(persistedElements!=null) {
			/*
			 * Throw out the new temp counter that gets placed
			 * in every new session cache.  This is a hack and with
			 * this new use case of recreating persisted caches, we may 
			 * want to later revisit the automatic creation of TempReportCounter
			 * for every new session.
			 * -DB
			 */
			boolean removedTempCounter = sessionCache.remove(RembrandtConstants.REPORT_COUNTER);
			try {
				Set keys = persistedElements.keySet();
				for(Object key: keys) {
					Element element = (Element)persistedElements.get(key);
					sessionCache.put(element);
				}
			} catch (IllegalStateException e) {
				logger.error(e);
			}
			/*
			 * There was a persisted cache available for that userName
			 */
			return true;
		}else {
			/*
			 * There was not persisted cache for that userName available
			 */
			return false;
		}
	}
	/**
	 * Returns either the userName's previously saved session information or null
	 * if one is not found.
	 * 
	 * @param userName
	 * @return
	 */
	private HashMap getPersistedElements(String userName) {
		HashMap persistedElementsMap = null;
		if(manager!=null){
			Cache persistedCache = manager.getCache(PresentationCacheManager.PERSISTED_SESSIONS_CACHE);
			try {
				Element persistedElements = persistedCache.get(userName);
				if(persistedElements!=null)
					persistedElementsMap = (HashMap)persistedElements.getValue();
			} catch (IllegalStateException e) {
				logger.error(e);
			} catch (CacheException e) {
				logger.error(e);
			}
        }
		return persistedElementsMap;
	}
	
	/**
	 * This method is the workhorse of the PresentationCacheManager, almost
	 * every other mehtod makes use of this method to retrieve and create all
	 * session caches for the cache manager.
	 * 
	 * @param sessionId
	 * @param createTempCounter 
	 * @return
	 */
    private Cache getSessionCache(String sessionId) {
        Cache sessionCache = null;
        /*
         * Process the sessionId to make sure that we have a unique sessionName for
         * the presentation tier cache
         */
        String uniqueSession = processSessionId(sessionId);
        if( manager!=null && !manager.cacheExists(uniqueSession) ) {
        	/**
        	 * Here are the parameters that we are using for creating the presentation
        	 * tier session caches.  These caches are only stored in Memory and 
        	 * never persisted out to disk
        	 * 
        	 *  	CacheName = the sessionId;
        	 *  	Max Elements in Memory = 1000;
        	 *  	Overflow to disk = false;
        	 *  	Make the cache eternal = false;
        	 *  	Elements time to live in seconds = 12000 (200 minutes, this not eternal in case the data changes);
        	 *  	Elements time to idle in seconds = 0 (Special setting which means never check);
         	 */
            sessionCache = new Cache(uniqueSession, 1000, false, false, 12000, 12000);
            logger.debug("New Presentation SessionCache created: "+sessionId);
            try {
            	manager.addCache(sessionCache);
            	Element counter = new Element(RembrandtConstants.REPORT_COUNTER, new SessionTempReportCounter());
            	sessionCache.put(counter);
            }catch(ObjectExistsException oee) {
                logger.error("Attempted to create the same session cache twice.");
                logger.error(oee);
            }catch(CacheException ce) {
                logger.error("Attempt to create session cache failed.");
                logger.error(ce);
            }
        }else if(manager!=null){
        	logger.debug("Returning an existing session cache");
        	sessionCache = manager.getCache(uniqueSession);
        }
        return sessionCache;
    }
    /**
     * Comment this!
     * @param sessionId
     * @return
     */
	public SessionCriteriaBag getSessionCriteriaBag(String sessionId) {
		Cache sessionCache =  this.getSessionCache(sessionId);
		SessionCriteriaBag theBag = null;
		try {
			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_CRITERIA_BAG_KEY);
			if(cacheElement!=null) {
				theBag = (SessionCriteriaBag)cacheElement.getValue();
			}
			if(theBag==null){
				logger.debug("There is no query bag for session: "+sessionId);
				logger.debug("Creating new SessionCriteriaBag");
				theBag = new SessionCriteriaBag();
				Element element = new Element(RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag);
				sessionCache.put(element);
			}
		}catch(CacheException ce) {
			logger.error("Retreiving the SessionCriteriaBag threw an exception for session: "+sessionId);
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("Someone put something other than a SessionCriteriaBag in the cache as a SessionQueryBag");
			logger.error(cce);
		}catch(NullPointerException npe){
			logger.error(npe);		
		}
		return theBag;
	}
	/**
	 * Comment this!
	 * @param sessionId
	 * @param graphData
	 */
	public void addSessionGraphingData(String sessionId, CachableGraphData graphData) {
		myInstance.addToSessionCache(sessionId,graphData.getId(),graphData);
	}
	/**
	 * Comment this!
	 * @param sessionId
	 * @param graphId
	 * @return
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
	/***
	 * Comment this!
	 * @param sessionId
	 * @return
	 */
	public Collection<ReportBean> getAllReportBeans(String sessionId) {
		Collection<ReportBean> beans = new ArrayList<ReportBean>();
		Cache sessionCache = this.getSessionCache(sessionId);
		try {
			List keys = sessionCache.getKeys();
			for(Iterator i = keys.iterator();i.hasNext();) {
				Element element = sessionCache.get((String)i.next());
				Object object = element.getValue();
				if(object instanceof ReportBean) {
						beans.add((ReportBean)object);
				}
			}
		}catch(CacheException ce) {
			logger.error(ce);
		}
		return beans;
	}
	/**
	 * Comment this!
	 * @param sessionId
	 * @return
	 */
	public Collection getAllSampleSetReportBeans(String sessionId) {
		Collection beans = new ArrayList();
		List beanNames = getSampleSetNames(sessionId);
		for(Iterator i = beanNames.iterator();i.hasNext();) {
			beans.add(this.getReportBean(sessionId, (String)i.next()));
		}
		return beans;
	}
	/**
	 * Returns the SessionQueryBag for the session whose id was passed
	 * 
	 * @param sessionId
	 */
	public SessionQueryBag getSessionQueryBag(String sessionId) {
		Cache sessionCache =  this.getSessionCache(sessionId);
		SessionQueryBag theBag = null;
		try {
			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_QUERY_BAG_KEY);
			if(cacheElement != null){
				theBag = (SessionQueryBag)cacheElement.getValue();
			}
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
	
	/**
	 * This is a hack method here for one reason, and is intended to only be used
	 * by the presentationTier to check for view incompatabilites.
	 *  
	 * 
	 * @param sessionId
	 * @param queryName
	 * @param view
	 * @return
	 */
	public ReportBean getReportBean(String sessionId, String queryName, View view) {
		ReportBean reportBean = null;
		Cache sessionCache = this.getSessionCache(sessionId);
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
	/**
	 * Returns the ReportBean with the queryName and stored in the session whose
	 * id was passed.
	 * 
	 * @param sessionId
	 * @param queryName
	 * @return
	 */
    public ReportBean getReportBean(String sessionId, String queryName) {
    	ReportBean reportBean = null;
		Cache sessionCache = this.getSessionCache(sessionId);
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
    /**
     * Will look in the session cache of the sessionId that was passed and 
     * look for a cache element stored under the key that was passed.
     * 
     * @param sessionId
     * @param key
     * @return
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
		} catch (NullPointerException e){
            logger.error(e);
        }
    	return returnObject;
    }
    /**
     * This will return a previously unused Report Name that can be applied to
     * unnamed result sets.
     * 
     * @param sessionId is the sessionId that you are requesting a unique
     * report name for
     * @return
     */
	public String getTempReportName(String sessionId) {
		String tempReportName = null;
		Cache sessionCache = this.getSessionCache(sessionId);
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
	/**
	 * This should be the only place that the PresentationCache delegates the
	 * work to the _businessTier
	 * 
	 * @return
	 */
	public Collection checkLookupCache(String lookupType) {
		Cache applicationCache = this.getApplicationCache();
		Collection lookpCollection = null;
		try {
			Element element = applicationCache.get(lookupType);
			if(element!=null) {
				if(element.getValue() instanceof Collection)
					lookpCollection = (Collection)element.getValue();
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
		return lookpCollection;
	}
	 
	/**
	 * Comment this!
	 * @param sessionId
	 * @return
	 */
	public boolean removeSessionCache(String rawSessionId) {
		/*
		 * process the sessionId to make sure that we have a session id
		 * unique to the presentation tier cache
		 */
		String safeSessionId = processSessionId(rawSessionId);
    	if(manager!=null && manager.cacheExists(safeSessionId)) {
    		manager.removeCache(safeSessionId);
            logger.debug("SessionCache removed: "+ rawSessionId);
            //cache found and removed
            return true;
        }else {
        	//there was no sessionCache to remove
        	logger.debug("There was no sessionCache for : "+ rawSessionId+" to remove.");
        	return false;
        }
    }
	
	/***
	 * Comment this!
	 * @param sessionId
	 * @param theBag
	 */
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag) {
		addToSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY, theBag );
	}
	
	/**
	 * This method will simply add the key/value pair to the Presentation Tier 
	 * main cache making it accessable by anyone in the presentation tier.
	 * 
	 * @param key
	 * @param value
	 */
	public void addToPresentationCache(Serializable key, Serializable value) {
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
	/***
	 * Comment this!
	 * @param sessionId
	 * @param queryName
	 * @return
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
	/***
	 * Returns a list of all the names of all the Sample Sets that have been stored
	 * for the given sessionId
	 * @param the session id of the cache that you want the list from
	 * @return  
	 */
	public List getSampleSetNames(String sessionId) {
		List names = new ArrayList();
		Cache sessionCache = this.getSessionCache(sessionId);
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
	/**
	 * This method will return a null value until it is actually implemented
	 * @return a String[] of all the caches currently stored
	 */
	 public String[] getCacheList(){
		 return null;
	 }
	 /**
	  * Returns the singleton instance of the PresentationTierCache
	  * 
	  * @return The singleton instance of the PresentationTierCache
	  */
	 public static PresentationTierCache getInstance() {
		 return myInstance;
	 }
	 /***
	  * Adds the Passed Serializable value object to the "Session Cache" for the
	  * sessionId passed (if it exists) under the lookup key of the passed 
	  * Serializable key
	  * 
	  *  @param The SessionId of the cache that you would like to drop this object in
	  *  @param The key that you would like to store the object under in the cache
	  *  @param The object that you would like to have stored in the cache.
	  *  
	  *  @return void
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
	 /**
	  * This is the method that is responsible for persisting a users session 
	  * across log outs.  It will check to see if the user actually has anything
	  * stored in the cache and then will copy the information over into a cache
	  * that is immediatly persisted to disk.
	  * 
	  * Right now the persisted caches are written to the System Property 
	  * "java.io.tmpdir" but could easily be changed to somewhere else. 
	  * 
	  * @param userName
	  * @param sessionId
	  * 
	  */
	public void persistUserSession(String userName, String sessionId) {
			logger.debug("Temp Directoy used to persist session information:" +System.getProperty("java.io.tmpdir"));
			logger.debug("Storing user session for later use: "+userName);
            //Grab the sessionCache for the user
            Cache sessionCache = getSessionCache(sessionId);
            if(sessionCache!=null) {
            	HashMap<Serializable, Element> persistedElements = new HashMap<Serializable, Element>();
	            try {
	            	List keys = sessionCache.getKeys();
					for(Object key: keys) {
						/**
						 * Right now only store the query map or the Temp Report Counter
						 */
						if(//RembrandtConstants.SESSION_QUERY_BAG_KEY.equals(key)) {
								//||
							RembrandtConstants.REPORT_COUNTER.equals(key)) {
							Element element = sessionCache.get((Serializable)key);
							persistedElements.put((Serializable)key, element);
						}
					}
					Cache persistedCache = manager.getCache(PresentationCacheManager.PERSISTED_SESSIONS_CACHE);
					if(persistedCache!=null) {
						persistedCache.put(new Element(userName,persistedElements));
						/**
						 * WRITE EVERYTHING TO DISK NOW!
						 */
						persistedCache.flush();		
					}else {
						logger.error("THERE IS NO CACHE FOR STORING PERSISTED SESSIONS!");
					}
						
				} catch (IllegalStateException e) {
					logger.error(e);
				}catch (CacheException e) {
					/**
					 * Most likely an object stored in the cache memory store is not serializable
					 */
					logger.error(e);
				}
            
		}
	}
	/**
	 * The only purpose of this code is to make sure we never have collisions
	 * between the Presentation and BusinessTier session caches since often 
	 * times we are using the same HttpSession Id when identifying a particualar
	 * session.  When the CacheManagers can be seperated we will be able to remove
	 * this. 
	 * 
	 * @param givenSessionId
	 * @return
	 */
	private String processSessionId(String givenSessionId) {
		String returnedSessionId = givenSessionId+"_presentation";
		return returnedSessionId;
	}
}