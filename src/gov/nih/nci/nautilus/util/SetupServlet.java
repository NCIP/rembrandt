package gov.nih.nci.nautilus.util;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SetupServlet extends HttpServlet {
  private static final String CLASSNAME = "SetupServlet";
  private static SetupServlet onlyInstance = null;
  private static String logFile;
  
  public static synchronized SetupServlet getInstance()
    {
      if (onlyInstance == null){
            onlyInstance = new SetupServlet();
		 }
      return onlyInstance;
    }

 /*private SetupServlet(){
    try{
      init();  
      }  
    catch(Exception e){
	  e.printStackTrace();
	  }
   }
   */   
  public void init() throws UnavailableException {
    ServletConfig config = getServletConfig();
    ServletContext context = getServletContext();

    String realPath = context.getRealPath("/");	
    logFile = realPath + config.getInitParameter("logfile");
    if (logFile != null) {
      logFile = logFile.replace('|', File.separatorChar);
      context.setAttribute("logfile", logFile);
      } 
	else {
      log(CLASSNAME + ": Unable to load init parameters."); 
      throw new UnavailableException("Setup not complete.");
    } 
	
	System.out.println("logFile is :"+logFile);  
     }
   
  public static String getLogFile(){
    return logFile;
   }  
} 
