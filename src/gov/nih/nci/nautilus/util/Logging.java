package gov.nih.nci.nautilus.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.FileAppender;


public class Logging {

  static Logger logger = Logger.getLogger(Logging.class);   
  
  public static void  add(LogEntry logEntry){    
     SimpleLayout layout = new SimpleLayout();  
     FileAppender appender = null; 
	 try{
	   appender = new FileAppender(layout, SetupServlet.getLogFile(), false);	    
	   } 
	 catch(Exception ex){
	  ex.printStackTrace();
	 }
	   
     logger.addAppender(appender);
	 logger.setLevel(logEntry.getLevel());
	 if(logEntry.getLevel().equals(Level.INFO)){
	    logger.info(logEntry.getMessage());
	   }
     else if(logEntry.getLevel().equals(Level.DEBUG)){
	    logger.debug(logEntry.getMessage());	   
	  }
	 else if(logEntry.getLevel().equals(Level.ERROR)){
	    logger.error(logEntry.getMessage());		   
	 } 
	 else if (logEntry.getLevel().equals(Level.WARN)){
	    logger.warn(logEntry.getMessage());
     }
	 else if (logEntry.getLevel().equals(Level.FATAL)){
	   logger.fatal(logEntry.getMessage());	   
	  }
	 else{
	  logger.info(logEntry.getMessage());	  
	   } 
	 }
	 
  
  public static  void log(String message) {
     SimpleLayout layout = new SimpleLayout();  
     FileAppender appender = null;  

      try {	        
	      appender = new FileAppender(layout,"C:\\Dev\\caintegrator\\log\\log.txt",false);
          } 
	  catch(Exception e) {
          e.printStackTrace();
         }

      logger.addAppender(appender);
      logger.setLevel((Level) Level.DEBUG);
      logger.debug(message);
      }


 // this is for testing purposes only
  public static void main(String args[]) {
  
    SimpleLayout layout = new SimpleLayout();
	FileAppender appender = null;
	
	try {
         appender = new FileAppender(layout,"output1.txt",false);
      } 
	catch(Exception e) {
	  e.printStackTrace();
	}
    logger.addAppender(appender);
	logger.setLevel((Level) Level.DEBUG);    
	
	logger.debug("Here is some DEBUG");
    logger.info("Here is some INFO");
    logger.warn("Here is some WARN");
    logger.error("Here is some ERROR");
    logger.fatal("Here is some FATAL");
   }  

 }
 
 

