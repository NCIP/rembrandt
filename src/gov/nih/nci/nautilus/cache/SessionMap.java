package gov.nih.nci.nautilus.cache;
import java.util.HashMap;

import javax.servlet.http.HttpSession;


/**
 * A simple decorator to avoid casting to HttpSession
 * 
 * @author BauerD
 * Feb 9, 2005
 * 
 */
public class SessionMap extends HashMap{
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public SessionMap() {
    	super();
    }
  
    public void putSession(HttpSession session) {
    	super.put(session.getId(), session);
    }
    
    public HttpSession getSession(String sessionId) {
    	return (HttpSession)super.get(sessionId);
    }
}
