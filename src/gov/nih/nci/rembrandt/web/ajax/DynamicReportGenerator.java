package gov.nih.nci.rembrandt.web.ajax;

import java.util.Map;

import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.FindingReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicReportGenerator {

		
	public DynamicReportGenerator()	{}
	
	public void generateDynamicReport(String key, Map<String, String> params)	{
		String html = new String();

		HttpSession session = ExecutionContext.get().getSession(false);
		PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();
		HttpServletRequest request = ExecutionContext.get().getHttpServletRequest();
//		HttpServletResponse response = ExecutionContext.get().getHttpServletResponse();
		
		//only generate XML if its not already cached...leave off for debug
		if(ptc.getObjectFromSessionCache(session.getId(), key) == null)	{
			Object o = btc.getObjectFromSessionCache(session.getId(), key);
			Finding finding = (Finding) o; 
			//generate the XML and cached it
			ReportGeneratorHelper.generateReportXML(finding);
		}
		Object ob = ptc.getObjectFromSessionCache(session.getId(), key);
		if(ob != null && ob instanceof FindingReportBean)	{
			try	{
				FindingReportBean frb = (FindingReportBean) ob;
				Document reportXML = (Document) frb.getXmlDoc();
			
				html = ReportGeneratorHelper.renderReport(params, reportXML,"cc_report.xsl");
			}
			catch(Exception e)	{
				html = "Error Generating the report.";
			}
		}
		else	{
			html = "Error generating the report";
		}
		//out the XHTML in the session for reference in presentation...could store in Prescache
		session.setAttribute(key+"_xhtml", html);
		return;
	}
}
