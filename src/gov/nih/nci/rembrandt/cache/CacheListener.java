package gov.nih.nci.rembrandt.cache;

/**
 * A simple interface that is used by classes that want to be notified
 * when the BusinessCacheManager creates or destroys a cache.  At the
 * time of writing it was only used for the creation/destruction of
 * session based caches
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public interface CacheListener {
	public void cacheCreated(String cacheId);
    public void cacheRemoved(String cacheId);
}
