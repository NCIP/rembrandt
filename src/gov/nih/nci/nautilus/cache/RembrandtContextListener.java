package gov.nih.nci.nautilus.cache;

import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener;
/**
 * This listener is used by the application to be notified of 
 * @author BauerD
 * Feb 17, 2005
 * 
 */
public class RembrandtContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		SessionTracker.setAppplicationRunning(true);

	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		SessionTracker.setAppplicationRunning(false);

	}

}
