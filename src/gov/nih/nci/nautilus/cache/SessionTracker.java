package gov.nih.nci.nautilus.cache;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


/**
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class SessionTracker implements HttpSessionListener {
    private static Logger logger = Logger.getLogger(SessionTracker.class);
    
	private static int totalActiveSessions = 0;
    
	public void sessionCreated(HttpSessionEvent evt) {
		totalActiveSessions++;
        CacheOverlord.addSession(evt.getSession());
        logger.debug("New session added: "+ evt.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent evt) {
		totalActiveSessions--;
        CacheOverlord.removeSession(evt.getSession());
        logger.debug("Session Destroyed: "+ evt.getSession().getId());
	}

	/**
	 * @return Returns the totalActiveSessions.
	 */
	public int getTotalActiveSessions() {
		return totalActiveSessions;
	}
}
