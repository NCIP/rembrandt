package gov.nih.nci.nautilus.cache;

import java.io.Serializable;
import java.util.Collection;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.bean.SessionQueryBag;
import gov.nih.nci.nautilus.view.View;

/**
 * This interface was written to hide the implementation of the cache
 * from the classes that use it.  In the instance that we decide to 
 * change out our caching mechanism we simply need to change out our
 * CacheManagerDelegate and make sure that the new Class uses this
 * interface.  If the cache needs more conveniece methods they should
 * be added here first and not just to the CacheManagerDelegate!
 * 
 * Any references to the cache should be made through this interface.
 * Since most of the methods in the CacheManagerDelegate are actually
 * private, this requirements is explicitly forced.
 * 
 * @author BauerD
 * Mar 10, 2005
 * 
 */
public interface ConvenientCache {
	public ReportBean getReportBean(String sessionId, String queryName, View view);
	public void addToSessionCache(String sessionId, Serializable key, Serializable value);
	public String getTempReportName(String sessionId);
	public Collection checkLookupCache(String lookupType);
	public void addToApplicationCache(Serializable key, Serializable value);
	public SessionQueryBag getSessionQueryBag(String sessionId);
	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag);
	public CompoundQuery getQuery(String sessionId, String queryName);
}
