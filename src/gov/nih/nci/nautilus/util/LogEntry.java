package gov.nih.nci.nautilus.util;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.log4j.Level; 

public class LogEntry {  

   private static final String timestampFormat = "hh:mm:ss.SSSS MM-dd-yyyy";
   private static final SimpleDateFormat formatter = 
    new SimpleDateFormat(timestampFormat);
   private Level logLevel;
   private static String logFile; 
   private Date date;
   private String message; 
   private Throwable throwable;   
 
   
  public LogEntry(Level logLevel, String message) {
    this.logLevel = logLevel; 	
	this.date = new Date();	
    this.message = (message == null) ? "" : date +(":\n") + message;    
  }
  
  public LogEntry(Level logLevel, Throwable throwable) {
    this.logLevel = logLevel; 	
	this.date = new Date();
	this.throwable = throwable;
    this.message = (message == null) ? "" : date +(":\n") + throwableToString(this.throwable);  
  }

 public String getMessage(){
   return this.message;
  } 
  
 public Level getLevel(){
   return this.logLevel;
  } 
  
  private String throwableToString(Throwable throwable) {
    StringWriter strWriter = new StringWriter();
    PrintWriter prnWriter = new PrintWriter(strWriter);
    if (throwable != null) {
      throwable.printStackTrace(prnWriter);
    }
    prnWriter.flush();
    return(strWriter.toString());
  } 
  
 public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(formatter.format(date));   
    buffer.append(message);  
    return(buffer.toString());
  }
}
