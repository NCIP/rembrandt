package gov.nih.nci.nautilus.cache;

/**
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public interface CacheListener {
	public void cacheCreated(String cacheId);
    public void cacheRemoved(String cacheId);
}
