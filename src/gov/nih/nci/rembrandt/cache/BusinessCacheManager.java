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

public class BusinessCacheManager implements BusinessTierCache{
	
    //This value must match the name of the cache in the configuration xml file
	private static final String REMBRANDT_CACHE = "applicationCache"; 
    private static transient List cacheListeners;
    private static Logger logger = Logger.getLogger(BusinessCacheManager.class);
    private static CacheManager manager = null;
    private static BusinessCacheManager instance = null;       
    //Create the CacheManager and the ApplicationCache
    static {
     	try {
           instance = new BusinessCacheManager();
           manager = CacheManager.getInstance();
           logger.debug("Getting ehCache manager instance");
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
    		/**
        	 * Here are the parameters that we are using for creating the business
        	 * tier Application Cache
        	 *  	CacheName = REMBRANDT_CACHE;
        	 *  	Max Elements in Memory = 100;
        	 *  	Overflow to disk = false;
        	 *  	Make the cache eternal = true;
        	 *  	Elements time to live in seconds = 0 (Special setting which means never check);
        	 *  	Elements time to idle in seconds = 0 (Special setting which means never check);
        	 *  
        	 */
        	applicationCache = new Cache(BusinessCacheManager.REMBRANDT_CACHE, 100, false, true, 0, 0);
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
        	/**
        	 * Here are the parameters that we are using for creating the business
        	 * tier session caches
        	 *  	CacheName = the sessionId;
        	 *  	Max Elements in Memory = 1000;
        	 *  	Overflow to disk = false;
        	 *  	make the cache eternal = true;
        	 *  	elements time to live in seconds = 0 (Special setting which means never check);
        	 *  	elements time to idle in seconds = 0 (Special setting which means never check);
        	 *  
        	 */
            sessionCache = new Cache(sessionId, 1000, false, true, 0, 0);
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
