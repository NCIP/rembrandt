package gov.nih.nci.nautilus.cache;

import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener;
/**
 * This listener has one purpose and that is to notify the
 * SessionTracker class when the application context has been 
 * loaded or unloaded. Check the application Web.xml for the
 * intialization of this listener.
 * 
 * @author BauerD
 * Feb 17, 2005
 * 
 */
public class RembrandtContextListener implements ServletContextListener {
	/** 
	 * this method is fired whenever application server loads the context
	 * that this listener is added to in the web.xml
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		SessionTracker.setAppplicationRunning(true);

	}

	/**
	 * this method is fired whenever the application server unloads 
	 * the context that this listener is added to in the web.xml
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		SessionTracker.setAppplicationRunning(false);

	}

}
