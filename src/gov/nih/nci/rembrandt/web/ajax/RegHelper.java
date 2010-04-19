package gov.nih.nci.rembrandt.web.ajax;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

import gov.nih.nci.caintegrator.application.mail.Mail;
import gov.nih.nci.caintegrator.application.mail.MailProps;
import gov.nih.nci.caintegrator.exceptions.ValidationException;

public class RegHelper {
	public static String pReg(String ln, String fn, String em, String in, String ca, String ph, String de)	{
       	//look @ the captcha
		JSONObject jso = new JSONObject();
		String status = "pass";
		String msg = "";
		HttpSession session = ExecutionContext.get().getSession();
		String k = session.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY) != null ? (String) session.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY) : null;
		if(k!=null && k.equals(ca.trim()))	{

			try	{
	        	//need to clean the generalFeedback field, also put this in the props as a template
	        	MailProps mp = new MailProps();
	
	        	//make sure its only 1 email
	        	em = em.replace(",", "").replace(";","");
	        	//send the mail to APP support
	        	/*
	        	String fdbk = fn + " " + ln + " is requesting an account for the Rembrandt Application. \n\n";
	        	fdbk += "Their email is: " + em + " \n";
	        	fdbk += "Their institution is: " + in + " \n";
	        	fdbk += "\n\nThis is an automated email sent from the Rembrandt Application.\n";
	        	*/
	        	String fdbk = System.getProperty("rembrandt.register.template.support");
	        	fdbk = fdbk.replace("{last_name}", (ln!=null)? ln : "None");
	        	fdbk = fdbk.replace("{first_name}", (fn!=null)? fn : "None");
	        	fdbk = fdbk.replace("{email}", (em!=null)? em : "None");
	        	fdbk = fdbk.replace("{phone}", (ph!=null)? ph : "None");
	        	fdbk = fdbk.replace("{department}", (de!=null)? de : "None");
	        	fdbk = fdbk.replace("{institution}", (in!=null)? in : "None");
	        	
	        	
	        	mp.setBody(fdbk);
	        	//the fields below should be in a props file
	         	mp.setSmtp(System.getProperty("rembrandt.feedback.mailSMPT"));
	        	mp.setSubject(System.getProperty("rembrandt.register.mailSubject.support"));
	        	mp.setMailTo(System.getProperty("rembrandt.register.mailTo.support"));
	        	mp.setMailFrom(System.getProperty("rembrandt.feedback.mailFrom"));
	        	
	    		Mail.sendMail(mp);
	    		
	    		//send the mail to the user
	    		mp = new MailProps();
	    		fdbk = System.getProperty("rembrandt.register.template.user");
	        	fdbk = fdbk.replace("{last_name}", (ln!=null)? ln : "None");
	        	fdbk = fdbk.replace("{first_name}", (fn!=null)? fn : "None");
	    		/*
	        	fdbk = "Dear " + fn + " " + ln + ",\n Thanks for registering for access to the Rembrandt Application.  You will receive your official account information via email shortly.  Please contact ncicb@pop.nci.nih.gov for further assistance.\n";
	    		fdbk += "\n\nSincerely,\n-The Rembrandt Team";
	    		*/
	    		mp.setBody(fdbk);
	    		mp.setSmtp(System.getProperty("rembrandt.feedback.mailSMPT"));
	        	mp.setSubject(System.getProperty("rembrandt.register.mailSubject.user"));
	        	mp.setMailTo(em.trim());
	        	mp.setMailFrom(System.getProperty("rembrandt.feedback.mailFrom"));
	        	Mail.sendMail(mp);
	        	
	
	    	}
	    	catch (ValidationException e) {
	    		System.out.println("mail did not send from regHelper");
	    		status = "failed";
	    		msg = "SEND_FAILED";
	    	}
	    	catch (Exception e) {
	    		System.out.println("mail did not send from regHelper");
	    		status = "failed";
	    		msg = "SEND_FAILED_GENERIC";
	    	}
		}
		else	{
			status = "fail";
			msg = "BAD_CAPTCHA";
		}
		
		jso.put("status", status);
		jso.put("msg", msg);
		
		if(status.equals("pass"))	{
			jso.put("un", "RBTuser");
			jso.put("ps", "RBTpass");
		}
		return jso.toString();
	}
	/*public static String pListServe1(String email, String subscription){
		JSONObject jso = new JSONObject();
		String status = "pass";
		   try {
		        // Construct data
		      	String listServeName = System.getProperty("gov.nih.nci.rembrandt.list_serve.name")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.list_serve.name") : "REMBRANDT_USER_L";
		        String data = URLEncoder.encode("SUBED2", "UTF-8") + "=" + URLEncoder.encode(listServeName, "UTF-8");
		        data += "&" + URLEncoder.encode("A", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
		        data += "&" + URLEncoder.encode("s", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
		        //&0=&1=&2=&4=&p=&q=
		        if(subscription.equals("JOIN")){
			        data += "&" + URLEncoder.encode("b", "UTF-8") + "=" + URLEncoder.encode("Join+the+list", "UTF-8");
		        	
		        }else if(subscription.equals("LEAVE")){
			        data += "&" + URLEncoder.encode("a", "UTF-8") + "=" + URLEncoder.encode("Leave+the+list", "UTF-8");

		        }
		        // Send data
		      	String listServeURL = System.getProperty("gov.nih.nci.rembrandt.list_serve.url")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.list_serve.url") : "https://list.nih.gov/cgi-bin/wa?";

		        URL url = new URL(listServeURL);
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		    
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        while ((line = rd.readLine()) != null) {
		        	//System.out.println(line);
		            // Process line...
		        }
		        wr.close();
		        rd.close();
		    } catch (Exception e) {
		    	System.out.println("Error in sending list serve from regHelper");
	    		status = "failed";
		    }
		    jso.put("status", status);
			return jso.toString();
	}
	*/
	public static String pListServe(String email, String subscription){
		JSONObject jso = new JSONObject();
		String status = "pass";
		   try {
			 //need to clean the generalFeedback field, also put this in the props as a template
		      	String listServeName = System.getProperty("gov.nih.nci.rembrandt.list_serve.name")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.list_serve.name") : "REMBRANDT_USER_L";
		      	String listServeMailTo = System.getProperty("gov.nih.nci.rembrandt.list_serve.mailTo.support")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.list_serve.mailTo.support") : "listserv@list.nih.gov";


	        	MailProps mp = new MailProps();
 
	        	//the fields below should be in a props file
	         	mp.setSmtp(System.getProperty("rembrandt.feedback.mailSMPT"));	        	
	        	mp.setMailTo(listServeMailTo);
	        	mp.setMailFrom(email);
	        	

		        // Construct data
	    		String message ="";
		        if(subscription.equals("JOIN")){
		        	message = "subscribe "+ listServeName + " Anonymous";
		        	
		        }else if(subscription.equals("LEAVE")){
		        	message = "unsubscribe "+listServeName;

		        }
		        mp.setSubject(message);
	        	mp.setBody(message);
	        	
	    		Mail.sendMail(mp);

		    }  catch (ValidationException e) {
		    	System.out.println("Error in sending list serve from regHelper");
	    		status = "failed";
			}
		    jso.put("status", status);
			return jso.toString();
	}
}
