package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.dto.finding.FindingsResultset;
import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
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

	public Object getObjectFromSessionCache(String sessionId, String key);

	/**
	 * Removes the cache for the specified sessionId, if one exists.
	 * 
	 * @param sessionId Session to remove
	 * @return True-cache found and removed, False-Cache found for that session
	 */
	public boolean removeSessionCache(String sessionId);

	public String[] getCacheList();

	/**
	 * Adds a cache listener to the CacheManagerDelegate
	 * @param cacheListener
	 */
	@SuppressWarnings("unchecked")
	public void addCacheListener(CacheListener cacheListener);

	/**
	 * removes a cache listener from the CacheManagerDelegate
	 * @param cacheListener
	 */
	public void removeCacheListener(CacheListener cacheListener);

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
	public ReportBean getReportBean(String sessionId, String queryName);

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
	public ReportBean getReportBean(String sessionId, String queryName,
			View view);

	/**
	 * This method will add a serializable key/value pair to the given sessions
	 * cache.
	 *  
	 * @param sessionId
	 * @param key
	 * @param value
	 */
	public void addToSessionCache(String sessionId, Serializable key,
			Serializable value);

	/**
	 * This method will return a unique name, for the specified session, via the
	 * use of the SessionTempReportCounter object that is added to every
	 * sessionCache at instantiation. This method should be used to generate 
	 * unique temporary report names for any given session within the
	 * application.  It has a reference to a counter (SessionTempReportCounter)
	 * that is present and unique for every session, and tracks the number of
	 * unique temp reports.  It uses this counter to create a new query/report
	 * name that will be unique for the session.
	 * 
	 * @param sessionId --the session that wants the report/query name
	 * @return tempReportName --a unique name for the report/query
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
	 * This method when given a sessionId and a queryName will check the 
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
	 * This is a convenience method for returning the SessionQueryBag for the
	 * the specified session.  If there is no SessionQueryBag stored in the
	 * cache for the session, it will create one and return it.   
	 * 
	 * @param --the sessionId you want the bag for
	 * @return --the SessionQueryBag for the session
	 */
	public SessionQueryBag getSessionQueryBag(String sessionId);

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
	 * This simply puts the SessionQueryBag into the sessionCache
	 * 
	 * @param sessionId --the session that this query bag should be associated
	 * with
	 * @param theBag --the bag you want to set in the cache.
	 */
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag);

	/*****
	 * This is the signature that will allow the middle tier to store graphing 
	 * session specific CachableGraphData
	 * @param sessionId
	 * @param graphData
	 */
	public void addSessionGraphingData(String sessionId,
			CachableGraphData graphData);
	/**
	 * Retrieves the stored CachebaleGraphData from the session cache using the
	 * sessionID that was passed.
	 * 
	 * @param sessionId
	 * @param graphId
	 * @return
	 */
	public CachableGraphData getSessionGraphingData(String sessionId,
			String graphId);

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
	public void putSessionCriteriaBag(String sessionId,
			SessionCriteriaBag theBag);

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
	public FindingsResultset getAllFindingsResultsets(String sessionId,
			String taskId);

}