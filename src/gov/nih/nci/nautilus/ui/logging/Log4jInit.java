/*
 * Created on Nov 21, 2004
 */
package gov.nih.nci.nautilus.ui.logging;

/**
 * @author David C Bauer
 */


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import gov.nih.nci.nautilus.constants.NautilusConstants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;

public class Log4jInit extends HttpServlet {
  public void init() {
    String prefix =  getServletContext().getRealPath("/");
    String file = getInitParameter("log4j-init-file");
    if(file != null) {
      PropertyConfigurator.configure(prefix+file);
      Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
      logger.debug("Nautilus Logger Initialized");
    }
  }

  public
  void doGet(HttpServletRequest req, HttpServletResponse res) {
  }
}


