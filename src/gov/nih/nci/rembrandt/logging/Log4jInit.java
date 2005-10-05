/*
 * Created on Nov 21, 2004
 */
package gov.nih.nci.rembrandt.logging;
/**
 * @author David C Bauer
 */

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {
  public void init() {
    String prefix =  getServletContext().getRealPath("/");
    String file = getInitParameter("log4j-init-file");
    if(file != null) {
      PropertyConfigurator.configure(prefix+file);
      Logger logger = Logger.getLogger(Log4jInit.class);
      logger.debug("Nautilus Logger Initialized");
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) {
  	
  }
}


