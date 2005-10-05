package gov.nih.nci.rembrandt.web.inbox;

import java.util.Random;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.ExecutionContext;

public class QueryInbox {
	
	public QueryInbox()	{
		//nada
	}
	
	public String checkStatus()	{
		//simulate that the query is still running, assuming we have only 1 query for testing
		
		HttpSession session = ExecutionContext.get().getSession(false);

		Random r = new Random();
		int randInt = Math.abs(r.nextInt()) % 11;
		if(randInt % 2 == 0)
			return "false";
		else
			return "true";
	}

}
