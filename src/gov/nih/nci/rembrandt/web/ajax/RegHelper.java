package gov.nih.nci.rembrandt.web.ajax;

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
}
