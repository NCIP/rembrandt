package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.service.findings.Finding;

import java.io.Serializable;
import java.util.Collection;

import net.sf.ehcache.Cache;

public interface BusinessTierCache {

	public Cache getSessionCache(String sessionId);

	public Object getObjectFromSessionCache(String sessionId, String key);

	public boolean removeSessionCache(String sessionId);

	public String[] getCacheList();

	public void addCacheListener(CacheListener cacheListener);

	public void removeCacheListener(CacheListener cacheListener);

	public void addToSessionCache(String sessionId, Serializable key,
			Serializable value);

	public void addToApplicationCache(Serializable key, Serializable value);

	public Collection<Finding> getAllSessionFindings(String sessionId);

	public Finding getSessionFinding(String sessionId, String taskId);
	
	public Object getFromApplicationCache(String lookupType);
	

}