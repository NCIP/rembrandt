package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.application.cache.SessionTempReportCounter;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;


import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

//import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
/**
 * RembrandtPresentationCacheManager was written to provide a cache written specifically
 * for the Presentation tier.  At the time of writing, methods have been removed
 * from the ConvenientCache interface and added to the PresentationTierCache 
 * interface.  
 *  
 * @author BauerD
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

/**
 * @author bauerd, sahnih
 *
 */
public class RembrandtPresentationCacheManager extends  gov.nih.nci.caintegrator.application.cache.PresentationCacheManager implements RembrandtPresentationTierCache{
	private static final String PRESENTATION_CACHE = "PresentationTierCache";
	//DO NOT change PERSISTED_SESSIONS_CACHE value without modifying ehcache.xml
	private static final String PERSISTED_SESSIONS_CACHE = "persistedSessionsCache";
	private static Logger logger = Logger.getLogger(RembrandtPresentationCacheManager.class);
	private static RembrandtPresentationTierCache myInstance;
	static private Cache presentationCache = null;
	static {
	   	try {
     		myInstance = new RembrandtPresentationCacheManager();
     	   //Create the cacheManager and the application cache
           //as specified in the configurationFile.xml 
     		logger.debug("Getting ehCache manager instance");
     		String configPath = System.getProperty("gov.nci.nih.rembrandt.echache.configFile");
     		if (configPath != null){
     			manager = new CacheManager(configPath);
     		}
     		else{
             	manager = CacheManager.getInstance();//use default);
     		}
        	logger.debug("CacheManger available");
        }catch(Throwable t) {
            logger.error("FATAL: Problem creating CacheManager!");
            logger.error(t);
            throw new ExceptionInInitializerError(t);
        }
 	}
	private RembrandtPresentationCacheManager() {
		super();
	}
	/**
	 * Returns the Cache that is intended to be used to store application scoped
	 * variables 
	 * @return
	 */
	private Cache getApplicationCache() {
        Cache applicationCache = null;
    	if(manager!=null && !manager.cacheExists(RembrandtPresentationCacheManager.PRESENTATION_CACHE)) {
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
    		presentationCache = new Cache(RembrandtPresentationCacheManager.PRESENTATION_CACHE, 100, false, true, 0, 0);
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
        	presentationCache = manager.getCache(RembrandtPresentationCacheManager.PRESENTATION_CACHE);
        }
      
    	return presentationCache;
    }
	/**
	 * Adds the SessionCriteriaBag to the session cache of the session specified
	 * @param sessionId
	 * @param the Bag
	 */
	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag) {
		addPersistableToSessionCache(sessionId,RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag );
		
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
		HashMap<String, Element> persistedElements = getPersistedElements(userName);
		if(persistedElements!=null||!persistedElements.isEmpty()) {
			/*
			 * Throw out the new temp counter that gets placed
			 * in every new session cache.  This is a hack and with
			 * this new use case of recreating persisted caches, we may 
			 * want to later revisit the automatic creation of TempReportCounter
			 * for every new session.
			 * -DB
			 */
			if(persistedElements.containsKey(RembrandtConstants.REPORT_COUNTER)) {
				boolean removedTempCounter = sessionCache.remove(RembrandtConstants.REPORT_COUNTER);
			}
			Set keys = persistedElements.keySet();
			for(Object key: keys) {
				sessionCache.put(persistedElements.get(key));
				
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
	private HashMap<String, Element> getPersistedElements(String userName) {
		HashMap<String, Element> newElementMap = null;
		if(manager!=null){
			Cache persistedCache = manager.getCache(RembrandtPresentationCacheManager.PERSISTED_SESSIONS_CACHE);
			try {
				newElementMap = new HashMap<String, Element>();
				Element persistedMapElement = persistedCache.get(userName);
				if(persistedMapElement!=null) {
				HashMap oldElementMap = (HashMap)persistedMapElement.getValue();
				if(oldElementMap!=null)
					try {
						Set keys = oldElementMap.keySet();
						for(Object key: keys) {
							Element element = (Element)oldElementMap.get(key);
							//Clones the session query bag
							if(RembrandtConstants.SESSION_QUERY_BAG_KEY.equals(key.toString())) {
								SessionQueryBag oldBag = (SessionQueryBag)element.getValue();
								SessionQueryBag newBag = (SessionQueryBag)oldBag.clone();
								element = new Element((String)key,(SessionQueryBag)newBag);
							}
							newElementMap.put(key.toString(),element);
						}
					} catch (IllegalStateException e) {
						logger.error(e);
					}
				}
					
			} catch (IllegalStateException e) {
				logger.error(e);
			} catch (CacheException e) {
				logger.error(e);
			}
        }
		return newElementMap;
	}
	
	/**
	 * This method is the workhorse of the RembrandtPresentationCacheManager, almost
	 * every other mehtod makes use of this method to retrieve and create all
	 * session caches for the cache manager.
	 * 
	 * @param sessionId
	 * @param createTempCounter 
	 * @return
	 */
//    private Cache getSessionCache(String sessionId) {
//        Cache sessionCache = null;
//        /*
//         * Process the sessionId to make sure that we have a unique sessionName for
//         * the presentation tier cache
//         */
//        String uniqueSession = processSessionId(sessionId);
//        if( manager!=null && !manager.cacheExists(uniqueSession) ) {
//        	/**
//        	 * Here are the parameters that we are using for creating the presentation
//        	 * tier session caches.  These caches are only stored in Memory and 
//        	 * never persisted out to disk
//        	 * 
//        	 *  	CacheName = the sessionId;
//        	 *  	Max Elements in Memory = 1000;
//        	 *  	Overflow to disk = false;
//        	 *  	Make the cache eternal = true;
//        	 *  	Elements time to live in seconds = 12000 (200 minutes, this not eternal in case the data changes);
//        	 *  	Elements time to idle in seconds = 0 (Special setting which means never check);
//         	 */
//            sessionCache = new Cache(uniqueSession, 10000, true, true, 0, 0);
//            logger.debug("New Presentation SessionCache created: "+sessionId);
//            try {
//            	manager.addCache(sessionCache);
//            	Element counter = new Element(RembrandtConstants.REPORT_COUNTER, new SessionTempReportCounter());
//            	sessionCache.put(counter);
//            }catch(ObjectExistsException oee) {
//                logger.error("Attempted to create the same session cache twice.");
//                logger.error(oee);
//            }catch(CacheException ce) {
//                logger.error("Attempt to create session cache failed.");
//                logger.error(ce);
//            }
//        }else if(manager!=null){
//        	logger.debug("Returning an existing session cache");
//        	sessionCache = manager.getCache(uniqueSession);
//        }
//        return sessionCache;
//    }
    /**
     * Comment this!
     * @param sessionId
     * @return
     */
	public SessionCriteriaBag getSessionCriteriaBag(String sessionId) {
		Cache sessionCache =  this.getSessionCache(sessionId);
		SessionCriteriaBag theBag = null;
		try {
			Object cacheElement = getPersistableObjectFromSessionCache(sessionId, RembrandtConstants.SESSION_CRITERIA_BAG_KEY);
			if(cacheElement!=null) {
				theBag = (SessionCriteriaBag)cacheElement;
			}
			if(theBag==null){
				logger.debug("There is no query bag for session: "+sessionId);
				logger.debug("Creating new SessionCriteriaBag");
				theBag = new SessionCriteriaBag();
				Element element = new Element(RembrandtConstants.SESSION_CRITERIA_BAG_KEY, theBag);
				sessionCache.put(element);
			}
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
		myInstance.addNonPersistableToSessionCache(sessionId,graphData.getId(),graphData);
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
			}catch(NullPointerException e) {
				logger.debug("There was no Session Graphing Data under the key: "+graphId+" for the session: "+sessionId);
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
		}catch(NullPointerException e) {
			logger.debug("The SessionCache is empty for the session: "+sessionId);
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
		System.out.print("");
		SessionQueryBag theBag = null;
		try {
//			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_QUERY_BAG_KEY);
			Object cacheElement = getPersistableObjectFromSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY);
			if(cacheElement != null){
				theBag = (SessionQueryBag)cacheElement;
			}
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
	public List<UserList> getRembrandtUserList(String sessionId){
		System.out.print("");
		List<UserList> userlists = null;
		try {
//			Element cacheElement = sessionCache.get(RembrandtConstants.SESSION_QUERY_BAG_KEY);
			Object cacheElement = getPersistableObjectFromSessionCache(sessionId,RembrandtConstants.REMBRANDT_USER_LIST_BEAN_KEY);
			if(cacheElement != null){
				userlists = (List<UserList>)cacheElement;
			}
		}catch(ClassCastException cce) {
			logger.error("Someone put something other than a RembrandtUserListBean in the cache as a RembrandtUserListBean");
			logger.error(cce);
		}catch(NullPointerException npe){
			logger.debug("There is no query bag for session: "+sessionId);		
		}
		/**
		 * There is no SessionQueryBag for this session, create one
		 */
		if(userlists==null) {
			
			logger.debug("Creating new RembrandtUserListBean");
			userlists = new ArrayList<UserList>();
		}
		return userlists;
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
//    public Object getObjectFromSessionCache(String sessionId, String key) {
//    	Cache sessionCache = getSessionCache(sessionId);
//    	Object returnObject = null;
//    	try {
//			Element element = sessionCache.get(key);
//			if(element != null){
//				returnObject = element.getValue();
//			}
//		} catch (IllegalStateException e) {
//			logger.error(e);
//		} catch (CacheException e) {
//			logger.error(e);
//		} catch (NullPointerException e){
//            logger.error(e);
//        }
//    	return returnObject;
//    }
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
			if(tempReportName == null) {
				/**
				 * HACK! Somehow the old counter got blitzed!  This requires more
				 * looking into by us.  But this should resolve it short term.  This
				 * found....
				 */
				SessionTempReportCounter resetCounter = new SessionTempReportCounter();
				tempReportName = resetCounter.getNewTempReporterName();
				sessionCache.put(new Element(RembrandtConstants.REPORT_COUNTER, resetCounter));
			}
			
		}catch(IllegalStateException ise) {
			logger.error("Getting the SessionTempReportCounter from cache threw IllegalStateException");
			logger.error(ise);
		}
		catch(CacheException ce) {
			logger.error("Getting the SessionTempReportCounter from cache threw a new CacheException");
			logger.error(ce);
		}catch(ClassCastException cce) {
			logger.error("CacheElement was not a SessionTempReportCounter");
			logger.error(cce);
		}
		return tempReportName;
	}

	 
	/**
	/**
	 * Removes the session cache for the sessionId that was passed.  This should
	 * be called whenever a user session times out.  
	 * 
	 * @param  the session that wants to be logged out.
	 * @return
	 */
//	public boolean removeSessionCache(String rawSessionId) {
//		/*
//		 * process the sessionId to make sure that we have a session id
//		 * unique to the presentation tier cache
//		 */
//		String safeSessionId = processSessionId(rawSessionId);
//    	if(manager!=null && manager.cacheExists(safeSessionId)) {
//    		String[] beforeCaches = manager.getCacheNames();
//    		logger.debug("Current Caches");
//    		logger.debug("--------------------------------------");
//    		for(String name:beforeCaches) {
//    			logger.debug("cache: "+name);
//    		}
//    		logger.debug("--------------------------------------");
//    		logger.debug("removing all temp files associated with session");
//    		String sessionTempFolder = getSessionTempFolderPath(rawSessionId);
//    		/*
//    		 * remove the temp folder if the folder exist
//    		 */
//    		deleteAllFiles(sessionTempFolder);
//    		logger.debug("--------------------------------------");
//    		manager.removeCache(safeSessionId);
//    		logger.debug("Removing Cache: "+safeSessionId);
//    		String[] remainingCaches = manager.getCacheNames();
//    		logger.debug("Remaining Caches");
//    		logger.debug("--------------------------------------");
//    		for(String name:remainingCaches) {
//    			logger.debug("cache: "+name);
//    		}
//    		logger.debug("--------------------------------------");
//            return true;
//        }else {
//        	//there was no sessionCache to remove
//        	logger.debug("There was no sessionCache for : "+ rawSessionId+" to remove.");
//        	return false;
//        }
//    }
	
	/***
	 * Comment this!
	 * @param sessionId
	 * @param theBag
	 */
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag) {
		addPersistableToSessionCache(sessionId,RembrandtConstants.SESSION_QUERY_BAG_KEY, theBag );
	}
	
	public void putRembrandtUserList(String sessionId, List<UserList> userLists){
		addPersistableToSessionCache(sessionId, RembrandtConstants.REMBRANDT_USER_LIST_BEAN_KEY, (Serializable) userLists );
	}
	public void removeRembrandtUserList(String sessionId){
		removeObjectFromPersistableSessionCache(sessionId, RembrandtConstants.REMBRANDT_USER_LIST_BEAN_KEY);
	}
	/**
	 * This method will simply add the key/value pair to the Presentation Tier 
	 * main cache making it accessable by anyone in the presentation tier.
	 * 
	 * @param key
	 * @param value
	 */
//	public void addToPresentationCache(Serializable key, Serializable value) {
//		Cache applicationCache = getApplicationCache();
//		try {
//			Element element = new Element(key, value);
//			applicationCache.put(element);
//		}catch(IllegalStateException ise) {
//			logger.error("Checking applicationCache threw IllegalStateException");
//			logger.error(ise);
//		}catch(ClassCastException cce) {
//			logger.error("CacheElement was not a Collection");
//			logger.error(cce);
//		}
//	}
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
	 public static RembrandtPresentationTierCache getInstance() {
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
//	 public void addToSessionCache(String sessionId, Serializable key, Serializable value) {
//			Cache sessionCache = getSessionCache(sessionId);
//			Element element = new Element(key, value);
//			try {	
//				sessionCache.put(element);
//			}catch(IllegalStateException ise) {
//				logger.error("Placing object in SessionCache threw IllegalStateException");
//				logger.error(ise);
//			}catch(IllegalArgumentException iae) {
//				logger.error("Placing object in SessionCache threw IllegalArgumentException");
//				logger.error(iae);
//			}
//	}
	 
	 //public void persistUserSession(String userName, HttpSession session){
		 
	 //}
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
					logger.debug("The list of Object from the users session cache:");
	            	int i = 1;
					for(Object key: keys) {
						logger.debug("Key "+i+": "+key);
						i++;
						/**
						 * Right now only store the query map or the Temp Report Counter
						 */
						
						if((RembrandtConstants.SESSION_QUERY_BAG_KEY+this.PERSISTED_SUFFIX).equals(key)||	
							RembrandtConstants.REPORT_COUNTER.equals(key) ||
							(RembrandtConstants.REMBRANDT_USER_LIST_BEAN_KEY+this.PERSISTED_SUFFIX).equals(key)) {
							logger.debug("Key "+i+" being persisted: "+key);
							Element element = sessionCache.get((Serializable)key);
							persistedElements.put((Serializable)key, element);
						}
					}
					Cache persistedCache = manager.getCache(RembrandtPresentationCacheManager.PERSISTED_SESSIONS_CACHE);
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
//	private String processSessionId(String givenSessionId) {
//		String returnedSessionId = givenSessionId+"_presentation";
//		return returnedSessionId;
//	}
	/**
	 * Removes the session cache for the sessionId that was passed.  This should
	 * be called whenever a user logs out of the applicaiton.  Else if they log
	 * in from the same browser no matter what username, they will get the previous
	 * sessions information.
	 * 
	 * @param  the session that wants to be logged out.
	 */
	public void deleteSessionCache(String id) {
		String[] beforeCaches = manager.getCacheNames();
		logger.debug("Current Caches");
		logger.debug("--------------------------------------");
		for(String name:beforeCaches) {
			logger.debug("cache: "+name);
		}
		logger.debug("--------------------------------------");
		logger.debug("removing all temp files associated with session");
		String sessionTempFolder = getSessionTempFolderPath(id);
		/*
		 * remove the temp folder if the folder exist
		 */
		deleteAllFiles(sessionTempFolder);
		logger.debug("--------------------------------------");
		manager.removeCache(processSessionId(id));
		logger.debug("Removing Cache: "+processSessionId(id));
		String[] remainingCaches = manager.getCacheNames();
		logger.debug("Remaining Caches");
		logger.debug("--------------------------------------");
		for(String name:remainingCaches) {
			logger.debug("cache: "+name);
		}
		logger.debug("--------------------------------------");
	}
	/* Stores the path name where temporary files such as image files are stored for this session
	 * @see gov.nih.nci.rembrandt.cache.PresentationTierCache#addSessionTempFolderPath(java.lang.String, java.lang.String)
	 */
//	public void addSessionTempFolderPath(String sessionId, String sessionTempFolderPath) {
//		myInstance.addNonPersistableToSessionCache(sessionId,RembrandtConstants.SESSION_TEMP_FOLDER_PATH,sessionTempFolderPath);
//		
//	}
	/* Returns the path name where temporary files such as image files are stored for this session
	 * @see gov.nih.nci.rembrandt.cache.PresentationTierCache#addSessionTempFolderPath(java.lang.String, java.lang.String)
	 */
//	public String getSessionTempFolderPath(String sessionId) {
//		return (String) myInstance.getNonPersistableObjectFromSessionCache(sessionId,RembrandtConstants.SESSION_TEMP_FOLDER_PATH);
//	}
	/*
	 * remove the temp folder if the folder exist
	 */
//	private void deleteAllFiles(String filePath){
//		if(filePath != null){
//			File dir = new File(filePath);
//			File[] list = dir.listFiles();
//			for(File fileToDelete:list){
//					fileToDelete.delete();
//				}
//			dir.delete();
//			}
//		}
	public List<TaskResult> getAllSessionTaskResults(String sessionId) {
        List<TaskResult> tasks = new ArrayList<TaskResult>();
        Cache sessionCache = getSessionCache(sessionId);
        try {
            List keys = sessionCache.getKeys();
            for(Iterator i = keys.iterator();i.hasNext();) {
                Element element = sessionCache.get((String)i.next());
                Object object = element.getValue();
                if(object instanceof TaskResult) {
                    tasks.add((TaskResult)object);
                }
            }
        }catch(CacheException ce) {
            logger.error(ce);
        }
		return tasks;
	}
	public TaskResult getTaskResult(String sessionId, String taskId) {
		try {
			taskId = URLDecoder.decode ( taskId , "UTF-8" ) ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TaskResult taskResult = null;
		
        Cache sessionCache = getSessionCache(sessionId);
        try {
            List keys = sessionCache.getKeys();
            for(Iterator i = keys.iterator();i.hasNext();) {
                Element element = sessionCache.get((String)i.next());
                Object object = element.getValue();
                if(object instanceof TaskResult) {
                    TaskResult task = (TaskResult)object;
                    if(task.getTask() != null && task.getTask().getId().equals(taskId)){
                    	taskResult = task;
                    }
                }
            }
        }catch(CacheException ce) {
            logger.error(ce);
        }
		return taskResult;
	}

}
