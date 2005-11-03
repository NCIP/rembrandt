package gov.nih.nci.rembrandt.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
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
 * This new cache delegate was written to provide a cache written specifically
 * for the Presentation tier.  At the time of writing methods have been removed
 * from the ConvenientCache interface and added to the PresentationTierCache 
 * interface.  This class will still make use of the original CacheMangerDelegate
 * until we take the time to actually create a seperate cache for the presentation
 * tier.  For now that implementation detail will be hidden from the WebTier
 * and the future transistion should be equally transparent.
 *  
 * @author BauerD
 */
public class PresentationCacheManager implements PresentationTierCache{
	private static Logger logger = Logger.getLogger(PresentationCacheManager.class);
	private static BusinessTierCache _businessTierCache = BusinessCacheManager.getInstance();
	private static PresentationTierCache myInstance;
	
	static {
	     	try {
	           myInstance = new PresentationCacheManager();
	     	   logger.debug("PresentationCacheManager created");
	        }catch(Throwable t) {
	            logger.error("FATAL: PresentationCacheManager not created!");
	            logger.error(t);
	            throw new ExceptionInInitializerError(t);
	        }
	}
	private PresentationCacheManager() {}
	

	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag) {
		_businessTierCache.addToSessionCache(sessionId,RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag );
		
	}

	public SessionCriteriaBag getSessionCriteriaBag(String sessionId) {
		Cache sessionCache =  _businessTierCache.getSessionCache(sessionId);
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
		_businessTierCache.addToSessionCache(sessionId,graphData.getId(),graphData);
	}
	
	public CachableGraphData getSessionGraphingData(String sessionId, String graphId) {
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
		Cache sessionCache =  _businessTierCache.getSessionCache(sessionId);
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
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
    	return _businessTierCache.getObjectFromSessionCache(sessionId, key);
    
    }
 
	public String getTempReportName(String sessionId) {
		String tempReportName = null;
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
	
	public Collection checkLookupCache(String lookupType) {
		return _businessTierCache.checkLookupCache(lookupType);
	}
	 
	public boolean removeSessionCache(String sessionId) {
		return _businessTierCache.removeSessionCache(sessionId);
	 }
	

	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag) {
		_businessTierCache.addToSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY, theBag );
	}
	
	public void addToApplicationCache(Serializable key, Serializable value) {
		_businessTierCache.addToApplicationCache(key,value);
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
		Cache sessionCache = _businessTierCache.getSessionCache(sessionId);
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
		 return _businessTierCache.getCacheList();
	 }
	 
	 public static PresentationTierCache getInstance() {
		 return myInstance;
	 }


	public void addToSessionCache(String sessionId, String key, Serializable value) {
		_businessTierCache.addToSessionCache(sessionId, key, value);
		
	}
	
	 
}