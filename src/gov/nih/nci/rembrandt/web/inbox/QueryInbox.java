package gov.nih.nci.rembrandt.web.inbox;

import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.ExecutionContext;

public class QueryInbox {
	
	private HttpSession session;
	private BusinessTierCache btc;
	private PresentationTierCache ptc;
	
	public QueryInbox()	{
		//get some common stuff
		session = ExecutionContext.get().getSession(false);
		btc = ApplicationFactory.getBusinessTierCache();
		ptc = ApplicationFactory.getPresentationTierCache();
	}
	
	public QueryInbox(HttpSession session)	{
		this.session = session;
		btc = ApplicationFactory.getBusinessTierCache();
		ptc = ApplicationFactory.getPresentationTierCache();
	}
	
	
	public HashMap checkSingle(String sid, String tid)	{
		//check the status of a single task
		HashMap currentStatus = new HashMap();
		
		Finding f = (Finding) btc.getObjectFromSessionCache(sid, tid);
		
		switch(f.getStatus())	{
			case Completed:
				currentStatus.put(tid, "completed");
			break;
			case Running:
				currentStatus.put(tid, "running");
			break;
			case Error:
				currentStatus.put(tid, "error");
			break;
			default:
				currentStatus.put(tid, "running");
			break;
		}
		
		return currentStatus;
	}
	
	public HashMap checkAllStatus(String sid)	{
		HashMap currentStatuses = new HashMap();
		
		Collection<Finding> findings = btc.getAllSessionFindings(sid);
		for(Finding f: findings){
			HashMap tmp = new HashMap();
			tmp = this.checkSingle(sid, f.getTaskId());
			currentStatuses.put(tmp.get("taskId"), tmp.get("status"));
		}
		
		return currentStatuses;
	}
	
	
	public Map mapTest(String testKey)	{
		Map myMap = new HashMap();
		myMap.put("firstKey", testKey);
		myMap.put("secondKey", testKey+"_1");
		return myMap;
	}
	
	public String checkStatus()	{
		//simulate that the query is still running, assuming we have only 1 query for testing

		Random r = new Random();
		int randInt = Math.abs(r.nextInt()) % 11;
		if(randInt % 2 == 0)
			return "false";
		else
			return "true";
	}

	public String getQueryName()	{
		String st = "nothing";
		
		try	{
			st = String.valueOf(ptc.getSessionQueryBag(session.getId()).getQueries().size());
		}
		catch(Exception e){
			st = "no worky";
		}
		
		return st;
		
		
	}
}
