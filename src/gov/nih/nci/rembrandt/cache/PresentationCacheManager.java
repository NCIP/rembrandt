package gov.nih.nci.rembrandt.cache;

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
 * for the Presentation tier.  At the time of writing methods have been removed
 * from the ConvenientCache interface and added to the PresentationTierCache 
 * interface.  
 *  
 * @author BauerD
 */
public class PresentationCacheManager implements PresentationTierCache{
	private static final String PRESENTATION_CACHE = "PresentationTierCache";
	private static Logger logger = Logger.getLogger(PresentationCacheManager.class);
	private static PresentationTierCache myInstance;
	static private CacheManager manager = null;
	static private Cache presentationCache = null;
	static {
	   	try {
     		myInstance = new PresentationCacheManager();
     	   //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
          if(manager==null) {
        	  manager = CacheManager.create();
        	 logger.debug("Creating ehCache manager instance");
          }else {
        	  manager = CacheManager.getInstance();
        	  logger.debug("Getting ehCache manager instance");
          }
          logger.debug("CacheManger available");
        }catch(Throwable t) {
            logger.error("FATAL: Problem creating CacheManager!");
            logger.error(t);
            throw new ExceptionInInitializerError(t);
        }
 	}
	private PresentationCacheManager() {}
	
	private Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(PresentationCacheManager.PRESENTATION_CACHE)) {
    		presentationCache = new Cache(PresentationCacheManager.PRESENTATION_CACHE, 1, true, false, 5, 2);
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

	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag) {
		myInstance.addToSessionCache(sessionId,RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag );
		
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.cache.BusinessTierCache#getSessionCache(java.lang.String)
	 */
    private Cache getSessionCache(String sessionId) {
        Cache sessionCache = null; 
        /**
         * The following,
         * 	String uniqueSession = sessionID+"_presentation";
         * is required because at present, there is only one ehCache CacheManager
         * per Virtual Machine I believe.  This changed in ehCache 1.2 beta and
         * when after we upgrade from 1.1 can be removed, though it does no harm.
         * It is a quick way of avoiding collisions between the Business and
         * Presentation tiers.
         */
        String uniqueSession = sessionId+"_presentation";
        if( manager!=null && !manager.cacheExists(uniqueSession) ) {
            sessionCache = new Cache(uniqueSession, 1000, true, true, 1200, 600);
            logger.debug("New Presentation SessionCache created: "+sessionId);
            Element counter = new Element(RembrandtConstants.REPORT_COUNTER, new SessionTempReportCounter());
            try {
            	manager.addCache(sessionCache);
            	sessionCache.put(counter);
            }catch(ObjectExistsException oee) {
                logger.error("Attempted to create the same session cache twice.");
                logger.error(oee);
            }catch(CacheException ce) {
                logger.error("Attempt to create session cache failed.");
                logger.error(ce);
            }
        }else if(manager!=null){
        	sessionCache = manager.getCache(uniqueSession);
        }
        return sessionCache;
    }

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

	public void addSessionGraphingData(String sessionId, CachableGraphData graphData) {
		myInstance.addToSessionCache(sessionId,graphData.getId(),graphData);
	}
	
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
	
	public Collection getAllSampleSetReportBeans(String sessionId) {
		Collection beans = new ArrayList();
		List beanNames = getSampleSetNames(sessionId);
		for(Iterator i = beanNames.iterator();i.hasNext();) {
			beans.add(this.getReportBean(sessionId, (String)i.next()));
		}
		return beans;
	}

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
	 
	public boolean removeSessionCache(String sessionId) {
    	if(manager!=null && manager.cacheExists(sessionId)) {
    		manager.removeCache(sessionId);
            logger.debug("SessionCache removed: "+ sessionId);
            //cache found and removed
            return true;
        }else {
        	//there was no sessionCache to remove
        	logger.debug("There was no sessionCache for : "+ sessionId+" to remove.");
        	return false;
        }
    }
	

	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag) {
		myInstance.addToSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY, theBag );
	}
	
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
	 public String[] getCacheList() {
		 return myInstance.getCacheList();
	 }
	 
	 public static PresentationTierCache getInstance() {
		 return myInstance;
	 }


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
	
	 
}