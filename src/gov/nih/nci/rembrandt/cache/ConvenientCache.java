package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.dto.finding.FindingsResultset;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.view.View;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This interface was written to hide the implementation of the cache
 * from the classes that use it.  In the instance that we decide to 
 * change out our caching mechanism we simply need to change out our
 * CacheManagerDelegate and make sure that the new Class uses this
 * interface.  If the cache needs more conveniece methods they should
 * be added here first and not just to the CacheManagerDelegate!
 * 
 * Any references to the cache should be made through this interface.
 * 
 * @author BauerD, SahniH
 * Mar 10, 2005
 * Oct 10, 2005 Added by SahniH
 * public SessionCriteriaBag getSessionCriteriaBag(String sessionId) and
 * public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag);
 * 
 */
public interface ConvenientCache {
	/**
	 * Returns a ReportBean if one is stored in the session cache using the
	 * parameters passed.  The view is required to help enforce a back end
	 * result container/view discrepency that is curently present in the
	 * Rembrandt code base.
	 *  
	 * @param sessionId
	 * @param queryName
	 * @param view
	 * @return
	 */
	public ReportBean getReportBean(String sessionId, String queryName, View view);
	/**
	 * This will add the Serializable Object in the session cache under the 
	 * Serializable key.
	 * 
	 * @param sessionId --which session cache to store the object in
	 * @param key --the key to retrieve the object from the session cache
	 * @param value --the Serializable object you want to store in the session
	 *  cache
	 */
	public void addToSessionCache(String sessionId, Serializable key, Serializable value);
	/**
	 * This method will return a unique name, for the specified session, via the
	 * use of the SessionTempReportCounter object that is added to every
	 * sessionCache at instantiation.
	 * 
	 * @param sessionId  --the session you are looking for a unique name for
	 * @return --a unique String name for this session
	 */
	public String getTempReportName(String sessionId);
	/**
	 * This method is intended to check the application cache for the specified
	 * lookupType that is stored.  It really is the same as making a call to the
	 * getFromApplicationCache(key) method, except that all lookupType objects
	 * should implement the java.util.Collection interface.  Returns null if the
	 * Collection is not found or if an error is thrown.
	 * 
	 * @param lookupType  Check the constants in the LookupManager to see what
	 * is meant by lookupType.
	 * 
	 * @return 
	 */
	public Collection checkLookupCache(String lookupType);
	/**
	 * This method takes a key value pair of serializable objects and places them
	 * in the application cache.
	 * @param key --This is the key that will retrieve the stored value
	 * @param value --This is the value to be stored
	 */
	public void addToApplicationCache(Serializable key, Serializable value);
	/**
	 * This is a convenience method for returning the SessionQueryBag for the
	 * the specified session.  If there is no SessionQueryBag stored in the
	 * cache for the session, it will create one and return it.   
	 * 
	 * @param --the sessionId you want the bag for
	 * @return --the SessionQueryBag for the session
	 */
	public SessionQueryBag getSessionQueryBag(String sessionId);
	/**
	 * This simply puts the SessionQueryBag into the sessionCache
	 * 
	 * @param sessionId --the session that this query bag should be associated
	 * with
	 * @param theBag --the bag you want to set in the cache.
	 */
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag);
	/**
	 * This method, when given a sessionId and a queryName, will check the 
	 * session cache for the stored compoundQuery.  If it is not stored or 
	 * causes an exception it will return Null.  If you are getting Null values,
	 * when you know that you have stored the query in the sessionCache, check
	 * the log files as any exceptions will be written there.
	 *   
	 * @param sessionId --the session that should have the query stored
	 * @param queryName --the query that is desired
	 * @return compoundQuery
	 */
	public CompoundQuery getQuery(String sessionId, String queryName);
	/**
	 * There is a subtle distinction between SampleSets and ResultSets, one is
	 * a ReportBean where isSampleSet()==true and the latter,isSampleSet()==false.
	 * That is it.  What that currently means is that one ReportBean is a list of
	 * samples selected from a regular result set.  This list of samples is then
	 * used to create a SampleCriteria that is placed in a GeneExpressionQuery that
	 * has a ClinicalView.  This gives us the ability to extract or look at these
	 * "Samples of Interest" at any time, and applying them to other queries if
	 * the user desires.    
	 * 
	 * @param sessionId --identifies the sessionCache that you want a complete
	 * list of SampleSetNames stored in.
	 */
	public List getSampleSetNames(String sessionId);
	/**
	 * The method will return all of the SampleSet ReportBeans that are stored
	 * in the sessionCache of the sessionId specified.  This is NOT all the
	 * ReportBeans in the sessionCache.  They will only be the SampleSets that
	 * were explicitly created and stored by the user in the cache.  To get all
	 * the ReportBeans use the getAllReportBeans(String sessionId) method of
	 * this class.
	 * 
	 * @param sessionId
	 * @return --all the SampleSet ReportBeans that are stored in the associated
	 * sessionCache
	 */
	public Collection getAllSampleSetReportBeans(String sessionId);
	/**
	 * This method will return all of the ReportBeans that are stored in the
	 * sessionCache of the sessionId specified.
	 *
	 * @param sessionId --the sessionCache that you want the ReportBeans from
	 * @return --All the ReportBeans in the sessionCache
	 */
	public Collection getAllReportBeans(String sessionId);
	/**
	 * This method should return the desired GraphData from the session cache
	 * 
	 * @param sessionId
	 * @param graphType
	 * @return
	 */
	public CachableGraphData getSessionGraphingData(String sessionId, String graphId);
	
	/**
	 * This is a convenience method for returning the SessionCriteriaBag for the
	 * the specified session.  If there is no SessionCriteriaBag stored in the
	 * cache for the session, it will create one and return it.   
	 * 
	 * @param --the sessionId you want the bag for
	 * @return --the SessionCriteriaBag for the session
	 */
	public SessionCriteriaBag getSessionCriteriaBag(String sessionId);
	/**
	 * This simply puts the SessionCriteriaBag into the sessionCache
	 * 
	 * @param sessionId --the session that this query bag should be associated
	 * with
	 * @param theBag --the bag you want to set in the cache.
	 */
	public void putSessionCriteriaBag(String sessionId, SessionCriteriaBag theBag);
	/**
	 * This method will retreive a serializable value for a given sessionId and key from the
	 * cache.
	 *  
	 * @param sessionId
	 * @param key
	 * @return Object
	 */
	public Object getObjectFromSessionCache(String sessionId, String key) ;
	/**
	 * Returns a FindingsResultset if one is stored in the session cache using the
	 * parameters passed.  
	 *  
	 * @param sessionId
	 * @return Collection
	 */
	public Collection getAllFindingsResultsets(String sessionId);
	/**
	 * Returns a FindingsResultset if one is stored in the session cache using the
	 * parameters passed.  
	 *  
	 * @param sessionId
	 * @param taskId (queryName)
	 * @return FindingsResultset
	 */
	public FindingsResultset getAllFindingsResultsets(String sessionId, String taskId);
	
}
